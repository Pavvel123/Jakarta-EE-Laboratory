package pl.artist.view;

import java.io.Serializable;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;

@Named
@ViewScoped
public class ArtistCreateView implements Serializable {
    
    @Inject
    private ArtistService artistService;

    @Getter
    @Setter
    private ArtistEntity artistEntity;

    @PostConstruct
    public void init()
    {
        this.artistEntity = new ArtistEntity();
        this.artistEntity.setId(ArtistId.of(UUID.randomUUID()));
        this.artistEntity.setIsActive(true);
    }

    public String save()
    {
        artistService.save(this.artistEntity);
        return "/artist/artist_list.xhtml?faces-redirect=true";
    }
}
