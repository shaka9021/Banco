package com.uniremington.bank.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
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
}
