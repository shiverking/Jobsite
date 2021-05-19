package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.HireMapper;
import project.dao.OrderinfoMapper;

import java.util.Date;
import java.util.List;


@Service
public class HireServiceImpl implements HireService{


    @Autowired
    HireMapper hireMapper;

    @Autowired
    OrderinfoMapper orderinfoMapper;

    @Override
    public int insertNewHire(int job_id, int employer_id, int employee_id, Date created_time) {
        return hireMapper.insertNewHire(job_id, employer_id, employee_id, created_time);
    }

    @Override
    public int countHiresByJob(int job_id) {

        return hireMapper.countHiresByJob(job_id);
    }

    @Override
    public boolean isHireExit(int job_id, int employer_id) {
        return hireMapper.isHireExit(job_id, employer_id);
    }

    @Override
    public int finishHire(int job_id, String state) {
        Date date =new Date();
        return  orderinfoMapper.insertNewOrder(job_id,"0",date,date);
    }

    @Override
    public List<Integer> getEmployerByJob(int job_id) {
        return hireMapper.getEmployerByJob(job_id);
    }
}
