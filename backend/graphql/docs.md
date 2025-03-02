# GraphQL Order Management Service - Dokumentation

## Übersicht
Das **Bestellmanagement-Backend** basiert auf **GraphQL** und bietet eine Schnittstelle zur Verwaltung von Bestellungen.

## Web-API-Technologie
Die API nutzt **GraphQL** und stellt den folgenden Endpoint zur Verfügung:

```
/graphql
```

GraphQL unterstützt zwei Arten von Anfragen:
1. **Normale GraphQL-Anfragen** – Direkt an den `/graphql`-Endpoint gesendet.
2. **REST-ähnliche Anfragen** – Über HTTP-`GET` oder `POST` mit einer Query-Parameter-Struktur.

## Hauptfunktionen
- **Bestellmanagement**
  - Bestellungen erstellen
  - Bestellungen abrufen
  - Bestellungen nach Kunden filtern
  - Bestellstatus aktualisieren

## Klassenbeschreibung

### **Order**
Repräsentiert eine Bestellung mit folgenden Attributen:
- `id`: Eindeutige ID
- `customerUsername`: Benutzername des Kunden
- `products`: Liste der bestellten Produkte
- `totalAmount`: Gesamtkosten der Bestellung
- `status`: Status der Bestellung ("Pending", "Shipped", "Done")
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

### Beispiel für `allOrders` Query:
```json
{
  "query": "query AllOrders { allOrders { id customerUsername totalAmount status createdAt companyName email address phoneNumber notes products { productId name quantity unitPrice } } }"
}
```

### Beispiel für `ordersByCustomer` Query:
```json
{
  "query": "query AllOrders { ordersByCustomer(customerUsername: \"lala2\") { id customerUsername totalAmount status createdAt companyName email address phoneNumber notes products { productId name quantity unitPrice } } }"
}
```

## GraphQL-Mutationen

| Mutation-Name  | Beschreibung | Parameter | Rückgabewert |
|---------------|-------------|-----------|--------------|
| `createOrder` | Erstellt eine neue Bestellung | `input: OrderInput!` | `Order` |
| `updateOrderStatus` | Aktualisiert den Status einer Bestellung | `orderId: String!`, `newStatus: String!` | `Order` |

### Beispiel für eine Bestellungserstellung:
```json
{
  "query": "mutation CreateOrder { createOrder(input: {customerUsername: \"lala2\", companyName: \"lala\", email: \"lala@gmail.com\", address: \"Lala Street\", phoneNumber: \"123456789\", notes: \"lala\", products: [{ productId: \"J003\", quantity: 2 }]}) { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }"
}
```

### Beispiel für eine Statusaktualisierung:
```json
{
  "query": "mutation { updateOrderStatus(orderId: \"a02e87ff-258a-482e-8593-47ddb2017c85\", newStatus: \"Shipped\") { id status } }"
}
```

## User Stories

### **1. Bestellungen verwalten**
- Als **Benutzer** möchte ich **Bestellungen aufgeben und einsehen**, um **meine Einkaufshistorie zu verwalten**.

### **2. Bestellungen nach Kunden anzeigen**
- Als **Benutzer** möchte ich **alle meine Bestellungen abrufen**, um **meine Transaktionen nachzuverfolgen**.

### **3. Bestellstatus aktualisieren**
- Als **Administrator** möchte ich **den Status von Bestellungen ändern**, um **den Bestellfortschritt korrekt zu verwalten**.
