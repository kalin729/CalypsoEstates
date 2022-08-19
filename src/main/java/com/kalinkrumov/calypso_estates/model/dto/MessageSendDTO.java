package com.kalinkrumov.calypso_estates.model.dto;

public class MessageSendDTO {

    private String senderName;

    private String email;

    private String subject;

    private String message;

    private String property;

    public String getSenderName() {
        return senderName;
    }

    public MessageSendDTO setSenderName(String senderName) {
        this.senderName = senderName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MessageSendDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MessageSendDTO setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageSendDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public MessageSendDTO setProperty(String property) {
        this.property = property;
        return this;
    }
}
