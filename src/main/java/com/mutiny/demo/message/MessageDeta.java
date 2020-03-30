package com.mutiny.demo.message;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/21 12:33
 */
public class MessageDeta {
    private String title;
    private String text;
    private String messageUrl;
    private String operator;
    private int messageId;

    @Override
    public String toString() {
        return "MessageDeta{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", messageUrl='" + messageUrl + '\'' +
                ", operator='" + operator + '\'' +
                ", messageId=" + messageId +
                '}';
    }

    public MessageDeta() {
    }

    public MessageDeta(String title, String text, String messageUrl, String operator, int messageId) {
        this.title = title;
        this.text = text;
        this.messageUrl = messageUrl;
        this.operator = operator;
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
