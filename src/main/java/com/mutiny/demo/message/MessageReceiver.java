package com.mutiny.demo.message;

import com.mutiny.demo.dao.MessageInfoMapper;
import com.mutiny.demo.dao.MessageSendMapper;
import com.mutiny.demo.dao.MessageUserMapper;
import com.mutiny.demo.pojo.MessageInfo;
import com.mutiny.demo.pojo.MessageSend;
import com.mutiny.demo.pojo.MessageSendExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/20 17:49
 */
@Component
public class MessageReceiver {
    @Autowired
    private MessageUserMapper messageUserMapper;
    @Autowired
    private MessageSendMapper messageSendMapper;
    @Autowired
    private MessageInfoMapper messageInfoMapper;

    private List<MessageSend> getMessage(String username){
        MessageSendExample messageSendExample = new MessageSendExample();
        messageSendExample.createCriteria().andUserIdEqualTo(username);
        List<MessageSend> messageSendList = messageSendMapper.selectByExample(messageSendExample);
        return messageSendList;
    }

    /**
     * 取得消息大概,
     * @param username
     */
    public List<MessageInto> getMessageSmall(String username) throws Exception{
        List<MessageSend> messageSendList = getMessage(username);
        List<MessageInto> messageIntoList = new ArrayList<>();
        for (MessageSend record:messageSendList){
            messageIntoList.add(new MessageInto(record.getMessageTitle(),record.getOperator(),record.getMessageSendId(),record.getStatus()));
        }
        return messageIntoList;
    }

    /**
     * 取得消息详情，更新已读，已发送,更新收到时间
     * @param username
     * @param messageId
     * @return
     * @throws Exception
     */
    public MessageDeta getMessageBig(String username,int messageId) throws Exception{
        MessageSend record = messageSendMapper.selectByPrimaryKey(messageId);
        if (!record.getUserId().equals(username)){
            throw new Exception("Your Have No Permission!");
        }
        if (record.getIsDel()){
            throw new Exception("No Such Message!");
        }
        MessageDeta messageDeta = new MessageDeta(record.getMessageTitle(),record.getMessageText(),record.getMessageUrl(),record.getOperator(),record.getMessageSendId());
        if (record.getStatus()){
            return messageDeta;
        }
        record.setPushTime(new Date());
        record.setStatus(true);
        messageSendMapper.updateByPrimaryKeySelective(record);
        MessageInfo messageInfo = messageInfoMapper.selectByPrimaryKey(record.getMessageInfoId());
        messageInfo.setnNum(messageInfo.getnNum()+1);
        messageInfoMapper.updateByPrimaryKeySelective(messageInfo);
        return messageDeta;
    }

    /**
     * 取得消息详情，更新已读，已发送,更新收到时间
     * @param username
     */
    public List<MessageDeta> getMessageBigAll(String username) throws Exception{
        List<MessageSend> messageSendList = getMessage(username);
        List<MessageDeta> messageDetaList = new ArrayList<>();
        for (MessageSend record:messageSendList){
            messageDetaList.add(new MessageDeta(record.getMessageTitle(),record.getMessageText(),record.getMessageUrl(),record.getOperator(),record.getMessageSendId()));
            if (record.getStatus()){
                continue;
            }
            if (record.getIsDel()){
                continue;
            }
            record.setPushTime(new Date());
            record.setStatus(true);
            messageSendMapper.updateByPrimaryKeySelective(record);
            MessageInfo messageInfo = messageInfoMapper.selectByPrimaryKey(record.getMessageInfoId());
            messageInfo.setnNum(messageInfo.getnNum()+1);
            messageInfoMapper.updateByPrimaryKeySelective(messageInfo);
        }
        return messageDetaList;
    }

    /**
     * 用户删除消息
     * @param username
     * @param messageSendId
     * @return
     */
    public boolean deleteMessage(String username,int messageSendId) throws Exception{
        MessageSend messageSend = messageSendMapper.selectByPrimaryKey(messageSendId);
        if (messageSend.getIsDel()){
            throw new Exception("No Such Message!");
        }
        if (!messageSend.getUserId().equals(username)){
            throw new Exception("Your Have No Permission!");
        }
        messageSend.setIsDel(true);
        messageSendMapper.updateByPrimaryKeySelective(messageSend);
        return true;
    }
}
