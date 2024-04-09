package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonImplTest {
    @Mock
    PersonneRepository repository;
    @Mock
    GenericValidation validation;
    @Autowired
    PersonImpl impl;
    Person person;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        this.person = new Person(
                1l,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
    }

    @Test
     void shouldCreatePersonWhenValid(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                1l,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
        List<String> checksErrors = validation.errors(person);
        assertThat(checksErrors).isEmpty();
        when(repository.save(this.person)).thenReturn(person);
        assertThat(person).isNotNull();
        assertThat(this.person.getId()).isEqualTo(person.getId());
        assertThat(this.person.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(this.person.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(this.person.getLastName()).isEqualTo(person.getLastName());
        assertThat(this.person.getPhoneNumber()).isEqualTo(person.getPhoneNumber());

        verify(repository, timeout(1)).save(this.person);
    }

    @Test
    void shouldNotCreatePersonWhenNotValid(){
        //todo
    }
}