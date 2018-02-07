-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema shop
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `shop` ;

-- -----------------------------------------------------
-- Schema shop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shop` DEFAULT CHARACTER SET utf8 ;
USE `shop` ;

-- -----------------------------------------------------
-- Table `shop`.`catalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`catalog` (
  `id_catalog` INT NOT NULL,
  `catalog_name` VARCHAR(70) NOT NULL,
  `parent` INT NULL,
  PRIMARY KEY (`id_catalog`))
ENGINE = InnoDB


-- -----------------------------------------------------
-- Table `shop`.`shop_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`shop_product` (
  `id_product` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `price` DOUBLE NOT NULL,
  `description` VARCHAR(500) NULL DEFAULT 'Very good product',
  `vendor` VARCHAR(100) NULL,
  `id_catalog` INT NOT NULL,
  PRIMARY KEY (`id_product`, `id_catalog`),
  CONSTRAINT `fk_shop_product_catalog1`
    FOREIGN KEY (`id_catalog`)
    REFERENCES `shop`.`catalog` (`id_catalog`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB


-- -----------------------------------------------------
-- Table `shop`.`shop_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`shop_order` (
  `id_order` INT NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор заказа.',
  `date` TIMESTAMP NOT NULL COMMENT 'Дата и время заказа.',
  `id_client` INT NOT NULL,
  `amount` INT NOT NULL COMMENT 'Количество заказанных единиц товара.',
  `id_status` INT NOT NULL,
  PRIMARY KEY (`id_order`, `id_client`, `id_status`))
ENGINE = InnoDB
COMMENT = 'Таблица описывает заказ товара. Характерезуется уникальным идентификатором заказа, стоимостью заказа, количеством заказанных единиц, датой и временем заказа, статусом заказа.';

CREATE INDEX `поиск заказа` ON `shop`.`shop_order` (`id_order` ASC);


-- -----------------------------------------------------
-- Table `shop`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`role` (
  `id_role` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(70) NOT NULL,
  PRIMARY KEY (`id_role`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`shop_client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`shop_client` (
  `id_client` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(70) NOT NULL,
  `lastname` VARCHAR(70) NOT NULL,
  `login` VARCHAR(40) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(120) NOT NULL,
  `balance` DOUBLE NULL DEFAULT 0,
  `banned` TINYINT(1) NULL DEFAULT 0,
  `id_role` INT NOT NULL,
  PRIMARY KEY (`id_client`, `id_role`),
  CONSTRAINT `fk_shop_client_role1`
    FOREIGN KEY (`id_role`)
    REFERENCES `shop`.`role` (`id_role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB


-- -----------------------------------------------------
-- Table `shop`.`basket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`basket` (
  `id_basket` INT NOT NULL,
  `id_client` INT NOT NULL,
  `id_product` INT NOT NULL,
  `amount` INT NULL,
  PRIMARY KEY (`id_client`, `id_product`, `id_basket`),
  CONSTRAINT `fk_basket_shop_client3`
    FOREIGN KEY (`id_product`)
    REFERENCES `shop`.`shop_product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_basket_shop_client1`
    FOREIGN KEY (`id_client`)
    REFERENCES `shop`.`shop_client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB


-- -----------------------------------------------------
-- Table `shop`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`status` (
  `id_status` INT NOT NULL,
  `name` VARCHAR(70) NOT NULL,
  PRIMARY KEY (`id_status`),
  CONSTRAINT `fk_status_shop_order1`
    FOREIGN KEY (`id_status`)
    REFERENCES `shop`.`shop_order` (`id_order`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`warehouse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`warehouse` (
  `id_wrehouse` INT NOT NULL,
  `id_product` INT NOT NULL,
  `amount` INT NULL,
  PRIMARY KEY (`id_wrehouse`, `id_product`),
  CONSTRAINT `fk_warehouse_shop_product1`
    FOREIGN KEY (`id_product`)
    REFERENCES `shop`.`shop_product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`order_items` (
  `id_order_iteams` INT NOT NULL,
  `id_product` INT NOT NULL,
  `id_order` INT NOT NULL,
  `amount` INT NULL,
  PRIMARY KEY (`id_order_iteams`, `id_product`, `id_order`),
  CONSTRAINT `fk_order_iteams_shop_order1`
    FOREIGN KEY (`id_order`)
    REFERENCES `shop`.`shop_order` (`id_order`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_iteams_shop_product1`
    FOREIGN KEY (`id_product`)
    REFERENCES `shop`.`shop_product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
