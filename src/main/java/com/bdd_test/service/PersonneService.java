package com.bdd_test.service;

import com.bdd_test.dto.PersonneDTO;

import java.util.Optional;

public interface PersonneService {
    PersonneDTO create(PersonneDTO personneDTO);
    PersonneDTO update(PersonneDTO person);
    Optional<PersonneDTO> findPersonById(Long id);
}
