package com.sokoldev.chepuhaserver.request;

import com.sokoldev.chepuhaserver.model.Answer;


public class AnswerRequest {
    public Answer answer;
    public String gameCode;

    public AnswerRequest(Answer answer, String gameCode) {
        this.answer = answer;
        this.gameCode = gameCode;
    }

    public AnswerRequest() {
    }
}
