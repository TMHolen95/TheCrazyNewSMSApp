package com.tmholen.messagingserver;

import com.tmholen.messagingserver.controllers.AccountController;
import com.tmholen.messagingserver.controllers.AccountController.Account;
import com.tmholen.messagingserver.controllers.ConversationController;
import com.tmholen.messagingserver.controllers.ConversationController.Conversation;
import com.tmholen.messagingserver.controllers.MessageController;
import com.tmholen.messagingserver.controllers.MessageController.Message;
import com.tmholen.messagingserver.sessions.AccountSession;
import com.tmholen.messagingserver.sessions.ConversationSession;
import com.tmholen.messagingserver.sessions.MessageSession;
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
    @Produces(value = MediaType.APPLICATION_JSON)
@Path("chat")
public class ChatService {

    @EJB
    private AccountSession accountSession;
    @Inject
    private AccountController accountController;

    @GET
    @Path("accounts")
    public List<Account> getAccounts() {
        return accountController.getAccounts();
    }

    @EJB
    private MessageSession messageSession;
    @Inject
    private MessageController messageController;

    @GET
    @Path("messages")
    public List<Message> getMessages() {
        return messageController.getMessages();
    }

    @EJB
    private ConversationSession conversationSession;
    @Inject
    private ConversationController conversationController;

    @GET
    @Path("conversations")
    public List<Conversation> getConversations() {
        return conversationController.getConversations();
    }

    @Path("testdata")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Account insertTestdata() {
        Testdata t = new Testdata(accountSession, messageSession, conversationSession);
        return new AccountController.Account();
    }

}
