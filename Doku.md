# Technische Dokumentation – Tourist-Group2

**Projekt:** Lower Austria Tourist Agency – Hotelverwaltungssystem  
**Sprache:** Java 23  
**Build-Tool:** Maven  
**GUI-Framework:** Java Swing  
**Erstellt:** 2026

---

## Inhaltsverzeichnis

1. [Projektübersicht](#projektübersicht)
2. [Projektstruktur](#projektstruktur)
3. [Abhängigkeiten (pom.xml)](#abhängigkeiten)
4. [Package `org.example` – Datenmodell & Datenzugriff](#package-orgexample)
   - [Hotel](#klasse-hotel)
   - [Hotelutil](#klasse-hotelutil)
   - [occupancies](#klasse-occupancies)
   - [occupanciesutil](#klasse-occupanciesutil)
   - [Main](#klasse-main-orgexample)
5. [Package `org.ui` – Benutzeroberfläche & Login](#package-orgui)
   - [myAPP](#klasse-myapp)
   - [login](#klasse-login)
   - [Senior](#klasse-senior)
   - [Senior2](#klasse-senior2)
   - [Junior](#klasse-junior)
   - [Admin](#klasse-admin)
   - [Hashing](#klasse-hashing)
   - [AddingOccupancies](#klasse-addingoccupancies)
6. [Package `org.ui_master_data` – Stammdatenverwaltung](#package-orgui_master_data)
   - [Main](#klasse-main-orgui_master_data)
   - [MainFrame](#klasse-mainframe)
   - [HotelTableModel](#klasse-hoteltablemodel)
   - [newHotelwindow](#klasse-newhotelwindow)
   - [summarydata](#klasse-summarydata)
   - [summarywindow](#klasse-summarywindow)
   - [popup](#klasse-popup)
7. [Package `org.ui_transactional_data` – Transaktionsdatenverwaltung](#package-orgui_transactional_data)
   - [Main](#klasse-main-orgui_transactional_data)
   - [MainFrame_transaction](#klasse-mainframe_transaction)
   - [HotelTableModel_transaction](#klasse-hoteltablemodel_transaction)
   - [newOccupanciesWindow](#klasse-newoccupancieswindow)
   - [EditingWindow_transaction](#klasse-editingwindow_transaction)
8. [Ressourcen](#ressourcen)
9. [Benutzerrollen & Login-Logik](#benutzerrollen--login-logik)
10. [Datenfluss](#datenfluss)

---

## Projektübersicht

Diese Desktop-Applikation dient der Verwaltung von Hotels und deren Belegungsdaten für die Niederösterreichische Tourismusagentur (*Lower Austria Tourist Agency*). Die Anwendung ist als Java-Swing-GUI realisiert und liest Stammdaten sowie Transaktionsdaten aus CSV-Dateien. Es gibt drei Benutzerrollen (Junior, Senior, Admin) mit unterschiedlichen Zugriffsrechten.

---

## Projektstruktur

```
tourist-group2-master/
├── pom.xml
└── src/main/
    ├── java/
    │   ├── org/example/
    │   │   ├── Hotel.java
    │   │   ├── Hotelutil.java
    │   │   ├── Main.java
    │   │   ├── occupancies.java
    │   │   └── occupanciesutil.java
    │   ├── org/ui/
    │   │   ├── myAPP.java
    │   │   ├── login.java
    │   │   ├── Senior.java
    │   │   ├── Senior2.java
    │   │   ├── Junior.java
    │   │   ├── Admin.java
    │   │   ├── Hashing.java
    │   │   └── AddingOccupancies.java
    │   ├── org/ui_master_data/
    │   │   ├── Main.java
    │   │   ├── MainFrame.java
    │   │   ├── HotelTableModel.java
    │   │   ├── newHotelwindow.java
    │   │   ├── summarydata.java
    │   │   ├── summarywindow.java
    │   │   └── popup.java
    │   └── org/ui_transactional_data/
    │       ├── Main.java
    │       ├── MainFrame_transaction.java
    │       ├── HotelTableModel_transaction.java
    │       ├── newOccupanciesWindow.java
    │       └── EditingWindow_transaction.java
    └── resources/
        ├── Hotel.csv
        ├── Hoteldata.csv
        └── Logo.jpg
```

---

## Abhängigkeiten

Definiert in `pom.xml`, Java-Version 23:

| Bibliothek | Version | Zweck |
|---|---|---|
| `org.projectlombok:lombok` | 1.18.44 | Automatische Getter/Setter/Konstruktoren via Annotationen |
| `org.hibernate.orm:hibernate-core` | 6.6.34.Final | ORM-Framework (vorbereitet, noch nicht aktiv genutzt) |
| `com.microsoft.sqlserver:mssql-jdbc` | 12.6.4.jre11 | SQL Server JDBC-Treiber (vorbereitet) |
| `jakarta.persistence:jakarta.persistence-api` | 3.1.0 | JPA-API für Hibernate |
| `org.slf4j:slf4j-simple` | 2.0.12 | Logging für Hibernate-Ausgaben |
| `org.apache.commons:commons-csv` | 1.10.0 | CSV-Verarbeitung |

---

## Package `org.example`

Enthält das Datenmodell und die Datenzugriffsschicht (CSV-Loader).

---

### Klasse `Hotel`

**Paket:** `org.example`  
**Datei:** `Hotel.java`  
**Annotationen:** `@Data`, `@AllArgsConstructor` (Lombok)

Repräsentiert einen einzelnen Hoteldatensatz aus der Stammdatendatei.

#### Felder

| Feld | Typ | Beschreibung |
|---|---|---|
| `id` | `int` | Eindeutige Hotel-ID |
| `category` | `String` | Hotelkategorie (Sternebewertung als String, z.B. `"*"` bis `"*****"`) |
| `name` | `String` | Hotelname |
| `owner` | `String` | Name des Eigentümers |
| `contact` | `String` | Kontaktperson |
| `address` | `String` | Adresse |
| `city` | `String` | Stadt |
| `citycode` | `String` | Stadtcode / PLZ |
| `phone` | `String` | Telefonnummer |
| `noRooms` | `int` | Anzahl Zimmer gesamt |
| `noBeds` | `int` | Anzahl Betten gesamt |

Lombok generiert automatisch alle Getter, Setter, `equals()`, `hashCode()`, `toString()` und einen Konstruktor mit allen Feldern.

---

### Klasse `Hotelutil`

**Paket:** `org.example`  
**Datei:** `Hotelutil.java`

Utility-Klasse zum Laden der Hotelstammdaten aus der CSV-Datei.

#### Methoden

```java
public static ArrayList<Hotel> HotelData() throws FileNotFoundException
```

Liest die Datei `src/main/resources/Hotel.csv` zeilenweise ein. Die erste Zeile (Header) wird übersprungen. Anführungszeichen werden aus den Werten entfernt. Jede Zeile wird in ein `Hotel`-Objekt umgewandelt und zur Liste hinzugefügt.

**Rückgabe:** `ArrayList<Hotel>` – alle geladenen Hotels  
**Wirft:** `FileNotFoundException` – wenn die CSV-Datei nicht gefunden wird

**CSV-Format erwartet:** `id,category,name,owner,contact,address,city,cityCode,phone,noRooms,noBeds`

---

### Klasse `occupancies`

**Paket:** `org.example`  
**Datei:** `occupancies.java`  
**Annotationen:** `@Data`, `@AllArgsConstructor` (Lombok)

Repräsentiert einen Belegungsdatensatz (Transaktionsdaten) eines Hotels für einen bestimmten Monat.

#### Felder

| Feld | Typ | Beschreibung |
|---|---|---|
| `id` | `int` | Referenz auf Hotel-ID (Fremdschlüssel) |
| `room` | `int` | Gesamtanzahl Zimmer |
| `usedrooms` | `int` | Belegte Zimmer |
| `beds` | `int` | Gesamtanzahl Betten |
| `usedbeds` | `int` | Belegte Betten |
| `year` | `int` | Jahr der Belegung |
| `month` | `int` | Monat der Belegung (1–12) |

---

### Klasse `occupanciesutil`

**Paket:** `org.example`  
**Datei:** `occupanciesutil.java`

Utility-Klasse zum Laden der Belegungsdaten aus der CSV-Datei.

#### Methoden

```java
public static List<occupancies> master_data_occupancies() throws FileNotFoundException
```

Liest die Datei `src/main/resources/Hoteldata.csv` zeilenweise ein. Es wird kein Header übersprungen. Jede Zeile wird in ein `occupancies`-Objekt umgewandelt.

**Rückgabe:** `List<occupancies>` – alle geladenen Belegungsdatensätze  
**Wirft:** `FileNotFoundException` – wenn die CSV-Datei nicht gefunden wird

**CSV-Format erwartet:** `id,room,usedrooms,beds,usedbeds,year,month`

---

### Klasse `Main` (`org.example`)

**Paket:** `org.example`  
**Datei:** `Main.java`

Einfacher Testeinstiegspunkt zum direkten Aufruf der Datenlader.

#### Methoden

```java
public static void main(String[] args) throws FileNotFoundException
```

Ruft `occupanciesutil.master_data_occupancies()` und `Hotelutil.HotelData()` auf. Dient ausschließlich Testzwecken, kein GUI-Start.

---

## Package `org.ui`

Enthält den Anwendungseinstieg, das Login-Fenster und die rollenspezifischen Hauptfenster.

---

### Klasse `myAPP`

**Paket:** `org.ui`  
**Datei:** `myAPP.java`

**Haupteinstiegspunkt der Anwendung.**

#### Methoden

```java
public static void main(String[] args)
```

Erstellt eine Instanz von `login` und macht sie sichtbar. Dies ist der einzige Startpunkt für die gesamte Anwendung.

---

### Klasse `login`

**Paket:** `org.ui`  
**Datei:** `login.java`  
**Erbt von:** `JFrame`

Login-Fenster mit Benutzername/Passwort-Feldern. Zeigt das Logo der Applikation und leitet nach erfolgreichem Login in das rollenspezifische Fenster weiter.

#### Felder

| Feld | Typ | Beschreibung |
|---|---|---|
| `ATP` | `static int` | Zähler für fehlgeschlagene Login-Versuche (max. 3) |

#### Konstruktor

```java
public login()
```

Baut die gesamte Login-UI auf:
- Lädt `/logo.jpg` als Header-Bild (220×100 px)
- Erstellt Eingabefelder für Benutzername (`JTextField`) und Passwort (`JPasswordField`)
- Der Login-Button ist deaktiviert, solange Benutzername oder Passwort kürzer als 5 Zeichen sind
- CaretListener auf beiden Feldern aktiviert/deaktiviert den Button dynamisch
- Nach mehr als 3 Login-Versuchen (gezählt über `ATP`) wird die Anwendung mit `System.exit(0)` beendet

#### Login-Logik

| Benutzername | Passwort | Aktion |
|---|---|---|
| `Junior` | `Junior` | Öffnet `Junior`-Fenster, schließt Login |
| `Senior` | `Senior` | Öffnet `Senior2`-Fenster, schließt Login |
| `admin` | `admin` | Öffnet `Admin`-Fenster, schließt Login |
| Andere | — | Zeigt Fehlermeldung „Invalid username or password" |

---

### Klasse `Senior`

**Paket:** `org.ui`  
**Datei:** `Senior.java`  
**Erbt von:** `JFrame`

Einfaches Auswahlmenü für die Senior-Rolle mit zwei Schaltflächen zur Navigation.

#### Konstruktor

```java
public Senior()
```

Erstellt ein Fenster (400×200 px) mit zwei Buttons:
- **Master Data** → öffnet `MainFrame` (Stammdatenverwaltung), schließt dieses Fenster
- **Transactional Data** → öffnet `MainFrame_transaction` (Belegungsdatenverwaltung), schließt dieses Fenster

> Hinweis: In der aktuellen Login-Logik wird statt `Senior` die Klasse `Senior2` geöffnet. `Senior` ist eine ältere Version.

---

### Klasse `Senior2`

**Paket:** `org.ui`  
**Datei:** `Senior2.java`  
**Erbt von:** `JFrame`

Hauptfenster für die Senior-Rolle. Zeigt eine vollständige Hotelübersichtstabelle mit Filter-, Hinzufüge-, Lösch- und Belegungsfunktionen.

#### Felder

| Feld | Typ | Beschreibung |
|---|---|---|
| `sorter` | `TableRowSorter<HotelTableModel>` | Sortierer/Filter für die Hoteltabelle |
| `model_transaction` | `HotelTableModel_transaction` | Datenmodell für Belegungsdaten |

#### Konstruktor

```java
public Senior2()
```

Aufbau des Hauptfensters (600×400 px):
- Lädt alle Hotels via `Hotelutil.HotelData()` in ein `HotelTableModel`
- Lädt alle Belegungsdaten via `occupanciesutil.master_data_occupancies()` in ein `HotelTableModel_transaction`
- Stellt eine `JTable` mit Sortierfunktion dar
- Nordbereich mit Filterfeld und Buttons
- Westbereich mit Delete-Button

#### Buttons & Funktionen

| Button | Funktion |
|---|---|
| **Filter + execute** | Filtert die Hoteltabelle per Regex-Suche (unterstützt `*` als Wildcard) |
| **Summary** | Öffnet `summarywindow` mit Statistikübersicht |
| **New Hotel** | Öffnet `newHotelwindow` zum Anlegen eines neuen Hotels |
| **Load Occupancies** | Öffnet `AddingOccupancies` für das aktuell ausgewählte Hotel |
| **Delete Hotel** | Löscht das ausgewählte Hotel nach Bestätigungsdialog; löscht auch alle zugehörigen Belegungsdaten |

#### Methoden

```java
public HotelTableModel_transaction getModel()
```

Gibt das aktuell verwendete Transaktionsdatenmodell zurück.

---

### Klasse `Junior`

**Paket:** `org.ui`  
**Datei:** `Junior.java`  
**Erbt von:** `JFrame`

Platzhalter-Fenster für die Junior-Rolle. Aktuell ohne Funktionsinhalt implementiert (500×500 px, nur Fenstertitel „Junior").

---

### Klasse `Admin`

**Paket:** `org.ui`  
**Datei:** `Admin.java`  
**Erbt von:** `JFrame`

Platzhalter-Fenster für die Admin-Rolle. Aktuell ohne Funktionsinhalt implementiert (500×500 px, nur Fenstertitel „Admin").

---

### Klasse `Hashing`

**Paket:** `org.ui`  
**Datei:** `Hashing.java`

Platzhalter-Klasse für eine geplante Passwort-Hashing-Funktion. Die `main`-Methode ist vorhanden, aber leer. Noch nicht implementiert.

---

### Klasse `AddingOccupancies`

**Paket:** `org.ui`  
**Datei:** `AddingOccupancies.java`  
**Erbt von:** `JFrame`

Fenster zur Anzeige und Ergänzung von Belegungsdaten für ein einzelnes Hotel.

#### Konstruktor

```java
public AddingOccupancies(HotelTableModel model, int hotelId, HotelTableModel_transaction tmodel)
```

| Parameter | Typ | Beschreibung |
|---|---|---|
| `model` | `HotelTableModel` | Stammdatenmodell (für die obere Tabelle) |
| `hotelId` | `int` | ID des ausgewählten Hotels |
| `tmodel` | `HotelTableModel_transaction` | Transaktionsdatenmodell (für die untere Tabelle) |

Erstellt ein geteiltes Fenster (`JSplitPane`) mit:
- Oberer Tabelle: Stammdaten des Hotels (gefiltert auf `hotelId`)
- Unterer Tabelle: Belegungsdaten des Hotels (gefiltert auf `hotelId`), einzelne Zellen editierbar
- Button **Add Occupancy** → öffnet `newOccupanciesWindow` für neue Belegungseinträge

---

## Package `org.ui_master_data`

Enthält Fenster und Tabellenmodell für die Stammdatenverwaltung (Hotels).

---

### Klasse `Main` (`org.ui_master_data`)

**Paket:** `org.ui_master_data`  
**Datei:** `Main.java`

Alternativer Einstiegspunkt, der direkt das `MainFrame` öffnet (ohne Login).

```java
public static void main(String[] args) throws FileNotFoundException
```

---

### Klasse `MainFrame`

**Paket:** `org.ui_master_data`  
**Datei:** `MainFrame.java`  
**Erbt von:** `JFrame`

Standalone-Fenster für die Stammdatenverwaltung. Ähnlich zu `Senior2`, jedoch ohne Belegungsdaten und Delete-Funktion.

#### Konstruktor

```java
public MainFrame() throws FileNotFoundException
```

Erstellt ein 500×500 px Fenster mit:
- Hoteltabelle mit Sortierung und Einzelzellen-Editierung
- Filterbereich (Textfeld + execute-Button mit Regex/Wildcard-Unterstützung)
- Button **Summary** → öffnet `summarywindow`
- Button **New Hotel** → öffnet `newHotelwindow`

---

### Klasse `HotelTableModel`

**Paket:** `org.ui_master_data`  
**Datei:** `HotelTableModel.java`  
**Erbt von:** `AbstractTableModel`

Swing-Tabellenmodell für die Hotel-Stammdaten.

#### Spalten

`id`, `category`, `name`, `owner`, `contact`, `address`, `city`, `cityCode`, `phone`, `noRooms`, `noBeds`

#### Konstruktor

```java
public HotelTableModel(ArrayList<Hotel> hotels)
```

#### Methoden

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `getColumnName(int column)` | `String` | Gibt den Spaltennamen zurück |
| `getRowCount()` | `int` | Anzahl der Hotels in der Liste |
| `getColumnCount()` | `int` | Anzahl Spalten (11) |
| `getValueAt(int rowIndex, int columnIndex)` | `Object` | Gibt den Zellwert zurück |
| `setValueAt(Object value, int rowIndex, int columnIndex)` | `void` | Setzt einen Zellwert; ID-Spalte (0) ist schreibgeschützt |
| `isCellEditable(int rowIndex, int columnIndex)` | `boolean` | Alle Spalten außer `id` (Spalte 0) sind editierbar |
| `getColumnClass(int columnIndex)` | `Class<?>` | Spalten 0, 9, 10 sind `Integer.class`, Rest `String.class` |
| `maxId()` | `int` | Gibt die höchste vorhandene Hotel-ID zurück |
| `addHotel(Hotel hotel)` | `void` | Fügt ein Hotel hinzu und benachrichtigt die Tabelle |
| `removeHotelById(int hotelId)` | `void` | Entfernt ein Hotel anhand der ID und benachrichtigt die Tabelle |
| `getHotel(int in)` | `Hotel` | Gibt das Hotel am angegebenen Index zurück |
| `refreshRow(int row)` | `void` | Löst ein Table-Row-Updated-Ereignis für die angegebene Zeile aus |

---

### Klasse `newHotelwindow`

**Paket:** `org.ui_master_data`  
**Datei:** `newHotelwindow.java`  
**Erbt von:** `JFrame`

Eingabefenster zum Anlegen eines neuen Hotels.

#### Konstruktor

```java
public newHotelwindow(HotelTableModel model)
```

Erstellt ein 1000×200 px Fenster mit Eingabefeldern für alle Hotelattribute außer `id` (wird automatisch als `maxId() + 1` berechnet). Nach dem Speichern wird gefragt, ob direkt Belegungsdaten angelegt werden sollen:
- **Ja** → öffnet `MainFrame_transaction` und `newOccupanciesWindow`
- **Nein** → öffnet `popup` (Bestätigung „Hotel has been saved")

Alle Felder müssen befüllt sein (Pflichtfeldprüfung mit Fehlerdialog bei Leerfeldern).

---

### Klasse `summarydata`

**Paket:** `org.ui_master_data`  
**Datei:** `summarydata.java`

Berechnungsklasse für die Hotelstatistik nach Kategorie.

#### Methoden

```java
public static String[][] summarydata() throws FileNotFoundException
```

Liest alle Hotels und gruppiert sie nach Sternekategorie (1–5 Sterne, bestimmt durch die Länge des Kategorie-Strings). Berechnet pro Kategorie:
- Anzahl Hotels
- Durchschnittliche Bettenzahl (auf 2 Dezimalstellen gerundet)
- Durchschnittliche Zimmerzahl (auf 2 Dezimalstellen gerundet)

**Rückgabe:** `String[5][4]` – 5 Zeilen (1–5 Sterne), 4 Spalten: `[Kategoriename, Anzahl, Ø Betten, Ø Zimmer]`

---

### Klasse `summarywindow`

**Paket:** `org.ui_master_data`  
**Datei:** `summarywindow.java`  
**Erbt von:** `JFrame`

Anzeigefenster für die Statistikzusammenfassung.

#### Konstruktor

```java
public summarywindow() throws FileNotFoundException
```

Erstellt ein 500×500 px Fenster in einem `GridLayout(6,4)`. Zeigt Spaltenköpfe (`Category`, `Sum hotels`, `average beds`, `average rooms`) und die von `summarydata.summarydata()` berechneten Werte als nicht editierbare Textfelder.

---

### Klasse `popup`

**Paket:** `org.ui_master_data`  
**Datei:** `popup.java`  
**Erbt von:** `JFrame`

Einfaches Bestätigungsfenster, das nach dem Speichern eines Hotels ohne Belegungserfassung angezeigt wird.

#### Konstruktor

```java
public popup()
```

300×300 px Fenster, nicht resizable, zeigt „Hotel has been saved" in Fettschrift. Schließt sich über einen OK-Button.

---

## Package `org.ui_transactional_data`

Enthält Fenster und Tabellenmodell für die Verwaltung der Belegungsdaten (Transaktionsdaten).

---

### Klasse `Main` (`org.ui_transactional_data`)

**Paket:** `org.ui_transactional_data`  
**Datei:** `Main.java`

Alternativer Einstiegspunkt, der direkt `MainFrame_transaction` öffnet (ohne Login).

```java
public static void main(String[] args) throws FileNotFoundException
```

---

### Klasse `MainFrame_transaction`

**Paket:** `org.ui_transactional_data`  
**Datei:** `MainFrame_transaction.java`  
**Erbt von:** `JFrame`

Hauptfenster für die Belegungsdatenverwaltung.

#### Felder

| Feld | Typ | Beschreibung |
|---|---|---|
| `model_transaction` | `HotelTableModel_transaction` | Datenmodell für Belegungsdaten |

#### Konstruktor

```java
public MainFrame_transaction() throws FileNotFoundException
```

Erstellt ein 500×500 px Fenster mit einer `JTable`, die alle Belegungsdaten anzeigt. Bei Doppelklick auf eine Zeile öffnet sich `EditingWindow_transaction` zum Bearbeiten des Eintrags.

#### Methoden

```java
public HotelTableModel_transaction getModel()
```

Gibt das Transaktionsdatenmodell zurück (wird von `newHotelwindow` und `Senior2` genutzt).

---

### Klasse `HotelTableModel_transaction`

**Paket:** `org.ui_transactional_data`  
**Datei:** `HotelTableModel_transaction.java`  
**Erbt von:** `AbstractTableModel`

Swing-Tabellenmodell für die Belegungsdaten.

#### Spalten

`id`, `rooms`, `usedrooms`, `beds`, `usedbeds`, `year`, `month`

#### Konstruktor

```java
public HotelTableModel_transaction(ArrayList<occupancies> hotels)
```

#### Methoden

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `getColumnName(int column)` | `String` | Gibt den Spaltennamen zurück |
| `getRowCount()` | `int` | Anzahl der Belegungsdatensätze |
| `getColumnCount()` | `int` | Anzahl Spalten (7) |
| `getValueAt(int rowIndex, int columnIndex)` | `Object` | Gibt den Zellwert zurück |
| `setValueAt(Object value, int rowIndex, int columnIndex)` | `void` | Setzt einen Integer-Zellwert; Spalte 0 (`id`) ist schreibgeschützt |
| `isCellEditable(int rowIndex, int columnIndex)` | `boolean` | Alle Spalten außer `id` (Spalte 0) sind editierbar |
| `getColumnClass(int columnIndex)` | `Class<?>` | Alle Spalten sind `Integer.class` |
| `getHotel(int in)` | `occupancies` | Gibt den Belegungsdatensatz am angegebenen Index zurück |
| `refreshRow(int row)` | `void` | Löst ein Row-Updated-Ereignis aus |
| `addOccupancies(occupancies oc)` | `void` | Fügt einen neuen Belegungsdatensatz hinzu |
| `removeRows(int[] rows)` | `void` | Entfernt mehrere Zeilen (sortiert absteigend, um Indexverschiebung zu vermeiden); ruft `fireTableDataChanged()` auf |

---

### Klasse `newOccupanciesWindow`

**Paket:** `org.ui_transactional_data`  
**Datei:** `newOccupanciesWindow.java`  
**Erbt von:** `JFrame`

Eingabefenster zum Anlegen neuer Belegungsdaten für ein Hotel.

#### Konstruktor

```java
public newOccupanciesWindow(int id, HotelTableModel_transaction trmodel)
```

| Parameter | Typ | Beschreibung |
|---|---|---|
| `id` | `int` | Hotel-ID, der die Belegung zugeordnet wird |
| `trmodel` | `HotelTableModel_transaction` | Datenmodell, dem der neue Datensatz hinzugefügt wird |

Erstellt ein 1000×200 px Fenster mit Eingabefeldern für `room`, `usedrooms`, `beds`, `usedbed`, `year`, `month`. Die `id` wird automatisch aus dem Parameter übernommen. Alle Felder sind Pflichtfelder. Nach dem Speichern wird der neue Datensatz per `trmodel.addOccupancies()` zum Modell hinzugefügt und das Fenster geschlossen.

---

### Klasse `EditingWindow_transaction`

**Paket:** `org.ui_transactional_data`  
**Datei:** `EditingWindow_transaction.java`  
**Erbt von:** `JDialog`

Dialog zum Bearbeiten eines bestehenden Belegungsdatensatzes.

#### Konstruktor

```java
public EditingWindow_transaction(MainFrame_transaction mainFrame, occupancies hotel, Runnable okAction)
```

| Parameter | Typ | Beschreibung |
|---|---|---|
| `mainFrame` | `MainFrame_transaction` | Elternfenster |
| `hotel` | `occupancies` | Der zu bearbeitende Datensatz (vorausgefüllte Felder) |
| `okAction` | `Runnable` | Callback für Aktualisierung der Tabellenzeile nach dem Speichern |

Zeigt alle 7 Felder des Belegungsdatensatzes als editierbare Textfelder. Der Save-Button zeigt aktuell nur eine Bestätigungsmeldung an; die tatsächliche Datenübernahme in das Modell ist noch nicht vollständig implementiert (der okAction-Callback ist auskommentiert).

---

## Ressourcen

| Datei | Pfad | Inhalt |
|---|---|---|
| `Hotel.csv` | `src/main/resources/Hotel.csv` | Stammdaten der Hotels (Header + Datensätze) |
| `Hoteldata.csv` | `src/main/resources/Hoteldata.csv` | Belegungsdaten (ohne Header) |
| `Logo.jpg` | `src/main/resources/Logo.jpg` | Logo der Applikation, angezeigt im Login-Fenster |

---

## Benutzerrollen & Login-Logik

```
Login-Fenster (login.java)
│
├── Benutzername: "Junior" / Passwort: "Junior"
│   └── → Junior.java (Platzhalter, keine Funktionen)
│
├── Benutzername: "Senior" / Passwort: "Senior"
│   └── → Senior2.java (volles Hauptfenster mit allen Funktionen)
│
└── Benutzername: "admin" / Passwort: "admin"
    └── → Admin.java (Platzhalter, keine Funktionen)
```

Sicherheitshinweis: Passwörter sind aktuell im Klartext im Quellcode hinterlegt. Die Klasse `Hashing.java` deutet auf eine geplante Verbesserung hin. Nach 3 fehlgeschlagenen Versuchen wird die Anwendung beendet.

---

## Datenfluss

```
CSV-Dateien (Hotel.csv, Hoteldata.csv)
        │
        ▼
Hotelutil.HotelData()          occupanciesutil.master_data_occupancies()
        │                                       │
        ▼                                       ▼
ArrayList<Hotel>                    List<occupancies>
        │                                       │
        ▼                                       ▼
HotelTableModel               HotelTableModel_transaction
        │                                       │
        ▼                                       ▼
JTable (Senior2/MainFrame)    JTable (MainFrame_transaction/AddingOccupancies)
        │
        ├── Neu anlegen → newHotelwindow → model.addHotel()
        ├── Löschen     → model.removeHotelById() + model_transaction.removeRows()
        └── Bearbeiten  → setValueAt() direkt in Tabellenzelle
```
