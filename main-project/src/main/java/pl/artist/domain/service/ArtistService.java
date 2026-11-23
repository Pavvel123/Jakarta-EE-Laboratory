package pl.artist.domain.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import pl.artist.persistence.entity.ArtistEntity;
import pl.artist.persistence.id.ArtistId;
import pl.artist.persistence.repository.ArtistRepository;
import pl.core.security.exception.ForbiddenException;
import pl.core.security.exception.UnauthorizedException;
import pl.user.domain.model.UserRoles;

@ApplicationScoped
public class ArtistService {

    @Inject
    private ArtistRepository repository;
    
    @Inject
    private SecurityContext securityContext;

    public Optional<ArtistEntity> findArtist(ArtistId id) {
        requireAuthenticated();
        return repository.findById(id);
    }

    public List<ArtistEntity> findAllArtists() {
        requireAuthenticated();
        return repository.findAll();
    }

    public void save(ArtistEntity artist) {
        requireAdmin();
        repository.save(artist);
    }

    public void update(ArtistEntity artist) {
        requireAdmin();
        repository.update(artist);
    }

    public void delete(ArtistId id) {
        requireAdmin();
        repository.delete(id);
    }
    
    // Metoda pomocnicza dla DataInitializera (wykonywana przy starcie bez kontekstu HTTP)
    // public void saveInitial(ArtistEntity artist) {
    //     repository.save(artist);
    // }


    private void requireAuthenticated() {
        if (securityContext.getCallerPrincipal() == null) {
            throw new UnauthorizedException("Wymagane uwierzytelnienie");
        }
    }

    private void requireAdmin() {
        requireAuthenticated();
        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            throw new ForbiddenException("Wymagana rola ADMIN");
        }
    }
}
