package pl.artist.rest.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistance.entity.ArtistEntity;
import pl.artist.persistance.id.ArtistId;
import pl.vinyl.domain.service.VinylService;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = {"/api/artists/*", "/api/vinyls/*"})
public class ArtistRestServlet extends HttpServlet {

    @Inject
    private ArtistService artistService;

    @Inject
    private VinylService vinylService;

    private Jsonb jsonb;

    @Override
    public void init() throws ServletException {
        this.jsonb = JsonbBuilder.create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();

        // GET /api/vinyls
        if ("/api/vinyls".equals(servletPath)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonb.toJson(vinylService.findAllVinyls()));
            return;
        }

        // Obsługa GET /api/artists
        if (pathInfo == null || "/".equals(pathInfo)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonb.toJson(artistService.findAllArtists()));
            return;
        }

        // Obsługa GET /api/artists/{id}
        try {
            ArtistId artistId = ArtistId.of(UUID.fromString(pathInfo.substring(1)));
            artistService.findArtist(artistId).ifPresentOrElse(
                artist -> {
                    try {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.getWriter().write(jsonb.toJson(artist));
                    } catch (IOException e) {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                },
                () -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
            );
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        // POST /api/artists
        if (pathInfo == null || "/".equals(pathInfo)) {
            ArtistEntity newArtist = jsonb.fromJson(req.getReader(), ArtistEntity.class);
            if (newArtist.getId() == null) {
                newArtist.setId(ArtistId.of(UUID.randomUUID()));
            }
            artistService.save(newArtist);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(jsonb.toJson(newArtist));
            return;
        }

        // POST /api/artists/{id}/vinyls
        String[] parts = pathInfo.split("/");
        if (parts.length == 3 && "vinyls".equals(parts[2])) {
            try {
                ArtistId artistId = ArtistId.of(UUID.fromString(parts[1]));
                var artistOpt = artistService.findArtist(artistId);

                if (artistOpt.isEmpty()) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                VinylEntity newVinyl = jsonb.fromJson(req.getReader(), VinylEntity.class);
                if (newVinyl.getId() == null) {
                    newVinyl.setId(VinylId.of(UUID.randomUUID()));
                }

                ArtistEntity artist = artistOpt.get();
                artist.getRecords().add(newVinyl);
                artistService.update(artist);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write(jsonb.toJson(newVinyl));
            } catch (IllegalArgumentException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}