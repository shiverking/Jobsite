package project.service;

import com.github.pagehelper.PageInfo;
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


    public int insertJob(Job job);

    public List<Job> findJobsByEmployerId(int employer_id);

    public int closeJobById(int id);

    public int openJobById(int id);

    public PageInfo<Job> findJobByPage(Integer pageNum,Integer limitNum);

    PageInfo<Job> searchJobByPage(Integer page, Integer limit, int employerId, String title,int check);

    int getJobCount();
}
