package epam.by.ivanchenko.config;

import epam.by.ivanchenko.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
@EnableWebSecurity
public class SecurityConfig {        // extends

    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    // Настройка аутентификации
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);                                       // провайдер
    }
}
