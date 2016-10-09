/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver;


import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
import com.tmholen.messagingserver.entities.Message;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
@Stateless
@Path("chat")
@Produces(MediaType.APPLICATION_XML)
public class ChatService {
    
    @PersistenceContext
    EntityManager em;
    
    public boolean createAccount(Account account){
        em.persist(account);
        return true;
    }
    
    public boolean createMessage(Message message){
        em.persist(message);
        return true;
    }
    
    public boolean createConversation(Conversation conversation){
        em.persist(conversation);
        return true;
    }
    
//    @GET
//    @Path("accounts")
//    public List<Account> getUsers() {
//        return Arrays.asList(a1,a2);
//    }
//
//    
//    @GET
//    @Path("messages")
//    public List<Message> getMessage() {
//        return Arrays.asList(m1,m2);
//    }
//
//    @GET
//    @Path("conversations")
//    public Conversation getConversation() {
//        Conversation conversation = new Conversation(1L, Arrays.asList(a1,a2), Arrays.asList(m1,m2));
//        return conversation;
//    }
    

    

}


