package com.uniremington.bank.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerFactory {

    @Produces
    @PersistenceContext
    private EntityManager em;

}
