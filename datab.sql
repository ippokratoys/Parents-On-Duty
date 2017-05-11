-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Login` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pelates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pelates` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `points` VARCHAR(45) NULL,
  `Login_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Pelates_Login1_idx` (`Login_email` ASC),
  CONSTRAINT `fk_Pelates_Login1`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`LocationOwner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`LocationOwner` (
  `Name` VARCHAR(20) NULL,
  `Surname` VARCHAR(45) NULL,
  `Login_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Login_email`),
  CONSTRAINT `fk_LocationOwner_Login1`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Locations` (
  `id` INT NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(10) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `LocationOwner_Login_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `LocationOwner_Login_email`),
  UNIQUE INDEX `Locationscol_UNIQUE` (`Name` ASC),
  INDEX `fk_Locations_LocationOwner1_idx` (`LocationOwner_Login_email` ASC),
  CONSTRAINT `fk_Locations_LocationOwner1`
    FOREIGN KEY (`LocationOwner_Login_email`)
    REFERENCES `mydb`.`LocationOwner` (`Login_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Organiser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Organiser` (
  `police_id` CHAR(8) NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Surname` VARCHAR(45) NULL,
  `Birthdate` DATE NULL,
  `Login_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`police_id`),
  INDEX `fk_Organiser_Login1_idx` (`Login_email` ASC),
  CONSTRAINT `fk_Organiser_Login1`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EventGroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`EventGroup` (
  `idEventClass` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Type` VARCHAR(45) NULL,
  `Organiser_police_id` CHAR(8) NOT NULL,
  PRIMARY KEY (`idEventClass`),
  INDEX `fk_EventGroup_Organiser1_idx` (`Organiser_police_id` ASC),
  CONSTRAINT `fk_EventGroup_Organiser1`
    FOREIGN KEY (`Organiser_police_id`)
    REFERENCES `mydb`.`Organiser` (`police_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day` DATE NOT NULL,
  `time` TIME NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Locations_id` INT NOT NULL,
  `EventClass_idEventClass` INT NULL,
  `Organiser_police_id` CHAR(8) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Events_Locations1_idx` (`Locations_id` ASC),
  INDEX `fk_Events_EventClass1_idx` (`EventClass_idEventClass` ASC),
  INDEX `fk_Events_Organiser1_idx` (`Organiser_police_id` ASC),
  CONSTRAINT `fk_Events_Locations1`
    FOREIGN KEY (`Locations_id`)
    REFERENCES `mydb`.`Locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Events_EventClass1`
    FOREIGN KEY (`EventClass_idEventClass`)
    REFERENCES `mydb`.`EventGroup` (`idEventClass`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Events_Organiser1`
    FOREIGN KEY (`Organiser_police_id`)
    REFERENCES `mydb`.`Organiser` (`police_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PelatesEvents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PelatesEvents` (
  `Pelates_id` INT UNSIGNED NOT NULL,
  `Events_id` INT NOT NULL,
  PRIMARY KEY (`Pelates_id`, `Events_id`),
  INDEX `fk_PelatesEvents_Events1_idx` (`Events_id` ASC),
  CONSTRAINT `fk_PelatesEvents_Pelates1`
    FOREIGN KEY (`Pelates_id`)
    REFERENCES `mydb`.`Pelates` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PelatesEvents_Events1`
    FOREIGN KEY (`Events_id`)
    REFERENCES `mydb`.`Events` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Login_has_Pelates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Login_has_Pelates` (
  `Login_email` VARCHAR(45) NOT NULL,
  `Pelates_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Login_email`, `Pelates_id`),
  INDEX `fk_Login_has_Pelates_Pelates1_idx` (`Pelates_id` ASC),
  INDEX `fk_Login_has_Pelates_Login1_idx` (`Login_email` ASC),
  CONSTRAINT `fk_Login_has_Pelates_Login1`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Login_has_Pelates_Pelates1`
    FOREIGN KEY (`Pelates_id`)
    REFERENCES `mydb`.`Pelates` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EventFeedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`EventFeedback` (
  `Events_id` INT NOT NULL,
  `feedback` VARCHAR(128) NULL,
  PRIMARY KEY (`Events_id`),
  CONSTRAINT `fk_EventFeedback_Events1`
    FOREIGN KEY (`Events_id`)
    REFERENCES `mydb`.`Events` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
