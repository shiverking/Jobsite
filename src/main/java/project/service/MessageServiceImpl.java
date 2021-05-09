package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.MessageMapper;
import project.model.MessageInfo;

import java.util.Date;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：MessageService的实现类
 * @date ：2021/4/30 20:43
 */
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageMapper messageMapper;
    @Override
    /**
     * 返回true表示聊过天，返回false表示没有聊过天
     */
    public boolean isFirstChat(int from_id, int to_id) {
        //判断是否聊过天
        if(messageMapper.isFirstChat(from_id, to_id)||messageMapper.isFirstChat(to_id,from_id)){
            return true;
        }
        return false;
    }

    /**
     * 向用户聊天关系主表中插入一条记录
     * @param from_id
     * @param to_id
     * @param create_time
     * @return
     */
    @Override
    public boolean insertChatUserLink(int from_id, int to_id, Date create_time) {
        if(messageMapper.insertChatUserLink(from_id,to_id,create_time)==1){
            //插入主表成功
            return true;
        }
        return false;
    }

    /**
     * 向聊天列表中插入两条数据
     * @param link_id
     * @param from_id
     * @param to_id
     * @return
     */
    @Override
    public boolean insertChatList(int link_id, int from_id, int to_id) {
        //分别插入两条数据
        if(messageMapper.insertChatList(link_id,from_id,to_id,0,0,0,0)==1&&messageMapper.insertChatList(link_id,to_id,from_id,0,0,0,0)==1){
            return true;
        }
        return false;
    }

    /**
     * 返回两个用户关联的linkid
     * @param from_id
     * @param to_id
     * @return
     */
    @Override
    public int selectLinkId(int from_id, int to_id) {
        return messageMapper.selectLinkId(from_id,to_id);
    }

    /**
     * 获取聊天了列表，返回值为和自己聊天的对象的id
     * @param from_id
     * @return
     */
    @Override
    public List<Integer> getFromUserChatList(int from_id) {
        return messageMapper.getRecentChatList(from_id);
    }

    /**
     * 返回所有聊天记录
     * //返回currentIndex数量的聊天记录
     * @param sender_id
     * @param receiver_id
     * @param currentIndex
     * @return
     */
    @Override
    public List<MessageInfo> getRecentChatRecords(int sender_id,int receiver_id , int currentIndex) {
        return messageMapper.findMessageBySenderIdAndReceiverId(sender_id,receiver_id);
    }

    /**
     * 保存消息记录
     * @param link_id
     * @param sender_id
     * @param receiver_id
     * @param content
     * @param send_time
     * @return
     */
    @Override
    public boolean saveMessage(int link_id, int sender_id, int receiver_id, String content, Date send_time) {
        //插入记录成功，返回true
        if(messageMapper.insertMessageRecord(link_id,sender_id,receiver_id,content,send_time)==1){
            return true;
        }
        return false;
    }
}
