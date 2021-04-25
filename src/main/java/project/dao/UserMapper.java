package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
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
     //根据身份查看用户
     List<User> getUserByIdentity(@Param("identity") String identity);
     //修改用户密码
     int updatePassword(@Param("password") String password,@Param("id") int id);
     //删除用户账号
     int deleteUser(@Param("id") int id);
     //Email是否存在
     boolean isEmailExist(@Param("email")String email);
     //通过Email找到Password
     String findPasswordByEmail(@Param("email") String email);
     //通过Email更新Password
     int updatePasswordByEmail(@Param("password") String password,@Param("email") String email);
     //找到数据库中最后一位ID
     int getLastId();
}
