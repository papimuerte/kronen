# Benutzerverwaltungsservice API-Dokumentation

## Übersicht

Das **Benutzerverwaltungs-Backend** dient zur Verwaltung von Benutzerkonten. Es stellt verschiedene Endpunkte zur Verfügung, um Benutzer zu erstellen, zu aktualisieren, zu löschen und abzurufen.

## Web-API-Technologie

Es wird REST genutzt, um eine flexible Schnittstelle für die Interaktion mit den Benutzern bereitzustellen. Die API ist unter folgendem Endpoint erreichbar:

<mark>http://localhost:8080/users</mark>

## Hauptfunktionen

- Erstellung neuer Benutzer
- Aktualisierung bestehender Benutzer
- Löschen von Benutzern
- Abrufen eines Benutzers anhand seiner ID
- Abrufen aller Benutzer

## Endpunkte

### GET /users
Abrufen aller registrierten Benutzer.

**Request:**
```http
GET http://localhost:8080/users HTTP/1.1
```

**Response:**
```json
[
  {
    "id": "john_doe",
    "email": "john.doe@example.com",
    "role": "USER"
  },
  {
    "id": "jane_doe",
    "email": "jane.doe@example.com",
    "role": "ADMIN"
  }
]
```

### GET /users/{id}
Abrufen eines Benutzers anhand seiner ID.

**Request:**
```http
GET http://localhost:8080/users/john_doe HTTP/1.1
```

**Response:**
```json
{
  "id": "john_doe",
  "email": "john.doe@example.com",
  "role": "USER"
}
```

### PUT /users/{id}
Aktualisieren der Details eines Benutzers.

**Request:**
```http
PUT http://localhost:8080/users/john_doe HTTP/1.1
Content-Type: application/json

{
  "email": "new.email@example.com",
  "phoneNumber": "123456789"
}
```

**Response:**
```json
{
  "message": "User successfully updated."
}
```

### DELETE /users/{id}
Löschen eines Benutzers anhand seiner ID.

**Request:**
```http
DELETE http://localhost:8080/users/john_doe HTTP/1.1
```

**Response:**
```json
{
  "message": "User successfully deleted."
}
```

## Datenmodell

### Benutzer

```json
{
  "id": "john_doe",
  "password": "securepassword",
  "role": "USER",
  "email": "john.doe@example.com",
  "phoneNumber": "123456789",
  "address": "123 Main Street",
  "companyName": "ExampleCorp"
}
```

## Fehlerbehandlung

Fehlerhafte Anfragen werden mit entsprechenden HTTP-Statuscodes beantwortet:

- **400 Bad Request** – Ungültige Eingaben.
- **404 Not Found** – Benutzer nicht gefunden.
- **500 Internal Server Error** – Serverfehler.

## Technologie-Stack

- **Spring Boot** für die Backend-Implementierung
- **Reactor (WebFlux)** für asynchrone Verarbeitung
- **REST API** für die Kommunikation
- **Jackson** für JSON-Serialisierung und -Deserialisierung

## User Stories

1. ***Benutzerverwaltung:***  
   - Als **Administrator** möchte ich neue Benutzer **hinzufügen, aktualisieren oder löschen**, um **die Benutzerkonten effektiv verwalten zu können**.
2. ***Benutzerübersicht:***  
   - Als **Administrator** möchte ich **alle registrierten Benutzer abrufen**, um **eine Übersicht über alle Benutzer zu erhalten**.
3. ***Benutzersuche:***  
   - Als **Administrator** möchte ich **einen Benutzer anhand seiner ID suchen**, um **schnell gezielt Informationen zu einem bestimmten Benutzer zu erhalten**.
4. ***Benutzeraktualisierung:***  
   - Als **Administrator** möchte ich **die Informationen eines Benutzers aktualisieren**, um **seine Daten auf dem neuesten Stand zu halten**.
5. ***Benutzerlöschung:***  
   - Als **Administrator** möchte ich **einen Benutzer löschen**, wenn **sein Konto nicht mehr benötigt wird**.