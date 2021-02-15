-- 1
SELECT work_places.name "Место работы", transports.type "Техника",
	orders.quantity "Количество", orders.price "Оплата" FROM orders
JOIN work_places ON (orders.work_place = work_places.id)
JOIN transports ON (orders.transport = transports.id)
ORDER BY orders.price, work_places.name;

-- 2
SELECT orders.id "Номер заказа", orders.date "Дата заказа", car_enterprises.name "Название автопредприятия" FROM orders
JOIN car_enterprises ON (orders.car_enterprise = car_enterprises.id);
