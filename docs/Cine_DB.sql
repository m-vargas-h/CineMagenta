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

-- verificar que se agregaron datas desde la app correctamente 
SELECT * FROM cartelera;


  