package pl.artist.persistance.id.converter;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import pl.artist.persistance.id.ArtistId;

import java.util.UUID;

@RequestScoped
@FacesConverter(forClass = ArtistId.class, managed = true)
public class ArtistIdConverter implements Converter<ArtistId> {

    @Override
    public ArtistId getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) return null;
        return ArtistId.of(UUID.fromString(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ArtistId value) {
        if (value == null || value.getId() == null) return "";
        return value.getId().toString();
    }
}