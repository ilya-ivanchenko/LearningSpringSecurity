package epam.by.ivanchenko.config;

import epam.by.ivanchenko.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {        // extends

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailService) {
        this.personDetailsService = personDetailService;
    }

    // Настройка аутентификации
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);                                       // Провайдер
    }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return NoOpPasswordEncoder.getInstance();                                          // Временно! Пароль не шифруется
            // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }


}
