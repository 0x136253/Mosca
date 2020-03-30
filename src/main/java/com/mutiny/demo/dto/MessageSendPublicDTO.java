package com.mutiny.demo.dto;

import com.mutiny.demo.message.Message;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/12 15:45
 */
public class MessageSendPublicDTO {
    @ApiModelProperty(value = "title", required = true)
    private String title;
    @ApiModelProperty(value = "text", required = true)
    private String text;
    @ApiModelProperty(value = "消息URL,一般为null", required = false)
    private String messageUrl;
    @ApiModelProperty(value = "消息推送时间(时间戳)", required = true)
    private long pushTime;

    public Message toMessage(){
        Message message = new Message();
        message.setTitle(title);
        message.setText(text);
        message.setMessageUrl(messageUrl);
        message.setPushTime(new Date(pushTime));
        message.setType("Public");
        return message;
    }

    public MessageSendPublicDTO() {
    }

    public MessageSendPublicDTO(String title, String text, String messageUrl, long pushTime) {
        this.title = title;
        this.text = text;
        this.messageUrl = messageUrl;
        this.pushTime = pushTime;
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
