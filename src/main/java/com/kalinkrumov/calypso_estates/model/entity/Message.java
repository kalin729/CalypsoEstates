package com.kalinkrumov.calypso_estates.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    private String property;

    private boolean replied;

    @Column(columnDefinition = "TEXT")
    private String reply;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public Message setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getSenderName() {
        return senderName;
    }

    public Message setSenderName(String senderName) {
        this.senderName = senderName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Message setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Message setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public Message setProperty(String property) {
        this.property = property;
        return this;
    }

    public boolean isReplied() {
        return replied;
    }

    public Message setReplied(boolean replied) {
        this.replied = replied;
        return this;
    }

    public String getReply() {
        return reply;
    }

    public Message setReply(String reply) {
        this.reply = reply;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Message setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
