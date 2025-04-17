package efs.task.collections.Units;

import efs.task.collections.Hero.Hero;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Unit {
    private Stats stats;
    private TypeOfUnit typeOfUnit;

    public Unit(TypeOfUnit type, int level) {
        switch (type) {
            case INFANTRY:
                stats = Data.infantry_stats[level];
                break;
            case CAVLARY:
                stats = Data.cavlary_stats[level];
                break;
            case ARCHER:
                stats = Data.archers_stats[level];
                break;
            default:
                break;
        }
    }

    public int getDamege() {
        return stats.getDamege();
    }

    public int getDefense() {
        return stats.getDefense();
    }

    public int getSpeed() {
        return stats.getSpeed();
    }

    public TypeOfUnit getTypeOfUnit() {
        return typeOfUnit;
    }

    public Stats getStats() {
        return stats;
    }
}
