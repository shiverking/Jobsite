package project.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import project.model.User;

@Mapper
@Component
public interface UserMapper {
     User findUserByUsername (String username);
     int insertUser(User user);

}
