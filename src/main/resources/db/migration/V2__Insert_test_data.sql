INSERT INTO Product(name, description, price) VALUES ('方便面', '不好吃', 2.5);
INSERT INTO Product(name, description, price) VALUES ('电脑', '坚如磐石', 5999);
INSERT INTO Product(name, description, price) VALUES ('方便面', '很好吃，康师傅', 4.8);
INSERT INTO Product(name, description, price) VALUES ('怡宝水', '550ml', 1.5);

INSERT INTO Inventory (productId, count) VALUES(1, 4);
INSERT INTO Inventory (productId, count) VALUES(2, 2);
INSERT INTO Inventory (productId, count) VALUES(3, 5);
INSERT INTO Inventory (productId, count) VALUES(4, 7);

INSERT INTO Logistics(outboundTime, status) VALUES(NOW(), 'inbound');
INSERT INTO jxOrder(status, createTime, payTime, logisticsId, userId) VALUES('payed', NOW(), NOW(), 1, 1);

INSERT INTO OrderItem(orderId, productId, purchaseCount, snapshotName, snapshotDescription, snapshotPrice) VALUES(1, 1, 2, '方便面', '不好吃', 2.5);
INSERT INTO OrderItem(orderId, productId, purchaseCount, snapshotName, snapshotDescription, snapshotPrice) VALUES(1, 2, 3, '电脑', '坚如磐石', 5999);