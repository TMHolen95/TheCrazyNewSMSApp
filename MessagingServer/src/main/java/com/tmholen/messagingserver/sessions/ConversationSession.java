/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.sessions;

import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
public class ConversationSession {
    @PersistenceContext
    EntityManager em;
    
    @GET
    public List<Conversation> getAllConversations() {
        return em.createQuery("SELECT c from Conversation c").getResultList();
    }
    
    public boolean createAccount(Conversation conversation){
        em.persist(conversation);
        return true;
    }
}
