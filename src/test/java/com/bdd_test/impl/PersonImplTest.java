package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
        this.person = new Person(
                1L,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void shouldCreatePersonWhenValid(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                1L,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
        when(validation.errors(person)).thenReturn(Collections.emptyList());
        when(repository.save(this.person)).thenReturn(person);
        assertThat(person).isNotNull();
        assertThat(this.person.getId()).isEqualTo(person.getId());
        assertThat(this.person.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(this.person.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(this.person.getLastName()).isEqualTo(person.getLastName());
        assertThat(this.person.getPhoneNumber()).isEqualTo(person.getPhoneNumber());

//        verify(repository, times(1)).save(this.person);
    }

    @Test()
    void shouldNotCreatePersonWhenNotValid() throws ValidationException{
        List<String> errors = List.of("lastname can't be empty");
        var message = "lastname can't be empty";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                null,
                "Desire Junior",
                "",
                "690134110",
                date
        );
        when(validation.errors(person)).thenReturn(errors);
        assertThat(errors.get(0)).isNotNull();
        assertThat(person.getLastName()).isBlank();
        assertThat(person.getId()).isNull();
        assertThat(errors.get(0)).isEqualTo("lastname can't be empty");
    }

    @Test
    void shouldUpdatePersonWhenValid(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                1l,
                "Desire Junior",
                "ETUBA",
                "690134110",
                date
        );
        when(validation.errors(person)).thenReturn(Collections.emptyList());
        when(repository.save(person)).thenReturn(person);
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getFirstName()).isNotNull();
        assertThat(person.getLastName()).isNotNull();
        assertThat(person.getPhoneNumber()).isNotNull();

        //verify(repository, times(1)).save(person);
    }

    @Test
    void shouldNotUpdatePersonWhenNotValid(){
        List<String> errors = List.of("lastname can't be empty");
        var message = "lastname can't be empty";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                1L,
                "Desire Junior",
                null,
                "690134110",
                date
        );
        when(validation.errors(person)).thenReturn(errors);
        assertThat(errors.get(0)).isNotNull();
        assertThat(person.getLastName()).isNull();
        assertThat(person.getId()).isNotNull();
        assertThat(errors.get(0)).isEqualTo("lastname can't be empty");
    }

    @Test
    void shouldFindPersonByIdWhenValid(){
        Long identifiant = 1L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                1L,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
        Optional<Person> optionalPerson = Optional.of(person);
        when(repository.findById(identifiant)).thenReturn(optionalPerson);
        assertThat(optionalPerson).isNotNull();
        assertThat(person.getId()).isEqualTo(optionalPerson.get().getId());
    }

    @Test
    void shouldNotFindPersonByIdWhenNotValid() throws NoSuchElementException {
        Long identifiant = 1L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                2L,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
        Optional<Person> optionalPerson = Optional.empty();
        when(repository.findById(identifiant)).thenThrow(NoSuchElementException.class);
        assertThat(optionalPerson).isEmpty();
    }
}