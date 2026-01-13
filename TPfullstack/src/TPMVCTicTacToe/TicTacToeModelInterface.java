package TPMVCTicTacToe;

public interface TicTacToeModelInterface {
    void recordProposedMove(int row, int col);
    Move getProposedMove();
    boolean isMoveValid();
    void playMove();
    boolean isGameOver();
    Player getActivePlayer();
    void changeActivePlayer();
    Symbol[][] getBoard();
    int getBoardSize();
}
