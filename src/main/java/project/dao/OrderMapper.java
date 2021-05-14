package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.Order;
import java.util.List;


@Mapper
@Component
public interface OrderMapper {
    //获得所有Order
    List<Order> findAllOrders();
    //根据id查找order
    Order findOrderById(@Param("id") int id);
    //根据id删除order
    int deleteOrderById(@Param("id") int id);

    List<Order> searchOrder(int jobId, String state);


    int getOrderCount();
}
