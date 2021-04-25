package project.controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import project.util.AESUtil;
import project.util.EmailUtil;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    // aes算法中的密钥（临时）
    private static String aesKey = "123456";

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
    @ResponseBody
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
    public RespBean verifyEmail(@RequestBody Map<String,Object> info) throws Exception {
        String email = (String)info.get("email");
        //System.out.println(email);
        //如果邮箱存在，则发送邮件并返回正确信息
        if(userServiceimpl.isEmailExist(email)){
            // 获取系统当前时间,并转换成yyyy/MM/dd HH:mm:ss格式
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = format.format(now);
            // 重置密码链接
            String urlStr = "http://localhost:8080/resetPassword?key=";
            String plainText = currentTime + "@" + email; // 当前时间加上用户邮箱 使用@进行连接二者，在大多数情况下不会产生歧义
            //生成加密链接
            byte[] encode = AESUtil.encrypt(plainText,aesKey);    //加密后的链接
            //转成16进制字符串,不转会崩溃
            String link = urlStr +  AESUtil.parseByte2HexStr(encode);
            //发送邮件
            String content ="<a href='#'>"+email+"</a>,您好：<br>\r\n"+
                    "请在两小时内访问如下链接，进行密码重置<br>"+"<a href="+link+">"+link+"</a><br>"+
                    "如果您还有任何疑问，请与我们联系。邮箱地址：<a href='#'>"+"1915939679@qq.com"+"</a><br>\r\n"+"请不要回复本邮件。<br>";
            emailUtil.sendMail(email,content);
            return RespBean.ok("邮箱验证成功");
        }
        //如果邮箱不存在，则返回错误信息
        else{
            return RespBean.error("邮箱不存在");
        }
    }

    /**
     * 加载重置密码的页面
     * @return
     */
    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "page/resetPassword";
    }

    /**
     * 判断请求的链接是否有效
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetPassword/isLegal")
    public RespBean resetPassword(@RequestBody Map<String,Object> info) throws UnsupportedEncodingException, ParseException {
        //获取后缀
        String key= (String)info.get("key");
        //System.out.println(key);
        //解密
        byte[] decode = AESUtil.parseHexStr2Byte(key);
        byte[] decryptResult = AESUtil.decrypt(decode, aesKey);
        String decryptRes = new String(decryptResult, "UTF-8");
        //分离时间戳和邮箱
        String[] res = decryptRes.split("@",2);
        //获取日期并转换成对应格式
        String time = res[0];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendTime =format.parse(time);
        //如果两个时间相差小于两个小时,则返回超时链接无效
        if(new Date().getTime()-sendTime.getTime()>2*60*60*1000)
        {
            return RespBean.timeout("超时");
        }
        //System.out.println(sendTime);
        //获取邮箱
        String email = res[1];
        if (!userServiceimpl.isEmailExist(email)) {
            return RespBean.invalid("无效链接");
        }
        return RespBean.ok("链接有效");
    }

    /**
     * 修改密码确认链接
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetPassword/confirm")
    public RespBean resetPasswordConfirm(@RequestBody Map<String,Object> info) throws UnsupportedEncodingException {
        //System.out.println(key);
        String newpPassword = (String) info.get("pwd");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //获取email
        String key= (String)info.get("key");
        byte[] decode = AESUtil.parseHexStr2Byte(key);
        byte[] decryptResult = AESUtil.decrypt(decode, aesKey);
        String decryptRes = new String(decryptResult, "UTF-8");
        String[] res = decryptRes.split("@",2);
        String email = res[1];
        //重设密码
        if(userServiceimpl.changePasswordByWord(encoder.encode(newpPassword),email)){
            return RespBean.ok("修改密码成功");
        }
        //返回错误信息
        return RespBean.error("修改密码失败");
    }

    /**
     * 设置个人信息
     * @param model
     * @return
     */
    @RequestMapping("/setting")
    public String editInformation(Model model){
        return "user/setting";
    }

    /**
     * 个人密码是否正确
     * @param authentication
     * @return
     */
    @ResponseBody
    @PostMapping("/setting/isPasswordExist")
    public RespBean isPasswordExist(Authentication authentication){
        return RespBean.ok("密码存在");
    }
}
