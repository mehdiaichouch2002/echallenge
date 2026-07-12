-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: echallenge
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `echallenge`
--

/*!40000 DROP DATABASE IF EXISTS `echallenge`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `echallenge` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `echallenge`;

--
-- Table structure for table `app_users`
--

DROP TABLE IF EXISTS `app_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('ADMIN','CANDIDATE') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4vj92ux8a2eehds1mdvmks473` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_users`
--

LOCK TABLES `app_users` WRITE;
/*!40000 ALTER TABLE `app_users` DISABLE KEYS */;
INSERT INTO `app_users` VALUES (1,'admin@echallenge.com','$2a$10$cJkNiNxXybovmZpxaVCiCuCnDNR8LD61sgrWs8imj915sHEnpi2i6','ADMIN'),(2,'mehdi@test.com','$2a$10$7yYpIhG2u0lrpmdR5ro0KeRtC5Lz7P67.MK1efYv8rYhqweY.OpJS','CANDIDATE'),(3,'mehdi@aichouch.com','$2a$10$4t2exlUO9f/ZeNsq8N0EluGnp5tkHgBu2OESWzFuYLRTAPchn6TtC','CANDIDATE'),(4,'e2e.1783595675231@test.com','$2a$10$V/W1eyDrUTS7GKugz9fj4OpLmm3Bmoj9IE9lVjL4Cd3JnQIljHB22','CANDIDATE'),(5,'bella.1783595907760@test.com','$2a$10$n5ujCt8Fzj/db.1TOKA1POdM2fqwP0wF6LMHIXm4oC0moty5vzCSS','CANDIDATE'),(6,'carl.1783595907760@test.com','$2a$10$Xm92hfqArVCHEALWk9YWQ.AL4QhcRv0oxVrg55LM2NIc7HbujTbG6','CANDIDATE'),(9,'e2e.1783615508305@test.com','$2a$10$a8HvZG9vHNH.GeFEuANSgex3m8BWSQGTto5dF0hOpNvlYRYKAhq7O','CANDIDATE'),(10,'shots.1783615629819@test.com','$2a$10$3FvNPJIPSGdzLOhX0DbVw.0f1EJNh63h0xqBmP5xU/1sYfETraFFa','CANDIDATE'),(11,'candidat@echallenge.com','$2a$10$4EGswcmbhD5z1kpCcH3wsO8Um00DG1hBorJG1YssyhGutGyPAZ9aK','CANDIDATE');
/*!40000 ALTER TABLE `app_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate_answers`
--

DROP TABLE IF EXISTS `candidate_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate_answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) NOT NULL,
  `time_spent_seconds` bigint NOT NULL,
  `question_id` bigint NOT NULL,
  `option_id` bigint DEFAULT NULL,
  `test_session_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc4wasekqn7jq7rjc0n0uy9v7g` (`question_id`),
  KEY `FKgqqxr2ik3kppefqirvhlk2nll` (`option_id`),
  KEY `FKa6m8igqprcyd0g0o1g1qulpa7` (`test_session_id`),
  CONSTRAINT `FKa6m8igqprcyd0g0o1g1qulpa7` FOREIGN KEY (`test_session_id`) REFERENCES `test_sessions` (`id`),
  CONSTRAINT `FKc4wasekqn7jq7rjc0n0uy9v7g` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKgqqxr2ik3kppefqirvhlk2nll` FOREIGN KEY (`option_id`) REFERENCES `question_options` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate_answers`
--

LOCK TABLES `candidate_answers` WRITE;
/*!40000 ALTER TABLE `candidate_answers` DISABLE KEYS */;
INSERT INTO `candidate_answers` VALUES (1,_binary '',10,1,1,1),(2,_binary '',10,2,3,1),(3,_binary '',0,2,3,2),(4,_binary '',0,1,1,2),(5,_binary '\0',0,1,2,3),(6,_binary '\0',0,3,8,3),(7,_binary '',0,1,1,5),(8,_binary '',0,2,3,5),(9,_binary '',0,5,11,7),(10,_binary '',0,15,45,7),(11,_binary '',0,5,11,9),(12,_binary '',0,6,15,9),(13,_binary '',0,5,11,10),(14,_binary '',0,14,41,10),(15,_binary '',0,26,83,10),(16,_binary '',0,12,35,10),(17,_binary '',0,6,15,10),(18,_binary '',0,28,89,10);
/*!40000 ALTER TABLE `candidate_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidates`
--

DROP TABLE IF EXISTS `candidates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidates` (
  `code` varchar(255) DEFAULT NULL,
  `confirmed` bit(1) NOT NULL,
  `field` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `gsm` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `school` varchar(255) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK89ivxofpdlgaxk7r2nroof43b` (`code`),
  CONSTRAINT `FKas8sagb67icudcwgye7npaxh4` FOREIGN KEY (`id`) REFERENCES `app_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidates`
--

LOCK TABLES `candidates` WRITE;
/*!40000 ALTER TABLE `candidates` DISABLE KEYS */;
INSERT INTO `candidates` VALUES ('CAND-BA985060',_binary '\0','GI','Mehdi','0600000000','Aichouch','ENSA',2),('CAND-4B63C328',_binary '\0','GLSI','mehdi','0699950955','aichouch','ENSA',3),('CAND-E4F0A3D7',_binary '\0','GI','Emma','0612345678','Verif','ENSA',4),('CAND-40808A29',_binary '\0','GI','Bella','0600000001','Sanity','ENSA',5),('CAND-C7CFF531',_binary '','GI','Carl','0600000001','Sanity','ENSA',6),('CAND-F8CCF639',_binary '\0','GI','Emma','0612345678','Verif','ENSA',9),('CAND-D611BDF6',_binary '\0','GI','Sami','0600000003','Demo','ENSA',10),('CAND-DEMO',_binary '','Génie Informatique','Karim','0600000000','Demo','ENSA',11);
/*!40000 ALTER TABLE `candidates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_options`
--

DROP TABLE IF EXISTS `question_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_options` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) NOT NULL,
  `display_order` int NOT NULL,
  `option_text` varchar(1000) NOT NULL,
  `question_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsb9v00wdrgc9qojtjkv7e1gkp` (`question_id`),
  CONSTRAINT `FKsb9v00wdrgc9qojtjkv7e1gkp` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_options`
--

LOCK TABLES `question_options` WRITE;
/*!40000 ALTER TABLE `question_options` DISABLE KEYS */;
INSERT INTO `question_options` VALUES (1,_binary '',1,'Java Virtual Machine',1),(2,_binary '\0',2,'Java Vendor Model',1),(3,_binary '',1,'Java Virtual Machine',2),(4,_binary '\0',2,'Java Vendor Model',2),(7,_binary '',1,'Vrai',3),(8,_binary '\0',2,'Faux',3),(11,_binary '',1,'Java Virtual Machine',5),(12,_binary '\0',2,'Java Vendor Model',5),(13,_binary '\0',3,'Java Visual Maker',5),(14,_binary '\0',4,'Just Virtual Memory',5),(15,_binary '',1,'extends',6),(16,_binary '\0',2,'implements',6),(17,_binary '\0',3,'inherits',6),(18,_binary '\0',4,'super',6),(19,_binary '',1,'Vrai',7),(20,_binary '\0',2,'Faux',7),(21,_binary '',1,'Set',8),(22,_binary '\0',2,'List',8),(23,_binary '\0',3,'ArrayList',8),(24,_binary '\0',4,'Vector',8),(25,_binary '',1,'Vrai',9),(26,_binary '\0',2,'Faux',9),(27,_binary '',1,'Object',10),(28,_binary '\0',2,'Class',10),(29,_binary '\0',3,'Main',10),(30,_binary '\0',4,'Root',10),(31,_binary '',1,'WHERE',11),(32,_binary '\0',2,'ORDER BY',11),(33,_binary '\0',3,'GROUP BY',11),(34,_binary '\0',4,'SELECT',11),(35,_binary '',1,'DROP TABLE',12),(36,_binary '\0',2,'DELETE TABLE',12),(37,_binary '\0',3,'REMOVE TABLE',12),(38,_binary '\0',4,'TRUNCATE ROW',12),(39,_binary '\0',1,'Vrai',13),(40,_binary '',2,'Faux',13),(41,_binary '',1,'INNER JOIN',14),(42,_binary '\0',2,'LEFT JOIN',14),(43,_binary '\0',3,'RIGHT JOIN',14),(44,_binary '\0',4,'FULL JOIN',14),(45,_binary '',1,'COUNT(*)',15),(46,_binary '\0',2,'SUM(*)',15),(47,_binary '\0',3,'TOTAL()',15),(48,_binary '\0',4,'NB()',15),(49,_binary '',1,'2x',16),(50,_binary '\0',2,'x²',16),(51,_binary '\0',3,'x',16),(52,_binary '\0',4,'x³',16),(53,_binary '',1,'Vrai',17),(54,_binary '\0',2,'Faux',17),(55,_binary '',1,'x = 4',18),(56,_binary '\0',2,'x = 9',18),(57,_binary '\0',3,'x = 5',18),(58,_binary '\0',4,'x = 8',18),(59,_binary '',1,'3,14159',19),(60,_binary '\0',2,'3,0',19),(61,_binary '\0',3,'3,5',19),(62,_binary '\0',4,'2,71828',19),(63,_binary '',1,'12',20),(64,_binary '\0',2,'10',20),(65,_binary '\0',3,'8',20),(66,_binary '\0',4,'14',20),(67,_binary '',1,'Newton',21),(68,_binary '\0',2,'Joule',21),(69,_binary '\0',3,'Watt',21),(70,_binary '\0',4,'Pascal',21),(71,_binary '',1,'Vrai',22),(72,_binary '\0',2,'Faux',22),(73,_binary '',1,'E = mc²',23),(74,_binary '\0',2,'F = ma',23),(75,_binary '\0',3,'E = ½mv²',23),(76,_binary '\0',4,'P = UI',23),(77,_binary '',1,'Vrai',24),(78,_binary '\0',2,'Faux',24),(79,_binary '',1,'HyperText Transfer Protocol',25),(80,_binary '\0',2,'High Transfer Text Protocol',25),(81,_binary '\0',3,'Host Transfer Protocol',25),(82,_binary '\0',4,'HyperText Terminal Process',25),(83,_binary '',1,'443',26),(84,_binary '\0',2,'80',26),(85,_binary '\0',3,'21',26),(86,_binary '\0',4,'8080',26),(87,_binary '',1,'Vrai',27),(88,_binary '\0',2,'Faux',27),(89,_binary '',1,'DNS',28),(90,_binary '\0',2,'DHCP',28),(91,_binary '\0',3,'FTP',28),(92,_binary '\0',4,'SMTP',28);
/*!40000 ALTER TABLE `question_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_types`
--

DROP TABLE IF EXISTS `question_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type` enum('MULTIPLE_CHOICE','SINGLE_CHOICE','TRUE_FALSE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2cwwesej39tn97d2mpbq597ik` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_types`
--

LOCK TABLES `question_types` WRITE;
/*!40000 ALTER TABLE `question_types` DISABLE KEYS */;
INSERT INTO `question_types` VALUES (1,NULL,'QCM','SINGLE_CHOICE'),(2,NULL,'Choix multiples','MULTIPLE_CHOICE'),(3,NULL,'Vrai/Faux','TRUE_FALSE');
/*!40000 ALTER TABLE `question_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `explanation` varchar(2000) DEFAULT NULL,
  `points` int NOT NULL,
  `question_text` varchar(1000) NOT NULL,
  `question_type_id` bigint NOT NULL,
  `theme_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdyyji0ey6u2yifjdt8ybu3tyw` (`question_type_id`),
  KEY `FKt83gwawyanqb85qu1lx8pyi3m` (`theme_id`),
  CONSTRAINT `FKdyyji0ey6u2yifjdt8ybu3tyw` FOREIGN KEY (`question_type_id`) REFERENCES `question_types` (`id`),
  CONSTRAINT `FKt83gwawyanqb85qu1lx8pyi3m` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,_binary '\0',NULL,1,'Question 1: what is JVM?',1,1),(2,_binary '\0',NULL,1,'Question 2: what is JVM?',1,1),(3,_binary '','',1,'SELECT sert à lire des données ?',3,2),(5,_binary '','JVM = Java Virtual Machine, la machine virtuelle qui exécute le bytecode',1,'Que signifie JVM ?',1,1),(6,_binary '','Une classe hérite d\'une autre avec « extends »',1,'Quel mot-clé permet l\'héritage entre classes en Java ?',1,1),(7,_binary '','Depuis Java 8, les interfaces peuvent définir des méthodes « default »',1,'Une interface Java peut contenir des méthodes par défaut.',3,1),(8,_binary '','Un Set garantit l\'unicité de ses éléments',1,'Quelle collection Java n\'autorise pas les doublons ?',1,1),(9,_binary '','Une méthode final ne peut pas être surchargée dans une sous-classe',1,'Le mot-clé « final » sur une méthode empêche sa redéfinition.',3,1),(10,_binary '','Toute classe hérite implicitement de java.lang.Object',1,'Quelle est la classe mère de toutes les classes Java ?',1,1),(11,_binary '','WHERE filtre les lignes avant agrégation',1,'Quelle clause filtre les lignes d\'une requête ?',1,2),(12,_binary '','DROP TABLE supprime la structure et les données',1,'Quelle commande supprime définitivement une table ?',1,2),(13,_binary '','Une clé primaire est NOT NULL et unique par définition',1,'Une clé primaire peut contenir des valeurs NULL.',3,2),(14,_binary '','INNER JOIN ne conserve que les correspondances',1,'Quelle jointure retourne uniquement les lignes présentes dans les deux tables ?',1,2),(15,_binary '','COUNT(*) compte toutes les lignes du groupe',1,'Quelle fonction d\'agrégation compte le nombre de lignes ?',1,2),(16,_binary '','Règle de dérivation : d/dx(x^n) = n·x^(n-1)',1,'Quelle est la dérivée de x² ?',1,4),(17,_binary '','Théorème fondamental de la géométrie euclidienne',1,'La somme des angles d\'un triangle vaut toujours 180°.',3,4),(18,_binary '','2x = 8 donc x = 4',1,'Résoudre : 2x + 5 = 13',1,4),(19,_binary '','π ≈ 3,14159',1,'Quelle est la valeur approchée de π ?',1,4),(20,_binary '','f(2) = 4 + 6 + 2 = 12',1,'Si f(x) = x² + 3x + 2, que vaut f(2) ?',1,4),(21,_binary '','Le newton (N) = kg·m·s⁻²',1,'Quelle est l\'unité SI de la force ?',1,5),(22,_binary '','Valeur standard utilisée en mécanique',1,'L\'accélération de la pesanteur sur Terre vaut environ 9,8 m/s².',3,5),(23,_binary '','E = mc² relie énergie et masse',1,'Quelle est la célèbre équation d\'Einstein ?',1,5),(24,_binary '','c ≈ 299 792 458 m/s',1,'La lumière se propage dans le vide à environ 300 000 km/s.',3,5),(25,_binary '','HyperText Transfer Protocol, protocole du Web',1,'Que signifie HTTP ?',1,6),(26,_binary '','HTTPS utilise le port 443',1,'Quel est le port par défaut de HTTPS ?',1,6),(27,_binary '','4 octets = 32 bits (ex. 192.168.1.1)',1,'Une adresse IPv4 est codée sur 32 bits.',3,6),(28,_binary '','DNS = Domain Name System',1,'Quel protocole traduit les noms de domaine en adresses IP ?',1,6);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `results` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `completion_time` datetime(6) NOT NULL,
  `correct_answers` int NOT NULL,
  `feedback` varchar(2000) DEFAULT NULL,
  `passed` bit(1) NOT NULL,
  `score` double NOT NULL,
  `total_questions` int NOT NULL,
  `test_session_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfvblcd6ts9takv9ee087t2ih7` (`test_session_id`),
  CONSTRAINT `FK3u17jtdr4d909dacp10btu6ag` FOREIGN KEY (`test_session_id`) REFERENCES `test_sessions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results`
--

LOCK TABLES `results` WRITE;
/*!40000 ALTER TABLE `results` DISABLE KEYS */;
INSERT INTO `results` VALUES (1,'2026-07-09 10:57:47.048266',2,NULL,_binary '',100,2,1),(2,'2026-07-09 11:14:41.908104',2,NULL,_binary '',100,2,2),(3,'2026-07-09 11:18:34.932824',0,NULL,_binary '\0',0,2,3),(4,'2026-07-09 11:21:33.718472',2,NULL,_binary '',100,2,5),(5,'2026-07-09 11:54:35.947773',2,NULL,_binary '',100,2,7),(6,'2026-07-09 16:45:15.413033',2,NULL,_binary '',100,2,9),(7,'2026-07-09 16:47:12.500990',6,NULL,_binary '',100,6,10);
/*!40000 ALTER TABLE `results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_sessions`
--

DROP TABLE IF EXISTS `test_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_time` datetime(6) DEFAULT NULL,
  `registration_date` datetime(6) NOT NULL,
  `session_code` varchar(255) NOT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `status` enum('CANCELLED','COMPLETED','EXPIRED','NOT_STARTED','STARTED') NOT NULL,
  `candidate_id` bigint NOT NULL,
  `test_id` bigint NOT NULL,
  `timeslot_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKafw929tqj48jh002y4mtc8kdo` (`session_code`),
  KEY `FK8gxm8k9joywqbna0wdfpdpcit` (`candidate_id`),
  KEY `FKl2xt3dfgn4wpbtgwc08he3iar` (`test_id`),
  KEY `FK7d3faot1vd8oo84n2nymbd0ab` (`timeslot_id`),
  CONSTRAINT `FK7d3faot1vd8oo84n2nymbd0ab` FOREIGN KEY (`timeslot_id`) REFERENCES `timeslots` (`id`),
  CONSTRAINT `FK8gxm8k9joywqbna0wdfpdpcit` FOREIGN KEY (`candidate_id`) REFERENCES `candidates` (`id`),
  CONSTRAINT `FKl2xt3dfgn4wpbtgwc08he3iar` FOREIGN KEY (`test_id`) REFERENCES `tests` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_sessions`
--

LOCK TABLES `test_sessions` WRITE;
/*!40000 ALTER TABLE `test_sessions` DISABLE KEYS */;
INSERT INTO `test_sessions` VALUES (1,'2026-07-09 10:57:47.047907','2026-07-09 10:52:51.108644','FFDBS31S','2026-07-09 10:56:19.209531','COMPLETED',2,1,1),(2,'2026-07-09 11:14:41.907984','2026-07-09 11:14:39.209085','F9D658ZL','2026-07-09 11:14:39.509955','COMPLETED',4,1,2),(3,'2026-07-09 11:18:34.931424','2026-07-09 11:18:34.296258','BPB45S63','2026-07-09 11:18:34.563662','COMPLETED',5,2,3),(4,NULL,'2026-07-09 11:18:36.053407','GRJKIZB1',NULL,'CANCELLED',6,1,4),(5,'2026-07-09 11:21:33.718113','2026-07-09 11:21:17.150049','9IFFIVV0','2026-07-09 11:21:20.113858','COMPLETED',3,1,4),(7,'2026-07-09 11:54:35.947508','2026-07-09 11:54:13.927014','8DP03R1Q','2026-07-09 11:54:16.827074','COMPLETED',3,2,9),(8,NULL,'2026-07-09 16:39:25.818922','NYGECF4N','2026-07-09 16:39:27.185738','STARTED',3,8,11),(9,'2026-07-09 16:45:15.412740','2026-07-09 16:45:12.513082','XYCIZYC0','2026-07-09 16:45:12.722940','COMPLETED',9,1,16),(10,'2026-07-09 16:47:12.500607','2026-07-09 16:47:11.305651','V7C73TDU','2026-07-09 16:47:11.568566','COMPLETED',10,7,7);
/*!40000 ALTER TABLE `test_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_theme_questions`
--

DROP TABLE IF EXISTS `test_theme_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_theme_questions` (
  `test_id` bigint NOT NULL,
  `question_count` int DEFAULT NULL,
  `theme_question_counts_key` bigint NOT NULL,
  PRIMARY KEY (`test_id`,`theme_question_counts_key`),
  CONSTRAINT `FKg0fbgwd0mkdvmkej2p1oxi37r` FOREIGN KEY (`test_id`) REFERENCES `tests` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_theme_questions`
--

LOCK TABLES `test_theme_questions` WRITE;
/*!40000 ALTER TABLE `test_theme_questions` DISABLE KEYS */;
INSERT INTO `test_theme_questions` VALUES (1,2,1),(2,1,1),(2,1,2),(6,4,2),(7,2,1),(7,2,2),(7,2,6),(8,3,4),(8,2,5);
/*!40000 ALTER TABLE `test_theme_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `question_duration_seconds` int NOT NULL,
  `total_duration_minutes` int NOT NULL,
  `total_questions` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests`
--

LOCK TABLES `tests` WRITE;
/*!40000 ALTER TABLE `tests` DISABLE KEYS */;
INSERT INTO `tests` VALUES (1,_binary '','demo','Java Test',60,30,2),(2,_binary '','Java + SQL','Test Mixte',60,15,2),(6,_binary '','Évaluation des bases du langage SQL','Test SQL',45,30,4),(7,_binary '','Quiz transversal : Java, SQL et réseaux','Quiz Culture Informatique',60,30,6),(8,_binary '','Mathématiques et physique de base','Test Scientifique',60,40,5);
/*!40000 ALTER TABLE `tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `themes`
--

DROP TABLE IF EXISTS `themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `themes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3estny12ybh85k7y8j6gyyrep` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `themes`
--

LOCK TABLES `themes` WRITE;
/*!40000 ALTER TABLE `themes` DISABLE KEYS */;
INSERT INTO `themes` VALUES (1,'Java basics','Java'),(2,'Bases de données relationnelles','SQL'),(4,'Algèbre, analyse et géométrie','Mathématiques'),(5,'Mécanique, électricité et physique moderne','Physique'),(6,'Protocoles, adressage et services réseau','Réseaux');
/*!40000 ALTER TABLE `themes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timeslots`
--

DROP TABLE IF EXISTS `timeslots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timeslots` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booked` bit(1) NOT NULL,
  `duration_minutes` int NOT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timeslots`
--

LOCK TABLES `timeslots` WRITE;
/*!40000 ALTER TABLE `timeslots` DISABLE KEYS */;
INSERT INTO `timeslots` VALUES (1,_binary '',60,'2026-07-10 10:00:00.000000','2026-07-10 09:00:00.000000'),(2,_binary '',60,'2026-07-20 14:00:00.000000','2026-07-20 13:00:00.000000'),(3,_binary '',60,'2026-07-21 10:00:00.000000','2026-07-21 09:00:00.000000'),(4,_binary '',60,'2026-07-21 12:00:00.000000','2026-07-21 11:00:00.000000'),(6,_binary '',120,'2026-07-22 11:00:00.000000','2026-07-22 09:00:00.000000'),(7,_binary '',120,'2026-07-22 16:00:00.000000','2026-07-22 14:00:00.000000'),(8,_binary '\0',120,'2026-07-23 11:00:00.000000','2026-07-23 09:00:00.000000'),(9,_binary '',120,'2026-07-23 16:00:00.000000','2026-07-23 14:00:00.000000'),(10,_binary '\0',120,'2026-07-24 11:00:00.000000','2026-07-24 09:00:00.000000'),(11,_binary '',120,'2026-07-24 16:00:00.000000','2026-07-24 14:00:00.000000'),(12,_binary '\0',120,'2026-07-27 11:00:00.000000','2026-07-27 09:00:00.000000'),(13,_binary '\0',120,'2026-07-27 16:00:00.000000','2026-07-27 14:00:00.000000'),(14,_binary '\0',120,'2026-07-28 11:00:00.000000','2026-07-28 09:00:00.000000'),(15,_binary '\0',120,'2026-07-28 16:00:00.000000','2026-07-28 14:00:00.000000'),(16,_binary '',60,'2026-07-20 14:00:00.000000','2026-07-20 13:00:00.000000');
/*!40000 ALTER TABLE `timeslots` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-11 13:55:39
