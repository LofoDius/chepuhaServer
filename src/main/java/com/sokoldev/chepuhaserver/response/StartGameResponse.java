package com.sokoldev.chepuhaserver.response;

import java.util.UUID;

public class StartGameResponse extends BaseResponse {
    public String gameCode;

    public StartGameResponse(int code, String gameCode) {
        super(code);
        this.gameCode = gameCode;
    }
}
