CREATE TABLE Category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR NOT NULL
);

CREATE TABLE Question (
    question_id SERIAL PRIMARY KEY,
    category_id INT NOT NULL,
    question_text VARCHAR NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

CREATE TABLE Answer (
    answer_id SERIAL PRIMARY KEY,
    question_id INT NOT NULL,
    answer_text VARCHAR NOT NULL,
    is_correct BOOLEAN NOT NULL,
    FOREIGN KEY (question_id) REFERENCES Question(question_id)
);