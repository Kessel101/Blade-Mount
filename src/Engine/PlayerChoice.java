package Engine;

import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Units.TypeOfUnit;
import efs.task.collections.Units.Unit;

import java.util.Scanner;



public class PlayerChoice {

    private static final Scanner scanner = new Scanner(System.in);

    public static void handlePlayerTurn() {
        Retinue player = RetinueMenager.getPlayer();
        boolean end = false;

        while (!end) {
            System.out.println("\n🎮 Co chcesz zrobić?");
            System.out.println("1. Przemieść się");
            System.out.println("2. Dodaj jednostkę (100 dukatów)");
            System.out.println("3. Ulepsz jednostki 50 dukatów za typ)");
            System.out.println("0. Zakończ turę");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> movePlayer(player);
                case 2 -> addUnit(player);
                case 3 -> upgradeUnits(player);
                case 0 -> end = true;
                default -> System.out.println("❌ Nieznana komenda.");
            }
        }
    }

    private static void movePlayer(Retinue player) {
        int speed = (int) player.overal_army_tats.getSpeed();
        System.out.println("📍 Obecna pozycja: (" + player.getX() + ", " + player.getY() + ")");
        System.out.println("👣 Maksymalny ruch: " + speed + " pól");
        System.out.print("Podaj dx: ");
        int dx = scanner.nextInt();
        System.out.print("Podaj dy: ");
        int dy = scanner.nextInt();

        if (Math.abs(dx) + Math.abs(dy) <= speed) {
            player.setPosition(dx, dy);
            System.out.println("✅ Przemieszczono na (" + player.getX() + ", " + player.getY() + ")");
        } else {
            System.out.println("❌ Zbyt duży ruch! Maksymalnie " + speed + " pól.");
        }
    }

    private static void addUnit(Retinue player) {
        if (player.owner.getDukaty() < 100) {
            System.out.println("❌ Za mało dukatów!");
            return;
        }

        System.out.println("🪖 Wybierz typ jednostki:");
        for (TypeOfUnit type : TypeOfUnit.values()) {
            System.out.println(type.ordinal() + ". " + type);
        }

        int typeIndex = scanner.nextInt();
        if (typeIndex < 0 || typeIndex >= TypeOfUnit.values().length) {
            System.out.println("❌ Zły typ.");
            return;
        }

        TypeOfUnit chosen = TypeOfUnit.values()[typeIndex];
        player.owner.removeDukaty(100);
        player.add_to_retinue(new java.util.ArrayList<>(java.util.List.of(new Unit(chosen, 0))));
        System.out.println("✅ Dodano jednostkę: " + chosen);
    }

    private static void upgradeUnits(Retinue player) {
        if (player.owner.getDukaty() < 50) {
            System.out.println("❌ Za mało dukatów!");
            return;
        }

        System.out.println("⚔️ Ulepszanie jednostek (+1 do dmg/def/speed) wszystkich w typie:");
        for (TypeOfUnit type : TypeOfUnit.values()) {
            System.out.println(type.ordinal() + ". " + type);
        }

        int typeIndex = scanner.nextInt();
        if (typeIndex < 0 || typeIndex >= TypeOfUnit.values().length) {
            System.out.println("❌ Zły typ.");
            return;
        }

        TypeOfUnit chosen = TypeOfUnit.values()[typeIndex];
        player.stats.get(chosen).upgradeStats(1, 1, 1);
        player.recalculateOverallStats();
        player.owner.removeDukaty(50);
        System.out.println("✅ Ulepszono jednostki typu: " + chosen);
    }
}
