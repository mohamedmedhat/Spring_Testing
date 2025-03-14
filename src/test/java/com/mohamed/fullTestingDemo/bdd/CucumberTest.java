package com.mohamed.fullTestingDemo.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.mohamed.fullTestingDemo.bdd.steps",
        plugin = {"pretty", "json:target/cucumber-report.json"}
)
public class CucumberTest {
}