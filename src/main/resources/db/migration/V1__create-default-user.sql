-- townsq definition
CREATE DATABASE  IF NOT EXISTS `townsq` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- townsq.users definition
CREATE TABLE  IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL DEFAULT 'DEFAULT',
  `created_at` datetime(0) DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime(0) DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- townsq.users.defaultUser definition
INSERT INTO users (username, password, role)
VALUES ('townsq_super', '$2a$10$8zmRZ9ZEXrdiO8ony7jNXuIepasoH.AykgBSEPiOMKCbOwTcOllfe', 'ADMIN');

-- !!! REMOVER
INSERT INTO users (username, password, role)
VALUES ('samuel', '$2a$10$U24YKRpH3Ye1tpZC94C9K.PCQGWuy5KTcH.LUsadnFrUZbEIL0fTq', 'DEFAULT');
INSERT INTO users (username, password, role)
VALUES ('hellen', '$2a$10$BscwXPSvX4Winm2QrV5UXu9AAR4RtEjboAUrwjp.ANmThzLIjgo/.', 'ACCOUNT_MANAGER');