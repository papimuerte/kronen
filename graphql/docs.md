# GraphQL Task & Order Management Service - Dokumentation

## Übersicht
Das **Task- und Bestellmanagement-Backend** basiert auf **GraphQL** und bietet eine Schnittstelle zur Verwaltung von Bestellungen.

## Web-API-Technologie
Die API nutzt **GraphQL** und stellt den folgenden Endpoint zur Verfügung:

```
/graphql
```

## Hauptfunktionen
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

### **OrderDataUtil**
Hilft beim Laden und Speichern von Bestellungen über eine externe Datenquelle.

### **GraphQLResolver**
GraphQL-Controller zur Verwaltung von Bestellungen, inklusive Mutationen und Queries.

## GraphQL-Queries

| Query-Name         | Beschreibung | Parameter | Rückgabewert |
|--------------------|-------------|-----------|--------------|
| `ordersByCustomer` | Gibt Bestellungen eines Kunden zurück | `customerUsername: String!` | `[Order]` |
| `allOrders`       | Gibt alle Bestellungen zurück | - | `[Order]` |
| `order`           | Gibt eine Bestellung anhand ihrer ID zurück | `id: String!` | `Order` |

## GraphQL-Mutationen

| Mutation-Name  | Beschreibung | Parameter | Rückgabewert |
|---------------|-------------|-----------|--------------|
| `createOrder` | Erstellt eine neue Bestellung | `input: OrderInput!` | `Order` |

### Beispiel für eine Bestellungserstellung:
```json
{
  "query": "mutation CreateOrder { createOrder(input: {customerUsername: \"lala2\", companyName: \"lala\", email: \"lala@gmail.com\", address: \"Lala Street\", phoneNumber: \"123456789\", notes: \"lala\", products: [{ productId: \"J003\", quantity: 2 }]}) { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }"
}
```

## User Stories

### **1. Bestellungen verwalten**
- Als **Benutzer** möchte ich **Bestellungen aufgeben und einsehen**, um **meine Einkaufshistorie zu verwalten**.

### **2. Bestellungen nach Kunden anzeigen**
- Als **Benutzer** möchte ich **alle meine Bestellungen abrufen**, um **meine Transaktionen nachzuverfolgen**.

