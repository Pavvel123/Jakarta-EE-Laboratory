package pl.edu.pg.eti.kask.rpg.auth.config;

import org.intellij.lang.annotations.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class UserConfig {

    @Language("SQL")
    public static final String AUTHORITIES_BY_USERNAME_QUERY_STRING = "select u.username, 'ROLE_' || upper(r.role) from users u join users__roles r on r.id = u.id where u.username = ?";

    @Language("SQL")
    public static final String USERS_BY_USERNAME_QUERY_STRING = "select username, password, true from users where username = ?";

    /**
     * Users read from the database shared with simple-rpg-ee. Roles are mapped to Spring authorities (e.g. admin ->
     * ROLE_ADMIN).
     */
    @Bean
    public UserDetailsService users(DataSource dataSource) {
        JdbcDaoImpl users = new JdbcDaoImpl();
        users.setDataSource(dataSource);
        users.setUsersByUsernameQuery(USERS_BY_USERNAME_QUERY_STRING);
        users.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY_STRING);
        return users;
    }

    /**
     * Standard delegating encoder (needed for {noop} client secrets), with passwords without {id} prefix treated as
     * Jakarta Security PBKDF2 hashes.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder encoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        encoder.setDefaultPasswordEncoderForMatches(new JakartaPbkdf2PasswordEncoder());
        return encoder;
    }

}
