package pl.vinyl.persistence.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.artist.persistence.entity.ArtistEntity;
// import pl.user.persistance.entity.UserEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VinylEntity implements Serializable
{
    @EmbeddedId
    @EqualsAndHashCode.Include
    private VinylId id;

    private String title;

    private LocalDate releaseDate;

    private Double price;

    private VinylCondition condition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private ArtistEntity artist;

    // private UserEntity owner;
}
