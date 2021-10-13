package ex.com.challenge.cucumber.glue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.Map;
/**
 * @author Ehsan Sh
 */


public class MancalaBrowserSteps {

    @LocalServerPort
    int serverPort;

    WebDriver firstUserBrowser = null;
    WebDriver secondUserBrowser = null;
    String gameId;
    String mainPageUrl;

    @Before
    public void initiate(){
        mainPageUrl ="http://localhost:" + serverPort + "/";
    }

    @Given("first user browser is open")
    public void firstUserBrowserIsOpen() {
        firstUserBrowser = openAndInitiateBrowser(new Point(0, 0));
    }

    private WebDriver openAndInitiateBrowser(Point position) {
        String projectPath = System.getProperty("user.dir");

        System.setProperty("webdriver.gecko.driver"
                , projectPath + "/src/test/resources/drivers/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        webDriver.manage().window().setPosition(position);
        webDriver.manage().window().setSize(new Dimension(800, 500));

        return webDriver;
    }

    @And("first user browser is on main page")
    public void firstUserIsOnMainPage() {

        firstUserBrowser.navigate().to(mainPageUrl);
    }

    @And("second user browser is open")
    public void secondUserBrowserIsOpen() {
        secondUserBrowser = openAndInitiateBrowser(new Point(750, 300));
    }

    @And("second user browser is on main page")
    public void secondUserIsOnMainPage() {

        secondUserBrowser.navigate().to(mainPageUrl);
    }

    @When("first user enter name in name box")
    public void firstUserEnterNameInNameBox() {
        firstUserBrowser.findElement(By.id("name")).sendKeys("firstUser");
    }

    @And("first user hit create button")
    public void hitCreateButton() {
        firstUserBrowser.findElement(By.name("createNewGameBtn")).sendKeys(Keys.ENTER);
    }

    @Then("one game is created")
    public void oneGameIsCreated() throws InterruptedException {
        Thread.sleep(2000);
        Alert alert = firstUserBrowser.switchTo().alert();
        if (alert.getText().startsWith("Your created a game. Game id is: ")) {
            gameId = alert.getText().substring(33);
            System.out.println(gameId);
            alert.accept();
        } else {
            Assert.fail();
        }

    }


    @When("second user enter name in the name box")
    public void secondUserEnterNameInNameBox() {
        secondUserBrowser.findElement(By.id("name")).sendKeys("secondUser");
    }

    @When("second user enter game id in the game id box")
    public void secondUserEnterGameId() {
        secondUserBrowser.findElement(By.id("game_id")).sendKeys(gameId);
    }

    @And("second user hit connect by game id button")
    public void secondUserHitConnectByGameIdButton() throws InterruptedException {
        secondUserBrowser.findElement(By.name("connectByGameIdBtn")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    @Then("second user is joined to the game")
    public void secondUserIsJoinedToTheGame() throws InterruptedException {
        Thread.sleep(2000);
        Alert alert = secondUserBrowser.switchTo().alert();
        if (alert.getText().startsWith("Congrats you're playing with: firstUser")) {
            alert.accept();
        } else {
            Assert.fail();
        }

    }

    @When("first user hit pit#2")
    public void firstUserHitPit() {
        firstUserBrowser.findElement(By.id("pit_1")).click();
    }


    @Then("board game is updated {string}")
    public void boardGameIsUpdated(String result) throws JsonProcessingException, InterruptedException {

        Thread.sleep(2000);

        ObjectReader reader = new ObjectMapper().readerFor(Map.class);
        Map<String, String> map = reader.readValue(result);


        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_0")).getText(), map.get("pit_0"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_1")).getText(), map.get("pit_1"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_2")).getText(), map.get("pit_2"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_3")).getText(), map.get("pit_3"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_4")).getText(), map.get("pit_4"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_5")).getText(), map.get("pit_5"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_6")).getText(), map.get("pit_6"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_7")).getText(), map.get("pit_7"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_8")).getText(), map.get("pit_8"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_9")).getText(), map.get("pit_9"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_10")).getText(), map.get("pit_10"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_11")).getText(), map.get("pit_11"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_12")).getText(), map.get("pit_12"));
        Assert.assertEquals(firstUserBrowser.findElement(By.id("pit_13")).getText(), map.get("pit_13"));
    }
}
