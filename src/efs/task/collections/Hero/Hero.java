package efs.task.collections.Hero;

import java.util.HashMap;
import java.util.ArrayList;

public class Hero {
    private String name;
    private int ducats;
    private HeroStats heroStats;
    private HeroClass heroClass;

    public Hero(String name, int ducats, HeroStats heroStats,HeroClass heroClass) {
        this.name = name;
        this.ducats = ducats;
        this.heroStats = heroStats;
        this.heroClass = heroClass;

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
    public HeroStats getHeroStats() {
        return heroStats;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

}
