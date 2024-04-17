package com.bdd_test.service;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@DisplayName(value = "Test fonctions present in service")
class PersonneServiceTest {
    private PersonneService service;

    @Autowired
    public PersonneServiceTest(PersonneService service) {
        this.service = service;
    }

    @Test
    @DisplayName(value = "create person an return a personDTO")
    void shouldExcuteCreatePersonAndReturnPersonDTO(){
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997", formatter);
        var person = PersonneDTO.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .date(date)
                .email("ndjogdesire@gmail.com")
                .build();

        //When
        var personDTO = service.create(person);

        //Then
        assertThat(personDTO).isNotNull();
        assertThat(person.getFirstName()).isEqualTo(personDTO.getFirstName());
        assertThat(person.getLastName()).isEqualTo(personDTO.getLastName());
        assertThat(person.getPhoneNumber()).isEqualTo(personDTO.getPhoneNumber());
        assertThat(person.getDate()).isEqualTo(personDTO.getDate());
        assertThat(person.getEmail()).isEqualTo(personDTO.getEmail());

        verify(service, times(1)).create(person);
    }

    @Test
    @DisplayName(value = "update person an return personDTO")
    void shouldUpdatePersonAndReturnPersonDTO(){
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997", formatter);
        var person = PersonneDTO.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .date(date)
                .email("ndjogdesire@gmail.com")
                .build();

        //When
        var personDTO = service.update(person);

        //Then
        assertThat(personDTO).isNotNull();
        assertThat(person.getFirstName()).isEqualTo(personDTO.getFirstName());
        assertThat(person.getLastName()).isEqualTo(personDTO.getLastName());
        assertThat(person.getPhoneNumber()).isEqualTo(personDTO.getPhoneNumber());
        assertThat(person.getDate()).isEqualTo(personDTO.getDate());
        assertThat(person.getEmail()).isEqualTo(personDTO.getEmail());

        verify(service, times(1)).update(person);
    }

    @DisplayName(value = "find a person by identifiant")
    @ValueSource(longs = {1, 2, 3})
    @ParameterizedTest()
    void shouldFindAUserByIdAndReturnOptionalPersonDTO(long id){
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997", formatter);
        var person = Person.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .birthDate(date)
                .email("ndjogdesire@gmail.com")
                .build();

        //When
        Optional<PersonneDTO> optionalPerson = service.findPersonById(id);
        //Optional<PersonneDTO> = service.findPersonById(anyLong());

        //Then
        assertThat(optionalPerson).isNotNull();
        assertThat(person.getFirstName()).isEqualTo(optionalPerson.get().getFirstName());
        assertThat(person.getLastName()).isEqualTo(optionalPerson.get().getLastName());
        assertThat(person.getPhoneNumber()).isEqualTo(optionalPerson.get().getPhoneNumber());
        assertThat(person.getBirthDate()).isEqualTo(optionalPerson.get().getDate());
        assertThat(person.getEmail()).isEqualTo(optionalPerson.get().getEmail());

        verify(service, times(1)).findPersonById(id);
    }
}
