-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jun 22, 2020 at 01:29 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `SistemasDistribuidos`
--
CREATE DATABASE IF NOT EXISTS `SistemasDistribuidos` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `SistemasDistribuidos`;

-- --------------------------------------------------------

--
-- Table structure for table `Companias`
--

CREATE TABLE `Companias` (
  `RFC` varchar(10) NOT NULL,
  `TotalAcc` int(11) NOT NULL,
  `AccDisponibles` int(11) NOT NULL,
  `ValorAcc` float(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Companias`
--

INSERT INTO `Companias` (`RFC`, `TotalAcc`, `AccDisponibles`, `ValorAcc`) VALUES
('A123456789', 500, 25, 1.50),
('A1B1C1D1E1', 160, 24, 13.90),
('ABCDEFGH12', 250, 12, 45.70),
('MBYLC4EVER', 10000, 2300, 69.12),
('SEIRI12345', 5000, 156, 340.12);

-- --------------------------------------------------------

--
-- Table structure for table `Province`
--

CREATE TABLE `Province` (
  `Id` int(11) NOT NULL,
  `ShortName` text NOT NULL,
  `Name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Transacciones`
--

CREATE TABLE `Transacciones` (
  `RFCUsuario` varchar(10) NOT NULL,
  `RFCCompania` varchar(10) NOT NULL,
  `FechaHora` datetime DEFAULT CURRENT_TIMESTAMP,
  `Acciones` int(11) NOT NULL,
  `PrecioAccion` float(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Usuarios`
--

CREATE TABLE `Usuarios` (
  `RFCUsuario` varchar(10) NOT NULL,
  `RFCCompania` varchar(10) NOT NULL,
  `NumeroAcc` int(11) NOT NULL,
  `UltPrecCompra` float(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Usuarios`
--

INSERT INTO `Usuarios` (`RFCUsuario`, `RFCCompania`, `NumeroAcc`, `UltPrecCompra`) VALUES
('CMMR080397', 'SEIRI12345', 311, 100.00),
('JAQS030697', 'ABCDEFGH12', 200, 245.12),
('JCMS280696', 'A123456789', 50, 12.40),
('JECV060998', 'A1B1C1D1E1', 21, 18.40),
('MOBC220697', 'MBYLC4EVER', 1000, 153.98);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Companias`
--
ALTER TABLE `Companias`
  ADD PRIMARY KEY (`RFC`);

--
-- Indexes for table `Province`
--
ALTER TABLE `Province`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `Transacciones`
--
ALTER TABLE `Transacciones`
  ADD KEY `RFCCompania` (`RFCCompania`),
  ADD KEY `RFCUsuario` (`RFCUsuario`);

--
-- Indexes for table `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD PRIMARY KEY (`RFCUsuario`),
  ADD KEY `RFCCompania` (`RFCCompania`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Transacciones`
--
ALTER TABLE `Transacciones`
  ADD CONSTRAINT `compa` FOREIGN KEY (`RFCCompania`) REFERENCES `Companias` (`RFC`),
  ADD CONSTRAINT `user` FOREIGN KEY (`RFCUsuario`) REFERENCES `Usuarios` (`RFCUsuario`);

--
-- Constraints for table `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD CONSTRAINT `comp` FOREIGN KEY (`RFCCompania`) REFERENCES `Companias` (`RFC`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
