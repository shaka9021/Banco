package com.uniremington.bank.service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ledger")
@Produces(MediaType.APPLICATION_JSON)
public class LedgerService {

    @Inject
    private LedgerBusiness business;

    @GET
    @Path("/{uid}")
    public Response balance(
        @NotNull @Size(min = 5) @Pattern(regexp = "[a-z0-9]*") @PathParam("uid") String uid) {

        double balance = business.balance(uid);

        return Response.ok(new Double(balance)).build();
    }
}
