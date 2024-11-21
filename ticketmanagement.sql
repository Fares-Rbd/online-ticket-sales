create database ticketmanagement;

use ticketmanagement;

-- Create table for categories
CREATE TABLE categorie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code_categorie VARCHAR(50) NOT NULL UNIQUE,
    nom_categorie VARCHAR(100) NOT NULL
);

-- Create table for events
CREATE TABLE evenement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_evenement VARCHAR(100) NOT NULL UNIQUE,
    nb_places_restantes INT NOT NULL,
    date_evenement DATE NOT NULL
);

-- Create table for many-to-many relationship
CREATE TABLE evenement_categorie (
    evenement_id BIGINT NOT NULL,
    categorie_id BIGINT NOT NULL,
    PRIMARY KEY (evenement_id, categorie_id),
    FOREIGN KEY (evenement_id) REFERENCES evenement(id) ON DELETE CASCADE,
    FOREIGN KEY (categorie_id) REFERENCES categorie(id) ON DELETE CASCADE
);

-- Insert categories
INSERT INTO categorie (code_categorie, nom_categorie) VALUES
('c1', 'Music'),
('c2', 'Festival'),
('c3', 'Career'),
('c4', 'Tech'),
('c5', 'Health');

-- Insert events
INSERT INTO evenement (nom_evenement, nb_places_restantes, date_evenement) VALUES
('Summer Vibes', 200, '2024-12-15'),
('How to Get a Job in One Week', 80, '2024-11-30'),
('Tech Revolution', 150, '2024-10-15'),
('Healthy Living Workshop', 50, '2024-09-10');

-- Associate events with categories
INSERT INTO evenement_categorie (evenement_id, categorie_id) VALUES
(1, 1), (1, 2), -- Summer Vibes -> Music, Festival
(2, 3),         -- How to Get a Job in One Week -> Career
(3, 4),         -- Tech Revolution -> Tech
(4, 5);         -- Healthy Living Workshop -> Health

-- Create table for users
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tranche_age VARCHAR(50) NOT NULL
);

-- Insert users
INSERT INTO user (name, tranche_age) VALUES
('Alice', '18-25'),
('Bob', '26-35'),
('Charlie', '36-45'),
('Diana', '18-25'),
('Edward', '46-55'),
("Malek", '18-25');



-- Create table for tickets
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code_ticket VARCHAR(100) NOT NULL UNIQUE,
    prix_ticket DOUBLE NOT NULL,
    type_ticket ENUM('CLASSIQUE', 'VIP') NOT NULL,
    id_event BIGINT NOT NULL,
    id_internaute BIGINT NOT NULL,
    FOREIGN KEY (id_event) REFERENCES evenement(id) ON DELETE CASCADE,
    FOREIGN KEY (id_internaute) REFERENCES user(id) ON DELETE CASCADE
    );
    
-- Insert tickets
INSERT INTO ticket (code_ticket, prix_ticket, type_ticket, id_event, id_internaute) VALUES
('TICKET001', 50.0, 'CLASSIQUE', 1, 1), -- Alice -> Summer Vibes
('TICKET002', 100.0, 'VIP', 1, 1), -- Alice -> Summer Vibes
('TICKET003', 60.0, 'CLASSIQUE', 1, 2), -- Bob -> Summer Vibes
('TICKET004', 80.0, 'VIP', 2, 2), -- Bob -> How to Get a Job
('TICKET005', 60.0, 'CLASSIQUE', 2, 3), -- Charlie -> How to Get a Job
('TICKET006', 120.0, 'VIP', 3, 4), -- Diana -> Tech Revolution
('TICKET007', 30.0, 'CLASSIQUE', 4, 5); -- Edward -> Healthy Living Workshop


select * from user;
select * from ticket;
select * from evenement;
select * from evenement_categorie;
select * from categorie;






