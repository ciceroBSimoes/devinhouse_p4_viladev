CREATE TABLE residents (id SERIAL PRIMARY KEY, name VARCHAR(60), surname VARCHAR(60), birth_date DATE, income NUMERIC(6,2), cpf VARCHAR(60) UNIQUE);
