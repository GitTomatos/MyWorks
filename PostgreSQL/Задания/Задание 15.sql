-- 1
SELECT transports.type FROM orders
JOIN transports ON (orders.transport = transports.id)
GROUP BY transports.type
HAVING SUM (transports.price) <= 500000
ORDER BY transports.type;


-- 2
SELECT car_enterprises.name, COUNT(*) FILTER(WHERE orders.price > 200000) AS "Заказы > 200000" FROM orders
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
GROUP BY car_enterprises.name
ORDER BY "Заказы > 200000" DESC

-- 3
SELECT work_places.name, orders.date,
	COALESCE (
		SUM(orders.price)
		FILTER(WHERE orders.work_place = work_places.id), 0
	) AS "Summa"
FROM orders, work_places
GROUP BY work_places.name, orders.date
ORDER BY work_places.name, "Summa", orders.date;

-- 4
SELECT orders.date, work_places.address, COUNT(*) FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
WHERE work_places.address != 'Н.Новгород'
GROUP BY orders.date, work_places.address
HAVING COUNT(*) > 3
ORDER BY orders.date, work_places.address;