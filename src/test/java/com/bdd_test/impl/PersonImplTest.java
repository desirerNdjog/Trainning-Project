package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.mapper.PersonMapper;
import com.bdd_test.mapper.PersonMapperImpl;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import com.bdd_test.service.PersonneService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@DisplayName(value = "Write tests on every case of functions")
class PersonImplTest {
  @Mock
  private PersonneRepository repository;
  private PersonneService personImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    personImpl = new PersonImpl(repository, new GenericValidation(), PersonMapper.INSTANCE);
  }

  private LocalDate date() {
    LocalDate date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return date = LocalDate.parse("25/08/1997", formatter);
  }

  private PersonneDTO buildPerson() {
    return PersonneDTO.builder()
        .id(null)
        .firstName("Desire Ngono")
        .lastName("NDJOG")
        .email("ndjogdesire@gmail.com")
        .date("25/08/1997")
        .phoneNumber("689543854").build();
  }

  @Test
  @DisplayName(value = "valide and create person when valid")
  void shouldGivePersonDTOAndValidateAndCreateAndReturPersonDTO() {
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

  @Test
  @DisplayName(value = "given person dto without lastname should throw validation exception")
  void given_person_dto_without_lastname_for_create_should_throw_validation_exception() {
    //Given
    PersonneDTO person = PersonneDTO.builder()
        .id(null)
        .firstName("Desire Ngono")
        .email("ndjogdesire@gmail.com")
        .date("25/08/1997")
        .phoneNumber("689543854").build();

    //When
    ValidationException exception = assertThrows(ValidationException.class, () -> personImpl.create(person));

    //Then
    assertThat(exception).isInstanceOf(ValidationException.class);
    assertThat(exception.getMessage()).isEqualTo("last is empty");
  }

  @Test
  @DisplayName(value = "update person when everything is okay")
  void given_person_dto_for_update_should_updated_and_return_person_dto() {
    //Given
    PersonneDTO personneDTO = buildPerson();
    var person = Person.builder()
        .id(1L)
        .firstName("Desire Ngono")
        .lastName("NDJOG")
        .email("ndjogdesire@gmail.com")
        .birthDate(date())
        .phoneNumber("689543854").build();
    when(repository.save(any(Person.class))).thenReturn(person);

    //When
    PersonneDTO personExpected = personImpl.update(personneDTO);

    //Then
    assertThat(personExpected).isNotNull();
    assertThat(personExpected.getFirstName()).isEqualTo(personneDTO.getFirstName());
    assertThat(personExpected.getLastName()).isEqualTo(personneDTO.getLastName());
    assertThat(personExpected.getEmail()).isEqualTo(personneDTO.getEmail());
    assertThat(personExpected.getPhoneNumber()).isEqualTo(personneDTO.getPhoneNumber());
  }

  @Test
  @DisplayName(value = "don't update person when data are not good")
  void shouldNotUpdatePersonWhenNotValid() {
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
  }

  @DisplayName(value = "find personne when is ok")
  @Test()
  void shouldFindPersonByIdWhenValid() {
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
    assertThat(optionalPerson).isPresent();
    assertThat(optionalPerson.get().getFirstName()).isEqualTo(person.getFirstName());
    assertThat(optionalPerson.get().getLastName()).isEqualTo(person.getLastName());
    assertThat(optionalPerson.get().getEmail()).isEqualTo(person.getEmail());
    assertThat(optionalPerson.get().getPhoneNumber()).isEqualTo(person.getPhoneNumber());
  }

  @DisplayName(value = "not find personne when is not ok and throws exception")
  @Test()
  void shouldNotFindPersonByIdWhenNotValid() {
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
