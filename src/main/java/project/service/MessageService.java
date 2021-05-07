package project.service;

import org.apache.ibatis.annotations.Param;
import project.model.MessageInfo;

import java.util.Date;
import java.util.List;

/**
 * 聊天的Service接口
 */
public interface MessageService {
    /**
     * 查询聊天双方的关联id
     * @param from_id
     * @param to_id
     * @return
     */
    int selectLinkId(int from_id, int to_id);

    /**
     * 是否第一次聊天
     * @param from_id
     * @param to_id
     * @return
     */
    boolean isFirstChat(int from_id, int to_id);

    /**
     * 往聊天主表内插入一条记录
     * @param from_id
     * @param to_id
     * @param create_time
     * @return
     */
    boolean insertChatUserLink(int from_id, int to_id, Date create_time);
    /**
     * 往聊天列表插入一条记录
     * @param link_id
     * @param from_id
     * @param to_id
     * @return
     */
    boolean insertChatList(int link_id,int from_id, int to_id);

    /**
     * 保存聊天记录
     * @param link_id
     * @param sender_id
     * @param receiver_id
     * @param content
     * @param send_time
     * @return
     */
    boolean saveMessage(int link_id, int sender_id, int receiver_id, String content, Date send_time);

    /**
     * 获取当前用户的聊天列表
     * @param from_id
     * @return
     */
    List<Integer> getFromUserChatList(int from_id);

    /**
     * 获取发送者与接收者的最近的聊天记录
     * @param sender_id
     * @param receiver_id
     * @param currentIndex
     * @return
     */
    List<MessageInfo> getRecentChatRecords(int sender_id, int receiver_id, int currentIndex);

    /**
     * 更新是否在同一窗口值
     * @param from_id
     * @param to_id
     */
    //void updateWindows(int from_id, int to_id);


    /**
     * 获取当前用户的未读数
     * @param id
     * @return
     */
    //ResultInfo getUnreadTotalNumber(int id);

    /**
     *
     * @param username
     */
    //void resetWindows(String username);

}
