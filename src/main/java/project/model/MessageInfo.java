package project.model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：发送的消息实体类(Message类已经被占用)
 * @date ：2021/4/14 22:38
 */
public class MessageInfo{
    int id;
    //发送者的id
    private int sender_id;
    //接受者的id
    private int receiver_id;
    //消息内容
    private String content;
    //发送的时间
    private Date send_time;

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

    @Override
    public String toString() {
        return "message{" +
                "id=" + id +
                ", sender_id=" + sender_id +
                ", receiver_id=" + receiver_id +
                ", content='" + content + '\'' +
                ", send_time=" + send_time +
                '}';
    }
}
