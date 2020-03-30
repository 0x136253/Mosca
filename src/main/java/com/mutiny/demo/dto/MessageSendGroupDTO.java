package com.mutiny.demo.dto;

import com.mutiny.demo.message.Message;
import com.mutiny.demo.pojo.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/12 15:45
 */
public class MessageSendGroupDTO {
    @ApiModelProperty(value = "title", required = true)
    private String title;
    @ApiModelProperty(value = "text", required = true)
    private String text;
    @ApiModelProperty(value = "消息URL,一般为null", required = false)
    private String messageUrl;
    @ApiModelProperty(value = "消息推送时间(时间戳)", required = true)
    private long pushTime;
    @ApiModelProperty(value = "username List",required = true)
    private List<String> userNameList;

    public Message toMessage(){
        Message message = new Message();
        message.setTitle(title);
        message.setText(text);
        message.setMessageUrl(messageUrl);
        message.setPushTime(new Date(pushTime));
        message.setType("Group");
        return message;
    }

    public List<User> toUserList(){
        List<User> userList = new ArrayList<>();
        for (String str:userNameList){
            User user = new User();
            user.setId(str);
            userList.add(user);
        }
        return userList;
    }

    public MessageSendGroupDTO() {
    }

    public MessageSendGroupDTO(String title, String text, String messageUrl, long pushTime, List<String> userNameList) {
        this.title = title;
        this.text = text;
        this.messageUrl = messageUrl;
        this.pushTime = pushTime;
        this.userNameList = userNameList;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public long getPushTime() {
        return pushTime;
    }

    public void setPushTime(long pushTime) {
        this.pushTime = pushTime;
    }
}
