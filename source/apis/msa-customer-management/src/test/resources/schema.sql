CREATE SCHEMA IF NOT EXISTS S_CHALLENGE;

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