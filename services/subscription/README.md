# Subscription Service

This service manages user subscriptions, subscription plans, and subscription analytics.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 14 or higher (running on port 5433)
- Redis 6.0 or higher
- Kafka 3.0 or higher
- Spring Cloud Config Server (optional)

## Configuration

The service uses the following configuration files:

- `application.yml`: Main configuration file
- `bootstrap.yml`: Spring Cloud Config configuration (optional)
- `schema.sql`: Database schema and initial data

### Required Environment Variables

- `SPRING_DATASOURCE_URL`: PostgreSQL database URL (default: jdbc:postgresql://localhost:5433/subscription_db)
- `SPRING_DATASOURCE_USERNAME`: PostgreSQL username (default: postgres)
- `SPRING_DATASOURCE_PASSWORD`: PostgreSQL password (default: postgres)
- `JWT_SECRET`: Secret key for JWT token generation
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: Eureka server URL (if using service discovery)

## Building the Service

```bash
mvn clean install
```

## Running the Service

1. Start the required infrastructure services:
   - PostgreSQL (on port 5433)
   - Redis
   - Kafka
   - Eureka Server (optional)
   - Config Server (optional)

2. Run the service:
   ```bash
   mvn spring-boot:run
   ```

The service will start on port 8084 by default.

## API Documentation

Once the service is running, you can access the Swagger UI at:
```
http://localhost:8084/swagger-ui.html
```

## Features

- Subscription plan management
- User subscription management
- Subscription analytics
- JWT-based authentication
- Kafka integration for event-driven architecture
- Redis caching
- OpenAPI documentation

## Security

The service uses JWT for authentication. Protected endpoints require a valid JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

## Monitoring

The service exposes actuator endpoints for monitoring:
```
http://localhost:8084/actuator
```

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Cloud
- Spring Kafka
- Spring Redis
- PostgreSQL
- Lombok
- OpenAPI
- JWT 