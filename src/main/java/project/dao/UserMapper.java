package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.User;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
     User findUserByUsername (String username);
     int insertUser(User user);
     User findUserByTelephone(@Param("telephone") String telephone);
     //查看所有用户
     List<User> getAllUsers();
     //根据身份查看用户
     List<User> getUserByIdentity(@Param("identity") String identity);
     //修改用户密码
     int updatePassword(@Param("password") String password,@Param("id") int id);
     //删除用户账号
     int deleteUser(@Param("id") int id);
     boolean isEmailExist(@Param("email")String email);
     String findPasswordByEmail(@Param("email") String email);
     int updatePasswordByEmail(@Param("password") String password,@Param("email") String email);
}
