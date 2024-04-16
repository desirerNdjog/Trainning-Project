package com.bdd_test.mapper;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PersonDTOMapper implements Function<Person, PersonneDTO> {
    @Override
    public PersonneDTO apply(Person person) {
        return new PersonneDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getBirthDate()
        );
    }
}
