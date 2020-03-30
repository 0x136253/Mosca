package com.mutiny.demo.message;

import com.mutiny.demo.dao.MessageInfoMapper;
import com.mutiny.demo.dao.MessageSendMapper;
import com.mutiny.demo.dao.MessageUserMapper;
import com.mutiny.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/20 17:49
 */
@Component
public class MessageUpdate {
    @Autowired
    private MessageUserMapper messageUserMapper;
    @Autowired
    private MessageSendMapper messageSendMapper;
    @Autowired
    private MessageInfoMapper messageInfoMapper;

    /**
     * 用户登陆后拉取消息
     * @param username
     * @return
     * @throws Exception
     */
    public boolean update(String username) throws Exception{
        MessageUserExample messageUserExample = new MessageUserExample();
        messageUserExample.createCriteria().andUserIdEqualTo(username);
        List<MessageUser> messageUserList = messageUserMapper.selectByExample(messageUserExample);
        if (messageUserList.size()==0){
            return true;
        }
        MessageSendExample messageSendExample = new MessageSendExample();
        for (MessageUser record:messageUserList){
            messageSendExample.clear();
            if (!record.getStatus()){
                MessageInfo temp = messageInfoMapper.selectByPrimaryKey(record.getMessageInfoId());
                if (temp.getPushTime().after(new Date())){
                    continue;
                }
                messageSendExample.createCriteria().andUserIdEqualTo(username).andMessageInfoIdEqualTo(temp.getMessageInfoId());
                if (messageSendMapper.selectByExample(messageSendExample).size()!=0){
                    continue;
                }
                MessageSend messageSend = new MessageSend();
                messageSend.setMessageInfoId(temp.getMessageInfoId());
                messageSend.setUserId(username);
                messageSend.setMessageTitle(temp.getTitle());
                messageSend.setMessageText(temp.getText());
                messageSend.setMessageUrl(temp.getMessageUrl());
                messageSend.setType(temp.getType());
                messageSend.setOperator(temp.getOperator());
                messageSendMapper.insertSelective(messageSend);
            }
        }
        return true;
    }

    /**
     * 消息发送方删除即将发送的消息????Chenck permission
     * @param username
     * @param messageInfoId
     * @return
     * @throws Exception
     */
    public boolean deleteMessage(String username,int messageInfoId) throws Exception{
        MessageUserExample messageUserExample = new MessageUserExample();
        messageUserExample.createCriteria().andUserIdEqualTo(username).andMessageInfoIdEqualTo(messageInfoId);
        MessageUser messageUser = messageUserMapper.selectByExample(messageUserExample).get(0);
        messageUser.setStatus(true);
        messageUserMapper.updateByPrimaryKeySelective(messageUser);
        MessageInfo messageInfo = messageInfoMapper.selectByPrimaryKey(messageInfoId);
        messageInfo.setsNum(messageInfo.getsNum()-1);
        messageInfoMapper.updateByPrimaryKeySelective(messageInfo);
        return true;
    }

}
