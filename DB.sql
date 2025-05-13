use appdb;

create table customer(
    customerId int PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phoneNumber VARCHAR(15),
    password VARCHAR(255) NOT NULL
);
create Table restaurant(
    restaurantId int PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    cuisineType VARCHAR(55),
    contactNumber VARCHAR(15)
);

create Table menuItem(
    itemId int PRIMARY KEY AUTO_INCREMENT,
    restaurantId INT,
    name VARCHAR(100) NOT null,
    price DOUBLE NOT NULL,
    description VARCHAR(255),
    availableQuantity int not null,
    Foreign Key (restaurantId) REFERENCES restaurant(restaurantId)
);

create table `order`(
    orderId int primary key AUTO_INCREMENT,
    customerId int,
    restaurantId int,
    orderStatus VARCHAR(35),
    totalPrice DOUBLE NOT NULL,
    deliveryAddress VARCHAR(255),
    Foreign Key (customerId) REFERENCES customer(customerId),
    Foreign Key (restaurantId) REFERENCES restaurant(restaurantId)
);

create Table orderItem(
    orderId int,
    itemId INT,
    quantity INT NOT NULL,
    PRIMARY KEY(orderId, itemId),
    Foreign Key (orderId) REFERENCES `order`(orderId),
    Foreign Key (itemId) REFERENCES menuItem(itemId)
);

create Table payment(
    paymentId int PRIMARY KEY,
    orderId INT,
    paymentDate DATETIME NOT NULL,
    paymentStatus VARCHAR(25),
    amountPaid DOUBLE NOT NULL,
    Foreign Key (orderId) REFERENCES `order`(orderId)
);
