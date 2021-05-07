package project.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.MessageInfo;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：用于聊天的Mapper类
 * @date ：2021/4/19 13:57
 */
@Mapper
@Component
public interface MessageMapper {
    //获取所有聊天记录
    List<MessageInfo> findMessageBySenderIdAndReceiverId(@Param("sender_id") int sender_id,@Param("receiver_id") int receiver_id);
    //查询是否是第一次聊天
    boolean isFirstChat(@Param("from_id")int from_id, @Param("to_id") int to_id);
    //如果是第一次聊天，往聊天主表插入一条记录
    int insertChatUserLink(@Param("from_id") int from_id,@Param("to_id") int to_id,@Param("create_time") Date create_time);
    //往聊天列表中插入一条记录 from_window和to_window都是0表示离线 1表示在线
    int insertChatList(@Param("link_id") int link_id,@Param("from_id") int from_id,@Param("to_id") int to_id,@Param("from_window") int from_window,@Param("to_window") int to_window,@Param("unread") int unread,@Param("status") int status);
    //修改在线状态,将在线状态修改为0或者1
    int updateOnlineStatus(@Param("from_id") int from_id,@Param("from_window")int from_window);
    //获取单个点对点聊天的未读数
    int getUnreadByFromIdAndToId(@Param("from_id") int from_id,@Param("to_id")int to_id);
    //根据from_id和to_id修改对方的未读数
    int updateUnread(@Param("from_id") int from_id,@Param("to_id") int to_id,@Param("unread") int unread);
    //往聊天详情表插入一条数据,保存聊天记录
    int insertMessageRecord(@Param("link_id") int link_id,@Param("sender_id") int sender_id,@Param("receiver_id") int receiver_id,@Param("content") String content,@Param("send_time") Date send_time);
    //从聊天主表查找两个人聊天的关系LinkID
    int selectLinkId(@Param("from_id")int from_id, @Param("to_id") int to_id);
    //查找个人聊天列表,返回类型为聊天对象的id列表
    List<Integer> getRecentChatList(@Param("from_id")int from_id);
}
