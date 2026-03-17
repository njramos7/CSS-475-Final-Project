# 📊 Sales Analytics
### CSS 475 Final Project — Team: Chill Guys

> A Java-based sales management system backed by PostgreSQL on AWS RDS.  
> Replaces error-prone spreadsheets with a centralized relational database supporting pipeline tracking, rep performance analytics, and deal forecasting.

**Team Members:** Joshua Ramos · Ryan Doan · Tenzin Tashi · Vito Mkrtychyan · Brian Nguyen

---

## 📌 Business Problem

Sales teams relying on spreadsheets face inconsistent data, missing history, unclear pipeline status, and poor visibility into rep performance. This system provides a centralized relational database to track sales reps, customers, opportunities, interactions, and outcomes — with full analytics support.

---

## 🗂️ Project Structure

```
CSS-475-Final-Project/
├── sql/
│   ├── 01_create_schema.sql        # All tables, FKs, indexes
│   └── 02_seed_data.sql            # Sample data for testing
├── lib/
│   └── postgresql-42.7.3.jar       # JDBC driver (download separately — see setup)
├── src/main/java/com/salesanalytics/
│   ├── util/
│   │   └── DBConnection.java       # AWS RDS connection config
│   ├── server/                     # DB query logic (Server_<ApiName>.java)
│   │   ├── Server_ListOpportunitiesForRep.java
│   │   ├── Server_GetPipelineForecast.java
│   │   └── ... (teammates' files)
│   ├── client/                     # User-facing I/O (Client_<ApiName>.java)
│   │   ├── Client_ListOpportunitiesForRep.java
│   │   ├── Client_GetPipelineForecast.java
│   │   └── ... (teammates' files)
│   └── driver/
│       └── Driver.java             # Main menu entry point
├── compile_and_run.sh              # One-command build & run (Mac/Linux)
├── .gitignore
└── README.md
```

---

## ⚙️ Setup & Installation

### 1. AWS RDS — Database is already set up

The PostgreSQL database is live on AWS RDS. Use these connection details:

- **Host:** `salesanalytics.cn6sqqsw4jj5.us-east-2.rds.amazonaws.com`
- **Port:** `5432`
- **Database:** `salesanalytics`
- **User:** `postgres`
- **Password:** XXXX

> The schema and seed data are already loaded. You do NOT need to run the SQL files unless starting fresh.

---

### 2. Configure the Database Connection

Open `src/main/java/com/salesanalytics/util/DBConnection.java` and make sure the endpoint is set:

```java
private static final String DB_HOST = "salesanalytics.cn6sqqsw4jj5.us-east-2.rds.amazonaws.com";
```

Also set your password:
```java
private static final String DB_PASS = System.getenv().getOrDefault("DB_PASS", "your_password_here");
```

> ⚠️ Never commit your password to GitHub.

---

### 3. Download the PostgreSQL JDBC Driver

```bash
mkdir lib
curl -L -o lib/postgresql-42.7.3.jar \
  https://jdbc.postgresql.org/download/postgresql-42.7.3.jar
```

Or download manually from [jdbc.postgresql.org](https://jdbc.postgresql.org/download/) and place the `.jar` in `lib/`.

---

### 4. Compile & Run

**Mac / Linux:**
```bash
chmod +x compile_and_run.sh
./compile_and_run.sh
```

**Windows:**
```cmd
mkdir out
javac -cp lib\postgresql-42.7.3.jar -d out src\main\java\com\salesanalytics\util\*.java src\main\java\com\salesanalytics\server\*.java src\main\java\com\salesanalytics\client\*.java src\main\java\com\salesanalytics\driver\*.java
java -cp out;lib\postgresql-42.7.3.jar com.salesanalytics.driver.Driver
```

---

### 5. Adding Your APIs to the Project

1. Add your `Client_<ApiName>.java` to `src/main/java/com/salesanalytics/client/`
2. Add your `Server_<ApiName>.java` to `src/main/java/com/salesanalytics/server/`
3. Open `Driver.java` and uncomment your import and your case number
4. Run `compile_and_run.sh` to test

---

## 🧰 API Reference

### Sales Rep Management
| API | Type | Author |
|-----|------|--------|
| `CreateSalesRep` | CRUD Single | Tenzin |
| `UpdateSalesRep` | CRUD Single | Tenzin |
| `ListSalesReps` | List | Tenzin |
| `GetSalesRepDetails` | Detail | Tenzin |

### Customer Management
| API | Type | Author |
|-----|------|--------|
| `CreateCustomer` | CRUD Single | Tenzin |
| `UpdateCustomer` | CRUD Single | Tenzin |
| `GetCustomerHistory` | Detail | Ryan |

### Opportunity Management
| API | Type | Author |
|-----|------|--------|
| `CreateOpportunity` | CRUD Single | Tenzin |
| `UpdateOpportunity` | CRUD Single | Brian |
| `ListOpportunitiesForRep` | Large List | Joshua |
| `CloseOpportunity` | CRUD Multi | Ryan |

### Interaction & Analytics
| API | Type | Author |
|-----|------|--------|
| `LogInteraction` | CRUD Single | Brian |
| `GetPipelineForecast` | Complex Query | Joshua |
| `CalculateRepPerformance` | Complex Query | Vito |

---

## 🗄️ Database Schema

9 tables with enforced foreign keys and no derived data storage (3NF compliant):

```
SalesReps ──< Customers ──< Opportunities >── OpportunityStage
                │                │             OpportunityStatus
                └──< Quotas      └──< Interactions >── InteractionType
```

**Core tables:** `SalesReps`, `Customers`, `Opportunities`, `Interactions`, `Quotas`  
**Lookup tables:** `OpportunityStage`, `OpportunityStatus`, `InteractionType`

---

## 👥 Team Contributions

| Name | Components | Max Points |
|------|-----------|-----------|
| **Vito** | DB Creation, `CalculateRepPerformance` | 30 + 60 |
| **Ryan** | `CloseOpportunity`, `GetCustomerHistory` | 60 + 50 |
| **Tenzin** | `CreateSalesRep`, `CreateCustomer`, `CreateOpportunity` | 40 + 40 + 40 |
| **Brian** | `LogInteraction`, `UpdateOpportunity` | 40 + 40 |
| **Joshua** | Driver Program, `ListOpportunitiesForRep`, `GetPipelineForecast` | 30 + 80 + 60 |

---

## 🛠️ Tech Stack

- **Language:** Java 17+
- **Database:** PostgreSQL 15 on AWS RDS
- **JDBC Driver:** postgresql-42.7.3
- **Schema:** Relational, 3NF, no derived attributes
- **Version Control:** Git / GitHub
