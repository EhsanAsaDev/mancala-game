package bol.com.challenge.service;

import bol.com.challenge.model.Game;
import bol.com.challenge.model.GameStatusEnum;
import bol.com.challenge.model.Player;
import bol.com.challenge.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static bol.com.challenge.model.GameStatusEnum.IN_PROGRESS;
import static bol.com.challenge.model.GameStatusEnum.NEW;

/**
 * @author: e.shakeri
 */

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class GameServiceTests {


    private Game game;
    private Player firstPlayerTest = new Player("First_Player_Test");
    private Player secondPlayerTest = new Player("Second_Player_Test");

    @MockBean
    private GameRepository gameRepository;


    @Autowired
    private GameService gameService;


    @Test
    void createGame() {

        Mockito.when(this.gameRepository.save(game)).thenReturn(game);

        game = gameService.createGame(firstPlayerTest);
        Assertions.assertThat(game.getId()!= null);
        Assertions.assertThat(game.getFirstPlayer().equals(firstPlayerTest));
        Assertions.assertThat(game.getStatus().equals(GameStatusEnum.NEW));

        Assertions.assertThat(game.getPits().size()==14);
        Assertions.assertThat(game.getPitByIndex(Game.pit1FirstPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit2FirstPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit3FirstPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit4FirstPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit5FirstPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit6FirstPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.largeRightPitFirstPlayer).getStones().equals(0));
        Assertions.assertThat(game.getPitByIndex(Game.pit8SecondPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit9SecondPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit10SecondPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit11SecondPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit12SecondPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.pit13SecondPlayer).getStones().equals(6));
        Assertions.assertThat(game.getPitByIndex(Game.largeRightPitSecondPlayer).getStones().equals(0));

    }

    @Test
    void connectToGame(){
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setFirstPlayer(firstPlayerTest);
        game.setStatus(NEW);

        Mockito.when(this.gameRepository.findById(game.getId())).thenReturn(java.util.Optional.of(game));

        game = gameService.connectToGame(secondPlayerTest,game.getId());
        Assertions.assertThat(game.getFirstPlayer().equals(firstPlayerTest)).isEqualTo(true);
        Assertions.assertThat(game.getSecondPlayer().equals(secondPlayerTest)).isEqualTo(true);
        Assertions.assertThat(game.getStatus().equals(IN_PROGRESS)).isEqualTo(true);

    }

    @Test
    void connectToRandomGame(){
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setFirstPlayer(firstPlayerTest);
        game.setStatus(NEW);

        Mockito.when(this.gameRepository.findFirstByStatusAndSecondPlayerIsNull(GameStatusEnum.NEW)).thenReturn(java.util.Optional.of(game));

        game = gameService.connectToRandomGame(secondPlayerTest);
        Assertions.assertThat(game.getFirstPlayer().equals(firstPlayerTest)).isEqualTo(true);
        Assertions.assertThat(game.getSecondPlayer().equals(secondPlayerTest)).isEqualTo(true);
        Assertions.assertThat(game.getStatus().equals(IN_PROGRESS)).isEqualTo(true);
    }
}
