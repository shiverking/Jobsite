package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.Job;

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
    Job findJobByName(@Param("name") String name);
}
