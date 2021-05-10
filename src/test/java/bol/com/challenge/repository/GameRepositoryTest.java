package bol.com.challenge.repository;

import bol.com.challenge.model.Game;
import bol.com.challenge.model.GameStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.util.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


/**
 * @author: e.shakeri
 */

@DataMongoTest
class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Test
    void testRepository() throws  Exception {
        this.gameRepository.deleteAll();

        Game game = new Game();
        game.setId("91f69bc5-803c-46e5-bc30-4a6b71fd860e");
        game.setStatus(GameStatusEnum.NEW);

        Game saved= this.gameRepository.save(game);
        Assertions.assertNotNull(saved.getId());

        Optional<Game> fetch= this.gameRepository.findFirstByStatusAndSecondPlayerIsNull(GameStatusEnum.NEW);
        Assertions.assertFalse(fetch.isEmpty());


        Optional<Game> fetchById  = this.gameRepository.findById(game.getId());
        Assertions.assertFalse( fetchById.isEmpty());
    }




}
