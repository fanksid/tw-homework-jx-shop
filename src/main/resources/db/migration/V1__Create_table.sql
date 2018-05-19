CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `inventory` (
  `product_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  KEY `product_id` (`product_id`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `logistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_time` datetime DEFAULT NULL,
  `status` enum('UNSEND','TRANSPORT','DELIVERY','RECEIVED') DEFAULT NULL,
  `receive_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` enum('UNPAID','PAID','CANCEL','FINISH') NOT NULL,
  `pay_time` datetime DEFAULT NULL,
  `logistics_id` int(11) DEFAULT '0' COMMENT '0 means no logistics_id',
  `user_id` int(11) NOT NULL,
  `cancel_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logistics_id` (`logistics_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`logistics_id`) REFERENCES `logistics` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_items` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `snapshot_name` varchar(255) NOT NULL,
  `snapshot_description` text NOT NULL,
  `snapshot_price` double NOT NULL,
  KEY `order_id` (`order_id`),
  KEY `order_items_ibfk_2` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON UPDATE NO ACTION,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;