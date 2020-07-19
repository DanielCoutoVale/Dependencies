-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: dependencies
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `analysis`
--

DROP TABLE IF EXISTS `analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `analysis` (
  `description-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `analysis-fk1_idx` (`description-id`),
  KEY `analysis-idx1` (`name`),
  CONSTRAINT `analysis-fk1` FOREIGN KEY (`description-id`) REFERENCES `description` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `corpus`
--

DROP TABLE IF EXISTS `corpus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corpus` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `iri` varchar(1024) NOT NULL DEFAULT '',
  `name` varchar(256) NOT NULL,
  `description` varchar(4096) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `description`
--

DROP TABLE IF EXISTS `description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `description` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'A linguistic description for a particular language by a particular author.',
  `name` varchar(255) NOT NULL COMMENT 'Ex: PROIEL, ITTB, PERSEUS, IP',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `description-idx1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feature`
--

DROP TABLE IF EXISTS `feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature` (
  `system-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `feature-fk2_idx` (`system-id`),
  KEY `feature-idx1` (`name`),
  CONSTRAINT `systemic-feature-fk2` FOREIGN KEY (`system-id`) REFERENCES `system` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37834 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `function`
--

DROP TABLE IF EXISTS `function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `function` (
  `metafunction-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `function-fk2_idx` (`metafunction-id`),
  KEY `function-idx1` (`name`),
  CONSTRAINT `function-fk2` FOREIGN KEY (`metafunction-id`) REFERENCES `metafunction` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2074 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `ip-anchor`
--

DROP TABLE IF EXISTS `ip-anchor`;
/*!50001 DROP VIEW IF EXISTS `ip-anchor`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-anchor` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `function`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ip-chain`
--

DROP TABLE IF EXISTS `ip-chain`;
/*!50001 DROP VIEW IF EXISTS `ip-chain`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-chain` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `id3`,
 1 AS `form3`,
 1 AS `lemma3`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `class3`,
 1 AS `function1`,
 1 AS `function2`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ip-dependency`
--

DROP TABLE IF EXISTS `ip-dependency`;
/*!50001 DROP VIEW IF EXISTS `ip-dependency`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-dependency` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `class1`,
 1 AS `rank1`,
 1 AS `function`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `class2`,
 1 AS `rank2`,
 1 AS `wording`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ip-frequencies`
--

DROP TABLE IF EXISTS `ip-frequencies`;
/*!50001 DROP VIEW IF EXISTS `ip-frequencies`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-frequencies` AS SELECT 
 1 AS `count`,
 1 AS `class1`,
 1 AS `function`,
 1 AS `class2`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ip-systemic-features`
--

DROP TABLE IF EXISTS `ip-systemic-features`;
/*!50001 DROP VIEW IF EXISTS `ip-systemic-features`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-systemic-features` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `system`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ip-word`
--

DROP TABLE IF EXISTS `ip-word`;
/*!50001 DROP VIEW IF EXISTS `ip-word`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-word` AS SELECT 
 1 AS `wording-id`,
 1 AS `order`,
 1 AS `id`,
 1 AS `form`,
 1 AS `backspaced`,
 1 AS `lemma`,
 1 AS `class`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ip-word-features`
--

DROP TABLE IF EXISTS `ip-word-features`;
/*!50001 DROP VIEW IF EXISTS `ip-word-features`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ip-word-features` AS SELECT 
 1 AS `id`,
 1 AS `form`,
 1 AS `lemma`,
 1 AS `feature`,
 1 AS `system`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-anchor`
--

DROP TABLE IF EXISTS `ittb-anchor`;
/*!50001 DROP VIEW IF EXISTS `ittb-anchor`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-anchor` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `function`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-chain`
--

DROP TABLE IF EXISTS `ittb-chain`;
/*!50001 DROP VIEW IF EXISTS `ittb-chain`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-chain` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `id3`,
 1 AS `form3`,
 1 AS `lemma3`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `class3`,
 1 AS `function1`,
 1 AS `function2`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-dependency`
--

DROP TABLE IF EXISTS `ittb-dependency`;
/*!50001 DROP VIEW IF EXISTS `ittb-dependency`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-dependency` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `class1`,
 1 AS `rank1`,
 1 AS `function`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `class2`,
 1 AS `rank2`,
 1 AS `wording`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-frequencies`
--

DROP TABLE IF EXISTS `ittb-frequencies`;
/*!50001 DROP VIEW IF EXISTS `ittb-frequencies`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-frequencies` AS SELECT 
 1 AS `count`,
 1 AS `class1`,
 1 AS `function`,
 1 AS `class2`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-ip-anchor`
--

DROP TABLE IF EXISTS `ittb-ip-anchor`;
/*!50001 DROP VIEW IF EXISTS `ittb-ip-anchor`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-ip-anchor` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `function`,
 1 AS `class1A`,
 1 AS `class2A`,
 1 AS `functionA`,
 1 AS `class1B`,
 1 AS `class2B`,
 1 AS `functionB`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-ip-anchor-back`
--

DROP TABLE IF EXISTS `ittb-ip-anchor-back`;
/*!50001 DROP VIEW IF EXISTS `ittb-ip-anchor-back`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-ip-anchor-back` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `function`,
 1 AS `class1A`,
 1 AS `class2A`,
 1 AS `functionA`,
 1 AS `class1B`,
 1 AS `class2B`,
 1 AS `functionB`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-ip-word`
--

DROP TABLE IF EXISTS `ittb-ip-word`;
/*!50001 DROP VIEW IF EXISTS `ittb-ip-word`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-ip-word` AS SELECT 
 1 AS `wording-id`,
 1 AS `order`,
 1 AS `id`,
 1 AS `form`,
 1 AS `backspaced`,
 1 AS `lemma`,
 1 AS `class1`,
 1 AS `class2`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-roof`
--

DROP TABLE IF EXISTS `ittb-roof`;
/*!50001 DROP VIEW IF EXISTS `ittb-roof`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-roof` AS SELECT 
 1 AS `id1`,
 1 AS `form1`,
 1 AS `lemma1`,
 1 AS `id2`,
 1 AS `form2`,
 1 AS `lemma2`,
 1 AS `id3`,
 1 AS `form3`,
 1 AS `lemma3`,
 1 AS `class1`,
 1 AS `class2`,
 1 AS `class3`,
 1 AS `function1`,
 1 AS `function2`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-systemic-features`
--

DROP TABLE IF EXISTS `ittb-systemic-features`;
/*!50001 DROP VIEW IF EXISTS `ittb-systemic-features`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-systemic-features` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `system`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-word`
--

DROP TABLE IF EXISTS `ittb-word`;
/*!50001 DROP VIEW IF EXISTS `ittb-word`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-word` AS SELECT 
 1 AS `wording-id`,
 1 AS `order`,
 1 AS `id`,
 1 AS `form`,
 1 AS `backspaced`,
 1 AS `lemma`,
 1 AS `class`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ittb-word-features`
--

DROP TABLE IF EXISTS `ittb-word-features`;
/*!50001 DROP VIEW IF EXISTS `ittb-word-features`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ittb-word-features` AS SELECT 
 1 AS `id`,
 1 AS `form`,
 1 AS `lemma`,
 1 AS `feature`,
 1 AS `system`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'A language to be studied.',
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metafunction`
--

DROP TABLE IF EXISTS `metafunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metafunction` (
  `description-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `metafunction-fk1_idx` (`description-id`),
  KEY `metafunction-idx1` (`name`),
  CONSTRAINT `metafunction-fk1` FOREIGN KEY (`description-id`) REFERENCES `description` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system`
--

DROP TABLE IF EXISTS `system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system` (
  `description-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `entry-condition` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `system-fk1_idx` (`description-id`),
  KEY `system-idx1` (`name`),
  CONSTRAINT `system-fk1` FOREIGN KEY (`description-id`) REFERENCES `description` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `text`
--

DROP TABLE IF EXISTS `text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `text` (
  `corpus-id` int(10) unsigned NOT NULL,
  `language-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `text-fk1_idx` (`corpus-id`),
  KEY `text-fk2_idx` (`language-id`),
  CONSTRAINT `text-fk1` FOREIGN KEY (`corpus-id`) REFERENCES `corpus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `text-fk2` FOREIGN KEY (`language-id`) REFERENCES `language` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word`
--

DROP TABLE IF EXISTS `word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word` (
  `wording-id` int(10) unsigned NOT NULL,
  `order` int(11) NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `form` varchar(256) NOT NULL,
  `backspaced` int(1) unsigned NOT NULL,
  `lemma` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `word-fk4_idx` (`wording-id`),
  KEY `word-idx1` (`form`),
  KEY `word-idx2` (`lemma`),
  CONSTRAINT `word-fk4` FOREIGN KEY (`wording-id`) REFERENCES `wording` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=538019 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word-feature`
--

DROP TABLE IF EXISTS `word-feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word-feature` (
  `analysis-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL,
  `word-id` int(10) unsigned NOT NULL,
  `role-id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`role-id`),
  UNIQUE KEY `role-id_UNIQUE` (`role-id`),
  KEY `word-feature-fk1_idx` (`id`),
  KEY `word-feature-fk2_idx` (`word-id`),
  KEY `word-feature-fk3_idx` (`analysis-id`),
  CONSTRAINT `word-feature-fk1` FOREIGN KEY (`id`) REFERENCES `feature` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-feature-fk2` FOREIGN KEY (`word-id`) REFERENCES `word` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-feature-fk3` FOREIGN KEY (`analysis-id`) REFERENCES `analysis` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16415851 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word-function`
--

DROP TABLE IF EXISTS `word-function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word-function` (
  `analysis-id` int(10) unsigned NOT NULL,
  `id` int(10) unsigned NOT NULL,
  `word-id` int(10) unsigned NOT NULL,
  `word-rank-id` int(10) unsigned NOT NULL,
  `head-id` int(10) unsigned NOT NULL,
  `head-rank-id` int(10) unsigned NOT NULL,
  `role-id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`role-id`),
  UNIQUE KEY `role-id_UNIQUE` (`role-id`),
  KEY `word-function-fk5_idx` (`head-id`),
  KEY `word-function-fk6_idx` (`head-rank-id`),
  KEY `word-function-fk2_idx` (`id`),
  KEY `word-function-fk1_idx` (`analysis-id`),
  KEY `word-function-fk3_idx` (`word-id`),
  KEY `word-function-fk4_idx` (`word-rank-id`),
  CONSTRAINT `word-function-fk1` FOREIGN KEY (`analysis-id`) REFERENCES `analysis` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-function-fk2` FOREIGN KEY (`id`) REFERENCES `function` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-function-fk3` FOREIGN KEY (`word-id`) REFERENCES `word` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-function-fk4` FOREIGN KEY (`word-rank-id`) REFERENCES `feature` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-function-fk5` FOREIGN KEY (`head-id`) REFERENCES `word` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word-function-fk6` FOREIGN KEY (`head-rank-id`) REFERENCES `feature` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1554303 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wording`
--

DROP TABLE IF EXISTS `wording`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wording` (
  `text-id` int(10) unsigned NOT NULL,
  `order` int(10) NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `form` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `wording-fk3_idx` (`text-id`),
  CONSTRAINT `wording-fk3` FOREIGN KEY (`text-id`) REFERENCES `text` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46236 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `ip-anchor`
--

/*!50001 DROP VIEW IF EXISTS `ip-anchor`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-anchor` AS select `a`.`id` AS `id1`,`a`.`form` AS `form1`,`a`.`lemma` AS `lemma1`,`b`.`id` AS `id2`,`b`.`form` AS `form2`,`b`.`lemma` AS `lemma2`,`a`.`class` AS `class1`,`b`.`class` AS `class2`,`F`.`name` AS `function` from (((((`ip-word` `A` join `word-function` `WF` on((`WF`.`word-id` = `a`.`id`))) join `ip-word` `B` on((`b`.`id` = `WF`.`head-id`))) join `function` `F` on((`F`.`id` = `WF`.`id`))) join `metafunction` `M` on((`M`.`id` = `F`.`metafunction-id`))) join `description` `D` on(((`D`.`id` = `M`.`description-id`) and (`D`.`name` = 'IP')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ip-chain`
--

/*!50001 DROP VIEW IF EXISTS `ip-chain`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-chain` AS select `a`.`id` AS `id1`,`a`.`form` AS `form1`,`a`.`lemma` AS `lemma1`,`b`.`id` AS `id2`,`b`.`form` AS `form2`,`b`.`lemma` AS `lemma2`,`c`.`id` AS `id3`,`c`.`form` AS `form3`,`c`.`lemma` AS `lemma3`,`a`.`class` AS `class1`,`b`.`class` AS `class2`,`c`.`class` AS `class3`,`FA`.`name` AS `function1`,`FB`.`name` AS `function2` from ((((((((`ip-word` `A` join `word-function` `WFA` on((`WFA`.`word-id` = `a`.`id`))) join `ip-word` `B` on((`b`.`id` = `WFA`.`head-id`))) join `function` `FA` on((`FA`.`id` = `WFA`.`id`))) join `word-function` `WFB` on((`WFB`.`word-id` = `b`.`id`))) join `ip-word` `C` on((`c`.`id` = `WFB`.`head-id`))) join `function` `FB` on((`FB`.`id` = `WFB`.`id`))) join `metafunction` `M` on(((`M`.`id` = `FA`.`metafunction-id`) and (`M`.`id` = `FB`.`metafunction-id`)))) join `description` `D` on(((`D`.`id` = `M`.`description-id`) and (`D`.`name` = 'IP')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ip-dependency`
--

/*!50001 DROP VIEW IF EXISTS `ip-dependency`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-dependency` AS select `W1`.`id` AS `id1`,`W1`.`form` AS `form1`,`W1`.`lemma` AS `lemma1`,`F1`.`name` AS `class1`,`R1`.`name` AS `rank1`,`F`.`name` AS `function`,`W2`.`id` AS `id2`,`W2`.`form` AS `form2`,`W2`.`lemma` AS `lemma2`,`F2`.`name` AS `class2`,`R2`.`name` AS `rank2`,`W`.`form` AS `wording` from ((((((((((((((`word` `W1` join `wording` `W` on((`W`.`id` = `W1`.`wording-id`))) join `word-feature` `WF1` on((`WF1`.`word-id` = `W1`.`id`))) join `feature` `F1` on((`F1`.`id` = `WF1`.`id`))) join `system` `S1` on(((`S1`.`id` = `F1`.`system-id`) and (`S1`.`name` = 'WORD-CLASS')))) join `description` `D1` on(((`D1`.`id` = `S1`.`description-id`) and (`D1`.`name` = 'IP')))) join `word-function` `WF` on((`WF`.`word-id` = `W1`.`id`))) join `feature` `R1` on((`R1`.`id` = `WF`.`word-rank-id`))) join `feature` `R2` on((`R2`.`id` = `WF`.`head-rank-id`))) join `function` `F` on((`F`.`id` = `WF`.`id`))) join `word` `W2` on((`W2`.`id` = `WF`.`head-id`))) join `word-feature` `WF2` on((`WF2`.`word-id` = `W2`.`id`))) join `feature` `F2` on((`F2`.`id` = `WF2`.`id`))) join `system` `S2` on(((`S2`.`id` = `F2`.`system-id`) and (`S2`.`name` = 'WORD-CLASS')))) join `description` `D2` on(((`D2`.`id` = `S2`.`description-id`) and (`D2`.`name` = 'IP')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ip-frequencies`
--

/*!50001 DROP VIEW IF EXISTS `ip-frequencies`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-frequencies` AS select count(`ip-dependency`.`id1`) AS `count`,`ip-dependency`.`class1` AS `class1`,`ip-dependency`.`function` AS `function`,`ip-dependency`.`class2` AS `class2` from `ip-dependency` group by `ip-dependency`.`class1`,`ip-dependency`.`function`,`ip-dependency`.`class2` order by `ip-dependency`.`class1`,`count` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ip-systemic-features`
--

/*!50001 DROP VIEW IF EXISTS `ip-systemic-features`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-systemic-features` AS select `F`.`id` AS `id`,`F`.`name` AS `name`,`S`.`name` AS `system` from ((`feature` `F` join `system` `S` on((`S`.`id` = `F`.`system-id`))) join `description` `D` on(((`D`.`id` = `S`.`description-id`) and (`D`.`name` = 'IP')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ip-word`
--

/*!50001 DROP VIEW IF EXISTS `ip-word`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-word` AS select `W`.`wording-id` AS `wording-id`,`W`.`order` AS `order`,`W`.`id` AS `id`,`W`.`form` AS `form`,`W`.`backspaced` AS `backspaced`,`W`.`lemma` AS `lemma`,`F`.`name` AS `class` from ((((`word` `W` join `word-feature` `WF` on((`W`.`id` = `WF`.`word-id`))) join `feature` `F` on((`F`.`id` = `WF`.`id`))) join `system` `S` on(((`S`.`id` = `F`.`system-id`) and (`S`.`name` = 'WORD-CLASS')))) join `description` `D` on(((`D`.`id` = `S`.`description-id`) and (`D`.`name` = 'IP')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ip-word-features`
--

/*!50001 DROP VIEW IF EXISTS `ip-word-features`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ip-word-features` AS select `W`.`id` AS `id`,`W`.`form` AS `form`,`W`.`lemma` AS `lemma`,`F`.`name` AS `feature`,`S`.`name` AS `system` from ((((`feature` `F` join `system` `S` on((`S`.`id` = `F`.`system-id`))) join `description` `D` on(((`D`.`id` = `S`.`description-id`) and (`D`.`name` = 'IP')))) join `word-feature` `WF` on((`WF`.`id` = `F`.`id`))) join `word` `W` on((`W`.`id` = `WF`.`word-id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-anchor`
--

/*!50001 DROP VIEW IF EXISTS `ittb-anchor`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-anchor` AS select `a`.`id` AS `id1`,`a`.`form` AS `form1`,`a`.`lemma` AS `lemma1`,`b`.`id` AS `id2`,`b`.`form` AS `form2`,`b`.`lemma` AS `lemma2`,`a`.`class` AS `class1`,`b`.`class` AS `class2`,`F`.`name` AS `function` from (((((`ittb-word` `A` join `word-function` `WF` on((`WF`.`word-id` = `a`.`id`))) join `ittb-word` `B` on((`b`.`id` = `WF`.`head-id`))) join `function` `F` on((`F`.`id` = `WF`.`id`))) join `metafunction` `M` on((`M`.`id` = `F`.`metafunction-id`))) join `description` `D` on(((`D`.`id` = `M`.`description-id`) and (`D`.`name` = 'ITTB')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-chain`
--

/*!50001 DROP VIEW IF EXISTS `ittb-chain`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-chain` AS select `a`.`id` AS `id1`,`a`.`form` AS `form1`,`a`.`lemma` AS `lemma1`,`b`.`id` AS `id2`,`b`.`form` AS `form2`,`b`.`lemma` AS `lemma2`,`c`.`id` AS `id3`,`c`.`form` AS `form3`,`c`.`lemma` AS `lemma3`,`a`.`class` AS `class1`,`b`.`class` AS `class2`,`c`.`class` AS `class3`,`FA`.`name` AS `function1`,`FB`.`name` AS `function2` from ((((((((`ittb-word` `A` join `word-function` `WFA` on((`WFA`.`word-id` = `a`.`id`))) join `ittb-word` `B` on((`b`.`id` = `WFA`.`head-id`))) join `function` `FA` on((`FA`.`id` = `WFA`.`id`))) join `word-function` `WFB` on((`WFB`.`word-id` = `b`.`id`))) join `ittb-word` `C` on((`c`.`id` = `WFB`.`head-id`))) join `function` `FB` on((`FB`.`id` = `WFB`.`id`))) join `metafunction` `M` on(((`M`.`id` = `FA`.`metafunction-id`) and (`M`.`id` = `FB`.`metafunction-id`)))) join `description` `D` on(((`D`.`id` = `M`.`description-id`) and (`D`.`name` = 'ITTB')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-dependency`
--

/*!50001 DROP VIEW IF EXISTS `ittb-dependency`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-dependency` AS select `W1`.`id` AS `id1`,`W1`.`form` AS `form1`,`W1`.`lemma` AS `lemma1`,`F1`.`name` AS `class1`,`R1`.`name` AS `rank1`,`F`.`name` AS `function`,`W2`.`id` AS `id2`,`W2`.`form` AS `form2`,`W2`.`lemma` AS `lemma2`,`F2`.`name` AS `class2`,`R2`.`name` AS `rank2`,`W`.`form` AS `wording` from ((((((((((((((`word` `W1` join `wording` `W` on((`W`.`id` = `W1`.`wording-id`))) join `word-feature` `WF1` on((`WF1`.`word-id` = `W1`.`id`))) join `feature` `F1` on((`F1`.`id` = `WF1`.`id`))) join `system` `S1` on(((`S1`.`id` = `F1`.`system-id`) and (`S1`.`name` = 'WORD-CLASS')))) join `description` `D1` on(((`D1`.`id` = `S1`.`description-id`) and (`D1`.`name` = 'ITTB')))) join `word-function` `WF` on((`WF`.`word-id` = `W1`.`id`))) join `feature` `R1` on((`R1`.`id` = `WF`.`word-rank-id`))) join `feature` `R2` on((`R2`.`id` = `WF`.`head-rank-id`))) join `function` `F` on((`F`.`id` = `WF`.`id`))) join `word` `W2` on((`W2`.`id` = `WF`.`head-id`))) join `word-feature` `WF2` on((`WF2`.`word-id` = `W2`.`id`))) join `feature` `F2` on((`F2`.`id` = `WF2`.`id`))) join `system` `S2` on(((`S2`.`id` = `F2`.`system-id`) and (`S2`.`name` = 'WORD-CLASS')))) join `description` `D2` on(((`D2`.`id` = `S2`.`description-id`) and (`D2`.`name` = 'ITTB')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-frequencies`
--

/*!50001 DROP VIEW IF EXISTS `ittb-frequencies`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-frequencies` AS select count(`ittb-dependency`.`id1`) AS `count`,`ittb-dependency`.`class1` AS `class1`,`ittb-dependency`.`function` AS `function`,`ittb-dependency`.`class2` AS `class2` from `ittb-dependency` group by `ittb-dependency`.`class1`,`ittb-dependency`.`function`,`ittb-dependency`.`class2` order by `ittb-dependency`.`class1`,`count` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-ip-anchor`
--

/*!50001 DROP VIEW IF EXISTS `ittb-ip-anchor`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-ip-anchor` AS select `o`.`id1` AS `id1`,`o`.`form1` AS `form1`,`o`.`lemma1` AS `lemma1`,`o`.`id2` AS `id2`,`o`.`form2` AS `form2`,`o`.`lemma2` AS `lemma2`,`o`.`class1` AS `class1`,`o`.`class2` AS `class2`,`o`.`function` AS `function`,`a`.`class1` AS `class1A`,`a`.`class2` AS `class2A`,`a`.`function` AS `functionA`,`b`.`class1` AS `class1B`,`b`.`class2` AS `class2B`,`b`.`function` AS `functionB` from ((`ittb-anchor` `O` left join `ip-anchor` `a` on(((`a`.`id1` = `o`.`id1`) and (`a`.`id2` = `o`.`id2`)))) left join `ip-anchor` `b` on(((`b`.`id1` = `o`.`id1`) and (`b`.`id2` <> `o`.`id2`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-ip-anchor-back`
--

/*!50001 DROP VIEW IF EXISTS `ittb-ip-anchor-back`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-ip-anchor-back` AS select `o`.`id1` AS `id1`,`o`.`form1` AS `form1`,`o`.`lemma1` AS `lemma1`,`o`.`id2` AS `id2`,`o`.`form2` AS `form2`,`o`.`lemma2` AS `lemma2`,`o`.`class1` AS `class1`,`o`.`class2` AS `class2`,`o`.`function` AS `function`,`a`.`class1` AS `class1A`,`a`.`class2` AS `class2A`,`a`.`function` AS `functionA`,`b`.`class1` AS `class1B`,`b`.`class2` AS `class2B`,`b`.`function` AS `functionB` from ((`ittb-anchor` `O` left join `ip-anchor` `a` on(((`a`.`id2` = `o`.`id1`) and (`a`.`id1` = `o`.`id2`)))) left join `ip-anchor` `b` on(((`b`.`id2` = `o`.`id1`) and (`b`.`id1` <> `o`.`id2`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-ip-word`
--

/*!50001 DROP VIEW IF EXISTS `ittb-ip-word`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-ip-word` AS select `w1`.`wording-id` AS `wording-id`,`w1`.`order` AS `order`,`w1`.`id` AS `id`,`w1`.`form` AS `form`,`w1`.`backspaced` AS `backspaced`,`w1`.`lemma` AS `lemma`,`w1`.`class` AS `class1`,`w2`.`class` AS `class2` from (`ittb-word` `W1` left join `ip-word` `W2` on((`w2`.`id` = `w1`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-roof`
--

/*!50001 DROP VIEW IF EXISTS `ittb-roof`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-roof` AS select `a`.`id` AS `id1`,`a`.`form` AS `form1`,`a`.`lemma` AS `lemma1`,`b`.`id` AS `id2`,`b`.`form` AS `form2`,`b`.`lemma` AS `lemma2`,`c`.`id` AS `id3`,`c`.`form` AS `form3`,`c`.`lemma` AS `lemma3`,`a`.`class` AS `class1`,`b`.`class` AS `class2`,`c`.`class` AS `class3`,`FA`.`name` AS `function1`,`FB`.`name` AS `function2` from ((((((((`ittb-word` `A` join `word-function` `WFA` on((`WFA`.`word-id` = `a`.`id`))) join `ittb-word` `B` on((`b`.`id` = `WFA`.`head-id`))) join `function` `FA` on((`FA`.`id` = `WFA`.`id`))) join `word-function` `WFB` on((`WFB`.`head-id` = `b`.`id`))) join `ittb-word` `C` on((`c`.`id` = `WFB`.`word-id`))) join `function` `FB` on((`FB`.`id` = `WFB`.`id`))) join `metafunction` `M` on(((`M`.`id` = `FA`.`metafunction-id`) and (`M`.`id` = `FB`.`metafunction-id`)))) join `description` `D` on(((`D`.`id` = `M`.`description-id`) and (`D`.`name` = 'ITTB')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-systemic-features`
--

/*!50001 DROP VIEW IF EXISTS `ittb-systemic-features`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-systemic-features` AS select `F`.`id` AS `id`,`F`.`name` AS `name`,`S`.`name` AS `system` from ((`feature` `F` join `system` `S` on((`S`.`id` = `F`.`system-id`))) join `description` `D` on(((`D`.`id` = `S`.`description-id`) and (`D`.`name` = 'ITTB')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-word`
--

/*!50001 DROP VIEW IF EXISTS `ittb-word`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-word` AS select `W`.`wording-id` AS `wording-id`,`W`.`order` AS `order`,`W`.`id` AS `id`,`W`.`form` AS `form`,`W`.`backspaced` AS `backspaced`,`W`.`lemma` AS `lemma`,`F`.`name` AS `class` from ((((`word` `W` join `word-feature` `WF` on((`W`.`id` = `WF`.`word-id`))) join `feature` `F` on((`F`.`id` = `WF`.`id`))) join `system` `S` on(((`S`.`id` = `F`.`system-id`) and (`S`.`name` = 'WORD-CLASS')))) join `description` `D` on(((`D`.`id` = `S`.`description-id`) and (`D`.`name` = 'ITTB')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ittb-word-features`
--

/*!50001 DROP VIEW IF EXISTS `ittb-word-features`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ittb-word-features` AS select `W`.`id` AS `id`,`W`.`form` AS `form`,`W`.`lemma` AS `lemma`,`F`.`name` AS `feature`,`S`.`name` AS `system` from ((((`feature` `F` join `system` `S` on((`S`.`id` = `F`.`system-id`))) join `description` `D` on(((`D`.`id` = `S`.`description-id`) and (`D`.`name` = 'ITTB')))) join `word-feature` `WF` on((`WF`.`id` = `F`.`id`))) join `word` `W` on((`W`.`id` = `WF`.`word-id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-19 16:13:22
