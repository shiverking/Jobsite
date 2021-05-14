package project.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.common.util.JsonResult;
import project.exception.ServiceException;
import project.model.RespBean;
import project.model.User;
import project.service.RoleServiceImpl;
import project.service.UserServiceImpl;

import java.util.List;
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

    @RequestMapping("/superadmin/management")
    public String toSaManagement(Model model){return "superadmin/admin_management";}

    @RequestMapping("/superadmin/changePassword")
    public String toChangePwd(Model model){return "superadmin/reset_admin";}

    @RequestMapping("/superadmin/createAdmin")
    public String toCreateAdmin(Model model){return "superadmin/create_admin";}

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @DeleteMapping("/superadmin/users")
    @ResponseBody
    public JsonResult deleteUsers(@RequestBody List<String> userIds) {
        for(String userId:userIds) {
            int id = Integer.parseInt(userId);
            userServiceImpl.deleteUserById(id);
            System.out.println("delete user "+id+" success");
        }
        return JsonResult.success();
    }

    /**
     * 编辑管理员信息
     * @param info
     * @return
     */
    @ResponseBody
    @PutMapping("/superadmin/user")
    public JsonResult updateUser(@RequestBody Map<String,Object> info){
        int id = Integer.parseInt((String) info.get("id"));
        String username = (String) info.get("username");
        String newPassword = (String) info.get("password");
        String telephone = (String) info.get("telephone");
        String email = (String) info.get("email");
        //对密码进行加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword.trim());
        //更新信息，如果成功则返回结果
        if(userServiceImpl.updateUserInfo(id,encodedPassword,username,telephone,email)){
            return JsonResult.success();
        }
        return JsonResult.error();
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
        if(userServiceImpl.insertUser(user)==1&&roleServiceImpl.addUserAndRole(id,3)){
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

    /**
     * 根据条件查询用户
     * @param page
     * @param limit
     * @param username
     * @param telephone
     * @return
     */
    @PostMapping("/superadmin/searchUser")
    @ResponseBody
    public JsonResult searchUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam(defaultValue="",value="username") String username,@RequestParam(defaultValue="",value="telephone") String telephone){
        PageInfo<User> pageInfo = userServiceImpl.searchUserByPage(page,limit,username,telephone,3);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 分页获取管理员列表
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/superadmin/adminlist")
    @ResponseBody
    public JsonResult getUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<User> pageInfo = userServiceImpl.findAdminByPage(page,limit);
        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }


}
