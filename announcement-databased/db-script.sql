-- MySQL Workbench Forward Engineering

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
  `announcementID` INT NOT NULL,
  `announcementTitle` VARCHAR(200) NOT NULL,
  `announcementDescription` VARCHAR(10000) NOT NULL,
  `publishDate` DATETIME NULL,
  `closeDate` DATETIME NULL,
  `announcementDisplay` ENUM('Y', 'N') NOT NULL DEFAULT 'N',
  `categoryId` INT NOT NULL,
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



insert into category values(1,'ทั่วไป'),(2,'ทุนการศึกษา'),(3,'หางาน'),(4,'ฝึกงาน');

insert into announcement values
(1,
'บริษัท เน็ตเซอร์พลัส จำกัด รับสมัครงาน 2 ตำแหน่ง',
'บริษัท เน็ตเซอร์พลัส จำกัด เปิดรับสมังาน 2 ตำแหน่ง Application Support และ Customer Support',
null,
null,
'N',
3),
(2,
'รายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" 2/2565',
'คณะ ฯประกาศรายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" ประจําภาคการศึกษา 2/2565',
null,
'2023-05-31T11:00:00',
'Y',
2),
(3,
'แนวปฎิบัติการสอบออนไลน์ พ.ศ. 2565',
'ประกาศมหาวิทยาลัยเทคโนโลยีพระจอมเกล้าธนบุรี เรื่องแนวทางปฎิบัติการสอบออนไลน์ พ.ศ. 2565',
'2023-01-26T23:00:00',
null,
'Y',
1),
(4,'กิจกรรมพี่อ้อย พี่ฉอด On Tour 2566',
'ขอเชิญนักศึกษาทุกชั้นปี เข้าร่วมกิจกรรมพี่อ้อย พี่ฉอด On Tour',
'2023-04-18T23:00:00',
'2023-05-08T11:00:00',
'Y',
1);
