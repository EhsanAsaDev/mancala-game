package ex.com.challenge.repository;

import ex.com.challenge.model.Game;
import ex.com.challenge.model.GameStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


/**
 * @author: e.shakeri
 */
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
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
