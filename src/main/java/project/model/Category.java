package project.model;

/**
 * @author ：闫崇傲
 * @description：发布的职位所属的种类
 * @date ：2021/4/14 23:25
 */
public class Category {
    private int id;
    //种类名称
    private int name;
    //所属的jobid
    private int job_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name=" + name +
                ", job_id=" + job_id +
                '}';
    }
}
