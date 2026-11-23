package pl.artist.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ArtistRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public void save(ArtistEntity user) {
        em.persist(user);
    }

    @Transactional
    public Optional<ArtistEntity> findById(ArtistId id) {
        return Optional.ofNullable(em.find(ArtistEntity.class, id));
    }

    @Transactional
    public List<ArtistEntity> findAll() {
        return em.createQuery("SELECT a FROM ArtistEntity a", ArtistEntity.class).getResultList();
    }

    @Transactional
    public void update(ArtistEntity artist) {
        em.merge(artist);
    }

    @Transactional
    public void delete(ArtistId id) {
        ArtistEntity artist = em.find(ArtistEntity.class, id);
        if (artist != null) {
            em.remove(artist);
        }
    }
}