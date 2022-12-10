package epam.by.ivanchenko.util;

import epam.by.ivanchenko.model.Person;
import epam.by.ivanchenko.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);                                                           // Нужен для объектов класса Person
    }

    @Override
    public void validate(Object target, Errors errors) {
         Person person = (Person) target;
                                                                                                    // Лучше делать через Optional<Person> валидато (см. пред. темы)
         try {
             personDetailsService.loadUserByUsername(person.getUsername());
         } catch (UsernameNotFoundException ignored) {
             return;                                                                                 // ок, пользователен не найден
         }
         errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}
