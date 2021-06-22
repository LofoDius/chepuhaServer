package com.sokoldev.chepuhaserver.model;

import java.util.ArrayList;

public class Story {
    private ArrayList<Answer> answers = new ArrayList<>();

    public Story(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public Story() {

    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
