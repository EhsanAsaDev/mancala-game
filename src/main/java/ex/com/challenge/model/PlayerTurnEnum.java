package ex.com.challenge.model;

/**
 * @author Ehsan Sh
 */


public enum PlayerTurnEnum {
    FIRST_PLAYER, SECOND_PLAYER;

    public static PlayerTurnEnum togglePlayerTurn(PlayerTurnEnum currentPlayerTurn) {
        switch (currentPlayerTurn) {
            case FIRST_PLAYER: return SECOND_PLAYER;
            case SECOND_PLAYER: return FIRST_PLAYER;
            default: throw new RuntimeException("Invalid player turn");
        }
    }
}   
