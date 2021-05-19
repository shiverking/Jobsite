package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.CommentMapper;
import project.model.Comment;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：CommentService的实现类
 * @date ：2021/5/10 0:31
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentMapper commentMapper;
    /**
     * 获得自己对别人的评价
     * @param reviewer_id
     * @return
     */
    @Override
    public List<Comment> getOwnComments(int reviewer_id) {
        return commentMapper.getOwnComments(reviewer_id);
    }

    /**
     * 获得别人对自己的评价
     * @param commented_id
     * @return
     */
    @Override
    public List<Comment> getCommentsAboutMe(int commented_id) {
        return commentMapper.getCommentsAboutMe(commented_id);
    }

    /**
     * 查看某一项具体的评价
     * @param reviewer_id
     * @param commented_id
     * @return
     */
    @Override
    public Comment getCommentById(int reviewer_id, int commented_id) {
        return commentMapper.getCommentById(reviewer_id,commented_id);
    }

    /**
     * 插入一条新评价
     * @param reviewer_id
     * @param commented_id
     * @param content
     * @param score
     * @param create_time
     * @return
     */
    @Override
    public boolean insertNewComment(int reviewer_id, int commented_id, String content, double score, Date create_time) {
        if(commentMapper.insertNewComment(reviewer_id,commented_id,content,score,create_time)==1){
            return true;
        }
        return false;
    }

    /**
     * 查询是否已有评价记录
     * @param reviewer_id
     * @param commented_id
     * @return
     */
    @Override
    public Boolean ifReviewed(int reviewer_id, int commented_id) {
        return commentMapper.ifreviewed(reviewer_id,commented_id);
    }
}

