package Engine;

import Map.GameMap;
import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Army.RetinueUtils;
import efs.task.collections.Units.TypeOfUnit;
import efs.task.collections.Units.Unit;

import java.util.Scanner;



public class PlayerChoice {

    public static final Scanner scanner = new Scanner(System.in);
    private static int spySentTurn = -1;
    int spyDelay = 2; // ile tur musi minąć
    String spyStatus;

    public static void handlePlayerTurn() {
        Retinue player = RetinueMenager.getPlayer();
        boolean end = false;
        int spyDelay = 2; // ile tur musi minąć
        String spyStatus;

        if (spySentTurn == -1) {
            spyStatus = " (gotowy do wysłania)";
        } else {
            int turnsLeft = spyDelay - (TurnManager.currentTurn - spySentTurn);
            if (turnsLeft > 0) {
                spyStatus = " (raport za " + turnsLeft + " tur)";
            } else {
                spyStatus = " (raport gotowy!)";
            }
        }
        while (!end) {



            System.out.println("\nLiczba dukatów: " + player.owner.getDukaty());
            System.out.println("🎮 Co chcesz zrobić?");
            System.out.println("1. Przemieść się");
            System.out.println("2. Dodaj jednostkę (100 dukatów)");
            System.out.println("3. Ulepsz jednostki 50 dukatów za typ)");
            System.out.println("4. Pokaz mape");
            System.out.println("5. Pokaż siłę wojska");
            System.out.println("6. Pokaż stan armii");
            System.out.println("7. Wyślij szpiega" + spyStatus);

            System.out.println("0. Zakończ turę");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> movePlayer(player);
                case 2 -> addUnit(player);
                case 3 -> upgradeUnits(player);
                case 4 -> showMap();
                case 5 -> showArmyStrength();
                case 6 -> player.displayArmyStats();
                case 7 -> displayArmies();

                case 0 -> end = true;
                default -> System.out.println("❌ Nieznana komenda.");
            }
        }

    }
    private static void displayArmies() {
        if (spySentTurn == -1) {
            spySentTurn = TurnManager.currentTurn;
            System.out.println("🕵️‍♂️ Szpieg został wysłany. Raport będzie za 2 tury.");
        } else if (TurnManager.currentTurn - spySentTurn >= 2) {
            System.out.println("📜 Raport szpiega:");
            for (Retinue retinue : RetinueMenager.getList()) {
                retinue.displayArmyStats();
            }
            spySentTurn = -1; // reset szpiega
        } else {
            System.out.println("⌛ Szpieg w drodze. Raport dostępny za " + (2 - (TurnManager.currentTurn - spySentTurn)) + " tury.");
        }
    }

    private static void showArmyStrength() {
        Retinue player = RetinueMenager.getPlayer();
        System.out.println("🟢 Twoja siła armii: " + player.overal_army_tats.totalPower());

        System.out.println("\n🔴 Przeciwnicy:");
        for (Retinue r : RetinueMenager.getList()) {
            if (r != player) {
                System.out.println("- " + r.owner.getName() + ": " + r.overal_army_tats.totalPower());
            }
        }
    }

    private static void showMap() {
        GameMap.updateMap(RetinueMenager.getList(), RetinueMenager.getPlayer());
        GameMap.displayMap();
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
            double newX = player.getX() + dx;
            double newY = player.getY() + dy;

            // sprawdź czy nowa pozycja nie koliduje z innymi
            boolean kolizja = false;
            for (Retinue r : RetinueMenager.getList()) {
                if (r != player && (int) r.getX() == (int) newX && (int) r.getY() == (int) newY) {
                    kolizja = true;
                    break;
                }
            }
            if(newX>GameMap.HEIGHT || newX<0 || newY>GameMap.HEIGHT || newY<0) {
                System.out.println("❌ Nie możesz opuscic pola walki!");
            }
            else if (kolizja) {
                System.out.println("❌ Nie możesz wejść na pole zajęte przez wroga!");
            } else {
                player.setPosition(dx, dy);
                System.out.println("✅ Przemieszczono na (" + player.getX() + ", " + player.getY() + ")");
            }
        } else {
            System.out.println("❌ Zbyt duży ruch! Maksymalnie " + speed + " pól.");
        }

        if (!GameMap.treasureCollected
                && (int) player.getX() == GameMap.treasureX
                && (int) player.getY() == GameMap.treasureY) {

            System.out.println("🎉 ZNALAZŁEŚ SKARB! Otrzymujesz 100 dukatów!");
            player.owner.giveDukaty(100);
            GameMap.treasureCollected = true;
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
