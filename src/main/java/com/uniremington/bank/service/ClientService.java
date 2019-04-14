package com.uniremington.bank.service;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"EMPLOYEE"})
public class ClientService {

    @Inject
    private ClientBusiness client;

    @GET
    public List<ClientDTO> list() {
        return client.list();
    }

    @GET
    @Path("/{uid}")
    public Response get(@PathParam("uid") String uid) {

        return client.get(uid)
            .map(saved -> {
                return Response.ok().entity(saved).build();
            }).orElseGet(
                () -> Response.status(Status.NOT_FOUND).build());

    }

    @POST
    public Response save(@Valid ClientDTO client) {

        try {
            return Response.ok().entity(this.client.save(client)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT).build();
        }

    }

    @PUT
    public Response update(@Valid ClientDTO client) {

        return this.client.update(client)
            .map(saved -> {
                return Response.ok().entity(saved).build();
            }).orElseGet(
                () -> Response.status(Status.NOT_FOUND).build());

    }

    @DELETE
    @Path("/{uid}")
    public Response delete(@PathParam("uid") String uid) {

        try {
            return client.delete(uid)
                .map(deleted -> Response.ok().entity(deleted).build())
                .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT).build();
        }

    }
}
