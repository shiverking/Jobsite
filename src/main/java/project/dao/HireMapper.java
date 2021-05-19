package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * 雇佣关系mapper
 */
@Mapper
@Component
public interface HireMapper {

    int insertNewHire(@Param("job_id") int job_id, @Param("employee_id") int employee_id,
                      @Param("employer_id") int employer_id, @Param("created_time") Date created_time);

    int countHiresByJob(@Param("job_id") int job_id);

    boolean isHireExit(@Param("job_id")int job_id, @Param("employer_id") int employer_id);

    List<Integer> getEmployerByJob(@Param("job_id") int job_id);



}
