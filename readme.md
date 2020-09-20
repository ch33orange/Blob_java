## Agency

## 资料

[Spring 文档](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[Github deploy key](https://developer.github.com/v3/guides/mNfinf-deploy-keys/#deploy-keys)
[Github Oauth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[editor.md](https://pandao.github.io/editor.md/)
[UFile SDK](https://github.com/ucloud/ufile-sdk-java)

## 工具

[Git](https://git-scm.com/download)
[Visual Paradigm](https://www.visual-paradigm.com)
[mybatis](https://mybatis.org/mybatis-3/zh/index.html)
[PostMan](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)

##脚本

```sql
/*
 Navicat Premium Data Transfer

 Source Server         : bluer2
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 121.196.52.244:3306
 Source Schema         : agency

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 20/09/2020 21:20:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agency_class
-- ----------------------------
DROP TABLE IF EXISTS `agency_class`;
CREATE TABLE `agency_class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级名称',
  `teacher` int(11) NOT NULL COMMENT '任课老师id',
  `assistant` int(11) NOT NULL COMMENT '小班id',
  `teacher_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任课老师名字',
  `assistant_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小班名字',
  `label` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '课程分属',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_comment
-- ----------------------------
DROP TABLE IF EXISTS `agency_comment`;
CREATE TABLE `agency_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL COMMENT '父类id',
  `type` int(11) NOT NULL COMMENT '父类类型',
  `commentator` bigint(20) NOT NULL COMMENT '评论人id',
  `like_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `comment_count` int(11) NOT NULL DEFAULT 0 COMMENT '二级评论数',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_notification
-- ----------------------------
DROP TABLE IF EXISTS `agency_notification`;
CREATE TABLE `agency_notification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notifier` bigint(20) NOT NULL COMMENT '通知人',
  `notifier_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `receiver` bigint(20) NOT NULL,
  `outer_id` bigint(20) NOT NULL COMMENT '是问题或者回复的id',
  `type` int(11) NOT NULL COMMENT '用于区分评论还是回复',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '已读/未读',
  `outer_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_question
-- ----------------------------
DROP TABLE IF EXISTS `agency_question`;
CREATE TABLE `agency_question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '正文',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频的url',
  `creator` bigint(20) NOT NULL,
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '0',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '0',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '0',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_record
-- ----------------------------
DROP TABLE IF EXISTS `agency_record`;
CREATE TABLE `agency_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习记录 id',
  `type` tinyint(1) NOT NULL COMMENT '0-测试 1-视频',
  `student_id` int(11) NOT NULL COMMENT '学生id',
  `test_id` int(11) NULL DEFAULT NULL COMMENT '测试的id',
  `video_id` int(11) NULL DEFAULT NULL COMMENT 'video的id',
  `score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分数',
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `teacher` int(11) NOT NULL COMMENT '老师id',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_teacher
-- ----------------------------
DROP TABLE IF EXISTS `agency_teacher`;
CREATE TABLE `agency_teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '老师表id',
  `icon` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '老师头像',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '老师名字',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码,MD5加密',
  `gender` int(1) NOT NULL COMMENT '0-男 1-女',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `degree` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学历',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职称',
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '教师经历介绍',
  `question` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码答案',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_test
-- ----------------------------
DROP TABLE IF EXISTS `agency_test`;
CREATE TABLE `agency_test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `test_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '试题题目',
  `test_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '试题内容',
  `test_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '试题答案',
  `test_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '试题类型 0-选择题 1-问答题',
  `teacher` int(11) NOT NULL COMMENT '任课老师id',
  `teacher_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任课老师名字',
  `label` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '科目类别',
  `class_id` int(11) NOT NULL COMMENT '归属班级',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agency_user
-- ----------------------------
DROP TABLE IF EXISTS `agency_user`;
CREATE TABLE `agency_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `icon` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户头像',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码,MD5加密',
  `gender` int(1) NOT NULL COMMENT '0-男 1-女',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '学生个人信息',
  `question` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码答案',
  `role` int(1) NOT NULL COMMENT '角色 1-管理员,0-普通用户',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for relation_belong
-- ----------------------------
DROP TABLE IF EXISTS `relation_belong`;
CREATE TABLE `relation_belong`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生与班级关系 id',
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `student_id` int(11) NOT NULL COMMENT '学生id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```

```bash mybatis逆向工程生成dao mapper
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
