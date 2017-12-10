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

INSERT INTO `engenheiro` (`id`, `nome`) VALUES
(1, 'Joao Bolsson'),
(2, 'Leonardo'),
(3, 'Henry'),
(4, 'Daniele'),
(5, 'Renata');

CREATE TABLE `estacao` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL,
  `endereco` varchar(50) NOT NULL,
  `tipo` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `estacao` (`id`, `nome`, `endereco`, `tipo`) VALUES
(1, 'Estacao Legal', 'Rua dos Bobos 0', 1),
(2, 'Estacao da Gare', 'Rua Joao das Cove', 2),
(3, 'Estacao Huahau', 'Rua Andre Marques', 2),
(4, 'Estacao Heheheh', 'Rua Silva Jardim', 2),
(5, 'Estacao Ola mundo', 'Av Rio Branco', 1);

CREATE TABLE `tipo_estacao` (
  `id` tinyint(3) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `tipo_estacao` (`id`, `nome`) VALUES
(1, 'Expressa'),
(2, 'Local');

CREATE TABLE `tipo_trem` (
  `id` tinyint(3) UNSIGNED NOT NULL,
  `nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `tipo_trem` (`id`, `nome`) VALUES
(1, 'Expresso'),
(2, 'Local');

CREATE TABLE `trem` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `engenheiro` smallint(5) UNSIGNED NOT NULL,
  `tipo` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `trem` (`id`, `engenheiro`, `tipo`) VALUES
(2, 1, 1),
(7, 5, 1),
(8, 4, 1),
(11, 1, 2),
(12, 1, 1),
(13, 2, 2);

CREATE TABLE `trem_estacao` (
  `id_trem` smallint(5) UNSIGNED NOT NULL,
  `id_estacao` smallint(5) UNSIGNED NOT NULL,
  `hora_ida` time NOT NULL,
  `hora_volta` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `trem_estacao` (`id_trem`, `id_estacao`, `hora_ida`, `hora_volta`) VALUES
(2, 1, '14:30:00', '15:30:00'),
(2, 5, '16:30:00', '17:30:00'),
(11, 1, '14:00:00', '15:00:00'),
(11, 2, '14:10:00', '15:10:00');


ALTER TABLE `engenheiro`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `estacao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tipo` (`tipo`);

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
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
ALTER TABLE `estacao`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
ALTER TABLE `tipo_estacao`
  MODIFY `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE `tipo_trem`
  MODIFY `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE `trem`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `estacao`
  ADD CONSTRAINT `estacao_ibfk_1` FOREIGN KEY (`tipo`) REFERENCES `tipo_estacao` (`id`);

ALTER TABLE `trem`
  ADD CONSTRAINT `trem_ibfk_1` FOREIGN KEY (`engenheiro`) REFERENCES `engenheiro` (`id`),
  ADD CONSTRAINT `trem_ibfk_2` FOREIGN KEY (`tipo`) REFERENCES `tipo_trem` (`id`);

ALTER TABLE `trem_estacao`
  ADD CONSTRAINT `trem_estacao_ibfk_1` FOREIGN KEY (`id_trem`) REFERENCES `trem` (`id`),
  ADD CONSTRAINT `trem_estacao_ibfk_2` FOREIGN KEY (`id_estacao`) REFERENCES `estacao` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
