(SELECT work_places.name "Места работы или техника", work_places.address "Адреса мест работы или размещения техники" FROM work_places)
UNION
(SELECT transports.type, transports.address FROM transports)
