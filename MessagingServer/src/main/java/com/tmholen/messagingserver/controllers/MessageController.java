/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.controllers;

import com.tmholen.messagingserver.entities.Account;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
public class MessageController {
    
    
    
     @Data @AllArgsConstructor @NoArgsConstructor
    @XmlRootElement @XmlAccessorType(XmlAccessType.FIELD)
    public static class Message implements Serializable{
        long messageId;
        long messageConversationId;
        Account messageSender;
        String messageText;
        long messageTimestamp;
    }   

}
