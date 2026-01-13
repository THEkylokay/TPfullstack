package TPMVCTicTacToe;

public class TestTicTacToe {
    public static void main(String[] args) {
        TicTacToeModelInterface model = new TicTacToeModel();
        TicTacToeController controller = new TicTacToeController(model);
        TicTacToeGUI gui = new TicTacToeGUI(model, controller);
        controller.setView(gui);
    }
}
