package pl.vinyl.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;
import pl.vinyl.domain.service.VinylService;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class VinylCreateView implements Serializable {

    @Inject
    private VinylService vinylService;

    @Inject
    private ArtistService artistService;

    @Getter
    @Setter
    private ArtistId artistId;

    @Getter
    @Setter
    private VinylEntity vinyl;

    @Getter
    private List<ArtistEntity> artists;

    public void init() {
        this.vinyl = new VinylEntity();
        this.vinyl.setId(VinylId.of(UUID.randomUUID()));
        this.artists = artistService.findAllArtists();

        if (this.artistId != null) {
            artistService.findArtist(this.artistId).ifPresent(vinyl::setArtist);
        }
    }

    public VinylCondition[] getConditions() {
        return VinylCondition.values();
    }

    public String save() {
        vinylService.save(this.vinyl);
        return "/artist/artist_view.xhtml?faces-redirect=true&id=" + vinyl.getArtist().getId().getId();
    }
}