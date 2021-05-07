package project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.protocol.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.model.Job;
import project.model.RespBean;
import project.model.User;
import project.service.JobService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author ：闫崇傲
 * @description：工作的Controller
 * @date ：2021/4/16 11:44
 */
@Controller
public class JobController {
        @Autowired
        JobService jobService;
        /**
         * 分页查找所有工作
         * @param model
         * @return
         */
        @RequestMapping("/job")
        public String getJobList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
                //引入分页查询
                PageHelper.startPage(pageNum,pageSize);
                //分页查询
                List<Job> jobs = jobService.findAllJobs();
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

                return "job/browse_jobs";
        }
        @RequestMapping("/job/{id}")
        public String getJobById(Model model,@PathVariable int id){
                Job job = jobService.findJobById(id);
                model.addAttribute("job",job);
                return "job/single_job";

        }


        @RequestMapping("/job/postJob")
        public String toPostJobPage(){
                return "/job/post-a-job";
        }


        /**
         * 发布工作
         * @param
         * @return
         */
        @ResponseBody
        @RequestMapping("/job/postAJob")
        public RespBean postJob(@Valid @RequestBody Job job) {
                //获取当前发布用户id
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = (User)principal;
                job.setEmployer_id(user.getId());
                //获取当前系统创建时间
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                job.setCreate_time(timestamp);
                //设置为未审核
                job.setCheck(false);
                //插入对象
                jobService.insertJob(job);
                //获取插入对象的id值
                Map<String,Object> map = new HashMap<>();
                map.put("JobId",job.getId());
            return RespBean.ok("发布工作成功",map);
        }

        @RequestMapping("/job/MyJobLists")
        public ModelAndView toMyJobListsPage(){
                ModelAndView modelAndView = new ModelAndView("/job/my-job-listing");
                List<Job> jobs;
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = (User)principal;
                jobs = jobService.findJobsByEmployerId(user.getId());
                if(jobs.size()>0){
                        modelAndView.addObject("jobs",jobs);
                        modelAndView.addObject("hasJobs",true);
                }else {
                        modelAndView.addObject("hasJobs",false);
                }
                return modelAndView;
        }





}
