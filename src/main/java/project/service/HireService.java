package project.service;

import java.util.Date;
import java.util.List;

public interface HireService {

    int insertNewHire(int job_id, int employer_id, int employee_id, Date created_time);
    int countHiresByJob(int job_id);
    boolean isHireExit(int job_id,int employer_id);
    //招聘人数已满，将创建一个新order添加
    int finishHire(int job_id, String state);
    List<Integer> getEmployerByJob(int job_id);
}
