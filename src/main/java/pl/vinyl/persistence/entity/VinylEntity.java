package pl.vinyl.persistence.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.artist.persistance.entity.ArtistEntity;
import pl.user.persistance.entity.UserEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class VinylEntity
{
    private VinylId id;

    private String title;

    private LocalDate releaseDate;

    private Double price;

    private VinylCondition condition;

    private ArtistEntity artist;

    private UserEntity owner;
}
