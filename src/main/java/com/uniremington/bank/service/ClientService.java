package com.uniremington.bank.service;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("client")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.REQUIRED)
public class ClientService {

    @Inject
    private EntityManager em;

    @GET
    public List<ClientDTO> list() {
        return em.createQuery("select c from ClientDTO as c",
            ClientDTO.class).getResultList();
    }

    @GET
    @Path("/{uid}")
    public Response get(@PathParam("uid") String uid) {

        return Optional.ofNullable(em.find(ClientDTO.class, uid))
            .map(saved -> {
                return Response.ok().entity(saved).build();
            }).orElseGet(
                () -> Response.status(Status.NOT_FOUND).build());

    }

    @POST
    public Response save(@Valid ClientDTO client) {

        Optional<ClientDTO> saved = Optional.ofNullable(
            em.find(ClientDTO.class, client.getUid()));

        if (saved.isPresent()) {
            return Response.status(Status.CONFLICT).build();
        }

        em.persist(client);
        return Response.ok().entity(client).build();

    }

    @PUT
    public Response update(@Valid ClientDTO client) {

        return Optional.ofNullable(em.find(ClientDTO.class, client.getUid()))
            .map(saved -> {

                saved.setName(client.getName());
                em.merge(saved);

                return Response.ok().entity(client).build();
            }).orElseGet(
                () -> Response.status(Status.NOT_FOUND).build());

    }

    @DELETE
    @Path("/{uid}")
    public Response delete(@PathParam("uid") String uid) {

        return Optional.ofNullable(em.find(ClientDTO.class, uid))
            .map(saved -> {
                em.remove(saved);
                return Response.ok().entity(saved).build();
            }).orElseGet(
                () -> Response.status(Status.NOT_FOUND).build());

    }
}
