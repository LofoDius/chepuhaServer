package com.sokoldev.chepuhaserver.model;


public class Answer {
    public static final int WHO = 0;
    public static final int WITH_WHO = 1;
    public static final int WHERE = 2;
    public static final int WHEN = 3;
    public static final int WHAT = 4;
    public static final int THEM_SAID = 5;
    public static final int END = 6;

    private int questionNumber;
    private String text;
    private String author;

    public Answer() {
    }

    public Answer(int questionNumber, String text, String author) {
        this.questionNumber = questionNumber;
        this.text = text;
        this.author = author;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
