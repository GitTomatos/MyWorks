-- 1
SELECT * FROM orders
JOIN car_enterprises ON (orders.car_enterprise  = car_enterprises.id)
WHERE orders.date = 'Четверг'
AND car_enterprises.add_percents <= ALL 
	(
		SELECT car_enterprises.add_percents
		FROM car_enterprises
		WHERE orders.date = 'Четверг'
	);


-- 2
SELECT orders.price AS "Максимальная стоимость заказа во вторник" FROM orders
WHERE orders.date = 'Вторник'
AND orders.price >= ALL 
	(
		SELECT orders.price FROM orders
		WHERE orders.date = 'Вторник'
	);


-- 3
SELECT DISTINCT transports.type "Тип машины"
FROM orders 
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
JOIN transports ON (orders.transport = transports.id)
WHERE orders.transport = ANY
	(
		SELECT DISTINCT orders.transport
		FROM orders
		JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
		JOIN transports ON (orders.transport = transports.id)
		GROUP BY orders.transport
		HAVING COUNT( DISTINCT orders.car_enterprise) > 1
	)
	AND orders.price > 115000;


-- 4
SELECT work_places.name "Место работы", work_places.exemption "Размер льгот" FROM work_places
WHERE exemption <= ALL 
	(
		SELECT work_places.exemption
		FROM work_places
	)
