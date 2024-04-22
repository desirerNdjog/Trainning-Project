package com.bdd_test.dao;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.mapper.PersonMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: desirejuniorndjog.
 * @created: 23/04/2024 : 00:31
 * @project: trainning
 */
@DataJpaTest
@EnableJpaRepositories
class PersonDaoIT {
    private EntityManager entityManager;
    private PersonDAO personDAO = new PersonDAO(
            entityManager, PersonMapper.INSTANCE
    );

    @Test
    @DisplayName(value = "fetch list persondto and return list of persondto")
    void fetch_list_persondt_and_return_list_of_persondto(){
    //Given

    //When
    List<PersonneDTO> expectedPersonList = personDAO.findAllPerson();

    //Then
    assertThat(expectedPersonList).isNotEmpty()
            .hasSize(2);
    }
}
