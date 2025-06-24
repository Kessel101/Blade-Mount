# Blade & Mount: Turowa gra strategiczna

##  Opis projektu

**Blade & Mount** to projekt stworzony na zajęcia z programowania w języku Java (IV semestr). Jest to turowa gra strategiczna (TBS), inspirowany klasykami gatunku, takimi jak seria *Heroes of Might and Magic*. Projekt symuluje zarządzanie armią, poruszanie się po mapie, walkę z wrogimi jednostkami oraz rozwój bohatera. Gra działa w oparciu o pętlę tur, a interfejs użytkownika jest realizowany w konsoli tekstowej.

---

##  Główne funkcjonalności

- **System turowy**: Rozgrywka podzielona jest na tury, w których gracz i przeciwnicy sterowani przez AI wykonują swoje akcje.
- **Zarządzanie armią**: Gracz dowodzi armią (`Retinue`), która składa się z bohatera oraz różnych typów jednostek (Piechota, Łucznicy, Kawaleria).
- **Klasy bohaterów**: Bohaterowie mogą należeć do różnych klas (`WARLORD`, `DEFENDER`, `ARCHMASTER`), co wpływa na statystyki ich armii.
- **Mapa świata**: Gra toczy się na siatce 10x10. Mapa wyświetlana jest w konsoli, z kolorowym oznaczaniem gracza, wrogów i skarbów.
- **Symulacja bitew**: Starcie dwóch armii jest automatycznie symulowane. Wynik zależy od statystyk jednostek oraz mechaniki kontr (np. Kawaleria jest silniejsza przeciwko Łucznikom).
- **Prosta sztuczna inteligencja**: Wrogie armie dynamicznie decydują, czy atakować gracza, czy się wycofać, na podstawie porównania sił.
- **Zdarzenia losowe i zasoby**: W każdej turze mogą wystąpić losowe zdarzenia, a gracz i wrogowie otrzymują nowe zasoby.

---

##  Struktura projektu

```
.
├── src
│   ├── AI
│   │   └── AIMoves.java          # Logika sztucznej inteligencji wrogów
│   ├── Engine
│   │   ├── Battles.java          # Mechanika symulacji bitew
│   │   ├── PlayerChoice.java     # Obsługa decyzji gracza
│   │   ├── Resources.java        # Zarządzanie zasobami
│   │   ├── randomEvents.java     # Implementacja zdarzeń losowych
│   │   └── TurnManager.java      # Główny kontroler pętli gry
│   ├── Map
│   │   ├── GameMap.java          # Zarządzanie i wyświetlanie mapy
│   │   └── MapTile.java          # Pojedyncze pole na mapie
│   ├── efs/task/collections
│   │   ├── Army                  # Klasy związane z armią (Retinue, ArmyStats)
│   │   ├── Hero                  # Klasy związane z bohaterem (Hero, HeroStats, HeroClass)
│   │   └── Units                 # Klasy związane z jednostkami (Unit, Stats, TypeOfUnit)
│   ├── main/resources/sounds
│   │   └── ...                   # Pliki dźwiękowe
│   └── Main.java                 # Główny plik startowy aplikacji
└── README.md
```


## Architektura i Wzorce Projektowe

### Paradygmat Obiektowy (OOP)

Projekt jest  oparty na zasadach programowania obiektowego

-   **Enkapsulacja**: Każda klasa ukrywa swoje wewnętrzne dane, udostępniając jedynie publiczne metody do interakcji. Chroni to stan obiektów przed niekontrolowanymi modyfikacjami.
    > **Przykład**: Klasa `Hero` przechowuje prywatne pole `ducats` i pozwala na jego modyfikację tylko przez publiczne metody `giveDukaty()` i `removeDukaty()`.

-   **Abstrakcja**: Złożone operacje są ukryte za prostymi interfejsami. Użytkownik (inny programista) nie musi znać szczegółów implementacji, aby korzystać z danego komponentu.
    > **Przykład**: `TurnManager.nextTurn()` wykonuje serię skomplikowanych operacji (ruch gracza, AI, walka, zdarzenia), ale z zewnątrz wywołanie jest bardzo proste.

-   **Kompozycja ponad dziedziczenie**: Zamiast tworzyć skomplikowane hierarchie klas, projekt faworyzuje łączenie obiektów w całość. 
    > **Przykład**: `Retinue` (armia) nie dziedziczy po żadnej klasie, lecz *składa się* z obiektu `Hero` i listy obiektów `Unit`.

###  Wzorce Projektowe

-   **Singleton (Rejestr Globalny)**: Klasa `RetinueMenager` działa jak globalny zarządca armii. Dzięki statycznym metodom (`getPlayer()`, `getList()`) zapewnia jeden, spójny punkt dostępu do wszystkich jednostek na mapie z dowolnego miejsca w kodzie.
    > **Przykład**:
    > ```java
    > // Pobranie armii gracza z dowolnego miejsca w kodzie
    > Retinue player = RetinueMenager.getPlayer();
    > ```

-   **Prosta Fabryka (Simple Factory)**: Konstruktor klasy `Unit` pełni rolę fabryki. Na podstawie podanego typu (`TypeOfUnit`) decyduje, jakie statystyki przypisać nowej jednostce, ukrywając logikę ich tworzenia.
    > **Przykład**:
    > ```java
    > // Unit.java - konstruktor decyduje o statystykach na podstawie typu
    > public Unit(TypeOfUnit type, int level) {
    >     switch (type) {
    >         case INFANTRY:
    >             stats = Data.infantry_stats[level];
    >             break;
    >         // ...
    >     }
    > }
    > ```

---

    
##  Kluczowe komponenty

-   **`Main.java`**: Inicjalizuje obiekty gry (bohaterów, armie), wczytuje dźwięki i uruchamia główną pętlę gry zarządzaną przez `TurnManager`.
-   **`TurnManager`**: Odpowiada za cykl turowy. W każdej turze kolejno wykonuje akcje gracza, ruchy AI, symuluje bitwy, sprawdza warunki zwycięstwa i zarządza zasobami.
-   **`Retinue`**: Reprezentuje pojedynczą armię. Przechowuje informacje o bohaterze, jednostkach, pozycji na mapie oraz zagregowanych statystykach.
-   **`Battles`**: Symuluje starcia między armiami. Walka opiera się na porównaniu mocy poszczególnych typów jednostek, uwzględniając bonusy z systemu kontr.
-   **`AIMoves`**: Określa zachowanie wrogich armii. AI podejmuje proste decyzje taktyczne, takie jak pościg za słabszym graczem lub ucieczka przed silniejszym.
-   **`GameMap`**: Tworzy i zarządza mapą gry. Odpowiada za rysowanie planszy w konsoli, wraz z pozycjami wszystkich armii i obiektów specjalnych.

    
---

## Rozgrywka
1.  **Start Gry**:
    -   Po uruchomieniu aplikacji odtwarzana jest muzyka w tle.
    -   Gracz jest proszony o wybranie klasy dla swojego bohatera (np. `WARLORD`, `DEFENDER`). Wybór ten wpływa na bonusy dla jego armii.
    -   
![image](https://github.com/user-attachments/assets/93e70cd5-8a97-477e-8097-39c25fbfd3b8)


2.  **Inicjalizacja Świata**:
    -   Tworzona jest armia gracza (`Retinue`) oraz armie przeciwników (AI).
    -   Na mapie o wymiarach 10x10 losowo umieszczany jest skarb (`X`), którego zebranie daje bonusy.
    -   Mapa jest wyświetlana w konsoli. Armia gracza oznaczona jest jako zielone `P`, a wrogowie jako czerwone symbole (pierwsza litera imienia bohatera).

3.  **Cykl Turowy**:
    -   Gra przechodzi do pętli turowej, zarządzanej przez `TurnManager`.
    -   **Tura Gracza**: Gracz jest proszony o podjęcie decyzji – najczęściej jest to ruch armią po mapie za pomocą klawiszy W, A, S, D. Jeśli gracz wejdzie na pole ze skarbem, otrzymuje złoto i jego armia jest wzmacniana.
    -   **Tura AI**: Każda z wrogich armii wykonuje swój ruch. AI decyduje, czy zbliżyć się do gracza (jeśli czuje się silniejsza), czy od niego uciekać.

![image](https://github.com/user-attachments/assets/758fef41-78db-4ad5-b11f-635122ad62c1)
![image](https://github.com/user-attachments/assets/0c04078a-ae8c-4b5a-ae4e-4910bb31418c)


4.  **Walka**:
    -   Jeśli po ruchu którakolwiek z wrogich armii znajdzie się w bezpośrednim sąsiedztwie armii gracza (dystans < 2), automatycznie rozpoczyna się bitwa.
    -   `Battles.simulateBattles()` rozstrzyga starcie. Jednostki obu stron walczą ze sobą, a system kontr (np. Kawaleria vs Łucznicy) ma kluczowe znaczenie.
    -   Obie armie ponoszą straty. Jeśli liczba jednostek w którejś z armii spadnie do zera, zostaje ona pokonana.
    -   
![image](https://github.com/user-attachments/assets/d9c4fb10-1ede-4d7b-9627-a52532be7c99)

5.  **Koniec Tury**:
    -   Po wykonaniu ruchów przez wszystkie armie, sprawdzane są warunki zwycięstwa/przegranej.
    -   **Zwycięstwo**: Jeśli wszystkie wrogie armie zostaną pokonane, gra kończy się komunikatem o zwycięstwie.
    -   **Porażka**: Jeśli armia gracza zostanie pokonana, gra natychmiast się kończy.
    -   Jeśli gra toczy się dalej, mogą wystąpić zdarzenia losowe, a zasoby (złoto) są uzupełniane.

6.  **Kontynuacja**:
    -   Rozpoczyna się nowa tura, a cały cykl się powtarza. 
---
##  Dźwięk

Gra wykorzystuje prosty menedżer dźwięku (`SoundManager`) do obsługi muzyki w tle.

---
##  Setup

1.  Sklonuj repozytorium.
2.  Uruchom metodę `main` w klasie `Main.java`.
3.  Postępuj zgodnie z instrukcjami wyświetlanymi w konsoli, aby rozpocząć grę.

---



