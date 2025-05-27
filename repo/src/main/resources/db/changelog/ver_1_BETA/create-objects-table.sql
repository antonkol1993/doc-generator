-- liquibase formatted sql
-- changeset anton:create-objects-001

CREATE TABLE IF NOT EXISTS objects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    key_name TEXT,
    eng_name TEXT,
    rus_name TEXT,
    "1C8_name" TEXT,
    image_name TEXT,
    tn_ved_code TEXT,
    producer TEXT
);
