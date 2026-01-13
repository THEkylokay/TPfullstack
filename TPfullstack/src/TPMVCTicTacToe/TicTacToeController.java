package TPMVCTicTacToe;

public class TicTacToeController {
    private TicTacToeModelInterface model;
    private TicTacToeGUI view;

    public TicTacToeController(TicTacToeModelInterface model) {
        this.model = model;
    }

    public void setView(TicTacToeGUI view) {
        this.view = view;
    }

    public void handleMove(int row, int col) {
        model.recordProposedMove(row, col);
        if (!model.isMoveValid()) {
            view.showMessage("Invalid move! Try again.");
            return;
        }

        model.playMove();
        view.updateBoard();

        if (model.isGameOver()) {
            view.showMessage("Game Over! Winner: " + model.getActivePlayer());
            System.exit(0);
        } else {
            model.changeActivePlayer();
        }
    }
}
