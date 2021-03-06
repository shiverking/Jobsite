package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.Job;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：Job映射接口
 * @date ：2021/4/16 17:05
 */
@Mapper
@Component
public interface JobMapper {
    //获得所有Job
    List<Job> getAllJobs();
    //根据id找到Job
    Job findJobById(@Param("id") int id);
    //根据job信息模糊搜索
    Job findJobByName(@Param("title") String title);
    // 按时间降序排列
    List<Job> getAllJobDesc();
    // 按时间升序排列
    List<Job> getAllJobAsc();
    //根据id删除Job
    int deleteJobById(@Param("id") int id);
    //获得所有通过审核的Job
    List<Job> getJobIsCheck();
    //将未审核的Job更新为审核通过
    int updateJobCheck(@Param("id") int id);
    //获得所有未审核的Job
    List<Job> getJobNotCheck();
    //插入工作
    int insertJob(Job job);
    //根据雇主id查询工作列表
    List<Job> findJobsByEmployerId(@Param("employer_id") int employer_id);
    //关闭指定id工作招聘通道
    int closeJobById(@Param("id") int id);
    //开启指定id工作招聘通道
    int openJobById(@Param("id") int id);


    List<Job> getJobList();

    List<Job> searchJob(@Param("employerId") int employerId,@Param("title") String title,@Param("check") int check);

    int getJobCount();

    //检测指定id工作是否存在
    boolean isJobExist(@Param("job_id") int job_id);
    //Employer查询所有与自己有关的jobid
    List<Integer> getAllIdByUser(@Param("user_id") int user_id);
    //Employee查询所有与自己有关的jobid
    List<Integer> getAllJobIdByEmployeeId(@Param("user_id") int user_id);
    //向投递简历表中增添一条数据
    int insertSendResume(@Param("job_id")int job_id,@Param("profile_id")int profile_id,@Param("create_time") Date create_time);
    //查看是否已经投递过简历
    boolean ifResumeExist(@Param("job_id")int job_id,@Param("profile_id")int profile_id);
    //获取所有已投递简历的jobb标题
    List<String> getAllSummitedJobName(@Param("user_id")int user_id);
    //获取所有已投递简历的job投递时间
    List<Date> getAllSummitedJobTime(@Param("user_id")int user_id);
    //获取所有已投递简历的job的岗位
    List<String> getAllSummitedJobPosition(@Param("user_id")int user_id);
    //获取所有已投递简历的job状态
    List<Boolean> getAllSummitedJobStatus(@Param("user_id")int user_id);

    List<Job> getJobBySkill(@Param("skill") String skill);
}
