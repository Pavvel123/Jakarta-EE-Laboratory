package pl.vinyl.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VinylRepository {

    @Inject
    private EntityManager em;

    public void save(VinylEntity user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public Optional<VinylEntity> findById(VinylId id) {
        return Optional.ofNullable(em.find(VinylEntity.class, id));
    }

    public List<VinylEntity> findAll() {
        return em.createQuery("SELECT v FROM VinylEntity v", VinylEntity.class).getResultList();
    }
}