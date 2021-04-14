package model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：账户实体类
 * @date ：2021/4/14 22:45
 */
public class account {
    private int id;
    //用户的id
    private int user_id;
    //金额
    private double money;
    //最后修改的时间
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "account{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", money=" + money +
                ", time=" + time +
                '}';
    }
}
