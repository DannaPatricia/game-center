-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 20-01-2025 a las 21:16:34
-- Versión del servidor: 8.0.40-0ubuntu0.22.04.1
-- Versión de PHP: 8.1.2-1ubuntu2.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cursoscp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cursos`
--

CREATE TABLE `cursos` (
  `codigo` int NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `abierto` tinyint(1) DEFAULT '1',
  `numeroplazas` int DEFAULT NULL,
  `plazoinscripcion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cursos`
--

INSERT INTO `cursos` (`codigo`, `nombre`, `abierto`, `numeroplazas`, `plazoinscripcion`) VALUES
(1, 'JavaScript', 1, 27, '2025-05-30'),
(2, 'PHP', 0, 2, '2024-05-08'),
(3, 'Bases de Datos', 1, 19, '2025-07-10'),
(4, 'Desarrollo Web', 1, 20, '2025-08-01'),
(5, 'Programación en Java', 1, 15, '2025-06-25'),
(6, 'Ciberseguridad', 1, 9, '2025-07-20'),
(7, 'Diseño Web', 0, 10, '2025-06-30'),
(8, 'Frameworks Web', 1, 18, '2025-09-10'),
(9, 'Machine Learning', 1, 10, '2025-02-05'),
(11, 'MySQL', 1, 20, '2025-01-16');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitantes`
--

CREATE TABLE `solicitantes` (
  `dni` char(9) NOT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `codigocentro` char(8) DEFAULT NULL,
  `coordinadortc` tinyint(1) DEFAULT NULL,
  `grupotc` tinyint(1) DEFAULT NULL,
  `nombregrupo` varchar(50) DEFAULT NULL,
  `pbilin` tinyint(1) DEFAULT NULL,
  `cargo` tinyint(1) DEFAULT NULL,
  `nombrecargo` varchar(15) DEFAULT NULL,
  `situacion` enum('activo','inactivo') DEFAULT NULL,
  `fechanac` date DEFAULT NULL,
  `especialidad` varchar(50) DEFAULT NULL,
  `puntos` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `solicitantes`
--

INSERT INTO `solicitantes` (`dni`, `apellidos`, `nombre`, `telefono`, `correo`, `codigocentro`, `coordinadortc`, `grupotc`, `nombregrupo`, `pbilin`, `cargo`, `nombrecargo`, `situacion`, `fechanac`, `especialidad`, `puntos`) VALUES
('12345678A', 'admin', 'admin', '143569764', NULL, '012A', 0, 1, 'informatica', 1, 0, 'informatica', 'inactivo', '2004-11-18', 'dam', 7),
('12345678B', 'elUsuario1', 'Elusuario', '987654321', 'user1@gmail.com', '012A', 1, 0, 'grupoA', 1, 1, '', 'inactivo', '2004-12-03', 'Dam', 8),
('38594400F', 'paca', 'Paca', '339584712', 'user9@gmail.com', '012A', 0, 1, 'grupoC', 1, 1, 'secretario', 'inactivo', '2004-12-29', 'dam', 9),
('38919977Y', 'Sal', 'Karen', '992864773', 'user5@gmail.com', '012A', 1, 0, 'informatica', 0, 1, 'informatica', 'activo', '2004-12-07', 'dam', 6),
('40385018G', 'pepe', 'elUsuario3', '638204938', NULL, '012A', 0, 1, 'informatica', 0, 1, 'informatica', 'activo', '2004-11-16', 'dam', 5),
('98375649', 'pol', 'benito', '93827564', 'user6@gmail.com', '012A', 0, 1, 'grupob', 0, 1, '', 'inactivo', '2004-12-30', 'Dam', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudes`
--

CREATE TABLE `solicitudes` (
  `dni` char(9) NOT NULL,
  `codigocurso` int NOT NULL,
  `fechasolicitud` date DEFAULT NULL,
  `admitido` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `solicitudes`
--

INSERT INTO `solicitudes` (`dni`, `codigocurso`, `fechasolicitud`, `admitido`) VALUES
('12345678A', 1, '2025-01-12', NULL),
('12345678A', 3, '2025-01-12', 1),
('12345678A', 4, '2025-01-12', NULL),
('12345678A', 6, '2025-01-13', NULL),
('12345678A', 8, '2025-01-12', NULL),
('12345678B', 1, '2025-01-14', NULL),
('12345678B', 2, '2024-01-01', 1),
('38594400F', 1, '2025-01-14', NULL),
('38919977Y', 2, '2023-09-05', 1),
('38919977Y', 3, '2025-01-12', 1),
('38919977Y', 4, '2025-01-12', NULL),
('40385018G', 2, '2024-02-08', NULL),
('98375649', 2, '2024-03-08', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int NOT NULL,
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `apellidos` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `correo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `telefono` int NOT NULL,
  `nombre_usuario` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `clave` varchar(255) NOT NULL,
  `dni` char(9) DEFAULT NULL,
  `rol` enum('admin','cliente') DEFAULT 'cliente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `correo`, `telefono`, `nombre_usuario`, `clave`, `dni`, `rol`) VALUES
(1, 'Pepe', 'Juarez', 'user2@gmail.com', 123456789, 'user2', 'user21234', '12345678Z', 'cliente'),
(2, 'Elusuario', 'elUsuario1', 'user1@gmail.com', 987654321, 'user1', 'user1234', '12345678B', 'cliente'),
(7, 'admin', 'admin', 'admin@gmail.com', 143569764, 'admin', '1234', '12345678A', 'admin'),
(8, 'elUsuario3', 'pepe', 'user3@gmail.com', 638204938, 'user3', '3user1234', '40385018G', 'cliente'),
(9, 'Paco', 'juarez', 'user4@gmail.com', 482603366, 'user4', 'user41234', '98736290B', 'cliente'),
(10, 'Karen', 'Sal', 'user5@gmail.com', 992864773, 'user4', '4user1234', '38919977Y', 'cliente'),
(11, 'benito', 'pol', 'user6@gmail.com', 93827564, 'user6', '6user1234', '98375649', 'cliente'),
(12, 'maria', 'luisa', 'user7@gmail.com', 998670940, 'user7', 'maria', '875544331', 'cliente'),
(13, 'Antonio', 'Banderas', 'user8@gmail.com', 990387464, 'user8', '8user1234', '98675598B', 'cliente'),
(14, 'Paca', 'paca', 'user9@gmail.com', 339584712, 'user9', '9user1234', '38594400F', 'cliente'),
(15, 'Mari', 'Mari', 'user9@gmail.com', 333333334, 'user9', '1234', '38884777U', 'cliente');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cursos`
--
ALTER TABLE `cursos`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `solicitantes`
--
ALTER TABLE `solicitantes`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD PRIMARY KEY (`dni`,`codigocurso`),
  ADD KEY `solicitudes_ibfk_2` (`codigocurso`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dni` (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cursos`
--
ALTER TABLE `cursos`
  MODIFY `codigo` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `solicitantes`
--
ALTER TABLE `solicitantes`
  ADD CONSTRAINT `fk_solicitantes_usuarios` FOREIGN KEY (`dni`) REFERENCES `usuarios` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD CONSTRAINT `solicitudes_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `solicitantes` (`dni`) ON DELETE CASCADE,
  ADD CONSTRAINT `solicitudes_ibfk_2` FOREIGN KEY (`codigocurso`) REFERENCES `cursos` (`codigo`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
