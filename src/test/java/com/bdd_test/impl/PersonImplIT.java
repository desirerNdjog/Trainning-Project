package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.mapper.PersonMapper;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import com.bdd_test.service.PersonneService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DateBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PersonImplIT {

     private PersonneRepository repository;

     private PersonneService personImpl = new PersonImpl(
             repository, new GenericValidation(), PersonMapper.INSTANCE
     );

    private PersonneDTO buildPerson() {
        return PersonneDTO.builder()
                .id(null)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .date("25/08/1997")
                .phoneNumber("689543854").build();
    }

    @Test
    @DisplayName(value = "given persondto validate, create and return persondto")
    void given_persondto_valide_and_create_and_return_persondto(){
        //given
        PersonneDTO personDto = buildPerson();

        //when
        PersonneDTO personExpected = personImpl.create(personDto);

        //then
        assertThat(personExpected).isNotNull();
        assertThat(personExpected.getFirstName()).isEqualTo(personDto.getFirstName());
        assertThat(personExpected.getLastName()).isEqualTo(personDto.getLastName());
        assertThat(personExpected.getEmail()).isEqualTo(personDto.getEmail());
        assertThat(personExpected.getPhoneNumber()).isEqualTo(personDto.getPhoneNumber());
    }

    @Test()
    @Disabled(value = "wait")
    @DisplayName(value = "no creation of person when data no valid")
    void shouldNotCreatePersonWhenNotValid(){
        try {
            //Given
            Person person = Person.builder()
                    .id(null)
                    .firstName("Desire Ngono")
                    .lastName("")
                    .email("ndjogdesire@gmail.com")
                    .birthDate(DateBuilder.date("25/08/1997"))
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
    @Disabled(value = "wait")
    @DisplayName(value = "update person when everythins is okay")
    void shouldUpdatePersonWhenValid(){
        //Given
        var person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(DateBuilder.date("25/08/1997"))
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
    @Disabled(value = "wait")
    @DisplayName(value = "don't update person when data are not good")
    void shouldNotUpdatePersonWhenNotValid(){
        try {
            //Given
            var person = Person.builder()
                    .id(1L)
                    .firstName("Desire Junior")
                    .lastName("")
                    .email("ndjogdesire@gmail.com")
                    .birthDate(DateBuilder.date("25/08/1997"))
                    .phoneNumber("689543854").build();

            //When
            PersonneDTO personneDTO = personImpl.update(buildPerson());

            //Then
            assertThat(personneDTO).isNull();
        }catch (ValidationException ex){

        }
    }

    @DisplayName(value = "find personne when is ok")
    @Disabled(value = "wait")
    @Test()
    void shouldFindPersonByIdWhenValid(){
        //Given
        Long id = 1L;
        Person person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(DateBuilder.date("25/08/1997"))
                .phoneNumber("689543854").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(person));

        //When
        Optional<PersonneDTO> optionalPerson = personImpl.findPersonById(id);

        //Then
        assertThat(optionalPerson).isNotEmpty();
        assertThat(optionalPerson.get().getFirstName()).isEqualTo(person.getFirstName());
        assertThat(optionalPerson.get().getLastName()).isEqualTo(person.getLastName());
        assertThat(optionalPerson.get().getEmail()).isEqualTo(person.getEmail());
        assertThat(optionalPerson.get().getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @DisplayName(value = "not find personne when is not ok and throws exception")
    @Disabled(value = "wait")
    @Test()
    void shouldNotFindPersonByIdWhenNotValid(){
        //Given
        Long id = 100L;
        Person person = Person.builder()
                .id(2L)
                .firstName("")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(DateBuilder.date("25/08/1997"))
                .phoneNumber("689543854").build();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<PersonneDTO> optionalPerson = personImpl.findPersonById(id);

        //Then
        assertThat(optionalPerson).isEmpty();
    }
}
