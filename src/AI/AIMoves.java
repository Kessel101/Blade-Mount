package AI;

import efs.task.collections.Army.Retinue;
import Map.GameMap;

import java.util.Random;

public class AIMoves {

    private static final Random rand = new Random();

    public static void enemyAI(Retinue enemy, Retinue player) {
        int enemyX = (int) enemy.getX();
        int enemyY = (int) enemy.getY();
        int playerX = (int) player.getX();
        int playerY = (int) player.getY();

        double dx = playerX - enemyX;
        double dy = playerY - enemyY;
        double distance = Math.abs(dx) + Math.abs(dy);
        int maxMove = (int) enemy.overal_army_tats.getSpeed();

        // Jeśli bardzo blisko gracza, to atak
        if (distance < 2) {
            System.out.println(enemy.owner.getName() + " atakuje gracza!");
            // Combat.fight(enemy, player); // <- ewentualna walka
            return;
        }

        // Ocena siły
        int enemyStrength = (int) enemy.overal_army_tats.totalPower(); // załóżmy, że masz taką metodę
        int playerStrength = (int) player.overal_army_tats.totalPower();

        boolean shouldApproach = rand.nextDouble() < calculateAggressionChance(enemyStrength, playerStrength);

        int moveX = 0, moveY = 0;

        if (shouldApproach) {
            // Zbliż się do gracza
            int[] move = calculateBestMove(enemyX, enemyY, playerX, playerY, maxMove, false);
            moveX = move[0];
            moveY = move[1];
        } else {
            // Uciekaj od gracza
            int[] move = calculateBestMove(enemyX, enemyY, playerX, playerY, maxMove, true);
            moveX = move[0];
            moveY = move[1];
        }

        int newX = enemyX + moveX;
        int newY = enemyY + moveY;

        // Sprawdź czy pole nie jest zajęte przez gracza i jest w mapie
        if (GameMap.isInBounds(newX, newY) &&
                (newX != playerX || newY != playerY)) {
            enemy.setPosition(moveX, moveY);
        }
    }

    private static double calculateAggressionChance(int enemyPower, int playerPower) {
        if (enemyPower >= playerPower) return 0.9; // silny -> agresywny
        double ratio = (double) enemyPower / playerPower;
        return 0.3 * ratio; // im słabszy, tym mniej agresywny
    }

    /**
     * Oblicza najlepszy ruch w kierunku (lub od) gracza
     */
    private static int[] calculateBestMove(int ex, int ey, int px, int py, int speed, boolean flee) {
        int dx = px - ex;
        int dy = py - ey;

        int moveX = 0, moveY = 0;

        // jeśli uciekamy, to robimy odwrotnie
        if (flee) {
            dx = -dx;
            dy = -dy;
        }

        // Strategia ruchu najpierw w dominującym kierunku
        if (Math.abs(dx) > Math.abs(dy)) {
            moveX = (int) Math.signum(dx) * Math.min(speed, Math.abs(dx));
            speed -= Math.abs(moveX);
            moveY = (int) Math.signum(dy) * Math.min(speed, Math.abs(dy));
        } else {
            moveY = (int) Math.signum(dy) * Math.min(speed, Math.abs(dy));
            speed -= Math.abs(moveY);
            moveX = (int) Math.signum(dx) * Math.min(speed, Math.abs(dx));
        }

        return new int[]{moveX, moveY};
    }
}
