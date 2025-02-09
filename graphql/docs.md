# TaskWebService - Dokumentation

## Übersicht
Das **Task-Management-Backend** dient der Verwaltung von Aufgaben. Es bietet verschiedene Funktionen, um einen genauen Überblick über geplante Aufgaben zu behalten.

## Web-API-Technologie
Die API basiert auf **GraphQL** und bietet eine flexible Schnittstelle für die Interaktion mit den Aufgaben. Der einzige Endpoint ist:

```
/graphql
```

## Hauptfunktionen
- Aufgaben erstellen
- Aufgaben bearbeiten
- Aufgaben löschen
- Aufgaben anhand ihrer ID abrufen
- Aufgaben aktualisieren
- Bestellungen erstellen und verwalten

## Klassenbeschreibung

### **Order**
Repräsentiert eine Bestellung mit relevanten Attributen wie ID, Kundenname, Liste der bestellten Produkte, Gesamtbetrag, Status und weiteren Informationen wie Lieferadresse und Kontaktangaben.

### **OrderInput**
Dient als Eingabemodell für Bestellungen, enthält Felder wie Kundenname, Firmenname, E-Mail, Lieferadresse, Telefonnummer und die Liste der bestellten Produkte.

### **OrderProduct**
Repräsentiert ein Produkt innerhalb einer Bestellung, mit Attributen wie Produkt-ID, Name, Menge und Einzelpreis.

### **Product**
Speichert Informationen zu einem Produkt im Inventar, wie Produkt-ID, Name, Beschreibung, Kategorie, Material, Einzelpreis, Währung, verfügbare Menge, Mindestbestellmenge, Lieferant und Gewicht.

### **OrderDataUtil**
Hilft beim Laden und Speichern von Bestellungen. Verwendet eine externe Datenquelle zum Abrufen und Speichern von Bestellungen.

### **ProductServiceUtil**
Prüft die Verfügbarkeit von Produkten und verwaltet Lagerbestände. 

### **OrderResolver**
GraphQL-Controller für die Verwaltung von Bestellungen, inklusive Mutationen und Queries.

### **GraphQLApplication**
Startpunkt der Spring Boot-Anwendung.

## GraphQL-Queries

| Query-Name      | Beschreibung                              | Parameter | Rückgabewert |
|-----------------|------------------------------------------|-----------|---------------|
| `ToDoList`     | Gibt eine Liste aller Aufgaben zurück     | -         | `[Task]`      |
| `TaskById`     | Gibt eine Aufgabe anhand ihrer ID zurück | `id: ID!` | `Task`        |
| `TaskDueToday` | Gibt heute fällige Aufgaben zurück       | -         | `[Task]`      |
| `ordersByCustomer` | Gibt Bestellungen eines Kunden zurück | `customerUsername: String!` | `[Order]` |
| `allOrders`    | Gibt alle Bestellungen zurück | - | `[Order]` |
| `order`        | Gibt eine Bestellung anhand ihrer ID zurück | `id: String!` | `Order` |

## GraphQL-Mutationen

| Mutation-Name   | Beschreibung                            | Parameter | Rückgabewert |
|----------------|----------------------------------------|-----------|---------------|
| `addTask`     | Erstellt eine neue Aufgabe              | `title, description, assignee, status, dueDate` | `Task` |
| `updateTask`  | Aktualisiert eine vorhandene Aufgabe   | `id, title, description, assignee, status, dueDate` | `Task` |
| `deleteTask`  | Löscht eine Aufgabe                     | `id: ID!` | `Task` |
| `createOrder` | Erstellt eine neue Bestellung          | `input: OrderInput!` | `Order` |

## User Stories

### **1. Aufgabenverwaltung**
- Als **Benutzer** möchte ich eine neue Aufgabe **hinzufügen, aktualisieren oder löschen**, um **meine Aufgaben effizient zu verwalten**.

### **2. Anzeige aller Aufgaben**
- Als **Benutzer** möchte ich **alle Aufgaben aufrufen**, um **eine Übersicht zu erhalten**.

### **3. Suche nach einer Aufgabe**
- Als **Benutzer** möchte ich eine **Aufgabe anhand ihrer ID suchen**, um **spezifische Aufgaben schneller zu finden**.

### **4. Heute fällige Aufgaben anzeigen**
- Als **Benutzer** möchte ich **alle Aufgaben sehen, die heute fällig sind**, um **dringende Aufgaben zu priorisieren**.

### **5. Bestellungen verwalten**
- Als **Benutzer** möchte ich **Bestellungen aufgeben und einsehen**, um **meine Einkaufshistorie zu verwalten**.

### **6. Bestellungen nach Kunden anzeigen**
- Als **Benutzer** möchte ich **alle meine Bestellungen abrufen**, um **meine Transaktionen nachzuverfolgen**.

