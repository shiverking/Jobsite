package project.model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：订单实体类
 * @date ：2021/4/14 23:27
 */
public class Order {
    private int id;
    //由哪个job生成而来的那个jobid
    private int job_id;
    //订单状态（只有四种）
    private String state;
    //开始时间
    private Date created_time;
    //结束时间
    private Date end_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", job_id=" + job_id +
                ", state='" + state + '\'' +
                ", created_time=" + created_time +
                ", end_time=" + end_time +
                '}';
    }
}
