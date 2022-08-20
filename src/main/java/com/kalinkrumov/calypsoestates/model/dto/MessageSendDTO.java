package com.kalinkrumov.calypsoestates.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MessageSendDTO {

    @NotBlank
    @Size(min = 2)
    private String senderName;

    @Email
    @NotBlank
    @Size(min = 3, max = 30)
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String subject;

    @NotBlank
    @Size(min = 10)
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
