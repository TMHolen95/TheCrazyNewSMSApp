/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver;

import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
import com.tmholen.messagingserver.entities.Message;
import com.tmholen.messagingserver.sessions.AccountSession;
import com.tmholen.messagingserver.sessions.ConversationSession;
import com.tmholen.messagingserver.sessions.MessageSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
public class Testdata {

    public Testdata(AccountSession as, MessageSession ms, ConversationSession cs) {
        
        as.createAccount(new Account("Tor-Martin Holen", "91367954", "Blank", "MightyPassword"));
        as.createAccount(new Account("Jeff Hudson", "41569822", "Blank", "1234"));
        as.createAccount(new Account("Barry Allan", "90874454", "Blank", "Pw"));
        
        cs.createConversation(new Conversation(as.getAccountByNumber("91367954")));
        
//        ms.createMessage(new Message(cs.getConversation(1L), as.getAccountByNumber("91367954"), "Hello"));
//        ms.createMessage(new Message(cs.getConversation(1L), as.getAccountByNumber("90874454"), "Hey, what's up?"));
 
//        cs.updateConversation(cs.getConversation(1L), as.getAllAccounts(), ms.getAllMessages());
    }
}
