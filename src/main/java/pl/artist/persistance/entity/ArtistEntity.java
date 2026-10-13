package pl.artist.persistance.entity;

import java.util.List;

import pl.artist.persistance.id.ArtistId;
import pl.vinyl.persistence.entity.VinylEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ArtistEntity
{
    private ArtistId id;

    private String name;

    private Integer formationYear;

    private Boolean isActive;

    private List<VinylEntity> records;
}
