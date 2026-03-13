# CSS-475-Final-Project
# Sales Analytics — CSS 475 Final Project
**Team: Chill Guys**
Joshua Ramos · Ryan Doan · Tenzin Tashi · Vito Mkrtychyan · Brian Nguyen

---

## Project Structure

```
sales-analytics/
├── sql/
│   ├── 01_create_schema.sql     # Creates all tables
│   └── 02_seed_data.sql         # Loads sample data
├── lib/
│   └── postgresql-42.7.3.jar    # JDBC driver (download separately)
├── src/main/java/com/salesanalytics/
│   ├── util/
│   │   └── DBConnection.java    # AWS RDS connection
│   ├── server/
│   │   ├── Server_ListOpportunitiesForRep.java   ← Joshua
│   │   └── Server_GetPipelineForecast.java       ← Joshua
│   ├── client/
│   │   ├── Client_ListOpportunitiesForRep.java   ← Joshua
│   │   └── Client_GetPipelineForecast.java       ← Joshua
│   └── driver/
│       └── Driver.java          # Main menu
├── compile_and_run.sh
└── README.md
```

---

## Step 1 — Set Up AWS RDS PostgreSQL

1. Log in to [AWS Console](https://console.aws.amazon.com/) → RDS → Create Database
2. Choose:
   - Engine: **PostgreSQL**
   - Template: **Free tier**
   - DB instance identifier: `salesanalytics`
   - Master username: `postgres`
   - Master password: (set one and remember it)
3. Under **Connectivity**, set **Public access = Yes**
4. Under **VPC security group**, add an inbound rule:
   - Type: PostgreSQL | Port: 5432 | Source: **My IP**
5. Click **Create database** and wait ~5 min for it to become Available
6. Copy the **Endpoint** from the RDS console (e.g. `salesanalytics.abc123.us-west-2.rds.amazonaws.com`)

---

## Step 2 — Create the Database

Connect with psql (or DBeaver/pgAdmin):

```bash
psql -h YOUR_ENDPOINT -U postgres -p 5432 -d postgres
```

Then run:

```sql
CREATE DATABASE salesanalytics;
\c salesanalytics
\i sql/01_create_schema.sql
\i sql/02_seed_data.sql
```

---

## Step 3 — Configure DBConnection.java

Open `src/main/java/com/salesanalytics/util/DBConnection.java` and set your endpoint:

```java
private static final String DB_HOST = "YOUR_RDS_ENDPOINT_HERE";
```

Or set environment variables (more secure):

```bash
export DB_HOST=salesanalytics.abc123.us-west-2.rds.amazonaws.com
export DB_USER=postgres
export DB_PASS=your_password
```

---

## Step 4 — Download PostgreSQL JDBC Driver

```bash
mkdir lib
# Download from https://jdbc.postgresql.org/download/
# Place as: lib/postgresql-42.7.3.jar
```

Or with curl:
```bash
curl -L -o lib/postgresql-42.7.3.jar \
  https://jdbc.postgresql.org/download/postgresql-42.7.3.jar
```

---

## Step 5 — Compile and Run

```bash
chmod +x compile_and_run.sh
./compile_and_run.sh
```

Windows:
```cmd
mkdir out
javac -cp lib\postgresql-42.7.3.jar -d out src\main\java\com\salesanalytics\**\*.java
java -cp out;lib\postgresql-42.7.3.jar com.salesanalytics.driver.Driver
```

---

## Step 6 — Push to GitHub

```bash
git init
git add .
git commit -m "Initial commit - Sales Analytics CSS 475"
git remote add origin https://github.com/YOUR_USERNAME/sales-analytics.git
git push -u origin main
```

**Important:** Add a `.gitignore` so you don't commit credentials:

```
# .gitignore
lib/
out/
*.class
```

---

## API Overview (Joshua's APIs)

### 8. ListOpportunitiesForRep (Large List — 80 pts)
- **Input:** rep email
- **Output:** all opportunities for that rep with company, stage, status, deal amount, close date
- **Files:** `Server_ListOpportunitiesForRep.java`, `Client_ListOpportunitiesForRep.java`

### 9. GetPipelineForecast (Complex Query — 60 pts)
- **Input:** rep email (optional — leave blank for all reps)
- **Output:** total open pipeline value + breakdown by stage
- **Files:** `Server_GetPipelineForecast.java`, `Client_GetPipelineForecast.java`

---

## Who Did What

| Name    | Component                                    | Points |
|---------|----------------------------------------------|--------|
| Vito    | DB Creation + CalculateRepPerformance        | 30+60  |
| Ryan    | Driver + CloseOpportunity + GetCustomerHistory | 30+60+50 |
| Tenzin  | CreateSalesRep + CreateCustomer + CreateOpportunity | 40×3 |
| Brian   | LogInteraction + UpdateOpportunity           | 40+40  |
| Joshua  | ListOpportunitiesForRep + GetPipelineForecast | 80+60  |
