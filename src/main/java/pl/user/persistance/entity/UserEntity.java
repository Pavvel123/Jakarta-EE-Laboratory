package pl.user.persistance.entity;

import pl.user.persistance.id.UserId;
// import pl.vinyl.persistence.entity.VinylEntity;

import java.time.LocalDateTime;
// import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity
{
    @EmbeddedId
    private UserId id;

    private String login;

    private String email;

    private LocalDateTime accountCreatedAt;

    // private List<VinylEntity> collection;

    public UserEntity(UserId id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.accountCreatedAt = LocalDateTime.now();
    }
}