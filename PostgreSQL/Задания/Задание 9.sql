ALTER TABLE orders
ADD COLUMN add_percents integer
	CHECK (add_percents >= 0)
	DEFAULT 0;
	
UPDATE orders
SET add_percents = 1
WHERE add_percents = 0;