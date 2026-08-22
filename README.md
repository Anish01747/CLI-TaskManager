# CLI Task Manager

A command-line task management application built with **Java, Spring Boot, Spring Shell, Spring Data JPA, Hibernate, and MySQL**.

The application allows users to create, manage, search, filter, sort, and track tasks directly from the command line. Tasks are stored persistently in MySQL.

## Features

* Add tasks
* List all tasks
* Get a task by ID
* Update tasks
* Delete tasks
* Task validation
* Custom exception handling
* Task status management

    * `PENDING`
    * `IN_PROGRESS`
    * `COMPLETED`
* Search tasks by title or description
* Filter tasks by status
* Sort tasks
* Assign due dates
* Detect overdue tasks
* Display task statistics
* Persistent MySQL database storage
* Clean CLI output

## Technologies Used

* **Java 24**
* **Spring Boot 4.0.7**
* **Spring Shell 4.0.2**
* **Spring Data JPA**
* **Hibernate**
* **MySQL**
* **Maven**

## Architecture

The application follows a layered architecture:

```text
                    Spring Shell CLI
                           │
                           ▼
                     TaskCommand
                           │
                           ▼
                      TaskService
                           │
                           ▼
                    TaskRepository
                           │
                           ▼
                 Spring Data JPA
                           │
                           ▼
                       Hibernate
                           │
                           ▼
                         MySQL
```

### Layers

**Command Layer**

Handles user interaction through Spring Shell commands.

**Service Layer**

Contains the application's business logic, validation, status management, searching, filtering, sorting, overdue detection, and statistics.

**Repository Layer**

Uses Spring Data JPA to communicate with the database.

**Model Layer**

Contains the `Task` JPA entity.

**Exception Layer**

Contains custom exceptions for validation and missing tasks.

## Project Structure

```text
CLI-Task-Manager/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── taskmanager/
│       │           │
│       │           ├── command/
│       │           │   └── TaskCommand.java
│       │           │
│       │           ├── exception/
│       │           │   ├── TaskNotFoundException.java
│       │           │   └── TaskValidationException.java
│       │           │
│       │           ├── model/
│       │           │   └── Task.java
│       │           │
│       │           ├── repository/
│       │           │   └── TaskRepository.java
│       │           │
│       │           ├── service/
│       │           │   └── TaskService.java
│       │           │
│       │           └── TaskManagerApplication.java
│       │
│       └── resources/
│           └── application.properties
│
├── pom.xml
└── README.md
```

## Task Model

Each task contains:

| Field       | Description                                |
| ----------- | ------------------------------------------ |
| ID          | Automatically generated task identifier    |
| Title       | Task title                                 |
| Description | Detailed task description                  |
| Status      | Current task status                        |
| Due Date    | Date by which the task should be completed |

## CLI Commands

### Add Task

Creates a new task with a title, description, and due date.

```text
add <title> <description> <due-date>
```

Example:

```text
add "Learn Java" "Complete Java revision" 2026-08-30
```

### List Tasks

Displays all stored tasks.

```text
list
```

### Get Task

Retrieves a task using its ID.

```text
get <id>
```

### Update Task

Updates an existing task.

```text
update <id> <title> <description> <due-date>
```

### Delete Task

Deletes a task using its ID.

```text
delete <id>
```

### Update Status

Changes the status of a task.

```text
status <id> <status>
```

Available statuses:

```text
PENDING
IN_PROGRESS
COMPLETED
```

### Search Tasks

Searches tasks by title or description.

```text
search <keyword>
```

Example:

```text
search spring
```

### Filter Tasks

Filters tasks according to their status.

```text
filter <status>
```

Example:

```text
filter PENDING
```

### Sort Tasks

Sorts tasks using a selected field and direction.

```text
sort <field> <direction>
```

Example:

```text
sort title asc
```

Supported directions:

```text
asc
desc
```

### Show Overdue Tasks

Displays tasks whose due dates have passed and which are not completed.

```text
overdue
```

### Show Statistics

Displays task statistics.

```text
stats
```

Example output:

```text
========== TASK STATISTICS ==========
Total tasks: 5
Pending: 2
In Progress: 1
Completed: 2
Overdue: 1
=====================================
```

## Validation and Exception Handling

The application validates task input before saving or updating data.

Examples of handled cases:

* Empty task title
* Empty task description
* Title exceeding the allowed length
* Description exceeding the allowed length
* Invalid task status
* Task ID not found
* Invalid due-date format
* Invalid sorting field

Custom exceptions are used for task validation and missing tasks.

## Database

The application uses **MySQL** for persistent storage.

Database:

```text
task_manager
```

The application uses:

* Spring Data JPA
* Hibernate
* JPA entity mapping
* Repository-based database operations

Tasks remain stored in the database even after the application is stopped and restarted.

## How the Application Works

```text
User enters command
        │
        ▼
Spring Shell
        │
        ▼
TaskCommand
        │
        ▼
TaskService
        │
        ├── Validation
        ├── Status handling
        ├── Search
        ├── Filtering
        ├── Sorting
        ├── Overdue detection
        └── Statistics
        │
        ▼
TaskRepository
        │
        ▼
JPA / Hibernate
        │
        ▼
MySQL
```

## Development Roadmap

The project was developed incrementally:

### Day 1 — Spring Boot Foundation

* Spring Boot project setup
* Maven configuration
* Basic application structure

### Day 2 — Task Foundation

* Task model
* In-memory task management foundation
* Basic task service

### Day 3 — Database + JPA + CLI

* MySQL integration
* JPA entity
* Hibernate
* Spring Data JPA repository
* CRUD operations
* Spring Shell CLI integration

### Day 4 — Validation + Exceptions + Status

* Task validation
* Custom exceptions
* Task status management
* Error handling

### Day 5 — Advanced Task Management

* Search
* Filter
* Sorting
* Due dates
* Overdue task detection
* Statistics

### Finalization

* Code cleanup
* CLI output improvements
* README documentation
* GitHub-ready project

## Key Learning Outcomes

Through this project, the following concepts were implemented:

* Spring Boot application structure
* Dependency Injection
* Spring Shell CLI
* Layered architecture
* Spring Data JPA
* Hibernate ORM
* MySQL database integration
* CRUD operations
* Entity and repository design
* Service-layer business logic
* Input validation
* Custom exceptions
* Searching and filtering
* Sorting
* Date handling with `LocalDate`
* Database persistence

## Future Improvements

Possible future extensions include:

* REST API
* Authentication and authorization
* Task priorities
* Categories and tags
* Pagination
* Web-based interface
* Docker support

## Author

**Anish Kumar**

---

⭐ If you found this project useful, feel free to explore the repository and its development history.
