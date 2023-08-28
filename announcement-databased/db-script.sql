USE announcement;
CREATE TABLE IF NOT EXISTS user (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL UNIQUE,
  email VARCHAR(150) NOT NULL UNIQUE,
  role ENUM("admin","announcer") DEFAULT "announcer",
  createdOn DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedOn DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id));
  
  insert into user VALUES (1,'sanit','Sanit Sirisawatvatana','sanit.sir@kmutt.ac.th','admin','2023-08-15 08:00:00+07:00','2023-08-15 08:00:00+07:00');
insert into user VALUES(2,'pornthip','Pornthip Sirijutikul','pornthip.sri@kmutt.ac.th','announcer','2023-08-15 09:30:00+07:00','2023-08-15 09:30:00+07:00');
insert into user VALUES(3,'jaruwan_w','Jaruwan Maneesart','jaruwan.wee@kmutt.ac.th','announcer','2023-08-16 08:00:00+07:00','2023-08-16 08:00:00+07:00');
insert into user VALUES(4,'vichchuda','Vichchuda Tedoloh','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00','2023-08-16 09:30:00+07:00');



SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema announcement
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema announcement
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `announcement` DEFAULT CHARACTER SET utf8 ;
USE `announcement` ;

-- -----------------------------------------------------
-- Table `mydb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `announcement`.`category` (
  `categoryId` INT NOT NULL,
  `categoryName` VARCHAR(50) NOT NULL DEFAULT 'General',
  PRIMARY KEY (`categoryId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `announcement`.`announcement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `announcement`.`announcement` (
  `announcementId` INT NOT NULL auto_increment,
  `announcementTitle` VARCHAR(200) NOT NULL,
  `announcementDescription` VARCHAR(10000) NOT NULL,
  `publishDate` DATETIME NULL,
  `closeDate` DATETIME NULL,
  `announcementDisplay` ENUM('Y', 'N') NOT NULL DEFAULT 'N',
  `categoryId` INT NOT NULL,
  `viewCount` INT default 0,
  PRIMARY KEY (`announcementID`),
  INDEX `fk_Announcement_Category_idx` (`categoryId` ASC) VISIBLE,
  CONSTRAINT `fk_Announcement_Category`
    FOREIGN KEY (`categoryId`)
    REFERENCES `announcement`.`category` (`categoryId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



insert into announcement  values 
(3,'(PBI10) Title 0','(PBI10) Description 0','2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y',1,1),
(4,'(PBI10) Title 1','(PBI10) Description 1','2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y',2,1),
(5,'(PBI10) Title 2','(PBI10) Description 2','2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y',1,1),
(6,'(PBI10) Title 3','(PBI10) Description 3','2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y',2,1),
(7,'(PBI10) Title 4','(PBI10) Description 4','2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y',1,1),
(8,'(PBI10) Title 5','(PBI10) Description 5','2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y',2,1)
