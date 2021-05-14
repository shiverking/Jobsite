package project.service;

import com.github.pagehelper.PageInfo;
import project.model.Job;
import project.model.Order;

import java.util.List;

/**
 * OrderService的接口类
 */
public interface OrderService {

    //返回所有订单
    public List<Order> findAllOrder();

    //根据id查找订单
    Order findOrderById(int id);

    //根据id删除订单
    boolean deleteOrderById(int id);

    PageInfo<Order> findOrderByPage(Integer pageNum, Integer limitNum);

    PageInfo<Order> searchOrderByPage(Integer page, Integer limit, int jobId, String state);

    int getOrderCount();
}
