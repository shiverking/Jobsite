package project.service;

import com.alibaba.druid.util.DaemonThreadFactory;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import project.model.Job;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：JobService的接口类
 * @date ：2021/4/16 17:13
 */
public interface JobService {
    //返回所有的job
    List<Job> findAllJobs();
    //按照id找到Job
    Job findJobById(int id);
    //插入一条工作
    int insertJob(Job job);
    //对job进行审核
    boolean checkJob(int id);
    //返回所有通过审核的job
    List<Job> findAllJobsChecked();
    //删除job
    boolean deleteJobById(int id);
    //按时间升序查看job
    List<Job> findAllJobsAsc();
    //按时间降序查看job
    List<Job> findAllJobsDesc();

    public PageInfo<Job> findJobByPage(Integer pageNum,Integer limitNum);

    PageInfo<Job> searchJobByPage(Integer page, Integer limit, int employerId, String title,int check);

    int getJobCount();

    //通过employerId找到job
    List<Job> findJobsByEmployerId(int employer_id);
    //通过id关闭job
    int closeJobById(int id);
    //通过idopenjob
    int openJobById(int id);
    //job是否存在
    boolean isJobExist(int job_id);

    List<Integer> getAllIdByUser(int user_id);
    //投递简历
    boolean sendResume(int jobId, int profileId, Date creatTime);
    //是否已经投递过简历
    boolean ifHasSendResume(int jobId,int profileId);
    //获取所有已投递简历的jobb标题
    List<String> getAllSummitedJobName(int user_id);
    //获取所有已投递简历的job投递时间
    List<Date> getAllSummitedJobTime(int user_id);
    //获取所有已投递简历的job的岗位
    List<String> getAllSummitedJobPosition(int user_id);
    //获取所有已投递简历的job状态
    List<Boolean> getAllSummitedJobStatus(int user_id);
    //通过Employee的Id查找所有自己已经被雇佣的jobId
    List<Integer> getAllJobIdByEmployeeId(int userId);

    List<Job> getJobByType(String skill);
}
