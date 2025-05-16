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

    // Zmienna statyczna dla przechowywania obecnej tury
    public static int currentTurn = 0;

    // Metoda do przejścia do następnej tury
    public static void nextTurn() {
        handlePlayerTurn();
        currentTurn++;
        Retinue player = RetinueMenager.getPlayer();

        Iterator<Retinue> it = RetinueMenager.getList().iterator();
        while (it.hasNext()) {
            Retinue retinue = it.next();
            if (RetinueUtils.distance(player, retinue) < 3) {
                simulateBattles(player, retinue);
                if (retinue.overal_army_tats.getQuantity() <= 0) {
                    //System.out.println("💀 " + retinue.owner.getName() + " został pokonany i usunięty z mapy.");
                    it.remove(); // to jest kluczowe – bezpieczne usunięcie z listy podczas iteracji
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
            System.out.println("\n🎉🎉🎉 GRATULACJE, " + player.owner.getName() + "! POKONAŁEŚ WSZYSTKICH WROGÓW! 🎉🎉🎉");
            System.out.println("🏆 Zwycięstwo w " + currentTurn + " turach.");
            System.exit(0); // kończy program
        }

        for (Retinue enemy : RetinueMenager.getList()) {
            if (enemy != RetinueMenager.getPlayer()) {
                AIMoves.enemyAI(enemy, RetinueMenager.getPlayer());
            }
        }

        // Tu możesz dodać dodatkowe procesy, jak AI, zdarzenia czy generowanie zasobów
        System.out.println("Aktualna tura: " + currentTurn);
        Resources.refillPlayer(currentTurn);
        Resources.refillEnemies(currentTurn);
        triggerRandomEvent();
        sleep(100); // Czekaj przez 100 milisekund
    }

    // Funkcja do usypiania wątku na określony czas
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
