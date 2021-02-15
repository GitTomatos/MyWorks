-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Янв 31 2021 г., 12:04
-- Версия сервера: 10.3.22-MariaDB
-- Версия PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `weather`
--

-- --------------------------------------------------------

--
-- Структура таблицы `info`
--

CREATE TABLE `info` (
  `city` varchar(255) CHARACTER SET utf8 NOT NULL,
  `country` varchar(255) CHARACTER SET utf8 NOT NULL,
  `timeLocal` varchar(255) CHARACTER SET utf8 NOT NULL,
  `temperature` int(11) NOT NULL,
  `windSpeed` int(11) NOT NULL,
  `pressure` int(11) NOT NULL,
  `refreshedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `info`
--

INSERT INTO `info` (`city`, `country`, `timeLocal`, `temperature`, `windSpeed`, `pressure`, `refreshedAt`) VALUES
('Los Angeles', 'United States of America', '2021-01-30 13:56', 15, 0, 1022, '2021-01-31 00:56:57'),
('Miami', 'United States of America', '2021-01-30 16:58', 22, 22, 1022, '2021-01-31 01:02:06'),
('New York', 'United States of America', '2021-01-30 16:50', 0, 15, 1025, '2021-01-31 00:56:00'),
('Seattle', 'United States of America', '2021-01-30 09:32', 6, 6, 1012, '2021-01-30 20:37:22'),
('Washington', 'United States of America', '2021-01-30 17:02', 3, 7, 1027, '2021-01-31 01:02:18');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `info`
--
ALTER TABLE `info`
  ADD PRIMARY KEY (`city`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
