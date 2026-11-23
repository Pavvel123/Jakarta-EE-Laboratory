package pl.vinyl.rest.dto;

import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import java.time.LocalDate;
import java.util.UUID;

public record VinylReadDto(
    UUID id,
    String title,
    LocalDate releaseDate,
    Double price,
    VinylCondition condition,
    UUID artistId
) {
    public static VinylReadDto fromEntity(VinylEntity entity) {
        return new VinylReadDto(
            entity.getId().getId(),
            entity.getTitle(),
            entity.getReleaseDate(),
            entity.getPrice(),
            entity.getCondition(),
            entity.getArtist() != null ? entity.getArtist().getId().getId() : null
        );
    }
}