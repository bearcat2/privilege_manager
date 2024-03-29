/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.24-log : Database - db_privilege
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_privilege` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_privilege`;

/*Table structure for table `sys_privilege` */

DROP TABLE IF EXISTS `sys_privilege`;

CREATE TABLE `sys_privilege` (
  `sp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `sp_name` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `sp_uri` varchar(128) DEFAULT NULL COMMENT '资源uri',
  `sp_type` int(11) DEFAULT NULL COMMENT '资源类型(1:模块;2:菜单;3:按钮)',
  `sp_parent_id` int(11) DEFAULT NULL COMMENT '父权限id',
  `sp_orderd` int(11) DEFAULT NULL COMMENT '位置排序',
  `sp_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `sp_update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`sp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `sr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id,自增',
  `sr_name` varchar(32) DEFAULT NULL COMMENT '角色名',
  `sr_description` varchar(64) DEFAULT NULL COMMENT '角色描述',
  `sr_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `sr_update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`sr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role_privilege` */

DROP TABLE IF EXISTS `sys_role_privilege`;

CREATE TABLE `sys_role_privilege` (
  `srp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限表主键id,自增',
  `srp_role_id` int(11) DEFAULT NULL COMMENT '角色表主键id',
  `srp_privilege_id` int(11) DEFAULT NULL COMMENT '权限表主键id',
  PRIMARY KEY (`srp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `su_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id,自增',
  `su_login_name` varchar(32) NOT NULL COMMENT '登录名',
  `su_real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `su_password` varchar(32) NOT NULL COMMENT '密码',
  `su_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `su_update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`su_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `sur_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色表主键,自增',
  `sur_user_id` int(11) DEFAULT NULL COMMENT '用户表主键',
  `sur_role_id` int(11) DEFAULT NULL COMMENT '角色表主键',
  PRIMARY KEY (`sur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
