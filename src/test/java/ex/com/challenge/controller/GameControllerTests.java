package ex.com.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ex.com.challenge.dto.ConnectRequest;
import ex.com.challenge.model.*;
import ex.com.challenge.service.GameService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.Arrays;

import static ex.com.challenge.model.Game.largeRightPitFirstPlayer;
import static ex.com.challenge.model.Game.largeRightPitSecondPlayer;
import static ex.com.challenge.model.Game.pit10SecondPlayer;
import static ex.com.challenge.model.Game.pit11SecondPlayer;
import static ex.com.challenge.model.Game.pit12SecondPlayer;
import static ex.com.challenge.model.Game.pit13SecondPlayer;
import static ex.com.challenge.model.Game.pit1FirstPlayer;
import static ex.com.challenge.model.Game.pit2FirstPlayer;
import static ex.com.challenge.model.Game.pit3FirstPlayer;
import static ex.com.challenge.model.Game.pit4FirstPlayer;
import static ex.com.challenge.model.Game.pit5FirstPlayer;
import static ex.com.challenge.model.Game.pit6FirstPlayer;
import static ex.com.challenge.model.Game.pit8SecondPlayer;
import static ex.com.challenge.model.Game.pit9SecondPlayer;
import static ex.com.challenge.model.GameStatusEnum.IN_PROGRESS;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: e.shakeri
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class GameControllerTests {

    @MockBean
    private GameService gameService;


    @Autowired
    private MockMvc mockMvc;

    private final Resource gameCreateJson = new ClassPathResource("game-create.json");
    private final Resource gameConnectJson = new ClassPathResource("game-connect.json");
    private final Resource gameSowJson = new ClassPathResource("game-sow.json");
    private final Resource gameRandomConnectJson = new ClassPathResource("game-random-connect.json");

    @Test
    void creatTest() throws Exception {

        Game game = new Game();
        game.setId("91f69bc5-803c-46e5-bc30-4a6b71fd860e");
        Player player = new Player("first_player");
        game.setFirstPlayer(player);
        game.setStatus(GameStatusEnum.NEW);

        Mockito.when(gameService.createGame(player)).thenReturn(game);

        this.mockMvc.perform(post("/game/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(player)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(asJson(gameCreateJson), false))
                .andReturn();

    }

    @Test
    void connectTest() throws Exception {

        Game game = new Game();
        game.setId("91f69bc5-803c-46e5-bc30-4a6b71fd860e");
        Player player = new Player("first_player");
        game.setFirstPlayer(player);
        Player secondPlayer = new Player("second_player");
        game.setSecondPlayer(secondPlayer);
        game.setStatus(IN_PROGRESS);

        ConnectRequest connectRequest = new ConnectRequest();
        connectRequest.setGameId(game.getId());
        connectRequest.setPlayer(secondPlayer);

        Mockito.when(gameService.connectToGame(secondPlayer, game.getId())).thenReturn(game);

        this.mockMvc.perform(post("/game/connect")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(connectRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(asJson(gameConnectJson), false))
                .andReturn();

    }

    @Test
    void connectRandomTest() throws Exception {

        Game game = new Game();
        game.setId("91f69bc5-803c-46e5-bc30-4a6b71fd860e");
        Player player = new Player("first_player");
        game.setFirstPlayer(player);
        Player secondPlayer = new Player("second_player");
        game.setSecondPlayer(secondPlayer);
        game.setStatus(IN_PROGRESS);

        Mockito.when(gameService.connectToRandomGame(secondPlayer)).thenReturn(game);

        this.mockMvc.perform(post("/game/connect/random")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(secondPlayer)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(asJson(gameConnectJson), false))
                .andReturn();

    }

    @Test
    void sowTest() throws Exception {

        Game game = new Game();
        game.setId("91f69bc5-803c-46e5-bc30-4a6b71fd860e");
        Player player = new Player("first_player");
        game.setFirstPlayer(player);
        Player secondPlayer = new Player("second_player");
        game.setSecondPlayer(secondPlayer);
        game.setStatus(IN_PROGRESS);
        game.setPits(Arrays.asList(new Pit(pit1FirstPlayer, 6),
                new Pit(pit2FirstPlayer, 6),
                new Pit(pit3FirstPlayer, 6),
                new Pit(pit4FirstPlayer, 0),
                new Pit(pit5FirstPlayer, 7),
                new Pit(pit6FirstPlayer, 7),
                new LargeRightPit(largeRightPitFirstPlayer, 1),
                new Pit(pit8SecondPlayer, 7),
                new Pit(pit9SecondPlayer, 7),
                new Pit(pit10SecondPlayer, 7),
                new Pit(pit11SecondPlayer, 7),
                new Pit(pit12SecondPlayer, 6),
                new Pit(pit13SecondPlayer, 6),
                new LargeRightPit(largeRightPitSecondPlayer, 0)));

        Sow sow = new Sow();
        sow.setGameId(game.getId());
        sow.setPitIndex(4);

        Mockito.when(gameService.sow(eq(sow))).thenReturn(game);

        this.mockMvc.perform(post("/game/sow")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(sow)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(asJson(gameSowJson), false))
                .andReturn();

    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private String asJson(Resource resource) {
        return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
    }

}
