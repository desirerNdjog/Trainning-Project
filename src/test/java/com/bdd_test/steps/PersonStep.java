package com.bdd_test.steps;

import com.bdd_test.config.TestConfig;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.service.PersonneService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: desirejuniorndjog.
 * @created: 10/05/2024 : 21:37
 * @project: trainning
 */

@SpringBootTest(classes = TestConfig.class)
public class PersonStep {

    @Autowired
    private PersonneService service;

    private PersonneDTO personneDTO;
    private PersonneDTO expectedPersonDTO;

    @Given("person with all required fields")
    public void personWithAllRequiredFields(DataTable dataTable) {
        List<Map<String, String>> personMap = dataTable.asMaps(String.class, String.class);
        personneDTO = PersonneDTO.builder()
                .id(Long.valueOf(personMap.get(0).get("id")))
                .firstName(personMap.get(0).get("firstName"))
                .lastName(personMap.get(0).get("lastName"))
                .email(personMap.get(0).get("email"))
                .phoneNumber(personMap.get(0).get("phoneNumber"))
                .date(personMap.get(0).get("date"))
                .build();
    }

    @When("created and return persondto")
    public void createdAndReturnPersondto() {
        expectedPersonDTO = service.create(personneDTO);
    }

    @Then("check if created person with required field")
    public void checkIfCreatedPersonWithRequiredField() {
        assertThat(expectedPersonDTO).isNotNull();
        assertThat(expectedPersonDTO.getId()).isEqualTo(personneDTO.getId());
        assertThat(expectedPersonDTO.getFirstName()).isEqualTo(personneDTO.getFirstName());
        assertThat(expectedPersonDTO.getLastName()).isEqualTo(personneDTO.getLastName());
        assertThat(expectedPersonDTO.getPhoneNumber()).isEqualTo(personneDTO.getPhoneNumber());
        assertThat(expectedPersonDTO.getDate()).isEqualTo(personneDTO.getDate());
    }
}
