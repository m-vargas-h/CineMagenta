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
    genero ENUM('Acción', 'Comedia', 'Drama', 'Suspenso', 'Terror', 'Animación') NOT NULL
);

-- verificar que se agregaron datas desde la app correctamente 
SELECT * FROM Cartelera;
  