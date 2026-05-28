-- CATEGORÍAS
INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

-- AUTORES
INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

-- JUEGOS
INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

-- CLIENTES
INSERT INTO client(name) VALUES ('Charles Leclerc');
INSERT INTO client(name) VALUES ('Max Verstappen');
INSERT INTO client(name) VALUES ('Lewis Hamilton');
INSERT INTO client(name) VALUES ('Kimi Antonelli');

-- PRÉSTAMOS
INSERT INTO loan(client_id, game_id, start_date, end_date) VALUES (1, 2, '2026-01-03', '2026-01-07');
INSERT INTO loan(client_id, game_id, start_date, end_date) VALUES (2, 3, '2026-01-03', '2026-01-13');
INSERT INTO loan(client_id, game_id, start_date, end_date) VALUES (1, 5, '2026-01-04', '2026-01-11');
INSERT INTO loan(client_id, game_id, start_date, end_date) VALUES (4, 4, '2026-01-03', '2026-01-07');