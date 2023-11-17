create SCHEMA IF NOT EXISTS rassvet_team_bot;

create TABLE IF NOT EXISTS position_tags (
    id          BIGINT   PRIMARY KEY,
    tag_name    VARCHAR
);

create TABLE IF NOT EXISTS vacancy (
    id                  BIGINT   PRIMARY KEY,
    position_name       VARCHAR,
    position_tag_id     BIGINT,
    FOREIGN KEY (position_tag_id) REFERENCES position_tags(id)
);

create TABLE IF NOT EXISTS application (
    id                  VARCHAR  PRIMARY KEY,
    application_status  VARCHAR,
    telegram_id         VARCHAR,
    full_name           VARCHAR,
    age                 INT,
    phone_number        VARCHAR,
    contact_method      VARCHAR,
    experience          VARCHAR,
    vacancy_id          BIGINT,
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id)
);

create TABLE IF NOT EXISTS vacancy_questions (
    id                  BIGINT      PRIMARY KEY,
    question_text       VARCHAR,
    vacancy_id          BIGINT,
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id)
);

create TABLE IF NOT EXISTS application_answers (
    application_id      BIGINT,
    question_id         BIGINT,
    answer_text         VARCHAR,
    FOREIGN KEY (application_id) REFERENCES application(id),
    FOREIGN KEY (question_id) REFERENCES vacancy_questions(id)
);

create TABLE IF NOT EXISTS roles (
    id          BIGINT      PRIMARY KEY,
    role_name   VARCHAR     UNIQUE
);

create TABLE IF NOT EXISTS roles_position_tags (
    role_id             BIGINT,
    position_tag_id     BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (position_tag_id) REFERENCES position_tags(id)
);

create TABLE IF NOT EXISTS users (
    id              BIGINT      PRIMARY KEY,
    telegram_id     VARCHAR     UNIQUE,
    username        VARCHAR,
    phone_number    VARCHAR,
    full_name       VARCHAR,
    secret_key      VARCHAR
);

create TABLE IF NOT EXISTS users_roles (
    user_id BIGINT,
    role_id BIGINT
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
);