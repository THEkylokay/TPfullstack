package TPMVCTicTacToe;

public class TicTacToeModel implements TicTacToeModelInterface {
    private final int size = 3;
    private Symbol[][] board = new Symbol[size][size];
    private Move lastMove;
    private Player activePlayer = Player.PLAYER_CROSS;

    public TicTacToeModel() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = Symbol.EMPTY;
    }

    @Override
    public void recordProposedMove(int row, int col) {
        Symbol symbol = (activePlayer == Player.PLAYER_CROSS) ? Symbol.CROSS : Symbol.CIRCLE;
        lastMove = new Move(row, col, symbol);
    }

    @Override
    public Move getProposedMove() { return lastMove; }

    @Override
    public boolean isMoveValid() {
        if (lastMove == null) return false;
        int r = lastMove.getRow();
        int c = lastMove.getCol();
        return r >= 0 && r < size && c >= 0 && c < size && board[r][c] == Symbol.EMPTY;
    }

    @Override
    public void playMove() {
        if (!isMoveValid()) return;
        board[lastMove.getRow()][lastMove.getCol()] = lastMove.getSymbol();
    }

    @Override
    public boolean isGameOver() {
        // Check rows
        for (int i = 0; i < size; i++)
            if (board[i][0] != Symbol.EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
        // Check cols
        for (int i = 0; i < size; i++)
            if (board[0][i] != Symbol.EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        // Check diagonals
        if (board[0][0] != Symbol.EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        if (board[0][2] != Symbol.EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;
        // Check tie
        boolean full = true;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == Symbol.EMPTY) full = false;
        return full;
    }

    @Override
    public Player getActivePlayer() { return activePlayer; }

    @Override
    public void changeActivePlayer() {
        activePlayer = (activePlayer == Player.PLAYER_CROSS) ? Player.PLAYER_CIRCLE : Player.PLAYER_CROSS;
    }

    @Override
    public Symbol[][] getBoard() { return board; }

    @Override
    public int getBoardSize() { return size; }
}
