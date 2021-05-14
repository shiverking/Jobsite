package project.model;

/**
 * @author ：闫崇傲
 * @description：个人简历的实体类
 * @date ：2021/4/14 22:51
 */
public class Profile {
    private int id;
    //建立所属用户的id
    private int user_id;
    //用户的姓名
    private String user_name;
    //自我介绍
    private String biography;
    //工作经历
    private String workexperience;
    //专业等级
    private String expertize_level;
    //专业领域
    private String expertize_realm;
    //预期完成时间
    private String completiontime;
    //预期酬金
    private String compensation;

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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getWorkexperience() {
        return workexperience;
    }

    public void setWorkexperience(String workexperience) {
        this.workexperience = workexperience;
    }

    public String getExpertize_level() {
        return expertize_level;
    }

    public void setExpertize_level(String expertize_level) {
        this.expertize_level = expertize_level;
    }

    public String getExpertize_realm() {
        return expertize_realm;
    }

    public void setExpertize_realm(String expertize_realm) {
        this.expertize_realm = expertize_realm;
    }

    public String getCompletiontime() {
        return completiontime;
    }

    public void setCompletiontime(String completiontime) {
        this.completiontime = completiontime;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", biography='" + biography + '\'' +
                ", workexperience='" + workexperience + '\'' +
                ", expertize_level='" + expertize_level + '\'' +
                ", expertize_realm='" + expertize_realm + '\'' +
                ", completiontime='" + completiontime + '\'' +
                ", compensation='" + compensation + '\'' +
                '}';
    }
}
