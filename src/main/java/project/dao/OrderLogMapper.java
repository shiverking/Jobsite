package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.OrderLog;

import java.util.List;

@Mapper
@Component
public interface OrderLogMapper {
    //获得所有OrderLog
    List<OrderLog> getAllOrderLog();

}
