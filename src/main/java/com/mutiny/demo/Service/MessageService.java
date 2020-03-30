package com.mutiny.demo.Service;

import com.mutiny.demo.dto.MessageSendGroupDTO;
import com.mutiny.demo.dto.MessageSendPrivateDTO;
import com.mutiny.demo.dto.MessageSendPublicDTO;
import com.mutiny.demo.message.MessageDeta;
import com.mutiny.demo.message.MessageInto;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/12 15:30
 */
public interface MessageService {
    String SendPrivate(MessageSendPrivateDTO messageSendPrivateDTO,String operator) throws Exception;
    String SendPublic(MessageSendPublicDTO messageSendPublicDTO,String operator) throws Exception;
    String SendGroup(MessageSendGroupDTO messageSendGroupDTO,String operator) throws Exception;
    List<MessageInto> getMessageSmall(String username) throws Exception;
    MessageDeta getMessageBig(String username, int messageId) throws Exception;
    boolean deleteMessage(String username,int messageSendId) throws Exception;
}
