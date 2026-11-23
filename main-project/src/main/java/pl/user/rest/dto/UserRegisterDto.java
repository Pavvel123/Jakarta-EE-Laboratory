package pl.user.rest.dto;

public record UserRegisterDto(
    String username,
    String email,
    String password
) {
}