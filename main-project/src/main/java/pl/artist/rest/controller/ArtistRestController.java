package pl.artist.rest.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;
import pl.artist.rest.dto.ArtistReadDto;
import pl.artist.rest.dto.ArtistWriteDto;
import pl.artist.rest.dto.ArtistsListDto;

import java.net.URI;
import java.util.UUID;

@Path("/artists")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistRestController {

    @Inject
    private ArtistService artistService;

    @Context
    private UriInfo uriInfo;

    // 1. Pobieranie wszystkich kategorii
    @GET
    public Response getAllArtists() {
        ArtistsListDto response = ArtistsListDto.fromEntities(artistService.findAllArtists());
        return Response.ok(response).build();
    }

    // 2. Pobieranie jednej kategorii
    @GET
    @Path("/{id}")
    public Response getArtist(@PathParam("id") UUID id) {
        return artistService.findArtist(ArtistId.of(id))
                .map(ArtistReadDto::fromEntity)
                .map(dto -> Response.ok(dto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 3. Dodawanie nowej kategorii
    @POST
    public Response createArtist(ArtistWriteDto request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UUID newId = UUID.randomUUID();
        ArtistEntity artist = ArtistEntity.builder()
                .id(ArtistId.of(newId))
                .name(request.name())
                .formationYear(request.formationYear())
                .isActive(request.isActive())
                .build();

        artistService.save(artist);

        URI location = uriInfo.getAbsolutePathBuilder().path(newId.toString()).build();
        return Response.created(location).entity(ArtistReadDto.fromEntity(artist)).build();
    }

    // 4. Modyfikowanie istniejącej kategorii
    @PUT
    @Path("/{id}")
    public Response updateArtist(@PathParam("id") UUID id, ArtistWriteDto request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ArtistId artistId = ArtistId.of(id);
        var existingOpt = artistService.findArtist(artistId);

        if (existingOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ArtistEntity artist = existingOpt.get();
        artist.setName(request.name());
        artist.setFormationYear(request.formationYear());
        artist.setIsActive(request.isActive());

        artistService.update(artist);
        return Response.ok(ArtistReadDto.fromEntity(artist)).build();
    }

    // 5. Usuwanie kategorii
    @DELETE
    @Path("/{id}")
    public Response deleteArtist(@PathParam("id") UUID id) {
        ArtistId artistId = ArtistId.of(id);
        if (artistService.findArtist(artistId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        artistService.delete(artistId);
        return Response.noContent().build();
    }
}