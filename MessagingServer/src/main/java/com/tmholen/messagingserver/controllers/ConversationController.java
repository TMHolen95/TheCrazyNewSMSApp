/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.controllers;


import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Message;
import java.io.Serializable;
import java.util.List;
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
public class ConversationController {
    
    
    @Data @AllArgsConstructor @NoArgsConstructor
    @XmlRootElement @XmlAccessorType(XmlAccessType.FIELD)
    public static class Conversation implements Serializable{
        long conversationId;
        List<Account> conversationAccountRecipients;
        List<Message> conversationMessage;
    }
}
