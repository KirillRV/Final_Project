
# Online Casino API

Comprehensive Online Casino API enabling users to place bets, manage games, track transactions, and ensure secure authentication and authorization.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Setup](#setup)
- [Testing](#testing)

---

## Features
- **User Management:** Registration, authentication (JWT), and role-based access control for players and admins.
- **Game Management:** CRUD operations for casino games with status management (active/inactive).
- **Betting System:** Place bets with validations (min/max bet, sufficient funds).
- **Balance Management:** Track and update user balances with transaction history.
- **Exception Handling:** Unified global exception handler for consistent error responses.
- **Security:** JWT-based authentication with token validation and refresh.
- **Admin Operations:** Management of users, games, bets, and financial reports.
- **Logging & Monitoring:** Integrated logging for error tracking and system monitoring.

---

## Technologies
- **Backend:** Java 21, Spring Boot 3.x
- **Database:** PostgreSQL
- **Security:** JWT (JSON Web Tokens)
- **Build Tool:** Maven
- **API Documentation:** Swagger UI available
- **Testing:** JUnit and Postman collections

---

## Database Schema
Key tables include:

| Table Name     | Description                                 |
|----------------|---------------------------------------------|
| `users`        | Stores user information and credentials     |
| `games`        | Contains casino game details and statuses   |
| `bets`         | Records individual user bets                 |
| `transactions` | Logs all financial operations                |
| `roles`        | User roles and permissions                    |

*See the full DDL scripts and ER diagrams in the repository.*

---

## API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login (returns JWT)
- `GET /api/users/{id}` - Get user info by ID
- `PUT /api/users/{id}` - Update user details
- `DELETE /api/users/{id}` - Delete user (admin only)

### Game Management
- `GET /api/games` - List all games
- `GET /api/games/{id}` - Get game details by ID
- `POST /api/games` - Create a new game (admin only)
- `PUT /api/games/{id}` - Update game info (admin only)
- `DELETE /api/games/{id}` - Delete game (admin only)

### Betting Operations
- `POST /api/bets` - Place a bet
- `GET /api/bets/user/{userId}` - Get all bets by user
- `GET /api/bets/{id}` - Get bet details by ID

### Transaction Management
- `GET /api/transactions/user/{userId}` - Get user transaction history
- `POST /api/transactions/deposit` - Deposit funds
- `POST /api/transactions/withdraw` - Withdraw funds

### Authentication
- `POST /api/auth/login` - Authenticate user and get JWT token
- `POST /api/auth/refresh` - Refresh JWT token

---

## Setup

1. Clone the repository:

```bash
git clone https://github.com/KirillRV/Final_Project.git
cd Final_Project
```

2. Configure your PostgreSQL database and update `application.properties` with your database credentials.

3. Build the project using Maven:

```bash
mvn clean install
```

4. Run the Spring Boot application:

```bash
mvn spring-boot:run
```

5. Access API documentation with Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testing

- Use the provided Postman collection (available in the repo) to test all API endpoints.
- Default test users and admin credentials are seeded on startup.

---

If you have any questions or want to contribute, feel free to open issues or pull requests in the repository.
