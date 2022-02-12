package com.sokoldev.chepuhaserver.controller;

import com.sokoldev.chepuhaserver.model.Game;
import com.sokoldev.chepuhaserver.model.Player;
import com.sokoldev.chepuhaserver.model.Story;
import com.sokoldev.chepuhaserver.request.AnswerRequest;
import com.sokoldev.chepuhaserver.request.ConnectToGameRequest;
import com.sokoldev.chepuhaserver.request.StartGameRequest;
import com.sokoldev.chepuhaserver.request.PlayerRequest;
import com.sokoldev.chepuhaserver.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class GameController {
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private HashMap<String, Game> games;
    private final MessageSendingOperations<String> messageSendingOperations;

    @Autowired
    public GameController(MessageSendingOperations<String> messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
        games = new HashMap<>();
    }

    @PostMapping("/create")
    public StartGameResponse createGame(@RequestBody StartGameRequest request) {
        String gameCode;
        do {
            gameCode = getGameCode();
        } while (games.containsKey(gameCode));
        games.put(gameCode, new Game(new ArrayList<>(), new ArrayList<>(), gameCode, false));
        games.get(gameCode).addPlayer(request.starter);
        return new StartGameResponse(0, gameCode);
    }

    private String getGameCode() {
        StringBuilder gameCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            gameCode.append(alphabet[random.nextInt(25)]);
        }
        return gameCode.toString();
    }

    @PostMapping("/start/{gameCode}")
    public BaseResponse startGame(@PathVariable String gameCode) {
        gameCode = gameCode.toLowerCase(Locale.ROOT);
        if (games.containsKey(gameCode) && !games.get(gameCode).getStarted()) {
            games.get(gameCode).setStarted(true);
            messageSendingOperations.convertAndSend("/topic/gameStarted/" + gameCode, "Started");
            return new BaseResponse(0);
        }


        return new BaseResponse(1);
    }

    @PostMapping("/connect")
    public BaseResponse connectToGame(@RequestBody ConnectToGameRequest request) {
        System.out.println("Connect from: " + request.player.getName());
        request.gameCode = request.gameCode.toLowerCase(Locale.ROOT);

        if (!games.containsKey(request.gameCode))
            return new BaseResponse(1);

        if (games.get(request.gameCode).getQuestionNumber() != 0) {
            if (games.get(request.gameCode).getPlayers().stream().anyMatch(player -> player.getId().equals(request.player.getId()))) {
                return new BaseResponse(0);
            } else return new BaseResponse(2);
        }

        if (games.get(request.gameCode).getPlayerIndexById(request.player.getId()) == -1) {
            String name = games.get(request.gameCode).addPlayer(request.player);
            request.player.setName(name);
            messageSendingOperations.convertAndSend("/topic/connectedPlayers/" + request.gameCode, request.player);
            return new BaseResponse(0);
        }
        return new BaseResponse(3);
    }

    @GetMapping("/currentQuestion/{gameCode}")
    public QuestionResponse getCurrentQuestion(@PathVariable String gameCode) {
        if (games.containsKey(gameCode)) {
            return new QuestionResponse(0, games.get(gameCode).getQuestion(), games.get(gameCode).getQuestionNumber());
        } else return new QuestionResponse(1, "", -1);
    }

    @PostMapping("/connectedPlayers/{gameCode}")
    public List<Player> getConnectedPlayers(@PathVariable String gameCode) {
        gameCode = gameCode.toLowerCase(Locale.ROOT);
        if (!games.containsKey(gameCode)) return null;
        System.out.println("\n\n[GET CONNECTED PLAYERS] gameCode: " + gameCode + "\n\n");
        return games.get(gameCode).getPlayers();
    }

    @PostMapping("/message")
    public BaseResponse sendAnswer(@RequestBody AnswerRequest request) {
        request.gameCode = request.gameCode.toLowerCase(Locale.ROOT);
        if (games.containsKey(request.gameCode)) {
            boolean isNewQuestion = games.get(request.gameCode).addAnswer(request.answer);

            if (isNewQuestion) {
                sendQuestion(request.gameCode);
            }
            return new BaseResponse(0);
        }
        return new BaseResponse(1);
    }

    private void sendQuestion(String gameCode) {
        String question = games.get(gameCode).getQuestion();
        int questionNumber = games.get(gameCode).getQuestionNumber();
        messageSendingOperations.convertAndSend("/topic/question/" + gameCode,
                new QuestionResponse(0, question, questionNumber));
        System.out.println("Send Question: " + question);
    }

    @PostMapping("/story")
    public StoryResponse getStory(@RequestBody PlayerRequest request) {
        request.gameCode = request.gameCode.toLowerCase(Locale.ROOT);
        Story story = games.get(request.gameCode).getStory(request.playerID);
        if (story == null) return new StoryResponse(1, null);
        else return new StoryResponse(0, story);
    }

    @PostMapping("/pidors")
    public List<Player> getPidors(@RequestBody PlayerRequest request) {
        request.gameCode = request.gameCode.toLowerCase(Locale.ROOT);
        Game game = games.get(request.gameCode);
        return game != null ?
                game.getPidors() :
                null;
    }
}
