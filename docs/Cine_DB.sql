-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS Cine_DB;

USE Cine_DB;

-- Crear la tabla Cartelera
CREATE TABLE IF NOT EXISTS Cartelera (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    director VARCHAR(50) NOT NULL,
    anno INT NOT NULL,
    duracion INT NOT NULL,
    genero VARCHAR(50) NOT NULL,
    ruta_portada VARCHAR(255)
);

-- Insertar Harry Potter y la Piedra Filosofal
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('Harry Potter y la Piedra Filosofal', 'Chris Columbus', 2001, 152, 'Acción', 'docs/portadas/HarryPotter_PiedraFilosofal.jpg');

-- Insertar Harry Potter y la Cámara Secreta
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('Harry Potter y la Cámara Secreta', 'Chris Columbus', 2002, 161, 'Acción', 'docs/portadas/HarryPotter_CamaraSecreta.jpg');

-- Insertar La La Land
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('La La Land', 'Damien Chazelle', 2016, 128, 'Musical', 'docs/portadas/LaLaLand.jpg');

-- Insertar El Padrino
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('El Padrino', 'Francis Ford Coppola', 1972, 175, 'Drama', 'docs/portadas/ElPadrino.jpg');

-- Insertar Jurassic Park I
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('Jurassic Park', 'Steven Spielberg', 1993, 127, 'Acción', 'docs/portadas/JurassicPark.jpg');

-- Insertar Amélie
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('Amélie', 'Jean-Pierre Jeunet', 2001, 122, 'Comedia', 'docs/portadas/Amelie.jpg');

-- Insertar Parasite
INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada)
VALUES ('Parasite', 'Bong Joon-ho', 2019, 132, 'Suspenso', 'docs/portadas/Parasite.jpg');

-- verificar que se agregaron datas desde la app correctamente 
SELECT * FROM cartelera;


  