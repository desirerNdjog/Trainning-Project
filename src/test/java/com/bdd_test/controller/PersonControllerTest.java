package com.bdd_test.controller;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.service.PersonDAOService;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author: desirejuniorndjog.
 * @created: 28/04/2024 : 07:26
 * @project: trainning
 */

@RunWith(value = SpringRunner.class)
@DisplayName(value = "Testing logic fonction of controller")
public class PersonControllerTest {
    @Mock
    private PersonDAOService daoService;
    @Mock
    private PersonneService service;
    @InjectMocks
    private PersonnController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new PersonnController(daoService, service);
    }

    @Test
    @DisplayName(value = "fetch list of persondto")
    public void givenListPersondtoAndReturnPersonListdto(){
        //given
        PersonneDTO personneDTO = PersonneDTO.builder()
                .id(1L)
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .build();

        PersonneDTO personneDTO1 = PersonneDTO.builder()
                .id(1L)
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .build();
        List<PersonneDTO> list = List.of(personneDTO, personneDTO1);
        when(daoService.findAllPerson()).thenReturn(list);

        //when
        ResponseEntity<HttpResponse> expectedResponse = controller.findAllNoPaginate();

        //then
        assertThat(expectedResponse).isNotNull();
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getDatas()).hasFieldOrPropertyWithValue("data", list);
    }

    @Test
    @DisplayName(value = "create persondto")
    public void givenPersondtoAndCreateAndReturnPersondto(){
        //given
        PersonneDTO personneDTO = PersonneDTO.builder()
                .id(null)
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .build();
        PersonneDTO personneDTO1 = PersonneDTO.builder()
                .id(1L)
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .build();
        when(service.create(any(PersonneDTO.class))).thenReturn(personneDTO1);

        //when
        ResponseEntity<HttpResponse> expectedResponse = controller.create(personneDTO);

        //then
        assertThat(expectedResponse).isNotNull();
        assertThat(Objects.requireNonNull(expectedResponse.getBody()).getDatas()).hasFieldOrPropertyWithValue("data", personneDTO1);
    }

    @Test
    @DisplayName(value = "succed find persondto by id")
    public void givenPersonIdAndReturnPersondto(){
        //given
        Long id = 1L;
        PersonneDTO personneDTO = PersonneDTO.builder()
                .id(1L)
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .build();
        when(service.findPersonById(anyLong())).thenReturn(Optional.of(personneDTO));

        //when
        ResponseEntity<HttpResponse> expectedResponse  = controller.findById(id);

        //then
        assertThat(expectedResponse).isNotNull();
        assertThat(expectedResponse.getBody().getDatas()).hasFieldOrPropertyWithValue("data", Optional.of(personneDTO));
    }

    @Test
    @DisplayName(value = "error find persondto by id")
    public void givenPersonIdAndReturnNoPersondto(){
        //given
        Long id = 1L;
        PersonneDTO personneDTO = PersonneDTO.builder()
                .id(1L)
                .date("25/08/1997")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .build();
        when(service.findPersonById(anyLong())).thenReturn(Optional.empty());

        //when
        ResponseEntity<HttpResponse> expectedResponse  = controller.findById(id);

        //then
        assertThat(expectedResponse).isNotNull();
        assertThat(expectedResponse.getBody().getMessage()).isEqualTo("person not found");
    }

}
