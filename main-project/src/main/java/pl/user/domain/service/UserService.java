package pl.user.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import pl.user.domain.model.UserRoles;
import pl.user.persistence.entity.UserEntity;
import pl.user.persistence.id.UserId;
import pl.user.persistence.repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository repository;
    
    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public Optional<UserEntity> findUser(UserId id) {
        return repository.findById(id);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<UserEntity> findAllUsers() {
        return repository.findAll();
    }

    // Rejestracja nowego użytkownika (dostępna bez logowania)
    public UserEntity register(String username, String email, String rawPassword) {
        if (repository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Użytkownik o podanym username już istnieje");
        }

        UserEntity newUser = UserEntity.builder()
                .id(UserId.of(UUID.randomUUID()))
                .username(username)
                .email(email)
                .password(passwordHash.generate(rawPassword.toCharArray()))
                .roles(new ArrayList<>(List.of(UserRoles.USER)))
                .accountCreatedAt(LocalDateTime.now())
                .build();

        repository.save(newUser);
        return newUser;
    }

    public void saveInitialUser(UserEntity user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.save(user);
    }
}
