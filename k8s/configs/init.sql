CREATE SCHEMA IF NOT EXISTS `dog_walking` DEFAULT CHARACTER SET utf8mb4;

create user root@'%' identified by 'dogwalking17';
GRANT ALL ON dog_walking.* TO 'root'@'%';
FLUSH PRIVILEGES;