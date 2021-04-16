package project.model;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：发布的职位实体类
 * @date ：2021/4/14 23:20
 */
public class Job {
    private int id;
    //雇佣者id
    private int employer_id;
    //题目
    private String title;
    //描述
    private String description;
    //预算
    private double budget;
    //所需专业等级
    private String expertize_level;
    //是否通过审核
    private boolean isCheck;
    //创建时间
    private Date create_time;
    //所需人数
    private int required;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(int employer_id) {
        this.employer_id = employer_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getExpertize_level() {
        return expertize_level;
    }

    public void setExpertize_level(String expertize_level) {
        this.expertize_level = expertize_level;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", employer_id=" + employer_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", budget=" + budget +
                ", expertize_level='" + expertize_level + '\'' +
                ", isCheck=" + isCheck +
                ", create_time=" + create_time +
                ", required=" + required +
                '}';
    }
}
