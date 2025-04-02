/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : localhost:3306
 Source Schema         : rbac-api

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 27/09/2023 13:43:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '岗位id',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别：1->男,  2->女',
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '帐号启用状态：1->启用,2->禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, 9, 5, 'admin', '/6xCnHpeuTZhlIak6GpfoQ==', '云州泇滴', 1, 'http://localhost:2002/api/dev/static/51fb5012-7d16-4697-8881-beae93aa0d09.jpg', '123456789@qq.com', '13789098909', '后台研发', '2023-07-12 12:26:14', 1);
INSERT INTO `sys_admin` VALUES (2, 1, 4, '卡拉', '0bcm2dxlMUWeNq4vRIdT/A==', 'kala', 1, NULL, '23456789@qq.com', '13768676767', '前端开发', '2023-09-18 19:31:18', 1);
INSERT INTO `sys_admin` VALUES (3, 1, 5, 'xiaofang', 'Tfr5HCD0h25nlgu6t+mK3A==', '小芳', 2, 'http://localhost:2002/api/dev/static/a3b1abbd-4e9c-4bdf-92bc-e19ac00a1430.webp', '1212121212@qq.com', '13789890987', '后端开发', '2023-09-18 20:29:49', 1);
INSERT INTO `sys_admin` VALUES (5, 1, 5, 'xiaofang12', '67NeY2vruSCMIZbJ+lRwYg==', '小芳12', 2, NULL, '1212122212@qq.com', '13783891987', '后端开发', '2023-09-26 08:42:40', 2);

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`  (
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `role_id` int(11) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员和角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role` VALUES (1, 1);
INSERT INTO `sys_admin_role` VALUES (2, 2);
INSERT INTO `sys_admin_role` VALUES (3, 2);
INSERT INTO `sys_admin_role` VALUES (5, 2);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NOT NULL COMMENT '父id',
  `dept_type` int(11) NOT NULL COMMENT '部门类型（1->公司, 2->中心，3->部门）',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '部门名称',
  `dept_status` int(11) NOT NULL DEFAULT 1 COMMENT '部门状态（1->正常 2->停用）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dept_name`(`dept_name`) USING BTREE,
  INDEX `dept_status`(`dept_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, 1, '深圳架构研发中心', 1, '2023-07-11 16:43:31');
INSERT INTO `sys_dept` VALUES (2, 1, 2, '罗湖区研发中心', 1, '2023-07-11 16:44:12');
INSERT INTO `sys_dept` VALUES (3, 2, 3, '架构设计部门', 1, '2023-07-11 16:47:46');
INSERT INTO `sys_dept` VALUES (4, 2, 3, '前端研发部门', 1, '2023-07-11 16:48:03');
INSERT INTO `sys_dept` VALUES (5, 2, 3, '后端研发部门', 1, '2023-07-11 16:48:12');
INSERT INTO `sys_dept` VALUES (6, 2, 3, '系统测试部门', 1, '2023-07-11 16:48:19');
INSERT INTO `sys_dept` VALUES (7, 2, 3, '视觉交互部门', 1, '2023-07-11 16:48:26');
INSERT INTO `sys_dept` VALUES (8, 2, 3, '产品体验部门', 1, '2023-07-11 16:48:35');

-- ----------------------------
-- Table structure for sys_login_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_info`;
CREATE TABLE `sys_login_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '用户账号',
  `ip_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作系统',
  `login_status` int(11) NULL DEFAULT NULL COMMENT '登录状态（1-成功 2-失败）',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `login_status`(`login_status`) USING BTREE,
  INDEX `login_time`(`login_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '登录日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_info
-- ----------------------------
INSERT INTO `sys_login_info` VALUES (1, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 2, '您输入的密码不正确', '2023-09-27 11:32:23');
INSERT INTO `sys_login_info` VALUES (2, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:32:27');
INSERT INTO `sys_login_info` VALUES (3, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:34:51');
INSERT INTO `sys_login_info` VALUES (4, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:36:09');
INSERT INTO `sys_login_info` VALUES (5, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:37:43');
INSERT INTO `sys_login_info` VALUES (6, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:41:06');
INSERT INTO `sys_login_info` VALUES (7, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:41:39');
INSERT INTO `sys_login_info` VALUES (8, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:44:18');
INSERT INTO `sys_login_info` VALUES (9, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 11:49:47');
INSERT INTO `sys_login_info` VALUES (10, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 2, '您输入的密码不正确', '2023-09-27 12:53:37');
INSERT INTO `sys_login_info` VALUES (11, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'WINDOWS_10', 1, '登录成功', '2023-09-27 12:53:40');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级菜单id',
  `menu_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口权限值',
  `menu_type` int(11) NULL DEFAULT NULL COMMENT '菜单类型：1->目录；2->菜单；3->按钮（接口绑定权限）',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `menu_status` int(11) NULL DEFAULT 2 COMMENT '启用状态；1->禁用；2->启用',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `menu_name`(`menu_name`) USING BTREE,
  INDEX `menu_status`(`menu_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (4, 0, '系统管理', 'Setting', '', 1, '', 2, 2, '2022-09-04 13:57:39');
INSERT INTO `sys_menu` VALUES (6, 4, '用户信息', 'Avatar', '', 2, 'base/admin', 2, 1, '2022-09-04 13:59:39');
INSERT INTO `sys_menu` VALUES (7, 4, '角色信息', 'Operation', '', 2, 'base/role', 2, 2, '2022-09-04 14:00:12');
INSERT INTO `sys_menu` VALUES (8, 4, '菜单信息', 'Menu', '', 2, 'base/menu', 2, 3, '2022-09-04 14:00:17');
INSERT INTO `sys_menu` VALUES (9, 4, '部门信息', 'Grid', '', 2, 'base/dept', 2, 4, '2022-09-04 14:01:58');
INSERT INTO `sys_menu` VALUES (10, 4, '岗位信息', 'HelpFilled', '', 2, 'base/post', 2, 5, '2022-09-04 14:02:06');
INSERT INTO `sys_menu` VALUES (16, 6, '新增用户', '', 'base:admin:add', 3, '', 2, 1, '2022-09-04 18:32:55');
INSERT INTO `sys_menu` VALUES (17, 6, '修改用户', '', 'base:admin:edit', 3, '', 2, 2, '2022-09-04 18:33:29');
INSERT INTO `sys_menu` VALUES (18, 6, '删除用户', '', 'base:admin:delete', 3, '', 2, 3, '2022-09-04 18:33:51');
INSERT INTO `sys_menu` VALUES (21, 7, '新增角色', '', 'base:role:add', 3, '', 2, 1, '2022-09-04 18:44:47');
INSERT INTO `sys_menu` VALUES (22, 7, '修改角色', '', 'base:role:edit', 3, '', 2, 2, '2022-09-04 18:45:10');
INSERT INTO `sys_menu` VALUES (23, 7, '删除角色', '', 'base:role:delete', 3, '', 2, 3, '2022-09-04 18:45:46');
INSERT INTO `sys_menu` VALUES (24, 7, '分配权限', '', 'base:role:assign', 3, '', 2, 4, '2022-09-04 18:46:20');
INSERT INTO `sys_menu` VALUES (26, 8, '新增菜单', '', 'base:menu:add', 3, '', 2, 1, '2022-09-04 18:49:51');
INSERT INTO `sys_menu` VALUES (27, 8, '修改菜单', '', 'base:menu:edit', 3, '', 2, 2, '2022-09-04 18:50:24');
INSERT INTO `sys_menu` VALUES (28, 8, '删除菜单', '', 'base:menu:delete', 3, '', 2, 3, '2022-09-04 18:50:53');
INSERT INTO `sys_menu` VALUES (29, 9, '新增部门', '', 'base:dept:add', 3, '', 2, 1, '2022-09-04 18:52:16');
INSERT INTO `sys_menu` VALUES (30, 9, '修改部门', '', 'base:dept:edit', 3, '', 2, 2, '2022-09-04 18:52:37');
INSERT INTO `sys_menu` VALUES (31, 9, '删除部门', '', 'base:dept:delete', 3, '', 2, 3, '2022-09-04 18:52:50');
INSERT INTO `sys_menu` VALUES (32, 10, '新增岗位', '', 'base:post:add', 3, '', 2, 1, '2022-09-04 18:53:28');
INSERT INTO `sys_menu` VALUES (33, 10, '修改岗位', '', 'base:post:edit', 3, '', 2, 2, '2022-09-04 18:53:48');
INSERT INTO `sys_menu` VALUES (34, 10, '删除岗位', '', 'base:post:delete', 3, '', 2, 3, '2022-09-04 18:54:00');
INSERT INTO `sys_menu` VALUES (44, 0, '日志管理', 'Document', '', 1, '', 2, 3, '2022-09-05 11:06:57');
INSERT INTO `sys_menu` VALUES (45, 44, '操作日志', 'Message', '', 2, 'log/operator', 2, 1, '2022-09-05 11:10:54');
INSERT INTO `sys_menu` VALUES (46, 44, '登录日志', 'MessageBox', '', 2, 'log/loginLog', 2, 2, '2022-09-05 11:11:31');
INSERT INTO `sys_menu` VALUES (47, 45, '清空操作日志', '', 'log:operator:clean', 3, '', 2, 1, '2022-09-05 11:12:36');
INSERT INTO `sys_menu` VALUES (49, 46, '清空登录日志', '', 'log:loginLog:clean', 3, '', 2, 1, '2022-09-05 11:16:01');
INSERT INTO `sys_menu` VALUES (60, 6, '重置密码', NULL, 'base:admin:reset', 3, NULL, 2, 6, '2022-12-01 16:33:34');
INSERT INTO `sys_menu` VALUES (62, 46, '删除登录日志', '', 'log:loginLog:delete', 3, '', 2, 4, '2022-12-02 15:41:56');
INSERT INTO `sys_menu` VALUES (73, 45, '删除操作日志', '', 'log:operator:delete', 3, '', 2, 3, '2023-06-02 10:09:38');

-- ----------------------------
-- Table structure for sys_operator_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_log`;
CREATE TABLE `sys_operator_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(11) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求方式',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作人员',
  `operator_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求URL',
  `operator_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '主机地址',
  `operator_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作地点',
  `operator_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '返回参数',
  `operator_status` int(11) NULL DEFAULT 0 COMMENT '操作状态（0->成功 1->失败）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '错误消息',
  `operator_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operator_name`(`operator_name`) USING BTREE,
  INDEX `operator_status`(`operator_status`) USING BTREE,
  INDEX `operator_time`(`operator_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operator_log
-- ----------------------------
INSERT INTO `sys_operator_log` VALUES (5, '批量删除操作日志', 3, 'com.rbac.project.controller.SysOperatorLogController.deleteBatchSysOperatorLog()', 'DELETE', 'admin', '/api/dev/sysOperatorLog/delete/2%2C3%2C4', '192.168.0.103', '内网IP', '{ids=2,3,4}', 'JsonResult(code=200, message=操作成功, data=true)', 0, '', '2023-09-26 15:57:31');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位名称',
  `post_status` int(11) NOT NULL DEFAULT 1 COMMENT '状态（1->正常 2->停用）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_name`(`post_name`) USING BTREE,
  INDEX `post_status`(`post_status`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'EEE', '组员', 1, '2023-07-10 20:56:04', '组员');
INSERT INTO `sys_post` VALUES (6, 'DDD', '组长', 1, '2023-07-10 21:22:21', '部门组长');
INSERT INTO `sys_post` VALUES (7, 'CCC', '主管', 1, '2023-07-10 21:22:45', '主管');
INSERT INTO `sys_post` VALUES (8, 'BBB', '经理', 1, '2023-07-10 21:23:09', '主管部分部门');
INSERT INTO `sys_post` VALUES (9, 'AAA', '总监', 1, '2023-07-10 21:23:32', '管各个部门');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '启用状态：1->启用；2->禁用',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`role_name`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '最大权限', '2023-07-12 08:45:37');
INSERT INTO `sys_role` VALUES (2, '管理员', 'user', 1, '管理员权限', '2023-07-12 08:46:09');
INSERT INTO `sys_role` VALUES (3, '普通管理员', 'common', 1, '普通管理员', '2023-09-18 09:50:41');
INSERT INTO `sys_role` VALUES (4, '运营管理员', 'operate', 1, '运营管理员', '2023-09-18 09:51:42');
INSERT INTO `sys_role` VALUES (5, '财务管理员', 'finance', 1, '财务管理员', '2023-09-18 09:52:23');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 60);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 29);
INSERT INTO `sys_role_menu` VALUES (1, 30);
INSERT INTO `sys_role_menu` VALUES (1, 31);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 32);
INSERT INTO `sys_role_menu` VALUES (1, 33);
INSERT INTO `sys_role_menu` VALUES (1, 34);
INSERT INTO `sys_role_menu` VALUES (1, 44);
INSERT INTO `sys_role_menu` VALUES (1, 45);
INSERT INTO `sys_role_menu` VALUES (1, 47);
INSERT INTO `sys_role_menu` VALUES (1, 73);
INSERT INTO `sys_role_menu` VALUES (1, 46);
INSERT INTO `sys_role_menu` VALUES (1, 49);
INSERT INTO `sys_role_menu` VALUES (1, 62);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 29);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 47);
INSERT INTO `sys_role_menu` VALUES (2, 49);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 44);
INSERT INTO `sys_role_menu` VALUES (2, 45);
INSERT INTO `sys_role_menu` VALUES (2, 46);
INSERT INTO `sys_role_menu` VALUES (5, 49);

SET FOREIGN_KEY_CHECKS = 1;
