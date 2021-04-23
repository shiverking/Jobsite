package project.service;

import project.exception.ServiceException;
import project.model.User;

public interface UserService {
    User findUserByUsername(String username);
    int insertUser(User user) throws ServiceException;
    String generateAuthCode(String telephone);
    boolean isEmailExist(String email);
    boolean changePasswordByWord(String email,String password);
}
