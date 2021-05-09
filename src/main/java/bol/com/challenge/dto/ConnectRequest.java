package bol.com.challenge.dto;

import bol.com.challenge.model.Player;
import lombok.Data;

/**
 * @author: e.shakeri
 */
@Data
public class ConnectRequest {
    private Player player;
    private String gameId;
}
