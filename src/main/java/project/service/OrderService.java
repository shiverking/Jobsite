package project.service;

import project.model.Order;

import java.util.List;

/**
 * OrderService的接口类
 */
public interface OrderService {

    //返回所有订单
    public List<Order> findAllOrder();

    Order findOrderById(int id);
}
