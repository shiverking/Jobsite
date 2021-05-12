package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.dao.UserMapper;
import project.exception.ServiceException;
import project.model.User;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int insertUser(User user) throws ServiceException {
        if (userMapper.findUserByUsername(user.getUsername()) != null) {
            throw new ServiceException("该用户名已经存在", "202");
        }
        if (userMapper.findUserByTelephone(user.getTelephone()) != null) {
            throw new ServiceException("该手机号已经存在", "203");
        }
        return userMapper.insertUser(user.getId(),user.getUsername(),user.getPassword(),user.getTelephone(),user.getEmail());
    }

    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 判断email是否存在
     *
     * @param email
     * @return 存在则返回true, 不存在则返回false
     */
    @Override
    public boolean isEmailExist(String email) {
        return userMapper.isEmailExist(email);
    }

    @Override
    public boolean changePasswordByEmail(String email, String password) {
        if (userMapper.updatePasswordByEmail(email,password) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 找到User表中最后一位ID
     * @return
     */
    @Override
    public int getLastId() {
        try{
            return userMapper.getLastId();
        }catch (Exception e){
            //如果是第一个数据
            return 0;
        }
    }

    /**
     * 登录时调用
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if(user.equals(null)){
            throw new UsernameNotFoundException("账户不存在!");
        }
        user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        return user;
    }

    /**
     * 根据id获取密码
     * @param id
     * @return
     */
    @Override
    public String getPassWord(int id) {
        return userMapper.findPasswordById(id);
    }

    /**
     * 改变用户密码
     * @param id
     * @return
     */
    @Override
    public boolean changePassword(String password,int id) {
        if(userMapper.changePassWordById(password,id)==1){
            return true;
        }
        return false;
    }

    /**
     * 通过ID获取邮箱
     * @param id
     * @return
     */
    @Override
    public String getEmail(int id) {
        return userMapper.getEmailById(id);
    }

    /**
     * 通过用户名返回id
     * @param username
     * @return
     */
    @Override
    public int getId(String username) {
        return userMapper.getIdByUserName(username);
    }

    /**
     * 通过id获取用户名
     * @param id
     * @return
     */
    @Override
    public String getUserName(int id) {
        return userMapper.getUsernameById(id);
    }

    /**
     * 修改用户的email
     * @param email
     * @param id
     * @return
     */
    @Override
    public boolean setEmail(String email, int id) {
        if(userMapper.setEmailById(email,id)==1){
            return true;
        }
        return false;
    }

    /**
     * 根据id来返回user对象
     * @param id
     * @return
     */
    public User getUserById(int id){

        return userMapper.getUserById(id);
    }
}
