package epam.by.ivanchenko.config;

import epam.by.ivanchenko.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailService;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // конфиг. Spring Security
        // конфиг. авторизации

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http
                .csrf().disable()
                .authorizeHttpRequests()
                // Порядок правил важен, проверка идет сверху вниз. Более специфичные правила идут первыми
                //.requestMatchers("/admin").hasRole("ADMIN")                                                 // На страницу /admin доступ только с ролью ADMIN. Spring понимает, что ADMIN - это роль  ROLE_ADMIN
                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()                 // Доступ неаутентиф. пользователям к этим адресам разрешен
                .anyRequest().hasAnyRole("USER", "ADMIN")                                                     // Для любого запроса доступ разрешен с ролями USER и ADMIN
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and()
                // выход
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login")                                       // При переходе на /logout куки и сессия удаляются
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)                                             // Сессия на сервере сохраняться не будет
                .and()
                .userDetailsService(personDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
