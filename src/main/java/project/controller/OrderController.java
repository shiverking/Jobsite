package project.controller;

import com.google.gson.Gson;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;
import project.model.*;
import project.service.*;

import javax.jms.Session;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author syrm
 * @description：order的Controller
 * @date ：2021/4/19 14:53
 */

@Controller
public class OrderController {


    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    MessageService messageService;

    @Autowired
    HireService hireService;

    @Autowired
    ProfileService profileService;



    @PreAuthorize("hasRole('ROLE_employer')" )
    @RequestMapping("/order/jobOrders")
    public ModelAndView toManageJob() {
        ModelAndView modelAndView = new ModelAndView("/job/order_job");
        return modelAndView;
    }

    //获取order信息
    //根据user_id去查job，返回页面展示的信息有 工作名称，参与工作的人员和人数，开始时间和结束时间，以及状态
    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/order/getOrders")
    public RespBean getOrdersBystate(@RequestBody Map<String, Object> info) {
        //获取当前用户上下文
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        //获取当前用户的所有订单的jobid
        List<Integer> jobids = jobService.getAllIdByUser(user.getId());
        List<String> job_titles = new ArrayList<>();
        //获取当前用户的所有的订单
        List<Order> orders = new ArrayList<>();
        //默认得到全部订单
        String state = "5";
        //获取想要查询的属性，参数无则默认查询全部order
        if (!info.get("state").equals("5")) {
            state = (String) info.get("state");
        }
        for (int job_id : jobids) {
            Order order = orderService.getOrderByJobId(job_id);
            if (order == null) {
                continue;
            }
            if (!state.equals("5")) {
                if (order.getState().equals(state)) {
                    orders.add(order);
                    job_titles.add(jobService.findJobById(job_id).getTitle());
                }
            } else {
                orders.add(order);
                job_titles.add(jobService.findJobById(job_id).getTitle());
            }
        }
        //将订单排序，按顺序返回，新建立的订单将首先展示
        orders.sort((o1, o2) -> {
            int flag = o1.getCreated_time().compareTo(o2.getCreated_time());
            return flag*-1;
        });
        Map<String, Object> map = new HashMap<>();
        map.put("order", orders);
        map.put("job_title", job_titles);
        return RespBean.ok("查询订单", map);
    }

    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/order/updateState")
    public RespBean updataStatue(@RequestBody Map<String, Object> info) {
        try {
            if (info.get("id") == null || info.get("state") == null) {
                return RespBean.error("更新状态发生错误，请重新尝试");
            } else {
                int id = (int) info.get("id");
                String state = (String) info.get("state");
                orderService.updateOrderState(id, state);
                if (state.equals("3")) {
                    Date now = new Date();
                    orderService.updateEnd(id, now);
                }
                return RespBean.ok("更新状态成功");
            }
        } catch (Exception e) {
            System.out.println(e);
            return RespBean.error("更新状态失败");
        }

    }

    @ResponseBody
    @RequestMapping("/order/sendMessage")
    public RespBean sendMessage(@RequestBody Map<String, Object> info) {

        try {
            if (info.get("id") == null || info.get("state") == null) {
                return RespBean.error("发送通知消息失败");
            } else {
                int id = (int) info.get("id");
                String state = (String) info.get("state");
                //获取发送消息的用户id
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = (User) principal;
                int myId = user.getId();
                //获取订单所属的jobid
                int jobid = orderService.getJobById(id);
                //根据jobid去hire表里获取雇佣的人，即消息通知的对象
                List<Integer> emp = hireService.getEmployerByJob(jobid);
                //根据不同状态编写发送消息内容
                String message = "";
                if (state.equals("1")) {
                    message += "您所属该<a href ='/job/" + jobid + "'>工作</a>，目前已经完成预支付，如有疑问，请尽快与雇主联系";
                } else if (state.equals("2")) {
                    message += "您所属该<a href ='/job/" + jobid + "'>工作</a>，目前已经完成工作，如有疑问，请尽快与雇主联系";
                } else if (state.equals("3")) {
                    message += "您所属该<a href ='/job/" + jobid + "'>工作</a>，目前已经最终支付，该订单即将完成，如有疑问，请尽快与雇主联系";
                }
                //给order所属的每个雇员发送消息
                for (int e : emp) {
                    if (!messageService.isFirstChat(myId, e)) {
                        Date date = new Date();
                        messageService.insertChatUserLink(myId, e, date);
                        int link_id = messageService.selectLinkId(myId, e);
                        messageService.insertChatList(link_id, myId, e);
                    }
                    Date now = new Date();
                    int link_id = messageService.selectLinkId(myId, e);
                    messageService.saveMessage(link_id, myId, e, message, now);
                }
                return RespBean.ok("消息发送成功");
            }
        } catch (Exception e) {
            System.out.println(e);
            return RespBean.error("发送通知消息失败");
        }
    }



    @ResponseBody
    @RequestMapping("/order/saveOrderSession")
    public RespBean OrderDetailSave(HttpServletRequest request, @RequestParam int orderId){
        try {
            HttpSession session = request.getSession();
            session.setAttribute("orderId", orderId);
            return RespBean.ok("success");
        }catch (Exception e){
            return RespBean.error("failed");
        }
    }

    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/order/getDetails")
    public RespBean OrderDetailGet(HttpServletRequest request){
            HttpSession session = request.getSession();
            if(session.getAttribute("orderId") == null){
                return RespBean.error("获取订单详情失败");
            }else {
                int orderId = (int) session.getAttribute("orderId");
                Order order =  orderService.findOrderById(orderId);
                Map<String,Object> map = new HashMap<>();
                Job job = jobService.findJobById(order.getJob_id());
                //由于User的getAuthorities方法返回空指针，所以导致数据无法json化，故建立多个列表分别返回需要的值
                List<Integer> employersId = hireService.getEmployerByJob(job.getId());
                List<String> userNames = new ArrayList<>();
                List<String> headUrls = new ArrayList<>();
                List<String> telephones = new ArrayList<>();
                for(int id : employersId){
                    User user = userService.findUserById(id);
                    userNames.add(user.getUsername());
                    headUrls.add(user.getHeadurl());
                    telephones.add(user.getTelephone());
                }
                //order存储order对象，job存储order所属job对象，employersId为应聘的id,userNames为应聘的姓名,headUrls为应聘的头像,telephones为头像
                map.put("order",order);
                map.put("job",job);
                map.put("Ids",employersId);
                map.put("userNames",userNames);
                map.put("headUrls",headUrls);
                map.put("telephones",telephones);
                return RespBean.ok("success",map);
            }
    }


    @RequestMapping("/order/showDetails")
    public ModelAndView showd(){
        ModelAndView modelAndView = new ModelAndView("/page/order_staff");
        return  modelAndView;
    }

    //订单评论参与订单的工作人员
    @RequestMapping("/order/comment")
    public ModelAndView commit(){
        ModelAndView modelAndView = new ModelAndView("/page/orderComment");
        return modelAndView;
    }


    //返回数据
    @ResponseBody
    @RequestMapping("/order/toComment")
    public RespBean OrderDetailComment(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("orderId") == null){
            return RespBean.error("获取订单详情失败");
        }else {
            int orderId = (int) session.getAttribute("orderId");
            Order order =  orderService.findOrderById(orderId);
            Map<String,Object> map = new HashMap<>();
            Job job = jobService.findJobById(order.getJob_id());
            List<Integer> employersId = hireService.getEmployerByJob(job.getId());
            List<Profile> profiles = new ArrayList<>();
            List<String> headUrls = new ArrayList<>();
            for(int id : employersId){
                User user = userService.findUserById(id);
                headUrls.add(user.getHeadurl());
                profiles.add(profileService.getProfile(id));
            }
            map.put("Ids",employersId);
            map.put("headUrls",headUrls);
            map.put("profiles",profiles);
            return RespBean.ok("success",map);
        }
    }
}
