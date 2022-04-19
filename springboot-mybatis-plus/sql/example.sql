/*
 Navicat Premium Data Transfer

 Source Server         : ubuntu
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.41.128:3306
 Source Schema         : example

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 11/04/2022 22:19:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for parent_list
-- ----------------------------
DROP TABLE IF EXISTS `parent_list`;
CREATE TABLE `parent_list`  (
  `p_id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NULL DEFAULT NULL COMMENT '父id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parent_list
-- ----------------------------
INSERT INTO `parent_list` VALUES (1, 0, 'aa');
INSERT INTO `parent_list` VALUES (2, 1, 'bb');
INSERT INTO `parent_list` VALUES (3, 1, 'cc');
INSERT INTO `parent_list` VALUES (4, 2, 'dd');
INSERT INTO `parent_list` VALUES (5, NULL, '565656');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `created_date` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `created_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `updated_date` datetime NULL DEFAULT NULL COMMENT '修改日期',
  `updated_by` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', 'zhangsan', '123456', '2022-04-11 22:01:54', 1, NULL, NULL);
INSERT INTO `user` VALUES (2, '李四', 'lisi', '123456', '2022-04-11 22:02:13', 1, NULL, NULL);
INSERT INTO `user` VALUES (3, '王五', 'wangwu', '123456', '2022-04-11 22:02:33', 1, NULL, NULL);
INSERT INTO `user` VALUES (4, 'qq', 'qq', '123456', '2022-04-11 22:02:54', 1, NULL, NULL);
INSERT INTO `user` VALUES (5, 'aa', 'aa', '123456', '2022-04-11 22:03:02', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
