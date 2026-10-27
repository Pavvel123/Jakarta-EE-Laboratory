package pl.vinyl.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// import pl.artist.persistance.entity.ArtistEntity;
// import pl.user.persistance.entity.UserEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinylEntity
{
    @EmbeddedId
    private VinylId id;

    private String title;

    private LocalDate releaseDate;

    private Double price;

    private VinylCondition condition;

    // private ArtistEntity artist;

    // private UserEntity owner;
}
