# User Service

Microservice responsible for user management in the SocialApp platform.

---

## Overview

The User Service handles user registration, authentication, and profile management. It is the foundational service that other microservices depend on for user identity.

---

## Tech Stack

| Technology       | Version |
|------------------|---------|
| Java             | 21      |
| Spring Boot      | 4.1.1   |
| Spring Security  | 7.1     |
| PostgreSQL       | 14+     |
| Hibernate/JPA    | 7.4     |
| Lombok           | 1.18    |
| Maven            | 3.9     |

---

## API Endpoints

| Method | Endpoint              | Description       | Auth Required |
|--------|-----------------------|-------------------|---------------|
| POST   | `/api/user/register`  | Register new user | No            |
| POST   | `/api/user/login`     | Login user        | No            |
| GET    | `/api/user/{username}`| Get user profile  | No (JWT planned) |

---

## Request / Response Examples

### Register User
**POST** `/api/user/register`

Request:
```json
{
  "name": "Nitin Kumar",
  "username": "nitin",
  "email": "nitin@example.com",
  "password": "Password@123",
  "bio": "Hey there! I'm using SocialApp"
}
```

Response (`201 Created`):
```json
{
  "id": 1,
  "name": "Nitin Kumar",
  "username": "nitin",
  "email": "nitin@example.com",
  "bio": "Hey there! I'm using SocialApp"
}
```

### Login User
**POST** `/api/user/login`

Request:
```json
{
  "username": "nitin",
  "password": "Password@123"
}
```

Response (`200 OK`):
```json
{
  "id": 1,
  "name": "Nitin Kumar",
  "username": "nitin",
  "email": "nitin@example.com",
  "bio": "Hey there! I'm using SocialApp"
}
```

### Get User Profile
**GET** `/api/user/{username}`

Response (`200 OK`):
```json
{
  "id": 1,
  "name": "Nitin Kumar",
  "username": "nitin",
  "email": "nitin@example.com",
  "bio": "Hey there! I'm using SocialApp"
}
```

---

## Error Responses

| Status Code | Scenario                        | Example Message                          |
|-------------|---------------------------------|------------------------------------------|
| 404         | User not found                  | `User with username nitin not found`     |
| 409         | Username already taken          | `Username 'nitin' is already taken`      |
| 409         | Email already in use            | `Email 'nitin@example.com' is already in use` |

Error Response Format:
```json
{
  "status": 404,
  "message": "User with username nitin not found",
  "timestamp": "2026-08-25T01:16:28"
}
```

---

## Database

- **Database:** PostgreSQL
- **DB Name:** `socialapp_users`
- **Table:** `users`

| Column   | Type         | Constraints              |
|----------|--------------|--------------------------|
| id       | BIGSERIAL    | Primary Key, Auto-gen    |
| username | VARCHAR(50)  | Unique, Not Null         |
| name     | VARCHAR(100) | Not Null                 |
| email    | VARCHAR(150) | Unique, Not Null         |
| password | VARCHAR(255) | Not Null (BCrypt hashed) |
| bio      | VARCHAR(500) | Nullable                 |

---

## Project Structure

```
user-service/
├── pom.xml
├── src/main/java/com/socialapp/userservice/
│   ├── UserServiceApplication.java
│   ├── config/
│   │   ├── PasswordConfig.java
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   └── UserController.java
│   ├── dto/
│   │   ├── LoginRequest.java
│   │   └── UserResponse.java
│   ├── exception/
│   │   ├── EmailAlreadyExistException.java
│   │   ├── ErrorResponse.java
│   │   ├── GlobalExceptionHandler.java
│   │   ├── UserNameAlreadyExistException.java
│   │   └── UserNotFoundException.java
│   ├── model/
│   │   └── User.java
│   ├── repository/
│   │   └── UserRepository.java
│   └── service/
│       └── UserService.java
└── src/main/resources/
    ├── application.yaml
    └── application-local.yaml
```

---

## How to Run

### Prerequisites
- Java 21
- PostgreSQL running on `localhost:5432`
- Database `socialapp_users` created

### Setup Database
```bash
psql -U postgres -c "CREATE DATABASE socialapp_users;"
```

### Run the Service
```bash
cd user-service
./mvnw spring-boot:run
```

The service starts on **http://localhost:8080**

---

## Configuration

### application.yaml
- Datasource URL: `jdbc:postgresql://localhost:5432/socialapp_users`
- Hibernate DDL: `update` (auto-creates/updates tables)
- Active Profile: `local`

### application-local.yaml
- Database credentials (local development only)

---

## Upcoming
- [ ] JWT token generation on login
- [ ] JWT validation filter for protected endpoints
- [ ] Eureka service registration
- [ ] Dockerfile

