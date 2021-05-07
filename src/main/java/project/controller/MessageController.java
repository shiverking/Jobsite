package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.model.MessageInfo;
import project.model.RespBean;
import project.model.User;
import project.service.MessageServiceImpl;
import project.service.UserServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author ：闫崇傲
 * @description：Message的Controller
 * @date ：2021/4/19 14:11
 */
@Controller
public class MessageController {
    @Autowired
    MessageServiceImpl messageServiceImpl;
    @Autowired
    UserServiceImpl userServiceImpl;
    //SimpMessagingTemplate这个类主要是实现向浏览器发送消息的功能
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 内部类，用于记录左侧聊天列表信息,可随时修改
     */
    class ResultInfo{
        //和自己聊过天的人的id
        private int id;
        //和自己聊过天的人的用户名
        private String userName;
        //构造函数
        public ResultInfo(int id, String userName) {
            this.id = id;
            this.userName = userName;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    /**
     * 和某个具体单位聊天时的controller
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/message")
    public String toMessagePage(Model model ,@RequestParam int id){
        //获取用户的验证信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int myId = user.getId();
        //没聊过天，在主表生成一条记录返回用户聊天关系表id，并在聊天列表表分别插入两条记录
        if(!(messageServiceImpl.isFirstChat(myId,id))&&id!=myId){
            Date date =new Date();
            //在主表生成一条记录,如果生成成功
            if(messageServiceImpl.insertChatUserLink(myId,id,date))
            {
                //获取主表的id
                int link_id  = messageServiceImpl.selectLinkId(myId,id);
                //聊天关系列表插入两条记录
                messageServiceImpl.insertChatList(link_id,myId,id);
            }
            //查询和自己聊过天的列表
            List<Integer>chatListId = messageServiceImpl.getFromUserChatList(myId);
            //获取聊天的信息列表
            List<ResultInfo> chatList = new ArrayList<ResultInfo>();
            for (int tmpId: chatListId) {
                chatList.add(new ResultInfo(tmpId,userServiceImpl.getUserName(tmpId)));
            }
            //model.addAttribute("chatListId",chatListId);
            model.addAttribute("chatList",chatList);
            model.addAttribute("myName",user.getUsername());
        }
        //查看聊天双方是否以前聊过天,如果聊过天
        else{
            //查询和自己聊过天的列表
            List<Integer>chatListId = messageServiceImpl.getFromUserChatList(myId);
            //获取聊天的信息列表
            List<ResultInfo> chatList = new ArrayList<ResultInfo>();
            for (int tmpId: chatListId) {
                chatList.add(new ResultInfo(tmpId,userServiceImpl.getUserName(tmpId)));
            }
            //model.addAttribute("chatListId",chatListId);
            model.addAttribute("chatList",chatList);
            model.addAttribute("myName",user.getUsername());
        }
        return "message/index";
    }

    /**
     * 直接打开聊天界面的controller
     * @param model
     * @return
     */
    @RequestMapping("/chatlist")
    public String toMessagePage(Model model){
        //获取用户的验证信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int myId = user.getId();
        //查询和自己聊过天的列表
        List<Integer>chatListId = messageServiceImpl.getFromUserChatList(myId);
        //获取聊天的姓名列表
        List<ResultInfo> chatList = new ArrayList<ResultInfo>();
        for (int tmpId: chatListId) {
            chatList.add(new ResultInfo(tmpId,userServiceImpl.getUserName(tmpId)));
        }
        //model.addAttribute("chatListId",chatListId);
        model.addAttribute("chatList",chatList);
        model.addAttribute("myName",user.getUsername());
        return "message/index";
    }

    /**
     * 保存聊天记录
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/message/saveMessage")
    public RespBean saveMessage(@RequestBody Map<String, Object> info){
        //获取用户的验证信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int myId = user.getId();
        //现在日期
        Date now  = new Date();
        String toUserName = (String) info.get("toUserName");
        String content = (String) info.get("content");
        int receiver_id = userServiceImpl.getId(toUserName);
        int link_id = messageServiceImpl.selectLinkId(myId,receiver_id);
        if(messageServiceImpl.saveMessage(link_id,myId,receiver_id,content,now)){
            return RespBean.ok("信息保存成功");
        }
        return RespBean.error("信息保存失败");
    }
    /**
     * 单聊发信息的控制器
     * @param principal
     * @param msg
     */
    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
        //原始分割符号
        String line_split = new String(new byte[]{0x01});
        String[] split = msg.split(line_split,2);
        //发送消息
        messagingTemplate.convertAndSendToUser(split[0], "/queue/chat",split[1]);
    }

    /**
     * 查询消息记录，默认查五条
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/message/queryMessageRecord")
    public List<MessageInfo> queryMessageRecord(@RequestBody Map<String, Object> info){
        //获取用户的验证信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int myId = user.getId();
        //获取对方的id
        String toUserName = (String) info.get("toUserName");
        int receiver_id = userServiceImpl.getId(toUserName);

        return messageServiceImpl.getRecentChatRecords(myId,receiver_id,5);
    }
}
