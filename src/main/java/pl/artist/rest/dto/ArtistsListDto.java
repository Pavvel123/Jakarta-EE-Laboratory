package pl.artist.rest.dto;

import java.util.Collection;
import java.util.List;

import pl.artist.persistence.entity.ArtistEntity;

public record ArtistsListDto(
    List<ArtistReadDto> artists
) {
    public static ArtistsListDto fromEntities(Collection<ArtistEntity> entities) {
        return new ArtistsListDto(
            entities.stream()
                    .map(ArtistReadDto::fromEntity)
                    .toList()
        );
    }
}