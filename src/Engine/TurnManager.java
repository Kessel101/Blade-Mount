package Engine;

import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Army.RetinueUtils;

import static Engine.Battles.simulateBattles;

public class TurnManager {

    private static RetinueMenager retinueMenager;

    public TurnManager(RetinueMenager retinueMenager) {
        this.retinueMenager = retinueMenager;
    }
    private static int currentTurn = 0;
    public  void nextTurn() {
        currentTurn++;
         Retinue player =  retinueMenager.getPlayer();

        for(Retinue retinue: retinueMenager.getList()){
            if(RetinueUtils.distance(player,retinue)<1){
                simulateBattles(player,retinue);
            }
        }

        //processAI();
       // handleEvents();
       // generateResources();
        System.out.println(currentTurn);
        sleep(100);

    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

}
