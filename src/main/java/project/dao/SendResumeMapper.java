package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;
@Mapper
@Component
public interface SendResumeMapper {

    int CountResumeByJobID(@Param("job_id") int job_id);

    List<Integer> getProfileByJob(@Param("job_id") int job_id);
}
