package com.bdd_test.controller;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.service.PersonDAOService;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
        when(dao.findAllPerson()).thenReturn(List.of(person, personTwo));

        //When
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
        PersonneDTO personDTO = PersonneDTO.builder()
                .id(null)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .date("25/08/1997").build();

        PersonneDTO personDTOTwo = PersonneDTO.builder()
                .id(1L)
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .date("25/08/1997").build();

        String jsonPerson = super.mapToJson(personDTO);
        when(service.create(any(PersonneDTO.class))).thenReturn(personDTOTwo);

        //When
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

    @Test
    @DisplayName(value = "find person by identifinat")
    void shouldFindPersonByIdWhhenSuccess() throws Exception {
        //Given
        var person = PersonneDTO.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .date("25/08/1997").build();
        when(service.findPersonById(anyLong())).thenReturn(Optional.of(person));

        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person/1")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        var response = super.mapFromJson(result.getResponse().getContentAsString(), HttpResponse.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("find");
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getCodeStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getDatas()).isNotEmpty();
    }

    @Test
    @DisplayName(value = "person not find")
    void shouldNotFindPersonByIdWhenNotFoundable() throws Exception {
        //Given
        var person = PersonneDTO.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .email("ndjogdesire@gmail.com")
                .phoneNumber("690134110")
                .date("25/08/1997").build();
        when(service.findPersonById(anyLong())).thenReturn(Optional.empty());

        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person/100")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        var response = super.mapFromJson(result.getResponse().getContentAsString(), HttpResponse.class);

        //Then
        assertThat(response.getMessage()).isEqualTo("person not found");
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getCodeStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
