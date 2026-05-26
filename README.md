📌 Task Management System — Spring Boot REST API

A RESTful Task Management System built using Java 17 and Spring Boot 3.2, demonstrating clean architecture, API design, exception handling, validation, and database integration.

👨‍💻 Developer

Zain

🧰 Tech Stack
Java 17
Spring Boot 3.2
Spring Data JPA / Hibernate
H2 In-Memory Database
Lombok
Swagger / OpenAPI
Maven
📁 Project Structure
src/main/java/com/zain/taskmanager/
│
├── controller/   → REST API endpoints
├── service/      → Business logic layer
├── repository/   → Database access layer
├── entity/       → JPA entities (database models)
├── dto/          → Request & Response models
├── enums/        → TaskStatus enum (TODO, IN_PROGRESS, DONE)
└── exception/    → Global exception handling
⚙️ Setup Instructions
📌 Prerequisites
Java 17+
Maven installed
IntelliJ IDEA (recommended)
🚀 Steps to Run
1️⃣ Clone the repository
git clone https://github.com/yourusername/taskmanager.git
cd taskmanager
2️⃣ Open in IDE
Open project in IntelliJ IDEA or Eclipse
Wait for Maven dependencies to download
3️⃣ Run Application

Run the main class:

TaskManagerApplication.java
4️⃣ Access Application
Service	URL
Application	http://localhost:8080
Swagger UI	http://localhost:8080/swagger-ui/index.html
H2 Console	http://localhost:8080/h2-console
🗄️ H2 Database Configuration
Field	Value
JDBC URL	jdbc:h2:mem:taskmanager_db
Username	sa
Password	(empty)
📡 API Endpoints
Method	Endpoint	Description
POST	/api/tasks	Create a new task
GET	/api/tasks	Retrieve all tasks
GET	/api/tasks/{id}	Get task by ID
PUT	/api/tasks/{id}	Update existing task
DELETE	/api/tasks/{id}	Delete a task
GET	/api/tasks/status/{status}	Filter tasks by status
📌 Sample API Requests
🟢 Create Task
POST /api/tasks
{
  "title": "Complete Spring Boot Project",
  "description": "Build REST API Task Manager",
  "status": "TODO"
}
🔵 Update Task
PUT /api/tasks/1
{
  "title": "Updated Task Title",
  "description": "Updated Description",
  "status": "IN_PROGRESS"
}
📊 Task Status Values
TODO → Task not started
IN_PROGRESS → Task in progress
DONE → Task completed
⚠️ Exception Handling

The application includes global exception handling for consistent API responses.

Scenario	HTTP Status	Message
Task not found	404	Task not found with id: X
Validation error	400	Field validation error details
Unexpected error	500	Something went wrong
🏗️ Architecture Overview

This project follows a Layered Architecture Pattern:

🔹 Controller Layer

Handles HTTP requests and responses

🔹 Service Layer

Contains business logic and processing

🔹 Repository Layer

Handles database interactions

🔹 Entity Layer

Maps Java objects to database tables

🔹 DTO Layer

Ensures separation between API and database models

✨ Key Features
Full CRUD REST APIs
Status-based filtering
Global exception handling
Request validation
Swagger API documentation
Clean layered architecture
H2 in-memory database (zero setup)
Lombok for clean code
🧪 Testing

Run unit tests using:

mvn test
📖 API Documentation

Interactive Swagger UI:

http://localhost:8080/swagger-ui/index.html
📌 Assumptions & Design Decisions
H2 in-memory database used for easy setup and portability
MySQL support can be enabled via configuration change
DTOs used to separate API layer from persistence layer
Global exception handler ensures consistent error responses
Swagger added for easy API testing and documentation
