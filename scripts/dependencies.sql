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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=31286 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  CONSTRAINT `function-fk2` FOREIGN KEY (`metafunction-id`) REFERENCES `metafunction` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=574 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `metafunction-fk1` FOREIGN KEY (`description-id`) REFERENCES `description` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=380 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  CONSTRAINT `word-fk4` FOREIGN KEY (`wording-id`) REFERENCES `wording` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=358691 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=1162154 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=222297 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=33630 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-07 23:19:31
