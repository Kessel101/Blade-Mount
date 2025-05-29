package efs.task.collections.Units;

public class Stats {
    private int damege;
    private int defense;
    private int speed;
    private int upgreade_cost;
    private int hirering_cost;

    public Stats(int p_damege, int p_defense, int p_speed, int p_upgreade_cost, int p_hirering_cost) {
        damege = p_damege;
        defense = p_defense;
        speed = p_speed;
        upgreade_cost = p_upgreade_cost;
        hirering_cost = p_hirering_cost;
    }

    public int getDamege() {
        return damege;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getUpgreade_cost() {return upgreade_cost;}

    public int getHirering_cost() {return hirering_cost;}
}
