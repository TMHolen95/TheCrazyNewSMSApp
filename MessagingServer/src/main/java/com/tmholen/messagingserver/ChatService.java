package com.tmholen.messagingserver;


import com.tmholen.messagingserver.controllers.AccountController;
import com.tmholen.messagingserver.controllers.AccountController.Account;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@Stateless
@Path("chat")
@Produces(MediaType.APPLICATION_XML)
public class ChatService {
    @Inject
    AccountController accountController;
    
    @GET
    @Produces(value = MediaType.APPLICATION_XML)
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
    

    

}


