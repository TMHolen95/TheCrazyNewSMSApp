package com.tmholen.messagingserver;

import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
import com.tmholen.messagingserver.entities.Message;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@Stateless
@Produces(value = MediaType.APPLICATION_JSON)
@Path("chat")
public class ChatService {

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("accounts")
    public List<Account> getAllAccounts() {
        return em.createQuery("SELECT a from Account a").getResultList();
    }

    @GET
    @Path("accounts/{id}")
    public List<Account> getAccount(@PathParam("id") Long accountId) {
        return em.createQuery("SELECT a from Account a WHERE a.id = :accountId").setParameter("accountId", accountId).getResultList();
    }
    
    @GET
    @Path("login/{number}/{password}")
    public List<Account> getAccount(@PathParam("number") String accountNumber, @PathParam("password") String accountPassword) {
        Query query = em.createQuery("SELECT a from Account a WHERE a.number = :accountNumber AND a.password = :accountPassword");
        query.setParameter("accountNumber", accountNumber).setParameter("accountPassword", accountPassword);
        return query.getResultList();
    }

    public Account getAccountById(Long accountId) {
        return (Account) em.createQuery("SELECT a from Account a WHERE a.id = :accountId").setParameter("accountId", accountId).getSingleResult();
    }

    public Account getAccountByNumber(String number) {
        return (Account) em.createQuery("SELECT a from Account a WHERE a.number = :number").setParameter("number", number).getSingleResult();
    }

    public Account createTestAccount(Account account) {
        em.persist(account);
        return account;
    }

    public void addAccountConversation(Account account, Conversation conversation) {
        List<Long> conversationIds = account.getConversationIds();
        if (conversationIds == null) {
            conversationIds = new ArrayList<>();
        }
        conversationIds.add(conversation.getId());
        account.setConversationIds(conversationIds);
    }

    public void addAccountConversation(Long accountId, Long conversationId) {
        Account account = getAccountById(accountId);
        Conversation conversation = getConversationById(accountId);
        List<Long> conversationIds = account.getConversationIds();
        conversationIds.add(conversation.getId());
        account.setConversationIds(conversationIds);
    }

    @GET
    @Path("messages")
    public List<Message> getAllMessages() {
        return em.createQuery("SELECT m from Message m").getResultList();
    }

    public Message getMessage(Long msgId) {
        return (Message) em.createQuery("SELECT m from Message m WHERE m.id = :msgId").setParameter("msgId", msgId).getSingleResult();
    }

    public Message createTestMessage(Message message) {
        em.persist(message);
        return message;
    }

    @POST
    @Path("messages/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Message createMessage(Message message) {
        try {
            em.persist(message);
        } catch (NullPointerException e) {
            System.out.println("com.tmholen.messagingserver.ChatService.sendMessage()");
        }
        return message;
    }
    
    @POST
    @Path("conversations/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Conversation createConversation(Conversation conversation) {
        try {
            em.persist(conversation);
        } catch (NullPointerException e) {
            System.out.println("com.tmholen.messagingserver.ChatService.sendMessage()");
        }
        return conversation;
    }
    
    @POST
    @Path("accounts/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Account createAccount(Account account) {
        try {
            em.persist(account);
        } catch (NullPointerException e) {
            System.out.println("com.tmholen.messagingserver.ChatService.sendMessage()");
        }
        return account;
    }    

    @GET
    @Path("conversations")
    public List<Conversation> getAllConversations() {
        return em.createQuery("SELECT c from Conversation c").getResultList();
    }

    public Conversation getConversationById(Long convId) {
        return (Conversation) em.createQuery("SELECT c from Conversation c WHERE c.id = :convId").setParameter("convId", convId).getSingleResult();
    }

    public Conversation createTestConversation(Conversation conversation) {
        em.persist(conversation);
        return conversation;
    }

    public void updateConversation(Conversation conversation, List<Account> newRecipients, List<Message> newMessages) {
        List<Account> recipientsList = conversation.getRecipients();
        List<Message> messageList = conversation.getMessages();

        if (newRecipients != null) {
            for (int i = 0; i < newRecipients.size(); i++) {
                Account account = newRecipients.get(i);
                addAccountConversation(account, conversation);
                recipientsList.add(account);
            }
            conversation.setRecipients(recipientsList);
        }

        if (newMessages != null) {
            for (int i = 0; i < newMessages.size(); i++) {
                Message message = newMessages.get(i);
                messageList.add(message);
            }
            conversation.setMessages(messageList);
        }
    }

    @GET
    @Path("testdata1")
    public Account insertTestdata1() {
        createTestAccount(new Account("Tor-Martin Holen", "91367954", "", "1234"));
        createTestAccount(new Account("Oliver Queen", "91234561", "", "1234"));
        createTestAccount(new Account("Felicity Smokes", "91234562", "", "1234"));
        createTestAccount(new Account("Barry Allan", "91234563", "", "1234"));
        createTestAccount(new Account("Dr. Wells", "91234564", "", "1234"));
        createTestAccount(new Account("Sarah Lance", "91234565", "", "1234"));
        createTestAccount(new Account("Ray Palmer", "91234566", "", "1234"));
        createTestAccount(new Account("Kara Danvers", "91234567", "", "1234"));
        createTestAccount(new Account("Alex Danvers", "91234568", "", "1234"));
        
        

        
        createTestConversation(new Conversation());

        createTestMessage(new Message(getAllConversations().get(0).getId(), getAllAccounts().get(0).getId(), "Hello"));
        createTestMessage(new Message(getAllConversations().get(0).getId(), getAllAccounts().get(1).getId(), "Hey, whats up?"));

        updateConversation(getAllConversations().get(0), getAllAccounts(), getAllMessages());

        Message m = createTestMessage(new Message(getAllConversations().get(0).getId(), getAllAccounts().get(0).getId(), "Nothing much, just coding like a pro!"));
        updateConversation(getAllConversations().get(0), null, Arrays.asList(m));

        createTestConversation(new Conversation());
        updateConversation(getConversationById(2L), getAllAccounts(), null);
        
        return new Account();
    }

//    @GET
//    @Path("testdata2")
//    public Account insertTestdata2() {
//
//
//        return new Account();
//    }
//    
//    @GET
//    @Path("testdata3")
//    public Account insertTestdata3(){
//        updateConversation(getAllConversations().get(0), getAllAccounts(), getAllMessages());
//        
//        return new Account();
//    }
//        @Resource(name = "jdbc/chat")
//    DataSource ds;
//    List<Message> messages;
//    public List<Message> getMessages() {
//        if (messages == null) {
//            messages = new ArrayList<>();
//
//            try (
//                    Connection c = ds.getConnection();
//                    Statement s = c.createStatement();) {
//
//                ResultSet rs = s.executeQuery("SELECT * FROM MESSAGE ORDER BY ID");
//                while (rs.next()) {
//                    messages.add(new Message(rs.getLong("ID"), rs.getString("TEXT"), 
//                            rs.getLong("TIMESTAMPSENT"), rs.getLong("CONVERSATION_ID"), 
//                            rs.getLong("SENDER_ID")));
//                }
//
//            } catch (SQLException e) {
//                System.out.println(e.getSQLState());
//            }
//        }
//        return messages;
//    }
}
