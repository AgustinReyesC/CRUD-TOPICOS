create database CRUD
go
use CRUD
create table cliente(
idCliente	int primary key,
nombre	varchar(30)
)

create table mascota(
idMascota		int primary key,
nombre			varchar(30),
descripcion		varchar(100),
idOwner INT NOT NULL,

FOREIGN KEY (idOwner) REFERENCES cliente(idCliente) ON DELETE CASCADE
)
go


drop database CRUD
-- Inserts para la tabla cliente
INSERT INTO cliente (idCliente, nombre) VALUES
(1, 'Juan Perez'),
(2, 'Maria Lopez'),
(3, 'Carlos Gomez'),
(4, 'Ana Martinez'),
(5, 'Luis Fernandez'),
(6, 'Elena Diaz'),
(7, 'Ricardo Sanchez'),
(8, 'Carmen Torres'),
(9, 'Jose Ramirez'),
(10, 'Paula Castillo'),
(11, 'Daniel Mendez'),
(12, 'Sofia Reyes'),
(13, 'Hugo Ortiz'),
(14, 'Valeria Morales'),
(15, 'Alejandro Vega'),
(16, 'Laura Herrera'),
(17, 'Fernando Rojas'),
(18, 'Patricia Navarro'),
(19, 'Jorge Fuentes'),
(20, 'Natalia Medina'),
(21, 'Andres Cortez'),
(22, 'Isabella Paredes'),
(23, 'Diego Nunez'),
(24, 'Camila Suarez'),
(25, 'Raul Silva'),
(26, 'Emma Alvarez'),
(27, 'Cristian Lara'),
(28, 'Monica Vargas'),
(29, 'Lucas Molina'),
(30, 'Gabriela Paz'),
(31, 'Manuel Rivera'),
(32, 'Ximena Soto'),
(33, 'Sebastian Jimenez'),
(34, 'Fabiola Carrillo'),
(35, 'Eduardo Guerra'),
(36, 'Rocio Salinas'),
(37, 'Gonzalo Acosta'),
(38, 'Daniela Fierro'),
(39, 'Felipe Montes'),
(40, 'Luciana Campos');

-- Inserts para la tabla mascota
INSERT INTO mascota (idMascota, nombre, descripcion, idOwner) VALUES
(1, 'Bobby', 'Perro labrador amistoso', 1),
(2, 'Mimi', 'Gata siamesa tranquila', 2),
(3, 'Rex', 'Pastor alemán protector', 3),
(4, 'Nina', 'Conejo blanco juguetón', 4),
(5, 'Tommy', 'Perico parlanchín', 5),
(6, 'Luna', 'Gata negra curiosa', 6),
(7, 'Rocky', 'Bulldog energético', 7),
(8, 'Coco', 'Hámster dorado activo', 8),
(9, 'Toby', 'Perro salchicha amigable', 9),
(10, 'Bella', 'Gata persa elegante', 10),
(11, 'Max', 'Golden retriever fiel', 11),
(12, 'Simba', 'Gato naranja dormilón', 12),
(13, 'Daisy', 'Caniche juguetona', 13),
(14, 'Nico', 'Tortuga longeva', 14),
(15, 'Kiwi', 'Loro inteligente', 15),
(16, 'Milo', 'Beagle curioso', 16),
(17, 'Pelusa', 'Conejo gris simpático', 17),
(18, 'Lola', 'Gata atigrada juguetona', 18),
(19, 'Bruno', 'Boxer fuerte', 19),
(20, 'Chispa', 'Perrito mestizo vivaz', 20),
(21, 'Princesa', 'Chihuahua mimada', 21),
(22, 'Oreo', 'Gato blanco y negro travieso', 22),
(23, 'Maya', 'Perra border collie astuta', 23),
(24, 'Pancho', 'Gato gordo perezoso', 24),
(25, 'Flash', 'Galgo veloz', 25),
(26, 'Pipo', 'Cacatúa ruidosa', 26),
(27, 'Tasha', 'Gata esfinge curiosa', 27),
(28, 'Bolt', 'Perro husky fuerte', 28),
(29, 'Chico', 'Perro callejero leal', 29),
(30, 'Kira', 'Husky siberiano hermoso', 30);