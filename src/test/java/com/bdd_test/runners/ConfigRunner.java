package com.bdd_test.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author: desirejuniorndjog.
 * @created: 17/05/2024 : 00:59
 * @project: trainning
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.bdd_test.steps"},
        plugin = {"pretty", "html:target/reports/report.html"}
)
public class ConfigRunner {

}
