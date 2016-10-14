package com.tmholen.messagingserver.entities;

import com.tmholen.messagingserver.entities.Account.AccountAdapter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Conversation implements Serializable {
    @Id @GeneratedValue
    Long id;
    
    @OneToMany (cascade = CascadeType.ALL)
    List<Message> messages;
    
    @XmlJavaTypeAdapter (AccountAdapter.class)
    @ManyToMany (cascade = CascadeType.PERSIST)
    List<Account> recipients;
    
    @XmlJavaTypeAdapter (AccountAdapter.class)
    @ManyToOne (optional = false, cascade = CascadeType.PERSIST)
    Account owner;


    public Conversation() {
    }
    
    public Conversation(Account owner){
        this.owner = owner;
    }

    public Conversation(List<Account> recipients, List<Message> messages, Account owner) {
        this.recipients = recipients;
        this.messages = messages;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public List<Account> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Account> recipients) {
        this.recipients = recipients;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
    
    
    
}
