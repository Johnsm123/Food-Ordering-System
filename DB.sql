-- Create Database
CREATE DATABASE appdb;
USE appdb;

-- Customer Table
CREATE TABLE customer (
    customerId INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phoneNumber VARCHAR(15),
    password VARCHAR(255) NOT NULL
);

-- Restaurant Table
CREATE TABLE restaurant (
    restaurantId INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    cuisineType VARCHAR(50),
    contactNumber VARCHAR(15)
);

-- MenuItem Table
CREATE TABLE menu_item (
    itemId INT PRIMARY KEY,
    restaurantId INT,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    description VARCHAR(255),
    availableQuantity INT NOT NULL,
    FOREIGN KEY (restaurantId) REFERENCES restaurant(restaurantId)
);

-- Order Table (Note: `order` is a reserved keyword, so use backticks)
CREATE TABLE `order` (
    orderId INT PRIMARY KEY,
    customerId INT,
    restaurantId INT,
    orderStatus VARCHAR(20),
    totalPrice DOUBLE NOT NULL,
    deliveryAddress VARCHAR(255),
    FOREIGN KEY (customerId) REFERENCES customer(customerId),
    FOREIGN KEY (restaurantId) REFERENCES restaurant(restaurantId)
);

-- OrderItem Table
CREATE TABLE order_item (
    orderId INT,
    itemId INT,
    quantity INT NOT NULL,
    PRIMARY KEY (orderId, itemId),
    FOREIGN KEY (orderId) REFERENCES `order`(orderId),
    FOREIGN KEY (itemId) REFERENCES menu_item(itemId)
);

-- Payment Table
CREATE TABLE payment (
    paymentId INT PRIMARY KEY,
    orderId INT,
    paymentDate DATETIME NOT NULL,
    paymentStatus VARCHAR(20),
    amountPaid DOUBLE NOT NULL,
    FOREIGN KEY (orderId) REFERENCES `order`(orderId)
);
