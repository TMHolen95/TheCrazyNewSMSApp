/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.database;

import com.tmholen.messagingserver.entities.Account;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
public class CreateAccount {
    public CreateAccount(String name, String number, String image){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Account a = new Account(name, number, image); 
        em.persist(a);
        em.getTransaction().commit();
    }
}
