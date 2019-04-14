package com.uniremington.bank.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(value = Transactional.TxType.REQUIRED,
    rollbackOn = {IllegalArgumentException.class})
public class LedgerBusiness {

    @Inject
    private EntityManager em;

    @Inject
    private ClientBusiness client;

    public double balance(String uid) {

        client.get(uid).orElseThrow(
            () -> new IllegalArgumentException("the client doesn't exist"));

        List<LedgerDTO> sources = em.createQuery("select l from LedgerDTO as l where l.source = :source",
            LedgerDTO.class).setParameter("source", uid).getResultList();

        List<LedgerDTO> targets = em.createQuery("select l from LedgerDTO as l where l.target = :target",
            LedgerDTO.class).setParameter("target", uid).getResultList();

        return targets.stream().mapToDouble(l -> l.getQuantity()).sum() -
            sources.stream().mapToDouble(l -> l.getQuantity()).sum();

    }

    public LedgerDTO record(String uid, double quantity) {

        client.get(uid).orElseThrow(
            () -> new IllegalArgumentException("the client doesn't exist"));

        LedgerDTO ledger = new LedgerDTO();

        ledger.setUuid(UUID.randomUUID().toString());
        ledger.setSource("");
        ledger.setTarget(uid);
        ledger.setQuantity(quantity);
        ledger.setCreated(new Date());

        em.persist(ledger);

        return ledger;
    }

    public LedgerDTO withdrawal(String uid, double quantity) {
        return transfer(uid, "", quantity);
    }

    public LedgerDTO transfer(String source, String target, double quantity) {

        // the target and source must be diferent

        if(source.equals(target)) {
            throw new IllegalArgumentException("the source and target must be equals");
        }

        // validate that the source and target exists!

        if(!"".equalsIgnoreCase(source)) {
            client.get(source).orElseThrow(
                () -> new IllegalArgumentException("the source doesn't exist"));
        }

        if(!"".equalsIgnoreCase(target)) {
            client.get(target).orElseThrow(
                () -> new IllegalArgumentException("the target doesn't exist"));
        }

        // validate that the balance of the source is greater than or equal to quantity

        double balance = balance(source);

        if (balance < quantity) {
            throw new IllegalArgumentException("insuficient balance!");
        }

        // execute the transfer

        LedgerDTO ledger = new LedgerDTO();

        ledger.setUuid(UUID.randomUUID().toString());
        ledger.setTarget(target);
        ledger.setSource(source);
        ledger.setQuantity(quantity);
        ledger.setCreated(new Date());

        em.persist(ledger);

        return ledger;

    }
}
