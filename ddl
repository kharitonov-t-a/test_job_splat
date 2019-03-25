CREATE SCHEMA `banner` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `banner`.`Banner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `imgSrc` VARCHAR(120) NOT NULL,
  `width` INT NULL,
  `height` INT NULL,
  `targetUrl` VARCHAR(45) NULL,
  `langId` INT NULL,
  `priority` INT NULL,
  `activity` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`));

CREATE TABLE `banner`.`Locale` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `activity` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`));

CREATE TABLE `banner`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(120) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `activity` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));

CREATE TABLE `banner`.`Audit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idBanner` INT NOT NULL,
  `idUser` INT NOT NULL,
  `crud` TINYINT(1) NOT NULL,
  `description` VARCHAR(300) NULL,
  `date` DATETIME NOT NULL,
  PRIMARY KEY (`id`));