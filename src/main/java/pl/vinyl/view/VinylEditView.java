package pl.vinyl.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistance.entity.ArtistEntity;
import pl.vinyl.domain.service.VinylService;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class VinylEditView implements Serializable {

    @Inject
    private VinylService vinylService;

    @Inject
    private ArtistService artistService;

    @Getter
    @Setter
    private VinylId id;

    @Getter
    @Setter
    private VinylEntity vinyl;

    @Getter
    private List<ArtistEntity> artists;

    public void init() throws IOException {
        Optional<VinylEntity> found = vinylService.findVinyl(id);
        if (found.isPresent()) {
            this.vinyl = found.get();
            this.artists = artistService.findAllArtists();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Nie znaleziono płyty");
        }
    }

    public VinylCondition[] getConditions() {
        return VinylCondition.values();
    }

    public String save() {
        vinylService.update(this.vinyl);
        return "/artist/artist_view.xhtml?faces-redirect=true&id=" + vinyl.getArtist().getId().getId();
    }
}