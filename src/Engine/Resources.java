package Engine;

import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;

public class Resources {

    public static void refillPlayer(int currentTurn) {
        Retinue player = RetinueMenager.getPlayer();
        if (player != null) {
            int income = 10 + currentTurn * 3;
            player.owner.giveDukaty(income);
            System.out.println("Gracz otrzymał " + income +  " dukatów.");
        }
    }

    public static void refillEnemies(int currentTurn) {
        for (Retinue r : RetinueMenager.getList()) {
                int income = 10 + currentTurn * 2;
                r.owner.giveDukaty(income);
                System.out.println(r.owner.getName() + " otrzymał " + income + " dukatów.");
        }
    }
}
