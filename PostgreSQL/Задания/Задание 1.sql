CREATE TABLE work_places(
	id varchar(3)
		PRIMARY KEY
		CHECK (
			id ~ '^[0-9]+$'
			AND length(id) = 3
		),
	name varchar(50) NOT NULL,
	address varchar(50) NOT NULL,
	exemption integer CHECK((exemption >= 0) and (exemption < 100))
		DEFAULT 0,
	UNIQUE (name, address)
);


CREATE TABLE car_enterprises (
	id varchar(3)
		PRIMARY KEY
		CHECK (
			id ~ '^[0-9]+$'
			AND length(id) = 3
		),
	name varchar(50) NOT NULL,
	address varchar(50) NOT NULL,
	add_percents integer CHECK(add_percents >= 0 and add_percents < 100)
		DEFAULT 0,
	UNIQUE (name, address)
);


CREATE TABLE transports (
	id varchar(3)
		PRIMARY KEY
		CHECK (
			id ~ '^[0-9]+$'
			AND length(id) = 3
		),
	type varchar(20) NOT NULL,
	address varchar(50) NOT NULL,
	max_quantity integer
		DEFAULT 0,
	price integer,
	UNIQUE (type, address)
);


CREATE TABLE orders (
	id varchar(5)
		PRIMARY KEY
		CHECK (
			id ~ '^[0-9]+$'
			AND length(id) = 5
		),
	date varchar(12),
	work_place varchar(3)
		CHECK (
			id ~ '^[0-9]+$'
			AND length(work_place) = 3
		)
		REFERENCES work_places
			ON DELETE CASCADE
			ON UPDATE CASCADE,
	car_enterprise varchar(3)
		CHECK (
			id ~ '^[0-9]+$'
			AND length(car_enterprise) = 3
		)
		REFERENCES car_enterprises
			ON DELETE CASCADE
			ON UPDATE CASCADE,
	transport varchar(3)
		CHECK (
			id ~ '^[0-9]+$'
			AND length(transport) = 3
		)
		REFERENCES transports
			ON DELETE CASCADE
			ON UPDATE CASCADE,
	quantity integer,
	price integer
);