package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.mapper.PersonMapperImpl;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import com.bdd_test.service.PersonneService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
    private PersonneService personneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personneService = new PersonImpl(repository, new GenericValidation(), new PersonMapperImpl());
    }

    private PersonneDTO buildPerson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        return PersonneDTO.builder()
                .id(1L)
                .firstName("Desire Ngono")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .date(date)
                .phoneNumber("690543476").build();
    }


    @Test
    @DisplayName(value = " validate and create personDTO")
     void given_personDto_for_create_should_validate_and_create_person(){
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        PersonneDTO personDto = buildPerson();
        var person = Person.builder()
            .id(null)
            .firstName("Desire Ngono")
            .lastName("NDJOG")
            .email("ndjogdesire@gmail.com")
            .birthDate(date)
            .phoneNumber("690543476").build();
        when(repository.save(any(Person.class))).thenReturn(person);

        //when
        PersonneDTO personExpected = personneService.create(personDto);

        //then
        assertThat(personExpected).isNotNull();
        assertThat(personExpected.getId()).isEqualTo(person.getId());
        assertThat(personExpected.getEmail()).isEqualTo(person.getEmail());
        assertThat(personExpected.getDate()).isEqualTo(person.getBirthDate());
        assertThat(personExpected.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(personExpected.getLastName()).isEqualTo(person.getLastName());
        assertThat(personExpected.getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test()
    @DisplayName(value = "no validate and avoid create personDTO")
    void given_personDto_for_create_should_no_validate_and_no_create_person(){
       try {
           //given
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
           LocalDate date = LocalDate.parse("25/08/1997",  formatter);
           PersonneDTO personDto = PersonneDTO.builder()
                   .id(null)
                   .firstName("Desire Ngono")
                   .lastName("")
                   .email("ndjogdesire@gmail.com")
                   .date(date)
                   .phoneNumber("690543476").build();
           //when
           PersonneDTO personExpected = personneService.create(personDto);
           //Then
           verify(personneService, times(1)).create(any(PersonneDTO.class));
       }catch (ValidationException e){

       }
    }

    @Test
    @DisplayName(value = "valide and update personDTO")
    void given_personDto_for_update_should_validate_and_update_person(){
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        PersonneDTO personDto = buildPerson();
        Person person = Person.builder()
                .id(1L)
                .firstName("Desire Ngono")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("690543476").build();
        when(repository.save(any(Person.class))).thenReturn(person);

        //when
        PersonneDTO personExpected = personneService.update(personDto);

        //then
        assertThat(personExpected).isNotNull();
        assertThat(personExpected.getId()).isEqualTo(person.getId());
        assertThat(personExpected.getEmail()).isEqualTo(person.getEmail());
        assertThat(personExpected.getDate()).isEqualTo(person.getBirthDate());
        assertThat(personExpected.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(personExpected.getLastName()).isEqualTo(person.getLastName());
        assertThat(personExpected.getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test
    @DisplayName(value = "do not validate and not update personDTO")
    void given_personDto_for_update_should_no_validate_and_no_update_person(){
        try {
            //given
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse("25/08/1997",  formatter);
            PersonneDTO personDto = PersonneDTO.builder()
                    .id(1L)
                    .firstName("Desire Ngono")
                    .lastName("")
                    .email("ndjogdesire@gmail.com")
                    .date(date)
                    .phoneNumber("690543476").build();

            //when
            PersonneDTO personExpected = personneService.update(personDto);

            //Then
            assertThat(personExpected).isNull();
            verify(personneService, times(1)).create(any(PersonneDTO.class));
        }catch (ValidationException ex){

        }
    }

    @DisplayName(value = "find personne when is ok")
    @Test()
    void shouldFindPersonByIdWhenValid(){
        //Given
        Long id = 100L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        Person person = Person.builder()
                .id(1L)
                .firstName("Desire Ngono")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date)
                .phoneNumber("689543854").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(person));

        //When
        Optional<PersonneDTO> personTwo = personneService.findPersonById(id);

        //Then
        assertThat(personTwo).isNotEmpty();
        assertThat(person.getEmail()).isEqualTo(personTwo.get().getEmail());
        assertThat(person.getPhoneNumber()).isEqualTo(personTwo.get().getPhoneNumber());
        verify(repository, times(1)).findById(id);
    }

    @DisplayName(value = "not find personne when is not ok")
    @Test()
    void shouldNotFindPersonByIdWhenNotValid(){
        //Given
        Long id = 100L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<PersonneDTO> person = personneService.findPersonById(id);

        //Then
        assertThat(person).isEmpty();
        verify(repository, times(1)).findById(id);
    }
}
