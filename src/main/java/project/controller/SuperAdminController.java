package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import project.exception.ServiceException;
import project.model.RespBean;
import project.model.User;
import project.service.RoleServiceImpl;
import project.service.UserServiceImpl;

import java.util.Map;

/**
 * 超管管理控制
 */
@Controller
public class SuperAdminController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RoleServiceImpl roleServiceImpl;

    @RequestMapping("/superadmin")
    public String toSaManagement(Model model){return "superadmin/SA_management";}

    /**
     * 删除管理员用户
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/superadmin/deleteUser")
    public RespBean deleteAdmin(@RequestBody Map<String,Object> info){
        String username = (String) info.get("username");
        //删除管理员账号，如果成功则返回结果
        if(userServiceImpl.deleteUserByUsername(username)){
            return RespBean.ok("账号删除成功");
        }
        return RespBean.error("账号删除失败");
    }

    /**
     * 重置管理员密码
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/superadmin/resetPassword")
    public RespBean resetPassWord(@RequestBody Map<String,Object> info){
        String username = (String) info.get("username");
        String newPassword = (String) info.get("newPassword");
        //对密码进行加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword.trim());
        //修改密码，如果成功则返回结果
        if(userServiceImpl.changePasswordByUsername(encodedPassword,username)){
            return RespBean.ok("修改密码成功");
        }
        return RespBean.error("修改密码失败");
    }

    /**
     * 添加管理员
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/superadmin/insertUser")
    public RespBean insertUser(@RequestBody Map<String,Object> info) throws ServiceException {
            User user = new User();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(((String)info.get("password")).trim());
            int id = userServiceImpl.getLastId()+1;
            user.setPassword(encodedPassword);
            user.setId(id);
            user.setTelephone((String)info.get("telephone"));
            user.setUsername((String)info.get("username"));
            //添加用户
            if(userServiceImpl.insertUser(user)==1){
                    return RespBean.ok("添加成功!");
            }
            return RespBean.error("添加失败");
    }

    /**
     * 用户名是否存在
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/superadmin/isUsernameExist")
    public RespBean isUsernameExist(@RequestBody Map<String,Object> info){
        //获取用户输入的用户名
        String username = (String) info.get("username");
        //如果用户名存在，则返回成功结果
        if(userServiceImpl.isUsernameExist(username)){
            return RespBean.ok("用户名存在");
        }
        return RespBean.passwordNotExist("用户名不存在");
    }

    /**
     * 手机号是否存在
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/superadmin/isTelephoneExist")
    public RespBean isTelephoneExist(@RequestBody Map<String,Object> info){
        //获取用户输入的手机号
        String telephone = (String) info.get("telephone");
        //如果手机号存在，则返回成功结果
        if(userServiceImpl.isTelephoneExist(telephone)){
            return RespBean.ok("手机号存在");
        }
        return RespBean.passwordNotExist("手机号不存在");
    }
}
