package Engine;

import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Units.TypeOfUnit;

import java.util.ArrayList;
import java.util.Random;

public class randomEvents {

    private static final Random rand = new Random();

    // Prawdopodobieństwa wystąpienia wydarzeń
    private static final int NATURAL_DISASTER_CHANCE = 10;
    private static final int EPIDEMIC_CHANCE = 8;
    private static final int REVOLT_CHANCE = 12;
    private static final int HISTORICAL_EVENT_CHANCE = 5;
    private static final int TREASURE_CHANCE = 10;
    private static final int DESERTION_CHANCE = 5;
    private static final int MORALE_CHANCE = 15;

    public static void triggerRandomEvent() {
        int roll = rand.nextInt(100);
        int currentChance = 0;

        if (roll < (currentChance += NATURAL_DISASTER_CHANCE)) {
            triggerNaturalDisaster();
        } else if (roll < (currentChance += EPIDEMIC_CHANCE)) {
            triggerEpidemic();
        } else if (roll < (currentChance += REVOLT_CHANCE)) {
            triggerRevolt();
        } else if (roll < (currentChance += TREASURE_CHANCE)) {
            treasureFound();
        } else if (roll < (currentChance += DESERTION_CHANCE)) {
            unitDesertion();
        } else if (roll < (currentChance += MORALE_CHANCE)) {
            moraleBoost();
        } else {
            System.out.println("Brak wydarzeń losowych tej tury.");
        }
    }

    private static void triggerNaturalDisaster() {
        Retinue target = getRandomPlayer();
        if (target == null) return;

        int disasterType = rand.nextInt(4);
        switch (disasterType) {
            case 0: // Powódź
                System.out.println("Powódź! " + target.owner.getName() + " traci 2 jednostki piechoty!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(2);
                break;
            case 1: // Pożar
                System.out.println("Pożar! " + target.owner.getName() + " traci 1 jednostkę łuczników!");
                target.stats.get(TypeOfUnit.ARCHER).removeUnits(1);
                break;
            case 2: // Trzęsienie ziemi
                System.out.println("Trzęsienie ziemi! " + target.owner.getName() + " traci 1 jednostkę kawalerii!");
                target.stats.get(TypeOfUnit.CAVLARY).removeUnits(1);
                break;
            case 3: // Burza
                System.out.println("Burza! " + target.owner.getName() + " traci 1 jednostkę każdego typu!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(1);
                target.stats.get(TypeOfUnit.ARCHER).removeUnits(1);
                target.stats.get(TypeOfUnit.CAVLARY).removeUnits(1);
                break;
        }
    }

    private static void triggerEpidemic() {
        Retinue target = getRandomPlayer();
        if (target == null) return;

        int epidemicType = rand.nextInt(3);
        switch (epidemicType) {
            case 0: // Dżuma
                System.out.println("Dżuma! " + target.owner.getName() + " traci 3 jednostki piechoty!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(3);
                break;
            case 1: // Tyfus
                System.out.println("Tyfus! " + target.owner.getName() + " traci 2 jednostki łuczników!");
                target.stats.get(TypeOfUnit.ARCHER).removeUnits(2);
                break;
            case 2: // Cholera
                System.out.println("Cholera! " + target.owner.getName() + " traci 1 jednostkę każdego typu!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(1);
                target.stats.get(TypeOfUnit.ARCHER).removeUnits(1);
                target.stats.get(TypeOfUnit.CAVLARY).removeUnits(1);
                break;
        }
    }

    private static void triggerRevolt() {
        Retinue target = getRandomPlayer();
        if (target == null) return;

        int revoltType = rand.nextInt(3);
        switch (revoltType) {
            case 0: // Bunt chłopski
                System.out.println("Bunt chłopski! " + target.owner.getName() + " traci 2 jednostki piechoty!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(2);
                break;
            case 1: // Bunt w armii
                System.out.println("Bunt w armii! " + target.owner.getName() + " traci 1 jednostkę każdego typu!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(1);
                target.stats.get(TypeOfUnit.ARCHER).removeUnits(1);
                target.stats.get(TypeOfUnit.CAVLARY).removeUnits(1);
                break;
            case 2: // Rewolucja
                System.out.println("Rewolucja! " + target.owner.getName() + " traci 3 jednostki piechoty i 2 łuczników!");
                target.stats.get(TypeOfUnit.INFANTRY).removeUnits(3);
                target.stats.get(TypeOfUnit.ARCHER).removeUnits(2);
                break;
        }
    }



    private static void treasureFound() {
        Retinue player = RetinueMenager.getPlayer();
        int gold = 50 + rand.nextInt(51); // 50–100 dukatów
        player.owner.giveDukaty(gold);
        System.out.println("Skarb! Gracz znalazł " + gold + " dukatów.");
    }

    private static void unitDesertion() {
        Retinue player = RetinueMenager.getPlayer();
        TypeOfUnit type = getRandomUnitType();
        var stats = player.stats.get(type);
        if (stats.getQuantity() > 0) {
            stats.removeUnits(1);
            System.out.println("Jednostka zdezerterowała! Straciłeś 1 " + type);
        }
    }

    private static void moraleBoost() {
        Retinue player = RetinueMenager.getPlayer();
        player.owner.giveDukaty(20);
        System.out.println("Wzrost morale! Otrzymujesz dodatkowe 20 dukatów.");
    }

    private static Retinue getRandomPlayer() {
        var allPlayers = new ArrayList<>(RetinueMenager.getList());
        allPlayers.add(RetinueMenager.getPlayer());
        return allPlayers.get(rand.nextInt(allPlayers.size()));
    }

    private static TypeOfUnit getRandomUnitType() {
        TypeOfUnit[] types = TypeOfUnit.values();
        return types[rand.nextInt(types.length)];
    }
}
