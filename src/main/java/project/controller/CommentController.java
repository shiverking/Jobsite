package project.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.User;

/**
 * @author ：闫崇傲
 * @description：评论的controller
 * @date ：2021/5/10 0:29
 */
@Controller
public class CommentController {
    @RequestMapping("/appraisals")
    public String getAppraisals(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);

        return "job/appraisals";
    }
}
