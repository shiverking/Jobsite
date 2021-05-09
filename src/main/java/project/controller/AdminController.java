package project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.exception.ServiceException;
import project.model.Job;
import project.model.Order;
import project.model.RespBean;
import project.model.User;
import project.service.*;

import java.util.List;
import java.util.Map;

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
     * 分页查找所有工作
     * @param model
     * @return
     */
    @RequestMapping("/admin")
    public String getJobList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
        //引入分页查询
        PageHelper.startPage(pageNum,pageSize);
        //分页查询
        List<Job> jobs = jobServiceImpl.findAllJobsAsc();
        //使用pageinfo封装查询结果
        PageInfo<Job> pageInfo = new PageInfo<Job>(jobs,5);

        model.addAttribute("pageInfo",pageInfo);

        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());

        return "admin/job_management";
    }

    /**
     * 分页查找所有工作（按时间降序）
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/jobOrderByTime")
    public List<Job> getJobListAsc(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){

        //引入分页查询
        PageHelper.startPage(pageNum,pageSize);
        //分页查询
        List<Job> jobs = jobServiceImpl.findAllJobsAsc();
        //使用pageinfo封装查询结果
        PageInfo<Job> pageInfo = new PageInfo<Job>(jobs,5);

        model.addAttribute("pageInfo",pageInfo);

        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());

        return jobs;
    }

    /**
     * 跳转到singlejob页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/admin/{id}")
    public String getJobById(Model model,@PathVariable int id){
        Job job = jobServiceImpl.findJobById(id);
        model.addAttribute("job",job);
        return "job/single_job";
    }

    /**
     * 审核职位
     * @param info
     * @return
     * @throws ServiceException
     */
    @ResponseBody
    @RequestMapping("/admin/checkJob")
    public RespBean checkJob(@RequestBody Map<String,Object> info){
        String idStr = (String) info.get("id");
        int id = (int)Integer.parseInt(idStr);
        if(jobServiceImpl.checkJob(id)){
            return RespBean.ok("审核成功");
        }
        return RespBean.error("审核失败");
    }

    /**
     * 删除职位
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/deleteJob")
    public RespBean deleteJob(@RequestBody Map<String,Object>info){
        String idStr = (String) info.get("id");
        int id = Integer.parseInt(idStr);
        if(jobServiceImpl.deleteJob(id)){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }



    /**
     * 分页查找所有订单
     * @param model
     * @return
     */
    @RequestMapping("/admin/order")
    public String getOrderList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
        //引入分页查询
        PageHelper.startPage(pageNum,pageSize);
        //分页查询
        List<Order> orders = orderServiceImpl.findAllOrder();
        //使用pageinfo封装查询结果
        PageInfo<Order> pageInfo = new PageInfo<Order>(orders,5);

        model.addAttribute("pageInfo",pageInfo);
        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());

        return "admin/order_management";
    }
    @RequestMapping("/admin/order/{id}")
    public String getOrderById(Model model,@PathVariable int id){
        Order order = orderServiceImpl.findOrderById(id);
        model.addAttribute("order",order);
        return "order/single_order";
    }

    /**
     * 分页查找所有人员
     * @param model
     * @return
     */
    @RequestMapping("/admin/user")
    public String getUserList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
        //引入分页查询
        PageHelper.startPage(pageNum,pageSize);
        //分页查询
        List<User> users = userServiceImpl.findAllUsers();
        //使用pageinfo封装查询结果
        PageInfo<User> pageInfo = new PageInfo<User>(users,5);

        model.addAttribute("pageInfo",pageInfo);

        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());

        return "admin/user_management";
    }

    /**
     * 分页查找所有招聘者
     * @param model
     * @return
     */
    @RequestMapping("/admin/employer")
    public String getEmployerList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
        //引入分页查询
        PageHelper.startPage(pageNum,pageSize);
        //分页查询
        List<User> users = userServiceImpl.findUserByRid(1);
        //使用pageinfo封装查询结果
        PageInfo<User> pageInfo = new PageInfo<User>(users,5);

        model.addAttribute("pageInfo",pageInfo);

        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());

        return "admin/user_management";
    }

    /**
     * 分页查找所有应聘者
     * @param model
     * @return
     */
    @RequestMapping("/admin/employee")
    public String getEmployeeList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
        //引入分页查询
        PageHelper.startPage(pageNum,pageSize);
        //分页查询
        List<User> users = userServiceImpl.findUserByRid(2);
        //使用pageinfo封装查询结果
        PageInfo<User> pageInfo = new PageInfo<User>(users,5);

        model.addAttribute("pageInfo",pageInfo);

        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());

        return "admin/user_management";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserById(Model model,@PathVariable int id){
        User user = userServiceImpl.findUserById(id);
        model.addAttribute("user",user);
        return "user/single_user";
    }

    @RequestMapping("/admin/singleUser")
    public String toStaff(Model model){
        return "user/staffProfile";
    }
}
