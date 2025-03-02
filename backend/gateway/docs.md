# SCM Gateway API-Dokumentation

## Übersicht

Das *SCM Gateway* dient als API-Gateway zur Verwaltung von Authentifizierung, Benutzern und Produkten. Es unterstützt mehrere Protokolle, darunter *REST, GraphQL und gRPC*.

## Web-API-Technologie

Das Gateway bietet eine Schnittstelle für die Interaktion mit den Microservices. Die API ist unter folgendem Endpoint erreichbar:

<mark>http://localhost:8080</mark>

## Hauptfunktionen

- Authentifizierung und Token-Generierung
- Verwaltung von Benutzern
- Verwaltung von Produkten
- Unterstützung von REST, GraphQL und gRPC

## Endpunkte

### Authentifizierung

| Methode | Endpoint           | Beschreibung |
|---------|-------------------|--------------|
| POST  | /auth/register  | Registriert einen neuen Benutzer |
| POST  | /auth/login     | Authentifiziert einen Benutzer und gibt ein JWT zurück |

*Request:*
http
POST http://localhost:8080/auth/login HTTP/1.1
Content-Type: application/json

{
  "username": "testuser",
  "password": "test123"
}


*Response:*
json
{
  "token": "eyJhbGciOiJIUzI1..."
}


### Benutzerverwaltung

| Methode | Endpoint       | Beschreibung |
|---------|--------------|--------------|
| GET   | /users     | Gibt eine Liste aller Benutzer zurück |
| GET   | /users/{id} | Gibt die Details eines bestimmten Benutzers zurück |
| PUT   | /users/{id} | Aktualisiert die Details eines Benutzers |
| DELETE| /users/{id} | Löscht einen Benutzer |

*Request:*
http
GET http://localhost:8080/users/john_doe HTTP/1.1


*Response:*
json
{
  "id": "john_doe",
  "email": "john.doe@example.com",
  "role": "USER"
}


### Produktverwaltung

| Methode | Endpoint                 | Beschreibung |
|---------|--------------------------|--------------|
| GET   | /api/products          | Gibt alle gespeicherten Produkte zurück |
| GET   | /api/products/{id}      | Gibt die Details eines bestimmten Produkts zurück |
| POST  | /api/products/admin/add | Erstellt ein neues Produkt |
| PUT   | /api/products/admin/{id} | Aktualisiert ein Produkt |
| DELETE| /api/products/admin/{id} | Löscht ein Produkt |

### GraphQL API

| Methode | Endpoint   | Beschreibung |
|---------|-----------|--------------|
| POST  | /graphql | Führt GraphQL-Abfragen aus |

### gRPC Services

| Methode | Endpoint                                      | Beschreibung |
|---------|---------------------------------------------|--------------|
| POST  | localhost:9090/InventoryService/CheckAvailability | Prüft die Verfügbarkeit eines Produkts |
| POST  | localhost:9090/InventoryService/UpdateInventory  | Aktualisiert den Lagerbestand eines Produkts |

## Sicherheit

Die API-Gateway-Sicherheit wird durch *Spring Security WebFlux* konfiguriert:

- *CSRF deaktiviert* für API-Anfragen
- *CORS deaktiviert*
- Öffentliche Endpunkte:
  - /graphql
  - /auth/**
  - /api/products/**
  - /users
  - /v3/**
  - /webjars/**
- Alle anderen Endpunkte erfordern Authentifizierung

## Fehlerbehandlung

| Statuscode | Bedeutung |
|------------|--------------------------------|
| 200 OK   | Erfolgreiche Anfrage |
| 400 Bad Request | Ungültige Eingabe |
| 401 Unauthorized | Keine gültige Authentifizierung |
| 500 Internal Server Error | Serverfehler |

## Technologie-Stack

- *Spring Boot* für die Gateway-Implementierung
- *Spring Security WebFlux* für API-Schutz
- *Swagger UI* für Dokumentation
- *gRPC* für schnelle Kommunikation
- *REST & GraphQL* für flexible Abfragen

## User Stories

1. **Authentifizierung:**  
   - Als *Benutzer* möchte ich mich registrieren und einloggen können, um auf die Services zuzugreifen.
2. **Benutzerverwaltung:**  
   - Als *Administrator* möchte ich Benutzer erstellen, bearbeiten und löschen können.
3. **Produktverwaltung:**  
   - Als *Admin* möchte ich Produkte verwalten, um den Bestand aktuell zu halten.
4. **GraphQL-Abfragen:**  
   - Als *Entwickler* möchte ich flexible GraphQL-Abfragen ausführen können.
5. **gRPC-Interaktion:**  
   - Als *System* möchte ich gRPC nutzen, um Bestandsprüfungen durchzuführen.