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
  `pwd` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`LocationOwner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`LocationOwner` (
  `Login_email` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Surname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Login_email`),
  CONSTRAINT `fk_LocationOwner_Login`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Organiser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Organiser` (
  `Login_email` VARCHAR(45) NOT NULL,
  `Police_id` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Surname` VARCHAR(45) NOT NULL,
  `Birthdate` DATE NULL,
  PRIMARY KEY (`Login_email`),
  UNIQUE INDEX `Police_id_UNIQUE` (`Police_id` ASC),
  CONSTRAINT `fk_Organiser_Login1`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Customer` (
  `Login_email` VARCHAR(45) NOT NULL,
  `Points` INT NULL,
  `Wallet` INT NULL,
  PRIMARY KEY (`Login_email`),
  CONSTRAINT `fk_table1_Login1`
    FOREIGN KEY (`Login_email`)
    REFERENCES `mydb`.`Login` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Location` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Address` VARCHAR(45) NOT NULL,
  `Postcode` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(45) NULL,
  `LocationOwner_Login_email` VARCHAR(45) NOT NULL,
  INDEX `fk_Location_LocationOwner1_idx` (`LocationOwner_Login_email` ASC),
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_Location_LocationOwner1`
    FOREIGN KEY (`LocationOwner_Login_email`)
    REFERENCES `mydb`.`LocationOwner` (`Login_email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EventsGroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`EventsGroup` (
  `idEventsGroup` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Type` VARCHAR(45) NULL,
  `Organiser_Login_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEventsGroup`),
  INDEX `fk_EventsGroup_Organiser1_idx` (`Organiser_Login_email` ASC),
  CONSTRAINT `fk_EventsGroup_Organiser1`
    FOREIGN KEY (`Organiser_Login_email`)
    REFERENCES `mydb`.`Organiser` (`Login_email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Events` (
  `idEvents` INT NOT NULL AUTO_INCREMENT,
  `Day` VARCHAR(45) NULL,
  `Time` VARCHAR(45) NULL,
  `Location_ID` INT NOT NULL,
  `Organiser_Login_email` VARCHAR(45) NOT NULL,
  `EventsGroup_idEventsGroup` INT NULL,
  PRIMARY KEY (`idEvents`),
  INDEX `fk_Events_Location1_idx` (`Location_ID` ASC),
  INDEX `fk_Events_Organiser1_idx` (`Organiser_Login_email` ASC),
  INDEX `fk_Events_EventsGroup1_idx` (`EventsGroup_idEventsGroup` ASC),
  CONSTRAINT `fk_Events_Location1`
    FOREIGN KEY (`Location_ID`)
    REFERENCES `mydb`.`Location` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Events_Organiser1`
    FOREIGN KEY (`Organiser_Login_email`)
    REFERENCES `mydb`.`Organiser` (`Login_email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Events_EventsGroup1`
    FOREIGN KEY (`EventsGroup_idEventsGroup`)
    REFERENCES `mydb`.`EventsGroup` (`idEventsGroup`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EventsFeedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`EventsFeedback` (
  `idEventsFeedback` INT NOT NULL AUTO_INCREMENT,
  `Events_idEvents` INT NOT NULL,
  `Content` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`idEventsFeedback`, `Events_idEvents`),
  INDEX `fk_EventsFeedback_Events1_idx` (`Events_idEvents` ASC),
  CONSTRAINT `fk_EventsFeedback_Events1`
    FOREIGN KEY (`Events_idEvents`)
    REFERENCES `mydb`.`Events` (`idEvents`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Events_has_Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Events_has_Customer` (
  `Events_idEvents` INT NOT NULL,
  `Customer_Login_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Events_idEvents`, `Customer_Login_email`),
  INDEX `fk_Events_has_Customer_Customer1_idx` (`Customer_Login_email` ASC),
  INDEX `fk_Events_has_Customer_Events1_idx` (`Events_idEvents` ASC),
  CONSTRAINT `fk_Events_has_Customer_Events1`
    FOREIGN KEY (`Events_idEvents`)
    REFERENCES `mydb`.`Events` (`idEvents`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Events_has_Customer_Customer1`
    FOREIGN KEY (`Customer_Login_email`)
    REFERENCES `mydb`.`Customer` (`Login_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
