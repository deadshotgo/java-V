CREATE TABLE device (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      type VARCHAR(255) NOT NULL,
                      room_id INT NOT NULL,
                      FOREIGN KEY (room_id) REFERENCES room(id)
);