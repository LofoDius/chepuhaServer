package com.sokoldev.chepuhaserver.response;

import com.sokoldev.chepuhaserver.model.Player;

public class ConnectToGameResponse extends BaseResponse {
    public Player player;
    public ConnectToGameResponse(int code, Player player) {
        super(code);
        this.player = player;
    }
}
