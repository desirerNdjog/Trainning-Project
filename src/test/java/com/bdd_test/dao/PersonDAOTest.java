package com.bdd_test.dao;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.mapper.PersonMapper;
import com.bdd_test.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import utils.DateBuilder;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PersonDAOTest {

    @Mock
    EntityManager entityManager;

    private PersonDAO personDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personDAO = new PersonDAO(entityManager, PersonMapper.INSTANCE);

    }

    @Test
    @DisplayName(value = "given list of student and return list of persondto when valid")
    void give_list_of_person_should_return_list_of_persondto_when_valid(){
        //Given
        Person person = Person.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("690865679")
                .birthDate(DateBuilder.date("25/08/1997"))
                .email("ndjogdesire@gmail.com")
                .build();
        Person personTwo = Person.builder()
                .firstName("Diland Miller")
                .lastName("ETUBA")
                .phoneNumber("698549032")
                .birthDate(DateBuilder.date("25/08/1997"))
                .email("etubadiland@gmail.com")
                .build();
        List<Person> list = List.of(person, personTwo);

        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Person> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        Root<Person> root = Mockito.mock(Root.class);
        TypedQuery<Person> typedQuery = Mockito.mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Person.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Person.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(list);

        //When
        List<PersonneDTO> expectedPersonList = personDAO.findAllPerson();

        //Then
        assertThat(expectedPersonList).isNotEmpty()
                .hasSize(2);
    }
}
