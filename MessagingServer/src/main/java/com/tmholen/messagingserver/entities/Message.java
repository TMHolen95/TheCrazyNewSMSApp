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
    @Id @GeneratedValue
    Long id;
    
    @ManyToOne
    Conversation conversation;

    // Data fields
    @OneToOne
    Account sender;
    String text;
    Long messageTimestamp;
}
