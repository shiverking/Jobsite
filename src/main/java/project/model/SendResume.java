package project.model;

import javax.xml.crypto.Data;

/**
 * @author syrm
 * @description 投递简历实体类
 * @date 2021/04/14
 */
public class SendResume {
    //投递简历id
    private int id;
    //投递的简历id
    private int profile_id;
    //投递的简历目标工作id
    private int job_id;
    //创建时间
    private Data create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public Data getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Data create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "SendResume{" +
                "id=" + id +
                ", profile_id=" + profile_id +
                ", job_id=" + job_id +
                ", create_time=" + create_time +
                '}';
    }
}
