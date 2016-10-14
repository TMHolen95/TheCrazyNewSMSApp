/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.sessions;

import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
import com.tmholen.messagingserver.entities.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@Stateless
@Path("conversationmanagement")
public class ConversationSession {
    @PersistenceContext
    EntityManager em;
    
    @GET
    public List<Conversation> getAllConversations() {
        return em.createQuery("SELECT c from Conversation c").getResultList();
    }
    
    
    public Conversation getConversation(Long convId) {
        return (Conversation) em.createQuery("SELECT c from Conversation c WHERE c.id = :convId").setParameter("convId", convId).getSingleResult();
    }
    
    public boolean createConversation(Conversation conversation){
        em.persist(conversation);
        return true;
    }
    
    public void updateConversation(Conversation conversation, List<Account> res, List<Message> msg){
        Conversation conv = em.find(Conversation.class, conversation.getId());
        conv.setRecipients(res);
        conv.setMessages(msg);
    }
}
