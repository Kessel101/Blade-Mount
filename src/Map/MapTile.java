package Map;

public class MapTile {
    private char symbol;

    public MapTile() {
        this.symbol = '.';  // domyślnie pusty
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isEmpty() {
        return symbol == '.';
    }

    public void clear() {
        this.symbol = '.';
    }
}
