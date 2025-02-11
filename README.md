# *SCM System - Hauptdokumentation*

## *1. Einleitung*

Das *SCM System* ist eine umfassende *Bestands- und Bestellverwaltungsplattform, die speziell für **Großhändler* entwickelt wurde. Ziel ist es, die *Effizienz der Lagerverwaltung zu steigern, eine **bessere Übersicht über ausgehende Bestände* zu bieten und eine *flexible Produktverwaltung* zu ermöglichen. Das System digitalisiert und automatisiert Abläufe, um sowohl Administratoren als auch Kunden eine *reibungslosere Bestellabwicklung* zu bieten.

### *Lösung für den Admin*
- *Optimierte Bestandsverwaltung*: Automatische Reduzierung des Lagerbestands bei Bestellungen.
- *Bessere Übersicht über ausgehende Produkte*: Transparente Darstellung von Bestellvorgängen und Lagerbewegungen.
- *Flexible Produkt- und Inventarverwaltung*: Einfache Verwaltung von Produkten und Inventar über das Admin-Panel.
- *Nur registrierte und eingeloggte Benutzer können Bestellungen aufgeben*, sodass eine genaue Übersicht darüber besteht, welcher Benutzer welche Bestellungen tätigt. Dadurch kann überprüft werden, ob es sich um eine echte Person handelt.


### *Lösung für den Benutzer*
- *Nur registrierte und eingeloggte Benutzer können Bestellungen aufgeben:*
  - Dadurch wird sichergestellt, dass alle Bestellungen einer echten Person zugeordnet werden können.
  - Dies ermöglicht eine detaillierte Nachverfolgung der Bestellungen pro Benutzer und verhindert anonyme oder betrügerische Bestellungen.
  
- *Bessere Übersicht über alle verfügbaren Produkte*.
- *Einfache Bestellungen mit minimalem Aufwand* – kein manueller Kontakt über Telefon oder E-Mail nötig.
- *Effiziente Verwaltung von Bestellungen und Warenkorb-Funktionalität*.

Das System wurde entwickelt, um *echte Probleme* von Großhändlern zu lösen. Als Beispiel wurde ein *Juwelier-Großhandel* genutzt, für den wir bereits eine erweiterte und optimierte Lösung entwickelt haben. Diese Lösung konnte sich *über einen längeren Zeitraum erfolgreich bewähren*.

Technologisch basiert das System auf einer *Microservice-Architektur, mit einem **React-Frontend, einem **Spring Boot-Backend, sowie **JWT für die Authentifizierung* und *OpenAPI für die API-Dokumentation*.

## *2. Systemarchitektur*

Zu Beginn haben wir mit einem *Monolithen* gearbeitet, da es die Entwicklung und das Debugging erleichtert. Eine einzelne Codebasis machte es für unser Team einfacher, Änderungen zu testen, neue Features zu implementieren und das System in einem frühen Stadium stabil zu halten.


Das System folgt einer Microservice-Architektur mit einem zentralen *API Gateway, das Anfragen an die entsprechenden Services weiterleitet. Alle Daten werden über den **Data Service* verwaltet.

```text
                      +---------------------+
                      |      Client        |
                      |  (localhost:3000)  |
                      +---------+---------+
                                |
                      +---------v---------+
                      |   API Gateway    |
                      | (Spring Gateway) |
                      |   Port: 8080     |
                      +---------+---------+
                                |
    ------------------------------------------------------------------------------
    |                 |                  |                                       |
 +------+         +------+          +--------+                              +----------+
 | Auth |         | User |          | Product| <--------------------------- | GraphQL  |
 | Svc  |         | Svc  |          | Svc    |      Inventar bearbeiten     | API Order|
 +------+         +------+          +--------+                              +----------+
                                |
                            +--------+
                            | Data   |
                            | Service|
                            +--------+
                                |
                            +--------+
                            |  DB    |
                            +--------+

```
## *3. Funktionsübersicht*

### *3.1 Admin-Panel Funktionen*
- Verwaltung von *Produkten* (Hinzufügen, Bearbeiten, Löschen, Bestand prüfen).
- Verwaltung von *Benutzern*.
- Einsicht in *Bestellungen*.
- Kontrolle des *Lagerbestands*.

### *3.2 User-Panel Funktionen*
- *Produkte durchsuchen*.
- *Produkte in den Warenkorb legen*.
- *Bestellungen aufgeben*.

## *4. Verwendete Technologien*

| Technologie | Zweck | Dokumentation |
|------------|--------|---------------|
| *Spring Boot* | Backend-Entwicklung | [Spring Boot Docs](https://spring.io/projects/spring-boot) |
| *Spring Cloud Gateway* | API Gateway | [Spring Cloud Gateway Docs](https://spring.io/projects/spring-cloud-gateway) |
| *Spring Security & JWT* | Authentifizierung | [jjwt Docs](https://github.com/jwtk/jjwt) |
| *Spring Boot REST* | REST API Endpunkte | [Spring Boot REST Docs](https://spring.io/guides/gs/rest-service/) |
| *GraphQL für Spring Boot* | GraphQL API | [Spring Boot GraphQL Docs](https://www.graphql-java.com/documentation/spring-boot) |
| *JSON* | Datenformat | [JSON Docs](https://www.json.org/json-en.html) |
| *React.js* | Frontend-Entwicklung | [React Docs](https://reactjs.org/) |
| *OpenAPI (Swagger UI)* | API-Dokumentation | [OpenAPI Docs](https://swagger.io/specification/) |

## *5. Microservices & Dokumentationen*

| Service | Beschreibung | Dokumentation |
|---------|-------------|---------------|
| *Auth Service* | Verwaltung von Benutzerauthentifizierung & JWT | [Auth Service Docs](https://github.com/WebApps-WiSe-24/webapp-power-rangers/blob/main/auth/Docs.md) |
| *User Service* | Benutzerverwaltung | [User Service Docs](https://github.com/WebApps-WiSe-24/webapp-power-rangers/blob/main/users/docs.md) |
| *Product Service* | Verwaltung von Produkten | [Product Service Docs](https://github.com/example/product-docs) |
| *GraphQL API* | Flexible Abfragen über GraphQL | [GraphQL API Docs](https://github.com/WebApps-WiSe-24/webapp-power-rangers/blob/main/graphql/docs.md) |
| *Data Service* | Zentraler Datenzugriff | [Data Service Docs](https://github.com/WebApps-WiSe-24/webapp-power-rangers/blob/main/dataservice/docs.md) |

## *6. Fazit*

Das *SCM System* bietet eine vollständige Lösung zur Verwaltung von Großhandelsbestellungen mit *automatischer Bestandsanpassung* und *flexibler Benutzerverwaltung. Durch die Nutzung von **Microservices* und einer *klaren API-Struktur* ist es leicht skalierbar und erweiterbar.
