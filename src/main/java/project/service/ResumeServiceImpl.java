package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.SendResumeMapper;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService{

    @Autowired
    SendResumeMapper sendResumeMapper;

    @Override
    public int countByJobId(int job_id) {
        return sendResumeMapper.CountResumeByJobID(job_id);
    }

    @Override
    public List<Integer> getProfileByJob(int job_id) {
        return sendResumeMapper.getProfileByJob(job_id);
    }
}
