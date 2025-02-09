# GraphQL Task & Order Management Service - Dokumentation

## Übersicht
Das **Task- und Bestellmanagement-Backend** basiert auf **GraphQL** und bietet eine Schnittstelle zur Verwaltung von Aufgaben und Bestellungen.

## Web-API-Technologie
Die API nutzt **GraphQL** und stellt den folgenden Endpoint zur Verfügung:

```
/graphql
```

## Hauptfunktionen
- **Aufgabenverwaltung**
  - Aufgaben erstellen
  - Aufgaben bearbeiten
  - Aufgaben löschen
  - Aufgaben anhand ihrer ID abrufen
  - Aufgaben mit Fälligkeitsdatum abrufen

- **Bestellmanagement**
  - Bestellungen erstellen
  - Bestellungen abrufen
  - Bestellungen nach Kunden filtern

## Klassenbeschreibung

### **Order**
Repräsentiert eine Bestellung mit folgenden Attributen:
- `id`: Eindeutige ID
- `customerUsername`: Benutzername des Kunden
- `products`: Liste der bestellten Produkte
- `totalAmount`: Gesamtkosten der Bestellung
- `status`: Status der Bestellung (z.B. "pending")
- `createdAt`: Erstellungsdatum
- `companyName`: Firmenname des Kunden
- `email`: E-Mail-Adresse des Kunden
- `address`: Lieferadresse
- `phoneNumber`: Telefonnummer des Kunden
- `notes`: Zusätzliche Notizen

### **OrderInput**
Eingabemodell für Bestellungen mit den gleichen Feldern wie `Order`, jedoch ohne `id` und `createdAt`.

### **OrderProduct**
Repräsentiert ein Produkt innerhalb einer Bestellung:
- `productId`: Produkt-ID
- `name`: Name des Produkts
- `quantity`: Bestellmenge
- `unitPrice`: Einzelpreis

### **Task**
Repräsentiert eine Aufgabe mit folgenden Attributen:
- `id`: Eindeutige ID
- `title`: Titel der Aufgabe
- `description`: Beschreibung der Aufgabe
- `assignee`: Verantwortliche Person
- `status`: Status der Aufgabe
- `dueDate`: Fälligkeitsdatum

### **TaskInput**
Eingabemodell für Aufgaben mit den gleichen Feldern wie `Task`, jedoch ohne `id`.

### **OrderDataUtil**
Hilft beim Laden und Speichern von Bestellungen über eine externe Datenquelle.

### **TaskDataUtil**
Hilft beim Laden, Speichern und Verwalten von Aufgaben.

### **GraphQLResolver**
GraphQL-Controller zur Verwaltung von Aufgaben und Bestellungen, inklusive Mutationen und Queries.

## GraphQL-Queries

| Query-Name         | Beschreibung | Parameter | Rückgabewert |
|--------------------|-------------|-----------|--------------|
| `ordersByCustomer` | Gibt Bestellungen eines Kunden zurück | `customerUsername: String!` | `[Order]` |
| `allOrders`       | Gibt alle Bestellungen zurück | - | `[Order]` |
| `order`           | Gibt eine Bestellung anhand ihrer ID zurück | `id: String!` | `Order` |
| `toDoList`        | Gibt eine Liste aller Aufgaben zurück | - | `[Task]` |
| `taskById`        | Gibt eine Aufgabe anhand ihrer ID zurück | `id: String!` | `Task` |
| `taskDueToday`    | Gibt heute fällige Aufgaben zurück | - | `[Task]` |

## GraphQL-Mutationen

| Mutation-Name  | Beschreibung | Parameter | Rückgabewert |
|---------------|-------------|-----------|--------------|
| `createOrder` | Erstellt eine neue Bestellung | `input: OrderInput!` | `Order` |
| `addTask`     | Erstellt eine neue Aufgabe | `input: TaskInput!` | `Task` |
| `updateTask`  | Aktualisiert eine vorhandene Aufgabe | `id: String!, input: TaskInput!` | `Task` |
| `deleteTask`  | Löscht eine Aufgabe | `id: String!` | `Boolean` |

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
