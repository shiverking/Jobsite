package project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.dao.HireMapper;
import project.model.*;
import project.service.HireService;
import project.service.JobService;
import project.service.ProfileService;
import project.service.ResumeService;
import project.service.UserService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Autowired
    ResumeService resumeService;

    @Autowired
    ProfileService profileService;

    @Autowired
    HireService hireService;

    @Autowired
    UserService userService;
    /**
     * 发布工作
     */
    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/job/postAJob")
    public RespBean postJob(@Valid @RequestBody Job job) {
        //获取当前发布用户id
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        job.setEmployer_id(user.getId());
        //获取当前系统创建时间
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        job.setCreate_time(timestamp);
        //设置为未审核，开启招聘
        job.setCheck(false);
        job.setJob_status(true);
        //插入对象
        jobService.insertJob(job);
        //获取插入对象的id值
        Map<String, Object> map = new HashMap<>();
        map.put("JobId", job.getId());
        return RespBean.ok("发布工作成功", map);
    }


    @PreAuthorize("hasRole('ROLE_employer')" )
    @RequestMapping("/job/MyJobLists")
    public ModelAndView toMyJobListsPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        ModelAndView modelAndView = new ModelAndView("/job/my-job-listing");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    //获取给用户展示的工作列表
    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/job/showMyJobLists")
    public RespBean showMyJobLists() {
        ModelAndView modelAndView = new ModelAndView("/job/my-job-listing");
        List<Job> jobs;
        //获取当前用户id
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        jobs = jobService.findJobsByEmployerId(user.getId());
        if (jobs.size() > 0) {
            //添加工作列表
            Map<String, Object> map = new HashMap<>();
            map.put("jobLists", jobs);
            //排序
            jobs.sort((o1, o2) -> {
                int flag = o1.getCreate_time().compareTo(o2.getCreate_time());
                return flag*-1;
            });
            //获取每个工作作为投递对象的次数
            List<Integer> sends = new ArrayList<>();
            for (Job job : jobs) {
                int i = resumeService.countByJobId(job.getId());
                sends.add(i);
            }
            //添加
            map.put("sends", sends);
            return RespBean.ok("获取成功", map);
        } else {
            return RespBean.error("列表为空");
        }
        }
    /**
     * 分页查找所有工作
     * @param model
     * @return
     */
    @RequestMapping("/job")
    public String getJobList(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") Integer pageSize){
            //如果已经登录,则添加用户属性
            if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    User user = (User) principal;
                    model.addAttribute("user", user);
            }
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
            //如果已经登录,则添加用户属性
            if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    User user = (User) principal;
                    model.addAttribute("user", user);
            }
            Job job = jobService.findJobById(id);
            model.addAttribute("job",job);
            //添加雇主头像
            model.addAttribute("employerHeadurl",userService.getHeadurl(job.getEmployer_id()));
            return "job/single_job";
    }


    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/job/closeJob/{id}")
    public RespBean closeJobById(@PathVariable("id") int id) {
        int a = jobService.closeJobById(id);
        if (a == -1) {
            return RespBean.error("未找到该职位招聘信息");
        } else {
            return RespBean.ok("关闭该职位招聘成功");
        }
    }


    @PreAuthorize("hasRole('ROLE_employer')" )
    @RequestMapping("/job/postJob")
    public String toPostJobPage (Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "/job/post-a-job";
    }




    //根据指定id来开启工作招聘
    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @RequestMapping("/job/openJob/{id}")
    public RespBean openJobById(@PathVariable("id") int id) {
            Job job = jobService.findJobById(id);
            int jobid= job.getId();
            int people = hireService.countHiresByJob(jobid);
            if(people>= job.getRequired()){
                return RespBean.error("该工作目前招聘人数已满，开启招聘失败");
            }else {
                jobService.openJobById(id);
                return RespBean.ok("开启该职位招聘成功");
            }
    }



    //跳转到查看人员名单页面
    @PreAuthorize("hasRole('ROLE_employer')" )
    @RequestMapping("/showjobstaff")
    public String show(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "/page/show_staff";
    }

    //获取指定jobId的投递简历列表
    @PreAuthorize("hasRole('ROLE_employer')" )
    @RequestMapping("/job/viewSeeker/job_id={job_id}")
    public ModelAndView showSeekerList(@PathVariable("job_id") int job_id) {
        ModelAndView modelAndView = new ModelAndView("/page/show_staff");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;

        //比对一下当前操作人的id和当前操作对象所属的userid是否一样
        int currentId = user.getId();
        Job job = jobService.findJobById(job_id);
        if(job == null || currentId != job.getEmployer_id() ){
            return modelAndView;
        }

        if (resumeService.countByJobId(job_id) == 0) {
            modelAndView.addObject("hasError", true);
            return modelAndView;
        } else {
            List<Integer> profiles_id = resumeService.getProfileByJob(job_id);
            if (profiles_id.size() > 0) {
                modelAndView.addObject("hasProfile", true);
                List<Profile> profiles = new ArrayList<>();
                for (int id : profiles_id) {
                    profiles.add(profileService.getProfileById(id));
                }
                modelAndView.addObject("Profiles", profiles);
                modelAndView.addObject("job_id",job_id);
                modelAndView.addObject("user",user);
                return modelAndView;
            } else {
                modelAndView.addObject("hasProfile", false);
                return modelAndView;
            }
        }
    }

    @RequestMapping("/findStaff")
    public String findStaff(Model model){
        //如果已经登录
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) principal;
            model.addAttribute("user", user);
        }
        return "job/find-staff";
    }

    @PreAuthorize("hasRole('ROLE_employer')" )
    @ResponseBody
    @PostMapping("/findStaff/getAllProfile")
    public List<List<String>> getAllProfile(){
        List<List<String>> res = new ArrayList<List<String>>();
        List<Integer> employeeIds = userService.getAllEmployeeId();
        for(int id :employeeIds){
            if(profileService.isProfileExist(id)){
                List<String> tmp = new ArrayList<String>();
                //0
                tmp.add(userService.getUserName(id));
                //1
                tmp.add(userService.getHeadurl(id));
                //2
                tmp.add(profileService.getProfile(id).getExpertize_level());
                //3
                tmp.add(profileService.getProfile(id).getCompensation());
                //4
                tmp.add(profileService.getProfile(id).getExpertize_realm());
                System.out.println(tmp);
                res.add(tmp);
            }
        }
        return res;
    }

    @RequestMapping("/job/submittedJob")
    public String mySubmittedJob(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("user", user);
        return "job/my-submitted-jobs";
    }

    /**
     * 投递简历
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/job/submitJobConfirm")
    public RespBean submitJobConfirm(@RequestBody Map<String, Object> info){
        int job_id =Integer.valueOf((String) info.get("job_id"));
        int profile_id =Integer.valueOf((String) info.get("profile_id"));
        Date now = new Date();
        if(jobService.sendResume(job_id,profile_id,now)){
            return RespBean.ok("投递简历成功");
        }
        return RespBean.ok("投递简历失败");
    }
    /**
     * 投递简历
     * @param info
     * @return
     */
    @ResponseBody
    @PostMapping("/job/ifHasSubmitResume")
    public RespBean ifHasSubmitResume(@RequestBody Map<String, Object> info){
        int job_id =Integer.valueOf((String) info.get("job_id"));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        //查询用户是否有profile
        Profile profile = profileService.getProfile(user.getId());
        if(profile==null){
            return RespBean.error("没有简历");
        }
        int profile_id= profile.getId();
        if(jobService.ifHasSendResume(job_id,profile_id)){
            return RespBean.ok("已经投递过简历");
        }
        return RespBean.error("没有投递过简历");
    }
    /**
     * 获取所有的已投递的简历
     * @return
     */
    @ResponseBody
    @PostMapping("/job/getAllSubmittedJobs")
    public List<List<String>> getAllSubmitJob(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        List<List<String>> res = new ArrayList<>();
        List<String>  titles = jobService.getAllSummitedJobName(user.getId());
        List<Date> times = jobService.getAllSummitedJobTime(user.getId());
        List<String> positions = jobService.getAllSummitedJobPosition(user.getId());
        List<Boolean> status = jobService.getAllSummitedJobStatus(user.getId());
        int length = titles.size();
        if(length==0){
            return null;
        }
        for(int i=0;i<length;i++){
            List<String> tmp = new ArrayList<String>();
            //加入标题
            tmp.add(titles.get(i));
            //加入时间
            tmp.add(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(times.get(i)));
            //加入职位
            tmp.add(positions.get(i));
            //加入状态
            tmp.add(status.get(i).toString());

            res.add(tmp);
        }
        return res;
    }
}
