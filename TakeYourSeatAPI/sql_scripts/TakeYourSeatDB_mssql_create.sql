USE [master]
GO
/****** Object:  Database [TakeYourSeatDB]    Script Date: 8/31/2017 22:02:15 ******/
CREATE DATABASE [TakeYourSeatDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'TakeYourSeatDB_Data', FILENAME = N'c:\dzsqls\TakeYourSeatDB.mdf' , SIZE = 3136KB , MAXSIZE = 15360KB , FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'TakeYourSeatDB_Logs', FILENAME = N'c:\dzsqls\TakeYourSeatDB.ldf' , SIZE = 1024KB , MAXSIZE = 20480KB , FILEGROWTH = 10%)
GO
ALTER DATABASE [TakeYourSeatDB] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TakeYourSeatDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [TakeYourSeatDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [TakeYourSeatDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [TakeYourSeatDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET  ENABLE_BROKER 
GO
ALTER DATABASE [TakeYourSeatDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [TakeYourSeatDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [TakeYourSeatDB] SET  MULTI_USER 
GO
ALTER DATABASE [TakeYourSeatDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [TakeYourSeatDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [TakeYourSeatDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [TakeYourSeatDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [TakeYourSeatDB]
GO
/****** Object:  User [pma2017tim8]    Script Date: 8/31/2017 22:02:18 ******/
CREATE USER [pma2017tim8] FOR LOGIN [pma2017tim8] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [pma2017tim8]
GO
/****** Object:  Schema [pma2017tim8]    Script Date: 8/31/2017 22:02:19 ******/
CREATE SCHEMA [pma2017tim8]
GO
/****** Object:  Table [dbo].[Comment]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[Friends]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[Location]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[MenuItem]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[Rating]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[Reservation]    Script Date: 8/31/2017 22:02:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reservation](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[User] [int] NOT NULL,
 CONSTRAINT [Reservation_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Restaurant]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[RestaurantTable]    Script Date: 8/31/2017 22:02:19 ******/
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
/****** Object:  Table [dbo].[TableReservation]    Script Date: 8/31/2017 22:02:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TableReservation](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[TableId] [int] NOT NULL,
	[ReservationId] [int] NOT NULL,
 CONSTRAINT [TableReservation_pk] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[User]    Script Date: 8/31/2017 22:02:19 ******/
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

INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (1, N'Very good restaurant.', 1, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (2, N'I do not like this place.', 2, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (3, N'I like it.', 3, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (4, N'Very good and nice restaurant.', 1, 2)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (5, N'I do not like this place and food they offer.', 2, 2)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (6, N'I like it so much.', 3, 2)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (7, N'Very good and nice restaurant.', 1, 3)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (8, N'They offer good food.', 2, 3)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (9, N'I like it so much.', 3, 3)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (10, N'Nice ambient.', 4, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (11, N'Good service', 5, 1)
INSERT [dbo].[Comment] ([Id], [Description], [User], [Restaurant]) VALUES (12, N'Delicious food.', 6, 1)
SET IDENTITY_INSERT [dbo].[Comment] OFF
SET IDENTITY_INSERT [dbo].[Friends] ON 

INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (1, 1, 2)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (2, 1, 3)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (3, 1, 4)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (4, 1, 5)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (5, 2, 4)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (6, 2, 5)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (7, 3, 6)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (8, 4, 3)
INSERT [dbo].[Friends] ([Id], [User], [Friend]) VALUES (9, 4, 5)
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
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (31, 2, N'Potatoes', N'Potatoes', CAST(7 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (32, 2, N'Spaghetti', N'Spaghetti bolognese', CAST(10 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (33, 2, N'Rice', N'Rice with meat', CAST(10 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (34, 3, N'Ice cream', N'Vanilla, chocolate', CAST(2 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (35, 3, N'Cheese cake', N'Cheese cake', CAST(4 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (36, 3, N'Pancakes', N'Pancakes with jam', CAST(6 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (37, 3, N'Cookies', N'Cookies', CAST(3 AS Decimal(18, 0)), 2)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (38, 3, N'Pie', N'Pie with cherry', CAST(4 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (39, 3, N'Fruit salad', N'Salad with cream and fruits', CAST(5 AS Decimal(18, 0)), 3)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (40, 1, N'Eggs', N'Eggs with bacon', CAST(4 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (41, 1, N'Croissant', N'Croissant with cream', CAST(2 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (42, 2, N'Fish', N'Fish with potatoes', CAST(10 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (43, 2, N'Squid', N'Squid salad', CAST(11 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (44, 3, N'Fruit salad', N'Salad with fruits', CAST(4 AS Decimal(18, 0)), 1)
INSERT [dbo].[MenuItem] ([Id], [Category], [Name], [Description], [Price], [Restaurant]) VALUES (45, 3, N'Banana cake', N'Cake with bananas', CAST(3 AS Decimal(18, 0)), 1)
SET IDENTITY_INSERT [dbo].[MenuItem] OFF
SET IDENTITY_INSERT [dbo].[Rating] ON 

INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (1, 4, 1, 1)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (2, 5, 1, 2)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (3, 3, 1, 3)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (4, 5, 1, 4)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (5, 2, 1, 5)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (6, 5, 1, 6)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (7, 5, 2, 1)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (8, 4, 2, 2)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (9, 5, 2, 3)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (10, 4, 2, 4)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (11, 5, 2, 5)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (12, 3, 2, 6)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (13, 3, 3, 1)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (14, 4, 3, 2)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (15, 5, 3, 3)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (16, 2, 3, 4)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (17, 5, 3, 5)
INSERT [dbo].[Rating] ([Id], [Rank], [Restaurant], [User]) VALUES (18, 5, 3, 6)
SET IDENTITY_INSERT [dbo].[Rating] OFF
SET IDENTITY_INSERT [dbo].[Reservation] ON 

INSERT [dbo].[Reservation] ([Id], [StartDate], [EndDate], [User], [RestaurantTable]) VALUES (1, CAST(N'2017-06-18T20:00:00.000' AS DateTime), CAST(N'2017-06-18T23:00:00.000' AS DateTime), 1, 1)
SET IDENTITY_INSERT [dbo].[Reservation] OFF
SET IDENTITY_INSERT [dbo].[Restaurant] ON 

INSERT [dbo].[Restaurant] ([Id], [Name], [Description], [Image], [Phone], [Email], [Webste], [Address], [Location]) VALUES (1, N'Play', N'Description of Play', N'https://www.donesi.com/images/product/75/116275_m.jpg', N'852369741', N'play@gmail.com', N'www.play.com', N'Novosadskog Sajma 2', 12)
INSERT [dbo].[Restaurant] ([Id], [Name], [Description], [Image], [Phone], [Email], [Webste], [Address], [Location]) VALUES (2, N'Kafemat', N'Description of Kafemat restaurant', N'http://www.novisadnocu.com/wp-content/uploads/2015/11/1472018_1431032420446513_824134356_n-620x315-424x306.jpg', N'0215564789', N'kafematrs@gmail.com', N'www.kafemat.rs', N'Negde kod Spensa', 13)
INSERT [dbo].[Restaurant] ([Id], [Name], [Description], [Image], [Phone], [Email], [Webste], [Address], [Location]) VALUES (3, N'Rosetto', N'Description of Rosetto restaurant', N'http://www.rosetto.rs/uploads/images/structure/logo_rosetto.png', N'021114558', N'rosettons@gmail.com', N'www.rosetto.rs', N'Negde', 14)
SET IDENTITY_INSERT [dbo].[Restaurant] OFF
SET IDENTITY_INSERT [dbo].[RestaurantTable] ON 

INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (1, 12, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (2, 4, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (3, 20, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (4, 5, 1)
INSERT [dbo].[RestaurantTable] ([Id], [Number], [Restaurant]) VALUES (5, 7, 1)
SET IDENTITY_INSERT [dbo].[RestaurantTable] OFF
SET IDENTITY_INSERT [dbo].[User] ON 

INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (1, NULL, N'Nenad', N'Rad', N'Kisacka 51', N'nenad.rad933@gmail.com', N'dev_nenad', N'url')
INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (2, NULL, N'Ivana', N'Tesanovic', N'Stevana Sremca 2', N'ivana@gmail.com', N'aaaaaa', NULL)
INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (3, NULL, N'Anica', N'Sutic', N'Cirpanova ?', N'anica@gmail.com', N'dev_anica', NULL)
INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (4, NULL, N'Test name', N'test last name', N'Test', N'test@gmail.com', N'testtest', NULL)
INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (5, NULL, N'Gorica', N'Sutic', N'Novi Sad', N'gorica@gmail.com', N'gorica', NULL)
INSERT [dbo].[User] ([Id], [Token], [Name], [LastName], [Address], [Email], [Password], [Image]) VALUES (6, NULL, N'stefan', N'stanojevic', N'bp', N'stanoje92@hotmail.com', N'762660', NULL)
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
ALTER TABLE [dbo].[Reservation]  WITH CHECK ADD  CONSTRAINT [Reservation_User] FOREIGN KEY([User])
REFERENCES [dbo].[User] ([Id])
GO
ALTER TABLE [dbo].[Reservation] CHECK CONSTRAINT [Reservation_User]
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
USE [master]
GO
ALTER TABLE [dbo].[TableReservation]  WITH CHECK ADD  CONSTRAINT [FK_TableReservation_Reservation] FOREIGN KEY([Reservation])
REFERENCES [dbo].[Reservation] ([Id])
GO
ALTER TABLE [dbo].[TableReservation] CHECK CONSTRAINT [FK_TableReservation_Reservation]
GO
ALTER TABLE [dbo].[TableReservation]  WITH CHECK ADD  CONSTRAINT [FK_TableReservation_Table] FOREIGN KEY([Table])
REFERENCES [dbo].[Table] ([Id])
GO
ALTER TABLE [dbo].[TableReservation] CHECK CONSTRAINT [FK_TableReservation_Table]
GO
ALTER DATABASE [TakeYourSeatDB] SET  READ_WRITE 
GO
