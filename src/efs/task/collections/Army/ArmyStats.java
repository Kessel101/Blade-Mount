package efs.task.collections.Army;

import java.util.HashMap;
import java.util.ArrayList;

import efs.task.collections.Units.*;


public class ArmyStats {
    private double damege; //sum
    private double defense; //sum
    private int sum_of_speed;
    private double speed; // avg
    private int quantity;

    public ArmyStats(double damege, double defense, int sum_of_speed, int quantity) {
        this.damege = damege;
        this.defense = defense;
        this.sum_of_speed = sum_of_speed;
        this.quantity = quantity;
        this.speed = sum_of_speed / quantity;
    }

    public void update_stats_by_unit(Stats stats) {
        this.damege += stats.getDamege();
        this.defense += stats.getDefense();
        this.sum_of_speed += stats.getSpeed();
        this.quantity += 1;
        this.speed = (double)sum_of_speed / quantity;
    }

    public void calculate_speed() {
        this.speed /= quantity;
    }
}
