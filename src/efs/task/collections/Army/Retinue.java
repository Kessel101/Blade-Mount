package efs.task.collections.Army;

import java.util.HashMap;
import java.util.ArrayList;

import efs.task.collections.Hero.Hero;
import efs.task.collections.Units.*;


public class Retinue {
    private Hero owner;
    private ArmyStats overal_army_tats;
    private HashMap<TypeOfUnit, ArmyStats> stats = new HashMap();

    public Retinue(ArrayList<Unit> units) {

        stats.put(TypeOfUnit.ARCHER, new ArmyStats(0, 0, 0, 0));
        stats.put(TypeOfUnit.INFANTRY, new ArmyStats(0, 0, 0, 0));
        stats.put(TypeOfUnit.CAVLARY, new ArmyStats(0, 0, 0, 0));

        for(Unit unit : units) {
            stats.get(unit.getTypeOfUnit()).update_stats_by_unit(unit.getStats());
            overal_army_tats.update_stats_by_unit(unit.getStats());
        }


    }

    public void add_to_retinue(ArrayList<Unit> units) {
        for(Unit unit : units) {
            stats.get(unit.getTypeOfUnit()).update_stats_by_unit(unit.getStats());
            overal_army_tats.update_stats_by_unit(unit.getStats());
        }

    }
}
