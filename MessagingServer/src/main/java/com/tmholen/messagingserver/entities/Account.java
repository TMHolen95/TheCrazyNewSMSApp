package com.tmholen.messagingserver.entities;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    String name;
    String number;
    String image;
    String password;

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

}
