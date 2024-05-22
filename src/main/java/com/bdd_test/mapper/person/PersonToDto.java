package com.bdd_test.mapper.person;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * @author: desirejuniorndjog.
 * @created: 22/05/2024 : 19:05
 * @project: trainning
 */

@Service
public class PersonToDto implements Function<Person, PersonneDTO> {
    @Override
    public PersonneDTO apply(Person person) {
        return PersonneDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .date(person.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
