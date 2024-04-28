package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.mapper.PersonMapperImpl;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import com.bdd_test.service.PersonneService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import utils.DateBuilder;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName(value = "Write tests on every case of functions")
class PersonImplTest {
    @Mock
    private PersonneRepository repository;
    private PersonneService personImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personImpl = new PersonImpl(repository, new GenericValidation(), PersonMapperImpl.INSTANCE);
    }

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
    @DisplayName(value = "create and return persondto")
     void givenPersondtoValidAndCreateAndReturnPersondto(){
        //given
        PersonneDTO personDto = buildPerson();
        Person person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(DateBuilder.date("25/08/1997"))
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
    @DisplayName(value = "given persondto no valide and return validation exception")
    void given_invalide_personedto_and_return_validation_exception(){
        //Given
        PersonneDTO person = PersonneDTO.builder()
                .id(null)
                .firstName("Desire Ngono")
                .lastName("")
                .email("ndjogdesire@gmail.com")
                .date("25/08/1997")
                .phoneNumber("689543854").build();

        //When
        ValidationException exception = assertThrows(ValidationException.class, ()->personImpl.create(person));

        //Then
        assertThat(exception).isInstanceOf(ValidationException.class);
        assertThat(exception.getMessage()).isEqualTo("lastname is empty");
    }

    @Test
    @DisplayName(value = "Given valide persondto ")
    void given_valid_persondto_update_and_return_update_persondto(){
        //Given
        PersonneDTO personneDTO = buildPerson();
        var person = Person.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .birthDate(DateBuilder.date("25/08/1997"))
                .phoneNumber("689543854").build();
        when(repository.save(any(Person.class))).thenReturn(person);

        //When
        PersonneDTO personExpected = personImpl.update(personneDTO);

        //Then
        assertThat(personExpected).isNotNull();
        assertThat(personExpected.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(personExpected.getLastName()).isEqualTo(person.getLastName());
        assertThat(personExpected.getEmail()).isEqualTo(person.getEmail());
        assertThat(personExpected.getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test
    @DisplayName(value = "Given invalid persondto and return validation exception")
    void given_invalid_persondto_and_return_validation_exception(){
        //Given
        PersonneDTO personneDTO = PersonneDTO.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("")
                .email("ndjogdesire@gmail.com")
                .date("25/08/1997")
                .phoneNumber("689543854").build();

        //When
        ValidationException expectedException = assertThrows(ValidationException.class, ()-> personImpl.update(personneDTO));

        //Then
        assertThat(expectedException).isInstanceOf(ValidationException.class);
        assertThat(expectedException.getMessage()).isEqualTo("lastname is empty");
    }

    @DisplayName(value = "Given person id and return optional person")
    @Test()
    void given_persondto_id_and_return_persondto(){
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
        Optional<PersonneDTO> expectedPerson = personImpl.findPersonById(id);

        //Then
        assertThat(expectedPerson).isNotEmpty();
        assertThat(expectedPerson.get().getFirstName()).isEqualTo(person.getFirstName());
        assertThat(expectedPerson.get().getLastName()).isEqualTo(person.getLastName());
        assertThat(expectedPerson.get().getEmail()).isEqualTo(person.getEmail());
        assertThat(expectedPerson.get().getPhoneNumber()).isEqualTo(person.getPhoneNumber());
    }

    @Test()
    @DisplayName(value = "Given persondto id and return empty optional person")
    void given_persondto_id_when_not_found_return_empty_optional_person(){
        //Given
        Long id = 1L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<PersonneDTO> optionalPerson = personImpl.findPersonById(id);

        //Then
        assertThat(optionalPerson).isEmpty();
    }
}
