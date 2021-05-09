package bol.com.challenge.repository;

import bol.com.challenge.model.Game;
import bol.com.challenge.model.GameStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author: e.shakeri
 */
public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findFirstByStatusAndSecondPlayerIsNull(GameStatusEnum status);
}
