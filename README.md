

### 🔹 `Main.java`
- Tworzy obiekty `Hero`, `Unit`, `Retinue`.
- Przypisuje gracza (jego armię) do `RetinueMenager`.
- Uruchamia grę przez `TurnManager`.

### 🔹 `RetinueMenager`
- Przechowuje listę wszystkich armii (`Retinue`).
- Przechowuje gracza jako osobną referencję (`static Retinue player`).
- Umożliwia dodawanie nowych armii do świata.

### 🔹 `Retinue`
- Reprezentuje pojedynczą armię.
- Zawiera:
  - Bohatera (`Hero`)
  - Listę jednostek (`Unit`)
  - Pozycję na mapie (x, y)
  - Statystyki całkowite (`ArmyStats`) oraz per typ jednostki (`HashMap<TypeOfUnit, ArmyStats>`)

### 🔹 `ArmyStats`
- Przechowuje zagregowane statystyki:
  - Obrażenia (sumaryczne)
  - Obrona (sumaryczna)
  - Szybkość (średnia)
  - Ilość jednostek

### 🔹 `TurnManager`
- Odpowiada za przebieg każdej tury.
- Pobiera armię gracza z `RetinueMenager`.
- Dla każdej innej armii:
  - Sprawdza dystans do gracza przez `RetinueUtils.distance()`.
  - Jeśli dystans < 1 → inicjuje walkę (`Battles.simulateBattles()`).

### 🔹 `RetinueUtils`
- Narzędzia dla klas armii:
  - Obliczanie dystansu między armiami
  - Przemieszczanie armii

### 🔹 `Battles`
- Obecnie: wypisuje komunikat o walce.
- W przyszłości: będzie przeprowadzać realne starcia na podstawie statystyk `ArmyStats`.

---

## 🔄 Przepływ kontroli

1. **Main.java** tworzy wszystkie potrzebne dane (gracz, wrogowie).
2. Przypisuje gracza do `RetinueMenager.setPlayer()`.
3. Dodaje pozostałe armie.
4. Tworzy `TurnManager`, który uruchamia pętlę tur.
5. W każdej turze:
   - Jeśli jakaś armia znajdzie się w zasięgu (<1 jednostki) od gracza → wywoływana jest `simulateBattles()`.

---

## 🧠 Przemyślenia architektoniczne

- `static` zmienne w `RetinueMenager` są używane, by umożliwić globalny dostęp do gracza i listy armii bez konieczności przekazywania referencji.
- Przyszłe zmiany mogłyby zastąpić `static` dependency injectionem (np. przez kontener lub `GameContext`).
- `TurnManager` pełni rolę kontrolera logiki turowej – może zostać rozbudowany o więcej funkcji jak AI, wydarzenia, produkcję surowców itd.

---

## 🛠️ Możliwe kierunki rozwoju

- **System bitew** – oparty o faktyczne statystyki, z mechaniką strat i zwycięstw.
- **Mapa świata** – siatka pozycji, z ruchem armii i przeszkodami.
- **Zapis gry** – serializacja stanu.



