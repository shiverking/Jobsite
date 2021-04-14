package model;

/**
 * @author ：闫崇傲
 * @description：用户类定义
 * @date ：2021/4/14 22:30
 */
public class User {
    //用户id
    private int id;
    //用户名
    private String username;
    //密码
    private String password;
    //电话
    private String telephone;
    //邮箱
    private String email;
    //地址
    private String location;
    //身份（存储权限）(employer,employee,admin,superadmin)
    private String identity;
    //头像存储地址
    private String headurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", identity='" + identity + '\'' +
                ", headurl='" + headurl + '\'' +
                '}';
    }
}
