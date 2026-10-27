package pl.user.persistance.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pl.user.persistance.entity.UserEntity;
import pl.user.persistance.id.UserId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @Inject
    private EntityManager em;

    public void save(UserEntity user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public Optional<UserEntity> findById(UserId id) {
        return Optional.ofNullable(em.find(UserEntity.class, id));
    }

    public List<UserEntity> findAll() {
        return em.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
    }
}