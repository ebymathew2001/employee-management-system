# 🧑‍💼 Employee Management System – Spring Boot

This is a RESTful API project built using **Java 21**, **Spring Boot 3.5.3**, and **MySQL** to manage employees and departments. It fulfills the given coding assessment requirements with clean code structure and separation of concerns.

---

## ✅ Features Implemented

- ✅ Create, update, and fetch employees
- ✅ Move employee between departments
- ✅ Create, update, delete departments (deletion fails if employees exist)
- ✅ Fetch department with employees using `expand=employee`
- ✅ List only employee names and IDs using `lookup=true`
- ✅ All GET APIs support pagination (`page`, `size`)
- ✅ Validation and exception handling using Jakarta Bean Validation
- ✅ JPA entity relationships: One-to-Many, Many-to-One, Self-Reference
- ✅ Clean architecture: Controller → Service → Repository

---

## 🚀 Tech Stack

| Technology        | Version     |
|------------------|-------------|
| Java             | 21          |
| Spring Boot      | 3.5.3       |
| Spring Data JPA  | ✅          |
| MySQL            | ✅          |
| Lombok           | ✅          |
| Maven            | ✅          |

---

## 📦 API Endpoints

### 🧑 Employee APIs

| HTTP Method | Endpoint                            | Description                                 |
|-------------|--------------------------------------|---------------------------------------------|
| POST        | `/api/employees`                    | Create a new employee                       |
| PUT         | `/api/employees/{id}`               | Update an existing employee                 |
| PATCH       | `/api/employees/{id}/department`    | Move employee to a different department     |
| GET         | `/api/employees`                    | Get all employee details (paginated)        |
| GET         | `/api/employees?lookup=true`        | Get only employee IDs and names             |

---

### 🏢 Department APIs

| HTTP Method | Endpoint                                      | Description                                   |
|-------------|-----------------------------------------------|-----------------------------------------------|
| POST        | `/api/departments`                           | Create a new department                       |
| PUT         | `/api/departments/{id}`                      | Update department details                     |
| DELETE      | `/api/departments/{id}`                      | Delete department (fails if employees exist)  |
| GET         | `/api/departments`                           | Get all departments (paginated)               |
| GET         | `/api/departments/{id}?expand=employee`      | Get department + list of employees inside     |

---

## 🗄️ Database

- ## 🗄️ Database

- You can find the schema file at: `schema.sql` (root folder)
- DB used: **MySQL**
- The schema includes:
    - `departments` table
    - `employees` table
    - Foreign keys: department head, employee's department, reporting manager

---

## ⚙️ How to Run

1. Clone the repository
2. Update your `application.properties` with your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```
3. Run the app:
    ```bash
    mvn spring-boot:run
    ```

---
