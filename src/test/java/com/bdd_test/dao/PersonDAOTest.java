package com.bdd_test.dao;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.mapper.PersonMapper;
import com.bdd_test.mapper.PersonMapperImpl;
import com.bdd_test.models.Person;
import com.bdd_test.service.PersonDAOService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PersonDAOTest {
    @Mock
    private EntityManager entityManager;
    @Mock
    CriteriaBuilder criteriaBuilder;
    @Mock
    Root<Person> root;
    @Mock
    CriteriaQuery<Person> criteriaQuery;

    private PersonDAO personDAO;
    private PersonMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new PersonMapperImpl();
        personDAO = new PersonDAO(entityManager, new PersonMapperImpl());
        MockitoAnnotations.openMocks(this);
    }

    private LocalDate date(){
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date = LocalDate.parse("25/08/1997",  formatter);
    }

    @Test
    void shouldFindAllPersonWhenValid(){
        //Given
        List<Person> list = new ArrayList<>();
        Person person = Person.builder()
                .id(null)
                .firstName("Desire Ngono")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(this.date())
                .phoneNumber("690543476").build();

        Person personOne = Person.builder()
                .id(null)
                .firstName("Miller Dilan")
                .lastName("ETUBA")
                .email("miller@gmail.com")
                .birthDate(this.date())
                .phoneNumber("690543476").build();

        list.add(personOne);
        list.add(person);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Person.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Person.class)).thenReturn(root);
        when(entityManager.createQuery(anyString()).getResultList()).thenReturn(list);
        List<PersonneDTO> dtoList = list.stream().map(mapper::fromPersonToPersonDTO).toList();

        //When
        List<PersonneDTO> personneDTOS = personDAO.findAllPerson();

        //Then
        assertThat(personneDTOS).isNotEmpty();

    }
}
