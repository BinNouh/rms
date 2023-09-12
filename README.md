## RMS Project

This project consists of a frontend built with Angular, a backend built with Spring Boot, and authentication provided by Keycloak.

### Prerequisites

- Java 17
- Spring Boot 3.0
- Angular 16.2
- Docker (if you want to containerize the Spring Boot app)

### Running the Project

#### 1. Keycloak

Navigate to Keycloak directory and run:

### bash
`.keycloak/bin> kc.bat start-dev`

#### This will start Keycloak on `port 8080`.

### 2. backend (Spring Boot)

Navigate to the backend directory:

### bash
`cd backend`

`./mvnw spring-boot:run`

#### This will start the Spring Boot application on `port 8081`.

### 3. Frontend (Angular)

Navigate to frontend directory and run:

### bash
`cd frontend`

`npm install`

`ng serve`

#### This will start the Angular application on `port 4200`.

### 4. Using Docker (Optional)     ((NOT YET INITIATED))

#### Make sure you are in docker-compose.yml file dir, then run this command:

`docker-compose up -d`

#### Now, visit the application at http://localhost:4200


### Project Describition:



### Project Structure:



### Project Flow:


