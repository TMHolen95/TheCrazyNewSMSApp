/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmholen.messagingserver.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Tor-Martin Holen <tormartin.holen@gmail.com>
 */
@Entity
public class Contactlist implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne (optional = false, cascade = CascadeType.PERSIST)
    private Account owner;
    
    @OneToMany
    private List<Account> contacts;
    
    public Long getId() {
        return id;
    }

}
