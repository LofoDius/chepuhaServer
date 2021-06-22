package com.sokoldev.chepuhaserver.request;

import com.sokoldev.chepuhaserver.model.Player;

public class ConnectToGameRequest {
    public String gameCode;
    public Player player;

    public ConnectToGameRequest(String gameCode, Player player) {
        this.gameCode = gameCode;
        this.player = player;
    }

    public ConnectToGameRequest() {
    }
}
