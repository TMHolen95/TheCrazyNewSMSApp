package com.tmholen.messagingserver.entities;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.TABLE;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@SequenceGenerator(name="seqmessage", initialValue=1)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Message implements Serializable {

    // Identification fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqmessage")
    Long id;
    
    Long conversationId;

    // Data fields
    
    Long senderId;
    String text;
    Long timestampSent;

    public Message() {

    }

    public Message(Long conversationId, Long senderId, String text, Long timestampSent) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
        this.timestampSent = timestampSent;
    }

    public Message(Long conversationId, Long senderId, String text) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
        this.timestampSent = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
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
