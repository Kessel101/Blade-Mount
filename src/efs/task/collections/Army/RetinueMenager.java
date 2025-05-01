package efs.task.collections.Army;

import java.util.ArrayList;
import java.util.List;

public class RetinueMenager {
    static List<Retinue> reunites = new ArrayList<Retinue>();
    static Retinue player;

    public static void setPlayer(Retinue player) {
         RetinueMenager.player = player;
    }
    public void add(Retinue retinue) {
        reunites.add(retinue);
    }
    public void displayArmyStats() {
        for(Retinue r:reunites){
            r.displayArmyStats();
        }
    }

    public List<Retinue> getList(){
        return reunites;
    }

    public Retinue getPlayer(){
        return player;
    }


}
