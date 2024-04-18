package com.bdd_test.dao;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PersonDAOTest {
    @Mock
    private PersonDAO dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled(value = "do not yet test")
    void shouldFindAllPersonWhenValid(){
        //Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = PersonneDTO.builder()
                .firstName("Desire Junior")
                .lastName("NDJOG")
                .phoneNumber("690865679")
                .date(date)
                .email("ndjogdesire@gmail.com")
                .build();
        var persontwo = PersonneDTO.builder()
                .firstName("Diland Miller")
                .lastName("ETUBA")
                .phoneNumber("698549032")
                .date(date)
                .email("etubadiland@gmail.com")
                .build();
        List<PersonneDTO> list = List.of(person, persontwo);

        //When
        when(dao.findAllPerson()).thenReturn(list);

        //Then
        assertThat(list).isNotEmpty()
                .hasSize(2);
    }
}
