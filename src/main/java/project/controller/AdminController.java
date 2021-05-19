package project.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.common.util.JsonResult;
import project.model.Job;
import project.model.Order;
import project.model.User;
import project.service.*;

import java.util.List;

/**
 * 管理员管理控制
 */
@Controller
public class AdminController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RoleServiceImpl roleServiceImpl;

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @Autowired
    JobServiceImpl jobServiceImpl;

    /**
     * 返回主版
     * @param model
     * @return
     */
    @RequestMapping("/admin/board")
    public String toBoard(Model model){
        //获取用户验证信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        String username = user.getUsername();
        int jobCount = jobServiceImpl.getJobCount();
        int userCount = userServiceImpl.getUserCount();
        int orderCount = orderServiceImpl.getOrderCount();
        model.addAttribute("username",username);
        model.addAttribute("jobCount",jobCount);
        model.addAttribute("userCount",userCount);
        model.addAttribute("orderCount",orderCount);
        return "admin/board";
    }

    /**
     * 返回职位管理
     * @return
     */
    @RequestMapping("/admin/job")
    public String toJobManagement(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "admin/job_management"; }

    /**
     * 返回订单管理
     * @return
     */
    @RequestMapping("/admin/order")
    public String toOrderManagement(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "admin/order_management"; }

    /**
     * 返回用户管理
     * @return
     */
    @RequestMapping("/admin/user")
    public String toUserManagement(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "admin/user_management"; }

    /**
     * 分页获取职位列表
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/admin/joblist")
    @ResponseBody
    public JsonResult getJobs(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Job> pageInfo = jobServiceImpl.findJobByPage(page,limit);
        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 根据条件查询职位
     * @param page
     * @param limit
     * @param employer_id
     * @param title
     * @param check
     * @return
     */
    @PostMapping("/admin/searchJob")
    @ResponseBody
    public JsonResult searchJobs(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam(value="employer_id") String employer_id,@RequestParam(defaultValue="",value="title") String title,@RequestParam(defaultValue="",value="check")String check){
        int employerId = Integer.parseInt(employer_id);
        int oneCheck = Integer.parseInt(check);
        PageInfo<Job> pageInfo = jobServiceImpl.searchJobByPage(page,limit,employerId,title,oneCheck);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 分页获取订单列表
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/admin/orderlist")
    @ResponseBody
    public JsonResult getOrders(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Order> pageInfo = orderServiceImpl.findOrderByPage(page,limit);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 根据条件查询订单
     * @param page
     * @param limit
     * @param job_id
     * @param state
     * @return
     */
    @PostMapping("/admin/searchOrder")
    @ResponseBody
    public JsonResult searchOrders(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam(value="job_id") String job_id,@RequestParam(defaultValue="",value="state") String state){
        int jobId = Integer.parseInt(job_id);
        PageInfo<Order> pageInfo = orderServiceImpl.searchOrderByPage(page,limit,jobId,state);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 分页获取用户列表
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/admin/userlist")
    @ResponseBody
    public JsonResult getUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<User> pageInfo = userServiceImpl.findUserByPage(page,limit);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 根据条件查询用户
     * @param page
     * @param limit
     * @param username
     * @param telephone
     * @param rid
     * @return
     */
    @PostMapping("/admin/searchUser")
    @ResponseBody
    public JsonResult searchUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam(defaultValue="",value="username") String username,@RequestParam(defaultValue="",value="telephone") String telephone,@RequestParam(defaultValue="",value="rid") String rid){
        int roleId = Integer.parseInt(rid);
        PageInfo<User> pageInfo = userServiceImpl.searchUserByPage(page,limit,username,telephone,roleId);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 删除职位
     * @param jobIds
     * @return
     */
    @DeleteMapping("/admin/jobs")
    @ResponseBody
    public JsonResult deleteJobs(@RequestBody List<String> jobIds) {
        for(String jobId:jobIds) {
            int id = Integer.parseInt(jobId);
            jobServiceImpl.deleteJobById(id);
            System.out.println("delete job "+id+" success");
        }
        return JsonResult.success();
    }

    /**
     * 审核职位
     * @param jobIds
     * @return
     */
    @PutMapping("/admin/jobs/check")
    @ResponseBody
    public JsonResult checkJobs(@RequestBody List<String> jobIds){
        for(String jobId:jobIds){
            int id = Integer.parseInt(jobId);
            jobServiceImpl.checkJob(id);
            System.out.println("check job "+id+" success");
        }
        return JsonResult.success();
    }


    /**
     * 删除订单
     * @param orderIds
     * @return
     */
    @DeleteMapping("/admin/orders")
    @ResponseBody
    public JsonResult deleteOrders(@RequestBody List<String> orderIds) {
        for(String orderId:orderIds) {
            int id = Integer.parseInt(orderId);
            orderServiceImpl.deleteOrderById(id);
            System.out.println("delete order "+id+" success");
        }
        return JsonResult.success();
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @DeleteMapping("/admin/users")
    @ResponseBody
    public JsonResult deleteUsers(@RequestBody List<String> userIds) {
        for(String userId:userIds) {
            int id = Integer.parseInt(userId);
            userServiceImpl.deleteUserById(id);
            System.out.println("delete user "+id+" success");
        }
        return JsonResult.success();
    }
}
