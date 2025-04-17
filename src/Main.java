
import efs.task.collections.Hero.Hero;
import efs.task.collections.Hero.HeroStats;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Hero hero = new Hero("Alexander", 500, HeroStats.builder().total_leader(5,5,5).build());

        HeroStats heroStats1 = new HeroStats(2, 4, 3);
        Hero hero1 = new Hero("Cesar", 500, heroStats1);

        HeroStats heroStats2 = new HeroStats(1, 1, 1);
        Hero hero2 = new Hero("Crassus", 500, heroStats2);


        Retinue Alexander_Retinue = new Retinue();

    }
}