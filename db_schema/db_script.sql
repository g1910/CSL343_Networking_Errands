-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 03, 2015 at 02:57 PM
-- Server version: 5.5.41-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `csl343`
--
CREATE DATABASE IF NOT EXISTS `csl343` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `csl343`;

-- --------------------------------------------------------

--
-- Table structure for table `Auction`
--

CREATE TABLE IF NOT EXISTS `Auction` (
  `idAuction` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `expctd_time` datetime DEFAULT NULL,
  `description` varchar(140) DEFAULT NULL,
  `idUser` int(11) NOT NULL,
  `orderLimit` int(11) NOT NULL,
  `minPrice` int(11) NOT NULL,
  PRIMARY KEY (`idAuction`),
  KEY `fk_Auction_User_idx` (`idUser`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2553027 ;

--
-- Dumping data for table `Auction`
--

INSERT INTO `Auction` (`idAuction`, `location`, `start_time`, `end_time`, `expctd_time`, `description`, `idUser`, `orderLimit`, `minPrice`) VALUES
(2553025, 'test', '2015-04-28 13:27:56', '2015-04-28 13:30:00', '2015-04-28 19:30:00', 'test', 30, 9, 70),
(2553022, 'kota', '2015-04-28 12:19:23', '2015-04-28 12:21:00', '2015-04-28 21:19:00', 'hi', 27, 5, 23),
(2553023, 'Chandigarh ', '2015-04-28 12:27:07', '2015-04-28 13:27:00', '2015-04-28 19:27:00', 'Gf', 13, 5, 80),
(2553024, 'Chandigarh', '2015-04-28 13:21:39', '2015-04-28 18:20:00', '2015-04-29 08:49:00', 'elaante', 31, 10, 10),
(2553026, 'Chandigarh', '2015-05-03 12:09:23', '2015-05-03 16:08:00', '2015-05-03 23:09:00', 'need computer networks book', 30, 5, 20);

-- --------------------------------------------------------

--
-- Table structure for table `Auction_Categories`
--

CREATE TABLE IF NOT EXISTS `Auction_Categories` (
  `idCategory` int(11) NOT NULL AUTO_INCREMENT,
  `idAuction` int(11) NOT NULL,
  `minPrice` int(11) NOT NULL,
  PRIMARY KEY (`idCategory`),
  KEY `idAuction` (`idAuction`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `Auction_Categories`
--

INSERT INTO `Auction_Categories` (`idCategory`, `idAuction`, `minPrice`) VALUES
(18, 2553021, 20),
(19, 2553022, 23),
(20, 2553023, 80),
(21, 2553024, 10),
(22, 2553025, 70),
(23, 2553026, 20);

-- --------------------------------------------------------

--
-- Table structure for table `Bid`
--

CREATE TABLE IF NOT EXISTS `Bid` (
  `idBid` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  `bid_description` varchar(140) NOT NULL,
  PRIMARY KEY (`idBid`),
  KEY `fk_Bid_User1_idx` (`idUser`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=71 ;

--
-- Dumping data for table `Bid`
--

INSERT INTO `Bid` (`idBid`, `location`, `idUser`, `bid_description`) VALUES
(59, 'Chd', 27, 'To youngest'),
(60, 'Tu', 29, 'Tuhbf'),
(61, 'Ghj', 13, 'Chh'),
(62, 'Chandigarh', 31, 'Hi'),
(63, 'Chandigarh', 31, 'Hi'),
(64, 'Test', 27, 'Test'),
(65, 'Chandigarh', 29, 'Two pizzas'),
(66, 'Gsi', 27, 'Sad'),
(67, 'Gsi', 27, 'Sad'),
(68, 'Gsi', 27, 'Sad'),
(69, 'Gsi', 27, 'Sad'),
(70, 'Gsi', 27, 'Sad');

-- --------------------------------------------------------

--
-- Table structure for table `Feedback`
--

CREATE TABLE IF NOT EXISTS `Feedback` (
  `idFeedback` int(11) NOT NULL AUTO_INCREMENT,
  `idSender` int(11) NOT NULL,
  `idReceiver` int(11) NOT NULL,
  `stars` tinyint(1) NOT NULL,
  `description` varchar(140) DEFAULT NULL,
  `status` int(1) NOT NULL,
  `role` int(1) NOT NULL,
  `idAuction` int(11) NOT NULL,
  PRIMARY KEY (`idFeedback`),
  KEY `fk_Feedback_User1_idx` (`idSender`),
  KEY `fk_Feedback_User2_idx` (`idReceiver`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=36 ;

--
-- Dumping data for table `Feedback`
--

INSERT INTO `Feedback` (`idFeedback`, `idSender`, `idReceiver`, `stars`, `description`, `status`, `role`, `idAuction`) VALUES
(21, 200, 400, 0, NULL, 0, 1, 1800),
(20, 400, 200, 0, NULL, 0, 0, 1800),
(22, 400, 300, 0, NULL, 0, 0, 1800),
(23, 300, 400, 0, NULL, 0, 1, 1800),
(24, 400, 200, 0, NULL, 0, 0, 1900),
(25, 200, 400, 0, NULL, 0, 1, 1900),
(26, 400, 300, 0, NULL, 0, 0, 1900),
(27, 300, 400, 0, NULL, 0, 1, 1900),
(28, 27, 29, 5, 'tiiff', 1, 0, 2553022),
(29, 29, 27, 5, 'awesomee', 1, 1, 2553022),
(30, 500, 30, 0, NULL, 0, 0, 2553021),
(31, 30, 500, 0, NULL, 0, 1, 2553021),
(32, 30, 27, 0, NULL, 0, 0, 2553025),
(33, 27, 30, 0, NULL, 0, 1, 2553025),
(34, 30, 29, 4, 'great ', 1, 0, 2553025),
(35, 29, 30, 3, 'hhjjsjii', 1, 1, 2553025);

-- --------------------------------------------------------

--
-- Table structure for table `Order`
--

CREATE TABLE IF NOT EXISTS `Order` (
  `item` varchar(15) NOT NULL,
  `quantity` int(10) unsigned DEFAULT NULL,
  `price_per_item` decimal(8,2) unsigned DEFAULT NULL,
  `idBid` int(11) NOT NULL,
  PRIMARY KEY (`idBid`,`item`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Order`
--

INSERT INTO `Order` (`item`, `quantity`, `price_per_item`, `idBid`) VALUES
('pizza', 2, 23.00, 59),
('tygg', 2, 12.00, 60),
('pixza', 3, 59.00, 62),
('pixza', 3, 59.00, 63),
('tua', 1, 12.00, 64),
('pizzas', 1, 100.00, 65),
('ee', 1, 555.00, 65),
('zgsh', 2, 2.00, 66),
('zgsh', 2, 2.00, 67),
('zgsh', 2, 2.00, 68),
('zgsh', 2, 2.00, 69),
('zgsh', 2, 2.00, 70);

-- --------------------------------------------------------

--
-- Table structure for table `Placed`
--

CREATE TABLE IF NOT EXISTS `Placed` (
  `idBid` int(11) NOT NULL,
  `idAuction` int(11) NOT NULL,
  `Price` int(11) NOT NULL,
  `status` char(1) NOT NULL,
  `idCategory` int(11) DEFAULT NULL,
  PRIMARY KEY (`idBid`,`idAuction`),
  KEY `fk_Placed_Bid1_idx` (`idBid`),
  KEY `fk_Placed_Auction1_idx` (`idAuction`),
  KEY `idCategory` (`idCategory`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Placed`
--

INSERT INTO `Placed` (`idBid`, `idAuction`, `Price`, `status`, `idCategory`) VALUES
(59, 2553021, 28, 'A', 18),
(62, 2553023, 0, 'P', NULL),
(60, 2553021, 24, 'C', 19),
(63, 2553023, 0, 'P', NULL),
(60, 2553022, 0, 'P', NULL),
(60, 2553024, 80, 'A', 21),
(64, 2553025, 22, 'C', 22),
(65, 2553025, 22, 'C', 22),
(66, 2553026, 0, 'P', NULL),
(67, 2553026, 0, 'P', NULL),
(68, 2553026, 0, 'P', NULL),
(69, 2553026, 0, 'P', NULL),
(70, 2553026, 0, 'P', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` char(10) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `picurl` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `emailId_UNIQUE` (`emailId`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`idUser`, `name`, `phone`, `emailId`, `address`, `picurl`) VALUES
(27, 'MOHIT GARG', NULL, 'nikhil55591@gmail.com', NULL, 'https://lh3.googleusercontent.com/-fDalKnqQw_0/AAAAAAAAAAI/AAAAAAAAAOI/2438_Q2XWMY/photo.jpg?sz=500'),
(29, 'Gaurav Kushwaha', NULL, 'gauravkushwaha999@gmail.com', NULL, 'https://lh3.googleusercontent.com/-Qr-YnznCvOg/AAAAAAAAAAI/AAAAAAAABac/0VdQ7nkYnvE/photo.jpg?sz=500'),
(30, 'Jaideep Singh', '8968388990', 'jaideepst@iitrpr.ac.in', 'kabristaan', 'https://lh5.googleusercontent.com/-6YUHsFZKXsU/AAAAAAAAAAI/AAAAAAAAAFw/NB84zj-hals/photo.jpg?sz=500'),
(31, 'Jeevanjot Singh', NULL, 'jeevan22jot@gmail.com', NULL, 'https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg?sz=500');

-- --------------------------------------------------------

--
-- Table structure for table `UserRequests`
--

CREATE TABLE IF NOT EXISTS `UserRequests` (
  `reqID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `item` varchar(50) NOT NULL,
  `location` varchar(45) NOT NULL,
  `exptime` time NOT NULL,
  `expdate` date NOT NULL,
  `description` varchar(140) DEFAULT NULL,
  PRIMARY KEY (`reqID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=159 ;

--
-- Dumping data for table `UserRequests`
--

INSERT INTO `UserRequests` (`reqID`, `email`, `item`, `location`, `exptime`, `expdate`, `description`) VALUES
(56, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(55, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(54, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(53, 'jaideepst@iitrpr.ac.in', '', '', '00:00:00', '0000-00-00', NULL),
(52, 'gauravkushwaha999@gmail.com', 'Pizzahut crown pizza', 'Chandigarh', '20:15:00', '2015-03-16', ''),
(51, 'gauravkushwaha999@gmail.com', 'Pizzahut crown pizza', 'Chandigarh', '20:15:00', '2015-03-16', ''),
(50, 'gauravkushwaha999@gmail.com', 'KFC Brownie Sundae', 'Chandigarh', '21:55:00', '2015-03-17', 'Bring 3 today or tomorrow'),
(49, 'gauravkushwaha999@gmail.com', 'KFC Veg zinger', 'Chandigarh', '22:00:00', '2015-03-16', 'Bring 2'),
(48, 'gauravkushwaha999@gmail.com', 'KFC Veg zinger', 'Chandigarh', '22:00:00', '2015-03-16', ''),
(47, 'jaideepst@iitrpr.ac.in', 'Belgian Chocolate Shot', 'ropar', '03:51:00', '2015-03-18', 'Need a Belgian Chocolate Shot worth Rs. 30 from Cafe Coffee Day'),
(46, 'jaideepst@iitrpr.ac.in', 'pizza ', 'chandigarh ', '23:42:00', '2015-03-28', 'Single Cheese Burst pizza needed only'),
(57, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(156, 'gauravkushwaha999@gmail.com', 'Pizza', 'Chandigarh', '17:06:00', '2015-04-28', 'Dominos'),
(61, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(62, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(63, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(64, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(158, 'gauravkushwaha999@gmail.com', 'Bmn', 'Hjk', '15:15:00', '2015-04-28', 'Mnk'),
(155, 'gauravkushwaha999@gmail.com', 'Pizza', 'Chandigarh', '17:06:00', '2015-04-28', 'Dominos'),
(67, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(157, 'gauravkushwaha999@gmail.com', 'Pizza', 'Chandigarh', '17:06:00', '2015-04-28', 'Dominos'),
(69, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(70, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(154, 'nvnjangir479@gmail.com', 'Pizzahut Crown pizza', 'Chandigarh', '14:00:00', '2015-04-26', 'Large size'),
(72, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(73, 'jaideepst@iitrpr.ac.in', 'Dominos Pizza', 'Chandigarh', '20:00:00', '2015-04-15', 'Cheese Burst pizza needed only'),
(75, 'gauravkushwaha999@gmail.com', 'Dghh', 'Ghjhh', '23:26:00', '2015-03-18', 'Fgghh'),
(76, 'nikhil55591@gmail.com', 'aalo', 'chdhdsykhswujcatj', '17:17:00', '2015-03-27', ''),
(77, 'nikhil55591@gmail.com', 'Dirty', 'Fahey truck, Uyghur', '13:07:00', '2015-09-26', 'Urgency'),
(78, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(79, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(80, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(81, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(82, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(83, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(84, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(85, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(86, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(87, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(88, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(89, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(90, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(91, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(92, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(93, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(94, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(95, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(96, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(97, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(98, 'nikhil55591@gmail.com', 'Hedge', 'Greg', '19:51:00', '2015-08-22', 'Gritty\n'),
(99, 'nikhil55591@gmail.com', 'Rtyy', 'Fggg', '19:52:00', '2015-03-22', 'Dfggg'),
(100, 'gauravkushwaha999@gmail.com', 'Pizzahut crown pizza', 'Chandigarh', '12:13:00', '2015-03-24', 'Must be large in size'),
(101, 'gauravkushwaha999@gmail.com', 'Pizzahut crown pizza', 'Chandigarh', '12:17:00', '2015-03-24', 'Must be large in size'),
(102, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(103, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(104, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(105, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(106, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(107, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(108, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(109, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(110, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(111, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(112, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(113, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(114, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(115, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(116, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(117, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(118, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(119, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(120, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(121, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(122, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(123, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(124, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(125, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(126, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(127, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(128, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(129, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(130, 'nvnjangir479@gmail.com', 'Burger', 'Hostel', '20:23:00', '2015-03-23', 'All'),
(131, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(132, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(133, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(134, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(135, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(136, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(137, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(138, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(139, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(140, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(141, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(142, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(143, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(144, 'nvnjangir479@gmail.com', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(145, '', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(146, '', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(147, '', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(148, '', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(149, '', 'Abc', 'Aaaa', '20:41:00', '2015-03-23', 'Gajdk'),
(150, 'nvnjangir479@gmail.com', 'Pizza', 'Café', '20:56:00', '2015-03-23', 'Hola'),
(151, 'nvnjangir479@gmail.com', 'Pizza', 'Café', '20:56:00', '2015-03-23', 'Hola'),
(152, 'nvnjangir479@gmail.com', 'Cheeseburger', 'Cafe', '21:10:00', '2015-03-23', 'Hhhhh'),
(153, 'nvnjangir479@gmail.com', 'A', 'B', '23:30:00', '2015-04-24', 'C');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
