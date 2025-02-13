# Product Service - Dokumentation

## Einleitung
Der **Product Service** verwaltet die Produktdaten 
Er ermöglicht:
- Das Abrufen und Anzeigen von Produkten
- Das Erstellen und Aktualisieren von Produkten durch Administratoren
- Das Löschen von Produkten aus dem Bestand
- updaten und ändern des Inventars

Die API ist für eine einfache Integration in bestehende Systeme optimiert.

---

## **1. Sicherheitskonfiguration**

### **Funktionalität**
Die `SecurityConfig`-Klasse definiert die Sicherheitsrichtlinien für den Zugriff auf verschiedene API-Endpunkte. Sie erlaubt oder blockiert den Zugriff basierend auf den Anforderungen des Systems.

### **Wichtige Endpunkte**

| Endpunkt                     |Beschreibung                                 |
|------------------------------|---------------------------------------------|
| `/auth/**`                   |Erlaubt Zugriff auf Authentifizierungs-APIs. |
| `/api/**`                    |Erlaubt Zugriff auf allgemeine APIs.         |
| `/api/products/**`           |Zugriff auf Produkte-APIs.                   |
| `/api/products/admin/**`     |Zugriff auf administrative APIs.             |

---

## **2. Produktverwaltung (ProductController)**

### **Funktionalität**
Der `ProductController` bietet Endpunkte zur Verwaltung von Produkten und Inventar, einschließlich Abrufen, Hinzufügen, Aktualisieren und Löschen von Produkten.

### **User Story**
**Als Kunde**  
möchte ich eine Liste aller verfügbaren Produkte abrufen,  
damit ich eine informierte Kaufentscheidung treffen kann.

**Akzeptanzkriterien:**
- Die API soll eine Liste mit allen Produkten zurückgeben.
- Falls ein Produkt nicht existiert, soll eine `404`-Fehlermeldung erscheinen.

### **API-Schnittstellen**

#### **2.1 Alle Produkte abrufen**
**`GET /api/products`**

- **Beschreibung:** Ruft alle verfügbaren Produkte ab.
- **Response:**
  - **200 – Erfolg:**
    ```json
    [
      {
        "productId":"J001",
        "name":"Diamond Tennis Bracelet",
        "description":"Elegant 18k white gold bracelet with 2-carat diamonds.","category":"Bracelets",
        "material":"18k White Gold, Diamond",
        "unitPrice":4000.0,
        "currency":"EUR",
        "availableQuantity":29,
        "minimumOrderQuantity":1,
        "supplier":"GemLux Creations",
        "leadTimeDays":4,
        "weightGram":4      
      }
    ]
    ```
  - **500 – Serverfehler:**
    ```json
    {
      "error": "Fehler beim Abrufen der Produkte."
    }
    ```

#### **2.2 Produkt nach ID abrufen**
**`GET /api/products/{id}`**

- **Beschreibung:** Ruft ein einzelnes Produkt anhand seiner ID ab.
- **Response:**
  - **200 – Erfolg:**
    ```json
    {
        "productId":"J001",
        "name":"Diamond Tennis Bracelet",
        "description":"Elegant 18k white gold bracelet with 2-carat diamonds.","category":"Bracelets",
        "material":"18k White Gold, Diamond",
        "unitPrice":4000.0,
        "currency":"EUR",
        "availableQuantity":29,
        "minimumOrderQuantity":1,
        "supplier":"GemLux Creations",
        "leadTimeDays":4,
        "weightGram":4  
    }
    ```
  - **404 – Nicht gefunden:**
    ```json
    {
      "error": "Produkt nicht gefunden."
    }
    ```
  - **500 – Serverfehler:**
    ```json
    {
      "error": "Fehler beim Abrufen des Produkts."
    }
    ```

#### **2.3 Neues Produkt hinzufügen**
**`POST /api/products/admin/add`**

- **Beschreibung:** Fügt ein neues Produkt hinzu.
- **Request:**
```json
{
        "productId":"J002",
        "name":"Diamond Tennis Ring",
        "description":"Elegant 18k white gold ring with 2-carat diamonds.","category":"Rings",
        "material":"18k White Gold, Diamond",
        "unitPrice":4000.0,
        "currency":"EUR",
        "availableQuantity":29,
        "minimumOrderQuantity":1,
        "supplier":"GemLux Creations",
        "leadTimeDays":4,
        "weightGram":4  
}
```
- **Response:**
  - **200 – Erfolg:**
    ```json
    {
      "message": "Produkt erfolgreich hinzugefügt."
    }
    ```
  - **500 – Serverfehler:**
    ```json
    {
      "error": "Fehler beim Hinzufügen des Produkts."
    }
    ```

#### **2.4 Produkt aktualisieren**
**`PUT /api/products/admin/{id}`**

- **Beschreibung:** Aktualisiert ein bestehendes Produkt.
- **Request:**
```json
{
    "productId":"J002",
  "name":"Diamond Tennis Ring",
  "unitPrice":8000.0
}
```
- **Response:**
  - **200 – Erfolg:**
    ```json
    {
      "message": "Produkt erfolgreich aktualisiert."
    }
    ```
  - **404 – Nicht gefunden:**
    ```json
    {
      "error": "Produkt nicht gefunden."
    }
    ```
  - **500 – Serverfehler:**
    ```json
    {
      "error": "Fehler beim Aktualisieren des Produkts."
    }
    ```

#### **2.5 Produkt löschen**
**`DELETE /api/products/admin/{id}`**

- **Beschreibung:** Löscht ein Produkt anhand seiner ID.
- **Response:**
  - **200 – Erfolg:**
    ```json
    {
      "message": "Produkt erfolgreich gelöscht."
    }
    ```
  - **404 – Nicht gefunden:**
    ```json
    {
      "error": "Produkt nicht gefunden."
    }
    ```
  - **500 – Serverfehler:**
    ```json
    {
      "error": "Fehler beim Löschen des Produkts."
    }
    ```
