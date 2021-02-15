-- 1
SELECT car_enterprises.name "Название автопредприятия", work_places.name "Название организации", work_places.address "Город расположения организации" FROM car_enterprises 
JOIN orders ON (car_enterprises.id = orders.car_enterprise)
JOIN work_places ON (orders.work_place = work_places.id)
WHERE (work_places.address != car_enterprises.address) AND (work_places.exemption >= 3)
ORDER BY car_enterprises.name;

-- 2
SELECT DISTINCT ON (transports.type) transports.id "Идентификатор", transports.type "Тип машины" FROM orders
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
JOIN transports ON (orders.transport = transports.id)
WHERE transports.address = car_enterprises.address;


-- 3
SELECT transports.type "Тип машины"
FROM orders 
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id)
JOIN transports ON (orders.transport = transports.id)
WHERE orders.price > 115000
GROUP BY transports.type
HAVING 
	COUNT(DISTINCT orders.car_enterprise) > 1;

-- 4
 
SELECT work_places.name AS "Место работы"
FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
GROUP BY (work_places.id)
HAVING SUM(orders.price) > 100000;
