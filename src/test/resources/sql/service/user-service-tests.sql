INSERT INTO address(id, line_one, line_two, street, country, postcode)
VALUES (-10, 'street address', 'seconds line', 'terrington street', 'United Kingdom', 'NRR 3SS'),
       (-20, '4 Pringles Road', 'seconds line', 'terrington street', 'United Kingdom', 'NT5 3DD'),
       (-30, '3 Rofter Pot', 'seconds line', 'Florangter Pop', 'United Kingdom', 'NR43 3DD'),
       (-40, '44 Gorapter', 'seconds line', 'Ropper Street', 'United Kingdom', 'NRR 3GD'),
       (-50, '22 Splitter Foor', 'seconds line', 'Tinglerton', 'United Kingdom', 'NRR 4DD');

INSERT INTO user (id, first_name, last_name, phone_number, date_of_birth, address_id, age)
VALUES (-1, 'Bob', 'Smith', '07728744735', now(), -10, 44),
       (-2, 'Elaine', 'Sorta', '0778657765', now(), -20, 32),
       (-3, 'Tina', 'Equaint', '07738477583', now(), -30, 99),
       (-4, 'Robert', 'Brip', '03395833759', now(), -40, 105),
       (-5, 'Eller', 'Toraint', '07749577384', now(), -50, 56);