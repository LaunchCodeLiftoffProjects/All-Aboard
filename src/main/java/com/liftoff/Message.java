package com.liftoff;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
public class Message {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String messageText;

    private LocalDate timeSent;
    private int fromUserId;
    private int toGameGroupId;

    public Message(String messageText, int fromUserId, int toGameGroupId) {
        this.timeSent = LocalDate.now();
        setMessageText(messageText);
        setFromUserId(fromUserId);
        setToGameGroupId(toGameGroupId);
    }


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDate getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDate timeSent) {
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



