package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.model.Comment;
import project.model.RespBean;
import project.model.User;
import project.service.CommentServiceImpl;
import project.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：闫崇傲
 * @description：评论的controller
 * @date ：2021/5/10 0:29
 */
@Controller
public class CommentController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    CommentServiceImpl commentServiceImpl;

    /**
     * 内部类，用于记录评论,包括我对他人的和他人对我的评论
     */
    class CommentDetail{
        //被评论者的姓名
        private String name;
        //被评论者id
        private int id;
        //评论内容
        private String content;
        //评分
        private double score;
        //评论时间
        private Date create_time;
        //构造函数

        public CommentDetail(int id,String name,String content, double score, Date create_time) {
            this.name = name;
            this.id = id;
            this.content = content;
            this.score = score;
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public Date getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Date create_time) {
            this.create_time = create_time;
        }

    }

    /**
     * 返回评价页面
     * @param model
     * @return
     */
    @RequestMapping("/appraisals")
    public String getAppraisals(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "job/appraisals";
    }

    /**
     * 评价某人,返回被评价人的用户名
     *
     * @param model
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/judge")
    public List<String> judgeSomeOne(Model model, @RequestParam int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        List<String> newList = new ArrayList<String>();
        //0
        newList.add(userServiceImpl.getUserName(id));
        //1
        newList.add(userServiceImpl.getHeadurl(id));
        //2
        newList.add(String.valueOf(id));
        //查询两人是否已评价过
        boolean res = commentServiceImpl.ifReviewed(user.getId(),id);
        //3
        newList.add(String.valueOf(res));
        //如果已经评价过，则返回查询的记录
        if(res){
            Comment comment = commentServiceImpl.getCommentById(user.getId(),id);
            //4
            newList.add(comment.getContent());
            //5
            newList.add(String.valueOf(comment.getScore()));
        }
        return newList;
    }
    /**
     * 保存评价信息
     *
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/judge/confirm")
    public RespBean confirmJudgement(@RequestBody Map<String, Object> info) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int id = Integer.valueOf((String)info.get("id"));
        double score = Double.valueOf((String)info.get("score"));
        String content = (String) info.get("content");
        Date now = new Date();
        if (commentServiceImpl.insertNewComment(user.getId(), id, content, score, now)) {
            return RespBean.ok("保存评价成功");
        }
        return RespBean.error("保存评价失败");
    }


    @ResponseBody
    @PostMapping("/judge/fromme")
    public List<CommentDetail> getJudgesFromMe(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        List<Comment> comments = commentServiceImpl.getOwnComments(user.getId());

        //获取聊天的姓名列表
        List<CommentDetail> commentFromMeList = new ArrayList<CommentDetail>();
        for (Comment comment: comments) {
            int commented_id = comment.getCommented_id();
            commentFromMeList.add(new CommentDetail(commented_id,userServiceImpl.getUserName(commented_id),comment.getContent(),comment.getScore(),comment.getCreate_time()));
        }
        return commentFromMeList;
    }

    @ResponseBody
    @PostMapping("/judge/tome")
    public List<CommentDetail> getJudgesToMe(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        List<Comment> comments = commentServiceImpl.getCommentsAboutMe(user.getId());

        //获取聊天的姓名列表
        List<CommentDetail> commentToMeList = new ArrayList<CommentDetail>();
        for (Comment comment: comments) {
            int reviewer_id = comment.getReviewer_id();
            commentToMeList.add(new CommentDetail(reviewer_id,userServiceImpl.getUserName(reviewer_id),comment.getContent(),comment.getScore(),comment.getCreate_time()));
        }
        return commentToMeList;
    }
    /**
     * 查询某个评价
     *
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/judge/findJudgement")
    public List<String> findjJudgeMent(@RequestBody Map<String, Object> info) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        //获取查询的类型，1为查关于自己的评论，2为查自己对别人的评论
        String type = (String)info.get("type");
        //对方的id
        int id = Integer.valueOf((String)info.get("id"));
        System.out.println("=======type="+type+"==========id="+id);
        List<String> newList = new ArrayList<String>();
        //0
        newList.add(userServiceImpl.getUserName(id));
        //1
        newList.add(userServiceImpl.getHeadurl(id));
        //2
        newList.add(String.valueOf(id));
        if(type.equals("1")){
            Comment comment = commentServiceImpl.getCommentById(id,user.getId());
            //3
            newList.add(comment.getContent());
            //4
            newList.add(String.valueOf(comment.getScore()));
        }
        if(type.equals("2")){
            Comment comment = commentServiceImpl.getCommentById(user.getId(),id);
            //3
            newList.add(comment.getContent());
            //4
            newList.add(String.valueOf(comment.getScore()));
        }
        return newList;
    }
}
