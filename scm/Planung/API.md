# API-Dokumentation für das B2B Supply Chain Management System

## Einleitung
Diese API-Dokumentation bietet Details zu den Endpunkten, Anfragemethoden und Antwortstrukturen des B2B Supply Chain Management Systems. Sie ist in die verschiedenen Services des Systems unterteilt.

---

## Authentifizierungsdienst
### Basis-URL: `/auth`

### **1. Benutzerregistrierung**
- **Endpunkt:** `POST /register`
- **Beschreibung:** Registriert einen neuen Benutzer.
- **Request-Body:**
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string",
    "role": "string"
  }
  ```
- **Antwort:**
  ```json
  {
    "message": "Benutzer erfolgreich registriert",
    "userId": "string"
  }
  ```

### **2. Benutzer-Login**
- **Endpunkt:** `POST /login`
- **Beschreibung:** Authentifiziert einen Benutzer und generiert ein JWT-Token.
- **Request-Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Antwort:**
  ```json
  {
    "token": "string",
    "expiresIn": "number"
  }
  ```

### **3. Token-Validierung**
- **Endpunkt:** `GET /validate`
- **Beschreibung:** Validiert das JWT-Token.
- **Header:**
  ```json
  {
    "Authorization": "Bearer {token}"
  }
  ```
- **Antwort:**
  ```json
  {
    "valid": true
  }
  ```

---

## Bestellmanagement-Dienst
### Basis-URL: `/orders`

### **1. Bestellung erstellen**
- **Endpunkt:** `POST /`
- **Beschreibung:** Erstellt eine neue Bestellung.
- **Request-Body:**
  ```json
  {
    "customerId": "string",
    "items": [
      {
        "productId": "string",
        "quantity": "number"
      }
    ]
  }
  ```
- **Antwort:**
  ```json
  {
    "orderId": "string",
    "status": "Pending"
  }
  ```

### **2. Bestellung abrufen**
- **Endpunkt:** `GET /{orderId}`
- **Beschreibung:** Ruft Details zu einer bestimmten Bestellung ab.
- **Antwort:**
  ```json
  {
    "orderId": "string",
    "customerId": "string",
    "items": [
      {
        "productId": "string",
        "quantity": "number"
      }
    ],
    "status": "string",
    "createdAt": "string"
  }
  ```

### **3. Bestellstatus aktualisieren**
- **Endpunkt:** `PATCH /{orderId}`
- **Beschreibung:** Aktualisiert den Status einer Bestellung.
- **Request-Body:**
  ```json
  {
    "status": "string"
  }
  ```
- **Antwort:**
  ```json
  {
    "message": "Bestellstatus erfolgreich aktualisiert"
  }
  ```

---

## Lagerverwaltungsdienst
### Basis-URL: `/inventory`

### **1. Lagerbestand abrufen**
- **Endpunkt:** `GET /`
- **Beschreibung:** Ruft alle Lagerartikel ab.
- **Antwort:**
  ```json
  [
    {
      "productId": "string",
      "productName": "string",
      "quantity": "number"
    }
  ]
  ```

### **2. Lagerbestand aktualisieren**
- **Endpunkt:** `PATCH /{productId}`
- **Beschreibung:** Aktualisiert die Menge eines Produkts im Lager.
- **Request-Body:**
  ```json
  {
    "quantity": "number"
  }
  ```
- **Antwort:**
  ```json
  {
    "message": "Lagerbestand erfolgreich aktualisiert"
  }
  ```

### **3. Neues Produkt hinzufügen**
- **Endpunkt:** `POST /`
- **Beschreibung:** Fügt ein neues Produkt zum Lager hinzu.
- **Request-Body:**
  ```json
  {
    "productName": "string",
    "quantity": "number"
  }
  ```
- **Antwort:**
  ```json
  {
    "productId": "string",
    "message": "Produkt erfolgreich hinzugefügt"
  }
  ```

---

## Tracking-Dienst
### Basis-URL: `/tracking`

### **1. Bestellung verfolgen**
- **Endpunkt:** `GET /{orderId}`
- **Beschreibung:** Ruft den Tracking-Status einer Bestellung ab.
- **Antwort:**
  ```json
  {
    "orderId": "string",
    "status": "In Transit",
    "lastUpdated": "string"
  }
  ```

### **2. Tracking-Status aktualisieren**
- **Endpunkt:** `PATCH /{orderId}`
- **Beschreibung:** Aktualisiert den Tracking-Status einer Bestellung.
- **Request-Body:**
  ```json
  {
    "status": "string"
  }
  ```
- **Antwort:**
  ```json
  {
    "message": "Tracking-Status erfolgreich aktualisiert"
  }
  ```

---

## Benachrichtigungsdienst
### Basis-URL: `/notifications`

### **1. Benachrichtigung senden**
- **Endpunkt:** `POST /send`
- **Beschreibung:** Sendet eine Benachrichtigung an einen Benutzer.
- **Request-Body:**
  ```json
  {
    "userId": "string",
    "message": "string"
  }
  ```
- **Antwort:**
  ```json
  {
    "message": "Benachrichtigung erfolgreich gesendet"
  }
  ```

### **2. Benachrichtigungen abrufen**
- **Endpunkt:** `GET /user/{userId}`
- **Beschreibung:** Ruft alle Benachrichtigungen für einen Benutzer ab.
- **Antwort:**
  ```json
  [
    {
      "notificationId": "string",
      "message": "string",
      "sentAt": "string"
    }
  ]
  ```

### **3. Externe API Nutzung für Benachrichtigungen**
- **Externe API:** Twilio (oder vergleichbar)
- **Wichtigste Funktion:** SMS oder E-Mail-Benachrichtigungen senden.
- **Anwendungsfall:**
  - **Endpoint:** `/send-sms`
  - **Beschreibung:** Sendet eine SMS an einen Benutzer.
  - **Request-Body:**
    ```json
    {
      "phoneNumber": "string",
      "message": "string"
    }
    ```
  - **Antwort:**
    ```json
    {
      "status": "success",
      "messageId": "string"
    }
    ```

  - **Endpoint:** `/send-email`
  - **Beschreibung:** Sendet eine E-Mail an einen Benutzer.
  - **Request-Body:**
    ```json
    {
      "email": "string",
      "subject": "string",
      "message": "string"
    }
    ```
  - **Antwort:**
    ```json
    {
      "status": "success",
      "messageId": "string"
    }
    ```

---

## Zuordnung von Technologien zu Funktionen

1. **REST API**:
   - Authentifizierungsdienst (Benutzerregistrierung, Login).
   - Bestellmanagement (Erstellen, Abrufen, Aktualisieren von Bestellungen).
   - Lagerverwaltungsdienst (Abrufen und Aktualisieren von Lagerbeständen).

2. **GraphQL**:
   - Berichtswesen und Analysen (flexible Datenabfragen, Datenvisualisierung).

3. **gRPC**:
   - Kommunikation zwischen Microservices, insbesondere für Tracking- und Benachrichtigungsdienste.

4. **Externe API** (z. B. Twilio oder SendGrid):
   - Wird ausschließlich für den Benachrichtigungsdienst genutzt, um Nachrichten zu versenden.

---

## Allgemeine HTTP-Statuscodes
- **200 OK:** Anfrage war erfolgreich.
- **201 Created:** Ressource wurde erfolgreich erstellt.
- **400 Bad Request:** Anfrage ist ungültig oder fehlerhaft.
- **401 Unauthorized:** Authentifizierung fehlgeschlagen.
- **404 Not Found:** Ressource wurde nicht gefunden.
- **500 Internal Server Error:** Serverfehler.

---

## Beispiel für Authentifizierungs-Header
Alle Endpunkte (außer `/auth/register` und `/auth/login`) erfordern den folgenden Header:
```json
{
  "Authorization": "Bearer {token}"
}
```
