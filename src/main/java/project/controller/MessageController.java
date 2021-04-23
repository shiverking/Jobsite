package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：闫崇傲
 * @description：Message的Controller
 * @date ：2021/4/19 14:11
 */
@Controller
public class MessageController {
    @RequestMapping("/message")
    public String toMessagePage(Model model){
        return "message/index";
    }
}
