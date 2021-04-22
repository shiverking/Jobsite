package project.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.MessageInfo;
/**
 * @author ：闫崇傲
 * @description：用于聊天的Mapper类
 * @date ：2021/4/19 13:57
 */
@Mapper
@Component
public interface MessageMapper {
    MessageInfo findMessageBySenderIdAndReceiverId(@Param("senderId") int SenderId,@Param("receiverId") int receiverId);

}
