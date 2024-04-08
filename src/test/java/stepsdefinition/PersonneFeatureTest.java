package stepsdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class PersonneFeatureTest {

    @Given("testun")
    public void testun() {
        System.out.println("je suis la un");
    }
    @When("testdeux")
    public void testdeux() {
        System.out.println("je suis la deux");
    }
    @Then("testtrois")
    public void testtrois() {
        System.out.println("je suis la trois");
    }
}
