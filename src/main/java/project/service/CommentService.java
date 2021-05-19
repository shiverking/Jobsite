package project.service;

import project.model.Comment;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：评论的service
 * @date ：2021/5/10 0:29
 */
public interface CommentService {
    //查询自己发出的评论
    List<Comment> getOwnComments(int reviewer_id);
    //查询自己收到的评论
    List<Comment> getCommentsAboutMe(int commented_id);
    //查看某项具体的评论
    Comment getCommentById(int reviewer_id,int commented_id);
    //插入一条新评论
    boolean insertNewComment(int reviewer_id,int commented_id,String content,double score,Date create_time);
    //查询是否已经评价过
    Boolean ifReviewed(int reviewer_id,int commented_id);
}
