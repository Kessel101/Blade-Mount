package Engine;

import AI.AIMoves;
import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Army.RetinueUtils;

import java.util.Iterator;

import static Engine.Battles.simulateBattles;
import static Engine.PlayerChoice.handlePlayerTurn;
import static Engine.randomEvents.triggerRandomEvent;

public class TurnManager {

    public static int currentTurn = 0;

    public static void nextTurn() {
        handlePlayerTurn();
        currentTurn++;
        Retinue player = RetinueMenager.getPlayer();

        Iterator<Retinue> it = RetinueMenager.getList().iterator();
        while (it.hasNext()) {
            Retinue retinue = it.next();
            if (RetinueUtils.distance(player, retinue) < 2) {
                simulateBattles(player, retinue);
                if (retinue.overal_army_tats.getQuantity() <= 0) {
                    it.remove();
                }
            }
        }

        boolean anyEnemiesLeft = false;
        for (Retinue r : RetinueMenager.getList()) {
            if (r != player) {
                anyEnemiesLeft = true;
                break;
            }
        }

        if (!anyEnemiesLeft) {
            System.out.println("\nGRATULACJE, " + player.owner.getName() + "! POKONAŁEŚ WSZYSTKICH WROGÓW!");
            System.out.println("Zwycięstwo w " + currentTurn + " turach.");
            System.exit(0); // kończy program
        }

        for (Retinue enemy : RetinueMenager.getList()) {
            if (enemy != RetinueMenager.getPlayer()) {
                AIMoves.enemyAI(enemy, RetinueMenager.getPlayer());
            }
        }

        System.out.println("Aktualna tura: " + currentTurn);
        Resources.refillPlayer(currentTurn);
        Resources.refillEnemies(currentTurn);
        triggerRandomEvent();
        sleep(100); // Czekaj przez 100 milisekund
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
