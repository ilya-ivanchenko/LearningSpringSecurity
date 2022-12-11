package epam.by.ivanchenko.security;

import epam.by.ivanchenko.model.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetail implements UserDetails {

    private final Person person;

    public PersonDetail(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {                                                    // Авторизация на основе роли
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));                                 // Получаем роли пользователей и возращаем права для роли
        // Булет либо ROLE_USER либо ROLE_ADMIN
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson() {                                                              // Для получения данных(полей) аутенфиц. юзера
        return this.person;
    }
}
