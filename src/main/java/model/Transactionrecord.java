package model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：交易记录实体类
 * @date ：2021/4/14 22:48
 */
public class Transactionrecord {
    private  int id ;
    //记录内容
    private String record;
    //创建时间
    private Date create_time;
    //所属账户id
    private int account_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "Transactionrecord{" +
                "id=" + id +
                ", record='" + record + '\'' +
                ", create_time=" + create_time +
                ", account_id=" + account_id +
                '}';
    }
}
