package pl.artist.persistance.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pl.artist.persistance.entity.ArtistEntity;
import pl.artist.persistance.id.ArtistId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ArtistRepository {

    @Inject
    private EntityManager em;

    public void save(ArtistEntity user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public Optional<ArtistEntity> findById(ArtistId id) {
        return Optional.ofNullable(em.find(ArtistEntity.class, id));
    }

    public List<ArtistEntity> findAll() {
        return em.createQuery("SELECT a FROM ArtistEntity a", ArtistEntity.class).getResultList();
    }

    public void update(ArtistEntity artist) {
        em.getTransaction().begin();
        em.merge(artist);
        em.getTransaction().commit();
    }
}