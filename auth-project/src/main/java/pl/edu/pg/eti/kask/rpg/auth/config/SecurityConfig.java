package pl.edu.pg.eti.kask.rpg.auth.config;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class SecurityConfig {

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
		return (context) -> {
			Set<String> authorities = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities());
			/* Roles without ROLE_ prefix, in lower case, as expected by Jakarta Security clients (callerGroupsClaim). */
			Set<String> groups = authorities.stream()
					.filter(authority -> authority.startsWith("ROLE_"))
					.map(authority -> authority.substring("ROLE_".length()).toLowerCase(Locale.ROOT))
					.collect(Collectors.toSet());
			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
				context.getClaims().claims((claims) -> {
					claims.put("authorities", authorities);
					claims.put("groups", groups);
					/* Caller name as expected by MicroProfile JWT resource servers. */
					claims.put("upn", context.getPrincipal().getName());
				});
			} else if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
				context.getClaims().claims((claims) -> {
					claims.put("groups", groups);
				});
			}
		};
	}

}
