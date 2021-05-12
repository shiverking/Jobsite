package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.OrderMapper;

import java.util.Date;


@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderMapper orderMapper;


    @Override
    public int countOrderByJobId(int job_id) {
        return orderMapper.countOrdersByJobId(job_id);
    }

    @Override
    public int insertNewOrder(int job_id, String state, Date create_time, Date end_time, int employer_id, int employee_id) {
        return orderMapper.insertNewOrder(job_id, state, create_time, end_time, employer_id, employee_id);
    }

    @Override
    public boolean isOrderExistByJobAndEmployee(int job_id, int employee_id) {
        return orderMapper.isOrderExistByJobAndEmployee(job_id,employee_id);
    }

}
