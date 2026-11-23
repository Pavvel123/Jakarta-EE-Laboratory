package pl.vinyl.rest.controller;

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
import pl.vinyl.domain.service.VinylService;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;
import pl.vinyl.rest.dto.VinylReadDto;
import pl.vinyl.rest.dto.VinylWriteDto;
import pl.vinyl.rest.dto.VinylsListDto;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Path("/artists/{artistId}/vinyls")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VinylRestController {

    @Inject
    private ArtistService artistService;

    @Inject
    private VinylService vinylService;

    @Context
    private UriInfo uriInfo;

    // 1. Pobieranie wszystkich elementów danej kategorii
    @GET
    public Response getAllVinylsByArtist(@PathParam("artistId") UUID artistId) {
        Optional<ArtistEntity> artistOpt = artistService.findArtist(ArtistId.of(artistId));
        if (artistOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        VinylsListDto response = VinylsListDto.fromEntities(
                vinylService.findByArtist(artistOpt.get())
        );

        return Response.ok(response).build();
    }

    // 2. Pobieranie jednego elementu
    @GET
    @Path("/{vinylId}")
    public Response getVinyl(@PathParam("artistId") UUID artistId,
                             @PathParam("vinylId") UUID vinylId) {
        Optional<VinylEntity> vinylOpt = findVinylBelongingToArtist(artistId, vinylId);
        if (vinylOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(VinylReadDto.fromEntity(vinylOpt.get())).build();
    }

    // 3. Dodawanie nowego elementu
    @POST
    public Response createVinyl(@PathParam("artistId") UUID artistId,
                                VinylWriteDto request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<ArtistEntity> artistOpt = artistService.findArtist(ArtistId.of(artistId));
        if (artistOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        UUID newVinylId = UUID.randomUUID();
        VinylEntity vinyl = VinylEntity.builder()
                .id(VinylId.of(newVinylId))
                .title(request.title())
                .releaseDate(request.releaseDate())
                .price(request.price())
                .condition(request.condition())
                .artist(artistOpt.get())
                .build();



        if (vinyl.getPrice() < 0) {
            return Response.notModified().entity("Price cannot be negative").build();
        }
        vinylService.save(vinyl);

        URI location = uriInfo.getAbsolutePathBuilder().path(newVinylId.toString()).build();
        return Response.created(location).entity(VinylReadDto.fromEntity(vinyl)).build();
    }

    // 4. Modyfikowanie elementu
    @PUT
    @Path("/{vinylId}")
    public Response updateVinyl(@PathParam("artistId") UUID artistId,
                                @PathParam("vinylId") UUID vinylId,
                                VinylWriteDto request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<VinylEntity> vinylOpt = findVinylBelongingToArtist(artistId, vinylId);
        if (vinylOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        VinylEntity vinyl = vinylOpt.get();
        vinyl.setTitle(request.title());
        vinyl.setReleaseDate(request.releaseDate());
        vinyl.setPrice(request.price());
        vinyl.setCondition(request.condition());

        if (vinyl.getPrice() < 0) {
            return Response.notModified().entity("Price cannot be negative").build();
        }

        vinylService.update(vinyl);
        return Response.ok(VinylReadDto.fromEntity(vinyl)).build();
    }

    // 5. Usuwanie elementu
    @DELETE
    @Path("/{vinylId}")
    public Response deleteVinyl(@PathParam("artistId") UUID artistId,
                                @PathParam("vinylId") UUID vinylId) {
        Optional<VinylEntity> vinylOpt = findVinylBelongingToArtist(artistId, vinylId);
        if (vinylOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        vinylService.delete(VinylId.of(vinylId));
        return Response.noContent().build();
    }

    private Optional<VinylEntity> findVinylBelongingToArtist(UUID artistId, UUID vinylId) {
        if (artistService.findArtist(ArtistId.of(artistId)).isEmpty()) {
            return Optional.empty();
        }
        return vinylService.findVinyl(VinylId.of(vinylId))
                .filter(v -> v.getArtist() != null
                        && v.getArtist().getId() != null
                        && artistId.equals(v.getArtist().getId().getId()));
    }
}