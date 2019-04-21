package com.uniremington.bank.service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"EMPLOYEE"})
public class UserService {

    @POST
    @Path("login")
    public Response login() {
        return Response.noContent().build();
    }
}
