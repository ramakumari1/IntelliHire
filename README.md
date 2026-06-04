# IntelliHire – Job Portal Web Application

A full-stack Job Portal Application built using Spring Boot, Thymeleaf, MySQL, Spring Data JPA, Bootstrap 5, Maven, and BCrypt Authentication.

## Overview

IntelliHire is a role-based job portal that connects job seekers and administrators through a secure and responsive web platform. The application enables users to browse and apply for jobs, upload resumes, manage profiles, and track applications, while administrators can manage users, job postings, and platform activities through a dedicated dashboard.

## Features

### User Features

* User Registration and Login
* BCrypt Password Encryption
* Profile Management
* Resume Upload
* Browse Available Jobs
* Search Jobs
* Apply for Jobs
* Prevent Duplicate Applications
* View Applied Jobs
* Application Status Tracking

### Admin Features

* Admin Login
* Dashboard with Statistics
* Manage Users
* Add Jobs
* Delete Jobs
* View Applications
* Track Platform Activity

### General Features

* Role-Based Access Control (Admin/User)
* Session Management
* Pagination
* Search Functionality
* Responsive Bootstrap UI
* Secure Password Storage

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven

### Frontend

* Thymeleaf
* HTML5
* CSS3
* Bootstrap 5
* Bootstrap Icons

### Database

* MySQL

### Security

* BCrypt Password Encryption
* Session-Based Authentication
* 
## Project Structure

IntelliHire
│
├── Controllers
├── Services
├── Repositories
├── Entities
├── Templates (Thymeleaf)
├── Static Resources
├── Uploads
└── MySQL Database

## Project Highlights

* Engineered a secure authentication and authorization system using BCrypt password hashing and session-based access control.
* Implemented role-based access control to separate Admin and User permissions.
* Modelled relational data across Users, Jobs, and Applications using Spring Data JPA and MySQL.
* Developed a complete job application workflow including resume uploads and application tracking.
* Built an administrative dashboard displaying real-time statistics for users, jobs, and applications.
* Designed a responsive and user-friendly interface using Bootstrap 5 and Thymeleaf templates.
* Developed the entire application independently, covering database design, backend development, frontend integration, authentication, file handling, and deployment-ready project structure.

## Screenshots

### Registration Page

<img width="1095" height="648" alt="WhatsApp Image 2026-06-04 at 11 29 11 AM" src="https://github.com/user-attachments/assets/ae6f3b4e-c85e-43e1-b155-c4a68371204b" />

### Login Page

<img width="1725" height="1031" alt="Screenshot 2026-06-04 113037" src="https://github.com/user-attachments/assets/5b710650-0ba8-4f9c-8d13-374aed27488b" />

### User Dashboard

<img width="1905" height="924" alt="image" src="https://github.com/user-attachments/assets/3c1bcdff-937d-42a7-b670-b0da889381d3" />

### Admin Dashboard

<img width="1888" height="1013" alt="image" src="https://github.com/user-attachments/assets/05de13e8-5a6d-4b25-9320-e3e599d8f070" />

### Admin Panel

<img width="1629" height="914" alt="Screenshot 2026-06-04 113309" src="https://github.com/user-attachments/assets/1172162c-fe66-4ed9-9fb7-f9083c6403a5" />

### Available Jobs

<img width="1762" height="1033" alt="image" src="https://github.com/user-attachments/assets/4b9647a9-b1f4-4527-8f81-0e81065da85d" />

### Profile Page
<img width="1781" height="937" alt="image" src="https://github.com/user-attachments/assets/3511a097-8fde-4ff8-94ce-bbc6f2ddba8e" />

### My Applications

<img width="1787" height="827" alt="image" src="https://github.com/user-attachments/assets/75308707-718a-4ca0-903a-8b4fdf70b47d" />

## Installation & Setup

1. Clone the repository

git clone https://github.com/ramakumari1/IntelliHire.git

2. Open in IntelliJ IDEA

3. Configure MySQL database

Create database:

CREATE DATABASE intellihire;

4. Update application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/intellihire
spring.datasource.username=root
spring.datasource.password=your_password

5. Run the application

6. Open browser

http://localhost:8080

## Future Enhancements

* Forgot Password via Email OTP
* Email Verification During Registration
* Job Recommendation System
* Admin Application Search & Filtering
* Interview Scheduling Module
* Notification System

## Author

Chandrakala Ramakumari

GitHub:
https://github.com/ramakumari1

Project Repository:
https://github.com/ramakumari1/IntelliHire
