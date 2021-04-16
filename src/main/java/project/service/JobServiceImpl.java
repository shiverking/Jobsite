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


}
