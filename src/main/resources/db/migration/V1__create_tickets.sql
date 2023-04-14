CREATE TABLE tickets (
  id INT NOT NULL AUTO_INCREMENT,
  date DATE,
  time TIME,
  price DECIMAL(10,2),
  seat_row INT,
  seat_number INT,
  telephone_number VARCHAR(20),
  name VARCHAR(50),
  surname VARCHAR(50),
  email VARCHAR(100),
  film_name VARCHAR(255),
  hall_number INT,
  validated BOOLEAN DEFAULT false,
  PRIMARY KEY (id),
  UNIQUE KEY (seat_row, seat_number, time, date, hall_number)
);
