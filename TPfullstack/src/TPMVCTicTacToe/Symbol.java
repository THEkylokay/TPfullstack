package TPMVCTicTacToe;

public enum Symbol {
    EMPTY('-'), CROSS('X'), CIRCLE('O');

    private final char label;

    private Symbol(char label) {
        this.label = label;
    }

    public char getLabel() {
        return label;
    }
}
