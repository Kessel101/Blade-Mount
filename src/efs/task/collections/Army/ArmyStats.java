package efs.task.collections.Army;

import java.util.HashMap;
import java.util.ArrayList;

import efs.task.collections.Units.*;


public class ArmyStats {
    int level;
    private double damege; //sum
    private double defense; //sum
    private int sum_of_speed;
    private double speed; // avg
    private int quantity;

    public double getDamege() {
        return damege;
    }

    public double getDefense() {
        return defense;
    }

    public double getSpeed() {
        return speed;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSumOfSpeed() {
        return sum_of_speed;
    }

    public int getLevel() {
        return level;
    }

    public void increase_level() {
        level++;
    }

    public ArmyStats(double damege, double defense, int sum_of_speed, int quantity, int level) {
        this.damege = damege;
        this.defense = defense;
        this.sum_of_speed = sum_of_speed;
        this.quantity = quantity;
        this.speed = quantity == 0 ? 0 : (double) sum_of_speed / quantity;
        this.level = level;
    }


    public void update_stats_by_unit(Stats stats) {
        this.damege += stats.getDamege();
        this.defense += stats.getDefense();
        this.sum_of_speed += stats.getSpeed();
        this.quantity += 1;
        this.level = stats.getLevel();
        this.speed = (double)sum_of_speed / quantity;
    }
    // Dodaje podsumowane statystyki innej grupy jednostek
    public void update_stats_by_unit(ArmyStats other) {
        this.damege += other.damege;
        this.defense += other.defense;
        this.sum_of_speed += other.sum_of_speed;
        this.quantity += other.quantity;
        this.level = other.level;
        this.speed = quantity > 0 ? (double) sum_of_speed / quantity : 0;
    }

    public void calculate_speed() {
        this.speed /= quantity;
    }

    public void removeUnits(int count) {
        if (count >= quantity) {
            // usuwanie wszystkich jednostek
            quantity = 0;
            damege = 0;
            defense = 0;
            sum_of_speed = 0;
            speed = 0;
        } else {
            double unitDmg = damege / quantity;
            double unitDef = defense / quantity;
            double unitSpeed = (double) sum_of_speed / quantity;

            damege -= unitDmg * count;
            defense -= unitDef * count;
            sum_of_speed -= unitSpeed * count;
            quantity -= count;
            speed = quantity == 0 ? 0 : (double) sum_of_speed / quantity;
        }
    }


    public void upgradeStats(int i, int i1, int i2) {
        damege += i ;
        defense += i1 ;
        sum_of_speed += i2 ;
        speed = quantity == 0 ? 0 : (double) sum_of_speed / quantity;

    }

    public void upgradeStatsByUnit(int i, int i1, int i2, TypeOfUnit type) {
        damege += i ;
        defense += i1 ;
        sum_of_speed += i2 ;
        speed = quantity == 0 ? 0 : (double) sum_of_speed / quantity;

    }

    public double totalPower() {
         int total = (int) (damege + defense + speed);
         return total;
    }

}

