package pl.artist.rest.dto;

public record ArtistWriteDto(
    String name,
    Integer formationYear,
    Boolean isActive
) {
}