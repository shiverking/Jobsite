package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import project.model.Order;

import java.util.Date;
import java.util.List;


@Mapper
@Component
public interface OrderMapper {
        //获得所有Order
        List<Order> getAllOrders();

        int countOrdersByJobId(@Param("job_id") int job_id);

        int insertNewOrder(@Param("job_id") int job_id,@Param("state") String state,
                           @Param("created_time") Date create_time ,@Param("end_time") Date end_time,
                           @Param("employer_id") int employer_id,@Param("employee_id") int employee_id
                           );

        //根据job_ID和employee_id来判断是否已经有该order，防止重复
        boolean isOrderExistByJobAndEmployee(@Param("job_id") int job_id,@Param("employee_id") int employee_id);

}
