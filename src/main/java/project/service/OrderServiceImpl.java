package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.OrderinfoMapper;
import project.dao.UserMapper;
import project.model.Order;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderinfoMapper orderinfoMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public int countOrderByJobId(int job_id) {
        return orderinfoMapper.countOrdersByJobId(job_id);
    }

    @Override
    public int insertNewOrder(int job_id, String state, Date create_time, Date end_time) {
        return orderinfoMapper.insertNewOrder(job_id, state, create_time, end_time);
    }

    @Override
    public boolean isOrderExistByJobAndEmployee(int job_id, int employee_id) {
        return orderinfoMapper.isOrderExistByJobAndEmployee(job_id,employee_id);
    }

    @Override
    public List<Order> findAllOrder() {
        return orderinfoMapper.findAllOrders();
    }

    @Override
    public Order findOrderById(int id) {
        return orderinfoMapper.findOrderById(id);
    }

    @Override
    public Order getOrderByJobId(int job_id) {
        return orderinfoMapper.getOrderByJobId(job_id);
    }

    @Override
    public List<Order> getOrdersBystate(String state) {
        return orderinfoMapper.getOrdersByState(state);
    }

    @Override
    public int updateOrderState(int id, String state) {
        return orderinfoMapper.updateOrderState(id,state);
    }

    @Override
    public List<Integer> getJobInOrderByemployer(int employer_id) {
            return orderinfoMapper.getJobInOrderByemployer(employer_id);
    }

    @Override
    public int getJobById(int id) {
        return orderinfoMapper.getJobById(id);
    }

    @Override
    public int updateEnd(int id, Date end) {
        return orderinfoMapper.updateEnd(id,end);
    }
}
