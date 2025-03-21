-- Create table for Persons
CREATE TABLE S_CHALLENGE.T_PERSONS (
    identification VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL CHECK (char_length(name) <= 100),
    gender VARCHAR(10) NOT NULL,
    address VARCHAR(200) NOT NULL CHECK (char_length(address) <= 200),
    phone VARCHAR(15) NOT NULL CHECK (char_length(phone) <= 15)
);

-- Create table for Customers linked to Persons
CREATE TABLE S_CHALLENGE.T_CUSTOMERS (
    identification VARCHAR(20) NOT NULL PRIMARY KEY REFERENCES S_CHALLENGE.T_PERSONS(identification),
    password VARCHAR(100) NOT NULL CHECK (char_length(password) <= 100),
    status VARCHAR(10) NOT NULL
);

CREATE SEQUENCE ACCOUNT_NUMBER_SEQ
START WITH 100000
MINVALUE 100000
MAXVALUE 999999
INCREMENT BY 1;

CREATE TABLE S_CHALLENGE.T_ACCOUNTS (
    account_number BIGINT PRIMARY KEY DEFAULT nextval('ACCOUNT_NUMBER_SEQ') CHECK (account_number BETWEEN 100000 AND 999999),
    customer_id VARCHAR(20) NOT NULL,
    type VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(15, 2),
    status VARCHAR(20) NOT NULL
);

CREATE TABLE S_CHALLENGE.T_MOVEMENTS (
    id SERIAL PRIMARY KEY,
    account_number BIGINT NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(20) NOT NULL,
    value DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (account_number) REFERENCES S_CHALLENGE.T_ACCOUNTS(account_number)
);

CREATE TABLE S_CHALLENGE.T_REPORTS (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    customer_id VARCHAR(100) NOT NULL,
    account_number BIGINT NOT NULL,
    type_account VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(15, 2),
    status VARCHAR(20) NOT NULL,
    value DECIMAL(15, 2) NOT NULL,
    type_movement VARCHAR(20) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL
);

-- Insert initial data into Personas
INSERT INTO S_CHALLENGE.T_PERSONS (name, gender, address, phone, identification) VALUES
('Jose Lema', 'Male', 'Otavalo sn y principal', '098254785', '1705201446'),
('Marianela Montalvo', 'Female', 'Amazonas y NNUU', '097548965', '0101503206'),
('Juan Osorio', 'Male', '13 junio y Equinoccial', '098874587', '0502648725');

-- Insert initial data into Customers based on Personas
INSERT INTO S_CHALLENGE.T_CUSTOMERS (identification, password, status) VALUES
((SELECT identification FROM S_CHALLENGE.T_PERSONS WHERE name='Jose Lema'), '1234', TRUE),
((SELECT identification FROM S_CHALLENGE.T_PERSONS WHERE name='Marianela Montalvo'), '5678', TRUE),
((SELECT identification FROM S_CHALLENGE.T_PERSONS WHERE name='Juan Osorio'), '1245', TRUE);

-- Assuming customers is stored in the table as identification:
INSERT INTO S_CHALLENGE.T_ACCOUNTS (customer_id, type, initial_balance, status) VALUES
( '1705201446', 'SAVINGS', 2000.00, 'ACTIVE'),
( '0101503206', 'CHECKING', 100.00, 'ACTIVE'),
( '0502648725', 'SAVINGS', 0.00, 'ACTIVE'),
( '0101503206', 'SAVINGS', 540.00, 'ACTIVE'),
( '1705201446', 'CHEKING', 1000.00, 'ACTIVE');

-- Movements for bank accounts
INSERT INTO S_CHALLENGE.T_MOVEMENTS (account_number, date, type, value, balance, status) VALUES
(100000, '2024-04-01', 'CREDIT', 2000.00, 2000,TRUE),
(100001, '2024-04-01', 'CREDIT', 1000.00, 1000, TRUE),
(100002, '2024-04-01', 'CREDIT', 0.00, 0, TRUE),
(100003, '2024-04-01', 'CREDIT', 540.00, 540, TRUE),
(100004, '2024-04-01', 'CREDIT', 1000.00, 1000, TRUE);