package com.sokoldev.chepuhaserver.request;

import java.util.UUID;

public class PlayerRequest {
    public UUID playerID;
    public String gameCode;

    public PlayerRequest(UUID playerID, String gameCode) {
        this.playerID = playerID;
        this.gameCode = gameCode;
    }

    public PlayerRequest() {
    }
}
