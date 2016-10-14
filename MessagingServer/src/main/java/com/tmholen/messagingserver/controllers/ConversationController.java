/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.controllers;


import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Message;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.ws.rs.core.GenericEntity;
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
@Named("conversationcontroller")
@RequestScoped
public class ConversationController {
        @Resource(name = "jdbc/chat")
    DataSource ds;
    List<Conversation> conversations;
    public List<Conversation> getConversations() {
        if (conversations == null) {
            conversations = new ArrayList<>();

            try (
                    Connection c = ds.getConnection();
                    Statement s = c.createStatement();) {

                ResultSet rs = s.executeQuery("SELECT * FROM CONVERSATION ORDER BY ID");
                while (rs.next()) {
                    conversations.add(new Conversation(rs.getLong("ID"), (GenericEntity<List<Account>>) rs.getObject("RECIPIENTS"), (GenericEntity<List<Message>>) rs.getObject("MESSAGES")));
                }

            } catch (SQLException e) {
                System.out.println(e.getSQLState());
            }
        }
        return conversations;
    }
    
    @Data @AllArgsConstructor @NoArgsConstructor
    @XmlRootElement @XmlAccessorType(XmlAccessType.FIELD)
    public static class Conversation implements Serializable{
        long id;
        GenericEntity<List<Account>> recipients;
        GenericEntity<List<Message>> messages;
    }
}
