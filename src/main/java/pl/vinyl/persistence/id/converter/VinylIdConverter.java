package pl.vinyl.persistence.id.converter;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import pl.vinyl.persistence.id.VinylId;

import java.util.UUID;

@RequestScoped
@FacesConverter(forClass = VinylId.class, managed = true)
public class VinylIdConverter implements Converter<VinylId> {

    @Override
    public VinylId getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) return null;
        return VinylId.of(UUID.fromString(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, VinylId value) {
        if (value == null || value.getId() == null) return "";
        return value.getId().toString();
    }
}