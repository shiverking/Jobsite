package project.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author ：闫崇傲
 * @description：用户类定义
 * @date ：2021/4/14 22:30
 */

public class User {
    //用户id
    private int id;
    //用户名
    @NotEmpty(message = "用户名不能为空")
    private String username;
    //密码
    @Size(min = 6, max = 15,message = "密码的位数为6~15")
    private String password;
    //电话
    @NotEmpty(message = "手机号不能为空")
    private String telephone;
    //邮箱
    @Email(message = "请输入正确的邮箱地址")
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
//    //账户是否过期
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//    //账户是否被锁定
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//    //证书是否过期
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//    //账户是否能使用
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

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
