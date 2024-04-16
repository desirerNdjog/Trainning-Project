package com.bdd_test.service;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;

import java.util.Optional;

public interface PersonneService {
    PersonneDTO create(Person person );
    PersonneDTO update(Person person);
    Optional<PersonneDTO> findPersonById(Long id);
}
