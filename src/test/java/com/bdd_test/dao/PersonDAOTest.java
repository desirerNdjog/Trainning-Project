package com.bdd_test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.infrastructure.util.DateManager;
import com.bdd_test.mapper.PersonMapper;
import com.bdd_test.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class PersonDAOTest {

  @Mock private EntityManager entityManager;
  private PersonDAO personDAO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    personDAO = new PersonDAO(entityManager, PersonMapper.INSTANCE);
  }

  @Test
  @DisplayName(value = "fetch all users")
  void fetch_person_should_return_list_person_found() {
    // Given
    List<Person> listPerson =
        List.of(
            Person.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("690865679")
                .birthDate(DateManager.buildDate("25/08/1997"))
                .email("ndjogdesire@gmail.com")
                .id(1L)
                .build(),
            Person.builder()
                .firstName("Diland Miller")
                .lastName("ETUBA")
                .id(2L)
                .phoneNumber("698549032")
                .birthDate(DateManager.buildDate("25/08/1997"))
                .email("etubadiland@gmail.com")
                .build());
    CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    CriteriaQuery<Person> criteriaQuery = mock(CriteriaQuery.class);
    TypedQuery<Person> typedQuery = mock(TypedQuery.class);
    Root<Person> personRoot = mock(Root.class);

    when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    when(criteriaBuilder.createQuery(Person.class)).thenReturn(criteriaQuery);
    when(criteriaQuery.from(Person.class)).thenReturn(personRoot);
    when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    when(typedQuery.getResultList()).thenReturn(listPerson);

    // When
    List<PersonneDTO> listPersonDtoExpected = personDAO.findAllPerson();

    // Then
    assertThat(listPersonDtoExpected).isNotEmpty().hasSize(2);
  }
}
