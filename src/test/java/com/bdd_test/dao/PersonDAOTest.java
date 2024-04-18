package com.bdd_test.dao;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.mapper.PersonMapperImpl;
import com.bdd_test.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PersonDAOTest {

    @Mock
    EntityManager entityManager;
    @Mock
    CriteriaBuilder criteriaBuilder;
    @Mock
    CriteriaQuery<Person> criteriaQuery;
    @Mock
    Root<Person> root;
    private PersonDAO personDAO;
    private PersonMapperImpl mapper = new PersonMapperImpl();

    @BeforeEach
    void setUp() {
        personDAO = new PersonDAO(entityManager, new PersonMapperImpl());
        MockitoAnnotations.openMocks(this);
    }

    private LocalDate date(){
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse("25/08/1997",  formatter);
    }

    @Test
    @Disabled(value = "do not yet test")
    void shouldFindAllPersonWhenValid(){
        //Given
        Person person = Person.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("690865679")
                .birthDate(date())
                .email("ndjogdesire@gmail.com")
                .build();
        Person personTwo = Person.builder()
                .firstName("Diland Miller")
                .lastName("ETUBA")
                .phoneNumber("698549032")
                .birthDate(date())
                .email("etubadiland@gmail.com")
                .build();
        List<Person> list = List.of(person, personTwo);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Person.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Person.class)).thenReturn(root);
        when(entityManager.createQuery(anyString()).getResultList()).thenReturn(list);
        List<PersonneDTO> personneDTOS = list.stream().map(mapper::fromPersonToPersonDTO).toList();

        //When
        List<PersonneDTO> personList = personDAO.findAllPerson();

        //Then
        assertThat(personList).isNotEmpty()
                .hasSize(2);
    }
}
