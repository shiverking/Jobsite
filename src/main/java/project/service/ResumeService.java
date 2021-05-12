package project.service;

import java.util.List;

public interface ResumeService {
    int countByJobId(int job_id);

    List<Integer>  getProfileByJob(int job_id);

}
