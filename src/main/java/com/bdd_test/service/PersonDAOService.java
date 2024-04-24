package com.bdd_test.service;

import com.bdd_test.dto.PersonneDTO;

import java.util.List;

public interface PersonDAOService {
    List<PersonneDTO> findAllPerson();
}
