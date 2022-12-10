package epam.by.ivanchenko.service;

import epam.by.ivanchenko.model.Person;
import epam.by.ivanchenko.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final PersonRepository personRepository;

    @Autowired
    public RegistrationService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Transactional
    public void register(Person person) {
        personRepository.save(person);
    }
}
