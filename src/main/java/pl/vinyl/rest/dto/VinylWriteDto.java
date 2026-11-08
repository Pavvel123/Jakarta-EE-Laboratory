package pl.vinyl.rest.dto;

import pl.vinyl.persistence.enumeration.VinylCondition;
import java.time.LocalDate;

public record VinylWriteDto(
    String title,
    LocalDate releaseDate,
    Double price,
    VinylCondition condition
) {
}