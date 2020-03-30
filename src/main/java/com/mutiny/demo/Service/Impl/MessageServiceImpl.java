package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.MessageService;
import com.mutiny.demo.dto.MessageSendGroupDTO;
import com.mutiny.demo.dto.MessageSendPrivateDTO;
import com.mutiny.demo.dto.MessageSendPublicDTO;
import com.mutiny.demo.message.*;
import com.mutiny.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/12 15:30
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private MessageUpdate messageUpdate;
    @Autowired
    private MessageReceiver messageReceiver;

    @Override
    public String SendPrivate(MessageSendPrivateDTO messageSendPrivateDTO,String operator) throws Exception {
        Message message = messageSendPrivateDTO.toMessage();
        message.setOperator(operator);
        return messageSender.sendPrivate(message,messageSendPrivateDTO.getUserName())?"Success":"failed";
    }

    @Override
    public String SendPublic(MessageSendPublicDTO messageSendPublicDTO,String operator) throws Exception {
        Message message = messageSendPublicDTO.toMessage();
        message.setOperator(operator);
        return messageSender.sendPublic(message)?"Success":"failed";
    }

    @Override
    public String SendGroup(MessageSendGroupDTO messageSendGroupDTO,String operator) throws Exception {
        Message message = messageSendGroupDTO.toMessage();
        message.setOperator(operator);
        List<User> userList = messageSendGroupDTO.toUserList();
        return messageSender.sendAll(message,userList)?"Success":"failed";
    }

    @Override
    public List<MessageInto> getMessageSmall(String username) throws Exception {
        return messageReceiver.getMessageSmall(username);
    }

    @Override
    public MessageDeta getMessageBig(String username, int messageId) throws Exception {
        return messageReceiver.getMessageBig(username,messageId);
    }

    @Override
    public boolean deleteMessage(String username, int messageSendId) throws Exception {
        return messageReceiver.deleteMessage(username,messageSendId);
    }
}
