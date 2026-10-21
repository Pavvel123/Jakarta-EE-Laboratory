package pl.vinyl.domain.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;
import pl.vinyl.persistence.repository.VinylRepository;

@ApplicationScoped
public class VinylService {

    @Inject
    private VinylRepository repository;

    public Optional<VinylEntity> findVinyl(VinylId id) {
        return repository.findById(id);
    }

    public List<VinylEntity> findAllVinyls() {
        return repository.findAll();
    }
}
