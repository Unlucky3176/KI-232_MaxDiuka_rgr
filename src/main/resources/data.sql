INSERT INTO customer (customer_id, name, phone, email) VALUES
    (1, 'Тарас Шевченко', '0501112233', 'kobzar1861@gmail.com')
ON CONFLICT DO NOTHING;

INSERT INTO customer (customer_id, name, phone, email) VALUES
    (2, 'Біллі Рубін', '0672223344', 'williamrubin1998@gmail.com')
ON CONFLICT DO NOTHING;


INSERT INTO vehicle (brand, model, engine_type, engine_capacity) VALUES
                                                                     ('Yamaha', 'R1', 'Inline-4', 1000),
                                                                     ('Honda', 'CBR600RR', 'Inline-4', 600)
ON CONFLICT DO NOTHING;

INSERT INTO sale (sale_id, sale_date, customer_id, vehicle_id, amount) VALUES
    (1, current_date - INTERVAL '10 days', 1, 1, 15000)
ON CONFLICT DO NOTHING;

INSERT INTO sale (sale_id, sale_date, customer_id, vehicle_id, amount) VALUES
    (2, current_date - INTERVAL '2 days', 2, 2, 9000)
ON CONFLICT DO NOTHING;

INSERT INTO users(username, password, role) VALUES
    ('admin', '$2a$10$tZygIjeclIWL9mohvcvLBuJ3.tUfIdOOpKzbANjlk9YQNBy4Xk.RW', 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;