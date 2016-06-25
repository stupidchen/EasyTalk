package com.stupidchen.easytalk.data;


import java.sql.Time;

/**
 * Created by Mike on 16/6/6.
 */
public class Message {
    private int messageId;

    private String fromUserId;

    private String toUserId;

    private String message;

    private String sendTime;

    private int status;

    public Message() {
    }

    public Message(String toUserId, String message, String sendTime) {
        this.toUserId = toUserId;
        this.message = message;
        this.sendTime = sendTime;
    }

    public Message(String fromUserId, String toUserId, String message, String sendTime, int status) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.sendTime = sendTime;
        this.status = status;
    }

    public Message(int messageId, String fromUserId, String toUserId, String message, String sendTime, int status) {
        this.messageId = messageId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.sendTime = sendTime;
        this.status = status;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
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
