package efs.task.collections.Army;

import java.util.HashMap;
import java.util.ArrayList;

import efs.task.collections.Hero.Hero;
import efs.task.collections.Units.*;

import static java.lang.Math.sqrt;


public class Retinue {
    private int x;
    private int y;

    private Hero owner;
    private ArmyStats overal_army_tats;
    private HashMap<TypeOfUnit, ArmyStats> stats = new HashMap();

    public Retinue(Hero owner,ArrayList<Unit> units,int x,int y) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.overal_army_tats = new ArmyStats(0, 0, 0, 0);
        stats.put(TypeOfUnit.ARCHER, new ArmyStats(0, 0, 0, 0));
        stats.put(TypeOfUnit.INFANTRY, new ArmyStats(0, 0, 0, 0));
        stats.put(TypeOfUnit.CAVLARY, new ArmyStats(0, 0, 0, 0));

        // Metoda do dodawania jednostek do armii
        for (Unit unit : units) {
            stats.get(unit.getTypeOfUnit()).update_stats_by_unit(unit.getStats());
            overal_army_tats.update_stats_by_unit(unit.getStats());
        }


    }

    public void add_to_retinue(ArrayList<Unit> units) {
        for (Unit unit : units) {
            stats.get(unit.getTypeOfUnit()).update_stats_by_unit(unit.getStats());
            overal_army_tats.update_stats_by_unit(unit.getStats());
        }

    }

    // Metoda wypisująca statystyki armii
    public void displayArmyStats() {
        System.out.println("Armia " + owner.getName() + ":");
        System.out.println("Ogólne statystyki armii:");
        System.out.println("Obrażenia: " + overal_army_tats.getDamege());
        System.out.println("Obrona: " + overal_army_tats.getDefense());
        System.out.println("Szybkość: " + overal_army_tats.getSpeed());
        System.out.println("Ilość jednostek: " + overal_army_tats.getQuantity());

        System.out.println("\nStatystyki jednostek:");
        for (TypeOfUnit type : stats.keySet()) {
            ArmyStats unitStats = stats.get(type);
            System.out.println(type + ":");
            System.out.println("Obrażenia: " + unitStats.getDamege());
            System.out.println("Obrona: " + unitStats.getDefense());
            System.out.println("Szybkość: " + unitStats.getSpeed());
            System.out.println("Ilość: " + unitStats.getQuantity());
        }
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setPosition(double dx,double dy){
        x += dx;
        y += dy;
    }
}