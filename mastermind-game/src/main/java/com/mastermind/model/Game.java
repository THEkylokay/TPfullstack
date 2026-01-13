package com.mastermind.model;

import com.mastermind.util.Constants;

public class Game {
    private final int pegs;
    private final int colors;
    private final int maxAttempts;
    private final Combination secret;
    private final GameHistory history;
    private int attemptCount = 0;
    private boolean won = false;

    public Game(int pegs, int colors, int maxAttempts) {
        if (pegs < 2 || pegs > Constants.MAX_PEGS) 
            throw new IllegalArgumentException("pegs must be 2-" + Constants.MAX_PEGS);
        if (colors < 2 || colors > Constants.COLOR_NAMES.length - 1) 
            throw new IllegalArgumentException("colors invalid");

        this.pegs = pegs;
        this.colors = colors;
        this.maxAttempts = maxAttempts;
        this.secret = Combination.generateSecret(pegs, colors);
        this.history = new GameHistory();
    }

    public boolean isWon() { return won; }
    public boolean isGameOver() { return won || attemptCount >= maxAttempts; }
    public int getAttemptCount() { return attemptCount; }
    public int getPegs() { return pegs; }
    public int getColors() { return colors; }
    public int getMaxAttempts() { return maxAttempts; }
    public Combination getSecret() { return secret; }
    public GameHistory getHistory() { return history; }

    public int[] evaluate(Combination guess) {
        int[] secretCopy = secret.getValues();
        int[] guessCopy = guess.getValues();

        int well = countWellPlaced(secretCopy, guessCopy);
        int mis = countMisPlaced(secretCopy, guessCopy);

        history.addAttempt(guess, well, mis);
        attemptCount++;
        
        if (well == pegs) {
            won = true;
        }

        return new int[]{well, mis};
    }

    private int countWellPlaced(int[] secretCopy, int[] guessCopy) {
        int count = 0;
        for (int i = 0; i < pegs; i++) {
            if (secretCopy[i] == guessCopy[i]) {
                secretCopy[i] = Constants.WELL_PLACED;
                guessCopy[i] = Constants.WELL_PLACED;
                count++;
            }
        }
        return count;
    }

    private int countMisPlaced(int[] secretCopy, int[] guessCopy) {
        int count = 0;
        for (int i = 0; i < pegs; i++) {
            if (guessCopy[i] != Constants.WELL_PLACED) {
                for (int j = 0; j < pegs; j++) {
                    if (secretCopy[j] != Constants.WELL_PLACED && 
                        secretCopy[j] == guessCopy[i]) {
                        secretCopy[j] = Constants.MISPLACED;
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
