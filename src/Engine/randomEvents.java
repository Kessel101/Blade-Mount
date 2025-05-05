package Engine;

import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Units.TypeOfUnit;

import java.util.ArrayList;
import java.util.Random;

public class randomEvents {

    private static final Random rand = new Random();

    public static void triggerRandomEvent() {
        int roll = rand.nextInt(100);

        if (roll < 5) {
            plagueEvent();
            } else if (roll < 15) {
            treasureFound();
        } else if (roll < 20) {
            unitDesertion();
        } else if (roll < 35) {
            moraleBoost();
        } else {
            System.out.println("📜 Brak wydarzeń losowych tej tury.");
        }
    }

    private static void plagueEvent() {
        Retinue target = getRandomPlayer();
        if (target == null) return;

        TypeOfUnit type = getRandomUnitType();
        var stats = target.stats.get(type);
        if (stats.getQuantity() > 0) {
            stats.removeUnits(1);
            System.out.println("☠️ Zaraza! " + target.owner.getName() + " stracił 1 jednostkę typu " + type);
        }
    }

    private static void treasureFound() {
        Retinue player = RetinueMenager.getPlayer();
        int gold = 50 + rand.nextInt(51); // 50–100 dukatów
        player.owner.giveDukaty(gold);
        System.out.println("💎 Skarb! Gracz znalazł " + gold + " dukatów.");
    }

    private static void unitDesertion() {
        Retinue player = RetinueMenager.getPlayer();
        TypeOfUnit type = getRandomUnitType();
        var stats = player.stats.get(type);
        if (stats.getQuantity() > 0) {
            stats.removeUnits(1);
            System.out.println("😠 Jednostka zdezerterowała! Straciłeś 1 " + type);
        }
    }

    private static void moraleBoost() {
        Retinue player = RetinueMenager.getPlayer();
        player.owner.giveDukaty(20);
        System.out.println("🔥 Wzrost morale! Otrzymujesz dodatkowe 20 dukatów.");
    }

    private static Retinue getRandomPlayer() {
        // Skopiowanie listy wrogów i dodanie gracza
        var allPlayers = new ArrayList<>(RetinueMenager.getList());
        allPlayers.add(RetinueMenager.getPlayer()); // Dodajemy gracza do listy

        return allPlayers.get(rand.nextInt(allPlayers.size())); // Losowanie przeciwnika
    }


    private static TypeOfUnit getRandomUnitType() {
        TypeOfUnit[] types = TypeOfUnit.values();
        return types[rand.nextInt(types.length)];
    }
}
