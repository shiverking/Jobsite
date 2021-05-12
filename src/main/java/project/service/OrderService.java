package project.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import project.model.Order;

import java.util.List;


public interface OrderService {

    int countOrderByJobId(int job_id);
    int insertNewOrder(int job_id, String state, Date create_time, Date end_time, int employer_id, int employee_id);
    boolean isOrderExistByJobAndEmployee(int job_id, int employee_id);
    //返回所有订单
    List<Order> findAllOrder();
    Order findOrderById(int id);
}
