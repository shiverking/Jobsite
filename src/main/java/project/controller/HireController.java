package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.model.Job;
import project.model.RespBean;
import project.service.HireService;
import project.service.JobService;
import project.service.OrderService;
import project.service.UserService;

import java.util.Date;
import java.util.Map;

@Controller
public class HireController {


    @Autowired
    JobService jobService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    HireService hireService;

    /**
     * 雇佣工作，即将该工作添加到order
     */
    @ResponseBody
    @RequestMapping("/job/hireUser")
    public RespBean addOrder(@RequestBody Map<String, Object> map) {
        int job_id = (int) map.get("job_id");
        int user_id = (int) map.get("user_id");
        if (jobService.isJobExist(job_id)) {
            if (userService.getUserById(user_id) != null) {
                Job job = jobService.findJobById(job_id);
                if (job.isJob_statue()) {
                    int employer_id = job.getEmployer_id();
                    int require_people = job.getRequired();
                    if (require_people <= hireService.countHiresByJob(job_id)) {
                        return RespBean.error("该工作目前所需人数已满");
                    } else if (hireService.isHireExit(job_id, user_id)) {
                        return RespBean.error("您已在同一工作招聘过该人才");
                    } else {
                        //创建一个新订单
                        Date date = new Date();
                        int result = hireService.insertNewHire(job_id,employer_id,user_id,date);
                        if (result > 0) {
                            //如果招聘人数满了，将关闭该招聘通道
                            if (hireService.countHiresByJob(job_id) == require_people) {
                                jobService.closeJobById(job_id);
                                hireService.finishHire(job_id,"0");
                                return RespBean.ok("招聘该用户成功，目前该工作所需人数已满，将关闭该工作招聘通道,请尽快完成预付");
                            }
                            return RespBean.ok("招聘该用户成功");
                        } else {
                            return RespBean.error("招聘该用户失败，请重新尝试");
                        }
                    }
                } else {
                    return RespBean.error("该工作招聘目前处于关闭状态");
                }
            } else {
                return RespBean.error("未找到该用户");
            }
        } else {
            return RespBean.error("没有找到该工作");
        }
    }
}
