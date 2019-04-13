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

    public double balance(String uid) {

        List<LedgerDTO> sources = em.createQuery("select l from LedgerDTO as l where l.source = :source",
            LedgerDTO.class).setParameter("source", uid).getResultList();

        List<LedgerDTO> targets = em.createQuery("select l from LedgerDTO as l where l.target = :target",
            LedgerDTO.class).setParameter("target", uid).getResultList();

        return targets.stream().mapToDouble(l -> l.getQuantity()).sum() -
            sources.stream().mapToDouble(l -> l.getQuantity()).sum();

    }

    public LedgerDTO record(String uid, double quantity) {

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

        double balance = balance(source);

        if (balance < quantity) {
            throw new IllegalArgumentException("insuficient balance!");
        }

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
