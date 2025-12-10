# 🌦️ Weather Service Backend
A fully functional weather management and automated reporting system built using Java, Spring Boot, and modern backend engineering practices. This project delivers end-to-end weather processing capabilities including client onboarding, subscription-based weather delivery, PDF report generation, and scheduled email notifications, showcasing real-world API design and automation patterns.

---

## 🚀 Live Demo
🔗 [**Backend Live API**](http://springbootrestapi-env.eba-bugmbvqw.ap-south-1.elasticbeanstalk.com/swagger-ui/index.html)  
🔗 [**Frontend App**](https://globalweatherservice.vercel.app/)  
🔗 [**Frontend Repository**](https://github.com/pritiranjan-01/weather-service-frontend)


---

# 📋 Table of Contents
- [Overview](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-overview)  
- [Architecture](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/edit/main/README.md#-api-documentation)  
- [Features](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#%EF%B8%8F-architecture)  
- [Technology Stack](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#%EF%B8%8F-technology-stack)  
- [Functional Areas](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-functional-areas)  
- [Getting Started](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-getting-started)
- [Deployment](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-deployment)
- [Configuration](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#3-configure-environment-variables)  
- [API Documentation](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-api-documentation)  
- [Scheduled Tasks](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-scheduled-tasks)
- [Monitoring](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-monitoring)
- [Contributing](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-contributing)  
- [License & Author](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/tree/main?tab=readme-ov-file#-license)  

---

# 🎯 Overview
The Weather Service Backend provides:

- Weather report CRUD using MySQL + JPA  
- Client onboarding with **email OTP verification**  
- Subscription-based weather delivery (GO / PRO / MAX)  
- PDF weather report generation (OpenPDF)
- Automated scheduled email notifications (Thymeleaf) 
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

## 📄 PDF Weather Reports
- Generated using **OpenPDF**  
- Includes personalized weather data based on subscription  

## 📧 Scheduled Email Notifications
- GO / PRO → daily 08:00 AM  
- MAX → 08:00 AM & 08:00 PM  
- Dynamic HTML email templates powered by **Thymeleaf**
- Email includes:
         - City-wise weather insights
         - Alerts, recommendations & temperature highlights
         - A “Download Full Report” button for PDF download
- PDF is generated on-demand using OpenPDF and served through a secure endpoint

---

# 🛠️ Technology Stack

### Backend
- **Java 17**: Core programming language 
- **Spring Boot**: Application framework
- **Spring Data JPA**: Database access
- **Spring AOP**: Centralised Logging
- **Spring Mail**: Mail Service
- **Spring Validation**: Validating API

### API Communication
- **RestTemplate**: External Microservice Communication

### PDF & Email
- **OpenPDF**: PDF Generation
- **Thymeleaf**: Html Email Generation

### Database
- **MySQL**: Relational database
- **Hibernate (JPA)**: ORM Tool  

### Monitoring & Tools
- **Spring Boot Actuator**: Application monitoring
- **Lombok**: Reduce boilerplate code
- **Maven**: Build automation
- **Git**: Version control

---

# 🧩 Functional Areas

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
- Fetches international weather from an external microservice
- Uses **RestTemplate** for API communication
- Exposes the data via read-only endpoints for client

### **4️⃣ PDF Generation**
- Generates PDF weather reports using OpenPDF  

### **5️⃣ Scheduled Email Delivery**
- Cron-based scheduling  
- Sends Thymeleaf-based HTML emails with PDF attachments  

---

# 🚀 Getting Started

## Prerequisites
- Java 17 or higher
- Maven  
- MySQL  
- Postman (for API testing)

---

## 📦 Installation

### 1. Clone the repository
```bash
git clone https://github.com/pritiranjan-01/global-weather-service-backend-springboot.git
cd global-weather-service-backend-springboot
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

global_weather_baseurl=external_microservice_url
app.base-url=http://localhost:8080
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

# 🚀 Deployment

### Backend Deployment (AWS Elastic Beanstalk)
The backend is deployed on **AWS Elastic Beanstalk**, which provides auto-scaling, load balancing, environment management, and CI/CD deployment pipelines for Spring Boot applications.

🔗 Live Backend API:  
https://springbootrestapi-env.eba-bugmbvqw.ap-south-1.elasticbeanstalk.com/swagger-ui/index.html

### Frontend Deployment (Vercel)
The frontend is deployed on **Vercel**, offering instant deployment, CDN optimization, and continuous updates whenever new commits are pushed.

🔗 Live Frontend App:  
https://globalweatherservice.vercel.app/

---

# 📘 API Documentation

### Weather APIs
``POST /weather`` - Create Weather Report  
``GET /weather`` -  All Weather Report  
``GET /weather/{id}`` - Id wise Weather Report  
``GET /weather/page?pageNumber=&pageSize=`` - Page wise Weather Report  
``GET /weather/count`` - Total Weather Report  
``PUT /weather/{id}`` - Update Weather Report  
``DELETE /weather/{id}`` - Delete Weather Report  

### Global Weather APIs
``GET /global`` - All global weather  
``GET /global/{city}`` - City wise global weather  

### Client APIs
``GET    /client/{email}`` - Fetch a Client by Email  
``PUT    /client/{email}`` - Update a Client by Email  
``DELETE /client/{email}`` - Delete a Client (Soft Delete)  
``POST   /client/register `` - Register a new Client  
``POST   /client/verify-otp`` - Verify Client Email OTP (Query Params: email, otp)  
``PATCH  /client/{email}/subscription`` - Update Client Subscription Type  
``PATCH  /client/{email}/status`` - Update Client Active/Inactive Status  
``GET    /client`` - Fetch all Clients  
``GET    /client/subscription-types`` - Get all available Subscription Types  
``GET    /client/count/{isActive}`` - Count Clients by Active/Inactive state  

### Audit Reports API 
``GET /audit/report`` - Audit Report  

### PDF Generation API
``GET /weather-report/download`` - Genrate PDF Weather Report  

# 📝 API Documentation

## Sample API Request

### Weather Report Creation
```
POST http://localhost:8080/weather  
{  
  "temp": 10,  
  "city": "bbsr",  
  "weathertype": "rainy"  
}  
```
### Client Registration
```
 POST http://localhost:8080/client/register  
{  
  "name": "example",  
  "email": "example@gmail.com",  
  "subscriptionType": 0,  
  "phoneNumber": "1234567890"  
}
```
### OTP Verification
```
http://localhost:8080/client/verify-otp?email=example@@gmail.com&otp=311532
```


# ⏰ Scheduled Tasks

| Subscription | Frequency | Action |
|--------------|-----------|--------|
| GO | Daily at 8 AM | Email PDF |
| PRO | Daily at 8 AM | Email PDF |
| MAX | 8 AM & 8 PM | Email PDF |

---

# 📊 Monitoring
Access Spring Boot Actuator endpoints:
- Health: `http://localhost:8080/actuator/health`  
- Metrics: `http://localhost:8080/actuator/metrics`  
- Info: `http://localhost:8080/actuator/info`  

# 🤝 Contributing
1. Fork the repo  
2. Create feature branch  
3. Commit changes  
4. Open Pull Request  

---

# 📄 License
MIT License
This project is licensed under the MIT License. See the [LICENSE](https://github.com/pritiranjan-01/global-weather-service-backend-springboot/blob/main/LICENSE) file for details.

---

# 👤 Author
**Pritiranjan Mohanty**  
GitHub: https://github.com/pritiranjan-01  
Email: pritiranjan.mohanty2003@gmail.com  

---

### ⭐ If you find this project helpful, please give it a star!
