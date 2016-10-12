package com.tmholen.messagingserver;

import com.tmholen.messagingserver.controllers.AccountController;
import com.tmholen.messagingserver.controllers.AccountController.Account;
import com.tmholen.messagingserver.sessions.AccountSession;
import java.io.Serializable;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
public class ChatService {

    @EJB
    private AccountSession accountSession;

    @Inject
    AccountController accountController;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("accounts")
    public List<Account> getAccounts() {

        return accountController.getAccounts();
    }

//    @GET
//    @Path("messages")
//    public List<Message> getMessage() {
//        
//    }
//
//    @GET
//    @Path("conversations")
//    public Conversation getConversation() {
//        
//    }
    @Path("testdata")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Account insertTestdata() {
        accountSession.insertTestAccounts();
        return new AccountController.Account();
    }
    
    

}
