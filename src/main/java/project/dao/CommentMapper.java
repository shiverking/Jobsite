package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.Comment;

import java.util.List;

/**
 * @author ：闫崇傲
 * @description：Comment映射接口
 * @date ：2021/5/10 00:18
 */
@Mapper
@Component
public interface CommentMapper {
    //查询自己发出的评论
    List<Comment> getOwnComments(@Param("reviewer_id") int reviewer_id);
    //查询自己收到的评论
    List<Comment> getCommentsAboutMe(@Param("commented_id") int commented_id);
    //根据评论id查看具体的某项评论
    Comment getCommentById(@Param("id") int id);
}
