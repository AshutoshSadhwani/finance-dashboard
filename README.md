# 📊 Finance Dashboard Backend

A role-based financial transaction management and analytics backend built with **Spring Boot 3**, **PostgreSQL**, and **JWT authentication**. Exposes both REST and GraphQL APIs for querying financial data.

---

## 📑 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Environment Variables](#-environment-variables)
- [API Reference](#-api-reference)
    - [Authentication](#authentication)
    - [Transactions](#transactions)
    - [Dashboard](#dashboard)
    - [GraphQL](#graphql)
- [Data Models](#-data-models)
- [Roles & Permissions](#-roles--permissions)
- [Security](#-security)
- [Assumptions & Trade-offs](#-assumptions--trade-offs)

---

## ✨ Features

- **User registration & login** with JWT-based authentication
- **Role-based access control** — VIEWER, ANALYST, ADMIN
- **Transaction management** — create, read, update, delete, and filter income/expense records
- **Financial analytics dashboard** — summary totals, category breakdowns, monthly trends, and recent transactions
- **Dual API interface** — REST endpoints and a GraphQL endpoint with GraphiQL playground
- **Secure passwords** stored with BCrypt hashing

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security + JJWT 0.11.5 |
| Database | PostgreSQL (via Spring Data JPA / Hibernate) |
| GraphQL | Spring for GraphQL |
| Validation | Spring Validation |
| Build | Maven 3.9+ (Maven Wrapper included) |
| Utilities | Lombok |

---

## 📁 Project Structure

```
finance-dashboard/
├── src/
│   ├── main/
│   │   ├── java/com/finance/finance_dashboard/
│   │   │   ├── FinanceDashboardApplication.java   # Application entry point
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java            # Security & CORS configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java            # Register / Login endpoints
│   │   │   │   ├── TransactionController.java     # CRUD & filter for transactions
│   │   │   │   ├── DashboardController.java       # REST dashboard analytics
│   │   │   │   ├── DashboardGraphQLController.java# GraphQL dashboard resolver
│   │   │   │   └── UserController.java            # User management (ADMIN)
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── TransactionService.java
│   │   │   │   └── DashboardService.java
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   ├── Transaction.java
│   │   │   │   ├── Role.java         # Enum: VIEWER, ANALYST, ADMIN
│   │   │   │   ├── RecordType.java   # Enum: INCOME, EXPENSE
│   │   │   │   └── Status.java       # Enum: ACTIVE, INACTIVE
│   │   │   ├── Repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── TransactionRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── AuthRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── TransactionDTO.java
│   │   │   │   ├── DashboardDTO.java
│   │   │   │   ├── DashboardSummaryDTO.java
│   │   │   │   ├── CategorySummaryDTO.java
│   │   │   │   └── TrendDTO.java
│   │   │   ├── security/
│   │   │   │   ├── JwtFilter.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   └── util/
│   │   │       └── JwtUtil.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── graphql/
│   │           └── schema.graphqls
│   └── test/
│       └── java/com/finance/finance_dashboard/
│           └── FinanceDashboardApplicationTests.java
├── pom.xml
├── mvnw / mvnw.cmd
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **JDK 21** or later
- **PostgreSQL** (running locally or remotely)
- **Maven 3.9+** (or use the included `mvnw` wrapper)

### 1. Clone the repository

```bash
git clone https://github.com/AshutoshSadhwani/finance-dashboard.git
cd finance-dashboard
```

### 2. Configure the database

Create a PostgreSQL database (e.g. `finance_db`) and set the required environment variables (see [Environment Variables](#-environment-variables)).

### 3. Run the application

```bash
# Using Maven Wrapper (no Maven installation required)
./mvnw spring-boot:run          # Linux / macOS
mvnw.cmd spring-boot:run        # Windows

# Or, build a JAR and run it
./mvnw clean package -DskipTests
java -jar target/finance-dashboard-0.0.1-SNAPSHOT.jar
```

The server starts on **http://localhost:8080** by default.

### 4. Run tests

```bash
./mvnw test
```

---

## 🔑 Environment Variables

Set the following variables before starting the application (e.g. in a `.env` file or your shell):

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_URL` | JDBC URL for PostgreSQL | `jdbc:postgresql://localhost:5432/finance_db` |
| `DB_USERNAME` | PostgreSQL username | `postgres` |
| `DB_PASSWORD` | PostgreSQL password | `secret` |

---

## 📡 API Reference

All protected endpoints require the following header:

```
Authorization: Bearer <jwt_token>
```

---

### Authentication

#### Register a new user

```
POST /api/auth/register
```

**Request body**

```json
{
  "name": "Alice",
  "email": "alice@example.com",
  "password": "strongPassword123"
}
```

**Response** `200 OK`

```json
{
  "message": "User registered successfully"
}
```

> New users are automatically assigned the **VIEWER** role.

---

#### Login

```
POST /api/auth/login
```

**Request body**

```json
{
  "email": "alice@example.com",
  "password": "strongPassword123"
}
```

**Response** `200 OK`

```json
{
  "token": "<jwt_token>"
}
```

Use the returned token as `Bearer <token>` in subsequent requests.

---

### Transactions

> Requires **ANALYST** or **ADMIN** role.

#### Create a transaction

```
POST /transactions
```

**Request body**

```json
{
  "amount": 1500.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-06-01",
  "notes": "Monthly salary"
}
```

---

#### Get all transactions

```
GET /transactions
```

Returns all transactions belonging to the authenticated user.

---

#### Update a transaction

```
PUT /transactions/{id}
```

**Request body** — same structure as the create request.

---

#### Delete a transaction

```
DELETE /transactions/{id}
```

---

#### Filter transactions

```
GET /transactions/filter?type=INCOME&category=Salary&startDate=2024-01-01&endDate=2024-12-31
```

| Query parameter | Type | Description |
|-----------------|------|-------------|
| `type` | `INCOME` \| `EXPENSE` | Filter by record type |
| `category` | string | Filter by category name |
| `startDate` | `YYYY-MM-DD` | Start of date range |
| `endDate` | `YYYY-MM-DD` | End of date range |

---

### Dashboard

> Requires **VIEWER**, **ANALYST**, or **ADMIN** role.

#### Get dashboard summary

```
GET /api/dashboard
```

**Response** `200 OK`

```json
{
  "summary": {
    "totalIncome": 50000.00,
    "totalExpense": 32000.00,
    "netBalance": 18000.00
  },
  "categories": [
    { "category": "Salary", "total": 50000.00 },
    { "category": "Food", "total": 8000.00 }
  ],
  "trends": [
    { "period": "2024-05", "income": 10000.00, "expense": 6500.00 }
  ],
  "recent": [
    {
      "id": 42,
      "amount": 1500.00,
      "type": "INCOME",
      "category": "Salary",
      "date": "2024-06-01"
    }
  ]
}
```

---

### GraphQL

- **Endpoint:** `POST /graphql`
- **Playground (GraphiQL):** `GET /graphiql`

#### Schema

```graphql
type Query {
  dashboard: DashboardDTO
}

type DashboardDTO {
  summary: DashboardSummaryDTO
  categories: [CategorySummaryDTO]
  trends: [TrendDTO]
  recent: [TransactionDTO]
}

type DashboardSummaryDTO {
  totalIncome: Float
  totalExpense: Float
  netBalance: Float
}

type CategorySummaryDTO {
  category: String
  total: Float
}

type TrendDTO {
  period: String
  income: Float
  expense: Float
}

type TransactionDTO {
  id: ID
  amount: Float
  type: String
  category: String
  date: String
}
```

#### Example query

```graphql
query {
  dashboard {
    summary {
      totalIncome
      totalExpense
      netBalance
    }
    categories {
      category
      total
    }
    trends {
      period
      income
      expense
    }
    recent {
      id
      amount
      type
      category
      date
    }
  }
}
```

---

## 🗃 Data Models

### User

| Field | Type | Notes |
|-------|------|-------|
| `id` | Long | Primary key, auto-generated |
| `name` | String | Full name |
| `email` | String | Unique, used as username |
| `password` | String | BCrypt-hashed |
| `role` | Role | `VIEWER`, `ANALYST`, or `ADMIN` |
| `status` | Status | `ACTIVE` or `INACTIVE` |

### Transaction

| Field | Type | Notes |
|-------|------|-------|
| `id` | Long | Primary key, auto-generated |
| `amount` | Double | Monetary amount |
| `type` | RecordType | `INCOME` or `EXPENSE` |
| `category` | String | e.g. Salary, Food, Rent |
| `date` | LocalDate | Transaction date |
| `notes` | String | Optional description |
| `user` | User | Many-to-one relationship |

---

## 🔐 Roles & Permissions

| Endpoint pattern | VIEWER | ANALYST | ADMIN |
|-----------------|:------:|:-------:|:-----:|
| `/api/auth/**` | ✅ public | ✅ public | ✅ public |
| `/graphql`, `/graphiql` | ✅ public | ✅ public | ✅ public |
| `/api/dashboard/**` | ✅ | ✅ | ✅ |
| `/transactions/**` | ❌ | ✅ | ✅ |
| `/api/users/**` | ❌ | ❌ | ✅ |

---

## 🔒 Security

- **Stateless JWT authentication** — tokens are signed with HMAC-SHA256 and expire after **1 hour**.
- **BCrypt password hashing** — raw passwords are never stored.
- **JWT filter** (`JwtFilter`) validates the token on every request before reaching any controller.
- **CSRF disabled** — appropriate for a stateless REST/GraphQL API.
- Public routes (auth, GraphQL playground) are explicitly whitelisted in `SecurityConfig`.

> ⚠️ **Production note:** The JWT secret key is currently hardcoded. Move it to an environment variable (e.g. `JWT_SECRET`) before deploying to production.

---

## ✅ Assumptions & Trade-offs

### Assumptions

- All newly registered users receive the **VIEWER** role; role promotion is an ADMIN action.
- The system operates as a **single backend service** — no microservices architecture.
- JPA `ddl-auto=update` auto-manages the schema; a migration tool (e.g. Flyway) is recommended for production.

### Trade-offs

| Decision | Rationale |
|----------|-----------|
| **JWT over session-based auth** | Stateless design scales horizontally without a shared session store |
| **Default VIEWER role on registration** | Prevents privilege escalation; users must be explicitly promoted |
| **Hardcoded JWT secret** | Simplifies initial development; should be externalised via environment variable in production |
| **No refresh tokens** | Reduces initial complexity; recommended to add for production-grade security |
| **GraphQL + REST** | REST for simple CRUD operations; GraphQL for flexible dashboard queries |