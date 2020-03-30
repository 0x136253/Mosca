package com.mutiny.demo.message;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/21 12:32
 */
public class MessageInto {
    private String title;
    private String operator;
    private int messageId;
    private boolean isRead;

    @Override
    public String toString() {
        return "MessageInto{" +
                "title='" + title + '\'' +
                ", operator='" + operator + '\'' +
                ", messageId=" + messageId +
                ", isRead=" + isRead +
                '}';
    }

    public MessageInto() {
    }

    public MessageInto(String title, String operator, int messageId, boolean isRead) {
        this.title = title;
        this.operator = operator;
        this.messageId = messageId;
        this.isRead = isRead;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
