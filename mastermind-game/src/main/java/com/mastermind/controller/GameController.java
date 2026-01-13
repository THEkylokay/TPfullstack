package com.mastermind.controller;

import com.mastermind.model.Game;
import com.mastermind.model.Combination;
import com.mastermind.util.FileSaver;

public class GameController {
    private Game currentGame;

    public void startNewGame(int pegs, int colors, int maxAttempts) {
        currentGame = new Game(pegs, colors, maxAttempts);
    }

    public int[] submitGuess(Combination guess) {
        if (currentGame == null || currentGame.isGameOver()) {
            return null;
        }
        return currentGame.evaluate(guess);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void saveCurrentGame() {
        if (currentGame != null) {
            FileSaver.saveGame(currentGame);
        }
    }

    public boolean isGameOver() {
        return currentGame != null && currentGame.isGameOver();
    }

    public boolean isGameWon() {
        return currentGame != null && currentGame.isWon();
    }
}
