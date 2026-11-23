package pl.core.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String ISSUER = "http://localhost:8089";
    private static final String JWKS_URI = ISSUER + "/oauth2/jwks";

    private ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

    @PostConstruct
    public void init() {
        try {
            JWKSource<SecurityContext> keySource = JWKSourceBuilder
                    .create(URI.create(JWKS_URI).toURL())
                    .build();

            jwtProcessor = new DefaultJWTProcessor<>();
            JWSKeySelector<SecurityContext> keySelector =
                    new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySource);
            jwtProcessor.setJWSKeySelector(keySelector);

            jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier<>(
                    new JWTClaimsSet.Builder().issuer(ISSUER).build(),
                    Set.of("sub", "exp")
            ));
        } catch (Exception e) {
            throw new IllegalStateException("Nie udało się zainicjalizować weryfikatora JWT", e);
        }
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request,
                                                HttpServletResponse response,
                                                HttpMessageContext httpMessageContext) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            try {
                JWTClaimsSet claims = jwtProcessor.process(token, null);

                // SecurityConfig ustawia nazwę użytkownika w "upn" (oraz standardowo w "sub")
                String username = claims.getStringClaim("upn");
                if (username == null || username.isBlank()) {
                    username = claims.getSubject();
                }

                // SecurityConfig ustawia role małymi literami w claimie "groups"
                List<String> groups = claims.getStringListClaim("groups");
                Set<String> roles = groups != null ? new HashSet<>(groups) : Set.of();

                if (username != null) {
                    return httpMessageContext.notifyContainerAboutLogin(username, roles);
                }
            } catch (Exception e) {
                return httpMessageContext.responseUnauthorized();
            }
        }

        return httpMessageContext.doNothing();
    }
}