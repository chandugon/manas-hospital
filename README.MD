# 🏥 Manas Hospital Management System

### A Full-Stack Hospital Management System built with Java, Spring Boot, MySQL and Thymeleaf

**Manas Hospital Management System (HMS)** is a web-based hospital management application developed to digitize and simplify common hospital operations such as patient registration, authentication, doctor management, appointment booking, and administrative user management.

The project follows a **layered Spring Boot architecture** with role-based access control for **Patients, Doctors, and Administrators**. It uses **Spring Data JPA/Hibernate** for database persistence, **Spring Security** for authentication and authorization, and **Thymeleaf + Tailwind CSS** for the user interface.

The application has also been **containerized with Docker and deployed on Railway**, making it accessible as a live web application.

---

## 🚀 Live Application

🌐 **Live Website:**
https://manas-hospital-production.up.railway.app/

💻 **GitHub Repository:**
https://github.com/chandugon/manas-hospital

---

# 🎯 Project Objective

The main objective of this project is to provide a centralized digital platform for managing hospital users and appointments.

The system replaces manual appointment and user-management workflows with a web-based application where:

* Patients can register and book appointments.
* Doctors can view their scheduled appointments.
* Administrators can manage users.
* Different user roles receive different access permissions.
* Appointment data is persisted in a relational MySQL database.
* Authentication and authorization are handled through Spring Security.

---

# ⭐ Key Features

## 👤 Patient Module

* Patient registration and login
* Secure authentication
* Patient dashboard
* Appointment booking
* Doctor selection while booking
* Appointment date and time selection
* Appointment history
* Calendar-based appointment view
* Role-based access to patient functionality

### Appointment Flow

```text
Patient Login
     ↓
Patient Dashboard
     ↓
Select Doctor
     ↓
Select Date & Time
     ↓
Create Appointment
     ↓
Appointment Stored in MySQL
     ↓
Patient / Doctor Appointment View
```

---

## 👨‍⚕️ Doctor Module

The system supports multiple doctors with separate accounts.

Current doctor accounts include:

* **Dr. Madhav Gonge**
* **Dr. Sandhya Gonge**

Doctor functionality includes:

* Secure doctor login
* Doctor-specific dashboard
* View scheduled appointments
* View patient appointment information
* Appointment calendar
* Role-based access control

Doctors can only access functionality permitted to the `DOCTOR` role.

---

## 👨‍💼 Admin Module

The administrator has access to administrative functionality for managing the hospital system.

Features include:

* Admin authentication
* User management
* View registered users
* Manage patient accounts
* Manage doctor accounts
* Role-based administrative access
* Appointment-related administration

Administrative endpoints are protected using Spring Security authorization.

---

# 🛠️ Technology Stack

## Backend

| Technology          | Purpose                                    |
| ------------------- | ------------------------------------------ |
| **Java 21**         | Core programming language                  |
| **Spring Boot 4**   | Backend application framework              |
| **Spring MVC**      | Web request and controller handling        |
| **Spring Data JPA** | Database persistence                       |
| **Hibernate**       | ORM implementation                         |
| **Spring Security** | Authentication and authorization           |
| **Maven**           | Dependency management and build automation |
| **Lombok**          | Reducing boilerplate Java code             |

---

## Database

### MySQL

The application uses **MySQL** as its relational database.

Main entities include:

```text
User
 ├── Patient
 ├── Doctor
 └── Appointment
```

The application uses JPA entity relationships to connect users, patients, doctors and appointments.

Hibernate is responsible for mapping Java entities to database tables.

---

## Frontend

| Technology          | Purpose                        |
| ------------------- | ------------------------------ |
| **Thymeleaf**       | Server-side HTML rendering     |
| **HTML5**           | Page structure                 |
| **Tailwind CSS**    | Responsive UI styling          |
| **JavaScript**      | Client-side interactions       |
| **FullCalendar.js** | Appointment calendar interface |
| **Font Awesome**    | UI icons                       |

The frontend uses Thymeleaf to dynamically display authenticated user information and appointment data received from Spring Boot controllers.

---

# 🔐 Security Architecture

Security is implemented using **Spring Security** with role-based authorization.

### Authentication

Users authenticate using their registered email and password.

Passwords are protected using **BCrypt password hashing** rather than storing plain-text passwords.

### Authorization

The application separates functionality using roles such as:

```text
PATIENT
DOCTOR
ADMIN
```

Administrative functionality is protected using method-level authorization such as:

```java
@PreAuthorize("hasRole('ADMIN')")
```

This prevents unauthorized users from accessing administrative operations.

---

# 🏗️ Application Architecture

The project follows a layered architecture:

```text
                    ┌─────────────────────┐
                    │      Browser        │
                    │ Thymeleaf / HTML UI │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Controllers      │
                    │  HTTP Request Layer │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     Services        │
                    │ Business Logic      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Repositories      │
                    │ Spring Data JPA     │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       MySQL         │
                    │     Database        │
                    └─────────────────────┘
```

### Main package structure

```text
com.hms.hospital
│
├── config
│   └── DataInitializer
│
├── controller
│   ├── AdminController
│   ├── AppointmentController
│   └── ...
│
├── entity
│   ├── User
│   ├── Patient
│   ├── Appointment
│   └── Role
│
├── repository
│   ├── UserRepository
│   ├── PatientRepository
│   └── AppointmentRepository
│
└── service
    └── PatientService
```

This separation keeps controllers, business logic, persistence and configuration responsibilities organized.

---

# 📅 Appointment Management

Appointments are represented using an `Appointment` entity containing information such as:

```text
Appointment
├── Patient
├── Doctor
├── Start Time
├── End Time
├── Title
└── Status
```

When a patient books an appointment:

1. The authenticated patient is identified.
2. The selected doctor is retrieved.
3. The requested date and time are converted into `LocalDateTime`.
4. The appointment duration is calculated.
5. The appointment is assigned a `SCHEDULED` status.
6. The appointment is saved through `AppointmentRepository`.
7. The appointment becomes available in the relevant appointment views.

---

# 🗄️ Database Design

The core database relationships can be represented as:

```text
                    USERS
                      │
             ┌────────┴────────┐
             │                 │
             ▼                 ▼
         PATIENTS           DOCTORS
             │                 │
             │                 │
             └───────┬─────────┘
                     │
                     ▼
               APPOINTMENTS
```

The application uses Hibernate/JPA to manage persistence and relationships between these entities.

---

# ⚙️ Configuration

The application uses Spring Boot configuration through:

```text
application.properties
```

Important configuration areas include:

```properties
spring.datasource.url
spring.datasource.username
spring.datasource.password

spring.jpa.hibernate.ddl-auto

server.port
```

For deployment, database configuration can be supplied through environment variables instead of hard-coding production credentials.

---

# 🐳 Docker & Deployment

The project includes Docker-based deployment support.

### Docker workflow

```text
Source Code
     ↓
Maven Build
     ↓
Spring Boot JAR
     ↓
Docker Image
     ↓
Railway Deployment
     ↓
Live Application
```

The application is deployed using **Railway** and runs as a production web application.

### Production URL

https://manas-hospital-production.up.railway.app/

---

# 🔄 Git & Version Control

Git is used for source-code management.

Development workflow:

```text
Local Development
       ↓
Git
       ↓
Commit
       ↓
GitHub
       ↓
Docker Build
       ↓
Railway Deployment
```

The project repository is available at:

https://github.com/chandugon/manas-hospital

---

# 🧪 Development & Problem Solving

During development, several real-world application problems were addressed, including:

### 1. Role-Based Login

Implemented separate access flows for:

```text
Patient
Doctor
Admin
```

### 2. Appointment Relationships

Connected patients and doctors through appointment records using JPA relationships.

### 3. Appointment Calendar

Integrated calendar-based appointment viewing to make scheduled appointments easier to understand.

### 4. Authentication & Authorization

Protected application functionality based on authenticated user roles.

### 5. Production Deployment

Configured the Spring Boot application for Docker-based deployment and deployed it to Railway.

### 6. Database Integration

Connected the Spring Boot application to MySQL using:

```text
Spring Data JPA
        +
Hibernate
        +
MySQL
```

---

# 💻 Running the Project Locally

## Prerequisites

Install:

* Java 21
* Maven
* MySQL
* Git
* Docker *(optional)*

---

## 1. Clone the Repository

```bash
git clone https://github.com/chandugon/manas-hospital.git
```

```bash
cd manas-hospital
```

---

## 2. Configure MySQL

Create a database:

```sql
CREATE DATABASE hospitaldb;
```

Update the database configuration in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospitaldb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## 3. Build the Application

Using Maven:

```bash
mvn clean install
```

---

## 4. Run the Application

```bash
mvn spring-boot:run
```

The application will run on:

```text
http://localhost:8080
```

---

# 🔑 Demo Accounts

## Administrator

```text
Email: admin@hospital.com
Role: ADMIN
```

## Doctor

```text
Email: madhav@hospital.com
Role: DOCTOR
```

## Doctor

```text
Email: sandhya@hospital.com
Role: DOCTOR
```

> Passwords should be configured according to the current application/database configuration. Do not commit production credentials or database passwords to GitHub.

---

# 📸 Main Application Modules

The project contains interfaces for:

* 🔐 Login
* 📝 Patient Registration
* 👤 Patient Dashboard
* 📅 Appointment Booking
* 🗓️ Appointment Calendar
* 👨‍⚕️ Doctor Dashboard
* 👨‍💼 Admin Panel
* 👥 User Management

---

# 📈 Future Improvements

Potential improvements for future versions include:

* Email notifications for appointments
* SMS appointment reminders
* Online medical-record management
* Doctor availability management
* Advanced appointment conflict detection
* REST API documentation
* Automated unit and integration testing
* CI/CD pipeline
* Application monitoring
* Advanced reporting and analytics

---

# 👨‍💻 About Me

## Chandrashekhar Gonge

**Java Developer | Spring Boot Developer | Full-Stack Developer**

I developed the **Manas Hospital Management System** to demonstrate practical experience in backend development, database integration, authentication, authorization, web application development and cloud deployment.

The project gave me hands-on experience working with:

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* Thymeleaf
* Tailwind CSS
* JavaScript
* Maven
* Git & GitHub
* Docker
* Railway

### Connect With Me

📧 **Email:** [chandrashekhargonge7@gmail.com](mailto:chandrashekhargonge7@gmail.com)

💼 **LinkedIn:**
https://www.linkedin.com/in/chandrashekhar-gonge-539b6420a/

🐙 **GitHub:**
https://github.com/chandugon

---

# 📌 Project Highlights

| Area             | Implementation                  |
| ---------------- | ------------------------------- |
| Language         | Java 21                         |
| Backend          | Spring Boot 4                   |
| Security         | Spring Security + BCrypt        |
| Authorization    | Role-Based Access Control       |
| Database         | MySQL                           |
| ORM              | Hibernate / JPA                 |
| Frontend         | Thymeleaf + HTML + Tailwind CSS |
| Calendar         | FullCalendar.js                 |
| Build Tool       | Maven                           |
| Version Control  | Git / GitHub                    |
| Containerization | Docker                          |
| Deployment       | Railway                         |
| Architecture     | Layered Architecture            |

---

# ⭐ Project Summary

**Manas Hospital Management System** demonstrates the development of a complete web application using modern Java technologies.

The project covers the complete development lifecycle:

```text
Requirement
    ↓
Application Design
    ↓
Java / Spring Boot Development
    ↓
Database Integration
    ↓
Security Implementation
    ↓
Frontend Development
    ↓
Testing & Debugging
    ↓
Dockerization
    ↓
GitHub Version Control
    ↓
Cloud Deployment
```

The project demonstrates practical understanding of **object-oriented programming, Spring Boot application development, relational database management, authentication and authorization, MVC architecture, Git version control, Docker and cloud deployment**.

---

## 📞 Contact

If you would like to discuss this project or my development experience:

**Chandrashekhar Gonge**
📧 [chandrashekhargonge7@gmail.com](mailto:chandrashekhargonge7@gmail.com)
💼 LinkedIn: https://www.linkedin.com/in/chandrashekhar-gonge-539b6420a/
🐙 GitHub: https://github.com/chandugon
