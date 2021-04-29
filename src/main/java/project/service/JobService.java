package project.service;

import project.model.Job;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：JobService的接口类
 * @date ：2021/4/16 17:13
 */
public interface JobService {
    //返回所有的job
    public List<Job> findAllJobs();
    //按照id找到Job
    public Job findJobById(int id);

    public int insertJob(Job job);

    public List<Job> findJobsByEmployerId(int employer_id);

}
