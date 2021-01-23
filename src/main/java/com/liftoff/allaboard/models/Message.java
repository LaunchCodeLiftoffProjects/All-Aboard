package com.liftoff.allaboard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class Message {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String messageText;

    private LocalDateTime timeSent;
    private int fromUserId;
    private int toGameGroupId;


    public Message() {

    }

    public Message(String messageText, int fromUserId, int toGameGroupId) {
        this.timeSent = LocalDateTime.now();
        setMessageText(messageText);
        setFromUserId(fromUserId);
        setToGameGroupId(toGameGroupId);
    }

    public int getId() {
        return id;
    }


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToGameGroupId() {
        return toGameGroupId;
    }

    public void setToGameGroupId(int toGameGroupId) {
        this.toGameGroupId = toGameGroupId;
    }
}



