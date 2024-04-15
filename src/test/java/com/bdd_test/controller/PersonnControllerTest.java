package com.bdd_test.controller;

import com.bdd_test.dao.PersonDAO;
import com.bdd_test.models.Person;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {PersonnController.class})
@ContextConfiguration(classes = {PersonnController.class})
@RunWith(MockitoJUnitRunner.class)
class PersonnControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonDAO dao;
    @MockBean
    private PersonneService service;
    private String path;
    private LocalDate date;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        DateTimeFormatter dateTimeFormatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = LocalDate.parse("25/08/1997", dateTimeFormatter);
        this.path = "/api/v1/person";
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Disabled("don't run the test")
    void shouldFindAllNoPaginageWithSuccess() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(this.path)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        var response = super.mapFromJson(result.getResponse().getContentAsString(), HttpResponse.class);
        assertThat(response.getCodeStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Disabled(value = "don't run this test")
    void shouldCreatePersonWhenSuccess() throws Exception{
        Person  person = new Person(
                null,
                "Audrey Thalia",
                "CHIMI",
                "690134110",
                date
        );
        var jsonPerson = super.mapToJson(person);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(this.path)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)).andReturn();
        var response = super.mapFromJson(result.getResponse().getContentAsString(), HttpResponse.class);
        assertThat(response.getCodeStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("create");
    }

    @Test
    void shouldNotCreatePersonWhenError(){

    }
}
