package com.stupidchen.easytalk.data;


import java.sql.Time;

/**
 * Created by Mike on 16/6/6.
 */
public class Message {
    private String messageId;

    private String fromUserId;

    private String toUserId;

    private String message;

    private Time sendTime;

    private int status;

    public Message() {
    }

    public Message(String messageId, String fromUserId, String toUserId, String message, Time sendTime, int status) {
        this.messageId = messageId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.sendTime = sendTime;
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Time getSendTime() {
        return sendTime;
    }

    public void setSendTime(Time sendTime) {
        this.sendTime = sendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return messageId + ":" + message;
    }

}
