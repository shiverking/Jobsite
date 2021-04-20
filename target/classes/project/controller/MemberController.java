package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.model.User;
import project.service.UserServiceimpl;

@RestController
@RequestMapping("/sso")
public class MemberController {
    @Autowired
    UserServiceimpl userServiceimpl;

    @RequestMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("Useregister");
    }


    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("page/login");
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