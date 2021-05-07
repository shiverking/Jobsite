package project.model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：发送的消息实体类(Message类已经被占用)
 * @date ：2021/4/14 22:38
 */
public class MessageInfo{
    //文本
    public static final int MESSAGE_TYPE_TEXT = 0;

    //图片
    public static final int MESSAGE_TYPE_IMAGE = 1;

    int id;
    //聊天关系表id
    private int link_id;
    //发送者的id
    private int sender_id;
    //接受者的id
    private int receiver_id;
    //消息内容
    private String content;
    //发送的时间
    private Date send_time;
    //是否是最后一条
    private Boolean isLatest;

    //消息类型  0--普通文本（默认）
    private int type = MESSAGE_TYPE_TEXT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public int getLink_id() {
        return link_id;
    }

    public void setLink_id(int link_id) {
        this.link_id = link_id;
    }

    public Boolean getLatest() {
        return isLatest;
    }

    public void setLatest(Boolean latest) {
        isLatest = latest;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "id=" + id +
                ", link_id=" + link_id +
                ", sender_id=" + sender_id +
                ", receiver_id=" + receiver_id +
                ", content='" + content + '\'' +
                ", send_time=" + send_time +
                ", isLatest=" + isLatest +
                ", type=" + type +
                '}';
    }
}
