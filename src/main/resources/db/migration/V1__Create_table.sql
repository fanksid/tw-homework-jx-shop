CREATE TABLE `Product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Inventory` (
  `productId` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  KEY `productId` (`productId`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Logistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outboundTime` datetime DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `signedTime` datetime DEFAULT NULL,
  `deliveryMan` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `jxOrder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `payTime` datetime DEFAULT NULL,
  `logisticsId` int(11) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `cancelTime` datetime DEFAULT NULL,
  `finishTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logistics_id` (`logisticsId`),
  CONSTRAINT `jxOrder_ibfk_1` FOREIGN KEY (`logisticsId`) REFERENCES `Logistics` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `OrderItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `purchaseCount` int(11) NOT NULL,
  `snapshotName` varchar(255) NOT NULL,
  `snapshotDescription` text NOT NULL,
  `snapshotPrice` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderId` (`orderId`),
  KEY `orderItem_ibfk_2` (`productId`),
  CONSTRAINT `orderItem_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `jxOrder` (`id`) ON UPDATE NO ACTION,
  CONSTRAINT `orderItem_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;