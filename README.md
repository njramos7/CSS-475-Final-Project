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
sales-analytics/
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

### 1. AWS RDS — Create a PostgreSQL Database

1. Go to [AWS Console](https://console.aws.amazon.com/) → **RDS** → **Create database**
2. Configure:
   - Engine: **PostgreSQL**
   - Template: **Free tier**
   - DB identifier: `salesanalytics`
   - Master username: `postgres`
   - Set a password and save it
3. Under **Connectivity** → set **Public access = Yes**
4. Under **VPC security group** → add an inbound rule:
   - Type: `PostgreSQL` | Port: `5432` | Source: `My IP`
5. Create the database and wait ~5 minutes for it to become **Available**
6. Copy the **Endpoint** from the RDS console — you'll need it next

---

### 2. Load the Database

Connect via `psql`, DBeaver, or pgAdmin:

```bash
psql -h YOUR_RDS_ENDPOINT -U postgres -p 5432 -d postgres
```

Then create and populate the database:

```sql
CREATE DATABASE salesanalytics;
\c salesanalytics
\i sql/01_create_schema.sql
\i sql/02_seed_data.sql
```

---

### 3. Configure the Database Connection

Open `src/main/java/com/salesanalytics/util/DBConnection.java` and paste your RDS endpoint:

```java
private static final String DB_HOST = "your-endpoint.rds.amazonaws.com";
```

Alternatively, use environment variables (recommended — keeps credentials out of code):

```bash
export DB_HOST=your-endpoint.rds.amazonaws.com
export DB_USER=postgres
export DB_PASS=your_password
```

> ⚠️ **Never commit your password or endpoint to GitHub.** The `.gitignore` already excludes secrets files — keep it that way.

---

### 4. Download the PostgreSQL JDBC Driver

```bash
mkdir lib
curl -L -o lib/postgresql-42.7.3.jar \
  https://jdbc.postgresql.org/download/postgresql-42.7.3.jar
```

Or download manually from [jdbc.postgresql.org](https://jdbc.postgresql.org/download/) and place the `.jar` in `lib/`.

---

### 5. Compile & Run

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
                └──< Quotas      └──< Sales
                └──< Interactions >── InteractionType
```

**Core tables:** `SalesReps`, `Customers`, `Opportunities`, `Interactions`, `Sales`, `Quotas`  
**Lookup tables:** `OpportunityStage`, `OpportunityStatus`, `InteractionType`

---

## 👥 Team Contributions

| Name | Components | Max Points |
|------|-----------|-----------|
| **Vito** | DB Creation, `CalculateRepPerformance` | 30 + 60 |
| **Ryan** | Driver Program, `CloseOpportunity`, `GetCustomerHistory` | 30 + 60 + 50 |
| **Tenzin** | `CreateSalesRep`, `CreateCustomer`, `CreateOpportunity` | 40 + 40 + 40 |
| **Brian** | `LogInteraction`, `UpdateOpportunity` | 40 + 40 |
| **Joshua** | `ListOpportunitiesForRep`, `GetPipelineForecast` | 80 + 60 |

---

## 🛠️ Tech Stack

- **Language:** Java 17+
- **Database:** PostgreSQL 15 on AWS RDS
- **JDBC Driver:** postgresql-42.7.3
- **Schema:** Relational, 3NF, no derived attributes
- **Version Control:** Git / GitHub
