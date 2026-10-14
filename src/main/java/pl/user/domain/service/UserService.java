package pl.user.domain.service;

import java.util.List;
import java.util.Optional;

import pl.user.persistance.entity.UserEntity;
import pl.user.persistance.id.UserId;
import pl.user.persistance.repository.UserRepository;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public Optional<UserEntity> findUser(UserId id) {
        return repository.findById(id);
    }
    
    public List<UserEntity> findAllUsers() {
        return repository.findAll();
    }
}
