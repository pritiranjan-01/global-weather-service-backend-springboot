# SpringBoot_REST

Short description

A demo Spring Boot REST API that manages weather records, exposes international weather lookups, supports client registration with email OTP, and includes production-ready features such as OpenAPI docs, Actuator, caching, AOP, async execution, and JPA persistence to MySQL.

Features

- CRUD operations for WeatherReport (create, read, update, delete)
- Pagination for weather listing
- International/global weather read-only endpoints (in-memory store)
- Client registration with email OTP and OTP verification
- Uniform response wrapper (`ResponseStructure<T>`) and mappers to standardize responses
- Health/heartbeat endpoint
- OpenAPI/Swagger UI (springdoc)
- Spring Boot Actuator endpoints enabled
- JPA (MySQL) persistence for WeatherReport
- Caching, AOP, and asynchronous support enabled
- Validation on request DTOs

Tech stack

- Java 17
- Spring Boot 3.5.7
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-mail
- spring-boot-starter-validation
- spring-boot-starter-actuator
- spring-boot-starter-aop
- springdoc-openapi-starter-webmvc-ui
- Lombok
- MySQL (mysql-connector-j)

Quick start

1. Build and run with the included Maven wrapper (Windows cmd):

```cmd
cd "C:\Users\priti\eclipse-workspace\SpringBoot_REST"
mvnw.cmd spring-boot:run
```

Or build the jar and run:

```cmd
cd "C:\Users\priti\eclipse-workspace\SpringBoot_REST"
mvnw.cmd -DskipTests package
java -jar target\SpringBoot_REST-0.0.1-SNAPSHOT.jar
```

Default port

- The application default server port is 8080 (unless overridden in `application.properties` or via environment).

Important configuration

The project uses `src/main/resources/application.properties`. Important properties found in the file:

- Database (MySQL):
  - `spring.datasource.url=jdbc:mysql://localhost:3306/rest`
  - `spring.datasource.username` and `spring.datasource.password`
  - `spring.jpa.hibernate.ddl-auto=update`

- Actuator:
  - `management.endpoints.web.exposure.include=*` (exposes all actuator endpoints)

- Mail (SMTP):
  - `spring.mail.host`, `spring.mail.port`, `spring.mail.username`, `spring.mail.password` are present in the file.

Security note: DB credentials and SMTP username/password appear in plaintext in `application.properties`. For non-local/dev usage move these secrets into environment variables or a secrets manager.

Environment variables (recommended) example (Windows cmd):

```cmd
set SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/rest
set SPRING_DATASOURCE_USERNAME=root
set SPRING_DATASOURCE_PASSWORD=your_db_password
set SPRING_MAIL_USERNAME=your@mail.com
set SPRING_MAIL_PASSWORD=your_mail_password
```

Key endpoints (summary)

- Health
  - GET /api/health — quick heartbeat

- Weather (persistent, JPA-backed)
  - POST /weather — create a weather report
    - Example body (JSON):
      {
        "city": "Bengaluru",
        "temp": 30,
        "weatherType": "Sunny"
      }
  - GET /weather — return all weather reports
  - GET /weather/{id} — get a weather report by id
  - PUT /weather/{id} — update a weather report (body depends on `WeatherReportUpdateRequestDTO`)
  - DELETE /weather/{id} — delete by id
  - GET /weather/page?pageNumber=0&pageSize=25 — paged results

- International (in-memory)
  - GET /global/{city} — get international weather for `city`
  - GET /global — get all international weather (Map<String, WeatherDTO>)

- Client (OTP)
  - GET /client/getAllClientSubscriptionsTypes — returns subscription types
  - POST /client/createClient — registers a client and sends OTP via email
    - Example body (JSON):
      {
        "name": "Alice",
        "email": "alice@example.com",
        "subscriptionType": "0"
      }
  - POST /client/verifyClientOtp?email=alice@example.com&otp=123456 — verify OTP

OpenAPI / Swagger

- After startup, Swagger UI is available (common paths):
  - http://localhost:8080/swagger-ui/index.html
  - or http://localhost:8080/swagger-ui.html

Actuator

- All actuator endpoints are exposed by default per `application.properties`.
  - Example: http://localhost:8080/actuator/health

