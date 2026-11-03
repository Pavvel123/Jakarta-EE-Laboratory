package pl.artist.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistance.entity.ArtistEntity;
import pl.artist.persistance.id.ArtistId;
import pl.vinyl.domain.service.VinylService;
import pl.vinyl.persistence.entity.VinylEntity;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class ArtistView implements Serializable {

    @Inject
    private ArtistService artistService;

    @Inject
    private VinylService vinylService;

    @Getter
    @Setter
    private ArtistId id;

    @Getter
    private ArtistEntity artist;

    @Getter
    private List<VinylEntity> vinyls;

    public void init() throws IOException {
        Optional<ArtistEntity> found = artistService.findArtist(id);
        if (found.isPresent()) {
            this.artist = found.get();
            this.vinyls = vinylService.findByArtist(this.artist);
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Nie znaleziono artysty");
        }
    }

    public String deleteVinyl(VinylEntity vinyl) {
        vinylService.delete(vinyl.getId());
        this.vinyls = vinylService.findByArtist(this.artist);
        return null;
    }
}