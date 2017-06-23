DROP DATABASE IF EXISTS `test`;
CREATE DATABASE `test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test`;

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `class_id` char(6) NOT NULL,
  `dept_id` char(4) NOT NULL,
  `coll_id` char(2) DEFAULT NULL,
  `class_name` varchar(6) NOT NULL,
  PRIMARY KEY (`class_id`),
  KEY `class_fk1` (`dept_id`),
  KEY `class_fk2` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `class` WRITE;
INSERT INTO `class` VALUES ('010101','0101','01','计算机一班');
INSERT INTO `class` VALUES ('020201','0201','02','数学二班');
UNLOCK TABLES;

DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `coll_id` char(2) NOT NULL,
  `coll_name` varchar(30) NOT NULL,
  PRIMARY KEY (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `college` WRITE;
INSERT INTO `college` VALUES ('01','信息学院');
INSERT INTO `college` VALUES ('02','理学院');
UNLOCK TABLES;

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cou_id` char(6) NOT NULL,
  `cou_name` varchar(30) NOT NULL,
  `xuefen` decimal(3,1) NOT NULL,
  `coll_id` char(2) NOT NULL,
  `dept_id` char(4) NOT NULL,
  PRIMARY KEY (`cou_id`),
  KEY `cou_fk1` (`dept_id`),
  KEY `cou_fk2` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `course` WRITE;
INSERT INTO `course` VALUES ('010001','JAVA程序设计',4.0,'01','0101');
INSERT INTO `course` VALUES ('010002','计算机组成原理',4.0,'01','0101');
INSERT INTO `course` VALUES ('010003','计算机系统原理',4.0,'01','0101');
INSERT INTO `course` VALUES ('010004','C语言程序设计',4.0,'01','0101');
INSERT INTO `course` VALUES ('020001','线性代数',3,'02','0201');
UNLOCK TABLES;

DROP TABLE IF EXISTS `courseinfo`;
CREATE TABLE `courseinfo` (
  `cou_id` char(6) NOT NULL DEFAULT '',
  `cou_day` char(1) NOT NULL DEFAULT '',
  `cou_time` char(1) NOT NULL DEFAULT '',
  `teacher` varchar(20) NOT NULL,
  `onchosing` char(1) DEFAULT '0',
  PRIMARY KEY (`cou_id`,`cou_day`,`cou_time`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `courseinfo` WRITE;
INSERT INTO `courseinfo` VALUES ('010001','1','2','许加兵','0');
INSERT INTO `courseinfo` VALUES ('010002','1','1','金国英','0');
INSERT INTO `courseinfo` VALUES ('010003','5','3','王玉巧','1');
INSERT INTO `courseinfo` VALUES ('010004','3','5','周碧涛','1');
UNLOCK TABLES;

DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `dept_id` char(4) NOT NULL,
  `coll_id` char(2) NOT NULL,
  `dept_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`dept_id`),
  KEY `dept_fk` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `dept` WRITE;
INSERT INTO `dept` VALUES ('0101','01','计算机专业');
INSERT INTO `dept` VALUES ('0201','02','数学专业');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `stu_id` char(12) NOT NULL DEFAULT '',
  `cou_id` char(6) NOT NULL DEFAULT '',
  `score` decimal(4,1) DEFAULT '0.0',
  `isdual` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`stu_id`,`cou_id`),
  KEY `grade_fk1` (`cou_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `grade` WRITE;
INSERT INTO `grade` VALUES ('514039927200','010001','99','0');
INSERT INTO `grade` VALUES ('514039927200','020001','96','0');
INSERT INTO `grade` VALUES ('514039927200','010002','58','0');
INSERT INTO `grade` VALUES ('514039927200','010003','96','1');
INSERT INTO `grade` VALUES ('514039927200','010004','59','1');
UNLOCK TABLES;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stu_id` char(12) NOT NULL,
  `stu_name` varchar(20) NOT NULL,
  `stu_gender` char(2) DEFAULT NULL,
  `stu_birth` datetime DEFAULT NULL,
  `nativeplace` varchar(60) DEFAULT NULL,
  `coll_id` char(2) NOT NULL,
  `dept_id` char(4) NOT NULL,
  `class_id` char(6) NOT NULL,
  `cometime` datetime NOT NULL,
  PRIMARY KEY (`stu_id`),
  KEY `stu_fk1` (`class_id`),
  KEY `stu_fk2` (`dept_id`),
  KEY `stu_fk3` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `student` WRITE;
INSERT INTO `student` VALUES ('514039927200','陈彦琦','男','1996-03-09','江西','01','0101','010101','2004-09-01');
UNLOCK TABLES;

DROP TABLE IF EXISTS `user_stu`;
CREATE TABLE `user_stu` (
  `s_id` char(12) NOT NULL,
  `pwd` char(12) NOT NULL,
  `stu_id` char(12) NOT NULL,
  PRIMARY KEY (`s_id`),
  KEY `user1_fk` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `user_stu` WRITE;
INSERT INTO `user_stu` VALUES ('514039927200','514039927200','514039927200');
UNLOCK TABLES;

DROP TABLE IF EXISTS `user_teacher`;
CREATE TABLE `user_teacher` (
  `t_id` char(6) NOT NULL,
  `pwd` char(12) NOT NULL,
  `coll_id` char(2) NOT NULL,
  PRIMARY KEY (`t_id`),
  KEY `user2_fk` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
LOCK TABLES `user_teacher` WRITE;
INSERT INTO `user_teacher` VALUES ('090011','090011','01');
INSERT INTO `user_teacher` VALUES ('090012','090012','02');
UNLOCK TABLES;

ALTER TABLE `class`
ADD CONSTRAINT `class_fk1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`),
ADD CONSTRAINT `class_fk2` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`);

ALTER TABLE `course`
ADD CONSTRAINT `cou_fk1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`),
ADD CONSTRAINT `cou_fk2` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`);

ALTER TABLE `courseinfo`
ADD CONSTRAINT `couinfo_fk1` FOREIGN KEY (`cou_id`) REFERENCES `course` (`cou_id`);

ALTER TABLE `dept`
ADD CONSTRAINT `dept_fk` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`);

ALTER TABLE `grade`
ADD CONSTRAINT `grade_fk1` FOREIGN KEY (`cou_id`) REFERENCES `course` (`cou_id`),
ADD CONSTRAINT `grade_fk2` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`);


ALTER TABLE `student`
ADD CONSTRAINT `stu_fk1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
ADD CONSTRAINT `stu_fk2` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`),
ADD CONSTRAINT `stu_fk3` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`);

ALTER TABLE `user_stu`
ADD CONSTRAINT `user1_fk` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`);

ALTER TABLE `user_teacher`
ADD CONSTRAINT `user2_fk` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`);

