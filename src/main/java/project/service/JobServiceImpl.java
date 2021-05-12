package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.JobMapper;
import project.model.Job;

import java.util.List;

/**
 * @author ：闫崇傲
 * @description：JobService接口的实现类
 * @date ：2021/4/16 21:09
 */
@Service
public class JobServiceImpl implements JobService{
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

    public int insertJob(Job job){
        return jobMapper.insertJob(job);
    }

    @Override
    public List<Job> findJobsByEmployerId(int employer_id) {
        return jobMapper.findJobsByEmployerId(employer_id);
    }

    @Override
    public int closeJobById(int id) {
        if(findJobById(id) != null){
            return jobMapper.closeJobById(id);
        }else{
            return -1;
        }
    }

    @Override
    public int openJobById(int id) {
        if(findJobById(id) != null){
            return jobMapper.openJobById(id);
        }else{
            return -1;
        }
    }

    public boolean isJobExist(int job_id) {
        //如果没有找到简历，则返回false
        return jobMapper.isJobExist(job_id);
    }

}
