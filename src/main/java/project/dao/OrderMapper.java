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
        List<Order> getAllOrders();

}
