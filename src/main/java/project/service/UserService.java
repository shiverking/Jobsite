package project.service;

import project.exception.ServiceException;
import project.model.User;

public interface UserService {
    User findUserByUsername(String username);
    int insertUser(User user) throws ServiceException;
    String generateAuthCode(String telephone);
    boolean isEmailExist(String email);
    boolean changePasswordByEmail(String email,String password);
    //获得数据库中最后一位的ID
    int getLastId();
    //根据用户ID获取密码
    String getPassWord(int id);
    //修改用户密码
    boolean changePassword(String password,int id);
}
