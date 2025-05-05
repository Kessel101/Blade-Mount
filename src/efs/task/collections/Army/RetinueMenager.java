package efs.task.collections.Army;

import java.util.ArrayList;
import java.util.List;

public class RetinueMenager {
    // Statyczna lista dla wszystkich retinue
    private static List<Retinue> reunites = new ArrayList<>();

    // Statyczna zmienna dla gracza
    private static Retinue player;

    // Ustawienie gracza
    public static void setPlayer(Retinue player) {
        RetinueMenager.player = player;
    }

    // Dodanie nowego retinue do listy
    public static void add(Retinue retinue) {
        reunites.add(retinue);
    }

    // Wyświetlanie statystyk armii
    public static void displayArmyStats() {
        for (Retinue r : reunites) {
            r.displayArmyStats();
        }
    }

    // Pobranie listy wszystkich retinue
    public static List<Retinue> getList() {
        return reunites;
    }

    // Pobranie gracza
    public static Retinue getPlayer() {
        return player;
    }

    public static void remove(Retinue r) {
        reunites.remove(r);

    }
}
