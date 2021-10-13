package ex.com.challenge.cucumber;

import ex.com.challenge.MancalaChallengeApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ehsan Sh
 */

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {
        MancalaChallengeApplication.class,
        CucumberIntegrationTest.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberOptions(
        plugin = { "pretty", "html:target/reports/cucumber.html" ,
                "json:target/reports/cucumber.json",
                "junit:target/reports/cucumber.xml"},
        tags = "@smokeTest",
        features = "src/test/resources/features")
public class CucumberIntegrationTest {

    public CucumberIntegrationTest() {
    }
}
