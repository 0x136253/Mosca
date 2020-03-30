/*
 Navicat Premium Data Transfer

 Source Server         : mutiny
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 47.103.0.173:3306
 Source Schema         : Muti

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 01/02/2020 17:14:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for DefaultData
-- ----------------------------
DROP TABLE IF EXISTS `DefaultData`;
CREATE TABLE `DefaultData`  (
  `DefaultModule_ID` int(32) NOT NULL AUTO_INCREMENT,
  `Default_ID` int(32) NOT NULL,
  `Data_Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Data_Param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[数据与参数对应关系]',
  `Is_Calculate` tinyint(1) NULL DEFAULT NULL COMMENT '[是否计算完毕]',
  `Is_Userful` tinyint(1) NULL DEFAULT NULL COMMENT '[是否为有效]',
  `Description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `File_ID` int(32) NULL DEFAULT NULL COMMENT '[保存的数据]',
  PRIMARY KEY (`DefaultModule_ID`) USING BTREE,
  INDEX `Default_ID`(`Default_ID`) USING BTREE,
  INDEX `File_ID`(`File_ID`) USING BTREE,
  CONSTRAINT `DefaultData_ibfk_3` FOREIGN KEY (`Default_ID`) REFERENCES `Default_Module` (`Default_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `DefaultData_ibfk_4` FOREIGN KEY (`File_ID`) REFERENCES `File_Module` (`File_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of DefaultData
-- ----------------------------
INSERT INTO `DefaultData` VALUES (5, 1, 'smd\'sData', NULL, 0, 1, 'test2.1', 10);

-- ----------------------------
-- Table structure for Default_Module
-- ----------------------------
DROP TABLE IF EXISTS `Default_Module`;
CREATE TABLE `Default_Module`  (
  `Default_ID` int(32) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Function` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Param_Number` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`Default_ID`) USING BTREE,
  INDEX `Default_ID`(`Default_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Default_Module
-- ----------------------------
INSERT INTO `Default_Module` VALUES (1, 'test', 'test', 'x+y+z', 3);

-- ----------------------------
-- Table structure for File_Answer
-- ----------------------------
DROP TABLE IF EXISTS `File_Answer`;
CREATE TABLE `File_Answer`  (
  `File_ID` int(32) NOT NULL AUTO_INCREMENT,
  `File_Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Module_ID` int(32) NULL DEFAULT NULL,
  `File_URL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Encode_Algorithm` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PublicKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PrivateKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`File_ID`) USING BTREE,
  INDEX `Module_ID`(`Module_ID`) USING BTREE,
  CONSTRAINT `File_Answer_ibfk_1` FOREIGN KEY (`Module_ID`) REFERENCES `Module` (`Module_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for File_Module
-- ----------------------------
DROP TABLE IF EXISTS `File_Module`;
CREATE TABLE `File_Module`  (
  `File_ID` int(32) NOT NULL AUTO_INCREMENT,
  `File_URL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Module_ID` int(32) NULL DEFAULT NULL,
  `Encode_Algorithm` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PublicKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PrivateKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`File_ID`) USING BTREE,
  INDEX `File_ID`(`File_ID`) USING BTREE,
  INDEX `Module_ID`(`Module_ID`) USING BTREE,
  CONSTRAINT `File_Module_ibfk_3` FOREIGN KEY (`Module_ID`) REFERENCES `Module` (`Module_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of File_Module
-- ----------------------------
INSERT INTO `File_Module` VALUES (5, 'D://temp-rainy//ModuleFile//435b7443-54a5-4b90-876c-af06e17887a3.csv', 5, 'GSW', '789456', '123456');
INSERT INTO `File_Module` VALUES (10, 'D://temp-rainy//ModuleFile//ddeafcf9-e031-4b56-a3c4-2feb38f4559f.csv', NULL, 'GSW', '789456', '123456');

-- ----------------------------
-- Table structure for File_Module_Temp
-- ----------------------------
DROP TABLE IF EXISTS `File_Module_Temp`;
CREATE TABLE `File_Module_Temp`  (
  `File_ID` int(32) NOT NULL AUTO_INCREMENT,
  `File_Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `File_URL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Module_ID` int(32) NULL DEFAULT NULL,
  `Uploader` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Encode_Algorithm` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PublicKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PrivateKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TotleParam` int(16) NULL DEFAULT NULL,
  `NowParam` int(16) NULL DEFAULT NULL,
  `DefaultID` int(32) NULL DEFAULT NULL,
  `Is_Default` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`File_ID`) USING BTREE,
  INDEX `Uploader`(`Uploader`) USING BTREE,
  INDEX `File_ID`(`File_ID`) USING BTREE,
  INDEX `Module_ID`(`Module_ID`) USING BTREE,
  INDEX `DefaultID`(`DefaultID`) USING BTREE,
  CONSTRAINT `File_Module_ibfk_55` FOREIGN KEY (`Uploader`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `File_Module_ibfk_66` FOREIGN KEY (`Module_ID`) REFERENCES `Module` (`Module_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `File_Module_Temp_ibfk_1` FOREIGN KEY (`DefaultID`) REFERENCES `DefaultData` (`DefaultModule_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of File_Module_Temp
-- ----------------------------
INSERT INTO `File_Module_Temp` VALUES (18, NULL, 'D://temp-rainy//TempFile//string//27a355f0-1432-4c9a-8ca3-89294dd9954e.csv', 5, 'string', 'GSW', '789456', '123456', 3, 2, NULL, 0);
INSERT INTO `File_Module_Temp` VALUES (19, NULL, 'D://temp-rainy//TempFile//string//60d921f5-f90d-4d31-8164-79c07f1e5559.csv', 5, 'string', 'GSW', '789456', '123456', 3, 1, NULL, 0);
INSERT INTO `File_Module_Temp` VALUES (28, NULL, 'D://temp-rainy//TempFile//string//f4e6c44e-c4ed-4233-a760-0804b752245c.csv', NULL, 'string', 'GSW', '789456', '123456', 3, 1, 5, 1);
INSERT INTO `File_Module_Temp` VALUES (29, NULL, 'D://temp-rainy//TempFile//string//6b1bcdfe-fdcd-4e01-9247-8c6173122362.csv', NULL, 'string', 'GSW', '789456', '123456', 3, 2, 5, 1);

-- ----------------------------
-- Table structure for File_Other
-- ----------------------------
DROP TABLE IF EXISTS `File_Other`;
CREATE TABLE `File_Other`  (
  `File_ID` int(20) NOT NULL AUTO_INCREMENT,
  `File_Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `File_URL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Type` int(11) NULL DEFAULT NULL COMMENT '0头像，1许可证',
  PRIMARY KEY (`File_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of File_Other
-- ----------------------------
INSERT INTO `File_Other` VALUES (1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for Log
-- ----------------------------
DROP TABLE IF EXISTS `Log`;
CREATE TABLE `Log`  (
  `Log_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作ID',
  `User_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `Operation_Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名',
  `Method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用函数名',
  `Params` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '函数参数',
  `Database_Influence` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '影响的表',
  `Operation_Time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `IP` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `Log_ID`(`Log_ID`) USING BTREE,
  INDEX `User_ID`(`User_ID`) USING BTREE,
  CONSTRAINT `Log_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Log
-- ----------------------------
INSERT INTO `Log` VALUES (17, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@19bff61a[\r\n  username=string\r\n  password=string\r\n]}', '登陆', '2020-01-30 11:44:20', '127.0.0.1');
INSERT INTO `Log` VALUES (18, 'string', '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@55011e5d[\r\n  username=string\r\n  password=string\r\n]}', '登陆', '2020-01-30 11:45:26', '127.0.0.1');
INSERT INTO `Log` VALUES (19, 'string', '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@59eb660f[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-01-30 11:47:31', '127.0.0.1');
INSERT INTO `Log` VALUES (20, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@54e163dc[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-01-30 13:18:10', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (21, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@73ae1389[\r\n  moduleID=0\r\n  EncodeAlgorithm=string\r\n  PublicKey=string\r\n  PrivateKey=string\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\r\n  is_default=true\r\n]}', 'File_Module', '2020-01-30 13:19:05', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (22, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@7908ba8d[\r\n  moduleID=0\r\n  EncodeAlgorithm=string\r\n  PublicKey=string\r\n  PrivateKey=string\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\r\n  is_default=true\r\n]}', 'File_Module', '2020-01-30 13:21:00', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (23, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@180e2acc[\r\n  moduleID=0\r\n  EncodeAlgorithm=string\r\n  PublicKey=string\r\n  PrivateKey=string\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\r\n  is_default=true\r\n]}', 'File_Module', '2020-01-30 13:22:08', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (24, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@162e106d[\r\n  moduleID=0\r\n  EncodeAlgorithm=string\r\n  PublicKey=string\r\n  PrivateKey=string\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\r\n  is_default=true\r\n]}', 'File_Module', '2020-01-30 13:22:56', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (25, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@22de9944[\r\n  moduleID=0\r\n  EncodeAlgorithm=string\r\n  PublicKey=string\r\n  PrivateKey=string\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\r\n  is_default=true\r\n]}', 'File_Module', '2020-01-30 13:23:29', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (26, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@5e807774[\r\n  moduleID=0\r\n  EncodeAlgorithm=string\r\n  PublicKey=string\r\n  PrivateKey=string\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\r\n  is_default=true\r\n]}', 'File_Module', '2020-01-30 13:23:48', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (27, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@282caef7[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-01-30 15:44:10', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (28, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@58ab0dad[\r\n  username=Anon\r\n  password=string\r\n]}', 'User,Role', '2020-01-30 15:44:17', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (29, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@2b8110ed[\r\n  username=Anon\r\n  password=string\r\n]}', 'User,Role', '2020-01-30 15:44:21', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (30, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1934cb40[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 15:45:44', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (31, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@10e86128[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 15:47:37', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (32, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@5eff9e72[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 15:54:59', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (33, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@76994dee[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 15:56:34', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (34, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1eb391e2[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 15:57:15', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (35, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@6211560c[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 18:42:08', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (36, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@7e922e33[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 18:46:41', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (37, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@50b2209d[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 18:46:58', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (38, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@195a9977[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[12, 11], additionalProp2=[22, 21], additionalProp3=[32, 31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-30 18:47:49', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (39, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@748338af[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-01-31 13:16:25', '127.0.0.1');
INSERT INTO `Log` VALUES (40, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@2eecb37c[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:22:03', '127.0.0.1');
INSERT INTO `Log` VALUES (41, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@15986f59[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[[11,221,23],[ss,ss,ss]], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:26:58', '127.0.0.1');
INSERT INTO `Log` VALUES (42, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@5c4dea0d[\r\n  moduleID=12\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:38:34', '127.0.0.1');
INSERT INTO `Log` VALUES (43, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4b773228[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:40:08', '127.0.0.1');
INSERT INTO `Log` VALUES (44, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@3634a0b7[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:40:17', '127.0.0.1');
INSERT INTO `Log` VALUES (45, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@3c1ecd04[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:47:21', '127.0.0.1');
INSERT INTO `Log` VALUES (46, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4b66ff97[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=1\r\n  now=1\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:47:41', '127.0.0.1');
INSERT INTO `Log` VALUES (47, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@6951dd2c[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[11], additionalProp2=[21], additionalProp3=[31]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:48:41', '127.0.0.1');
INSERT INTO `Log` VALUES (48, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@154b7b7d[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[12], additionalProp2=[22], additionalProp3=[32]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:51:24', '127.0.0.1');
INSERT INTO `Log` VALUES (49, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@39977aa8[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[[11,12,13],[11,12,13]], additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:52:58', '127.0.0.1');
INSERT INTO `Log` VALUES (50, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@3d93e653[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[[11,12,13],[11,12,13]], additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module', '2020-01-31 13:53:09', '127.0.0.1');
INSERT INTO `Log` VALUES (51, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@79e62072[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-01-31 16:06:27', '127.0.0.1');
INSERT INTO `Log` VALUES (52, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@1e7a1d9d[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:06:45', '127.0.0.1');
INSERT INTO `Log` VALUES (53, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@20c01e23[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:08:40', '127.0.0.1');
INSERT INTO `Log` VALUES (54, 'string', '删除文件', 'com.mutiny.demo.controller.FileModuleController.delete', '{fileid=java.lang.Integer@29168629[\r\n  value=1\r\n]}', 'File_Module_Temp', '2020-01-31 16:08:53', '127.0.0.1');
INSERT INTO `Log` VALUES (55, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@7204a141[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:08:58', '127.0.0.1');
INSERT INTO `Log` VALUES (56, 'string', '删除文件', 'com.mutiny.demo.controller.FileModuleController.delete', '{fileid=java.lang.Integer@5f0b87c7[\r\n  value=2\r\n]}', 'File_Module_Temp', '2020-01-31 16:09:55', '127.0.0.1');
INSERT INTO `Log` VALUES (57, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4b26c77[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[[11,12,13],[11,12,13]], additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:15:42', '127.0.0.1');
INSERT INTO `Log` VALUES (58, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@72f5456d[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[[11,12,13],[11,12,13]], additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:15:52', '127.0.0.1');
INSERT INTO `Log` VALUES (59, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@4a7d4220[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:16:10', '127.0.0.1');
INSERT INTO `Log` VALUES (60, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@6259393b[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:36:44', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (61, 'string', '清空缓存', 'com.mutiny.demo.controller.FileModuleController.flush', '{}', 'File_Module_Temp', '2020-01-31 16:37:35', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (62, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@5f08a5b1[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:38:36', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (63, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@6a6ab5dd[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:38:47', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (64, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@24943222[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:38:56', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (65, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@72884c04[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:39:03', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (66, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@6bdb8322[\r\n  username=Anon\r\n  password=string\r\n]}', 'User,Role', '2020-01-31 16:39:18', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (67, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@284fbeb3[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:39:51', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (68, 'Anon', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@230c11a6[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-01-31 16:40:02', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (69, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@1d45e9f1[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-01-31 16:40:09', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (70, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@502aefb5[\n  moduleID=4\n  EncodeAlgorithm=string\n  PublicKey=string\n  PrivateKey=string\n  total=1\n  now=1\n  data={additionalProp1=[string], additionalProp2=[string], additionalProp3=[string]}\n  is_default=true\n]}', 'File_Module_Temp', '2020-01-31 16:52:40', '112.17.247.132');
INSERT INTO `Log` VALUES (71, 'string', '显示上传文件', 'com.mutiny.demo.controller.FileModuleController.show', '{moduleid=java.lang.Integer@2a586ff5[\n  value=4\n]}', 'File_Module_Temp', '2020-01-31 16:53:06', '112.17.247.132');
INSERT INTO `Log` VALUES (72, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@8a5023[\n  username=string\n  password=string\n]}', 'User,Role', '2020-01-31 17:09:29', '112.17.247.132');
INSERT INTO `Log` VALUES (73, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@144330a7[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-02-01 12:28:59', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (74, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@677e4c53[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:29:51', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (75, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@37d135ec[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:30:04', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (76, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@61a549a3[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:30:13', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (77, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4fee2c0b[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:30:44', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (78, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@697bb7e2[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:34:25', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (79, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@235808f3[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:34:34', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (80, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@7c23ed09[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:39:19', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (81, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@7ef145c5[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:39:28', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (82, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@335771a3[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:42:42', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (83, 'string', '清空缓存', 'com.mutiny.demo.controller.FileModuleController.flush', '{}', 'File_Module_Temp', '2020-02-01 12:43:02', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (84, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@5ef422bf[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:43:08', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (85, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@68c2c5e5[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:43:33', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (86, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@e09840c[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:45:21', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (87, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@2e72bbcb[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:45:39', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (88, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@13d8d1e0[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:45:49', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (89, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@5cca61f[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:45:59', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (90, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4399727f[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:46:08', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (91, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@f801480[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:48:34', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (92, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@2d40e722[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:48:45', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (93, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@594f8c54[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:48:55', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (94, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@407b9685[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:55:37', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (95, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@2b457240[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:55:45', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (96, 'string', '清空缓存', 'com.mutiny.demo.controller.FileModuleController.flush', '{}', 'File_Module_Temp', '2020-02-01 12:56:37', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (97, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1fad580f[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:56:48', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (98, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@55ddd54c[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:56:57', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (99, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@3996b38e[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:57:06', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (100, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@7b1b4723[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 12:57:14', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (101, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@216ba3a6[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:58:03', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (102, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@f557f72[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp2=[[11,12,13],[11,12,13]], additionalProp3=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:58:14', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (103, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@175f5760[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:58:22', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (104, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@7df3bcd0[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additionalProp1=[[11,12,13],[11,12,13]]}\r\n  is_default=false\r\n]}', 'File_Module_Temp', '2020-02-01 12:58:30', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (105, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1189f7b7[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:00:56', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (106, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4086402e[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:01:05', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (107, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1f660a18[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:01:12', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (108, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@5f45e270[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:05:11', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (109, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1b0cdd46[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:05:21', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (110, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@4bf6e588[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:05:29', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (111, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@61782d15[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:08:43', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (112, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@31ed9b86[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:08:52', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (113, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@40572259[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:08:59', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (114, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@1ba76978[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:11:00', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (115, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@54f7631[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:11:09', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (116, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@441a5cf8[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:11:17', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (117, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@12065a31[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:14:21', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (118, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@36cf010[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:14:31', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (119, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@77fe5761[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=2\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:14:39', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (120, 'string', '上传数据文件', 'com.mutiny.demo.controller.FileModuleController.uploadData', '{fileModuleUploaderDTO=com.mutiny.demo.dto.FileModuleUploaderDTO@3595d88a[\r\n  moduleID=5\r\n  EncodeAlgorithm=GSW\r\n  PublicKey=789456\r\n  PrivateKey=123456\r\n  total=2\r\n  now=1\r\n  data={additional2=[[11,12,13],[11,12,13]], additional3=[[11,12,13],[11,12,13]]}\r\n  is_default=true\r\n]}', 'File_Module_Temp', '2020-02-01 13:14:48', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (121, 'string', '显示固定模型数据上传文件', 'com.mutiny.demo.controller.FileModuleController.showDefault', '{DefaultDataID=java.lang.Integer@6ee7a547[\r\n  value=5\r\n]}', 'File_Module_Temp', '2020-02-01 13:15:36', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (122, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@532bd24e[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-02-01 13:21:45', '127.0.0.1');
INSERT INTO `Log` VALUES (123, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@2d564c6a[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-02-01 13:21:59', '192.168.43.63');
INSERT INTO `Log` VALUES (124, NULL, '登陆', 'com.mutiny.demo.controller.AccountController.login', '{userLoginDTO=com.mutiny.demo.dto.UserLoginDTO@79e052c1[\r\n  username=string\r\n  password=string\r\n]}', 'User,Role', '2020-02-01 15:07:39', '0:0:0:0:0:0:0:1');
INSERT INTO `Log` VALUES (125, NULL, '企业管理员注册', 'com.mutiny.demo.controller.AccountController.registerAdmin', '{userRegisterAdminDTO=com.mutiny.demo.dto.UserRegisterAdminDTO@7369ccd6[\r\n  ID=admin1\r\n  Password=string\r\n  Name=admin1\r\n  Tel=string\r\n  Email=string\r\n  Company=string\r\n  FileID=1\r\n  CerCode=string\r\n]}', 'User,Role', '2020-02-01 17:11:57', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for Log_Calculate
-- ----------------------------
DROP TABLE IF EXISTS `Log_Calculate`;
CREATE TABLE `Log_Calculate`  (
  `Calculate_ID` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `User_ID` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '操作ID',
  `Module_Name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '操作名',
  `Type` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '计算/查看结果',
  `Operation_Time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for Module
-- ----------------------------
DROP TABLE IF EXISTS `Module`;
CREATE TABLE `Module`  (
  `Module_ID` int(32) NOT NULL AUTO_INCREMENT,
  `Project_ID` int(32) NULL DEFAULT NULL,
  `Module_Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Function` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Param_Number` int(11) NULL DEFAULT NULL COMMENT '参数的个数',
  `Data_Param` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据与参数的对应关系',
  `Is_Calculate` tinyint(1) NULL DEFAULT 0 COMMENT '是否计算完毕',
  `Is_Default` tinyint(1) NULL DEFAULT 0 COMMENT '是否固定模型',
  `Is_Userful` tinyint(1) NULL DEFAULT 1,
  `DefaultModule_ID` int(32) NULL DEFAULT NULL COMMENT '[固定模型对应的数据源,仅当模型是固定模型的时候有]',
  PRIMARY KEY (`Module_ID`) USING BTREE,
  INDEX `Project_ID`(`Project_ID`) USING BTREE,
  INDEX `DefaultModule_ID`(`DefaultModule_ID`) USING BTREE,
  CONSTRAINT `Module_ibfk_3` FOREIGN KEY (`Project_ID`) REFERENCES `Project` (`Project_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Module_ibfk_4` FOREIGN KEY (`DefaultModule_ID`) REFERENCES `DefaultData` (`DefaultModule_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Module
-- ----------------------------
INSERT INTO `Module` VALUES (1, 11, 'string', 'string', 'string', 0, NULL, 0, 0, 1, NULL);
INSERT INTO `Module` VALUES (2, 11, 'string', 'string', 'string', 0, NULL, 0, 0, 1, NULL);
INSERT INTO `Module` VALUES (3, 11, 'string', 'string', 'string', 5, NULL, 0, 0, 1, NULL);
INSERT INTO `Module` VALUES (4, 12, 'Test', 'x^y+2', 'TEst', 2, NULL, 0, 0, 1, NULL);
INSERT INTO `Module` VALUES (5, 12, 'Testd', 'x^y+2+z', 'TEstd', 3, NULL, 0, 0, 1, NULL);

-- ----------------------------
-- Table structure for Module_User
-- ----------------------------
DROP TABLE IF EXISTS `Module_User`;
CREATE TABLE `Module_User`  (
  `Relation_ID` int(32) NOT NULL AUTO_INCREMENT,
  `Module_ID` int(32) NULL DEFAULT NULL COMMENT '外键',
  `User_ID` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键',
  `Type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者/使用者',
  `Is_Permiss` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经授权',
  PRIMARY KEY (`Relation_ID`) USING BTREE,
  INDEX `User_ID`(`User_ID`) USING BTREE,
  INDEX `Module_ID`(`Module_ID`) USING BTREE,
  CONSTRAINT `Module_User_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Module_User_ibfk_2` FOREIGN KEY (`Module_ID`) REFERENCES `DefaultData` (`DefaultModule_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for Project
-- ----------------------------
DROP TABLE IF EXISTS `Project`;
CREATE TABLE `Project`  (
  `Project_ID` int(32) NOT NULL AUTO_INCREMENT,
  `Project_Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Project_Purpose` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目用途',
  `Project_Description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目描述',
  `Create_Time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `Module_Description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型描述',
  `Invite_Code_1` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级邀请码',
  `Invite_Code_2` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级邀请码',
  `Is_Default` tinyint(1) NULL DEFAULT 0 COMMENT '是否固定模型',
  `Is_End` tinyint(1) NULL DEFAULT 0 COMMENT '是否计算结束',
  `Is_Userful` tinyint(1) NULL DEFAULT 1 COMMENT '是否删除（弃用）',
  `stage` int(4) NULL DEFAULT NULL COMMENT '1基本信息2输入风控模型3邀请他人及信息4开始计算5计算结束',
  PRIMARY KEY (`Project_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12346 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Project
-- ----------------------------
INSERT INTO `Project` VALUES (2, 'string', 'string', 'string', '2020-01-16 18:57:09', 'string', NULL, NULL, 0, 0, 0, 1);
INSERT INTO `Project` VALUES (3, 'string', 'string', 'string', '2020-01-16 18:57:13', 'string', NULL, NULL, 0, 0, 0, 1);
INSERT INTO `Project` VALUES (4, 'string', 'string', 'string', '2020-01-16 18:57:17', 'string', NULL, NULL, 0, 0, 0, 1);
INSERT INTO `Project` VALUES (5, 'string', 'string', 'string', '2020-01-16 19:03:39', 'string', NULL, NULL, 0, 0, 0, 1);
INSERT INTO `Project` VALUES (6, 'string', 'string', 'string', '2020-01-16 19:09:52', 'string', NULL, NULL, 0, 0, 0, 1);
INSERT INTO `Project` VALUES (7, 'string', 'string', 'string', '2020-01-16 19:10:52', 'string', NULL, NULL, 0, 0, 0, 1);
INSERT INTO `Project` VALUES (8, 'string', 'string', 'string', '2020-01-17 10:22:22', 'string', 'jCiteL', '2CYN2S', 0, 1, 1, 1);
INSERT INTO `Project` VALUES (9, 'string', 'string', 'string', '2020-01-17 10:24:52', 'string', '9DLrUs', 'f6l5d6', 0, 0, 0, 1);
INSERT INTO `Project` VALUES (10, 'string', 'string', 'string', '2020-01-17 10:41:24', 'string', '7822f1', 'ssssss', 0, 0, 0, 1);
INSERT INTO `Project` VALUES (11, 'string', 'string', 'string', '2020-01-17 13:28:30', NULL, 'zGIsN1', '3mIyGr', 0, 0, 1, 1);
INSERT INTO `Project` VALUES (12, 'The sky', 'string', 'Test', '2020-01-18 17:57:49', 'for test', 'string', 'string', 0, 0, 0, 1);
INSERT INTO `Project` VALUES (12345, 'string', 'string', 'string', '2020-01-16 18:52:40', 'string', NULL, NULL, 0, 0, 0, 1);

-- ----------------------------
-- Table structure for Project_User
-- ----------------------------
DROP TABLE IF EXISTS `Project_User`;
CREATE TABLE `Project_User`  (
  `Relation_ID` int(32) NOT NULL AUTO_INCREMENT,
  `Project_ID` int(32) NULL DEFAULT NULL COMMENT '外键',
  `User_ID` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键',
  `Type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Relation_ID`) USING BTREE,
  INDEX `User_ID`(`User_ID`) USING BTREE,
  INDEX `Project_ID`(`Project_ID`) USING BTREE,
  CONSTRAINT `Project_User_ibfk_2` FOREIGN KEY (`User_ID`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Project_User_ibfk_3` FOREIGN KEY (`Project_ID`) REFERENCES `Project` (`Project_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Project_User
-- ----------------------------
INSERT INTO `Project_User` VALUES (3, 8, '123456789', 'creater');
INSERT INTO `Project_User` VALUES (4, 9, '123456789', 'watch');
INSERT INTO `Project_User` VALUES (5, 10, '123456789', 'join');
INSERT INTO `Project_User` VALUES (6, 11, '123456789', 'creater');
INSERT INTO `Project_User` VALUES (7, 12, 'Anon', 'creater');

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role`  (
  `Role_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Role_Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Role_Type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ROLE_ROOT 管理员\r\nROLE_GOVER 政府\r\nROLE_ADMIN 企业管理员\r\nROLE_USER 企业用户',
  `Create_Time` timestamp(0) NULL DEFAULT NULL,
  `Description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该角色的描述',
  `Is_Userful` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  PRIMARY KEY (`Role_ID`) USING BTREE,
  INDEX `User_ID`(`User_ID`) USING BTREE,
  CONSTRAINT `Role_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Role
-- ----------------------------
INSERT INTO `Role` VALUES (6, 'mxx', '企业工作人员', 'ROLE_ADMIN', '2020-01-18 00:02:59', '企业数据管理与合作计算人员', 1);
INSERT INTO `Role` VALUES (7, 'Anon', '企业职员', 'ROLE_USER', '2020-01-18 03:55:52', '企业普通工作人员', 1);
INSERT INTO `Role` VALUES (8, 'admin1', '企业工作人员', 'ROLE_ADMIN', '2020-02-01 03:11:58', '企业数据管理与合作计算人员', 1);

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User`  (
  `ID` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `Password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `Portrait_ID` int(11) NULL DEFAULT NULL COMMENT '头像URL',
  `Name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `Tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `Email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `Company` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司[政府为政府单位名称]',
  `Type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份标签[政府|企业管理员|企业员工]',
  `Is_Userful` tinyint(1) NULL DEFAULT 1 COMMENT '是否被删除',
  `license_ID` int(11) NULL DEFAULT NULL COMMENT '文件ID[企业为营业执照|政府为组织机构代码证]',
  `Is_Pass` tinyint(1) NULL DEFAULT 0 COMMENT '是否通过验证',
  `Cer_Code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '认证code[企业为社会统一信用代码|政府为组织机构代码',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `File_ID`(`license_ID`) USING BTREE,
  INDEX `ID`(`ID`) USING BTREE,
  INDEX `Portrait_ID`(`Portrait_ID`) USING BTREE,
  CONSTRAINT `User_ibfk_1` FOREIGN KEY (`license_ID`) REFERENCES `File_Other` (`File_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `User_ibfk_2` FOREIGN KEY (`Portrait_ID`) REFERENCES `File_Other` (`File_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of User
-- ----------------------------
INSERT INTO `User` VALUES ('123456789', '$2a$10$CBgiREZGuy9onoo8QuB.ZebfbZBlYgQ1/1agXODk12hldAMWuiMTi', NULL, NULL, '13588581505', NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `User` VALUES ('admin1', '$2a$10$UAE1iqnKf4sMHtH.akMYfONeGxCurC6cc5fHcgCth6CoOw7LLN7fO', NULL, 'admin1', 'string', 'string', 'string', '企业管理员', NULL, 1, NULL, 'string');
INSERT INTO `User` VALUES ('Anon', '$2a$10$cYhuIpHbzChkwNaXo25Ar.6RPmMM7WNUrTx17EUhYF5HUfV/qZefe', NULL, 'sun mengda', '12345678900', 'string@qq.com', 'alibab', '公司', 1, NULL, NULL, NULL);
INSERT INTO `User` VALUES ('mxx', '$2a$10$Fce5Tmg4lerK75.fCgExE.Iuet7jQ3WRECcvoAnITkPrXfsgx/.mq', NULL, ',xx', '123456789', 'string@gamil.com', 'mosca', '企业', 1, NULL, NULL, NULL);
INSERT INTO `User` VALUES ('string', '$2a$10$CBgiREZGuy9onoo8QuB.ZebfbZBlYgQ1/1agXODk12hldAMWuiMTi', NULL, 'string', 'string', 'string', 'string', 'string', 1, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
