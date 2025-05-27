-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafytrip` ;

-- -----------------------------------------------------
-- Table `ssafytrip`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`sidos` (
  `no` INT NOT NULL AUTO_INCREMENT COMMENT '시도번호',
  `sido_code` INT NOT NULL COMMENT '시도코드',
  `sido_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '시도이름',
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '시도정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`guguns` (
  `no` INT NOT NULL AUTO_INCREMENT COMMENT '구군번호',
  `sido_code` INT NOT NULL COMMENT '시도코드',
  `gugun_code` INT NOT NULL COMMENT '구군코드',
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '구군이름',
  PRIMARY KEY (`no`),
  INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `ssafytrip`.`sidos` (`sido_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 469
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '구군정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`contenttypes` (
  `content_type_id` INT NOT NULL COMMENT '콘텐츠타입번호',
  `content_type_name` VARCHAR(45) NULL DEFAULT NULL COMMENT '콘텐츠타입이름',
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '콘텐츠타입정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`attractions` (
  `no` INT NOT NULL AUTO_INCREMENT COMMENT '명소코드',
  `content_id` INT NULL DEFAULT NULL COMMENT '콘텐츠번호',
  `title` VARCHAR(500) NULL DEFAULT NULL COMMENT '명소이름',
  `content_type_id` INT NULL DEFAULT NULL COMMENT '콘텐츠타입',
  `area_code` INT NULL DEFAULT NULL COMMENT '시도코드',
  `si_gun_gu_code` INT NULL DEFAULT NULL COMMENT '구군코드',
  `first_image1` VARCHAR(100) NULL DEFAULT NULL COMMENT '이미지경로1',
  `first_image2` VARCHAR(100) NULL DEFAULT NULL COMMENT '이미지경로2',
  `map_level` INT NULL DEFAULT NULL COMMENT '줌레벨',
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL COMMENT '위도',
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL COMMENT '경도',
  `tel` VARCHAR(20) NULL DEFAULT NULL COMMENT '전화번호',
  `addr1` VARCHAR(100) NULL DEFAULT NULL COMMENT '주소1',
  `addr2` VARCHAR(100) NULL DEFAULT NULL COMMENT '주소2',
  `homepage` VARCHAR(1000) NULL DEFAULT NULL COMMENT '홈페이지',
  `overview` VARCHAR(10000) NULL DEFAULT NULL COMMENT '설명',
  PRIMARY KEY (`no`),
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `ssafytrip`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `ssafytrip`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `ssafytrip`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 107559
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '명소정보테이블';


-- -----------------------------------------------------
-- Table `ssafytrip`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`members` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `role` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`hotplace`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`hotplace` (
  `hid` INT NOT NULL AUTO_INCREMENT,
  `mid` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL COMMENT '콘텐츠타입',
  `image` VARCHAR(100) NULL DEFAULT NULL COMMENT '이미지경로',
  `x` DECIMAL(25,10) NULL DEFAULT NULL COMMENT 'utm-k x',
  `y` DECIMAL(25,10) NULL DEFAULT NULL COMMENT 'utm-k y',
  `likes` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  PRIMARY KEY (`hid`),
  INDEX `fk_hot_member_id` (`mid` ASC) VISIBLE,
  INDEX `hotplace_typeid_to_types_typeid_fk` (`content_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_hot_member_id`
    FOREIGN KEY (`mid`)
    REFERENCES `ssafytrip`.`members` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `hotplace_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `ssafytrip`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`plan` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `start` DATETIME NULL DEFAULT NULL,
  `end` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`pid`),
  INDEX `fk_id` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_id`
    FOREIGN KEY (`id`)
    REFERENCES `ssafytrip`.`members` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`plandetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`plandetail` (
  `NO` INT NOT NULL AUTO_INCREMENT,
  `PID` INT NULL DEFAULT NULL,
  `ANO` INT NULL DEFAULT NULL,
  `START` DATETIME NULL DEFAULT NULL,
  `END` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`NO`),
  INDEX `PID` (`PID` ASC) VISIBLE,
  CONSTRAINT `plandetail_ibfk_1`
    FOREIGN KEY (`PID`)
    REFERENCES `ssafytrip`.`plan` (`pid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`posts` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `mid` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `content` TEXT NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_post_member_id` (`mid` ASC) VISIBLE,
  CONSTRAINT `fk_post_member_id`
    FOREIGN KEY (`mid`)
    REFERENCES `ssafytrip`.`members` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
