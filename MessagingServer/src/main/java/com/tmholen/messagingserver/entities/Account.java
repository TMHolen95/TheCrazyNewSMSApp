package com.tmholen.messagingserver.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@SequenceGenerator(name = "seqaccount", initialValue = 1)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqaccount")
    Long id;

    String name;

    @Column(unique = true)
    String number;
    String image;
    String password;
    
    List<Long> conversationIds;

    public Account() {

    }

    public Account(String name, String number, String image, String password) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String accountName) {
        this.name = accountName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String accountNumber) {
        this.number = accountNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String accountImage) {
        this.image = accountImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getConversationIds() {
        return conversationIds;
    }

    public void setConversationIds(List<Long> conversationIds) {
        this.conversationIds = conversationIds;
    }

    public static class AccountAdapter extends XmlAdapter<Long, Account> {

        @Override
        public Account unmarshal(Long v) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Long marshal(Account v) throws Exception {
            return v != null ? v.getId() : null;
        }

    }
}
