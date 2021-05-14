package project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.OrderMapper;
import project.model.Job;
import project.model.Order;

import java.util.Date;
import java.util.List;


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

    @Override
    public List<Order> findAllOrder() {
        return orderMapper.findAllOrders();
    }

    @Override
    public Order findOrderById(int id) {
        return orderMapper.findOrderById(id);
    }


    @Override
    public boolean deleteOrderById(int id) {
        if(orderMapper.deleteOrderById(id)==1)
            return true;
        else return false;
    }

    @Override
    public PageInfo<Order> findOrderByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Order> info = new PageInfo<Order>(orderMapper.findAllOrders());
        return info;
    }

    @Override
    public PageInfo<Order> searchOrderByPage(Integer page, Integer limit, int jobId, String state) {
        PageHelper.startPage(page,limit);
        PageInfo<Order> info = new PageInfo<Order>(orderMapper.searchOrder(jobId,state));
        return info;
    }

    @Override
    public int getOrderCount() {
        return orderMapper.getOrderCount();
    }

}
