package project.service;

import org.springframework.stereotype.Service;

import java.util.Date;


public interface OrderService {

    int countOrderByJobId(int job_id);
    int insertNewOrder(int job_id, String state, Date create_time, Date end_time, int employer_id, int employee_id);
    boolean isOrderExistByJobAndEmployee(int job_id, int employee_id);
}
