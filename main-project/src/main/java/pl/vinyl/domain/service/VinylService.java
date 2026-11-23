package pl.vinyl.domain.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import pl.artist.persistence.entity.ArtistEntity;
import pl.core.security.exception.ForbiddenException;
import pl.core.security.exception.UnauthorizedException;
import pl.user.domain.model.UserRoles;
import pl.user.persistence.entity.UserEntity;
import pl.user.persistence.repository.UserRepository;
import pl.vinyl.persistence.entity.VinylEntity;
import pl.vinyl.persistence.id.VinylId;
import pl.vinyl.persistence.repository.VinylRepository;

@ApplicationScoped
public class VinylService {

    @Inject
    private VinylRepository vinylRepository;
    
    @Inject
    private UserRepository userRepository;

    @Inject
    private SecurityContext securityContext;

    public Optional<VinylEntity> findVinyl(VinylId id) {
        UserEntity caller = getAuthenticatedUser();
        Optional<VinylEntity> vinylOpt = vinylRepository.findById(id);

        if (vinylOpt.isPresent()) {
            checkOwnershipOrAdmin(vinylOpt.get(), caller);
        }
        return vinylOpt;
    }

    // public List<VinylEntity> findAllVinyls() {
    //     return vinylRepository.findAll();
    // }

    public List<VinylEntity> findByArtist(ArtistEntity artist) {
        UserEntity caller = getAuthenticatedUser();
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return vinylRepository.findByArtist(artist);
        }
        return vinylRepository.findByArtistAndUser(artist, caller);
    }

    public void save(VinylEntity vinyl) {
        vinylRepository.save(vinyl);
    }

    public void update(VinylEntity vinyl) {
        vinylRepository.update(vinyl);
    }

    public void delete(VinylId id) {
        vinylRepository.delete(id);
    }

    private UserEntity getAuthenticatedUser() {
        if (securityContext.getCallerPrincipal() == null) {
            throw new UnauthorizedException("Brak zalogowanego użytkownika");
        }
        String username = securityContext.getCallerPrincipal().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Nie znaleziono użytkownika: " + username));
    }

    private void checkOwnershipOrAdmin(VinylEntity vinyl, UserEntity caller) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (vinyl.getUser() == null || !vinyl.getUser().equals(caller)) {
            throw new ForbiddenException("Brak uprawnień do elementu innego użytkownika");
        }
    }
}
