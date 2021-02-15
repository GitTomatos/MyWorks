SELECT * FROM car_enterprises;
UPDATE car_enterprises
SET address = 'Дзержинск' WHERE address = 'Джержинск';

SELECT * FROM transports;
UPDATE transports
SET address = 'Дзержинск' WHERE address = 'Джержинск';

SELECT * FROM work_places;
UPDATE work_places
SET address = 'Дзержинск' WHERE address = 'Джержинск';

UPDATE work_places
SET name = 'Песочный карьер' WHERE name = 'Песочный карьер 8';

UPDATE work_places
SET name = 'Детский сад' WHERE name = 'Детский сад 1';

UPDATE work_places
SET name = 'Стройплощадка' WHERE name = 'Стройплощадка 15';

SELECT * FROM orders;
