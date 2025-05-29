package efs.task.collections.Army;


import efs.task.collections.Hero.HeroClass;
import efs.task.collections.Units.TypeOfUnit;

public class RetinueUtils {
    public static double distance(Retinue r1, Retinue r2) {
        double dx = r1.getX() - r2.getX();
        double dy = r1.getY() - r2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void moveRetinue(Retinue r, int dx, int dy) {
        r.setPosition(r.getX() + dx, r.getY() + dy);
    }

    public static void applyHeroClassBonus(Retinue retinue) {
        HeroClass heroClass = retinue.owner.getHeroClass();

        for (TypeOfUnit type : retinue.stats.keySet()) {
            ArmyStats stats = retinue.stats.get(type);

            switch (heroClass) {
                case WARLORD -> stats.upgradeStats(1, 0, 0);
                case DEFENDER -> stats.upgradeStats(0, 1, 0);
                case STRATEGIST -> stats.upgradeStats(0, 0, 1);
                case ARCHMASTER -> {
                    if (type == TypeOfUnit.ARCHER)
                        stats.upgradeStats(1, 1, 1);
                }
                case CAVALIER -> {
                    if (type == TypeOfUnit.CAVLARY)
                        stats.upgradeStats(1, 1, 1);
                }
                case INFANTRY_GENERAL -> {
                    if (type == TypeOfUnit.INFANTRY)
                        stats.upgradeStats(1, 1, 1);
                }
            }
        }

        retinue.recalculateOverallStats();
    }

    //Fukcja dodająca bonus dowódcy do daneeog typu jednostki po ulepszeniu go
    public static void applyHeroClassBonusByUnit(Retinue retinue, TypeOfUnit type) {
        HeroClass heroClass = retinue.owner.getHeroClass();
            ArmyStats stats = retinue.stats.get(type);
            switch (heroClass) {
                case WARLORD -> {
                    stats.upgradeStats(1, 0, 0);
                }
                case DEFENDER -> stats.upgradeStats(0, 1, 0);
                case STRATEGIST -> stats.upgradeStats(0, 0, 1);
                case ARCHMASTER -> {
                    if (type == TypeOfUnit.ARCHER)
                        stats.upgradeStats(1, 1, 1);
                }
                case CAVALIER -> {
                    if (type == TypeOfUnit.CAVLARY)
                        stats.upgradeStats(1, 1, 1);
                }
                case INFANTRY_GENERAL -> {
                    if (type == TypeOfUnit.INFANTRY)
                        stats.upgradeStats(1, 1, 1);
                }
        }

        retinue.recalculateOverallStats();
    }
}
