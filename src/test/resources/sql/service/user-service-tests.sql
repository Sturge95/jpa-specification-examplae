INSERT INTO address(id, line_one, line_two, street, country, postcode)
VALUES (-1, 'street address', 'seconds line', 'terrington street', 'United Kingdom', 'NRR 3SS');

INSERT INTO user(id, first_name, last_name, phone_number, date_of_birth, address_id, age)
VALUES (01, 'shane', 'sturgeon', '07728744735', now(), -1, 44);