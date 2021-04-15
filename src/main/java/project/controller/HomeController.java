package project.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：闫崇傲
 * @description：主页控制层
 * @date ：2021/4/15 23:50
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home(Model model){
        return "home_page";
    }
}

