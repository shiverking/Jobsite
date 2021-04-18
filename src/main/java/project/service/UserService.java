package project.service;

import project.model.User;

public interface UserService {
    User findUserByUsername(String username);
    int insertUser(User user);
    String generateAuthCode(String telephone);

}
