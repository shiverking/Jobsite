package project.service;

import project.exception.ServiceException;
import project.model.User;

public interface UserService {
    //通过用户名找到用户
    User findUserByUsername(String username);
    //插入一名用户
    int insertUser(User user) throws ServiceException;
    //根据手机好生成验证码
    String generateAuthCode(String telephone);
    //输入的邮箱是否存在
    boolean isEmailExist(String email);
    //根于用户的EmaiL修改password
    boolean changePasswordByEmail(String email,String password);
    //获得数据库中最后一位的ID
    int getLastId();
    //根据用户ID获取密码
    String getPassWord(int id);
    //修改用户密码
    boolean changePassword(String password,int id);
    //修改邮箱
    boolean setEmail(String Email,int id);
    //通过ID获取邮箱
    String getEmail(int id);
    //通过ID获得用户名
    String getUserName(int id);
    //通过用户名获取id
    int getId(String username);
}
