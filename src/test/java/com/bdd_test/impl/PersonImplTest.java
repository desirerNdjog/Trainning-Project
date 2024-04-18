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
    private PersonImpl personImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personImpl = new PersonImpl(repository, new GenericValidation(), new PersonMapperImpl());
    }

    private LocalDate date(){
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date = LocalDate.parse("25/08/1997",  formatter);
    }

    private PersonneDTO buildPerson() {
        return PersonneDTO.builder()
                .id(null)
                .firstName("Desire Ngono")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .date(date())
                .phoneNumber("689543854").build();
    }

    @Test
    @DisplayName(value = "valide and create person when valid")
     void given_personDto_for_create_should_validate_and_create_person(){
        //given
        PersonneDTO personDto = buildPerson();
        Person person = Person.builder()
            .id(null)
            .firstName("Desire Ngono")
            .lastName("NDJOG")
            .email("ndjogdesire@gmail.com")
            .birthDate(date())
            .phoneNumber("689543854").build();
        when(repository.save(any(Person.class))).thenReturn(person);

        //when
        PersonneDTO personExpected = personImpl.create(personDto);

        //then
        assertThat(personExpected).isNotNull();
        assertThat(personExpected.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(personExpected.getLastName()).isEqualTo(person.getLastName());
        assertThat(personExpected.getEmail()).isEqualTo(person.getEmail());
        assertThat(personExpected.getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test()
    @DisplayName(value = "no creation of person when data no valid")
    void shouldNotCreatePersonWhenNotValid(){
        try {
            //Given
            Person person = Person.builder()
                    .id(null)
                    .firstName("Desire Ngono")
                    .lastName("")
                    .email("ndjogdesire@gmail.com")
                    .birthDate(date())
                    .phoneNumber("689543854").build();

            //When
            PersonneDTO personneDTO = personImpl.create(buildPerson());

            //Then
            assertThat(person.getFirstName()).isNotBlank();
            assertThat(person.getLastName()).isBlank();
            assertThat(person.getEmail()).isNotEmpty();
            assertThat(person.getBirthDate()).isNotNull();
            assertThat(person.getId()).isNull();
        }catch (ValidationException ex){

        }
    }

    @Test
    @DisplayName(value = "update person when everythins is okay")
    void shouldUpdatePersonWhenValid(){
        //Given
        var person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date())
                .phoneNumber("689543854").build();
        when(repository.save(any(Person.class))).thenReturn(person);

        //When
        PersonneDTO personExpected = personImpl.update(buildPerson());

        //Then
        assertThat(personExpected).isNotNull();
        assertThat(personExpected.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(personExpected.getLastName()).isEqualTo(person.getLastName());
        assertThat(personExpected.getEmail()).isEqualTo(person.getEmail());
        assertThat(personExpected.getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test
    @DisplayName(value = "don't update person when data are not good")
    void shouldNotUpdatePersonWhenNotValid(){
       try {
           //Given
           var person = Person.builder()
                   .id(1L)
                   .firstName("Desire Junior")
                   .lastName("")
                   .email("ndjogdesire@gmail.com")
                   .birthDate(date())
                   .phoneNumber("689543854").build();

           //When
           PersonneDTO personneDTO = personImpl.update(buildPerson());

           //Then
           assertThat(personneDTO).isNull();
       }catch (ValidationException ex){

       }
    }

    @DisplayName(value = "find personne when is ok")
    @Test()
    void shouldFindPersonByIdWhenValid(){
        //Given
        Long id = 1L;
        Person person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date())
                .phoneNumber("689543854").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(person));

        //When
        Optional<PersonneDTO> optionalPerson = personImpl.findPersonById(id);

        //Then
        assertThat(optionalPerson).isNotNull();
        assertThat(optionalPerson.get().getFirstName()).isEqualTo(person.getFirstName());
        assertThat(optionalPerson.get().getLastName()).isEqualTo(person.getLastName());
        assertThat(optionalPerson.get().getEmail()).isEqualTo(person.getEmail());
        assertThat(optionalPerson.get().getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @DisplayName(value = "not find personne when is not ok and throws exception")
    @Test()
    void shouldNotFindPersonByIdWhenNotValid(){
        //Given
        Long id = 100L;
        Person person = Person.builder()
                .id(2L)
                .firstName("")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(date())
                .phoneNumber("689543854").build();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<PersonneDTO> optionalPerson = personImpl.findPersonById(id);

        //Then
        assertThat(optionalPerson).isEmpty();
    }
}
