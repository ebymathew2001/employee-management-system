-- Drop tables if they exist
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

-- Create departments table
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    head_id BIGINT,
    CONSTRAINT fk_department_head
        FOREIGN KEY (head_id)
        REFERENCES employees(id)
        ON DELETE SET NULL
);

-- Create employees table
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    salary DOUBLE NOT NULL,
    address VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    joining_date DATE NOT NULL,
    yearly_bonus_percentage INT NOT NULL,
    department_id BIGINT NOT NULL,
    reporting_manager_id BIGINT,

    CONSTRAINT fk_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_reporting_manager
        FOREIGN KEY (reporting_manager_id)
        REFERENCES employees(id)
        ON DELETE SET NULL
);
