package com.bdd_test.controller;

import com.bdd_test.dao.PersonDAO;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author: desirejuniorndjog.
 * @created: 24/04/2024 : 16:52
 * @project: trainning
 */

@RunWith(MockitoJUnitRunner.class)
 class PersonControllerTest {
     @Mock
     private PersonDAO dao;
     @Mock
     private PersonneService service;
     private PersonnController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new PersonnController(dao, service);
    }

    private PersonneDTO buildPersondDTO(){
        return PersonneDTO.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .build();
    }

    @Test
    @DisplayName(value = "give a list of persondto and return list of persondto")
    void give_list_of_persondto_and_return_list_of_persondto(){
        //Given
        PersonneDTO person = PersonneDTO.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .build();
        PersonneDTO personTwo = PersonneDTO.builder()
                .id(2L)
                .firstName("Miller")
                .lastName("ETUBA")
                .phoneNumber("6904357843")
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .build();
        List<PersonneDTO> personneDTOList = List.of(person, personTwo);
        when(dao.findAllPerson()).thenReturn(personneDTOList);

        //when
        ResponseEntity<HttpResponse> response = controller.findAllNoPaginate();

        //then
        assertThat(response).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getCodeStatus()).isEqualTo(200);
        assertThat(Objects.requireNonNull(response.getBody()).getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("list person");
        assertThat(response.getBody().getDatas()).hasFieldOrPropertyWithValue("data", personneDTOList);
    }

    @Test
    @DisplayName(value = "give a persondt valid, create and return persondto")
    void give_persondto_valid_and_create_and_return_persondto(){
        //given
        PersonneDTO  personneDTO = buildPersondDTO();
        PersonneDTO person = PersonneDTO.builder()
                .id(null)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .build();
        when(service.create(any(PersonneDTO.class))).thenReturn(personneDTO);

        //when
        ResponseEntity<HttpResponse> expectedResponse = controller.create(person);

        //then
        assertThat(expectedResponse).isNotNull();
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getCodeStatus()).isEqualTo(200);
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getMessage()).isEqualTo("created");
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getDatas()).hasFieldOrPropertyWithValue("data", personneDTO);
    }

    @Test
    @DisplayName(value = "give persondto id and return persondto found")
    void give_persondto_id_and_return_persondto_found(){
        //given
        Long id = 1L;
        PersonneDTO personneDTO = buildPersondDTO();
        when(service.findPersonById(anyLong())).thenReturn(Optional.of(personneDTO));

        //when
        ResponseEntity<HttpResponse> expectedResponse = controller.findById(id);
        assertThat(expectedResponse).isNotNull();
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getCodeStatus()).isEqualTo(200);
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getMessage()).isEqualTo("find");
        assertThat(expectedResponse.getBody().getDatas()).hasFieldOrPropertyWithValue("data", Optional.of(personneDTO));

    }

    @Test
    @DisplayName(value = "give persondto id and return no persondto")
    void give_persondto_id_and_return_no_persondto_found(){
        //given
        Long id = 1L;
        when(service.findPersonById(anyLong())).thenReturn(Optional.empty());

        //when
        ResponseEntity<HttpResponse> expectedResponse = controller.findById(id);

        //then
        assertThat(expectedResponse).isNotNull();
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getCodeStatus()).isEqualTo(404);
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getMessage()).isEqualTo("person not found");
    }

}
