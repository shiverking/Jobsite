package project.service;


import com.github.pagehelper.PageInfo;
import project.model.Job;

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
    //根据id查找订单
    Order findOrderById(int id);

    //根据id删除订单
    boolean deleteOrderById(int id);

    PageInfo<Order> findOrderByPage(Integer pageNum, Integer limitNum);

    PageInfo<Order> searchOrderByPage(Integer page, Integer limit, int jobId, String state);

    int getOrderCount();
}
