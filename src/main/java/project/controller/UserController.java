package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.exception.ServiceException;
import project.model.RespBean;
import project.model.User;
import project.service.RedisServiceimpl;
import project.service.UserServiceimpl;
import project.util.EmailUtil;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录注册控制
 */
@Controller
public class UserController {

    @Autowired
    UserServiceimpl userServiceimpl;
    @Autowired
    RedisServiceimpl redisServiceimpl;
    @Autowired
    EmailUtil emailUtil;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/user")
    public String showUser(){
        return userServiceimpl.findUserByUsername("admin").toString();
    }

    /**
     * employee注册controller
     * @return
     */
    @RequestMapping("/register")
        public ModelAndView registerEmployee(){
        return new ModelAndView("page/registration");
    }
    /**
     * employer注册controller
     * @return
     */
    @RequestMapping("/register/employer")
    public ModelAndView registerEployer(){
        return new ModelAndView("page/registration_employer");
    }

    /**
     * 登录controller
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("page/login");
    }

    /**
     * 验证码验证
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/register/verify")
    public RespBean verifyCode(@RequestBody Map<String,Object> info){
        String telephone = (String) info.get("telephone");
        String code = (String) info.get("mobileCode");
        String tel = telephone.concat("_SMScode");
        if(code .equals(redisServiceimpl.get(tel))){
            return RespBean.ok("验证码验证成功");
        }else{
            return RespBean.error("验证码验证失败");
        }
    }

    /**
     * 获取验证码
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVerifyCode")
    public RespBean getVerifyCode(@RequestBody Map<String,Object> info){
        String telephone = (String) info.get("telephone");
        StringBuffer sb = new StringBuffer();
        sb.append(telephone);
        sb.append("_SMScode");
        if(telephone.equals(null)){
            return RespBean.error("发送验证码失败,请重新尝试");
        }else if(redisServiceimpl.hasKey(sb.toString())){
            return RespBean.error("验证码已发送");
        }else {
            String code =userServiceimpl.generateAuthCode(telephone);
            System.out.println(code);
            redisServiceimpl.set(sb.toString(),code,60);
            return RespBean.ok("验证码发送成功");
        }
    }

    /**
     * 修改个人注册信息
     * @return
     */
    @RequestMapping("/editProfile")
    public ModelAndView editProfile(Model model){
        return new ModelAndView("user/edit_Profile");
    }

    /**
     * 注册方法
     */
    @RequestMapping("/add")
    public RespBean test(@RequestBody  @Valid User user, BindingResult bindingResult) throws ServiceException {
        Map<String,Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();
            sb.append(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return RespBean.error(sb.toString());
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
            user.setPassword(encodedPassword);
            user.setHeadurl("sss");
            user.setLocation("xian");
            userServiceimpl.insertUser(user);
            return RespBean.ok("添加成功");
        }
    }

    /**
     * 登出controller
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        return "logout";
    }

    /**
     * 忘记密码controller
     */
    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "page/lost_password";
    }

    @ResponseBody
    @RequestMapping("/forgetPassword/email")
    public RespBean verifyEmail(@RequestBody Map<String,Object> info){
        String email = (String)info.get("email");
        System.out.println(email);
        //如果邮箱存在，则发送邮件并返回正确信息
        if(userServiceimpl.isEmailExist(email)){
            //发送邮件
            //emailUtil.sendMail(email,"JobSite提醒，您的账户密码为"+bCryptPasswordEncoder.d);
            return RespBean.ok("邮箱验证成功");
        }
        //如果邮箱不存在，则返回错误信息
        else{
            return RespBean.error("邮箱不存在");
        }
    }

}
