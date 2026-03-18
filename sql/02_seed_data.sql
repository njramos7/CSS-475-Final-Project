
-- Sales Analytics Seed Data
-- Team: Chill Guys | CSS 475 Final Project

-- Lookup: Interaction Types
INSERT INTO InteractionType (typeName) VALUES
    ('Call'),
    ('Email'),
    ('Meeting');

-- Lookup: Opportunity Stages
INSERT INTO OpportunityStage (stageName) VALUES
    ('Prospecting'),
    ('Qualification'),
    ('Proposal'),
    ('Negotiation'),
    ('Closed');

-- Lookup: Opportunity Statuses
INSERT INTO OpportunityStatus (statusName) VALUES
    ('Open'),
    ('Won'),
    ('Lost');

-- Sales Reps
INSERT INTO SalesReps (firstName, lastName, email) VALUES
    ('Alice',   'Johnson', 'alice.johnson@company.com'),
    ('Bob',     'Smith',   'bob.smith@company.com'),
    ('Carol',   'Davis',   'carol.davis@company.com'),
    ('David',   'Lee',     'david.lee@company.com'),
    ('Eva',     'Martinez','eva.martinez@company.com');

-- Customers
INSERT INTO Customers (companyName, contactName, contactEmail, contactPhone, assignedRepID) VALUES
    ('Acme Corp',        'Tom Green',    'tom@acme.com',        '206-555-0101', 1),
    ('BetaCo',           'Sara Blue',    'sara@betaco.com',     '206-555-0102', 1),
    ('Gamma LLC',        'Mike Brown',   'mike@gamma.com',      '206-555-0103', 2),
    ('Delta Inc',        'Nina White',   'nina@delta.com',      '206-555-0104', 2),
    ('Epsilon Solutions','Jake Black',   'jake@epsilon.com',    '206-555-0105', 3),
    ('Zeta Group',       'Lucy Gold',    'lucy@zeta.com',       '206-555-0106', 3),
    ('Eta Partners',     'Sam Silver',   'sam@eta.com',         '206-555-0107', 4),
    ('Theta Works',      'Rita Red',     'rita@theta.com',      '206-555-0108', 4),
    ('Iota Tech',        'Leo Gray',     'leo@iota.com',        '206-555-0109', 5),
    ('Kappa Systems',    'Mia Pink',     'mia@kappa.com',       '206-555-0110', 5);

-- Quotas
INSERT INTO Quotas (salesRepID, year, quarter, quotaAmount) VALUES
    (1, 2024, 1, 100000.00), (1, 2024, 2, 110000.00),
    (1, 2024, 3, 120000.00), (1, 2024, 4, 130000.00),
    (2, 2024, 1,  90000.00), (2, 2024, 2,  95000.00),
    (2, 2024, 3, 100000.00), (2, 2024, 4, 105000.00),
    (3, 2024, 1,  80000.00), (3, 2024, 2,  85000.00),
    (4, 2024, 1,  75000.00), (4, 2024, 2,  80000.00),
    (5, 2024, 1,  70000.00), (5, 2024, 2,  75000.00);

-- Opportunities (stageID: 1=Prospecting 2=Qual 3=Proposal 4=Nego 5=Closed)
--              (statusID: 1=Open 2=Won 3=Lost)
INSERT INTO Opportunities (customerID, salesRepID, stageID, statusID, dealAmount, expectedCloseDate, actualCloseDate, saleDate, dateCreated, lastUpdated) VALUES
    (1, 1, 5, 2, 45000.00, '2024-03-31', '2024-03-28', '2024-03-28', '2024-01-10', '2024-03-28'),
    (1, 1, 5, 3, 30000.00, '2024-02-28', '2024-02-25', NULL,         '2024-01-05', '2024-02-25'),
    (2, 1, 4, 1, 60000.00, '2024-06-30', NULL,          NULL,         '2024-03-01', '2024-05-15'),
    (3, 2, 5, 2, 80000.00, '2024-04-30', '2024-04-20', '2024-04-20', '2024-02-01', '2024-04-20'),
    (4, 2, 3, 1, 25000.00, '2024-07-31', NULL,          NULL,         '2024-04-01', '2024-06-01'),
    (5, 3, 5, 2, 55000.00, '2024-05-31', '2024-05-25', '2024-05-25', '2024-03-01', '2024-05-25'),
    (6, 3, 2, 1, 40000.00, '2024-08-31', NULL,          NULL,         '2024-05-01', '2024-06-10'),
    (7, 4, 5, 3, 35000.00, '2024-03-31', '2024-03-30', NULL,         '2024-01-15', '2024-03-30'),
    (8, 4, 1, 1, 20000.00, '2024-09-30', NULL,          NULL,         '2024-06-01', '2024-06-01'),
    (9, 5, 5, 2, 90000.00, '2024-06-30', '2024-06-20', '2024-06-20', '2024-03-15', '2024-06-20'),
    (10,5, 3, 1, 50000.00, '2024-10-31', NULL,          NULL,         '2024-06-15', '2024-07-01');

-- Sales (won opportunities)
INSERT INTO Sales (opportunityID, customerID, salesRepID, saleDate, finalAmount) VALUES
    (1,  1, 1, '2024-03-28', 45000.00),
    (4,  3, 2, '2024-04-20', 80000.00),
    (6,  5, 3, '2024-05-25', 55000.00),
    (10, 9, 5, '2024-06-20', 90000.00);

-- Interactions
INSERT INTO Interactions (customerID, salesRepID, interactionTypeID, interactionDate, notes) VALUES
    (1, 1, 1, '2024-01-11', 'Initial discovery call with Tom.'),
    (1, 1, 3, '2024-01-25', 'Demo meeting at their office.'),
    (1, 1, 2, '2024-02-10', 'Sent proposal via email.'),
    (2, 1, 1, '2024-03-02', 'Intro call with Sara.'),
    (3, 2, 1, '2024-02-05', 'Discovery call with Mike.'),
    (3, 2, 3, '2024-03-01', 'Product walkthrough.'),
    (4, 2, 2, '2024-04-05', 'Sent comparison document.'),
    (5, 3, 1, '2024-03-05', 'First contact with Jake.'),
    (6, 3, 2, '2024-05-05', 'Sent intro brochure.'),
    (7, 4, 1, '2024-01-20', 'Called Sam to introduce product.'),
    (8, 4, 2, '2024-06-02', 'Sent pricing sheet to Rita.'),
    (9, 5, 3, '2024-03-20', 'Discovery meeting with Leo.'),
    (10,5, 1, '2024-06-16', 'Follow-up call with Mia.');
