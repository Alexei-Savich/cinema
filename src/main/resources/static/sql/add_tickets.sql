INSERT INTO tickets (hall, datetime, seat, price, telephone_number, name, surname, email, film_name, validated, validated_by, validated_at, additional_services, session_id)
VALUES ('Hall A', '2023-05-10 13:30:00', 'A1', 10.0, '123456789', 'John', 'Doe', 'johndoe@example.com', 'Avengers: Endgame', true, 1, '2023-05-10 13:45:00', 'Popcorn and drink', 1);

INSERT INTO tickets (hall, datetime, seat, price, telephone_number, name, surname, email, film_name, validated, validated_by, validated_at, additional_services, session_id)
VALUES ('Hall B', '2023-05-11 15:45:00', 'B3', 8.5, '987654321', 'Jane', 'Doe', 'janedoe@example.com', 'Joker', true, 2, '2023-05-11 16:00:00', NULL, 2);

INSERT INTO tickets (hall, datetime, seat, price, telephone_number, name, surname, email, film_name, validated, validated_by, validated_at, additional_services, session_id)
VALUES ('Hall C', '2023-05-12 19:00:00', 'C7', 12.0, '555555555', 'Bob', 'Smith', 'bobsmith@example.com', 'The Lion King', false, NULL, NULL, NULL, 3);

INSERT INTO tickets (hall, datetime, seat, price, telephone_number, name, surname, email, film_name, validated, validated_by, validated_at, additional_services, session_id)
VALUES ('Hall A', '2023-05-14 12:00:00', 'A5', 9.0, '111111111', 'Alice', 'Johnson', 'alicejohnson@example.com', 'Spider-Man: Far From Home', false, NULL, NULL, NULL, 1);

INSERT INTO tickets (hall, datetime, seat, price, telephone_number, name, surname, email, film_name, validated, validated_by, validated_at, additional_services, session_id)
VALUES ('Hall B', '2023-05-15 16:30:00', 'B1', 7.5, '222222222', 'Tom', 'Brown', 'tombrown@example.com', 'Black Panther', true, 3, '2023-05-15 16:45:00', 'Popcorn', 2);
