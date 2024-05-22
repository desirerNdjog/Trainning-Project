package com.bdd_test.mapper.person;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.domain.models.Person;
import com.bdd_test.utils.DateUtils;

import java.util.function.Function;

/**
 * @author: desirejuniorndjog.
 * @created: 22/05/2024 : 19:05
 * @project: trainning
 */

public class DtoToPerson implements Function<PersonneDTO, Person> {
    @Override
    public Person apply(PersonneDTO persondto) {
        return Person.builder()
                .id(persondto.getId())
                .firstName(persondto.getFirstName())
                .lastName(persondto.getLastName())
                .email(persondto.getEmail())
                .phoneNumber(persondto.getPhoneNumber())
                .birthDate(DateUtils.stringDateToLocaDate(persondto.getDate()))
                .build();
    }
}
