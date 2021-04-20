package project.service;


import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.UserMapper;
import project.exception.ServiceException;
import project.model.User;

import javax.validation.Valid;
import java.util.Random;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserMapper userMapper ;

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int insertUser(User user ) throws ServiceException {
            if (userMapper.findUserByUsername(user.getUsername()) != null) {
                throw new ServiceException("该用户名已经存在","202");
            }
            if (userMapper.findUserByTelephone(user.getTelephone()) != null) {
                throw new ServiceException("该手机号已经存在","203");
            }
        return userMapper.insertUser(user);
    }

    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
