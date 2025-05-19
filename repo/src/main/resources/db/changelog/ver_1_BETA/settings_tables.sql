-- Liquibase formatted sql

-- Changeset antonio:settings-1
CREATE TABLE application_settings
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    section TEXT NOT NULL,
    key     TEXT NOT NULL,
    value   TEXT,
    UNIQUE (section, key)
);

-- Changeset antonio:settings-2
CREATE TABLE input_settings
(
    id    INTEGER PRIMARY KEY AUTOINCREMENT,
    key   TEXT NOT NULL,
    value TEXT NOT NULL
);

-- Changeset antonio:settings-3
CREATE TABLE output_settings
(
    id    INTEGER PRIMARY KEY AUTOINCREMENT,
    key   TEXT NOT NULL,
    value TEXT NOT NULL
);

-- Changeset antonio:settings-4
INSERT INTO application_settings (section, key, value)
VALUES ('general', 'inputType', 'DEFAULT'),
       ('general', 'outputType', 'XLSX'),
       ('general', 'logLevel', 'INFO');

-- Changeset antonio:settings-5
INSERT INTO input_settings (key, value)
VALUES ('inputFileName', 'excel-example/DataFromInvoice.xlsx'),
       ('inputFormat', 'XLSX');

-- Changeset antonio:settings-6
INSERT INTO output_settings (key, value)
VALUES ('outputFileName', 'output.xlsx'),
       ('outputFormat', 'XLSX');
