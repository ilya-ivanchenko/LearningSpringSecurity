package epam.by.ivanchenko.security;

import epam.by.ivanchenko.service.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final PersonDetailService personDetailService;

    @Autowired
    public AuthProviderImpl(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {                       // Логика аутентификации
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();                                                            // Получаем пароль

        UserDetails personDetails = personDetailService.loadUserByUsername(username);

        if (!password.equals(personDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password!");
        } else {
            // Возвращаем объект с аутентификацией
            return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());             // 3-им аргументом  передаем список прав
            // Все что положим в объект authentication в качестве Principal, мы будем иметь к этму доступ при каждом запросе человека person к серверу
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
