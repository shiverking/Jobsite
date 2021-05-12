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
    List<Job> findAllJobs();
    //按照id找到Job
    Job findJobById(int id);

    int insertJob(Job job);

    List<Job> findJobsByEmployerId(int employer_id);

    int closeJobById(int id);

    int openJobById(int id);

    boolean isJobExist(int job_id);

}
