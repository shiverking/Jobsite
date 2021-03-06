package project.service;

import com.github.pagehelper.PageInfo;
import project.exception.ServiceException;
import project.model.Job;
import project.model.User;

import java.util.List;

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
    //删除用户
    boolean deleteUserByUsername(String username);
    //根据用户名修改密码
    boolean changePasswordByUsername(String password,String username);
    //获得所有用户
    List<User> findAllUsers();
    //通过id获得用户
    User findUserById(int id);
    //通过身份获得用户
    List<User> findUserByRid(int rid);
    //输入的用户名是否存在
    boolean isUsernameExist(String username);
    //输入的手机号是否存在
    boolean isTelephoneExist(String telephone);
    //通过ID获得用户名
    String getUserName(int id);
    //通过用户名获取id
    int getId(String username);
    //通过id获取user
    User getUserById(int id);
    //获取用户的url
    String getHeadurl(int id);
    //修改用户的头像地址
    boolean updateAvatar(int id,String headrul);
    //修改用户的位置
    boolean updateLocation(int id,String location);
    //获取用户的位置
    String getLocation(int id);
    //获取所有Employee的id
    List<Integer> getAllEmployeeId();

    boolean deleteUserById(int id);

    public PageInfo<User> findUserByPage(Integer pageNum, Integer limitNum);

    public PageInfo<User> findAdminByPage(Integer pageNum, Integer limitNum);

    PageInfo<User> searchUserByPage(Integer page, Integer limit, String username, String telephone, int rid);

    PageInfo<User> searchAdminByPage(Integer page, Integer limit, String username, String telephone);

    int getUserCount();

    boolean updateUserInfo(int id,String password, String username, String telephone,String email);
}
