CREATE SCHEMA IF NOT EXISTS rassvet_team_bot;

CREATE TABLE IF NOT EXISTS vacancy (
    id                  BIGINT   PRIMARY KEY,
    position_name       VARCHAR
);

CREATE TABLE IF NOT EXISTS application (
    id                  VARCHAR  PRIMARY KEY,
    telegram_id         VARCHAR,
    vacancy_id          BIGINT,
    full_name           VARCHAR,
    age                 INT,
    phone_number        VARCHAR,
    contact_method      VARCHAR,
    experience          VARCHAR,
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id)
);

CREATE TABLE IF NOT EXISTS vacancy_questions (
    id                  BIGINT      PRIMARY KEY,
    question_text       VARCHAR,
    vacancy_id          BIGINT,
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id)
);

CREATE TABLE IF NOT EXISTS application_answers (
    application_id      BIGINT,
    question_id         BIGINT,
    answer_text         VARCHAR,
    FOREIGN KEY (application_id) REFERENCES application(id),
    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS users (
    id              BIGINT      PRIMARY KEY,
    role            VARCHAR,
    telegram_id     VARCHAR,
    username        VARCHAR,
    phone_number    VARCHAR,
    full_name       VARCHAR,
    secret_key      VARCHAR
);