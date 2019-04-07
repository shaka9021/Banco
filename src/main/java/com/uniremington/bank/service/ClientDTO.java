package com.uniremington.bank.service;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "client")
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 5)
    @Pattern(regexp = "[a-z0-9]*")
    @Column(name = "uid", length = 15)
    private String uid;

    @NotNull
    @Size(min = 5)
    @Pattern(regexp = "(\\w\\s?)*")
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
