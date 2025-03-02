````markdown
# Data Service API Dokumentation

Diese Dokumentation beschreibt die REST-API für die Verwaltung von Bestell-, Produkt- und Benutzerdaten, die in JSON-Dateien gespeichert werden.

## Überblick

Die API ermöglicht das Abrufen, Hinzufügen, Aktualisieren und Löschen von Daten für Bestellungen, Produkte und Benutzer.

---

## 1. Bestellungen (Orders)
### Endpunkt: `/orders-data`

#### **GET /orders-data**
- **Beschreibung**: Ruft alle gespeicherten Bestellungen ab.
- **Antwort (JSON)**:
  ```json
  [
    {
      "id": "123",
      "customerUsername": "john_doe",
      "products": [
        {
          "productId": "P001",
          "name": "Laptop",
          "quantity": 2,
          "unitPrice": 1200.50
        }
      ],
      "totalAmount": 2401.00,
      "status": "Pending",
      "createdAt": "2024-02-09T10:00:00Z",
      "companyName": "TechCorp",
      "email": "john@techcorp.com",
      "address": "123 Tech Street",
      "phoneNumber": "+49 123 456 7890",
      "notes": "Express Delivery"
    }
  ]
````

#### **POST /orders-data**

- **Beschreibung**: Fügt eine neue Bestellung hinzu.

- **Anfrage (JSON)**:

  ```json
  {
    "id": "124",
    "customerUsername": "jane_doe",
    "products": [
      {
        "productId": "P002",
        "name": "Smartphone",
        "quantity": 1,
        "unitPrice": 800.99
      }
    ],
    "totalAmount": 800.99,
    "status": "Confirmed",
    "createdAt": "2024-02-10T15:30:00Z",
    "companyName": "MobileTech",
    "email": "jane@mobiletech.com",
    "address": "456 Mobile Ave",
    "phoneNumber": "+49 987 654 3210",
    "notes": "Gift Packaging"
  }
  ```

- **Antwort**: Gibt die aktualisierte Liste der Bestellungen zurück.

---

## 2. Produkte (Products)

### Endpunkt: `/products-data`

#### **GET /products-data**

- **Beschreibung**: Ruft alle gespeicherten Produkte ab.
- **Antwort (JSON)**:
  ```json
  [
    {
      "productId": "P001",
      "name": "Laptop",
      "description": "High-performance laptop",
      "category": "Electronics",
      "material": "Aluminum",
      "unitPrice": 1200.50,
      "currency": "EUR",
      "availableQuantity": 50,
      "minimumOrderQuantity": 1,
      "supplier": "TechSupplier",
      "leadTimeDays": 7,
      "weightGram": 2000
    }
  ]
  ```

#### **POST /products-data**

- **Beschreibung**: Fügt neue Produkte hinzu.

- **Anfrage (JSON)**:

  ```json
  [
    {
      "productId": "P002",
      "name": "Smartphone",
      "description": "Latest model smartphone",
      "category": "Electronics",
      "material": "Glass & Metal",
      "unitPrice": 800.99,
      "currency": "EUR",
      "availableQuantity": 100,
      "minimumOrderQuantity": 1,
      "supplier": "MobileSupplier",
      "leadTimeDays": 5,
      "weightGram": 500
    }
  ]
  ```

- **Antwort**: Gibt die aktualisierte Produktliste zurück.

#### **DELETE /products-data/{id}**

- **Beschreibung**: Löscht ein Produkt anhand der ID.
- **Antwort**: Gibt die aktualisierte Produktliste zurück.

---

## 3. Benutzer (Users)

### Endpunkt: `/users-data`

#### **GET /users-data**

- **Beschreibung**: Ruft alle gespeicherten Benutzer ab.
- **Antwort (JSON)**:
  ```json
  [
    {
      "username": "john_doe",
      "role": "USER",
      "email": "john@techcorp.com",
      "phoneNumber": "+49 123 456 7890",
      "address": "123 Tech Street",
      "companyName": "TechCorp"
    }
  ]
  ```

#### **POST /users-data**

- **Beschreibung**: Fügt einen neuen Benutzer hinzu.
- **Anfrage (JSON)**:
  ```json
  {
    "username": "jane_doe",
    "password": "securepassword",
    "role": "USER",
    "email": "jane@mobiletech.com",
    "phoneNumber": "+49 987 654 3210",
    "address": "456 Mobile Ave",
    "companyName": "MobileTech"
  }
  ```
- **Antwort**: Gibt den neu hinzugefügten Benutzer zurück.

---

## 4. JSON Datei-Handling

Die API speichert die Daten in JSON-Dateien und verwaltet sie über eine Hilfsklasse `JsonFileUtil`:

- **Lesen von JSON-Dateien**:
  - Wird für das Abrufen von Daten aus JSON-Dateien genutzt.
- **Schreiben in JSON-Dateien**:
  - Wird für das Speichern neuer oder aktualisierter Daten genutzt.

---

## 5. Anwendung starten

Die Anwendung wird durch folgende Klasse gestartet:

```java
public class DataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}
```

---

## 6. Fehlerbehandlung

- Falls eine Datei nicht gefunden wird oder beschädigt ist, wird ein Fehler ausgelöst.
- Falls ein Produkt oder Benutzer nicht gefunden wird, gibt die API eine entsprechende Fehlermeldung zurück.

```json
{
  "error": "Product with ID P999 not found."
}
```

```json
{
  "error": "User with username 'unknown_user' not found."
}
```

---

## Fazit

Diese API bietet eine einfache Verwaltung von Bestellungen, Produkten und Benutzerdaten über REST-Methoden mit JSON-Dateispeicherung.

```markdown
### Hauptfunktionen:
 Abrufen, Hinzufügen, Aktualisieren und Löschen von Bestellungen, Produkten und Benutzern.
 Speicherung der Daten in JSON-Dateien.
 Nutzung von Spring Boot für REST-APIs.
 Fehlerbehandlung bei nicht gefundenen Dateien oder Daten.
```

```
```
