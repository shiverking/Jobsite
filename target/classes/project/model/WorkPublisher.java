package project.model;

/**
 * @author ：闫崇傲
 * @description：雇主额外属性实体
 * @date ：2021/4/14 23:43
 */
public class WorkPublisher {
    private int id ;
    //雇主id
    private int user_id;
    //
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "WorkPublisher{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", info='" + info + '\'' +
                '}';
    }
}
