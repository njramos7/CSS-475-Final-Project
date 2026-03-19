Sales Analytics
CSS 475 Final Project
Team: Chill Guys
Joshua Ramos, Ryan Doan, Tenzin Tashi, Vito Mkrtychyan, Brian Nguyen

A sales management (Java) system backed by PostgreSQL on AWS RDS.  
Replaces error-prone spreadsheets with a centralized relational database supporting pipeline tracking, rep performance analytics, and deal forecasting.

-------------------------------------------------
Business Problem

Sales teams relying on spreadsheets face inconsistent data, missing history, unclear pipeline status, and poor visibility into rep performance. This system provides a centralized relational database to track sales reps, customers, opportunities, interactions, and outcomes.

----------------------------------------
 Project Structure

CSS-475-Final-Project/
sql/
  01_create_schema.sql        - database creation table create, etc....
  02_seed_data.sql            - Sample data for testing (did not hand create this)

lib/ (we put gitignore as the file is too big  )
  postgresql-42.7.3.jar       - JDBC driver (download separately — see setup)

src/main/java/com/salesanalytics/
  util/
    DBConnection.java          - AWS RDS connection 
  server/                      - DB query logic - Server_<ApiName>.java
    Server_Name.java
      (many more proj files)
  client/                      - User-facing Input and Output - Client_<ApiName>.java
    Client_name.java
     (many more proj files)
  driver/
    Driver.java               - menu or what the user seees entry point

compile_and_run.sh            - build & run (Mac/Linux) 
.gitignore
README.txt

------------------------------------------------------------

Quick Setup for proj

1 In Database Connection (is already done)

..../com/salesanalytics/util/DBConnection.java
you can see the the endpoint and confirm it is salesanalytics.cn6sqqsw4jj5.us-east-2.rds.amazonaws.com

DB_HOST = "salesanalytics.cn6sqqsw4jj5.us-east-2.rds.amazonaws.com";

Then password is ChillGuys!6767

DB_PASS = System.getenv().getOrDefault("DB_PASS", "ChillGuys!6767");

everything should already be written
---

2 Download the postgresql JDBC driver (will give error if you do not have)

(if lib is not been created)
mkdir lib 
curl -L -o lib/postgresql-42.7.3.jar \ https://jdbc.postgresql.org/download/postgresql-42.7.3.jar


You could also download manually from - [jdbc.postgresql.org](https://jdbc.postgresql.org/download/) 
afterwards place the .jar in lib/

but just running command above should do the trick

------------------------------------------

3 Compile & Run
On Mac / Linux

chmod +x compile_and_run.sh
./compile_and_run.sh

For Windows (works for Mac as well but running shell above is much easier and faster)
mkdir out
javac -cp lib\postgresql-42.7.3.jar -d out $(find src -name "*.java")
java -cp out;lib\postgresql-42.7.3.jar com.salesanalytics.driver.Driver

--------------------------------------------------------------------------------

Tech Stack

Language Java 17+
Database PostgreSQL 15 on AWS RDS
JDBC Driver postgresql-42.7.3
Schema Relational, 3NF, no derived attributes
Version Control Git / GitHub
