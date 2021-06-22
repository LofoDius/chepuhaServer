package com.sokoldev.chepuhaserver.response;

public class QuestionResponse extends BaseResponse {
    public String question;
    public int questionNumber;

    public QuestionResponse(int code, String question, int questionNumber) {
        super(code);
        this.question = question;
        this.questionNumber = questionNumber;
    }
}
