package com.bdd_test.controller;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import com.bdd_test.service.PersonDAOService;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@WebMvcTest(PersonnController.class)
@ContextConfiguration(classes = {PersonnController.class})
@RunWith(SpringRunner.class)
class PersonnControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonDAOService dao;
    @MockBean
    private PersonneService service;
    private LocalDate date;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        DateTimeFormatter dateTimeFormatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = LocalDate.parse("25/08/1997", dateTimeFormatter);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName(value = "return list of all persons")
    void shouldFindAllNoPaginageWithSuccess() throws Exception{
        //Given
        var person = PersonneDTO.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("6904357843")
                .date(date)
                .email("ndjogdesire@gmail.com")
                .build();
        var personTwwo = PersonneDTO.builder()
                .firstName("Miller")
                .lastName("ETUBA")
                .phoneNumber("6904357843")
                .date(date)
                .email("ndjogdesire@gmail.com")
                .build();
        List<PersonneDTO> list = List.of(person, personTwwo);

        //When
        when(dao.findAllPerson()).thenReturn(list);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        var response = super.mapFromJson(result.getResponse().getContentAsString(), HttpResponse.class);
        assertThat(response.getCodeStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getDatas().get("data")).isNotNull();
    }

    @Test
    @DisplayName(value = "create a person with success")
    void shouldCreatePersonWhenSuccess() throws Exception{
        //Given
        var person = Person.builder()
                .id(null)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .birthDate(date).build();

        var personDTO = PersonneDTO.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .date(date).build();

        var jsonPerson = super.mapToJson(person);

        //When
        when(service.create(any(Person.class))).thenReturn(personDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/person")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)).andReturn();
        var response = super.mapFromJson(result.getResponse().getContentAsString(), HttpResponse.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getCodeStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("created");
        assertThat(response.getDatas().get("data")).isNotNull();

    }
}
