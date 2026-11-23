package pl.vinyl.rest.dto;

import pl.vinyl.persistence.entity.VinylEntity;

import java.util.List;

public record VinylsListDto(
    List<VinylReadDto> vinyls
) {
    public static VinylsListDto fromEntities(List<VinylEntity> entities) {
        return new VinylsListDto(
            entities.stream()
                    .map(VinylReadDto::fromEntity)
                    .toList()
        );
    }
}