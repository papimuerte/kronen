# Benutzerverwaltung API-Dokumentation

## Überblick

Dieses Dokument beschreibt die REST API für die Verwaltung von Benutzerdaten. Die API bietet Endpunkte zur Erstellung, Aktualisierung, Löschung und Abfrage von Benutzern.

## Endpunkte

### Benutzer abrufen

**GET /users**

Abrufen aller registrierten Benutzer.

**Request:**

```http
GET http://localhost:8080/users HTTP/1.1
```

**Response:**

```json
[
  {
    "username": "john_doe",
    "email": "john.doe@example.com",
    "role": "USER"
  },
  {
    "username": "jane_doe",
    "email": "jane.doe@example.com",
    "role": "ADMIN"
  }
]
```

### Benutzer anhand des Benutzernamens abrufen

**GET /users/{id}**

Abrufen der Details eines bestimmten Benutzers anhand seines Benutzernamens.

**Request:**

```http
GET http://localhost:8080/users/john_doe HTTP/1.1
```

**Response:**

```json
{
  "username": "john_doe",
  "email": "john.doe@example.com",
  "role": "USER"
}
```

### Benutzer aktualisieren

**PUT /users/{id}**

Aktualisieren der Details eines Benutzers. Nur nicht-null Felder werden aktualisiert.

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

### Benutzer löschen

**DELETE /users/{id}**

Löschen eines Benutzers anhand seines Benutzernamens.

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
  "username": "john_doe",
  "password": "securepassword",
  "role": "USER",
  "email": "john.doe@example.com",
  "phoneNumber": "123456789",
  "address": "123 Main Street",
  "companyName": "ExampleCorp"
}
```

## Fehlerbehandlung

Bei fehlerhaften Anfragen werden entsprechende Fehlercodes zurückgegeben:

- **400 Bad Request** – Ungültige Eingaben.
- **404 Not Found** – Benutzer nicht gefunden.
- **500 Internal Server Error** – Serverfehler.

## Technologie-Stack

- **Spring Boot** für die Backend-Implementierung
- **Reactor (WebFlux)** für asynchrone Verarbeitung
- **REST API** für die Kommunikation
- **Jackson** für JSON-Serialisierung und -Deserialisierung

