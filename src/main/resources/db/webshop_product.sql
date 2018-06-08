CREATE DATABASE  IF NOT EXISTS `webshop` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `webshop`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: webshop
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_description` varchar(45) NOT NULL,
  `product_price` decimal(10,0) NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `manufacturer_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `product_image` varchar(45) NOT NULL DEFAULT 'default.png',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `id_UNIQUE` (`product_id`),
  KEY `fk_product_manufacturer_idx` (`manufacturer_id`),
  KEY `fk_product_category1_idx` (`category_id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_manufacturer` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`manufacturer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (1,'product1','description1',1,1,1,1,'pic10.jpg');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (2,'product2','description2',2,2,2,2,'pic11.jpg');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (3,'product3','description3',3,3,3,3,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (4,'product4','description4',4,4,1,1,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (5,'product5','description5',5,5,2,2,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (6,'product6','description6',6,6,3,3,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (7,'product7','description7',7,7,1,1,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (8,'product8','description8',8,8,2,2,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (9,'product9','description9',9,9,3,3,'default.png');
INSERT INTO `product` (`product_id`, `product_name`, `product_description`, `product_price`, `product_quantity`, `manufacturer_id`, `category_id`, `product_image`) VALUES (10,'product9','description9',10,11,1,1,'default.png');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-12 20:49:13
