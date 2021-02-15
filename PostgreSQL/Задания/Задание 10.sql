-- 1
SELECT DISTINCT work_places.name "Место работы" FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
WHERE work_places.name NOT IN (
	SELECT DISTINCT work_places.name FROM orders
	JOIN work_places ON (orders.work_place = work_places.id)
	JOIN transports ON (orders.transport = transports.id)
	WHERE transports.address = 'Н.Новгород'
);


-- 2
SELECT orders.id, car_enterprises.name, car_enterprises.address "Адрес автопредприятия", transports.type, transports.address "Адрес техники", work_places.name FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
JOIN transports ON (orders.transport = transports.id)
WHERE orders.id NOT IN
	(
		SELECT orders.id FROM orders
		JOIN work_places ON (orders.work_place = work_places.id)
		JOIN transports ON (orders.transport = transports.id)
		JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
		WHERE transports.address = car_enterprises.address OR work_places.name = 'Детский сад'
	);


-- 3 (7.a)
-- Изначально с NOT IN

-- 3 (7.b)
SELECT DISTINCT ON (transports.type) transports.id "Идентификатор", transports.type "Тип машины" FROM orders
JOIN transports ON (orders.transport = transports.id)
WHERE orders.id IN 
	(
		SELECT orders.id FROM orders
		JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
		JOIN transports ON (orders.transport = transports.id)
		WHERE transports.address = car_enterprises.address
	);