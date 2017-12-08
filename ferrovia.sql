SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `engenheiro` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `estacao` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL,
  `endereco` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tipo_estacao` (
  `id` tinyint(3) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tipo_trem` (
  `id` tinyint(3) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `trem` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `engenheiro` smallint(5) UNSIGNED NOT NULL,
  `tipo` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `trem_estacao` (
  `id_trem` smallint(5) UNSIGNED NOT NULL,
  `id_estacao` smallint(5) UNSIGNED NOT NULL,
  `hora_ida` time NOT NULL,
  `hora_volta` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `engenheiro`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `estacao`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `tipo_estacao`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `tipo_trem`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `trem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `engenheiro` (`engenheiro`),
  ADD KEY `tipo` (`tipo`);

ALTER TABLE `trem_estacao`
  ADD KEY `id_trem` (`id_trem`),
  ADD KEY `id_estacao` (`id_estacao`);


ALTER TABLE `engenheiro`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `estacao`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `tipo_estacao`
  MODIFY `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `tipo_trem`
  MODIFY `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `trem`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE `trem`
  ADD CONSTRAINT `trem_ibfk_1` FOREIGN KEY (`engenheiro`) REFERENCES `engenheiro` (`id`),
  ADD CONSTRAINT `trem_ibfk_2` FOREIGN KEY (`tipo`) REFERENCES `tipo_trem` (`id`);

ALTER TABLE `trem_estacao`
  ADD CONSTRAINT `trem_estacao_ibfk_1` FOREIGN KEY (`id_trem`) REFERENCES `trem` (`id`),
  ADD CONSTRAINT `trem_estacao_ibfk_2` FOREIGN KEY (`id_estacao`) REFERENCES `estacao` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
