package pl.user.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pl.user.persistence.entity.UserEntity;
import pl.user.persistence.id.UserId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public void save(UserEntity user) {
        em.persist(user);
    }

    @Transactional
    public Optional<UserEntity> findById(UserId id) {
        return Optional.ofNullable(em.find(UserEntity.class, id));
    }

    @Transactional
    public List<UserEntity> findAll() {
        return em.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
    }
}