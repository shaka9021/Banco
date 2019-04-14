package com.uniremington.bank.service;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("ledger")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"EMPLOYEE"})
public class LedgerService {

    @Inject
    private LedgerBusiness business;

    @GET
    @Path("/{uid}")
    public Response balance(
        @NotNull @Size(min = 5) @Pattern(regexp = "[a-z0-9]*") @PathParam("uid") String uid) {

        try {
            double balance = business.balance(uid);
            return Response.ok(new Double(balance)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT).build();
        }

    }

    @POST
    @Path("/record/{uid}/{quantity}")
    public Response record(
        @NotNull @Size(min = 5) @Pattern(regexp = "[a-z0-9]*") @PathParam("uid") String uid,
        @Min(1) @PathParam("quantity") double quantity) {
        try {
            return Response.ok(business.record(uid, quantity)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/withdrawal/{uid}/{quantity}")
    public Response withdrawal(
        @NotNull @Size(min = 5) @Pattern(regexp = "[a-z0-9]*") @PathParam("uid") String uid,
        @Min(1) @PathParam("quantity") double quantity) {

        try {
            return Response.ok(business.withdrawal(uid, quantity)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT).build();
        }

    }

    @POST
    @Path("/transfer/{source}/{target}/{quantity}")
    public Response transfer(
        @NotNull @Size(min = 5) @Pattern(regexp = "[a-z0-9]*") @PathParam("source") String source,
        @NotNull @Size(min = 5) @Pattern(regexp = "[a-z0-9]*") @PathParam("target") String target,
        @Min(1) @PathParam("quantity") double quantity) {

        try {
            return Response.ok(business.transfer(source, target, quantity)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT).build();
        }

    }
}
