package pl.vinyl.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.vinyl.domain.service.VinylService;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@Named
@ViewScoped
public class VinylView implements Serializable {

    @Inject
    private VinylService vinylService;

    @Getter
    @Setter
    private VinylId id;

    @Getter
    private VinylEntity vinyl;

    public void init() throws IOException {
        Optional<VinylEntity> found = vinylService.findVinyl(id);
        if (found.isPresent()) {
            this.vinyl = found.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Nie znaleziono płyty");
        }
    }
}