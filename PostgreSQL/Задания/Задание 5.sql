-- 1
SELECT type, address FROM transports
WHERE (max_quantity > 3);

-- 2
SELECT name, add_percents FROM car_enterprises
WHERE add_percents > 5 and address != 'Н.Новгород'
ORDER BY add_percents;

-- 3
SELECT name FROM work_places
WHERE address = 'Ильино';
