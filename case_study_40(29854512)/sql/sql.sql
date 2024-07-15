-- creating the database
CREATE DATABASE feedback_management;

-- using the database
USE feedback_management;

--  creating the customer table
CREATE TABLE Customer (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255)
);

-- creating the feedback table
CREATE TABLE Feedback (
    feedback_id INT PRIMARY KEY,
    customer_id INT,
    feedback_date DATE,
    feedback_text TEXT,
    status VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

-- creating the analysis table
CREATE TABLE Analysis (
    analysis_id INT PRIMARY KEY,
    feedback_id INT,
    analysis_date DATE,
    analysis_details TEXT,
    status VARCHAR(50),
    FOREIGN KEY (feedback_id) REFERENCES Feedback(feedback_id)
);

-- creating the response table
CREATE TABLE Response (
    response_id INT PRIMARY KEY,
    feedback_id INT,
    response_date DATE,
    response_text TEXT,
    status VARCHAR(50),
    FOREIGN KEY (feedback_id) REFERENCES Feedback(feedback_id)
);

-- To display the data of any table
SELECT * FROM table_name

-- For inserting the data into customer table
INSERT INTO customer(customer_id, customer_name, customer_email)
VALUES(value_1, value_2, value_3);