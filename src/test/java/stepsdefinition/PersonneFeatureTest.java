package stepsdefinition;

import com.bdd_test.models.Person;
import com.bdd_test.service.PersonDAOService;
import com.bdd_test.service.PersonneService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class PersonneFeatureTest {
    private PersonneService service;
    private PersonDAOService daoService;

    public PersonneFeatureTest(PersonneService service, PersonDAOService daoService){
        this.service = service;
        this.daoService = daoService;
    }
    @Given("Given a list of person with size")
    public void given_a_list_of_person_with_size() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("call the service of user creation and create user")
    public void call_the_service_of_user_creation_and_create_user() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("call list of person and check user created")
    public void call_list_of_person_and_check_user_created() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
