package com.tmholen.messagingserver.entities;

import com.tmholen.messagingserver.entities.Account.AccountAdapter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@SequenceGenerator(name="seqconversation", initialValue=1)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Conversation implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqconversation")
    Long id;
    
    @OneToMany (cascade = CascadeType.ALL)
    List<Message> messages;
    
        //@XmlTransient
    @XmlJavaTypeAdapter (AccountAdapter.class)
    @ManyToMany (/*mappedBy = "ConversationOverview",*/cascade = CascadeType.PERSIST)
    List<Account> recipients;
    

    public Conversation() {
    }
    
//    public Conversation(List<Account> recipients, List<Message> messages) {
//        this.recipients = recipients;
//        this.messages = messages;
//    }
//    

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
    
    public void addMessage(Message message){
        messages.add(message);
    }
    
}
