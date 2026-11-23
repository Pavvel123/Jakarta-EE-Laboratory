package pl.artist.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistence.entity.ArtistEntity;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ArtistListView implements Serializable {

    @Inject
    private ArtistService artistService;

    private List<ArtistEntity> artists;

    public List<ArtistEntity> getArtists() {
        if (artists == null) {
            artists = artistService.findAllArtists();
        }
        return artists;
    }

    public String deleteArtist(ArtistEntity artist) {
        artistService.delete(artist.getId());
        artists = null;
        return null;
    }
}