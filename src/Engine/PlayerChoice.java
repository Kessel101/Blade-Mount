package Engine;

import Map.GameMap;
import efs.task.collections.Army.ArmyStats;
import efs.task.collections.Army.Retinue;
import efs.task.collections.Army.RetinueMenager;
import efs.task.collections.Army.RetinueUtils;
import efs.task.collections.Units.Data;
import efs.task.collections.Units.TypeOfUnit;
import efs.task.collections.Units.Unit;

import java.util.Scanner;

import static efs.task.collections.Army.RetinueUtils.applyHeroClassBonusByUnit;
import static efs.task.collections.Units.Data.*;


public class PlayerChoice {

    public static final Scanner scanner = new Scanner(System.in);
    private static int spySentTurn = -1;
    int spyDelay = 2; // ile tur musi minąć
    String spyStatus;

    public static void handlePlayerTurn() {
        Retinue player = RetinueMenager.getPlayer();
        boolean end = false;
        boolean moved = false;
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
            System.out.println("2. Dodaj jednostkę");
            System.out.println("3. Ulepsz jednostki");
            System.out.println("4. Pokaz mape");
            System.out.println("5. Pokaż siłę wojska");
            System.out.println("6. Pokaż stan armii");
            System.out.println("7. Wyślij szpiega" + spyStatus);

            System.out.println("0. Zakończ turę");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> moved = movePlayer(player, moved);
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
    private static boolean movePlayer(Retinue player, boolean moved) {
        if (moved) {
            System.out.println("Wykonano już przemieszczenie w tej turze!");
            return true;
        }
        moved = false;

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
                moved = true;
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

        return moved;
    }


    private static void addUnit(Retinue player) {
        // Wyświetlamy ile gracz ma dukatów
        System.out.println("💰 Dukaty: " + player.owner.getDukaty());

        // Budujemy menu z kosztami wg poziomu
        System.out.println("Wybierz typ jednostki do rekrutacji:");
        for (TypeOfUnit type : TypeOfUnit.values()) {
            int level = player.stats.get(type).getLevel();
            int cost  = Data.statsMap.get(type)[level - 1].getHirering_cost();
            System.out.printf("%d. %-8s (poziom %d, koszt %d)%n",
                    type.ordinal()+1, type, level, cost);
        }

        // Odczyt wyboru
        int choice = scanner.nextInt();
        if (choice < 1 || choice > TypeOfUnit.values().length) {
            System.out.println("❌ Zły typ.");
            return;
        }
        // Mapowanie na enum
        TypeOfUnit chosen = TypeOfUnit.values()[choice-1];
        int level = player.stats.get(chosen).getLevel();
        System.out.println(level);

        int cost  = Data.statsMap.get(chosen)[level - 1].getHirering_cost();

        // Sprawdzenie i pobranie dukatów
        if (player.owner.getDukaty() < cost) {
            System.out.println("❌ Za mało dukatów! Potrzeba " + cost);
            return;
        }
        player.owner.removeDukaty(cost);

        // Rekrutacja jednej jednostki na poziomie bazowym tego typu
        player.add_to_retinue(
                new java.util.ArrayList<>(
                        java.util.List.of(new Unit(chosen, level - 1))
                )
        );

        System.out.println("✅ Zrekrutowano 1 x " + chosen +
                " (poziom " + level + "), koszt: " + cost);
    }


    private static void upgradeUnits(Retinue player) {
        if (player.owner.getDukaty() < 50) {
            System.out.println("❌ Za mało dukatów!");
            return;
        }

        System.out.println("⚔️ Ulepszanie jednostek (+1 do dmg/def/speed) wszystkich w typie:");
        for (TypeOfUnit type : TypeOfUnit.values()) {
            ArmyStats unitStats = player.stats.get(type);
            int level = unitStats.getLevel();
            System.out.print((type.ordinal() + 1) + " " + type + ", obecny poziom: " + level);

            if (level >= 3) {
                System.out.println(", osiągnięto poziom maksymalny");
            } else {
                int cost = Data.statsMap.get(type)[level].getUpgreade_cost();
                System.out.println(", koszt ulepszenia: " + cost);
            }
        }

        int typeIndex = scanner.nextInt();
        if (typeIndex < 1 || typeIndex > TypeOfUnit.values().length) {
            System.out.println("❌ Zły typ.");
            return;
        }

        TypeOfUnit chosen = TypeOfUnit.values()[typeIndex - 1];
        ArmyStats stats = player.stats.get(chosen);
        int level = stats.getLevel();

        if (level >= 3) {
            System.out.println("Poziom ma już maksymalną wartość");
            return;
        }

        int price = Data.statsMap.get(chosen)[level].getUpgreade_cost();
        if (player.owner.getDukaty() < price) {
            System.out.println("❌ Za mało dukatów na ulepszenie!");
            return;
        }

        stats.upgradeStats(1, 1, 1);
        stats.increase_level();
        player.recalculateOverallStats();
        player.owner.removeDukaty(price);

        System.out.println("✅ Ulepszono jednostki typu: " + chosen + ", pobrano " + price + " dukatów");
    }
}
