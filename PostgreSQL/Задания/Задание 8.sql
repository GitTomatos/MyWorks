UPDATE orders
SET price = price * ((1 - work_places.exemption::real / 100::real))
FROM work_places
WHERE orders.work_place = work_places.id;