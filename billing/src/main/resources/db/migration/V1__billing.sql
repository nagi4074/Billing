CREATE TABLE billing(
    id SERIAL PRIMARY KEY NOT NULL,
    date DATE NOT NULL,
    billing INTEGER NOT NULL,
    application TEXT NOT NULL,
    result TEXT,
    remarks TEXT
);