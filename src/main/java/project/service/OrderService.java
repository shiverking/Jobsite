package project.service;


import org.springframework.stereotype.Service;

import java.util.Date;
import project.model.Order;

import java.util.List;


public interface OrderService {

    int countOrderByJobId(int job_id);
    int insertNewOrder(int job_id, String state, Date create_time, Date end_time);
    boolean isOrderExistByJobAndEmployee(int job_id, int employee_id);
    //返回所有订单
    List<Order> findAllOrder();
    Order findOrderById(int id);
    Order getOrderByJobId(int job_id);
    List<Order> getOrdersBystate(String state);
    int updateOrderState(int id, String state);
    List<Integer> getJobInOrderByemployer(int employer_id);
    int getJobById(int id);
    int updateEnd(int id, Date end);


}
