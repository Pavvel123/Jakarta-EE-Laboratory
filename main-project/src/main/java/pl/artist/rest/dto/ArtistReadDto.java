package pl.artist.rest.dto;

import java.util.UUID;

import pl.artist.persistence.entity.ArtistEntity;

public record ArtistReadDto(
    UUID id,
    String name,
    Integer formationYear,
    Boolean isActive
) {
    public static ArtistReadDto fromEntity(ArtistEntity entity) {
        return new ArtistReadDto(
            entity.getId().getId(),
            entity.getName(),
            entity.getFormationYear(),
            entity.getIsActive()
        );
    }
}