package bol.com.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
@Data
@Builder
public class Game {
    @Id
    private String id;
    private List<Pit> pits;
    private GameStatusEnum status;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player winner;

    public final static Integer pit1FirstPlayer = 1;
    public final static Integer pit2FirstPlayer = 2;
    public final static Integer pit3FirstPlayer = 3;
    public final static Integer pit4FirstPlayer = 4;
    public final static Integer pit5FirstPlayer = 5;
    public final static Integer pit6FirstPlayer = 6;
    public final static Integer largeRightPitFirstPlayer = 7;

    public final static Integer pit8SecondPlayer = 8;
    public final static Integer pit9SecondPlayer = 9;
    public final static Integer pit10SecondPlayer = 10;
    public final static Integer pit11SecondPlayer = 11;
    public final static Integer pit12SecondPlayer = 12;
    public final static Integer pit13SecondPlayer = 13;
    public final static Integer largeRightPitSecondPlayer = 14;

    public final static Integer initiateStones = 6;


    private PlayerTurnEnum playerTurn;

    public Game() {


        this.pits = Arrays.asList(
                new Pit(pit1FirstPlayer, initiateStones),
                new Pit(pit2FirstPlayer, initiateStones),
                new Pit(pit3FirstPlayer, initiateStones),
                new Pit(pit4FirstPlayer, initiateStones),
                new Pit(pit5FirstPlayer, initiateStones),
                new Pit(pit6FirstPlayer, initiateStones),
                new LargeRightPit(largeRightPitFirstPlayer, 0),
                new Pit(pit8SecondPlayer, initiateStones),
                new Pit(pit9SecondPlayer, initiateStones),
                new Pit(pit10SecondPlayer, initiateStones),
                new Pit(pit11SecondPlayer, initiateStones),
                new Pit(pit12SecondPlayer, initiateStones),
                new Pit(pit13SecondPlayer, initiateStones),
                new LargeRightPit(largeRightPitSecondPlayer, 0)
        );


        playerTurn = PlayerTurnEnum.FIRST_PLAYER;
        /*PlayerTurnEnum[] turns = PlayerTurnEnum.values();
        Random random = new Random();
        playerTurn = turns[random.nextInt(turns.length)];*/


    }

    public Pit getPitByIndex(Integer index) {
        return pits.get(index - 1);
    }
}
