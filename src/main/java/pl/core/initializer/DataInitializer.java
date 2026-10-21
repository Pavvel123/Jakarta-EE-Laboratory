package pl.core.initializer;

// import java.io.IOException;
import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
// import jakarta.transaction.Transactional;
// import jakarta.servlet.ServletContextEvent;
// import jakarta.servlet.ServletContextListener;
// import jakarta.servlet.annotation.WebListener;
import pl.artist.persistance.entity.ArtistEntity;
import pl.artist.persistance.id.ArtistId;
import pl.artist.persistance.repository.ArtistRepository;
// import pl.avatar.domain.service.AvatarService;
// import pl.user.domain.service.UserService;
import pl.user.persistance.entity.UserEntity;
import pl.user.persistance.id.UserId;
import pl.user.persistance.repository.UserRepository;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

@ApplicationScoped
public class DataInitializer {

    public static final UUID FIXED_USER_ID = UUID.fromString("f70d3bc0-30e7-41eb-b402-7e9604fc20e4");
    public static final UUID FIXED_ARTIST_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID FIXED_VINYL_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Inject
    private UserRepository userRepository;

    @Inject
    private ArtistRepository artistRepository;

    // @Transactional 
    @ActivateRequestContext
    public void init(@Observes @Initialized (ApplicationScoped.class) Object init) {

        UserEntity fixedUser = new UserEntity(UserId.of(FIXED_USER_ID), "tester_stały", "tester@example.com");
        userRepository.save(fixedUser);

        ArtistEntity fixedArtist = new ArtistEntity(ArtistId.of(FIXED_ARTIST_ID), "Nirvana", 1990, true);
        VinylEntity fixedVinyl = new VinylEntity(VinylId.of(FIXED_VINYL_ID), "Nevermind", LocalDate.of(1991, 2, 15), 99.99, VinylCondition.VERY_GOOD);
        
        fixedArtist.setRecords(new java.util.ArrayList<>(java.util.List.of(fixedVinyl)));
        artistRepository.save(fixedArtist);
        
        System.out.println("Utworzono stałą kategorię (Artysta). ID: " + FIXED_ARTIST_ID);
        System.out.println("Utworzono stały element (Płyta). ID: " + FIXED_VINYL_ID);

        for (int i = 1; i <= 3; i++) {
            UserEntity user = new UserEntity(UserId.of(UUID.randomUUID()), "user" + i, "user" + i + "@example.com");
            userRepository.save(user);
        }

        ArtistEntity artist = new ArtistEntity(ArtistId.of(UUID.randomUUID()), "Pink Floyd", 1965, true);
        VinylEntity vinyl1 = new VinylEntity(VinylId.of(UUID.randomUUID()), "The Dark Side of the Moon", LocalDate.of(1973, 3, 1), 120.0, VinylCondition.VERY_GOOD);
        VinylEntity vinyl2 = new VinylEntity(VinylId.of(UUID.randomUUID()), "The Wall", LocalDate.of(1979, 3, 30), 150.0, VinylCondition.VERY_GOOD);
        
        artist.setRecords(new java.util.ArrayList<>(java.util.List.of(vinyl1)));
        artist.setRecords(new java.util.ArrayList<>(java.util.List.of(vinyl2)));
        artistRepository.save(artist);
    }
}