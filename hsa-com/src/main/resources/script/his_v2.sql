/*
 Navicat Premium Data Transfer

 Source Server         : his2.0测试
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 172.18.100.103:3306
 Source Schema         : his_v2

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 23/11/2020 15:07:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_advice
-- ----------------------------
DROP TABLE IF EXISTS `base_advice`;
CREATE TABLE `base_advice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱类别代码（YZLB）',
  `technology_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医技分类代码（动态取码表）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `out_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院执行科室编码（表base_dept）',
  `in_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊执行科室编码（表base_dept）',
  `biz_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '业务类别',
  `biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '业务类别代码（动态取码表）',
  `container_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '容器类型代码（RQ）',
  `specimen_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标本类型代码（BBLX）',
  `use_scope_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用范围（SYFW）',
  `dept_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用科室编码集合（多个用逗号隔开）',
  `doctor_level_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开嘱医生级别集合（多个用逗号）',
  `opeartion_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手术级别代码（SSJB）',
  `insure_list_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保目录标识代码（YBMLBS）',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别限制代码（XB）',
  `min_age` int(11) DEFAULT NULL COMMENT '最小年龄',
  `max_age` int(11) DEFAULT NULL COMMENT '最大年龄',
  `is_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否计费（SF）',
  `is_stop_same` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否停同类医嘱（SF）',
  `is_stop_same_not` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否停非同类医嘱（SF）',
  `is_stop_myself` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否停自身（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nadvice：医嘱\r\n\r\n表说明：\r\n                                -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_advice_detail
-- ----------------------------
DROP TABLE IF EXISTS `base_advice_detail`;
CREATE TABLE `base_advice_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `advice_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类别代码（XMLB）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `is_alone_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否数量独立计费（SF）',
  `is_frist_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否仅首次计费（SF）',
  `is_appoint_rate` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否指定频率（SF）',
  `rate_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱频率编码（表base_rate）',
  `insure_list_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保目录标识代码（YBMLBS）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nadvice：医嘱\r\ndetail：明细\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_assist
-- ----------------------------
DROP TABLE IF EXISTS `base_assist`;
CREATE TABLE `base_assist`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计费编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计费名称',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务类别代码（药品/材料/项目）（YWLB）',
  `biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务编码',
  `way_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计费方式代码（JFFS）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_first` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否首次：0否、1是（SF）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nassist：辅助计费表\r\ncalc：计算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_assist_detail
-- ----------------------------
DROP TABLE IF EXISTS `base_assist_detail`;
CREATE TABLE `base_assist_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `ac_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计费编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类别代码（XMLB）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `num` decimal(11, 4) DEFAULT NULL COMMENT '数量',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nassist：辅助计费明细表\r\ncalc：计算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_bed
-- ----------------------------
DROP TABLE IF EXISTS `base_bed`;
CREATE TABLE `base_bed`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码（KB）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '床位编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '床位名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '床位类型（CWLX）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '床位状态代码（CWZT）',
  `seq_no` int(11) NOT NULL AUTO_INCREMENT COMMENT '顺序号',
  `room_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '房间号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `seq_no`(`seq_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nbed：床位\r\n\r\n表说明：\r\n床' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_bed_item
-- ----------------------------
DROP TABLE IF EXISTS `base_bed_item`;
CREATE TABLE `base_bed_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `bed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '床位编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码（表base_item）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nbed：床位收费项目表\r\nitem：收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_dailyfirst_calc
-- ----------------------------
DROP TABLE IF EXISTS `base_dailyfirst_calc`;
CREATE TABLE `base_dailyfirst_calc`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `rate_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率编码（表base_rate）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `daily_first_num` int(11) DEFAULT NULL COMMENT '每日首次收费次数',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndailyfirst：每日首次计费表\r\ncalc：计算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_dept`;
CREATE TABLE `base_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室性质代码（KSXZ）',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国家编码（KB）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室名称',
  `ward_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病区编码（表base_ward）',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `up_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级科室编码（表base_dept）',
  `mg_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经管科室编码（JGKS）',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室介绍',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室位置',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type_identity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndept：科室\r\n\r\n表说明：\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_dept_drugstore
-- ----------------------------
DROP TABLE IF EXISTS `base_dept_drugstore`;
CREATE TABLE `base_dept_drugstore`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药房类别代码（YFLB）',
  `drugstore_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药房编码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndept：科室关联药房信息表\r\ndrugstore：药房' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_disease
-- ----------------------------
DROP TABLE IF EXISTS `base_disease`;
CREATE TABLE `base_disease`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '疾病分类代码（JBFL）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '疾病编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '疾病名称',
  `attach_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '附加编码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国家编码',
  `is_add` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否自增：0否、1是（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndisease：疾病\r\n\r\n表说明：\r\n                      ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_drug
-- ----------------------------
DROP TABLE IF EXISTS `base_drug`;
CREATE TABLE `base_drug`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `bfc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类编码（表base_finance_classify）',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品分类代码（YPFL）',
  `big_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品大类代码（YPDL）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品编码',
  `usual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '通用名',
  `good_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(11, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `out_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊单位代码（DW）',
  `in_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院单位代码（DW）',
  `rate_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率编码（表base_rate）',
  `price` decimal(11, 4) DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `last_buy_price` decimal(11, 4) DEFAULT NULL COMMENT '最近购进单价',
  `last_split_buy_price` decimal(11, 4) DEFAULT NULL COMMENT '最近拆零购进单价',
  `avg_buy_price` decimal(11, 4) DEFAULT NULL COMMENT '平均购进价',
  `avg_sell_price` decimal(11, 4) DEFAULT NULL COMMENT '平均零售价',
  `split_ratio` decimal(11, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_price` decimal(11, 4) DEFAULT NULL COMMENT '拆零单价',
  `is_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否门诊可用：0否、1是（SF）',
  `is_in` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否住院可用：0否、1是（SF）',
  `is_lvp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否大输液：0否、1是（SF）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保本位码编码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国家卫健委编码',
  `drug_remark` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '药品说明书',
  `drug_img_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品说明书图片路径',
  `max_dosage` decimal(11, 4) DEFAULT NULL COMMENT '最大剂量',
  `min_dosage` decimal(11, 4) DEFAULT NULL COMMENT '最小剂量',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别限制代码',
  `min_age` int(11) DEFAULT NULL COMMENT '最小年龄',
  `max_age` int(11) DEFAULT NULL COMMENT '最大年龄',
  `ddd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'DDD值（限定日剂量）',
  `durg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品级别代码（YPJB）',
  `ph_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '毒麻药品级别代码（DMTX）',
  `antibacterial_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '抗菌药品级别代码（KJYPJB）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_basic` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否基药：0否、1是（SF）',
  `basic_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基药代码（JY）',
  `dan` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批准文号',
  `ndan` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国药准字号',
  `prod_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产厂家编码（表base_product）',
  `usual_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '通用名拼音码',
  `usual_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '通用名五笔码',
  `good_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名拼音码',
  `good_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndrug：药品\r\n\r\n表说明：\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_finance_classify
-- ----------------------------
DROP TABLE IF EXISTS `base_finance_classify`;
CREATE TABLE `base_finance_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类名称',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院发票代码（FPGL）',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊发票代码（FPGL）',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级编码',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否末级：0否、1是（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nfinance：财务分类\r\nclassify：分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_item
-- ----------------------------
DROP TABLE IF EXISTS `base_item`;
CREATE TABLE `base_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国家编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目分类代码（XMFL）',
  `bfc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类编码（表base_finance_classify）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称',
  `abbr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目简称',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目规格',
  `price` decimal(11, 4) DEFAULT NULL COMMENT '项目单价',
  `one_price` decimal(11, 4) DEFAULT NULL COMMENT '一级价格',
  `two_price` decimal(11, 4) DEFAULT NULL COMMENT '二级价格',
  `three_price` decimal(11, 4) DEFAULT NULL COMMENT '三级价格',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `intension` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目内涵',
  `prompt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目提示',
  `except` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '除外内容',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否门诊可用：0否、1是（SF）',
  `is_in` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否住院可用：0否、1是（SF）',
  `is_cg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否可改价0否、1是（SF）（门诊直接划价收费用，如果直接划价收费选择的项目是可改价的，表格中价格一列是可以修改的）',
  `is_ms` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否医技可用：0否、1是（SF）',
  `is_supp_curtain` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否补记帐：0否、1是（SF）',
  `out_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊执行科室编码（表base_dept）',
  `in_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院执行科室编码（表base_dept）',
  `name_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称拼音码',
  `name_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称五笔码',
  `abbr_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '简称拼音码',
  `abbr_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '简称五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nitem：项目基础信息表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_material
-- ----------------------------
DROP TABLE IF EXISTS `base_material`;
CREATE TABLE `base_material`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '材料分类代码（CLFL）',
  `bfc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类编码（表base_finance_classify）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '材料编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '材料名称',
  `price` decimal(11, 4) DEFAULT NULL COMMENT '单价',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `last_buy_price` decimal(11, 4) DEFAULT NULL COMMENT '最近购进单价',
  `last_split_buy_price` decimal(11, 4) DEFAULT NULL COMMENT '最近拆零购进单价',
  `avg_buy_price` decimal(11, 4) DEFAULT NULL COMMENT '平均购进价',
  `avg_sell_price` decimal(11, 4) DEFAULT NULL COMMENT '平均零售价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_ratio` decimal(11, 4) DEFAULT NULL COMMENT '拆分比',
  `split_price` decimal(11, 4) DEFAULT NULL COMMENT '拆零单价',
  `is_supp_curtain` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否补记账：0否、1是（SF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '材料备注',
  `prod_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产厂家编码',
  `reg_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '注册证号',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nmaterial：材料\r\n\r\n表说明：\r\n                     ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_modify_trace
-- ----------------------------
DROP TABLE IF EXISTS `base_modify_trace`;
CREATE TABLE `base_modify_trace`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务ID',
  `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表名',
  `updt_conent` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  `updt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改人ID',
  `updt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改人姓名',
  `updt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_BMT_BIZ_ID`(`biz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nmodify：修改\r\ntrace：痕迹、记录\r\n                  ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_mris_classify
-- ----------------------------
DROP TABLE IF EXISTS `base_mris_classify`;
CREATE TABLE `base_mris_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mris_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案费用代码（BAFY）',
  `bfc_codes` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '财务分类编码集合（表base_finance_classify）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '25. 病案费用归类表（同步）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_nurse_order
-- ----------------------------
DROP TABLE IF EXISTS `base_nurse_order`;
CREATE TABLE `base_nurse_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '护理单编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '护理单名称',
  `is_assign` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否指定科室（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `dept_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室ID列表',
  `pg_size` int(11) DEFAULT NULL COMMENT '每页行数',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '护理单据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_nurse_tbhead
-- ----------------------------
DROP TABLE IF EXISTS `base_nurse_tbhead`;
CREATE TABLE `base_nurse_tbhead`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `bno_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '护理单编码',
  `seq_no` int(11) DEFAULT NULL COMMENT '列顺序号',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '上级编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '表头编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '表头名称',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目编码（19、护理记录明细表：扩展字段1-40）',
  `min_num` int(11) DEFAULT NULL COMMENT '最小数值（为数值型时使用）',
  `max_num` int(11) DEFAULT NULL COMMENT '最大数值（为数值型时使用）',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别限制代码',
  `date_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据类型代码（SJLX）',
  `data_width` int(11) DEFAULT NULL COMMENT '数据显示宽度',
  `data_length` int(11) DEFAULT NULL COMMENT '数据长度',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据来源方式代码（SJLYFS）',
  `source_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据来源方式值',
  `default_value` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '默认值',
  `is_up` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否上级标题（SF）',
  `is_sum` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否汇总',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '数据来源方式代码（SJLYFS）：0、手工录入，1、自定义下拉，2、护士SQL\r\n\r\n0、手工录入\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_order_receive
-- ----------------------------
DROP TABLE IF EXISTS `base_order_receive`;
CREATE TABLE `base_order_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单据编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单据名称',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `usage_codes` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法代码集合',
  `dept_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用科室ID集合',
  `is_lvp` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否大输液：0否、1是（SF）',
  `is_ph` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否特殊药品：0否、1是（SF）',
  `is_herb` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否中草药（SF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否出院带药（SF）',
  `is_emergency` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否紧急领药（SF）',
  `is_material` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否材料（SF）',
  `is_patient` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否按病人（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nbase：基础模块\r\norder：单据\r\nreceive：领药\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_order_rule
-- ----------------------------
DROP TABLE IF EXISTS `base_order_rule`;
CREATE TABLE `base_order_rule`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据类型代码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据名称',
  `format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生成格式',
  `curr_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前单据号',
  `prefix` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '前缀',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_continuity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否连续（业务事务）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_OR_HC_TC`(`hosp_code`, `type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\norder：单据\r\nrule：规则\r\n\r\n                     ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_outpt_exec
-- ----------------------------
DROP TABLE IF EXISTS `base_outpt_exec`;
CREATE TABLE `base_outpt_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `usage_codes` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用法代码列表（YF）',
  `dept_ids` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '科室ID列表',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行科室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '26. 门诊执行科室配置表-Y' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_preferential
-- ----------------------------
DROP TABLE IF EXISTS `base_preferential`;
CREATE TABLE `base_preferential`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠类别代码（YHLB）',
  `biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务编码',
  `pf_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠类型编码（base_preferential_type.code）',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊优惠方式代码（YHFS）',
  `out_scale` decimal(11, 2) DEFAULT NULL COMMENT '门诊优惠率',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院优惠方式代码（YHFS）',
  `in_scale` decimal(11, 2) DEFAULT NULL COMMENT '住院优惠率',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\npreferential：优惠\r\n\r\n表说明' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_preferential_type
-- ----------------------------
DROP TABLE IF EXISTS `base_preferential_type`;
CREATE TABLE `base_preferential_type`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠类型编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠类型名称',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\npreferential：优惠\r\n\r\n表说明' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_product
-- ----------------------------
DROP TABLE IF EXISTS `base_product`;
CREATE TABLE `base_product`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产企业编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产企业名称',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `fax` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '传真',
  `post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否，1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nproduct：生产企业信息表\r\n                    ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_rate
-- ----------------------------
DROP TABLE IF EXISTS `base_rate`;
CREATE TABLE `base_rate`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `daily_times` decimal(11, 4) DEFAULT NULL COMMENT '每日次数',
  `exec_interval` decimal(11, 4) DEFAULT 1.0000 COMMENT '执行周期',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `seq_no` int(11) DEFAULT NULL COMMENT '顺序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nrate：频率、频次\r\n\r\n表说明：\r\n                      ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_special_calc
-- ----------------------------
DROP TABLE IF EXISTS `base_special_calc`;
CREATE TABLE `base_special_calc`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `drug_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品编码（表base_drug）',
  `trunc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '取整方式代码（QZFS）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nspecial：特殊药品计费表\r\ncalc：计算\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_supplier
-- ----------------------------
DROP TABLE IF EXISTS `base_supplier`;
CREATE TABLE `base_supplier`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商类别代码（GYSLB）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商编码',
  `abbr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商简称',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商全称',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `fax` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '传真',
  `post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户银行',
  `account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户账号',
  `tax_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '纳税号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `abbr_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '简称拼音码',
  `abbr_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '简称五笔码',
  `name_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '全称拼音码',
  `name_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '全称五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nsupplier：供应商\r\n\r\n表说明：\r\n                    ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_window
-- ----------------------------
DROP TABLE IF EXISTS `base_window`;
CREATE TABLE `base_window`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '窗口编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '窗口名称',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nwindow：药房窗口\r\n\r\n表说明：\r\n                     ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_archive_logging
-- ----------------------------
DROP TABLE IF EXISTS `emr_archive_logging`;
CREATE TABLE `emr_archive_logging`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `archive_state` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `archive_time` datetime(0) DEFAULT NULL,
  `archive_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `archive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `archive_option` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '\r\n\r\n（住院病人表中新增字段）\r\n业务逻辑：\r\n1、归档       出院病人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_classify
-- ----------------------------
DROP TABLE IF EXISTS `emr_classify`;
CREATE TABLE `emr_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档名称',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档父编码',
  `page_print_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '页面打印格式编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文档类型代码(YWLX)',
  `doc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文档类别代码(WDLB)',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别限制代码',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '五笔码',
  `is_del_nullline` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否自动删除空行',
  `is_common` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否常用文档',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否末级文档',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否有效',
  `is_unique` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否唯一',
  `is_page_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否换页打印',
  `is_audit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否需要审核',
  `is_hosp` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否全院病历',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用科室id',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify：文档分类\r\n\r\n表说明：' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_classify_element
-- ----------------------------
DROP TABLE IF EXISTS `emr_classify_element`;
CREATE TABLE `emr_classify_element`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `classinfo_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档分类编码（emr_classify.code）',
  `element_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '元素编码（emr_element.code）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用科室id',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify：文档分类\r\nelement：元素\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_classify_template
-- ----------------------------
DROP TABLE IF EXISTS `emr_classify_template`;
CREATE TABLE `emr_classify_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `classify_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档分类编码（emr_classify.code）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用科室id',
  `template_html` blob NOT NULL COMMENT '模板文件',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify: 文档分类\r\ntemplate: ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_classify_template_copy1
-- ----------------------------
DROP TABLE IF EXISTS `emr_classify_template_copy1`;
CREATE TABLE `emr_classify_template_copy1`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `classify_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档分类编码（emr_classify.code）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用科室id',
  `template_html` blob NOT NULL COMMENT '模板文件',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify: 文档分类\r\ntemplate: ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_element
-- ----------------------------
DROP TABLE IF EXISTS `emr_element`;
CREATE TABLE `emr_element`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '父元素编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元素编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元素名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元素类型代码（YSLX）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '五笔码',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否末级元素',
  `is_require` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否必填',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否有效',
  `default_vlaue` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '默认值',
  `min_value` decimal(11, 4) DEFAULT NULL COMMENT '数值下限',
  `max_value` decimal(11, 4) DEFAULT NULL COMMENT '数值上限',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别限制代码',
  `min_age` int(11) DEFAULT NULL COMMENT '最小年龄',
  `max_age` int(11) DEFAULT NULL COMMENT '最大年龄',
  `patient_code_ref` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病人信息关联项代码（BRGLX）',
  `sys_code_ref` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '系统参数关联项(关联系统码表)',
  `sys_code_default` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '系统参数默认值',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient`;
CREATE TABLE `emr_patient`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '就诊id',
  `classify_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历模板ID（emr_classify_template.id）',
  `patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历记录ID（emr_patient_record.id）',
  `patient_html_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历内容ID（emr_patient_html.id）',
  `sc_patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '上次病历记录ID（emr_patient_record.id）',
  `sc_patient_html_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '上次病历内容ID（emr_patient_html.id）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用科室id',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送审状态代码（SHZT）',
  `review_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送审人id',
  `review_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送审人姓名',
  `review_time` datetime(0) DEFAULT NULL COMMENT '送审时间',
  `is_specify` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否指定审核人（SF）',
  `specify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '指定审核人id',
  `specify_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '指定审核人姓名',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实际审核人id',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实际审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '实际审核时间',
  `audit_option` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\npatient：病人\r\n\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_html
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_html`;
CREATE TABLE `emr_patient_html`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '就诊id',
  `patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历病人ID（emr_patient.id）',
  `classify_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历模板ID（emr_classify_template.id）',
  `patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历记录ID（emr_patient_record.id）',
  `html` blob COMMENT '病历内容',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送审状态代码（SHZT）',
  `review_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送审人id',
  `review_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送审人姓名',
  `review_time` datetime(0) DEFAULT NULL COMMENT '送审时间',
  `is_specify` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否指定审核人（SF）',
  `specify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '指定审核人id',
  `specify_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '指定审核人姓名',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实际审核人id',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实际审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '实际审核时间',
  `audit_option` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\npatient：病人\r\nsub：送审\r\nl' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_record
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_record`;
CREATE TABLE `emr_patient_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '就诊id',
  `patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历病人ID（emr_patient.id）',
  `classify_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历模板ID（emr_classify_template.id）',
  `patient_html_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历内容ID（emr_patient_html.id）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '垂直拆表后子表主键',
  `emr0201` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0301` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0104` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0105` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0106` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0107` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0202` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0108` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0103` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0102` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr04` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0401` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0402` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0403` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr05` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0101` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0109` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0110` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0111` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0112` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0113` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0114` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0115` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0116` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0117` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0118` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr06` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0601` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0602` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0603` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0604` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0119` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0120` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr07` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0701` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0702` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0703` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0704` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0302` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0303` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0304` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0404` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0405` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0121` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr08` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0801` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0802` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0803` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0804` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0805` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0806` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0807` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0808` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0809` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0810` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0811` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0812` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0501` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0502` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0406` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0407` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0122` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr0123` text CHARACTER SET utf8 COLLATE utf8_bin,
  `emr_json` json COMMENT '结构化数据（json格式）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\npatient：病人\r\n\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_record_1
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_record_1`;
CREATE TABLE `emr_patient_record_1`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `patient_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_record_2
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_record_2`;
CREATE TABLE `emr_patient_record_2`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `patient_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_record_3
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_record_3`;
CREATE TABLE `emr_patient_record_3`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `patient_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_print_setting
-- ----------------------------
DROP TABLE IF EXISTS `emr_print_setting`;
CREATE TABLE `emr_print_setting`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '格式编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '格式名称',
  `width` decimal(11, 4) DEFAULT NULL COMMENT '页宽',
  `height` decimal(11, 4) DEFAULT NULL COMMENT '页高',
  `margin_left` decimal(11, 4) DEFAULT NULL COMMENT '左边距',
  `margin_right` decimal(11, 4) DEFAULT NULL COMMENT '右边距',
  `margin_up` decimal(11, 4) DEFAULT NULL COMMENT '上边距',
  `margin_under` decimal(11, 4) DEFAULT NULL COMMENT '下边距',
  `is_infeed` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否横向（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nprint: 打印\r\nsetting：设置\r\n                                      -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advance_pay
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advance_pay`;
CREATE TABLE `inpt_advance_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `ap_order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '预交单号',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '预交金额',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否结算（SF）',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算id',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '冲红id',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日结缴款ID',
  `source_pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `cheque_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支票号码（支付方式为支票时：显示必填）',
  `service_price` decimal(14, 4) DEFAULT NULL COMMENT '手续费（支付方式为POS时：显示选填，默认0）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice`;
CREATE TABLE `inpt_advice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱单号',
  `iat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板ID',
  `iatd_group_no` int(11) DEFAULT NULL COMMENT '模板内组号',
  `iatd_group_seq_no` int(11) DEFAULT NULL COMMENT '模板组内序号',
  `iatd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板明细ID',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊/住院科室id',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开嘱科室ID',
  `group_no` int(11) DEFAULT NULL COMMENT '医嘱组号',
  `group_seq_no` int(11) DEFAULT NULL COMMENT '医嘱组内序号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱分类代码（YZFL）',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `start_exec_num` int(11) DEFAULT NULL COMMENT '开嘱当日执行次数',
  `end_exec_num` int(11) DEFAULT NULL COMMENT '停嘱当天执行次数',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱内容',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数量单位（DW）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `total_num` decimal(14, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总数量单位（DW）',
  `herb_num` decimal(14, 4) DEFAULT NULL COMMENT '中草药付（剂）数',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否阳性（SF）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药药房ID',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `plan_stop_time` datetime(0) DEFAULT NULL COMMENT '医嘱预停时间（长期）',
  `technology_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医技申请单号',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否交病人（SF）：临时医嘱',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第一执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第一执行人姓名',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第二执行人id',
  `second_exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第二执行人姓名',
  `long_start_time` datetime(0) DEFAULT NULL COMMENT '医嘱开始时间，长期医嘱生效时间',
  `last_exec_time` datetime(0) DEFAULT NULL COMMENT '最近执行时间',
  `teach_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '带教医生id',
  `teach_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '带教医生姓名',
  `is_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否核收（SF）',
  `check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱核收人ID',
  `check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱核收人姓名',
  `check_time` datetime(0) DEFAULT NULL COMMENT '医嘱核收时间',
  `is_stop_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否停嘱核收（SF）',
  `stop_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '停嘱医生ID',
  `stop_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '停嘱医生姓名',
  `stop_time` datetime(0) DEFAULT NULL COMMENT '停嘱时间',
  `stop_check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '停嘱核收人ID',
  `stop_check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '停嘱核收人姓名',
  `stop_check_time` datetime(0) DEFAULT NULL COMMENT '停嘱核收时间',
  `is_long` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否临嘱（SF）（0：长期，1：临时）',
  `is_stop` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否停嘱（SF）',
  `is_reject` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否拒收（SF）',
  `reject_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拒收备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人/开嘱医生ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人/开嘱医生姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建/录入时间',
  `skin_durg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品ID',
  `skin_phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品药房ID',
  `skin_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品单位代码（DW）',
  `source_ia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试来源ID',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否提交',
  `submit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交人ID',
  `submit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交人',
  `submit_time` datetime(0) DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_detail
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_detail`;
CREATE TABLE `inpt_advice_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `ia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院医嘱ID（inpt_advice.id）',
  `ba_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱目录ID（base_advice.id）',
  `ia_group_no` int(11) DEFAULT NULL COMMENT '医嘱组号',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '项目数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目数量单位（DW）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '项目单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '项目总金额',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源方式代码（FYLYFS）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '来源方式代码（FYLYFS）：6：医嘱 3：动静态计费 9：其他' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_exec
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_exec`;
CREATE TABLE `inpt_advice_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱ID',
  `advice_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开嘱科室ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `plan_exec_time` datetime(0) DEFAULT NULL COMMENT '计划执行时间',
  `exec_time` datetime(0) DEFAULT NULL COMMENT '实际执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第一执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第一执行人姓名',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第二执行人ID',
  `second_exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第二执行人姓名',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否阳性（SF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否打印（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医嘱执行记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_temp
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_temp`;
CREATE TABLE `inpt_advice_temp`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板类型代码（MBLX），0全院，1科室，2个人',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板使用科室ID（科室、个人）',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板使用科室名称（科室、个人）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板使用医生ID（个人）',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板使用医生名称（个人）',
  `herb_num` decimal(14, 4) DEFAULT NULL COMMENT '中草药付（剂）数',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院医嘱模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_temp_detail
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_temp_detail`;
CREATE TABLE `inpt_advice_temp_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `iat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板ID',
  `group_no` int(11) DEFAULT NULL COMMENT '模板组号',
  `group_seq_no` int(11) DEFAULT NULL COMMENT '模板组内序号',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(11, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(11, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(11, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `price` decimal(11, 4) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(11, 4) DEFAULT NULL COMMENT '总金额',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否交病人（SF），交病人默认为临嘱',
  `is_long` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否临嘱（SF）（0：长期，1：临时）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模版内容',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别代码（CFLB）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总数量单位（DW）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院处方模板明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_baby
-- ----------------------------
DROP TABLE IF EXISTS `inpt_baby`;
CREATE TABLE `inpt_baby`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别代码（XB）',
  `birth_time` datetime(0) DEFAULT NULL COMMENT '出生时间',
  `weight` decimal(14, 4) DEFAULT NULL COMMENT '出生体重（G）',
  `height` decimal(14, 4) DEFAULT NULL COMMENT '出生身高（CM）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出生时情况',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算类型代码（JSLX）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '结算类型代码：0：正常结算 1：中途结算 2：新生儿结算 3：其它结算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_bed_change
-- ----------------------------
DROP TABLE IF EXISTS `inpt_bed_change`;
CREATE TABLE `inpt_bed_change`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `change_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '异动类型代码（YDLX）',
  `before_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前床号ID',
  `before_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前床号名称',
  `after_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后床号ID',
  `after_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后床号名称',
  `before_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前科室ID',
  `before_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前科室名称',
  `after_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后科室ID',
  `after_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后科室名称',
  `before_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前病区ID',
  `before_ward_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前病区名称',
  `after_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后病区ID',
  `after_ward_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后病区名称',
  `before_zz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前主治医生ID',
  `before_zz_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前主治医生姓名',
  `after_zz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后主治医生ID',
  `after_zz_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后主治医生姓名',
  `before_jz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前经治医生ID',
  `before_jz_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前经治医生姓名',
  `after_jz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后经治医生ID',
  `after_jz_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后经治医生姓名',
  `before_zg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前主管医生ID',
  `before_zg_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换前主管医生姓名',
  `after_zg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后主管医生ID',
  `after_zg_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '换后主管医生姓名',
  `end_time` datetime(0) DEFAULT NULL COMMENT '终止时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '护理记录明细表\r\n\r\n\r\n异动类型代码：0、安床，1、换床，2、包床，3、:转科，4、释放' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_cost
-- ----------------------------
DROP TABLE IF EXISTS `inpt_cost`;
CREATE TABLE `inpt_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `iat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱ID',
  `iatd_group_no` int(11) DEFAULT NULL COMMENT '医嘱组号',
  `iatd_seq_no` int(11) DEFAULT NULL COMMENT '医嘱组内序号',
  `advice_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱执行签名ID（inpt_advice_exec.id）',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用来源方式代码（FYLYFS）',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用来源ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原费用ID',
  `source_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源科室ID',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊科室ID',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '速度代码（SD）',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总数量单位（DW）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_num` decimal(14, 4) DEFAULT NULL COMMENT '中草药付（剂）数',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '项目总金额',
  `preferential_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠总金额',
  `reality_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠后总金额',
  `back_num` decimal(14, 4) DEFAULT NULL COMMENT '退药数量',
  `back_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '退药状态代码（TYZT）:0、正常，1、已退费未退药，2、已退费已退药',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开嘱医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开嘱医生姓名',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开嘱科室ID',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `is_dist` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否已发药（SF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否交病人（SF）：临时医嘱',
  `is_ok` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否确费：0、未确认，1、已确认',
  `ok_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '确费人ID',
  `ok_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '确费人姓名',
  `ok_time` datetime(0) DEFAULT NULL COMMENT '确费时间',
  `settle_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '结算状态代码： 0未结算，1预结算，2已结算',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID（outpt_settle）',
  `is_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否核对（SF）',
  `check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用核对人ID',
  `check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用核对人姓名',
  `check_time` datetime(0) DEFAULT NULL COMMENT '费用核对时间',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主治医生名称',
  `jz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经治医生ID',
  `jz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经治医生名称',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主管医生名称',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人姓名',
  `exec_time` datetime(0) DEFAULT NULL COMMENT '执行时间',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `plan_exec_time` datetime(0) DEFAULT NULL COMMENT '计划执行时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `is_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否已记账（SF）',
  `cost_time` datetime(0) DEFAULT NULL COMMENT '计费时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院费用表\r\n费用来源方式代码：\r\n门诊使用：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试，5：皮试换药药品，\r\n住院使用：6、医嘱,，7、为长期记账；8、补记账\r\n9：其他费用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_cost_settle
-- ----------------------------
DROP TABLE IF EXISTS `inpt_cost_settle`;
CREATE TABLE `inpt_cost_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '婴儿ID',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '结算ID（inpt_settle.id）',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '费用ID（inpt_cost.id）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '1、费用来源方式代码：\r\n门诊使用：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试，5' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `inpt_diagnose`;
CREATE TABLE `inpt_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '疾病ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `is_main` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否主诊断（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院诊断表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_insure_pay
-- ----------------------------
DROP TABLE IF EXISTS `inpt_insure_pay`;
CREATE TABLE `inpt_insure_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '合同单位明细代码（HTDW）',
  `org_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保机构编码',
  `org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保机构名称',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '医保报销总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同单位明细代码：\r\n0、个人账户，1、基本医疗，2、补充医疗，3、民政，4、协议支付' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_long_cost
-- ----------------------------
DROP TABLE IF EXISTS `inpt_long_cost`;
CREATE TABLE `inpt_long_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室ID',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '项目总金额',
  `last_exec_time` datetime(0) DEFAULT NULL COMMENT '最近执行日期',
  `charge_time` datetime(0) DEFAULT NULL COMMENT '本次计费时间',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束日期',
  `source_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源类型代码（LYLX）',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源ID（床位ID）',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否取消（SF）',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '取消人ID',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '取消人姓名',
  `cancel_time` datetime(0) DEFAULT NULL COMMENT '取消时间',
  `cancel_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '取消备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '来源类型代码（LYLX）：0、床位，1、录入，2、包床\r\n\r\n录入：只能录入项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_nurse_record
-- ----------------------------
DROP TABLE IF EXISTS `inpt_nurse_record`;
CREATE TABLE `inpt_nurse_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `bno_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '护理单据ID（base_nursing_order.id）',
  `pg_index` int(11) DEFAULT NULL COMMENT '页码',
  `first_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第一签名护士ID',
  `first_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第一签名护士姓名',
  `second_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第二签名护士ID',
  `second_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第二签名护士姓名',
  `is_day_sum` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否日间小结（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `delete_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '删除人ID',
  `delete_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '删除人姓名',
  `delete_time` datetime(0) DEFAULT NULL COMMENT '删除时间',
  `day_sum_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日间小结人ID',
  `day_sum_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日间小结姓名',
  `day_sum_time` datetime(0) DEFAULT NULL COMMENT '日间小结时间',
  `teacher_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '带教护士ID',
  `teacher_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '带教护士姓名',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `item_001` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目001',
  `item_002` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目002',
  `item_003` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目003',
  `item_004` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目004',
  `item_005` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目005',
  `item_006` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目006',
  `item_007` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目007',
  `item_008` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目008',
  `item_009` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目009',
  `item_010` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目010',
  `item_011` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目011',
  `item_012` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目012',
  `item_013` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目013',
  `item_014` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目014',
  `item_015` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目015',
  `item_016` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目016',
  `item_017` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目017',
  `item_018` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目018',
  `item_019` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目019',
  `item_020` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目020',
  `item_021` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目021',
  `item_022` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目022',
  `item_023` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目023',
  `item_024` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目024',
  `item_025` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目025',
  `item_026` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目026',
  `item_027` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目027',
  `item_028` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目028',
  `item_029` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目029',
  `item_030` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目030',
  `item_031` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目031',
  `item_032` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目032',
  `item_033` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目033',
  `item_034` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目034',
  `item_035` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目035',
  `item_036` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目036',
  `item_037` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目037',
  `item_038` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目038',
  `item_039` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目039',
  `item_040` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目040'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '护理记录明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_nurse_template
-- ----------------------------
DROP TABLE IF EXISTS `inpt_nurse_template`;
CREATE TABLE `inpt_nurse_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板内容',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_nurse_third
-- ----------------------------
DROP TABLE IF EXISTS `inpt_nurse_third`;
CREATE TABLE `inpt_nurse_third`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `record_time` datetime(0) DEFAULT NULL COMMENT '护理记录时间',
  `in_num` int(11) DEFAULT NULL COMMENT '住院天数',
  `temperature_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '体温测量仪代码（TWCLY）',
  `temperature` decimal(14, 4) DEFAULT NULL COMMENT '体温',
  `temperature_retest` decimal(14, 4) DEFAULT NULL COMMENT '复测体温',
  `pulse` int(11) DEFAULT NULL COMMENT '脉搏',
  `heart_rate` int(11) DEFAULT NULL COMMENT '心率',
  `is_ventilator` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否使用呼吸机（SF）',
  `breath` int(11) DEFAULT NULL COMMENT '呼吸',
  `forty_up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '40°以上体温代码（FTTW）',
  `forty_up_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '40°以上体温备注',
  `thirty_five_down_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '35°以下体温代码（TFTW）',
  `thirty_five_down_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '35°以下体温备注',
  `intake` decimal(14, 4) DEFAULT NULL COMMENT '入量（ml）',
  `output` decimal(14, 4) DEFAULT NULL COMMENT '出量（ml）',
  `excrement_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '大便次数代码（XBCS）',
  `pee_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '小便次数代码（DBCS）',
  `height` decimal(14, 4) DEFAULT NULL COMMENT '身高（cm）',
  `weight` decimal(14, 4) DEFAULT NULL COMMENT '体重（kg）',
  `am_bp` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '上午血压（mmHg）',
  `pm_bp` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '下午血压（mmHg）',
  `is_operation` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否手术（SF）',
  `operation_days` int(11) DEFAULT NULL COMMENT '手术后天数',
  `operation_cnt` int(11) DEFAULT NULL COMMENT '手术次数',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '体温测量仪代码：0、腋表，1、口表，2、肛表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_adrs
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_adrs`;
CREATE TABLE `inpt_past_adrs`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品ID',
  `drug_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品名称',
  `adrs` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '不良反应症状',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发生原因',
  `serious` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '严重程度',
  `start_time` datetime(0) DEFAULT NULL COMMENT '发生日期',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_allergy
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_allergy`;
CREATE TABLE `inpt_past_allergy`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品ID',
  `drug_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品名称',
  `allergy` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '过敏症状',
  `serious` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '严重程度',
  `start_time` datetime(0) DEFAULT NULL COMMENT '发生日期',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_drug
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_drug`;
CREATE TABLE `inpt_past_drug`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品ID',
  `drug_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品名称',
  `start_time` datetime(0) DEFAULT NULL COMMENT '用药开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '用药结束时间',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '频率ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_operation
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_operation`;
CREATE TABLE `inpt_past_operation`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `chief_complaint` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主诉',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术ID（ICD-9）',
  `operation_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术医生ID',
  `operation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术医生名称',
  `operation_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_treat
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_treat`;
CREATE TABLE `inpt_past_treat`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `chief_complaint` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主诉',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊科室名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_pay
-- ----------------------------
DROP TABLE IF EXISTS `inpt_pay`;
CREATE TABLE `inpt_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '支付金额',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '票据号（微信条码号、支付宝条码号、支票号码）',
  `service_price` decimal(14, 4) DEFAULT NULL COMMENT '手续费（pos）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_settle
-- ----------------------------
DROP TABLE IF EXISTS `inpt_settle`;
CREATE TABLE `inpt_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `settle_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算类型代码（JSLX）',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束日期',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `reality_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠后总金额',
  `trunc_price` decimal(14, 4) DEFAULT NULL COMMENT '舍入金额（存在正负金额）',
  `actual_price` decimal(14, 4) DEFAULT NULL COMMENT '实收金额',
  `self_price` decimal(14, 4) DEFAULT NULL COMMENT '个人支付金额',
  `mi_price` decimal(14, 4) DEFAULT NULL COMMENT '统筹支付金额',
  `ap_total_price` decimal(14, 4) DEFAULT NULL COMMENT '预交金合计',
  `ap_offset_price` decimal(14, 4) DEFAULT NULL COMMENT '预交金冲抵',
  `settle_take_price` decimal(14, 4) DEFAULT NULL COMMENT '结算补收',
  `settle_back_price` decimal(14, 4) DEFAULT NULL COMMENT '结算退款',
  `settle_back_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算退款支付方式代码（ZFFS）',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否结算（SF）',
  `settle_time` datetime(0) DEFAULT NULL COMMENT '结算时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '冲红ID',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否打印（SF）',
  `hosp_df_price` decimal(14, 4) DEFAULT NULL COMMENT '医院垫付金额',
  `hosp_jm_price` decimal(14, 4) DEFAULT NULL COMMENT '医院减免金额',
  `out_settle_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院结算方式代码（CYJSFS）',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日结缴款ID',
  `source_pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '结算类型代码：0：正常结算 1：中途结算 2：新生儿结算 3：其它结算\r\n出院结算方式代码：0、正常结算，1、' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_settle_invoice
-- ----------------------------
DROP TABLE IF EXISTS `inpt_settle_invoice`;
CREATE TABLE `inpt_settle_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票ID',
  `invoice_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票明细ID',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票号码',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '发票总金额',
  `print_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票打印人ID',
  `print_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票打印人姓名',
  `print_time` datetime(0) DEFAULT NULL COMMENT '发票打印时间',
  `print_num` int(11) DEFAULT NULL COMMENT '发票打印次数',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '冲红ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_settle_invoice_content
-- ----------------------------
DROP TABLE IF EXISTS `inpt_settle_invoice_content`;
CREATE TABLE `inpt_settle_invoice_content`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算发票ID（outpt_settle_invoice）',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院发票代码',
  `in_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院发票名称',
  `reality_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠后总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_visit
-- ----------------------------
DROP TABLE IF EXISTS `inpt_visit`;
CREATE TABLE `inpt_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人档案ID',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病案号/住院档案号（入院登记回写）',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国籍代码（GJZD）',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职业代码（ZY）',
  `education_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学历代码（XL）',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人地址',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人电话',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '民族代码（MZ）',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '现住址',
  `preferential_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠类别ID',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `receive_hosp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '接收医院名称',
  `bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前床位ID',
  `bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前床位名称',
  `nursing_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '护理级别（医嘱回写）',
  `diet_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '膳食类型（医嘱回写）',
  `Illness_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病情标识（医嘱回写）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前状态代码（BRZT）',
  `in_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院病区ID',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院科室名称',
  `in_time` datetime(0) DEFAULT NULL COMMENT '入院时间',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主治医生名称',
  `jz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经治医生ID',
  `jz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经治医生名称',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主管医生名称',
  `in_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院备注',
  `in_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院方式代码（RYFS）',
  `in_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院主诊断ID（base_disease）',
  `in_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院主诊断名称（base_disease）',
  `in_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院主诊断ICD-10码（base_disease）',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入院情况代码（RYQK）',
  `outpt_visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊就诊号',
  `outpt_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊医生ID',
  `outpt_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊医生姓名',
  `outpt_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊主诊断ID',
  `outpt_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊主诊断名称',
  `outpt_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊主诊断ICD-10码',
  `out_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院病区ID',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院科室名称',
  `out_time` datetime(0) DEFAULT NULL COMMENT '出院时间',
  `out_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院主诊断ID',
  `out_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院主诊断名称',
  `out_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院主诊断ICD-10码',
  `out_oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院操作人ID',
  `out_oper_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院操作人姓名',
  `out_oper_time` datetime(0) DEFAULT NULL COMMENT '出院操作时间',
  `out_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院情况代码（CYQK）',
  `out_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院备注',
  `out_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出院方式代码（CYFS）',
  `is_archive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否归档（SF）',
  `archive_time` datetime(0) DEFAULT NULL COMMENT '归档时间',
  `archive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '归档人ID',
  `archive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '归档人姓名',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参保类型代码（CBLX）',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参保机构编码',
  `insure_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参保号',
  `insure_biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保业务类型编码',
  `insure_treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保待遇类型编码',
  `insure_patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保病人ID',
  `insure_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保合同单位备注',
  `total_advance` decimal(14, 4) DEFAULT NULL COMMENT '累计预交金',
  `total_cost` decimal(14, 4) DEFAULT NULL COMMENT '累计费用',
  `total_balance` decimal(14, 4) DEFAULT NULL COMMENT '累计余额',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '当前状态代码：\r\n0、待入（办理入院登记未安床）\r\n1、在院（护士安床后在院状态）\r\n2、' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_configuration
-- ----------------------------
DROP TABLE IF EXISTS `insure_configuration`;
CREATE TABLE `insure_configuration`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保机构编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保机构名称',
  `reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医疗机构编码',
  `attr_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保归属机构编码',
  `type_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保类型代码（YBLX）',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保服务请求地址路径',
  `request_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保请求方式代码（YBQQFS）',
  `time_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求超时时间',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `is_remote` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否异地（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nhosp：医院\r\nconfiguration：配置\r\n                                          -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_dict
-- ----------------------------
DROP TABLE IF EXISTS `insure_dict`;
CREATE TABLE `insure_dict`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类型编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典名',
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典值',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `ext01` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段1',
  `ext02` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段2',
  `ext03` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\ndict：字典\r\n\r\n表说明：\r\n用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_disease
-- ----------------------------
DROP TABLE IF EXISTS `insure_disease`;
CREATE TABLE `insure_disease`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保机构注册编码',
  `insure_illness_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中心疾病ID',
  `insure_illness_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中心疾病编码',
  `insure_illness_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中心疾病名称',
  `icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'ICD编码',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '五笔码',
  `take_date` datetime(0) DEFAULT NULL COMMENT '生效日期',
  `lose_date` datetime(0) DEFAULT NULL COMMENT '失效日期',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\ndiagnose：疾病\r\n\r\n表说明：\r\n                                   -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_disease_match
-- ----------------------------
DROP TABLE IF EXISTS `insure_disease_match`;
CREATE TABLE `insure_disease_match`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `hosp_illness_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院疾病ID',
  `hosp_illness_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院ICD编码',
  `hosp_illness_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院ICD名称',
  `insure_illness_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心疾病ID',
  `insure_illness_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心ICD编码',
  `insure_illness_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心ICD名称',
  `is_match` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否匹配（SF）',
  `is_trans` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否传输（SF）',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审批状态代码（SHZT）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\ndiagnose：疾病\r\nmatching：匹配\r\n                                         -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_function
-- ----------------------------
DROP TABLE IF EXISTS `insure_function`;
CREATE TABLE `insure_function`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `function_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '功能编码',
  `function_class` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '功能包地址（当前医保对应的医保功能号）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '功能号描述',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nfunction：功能号\r\n\r\n表说明：\r\n                                    -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_basic
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_basic`;
CREATE TABLE `insure_individual_basic`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `aac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人电脑号',
  `injury_borth_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人业务号(工伤、生育)',
  `aaa027` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分级统筹中心编码',
  `aaa027_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分级统筹中心名称',
  `aac003` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `aac004` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `aac002` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '公民身份号码',
  `aaz500` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '社会保障号码',
  `aae005` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `aac006` date DEFAULT NULL COMMENT '出生日期',
  `orgcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地区编码',
  `aab999` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位管理码',
  `aab019` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位类型',
  `aab001` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位编码',
  `bka008` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位名称',
  `bka035` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员类别编码',
  `bka035_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员类别名称',
  `aac008` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员状态编码',
  `aac008_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员状态名称',
  `bac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '公务员级别编码',
  `bac001_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '公务员级别名称',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `aka130_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型名称',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇类型',
  `bka006_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇类型名称',
  `aac148` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '补助类型',
  `aac148_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '补助类型名称',
  `aac013` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用工形式编码',
  `aac013_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用工形式名称',
  `aae140` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '险种类型（码表AAE140）',
  `bka888` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基金冻结状态',
  `akc252` decimal(14, 4) DEFAULT NULL COMMENT '个人帐户余额',
  `aac066` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参保身份',
  `aab301` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属行政区代码.常住地',
  `aac031` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员缴费状态{1参保缴费，2暂停缴费，3终止缴费}',
  `aae030_last` datetime(0) DEFAULT NULL COMMENT '上次住院入院日期',
  `aae031_last` datetime(0) DEFAULT NULL COMMENT '上次住院出院日期',
  `aae030_special` datetime(0) DEFAULT NULL COMMENT '特殊业务申请有效开始时间',
  `aae031_special` datetime(0) DEFAULT NULL COMMENT '特殊业务申请有效结束时间',
  `aaz267` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医疗待遇申请事件ID',
  `baa027` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参保地中心编码',
  `baa027_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参保地中心名称',
  `akc193` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病ICD编码',
  `akc193_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病ICD名称',
  `aac158` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '低保对象标识',
  `akc026` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参加公务员医疗补助标识',
  `baa301` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参保地行政区划代码(指参保人所在地的行政区划代码)',
  `aab300` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参保地社会保险经办机构名称(指参保人所在地的社会保险经办机构名称)',
  `akc009` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参保人员类别',
  `bka010` int(11) DEFAULT NULL COMMENT '本年住院次数',
  `bkh015` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '套餐标识',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nbasic：基本\r\n                                            -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_business
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_business`;
CREATE TABLE `insure_individual_business`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人基本信息id',
  `aaz267` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务申请序列号',
  `bear_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生育序列号',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `aka130_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务名称',
  `voip_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务认定编号',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇类型',
  `bka006_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇名称',
  `aka083` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '申请内容编码',
  `aka083_name` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '申请内容名称',
  `aae030` datetime(0) DEFAULT NULL COMMENT '申请生效日期',
  `aae031` datetime(0) DEFAULT NULL COMMENT '申请失效日期',
  `aka120` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '申请病种编码/疾病编码',
  `aka121` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '申请病种名称/疾病名称',
  `vulnerability` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '受伤部位',
  `pay_mark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '先行支付标志（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nbusiness：业务\r\n                                               ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_cost
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_cost`;
CREATE TABLE `insure_individual_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '费用id',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '结算id',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否住院（SF）',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '对应医保项目类别',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '对应医保项目编码',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '对应医保项目名称',
  `guest_ratio` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自付比例',
  `primary_price` decimal(14, 4) DEFAULT NULL COMMENT '原费用',
  `apply_last_price` decimal(14, 4) DEFAULT NULL COMMENT '报销后费用',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '顺序号（以患者为单位，生成费用顺序号）',
  `transmit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '传输标志（0：未传输、1：已传输）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：就诊\r\ncost：费用\r\n                                           -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_fund
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_fund`;
CREATE TABLE `insure_individual_fund`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人基本信息id',
  `fund_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基金编号',
  `fund_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基金名称',
  `indi_freeze_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基金状态标志（\"0\"——\"正常\" \r\n            \"1\"——\"冻结\"\r\n            \"2\"——\"暂停参保\" \"3\"——\"中止参保\" \"9\"—— \"未参保\"）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nfund：基金\r\n                                           -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_inpatient
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_inpatient`;
CREATE TABLE `insure_individual_inpatient`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人基本信息id',
  `akb020` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医疗机构编号',
  `aaa027` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中心编码',
  `akc190` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '住院号',
  `aae011` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登记人工号',
  `bka015` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登记人',
  `bka016` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登记标志',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `aae036` datetime(0) DEFAULT NULL COMMENT '业务登记日期',
  `aae030` datetime(0) DEFAULT NULL COMMENT '业务开始时间',
  `bka018` longtext CHARACTER SET utf8 COLLATE utf8_bin COMMENT '业务开始情况',
  `bkz101` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病名称',
  `akc193` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院疾病诊断（akc193码）',
  `akf001` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院科室',
  `bka020` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院科室名称',
  `bka021` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院病区',
  `bka021_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院病区名称',
  `ake0201` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院床位号',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nlastbizinfo：住' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_settle
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_settle`;
CREATE TABLE `insure_individual_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '结算id',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否住院（SF）',
  `visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊登记号',
  `discharge_dn_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院疾病诊断编码',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保机构编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `medicine_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医疗机构编码',
  `discharge_dn_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院疾病诊断名称',
  `discharged_date` datetime(0) DEFAULT NULL COMMENT '出院日期',
  `discharged_case` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院情况',
  `settleway` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式,01 普通结算,02 包干结算',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总费用',
  `plan_price` decimal(14, 4) DEFAULT NULL COMMENT '统筹基金支付',
  `serious_price` decimal(14, 4) DEFAULT NULL COMMENT '大病互助支付',
  `civil_price` decimal(14, 4) DEFAULT NULL COMMENT '公务员补助支付',
  `retire_price` decimal(14, 4) DEFAULT NULL COMMENT '离休基金支付',
  `personal_price` decimal(14, 4) DEFAULT NULL COMMENT '个人账户支付',
  `person_price` decimal(14, 4) DEFAULT NULL COMMENT '个人支付',
  `hosp_price` decimal(14, 4) DEFAULT NULL COMMENT '医院支付',
  `before_settle` decimal(14, 4) DEFAULT NULL COMMENT '结算前账户余额',
  `last_settle` decimal(14, 4) DEFAULT NULL COMMENT '结算后账户余额',
  `rests_price` decimal(14, 4) DEFAULT NULL COMMENT '其他支付',
  `assign_price` decimal(14, 4) DEFAULT NULL COMMENT '指定账户支付金额',
  `starting_price` decimal(14, 4) DEFAULT NULL COMMENT '起付线金额',
  `top_price` decimal(14, 4) DEFAULT NULL COMMENT '超封顶线金额',
  `plan_account_price` decimal(14, 4) DEFAULT NULL COMMENT '统筹段自负金额',
  `portion_price` decimal(14, 4) DEFAULT NULL COMMENT '部分自付金额',
  `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态标志,0正常，2冲红，1，被冲红',
  `settle_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保结算状态 0试算，1结算',
  `costbatch` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '费用批次',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇类型',
  `injury_borth_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务申请号,门诊特病，工伤，生育',
  `is_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '当前结算是否使用个人账户 0是，1否',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `ext01` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段1',
  `ext02` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段2',
  `ext03` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段4',
  `ext04` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段5',
  `ext05` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段6',
  `ext06` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段3',
  `ext07` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段7',
  `ext08` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段8',
  `ext09` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段9',
  `ext10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自定义字段10',
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：就诊\r\nsettle：结算\r\n                                             -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_stats
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_stats`;
CREATE TABLE `insure_individual_stats`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人基本信息id',
  `biz_year` int(11) DEFAULT NULL COMMENT '本年业务总次数',
  `drug_year` int(11) DEFAULT NULL COMMENT '本年购药次数',
  `diag_year` int(11) DEFAULT NULL COMMENT '本年门诊次数',
  `inhosp_year` int(11) DEFAULT NULL COMMENT '本年住院次数',
  `special_year` int(11) DEFAULT NULL COMMENT '本年门诊特殊病次数',
  `fee_year` decimal(14, 4) DEFAULT NULL COMMENT '本年总费用',
  `fund_year` decimal(14, 4) DEFAULT NULL COMMENT '本年统筹基金累计支出',
  `acct_year` decimal(14, 4) DEFAULT NULL COMMENT '本年个人帐户累计支出',
  `additional_year` decimal(14, 4) DEFAULT NULL COMMENT '本年大病互助金累计支出',
  `retire_year` decimal(14, 4) DEFAULT NULL COMMENT '本年离休基金累计支出',
  `official_year` decimal(14, 4) DEFAULT NULL COMMENT '本年公务员补助累计支出',
  `qfx_year` decimal(14, 4) DEFAULT NULL COMMENT '本年住院起付线支出',
  `declare_year` decimal(14, 4) DEFAULT NULL COMMENT '本年申报费用累计',
  `grzf_year` decimal(14, 4) DEFAULT NULL COMMENT '本年个人自付',
  `jmyw_year` decimal(14, 4) DEFAULT NULL COMMENT '本年居民意外伤害基金',
  `corp_add_year` decimal(14, 4) DEFAULT NULL COMMENT '本年企业补充支付',
  `month_diag_year` decimal(14, 4) DEFAULT NULL COMMENT '当前月份门诊公务员支付累积',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nfund：基金\r\n                                            -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_transfer
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_transfer`;
CREATE TABLE `insure_individual_transfer`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人基本信息id',
  `rela_hosp_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转院关联医院编号',
  `rela_aaz217` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转院关联就医登记号',
  `aaz267` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转院申请序列号',
  `bka068` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院标志',
  `akc200` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '本年度住院次数',
  `declare_year` decimal(14, 2) DEFAULT NULL COMMENT '本年住院申报累计金额',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\nmedical：医保\r\nIndividual：个人\r\ninpatient：住院' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_visit
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_visit`;
CREATE TABLE `insure_individual_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '患者就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人基本信息id',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保机构编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `medicine_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医疗机构编码',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否住院（SF）',
  `visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '住院号/就诊号',
  `aac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '个人电脑号',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保登记号',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `aka130_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型名称',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇类型',
  `bka006_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待遇类型名称',
  `injury_borth_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务申请号',
  `visit_icd_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊疾病编码',
  `visit_icd_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊疾病名称',
  `visit_time` datetime(0) DEFAULT NULL COMMENT '就诊时间',
  `visit_drpt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊科室ID',
  `visit_drpt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊科室名称',
  `visit_area_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊病区ID',
  `visit_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊病区名称',
  `visit_berth` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊床位',
  `starting_price` decimal(14, 4) DEFAULT NULL COMMENT '起付线金额',
  `shift_hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转入医院编码',
  `out_hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转出医院编码',
  `cause` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '住院原因',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nvisit：就诊\r\n                                            -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_item
-- ----------------------------
DROP TABLE IF EXISTS `insure_item`;
CREATE TABLE `insure_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `item_mark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目类别标志（11.西药、12.中成药、13.中草药、2.项目）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目编码',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目名称',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目类别',
  `item_dosage` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目剂型',
  `item_spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目规格',
  `item_price` decimal(14, 4) DEFAULT NULL COMMENT '医保中心项目价格',
  `item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目单位',
  `prod` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生产厂家',
  `deductible` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自费比例',
  `check_price` decimal(14, 4) DEFAULT NULL COMMENT '限价',
  `directory` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保目录标志（0.甲、1.乙、2.全自费）',
  `take_date` datetime(0) DEFAULT NULL COMMENT '生效日期',
  `lose_date` datetime(0) DEFAULT NULL COMMENT '失效日期',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nitem：项目\r\n\r\n表说明：\r\n用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_item_match
-- ----------------------------
DROP TABLE IF EXISTS `insure_item_match`;
CREATE TABLE `insure_item_match`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保注册编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目类别标志（11.西药、12.中成药、13.中草药、2.项目）',
  `molss_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人社部药品id',
  `pqcc_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '卫计委药品id',
  `hosp_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目id',
  `hosp_item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目名称',
  `hosp_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目编码',
  `hosp_item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目类别',
  `hosp_item_spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目规格',
  `hosp_item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目单位',
  `hosp_item_prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院项目剂型',
  `hosp_item_price` decimal(14, 4) DEFAULT NULL COMMENT '医院项目价格',
  `insure_item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目名称',
  `insure_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目编码',
  `insure_item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目类别',
  `insure_item_spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目规格',
  `insure_item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目单位',
  `insure_item_prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保中心项目剂型',
  `insure_item_price` decimal(14, 4) DEFAULT NULL COMMENT '医保中心项目价格',
  `deductible` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自费比例',
  `standard_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '本位码',
  `check_price` decimal(14, 4) DEFAULT NULL COMMENT '限价',
  `manufacturer` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生产厂家',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `is_match` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否匹配（SF）',
  `is_trans` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否传输（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `take_date` datetime(0) DEFAULT NULL COMMENT '生效日期',
  `lose_date` datetime(0) DEFAULT NULL COMMENT '失效日期',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '五笔码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\nitem：项目\r\nmatching：匹配\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_base_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_base_info`;
CREATE TABLE `mris_base_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案号',
  `in_blh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病理号',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '住院号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别代码（XB）',
  `gender_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别名称',
  `age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `age_unit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '年龄单位名称',
  `birthday` datetime(0) DEFAULT NULL COMMENT '出生日期',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型名称',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '国籍代码（GJ）',
  `nationality_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '国籍名称',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '民族代码（MZ）',
  `nation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '民族名称',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '籍贯',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '职业代码（ZY）',
  `occupation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '职业名称',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `marry_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '婚姻状况名称',
  `work` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '工作单位',
  `work_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位电话',
  `work_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位邮编',
  `work_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位地址',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_rela_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人关系名称',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人电话',
  `contact_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人邮编',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人地址',
  `now_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地（省）编码',
  `now_prov_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地（省）名称',
  `now_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地（市）编码',
  `now_city_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地（市）名称',
  `now_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地（区、县）编码',
  `now_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地（区、县）名称',
  `now_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '居住地邮编',
  `native_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地（省）编码',
  `native_prov_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地（省）名称',
  `native_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地（市）编码',
  `native_city_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地（市）名称',
  `native_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地（区、县）编码',
  `native_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地（区、县）名称',
  `native_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '户口地邮编',
  `in_cnt` int(11) DEFAULT NULL COMMENT '住院次数',
  `in_time` datetime(0) DEFAULT NULL COMMENT '入院时间',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院科室名称',
  `in_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院床位ID',
  `in_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院床位名称',
  `out_time` datetime(0) DEFAULT NULL COMMENT '出院时间',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院科室名称',
  `out_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院床位ID',
  `out_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院床位名称',
  `in_days` int(11) DEFAULT NULL COMMENT '住院天数',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病版本（疾病分类名称）',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病诊断ICD',
  `disease_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病诊断名称',
  `bl_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病理诊断ICD',
  `bl_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病理诊断名称',
  `director_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室主任ID',
  `director_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室主任姓名',
  `director_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室副主任ID',
  `director_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室副主任姓名',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主治医生姓名',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主管医生姓名',
  `zr_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '责任护士ID',
  `zr_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '责任护士姓名',
  `emr_quality_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历质量代码',
  `emr_quality_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历质量',
  `zk_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '质控医生ID',
  `zk_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '质控医生姓名',
  `zk_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '质控护士ID',
  `zk_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '质控护士姓名',
  `zk_time` datetime(0) DEFAULT NULL COMMENT '质控时间',
  `is_autopsy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否尸检（SF）编码',
  `is_autopsy_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否尸检（SF）名称',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '血型代码（XX）编码',
  `blood_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '血型名称',
  `rh_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'RH代码',
  `rh_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'RH名称',
  `baby_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '新生儿体重',
  `is_allergy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否药物过敏（SF）',
  `allergy_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '过敏药物合集',
  `out_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '离院方式代码（CYFS）',
  `out_mode_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '离院方式名称',
  `turn_org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转院机构名称',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院病情代码（RYQK）',
  `in_situation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院病情名称',
  `out_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院病情代码CYBQ）',
  `out_situation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出院病情名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_control
-- ----------------------------
DROP TABLE IF EXISTS `mris_control`;
CREATE TABLE `mris_control`  (
  `id` varchar(32) CHARACTER SET ujis COLLATE ujis_japanese_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `is_commit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否提交（SF）',
  `commit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '提交人ID',
  `commit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '提交人姓名',
  `commit_time` datetime(0) DEFAULT NULL COMMENT '提交时间',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否打印（SF）',
  `print_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '打印人ID',
  `print_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '打印人姓名',
  `print_time` datetime(0) DEFAULT NULL COMMENT '打印时间',
  `is_send` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否送档（SF）',
  `send_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送档人ID',
  `send_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送档人姓名',
  `send_time` datetime(0) DEFAULT NULL COMMENT '送档时间',
  `is_receive` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否接收（SF）',
  `receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接收人ID',
  `receive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接收人姓名',
  `receive_time` datetime(0) DEFAULT NULL COMMENT '接收时间',
  `borrow_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '借阅状态代码（JYZT）',
  `borrow_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '借阅人ID',
  `borrow_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '借阅人姓名',
  `borrow_time` datetime(0) DEFAULT NULL COMMENT '借阅时间',
  `back_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '归还人ID',
  `back_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '归还人姓名',
  `back_time` datetime(0) DEFAULT NULL COMMENT '归还时间',
  `is_copy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否复印（SF）',
  `copy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '复印人ID',
  `copy_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '复印人姓名',
  `copy_time` datetime(0) DEFAULT NULL COMMENT '复印时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '借阅状态代码（JYZT）：0、未借，1、借阅，2、归还' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_cost
-- ----------------------------
DROP TABLE IF EXISTS `mris_cost`;
CREATE TABLE `mris_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `fy01` decimal(14, 4) DEFAULT NULL COMMENT '总费用',
  `fy02` decimal(14, 4) DEFAULT NULL COMMENT '西药费',
  `fy03` decimal(14, 4) DEFAULT NULL COMMENT '中药费',
  `fy04` decimal(14, 4) DEFAULT NULL COMMENT '中成药费',
  `fy05` decimal(14, 4) DEFAULT NULL COMMENT '中草药费',
  `fy06` decimal(14, 4) DEFAULT NULL COMMENT '其他费用',
  `fy07` decimal(14, 4) DEFAULT NULL COMMENT '自费金额',
  `zhylfwl01` decimal(14, 4) DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费',
  `zhylfwl02` decimal(14, 4) DEFAULT NULL COMMENT '综合医疗服务类：（2）一般治疗操作费',
  `zhylfwl03` decimal(14, 4) DEFAULT NULL COMMENT '综合医疗服务类：（3）护理费',
  `zhylfwl04` decimal(14, 4) DEFAULT NULL COMMENT '综合医疗服务类：（4）其他费用',
  `zhylfwl05` decimal(14, 4) DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费（中医辨证论治费）',
  `zhylfwl06` decimal(14, 4) DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费（中医辨证论治会诊费）',
  `zdl01` decimal(14, 4) DEFAULT NULL COMMENT '诊断类：(5) 病理诊断费',
  `zdl02` decimal(14, 4) DEFAULT NULL COMMENT '诊断类：(6) 实验室诊断费',
  `zdl03` decimal(14, 4) DEFAULT NULL COMMENT '诊断类：(7) 影像学诊断费',
  `zdl04` decimal(14, 4) DEFAULT NULL COMMENT '诊断类：(8) 临床诊断项目费',
  `zll01` decimal(14, 4) DEFAULT NULL COMMENT '治疗类：(9) 非手术治疗项目费',
  `zll02` decimal(14, 4) DEFAULT NULL COMMENT '治疗类：非手术治疗项目费 其中临床物理治疗费',
  `zll03` decimal(14, 4) DEFAULT NULL COMMENT '治疗类：(10) 手术治疗费',
  `zll04` decimal(14, 4) DEFAULT NULL COMMENT '治疗类：手术治疗费 其中麻醉费',
  `zll05` decimal(14, 4) DEFAULT NULL COMMENT '治疗类：手术治疗费 其中手术费',
  `kfl01` decimal(14, 4) DEFAULT NULL COMMENT '康复类：(11) 康复费',
  `xyl01` decimal(14, 4) DEFAULT NULL COMMENT '西药类： 西药费 其中抗菌药物费用',
  `zyf01` decimal(14, 4) DEFAULT NULL COMMENT '中药费:医疗机构中药制剂费',
  `xyzpl01` decimal(14, 4) DEFAULT NULL COMMENT '血液和血液制品类： 血费',
  `xyzpl02` decimal(14, 4) DEFAULT NULL COMMENT '血液和血液制品类： 白蛋白类制品费',
  `xyzpl03` decimal(14, 4) DEFAULT NULL COMMENT '血液和血液制品类： 球蛋白制品费',
  `xyzpl04` decimal(14, 4) DEFAULT NULL COMMENT '血液和血液制品类：凝血因子类制品费',
  `xyzpl05` decimal(14, 4) DEFAULT NULL COMMENT '血液和血液制品类： 细胞因子类费',
  `hcl01` decimal(14, 4) DEFAULT NULL COMMENT '耗材类：检查用一次性医用材料费',
  `hcl02` decimal(14, 4) DEFAULT NULL COMMENT '耗材类：治疗用一次性医用材料费',
  `hcl03` decimal(14, 4) DEFAULT NULL COMMENT '耗材类：手术用一次性医用材料费',
  `zyl01` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医治疗类，中医类总费用',
  `zyl02` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医诊断费',
  `zyl03` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医外治费',
  `zyl04` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医骨伤',
  `zyl05` decimal(14, 4) DEFAULT NULL COMMENT '中医类：针刺与灸法',
  `zyl06` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医推拿治疗',
  `zyl07` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医肛肠治疗',
  `zyl08` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医特殊治疗',
  `zyl09` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中医其他',
  `zyl10` decimal(14, 4) DEFAULT NULL COMMENT '中医类：中药特殊调配加工',
  `zyl11` decimal(14, 4) DEFAULT NULL COMMENT '中医类：辩证施膳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `mris_diagnose`;
CREATE TABLE `mris_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `disease_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '诊断类型名称',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病ICD版本',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病ICD编码',
  `disease_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_oper_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_oper_info`;
CREATE TABLE `mris_oper_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `oper_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术疾病ID（base_disease）',
  `oper_disease_icd9` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术疾病编码ICD-9（base_disease）',
  `oper_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术疾病名称（base_disease）',
  `notched_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '切口代码（QK）',
  `notched_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '切口名称',
  `heal_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '愈合代码（YH）',
  `heal_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '愈合名称',
  `ana_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '麻醉方式代码（MZFS）',
  `ana_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '麻醉方式名称',
  `oper_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术医生ID',
  `oper_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术医生姓名',
  `oper_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术日期',
  `assistant_id4` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术一助ID',
  `assistant_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术一助姓名',
  `assistant_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术二助ID',
  `assistant_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术二助姓名',
  `assistant_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术三助ID',
  `assistant_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术三助姓名',
  `ana_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第一麻醉医生ID',
  `ana_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第一麻醉医生姓名',
  `ana_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第二麻醉医生ID',
  `ana_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第二麻醉医生姓名',
  `ana_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第三麻醉医生ID',
  `ana_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第三麻醉医生姓名',
  `oper_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术级别代码（SSJB）',
  `oper_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术级别名称',
  `oper_position` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术部位代码（SSBW）',
  `oper_position_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术部位名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_tcm_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_tcm_info`;
CREATE TABLE `mris_tcm_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '治疗类别代码（ZLLB）',
  `treat_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '治疗类别名称',
  `zzzyzj` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自制中药制剂代码（ZZZYZJ）',
  `zzzyzjmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '自制中药制剂名称',
  `zyryycyfh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中医入院与出院符合代码',
  `zyryycyfhmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中医入院与出院符合名称',
  `mjzzyzd` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '门（急）诊中医诊断编码',
  `mjzzyzdmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '门（急）诊中医诊断名称',
  `sslclj` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实施临床路径代码',
  `sslcljmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实施临床路径名称',
  `syyljgzyzj` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用医疗机构中药制剂代码',
  `syyljgzyzjmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用医疗机构中药制剂名称',
  `syzyzlsb` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用中医诊疗设备代码',
  `syzyzlsbmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用中医诊疗设备名称',
  `syzyzljs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用中医诊疗技术代码',
  `syzyzljsmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用中医诊疗技术名称',
  `bzsh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '辨证施护代码',
  `bzshmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '辨证施护名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_tumour_chemo
-- ----------------------------
DROP TABLE IF EXISTS `mris_tumour_chemo`;
CREATE TABLE `mris_tumour_chemo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `chemo_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗编号',
  `chemo_time` datetime(0) DEFAULT NULL COMMENT '化疗时间',
  `chemo_durg` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗药物及计量',
  `chemo_course` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗疗程',
  `effect_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疗效代码（LX）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_tumour_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_tumour_info`;
CREATE TABLE `mris_tumour_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `flfs_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '放疗方式代码（FLFS）',
  `flfs_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '放疗方式名称',
  `flcx_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '放疗程序代码（FLCX）',
  `flcx_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '放疗程序名称',
  `flzz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '放疗装置代码（FLZZ）',
  `flzz_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '放疗装置名称',
  `yfzjl` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '原发灶剂量',
  `yfzcs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '原发灶次数',
  `yfzts` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '原发灶天数',
  `yfzkssj` datetime(0) DEFAULT NULL COMMENT '原发灶开始日期',
  `yfzjssj` datetime(0) DEFAULT NULL COMMENT '原发灶结束时间',
  `qylbjjl` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '区域淋巴结剂量',
  `qylbjcs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '区域淋巴结次数',
  `qylbjts` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '区域淋巴结天数',
  `qylbjkssj` datetime(0) DEFAULT NULL COMMENT '区域淋巴结开始时间',
  `qylbjjssj` datetime(0) DEFAULT NULL COMMENT '区域淋巴结结束时间',
  `zyzmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转移灶名称',
  `zyzjl` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转移灶剂量',
  `zyzcs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转移灶次数',
  `zyzts` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转移灶天数',
  `zyzkssj` datetime(0) DEFAULT NULL COMMENT '转移灶开始时间',
  `zyzjssj` datetime(0) DEFAULT NULL COMMENT '转移灶结束时间',
  `hlfs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗方式代码（HLFS）',
  `hlfsmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗方式名称',
  `hlff` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗方法代码（HLFF）',
  `hlffmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '化疗方法名称',
  `zlfqlx` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '肿瘤分期类型代码（ZLFQ）',
  `fqt` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分期T',
  `fqn` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分期N',
  `fqm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分期M',
  `fq` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分期',
  `fqmh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分期编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_turn_dept
-- ----------------------------
DROP TABLE IF EXISTS `mris_turn_dept`;
CREATE TABLE `mris_turn_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转出科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转出科室名称',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转入科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '转入科室名称',
  `in_dept_time` datetime(0) DEFAULT NULL COMMENT '转科时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_info_record
-- ----------------------------
DROP TABLE IF EXISTS `oper_info_record`;
CREATE TABLE `oper_info_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '婴儿ID',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '住院号',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医嘱ID（inpt_advice.id）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '血型代码（XX）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊科室ID（住院科室）',
  `oper_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术科室ID',
  `bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '当前床位ID',
  `bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '当前床位名称',
  `oper_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术疾病ID（base_disease）',
  `oper_disease_icd9` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术疾病编码ICD-9（base_disease）',
  `oper_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术疾病名称（base_disease）',
  `in_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院主诊断ID（base_disease）',
  `in_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院主诊断名称（base_disease）',
  `in_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '入院主诊断ICD-10码（base_disease）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术主刀医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术主刀医生姓名',
  `assistant_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术一助ID',
  `assistant_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术一助姓名',
  `assistant_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术二助ID',
  `assistant_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术二助姓名',
  `assistant_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术三助ID',
  `assistant_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术三助姓名',
  `special_request` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术特殊要求',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术内容',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核医生ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核医生姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核备注',
  `oper_plan_time` datetime(0) DEFAULT NULL COMMENT '手术安排时间',
  `oper_room_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术室ID',
  `oper_room_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术室名称',
  `xh_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '巡回护士ID',
  `xh_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '巡回护士姓名',
  `qx_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '器械护士ID',
  `qx_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '器械护士姓名',
  `ana_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '麻醉方式代码（MZFS）',
  `ana_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第一麻醉医生ID',
  `ana_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第一麻醉医生姓名',
  `ana_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第二麻醉医生ID',
  `ana_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第二麻醉医生姓名',
  `ana_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第三麻醉医生ID',
  `ana_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '第三麻醉医生姓名',
  `oper_end_time` datetime(0) DEFAULT NULL COMMENT '手术完成时间',
  `notched_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '切口等级代码（QKDJ）',
  `heal_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '愈合情况代码（YHQK）',
  `oper_time_hour` decimal(14, 4) DEFAULT NULL COMMENT '手术耗时（按小时：1.5小时）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术状态代码（SSZT）',
  `is_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否计费（SF）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '计费总金额',
  `cost_time` datetime(0) DEFAULT NULL COMMENT '计费时间',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计费人ID',
  `cost_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计费人姓名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID/手术申请人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名/手术申请人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间/手术申请时间',
  `rank` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术级别',
  `ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病区ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '手术内容：拟 [今天上午 10-23 00:00] [手术申请测试01]\r\n手术状态标志代码：0、已申请，1、取消申请，3、已安排，4、取消安排，5、已完成' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_room
-- ----------------------------
DROP TABLE IF EXISTS `oper_room`;
CREATE TABLE `oper_room`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术室编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术室名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手术室地址',
  `is_use` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否在用（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily`;
CREATE TABLE `outin_daily`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '医院编码',
  `daily_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日结单号',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `total_price` decimal(14, 2) DEFAULT NULL COMMENT '总费用合计',
  `insure_total_price` decimal(14, 2) DEFAULT NULL COMMENT '合同单位总金额',
  `yjss_total_price` decimal(14, 2) DEFAULT NULL COMMENT '预交金实收总金额',
  `yjtk_total_price` decimal(14, 2) DEFAULT NULL COMMENT '预交金退款总金额',
  `yjjs_total_price` decimal(14, 2) DEFAULT NULL COMMENT '预交金结算总金额',
  `yjcd_total_price` decimal(14, 2) DEFAULT NULL COMMENT '预交金冲抵总金额',
  `back_total_price` decimal(14, 2) DEFAULT NULL COMMENT '退款总金额（减）',
  `preferential_total_price` decimal(14, 2) DEFAULT NULL COMMENT '优惠总金额（减）',
  `round_total_price` decimal(14, 2) DEFAULT NULL COMMENT '舍入总金额（减）',
  `reality_total_price` decimal(14, 2) DEFAULT NULL COMMENT '实缴总金额',
  `is_ok` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否缴款确认（SF）',
  `ok_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '确认缴款人ID',
  `ok_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '确认缴款人姓名',
  `ok_time` datetime(0) DEFAULT NULL COMMENT '确认缴款时间',
  `crte_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人/缴款人ID',
  `crte_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人/缴款人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建/缴款时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '缴款类型代码：0、门诊挂号，1、门诊收费，2、住院\r\n应缴总金额：费用合计-合同单位支付总金额' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_advance_pay
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_advance_pay`;
CREATE TABLE `outin_daily_advance_pay`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `pay_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `total_price` decimal(14, 2) DEFAULT NULL COMMENT '支付总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_finclassify
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_finclassify`;
CREATE TABLE `outin_daily_finclassify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '财务分类ID',
  `total_price` decimal(14, 2) DEFAULT NULL COMMENT '总费用合计',
  `preferential_total_price` decimal(14, 2) DEFAULT NULL COMMENT '优惠总金额',
  `reality_total_price` decimal(14, 2) DEFAULT NULL COMMENT '优惠后总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_insure
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_insure`;
CREATE TABLE `outin_daily_insure`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医保类型代码（YBLX）',
  `total_price` decimal(14, 2) DEFAULT NULL COMMENT '统筹支付总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_invoice
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_invoice`;
CREATE TABLE `outin_daily_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `num` int(11) DEFAULT NULL COMMENT '发票张数',
  `start_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发票起始号码',
  `end_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发票结束号码',
  `total_price` decimal(14, 2) DEFAULT NULL COMMENT '发票费用合计',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_pay
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_pay`;
CREATE TABLE `outin_daily_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `total_price` decimal(14, 2) DEFAULT NULL COMMENT '支付总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_invoice
-- ----------------------------
DROP TABLE IF EXISTS `outin_invoice`;
CREATE TABLE `outin_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领用人ID',
  `receive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领用人姓名',
  `receive_time` datetime(0) DEFAULT NULL COMMENT '领用时间',
  `use_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用人ID',
  `use_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用人姓名',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '票据类型代码',
  `prefix` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '票据号前缀',
  `start_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '起始号码',
  `end_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '终止号码',
  `curr_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前号码',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用状态代码',
  `num` int(11) DEFAULT NULL COMMENT '发票剩余数量',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票据领用信息表\r\n\r\n票据类型代码：0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_invoice_detail
-- ----------------------------
DROP TABLE IF EXISTS `outin_invoice_detail`;
CREATE TABLE `outin_invoice_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票ID（outin_invoice）',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票号码',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用状态代码',
  `use_time` datetime(0) DEFAULT NULL COMMENT '使用时间',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID（outpt_settle）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票据使用情况明细表\r\n\r\n使用状态代码：0、已使用，1、退领，2、作废' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classes
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classes`;
CREATE TABLE `outpt_classes`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '班次名称',
  `start_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '班次开始时间：时分秒',
  `end_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '班次结束时间：时分秒',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classes_doctor
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classes_doctor`;
CREATE TABLE `outpt_classes_doctor`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `cc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别排班ID',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生ID',
  `register_num` int(11) DEFAULT NULL COMMENT '限号数',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classes_queue
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classes_queue`;
CREATE TABLE `outpt_classes_queue`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `queue_date` date DEFAULT NULL COMMENT '队列日期',
  `cs_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '班次ID',
  `cc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别排班ID（outpt_classify_classes）',
  `cy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号类别ID（outpt_classify）',
  `triage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分诊台ID',
  `register_num` int(11) DEFAULT NULL COMMENT '限号数',
  `gen_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生成方式代码（SCFS）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号科室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生成方式代码：0、自动生成，1、手动生成' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classify
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classify`;
CREATE TABLE `outpt_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号科室ID',
  `visit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊类别代码（JZLB）',
  `register_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号类别代码（GHLB）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_expert` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否专家（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊类别，01：门诊 02 ：急诊 \r\n挂号类别代码：1、主任医师，2、主治医师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classify_classes
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classify_classes`;
CREATE TABLE `outpt_classify_classes`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `cs_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '班次ID',
  `cy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号类别ID（outpt_classify）',
  `triage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分诊台ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号科室ID',
  `weeks` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '星期',
  `register_num` int(11) DEFAULT NULL COMMENT '限号数',
  `is_call` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否叫号（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '星期，使用1....7 表示星期1-7\r\n分诊台ID：科室表中科室性质为分诊台的科室ID' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classify_cost
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classify_cost`;
CREATE TABLE `outpt_classify_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `cy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号类别ID（outpt_classify）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（项目/材料）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '项目数量',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_cost
-- ----------------------------
DROP TABLE IF EXISTS `outpt_cost`;
CREATE TABLE `outpt_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID（outpt_visit）',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方ID（outpt_prescribe）',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方明细id（outpt_prescribe_detail）',
  `ope_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方执行签名ID（outpt_prescribe_exec）',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用来源方式代码（FYLYFS）',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用来源ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原费用ID',
  `source_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源科室ID（直接划价来源收费室，划价收费来源开方科室）',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率ID',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_num` decimal(14, 4) DEFAULT NULL COMMENT '中草药付（剂）数',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '项目总金额',
  `preferential_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠总金额',
  `reality_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠后总金额',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `is_dist` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否已发药（SF）',
  `back_num` decimal(14, 4) DEFAULT NULL COMMENT '退药数量',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID（outpt_settle）',
  `settle_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算状态代码： 0未结算，1预结算，2已结算',
  `self_ratio` decimal(14, 4) DEFAULT NULL COMMENT '医保自费比例',
  `is_technology_ok` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否医技确费：0、未确认，1、已确认',
  `technology_ok_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医技确费人ID',
  `technology_ok_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医技确费人姓名',
  `technology_ok_time` datetime(0) DEFAULT NULL COMMENT '医技确费时间',
  `avg_buy_price` decimal(14, 4) DEFAULT NULL COMMENT '平均购进价（药品、材料中的平均购进价）',
  `avg_sell_price` decimal(14, 4) DEFAULT NULL COMMENT '平均零售价（药品、材料中的平均零售价）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方医生名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方科室ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '费用来源方式代码：\r\n门诊使用：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试，5：皮' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `outpt_diagnose`;
CREATE TABLE `outpt_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '疾病ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `is_main` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否主诊断（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_doctor_queue
-- ----------------------------
DROP TABLE IF EXISTS `outpt_doctor_queue`;
CREATE TABLE `outpt_doctor_queue`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `cq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '班次队列ID（outpt_classes_queue）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生姓名',
  `is_valid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  `register_num` int(11) DEFAULT NULL COMMENT '限号数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、加号处理 ：0 or null:可随意加号 1:必须挂号排班号后才能加号\r\n\r\n2、实际加号排序' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_doctor_register
-- ----------------------------
DROP TABLE IF EXISTS `outpt_doctor_register`;
CREATE TABLE `outpt_doctor_register`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生队列ID',
  `register_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '号源时间=号源时间/号源数',
  `start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '号源开始时间：1:00',
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '号源结束时间：1:30',
  `register_num` int(11) DEFAULT NULL COMMENT '分时段限号数',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人档案ID',
  `is_use` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否已用（SF）',
  `is_lock` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否锁号（SF）（第三方对接）',
  `is_add` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否加号（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、加号处理 ：0 or null:可随意加号 1:必须挂号排班号后才能加号\r\n\r\n2、实际加号排序' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_infusion_register
-- ----------------------------
DROP TABLE IF EXISTS `outpt_infusion_register`;
CREATE TABLE `outpt_infusion_register`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `exec_date` datetime(0) DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人姓名',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_insure_pay
-- ----------------------------
DROP TABLE IF EXISTS `outpt_insure_pay`;
CREATE TABLE `outpt_insure_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '合同单位明细代码（HTDW）',
  `org_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保机构编码',
  `org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保机构名称',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '医保报销总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同单位明细代码：\r\n0、个人账户，1、基本医疗，2、补充医疗，3、民政，4、协议支付' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_medical_record
-- ----------------------------
DROP TABLE IF EXISTS `outpt_medical_record`;
CREATE TABLE `outpt_medical_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人档案ID',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '疾病ID',
  `start_date` datetime(0) DEFAULT NULL COMMENT '发病日期',
  `chief_complaint` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主诉',
  `present_illness` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '现病史',
  `past_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '既往史',
  `oneself_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人史',
  `family_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '家族史',
  `allergy_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '过敏史',
  `vaccination_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '预防接种史',
  `auxiliary_inspect` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '辅助检查',
  `disease_analysis` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病种分析',
  `handle_suggestion` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处理意见',
  `temperature` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '体温',
  `min_blood_pressure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最低血压',
  `max_blood_pressure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最高血压',
  `breath` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '呼吸',
  `height` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身高',
  `blood_sugar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '血糖',
  `pulse` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '脉搏',
  `weight` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '体重',
  `bmi` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'BMI(计算公式:体重（kg）÷身高(米)^2)',
  `entrust` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '嘱托',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_pay
-- ----------------------------
DROP TABLE IF EXISTS `outpt_pay`;
CREATE TABLE `outpt_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '支付金额',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '票据号（微信条码号、支付宝条码号、支票号码）',
  `service_price` decimal(14, 4) DEFAULT NULL COMMENT '手续费（pos）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe`;
CREATE TABLE `outpt_prescribe`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `diagnose_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '诊断ID集合（多个用逗号分开）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方单号',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方医生名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开方科室名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方类别代码（CFLB）',
  `prescribe_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方类型代码（CFLX）',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否结算（SF）',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否作废（SF）',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否打印（SF）',
  `is_herb_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药是否本院煎药（SF）(执行次数)',
  `herb_num` decimal(11, 4) DEFAULT NULL COMMENT '中草药付（剂）数 (天数)',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `weight` decimal(11, 4) DEFAULT NULL COMMENT '体重（儿科）',
  `agent_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '代办人姓名（精麻）',
  `agent_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '代办人身份编号（精麻）',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作废人ID',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作废人',
  `cancel_date` datetime(0) DEFAULT NULL COMMENT '作废时间',
  `cancel_reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作废原因',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间（开方日期）',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否提交',
  `submit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交人ID',
  `submit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交人',
  `submit_time` datetime(0) DEFAULT NULL COMMENT '提交时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '处方类别：1、西药，2、材料，3、中草药，4、检验LIS，5、检查PACS，6、处置\r\n处方类型：1、普通，2' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_detail
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_detail`;
CREATE TABLE `outpt_prescribe_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `opt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方模板ID',
  `optd_group_no` int(11) DEFAULT NULL COMMENT '处方模板内组号',
  `optd_group_seq_no` int(11) DEFAULT NULL COMMENT '处方模板组内序号',
  `optd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方模板明细ID',
  `group_no` int(11) DEFAULT NULL COMMENT '处方组号',
  `group_seq_no` int(11) DEFAULT NULL COMMENT '处方组内序号',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '速度代码（SD）',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否皮试（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否阳性（SF）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方内容',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药药房ID',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `exec_date` datetime(0) DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人姓名',
  `exec_num` int(11) DEFAULT NULL COMMENT '本院执行次数',
  `technology_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医技申请单号',
  `skin_durg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品ID',
  `skin_phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品药房ID',
  `skin_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品单位代码（DW）'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '处方明细(lc_cf02)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_detail_ext
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_detail_ext`;
CREATE TABLE `outpt_prescribe_detail_ext`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '就诊ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方明细ID',
  `group_no` int(11) DEFAULT NULL COMMENT '处方组号',
  `group_seq_no` int(11) DEFAULT NULL COMMENT '处方组内序号',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医嘱目录ID（base_advice.id）',
  `self_ratio` decimal(14, 4) DEFAULT NULL COMMENT '医保自费比例',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目ID（药品、项目、材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称（药品、项目、材料）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '频率ID',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方内容',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '领药药房ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用药性质代码（YYXZ）'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_exec
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_exec`;
CREATE TABLE `outpt_prescribe_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `plan_exec_date` datetime(0) DEFAULT NULL COMMENT '计划执行时间',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人姓名',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `exec_time` datetime(0) DEFAULT NULL COMMENT '执行时间',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第二执行人ID',
  `second_exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第二执行人姓名',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否打印（SF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_temp
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_temp`;
CREATE TABLE `outpt_prescribe_temp`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板类型代码（MBLX），0全院，1科室，2个人',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板使用科室ID（科室、个人）',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板使用科室名称（科室、个人）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板使用医生ID（个人）',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板使用医生名称（个人）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '五笔码',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_temp_detail
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_temp_detail`;
CREATE TABLE `outpt_prescribe_temp_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `opt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方模板ID',
  `group_no` int(11) DEFAULT NULL COMMENT '处方模板组号',
  `group_seq_no` int(11) DEFAULT NULL COMMENT '处方模板组内序号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方类别代码（CFLB）',
  `prescribe_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方类型代码（CFLX）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称（药品、项目、材料、医嘱目录）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '财务分类ID',
  `use_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行科室ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方内容'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '处方模板明细表(lc_mb02)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_profile_file
-- ----------------------------
DROP TABLE IF EXISTS `outpt_profile_file`;
CREATE TABLE `outpt_profile_file`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '住院档案号（入院登记回写）',
  `out_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊档案号（挂号时生成）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '民族代码（MZ）',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国籍代码（GJ）',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '籍贯',
  `education_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学历代码（XL）',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职业代码（ZY）',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `often_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '常住类型（CZLX）（1 户籍，2非户籍）',
  `now_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '居住地（省）',
  `now_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '居住地（市）',
  `now_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '居住地（区、县）',
  `now_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '居住地邮编',
  `native_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户口地（省）',
  `native_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户口地（市）',
  `native_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户口地（区、县）',
  `native_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户口地邮编',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人电话',
  `contact_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人邮编',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人地址',
  `take_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '监护人姓名',
  `take_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '监护人关系（RYGX）',
  `take_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '监护人证件号码',
  `take_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '监护人联系电话',
  `work` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '工作单位',
  `work_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位电话',
  `work_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位邮编',
  `work_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位地址',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '血型代码（XX）',
  `family_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '家族史',
  `expose_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '暴露史',
  `heredity_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '遗传病史',
  `allergy_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '过敏史',
  `past_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '既往史疾病',
  `past_oper` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '既往史手术',
  `past_trauma` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '既往史外伤',
  `past_blood` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '既往史输血',
  `past_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '既往史描述',
  `allergy_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '过敏史描述',
  `source_tj_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人来源途径代码（LYTJ）',
  `source_tj_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人来源途径备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `total_out` int(11) DEFAULT NULL COMMENT '累计门诊次数',
  `total_in` int(11) DEFAULT NULL COMMENT '累计住院次数',
  `last_visit_time` datetime(0) DEFAULT NULL COMMENT '最后就诊时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、发卡状态 （0未发卡；1已发卡）\r\n2、注册方式:0网上注册；1身份证注册）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register`;
CREATE TABLE `outpt_register`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `register_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号单号（单据生成规则表）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别（XB）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型代码',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `source_bz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源标志代码（LYBZ）',
  `visit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊类别代码（JZLB）',
  `source_tj_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人来源途径代码（LYTJ）',
  `source_tj_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人来源途径备注',
  `register_time` datetime(0) DEFAULT NULL COMMENT '挂号时间',
  `cf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号类别ID',
  `cq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '坐诊班次ID',
  `dq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生队列ID',
  `dr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生号源明细ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号科室名称',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号医生姓名',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否作废（SF）',
  `is_first_visit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否初诊（SF）',
  `is_add` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否加号（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊类别:01 门诊 02 急诊 参照码表：ZQ\r\n\r\n状态标志:            0：正常 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register_detail
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register_detail`;
CREATE TABLE `outpt_register_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（项目/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（项目/材料）',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '财务分类ID',
  `price` decimal(11, 4) DEFAULT NULL COMMENT '项目单价',
  `num` decimal(11, 4) DEFAULT NULL COMMENT '项目数量',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `preferential_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠配置ID',
  `total_price` decimal(11, 4) DEFAULT NULL COMMENT '项目总金额',
  `preferential_price` decimal(11, 4) DEFAULT NULL COMMENT '优惠总金额',
  `reality_price` decimal(11, 4) DEFAULT NULL COMMENT '实收总金额',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `origin_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原费用明细ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '状态标志:0：正常 1：被冲红 2：冲红' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register_pay
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register_pay`;
CREATE TABLE `outpt_register_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `rs_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '票据号（微信条码号、支付宝条码号、支票号码）',
  `price` decimal(11, 4) DEFAULT NULL COMMENT '支付金额',
  `service_price` decimal(11, 4) DEFAULT NULL COMMENT '手续费（针对POS机）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register_settle
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register_settle`;
CREATE TABLE `outpt_register_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号ID',
  `total_price` decimal(11, 4) DEFAULT NULL COMMENT '挂号总金额',
  `preferential_price` decimal(11, 4) DEFAULT NULL COMMENT '优惠总金额',
  `reality_price` decimal(11, 4) DEFAULT NULL COMMENT '实收总金额',
  `settle_time` datetime(0) DEFAULT NULL COMMENT '结算时间',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日结缴款ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `origin_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原结算ID',
  `bill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票段ID',
  `bill_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '票据号码',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付方式代码（ZFFS，第三方对接）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊类别:01 门诊 02 急诊 参照码表：ZQ\r\n\r\n状态标志:            0：正常 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_settle
-- ----------------------------
DROP TABLE IF EXISTS `outpt_settle`;
CREATE TABLE `outpt_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `settle_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算单号',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `settle_time` datetime(0) DEFAULT NULL COMMENT '结算时间',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `reality_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠后总金额',
  `trunc_price` decimal(14, 4) DEFAULT NULL COMMENT '舍入金额（存在正负金额）',
  `actual_price` decimal(14, 4) DEFAULT NULL COMMENT '实收金额',
  `self_price` decimal(14, 4) DEFAULT NULL COMMENT '个人支付金额',
  `mi_price` decimal(14, 4) DEFAULT NULL COMMENT '统筹支付金额',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否结算（SF）',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日结缴款ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '冲红ID',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否打印（SF）',
  `old_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原结算ID',
  `is_print_list` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否打印清单（SF）',
  `print_list_time` datetime(0) DEFAULT NULL COMMENT '清单打印时间',
  `source_pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '舍入方式：\r\n0、不处理\r\n1、按角4舍5入：1.15块=1.2块\r\n2、按角舍 ：1.1' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_settle_invoice
-- ----------------------------
DROP TABLE IF EXISTS `outpt_settle_invoice`;
CREATE TABLE `outpt_settle_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票ID',
  `invoice_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票明细ID',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票号码',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '发票总金额',
  `print_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票打印人ID',
  `print_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发票打印人姓名',
  `print_time` datetime(0) DEFAULT NULL COMMENT '发票打印时间',
  `print_num` int(11) DEFAULT NULL COMMENT '发票打印次数',
  `is_main` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否主单（SF）',
  `main_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主单发票ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '冲红ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '结算票据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_settle_invoice_content
-- ----------------------------
DROP TABLE IF EXISTS `outpt_settle_invoice_content`;
CREATE TABLE `outpt_settle_invoice_content`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `settle_invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算发票ID（outpt_settle_invoice）',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊发票代码',
  `out_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊发票名称',
  `reality_price` decimal(14, 4) DEFAULT NULL COMMENT '优惠后总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票据内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_skin_result
-- ----------------------------
DROP TABLE IF EXISTS `outpt_skin_result`;
CREATE TABLE `outpt_skin_result`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `skin_durg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品ID',
  `skin_durg_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试药品名称',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否阳性（SF）',
  `skin_result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '皮试结果代码（PSJG）',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行科室ID',
  `exec_date` datetime(0) DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行人姓名',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_visit
-- ----------------------------
DROP TABLE IF EXISTS `outpt_visit`;
CREATE TABLE `outpt_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人档案ID',
  `out_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门诊档案号（挂号时生成）',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号ID',
  `register_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '挂号单号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '民族代码（MZ）',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话号码',
  `visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊号（根据单据规则生成）',
  `visit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊类别代码（JZLB）',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `preferential_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠类别ID',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参保类型代码（CBLX）',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参保机构编码',
  `insure_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参保号',
  `insure_biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保业务类型编码',
  `insure_treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保待遇类型编码',
  `insure_patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保病人ID',
  `insure_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医保合同单位备注',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊医生名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊科室名称',
  `visit_time` datetime(0) DEFAULT NULL COMMENT '就诊时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_visit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否就诊（SF）',
  `is_first_visit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否复诊（SF）',
  `tran_in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '转入院代码（ZRY）：0、未开住院证，1、已开住院证、2、已入院登记',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '建议入院科室ID',
  `in_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '建议入院诊断ID',
  `in_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '建议入院备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_apply
-- ----------------------------
DROP TABLE IF EXISTS `phar_apply`;
CREATE TABLE `phar_apply`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `in_stro_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入库库位ID',
  `out_stro_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出库库位ID',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `audit_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\napply：申领（申请领取）\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_apply_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_apply_detail`;
CREATE TABLE `phar_apply_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `apply_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申领ID',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产企业ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\napply：申领（申请领取）\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_distribute
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_distribute`;
CREATE TABLE `phar_in_distribute`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药窗口ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `order_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据类型代码（DJLX）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) DEFAULT NULL COMMENT '发药时间',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申请科室ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志（ZTZT）：0、正常，1、被冲红，2、冲红',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\ndistribut' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_distribute_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_distribute_detail`;
CREATE TABLE `phar_in_distribute_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `distribute_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药ID',
  `ir_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药申请ID',
  `ird_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药申请明细ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `stock_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库存明细ID',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `up_batch_surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '上期批号结余数量',
  `batch_surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '本期批号结余数量',
  `up_buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '上期购进总金额',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '本期购进总金额',
  `up_sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '上期零售总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '本期零售总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\ndistribut' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_receive
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_receive`;
CREATE TABLE `phar_in_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药窗口ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `order_receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药单据类型ID（base_order_receive.id）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) DEFAULT NULL COMMENT '发药时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药状态代码（LYZT）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申请科室ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\nreceive：领' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_receive_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_receive_detail`;
CREATE TABLE `phar_in_receive_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `wr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '待领ID',
  `receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药申请ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\nreceive：领' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_wait_receive
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_wait_receive`;
CREATE TABLE `phar_in_wait_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) DEFAULT NULL COMMENT '中药付数',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药状态代码（LYZT）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) DEFAULT NULL COMMENT '发药时间',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申请科室ID',
  `is_emergency` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否紧急（SF）',
  `is_back` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否退药（SF）',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用明细ID',
  `old_wr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原待领ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\nwait：等待\r\n                                         ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_distribute
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_distribute`;
CREATE TABLE `phar_out_distribute`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药窗口ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) DEFAULT NULL COMMENT '发药时间',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申请科室ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态标志（ZTZT）：0、正常，1、被冲红，2、冲红',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\ndistribu' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_distribute_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_distribute_detail`;
CREATE TABLE `phar_out_distribute_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `distribute_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方明细ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原费用ID',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `stock_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库存明细ID',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `back_num` decimal(14, 4) DEFAULT NULL COMMENT '当前退药数量',
  `total_back_num` decimal(14, 4) UNSIGNED ZEROFILL DEFAULT NULL COMMENT '累计退药数量',
  `up_batch_surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '上期批号结余数量',
  `batch_surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '本期批号结余数量',
  `up_buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '上期购进总金额',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '本期购进总金额',
  `up_sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '上期零售总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '本期零售总金额',
  `old_dist_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原发药明细ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '退药状态代码（TYZT）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\ndistribu' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_receive
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_receive`;
CREATE TABLE `phar_out_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '就诊ID',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结算ID',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药状态代码（LYZT）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药窗口ID',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) DEFAULT NULL COMMENT '发药时间',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申请科室ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\nreceive：' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_receive_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_receive_detail`;
CREATE TABLE `phar_out_receive_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `or_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '领药申请ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处方明细ID',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '费用ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `total_price` decimal(14, 4) DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\nreceive：' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_adjust
-- ----------------------------
DROP TABLE IF EXISTS `stro_adjust`;
CREATE TABLE `stro_adjust`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `before_price` decimal(14, 4) DEFAULT NULL COMMENT '调前总金额',
  `after_price` decimal(14, 4) DEFAULT NULL COMMENT '调后总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_adjust_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_adjust_detail`;
CREATE TABLE `stro_adjust_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医院编码',
  `adjust_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '调价ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位代码（DW）',
  `before_price` decimal(14, 4) DEFAULT NULL COMMENT '调前零售单价',
  `after_price` decimal(14, 4) DEFAULT NULL COMMENT '调后零售单价',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_buy_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零购进单价',
  `split_before_price` decimal(14, 4) DEFAULT NULL COMMENT '调前拆零零售单价',
  `split_after_price` decimal(14, 4) DEFAULT NULL COMMENT '调后拆零零售单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                       -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_in
-- ----------------------------
DROP TABLE IF EXISTS `stro_in`;
CREATE TABLE `stro_in`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入库方式代码（CRFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `stock_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库位ID',
  `supplier_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商ID',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nin：入库\r\n\r\n                            -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_in_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_in_detail`;
CREATE TABLE `stro_in_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `in_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入库ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nin：入库\r\ndet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_incdec
-- ----------------------------
DROP TABLE IF EXISTS `stro_incdec`;
CREATE TABLE `stro_incdec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `before_price` decimal(14, 4) DEFAULT NULL COMMENT '损益前总金额',
  `after_price` decimal(14, 4) DEFAULT NULL COMMENT '损益后总金额',
  `price` decimal(14, 4) DEFAULT NULL COMMENT '损益总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_incdec_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_incdec_detail`;
CREATE TABLE `stro_incdec_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `adjust_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '损益ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `sell_price` decimal(11, 4) DEFAULT NULL COMMENT '零售单价',
  `buy_price` decimal(11, 4) DEFAULT NULL COMMENT '购进单价',
  `split_price` decimal(11, 4) DEFAULT NULL COMMENT '拆零单价',
  `sell_before_price` decimal(14, 4) DEFAULT NULL COMMENT '损益前零售总金额（损益前库存数量 * 零售单价）',
  `sell_after_price` decimal(14, 4) DEFAULT NULL COMMENT '损益后零售总金额（（损益前库存数量 - 损益数量） * 零售单价）',
  `buy_before_price` decimal(14, 4) DEFAULT NULL COMMENT '损益前购进总金额（损益前库存数量 * 购进单价）',
  `buy_after_price` decimal(14, 4) DEFAULT NULL COMMENT '损益后购进总金额（（损益前库存数量 - 损益数量） * 购进单价）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  `before_num` decimal(14, 4) DEFAULT NULL COMMENT '损益前库存数量',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '损益数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盘点结论代码（PDJL）',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量（损益前库存数量 * 拆分比）',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                       -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_inventory
-- ----------------------------
DROP TABLE IF EXISTS `stro_inventory`;
CREATE TABLE `stro_inventory`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `before_price` decimal(14, 4) DEFAULT NULL COMMENT '盘前总金额',
  `after_price` decimal(14, 4) DEFAULT NULL COMMENT '盘后总金额',
  `incdec_price` decimal(14, 4) DEFAULT NULL COMMENT '损益总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\ninventory：盘点\r\n                                   -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_inventory_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_inventory_detail`;
CREATE TABLE `stro_inventory_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `inventory_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盘点单ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  `before_num` decimal(14, 4) DEFAULT NULL COMMENT '盘点前总数量',
  `before_split_num` decimal(14, 4) DEFAULT NULL COMMENT '盘点前拆零总数量',
  `final_num` decimal(14, 4) DEFAULT NULL COMMENT '实盘总数量',
  `incdec_num` decimal(14, 4) DEFAULT NULL COMMENT '损益总数量',
  `final_split_num` decimal(14, 4) DEFAULT NULL COMMENT '实盘拆零总数量',
  `result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盘点结论代码（PDJL）',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_buy_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零购进单价',
  `split_sell_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零零售单价',
  `incdec_buy_price` decimal(14, 4) DEFAULT NULL COMMENT '盘点损益购进总金额',
  `incdec_sell_price` decimal(14, 4) DEFAULT NULL COMMENT '盘点损益零售总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\ninventory：盘点\r\n                                          -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_invoicing
-- ----------------------------
DROP TABLE IF EXISTS `stro_invoicing`;
CREATE TABLE `stro_invoicing`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `outin_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出入方式代码（CRFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '大单位数量（入库为正，出库为负）',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前单位代码（DW）',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `batch_surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '批号结余数量',
  `up_surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '上期/期初数量',
  `surplus_num` decimal(14, 4) DEFAULT NULL COMMENT '本期/期末数量',
  `up_buy_price_all` decimal(14, 4) DEFAULT 0.0000 COMMENT '上期/期初购进总金额',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '本期/期末购进总金额',
  `up_sell_price_all` decimal(14, 4) DEFAULT 0.0000 COMMENT '上期/期初零售总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '本期/期末零售总金额',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID（操作）',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名（操作）',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（操作）',
  `crte_time_stamp` bigint(13) DEFAULT NULL COMMENT '时间戳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nInvoicing：进销存\r\n                                   ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_out
-- ----------------------------
DROP TABLE IF EXISTS `stro_out`;
CREATE TABLE `stro_out`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出库方式代码（CRFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `out_stock_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出库库位ID',
  `in_stock_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '入库库位ID',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `ok_audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '确认审核状态代码（SHZT）',
  `ok_audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '确认审核人ID',
  `ok_audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '确认审核人姓名',
  `ok_audit_time` datetime(0) DEFAULT NULL COMMENT '确认审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nout：出库\r\n\r\n                             ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_out_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_out_detail`;
CREATE TABLE `stro_out_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `out_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '出库ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `dosage` decimal(14, 4) DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nout：出库\r\nde' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_purchase
-- ----------------------------
DROP TABLE IF EXISTS `stro_purchase`;
CREATE TABLE `stro_purchase`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单据号',
  `supplier_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供应商ID',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\npurchase：采购\r\n                                  -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_purchase_detail`;
CREATE TABLE `stro_purchase_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `purchase_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '采购ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `seq_no` decimal(14, 4) DEFAULT NULL COMMENT '顺序号',
  `stock_num` decimal(14, 4) DEFAULT NULL COMMENT '当时库存数量',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产厂家ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\npurchase：采购\r\n                                         -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_stock
-- ----------------------------
DROP TABLE IF EXISTS `stro_stock`;
CREATE TABLE `stro_stock`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `location_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库位码（货架号）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '库存总数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `stock_max` decimal(14, 4) DEFAULT NULL COMMENT '库存上限',
  `stock_min` decimal(14, 4) DEFAULT NULL COMMENT '库存下限',
  `stock_occupy` decimal(14, 4) DEFAULT NULL COMMENT '占用库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nstock：库存\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_stock_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_stock_detail`;
CREATE TABLE `stro_stock_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称（药品/材料）',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位代码（DW）',
  `num` decimal(14, 4) DEFAULT NULL COMMENT '数量',
  `buy_price` decimal(14, 4) DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) DEFAULT NULL COMMENT '零售总金额',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批号',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  `split_ratio` decimal(14, 4) DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拆零单位代码（DW）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nstock：库存\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值域代码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值域名称',
  `show_default` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '显示默认值',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_tree` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否树形',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_HC_CODE`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：值域代码表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_code_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_code_detail`;
CREATE TABLE `sys_code_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `c_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值域代码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值域值名称',
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值域值',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `seq_no` int(11) DEFAULT NULL COMMENT '顺序号',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `up_value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级值域值',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否末级：0否，1是',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效：0否，1是',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：值域代码表明细表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `sys_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统编码：sys_system表系统编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单类型代码：0、目录，1、菜单',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级菜单编码',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单URL',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `seq_no` int(11) DEFAULT NULL COMMENT '顺序号',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单描述',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_button`;
CREATE TABLE `sys_menu_button`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `menu_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码：sys_menu表菜单编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮类型代码',
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮图标',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮描述',
  `seq_no` int(11) DEFAULT NULL COMMENT '顺序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '\r\n\r\n1、按钮类型代码：primary、success、info、warning、danger\r\n          ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数代码',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数值',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数描述',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效',
  `is_show` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否可见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：系统参数表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `seq_no` int(11) DEFAULT NULL COMMENT '顺序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_button`;
CREATE TABLE `sys_role_menu_button`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色编码',
  `menu_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `button_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_system`;
CREATE TABLE `sys_system`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统名称',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `seq_no` int(11) DEFAULT NULL COMMENT '顺序号',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所属科室编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工工号（登录账户）',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录密码（初始化密码：888888）',
  `is_in_job` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否在职',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别代码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '民族代码',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '籍贯',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婚姻状况代码',
  `is_pm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否党员',
  `in_pm_date` date DEFAULT NULL COMMENT '入党时间',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型代码',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `photo_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像照片路径',
  `sign_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子签名照片路径',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生介绍',
  `speciality` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医生擅长',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '家庭地址',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `education_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学历分类代码',
  `major_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所学专业代码',
  `school` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '毕业学校',
  `school_date` date DEFAULT NULL COMMENT '毕业时间',
  `school_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '毕业证书图片路径',
  `work_date` date DEFAULT NULL COMMENT '参加工作时间',
  `work_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职工类型代码(RYZW)',
  `duties_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职称代码',
  `prac_certi_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执业证书编号',
  `prac_certi_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执业证书名称',
  `durg_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '药品级别代码集合（YPJB）（多个用逗号分开）',
  `ph_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '毒麻药品级别代码集合（DMYPJB）（多个用逗号分开）',
  `antibacterial_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '抗菌药品级别代码集合（KJYPJB）（多个用逗号分开）',
  `opeartion_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手术级别代码集合（SSJB）（多个用逗号分开）',
  `pswd_err_cnt` int(11) DEFAULT 0 COMMENT '密码错误次数',
  `last_login_time` datetime(0) DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最后登录IP',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户状态代码',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '五笔码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`, `code`) USING BTREE,
  UNIQUE INDEX `code`(`code`, `hosp_code`) USING BTREE COMMENT '用户编码唯一约束'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '\r\n\r\n1、用户状态代码：1正常    2停用   3密码错误冻结\'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `us_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户子系统关系编码',
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_system`;
CREATE TABLE `sys_user_system`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '医院编码',
  `us_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户子系统关系编码',
  `user_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '员工工号（登录账户）',
  `system_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统编码',
  `teacher_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '带教员工工号（导师）',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作科室编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
