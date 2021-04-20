package project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.Job;
import project.service.JobService;

import java.util.List;

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
        public String getJobList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "1") Integer pageSize){
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






}