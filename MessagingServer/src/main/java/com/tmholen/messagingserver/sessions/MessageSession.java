/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.sessions;

import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
public class MessageSession {

    @PersistenceContext
    EntityManager em;

    @GET
    public List<Message> getAllMessages() {
        return em.createQuery("SELECT m from Message m").getResultList();
    }

    public boolean createMessage(Message message) {
        em.persist(message);
        return true;
    }
}
