USE [TakeYourSeatDB]
GO
ALTER TABLE [dbo].[TableReservation] DROP CONSTRAINT [FK_TableReservation_RestaurantTable]
GO
ALTER TABLE [dbo].[TableReservation] DROP CONSTRAINT [FK_TableReservation_Reservation]
GO
ALTER TABLE [dbo].[RestaurantTable] DROP CONSTRAINT [RestaurantTable_Restaurant]
GO
ALTER TABLE [dbo].[Restaurant] DROP CONSTRAINT [FK_Restaurant_Location]
GO
ALTER TABLE [dbo].[ReservationFriends] DROP CONSTRAINT [FK_ReservationFriends_User]
GO
ALTER TABLE [dbo].[ReservationFriends] DROP CONSTRAINT [FK_ReservationFriends_Reservation]
GO
ALTER TABLE [dbo].[Reservation] DROP CONSTRAINT [Reservation_User]
GO
ALTER TABLE [dbo].[Reservation] DROP CONSTRAINT [FK_Reservation_Restaurant]
GO
ALTER TABLE [dbo].[Rating] DROP CONSTRAINT [Rating_User]
GO
ALTER TABLE [dbo].[Rating] DROP CONSTRAINT [FK_Rating_Restaurant]
GO
ALTER TABLE [dbo].[MenuItem] DROP CONSTRAINT [FK_MenuItem_Restaurant]
GO
ALTER TABLE [dbo].[Friends] DROP CONSTRAINT [FK_Friends_User1]
GO
ALTER TABLE [dbo].[Friends] DROP CONSTRAINT [FK_Friends_User]
GO
ALTER TABLE [dbo].[Comment] DROP CONSTRAINT [FK_Comment_Restaurant]
GO
ALTER TABLE [dbo].[Comment] DROP CONSTRAINT [Comment_User]
GO
/****** Object:  Table [dbo].[User]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[User]
GO
/****** Object:  Table [dbo].[TableReservation]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[TableReservation]
GO
/****** Object:  Table [dbo].[RestaurantTable]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[RestaurantTable]
GO
/****** Object:  Table [dbo].[Restaurant]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[Restaurant]
GO
/****** Object:  Table [dbo].[ReservationFriends]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[ReservationFriends]
GO
/****** Object:  Table [dbo].[Reservation]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[Reservation]
GO
/****** Object:  Table [dbo].[Rating]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[Rating]
GO
/****** Object:  Table [dbo].[MenuItem]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[MenuItem]
GO
/****** Object:  Table [dbo].[Location]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[Location]
GO
/****** Object:  Table [dbo].[Friends]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[Friends]
GO
/****** Object:  Table [dbo].[Comment]    Script Date: 9/9/2017 17:31:59 ******/
DROP TABLE [dbo].[Comment]
GO
/****** Object:  Table [dbo].[Comment]    Script Date: 9/9/2017 17:31:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comment](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Description] [varchar](300) NOT NULL,
	[User] [int] NOT NULL,
	[Restaurant] [int] NOT NULL,
 CONSTRAINT [Comment_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Friends]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Friends](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[User] [int] NOT NULL,
	[Friend] [int] NOT NULL,
 CONSTRAINT [PK_Friends] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Location]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Location](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Latitude] [decimal](9, 6) NOT NULL,
	[Longitude] [decimal](9, 6) NOT NULL,
 CONSTRAINT [Location_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[MenuItem]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MenuItem](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Category] [int] NULL,
	[Name] [varchar](50) NOT NULL,
	[Description] [varchar](100) NOT NULL,
	[Price] [decimal](18, 0) NOT NULL,
	[Restaurant] [int] NULL,
 CONSTRAINT [MenuItem_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Rating]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rating](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Rank] [int] NOT NULL,
	[Restaurant] [int] NOT NULL,
	[User] [int] NOT NULL,
 CONSTRAINT [Rating_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Reservation]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reservation](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[User] [int] NOT NULL,
	[RestaurantId] [int] NOT NULL,
 CONSTRAINT [Reservation_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ReservationFriends]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ReservationFriends](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ReservationId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[Status] [int] NOT NULL,
 CONSTRAINT [PK_ReservationFriends] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Restaurant]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Restaurant](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](50) NOT NULL,
	[Description] [varchar](300) NOT NULL,
	[Image] [nvarchar](max) NULL,
	[Phone] [varchar](50) NOT NULL,
	[Email] [varchar](100) NOT NULL,
	[Webste] [varchar](50) NOT NULL,
	[Address] [varchar](100) NOT NULL,
	[Location] [int] NOT NULL,
 CONSTRAINT [Restaurant_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[RestaurantTable]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RestaurantTable](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Number] [int] NOT NULL,
	[Restaurant] [int] NOT NULL,
 CONSTRAINT [RestaurantTable_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TableReservation]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TableReservation](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[TableId] [int] NOT NULL,
	[ReservationId] [int] NOT NULL,
 CONSTRAINT [PK_TableReservation] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[User]    Script Date: 9/9/2017 17:32:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Token] [varchar](max) NULL,
	[Name] [varchar](50) NOT NULL,
	[LastName] [varchar](50) NOT NULL,
	[Address] [varchar](100) NOT NULL,
	[Email] [varchar](100) NOT NULL,
	[Password] [varchar](50) NOT NULL,
	[Image] [varchar](max) NULL,
 CONSTRAINT [User_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[Comment] ON 

INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (13, N'lose', 9, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (14, N'super', 9, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (15, N'fscv', 8, 3)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (16, N'five start test', 8, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (17, N'one start test', 8, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (18, N'fgdfgg', 8, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (19, N'greg', 8, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (20, N'hreh', 8, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (21, N'ferfreger', 8, 1)
SET IDENTITY_INSERT [dbo].[Comment] OFF
SET IDENTITY_INSERT [dbo].[Friends] ON 

INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (16, 9, 8)
SET IDENTITY_INSERT [dbo].[Friends] OFF
SET IDENTITY_INSERT [dbo].[Location] ON 

INSERT [dbo].[Location] ([Id], [Latitude], [Longitude]) VALUES (12, CAST(45.257882 AS Decimal(9, 6)), CAST(19.834457 AS Decimal(9, 6)))
INSERT [dbo].[Location] ([Id], [Latitude], [Longitude]) VALUES (13, CAST(45.248397 AS Decimal(9, 6)), CAST(19.843898 AS Decimal(9, 6)))
INSERT [dbo].[Location] ([Id], [Latitude], [Longitude]) VALUES (14, CAST(45.240873 AS Decimal(9, 6)), CAST(19.812943 AS Decimal(9, 6)))
SET IDENTITY_INSERT [dbo].[Location] OFF
SET IDENTITY_INSERT [dbo].[MenuItem] ON 

INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (22, 1, N'Roll', N'Savory roll with spinach', CAST(15 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (23, 1, N'Sandwich', N'Warm sandwich', CAST(5 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (24, 1, N'Salad', N'Salad with vegetables', CAST(10 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (25, 1, N'Sandwich', N'Warm sandwich', CAST(5 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (26, 1, N'Russian salad', N'Russian salad', CAST(15 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (27, 1, N'Eggs', N'Eggs', CAST(10 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (28, 2, N'Soup', N'Chicken soup', CAST(6 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (29, 2, N'Meat', N'Chicken meat', CAST(7 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (30, 2, N'Spaghetti', N'Spaghetti carbonara', CAST(10 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (31, 2, N'Potato', N'Potato', CAST(7 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (32, 2, N'Spaghetti', N'Spaghetti bolognese', CAST(10 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (33, 2, N'Rice', N'Rice with meet', CAST(10 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (34, 3, N'Ice cream', N'Vanila, chocolate', CAST(2 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (35, 3, N'Cheese cake', N'Cheese cake', CAST(4 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (36, 3, N'Pancakes', N'Pancakes with jam', CAST(6 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (37, 3, N'Cookies', N'Cookies', CAST(3 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (38, 3, N'Pie', N'Pie with cherry', CAST(4 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (39, 3, N'Fruit salad', N'Salad with cream and frits', CAST(5 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (40, 1, N'Eggs', N'Eggs with bacon', CAST(4 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (41, 1, N'Croissant', N'Croissant with cream', CAST(2 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (42, 2, N'Fish', N'Fish with potato', CAST(10 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (43, 2, N'Squid', N'Squid salad', CAST(11 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (44, 3, N'Friut salad', N'Salad with fruits', CAST(4 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (45, 3, N'Banana cake', N'Cake with bananas', CAST(3 AS Decimal(18, 0)), 1)
SET IDENTITY_INSERT [dbo].[MenuItem] OFF
SET IDENTITY_INSERT [dbo].[Rating] ON 

INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (19, 1, 1, 9)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (20, 5, 1, 9)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (21, 2, 3, 8)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (22, 5, 1, 8)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (23, 5, 1, 8)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (24, 0, 1, 8)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (25, 0, 1, 8)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (26, 5, 1, 8)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (27, 5, 1, 8)
SET IDENTITY_INSERT [dbo].[Rating] OFF
SET IDENTITY_INSERT [dbo].[Reservation] ON 

INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (27, CAST(N'2017-09-04T10:30:00.000' AS DateTime), CAST(N'2017-09-04T13:30:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (32, CAST(N'2017-09-08T00:07:00.000' AS DateTime), CAST(N'2017-09-08T03:07:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (33, CAST(N'2017-09-08T00:17:00.000' AS DateTime), CAST(N'2017-09-08T03:17:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (34, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (35, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (36, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (37, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (38, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (39, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (40, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (41, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (42, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (43, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (44, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantId]) VALUES (45, CAST(N'2017-09-08T11:49:00.000' AS DateTime), CAST(N'2017-09-08T14:49:00.000' AS DateTime), 9, 1)
SET IDENTITY_INSERT [dbo].[Reservation] OFF
SET IDENTITY_INSERT [dbo].[Restaurant] ON 

INSERT [dbo].[Restaurant] ([Id], [Name], [Description], [Image], [Phone], [Email], [Webste], [Address], [Location]) VALUES (1, N'Play', N'Description of Play', N'https://www.donesi.com/images/product/75/116275_m.jpg', N'852369741', N'play@gmail.com', N'www.play.com', N'Novosadskog Sajma 2', 12)
INSERT [dbo].[Restaurant] ([Id], [Name], [Description], [Image], [Phone], [Email], [Webste], [Address], [Location]) VALUES (2, N'Kafemat', N'Description of Kafemat restaurant', N'http://www.novisadnocu.com/wp-content/uploads/2015/11/1472018_1431032420446513_824134356_n-620x315-424x306.jpg', N'0215564789', N'kafematrs@gmail.com', N'www.kafemat.rs', N'Negde kod Spensa', 13)
INSERT [dbo].[Restaurant] ([Id], [Name], [Description], [Image], [Phone], [Email], [Webste], [Address], [Location]) VALUES (3, N'Rosetto', N'Description of Rosetto restaurant', N'http://www.rosetto.rs/uploads/images/structure/logo_rosetto.png', N'021114558', N'rosettons@gmail.com', N'www.rosetto.rs', N'Negde', 14)
SET IDENTITY_INSERT [dbo].[Restaurant] OFF
SET IDENTITY_INSERT [dbo].[RestaurantTable] ON 

INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (6, 1, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (7, 2, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (8, 12, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (9, 20, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (10, 7, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (11, 1, 2)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (12, 5, 2)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (13, 18, 2)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (14, 20, 2)
SET IDENTITY_INSERT [dbo].[RestaurantTable] OFF
SET IDENTITY_INSERT [dbo].[TableReservation] ON 

INSERT [dbo].[TableReservation] ([Id], [TableId], [ReservationId]) VALUES (1, 7, 27)
INSERT [dbo].[TableReservation] ([Id], [TableId], [ReservationId]) VALUES (2, 8, 27)
INSERT [dbo].[TableReservation] ([Id], [TableId], [ReservationId]) VALUES (3, 9, 27)
INSERT [dbo].[TableReservation] ([Id], [TableId], [ReservationId]) VALUES (4, 7, 32)
SET IDENTITY_INSERT [dbo].[TableReservation] OFF
SET IDENTITY_INSERT [dbo].[User] ON 

INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (8, N'ei5j68xqTDA:APA91bFG-wIl2-hXDJ6900rZDQ9Lw8TY3fgVHZ4TZIcjZgt-U9jd3a3Gz4BQsrkn8v2qwEcie6ZbgNP45q7J_8D2EQ4dhxew_vmPfgpeH7E7aDHOYcVploeeZfY427g_9xOqAh3gpW40', N'Nenad', N'Rad', N'Test', N'n.rad@gmail.com', N'pitajmamu', NULL)
INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (9, N'ePYeO4AttKo:APA91bHKgcGLSe0IYfjPTu6sosHjmxnnEVZUVxzJGU-eteId8mR91aPZUj6uSiUC1ycjSptQBC7dY8w_BoLSbXtwUcD5B4KMa3e25kmSItuLZ_6nSBVAYfNMT4sNzGOP9h1ymLnjFRHl', N'ghcjcvi', N'i', N'hdbzjdn', N'ivana@gmail.com', N'ivana1234', NULL)
SET IDENTITY_INSERT [dbo].[User] OFF
ALTER TABLE [dbo].[Comment]  WITH CHECK ADD  CONSTRAINT [Comment_User] FOREIGN KEY([User])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[Comment] CHECK CONSTRAINT [Comment_User]
GO
ALTER TABLE [dbo].[Comment]  WITH CHECK ADD  CONSTRAINT [FK_Comment_Restaurant] FOREIGN KEY([Restaurant])
REFERENCES [dbo].[Restaurant] ([Id])
GO
ALTER TABLE [dbo].[Comment] CHECK CONSTRAINT [FK_Comment_Restaurant]
GO
ALTER TABLE [dbo].[Friends]  WITH CHECK ADD  CONSTRAINT [FK_Friends_User] FOREIGN KEY([User])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[Friends] CHECK CONSTRAINT [FK_Friends_User]
GO
ALTER TABLE [dbo].[Friends]  WITH CHECK ADD  CONSTRAINT [FK_Friends_User1] FOREIGN KEY([Friend])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[Friends] CHECK CONSTRAINT [FK_Friends_User1]
GO
ALTER TABLE [dbo].[MenuItem]  WITH CHECK ADD  CONSTRAINT [FK_MenuItem_Restaurant] FOREIGN KEY([Restaurant])
REFERENCES [dbo].[Restaurant] ([Id])
GO
ALTER TABLE [dbo].[MenuItem] CHECK CONSTRAINT [FK_MenuItem_Restaurant]
GO
ALTER TABLE [dbo].[Rating]  WITH CHECK ADD  CONSTRAINT [FK_Rating_Restaurant] FOREIGN KEY([Restaurant])
REFERENCES [dbo].[Restaurant] ([Id])
GO
ALTER TABLE [dbo].[Rating] CHECK CONSTRAINT [FK_Rating_Restaurant]
GO
ALTER TABLE [dbo].[Rating]  WITH CHECK ADD  CONSTRAINT [Rating_User] FOREIGN KEY([User])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[Rating] CHECK CONSTRAINT [Rating_User]
GO
ALTER TABLE [dbo].[Reservation]  WITH CHECK ADD  CONSTRAINT [FK_Reservation_Restaurant] FOREIGN KEY([RestaurantId])
REFERENCES [dbo].[Restaurant] ([Id])
GO
ALTER TABLE [dbo].[Reservation] CHECK CONSTRAINT [FK_Reservation_Restaurant]
GO
ALTER TABLE [dbo].[Reservation]  WITH CHECK ADD  CONSTRAINT [Reservation_User] FOREIGN KEY([User])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[Reservation] CHECK CONSTRAINT [Reservation_User]
GO
ALTER TABLE [dbo].[ReservationFriends]  WITH CHECK ADD  CONSTRAINT [FK_ReservationFriends_Reservation] FOREIGN KEY([ReservationId])
REFERENCES [dbo].[Reservation] ([Id])
GO
ALTER TABLE [dbo].[ReservationFriends] CHECK CONSTRAINT [FK_ReservationFriends_Reservation]
GO
ALTER TABLE [dbo].[ReservationFriends]  WITH CHECK ADD  CONSTRAINT [FK_ReservationFriends_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[ReservationFriends] CHECK CONSTRAINT [FK_ReservationFriends_User]
GO
ALTER TABLE [dbo].[Restaurant]  WITH CHECK ADD  CONSTRAINT [FK_Restaurant_Location] FOREIGN KEY([Location])
REFERENCES [dbo].[Location] ([Id])
GO
ALTER TABLE [dbo].[Restaurant] CHECK CONSTRAINT [FK_Restaurant_Location]
GO
ALTER TABLE [dbo].[RestaurantTable]  WITH CHECK ADD  CONSTRAINT [RestaurantTable_Restaurant] FOREIGN KEY([Restaurant])
REFERENCES [dbo].[Restaurant] ([Id])
GO
ALTER TABLE [dbo].[RestaurantTable] CHECK CONSTRAINT [RestaurantTable_Restaurant]
GO
ALTER TABLE [dbo].[TableReservation]  WITH CHECK ADD  CONSTRAINT [FK_TableReservation_Reservation] FOREIGN KEY([ReservationId])
REFERENCES [dbo].[Reservation] ([Id])
GO
ALTER TABLE [dbo].[TableReservation] CHECK CONSTRAINT [FK_TableReservation_Reservation]
GO
ALTER TABLE [dbo].[TableReservation]  WITH CHECK ADD  CONSTRAINT [FK_TableReservation_RestaurantTable] FOREIGN KEY([TableId])
REFERENCES [dbo].[RestaurantTable] ([Id])
GO
ALTER TABLE [dbo].[TableReservation] CHECK CONSTRAINT [FK_TableReservation_RestaurantTable]
GO
