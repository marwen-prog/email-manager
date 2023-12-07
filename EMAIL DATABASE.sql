create  database mysql_database;
CREATE TABLE `login` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `mysql_database`.`login` (`username`, `password`) VALUES ("marwen", "jerbiano");USE demo;

delete from users where id= '2' ;
delete from login where username='mohamed' and password='irlandi';
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
select*from users;
select*from login;
