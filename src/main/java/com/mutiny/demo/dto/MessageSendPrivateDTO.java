package com.mutiny.demo.dto;

import com.mutiny.demo.message.Message;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/12 15:46
 */
public class MessageSendPrivateDTO {
    @ApiModelProperty(value = "title", required = true)
    private String title;
    @ApiModelProperty(value = "text", required = true)
    private String text;
    @ApiModelProperty(value = "消息URL,一般为null", required = false)
    private String messageUrl;
    @ApiModelProperty(value = "消息推送时间(时间戳)", required = true)
    private long pushTime;
    @ApiModelProperty(value = "接受消息人的ID", required = true)
    private String userName;

    public Message toMessage(){
        Message message = new Message();
        message.setTitle(title);
        message.setText(text);
        message.setMessageUrl(messageUrl);
        message.setPushTime(new Date(pushTime));
        message.setType("Private");
        return message;
    }

    public MessageSendPrivateDTO() {
    }

    public MessageSendPrivateDTO(String title, String text, String messageUrl, long pushTime, String userName) {
        this.title = title;
        this.text = text;
        this.messageUrl = messageUrl;
        this.pushTime = pushTime;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
