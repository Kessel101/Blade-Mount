package efs.task.collections.Army;


public class RetinueUtils {
    public static double distance(Retinue r1, Retinue r2) {
        double dx = r1.getX() - r2.getX();
        double dy = r1.getY() - r2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void moveRetinue(Retinue r, int dx, int dy) {
        r.setPosition(r.getX() + dx, r.getY() + dy);
    }
}
