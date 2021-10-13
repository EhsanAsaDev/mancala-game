package ex.com.challenge.cucumber.glue;

import ex.com.challenge.dto.ConnectRequest;
import ex.com.challenge.model.Game;
import ex.com.challenge.model.Player;
import ex.com.challenge.model.PlayerTurnEnum;
import ex.com.challenge.model.Sow;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static ex.com.challenge.model.GameStatusEnum.IN_PROGRESS;
import static ex.com.challenge.model.GameStatusEnum.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ehsan Sh
 */


public class MancalaRestSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private ResponseEntity<String> outOfTurnResponseEntity;

    private Player firstPlayerTest;
    private Player secondPlayerTest;
    private Game game;

    @When("first user create a game {string}")
    public void whenFirstUserCreatAGame(String firstUserName) {
        firstPlayerTest = new Player(firstUserName);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Player> request = new HttpEntity<>(firstPlayerTest, headers);
        ResponseEntity<Game> gameResponseEntity = testRestTemplate.exchange("/game/create", HttpMethod.POST, request, Game.class);
        game = gameResponseEntity.getBody();
    }

    @Then("^one game is created and returned$")
    public void thenCreatAGame() {
        assertNotNull(game.getId());
        assertEquals(game.getFirstPlayer(), firstPlayerTest);
        assertEquals(game.getStatus(), NEW);
    }


    @When("second user connect to the game {string}")
    public void whenSecondUserJoinTheGame(String secondUserName) {
        secondPlayerTest = new Player(secondUserName);
        HttpHeaders headers = new HttpHeaders();
        ConnectRequest connectRequest = new ConnectRequest();
        connectRequest.setGameId(game.getId());
        connectRequest.setPlayer(secondPlayerTest);
        HttpEntity<ConnectRequest> request = new HttpEntity<>(connectRequest, headers);

        ResponseEntity<Game> gameResponseEntity = testRestTemplate.exchange("/game/connect", HttpMethod.POST, request, Game.class);
        game = gameResponseEntity.getBody();
    }

    @Then("second user joined to the game$")
    public void thenSecondUserJoinTheGame() {
        assertNotNull(game.getId());
        assertEquals(game.getFirstPlayer(), firstPlayerTest);
        assertEquals(game.getSecondPlayer(), secondPlayerTest);
        assertEquals(game.getStatus(), IN_PROGRESS);
    }


    //@When("^first user play for the first time and sow pit$")
    @When("first user play for the first time and sow pit {int}")
    public void whenFirstUserPlay1th(int pitIndex) {
        HttpHeaders headers = new HttpHeaders();
        Sow sow = new Sow();
        sow.setGameId(game.getId());
        sow.setPitIndex(pitIndex);
        HttpEntity<Sow> request = new HttpEntity<>(sow, headers);

        ResponseEntity<Game> gameResponseEntity = testRestTemplate.exchange("/game/sow", HttpMethod.POST, request, Game.class);
        game = gameResponseEntity.getBody();
    }

    @Then("pits changed {string}")
    public void thenPitsChanged(String result) {
        assertEquals(game.getPits().toString(), result);
        assertEquals(game.getPlayerTurn(), PlayerTurnEnum.SECOND_PLAYER);
    }

    @When("^first user try to play out of turn$")
    public void whenFirstUserPlayOutOfTurn() {
        HttpHeaders headers = new HttpHeaders();
        Sow sow = new Sow();
        sow.setGameId(game.getId());
        sow.setPitIndex(2);
        HttpEntity<Sow> request = new HttpEntity<>(sow, headers);

        outOfTurnResponseEntity = testRestTemplate.exchange("/game/sow", HttpMethod.POST, request, String.class);
    }

    @Then("raise message it's not your turn$")
    public void thenRaiseMessage() {
        assertNotNull(outOfTurnResponseEntity);
        assertEquals(outOfTurnResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(outOfTurnResponseEntity.getBody(), "Bad Turn or Pit!");
    }


}
