package epam.by.ivanchenko.config;

import epam.by.ivanchenko.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailService) {
        this.personDetailsService = personDetailService;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // конфиг. Spring Security
        // конфиг. авторизации
        return http
                .csrf().disable()                                           // Откл. защиту межсайтовой подделки запросов
                .authorizeRequests()
                .requestMatchers("/auth/login", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and().build();
    }
    //WebSecurityCustomizer

//    @Bean
//    public UserDetailsManager userDetailsManager(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails person = auth.userDetailsService();
//       // auth.userDetailsService(personDetailsService);
//        return new
//    }









//
//    // OLD:
//
//    // Настройка аутентификации
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(personDetailsService);                                       // Провайдер
//    }
//
//
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();                                          // Временно! Пароль не шифруется
        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
