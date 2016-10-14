/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.controllers;

import com.tmholen.messagingserver.entities.Account;
import com.tmholen.messagingserver.entities.Conversation;
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
@Named("messagecontroller")
@RequestScoped
public class MessageController {
    @Resource(name = "jdbc/chat")
    DataSource ds;
    List<Message> messages;
    public List<Message> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();

            try (
                    Connection c = ds.getConnection();
                    Statement s = c.createStatement();) {

                ResultSet rs = s.executeQuery("SELECT * FROM MESSAGE ORDER BY ID");
                while (rs.next()) {
                    messages.add(new Message(rs.getLong("ID"), rs.getString("TEXT"), 
                            rs.getLong("TIMESTAMPSENT"), rs.getLong("CONVERSATION_ID"), 
                            rs.getLong("SENDER_ID")));
                }

            } catch (SQLException e) {
                System.out.println(e.getSQLState());
            }
        }
        return messages;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Message implements Serializable {

        //Main content
        long id;
        String text;
        long timestampSent;
        
        //Forreign keys
        long conversationId;
        long senderId;
        
    }

}
