package efs.task.collections.Hero;

import java.util.HashMap;
import java.util.ArrayList;

public class Hero {
    private String name;
    private int ducats;
    private HeroStats heroStats;

    public Hero(String name, int ducats, HeroStats heroStats) {
        this.name = name;
        this.ducats = ducats;
        this.heroStats = heroStats;
    }



    public String getName() {
        return name;
    }

    public int getDukaty() {
        return ducats;
    }


    public void removeDukaty(int i) {
        ducats -= i;
    }

    public void giveDukaty(int i) {
        ducats += i;
    }
}
