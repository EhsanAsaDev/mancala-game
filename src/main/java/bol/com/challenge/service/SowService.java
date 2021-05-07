package bol.com.challenge.service;

import bol.com.challenge.exception.GameException;
import bol.com.challenge.model.*;
import org.springframework.stereotype.Service;

/**
 * @author Ehsan Sh
 */

@Service
public class SowService {

    public Game sow(Game game, Integer pitIndex) {

        Pit selectedPit = game.getPitByIndex(pitIndex);

        //Exception
        if ((game.getPlayerTurn().equals(PlayerTurnEnum.FIRST_PLAYER) && pitIndex > 6)
                ||
                (game.getPlayerTurn().equals(PlayerTurnEnum.SECOND_PLAYER) && pitIndex < 7)) {
            throw new GameException("Bad Turn or Pit!");

        }
        if (pitIndex.equals(Game.largeRightPitFirstPlayer) || pitIndex.equals(Game.largeRightPitSecondPlayer)) {
            throw new GameException("largeRightPit couldn't have sow!");
        }
        if (selectedPit.getStones() == 0) {
            throw new GameException("The selected pit has 0 stones!");
        }
        //GameOver

        //Rules
        Integer currentPitIndex = pitIndex;
        for (int i = 1; i <= selectedPit.getStones() - 1; i++) {
            currentPitIndex = (pitIndex + i) <= 14 ? (pitIndex + i) : (pitIndex + i) % 14;

            if (ifCurrentPitIndexIs_a_LargeRightPit_but_belongToThePlayerWhoNowIsNotTurn(game, currentPitIndex)) {
                currentPitIndex++;
            }

            game.getPitByIndex(currentPitIndex).sow();
        }

        selectedPit.setStones(0);

        currentPitIndex = (currentPitIndex + 1) <= 14 ? (currentPitIndex + 1) : (currentPitIndex + 1) % 14;
        if (ifCurrentPitIndexIs_a_LargeRightPit_but_belongToThePlayerWhoNowIsNotTurn(game, currentPitIndex)) {
            currentPitIndex++;
        }
        Pit lastPin = game.getPitByIndex(currentPitIndex);
        Pit oppositePit = game.getPitByIndex(14 - currentPitIndex != 0 ? 14 - currentPitIndex : 7);

        //Capture
        if (!lastPin.getId().equals(Game.largeRightPitFirstPlayer) &&
                !lastPin.getId().equals(Game.largeRightPitSecondPlayer) &&
                lastPin.isEmpty() &&
                !oppositePit.isEmpty()) {

            Integer oppositeStones = oppositePit.getStones();
            Pit largeRightPit = currentPitIndex < Game.largeRightPitFirstPlayer ? game.getPitByIndex(Game.largeRightPitFirstPlayer)
                    : game.getPitByIndex(Game.largeRightPitSecondPlayer);
            largeRightPit.addStones(oppositeStones + 1);
            oppositePit.setStones(0);

        } else {
            lastPin.sow();
        }


        //End Game
        if (game.getPitByIndex(Game.largeRightPitSecondPlayer).getStones() +
                game.getPitByIndex(Game.largeRightPitSecondPlayer).getStones() == 72) {
            game.setStatus(GameStatusEnum.FINISHED);
            //@todo specify winner
            return game;
        }


        //player turn
        if (!((game.getPlayerTurn().equals(PlayerTurnEnum.FIRST_PLAYER) && lastPin.getId().equals(Game.largeRightPitFirstPlayer)) ||
                (game.getPlayerTurn().equals(PlayerTurnEnum.SECOND_PLAYER) && lastPin.getId().equals(Game.largeRightPitSecondPlayer))
        )) {
            game.setPlayerTurn(PlayerTurnEnum.togglePlayerTurn(game.getPlayerTurn()));
        }


        return game;
    }

    private boolean ifCurrentPitIndexIs_a_LargeRightPit_but_belongToThePlayerWhoNowIsNotTurn(Game game, Integer currentPitIndex) {
        return (game.getPlayerTurn().equals(PlayerTurnEnum.FIRST_PLAYER) && currentPitIndex == Game.largeRightPitSecondPlayer) ||
                (game.getPlayerTurn().equals(PlayerTurnEnum.SECOND_PLAYER) && currentPitIndex == Game.largeRightPitFirstPlayer);

    }

}   
