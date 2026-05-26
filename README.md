# Task Manager API — Spring Boot

A RESTful Task Management System built with **Java 17** and **Spring Boot**.

**Developer:** Zain

---

## Tech Stack
- Java 17
- Spring Boot 3.3.5
- Spring Data JPA + Hibernate
- H2 In-Memory Database
- Lombok
- Swagger/OpenAPI 3
- JUnit 5 + Mockito
- Maven

---

## Run The Application

```bash
git clone <your-repo-url>
cd taskmanager
mvn spring-boot:run
```

App starts at: **http://localhost:8080**

| URL | Purpose |
|---|---|
| http://localhost:8080/api/tasks | Main API |
| http://localhost:8080/swagger-ui.html | API Docs |
| http://localhost:8080/h2-console | Database |

**H2 Login:** JDBC URL: `jdbc:h2:mem:taskmanager_db` | User: `sa` | Password: *(empty)*

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/tasks | Create task |
| GET | /api/tasks | Get all tasks |
| GET | /api/tasks/{id} | Get by ID |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Delete task |
| GET | /api/tasks/status/{status} | Filter by status |

**Status values:** `TODO` `IN_PROGRESS` `DONE`

**Pagination:** `GET /api/tasks?page=0&size=10&sort=createdAt&direction=desc`

---

## Sample Request

```json
POST /api/tasks
{
  "title": "My Task",
  "description": "Task description",
  "status": "TODO"
}
```

## Run Tests

```bash
mvn test
```
