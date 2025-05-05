
import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Hero.Hero;
import efs.task.collections.Hero.HeroStats;
import Engine.TurnManager;
import efs.task.collections.Units.TypeOfUnit;
import efs.task.collections.Units.Unit;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        //RetinueMenager retinueMenager = new RetinueMenager();
        Hero hero = new Hero("Alexander", 500, HeroStats.builder().total_leader(5,5,5).build());

        HeroStats heroStats1 = new HeroStats(2, 4, 3);
        Hero hero1 = new Hero("Cesar", 500, heroStats1);

        HeroStats heroStats2 = new HeroStats(4, 5, 1);
        Hero hero2 = new Hero("Crassus", 500, heroStats2);

        ArrayList<Unit> unitsAlex = new ArrayList<Unit>();
        unitsAlex.add(new Unit(TypeOfUnit.INFANTRY, 1)); // Level 0
        unitsAlex.add(new Unit(TypeOfUnit.ARCHER, 2)); // Level 0
        unitsAlex.add(new Unit(TypeOfUnit.CAVLARY, 2)); // Level 0

        Retinue player = new Retinue(hero, unitsAlex,1,1);
        RetinueMenager.setPlayer(player);

        ArrayList<Unit> unitsCesar = new ArrayList<Unit>();
        unitsCesar.add(new Unit(TypeOfUnit.INFANTRY, 1)); // Level 0
        unitsCesar.add(new Unit(TypeOfUnit.ARCHER, 0)); // Level 0
        Retinue retinue1 = new Retinue(hero1, unitsCesar,3,3);
        RetinueMenager.add(retinue1);

        /// /

        ArrayList<Unit> unitsCrassus = new ArrayList<Unit>();
        unitsCrassus.add(new Unit(TypeOfUnit.INFANTRY, 2)); // Level 0
        unitsCrassus.add(new Unit(TypeOfUnit.ARCHER, 1)); // Level 0
        Retinue retinue2 = new Retinue(hero2, unitsCrassus,2,3);
        RetinueMenager.add(retinue2);


        //retinueMenager.add(retinue);
        // Retinue Alexander_Retinue = new Retinue();
        //player.displayArmyStats();
        //retinue1.displayArmyStats();


        for(int i=0; i<3;i++){
            TurnManager.nextTurn();

        }
    }
}