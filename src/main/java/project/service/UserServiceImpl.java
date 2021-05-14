package project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.dao.UserMapper;
import project.exception.ServiceException;
import project.model.Job;
import project.model.User;

import java.util.List;
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
        //默认头像
        return userMapper.insertUser(user.getId(),user.getUsername(),user.getPassword(),user.getTelephone(),user.getEmail(),"/assets/avatar/default.png" );
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

    @Override
    public boolean deleteUserById(int id) {
        if(userMapper.deleteUserById(id)==1)
            return true;
        else return false;
    }

    @Override
    public PageInfo<User> findUserByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<User> info = new PageInfo<User>(userMapper.getUserList());
        return info;
    }

    @Override
    public PageInfo<User> findAdminByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<User> info = new PageInfo<User>(userMapper.getAdminList());
        return info;
    }

    @Override
    public PageInfo<User> searchUserByPage(Integer page, Integer limit, String username, String telephone, int rid) {
        PageHelper.startPage(page,limit);
        PageInfo<User> info = new PageInfo<User>(userMapper.searchUser(username,telephone,rid));
        return info;
    }

    @Override
    public PageInfo<User> searchAdminByPage(Integer page, Integer limit, String username, String telephone) {
        PageHelper.startPage(page,limit);
        PageInfo<User> info = new PageInfo<User>(userMapper.searchAdmin(username,telephone));
        return info;
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }

    @Override
    public boolean updateUserInfo(int id,String password, String username, String telephone,String email) {
        if(userMapper.updateUserInfo(id,password,username,telephone,email)==1)
            return true;
        else return false;
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
    public User getUserById(int id) {

        return userMapper.getUserById(id);
    }

    /* 根据id修改用户的头像地址
     * @param id
     * @param headrul
     * @return
     */
    @Override
    public boolean updateAvatar(int id, String headrul) {
        if(userMapper.updateHeadUrlById(headrul,id)==1) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户id找到其头像地址
     * @param id
     * @return
     */
    @Override
    public String getHeadurl(int id) {
        return userMapper.getHeadurlById(id);
    }

    /**
     * 根据用户的id更新位置
     * @param id
     * @param location
     * @return
     */
    @Override
    public boolean updateLocation(int id, String location) {
        if(userMapper.updateLocationById(location,id)==1){
            return true;
        }
        return false;
    }

    /**
     * 通过用户名删除用户
     * @param username
     * @return
     */
    @Override
    public boolean deleteUserByUsername(String username){
        if(userMapper.deleteUserByUsername(username)==1)
            return true;
        else return false;
    }

    /**
     * 通过用户名修改密码
     * @param password
     * @param username
     * @return
     */
    @Override
    public boolean changePasswordByUsername(String password,String username){
        if(userMapper.changePasswordByUsername(password,username)==1)
            return true;
        else return false;
    }

    /**
     * 获得所有用户
     * @return
     */
    @Override
    public List<User> findAllUsers() {
        return userMapper.getAllUsers();
    }


    /**
     * 通过用户的id查找其位置
     * @param id
     * @return
     */
    @Override
    public String getLocation(int id) {
        return userMapper.getLocationById(id);
    }

    /**
     * 通过id获取用户
     * @param id
     * @return
     */
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    /**
     * 根据RID获取用户
     * @param rid
     * @return
     */
    @Override
    public List<User> findUserByRid(int rid) {
        return userMapper.getUserByRid(rid);
    }


    /**
     * 判断用户名是否存在
     * @param username
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean isUsernameExist(String username) {
        return userMapper.isUsernameExist(username);
    }

    /**
     * 判断手机号是否存在
     * @param telephone
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean isTelephoneExist(String telephone) {
        return userMapper.isTelephoneExist(telephone);

    }
}
