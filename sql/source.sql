/*
 Navicat MySQL Data Transfer

 Source Server         : 101.200.204.6
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : 101.200.204.6
 Source Database       : source

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : utf-8

 Date: 08/14/2017 12:00:26 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `simplename` varchar(45) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_dept`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('1', '1', '0', '总公司', '总公司', null, null), ('27', '1', '1', '开发部', '开发部', '', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` longtext,
  `description` longtext,
  `ip` varchar(100) DEFAULT NULL,
  `module` varchar(200) DEFAULT NULL,
  `response_time` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_log`
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES ('105', '2017-08-14 11:26:35', '0', '2017-08-14 11:26:35', '[类名]:com.source.app.controller.system.DeptController,[方法]:list,[参数]:null,[IP]:0:0:0:0:0:0:0:1', '获取部门列表', '0:0:0:0:0:0:0:1', '部门模块', null, 'admin'), ('106', '2017-08-14 11:26:35', '0', '2017-08-14 11:26:35', '[类名]:com.source.app.controller.system.DeptController,[方法]:list,[参数]:null,[IP]:0:0:0:0:0:0:0:1', '获取部门列表', '0:0:0:0:0:0:0:1', '部门模块', null, 'admin'), ('107', '2017-08-14 11:57:47', '0', '2017-08-14 11:57:47', '[类名]:com.source.app.controller.system.UserController,[方法]:getPages,[参数]:name=&phone=&sex=&page=1&rows=20&_=1502683067368&,[IP]:0:0:0:0:0:0:0:1', '获取用户列表', '0:0:0:0:0:0:0:1', '用户模块', null, 'admin'), ('108', '2017-08-14 11:57:47', '0', '2017-08-14 11:57:47', '[类名]:com.source.app.controller.system.UserController,[方法]:getPages,[参数]:name=&phone=&sex=&page=1&rows=20&_=1502683067368&,[IP]:0:0:0:0:0:0:0:1', '获取用户列表', '0:0:0:0:0:0:0:1', '用户模块', null, 'admin'), ('109', '2017-08-14 11:58:08', '0', '2017-08-14 11:58:08', '[类名]:com.source.app.controller.system.RoleController,[方法]:list,[参数]:order=desc&offset=0&limit=14&,[IP]:0:0:0:0:0:0:0:1', '获取角色列表', '0:0:0:0:0:0:0:1', '角色模块', null, 'admin'), ('110', '2017-08-14 11:58:08', '0', '2017-08-14 11:58:08', '[类名]:com.source.app.controller.system.RoleController,[方法]:list,[参数]:order=desc&offset=0&limit=14&,[IP]:0:0:0:0:0:0:0:1', '获取角色列表', '0:0:0:0:0:0:0:1', '角色模块', null, 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(65) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', 'system', '0', '[0],', '系统管理', 'fa-user', '/', '2', '1', '1', '', '1', '1'), ('2', 'user', 'system', '[0],[system],', '用户管理', '', '/user/list', '1', '2', '1', '', '1', '0'), ('3', 'user_add', 'user', '[0],[system],[user],', '添加用户', '', '/user/add', '1', '3', '0', '', '1', '0'), ('4', 'user_edit', 'user', '[0],[system],[user],', '修改用户', '', '/user/edit', '2', '3', '0', '', '1', '0'), ('6', 'user_delete', 'user', '[0],[system],[user],', '删除用户', '', '/user/delete', '3', '3', '0', null, '1', null), ('7', 'user_changepw', 'user', '[0],[system],[user],', '重置密码', '', '/user/changepw', '4', '3', '0', null, '1', null), ('8', 'user_freeze_account', 'user', '[0],[system],[user],', '用户冻结', '', '/user/freezeAccount', '5', '3', '0', null, '1', null), ('9', 'user_unfreeze', 'user', '[0],[system],[user],', '用户解除冻结', '', '/user/unfreeze', '6', '3', '0', null, '1', null), ('10', 'user_roleAssign', 'user', '[0],[system],[user],', '角色分配', '', '/user/roleAssign', '7', '3', '0', null, '1', null), ('11', 'menu', 'system', '[0],[system],', '菜单管理', '', '/menu/list', '2', '2', '1', null, '1', null), ('12', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', '', '/menu/add', '1', '3', '0', null, '1', null), ('14', 'menu_delete', 'menu', '[0],[system],[menu],', '删除菜单', '', '/menu/delete', '3', '3', '0', null, '1', null), ('15', 'role', 'system', '[0],[system],', '角色管理', '', '/role/list', '3', '2', '1', null, '1', null), ('16', 'role_add', 'role', '[0],[system],[role],', '添加角色', '', '/role/add', '1', '3', '0', null, '1', null), ('17', 'role_edit', 'role', '[0],[system],[role],', '编辑角色', '', '/role/role_edit', '2', '3', '0', null, '1', null), ('18', 'role_delete', 'role', '[0],[system],[role],', '删除角色', '', '/role/delete', '3', '3', '0', null, '1', null), ('19', 'role_assign', 'role', '[0],[system],[role],', '权限配置', '', '/role/assign', '4', '3', '0', null, '1', null), ('20', 'dept', 'system', '[0],[system],', '部门管理', '', '/dept/list', '4', '2', '1', null, '1', null), ('21', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', '', '/dept/add', '1', '3', '0', null, '1', null), ('22', 'dept_edit', 'dept', '[0],[system],[dept],', '编辑部门', '', '/dept/edit', '2', '3', '0', null, '1', null), ('23', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', '', '/dept/delete', '3', '3', '0', null, '1', null), ('24', 'druid', 'system', '[0],[system],', '监控系统', '', '/druid/sql.html', '5', '2', '1', null, '1', null), ('28', 'menu_edit', 'menu', '[0],[system],[menu],', '编辑菜单', '', '/menu/edit', '2', '3', '0', null, '1', null), ('29', 'train', '0', null, '火车票管理', 'fa-car', '#', '1', '1', '1', null, '1', null), ('31', 'train_list', 'train', 'null[train],', '查询列表', '', '/train/index', '2', '2', '1', null, '1', null), ('32', 'system_log', 'system', '[0],[system],', '日志管理', '', '/admin/log/list', '6', '2', '1', null, '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuid` int(11) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_relation`
-- ----------------------------
BEGIN;
INSERT INTO `sys_relation` VALUES ('378', '1', '3'), ('379', '2', '3'), ('380', '3', '3'), ('381', '4', '3'), ('382', '6', '3'), ('383', '7', '3'), ('384', '8', '3'), ('385', '9', '3'), ('386', '10', '3'), ('387', '11', '3'), ('388', '12', '3'), ('390', '14', '3'), ('391', '15', '3'), ('392', '16', '3'), ('393', '17', '3'), ('394', '18', '3'), ('395', '20', '3'), ('396', '21', '3'), ('397', '22', '3'), ('398', '23', '3'), ('399', '24', '3'), ('476', '1', '1'), ('477', '2', '1'), ('478', '3', '1'), ('479', '4', '1'), ('480', '6', '1'), ('481', '7', '1'), ('482', '8', '1'), ('483', '9', '1'), ('484', '10', '1'), ('485', '11', '1'), ('486', '12', '1'), ('487', '14', '1'), ('488', '28', '1'), ('489', '15', '1'), ('490', '16', '1'), ('491', '17', '1'), ('492', '18', '1'), ('493', '19', '1'), ('494', '20', '1'), ('495', '21', '1'), ('496', '22', '1'), ('497', '23', '1'), ('498', '24', '1'), ('499', '32', '1'), ('500', '29', '1'), ('501', '31', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '1', '0', '超级管理员', '1', 'administrator', '1'), ('3', null, '1', '普通管理员', '1', 'admin', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `salt` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `roleid` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '2017_08_11_d02fcb9edb764a5bbc70c903ef7f602f.jpg', 'admin', '4779e4a9903dfb08f9f877791c516a73', 'admin', '张三', '2017-08-08 00:00:00', '1', 'admin@qq.com', '15666668888', '1', '1', '1', null, '2017-08-09 09:11:33', '2017-08-09 09:11:37'), ('67', null, 'test', 'e49bcb2edf7012634b932e5712981baf', 'nrce7', '111', '2017-08-10 00:00:00', '2', '', '', '3', '1', '1', null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
