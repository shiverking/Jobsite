package project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：发布的职位实体类
 * @date ：2021/4/14 23:20
 */


@Data
public class Job {
    private int id;
    //雇佣者id
    private int employer_id;
    //题目
    @NotNull(message = "工作标题不能为空")
    private String title;
    //描述
    @NotNull(message = "描述不能为空")
    private String description;
    //时薪
    @NotNull(message = "时薪不能为空")
    @Min(value = 1, message = "时薪最少为1")
    private double budget;
    //所需专业等级
    @NotNull(message = "所需专业等级不能为空")
    private String expertize_level;
    //是否通过审核
    private boolean check;

    //创建时间
    private Date create_time;
    //所需人数
    @NotNull(message = "工作所需人数不能为空")
    @Min(value = 1,message = "最少需求人数为1")
    private int required;
    //每日平均工作时间
    @NotNull(message = "每日平均工作时间不能为空")
    @Min(value = 1,message = "每日平均工作时间最小为1")
    @Max(value = 24,message="每日平均工作时间最大为24")
    private int work_time;
    //工作所需要的技术
    private String skill;
    //岗位
    private String position;
    //该招聘是否开启
    private boolean job_status;

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

    public boolean isCheck() { return check; }

    public void setCheck(boolean newCheck) {
        check = newCheck;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getWork_time() {
        return work_time;
    }

    public void setWork_time(int work_time) {
        this.work_time = work_time;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public boolean isJob_status() {
        return job_status;
    }

    public void setJob_status(boolean job_status) {
        this.job_status = job_status;
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
                ", check=" + check +
                ", create_time=" + create_time +
                ", required=" + required +
                ", work_time=" + work_time +
                ", skill='" + skill + '\'' +
                ", position='" + position + '\'' +
                ", job_statue=" + job_statue +
                '}';
    }
}
