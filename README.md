# User Access Management System

## Project Overview
The **User Access Management System** is a web-based application that facilitates user registration, authentication, software application management, and access request handling within an organization. The application uses **Java Servlets**, **JSP**, and **PostgreSQL** for a robust and efficient solution.

---

## Features
### Core Functionalities
1. **User Registration (Sign-Up):** New users can create an account with a default role of "Employee."
2. **User Authentication (Login):** Validates credentials and redirects users based on roles.
3. **Software Management (Admin):** Admins can add and manage software applications.
4. **Access Request Submission (Employee):** Employees can request specific access to applications.
5. **Access Request Approval (Manager):** Managers review and approve/reject access requests.

### User Roles
1. **Employee:**
   - Sign up, log in, and request access to applications.
   - Cannot approve/reject requests or create applications.
2. **Manager:**
   - Review and manage access requests.
   - Cannot request access or create applications.
3. **Admin:**
   - Full access: create software, manage requests, and approve/reject requests.

---

## Technologies
- **Backend:** Java Servlets
- **Frontend:** JSP, HTML, CSS
- **Database:** PostgreSQL
- **Server:** Apache Tomcat
- **Build Tool:** Maven

---

## Prerequisites
1. Install **Java JDK** (version 8+).
2. Set up **Apache Tomcat** server.
3. Install **PostgreSQL** and configure a database for the application.
4. Install **Maven** for dependency management.

---

## Setup Instructions

### Database Setup
Open PostgreSQL and create the database:
Create Tables
   CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

CREATE TABLE software (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    access_levels TEXT
);

CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    software_id INT REFERENCES software(id),
    access_type TEXT NOT NULL,
    reason TEXT,
    status TEXT DEFAULT 'Pending'
);

## Git Clone
**git clone [https://github.com/your-repo/UserAccessManagementSystem](https://github.com/Sudhakar0512/UserAccessManagementSystem).git**

##Maven Install
**mvn clean install**

##WebBrowser
**http://localhost:8080/UserAccessManagementSystem/**
