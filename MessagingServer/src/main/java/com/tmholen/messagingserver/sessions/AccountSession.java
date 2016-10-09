/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.sessions;

import com.tmholen.messagingserver.entities.Account;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
public class AccountSession {
    @PersistenceContext
    EntityManager em;
    
    @GET
    public List<Account> getAllAccounts() {
        return em.createQuery("SELECT a from Account a").getResultList();
    }
    
    public boolean createAccount(Account account){
        em.persist(account);
        return true;
    }
}
