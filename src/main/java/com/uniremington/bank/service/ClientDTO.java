package com.uniremington.bank.service;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "uid", length = 15)
    private String uid;

    @Column(name = "name", length = 50)
    private String name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
