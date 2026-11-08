package pl.artist.domain.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;
import pl.artist.persistence.repository.ArtistRepository;

@ApplicationScoped
public class ArtistService {

    @Inject
    private ArtistRepository repository;

    public Optional<ArtistEntity> findArtist(ArtistId id) {
        return repository.findById(id);
    }

    public List<ArtistEntity> findAllArtists() {
        return repository.findAll();
    }

    public void save(ArtistEntity artist) {
        repository.save(artist);
    }

    public void update(ArtistEntity artist) {
        repository.update(artist);
    }

    public void delete(ArtistId id) {
        repository.delete(id);
    }
}
