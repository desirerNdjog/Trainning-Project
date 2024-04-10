package com.bdd_test.dao;

import com.bdd_test.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

class PersonDAOTest {

    private PersonDAO dao;

    @BeforeEach
    void setUp() {
        dao = Mockito.mock(PersonDAO.class);
    }

    @Test
    void shouldFindAllPersonWhenValid(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("25/08/1997",  formatter);
        var person = new Person(
                1L,
                "Desire Junior",
                "NDJOG",
                "690134110",
                date
        );
        var person1 = new Person(
                2L,
                "Desire Junior",
                "ETUBA",
                "690134110",
                date
        );
        List<Person> list = List.of(person, person1);
        when(dao.findAllPerson()).thenReturn(list);
        assertThat(list).isNotEmpty()
                .hasSize(2);
    }
}
