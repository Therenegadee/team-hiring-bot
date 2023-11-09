CREATE TABLE IF NOT EXISTS questionnaire (
    id BIGINT PRIMARY KEY,
    position VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS question (
    id BIGINT PRIMARY KEY,
    question_text VARCHAR(255),
    questionnaire_id BIGINT,
    FOREIGN KEY (questionnaire_id) REFERENCES questionnaire(id)
);

CREATE TABLE IF NOT EXISTS answer (
    id BIGINT PRIMARY KEY,
    answer_text VARCHAR(255),
    question_id BIGINT,
    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE IF NOT EXISTS application (
    id VARCHAR(255) PRIMARY KEY,
    telegram_id VARCHAR(255),
    full_name VARCHAR(255),
    age INT,
    phone_number VARCHAR(255),
    contact_method VARCHAR(255),
    experience VARCHAR(255),
    questionnaire_id BIGINT,
    FOREIGN KEY (questionnaire_id) REFERENCES questionnaire(id)
);