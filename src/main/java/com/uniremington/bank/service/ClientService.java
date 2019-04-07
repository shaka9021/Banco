package com.uniremington.bank.service;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("client")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.REQUIRED)
public class ClientService {

    @Inject
    private EntityManager em;

    @GET
    public List<ClientDTO> list() {

        ClientDTO c1 = new ClientDTO();
        c1.setUid("123654");
        c1.setName("John Doe");

        ClientDTO c2 = new ClientDTO();
        c2.setUid("78964");
        c2.setName("John Connor");

        return Arrays.asList(c1, c2);
    }

    @GET
    @Path("/{uid}")
    public ClientDTO get(@PathParam("uid") String uid) {
        ClientDTO client = new ClientDTO();

        client.setUid(uid);

        return client;
    }

    @POST
    public ClientDTO save(ClientDTO client) {
        em.persist(client);
        return client;
    }

    @PUT
    public ClientDTO update(ClientDTO client) {
        return client;
    }

    @DELETE
    @Path("/{uid}")
    public ClientDTO delete(@PathParam("uid") String uid) {
        ClientDTO client = new ClientDTO();

        client.setUid(uid);

        return client;
    }
}
