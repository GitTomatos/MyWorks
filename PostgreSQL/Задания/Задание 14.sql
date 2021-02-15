-- 1
SELECT COUNT (DISTINCT transports.type) AS "Количество разл. тех. в д. саду" FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
JOIN transports ON (orders.transport = transports.id)
WHERE work_places.name = 'Детский сад';

-- 2
SELECT ROUND(AVG(wp1.exemption), 2) FROM work_places wp1
WHERE
(
	SELECT COUNT (DISTINCT car_enterprises.name) FROM orders
	JOIN work_places wp2 ON (orders.work_place = wp2.id)
	JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
	WHERE wp2.name = wp1.name AND car_enterprises.address = 'Н.Новгород'
) = (
	SELECT COUNT(*) FROM car_enterprises
	WHERE car_enterprises.address = 'Н.Новгород'
);

-- 3
SELECT SUM(orders.price) FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
WHERE work_places.name = 'Овощная база';

-- 4
SELECT DISTINCT car_enterprises.name
FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
WHERE work_places.address = 'Н.Новгород' AND
	car_enterprises.add_percents >
	(
		SELECT ROUND(AVG(car_enterprises.add_percents), 3) FROM car_enterprises
	);
