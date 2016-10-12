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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    public boolean createAccount(Account account) {
        em.persist(account);
        return true;
    }

    public void insertTestAccounts() {
        createAccount(new Account("Tor-Martin Holen", "91367954", "Blank", "MightyPassword"));
        createAccount(new Account("Jeff Hudson", "41569822", "Blank", "1234"));
        createAccount(new Account("Barry Allan", "90874454", "Blank", "Pw"));
    }
}
