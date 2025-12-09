# 🌦️ Weather Service Backend
A production-ready Spring Boot application that manages weather reports, client registrations, subscription-based delivery, PDF report generation, and scheduled email notifications. The system integrates with an external microservice to provide international weather data and delivers automated weather reports via email.

---

# 📋 Table of Contents
Overview  
Architecture  
Features  
Technology Stack  
Functional Areas  
Getting Started  
Configuration  
API Documentation  
Scheduled Tasks  
Security  
Contributing  
License & Author  

---

# 🎯 Overview
The Weather Service Backend provides:

- Weather report CRUD using MySQL + JPA  
- Client onboarding with **email OTP verification**  
- Subscription-based weather delivery (GO / PRO / MAX)  
- **PDF weather report generation (OpenPDF)**  
- **Automated scheduled email notifications (Thymeleaf)**  
- Integration with external global weather API  
- Comprehensive REST API with Swagger support  
- Actuator endpoints for monitoring  

Designed with a clean, maintainable architecture — ready to scale or evolve into a microservice ecosystem in the future.

---

# 🏗️ Architecture
```
                         ┌──────────────────────────┐
                         │   Weather Service API    │
                         │       (Monolith)         │
                         └────────────┬─────────────┘
                                      │
              ┌───────────────────────┼─────────────────────────┐
              │                       │                         │
      ┌───────▼────────┐     ┌────────▼────────┐      ┌────────▼────────┐
      │ Weather CRUD    │     │ Client Handling │      │ Global Weather  │◄── External Microservice
      │ Local reports   │     │ Register + OTP  │      │ Integration     │
      └───────┬─────────┘     └────────┬────────┘      └────────┬────────┘
              │                        │                        │
      ┌───────▼────────┐       ┌───────▼────────┐      ┌────────▼────────┐
      │ PDF Generator  │       │ Email Sender   │      │ Scheduler Engine │
      │ (OpenPDF)      │       │ (Thymeleaf)    │      │ Cron triggers    │
      └───────┬────────┘       └───────┬────────┘      └────────┬────────┘
              │                        │                        │
        ┌─────▼───────┐         ┌──────▼────────┐       ┌───────▼────────┐
        │   MySQL     │         │ SMTP Provider │       │ Actuator/Logs   │
        └─────────────┘         └───────────────┘       └─────────────────┘
```

---

# ✨ Features

## 🌤 Weather Management
- Create, update, delete weather reports  
- Pagination support  
- Local weather + integrated global weather API  

## 👤 Client Management
- Client registration  
- Email OTP verification  
- Soft delete & update support  
- Subscription type assignment  

## 📄 PDF Weather Reports (Implemented)
- Generated using **OpenPDF**  
- Includes personalized weather data based on subscription  

## 📧 Scheduled Email Notifications (Implemented)
- GO / PRO → daily 08:00 AM  
- MAX → 08:00 AM & 08:00 PM  
- HTML email templates via **Thymeleaf**  
- PDF attached automatically  

## 🧰 Additional Features
- Swagger UI documentation  
- Uniform response structure  
- Actuator endpoints  
- Centralized exception handling  

---

# 🛠️ Technology Stack

### Backend
- Java 17  
- Spring Boot 3.5.x  
- Spring Web / JPA / Mail / Scheduler / Validation / Actuator  

### PDF & Email
- OpenPDF  
- Thymeleaf  

### Database
- MySQL  
- Hibernate (JPA)  

### Tools
- Maven  
- Lombok  
- springdoc-openapi (Swagger)  

---

# 🧩 Functional Areas (Accurate to Your Repository)

Your application is a **single Spring Boot project**, not a modular/microservice architecture.  
These are the logical **functional areas**, not separate modules:

### **1️⃣ Weather Management**
- Weather CRUD  
- Pagination  
- Entity + Repository + Service + Controller pattern  

### **2️⃣ Client Management**
- Registration  
- OTP email verification  
- Subscription assignment  
- Soft delete  

### **3️⃣ Global Weather Integration**
- Fetches international weather via an external microservice  
- Exposed via read-only endpoints  

### **4️⃣ PDF Generation**
- Generates PDF weather reports using OpenPDF  

### **5️⃣ Scheduled Email Delivery**
- Cron-based scheduling  
- Sends Thymeleaf-based HTML emails with PDF attachments  

---

# 🚀 Getting Started

## Prerequisites
- Java 17  
- Maven  
- MySQL  
- SMTP server (Mailtrap / Gmail App Password)

---

## 📦 Installation

### 1. Clone the repository
```bash
git clone https://github.com/pritiranjan-01/Weather-Service-Backend-Springboot-REST.git
cd Weather-Service-Backend-Springboot-REST
```

### 2. Create database
```sql
CREATE DATABASE weather_service;
```

### 3. Configure environment variables
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/weather_service
spring.datasource.username=root
spring.datasource.password=your_password

spring.mail.username=your_smtp_user
spring.mail.password=your_smtp_pass
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```

### 5. Open API documentation
```
http://localhost:8080/swagger-ui/index.html
```

---

# ⚙️ Configuration

### Database  
Spring Boot auto-creates tables using `ddl-auto=update`.

### SMTP Email  
Supports:  
- Gmail App Password  
- Mailtrap  
- Custom SMTP  

### Scheduler
```properties
schedule.go=0 0 8 * * *
schedule.pro=0 0 8 * * *
schedule.max.morning=0 0 8 * * *
schedule.max.evening=0 0 20 * * *
```

---

# 📘 API Documentation

### Weather APIs
```
POST /weather
GET /weather
GET /weather/{id}
PUT /weather/{id}
DELETE /weather/{id}
GET /weather/page?pageNumber=&pageSize=
```

### Global Weather APIs
```
GET /global
GET /global/{city}
```

### Client APIs
```
POST /client/register
POST /client/verify-otp?email=&otp=
GET /client
PUT /client?email=
DELETE /client?email=
```

---

# ⏰ Scheduled Tasks

| Subscription | Frequency | Action |
|--------------|-----------|--------|
| GO | Daily at 8 AM | Email PDF |
| PRO | Daily at 8 AM | Email PDF |
| MAX | 8 AM & 8 PM | Email PDF |

---

# 🔐 Security
- OTP-based email verification  
- Validation on all input fields  
- (Auth layer planned for future)  

---

# 🤝 Contributing
1. Fork the repo  
2. Create feature branch  
3. Commit changes  
4. Open Pull Request  

---

# 📄 License
MIT License

---

# 👤 Author
**Pritiranjan Mohanty**  
GitHub: https://github.com/pritiranjan-01  
Email: pritiranjan.mohanty2003@gmail.com  

---

# ⭐ If you find this project helpful, please give it a star!
