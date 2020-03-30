package com.mutiny.demo.message;

import com.mutiny.demo.dao.MessageInfoMapper;
import com.mutiny.demo.dao.MessageUserMapper;
import com.mutiny.demo.dao.UserMapper;
import com.mutiny.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/20 17:49
 */
@Component
public class MessageSender {
    @Autowired
    private MessageInfoMapper messageInfoMapper;
    @Autowired
    private MessageUserMapper messageUserMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 发送消息,userList 可用userMapper生成
     * @param message
     * @return
     * @throws Exception
     */
    public boolean sendAll(Message message,List<User> userList) throws Exception{
        MessageInfoExample messageInfoExample = new MessageInfoExample();
        messageInfoExample.createCriteria();
        int info_size = messageInfoMapper.selectByExample(messageInfoExample).size();
        int user_size = userList.size();
        MessageInfo messageInfo = message.toMessageInfo();
        messageInfo.setMessageInfoId(info_size+1);
        messageInfo.setsNum(user_size);
        messageInfo.setnNum(0);
        messageInfoMapper.insertSelective(messageInfo);
        for(User record:userList) {
            MessageUser messageUser = new MessageUser();
            messageUser.setMessageInfoId(info_size+1);
            messageUser.setUserId(record.getId());
            messageUserMapper.insertSelective(messageUser);
        }
        return true;
    }

    public boolean sendPrivate(Message message,String username) throws Exception{
        User user = new User();
        user.setId(username);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        return sendAll(message,userList);
    }

    public boolean sendPrivate(String title,String text,String username) throws Exception{
        Message message = new Message(title,text,"private");
        User user = new User();
        user.setId(username);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        return sendAll(message,userList);
    }

    public boolean sendPublic(Message message) throws Exception{
        UserExample userExample = new UserExample();
        userExample.createCriteria();
        List<User> userList = userMapper.selectByExample(userExample);
        return sendAll(message,userList);
    }

    public boolean sendPublic(String title,String text) throws Exception{
        Message message = new Message(title,text,"public");
        UserExample userExample = new UserExample();
        userExample.createCriteria();
        List<User> userList = userMapper.selectByExample(userExample);
        return sendAll(message,userList);
    }
}
