package com.tmholen.messagingserver.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Message implements Serializable {

    // Identification fields
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Conversation conversation;

    // Data fields
    @OneToOne
    Account sender;
    String text;
    Long timestampSent;

    public Message() {

    }

    public Message(Conversation conversation, Account sender, String text, Long timestampSent) {
        this.conversation = conversation;
        this.sender = sender;
        this.text = text;
        this.timestampSent = timestampSent;
    }

    public Message(Conversation conversation, Account sender, String text) {
        this.conversation = conversation;
        this.sender = sender;
        this.text = text;
        this.timestampSent = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTimestampSent() {
        return timestampSent;
    }
        
}
