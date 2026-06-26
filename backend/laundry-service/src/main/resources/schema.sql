INSERT INTO service_master
    (service_name, price, active, created_at, created_by)
VALUES
    ('Wash & Fold',50.00,b'1',NOW(),'SYSTEM'),
    ('Wash & Iron',70.00,b'1',NOW(),'SYSTEM'),
    ('Dry Clean Shirt',120.00,b'1',NOW(),'SYSTEM'),
    ('Dry Clean Suit',350.00,b'1',NOW(),'SYSTEM'),
    ('Blanket Wash',250.00,b'1',NOW(),'SYSTEM'),
    ('Curtain Cleaning',300.00,b'1',NOW(),'SYSTEM'),
    ('Saree Dry Clean',180.00,b'1',NOW(),'SYSTEM'),
    ('Jacket Cleaning',220.00,b'1',NOW(),'SYSTEM'),
    ('Carpet Cleaning',500.00,b'1',NOW(),'SYSTEM'),
    ('Steam Iron',25.00,b'1',NOW(),'SYSTEM');

INSERT INTO customer
    (
        first_name,last_name,
        flat_number,building_name,
        address_line_1,address_line_2,
        landmark,city,pincode,
        email,mobile,
        active,created_at,created_by
    )
VALUES
    ('Vivek','Gohil','A101','Sunrise Residency','SG Highway','Near Iscon Mall','Iscon Circle','Ahmedabad','380015','vivek@example.com','9876543210',b'1',NOW(),'SYSTEM'),

    ('Rahul','Sharma','B202','Shivam Heights','Satellite Road',NULL,'Star Bazaar','Ahmedabad','380015','rahul@example.com','9876543211',b'1',NOW(),'SYSTEM'),

    ('Priya','Patel','C303','Green Park','Prahlad Nagar',NULL,'Garden','Ahmedabad','380015','priya@example.com','9876543212',b'1',NOW(),'SYSTEM'),

    ('Amit','Shah','D404','Tulip Residency','Bopal Road',NULL,'School','Ahmedabad','380058','amit@example.com','9876543213',b'1',NOW(),'SYSTEM'),

    ('Neha','Joshi','E505','Silver Sky','Vastrapur',NULL,'Lake','Ahmedabad','380054','neha@example.com','9876543214',b'1',NOW(),'SYSTEM'),

    ('Kunal','Mehta','F606','Palm Greens','Gota',NULL,'Temple','Ahmedabad','382481','kunal@example.com','9876543215',b'1',NOW(),'SYSTEM'),

    ('Pooja','Desai','G707','Royal Homes','Science City Road',NULL,'Circle','Ahmedabad','380060','pooja@example.com','9876543216',b'1',NOW(),'SYSTEM'),

    ('Nirav','Patel','H808','Skyline','Thaltej',NULL,'Mall','Ahmedabad','380059','nirav@example.com','9876543217',b'1',NOW(),'SYSTEM'),

    ('Mehul','Trivedi','I909','Dream City','Memnagar',NULL,'Bus Stop','Ahmedabad','380052','mehul@example.com','9876543218',b'1',NOW(),'SYSTEM'),

    ('Rina','Shah','J1001','Elite Residency','Naranpura',NULL,'Garden','Ahmedabad','380013','rina@example.com','9876543219',b'1',NOW(),'SYSTEM');


INSERT INTO orders
    (
        customer_id,
        order_number,
        pickup_date,
        delivery_date,
        total_amount,
        status,
        created_at,
        created_by
    )
VALUES
    (1,'ORD-202507140001','2025-07-14','2025-07-16',450.00,'CREATED',NOW(),'SYSTEM'),
    (2,'ORD-202507140002','2025-07-14','2025-07-17',590.00,'PROCESSING',NOW(),'SYSTEM'),
    (3,'ORD-202507140003','2025-07-14','2025-07-15',600.00,'READY',NOW(),'SYSTEM'),
    (4,'ORD-202507140004','2025-07-14','2025-07-18',300.00,'PICKED_UP',NOW(),'SYSTEM'),
    (5,'ORD-202507140005','2025-07-14','2025-07-16',420.00,'OUT_FOR_DELIVERY',NOW(),'SYSTEM'),
    (6,'ORD-202507140006','2025-07-14','2025-07-17',180.00,'DELIVERED',NOW(),'SYSTEM'),
    (7,'ORD-202507140007','2025-07-14','2025-07-18',525.00,'CREATED',NOW(),'SYSTEM'),
    (8,'ORD-202507140008','2025-07-14','2025-07-16',700.00,'PROCESSING',NOW(),'SYSTEM'),
    (9,'ORD-202507140009','2025-07-14','2025-07-17',250.00,'READY',NOW(),'SYSTEM'),
    (10,'ORD-202507140010','2025-07-14','2025-07-18',400.00,'PICKUP_SCHEDULED',NOW(),'SYSTEM');

INSERT INTO order_item
    (order_id,service_id,item_name,quantity,unit_price,line_amount,created_at,created_by)
VALUES

    (1,2,'Shirt',5,70,350,NOW(),'SYSTEM'),
    (1,10,'Pant',4,25,100,NOW(),'SYSTEM'),

    (2,4,'Suit',1,350,350,NOW(),'SYSTEM'),
    (2,5,'Blanket',1,250,250,NOW(),'SYSTEM'),

    (3,6,'Curtain',2,300,600,NOW(),'SYSTEM'),

    (4,1,'T-Shirt',6,50,300,NOW(),'SYSTEM'),

    (5,2,'Shirt',3,70,210,NOW(),'SYSTEM'),
    (5,7,'Saree',1,180,180,NOW(),'SYSTEM'),

    (6,7,'Saree',1,180,180,NOW(),'SYSTEM'),

    (7,8,'Jacket',2,220,440,NOW(),'SYSTEM'),
    (7,10,'Shirt Iron',3,25,75,NOW(),'SYSTEM'),

    (8,9,'Carpet',1,500,500,NOW(),'SYSTEM'),
    (8,5,'Blanket',1,200,200,NOW(),'SYSTEM'),

    (9,5,'Blanket',1,250,250,NOW(),'SYSTEM'),

    (10,4,'Suit',1,350,350,NOW(),'SYSTEM'),
    (10,10,'Iron',2,25,50,NOW(),'SYSTEM'),

    (2,10,'Iron',2,25,50,NOW(),'SYSTEM'),
    (3,2,'Shirt',2,70,140,NOW(),'SYSTEM'),
    (4,10,'Iron',4,25,100,NOW(),'SYSTEM'),
    (5,1,'T-Shirt',2,50,100,NOW(),'SYSTEM'),
    (6,10,'Iron',2,25,50,NOW(),'SYSTEM'),
    (7,2,'Shirt',1,70,70,NOW(),'SYSTEM'),
    (8,3,'Dry Clean Shirt',1,120,120,NOW(),'SYSTEM'),
    (9,10,'Iron',3,25,75,NOW(),'SYSTEM'),
    (10,2,'Shirt',2,70,140,NOW(),'SYSTEM'),

    (1,1,'Kurta',1,50,50,NOW(),'SYSTEM'),
    (2,3,'Shirt',1,120,120,NOW(),'SYSTEM'),
    (5,8,'Jacket',1,220,220,NOW(),'SYSTEM'),
    (7,1,'Pant',2,50,100,NOW(),'SYSTEM'),
    (9,7,'Saree',1,180,180,NOW(),'SYSTEM');


SELECT COUNT(*) FROM customer; -- 10
SELECT * FROM customer;
SELECT COUNT(*) FROM service_master; -- 10
SELECT * FROM service_master;
SELECT COUNT(*) FROM orders; -- 10
SELECT * FROM orders;
SELECT COUNT(*) FROM order_item; -- 30
SELECT * FROM order_item;
SELECT
    o.order_number,
    CONCAT(c.first_name,' ',c.last_name) customer_name,
    o.status,
    o.total_amount
FROM orders o
    JOIN customer c
        ON o.customer_id = c.customer_id
ORDER BY o.order_id DESC;
