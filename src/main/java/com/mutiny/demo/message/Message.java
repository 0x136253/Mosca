package com.mutiny.demo.message;

import com.mutiny.demo.pojo.MessageInfo;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/20 17:53
 */
public class Message {
    /**
     *   消息标题
     */
    private String title;

    /**
     *   消息内容
     */
    private String text;

    /**
     *   消息跳转url
     * @mbg.generated 2020-03-20 17:46:49
     */
    private String messageUrl;

    /**
     *   消息推送时间
     */
    private Date pushTime;

    /**
     *   消息创建人id
     */
    private String operator;
    /**
     *   消息类型,private私信，group 100人以内组消息，public 100人以上公共消息
     */
    private String type;

    /**
     * ID,s_num,n_num need to Complete
     * @return
     */
    public MessageInfo toMessageInfo(){
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setTitle(title);
        messageInfo.setText(text);
        messageInfo.setMessageUrl(messageUrl);
        messageInfo.setPushTime(pushTime);
        messageInfo.setOperator(operator);
        messageInfo.setType(type);
        return messageInfo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", messageUrl='" + messageUrl + '\'' +
                ", pushTime=" + pushTime +
                ", operator='" + operator + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Message() {
    }

    /**
     * System Push RightNow
     * @param title
     * @param text
     * @param type
     */
    public Message(String title, String text, String type) {
        this.title = title;
        this.text = text;
        this.type = type;
        this.messageUrl = "";
        this.pushTime = new Date();
        this.operator = "System";
    }

    public Message(String title, String text, String messageUrl, Date pushTime, String operator, String type) {
        this.title = title;
        this.text = text;
        this.messageUrl = messageUrl;
        this.pushTime = pushTime;
        this.operator = operator;
        this.type = type;
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

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
