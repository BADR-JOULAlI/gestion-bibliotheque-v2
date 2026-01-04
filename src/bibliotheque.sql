-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2026 at 10:12 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bibliotheque`
--

-- --------------------------------------------------------

--
-- Table structure for table `emprunts`
--

CREATE TABLE `emprunts` (
                            `id` int(11) NOT NULL,
                            `livre_id` int(11) NOT NULL,
                            `membre_id` int(11) NOT NULL,
                            `date_emprunt` date NOT NULL,
                            `date_retour_prevue` date NOT NULL,
                            `date_retour_effective` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `emprunts`
--

INSERT INTO `emprunts` (`id`, `livre_id`, `membre_id`, `date_emprunt`, `date_retour_prevue`, `date_retour_effective`) VALUES
                                                                                                                          (1, 1, 1, '2026-01-04', '2026-01-07', NULL),
                                                                                                                          (2, 2, 1, '2026-01-04', '2026-01-07', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `livres`
--

CREATE TABLE `livres` (
                          `id` int(11) NOT NULL,
                          `titre` varchar(255) NOT NULL,
                          `auteur` varchar(255) NOT NULL,
                          `isbn` varchar(50) DEFAULT NULL,
                          `annee_publication` int(11) DEFAULT NULL,
                          `disponible` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `livres`
--

INSERT INTO `livres` (`id`, `titre`, `auteur`, `isbn`, `annee_publication`, `disponible`) VALUES
                                                                                              (1, 'Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 1943, 1),
                                                                                              (2, 'L’Étranger', 'Albert Camus', '9782070360024', 1942, 0),
                                                                                              (3, '1984', 'George Orwell', '9780451524935', 1949, 1);

-- --------------------------------------------------------

--
-- Table structure for table `membres`
--

CREATE TABLE `membres` (
                           `id` int(11) NOT NULL,
                           `nom` varchar(100) NOT NULL,
                           `prenom` varchar(100) NOT NULL,
                           `email` varchar(150) NOT NULL,
                           `telephone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `membres`
--

INSERT INTO `membres` (`id`, `nom`, `prenom`, `email`, `telephone`) VALUES
    (1, 'dfd', 'ddd', 'ba@gmail.com', 'o4-495');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `emprunts`
--
ALTER TABLE `emprunts`
    ADD PRIMARY KEY (`id`),
  ADD KEY `livre_id` (`livre_id`),
  ADD KEY `membre_id` (`membre_id`);

--
-- Indexes for table `livres`
--
ALTER TABLE `livres`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `membres`
--
ALTER TABLE `membres`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `emprunts`
--
ALTER TABLE `emprunts`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `livres`
--
ALTER TABLE `livres`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `membres`
--
ALTER TABLE `membres`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `emprunts`
--
ALTER TABLE `emprunts`
    ADD CONSTRAINT `emprunts_ibfk_1` FOREIGN KEY (`livre_id`) REFERENCES `livres` (`id`),
  ADD CONSTRAINT `emprunts_ibfk_2` FOREIGN KEY (`membre_id`) REFERENCES `membres` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
