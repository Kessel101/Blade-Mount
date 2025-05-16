package Engine;

import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.ArmyStats;
import efs.task.collections.Units.TypeOfUnit;
import efs.task.collections.Army.RetinueMenager;

public class Battles {

    public static void simulateBattles(Retinue r1, Retinue r2) {
        System.out.println("\n🔱 Bitwa między " + r1.owner.getName() + " a " + r2.owner.getName() + "!");

        for (TypeOfUnit type : TypeOfUnit.values()) {
            ArmyStats stats1 = r1.stats.get(type);

            if (stats1.getQuantity() == 0) continue;

            TypeOfUnit targetType = findTarget(r2);
            ArmyStats stats2 = r2.stats.get(targetType);
            if (stats2.getQuantity() == 0) continue;

            double multiplier1 = getCounterMultiplier(type, targetType);
            double multiplier2 = getCounterMultiplier(targetType, type);

            double power1 = (stats1.getDamege() * multiplier1) + stats1.getDefense();
            double power2 = (stats2.getDamege() * multiplier2) + stats2.getDefense();

            System.out.printf("⚔️ Pojedynek %s (%s) vs %s (%s)\n", r1.owner.getName(), type, r2.owner.getName(), targetType);
            System.out.printf("Siła %s: %.2f vs Siła %s: %.2f\n", r1.owner.getName(), power1, r2.owner.getName(), power2);

            if (power1 > power2) {
                int lost = Math.min(stats2.getQuantity(), (int) ((power1 - power2) / 2) + 1);
                System.out.println(r2.owner.getName() + " traci " + lost + " jednostek typu " + targetType);
                stats2.removeUnits(lost);
            } else if (power2 > power1) {
                int lost = Math.min(stats1.getQuantity(), (int) ((power2 - power1) / 2) + 1);
                System.out.println(r1.owner.getName() + " traci " + lost + " jednostek typu " + type);
                stats1.removeUnits(lost);
            } else {
                int lost = 1;
                if (stats1.getQuantity() > 0) {
                    stats1.removeUnits(lost);
                    System.out.println("⚖️ Remis – " + r1.owner.getName() + " traci 1 jednostkę typu " + type);
                }
                if (stats2.getQuantity() > 0) {
                    stats2.removeUnits(lost);
                    System.out.println("⚖️ Remis – " + r2.owner.getName() + " traci 1 jednostkę typu " + targetType);
                }
            }
        }

        r1.recalculateOverallStats();
        r2.recalculateOverallStats();
        if (r1.overal_army_tats.getQuantity() <= 0) {
            System.out.println("💀 " + r1.owner.getName() + " został pokonany i usunięty z mapy. Koniec gry.");
            System.exit(0);
           // RetinueMenager.remove(r1);
        }
        if (r2.overal_army_tats.getQuantity() <= 0) {
            System.out.println("💀 " + r2.owner.getName() + " został pokonany i usunięty z mapy.");
            //RetinueMenager.remove(r2);
        }

        //System.out.println("\n📊 Stan armii po bitwie:");
       // r1.displayArmyStats();
        //r2.displayArmyStats();


    }

    private static TypeOfUnit getDominantType(Retinue r) {
        TypeOfUnit dominant = null;
        int maxQuantity = -1;
        for (TypeOfUnit t : TypeOfUnit.values()) {
            int qty = r.stats.get(t).getQuantity();
            if (qty > maxQuantity) {
                maxQuantity = qty;
                dominant = t;
            }
        }
        return dominant;
    }

    private static TypeOfUnit findTarget(Retinue r) {
        // Zwraca typ z największą liczbą jednostek
        return getDominantType(r);
    }

    private static double getCounterMultiplier(TypeOfUnit attacker, TypeOfUnit defender) {
        if (attacker == TypeOfUnit.CAVLARY && defender == TypeOfUnit.ARCHER) return 1.5;
        if (attacker == TypeOfUnit.INFANTRY && defender == TypeOfUnit.CAVLARY) return 1.5;
        if (attacker == TypeOfUnit.ARCHER && defender == TypeOfUnit.INFANTRY) return 1.3;
        return 1.0;
    }
}
