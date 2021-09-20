package ex.com.challenge.service;

import ex.com.challenge.model.Game;
import ex.com.challenge.model.GameStatusEnum;
import ex.com.challenge.model.Player;
import ex.com.challenge.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author: e.shakeri
 */

@ExtendWith(MockitoExtension.class)
class GameServiceTests {


    private final Player firstPlayerTest = new Player("First_Player_Test");
    private final Player secondPlayerTest = new Player("Second_Player_Test");

    @Mock
    private GameRepository gameRepository;


    @InjectMocks
    private GameService gameService;


    @Test
    void createGame() {
        Game game = new Game();
        String id= UUID.randomUUID().toString();
        game.setId(id);
        game.setFirstPlayer(firstPlayerTest);
        game.setStatus(GameStatusEnum.NEW);

        Mockito.when(this.gameRepository.save(any(Game.class))).thenReturn(game);

        game = gameService.createGame(firstPlayerTest);
        assertNotNull(game.getId());
        assertEquals(firstPlayerTest, game.getFirstPlayer());
        assertEquals(GameStatusEnum.NEW, game.getStatus());

        assertEquals(game.getPits().size(), 14);
        assertEquals(game.getPitByIndex(Game.pit1FirstPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit2FirstPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit3FirstPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit4FirstPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit5FirstPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit6FirstPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.largeRightPitFirstPlayer).getStones(), 0);
        assertEquals( game.getPitByIndex(Game.pit8SecondPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit9SecondPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit10SecondPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit11SecondPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit12SecondPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.pit13SecondPlayer).getStones(), 6);
        assertEquals( game.getPitByIndex(Game.largeRightPitSecondPlayer).getStones(), 0);

    }

    @Test
    void connectToGame(){
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setFirstPlayer(firstPlayerTest);
        game.setStatus(GameStatusEnum.NEW);

        Mockito.when(this.gameRepository.findById(game.getId())).thenReturn(java.util.Optional.of(game));

        game = gameService.connectToGame(secondPlayerTest,game.getId());
        assertEquals(firstPlayerTest, game.getFirstPlayer());
        assertEquals(secondPlayerTest, game.getSecondPlayer());
        assertEquals(GameStatusEnum.IN_PROGRESS, game.getStatus());

    }

    @Test
    void connectToRandomGame(){
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setFirstPlayer(firstPlayerTest);
        game.setStatus(GameStatusEnum.NEW);

        Mockito.when(this.gameRepository.findFirstByStatusAndSecondPlayerIsNull(GameStatusEnum.NEW)).thenReturn(java.util.Optional.of(game));

        game = gameService.connectToRandomGame(secondPlayerTest);
        Assertions.assertThat(game.getFirstPlayer().equals(firstPlayerTest)).isEqualTo(true);
        Assertions.assertThat(game.getSecondPlayer().equals(secondPlayerTest)).isEqualTo(true);
        Assertions.assertThat(game.getStatus().equals(GameStatusEnum.IN_PROGRESS)).isEqualTo(true);
    }
}
