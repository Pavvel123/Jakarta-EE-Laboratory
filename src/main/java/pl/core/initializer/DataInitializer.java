package pl.core.initializer;

// import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;
import pl.artist.persistence.repository.ArtistRepository;
import pl.user.persistence.entity.UserEntity;
import pl.user.persistence.id.UserId;
import pl.user.persistence.repository.UserRepository;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.enumeration.VinylCondition;
import pl.vinyl.persistence.id.VinylId;

@ApplicationScoped
public class DataInitializer {

    public static final UUID FIXED_USER_ID = UUID.fromString("f70d3bc0-30e7-41eb-b402-7e9604fc20e4");
    public static final UUID FIXED_ARTIST_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID FIXED_ARTISTL_ID2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID FIXED_VINYL_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    @Inject
    private UserRepository userRepository;

    @Inject
    private ArtistRepository artistRepository;

    // @Transactional 
    @ActivateRequestContext
    public void init(@Observes @Initialized (ApplicationScoped.class) Object init) {

        UserEntity fixedUser = new UserEntity(
            UserId.of(FIXED_USER_ID),
            "tester_stały",
            "tester@example.com");
        userRepository.save(fixedUser);

        ArtistEntity fixedArtist = new ArtistEntity(
            ArtistId.of(FIXED_ARTIST_ID),
            "Nirvana",
            1990,
            true);
            
        ArtistEntity fixedArtist2 = new ArtistEntity(
            ArtistId.of(FIXED_ARTISTL_ID2),
            "Metallica",
            1981,
            true);

        VinylEntity fixedVinyl = new VinylEntity(
            VinylId.of(FIXED_VINYL_ID),
            "Nevermind",
            LocalDate.of(1991, 2, 15),
            99.99,
            VinylCondition.VERY_GOOD,
            fixedArtist,
            fixedUser);

        VinylEntity fixedVinyl1 = new VinylEntity(
            VinylId.of(UUID.randomUUID()),
            "In Utero",
            LocalDate.of(1993, 9, 21),
            79.99,
            VinylCondition.GOOD,
            fixedArtist,
            fixedUser);

        VinylEntity fixedVinyl2 = new VinylEntity(
            VinylId.of(UUID.randomUUID()),
            "Master of Puppets",
            LocalDate.of(1986, 3, 3),
            89.99,
            VinylCondition.GOOD,
            fixedArtist2,
            fixedUser);
        
        fixedArtist.setRecords(new java.util.ArrayList<>(List.of(fixedVinyl, fixedVinyl1)));
        fixedArtist2.setRecords(new java.util.ArrayList<>(List.of(fixedVinyl2)));
        artistRepository.save(fixedArtist);
        artistRepository.save(fixedArtist2);
        
        System.out.println("Utworzono stałą kategorię (Artysta). ID: " + FIXED_ARTIST_ID);
        System.out.println("Utworzono stały element (Płyta). ID: " + FIXED_VINYL_ID);

        for (int i = 1; i <= 3; i++) {
            UserEntity user = new UserEntity(
                UserId.of(UUID.randomUUID()),
                "user" + i,
                "user" + i + "@example.com");
            userRepository.save(user);
        }

        ArtistEntity artist = new ArtistEntity(
            ArtistId.of(UUID.randomUUID()),
            "Pink Floyd",
            1965,
            true);

        VinylEntity vinyl1 = new VinylEntity(
            VinylId.of(UUID.randomUUID()),
            "The Dark Side of the Moon",
            LocalDate.of(1973, 3, 1),
            120.0,
            VinylCondition.VERY_GOOD,
            artist,
            fixedUser);

        VinylEntity vinyl2 = new VinylEntity(
            VinylId.of(UUID.randomUUID()),
            "The Wall",
            LocalDate.of(1979, 3, 30),
            150.0,
            VinylCondition.VERY_GOOD,
            artist,
            fixedUser);

        artist.setRecords(new ArrayList<>(List.of(vinyl1)));
        artist.setRecords(new ArrayList<>(List.of(vinyl2)));
        artistRepository.save(artist);
    }
}