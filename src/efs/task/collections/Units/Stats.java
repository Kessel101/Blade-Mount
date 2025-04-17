package efs.task.collections.Units;

public class Stats {
    private int damege;
    private int defense;
    private int speed;

    public Stats(int p_damege, int p_defense, int p_speed) {
        damege = p_damege;
        defense = p_defense;
        speed = p_speed;
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
}
