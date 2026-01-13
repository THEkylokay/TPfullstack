package com.mastermind.view;

import com.mastermind.controller.GameController;
import com.mastermind.model.Combination;
import com.mastermind.model.Game;
import com.mastermind.util.Constants;

import javax.swing.*;
import java.awt.*;

public class SwingView extends JFrame {
    private GameController controller;
    private JPanel historyPanel;
    private JPanel currentGuessPanel;
    private JButton[] colorButtons;
    private JButton validateButton;
    private JLabel statusLabel;
    private JLabel attemptsLabel;
    
    private Combination currentGuess;
    private int currentPegIndex = 0;
    private int pegs = 4;
    private int colors = 8;
    private int maxAttempts = 10;

    public SwingView() {
        controller = new GameController();
        setTitle("Mastermind Professional");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        setupLayout();
        startNewGame();
    }

    private void setupLayout() {
        // Main Container
        JPanel mainContainer = new JPanel(new BorderLayout(15, 15));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContainer.setBackground(new Color(45, 45, 48));

        // 1. LEFT SIDE: Rules & Info
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(250, 0));
        sidePanel.setBackground(new Color(60, 63, 65));
        sidePanel.setBorder(BorderFactory.createTitledBorder(null, "RULES", 0, 0, null, Color.WHITE));

        String rulesHtml = "<html><body style='color:white; padding:10px; font-family:sans-serif;'>" +
                "<h3>How to Play</h3>" +
                "1. Find the <b>secret code</b>.<br><br>" +
                "2. Pick colors from the bottom.<br><br>" +
                "3. Feedback:<br>" +
                "   <font color='black'>●</font> Black: Right color, Right spot.<br>" +
                "   <font color='white'>○</font> White: Right color, Wrong spot.<br><br>" +
                "4. You have " + maxAttempts + " tries!</body></html>";
        JLabel rulesLabel = new JLabel(rulesHtml);
        sidePanel.add(rulesLabel);

        // 2. CENTER: Game History (The Board)
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(new Color(30, 30, 30));
        JScrollPane scrollBoard = new JScrollPane(historyPanel);
        scrollBoard.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // 3. BOTTOM: Input Controls
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);

        // Current Guess Display
        currentGuessPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        currentGuessPanel.setBackground(new Color(60, 63, 65));
        currentGuessPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        // Color Selection Buttons
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        selectionPanel.setOpaque(false);
        
        colorButtons = new JButton[colors];
        for (int i = 0; i < colors; i++) {
            final int colorIdx = i + 1;
            colorButtons[i] = new JButton();
            colorButtons[i].setPreferredSize(new Dimension(45, 45));
            colorButtons[i].setBackground(Constants.COLORS[colorIdx]);
            colorButtons[i].addActionListener(e -> selectColor(colorIdx));
            selectionPanel.add(colorButtons[i]);
        }

        validateButton = new JButton("CHECK GUESS");
        validateButton.setPreferredSize(new Dimension(150, 45));
        validateButton.setBackground(new Color(0, 120, 215));
        validateButton.setForeground(Color.WHITE);
        validateButton.setFont(new Font("Arial", Font.BOLD, 14));
        validateButton.setEnabled(false);
        validateButton.addActionListener(e -> validateGuess());
        selectionPanel.add(validateButton);

        bottomPanel.add(currentGuessPanel, BorderLayout.NORTH);
        bottomPanel.add(selectionPanel, BorderLayout.CENTER);

        // Status Bar
        statusLabel = new JLabel("Welcome! Start by picking a color.", SwingConstants.CENTER);
        statusLabel.setForeground(Color.YELLOW);
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        // Assembly
        mainContainer.add(sidePanel, BorderLayout.WEST);
        mainContainer.add(scrollBoard, BorderLayout.CENTER);
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);
        add(mainContainer);
    }

    private void startNewGame() {
        controller.startNewGame(pegs, colors, maxAttempts);
        historyPanel.removeAll();
        clearGuess();
        historyPanel.revalidate();
        historyPanel.repaint();
    }

    private void selectColor(int colorIndex) {
        if (currentPegIndex < pegs) {
            currentGuess.setValue(currentPegIndex, colorIndex);
            currentPegIndex++;
            updateCurrentGuessDisplay();
            if (currentPegIndex == pegs) validateButton.setEnabled(true);
        }
    }

    private void clearGuess() {
        currentGuess = new Combination(pegs);
        currentPegIndex = 0;
        validateButton.setEnabled(false);
        updateCurrentGuessDisplay();
    }

    private void updateCurrentGuessDisplay() {
        currentGuessPanel.removeAll();
        for (int i = 0; i < pegs; i++) {
            JPanel peg = new JPanel();
            peg.setPreferredSize(new Dimension(50, 50));
            int val = currentGuess.getValue(i);
            peg.setBackground(val > 0 ? Constants.COLORS[val] : Color.BLACK);
            peg.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            currentGuessPanel.add(peg);
        }
        currentGuessPanel.revalidate();
        currentGuessPanel.repaint();
    }

    private void validateGuess() {
        int[] result = controller.submitGuess(currentGuess);
        if (result != null) {
            addRowToBoard(currentGuess, result[0], result[1]);
            if (controller.isGameOver()) {
                handleEndGame();
            } else {
                clearGuess();
            }
        }
    }

    private void addRowToBoard(Combination guess, int black, int white) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(800, 65));

        // Attempt Number
        JLabel lbl = new JLabel("Try " + controller.getCurrentGame().getAttemptCount() + ":");
        lbl.setForeground(Color.WHITE);
        row.add(lbl);

        // The Guess Pegs
        for (int val : guess.getValues()) {
            JPanel p = new JPanel();
            p.setPreferredSize(new Dimension(40, 40));
            p.setBackground(Constants.COLORS[val]);
            p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            row.add(p);
        }

        // The Feedback Pegs
        JPanel feedback = new JPanel(new GridLayout(2, 2, 2, 2));
        feedback.setOpaque(false);
        for (int i = 0; i < black; i++) feedback.add(createMiniPeg(Color.BLACK));
        for (int i = 0; i < white; i++) feedback.add(createMiniPeg(Color.WHITE));
        row.add(feedback);

        historyPanel.add(row);
        historyPanel.revalidate();
    }

    private JPanel createMiniPeg(Color c) {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(15, 15));
        p.setBackground(c);
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return p;
    }

    private void handleEndGame() {
        boolean won = controller.isGameWon();
        statusLabel.setText(won ? "YOU WON! AMAZING!" : "GAME OVER. Solution: " + controller.getCurrentGame().getSecret());
        statusLabel.setForeground(won ? Color.GREEN : Color.RED);
        JOptionPane.showMessageDialog(this, won ? "Congratulations!" : "Maybe next time...");
        startNewGame();
    }
}