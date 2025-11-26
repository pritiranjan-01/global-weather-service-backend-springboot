# Client Weather Management REST API

A backend Spring Boot application that manages weather reports and client subscriptions. Registered clients receive weather information by email according to their subscription plan. This repository contains the REST API, persistence for weather reports, client registration (with email OTP verification), and an in-memory international weather store (fetched from another microservice).

---

## Table of Contents

- Project overview
- Current status (implemented)
- Subscription plans & delivery policy
- Tech stack
- Requirements
- Quick start
- Configuration
- Key endpoints (summary + examples)
- OpenAPI / Swagger
- Actuator / Health
- Upcoming features (roadmap)
- Testing tips
- Contributing
- Security notes
- License & contact

---

## Project overview

This service allows:
- Persisting and managing WeatherReport records (create, read, update, delete).
- Registering clients who choose a subscription type (GO, PRO, MAX).
- Sending email OTP to verify client emails during registration.
- Fetching international weather from an external microservice and exposing it via read-only endpoints.
- Providing a uniform API response wrapper for consistent client responses.

Planned: generate PDF weather reports and send scheduled emails to subscribed clients based on their subscription plan.

---

## Current status (implemented)

Implemented features:
- WeatherReport CRUD (JPA / MySQL).
- Pagination for listing weather reports.
- Client registration endpoints including email OTP sending and OTP verification.
- International/global weather endpoints (read-only) — data fetched from another microservice.
- Uniform response wrapper `ResponseStructure<T>` used by controllers.
- Health/heartbeat endpoint.
- OpenAPI/Swagger UI and Spring Boot Actuator enabled.
- DTO validation and basic exception handling.
- Project build with Maven (wrapper included).

Not yet implemented (planned):
- Generating weather reports as PDF files.
- Scheduled email notifications to clients with their weather reports.
- Production-grade scheduling and retry logic for email delivery.

---

## Subscription plans & delivery policy

When clients register they must choose one of the following subscription types:

- GO
  - Coverage: selected local cities (application-configured).
  - Delivery frequency: once per day.
  - Delivery format (planned): PDF attachment in email.

- PRO
  - Coverage: local cities + selected international cities.
  - Delivery frequency: once per day.
  - Delivery format (planned): PDF attachment in email.

- MAX
  - Coverage: same as PRO (local + international).
  - Delivery frequency: twice per day (e.g., morning and evening).
  - Delivery format (planned): PDF attachment in email.

Default delivery schedule (suggested):
- GO / PRO: daily at 08:00 AM (configurable).
- MAX: daily at 08:00 AM and 20:00 PM (configurable).

Note: Scheduling and email delivery are planned features and will be added in an upcoming release. The README documents the intended behavior so the team and contributors have clear requirements.

---

## Tech stack

- Java 17
- Spring Boot 3.5.7
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-mail
  - spring-boot-starter-validation
  - spring-boot-starter-actuator
  - spring-boot-starter-aop
- springdoc-openapi-starter-webmvc-ui (Swagger)
- Lombok
- MySQL (mysql-connector-j)
- Maven (wrapper included)

See `pom.xml` for exact dependency versions.

---

## Requirements

- JDK 17
- Maven (or use the included `mvnw` / `mvnw.cmd`)
- MySQL (or modify datasource to use another database)
- SMTP account (for email OTP; for testing, use a test SMTP server like MailHog or Mailtrap)

---

## Quick start

Clone and run:

```bash
git clone https://github.com/pritiranjan-01/Client-Weather-Management-REST-API.git
cd Client-Weather-Management-REST-API
# Linux / macOS
./mvnw spring-boot:run
# Windows
mvnw.cmd spring-boot:run
```

Or build and run the jar:

```bash
./mvnw -DskipTests package
java -jar target/SpringBoot_REST-0.0.1-SNAPSHOT.jar
```

Default port: 8080 (unless overridden).

---

## Configuration

Important properties are in `src/main/resources/application.properties`. Recommended: use environment variables for secrets.

Database
```
spring.datasource.url=jdbc:mysql://localhost:3306/rest
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

Mail (used for OTPs)
```
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_smtp_user
spring.mail.password=your_smtp_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Actuator
```
management.endpoints.web.exposure.include=*
```

Suggested environment variables (example):
```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/rest
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=secret
export SPRING_MAIL_USERNAME=mailer@example.com
export SPRING_MAIL_PASSWORD=mailerpassword
```

Scheduling configuration (planned)
- When scheduling is implemented, the cron expressions / schedules should be configurable via properties such as:
  - report.schedule.go
  - report.schedule.pro
  - report.schedule.max

These keys are currently conceptual — the implementation will read these to schedule report generation and email sending.

---

## Key endpoints (summary)

Health
- GET /api/health — quick heartbeat

Weather (persistent)
- POST /weather — create a weather report
  - Example:
    {
      "city": "Bengaluru",
      "temp": 30,
      "weatherType": "Sunny"
    }
- GET /weather — list all weather reports
- GET /weather/{id} — get a report by id
- PUT /weather/{id} — update an existing report
- DELETE /weather/{id} — delete a report
- GET /weather/page?pageNumber=0&pageSize=25 — paginated results

International (in-memory / external microservice-backed)
- GET /global/{city} — get international weather for `city`
- GET /global — get all international weather (Map<String, WeatherDTO>)

Client / Subscription / OTP
- GET /client/subscription-types — returns available subscription types (GO, PRO, MAX)
- POST /client/register — register a client and send OTP via email
- POST /client/verify-otp?email=alice@example.com&otp=123456 — verify OTP
- GET /client - returns all Clients available in database
- PUT /?email=alice@example.com - Update the Client details.
- DELETE /?email=alice@example.com - Delete the Client details (Soft Delete)

Responses use a uniform response wrapper: ResponseStructure<T> (contains status, message, data).

---

## OpenAPI / Swagger

Interactive docs are available after startup:
- http://localhost:8080/swagger-ui/index.html

Use Swagger UI to explore endpoints and example requests/responses.

---

## Actuator

Actuator endpoints are exposed by default (per `application.properties`), e.g.:
- http://localhost:8080/actuator/health

---

## Upcoming features (roadmap)

Planned for upcoming releases:
- PDF generation of weather reports (per-client, containing the cities they subscribed to).
- Scheduled email delivery of weather reports according to subscription plan (GO/PRO/MAX).
  - Email content will include summary + PDF as attachment.
  - Retry and failure handling for email delivery.
- Admin endpoints to preview and trigger scheduled sends (for testing).
- Improved security for public endpoints and actuator endpoints (authentication/authorization).
- Optionally support templated emails and localization.

---

## Testing tips

- For OTP/email testing, run a local SMTP testing tool such as MailHog, Mailtrap, or Papercut and point `spring.mail.*` to it.
- Use Swagger UI for manual API testing.
- Create a client via `/client/createClient` and verify with `/client/verifyClientOtp`.
- Add WeatherReport entries and test the pagination and CRUD endpoints.

---

## Contributing

Contributions are welcome. Suggested workflow:
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes and open a Pull Request describing the change and the reason.
4. Ensure secrets are not committed.

If you plan to implement the scheduled email & PDF feature, please open an issue describing your design (cron vs scheduler, whether to use queueing, how to store generated PDFs, etc.) so we can coordinate.

---

## License & contact

Repository owner: pritiranjan-01

Acknowledgements
- Built using Spring Boot and Spring ecosystem libraries (see `pom.xml` for details).
- International weather data is retrieved from a separate microservice — thank you to the microservice owners for the integration.
