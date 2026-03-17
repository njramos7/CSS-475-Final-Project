-- ============================================================
-- Sales Analytics Database Schema
-- Team: Chill Guys | CSS 475 Final Project
-- Database: PostgreSQL (AWS RDS)
-- ============================================================

-- Drop tables in reverse dependency order
DROP TABLE IF EXISTS Sales CASCADE;
DROP TABLE IF EXISTS Interactions CASCADE;
DROP TABLE IF EXISTS Opportunities CASCADE;
DROP TABLE IF EXISTS Quotas CASCADE;
DROP TABLE IF EXISTS Customers CASCADE;
DROP TABLE IF EXISTS SalesReps CASCADE;
DROP TABLE IF EXISTS OpportunityStage CASCADE;
DROP TABLE IF EXISTS OpportunityStatus CASCADE;
DROP TABLE IF EXISTS InteractionType CASCADE;

-- ============================================================
-- LOOKUP TABLES
-- ============================================================

CREATE TABLE InteractionType (
    interactionTypeID SERIAL PRIMARY KEY,
    typeName          VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE OpportunityStage (
    stageID   SERIAL PRIMARY KEY,
    stageName VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE OpportunityStatus (
    statusID   SERIAL PRIMARY KEY,
    statusName VARCHAR(40) NOT NULL UNIQUE
);

-- ============================================================
-- CORE TABLES
-- ============================================================

CREATE TABLE SalesReps (
    salesRepID SERIAL PRIMARY KEY,
    firstName  VARCHAR(40) NOT NULL,
    lastName   VARCHAR(40) NOT NULL,
    email      VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE Customers (
    customerID      SERIAL PRIMARY KEY,
    companyName     VARCHAR(40) NOT NULL,
    contactName     VARCHAR(40),
    contactEmail    VARCHAR(40) NOT NULL,
    contactPhone    VARCHAR(20),
    assignedRepID   INTEGER NOT NULL REFERENCES SalesReps(salesRepID),
    dateAdded       TIMESTAMP DEFAULT NOW(),
    UNIQUE (companyName, contactEmail)
);

CREATE TABLE Quotas (
    quotaID     SERIAL PRIMARY KEY,
    salesRepID  INTEGER NOT NULL REFERENCES SalesReps(salesRepID),
    year        INTEGER NOT NULL,
    quarter     INTEGER NOT NULL CHECK (quarter BETWEEN 1 AND 4),
    quotaAmount DECIMAL(15,2) NOT NULL,
    UNIQUE (salesRepID, year, quarter)
);

CREATE TABLE Opportunities (
    opportunityID      SERIAL PRIMARY KEY,
    customerID         INTEGER NOT NULL REFERENCES Customers(customerID),
    salesRepID         INTEGER NOT NULL REFERENCES SalesReps(salesRepID),
    stageID            INTEGER NOT NULL REFERENCES OpportunityStage(stageID),
    statusID           INTEGER NOT NULL REFERENCES OpportunityStatus(statusID),
    dealAmount         DECIMAL(15,2),
    expectedCloseDate  TIMESTAMP,
    actualCloseDate    TIMESTAMP NULL,
    saleDate           TIMESTAMP NULL,
    dateCreated        TIMESTAMP DEFAULT NOW(),
    lastUpdated        TIMESTAMP DEFAULT NOW()
);

CREATE TABLE Interactions (
    interactionID     SERIAL PRIMARY KEY,
    customerID        INTEGER NOT NULL REFERENCES Customers(customerID),
    salesRepID        INTEGER NOT NULL REFERENCES SalesReps(salesRepID),
    interactionTypeID INTEGER NOT NULL REFERENCES InteractionType(interactionTypeID),
    interactionDate   TIMESTAMP NOT NULL,
    notes             TEXT
);

CREATE TABLE Sales (
    saleID        SERIAL PRIMARY KEY,
    opportunityID INTEGER NOT NULL REFERENCES Opportunities(opportunityID),
    customerID    INTEGER NOT NULL REFERENCES Customers(customerID),
    salesRepID    INTEGER NOT NULL REFERENCES SalesReps(salesRepID),
    saleDate      TIMESTAMP NOT NULL,
    finalAmount   DECIMAL(15,2) NOT NULL
);

-- ============================================================
-- INDEXES for performance
-- ============================================================
CREATE INDEX idx_opportunities_repid    ON Opportunities(salesRepID);
CREATE INDEX idx_opportunities_status   ON Opportunities(statusID);
CREATE INDEX idx_opportunities_stage    ON Opportunities(stageID);
CREATE INDEX idx_sales_repid            ON Sales(salesRepID);
CREATE INDEX idx_sales_customerid       ON Sales(customerID);
CREATE INDEX idx_interactions_customerid ON Interactions(customerID);
