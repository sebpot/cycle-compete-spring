CREATE TABLE IF NOT EXISTS "_user" (
    "id" SERIAL PRIMARY KEY,
    "email" VARCHAR(255) NOT NULL,
    "firstname" VARCHAR(255),
    "lastname" VARCHAR(255),
    "password" VARCHAR(255) NOT NULL,
    "role" VARCHAR(255)
);