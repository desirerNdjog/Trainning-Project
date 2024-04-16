package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@DisplayName(value = "Write tests on every case of functions")
class PersonImplTest {
    @Mock
    private PersonneRepository repository;
    @Mock
    private GenericValidation validation;
    private Person person;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        this.person = Person.builder()
                .id(null)
                .firstName("NDJOG")
                .lastName("Desire Junior")
                .birthDate(date)
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName(value = "Creation of a person when everything is okay")
     void shouldCreatePersonWhenValid(){
        //given
        List<String> errors = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        this.person = Person.builder()
                .id(1L)
                .firstName("NDJOG")
                .lastName("Desire Junior")
                .birthDate(date)
                .phoneNumber("690134110")
                .build();

        //when
        when(validation.errors(person)).thenReturn(Collections.emptyList());
        when(repository.save(this.person)).thenReturn(person);

        //then
        assertThat(errors).isEmpty();
        assertThat(person).isNotNull();
        assertThat(this.person.getId()).isEqualTo(person.getId());
        assertThat(this.person.getEmail()).isEqualTo(person.getEmail());
        assertThat(this.person.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(this.person.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(this.person.getLastName()).isEqualTo(person.getLastName());
        assertThat(this.person.getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test()
    @DisplayName(value = "No creation of person when data no valid")
    void shouldNotCreatePersonWhenNotValid() throws ValidationException{
        //Given
        List<String> errors = List.of("lastname can't be empty");
        var message = "lastname can't be empty";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = Person.builder()
                .id(null)
                .firstName("Desire Ngono")
                .lastName("")
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("689543854").build();

        //When
        when(validation.errors(person)).thenReturn(errors);

        //Then
        assertThat(errors.get(0)).isNotNull();
        assertThat(person.getFirstName()).isNotBlank();
        assertThat(person.getLastName()).isBlank();
        assertThat(person.getEmail()).isNotEmpty();
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getId()).isNull();
        assertThat(errors.get(0)).isEqualTo("lastname can't be empty");
    }

    @Test
    @DisplayName(value = "update person when everythins is okay")
    void shouldUpdatePersonWhenValid(){
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("689543854").build();

        //When
        when(validation.errors(person)).thenReturn(Collections.emptyList());
        when(repository.save(person)).thenReturn(person);

        //Then
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getFirstName()).isNotNull();
        assertThat(person.getLastName()).isNotNull();
        assertThat(person.getPhoneNumber()).isNotNull();
    }

    @Test
    @DisplayName(value = "don't update person when data are not good")
    void shouldNotUpdatePersonWhenNotValid(){
        //Given
        List<String> errors = List.of("lastname can't be empty");
        var message = "lastname can't be empty";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName(null)
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("689543854").build();

        //When
        when(validation.errors(person)).thenReturn(errors);

        //Then
        assertThat(errors.get(0)).isNotNull();
        assertThat(person.getLastName()).isNull();
        assertThat(person.getId()).isNotNull();
        assertThat(errors.get(0)).isEqualTo("lastname can't be empty");
    }

    @DisplayName(value = "find personne when is ok")
    @ParameterizedTest(name = "identifiant: {0} search person value")
    @ValueSource(longs = {1L})
    void shouldFindPersonByIdWhenValid(long identifiant){
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = Person.builder()
                .id(1L)
                .firstName("")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("689543854").build();
        Optional<Person> optionalPerson = Optional.of(person);

        //When
        when(repository.findById(identifiant)).thenReturn(optionalPerson);

        //Then
        assertThat(optionalPerson).isNotNull();
        assertThat(person.getId()).isEqualTo(optionalPerson.get().getId());
    }

    @DisplayName(value = "not find personne when is not ok and throws exception")
    @ParameterizedTest(name = "identifiant: {0} search person value")
    @ValueSource(longs = {1L, 3L})
    void shouldNotFindPersonByIdWhenNotValid(long identifiant) throws NoSuchElementException {
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = Person.builder()
                .id(2L)
                .firstName("")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("689543854").build();
        Optional<Person> optionalPerson = Optional.empty();

        //When
        when(repository.findById(identifiant)).thenThrow(NoSuchElementException.class);

        //Then
        assertThat(optionalPerson).isEmpty();
    }
}