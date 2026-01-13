package com.mastermind.model;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {
    private final List<Combination> attempts = new ArrayList<>();
    private final List<Integer> wellPlaced = new ArrayList<>();
    private final List<Integer> misPlaced = new ArrayList<>();

    public void addAttempt(Combination attempt, int well, int mis) {
        attempts.add(new Combination(attempt.getSize()));
        for (int i = 0; i < attempt.getSize(); i++) {
            attempts.get(attempts.size() - 1).setValue(i, attempt.getValue(i));
        }
        wellPlaced.add(well);
        misPlaced.add(mis);
    }

    public void print() {
        System.out.println("\n=== HISTORIQUE DE LA PARTIE ===");
        for (int i = 0; i < attempts.size(); i++) {
            System.out.printf("Coup %2d : %s  →  %s%s%n",
                    i + 1, 
                    attempts.get(i).toColorString(), 
                    "#".repeat(wellPlaced.get(i)),
                    "o".repeat(misPlaced.get(i)));
        }
    }

    public int getAttemptCount() {
        return attempts.size();
    }

    public Combination getAttempt(int index) {
        return attempts.get(index);
    }

    public int getWellPlaced(int index) {
        return wellPlaced.get(index);
    }

    public int getMisPlaced(int index) {
        return misPlaced.get(index);
    }
}
