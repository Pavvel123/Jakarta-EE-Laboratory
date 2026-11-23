package pl.user.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
// import java.util.List;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "users")
public class UserEntity implements Serializable
{
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UserId id;

    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime accountCreatedAt;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "users__roles",
        joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<VinylEntity> records;

    public List<VinylEntity> getRecords() {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        return this.records;
    }

    public UserEntity(UserId id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountCreatedAt = LocalDateTime.now();
    }
}