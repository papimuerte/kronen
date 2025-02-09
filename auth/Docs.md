# Webservices-Dokumentation 

## **1. Benutzerregistrierung**

### **Beschreibung**
Registriert einen neuen Benutzer im System. Es wird geprüft, ob der Benutzername bereits existiert. Bei Erfolg wird der Benutzer gespeichert.

### **Schnittstellendefinition**
- **URL:** `POST /auth/register`
- **Request:**
```json
{
  "username": "john_doe",
  "password": "securepassword",
  "email": "john.doe@example.com",
  "phoneNumber": "+49123456789",
  "address": "Berlin, Germany",
  "companyName": "Tech Solutions"
}
```
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "message": "User successfully registered. Please log in."
    }
    ```
  - **Fehler (400 Bad Request):**
    ```json
    {
      "error": "Username already exists."
    }
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error registering user."
    }
    ```

---

## **2. Benutzeranmeldung**

### **Beschreibung**
Authentifiziert einen Benutzer basierend auf den bereitgestellten Anmeldedaten und generiert ein JWT-Token. Das Token enthält Benutzerdaten und Rolleninformationen.

### **Schnittstellendefinition**
- **URL:** `POST /auth/login`
- **Request:**
```json
{
  "username": "john_doe",
  "password": "securepassword"
}
```
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "links": {
        "self": "/auth/login",
        "redirect": "/shop"
      }
    }
    ```
  - **Fehler (401 Unauthorized):**
    ```json
    {
      "error": "Invalid login credentials"
    }
    ```
  - **Fehler (500 Internal Server Error):**
    ```json
    {
      "error": "Error accessing user data"
    }
    ```

---

## **3. Token-Validierung**

### **Beschreibung**
Überprüft die Gültigkeit eines bereitgestellten JWT-Tokens. Gibt die enthaltenen Claims zurück, wenn das Token gültig ist.

### **Schnittstellendefinition**
- **URL:** `GET /auth/validate?token=<JWT-TOKEN>`
- **Response:**
  - **Erfolg (200 OK):**
    ```json
    {
      "sub": "john_doe",
      "role": "USER",
      "email": "john.doe@example.com"
    }
    ```
  - **Fehler (401 Unauthorized):**
    ```json
    {
      "error": "Invalid token."
    }
    ```

