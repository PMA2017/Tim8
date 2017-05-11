-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-05-02 11:49:08.641

-- tables
-- Table: Address
CREATE TABLE Address (
    Id int  NOT NULL IDENTITY,
    Coutry varchar(50)  NOT NULL,
    Street varchar(100)  NOT NULL,
    Number varchar(10)  NOT NULL,
    Location int  NOT NULL,
    CONSTRAINT Address_pk PRIMARY KEY  (Id)
);

-- Table: Comment
CREATE TABLE Comment (
    Id int  NOT NULL IDENTITY,
    Description varchar(300)  NOT NULL,
    "User" int  NOT NULL,
    Restaurant int  NOT NULL,
    CONSTRAINT Comment_pk PRIMARY KEY  (Id)
);

-- Table: Location
CREATE TABLE Location (
    Id int  NOT NULL IDENTITY,
    Latitude decimal  NOT NULL,
    Longitude decimal  NOT NULL,
    CONSTRAINT Location_pk PRIMARY KEY  (Id)
);

-- Table: MenuItem
CREATE TABLE MenuItem (
    Id int  NOT NULL IDENTITY,
    Category int  NOT NULL,
    Name varchar(30)  NOT NULL,
    Description varchar(100)  NOT NULL,
    Price decimal  NOT NULL,
    CONSTRAINT MenuItem_pk PRIMARY KEY  (Id)
);

-- Table: Rating
CREATE TABLE Rating (
    Id int  NOT NULL IDENTITY,
    Rank int  NOT NULL,
    RestaurantTable int  NOT NULL,
    "User" int  NOT NULL,
    CONSTRAINT Rating_pk PRIMARY KEY  (Id)
);

-- Table: Reservation
CREATE TABLE Reservation (
    Id int  NOT NULL IDENTITY,
    "User" int  NOT NULL,
    RestaurantTable int  NOT NULL,
    CONSTRAINT Reservation_pk PRIMARY KEY  (Id)
);

-- Table: Restaurant
CREATE TABLE Restaurant (
    Id int  NOT NULL IDENTITY,
    Manager int  NOT NULL,
    Name varchar(50)  NOT NULL,
    Description varchar(300)  NOT NULL,
    Image image  NOT NULL,
    Phone varchar(50)  NOT NULL,
    Email varchar(100)  NOT NULL,
    Webste varchar(50)  NOT NULL,
    Address int  NOT NULL,
    CONSTRAINT Restaurant_pk PRIMARY KEY  (Id)
);

-- Table: RestaurantMenuCategory
CREATE TABLE RestaurantMenuCategory (
    Id int  NOT NULL IDENTITY,
    Name varchar(30)  NOT NULL,
    Restaurant int  NOT NULL,
    CONSTRAINT RestaurantMenuCategory_pk PRIMARY KEY  (Id)
);

-- Table: RestaurantTable
CREATE TABLE RestaurantTable (
    Id int  NOT NULL IDENTITY,
    Number int  NOT NULL,
    Restaurant int  NOT NULL,
    CONSTRAINT RestaurantTable_pk PRIMARY KEY  (Id)
);

-- Table: Role
CREATE TABLE Role (
    Id int  NOT NULL IDENTITY,
    Name varchar(20)  NOT NULL,
    CONSTRAINT Role_pk PRIMARY KEY  (Id)
);

-- Table: User
CREATE TABLE "User" (
    Id int  NOT NULL IDENTITY,
    Name varchar(50)  NOT NULL,
    LastName varchar(50)  NOT NULL,
    Address varchar(100)  NOT NULL,
    Email varchar(100)  NOT NULL,
    Password varchar(50)  NOT NULL,
    Role int  NOT NULL,
    CONSTRAINT User_pk PRIMARY KEY  (Id)
);

-- foreign keys
-- Reference: Address_Location (table: Address)
ALTER TABLE Address ADD CONSTRAINT Address_Location
    FOREIGN KEY (Location)
    REFERENCES Location (Id);

-- Reference: Comment_Restaurant (table: Comment)
ALTER TABLE Comment ADD CONSTRAINT Comment_Restaurant
    FOREIGN KEY (Restaurant)
    REFERENCES Restaurant (Id);

-- Reference: Comment_User (table: Comment)
ALTER TABLE Comment ADD CONSTRAINT Comment_User
    FOREIGN KEY ("User")
    REFERENCES "User" (Id);

-- Reference: MenuItem_RestaurantMenuCategory (table: MenuItem)
ALTER TABLE MenuItem ADD CONSTRAINT MenuItem_RestaurantMenuCategory
    FOREIGN KEY (Category)
    REFERENCES RestaurantMenuCategory (Id);

-- Reference: Rating_RestaurantTable (table: Rating)
ALTER TABLE Rating ADD CONSTRAINT Rating_RestaurantTable
    FOREIGN KEY (RestaurantTable)
    REFERENCES RestaurantTable (Id);

-- Reference: Rating_User (table: Rating)
ALTER TABLE Rating ADD CONSTRAINT Rating_User
    FOREIGN KEY ("User")
    REFERENCES "User" (Id);

-- Reference: Reservation_RestaurantTable (table: Reservation)
ALTER TABLE Reservation ADD CONSTRAINT Reservation_RestaurantTable
    FOREIGN KEY (RestaurantTable)
    REFERENCES RestaurantTable (Id);

-- Reference: Reservation_User (table: Reservation)
ALTER TABLE Reservation ADD CONSTRAINT Reservation_User
    FOREIGN KEY ("User")
    REFERENCES "User" (Id);

-- Reference: RestaurantMenuCategory_Restaurant (table: RestaurantMenuCategory)
ALTER TABLE RestaurantMenuCategory ADD CONSTRAINT RestaurantMenuCategory_Restaurant
    FOREIGN KEY (Restaurant)
    REFERENCES Restaurant (Id);

-- Reference: RestaurantTable_Restaurant (table: RestaurantTable)
ALTER TABLE RestaurantTable ADD CONSTRAINT RestaurantTable_Restaurant
    FOREIGN KEY (Restaurant)
    REFERENCES Restaurant (Id);

-- Reference: Restaurant_Address (table: Restaurant)
ALTER TABLE Restaurant ADD CONSTRAINT Restaurant_Address
    FOREIGN KEY (Address)
    REFERENCES Address (Id);

-- Reference: Restaurant_User (table: Restaurant)
ALTER TABLE Restaurant ADD CONSTRAINT Restaurant_User
    FOREIGN KEY (Manager)
    REFERENCES "User" (Id);

-- Reference: User_Role (table: User)
ALTER TABLE "User" ADD CONSTRAINT User_Role
    FOREIGN KEY (Role)
    REFERENCES Role (Id);

-- End of file.

