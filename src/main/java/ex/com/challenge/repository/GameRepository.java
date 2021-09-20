package ex.com.challenge.repository;

import ex.com.challenge.model.Game;
import ex.com.challenge.model.GameStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author: e.shakeri
 */
public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findFirstByStatusAndSecondPlayerIsNull(GameStatusEnum status);
}
