/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.sessions;

import com.tmholen.messagingserver.entities.Account;
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
@Path("accountmanagement")
public class AccountSession {

    @PersistenceContext
    EntityManager em;

    @GET
    public List<Account> getAllAccounts() {
        return em.createQuery("SELECT a from Account a").getResultList();
    }

    @GET
    public Account getAccountById(Long accId) {
        return (Account) em.createQuery("SELECT a from Account a WHERE a.id = :accId").setParameter("accId", accId).getSingleResult();
    }

    @GET
    public Account getAccountByNumber(String number) {
        return (Account) em.createQuery("SELECT a from Account a WHERE a.number = :number").setParameter("number", number).getSingleResult();
    }
    
    public boolean createAccount(Account account) {
        em.persist(account);
        return true;
    }

}
