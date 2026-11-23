package pl.user.rest.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pl.user.domain.service.UserService;
import pl.user.persistence.entity.UserEntity;
import pl.user.rest.dto.UserReadDto;
import pl.user.rest.dto.UserRegisterDto;

import java.net.URI;

@Path("/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRestController {

    @Inject
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    // Zadanie 1 i 4: Każdy nowy użytkownik może się zarejestrować (usługa niezabezpieczona)
    @POST
    @PermitAll
    public Response registerUser(UserRegisterDto request) {
        if (request == null || request.username() == null || request.password() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            UserEntity created = userService.register(request.username(), request.email(), request.password());
            URI location = uriInfo.getAbsolutePathBuilder().path(created.getId().getId().toString()).build();
            return Response.created(location).entity(UserReadDto.fromEntity(created)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
}