package com.bdd_test.service;

import com.bdd_test.models.Person;

import java.util.List;

public interface PersonDAOService {
    List<Person> findAllPerson();
}
