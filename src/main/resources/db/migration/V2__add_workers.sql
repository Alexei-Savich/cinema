CREATE TABLE staff_workers (
  worker_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  surname VARCHAR(50),
  email VARCHAR(100),
  PRIMARY KEY (worker_id)
);

ALTER TABLE tickets DROP INDEX seat_row, ADD UNIQUE KEY (seat, datetime, hall_number);

ALTER TABLE tickets
ADD COLUMN hall VARCHAR(50),
ADD COLUMN datetime DATETIME,
ADD COLUMN seat VARCHAR(10),
ADD COLUMN validated_by INT,
ADD COLUMN validated_at DATETIME DEFAULT NULL,
ADD COLUMN additional_services VARCHAR(255);

ALTER TABLE tickets
DROP COLUMN seat_row,
DROP COLUMN seat_number,
DROP COLUMN date,
DROP COLUMN time;

ALTER TABLE tickets ADD CONSTRAINT fk_validated_by FOREIGN KEY (validated_by) REFERENCES staff_workers(worker_id);
