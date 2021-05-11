package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.OrderMapper;
import project.model.Order;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> findAllOrder() {
        return orderMapper.findAllOrders();
    }

    @Override
    public Order findOrderById(int id) {
        return orderMapper.findOrderById(id);
    }

}
