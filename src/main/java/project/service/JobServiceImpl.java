package project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.JobMapper;
import project.model.Job;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：JobService接口的实现类
 * @date ：2021/4/16 21:09
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobMapper jobMapper;

    @Override
    public List<Job> findAllJobs() {
        return jobMapper.getAllJobs();
    }

    @Override
    public Job findJobById(int id) {
        return jobMapper.findJobById(id);
    }


    @Override
    public boolean checkJob(int id) {
        if (jobMapper.updateJobCheck(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Job> findAllJobsChecked() {
        return jobMapper.getJobIsCheck();
    }

    @Override
    public boolean deleteJobById(int id) {
        if (jobMapper.deleteJobById(id) == 1) {
            return true;
        }
        else return false;
    }

    @Override
    public List<Job> findAllJobsAsc() {
        return jobMapper.getAllJobAsc();
    }

    @Override
    public List<Job> findAllJobsDesc() {
        return jobMapper.getAllJobDesc();
    }

    @Override
    public int insertJob(Job job) {
        return jobMapper.insertJob(job);
    }

    @Override
    public List<Job> findJobsByEmployerId(int employer_id) {
        return jobMapper.findJobsByEmployerId(employer_id);
    }

    @Override
    public int closeJobById(int id) {
        if (findJobById(id) != null) {
            return jobMapper.closeJobById(id);
        } else {
            return -1;
        }
    }

    @Override
    public int openJobById(int id) {
        if (findJobById(id) != null) {
            return jobMapper.openJobById(id);
        } else {
            return -1;
        }
    }


    @Override
    public PageInfo<Job> findJobByPage(Integer pageNum,Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<Job> info = new PageInfo<Job>(jobMapper.getJobList());
        return info;
    }

    @Override
    public boolean isJobExist(int job_id) {
        //如果没有找到简历，则返回false
        return jobMapper.isJobExist(job_id);
    }

    @Override
    public List<Integer> getAllIdByUser(int user_id) {
        return jobMapper.getAllIdByUser(user_id);
    }

    public PageInfo<Job> searchJobByPage(Integer page, Integer limit, int employerId, String title,int check) {
        PageHelper.startPage(page,limit);
        PageInfo<Job> info = new PageInfo<Job>(jobMapper.searchJob(employerId,title,check));
        return info;
    }

    @Override
    public int getJobCount() {
        return jobMapper.getJobCount();
    }

    /**
     * 投递简历
     * @param jobId
     * @param profileId
     * @param creatTime
     * @return
     */
    @Override
    public boolean sendResume(int jobId, int profileId, Date creatTime) {
        if(jobMapper.insertSendResume(jobId,profileId,creatTime)==1){
            return true;
        }
        return false;
    }

    /**
     * 是否已经投递过简历
     * @param jobId
     * @param profileId
     * @return
     */
    @Override
    public boolean ifHasSendResume(int jobId, int profileId) {
        return jobMapper.ifResumeExist(jobId,profileId);
    }

    /**
     * 获取所有投递过的简历的标题
     * @param user_id
     * @return
     */
    @Override
    public List<String> getAllSummitedJobName(int user_id) {
        return jobMapper.getAllSummitedJobName(user_id);
    }

    /**
     * 获取所有投递过简历的时间
     * @param user_id
     * @return
     */
    @Override
    public List<Date> getAllSummitedJobTime(int user_id) {
        return jobMapper.getAllSummitedJobTime(user_id);
    }

    /**
     * 获取所有投递过简历的职位
     * @param user_id
     * @return
     */
    @Override
    public List<String> getAllSummitedJobPosition(int user_id) {
        return jobMapper.getAllSummitedJobPosition(user_id);
    }

    /**
     * 获取所有投递过简历的状态
     * @param user_id
     * @return
     */
    @Override
    public List<Boolean> getAllSummitedJobStatus(int user_id) {
        return jobMapper.getAllSummitedJobStatus(user_id);
    }

    /**
     * 通过Employee的Id查找所有自己已经被雇佣的jobId
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getAllJobIdByEmployeeId(int userId) {
        return jobMapper.getAllJobIdByEmployeeId(userId);
    }
}
