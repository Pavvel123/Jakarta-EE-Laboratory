package pl.user.domain.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.user.persistance.entity.UserEntity;
import pl.user.persistance.id.UserId;
import pl.user.persistance.repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository repository;

    public Optional<UserEntity> findUser(UserId id) {
        return repository.findById(id);
    }

    public List<UserEntity> findAllUsers() {
        return repository.findAll();
    }
}
