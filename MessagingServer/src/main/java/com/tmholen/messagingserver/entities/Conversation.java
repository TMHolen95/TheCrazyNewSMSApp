package com.tmholen.messagingserver.entities;

import java.io.Serializable;
import java.util.List;
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
public class Conversation implements Serializable {
    @Id @GeneratedValue
    Long id;
    
    // Data fields
    @OneToMany
    List<Account> accountRecipients;
    
    @OneToMany
    List<Message> messages;
}
