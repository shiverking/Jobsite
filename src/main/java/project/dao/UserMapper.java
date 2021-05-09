package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.Role;
import project.model.User;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
     //通过用户名找到用户
     User findUserByUsername (String username);
     //添加一名用户
     int insertUser(@Param("id") int id,@Param("username") String username,@Param("password") String password,@Param("telephone") String telephone,@Param("email") String email);
     //通过电话找到用户
     User findUserByTelephone(@Param("telephone") String telephone);
     //查看所有用户
     List<User> getAllUsers();
     //删除用户账号
     int deleteUserByUsername(@Param("username") String username);
     //Email是否存在
     boolean isEmailExist(@Param("email")String email);
     //通过Email找到Password
     String findPasswordByEmail(@Param("email") String email);
     //通过Email更新Password
     int updatePasswordByEmail(@Param("password") String password,@Param("email") String email);
     //找到数据库中最后一位ID
     int getLastId();
     //重要函数，登录自动调用
     User loadUserByUsername(@Param("username") String username);
     //获取用户的角色列表
     List<Role> getUserRolesByUid(@Param("id") int id);
     //根据ID找到密码
     String findPasswordById(@Param("id") int id);
     //根据ID修改密码
     int changePassWordById(@Param("password") String password,@Param("id") int id);
     //根据ID修改邮箱
     int setEmailById(@Param("email")String Email,@Param("id")int id);
     //根据ID找到邮箱
     String getEmailById(@Param("id") int id);
     //根据用户名修改密码
     int changePasswordByUsername(@Param("password") String password,@Param("username") String username);
     //根据ID获得用户
     User findUserById(@Param("id") int id);
     //根据RID获得用户
     List<User> getUserByRid(@Param("rid") int rid);
     //用户名是否存在
     boolean isUsernameExist(@Param("username") String username);
     //手机号是否存在
     boolean isTelephoneExist(@Param("telephone") String telephone);
}
