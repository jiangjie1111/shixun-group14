/*
 Navicat Premium Data Transfer

 Source Server         : shixun2022.10
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : sepbifang.mysql.rds.aliyuncs.com:3306
 Source Schema         : springboot-vue

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 19/10/2022 15:04:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `pid` int NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES (1, '安徽省', NULL, 'Anhui');
INSERT INTO `area` VALUES (2, '合肥市', 1, 'Hefei');
INSERT INTO `area` VALUES (3, '政务区', 2, 'Zhengwu');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '训练集数量',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '测试集数量',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '测试集预测正确数量',
  `create_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '测试集预测错误数量',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '正确率',
  `tn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'tn',
  `fp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'fp',
  `tp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'tp',
  `fn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'fn',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (4, '1497', '206', '997', '791', '0.8084252758274825', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `pid` int NULL DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '文学', NULL);
INSERT INTO `category` VALUES (2, '童书', 1);
INSERT INTO `category` VALUES (3, '社会科学', 1);
INSERT INTO `category` VALUES (4, '经济学', 1);
INSERT INTO `category` VALUES (5, '科普百科', 2);
INSERT INTO `category` VALUES (7, '法律', 3);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论人',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论时间',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID',
  `foreign_id` bigint NULL DEFAULT 0 COMMENT '关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '留言表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (21, '哦豁', 'admin', '2021-05-22 10:49:48', NULL, 0);
INSERT INTO `message` VALUES (22, '老弟', 'admin', '2021-05-22 10:51:07', 21, 0);
INSERT INTO `message` VALUES (23, '哈哈哈', 'zhang', '2021-05-24 17:13:45', 22, 0);
INSERT INTO `message` VALUES (24, '我们都爱吃大西瓜', 'zhang', '2021-05-24 17:13:58', NULL, 0);
INSERT INTO `message` VALUES (25, '1232', 'admin', '2021-10-31 16:46:39', NULL, 0);
INSERT INTO `message` VALUES (26, '2345', 'admin', '2021-10-31 16:47:00', 25, 0);
INSERT INTO `message` VALUES (27, '11111', 'admin', '2021-10-31 16:52:09', 26, 0);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (4, '4444', '<p>444444</p><p><br/></p><p>44444</p><p><br/></p><p><br/></p><p>555555<img src=\"https://img1.baidu.com/it/u=4110196045,3829597861&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p>', '管理员', '2021-07-17 18:55:27');
INSERT INTO `news` VALUES (5, '77777', '<p>7777</p><p><br/></p><p><img src=\"http://localhost:9090/files/c536f6f2f0e94983951240d73d740601\" style=\"max-width:100%;\" contenteditable=\"false\" width=\"388\" height=\"388\"/><br/></p><p>8888</p><p><br/></p><p><img src=\"http://localhost:9090/files/b344314319f047cf9192ce64ca454674\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '管理员', '2021-07-17 19:14:14');
INSERT INTO `news` VALUES (6, '带你从0搭建一个Springboot+vue前后端分离项目，真的很简单！', '<p><iframe src=\"//player.bilibili.com/player.html?aid=803885504&amp;bvid=BV14y4y1M7Nc&amp;cid=361690131&amp;page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" width=\"600\" height=\"400\"> </iframe></p>', '管理员', '2021-09-05 22:28:25');
INSERT INTO `news` VALUES (7, '我擦！妹子给我整害羞了⁄(⁄ ⁄ ⁄ω⁄ ⁄ ⁄)⁄……', '<p><iframe src=\"//player.bilibili.com/player.html?aid=717601909&amp;bvid=BV1sQ4y1174g&amp;cid=396410411&amp;page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" width=\"600\" height=\"400\"> </iframe></p>', '管理员', '2021-09-05 22:31:40');
INSERT INTO `news` VALUES (8, '11111', '<p>哈哈哈</p><p><br/></p><p><img src=\"http://localhost:9090/files/abbac1162e3948dcbb5df85f752f27f3\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p></p><p></p>', '管理员', '2021-09-29 23:17:27');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源路径',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'Home', '/home', '操作指南', 'el-icon-house');
INSERT INTO `permission` VALUES (2, 'Category', '/category', '上传文件（KNN）', 'el-icon-menu');
INSERT INTO `permission` VALUES (3, 'Book', '/book', '预测结果（KNN）', 'el-icon-files');
INSERT INTO `permission` VALUES (4, 'News', '/news', '上传文件（逻辑回归）', 'el-icon-news');
INSERT INTO `permission` VALUES (5, 'Order', '/order', '预测结果（逻辑回归）', 'el-icon-s-order');
INSERT INTO `permission` VALUES (6, 'Map', '/map', 'KNN预测结果', 'el-icon-map-location');
INSERT INTO `permission` VALUES (7, 'Im', '/im', '逻辑回归预测结果', 'el-icon-chat-round');
INSERT INTO `permission` VALUES (8, 'Message', '/message', '在线留言', 'el-icon-message');
INSERT INTO `permission` VALUES (9, 'User', '/user', '用户管理', 'el-icon-user');
INSERT INTO `permission` VALUES (10, 'Permission', '/permisssion', '权限菜单', 'el-icon-menu');
INSERT INTO `permission` VALUES (11, 'Role', '/role', '角色管理', 'el-icon-s-custom');
INSERT INTO `permission` VALUES (13, 'Person', '/person', '个人信息', '');
INSERT INTO `permission` VALUES (14, 'Password', '/password', '修改密码', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员');
INSERT INTO `role` VALUES (2, 'user', '普通用户');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` int NOT NULL COMMENT '角色id',
  `permission_id` int NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (1, 5);
INSERT INTO `role_permission` VALUES (1, 8);
INSERT INTO `role_permission` VALUES (1, 11);
INSERT INTO `role_permission` VALUES (1, 13);
INSERT INTO `role_permission` VALUES (1, 14);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (2, 2);
INSERT INTO `role_permission` VALUES (2, 4);
INSERT INTO `role_permission` VALUES (2, 6);
INSERT INTO `role_permission` VALUES (2, 7);
INSERT INTO `role_permission` VALUES (2, 8);
INSERT INTO `role_permission` VALUES (2, 13);
INSERT INTO `role_permission` VALUES (2, 14);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `total_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '总价',
  `pay_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '实付款',
  `discount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0.00' COMMENT '优惠金额',
  `transport_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0.00' COMMENT '运费',
  `tp` int NULL DEFAULT NULL COMMENT '订单编号',
  `tn` int NULL DEFAULT NULL COMMENT '用户id',
  `fp` int NULL DEFAULT NULL COMMENT '用户账户',
  `fn` int NULL DEFAULT NULL COMMENT '创建时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `state` int NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (29, '997', '691', '435', '256', '0.6295224312590448', 403, 144, 112, 32, NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `account` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '账户余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$.cxJ637DYA1PRSzfPmBO0efS1W2eTQefS51b83Nr5xgRNAW0ffgjO', '管理员', 19, '男', '北京', 'http://localhost:9090/files/888f2d39d0724816a738a716d56ad58a', 100000.00);
INSERT INTO `user` VALUES (13, 'zhang', '$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS', '张三', 20, '男', '上海', 'http://localhost:9090/files/d227473b758e4915a3e7c866d2d494cf', 200.00);
INSERT INTO `user` VALUES (14, 'wang', '$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS', '王梦晨', 24, '女', '地球', NULL, 300.00);
INSERT INTO `user` VALUES (15, 'li', '$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS', '李雪', 22, '女', '深圳', NULL, 500.00);
INSERT INTO `user` VALUES (16, 'qian', '$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS', '钱江', 22, '男', '山西', NULL, 700.00);
INSERT INTO `user` VALUES (17, '123', '$2a$10$xLB6Cl0epH/viyjOfnYOoeY2rvaVnClgdB.smvtN3Jw4MH0F7mf8i', '用户15824', NULL, NULL, '北京', NULL, 0.00);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (13, 2);
INSERT INTO `user_role` VALUES (14, 2);
INSERT INTO `user_role` VALUES (15, 2);
INSERT INTO `user_role` VALUES (16, 2);
INSERT INTO `user_role` VALUES (17, 2);
INSERT INTO `user_role` VALUES (18, 2);

SET FOREIGN_KEY_CHECKS = 1;
