package com.uniremington.bank.service;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(value = Transactional.TxType.REQUIRED,
    rollbackOn = {IllegalArgumentException.class})
public class ClientBusiness {

    @Inject
    private EntityManager em;

    @Inject
    private LedgerBusiness ledger;

    public List<ClientDTO> list() {
        return em.createQuery("select c from ClientDTO as c",
            ClientDTO.class).getResultList();
    }

    public Optional<ClientDTO> get(String uid) {
        return Optional.ofNullable(em.find(ClientDTO.class, uid));
    }

    public ClientDTO save(ClientDTO client) {

        Optional<ClientDTO> saved = get(client.getUid());

        if (saved.isPresent()) {
            throw new IllegalArgumentException("the client exists");
        }

        em.persist(client);

        return client;

    }

    public Optional<ClientDTO> update(ClientDTO client) {

        return get(client.getUid()).map(c -> {
            c.setName(client.getName());
            em.merge(c);
            return c;
        });

    }

    public Optional<ClientDTO> delete(String uid) {

        return get(uid)
            .map(saved -> {
                if (ledger.balance(uid) > 0) {
                    throw new IllegalArgumentException("the balance is greater than 0");
                }
                em.remove(saved);
                return saved;
            });

    }

}
