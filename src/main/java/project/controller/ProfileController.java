package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.model.Profile;
import project.model.RespBean;
import project.model.User;
import project.service.ProfileServiceImpl;
import project.service.UserService;

import java.util.Map;

/**
 * @author ：闫崇傲
 * @description：简历类的controller类
 * @date ：2021/4/27 14:00
 */
@Controller
public class ProfileController {
    @Autowired
    ProfileServiceImpl profileServiceImpl;

    @Autowired
    UserService userService;

    /**
     * 判断该用户的Profile是否存在
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/isProfileExist")
    public RespBean isProfileExist(@RequestBody Map<String, Object> info){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        //如果数据库中没有保存个人的简历
        if(!profileServiceImpl.isProfileExist(user.getId())){
            return RespBean.error("还没有简历");
        }
        return RespBean.ok("简历已存在");
    }

    /**
     * 保存个人简历
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/saveProfile")
    public RespBean saveProfile(@RequestBody Map<String, Object> info){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int user_id = user.getId();
        String user_name = user.getUsername();
        String complationTime = (String) info.get("completionTime");
        String expertize_realm = (String) info.get("expertize_realm");
        String compensation = (String)info.get("compensation");
        String expertize_level = (String) info.get("expertize_level");
        String workexperience = (String) info.get("workexperience");
        String biography = (String) info.get("biography");
        if(profileServiceImpl.saveProfile(user_id,complationTime,expertize_realm,compensation,workexperience,expertize_level,biography,user_name)){
            return RespBean.ok("保存成功！");
        }
        return RespBean.error("保存简历失败");
    }
    /**
     * 保存个人简历
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/updateProfile")
    public RespBean updateProfile(@RequestBody Map<String, Object> info){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int user_id = user.getId();
        String complationTime = (String) info.get("completionTime");
        String expertize_realm = (String) info.get("expertize_realm");
        String compensation = (String)info.get("compensation");
        String expertize_level = (String) info.get("expertize_level");
        String workexperience = (String) info.get("workexperience");
        String biography = (String) info.get("biography");
        if(profileServiceImpl.updateProfile(complationTime,expertize_realm,compensation,workexperience,expertize_level,biography,user_id)){
            return RespBean.ok("保存成功!");
        }
        return RespBean.error("保存简历失败");
    }
    /**
     * 展示个人简历
     * @param model
     * @return
     */
    @RequestMapping("/profile")
    public String updateProfile(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        int user_id = user.getId();

        Profile profile = profileServiceImpl.getProfile(user_id);
        model.addAttribute("profile",profile);
        model.addAttribute("user",user);

        return "user/staffProfile";
    }


    @RequestMapping("/user/viewStaffProfile")
    public String toProfilePage(){
        return "user/staffProfile";
    }


    /**
     * 根据指定用户id来查看简历
     */
    @RequestMapping("/viewProfile/user_id={user_id}")
    public ModelAndView viewProfileById(@PathVariable("user_id") int user_id){
        ModelAndView modelAndView = new ModelAndView("/user/staffProfile");
        User user = userService.getUserById(user_id);
        Profile profile = profileServiceImpl.getProfile(user_id);
        modelAndView.addObject("profile",profile);
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
