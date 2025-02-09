# Webservices-Dokumentation

## **1. Sicherheitskonfiguration**

### **Funktionalität**
Die `SecurityConfig`-Klasse definiert die Sicherheitsrichtlinien für den Zugriff auf verschiedene API-Endpunkte. Sie erlaubt oder blockiert den Zugriff basierend auf den Anforderungen des Systems.

### **Wichtige Endpunkte**

| Endpunkt                     | Zugriff        | Beschreibung                                   |
|------------------------------|----------------|-----------------------------------------------|
| `/auth/**`                   | Öffentlich     | Erlaubt Zugriff auf Authentifizierungs-APIs. |
| `/api/**`                    | Öffentlich     | Erlaubt Zugriff auf allgemeine APIs.         |
| `/api/products/**`           | Öffentlich     | Zugriff auf Produkte-APIs.                   |
| `/api/products/admin/**`     | Öffentlich     | Zugriff auf administrative APIs.             |
| `/**`                        | Öffentlich     | Standardzugriff auf alle Endpunkte.          |
| Andere Endpunkte             | Authentifiziert| Erfordert Authentifizierung.                 |

---

## **2. Produktverwaltung (ProductController)**

### **Funktionalität**
Der `ProductController` bietet Endpunkte zur Verwaltung von Produkten, einschließlich Abrufen, Hinzufügen, Aktualisieren und Löschen von Produkten.

### **API-Schnittstellen**

#### **2.1 Alle Produkte abrufen**
**`GET /api/products`**

- **Beschreibung:** Ruft alle verfügbaren Produkte ab.
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    [
      {
        "productId": "123",
        "name": "Laptop",
        "category": "Electronics",
        "unitPrice": 999.99
      }
    ]
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error fetching products."
    }
    ```

#### **2.2 Produkt nach ID abrufen**
**`GET /api/products/{id}`**

- **Beschreibung:** Ruft ein einzelnes Produkt anhand seiner ID ab.
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "productId": "123",
      "name": "Laptop",
      "category": "Electronics",
      "unitPrice": 999.99
    }
    ```
  - **Fehler (404 Not Found):**
    ```json
    {
      "error": "Product not found."
    }
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error fetching product."
    }
    ```

#### **2.3 Neues Produkt hinzufügen**
**`POST /api/products/admin/add`**

- **Beschreibung:** Fügt ein neues Produkt hinzu.
- **Request:**
```json
{
  "productId": "123",
  "name": "Laptop",
  "category": "Electronics",
  "unitPrice": 999.99,
  "availableQuantity": 10
}
```
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "message": "Product added successfully."
    }
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error adding product."
    }
    ```

#### **2.4 Produkt aktualisieren**
**`PUT /api/products/admin/{id}`**

- **Beschreibung:** Aktualisiert ein bestehendes Produkt.
- **Request:**
```json
{
  "name": "Gaming Laptop",
  "unitPrice": 1299.99
}
```
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "message": "Product updated successfully."
    }
    ```
  - **Fehler (404 Not Found):**
    ```json
    {
      "error": "Product not found."
    }
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error updating product."
    }
    ```

#### **2.5 Produkt löschen**
**`DELETE /api/products/admin/{id}`**

- **Beschreibung:** Löscht ein Produkt anhand seiner ID.
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "message": "Product deleted successfully."
    }
    ```
  - **Fehler (404 Not Found):**
    ```json
    {
      "error": "Product not found."
    }
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error deleting product."
    }
    ```

