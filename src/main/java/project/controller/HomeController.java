package project.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.User;

/**
 * @author ：闫崇傲
 * @description：主页控制层
 * @date ：2021/4/15 23:50
 */
@EnableAutoConfiguration
@Controller

public class HomeController {
    /**
     * 返回主页
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String toHome(Model model){
        //如果已经登录
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) principal;
            model.addAttribute("user", user);
        }
        return "home_page";
    }

    @GetMapping("/AboutUs")
    public String aboutUs(Model model){
        //如果已经登录
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) principal;
            model.addAttribute("user", user);
        }
        return "page/AboutUs";
    }

    @GetMapping("/JoinUs")
    public String joinUs(Model model){
        //如果已经登录
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) principal;
            model.addAttribute("user", user);
        }
        return "page/JoinUs";
    }
}

