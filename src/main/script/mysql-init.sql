CREATE DATABASE IF NOT EXISTS env_trac CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'OV_OGP'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'OV_OGP'@'%';
FLUSH PRIVILEGES;
CREATE TABLE IF NOT EXISTS `ov_ref` (
                                        `UserName` varchar(255) COLLATE utf8_bin NOT NULL,
                                        `password` varchar(255) COLLATE utf8_bin NOT NULL,
                                        `URL` varchar(255) COLLATE utf8_bin NOT NULL,
                                        `DRIVER` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                        `ACTIVITIE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                        `ENV` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TRIGGER new_rows_ticket AFTER INSERT ON ticket FOR EACH ROW INSERT INTO new_ticket_bat (id,instance_trac,priority,status) VALUES (NEW.id, "T24",NEW.priority , "NOTOK");

CREATE TABLE IF NOT EXISTS `new_ticket_bat` (
                                                `id` varchar(255) COLLATE utf8_bin NOT NULL,
                                                `instance_trac` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                                `priority` varchar(255) COLLATE utf8_bin NOT NULL,
                                                `status` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `ov_ref` (`UserName`, `password`, `URL`, `DRIVER`, `ACTIVITIE`, `ENV`) VALUES
('OV_OGP', 'password', 'jdbc:mysql://172.28.201.74:3306/tracanomaliet24?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC', 'com.mysql.jdbc.Driver', '1', 'tracanomaliet24'),
('OV_OGP', 'password', 'jdbc:mysql://172.28.201.74:3306/trac_bfi_titre?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC', 'com.mysql.jdbc.Driver', '1', 'trac BFI'),
('OV_OGP', 'password', 'jdbc:mysql://172.28.201.74:3306/ovtools?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC', 'com.mysql.jdbc.Driver', '1', 'niveauprojet'),
('OV_OGP', 'password', 'jdbc:mysql://172.28.201.74:3306/tracmxp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC', 'com.mysql.jdbc.Driver', '1', 'MXP'),
('OV_OGP', 'password', 'jdbc:mysql://172.28.201.74:3306/trachraccess?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC', 'com.mysql.jdbc.Driver', '1', 'HR_ACCESS'),
('OV', 'OV1', 'jdbc:oracle:thin:@172.28.70.156:1521:XE', 'oracle.jdbc.driver.OracleDriver', '1', 'ORACLEBAT');