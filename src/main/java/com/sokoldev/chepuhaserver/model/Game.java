package com.sokoldev.chepuhaserver.model;

import java.util.*;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Story> stories;
    private String gameCode;
    private Boolean isStarted = false;
    private int questionNumber = 0;

    private final ArrayList<String> funnyNames = new ArrayList<>(
            List.of(
                    "Сирота",
                    "Вкусные щечки",
                    "Бесяка",
                    "Вялая хватка",
                    "Усатая жопка",
                    "Юнга",
                    "Широкая кость",
                    "Кириешка",
                    "Тамада",
                    "Глубокая глотка",
                    "На подхвате",
                    "ЗАМАЙ",
                    "undefined",
                    "Милашка",
                    "Пенёк",
                    "Аниме гангрейв",
                    "Непризнанный гений",
                    "Жопа с ручкой",
                    "Одинокий волк",
                    "Пирожок с подливой",
                    "Грустный слоник",
                    "Задний привод",
                    "Человек-пердук",
                    "Голубь",
                    "Поезд пассаЖИРНЫЙ",
                    "Хохол",
                    "Горячая штучка"
            )
    );

    private ArrayList<Integer> usedFunnyNames = new ArrayList<>();

    private int answerCount = 0;

    public Game(ArrayList<Player> players, ArrayList<Story> stories, String gameCode, Boolean isStarted) {
        this.players = players;
        this.stories = stories;
        this.gameCode = gameCode;
        this.isStarted = isStarted;
    }

    public String addPlayer(Player player) {
        if (players.contains(player)) return null;
        if (isPlayerNameAlreadyTaken(player.getName())) {
            Random random = new Random();
            int index;
            do {
                index = random.nextInt(funnyNames.size());
            } while (usedFunnyNames.contains(index));

            player.setName(player.getName() + " aka " + funnyNames.get(index));
            usedFunnyNames.add(index);
        }
        players.add(player);
        stories.add(new Story());
        return player.getName();
    }

    private Boolean isPlayerNameAlreadyTaken(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) return  true;
        }
        return false;
    }

    public boolean addAnswer(Answer answer) {
        int index = getPlayerIndexByName(answer.getAuthor());
        if (index != -1) {
            stories.get(index).addAnswer(answer);
            answerCount++;
        }

        if (answerCount == players.size()) {
            questionNumber++;
            answerCount = 0;

            ArrayList<Story> newStories = new ArrayList<>();

            newStories.add(stories.get(stories.size() - 1));
            for (int i = 1; i < stories.size(); i++) {
                newStories.add(stories.get(i - 1));
            }
            stories = newStories;
            return true;
        }
        return false;
    }

    public Story getStory(UUID playerID) {
        int index = getPlayerIndexById(playerID);
        if (index == -1) return null;
        return stories.get(index);
    }

    public String getQuestion() {
        switch (questionNumber) {
            case Answer.WHO:
                return "Кто?";
            case Answer.WITH_WHO:
                return "С кем?";
            case Answer.WHERE:
                return "Где?";
            case Answer.WHEN:
                return "Когда?";
            case Answer.WHAT:
                return "Что делали?";
            case Answer.THEM_SAID:
                return "Им сказали:";
            case Answer.END:
                return "И закончилось все";
            case Answer.END + 1:
                return "game ended";
            default:
                return "";
        }
    }

    public int getPlayerIndexById(UUID id) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equals(id)) return i;
        }
        return -1;
    }

    public int getPlayerIndexByName(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(name)) return i;
        }

        return -1;
    }

    public List<Player> getPidors() {
        ArrayList<Player> pidors = new ArrayList<>();
        for (Player player : players) {
            if (getStory(player.getId()).getAnswers().size() == questionNumber) {
                pidors.add(player);
            }
        }

        return pidors;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }

    public Boolean getStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
