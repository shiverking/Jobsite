package project.model;

import java.util.Date;

/**
 * @author ：订单日志实体类
 * @description：雇佣者提供的工作日志
 * @date ：2021/4/14 23:31
 */
public class OrderLog {
    private int id;
    //日志内容
    private String content;
    //日志创建时间
    private Date create_time;
    //提交日志人的id
    private int user_id;
    //提交的订单的id
    private int order_id;

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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "OrderLog{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                ", user_id=" + user_id +
                ", order_id=" + order_id +
                '}';
    }
}
