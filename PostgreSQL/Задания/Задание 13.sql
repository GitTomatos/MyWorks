-- 1
-- Один из вариантов изменений: вставить в заказы 3 строки, где заказчиком будет "Песочный Карьер",
-- а транспортом будут "Цистерна", "Бетономешалка", "Самосвал"
-- В таком случае в результате выведется "Песочный карьер"
SELECT DISTINCT wp1.name "Место работы" FROM orders 
JOIN transports ON (orders.transport = transports.id)
JOIN work_places wp1 ON (orders.work_place = wp1.id)
WHERE NOT EXISTS
(
	SELECT * FROM orders
	JOIN work_places wp2 ON (orders.work_place = wp2.id)
	JOIN transports ON (orders.transport = transports.id)
	WHERE wp2.name = wp1.name AND transports.address = 'Н.Новгород'
)
GROUP BY wp1.name
HAVING COUNT(DISTINCT transports.id) =
	(
		SELECT COUNT (transports.type)
		FROM transports
		WHERE address != 'Н.Новгород'
	);


-- 2
SELECT DISTINCT ce1.name "Автопредприятие" FROM car_enterprises ce1
WHERE NOT EXISTS
(
	SELECT * FROM orders
	JOIN car_enterprises ce2 ON (orders.car_enterprise = ce2.id)
	JOIN transports ON (orders.transport = transports.id)
	WHERE ce2.name = ce1.name AND transports.type = 'Автокран'
);


-- 3
SELECT transports.type "Транспорт" FROM orders
JOIN work_places wp1 ON (orders.work_place = wp1.id)
JOIN transports ON (orders.transport = transports.id)
WHERE NOT EXISTS
(
	SELECT * FROM orders
	JOIN work_places wp2 ON (orders.work_place = wp2.id)
	JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
	WHERE (wp2.name = wp1.name AND wp2.address != car_enterprises.address)
);


-- 4
SELECT g1.type "Транспорт" FROM transports g1
WHERE NOT EXISTS
	(
		SELECT g2.type FROM orders
		JOIN work_places ON (orders.work_place = work_places.id)
		JOIN transports g2 ON (orders.transport = g2.id)
		WHERE work_places.name = 'Овощная база' AND g2.type = g1.type
		GROUP BY work_places.name, g2.type
		HAVING COUNT(*) > 1
	)
ORDER BY g1.type;