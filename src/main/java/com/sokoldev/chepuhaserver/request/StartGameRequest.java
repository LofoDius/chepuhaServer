package com.sokoldev.chepuhaserver.request;

import com.sokoldev.chepuhaserver.model.Player;

public class StartGameRequest {
    public Player starter;

    public StartGameRequest(Player starter) {
        this.starter = starter;
    }

    public StartGameRequest() {
    }

    public Player getStarter() {
        return starter;
    }

    public void setStarter(Player starter) {
        this.starter = starter;
    }
}
