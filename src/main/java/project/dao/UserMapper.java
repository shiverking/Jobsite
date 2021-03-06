package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
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
     int insertUser(@Param("id") int id,@Param("username") String username,@Param("password") String password,@Param("telephone") String telephone,@Param("email") String email,@Param("headurl") String headurl);
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
     //根据ID找到用户名
     String getUsernameById(@Param("id") int id);
     //根据用户名找到ID
     int getIdByUserName(@Param("username") String username);
     //根据id删除用户
     int deleteUserById(@Param("id") int id);
     //获取用户列表（雇主和应聘者）
     List<User> getUserList();
     //查询用户（雇主和应聘者）
     List<User> searchUser(String username, String telephone, int rid);
     //查询用户（管理员）
     List<User> searchAdmin(String username, String telephone);
     //获取用户总数
     int getUserCount();
     //更新用户信息
     int updateUserInfo(@Param("id") int id,@Param("password") String password,@Param("username") String username,@Param("telephone") String telephone,@Param("email") String email);
     //获取管理员列表
     List<User> getAdminList();
     //通过id获取用户
     User getUserById(@Param("id") int id);
     //根据ID找到聊天对象的头像地址
     String getHeadurlById(@Param("id")int id);
     //根据ID 修改聊天对象的头像地址
     int updateHeadUrlById(@Param("headurl") String headurl,@Param("id")int id);
     //通过用户的id找到其位置
     String getLocationById(@Param("id")int id);
     //根据id修改用户当前位置
     int updateLocationById(@Param("location") String location,@Param("id")int id);
     //返回所有应聘者的id
     List<Integer> getAllEmployeeId();

}
