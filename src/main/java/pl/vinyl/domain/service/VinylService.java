package pl.vinyl.domain.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.artist.persistance.entity.ArtistEntity;
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

    public List<VinylEntity> findByArtist(ArtistEntity artist) {
        return repository.findByArtist(artist);
    }

    public void save(VinylEntity vinyl) {
        repository.save(vinyl);
    }

    public void update(VinylEntity vinyl) {
        repository.update(vinyl);
    }

    public void delete(VinylId id) {
        repository.delete(id);
    }
}
