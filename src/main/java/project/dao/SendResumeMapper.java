package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SendResumeMapper {

    int CountResumeByJobID(@Param("job_id") int job_id);
}
