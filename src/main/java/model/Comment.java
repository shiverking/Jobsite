package model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：评论的实体类
 * @date ：2021/4/14 22:41
 */
public class Comment {
    private int id;
    //评论者的id
    private int reviewer_id;
    //被评论者id
    private int commented_id;
    //评论内容
    private String content;
    //评分
    private int score;
    //评论时间
    private Date create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(int reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    public int getCommented_id() {
        return commented_id;
    }

    public void setCommented_id(int commented_id) {
        this.commented_id = commented_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", reviewer_id=" + reviewer_id +
                ", commented_id=" + commented_id +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", create_time=" + create_time +
                '}';
    }
}
