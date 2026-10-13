package pl.user.persistance.entity;

import pl.user.persistance.id.UserId;
import pl.vinyl.persistence.entity.VinylEntity;

import java.time.LocalDateTime;
import java.util.List;

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
public class UserEntity
{
    private UserId id;

    private String login;

    private String email;

    private LocalDateTime accountCreatedAt;

    private List<VinylEntity> collection;
}