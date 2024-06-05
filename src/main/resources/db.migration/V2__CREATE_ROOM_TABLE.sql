CREATE TABLE room (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        user_id INT not null,
                        FOREIGN KEY (user_id) REFERENCES app_user(id)
);