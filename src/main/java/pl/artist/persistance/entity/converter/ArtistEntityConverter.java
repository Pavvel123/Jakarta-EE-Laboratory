package pl.artist.persistance.entity.converter;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.artist.domain.service.ArtistService;
import pl.artist.persistance.entity.ArtistEntity;
import pl.artist.persistance.id.ArtistId;

import java.util.UUID;

@RequestScoped
@FacesConverter(forClass = ArtistEntity.class, managed = true)
public class ArtistEntityConverter implements Converter<ArtistEntity> {

    @Inject
    private ArtistService artistService;

    @Override
    public ArtistEntity getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return artistService.findArtist(ArtistId.of(UUID.fromString(value))).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ArtistEntity value) {
        if (value == null || value.getId() == null || value.getId().getId() == null) {
            return "";
        }
        return value.getId().getId().toString();
    }
}