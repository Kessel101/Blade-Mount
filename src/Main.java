
import Engine.SoundManager;
import Map.GameMap;
import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Army.RetinueUtils;
import efs.task.collections.Hero.Hero;
import efs.task.collections.Hero.HeroClass;
import efs.task.collections.Hero.HeroStats;
import Engine.TurnManager;
import efs.task.collections.Units.TypeOfUnit;
import efs.task.collections.Units.Unit;

import java.util.ArrayList;

import static Engine.PlayerChoice.scanner;


public class Main {
    public static void main(String[] args) {

        SoundManager sound = new SoundManager();
        sound.load("/sounds/Runes of Magic OST - Bridge of Varanas (Entrance).wav");
        sound.loop();

        //RetinueMenager retinueMenager = new RetinueMenager();
        System.out.println("\nWybierz klasę swojego bohatera:\n");
        HeroClass[] classes = HeroClass.values();
        for (int i = 0; i < classes.length; i++) {
            System.out.println(i + ". " + classes[i]);
        }
        int choice = scanner.nextInt();
        HeroClass heroClass = HeroClass.values()[choice];

        HeroStats heroStats0 = new HeroStats(2, 4, 3);

        Hero hero = new Hero("Gracz", 300, heroStats0, heroClass);

        //Hero hero = new Hero("Alexander", 500, HeroStats.builder().total_leader(5,5,5).build(), HeroClass.WARLORD);

        HeroStats heroStats1 = new HeroStats(2, 4, 3);
        Hero hero1 = new Hero("Cesar", 500, heroStats1, HeroClass.DEFENDER);

        HeroStats heroStats2 = new HeroStats(4, 5, 1);
        Hero hero2 = new Hero("Adoolf", 500, heroStats2, HeroClass.ARCHMASTER);

        ArrayList<Unit> unitsAlex = new ArrayList<Unit>();
        unitsAlex.add(new Unit(TypeOfUnit.INFANTRY, 1)); // Level 0
        unitsAlex.add(new Unit(TypeOfUnit.ARCHER, 2)); // Level 0
        unitsAlex.add(new Unit(TypeOfUnit.CAVLARY, 2)); // Level 0

        Retinue player = new Retinue(hero, unitsAlex,1,1);
        RetinueMenager.setPlayer(player);
        RetinueUtils.applyHeroClassBonus(player);

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
        GameMap.placeTreasure(RetinueMenager.getList());
        //bufyy zwiazane z klasa postaci
        for (Retinue retinue : RetinueMenager.getList()) {
            RetinueUtils.applyHeroClassBonus(retinue);
        }



        for(int i=0; i<100;i++){
            TurnManager.nextTurn();

        }

        sound.stop();
        sound.close();
    }
}