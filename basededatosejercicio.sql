create database ejercicio;

SET FOREIGN_KEY_CHECKS=1;

use  ejercicio;

DROP TABLE IF EXISTS `persona`;

CREATE TABLE `persona` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `genero` varchar(45) NOT NULL,
  `edad` int(10) unsigned NOT NULL,
  `identificacion` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `IdCliente` int(10)  NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(45) NOT NULL,
  `estado` boolean default true,
  `IdPersona` int(10) DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  FOREIGN KEY (IdPersona)
      REFERENCES persona(id) on delete cascade
) ENGINE=InnoDB AUTO_INCREMENT=519 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `cuenta`;

CREATE TABLE `cuenta` (
  `NumeroCuenta` int(10) NOT NULL AUTO_INCREMENT,
  `tipocuenta` varchar(45)  NULL,
  `saldoinicial` decimal(10,2) NULL,
  `estado` boolean default true,
  `id_cliente` int(10) NOT NULL,
  PRIMARY KEY (`NumeroCuenta`),
  FOREIGN KEY (id_cliente)
      REFERENCES cliente(IdCliente) on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `movimientos`;

CREATE TABLE `movimientos` (
    `IdMovimiento` INT(10) NOT NULL AUTO_INCREMENT,
    `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `tipomovimiento` VARCHAR(45) NOT NULL,
    `valor` DECIMAL(10,2)  DEFAULT NULL,
    `saldo` DECIMAL(10,2) DEFAULT NULL,
    `idCuenta` INT(10)  NOT NULL,
    PRIMARY KEY (`IdMovimiento`),
    FOREIGN KEY (idCuenta)
        REFERENCES cuenta (NumeroCuenta)
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;



