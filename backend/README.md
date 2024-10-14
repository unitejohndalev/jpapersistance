# JPAPersistance - Spring Boot Backend

This project is a demo Spring Boot application that integrates security, database management and reporting. It includes advanced security features such as session management, JWT authentication, OAuth2 integration, password encryption, authorization annotations, and Cross-Origin Resource Sharing (CORS) configurations. Additionally, it provides JasperReports for exporting data and PostgreSQL database integration.


## Prerequisites

Before you begin, make sure you have the following installed and configured on your machine:

1. **Java 17**: Ensure that Java JDK 17 or higher is installed on your system.
   - You can verify the installation with the command:
     ```bash
     java -version
     ```
   - If Java is not installed, download and install it from [Oracle's official site](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

2. **Maven**: Ensure that Apache Maven is installed and configured.
   - Verify the installation by running:
     ```bash
     mvn -v
     ```
   - If Maven is not installed, you can follow the official installation guide [here](https://maven.apache.org/install.html).

3. **PostgreSQL**: Make sure PostgreSQL is installed and running, and that a database is created for this project.
   - You can verify the installation by running:
     ```bash
     psql --version
     ```
   - If you don't have PostgreSQL installed, download and install it from the [official site](https://www.postgresql.org/download/).
   - After installation, create a new database for the project:
     ```bash
     psql -U postgres
     CREATE DATABASE my_springboot_project;
     ```

Once these prerequisites are set up, proceed with the security configuration steps for the Spring Boot proj

## Tech Stack

- **Java Version**: 17
- **Spring Boot Version**: 3.3.3
- **Dependencies**:
  - **Spring Boot Data JPA**: `spring-boot-starter-data-jpa`
  - **Spring Boot Web Starter**: `spring-boot-starter-web`
  - **PostgreSQL Driver**: `postgresql`
  - **Spring Security**:
    - `spring-boot-starter-security`
    - `spring-security-core`
    - `spring-security-config`
    - `spring-security-web`
  - **JWT Token Authentication**: 
    - `jjwt-api: 0.11.5`
    - `jjwt-impl: 0.11.5`
    - `jjwt-jackson: 0.11.5`
  - **JasperReports**: `jasperreports: 7.0.0`, `jasperreports-pdf: 7.0.0`, `jasperreports-json: 7.0.0`, `jasperreports-fonts: 7.0.0`, `jasperreports-chart-themes: 7.0.0`
  - **Apache POI for Excel**: `poi-ooxml: 5.2.3`
  - **Apache PDFBox**: `pdfbox: 2.0.24`
  - **JFreeChart**: `jfreechart: 1.5.3`
  - **Swagger/OpenAPI Documentation**: `springdoc-openapi-starter-webmvc-ui: 2.6.0`
  - **Lombok**: `lombok`
  - **OAuth2 Client**: `spring-boot-starter-oauth2-client`

## Security Features

- **Session Management**: Secure session management is implemented for handling user sessions effectively.
- **JWT (JSON Web Token)**: Utilized for stateless authentication, ensuring secure and scalable authentication mechanisms.
- **OAuth2**: OAuth2 client support is integrated for managing authentication through external identity providers.
- **Password Encryption**: User passwords are securely hashed and stored using encryption mechanisms.
- **Authorization Annotations**: Role-based access control is enforced using Spring Security annotations (`@PreAuthorize`, `@Secured`, etc.).
- **Cross-Origin Resource Sharing (CORS)**: CORS settings are configured to control access from different origins to the backend.


