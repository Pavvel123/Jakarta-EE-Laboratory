package pl.user.rest.dto;

import pl.user.persistence.entity.UserEntity;
import java.util.List;
import java.util.UUID;

public record UserReadDto(
    UUID id,
    String username,
    String email,
    List<String> roles
) {
    public static UserReadDto fromEntity(UserEntity entity) {
        return new UserReadDto(
            entity.getId().getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getRoles()
        );
    }
}