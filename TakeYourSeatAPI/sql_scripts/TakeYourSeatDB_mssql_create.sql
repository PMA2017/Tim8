CREATE TABLE [Restaurant] (
	Id int NOT NULL,
	Name varchar(50) NOT NULL,
	Description varchar(300) NOT NULL,
	Manager int NOT NULL,
	Image image NOT NULL,
	Phone varchar(50) NOT NULL,
	Email varchar(100) NOT NULL,
	WebSite varchar(50) NOT NULL,
	Address int NOT NULL,
  CONSTRAINT [PK_RESTAURANT] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [User] (
	Id int NOT NULL,
	Name varchar(50) NOT NULL,
	Address varchar(100) NOT NULL,
	Email varchar(100) NOT NULL,
	Password varchar(100) NOT NULL,
	Role int NOT NULL,
  CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Role] (
	Id int NOT NULL,
	Name varchar(20) NOT NULL,
  CONSTRAINT [PK_ROLE] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Location] (
	Id int NOT NULL,
	Latitude decimal NOT NULL,
	Longitude decimal NOT NULL,
  CONSTRAINT [PK_LOCATION] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Address] (
	Id int NOT NULL,
	Country varchar(50) NOT NULL,
	Street varchar(100) NOT NULL,
	Number varchar(10) NOT NULL,
	Location int NOT NULL,
  CONSTRAINT [PK_ADDRESS] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [RestaurantMenuCategory] (
	Id int NOT NULL,
	Name varchar(30) NOT NULL,
	Restaurant int NOT NULL,
  CONSTRAINT [PK_RESTAURANTMENUCATEGORY] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [MenuItem] (
	Id int NOT NULL,
	Category int NOT NULL,
	Name varchar(30) NOT NULL,
	Description varchar(100) NOT NULL,
	Price decimal NOT NULL,
  CONSTRAINT [PK_MENUITEM] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [RestaurantTable] (
	Id int NOT NULL,
	Number int NOT NULL,
	Restaurant int NOT NULL,
  CONSTRAINT [PK_RESTAURANTTABLE] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Reservation] (
	Id int NOT NULL,
	StartDate datetime NOT NULL,
	EndDate datetime NOT NULL,
	[User] int NOT NULL,
	RestaurantTable int NOT NULL,
  CONSTRAINT [PK_RESERVATION] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Comment] (
	Id int NOT NULL,
	Description varchar(300) NOT NULL,
	Restaurant int NOT NULL,
	[User] int NOT NULL,
  CONSTRAINT [PK_COMMENT] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Rating] (
	Id int NOT NULL,
	Rank int NOT NULL,
	Restaurant int NOT NULL,
	[User] int NOT NULL,
  CONSTRAINT [PK_RATING] PRIMARY KEY CLUSTERED
  (
  [Id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
ALTER TABLE [Restaurant] WITH CHECK ADD CONSTRAINT [Restaurant_fk0] FOREIGN KEY ([Manager]) REFERENCES [User]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [Restaurant] CHECK CONSTRAINT [Restaurant_fk0]
GO
ALTER TABLE [Restaurant] WITH CHECK ADD CONSTRAINT [Restaurant_fk1] FOREIGN KEY ([Address]) REFERENCES [Address]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [Restaurant] CHECK CONSTRAINT [Restaurant_fk1]
GO

ALTER TABLE [User] WITH CHECK ADD CONSTRAINT [User_fk0] FOREIGN KEY ([Role]) REFERENCES [Role]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [User] CHECK CONSTRAINT [User_fk0]
GO



ALTER TABLE [Address] WITH CHECK ADD CONSTRAINT [Address_fk0] FOREIGN KEY ([Location]) REFERENCES [Location]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [Address] CHECK CONSTRAINT [Address_fk0]
GO

ALTER TABLE [RestaurantMenuCategory] WITH CHECK ADD CONSTRAINT [RestaurantMenuCategory_fk0] FOREIGN KEY ([Restaurant]) REFERENCES [Restaurant]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [RestaurantMenuCategory] CHECK CONSTRAINT [RestaurantMenuCategory_fk0]
GO

ALTER TABLE [MenuItem] WITH CHECK ADD CONSTRAINT [MenuItem_fk0] FOREIGN KEY ([Category]) REFERENCES [RestaurantMenuCategory]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [MenuItem] CHECK CONSTRAINT [MenuItem_fk0]
GO

ALTER TABLE [RestaurantTable] WITH CHECK ADD CONSTRAINT [RestaurantTable_fk0] FOREIGN KEY ([Restaurant]) REFERENCES [Restaurant]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [RestaurantTable] CHECK CONSTRAINT [RestaurantTable_fk0]
GO

ALTER TABLE [Reservation] WITH CHECK ADD CONSTRAINT [Reservation_fk0] FOREIGN KEY ([User]) REFERENCES [User]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [Reservation] CHECK CONSTRAINT [Reservation_fk0]
GO
ALTER TABLE [Reservation] WITH CHECK ADD CONSTRAINT [Reservation_fk1] FOREIGN KEY ([RestaurantTable]) REFERENCES [RestaurantTable]([Id])

GO
ALTER TABLE [Reservation] CHECK CONSTRAINT [Reservation_fk1]
GO

ALTER TABLE [Comment] WITH CHECK ADD CONSTRAINT [Comment_fk0] FOREIGN KEY ([Restaurant]) REFERENCES [Restaurant]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [Comment] CHECK CONSTRAINT [Comment_fk0]
GO
ALTER TABLE [Comment] WITH CHECK ADD CONSTRAINT [Comment_fk1] FOREIGN KEY ([User]) REFERENCES [User]([Id])

GO
ALTER TABLE [Comment] CHECK CONSTRAINT [Comment_fk1]
GO

ALTER TABLE [Rating] WITH CHECK ADD CONSTRAINT [Rating_fk0] FOREIGN KEY ([Restaurant]) REFERENCES [Restaurant]([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [Rating] CHECK CONSTRAINT [Rating_fk0]
GO
ALTER TABLE [Rating] WITH CHECK ADD CONSTRAINT [Rating_fk1] FOREIGN KEY ([User]) REFERENCES [User]([Id])

GO
ALTER TABLE [Rating] CHECK CONSTRAINT [Rating_fk1]
GO

