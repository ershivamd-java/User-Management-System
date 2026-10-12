# User Management System

A Spring Boot based **Student & User Management System** developed using Java, Spring Boot, Spring Data JPA, Hibernate and MySQL.

The project manages the complete student journey from **Enquiry → Admission → Student → Batch → Attendance → Course Completion → Certificate**.

## 🚀 Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST APIs
- JWT Authentication
- Postman
- Eclipse / VS Code

## 📦 Base Package

```text
com.gajendra
```

## 🏗️ Project Architecture

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Entity
    ↓
MySQL Database
```

## 🔄 Project Workflow

```text
Enquiry
   ↓
Admission
   ↓
Student
   ↓
Course + Batch
   ↓
Attendance
   ↓
Course Completion
   ↓
Certificate
```

## 📚 Main Modules

### 1. Course Management

Manages courses offered by the institute.

Main operations:

```text
GET    /api/courses
GET    /api/courses/{id}
POST   /api/courses
PUT    /api/courses/{id}
DELETE /api/courses/{id}
```

### 2. Batch Management

Manages course batches, timings and student capacity.

Examples:

```text
GET    /api/batches
GET    /api/batches/{id}
GET    /api/batches/course/{courseId}
POST   /api/batches/course/{courseId}
PUT    /api/batches/{id}/course/{courseId}
DELETE /api/batches/{id}
```

### 3. Enquiry Management

Stores and manages student enquiries.

The enquiry workflow supports:

- Student enquiry
- Enquiry source
- Enquiry status
- Follow-up information
- Admission conversion

### 4. Admission Management

Converts an enquiry into an admission.

Admission stores information such as:

- Admission number
- Student
- Course
- Batch
- Course fee
- Discount
- Final fee
- Admission date
- Admission status

Example admission number:

```text
TF-2026-0001
```

### 5. Student Management

Manages student information and admission-related student records.

Student profile includes:

- Student name
- Mobile
- Email
- Address
- Qualification
- Created date

### 6. Batch Student Management

Provides APIs to manage students inside batches.

Examples:

```text
GET /api/batches/{batchId}/students

GET /api/students/{studentId}/batch

PUT /api/students/{studentId}/batch/{newBatchId}
```

Batch capacity is also checked while assigning students.

### 7. Attendance Management

Tracks student attendance for batches.

The project also contains attendance reporting functionality.

### 8. Student Profile

Students can view and update their profile information.

Profile management includes:

- View profile
- Update profile
- Student information validation

### 9. Authentication & Authorization

The application uses JWT based authentication.

Authentication APIs include:

```text
POST /api/auth/register
POST /api/auth/login
```

Supported roles:

```text
ADMIN
STAFF
STUDENT
```

Passwords are stored securely using password hashing.

### 10. Course Completion & Certificate

When a student successfully completes a course, a course completion record is created.

API:

```text
POST /api/students/{studentId}/complete-course
```

A unique certificate number is automatically generated.

Example:

```text
TF-CERT-2026-0001
```

The admission status is updated to:

```text
COMPLETED
```

Certificate APIs:

```text
GET /api/students/{studentId}/certificate

GET /api/course-completions
```

The same admission cannot be completed twice.

## 🗂️ Main Package Structure

```text
com.gajendra
│
├── controller
├── dto
├── entity
├── repository
├── service
└── exception
```

## 🗄️ Main Entities

```text
User
Course
Batch
Enquiry
Admission
Student
Attendance
CourseCompletion
```

## 🔐 Security

JWT authentication is used to protect secured APIs.

For protected APIs, send the JWT token in the request header:

```text
Authorization: Bearer <JWT_TOKEN>
```

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/ershivamd-java/User-Management-System.git
```

### 2. Open the project

Open the project in Eclipse or IntelliJ IDEA.

### 3. Configure MySQL

Create the required MySQL database and update the database configuration in:

```text
src/main/resources/application.properties
```

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## 🧪 API Testing

The REST APIs can be tested using **Postman**.

Testing includes:

- Authentication
- Course APIs
- Batch APIs
- Enquiry APIs
- Admission APIs
- Student APIs
- Attendance APIs
- Course completion APIs
- Certificate APIs

## 📌 Course Completion Example

Request:

```http
POST /api/students/1/complete-course
```

Request body:

```json
{
  "remarks": "Course completed successfully"
}
```

Example response contains:

```json
{
  "certificateNumber": "TF-CERT-2026-0001",
  "completionDate": "2026-10-06",
  "remarks": "Course completed successfully"
}
```

## 🎯 Project Objective

The main objective of this project is to provide a complete backend system for managing students, courses, batches, enquiries, admissions, attendance and course completion.

It demonstrates practical implementation of:

- REST API development
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- JWT authentication
- Role based authorization
- DTOs
- Exception handling
- Entity relationships
- Git & GitHub workflow
  ## Task 9: Certificate PDF Generation

### Overview
Implemented PDF certificate generation for students who have successfully completed their course. The certificate uses the existing certificate number generated by Task 8.

### Features
- Generate and download a course completion certificate in PDF format.
- Include the student's name, course name, course completion date, and certificate number.
- Include remarks when available.
- Reuse the existing certificate record instead of generating a new certificate number.
- Download the certificate as a PDF file using the REST API.

### API Endpoint

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students/{studentId}/certificate/pdf` | Download the student's certificate as a PDF |
| GET | `/api/students/{studentId}/certificate` | Retrieve the existing certificate details |

### Example Request

```http
GET http://localhost:8080/api/students/1/certificate/pdf
```

**Authorization:** Bearer Token (JWT)

**Response:** A downloadable PDF certificate.

### Technology Used
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- OpenPDF
- Maven
- REST API
- JWT Authentication

### Testing
Verified the certificate PDF download using Postman. Checked that the student name, course name, completion date, and existing certificate number appear correctly in the generated PDF.

## 👨‍💻 Developer

**Shivam Dheemar/ Gajendra Singh lodhi **

Java Backend Developer | Spring Boot | Hibernate | MySQL

GitHub:

https://github.com/ershivamd-java
