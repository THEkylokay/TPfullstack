package TPMVCTicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI {
    private TicTacToeModelInterface model;
    private TicTacToeController controller;
    private JFrame frame;
    private JButton[][] buttons;

    public TicTacToeGUI(TicTacToeModelInterface model, TicTacToeController controller) {
        this.model = model;
        this.controller = controller;
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(model.getBoardSize(), model.getBoardSize()));

        int size = model.getBoardSize();
        buttons = new JButton[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton(String.valueOf(model.getBoard()[i][j].getLabel()));
                int row = i, col = j;
                btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                btn.addActionListener(e -> controller.handleMove(row, col));
                buttons[i][j] = btn;
                frame.add(btn);
            }
        }

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public void updateBoard() {
        for (int i = 0; i < model.getBoardSize(); i++)
            for (int j = 0; j < model.getBoardSize(); j++)
                buttons[i][j].setText(String.valueOf(model.getBoard()[i][j].getLabel()));
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
