package pl.user.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
// import java.util.List;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.user.persistence.id.UserId;
import pl.vinyl.persistence.entity.VinylEntity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "records")
public class UserEntity implements Serializable
{
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UserId id;

    private String login;

    private String email;

    private LocalDateTime accountCreatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<VinylEntity> records;

    public List<VinylEntity> getRecords() {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        return this.records;
    }

    public UserEntity(UserId id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.accountCreatedAt = LocalDateTime.now();
    }
}