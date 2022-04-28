/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : example

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 28/04/2022 10:01:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_date` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', 'zhangsan', '123456', '2022-04-11 22:01:54', 1, NULL, NULL);
INSERT INTO `user` VALUES (2, '李四', 'lisi', '123456', '2022-04-11 22:02:13', 1, NULL, NULL);
INSERT INTO `user` VALUES (3, '王五', 'wangwu', '123456', '2022-04-11 22:02:33', 1, NULL, NULL);
INSERT INTO `user` VALUES (4, 'qq', 'qq', '123456', '2022-04-11 22:02:54', 1, NULL, NULL);
INSERT INTO `user` VALUES (5, 'aa', 'aa', '123456', '2022-04-11 22:03:02', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
