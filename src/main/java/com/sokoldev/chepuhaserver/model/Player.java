package com.sokoldev.chepuhaserver.model;

import java.util.UUID;

public class Player {
    private String name = "";
    private UUID id;

    public Player() {
        name = "";
        id = null;
    }

    public Player(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
