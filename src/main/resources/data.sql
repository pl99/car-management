-- Вставка владельцев
INSERT INTO owners (name, contact_info) VALUES
('John Doe', 'johndoe@example.com'),
('Jane Smith', 'janesmith@example.com'),
('Mike Johnson', 'mikejohnson@example.com'),
('Emily Davis', 'emilydavis@example.com');

-- Вставка автомобилей
INSERT INTO cars (registration_number, vin, make, model, production_date) VALUES
('ABC123', '1HGCM82633A123456', 'Toyota', 'Corolla', '2020-05-15'),
('DEF456', '2C4RC1BG5JR567890', 'Honda', 'Civic', '2019-08-20'),
('GHI789', '3VWFE21C04M000001', 'Volkswagen', 'Passat', '2021-03-10'),
('JKL012', '1FTSW21R08EB12345', 'Ford', 'Focus', '2018-12-05'),
('MNO345', 'JH4KA2650MC123456', 'Acura', 'TLX', '2022-01-30');

-- Вставка историй владения
INSERT INTO ownerships (car_id, owner_id, purchase_date, sale_date) VALUES
(1, 1, '2020-05-20', '2022-01-15'),
(2, 2, '2019-09-01', NULL),
(3, 3, '2021-03-15', '2023-06-01'),
(4, 4, '2018-12-10', NULL),
(5, 1, '2022-02-01', NULL);
