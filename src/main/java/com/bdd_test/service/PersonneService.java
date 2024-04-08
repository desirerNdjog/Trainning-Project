package com.bdd_test.service;

import com.bdd_test.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonneService {
    Person create(Person person);
    Person update(Person person);
    Optional<Person> findPersonById(Long id);
}
