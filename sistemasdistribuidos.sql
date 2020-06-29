-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-06-2020 a las 22:12:24
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemasdistribuidos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `companias`
--

CREATE TABLE `companias` (
  `RFC` varchar(10) NOT NULL,
  `TotalAcc` int(11) NOT NULL,
  `AccDisponibles` int(11) NOT NULL,
  `ValorAcc` float(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `companias`
--

INSERT INTO `companias` (`RFC`, `TotalAcc`, `AccDisponibles`, `ValorAcc`) VALUES
('A123456789', -11, 10, 15.00),
('A1B1C1D1E1', 160, 24, 13.90),
('ABCDEFGH12', 250, 12, 45.70),
('MBYLC4EVER', 10000, 2300, 69.12),
('SEIRI12345', 5000, 156, 340.12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `province`
--

CREATE TABLE `province` (
  `Id` int(11) NOT NULL,
  `ShortName` text NOT NULL,
  `Name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transacciones`
--

CREATE TABLE `transacciones` (
  `RFCUsuario` varchar(10) NOT NULL,
  `RFCCompania` varchar(10) NOT NULL,
  `FechaHora` datetime DEFAULT current_timestamp(),
  `Acciones` int(11) NOT NULL,
  `PrecioAccion` float(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `transacciones`
--

INSERT INTO `transacciones` (`RFCUsuario`, `RFCCompania`, `FechaHora`, `Acciones`, `PrecioAccion`) VALUES
('CMMR080397', 'A1B1C1D1E1', '2020-06-25 18:52:38', 90, 56.00),
('CMMR080397', 'A1B1C1D1E1', '2020-06-25 18:52:45', 90, 56.50),
('CMMR080397', 'A123456789', '2020-06-26 01:59:47', 12, 12.00),
('CMMR080397', 'A123456789', '2020-06-29 11:41:08', -11, 1.20),
('CMMR080397', 'A123456789', '2020-06-29 11:55:04', -11, 1.20),
('CMMR080397', 'A123456789', '2020-06-29 12:13:46', -10, 0.80),
('CMMR080397', 'A123456789', '2020-06-29 12:16:17', 30, 30.00),
('CMMR080397', 'A123456789', '2020-06-29 15:09:37', -5, 15.00);

--
-- Disparadores `transacciones`
--
DELIMITER $$
CREATE TRIGGER `nueva_transaccion` AFTER INSERT ON `transacciones` FOR EACH ROW UPDATE companias SET companias.AccDisponibles= companias.AccDisponibles - NEW.acciones, companias.ValorAcc=NEW.PrecioAccion WHERE companias.RFC=NEW.RFCCompania
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `RFCUsuario` varchar(10) NOT NULL,
  `RFCCompania` varchar(10) NOT NULL,
  `NumeroAcc` int(11) NOT NULL,
  `UltPrecCompra` float(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`RFCUsuario`, `RFCCompania`, `NumeroAcc`, `UltPrecCompra`) VALUES
('CMMR080397', 'SEIRI12345', 311, 100.00),
('JAQS030697', 'ABCDEFGH12', 200, 245.12),
('JCMS280696', 'A123456789', 50, 12.40),
('JECV060998', 'A1B1C1D1E1', 21, 18.40),
('MOBC220697', 'MBYLC4EVER', 1000, 153.98);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `companias`
--
ALTER TABLE `companias`
  ADD PRIMARY KEY (`RFC`);

--
-- Indices de la tabla `province`
--
ALTER TABLE `province`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `transacciones`
--
ALTER TABLE `transacciones`
  ADD KEY `RFCCompania` (`RFCCompania`),
  ADD KEY `RFCUsuario` (`RFCUsuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`RFCUsuario`),
  ADD KEY `RFCCompania` (`RFCCompania`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `transacciones`
--
ALTER TABLE `transacciones`
  ADD CONSTRAINT `compa` FOREIGN KEY (`RFCCompania`) REFERENCES `companias` (`RFC`),
  ADD CONSTRAINT `user` FOREIGN KEY (`RFCUsuario`) REFERENCES `usuarios` (`RFCUsuario`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `comp` FOREIGN KEY (`RFCCompania`) REFERENCES `companias` (`RFC`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
