/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.controllers;

import com.tmholen.messagingserver.database.CreateAccount;
import com.tmholen.messagingserver.entities.Account;
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
@Named
@RequestScoped
public class AccountController {

    @Resource(name = "jdbc/chat")
    DataSource ds;
    CreateAccount ca = new CreateAccount("TMH", "91367954", "Blank");
    List<Account> accounts;

//    public List<Account> getAccounts() {
//        if (accounts == null) {
//            accounts = new ArrayList<>();
//
//            try (
//                    Connection c = ds.getConnection();
//                    Statement s = c.createStatement();) {
//                ResultSet rs = s.executeQuery("select account_id,name,phone from customer order by name");
//                while(rs.next()){
//                    //accounts.add(new Account(rs.get))
//                }
//            }catch(SQLException e){
//                
//            }
//        }
//
//    }
    
    @Data @AllArgsConstructor @NoArgsConstructor
    @XmlRootElement @XmlAccessorType(XmlAccessType.FIELD)
    public static class Account implements Serializable{
        long accountId;
        String accountName;
        String accountNumber;
        String accountImage;
    }
}
