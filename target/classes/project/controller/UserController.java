package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.model.User;
import project.service.UserService;
import project.service.UserServiceimpl;

@RestController
public class UserController {
    @Autowired
    UserServiceimpl userServiceimpl;

    @RequestMapping("/user")
    public String showUser(){
        return userServiceimpl.findUserByUsername("admin").toString();
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("page/login");
    }

    /**
     * 跳转注册陆界面/普通应聘者注册
     * @return 注册界面
     */
    @RequestMapping("/register")
    public ModelAndView registerEmployee(){
        return new ModelAndView("page/registration");
    }
    /**
     * 跳转注册陆界面/雇主注册
     * @return 注册界面
     */
    @RequestMapping("/register/employer")
    public ModelAndView registerEmployer(){
        return new ModelAndView("page/registration_employer");
    }

    @RequestMapping("/add")
    public ModelAndView addUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);
        user.setHeadurl("sss");
        user.setLocation("xian");
        user.setTelephone("123456222");
        System.out.println(user);
        userServiceimpl.insertUser(user);
        return new ModelAndView("page/login");
    }
}
