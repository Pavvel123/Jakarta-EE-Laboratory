package pl.vinyl.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pl.artist.persistence.entity.ArtistEntity;
import pl.user.persistence.entity.UserEntity;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VinylRepository {

    @Inject
    private EntityManager em;

    public void save(VinylEntity vinyl) {
        em.getTransaction().begin();

        if (vinyl.getArtist() != null) {
            ArtistEntity managedArtist = em.find(ArtistEntity.class, vinyl.getArtist().getId());
            if (managedArtist != null) {
                vinyl.setArtist(managedArtist);
                if (!managedArtist.getRecords().contains(vinyl)) {
                    managedArtist.getRecords().add(vinyl);
                }
            }
        }

        if (vinyl.getUser() != null) {
            UserEntity managedUser = em.find(UserEntity.class, vinyl.getUser().getId());
            if (managedUser != null) {
                vinyl.setUser(managedUser);
                if (!managedUser.getRecords().contains(vinyl)) {
                    managedUser.getRecords().add(vinyl);
                }
            }
        }

        em.persist(vinyl);
        em.getTransaction().commit();
    }

    public Optional<VinylEntity> findById(VinylId id) {
        return Optional.ofNullable(em.find(VinylEntity.class, id));
    }

    public List<VinylEntity> findAll() {
        return em.createQuery("SELECT v FROM VinylEntity v", VinylEntity.class).getResultList();
    }

    public List<VinylEntity> findByArtist(ArtistEntity artist) {
    return em.createQuery("SELECT v FROM VinylEntity v WHERE v.artist = :artist", VinylEntity.class)
             .setParameter("artist", artist)
             .getResultList();
}

    public void update(VinylEntity vinyl) {
        em.getTransaction().begin();

        VinylEntity existing = em.find(VinylEntity.class, vinyl.getId());
        if (existing != null) {
            ArtistEntity oldArtist = existing.getArtist();
            ArtistEntity newArtist = vinyl.getArtist() != null
                    ? em.find(ArtistEntity.class, vinyl.getArtist().getId())
                    : null;

            if (oldArtist != null && !oldArtist.equals(newArtist)) {
                oldArtist.getRecords().remove(existing);
            }
            if (newArtist != null && !newArtist.getRecords().contains(existing)) {
                newArtist.getRecords().add(existing);
            }
            vinyl.setArtist(newArtist);

            UserEntity oldUser = existing.getUser();
            UserEntity newUser = vinyl.getUser() != null
                    ? em.find(UserEntity.class, vinyl.getUser().getId())
                    : null;

            if (oldUser != null && !oldUser.equals(newUser)) {
                oldUser.getRecords().remove(existing);
            }
            if (newUser != null && !newUser.getRecords().contains(existing)) {
                newUser.getRecords().add(existing);
            }
            vinyl.setUser(newUser);
        }

        em.merge(vinyl);
        em.getTransaction().commit();
    }

    public void delete(VinylId id) {
        em.getTransaction().begin();

        VinylEntity vinyl = em.find(VinylEntity.class, id);
        if (vinyl != null) {
            if (vinyl.getArtist() != null) {
                vinyl.getArtist().getRecords().remove(vinyl);
                vinyl.setArtist(null);
            }
            if (vinyl.getUser() != null) {
                vinyl.getUser().getRecords().remove(vinyl);
                vinyl.setUser(null);
            }
            em.remove(vinyl);
        }

        em.getTransaction().commit();
    }
}