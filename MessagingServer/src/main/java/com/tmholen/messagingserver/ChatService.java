package com.tmholen.messagingserver;

import com.sun.istack.internal.Nullable;
import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
import com.tmholen.messagingserver.entities.Message;
import java.util.Arrays;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public Account getAccountById(Long accountId) {
        return (com.tmholen.messagingserver.entities.Account) em.createQuery("SELECT a from Account a WHERE a.id = :accountId").setParameter("accountId", accountId).getSingleResult();
    }

    public Account getAccountByNumber(String number) {
        return (com.tmholen.messagingserver.entities.Account) em.createQuery("SELECT a from Account a WHERE a.number = :number").setParameter("number", number).getSingleResult();
    }

    public Account createAccount(Account account) {
        em.persist(account);
        return account;
    }

    public void addAccountConversation(Long accountId, Conversation conversation) {
        Account account = getAccountById(accountId);
        List<Conversation> conversations = account.getConversations();
        conversations.add(conversation);
        account.setConversations(conversations);
    }

    public void addAccountConversation(String accountNumber, Conversation conversation) {
        Account account = getAccountByNumber(accountNumber);
        List<Conversation> conversations = account.getConversations();
        conversations.add(conversation);
        account.setConversations(conversations);
    }

    public void addAccountConversation(Account account, Conversation conversation) {
        List<Conversation> conversations = account.getConversations();
        conversations.add(conversation);
        account.setConversations(conversations);
    }

    @GET
    @Path("messages")
    public List<Message> getAllMessages() {
        return em.createQuery("SELECT m from Message m").getResultList();
    }

    public Message getMessage(Long msgId) {
        return (Message) em.createQuery("SELECT m from Message m WHERE m.id = :msgId").setParameter("msgId", msgId).getSingleResult();
    }

    public Message createMessage(Message message) {
        em.persist(message);
        return message;
    }

    @GET
    @Path("conversations")
    public List<Conversation> getAllConversations() {
        return em.createQuery("SELECT c from Conversation c").getResultList();
    }

    public Conversation getConversation(Long convId) {
        return (Conversation) em.createQuery("SELECT c from Conversation c WHERE c.id = :convId").setParameter("convId", convId).getSingleResult();
    }

    public Conversation createConversation(Conversation conversation) {
        em.persist(conversation);
        return conversation;
    }

    public void updateConversation(Conversation conversation, @Nullable List<Account> newRecipients, @Nullable List<Message> newMessages) {
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
        createAccount(new Account("Tor-Martin Holen", "91367954", "Blank", "MightyPassword"));
        createAccount(new Account("Jeff Hudson", "41569822", "Blank", "1234"));
        createAccount(new Account("Barry Allan", "90874454", "Blank", "Pw"));

        createConversation(new Conversation());

        createMessage(new Message(getAllConversations().get(0).getId(), getAllAccounts().get(0).getId(), "Hello"));
        createMessage(new Message(getAllConversations().get(0).getId(), getAllAccounts().get(1).getId(), "Hey, whats up?"));

        updateConversation(getAllConversations().get(0), getAllAccounts(), getAllMessages());

        return new Account();
    }

    @GET
    @Path("testdata2")
    public Account insertTestdata2(){
        
        Message m = createMessage(new Message(getAllConversations().get(0).getId(), getAllAccounts().get(0).getId(), "Nothing much, just coding like a pro!"));
        updateConversation(getAllConversations().get(0), null, Arrays.asList(m));
        return new Account();
    }
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
