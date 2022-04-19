/*
 Navicat Premium Data Transfer

 Source Server         : ubuntu
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.41.128:3306
 Source Schema         : mind_links_core

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 14/12/2021 21:59:08
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for parent_list
-- ----------------------------
DROP TABLE IF EXISTS `parent_list`;
CREATE TABLE `parent_list`
(
    `p_id`      int NOT NULL AUTO_INCREMENT,
    `parent_id` int NULL DEFAULT NULL COMMENT '父id',
    `name`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`p_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parent_list
-- ----------------------------
INSERT INTO `parent_list`
VALUES (1, 0, 'aa');
INSERT INTO `parent_list`
VALUES (2, 1, 'bb');
INSERT INTO `parent_list`
VALUES (3, 1, 'cc');
INSERT INTO `parent_list`
VALUES (4, 2, 'dd');

SET
FOREIGN_KEY_CHECKS = 1;
