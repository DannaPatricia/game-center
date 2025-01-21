-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-01-2025 a las 10:13:39
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `game_center`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` int(11) NOT NULL,
  `tipo_producto` enum('juego','consola') NOT NULL,
  `id_producto` int(11) NOT NULL,
  `nombre_producto` varchar(255) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 1,
  `fecha_agregado` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consolas`
--

CREATE TABLE `consolas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `potencia_cpu` decimal(5,2) DEFAULT NULL,
  `potencia_gpu` decimal(5,2) DEFAULT NULL,
  `compania` varchar(50) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `unidades_disponibles` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consolas`
--

INSERT INTO `consolas` (`id`, `nombre`, `potencia_cpu`, `potencia_gpu`, `compania`, `precio`, `unidades_disponibles`) VALUES
(1, 'Xbox One', 1.75, 1.31, 'Microsoft', 299.99, 49),
(2, 'Xbox Series X', 3.80, 12.00, 'Microsoft', 499.99, 97),
(3, 'Xbox Series S', 3.60, 4.00, 'Microsoft', 299.99, 80),
(4, 'Nintendo Switch', 0.10, 0.40, 'Nintendo', 299.99, 202),
(5, 'Nintendo Switch Lite', 0.10, 0.40, 'Nintendo', 199.99, 150),
(6, 'PS4', 1.60, 1.84, 'Sony', 349.99, 60),
(7, 'PS5 con CD', 3.50, 10.30, 'Sony', 499.99, 120),
(8, 'PS5 sin CD', 3.50, 10.30, 'Sony', 399.99, 90),
(10, 'wii', 3.50, 10.00, 'Nintendo', 20.00, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegos`
--

CREATE TABLE `juegos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `plataforma` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `compania_desarrolladora` varchar(50) DEFAULT NULL,
  `genero` varchar(50) DEFAULT NULL,
  `puntuacion_metacritic` decimal(5,2) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `unidades_disponibles` int(10) UNSIGNED NOT NULL,
  `consola_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `juegos`
--

INSERT INTO `juegos` (`id`, `nombre`, `plataforma`, `compania_desarrolladora`, `genero`, `puntuacion_metacritic`, `precio`, `unidades_disponibles`, `consola_id`) VALUES
(1, 'Halo Infinite', 'Xbox One', 'Microsoft', 'Shooter', 9.99, 59.99, 102, 1),
(2, 'Gears 5', 'Xbox One', 'Microsoft', 'Acción', 9.99, 49.99, 117, 1),
(3, 'Forza Horizon 4', 'Xbox One', 'Microsoft', 'Carreras', 9.99, 59.99, 80, 1),
(4, 'Minecraft', 'Xbox One', 'Mojang', 'Aventura', 9.99, 29.99, 205, 1),
(5, 'FIFA 21', 'Xbox One', 'EA Sports', 'Deportes', 9.99, 49.99, 148, 1),
(6, 'The Outer Worlds', 'Xbox One', 'Obsidian Entertainment', 'RPG', 9.99, 49.99, 140, 1),
(7, 'Metro Exodus', 'Xbox One', '4A Games', 'Acción', 9.99, 39.99, 60, 1),
(8, 'Apex Legends', 'Xbox One', 'Respawn Entertainment', 'Shooter', 9.99, 0.00, 200, 1),
(9, 'Red Dead Redemption 2', 'Xbox One', 'Rockstar Games', 'Aventura', 9.99, 59.99, 158, 1),
(10, 'Call of Duty: Modern Warfare', 'Xbox One', 'Activision', 'Shooter', 9.99, 59.99, 110, 1),
(11, 'Forza Horizon 5', 'Xbox Series X', 'Microsoft', 'Carreras', 9.99, 69.99, 120, 2),
(12, 'Halo Infinite', 'Xbox Series X', 'Microsoft', 'Shooter', 9.99, 59.99, 100, 2),
(13, 'Gears 5', 'Xbox Series X', 'Microsoft', 'Acción', 9.99, 49.99, 110, 2),
(14, 'Cyberpunk 2077', 'Xbox Series X', 'CD Projekt Red', 'RPG', 9.99, 59.99, 70, 2),
(15, 'FIFA 22', 'Xbox Series X', 'EA Sports', 'Deportes', 9.99, 59.99, 149, 2),
(16, 'Assassin’s Creed Valhalla', 'Xbox Series X', 'Ubisoft', 'Aventura', 9.99, 69.99, 130, 2),
(17, 'Battlefield V', 'Xbox Series X', 'DICE', 'Shooter', 9.99, 49.99, 80, 2),
(18, 'Red Dead Redemption 2', 'Xbox Series X', 'Rockstar Games', 'Aventura', 9.99, 59.99, 160, 2),
(19, 'The Witcher 3: Wild Hunt', 'Xbox Series X', 'CD Projekt Red', 'RPG', 9.99, 49.99, 90, 2),
(20, 'Doom Eternal', 'Xbox Series X', 'id Software', 'Acción', 9.99, 59.99, 100, 2),
(21, 'Forza Horizon 5', 'Xbox Series S', 'Microsoft', 'Carreras', 9.99, 69.99, 120, 3),
(22, 'Halo Infinite', 'Xbox Series S', 'Microsoft', 'Shooter', 9.99, 59.99, 100, 3),
(23, 'Gears 5', 'Xbox Series S', 'Microsoft', 'Acción', 9.99, 49.99, 110, 3),
(24, 'Cyberpunk 2077', 'Xbox Series S', 'CD Projekt Red', 'RPG', 9.99, 59.99, 70, 3),
(25, 'FIFA 22', 'Xbox Series S', 'EA Sports', 'Deportes', 9.99, 59.99, 150, 3),
(26, 'Assassin’s Creed Valhalla', 'Xbox Series S', 'Ubisoft', 'Aventura', 9.99, 69.99, 130, 3),
(27, 'Battlefield V', 'Xbox Series S', 'DICE', 'Shooter', 9.99, 49.99, 80, 3),
(28, 'Red Dead Redemption 2', 'Xbox Series S', 'Rockstar Games', 'Aventura', 9.99, 59.99, 160, 3),
(29, 'The Witcher 3: Wild Hunt', 'Xbox Series S', 'CD Projekt Red', 'RPG', 9.99, 49.99, 90, 3),
(30, 'Doom Eternal', 'Xbox Series S', 'id Software', 'Acción', 9.99, 59.99, 100, 3),
(31, 'The Legend of Zelda: Breath of the Wild', 'Nintendo Switch', 'Nintendo', 'Aventura', 9.99, 59.99, 249, 4),
(32, 'Super Mario Odyssey', 'Nintendo Switch', 'Nintendo', 'Aventura', 9.99, 49.99, 200, 4),
(33, 'Animal Crossing: New Horizons', 'Nintendo Switch', 'Nintendo', 'Simulación', 9.99, 59.99, 150, 4),
(34, 'Mario Kart 8 Deluxe', 'Nintendo Switch', 'Nintendo', 'Carreras', 9.99, 49.99, 185, 4),
(35, 'Splatoon 2', 'Nintendo Switch', 'Nintendo', 'Shooter', 9.99, 49.99, 120, 4),
(36, 'Super Smash Bros. Ultimate', 'Nintendo Switch', 'Nintendo', 'Lucha', 9.99, 59.99, 140, 4),
(37, 'Fire Emblem: Three Houses', 'Nintendo Switch', 'Nintendo', 'RPG', 9.99, 59.99, 110, 4),
(38, 'Pokémon Sword and Shield', 'Nintendo Switch', 'Nintendo', 'RPG', 9.99, 59.99, 200, 4),
(39, 'Luigi’s Mansion 3', 'Nintendo Switch', 'Nintendo', 'Aventura', 9.99, 49.99, 150, 4),
(40, 'Xenoblade Chronicles 2', 'Nintendo Switch', 'Monolith Soft', 'RPG', 9.99, 59.99, 90, 4),
(41, 'The Legend of Zelda: Breath of the Wild', 'Nintendo Switch Lite', 'Nintendo', 'Aventura', 9.99, 59.99, 250, 5),
(42, 'Super Mario Odyssey', 'Nintendo Switch Lite', 'Nintendo', 'Aventura', 9.99, 49.99, 200, 5),
(43, 'Animal Crossing: New Horizons', 'Nintendo Switch Lite', 'Nintendo', 'Simulación', 9.99, 59.99, 150, 5),
(44, 'Mario Kart 8 Deluxe', 'Nintendo Switch Lite', 'Nintendo', 'Carreras', 9.99, 49.99, 180, 5),
(45, 'Splatoon 2', 'Nintendo Switch Lite', 'Nintendo', 'Shooter', 9.99, 49.99, 120, 5),
(46, 'Super Smash Bros. Ultimate', 'Nintendo Switch Lite', 'Nintendo', 'Lucha', 9.99, 59.99, 140, 5),
(47, 'Fire Emblem: Three Houses', 'Nintendo Switch Lite', 'Nintendo', 'RPG', 9.99, 59.99, 110, 5),
(48, 'Pokémon Sword and Shield', 'Nintendo Switch Lite', 'Nintendo', 'RPG', 9.99, 59.99, 200, 5),
(49, 'Luigi’s Mansion 3', 'Nintendo Switch Lite', 'Nintendo', 'Aventura', 9.99, 49.99, 150, 5),
(50, 'Xenoblade Chronicles 2', 'Nintendo Switch Lite', 'Monolith Soft', 'RPG', 9.99, 59.99, 90, 5),
(51, 'God of War', 'PS4', 'Santa Monica Studio', 'Acción', 9.99, 49.99, 150, 6),
(52, 'Uncharted 4: A Thief’s End', 'PS4', 'Naughty Dog', 'Aventura', 9.99, 49.99, 180, 6),
(53, 'Final Fantasy VII Remake', 'PS4', 'Square Enix', 'RPG', 9.99, 59.99, 130, 6),
(54, 'Horizon Zero Dawn', 'PS4', 'Guerrilla Games', 'Aventura', 9.99, 59.99, 140, 6),
(55, 'Red Dead Redemption 2', 'PS4', 'Rockstar Games', 'Aventura', 9.99, 59.99, 160, 6),
(56, 'FIFA 22', 'PS4', 'EA Sports', 'Deportes', 9.99, 59.99, 120, 6),
(57, 'Call of Duty: Modern Warfare', 'PS4', 'Activision', 'Shooter', 9.99, 59.99, 110, 6),
(58, 'Gran Turismo Sport', 'PS4', 'Polyphony Digital', 'Carreras', 9.99, 39.99, 90, 6),
(59, 'The Last of Us Part II', 'PS4', 'Naughty Dog', 'Aventura', 9.99, 59.99, 140, 6),
(60, 'Spider-Man', 'PS4', 'Insomniac Games', 'Acción', 9.99, 49.99, 100, 6),
(61, 'God of War', 'PS5 con CD', 'Santa Monica Studio', 'Acción', 9.99, 49.99, 150, 7),
(62, 'Uncharted 4: A Thief’s End', 'PS5 con CD', 'Naughty Dog', 'Aventura', 9.99, 49.99, 180, 7),
(63, 'Final Fantasy VII Remake', 'PS5 con CD', 'Square Enix', 'RPG', 9.99, 59.99, 130, 7),
(64, 'Horizon Zero Dawn', 'PS5 con CD', 'Guerrilla Games', 'Aventura', 9.99, 59.99, 140, 7),
(65, 'Red Dead Redemption 2', 'PS5 con CD', 'Rockstar Games', 'Aventura', 9.99, 59.99, 160, 7),
(66, 'FIFA 22', 'PS5 con CD', 'EA Sports', 'Deportes', 9.99, 59.99, 120, 7),
(67, 'Call of Duty: Modern Warfare', 'PS5 con CD', 'Activision', 'Shooter', 9.99, 59.99, 110, 7),
(68, 'Gran Turismo 7', 'PS5 con CD', 'Polyphony Digital', 'Carreras', 9.99, 69.99, 80, 7),
(69, 'The Last of Us Part II', 'PS5 con CD', 'Naughty Dog', 'Aventura', 9.99, 59.99, 140, 7),
(70, 'Spider-Man: Miles Morales', 'PS5 con CD', 'Insomniac Games', 'Acción', 9.99, 49.99, 100, 7),
(71, 'God of War', 'PS5 sin CD', 'Santa Monica Studio', 'Acción', 9.99, 49.99, 150, 8),
(72, 'Uncharted 4: A Thief’s End', 'PS5 sin CD', 'Naughty Dog', 'Aventura', 9.99, 49.99, 180, 8),
(73, 'Final Fantasy VII Remake', 'PS5 sin CD', 'Square Enix', 'RPG', 9.99, 59.99, 130, 8),
(74, 'Horizon Zero Dawn', 'PS5 sin CD', 'Guerrilla Games', 'Aventura', 9.99, 59.99, 140, 8),
(75, 'Red Dead Redemption 2', 'PS5 sin CD', 'Rockstar Games', 'Aventura', 9.99, 59.99, 160, 8),
(76, 'FIFA 22', 'PS5 sin CD', 'EA Sports', 'Deportes', 9.99, 59.99, 120, 8),
(77, 'Call of Duty: Modern Warfare', 'PS5 sin CD', 'Activision', 'Shooter', 9.99, 59.99, 110, 8),
(78, 'Gran Turismo 7', 'PS5 sin CD', 'Polyphony Digital', 'Carreras', 9.99, 69.99, 80, 8),
(79, 'The Last of Us Part II', 'PS5 sin CD', 'Naughty Dog', 'Aventura', 9.99, 59.99, 140, 8),
(80, 'Spider-Man: Miles Morales', 'PS5 sin CD', 'Insomniac Games', 'Acción', 9.99, 49.99, 100, 8),
(81, 'Mario Bros', 'wii', 'Nintendo', 'Plataformas', 10.00, 20.00, 24, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` enum('administrador','cliente') NOT NULL DEFAULT 'cliente',
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `nombre_usuario`, `contrasena`, `rol`, `email`) VALUES
(1, 'elAdministrador', 'primero', 'admin', '1234', 'administrador', 'admin1234@gmail.com'),
(2, 'elUsuario', 'primero', 'user1', 'user1234', 'cliente', 'user1@gmail.com'),
(3, 'elUsuario2', 'segundo', 'user2', '1234', 'cliente', 'user2@gmail.com'),
(4, 'elUsuario3', 'tercero', 'user3', 'user1234', 'cliente', 'user3@gmail.com'),
(5, 'elUsuario4', 'cuarto', 'user4', '4user1234', 'cliente', 'user4@gmail.com'),
(6, 'elUsuario5', 'quinto', 'user5', '5user1234', 'cliente', 'user5@gmail.com'),
(7, 'elUsuario6', 'sexto', 'user6', '6user1234', 'cliente', 'user6@gmail.com'),
(8, 'elUsuario7', 'septimo', 'user7', '7user1234', 'cliente', 'user7@gmail.com'),
(9, 'elUsuario8', 'octavo', 'user8', '8user1234', 'cliente', 'user8@gmail.com'),
(10, 'elUsuario9', 'noveno', 'user9', '9user1234', 'cliente', 'user9@gmail.com'),
(11, 'elUsuario10', 'decimo', 'user10', '10user1234', 'cliente', 'user10@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_carrito_consola` (`id_producto`),
  ADD KEY `fk_carrito_usuario` (`id_usuario`);

--
-- Indices de la tabla `consolas`
--
ALTER TABLE `consolas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `juegos`
--
ALTER TABLE `juegos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `consola_id` (`consola_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre_usuario` (`nombre_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `consolas`
--
ALTER TABLE `consolas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `juegos`
--
ALTER TABLE `juegos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `fk_carrito_consola` FOREIGN KEY (`id_producto`) REFERENCES `consolas` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_carrito_juego` FOREIGN KEY (`id_producto`) REFERENCES `juegos` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_carrito_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `juegos`
--
ALTER TABLE `juegos`
  ADD CONSTRAINT `fk_consola_id` FOREIGN KEY (`consola_id`) REFERENCES `consolas` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `juegos_ibfk_1` FOREIGN KEY (`consola_id`) REFERENCES `consolas` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
