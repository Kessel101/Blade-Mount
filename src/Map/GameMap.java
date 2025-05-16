package Map;


import efs.task.collections.Army.Retinue;

import java.util.List;

public class GameMap {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private static final MapTile[][] grid = new MapTile[HEIGHT][WIDTH];


    public static int treasureX = -1;
    public static int treasureY = -1;
    public static boolean treasureCollected = false;


    static {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = new MapTile();
            }
        }
    }

    public static void updateMap(List<Retinue> retinues, Retinue player) {
        clear();

        int px = (int) player.getX();
        int py = (int) player.getY();
        if (isInBounds(px, py)) {
            grid[py][px].setSymbol('P');
        }

        for (Retinue r : retinues) {
            if (r == player) continue;
            int x = (int) r.getX();
            int y = (int) r.getY();
            if (isInBounds(x, y)) {
                char symbol = r.owner.getName().charAt(0);
                grid[y][x].setSymbol(symbol);

            }
        }
        if (!treasureCollected && isInBounds(treasureX, treasureY)) {
            grid[treasureY][treasureX].setSymbol('X'); // Złoty X
        }
    }

    public static void displayMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                char symbol = grid[i][j].getSymbol();
                if (symbol == 'P') {
                    System.out.print("\u001B[32m" + symbol + "\u001B[0m "); // zielony
                } else if (symbol== 'X'){
                    System.out.print("\u001B[33m" + symbol + "\u001B[0m ");
                }else if (symbol != '.') {
                    System.out.print("\u001B[31m" + symbol + "\u001B[0m "); // czerwony
                }
                else {
                    System.out.print(symbol + " ");
                }
            }
            System.out.println();
        }
    }


    private static void clear() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].clear();
            }
        }
    }

    public static boolean isInBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public static void placeTreasure(List<Retinue> retinues) {
        java.util.Random rand = new java.util.Random();

        while (true) {
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);

            boolean occupied = false;
            for (Retinue r : retinues) {
                if ((int) r.getX() == x && (int) r.getY() == y) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                treasureX = x;
                treasureY = y;
                treasureCollected = false;
                break;
            }
        }
    }


}


