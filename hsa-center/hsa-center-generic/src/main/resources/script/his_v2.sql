/*
 Navicat Premium Data Transfer

 Source Server         : （公司）云HIS 103
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 172.18.100.103:3306
 Source Schema         : his_v2

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 23/03/2022 14:57:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bad_blood_contact
-- ----------------------------
DROP TABLE IF EXISTS `bad_blood_contact`;
CREATE TABLE `bad_blood_contact`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `event_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型（BLSJLX）',
  `event_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件编号',
  `event_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件等级（SJDJ）',
  `find_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现科室ID',
  `upload_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报科室ID',
  `upload_status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报状态（SBZT）',
  `happen_time` datetime(0) NULL DEFAULT NULL COMMENT '事件发生时间',
  `upload_work_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报人职称(RYZW)',
  `contact_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '暴露发生地点(BLFSDD)',
  `know_source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人源来源情况(BRYLYQK)',
  `body_fluid_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体液类型（TYLX）',
  `is_blood_infect` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否受血液感染',
  `contact_position` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '暴露部位（BLBW）',
  `protect_tools_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '防护用具类型（FHYJLX）',
  `contact_reason` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '暴露接触原因（BLJCYY）',
  `event_course` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件经过',
  `cause_analysis` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因分析',
  `precautions` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理措施',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `is_anonymous` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匿名',
  `assign_examine` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定审核人',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `examine_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `examine_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：血液/体液暴露接触表\r\n事件类型（BLSJLX）：0医疗上报、1跌倒坠床、2院内压疮、3血液/体液暴露接触、4药品不良\r\n事件等级（SJDJ）：1Ⅰ级、2Ⅱ级、3Ⅲ级、4Ⅳ级、5Ⅴ级\r\n上报状态（SBZT）：0保存、1提交、2审核、3撤销、4作废\r\n暴露发生地点(BLFSDD)：0病房内、1病房外、2急诊室、3ICU、4手术室、5产房、6血库、7供应室、8门诊、9配血中心、10中心治疗室、11血液透析室、12辅助科室、13临床实验室、14病理检查室、15后勤服务、16社区、17其它\r\n病人源来源情况(BRYLYQK)：0知道、1不知道、2不清楚、3不适用\r\n体液类型（TYLX）：0血液或血制品、1呕吐物、2痰液、3唾液、4脑脊液、5腹膜液、6胸膜液、7羊水、8尿液、9其它\r\n暴露部位（BLBW）：0完整皮肤、1受损皮肤、2眼睛、3鼻、4口腔、5其他\r\n防护用具类型（FHYJLX）：0单层手套、1双层手套、2防护镜、3眼镜、4带侧面防护罩的眼镜、5面罩、6外科口罩、7外科手术衣、8塑料围裙、9实验室工作服、10其他工作服、11防护服、12其它\r\n暴露接触原因（BLJCYY）：0直接接触病人、1标本容器渗漏、2样品容器破裂、3静脉输液管/袋/泵破损、4其它体液容器溅出、5接触污染设备、6接触污染被服、7鼻饲管/通气管/其它管、8其它' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bad_drug
-- ----------------------------
DROP TABLE IF EXISTS `bad_drug`;
CREATE TABLE `bad_drug`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `event_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型（BLSJLX）',
  `event_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件编号',
  `event_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件等级（SJDJ）',
  `is_history` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有既往史药品不良事件',
  `history_bad_drug` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史不良药品',
  `is_family` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有家族史药品不良事件',
  `family_bad_drug` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家族史不良药品',
  `personal_life_habits` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人生活习性（GRSHXX）',
  `is_frist` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否首次',
  `report_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告类型（BGLX）',
  `report_company_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告单位类别（BGDWLB）',
  `severity_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重程度（YZCD）',
  `happen_time` datetime(0) NULL DEFAULT NULL COMMENT '发生时间',
  `find_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现科室ID',
  `upload_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报科室ID',
  `upload_status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报状态（SBZT）',
  `upload_work_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报人职称(RYZW)',
  `event_course` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件经过',
  `cause_analysis` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因分析',
  `precautions` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理措施',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `is_anonymous` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匿名',
  `assign_examine` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定审核人',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `examine_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `examine_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：药品不良反应表\r\n事件类型（BLSJLX）：0医疗上报、1跌倒坠床、2院内压疮、3血液/体液暴露接触、4药品不良\r\n事件等级（SJDJ）：1Ⅰ级、2Ⅱ级、3Ⅲ级、4Ⅳ级、5Ⅴ级\r\n个人生活习性（GRSHXX）：0吸烟史、1饮酒史、2妊娠期、3肝病史、4肾病史、5过敏史、6其它\r\n报告类型（BGLX）：0新的、1严重、2一般\r\n报告单位类别（BGDWLB）：0医疗机构、1经营企业、2生产企业、3个人、4其他\r\n严重程度（YZCD）：0导致死亡、1危及生命、2致癌/致畸/致出生缺陷、3致显著的或永久的人体伤残或器官功能损伤、4致住院或住院时间延长、5致其他重要医学事件\r\n上报状态（SBZT）：0保存、1提交、2审核、3撤销、4作废' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bad_drug_detail
-- ----------------------------
DROP TABLE IF EXISTS `bad_drug_detail`;
CREATE TABLE `bad_drug_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `bad_drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品不良事件ID（主表主键）',
  `bad_drug_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品不良类型（YPBLLX）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID',
  `good_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `usual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通用名',
  `dan` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批准文号',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂型',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型单位（JLDW）',
  `prod_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `batch_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产批号',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '用量',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质',
  `use_cause` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药原因',
  `use_start_time` datetime(0) NULL DEFAULT NULL COMMENT '用药开始时间',
  `use_end_time` datetime(0) NULL DEFAULT NULL COMMENT '用药结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：药品不良反应明细表\r\n药品不良类型（YPBLLX）：0怀疑药品、1并用药品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bad_fall_bed
-- ----------------------------
DROP TABLE IF EXISTS `bad_fall_bed`;
CREATE TABLE `bad_fall_bed`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `event_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型（BLSJLX）',
  `event_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件编号',
  `event_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件等级（SJDJ）',
  `find_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现科室ID',
  `upload_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报科室ID',
  `upload_status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报状态（SBZT）',
  `happen_time` datetime(0) NULL DEFAULT NULL COMMENT '事件发生时间',
  `party_work_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当事人职称(RYZW)',
  `party_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当事人层级（DSRCJ）',
  `culture_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '患者文化程度（BLWHCD）',
  `nurse_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理级别（HLJB）',
  `accident_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事故类型（SGLX）',
  `injured_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受伤程度(SSCD)',
  `event_course` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件经过',
  `cause_analysis` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因分析',
  `precautions` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理措施',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `is_anonymous` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匿名',
  `assign_examine` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定审核人',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `examine_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `examine_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：跌倒坠床表\r\n事件类型（BLSJLX）：0医疗上报、1跌倒坠床、2院内压疮、3血液/体液暴露接触、4药品不良\r\n事件等级（SJDJ）：1Ⅰ级、2Ⅱ级、3Ⅲ级、4Ⅳ级、5Ⅴ级\r\n上报状态（SBZT）：0保存、1提交、2审核、3撤销、4作废\r\n当事人层级（DSRCJ）：0N0、1N1、2N2、3N3、4N4、5N5\r\n患者文化程度（BLWHCD）：0文盲、1小学、2初中、3高中、4大专、5本科及以上、6博士、7未知\r\n护理级别（HLJB）：0特级、1一级、2二级、3三级\r\n事故类型（SGLX）：0跌倒、1坠床\r\n受伤程度（SSCD）：0极重度受伤、1重度受伤、2中度受伤、3轻度受伤、4无伤害' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bad_medic_upload
-- ----------------------------
DROP TABLE IF EXISTS `bad_medic_upload`;
CREATE TABLE `bad_medic_upload`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `event_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型（BLSJLX）',
  `event_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件编号',
  `event_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件等级（SJDJ）',
  `victim_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受害者类别(RYLB)',
  `find_people_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现人类别(RYLB)',
  `find_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现科室ID',
  `upload_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报科室ID',
  `upload_status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报状态（SBZT）',
  `happen_time` datetime(0) NULL DEFAULT NULL COMMENT '事件发生时间',
  `happen_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件发生地点(SJFSDD)',
  `happen_item` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件发生服务项目(FSXM)',
  `happen_peopel_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件发生前患者状态(FSHZZT)',
  `party_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当事人类别(RYLB)',
  `party_work_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当事人职称(RYZW)',
  `total_work_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '工作总年限',
  `dept_work_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '科室工作年限',
  `injured_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受伤程度(SSCD)',
  `is_dispute` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有纠纷',
  `defuse_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '化解方式（HJFS）',
  `event_course` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件经过',
  `cause_analysis` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因分析',
  `precautions` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理措施',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `is_anonymous` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匿名',
  `assign_examine` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定审核人',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `examine_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `examine_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：医疗上报\r\n事件类型（BLSJLX）：0医疗上报、1跌倒坠床、2院内压疮、3血液/体液暴露接触、4药品不良\r\n事件等级（SJDJ）：1Ⅰ级、2Ⅱ级、3Ⅲ级、4Ⅳ级、5Ⅴ级\r\n人员类别(RYLB)：0患者、1患者家属、2医生、3护士、4医技人员、5护工、6保洁、7其他\r\n上报状态（SBZT）：0保存、1提交、2审核、3撤销、4作废\r\n事件发生地点（SJFSDD）：0病区、1门诊、2住院、3急诊、4公共区域、5检查科室、6其他\r\n事件发生服务项目(FSXM)：0输液、1吃药、2检验检查、3手术\r\n事件发生前患者状态（FSHZZT）：0意识障碍、1听觉障碍、2视觉障碍、3语言障碍、4精神障碍、5痴呆、6记忆障碍、7嗜睡状态、8下肢功能障碍、9上肢功能障碍、10行走障碍、11紫绀/呼吸困难、12寒颤/高热、12皮肤黏糊障碍、13抽搐状态、14安静状态、15麻醉状态、16使用镇定剂后、17乘轮椅、18正常、19情况不明、20其他\r\n受伤程度（SSCD）：0极重度受伤、1重度受伤、2中度受伤、3轻度受伤、4无伤害\r\n化解方式（HJFS）：0移交纠纷办、1科室化解' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bad_pressure_sore
-- ----------------------------
DROP TABLE IF EXISTS `bad_pressure_sore`;
CREATE TABLE `bad_pressure_sore`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `event_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型（BLSJLX）',
  `event_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件编号',
  `event_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件等级（SJDJ）',
  `find_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现科室ID',
  `upload_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报科室ID',
  `upload_status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上报状态（SBZT）',
  `happen_time` datetime(0) NULL DEFAULT NULL COMMENT '事件发生时间',
  `nurse_measure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前护理措施（DQHLCS）',
  `is_inform_family` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否告知家属',
  `injured_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受伤程度(SSCD)',
  `event_course` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件经过',
  `cause_analysis` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因分析',
  `precautions` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理措施',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `is_anonymous` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匿名',
  `assign_examine` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定审核人',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `examine_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `examine_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：院内压疮表\r\n事件类型（BLSJLX）：0医疗上报、1跌倒坠床、2院内压疮、3血液/体液暴露接触、4药品不良\r\n事件等级（SJDJ）：1Ⅰ级、2Ⅱ级、3Ⅲ级、4Ⅳ级、5Ⅴ级\r\n上报状态（SBZT）：0保存、1提交、2审核、3撤销、4作废\r\n当前护理措施（DQHLCS）：0正确使用预防压疮用具、1垫枕、2防压疮气垫床、3保护性敷料、4翻身Q2H，避免局部受压、5保持皮肤清洁与干燥、6加强全身营养、7严格交接班制度，每班进行皮肤评估，必要时做好记录、8其他\r\n受伤程度（SSCD）：0极重度受伤、1重度受伤、2中度受伤、3轻度受伤、4无伤害' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bad_pressure_sore_detail
-- ----------------------------
DROP TABLE IF EXISTS `bad_pressure_sore_detail`;
CREATE TABLE `bad_pressure_sore_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `pressure_sore_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件ID（主表主键）',
  `pressure_sore_stage` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '压疮分期',
  `color` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `length` decimal(14, 4) NULL DEFAULT NULL COMMENT '长度(CM)',
  `width` decimal(14, 4) NULL DEFAULT NULL COMMENT '宽度(CM)',
  `depth` decimal(14, 4) NULL DEFAULT NULL COMMENT '深度(CM)',
  `pressure_sore_position` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '压疮部位',
  `pressure_sore_other` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '压疮部位其它',
  `happen_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发生科室ID',
  `happen_time` datetime(0) NULL DEFAULT NULL COMMENT '发生时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名：院内压疮明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_advice
-- ----------------------------
DROP TABLE IF EXISTS `base_advice`;
CREATE TABLE `base_advice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱编码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱名称',
  `other_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱别名',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱类别代码（YZLB）',
  `technology_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技分类代码（动态取码表）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `out_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院执行科室编码（表base_dept）',
  `in_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊执行科室编码（表base_dept）',
  `biz_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务类别',
  `biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务类别代码（动态取码表）',
  `container_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '容器类型代码（RQ）',
  `specimen_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标本类型代码（BBLX）',
  `use_scope_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用范围（SYFW）',
  `dept_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用科室编码集合（多个用逗号隔开）',
  `doctor_level_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开嘱医生级别集合（多个用逗号）',
  `opeartion_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术级别代码（SSJB）',
  `insure_list_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保目录标识代码（YBMLBS）',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别限制代码（XB）',
  `min_age` int(11) NULL DEFAULT NULL COMMENT '最小年龄',
  `max_age` int(11) NULL DEFAULT NULL COMMENT '最大年龄',
  `is_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否计费（SF）',
  `is_stop_same` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否停同类医嘱（SF）',
  `is_stop_same_not` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否停非同类医嘱（SF）',
  `is_stop_myself` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否停自身（SF）',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `oper_disease_icd9` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术疾病编码ICD-9',
  `reimbursement_ratio` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报销比例(BXBL)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_advice_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nadvice：医嘱\r\n\r\n表说明：\r\n                                -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_advice_detail
-- ----------------------------
DROP TABLE IF EXISTS `base_advice_detail`;
CREATE TABLE `base_advice_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `advice_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类别代码（XMLB）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `is_alone_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否数量独立计费（SF）',
  `is_frist_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否仅首次计费（SF）',
  `is_appoint_rate` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否指定频率（SF）',
  `rate_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱频率编码（表base_rate）',
  `insure_list_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保目录标识代码（YBMLBS）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_advice_detail_01`(`hosp_code`, `advice_code`, `item_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nadvice：医嘱\r\ndetail：明细\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_assist
-- ----------------------------
DROP TABLE IF EXISTS `base_assist`;
CREATE TABLE `base_assist`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计费编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计费名称',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类别代码（药品/材料/项目）（YWLB）',
  `biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务编码',
  `way_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计费方式代码（JFFS）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_first` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否首次：0否、1是（SF）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_assist_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nassist：辅助计费表\r\ncalc：计算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_assist_detail
-- ----------------------------
DROP TABLE IF EXISTS `base_assist_detail`;
CREATE TABLE `base_assist_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `ac_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计费编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类别代码（XMLB）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `num` decimal(11, 4) NULL DEFAULT NULL COMMENT '数量',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_assist_detail_01`(`hosp_code`, `ac_code`, `item_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nassist：辅助计费明细表\r\ncalc：计算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_bed
-- ----------------------------
DROP TABLE IF EXISTS `base_bed`;
CREATE TABLE `base_bed`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码（KB）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '床位编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '床位名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '床位类型（CWLX）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '床位状态代码（CWZT）',
  `seq_no` int(11) NOT NULL COMMENT '顺序号',
  `room_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房间号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_bed_01`(`hosp_code`, `code`) USING BTREE,
  INDEX `idx_base_bed_02`(`hosp_code`, `dept_code`, `visit_id`) USING BTREE,
  INDEX `idx_base_bed_03`(`visit_id`, `hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nbed：床位\r\n\r\n表说明：\r\n床' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_bed_item
-- ----------------------------
DROP TABLE IF EXISTS `base_bed_item`;
CREATE TABLE `base_bed_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `bed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '床位编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码（表base_item）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_bed_item_01`(`hosp_code`, `bed_code`, `item_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nbed：床位收费项目表\r\nitem：收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_card
-- ----------------------------
DROP TABLE IF EXISTS `base_card`;
CREATE TABLE `base_card`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `card_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡号',
  `card_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡密码',
  `card_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一卡通类型（YKTLX）\r\n1诊疗卡，2医保卡，3电子健康卡，\r\n',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一卡通状态（YKTZT）\r\n0正常，1挂失，2冻结，3注销，4确认挂失，5作废',
  `account_balance` decimal(14, 4) NULL DEFAULT NULL COMMENT '账户余额',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易ID',
  `ele_health_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子健康卡ID',
  `city_health_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市民健康系统ID',
  `def_card_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认关联卡类型',
  `def_card_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认关联卡号',
  `health_code_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '健康卡二维码文本',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ncard：一卡通\r\n\r\n表说明：\r\n一卡通' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_card_change
-- ----------------------------
DROP TABLE IF EXISTS `base_card_change`;
CREATE TABLE `base_card_change`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `card_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异动状态（YDZT）\r\n0发卡，1挂失，2冻结，3注销，4确认挂失，5作废',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '一卡通卡片异动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_card_recharge_change
-- ----------------------------
DROP TABLE IF EXISTS `base_card_recharge_change`;
CREATE TABLE `base_card_recharge_change`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案ID',
  `card_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异动状态（YDZT）\r\n6充值，7退费，8消费',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '支付金额',
  `start_balance` decimal(14, 4) NULL DEFAULT NULL COMMENT '异动前账户余额',
  `start_balance_encryption` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异动前账户余额密文',
  `end_balance` decimal(14, 4) NULL DEFAULT NULL COMMENT '异动后账户余额',
  `end_balance_encryption` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异动后账户余额密文',
  `settle_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算方式（JSFS）\r\n01挂号,02退号,03门诊结算,04门诊退费',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结缴款ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '一卡通卡片充值异动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_clinic
-- ----------------------------
DROP TABLE IF EXISTS `base_clinic`;
CREATE TABLE `base_clinic`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真是名称',
  `number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门牌号',
  `address` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效,0有效，1无效',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属科室',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建名称',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_dailyfirst_calc
-- ----------------------------
DROP TABLE IF EXISTS `base_dailyfirst_calc`;
CREATE TABLE `base_dailyfirst_calc`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `rate_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率编码（表base_rate）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `daily_first_num` int(11) NULL DEFAULT NULL COMMENT '每日首次收费次数',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_dailyfirst_calc_01`(`hosp_code`, `dept_code`, `rate_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndailyfirst：每日首次计费表\r\ncalc：计算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_dept`;
CREATE TABLE `base_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室性质代码（KSXZ）',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码（KB）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室名称',
  `ward_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病区编码（表base_ward）',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `person_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室负责人姓名',
  `up_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级科室编码（表base_dept）',
  `mg_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经管科室编码（JGKS）',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室介绍',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室位置',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `is_upload` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否上传',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `type_identity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别标识',
  `mris_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病案首页对应科别编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_dept_01`(`code`) USING BTREE,
  INDEX `idx_base_dept_02`(`hosp_code`, `type_code`, `nation_code`, `ward_code`) USING BTREE,
  INDEX `idx_base_dept_03`(`hosp_code`, `is_valid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndept：科室\r\n\r\n表说明：\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_dept_drugstore
-- ----------------------------
DROP TABLE IF EXISTS `base_dept_drugstore`;
CREATE TABLE `base_dept_drugstore`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药房类别代码（YFLB）',
  `drugstore_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药房编码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_dept_drugstore_01`(`hosp_code`, `dept_code`, `drugstore_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndept：科室关联药房信息表\r\ndrugstore：药房' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_diagnosis_template
-- ----------------------------
DROP TABLE IF EXISTS `base_diagnosis_template`;
CREATE TABLE `base_diagnosis_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病分类',
  `biz_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务类别 （门诊，住院）',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ID',
  `disease_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病名称',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ICD-10码',
  `use_scope_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用范围（MBLX）',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室名称',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生名称',
  `is_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否审核',
  `check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_disease
-- ----------------------------
DROP TABLE IF EXISTS `base_disease`;
CREATE TABLE `base_disease`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病分类代码（JBFL）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病编码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病名称',
  `usual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病别名',
  `attach_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加编码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码',
  `nation_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家诊断名称',
  `is_add` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否自增：0否、1是（SF）',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `is_infectious` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否传染病 0否、1是（SF）',
  `infectious_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传染病名称',
  `infectious_interval_days` int(4) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '传染病间隔（天）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `platform_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_match` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匹配医保',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_disease_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndisease：疾病\r\n\r\n表说明：\r\n                      ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_disease_rule
-- ----------------------------
DROP TABLE IF EXISTS `base_disease_rule`;
CREATE TABLE `base_disease_rule`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品ID',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提示类型代码（TSLX）',
  `prompt_msg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提示信息',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_disease_rule_01`(`hosp_code`, `disease_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '27. 疾病规则配置表-Y' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_drug
-- ----------------------------
DROP TABLE IF EXISTS `base_drug`;
CREATE TABLE `base_drug`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `bfc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类编码（表base_finance_classify）',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品分类代码（YPFL）',
  `big_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品大类代码（YPDL）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品编码',
  `usual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通用名',
  `good_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `spec` varchar(42) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `out_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊单位代码（DW）',
  `in_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院单位代码（DW）',
  `rate_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率编码（表base_rate）',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `last_buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '最近购进单价',
  `last_split_buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '最近拆零购进单价',
  `avg_buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '平均购进价',
  `avg_sell_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '平均零售价',
  `split_ratio` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `is_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否门诊可用：0否、1是（SF）',
  `is_in` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否住院可用：0否、1是（SF）',
  `is_lvp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否大输液：0否、1是（SF）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保本位码编码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家卫健委编码',
  `nation_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码',
  `drug_remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '药品说明书',
  `drug_img_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品说明书图片路径',
  `max_dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '最大剂量',
  `min_dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '最小剂量',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别限制代码',
  `min_age` int(11) NULL DEFAULT NULL COMMENT '最小年龄',
  `max_age` int(11) NULL DEFAULT NULL COMMENT '最大年龄',
  `ddd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'DDD值（限定日剂量）',
  `durg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品级别代码（YPJB）',
  `ph_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毒麻药品级别代码（DMTX）',
  `antibacterial_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抗菌药品级别代码（KJYPJB）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_basic` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否基药：0否、1是（SF）',
  `is_prescription` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否处方药(SF)',
  `basic_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基药代码（JY）',
  `dan` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批准文号',
  `ndan` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国药准字号',
  `prod_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家编码（表base_product）',
  `usual_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通用名拼音码',
  `usual_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通用名五笔码',
  `good_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名拼音码',
  `good_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `proton_pump` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '质子泵',
  `reimbursement_ratio` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报销比例(BXBL)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_drug_01`(`hosp_code`, `code`) USING BTREE,
  INDEX `idx_base_drug_02`(`hosp_code`, `bfc_code`, `type_code`) USING BTREE,
  INDEX `idx_base_drug_03`(`big_type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\ndrug：药品\r\n\r\n表说明：\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_finance_classify
-- ----------------------------
DROP TABLE IF EXISTS `base_finance_classify`;
CREATE TABLE `base_finance_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类名称',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院发票代码（FPGL）',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊发票代码（FPGL）',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级编码',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否末级：0否、1是（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_finance_classify_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nfinance：财务分类\r\nclassify：分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_item
-- ----------------------------
DROP TABLE IF EXISTS `base_item`;
CREATE TABLE `base_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码',
  `nation_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目分类代码（XMFL）',
  `bfc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类编码（表base_finance_classify）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `abbr` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目规格',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '项目单价',
  `one_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '一级价格',
  `two_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '二级价格',
  `three_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '三级价格',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `intension` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prompt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目提示',
  `except` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '除外内容',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否门诊可用：0否、1是（SF）',
  `is_in` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否住院可用：0否、1是（SF）',
  `is_cg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否可改价0否、1是（SF）（门诊直接划价收费用，如果直接划价收费选择的项目是可改价的，表格中价格一列是可以修改的）',
  `is_ms` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否医技可用：0否、1是（SF）',
  `is_supp_curtain` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否补记帐：0否、1是（SF）',
  `out_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊执行科室编码（表base_dept）',
  `in_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院执行科室编码（表base_dept）',
  `name_pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称拼音码',
  `name_wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称五笔码',
  `abbr_pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称拼音码',
  `abbr_wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `reimbursement_ratio` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报销比例(BXBL)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_item_01`(`hosp_code`, `code`) USING BTREE,
  INDEX `idx_base_item_02`(`hosp_code`, `bfc_code`, `type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nitem：项目基础信息表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_material
-- ----------------------------
DROP TABLE IF EXISTS `base_material`;
CREATE TABLE `base_material`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '材料分类代码（CLFL）',
  `bfc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类编码（表base_finance_classify）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '材料编码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '单价',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `last_buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '最近购进单价',
  `last_split_buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '最近拆零购进单价',
  `avg_buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '平均购进价',
  `avg_sell_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '平均零售价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_ratio` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `is_supp_curtain` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否补记账：0否、1是（SF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '材料备注',
  `prod_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家编码',
  `reg_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册证号',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码',
  `nation_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家名称',
  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '型号',
  `other_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `is_offical` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否办公用品（SF）',
  `reimbursement_ratio` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报销比例(BXBL)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_material_01`(`hosp_code`, `code`) USING BTREE,
  INDEX `idx_base_material_02`(`hosp_code`, `bfc_code`, `type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nmaterial：材料\r\n\r\n表说明：\r\n                     ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_modify_trace
-- ----------------------------
DROP TABLE IF EXISTS `base_modify_trace`;
CREATE TABLE `base_modify_trace`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务ID',
  `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `updt_conent` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `updt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人ID',
  `updt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `updt_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_modify_trace_01`(`hosp_code`, `biz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nmodify：修改\r\ntrace：痕迹、记录\r\n                  ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_mris_classify
-- ----------------------------
DROP TABLE IF EXISTS `base_mris_classify`;
CREATE TABLE `base_mris_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `mris_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病案费用代码（BAFY）',
  `bfc_codes` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类编码集合（表base_finance_classify）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_mris_classify_01`(`hosp_code`, `mris_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '25. 病案费用归类表（同步）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_nurse_order
-- ----------------------------
DROP TABLE IF EXISTS `base_nurse_order`;
CREATE TABLE `base_nurse_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理单编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理单名称',
  `is_assign` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否指定科室（SF）',
  `is_vertical` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否竖向打印（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `dept_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID列表',
  `pg_size` int(11) NULL DEFAULT NULL COMMENT '每页行数',
  `top_height` decimal(14, 4) NULL DEFAULT NULL COMMENT '单据头距顶部高度(mm)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  UNIQUE INDEX `idx_base_nurse_order_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '护理单据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_nurse_tbhead
-- ----------------------------
DROP TABLE IF EXISTS `base_nurse_tbhead`;
CREATE TABLE `base_nurse_tbhead`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `bno_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理单编码',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '列顺序号',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表头编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表头名称',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码（19、护理记录明细表：扩展字段1-40）',
  `min_num` int(11) NULL DEFAULT NULL COMMENT '最小数值（为数值型时使用）',
  `max_num` int(11) NULL DEFAULT NULL COMMENT '最大数值（为数值型时使用）',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别限制代码',
  `date_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据类型代码（SJLX）',
  `data_width` int(11) NULL DEFAULT NULL COMMENT '数据显示宽度',
  `data_length` int(11) NULL DEFAULT NULL COMMENT '数据长度',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据来源方式代码（SJLYFS）',
  `source_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据来源方式值',
  `default_value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `is_up` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上级标题（SF）',
  `is_sum` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否汇总',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `is_branch` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否分行（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_nurse_tbhead_01`(`hosp_code`, `bno_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据来源方式代码（SJLYFS）：0、手工录入，1、自定义下拉，2、护士SQL\r\n\r\n0、手工录入\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_order_receive
-- ----------------------------
DROP TABLE IF EXISTS `base_order_receive`;
CREATE TABLE `base_order_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据名称',
  `priority` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `usage_codes` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码集合',
  `dept_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用科室ID集合',
  `is_lvp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否大输液：0否、1是（SF）',
  `is_ph` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否特殊药品：0否、1是（SF）',
  `is_herb` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否中草药（SF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否出院带药（SF）',
  `is_emergency` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否紧急领药（SF）',
  `is_material` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否材料（SF）',
  `is_patient` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否按病人（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_all` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否为全部领药单 (SF)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_order_receive_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\norder：单据\r\nreceive：领药\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_order_rule
-- ----------------------------
DROP TABLE IF EXISTS `base_order_rule`;
CREATE TABLE `base_order_rule`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据类型代码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据名称',
  `format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成格式',
  `curr_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前单据号',
  `prefix` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前缀',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_continuity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否连续（业务事务）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_order_rule_01`(`hosp_code`, `type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\norder：单据\r\nrule：规则\r\n\r\n                     ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_outpt_exec
-- ----------------------------
DROP TABLE IF EXISTS `base_outpt_exec`;
CREATE TABLE `base_outpt_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `usage_codes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码列表（YF）',
  `dept_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID列表',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '26. 门诊执行科室配置表-Y' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_preferential
-- ----------------------------
DROP TABLE IF EXISTS `base_preferential`;
CREATE TABLE `base_preferential`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类别代码（YHLB）',
  `biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务编码',
  `pf_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类型编码（base_preferential_type.code）',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊优惠方式代码（YHFS）',
  `out_scale` decimal(11, 2) NULL DEFAULT NULL COMMENT '门诊优惠率',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院优惠方式代码（YHFS）',
  `in_scale` decimal(11, 2) NULL DEFAULT NULL COMMENT '住院优惠率',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_preferential_01`(`hosp_code`, `pf_type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\npreferential：优惠\r\n\r\n表说明' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_preferential_type
-- ----------------------------
DROP TABLE IF EXISTS `base_preferential_type`;
CREATE TABLE `base_preferential_type`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类型编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类型名称',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_preferential_type_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\npreferential：优惠\r\n\r\n表说明' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_product
-- ----------------------------
DROP TABLE IF EXISTS `base_product`;
CREATE TABLE `base_product`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产企业编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产企业名称',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `fax` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真',
  `post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否，1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_product_01`(`hosp_code`, `code`) USING BTREE,
  INDEX `indxe_product_code`(`code`) USING BTREE,
  INDEX `idx_base_product_03`(`hosp_code`) USING BTREE,
  INDEX `idx_base_dept_03`(`hosp_code`, `is_valid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nproduct：生产企业信息表\r\n                    ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_profile_file
-- ----------------------------
DROP TABLE IF EXISTS `base_profile_file`;
CREATE TABLE `base_profile_file`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院档案号（入院登记回写）',
  `out_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊档案号（挂号时生成）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族代码（MZ）',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍代码（GJ）',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `education_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历代码（XL）',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业代码（ZY）',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `often_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '常住类型（CZLX）（1 户籍，2非户籍）',
  `now_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地（省）',
  `now_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地（市）',
  `now_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地（区、县）',
  `now_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地详细地址',
  `now_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地邮编',
  `native_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口地（省）',
  `native_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口地（市）',
  `native_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口地（区、县）',
  `native_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口地详细地址',
  `native_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口地邮编',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `contact_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人邮编',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人地址',
  `take_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监护人姓名',
  `take_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监护人关系（RYGX）',
  `take_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监护人证件号码',
  `take_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监护人联系电话',
  `work` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `work_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位电话',
  `work_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位邮编',
  `work_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位地址',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '血型代码（XX）',
  `family_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家族史',
  `expose_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '暴露史',
  `heredity_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '遗传病史',
  `allergy_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏史',
  `past_disease` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史疾病',
  `past_oper` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史手术',
  `past_trauma` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史外伤',
  `past_blood` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史输血',
  `past_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史描述',
  `allergy_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏史描述',
  `source_tj_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人来源途径代码（LYTJ）',
  `source_tj_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人来源途径备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `total_out` int(11) NULL DEFAULT NULL COMMENT '累计门诊次数',
  `total_in` int(11) NULL DEFAULT NULL COMMENT '累计住院次数',
  `outpt_last_visit_time` datetime(0) NULL DEFAULT NULL COMMENT '门诊最后就诊时间',
  `inpt_last_visit_time` datetime(0) NULL DEFAULT NULL COMMENT '住院最后就诊时间',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `preferential_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类别ID',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cert_no`(`cert_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、发卡状态 （0未发卡；1已发卡）\r\n2、注册方式:0网上注册；1身份证注册）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_rate
-- ----------------------------
DROP TABLE IF EXISTS `base_rate`;
CREATE TABLE `base_rate`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `daily_times` decimal(11, 4) NULL DEFAULT NULL COMMENT '每日次数',
  `exec_interval` decimal(11, 4) NULL DEFAULT 1.0000 COMMENT '执行周期',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_rate_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nrate：频率、频次\r\n\r\n表说明：\r\n                      ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_special_calc
-- ----------------------------
DROP TABLE IF EXISTS `base_special_calc`;
CREATE TABLE `base_special_calc`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `drug_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品编码（表base_drug）',
  `trunc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取整方式代码（QZFS）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base_special_calc_01`(`hosp_code`, `dept_code`, `drug_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nspecial：特殊药品计费表\r\ncalc：计算\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_supplier
-- ----------------------------
DROP TABLE IF EXISTS `base_supplier`;
CREATE TABLE `base_supplier`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商类别代码（GYSLB）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `abbr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商简称',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商全称',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `fax` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真',
  `post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户账号',
  `tax_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纳税号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `abbr_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称拼音码',
  `abbr_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称五笔码',
  `name_pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全称拼音码',
  `name_wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全称五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `company_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位类型：1.供应商 2.平级单位（DWLX）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_supplier_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nsupplier：供应商\r\n\r\n表说明：\r\n                    ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_window
-- ----------------------------
DROP TABLE IF EXISTS `base_window`;
CREATE TABLE `base_window`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编码（表base_dept）',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '窗口编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '窗口名称',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_base_window_01`(`hosp_code`, `code`) USING BTREE,
  INDEX `idx_base_window_02`(`hosp_code`, `dept_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nwindow：药房窗口\r\n\r\n表说明：\r\n                     ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clinic_path_exec
-- ----------------------------
DROP TABLE IF EXISTS `clinic_path_exec`;
CREATE TABLE `clinic_path_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `clinical_path_stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '入径状态表id',
  `list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径目录ID(clinic_path_list.id)',
  `stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径阶段ID(clinic_path_stage.id)',
  `detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径阶段明细ID(clinic_path_stage_detail.id)',
  `detail_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径明细与医院项目对应ID(clinic_path_stage_detail_item.id)',
  `is_exec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否执行(SF)',
  `exec_time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人姓名',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目分类(XMFL):1诊疗；2医嘱；3；护理； 9其他 ',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID(clinic_path_stage_detail_item.item_id)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '临床路径执行情况表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clinical_path_item
-- ----------------------------
DROP TABLE IF EXISTS `clinical_path_item`;
CREATE TABLE `clinical_path_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码 根据票据规则：临床路径项目生成规则（LCXM）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '临床项目分类(LCXMFL):1诊疗；2医嘱；3；护理； 9其他 ',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_valid` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效SF',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_clinic_path_stage_detail_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '临床路径项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clinical_path_list
-- ----------------------------
DROP TABLE IF EXISTS `clinical_path_list`;
CREATE TABLE `clinical_path_list`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径编号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用范围（MBLX），0全院，1科室',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用科室ID（科室）',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用科室名称（科室）',
  `sort_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序编号',
  `diagnose_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '诊断ID集合（多个用逗号分开）',
  `min_inpt_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小住院天数',
  `max_inpt_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最大住院天数',
  `min_price` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小费用参考标准',
  `max_price` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最大费用参考标准',
  `is_free_entry` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否可以录入非路径内医嘱：0否、1是（SF）',
  `print_colunm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印几列格式(DYLS):1一列,2二列,3三列,4四列,5五列',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_clinic_path_list_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：临床路径目录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clinical_path_stage
-- ----------------------------
DROP TABLE IF EXISTS `clinical_path_stage`;
CREATE TABLE `clinical_path_stage`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径目录ID(clinic_path_list.id)',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段编号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段名称',
  `describe` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段描述',
  `min_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小天数',
  `max_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最大天数',
  `time_unit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间单位(SJDW):0天,1小时',
  `sort_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序编号',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_operation_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否手术日(预留)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_clinic_path_stage_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：临床路径阶段描述' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clinical_path_stage_detail
-- ----------------------------
DROP TABLE IF EXISTS `clinical_path_stage_detail`;
CREATE TABLE `clinical_path_stage_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径目录ID(clinic_path_list.id)',
  `stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径阶段ID(clinic_path_stage.id)',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '明细编号',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目分类(LCXMFL):1诊疗；2医嘱；3；护理； 9其他 ',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目目录ID(clinic_path_item.id)',
  `item_supply_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目补充分类(XMBCFL) 0长嘱，1临嘱，2出院',
  `is_must` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否必要(SF)',
  `classify` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统归类(XTGL)：1医嘱 2病历 3三测单 4 一般护理记录单',
  `sort_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序编号',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_operation_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否手术日(预留)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_clinic_path_stage_detail_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：临床路径阶段明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clinical_path_stage_detail_item
-- ----------------------------
DROP TABLE IF EXISTS `clinical_path_stage_detail_item`;
CREATE TABLE `clinical_path_stage_detail_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径目录ID(clinic_path_list.id)',
  `stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径阶段ID(clinic_path_stage.id)',
  `detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径阶段明细ID(clinic_path_stage_detail.id)',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目分类(LCXMFL):1诊疗；2医嘱；3；护理； 9其他',
  `classify` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统归类(XTGL)：1医嘱 2病历 3三测单 4 一般护理记录单',
  `is_must` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否必要(SF)',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组内序号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱分类代码（YZFL）',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `start_exec_num` int(11) NULL DEFAULT NULL COMMENT '开嘱当日执行次数',
  `end_exec_num` int(11) NULL DEFAULT NULL COMMENT '停嘱当天执行次数',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '总数量单位（DW）',
  `herb_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药付（剂）数',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否交病人（SF）：临时医嘱',
  `yylx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药类型',
  `is_long` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否临嘱（SF）（0：长期，1：临时）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '临床路径明细与医院项目对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dr_base_drug
-- ----------------------------
DROP TABLE IF EXISTS `dr_base_drug`;
CREATE TABLE `dr_base_drug`  (
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '药品编码',
  `usual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通用名',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '剂量',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '单价',
  `split_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_ratio` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆分比',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dr_base_item
-- ----------------------------
DROP TABLE IF EXISTS `dr_base_item`;
CREATE TABLE `dr_base_item`  (
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目编码',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '项目单价',
  `one_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '一级价格',
  `two_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '二级价格',
  `three_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '三级价格',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE INDEX `idx_base_item_01`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：基础模块\r\nitem：项目基础信息表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dr_drug_stor
-- ----------------------------
DROP TABLE IF EXISTS `dr_drug_stor`;
CREATE TABLE `dr_drug_stor`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `in_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目分类',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `spec` varchar(42) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `num` decimal(11, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '计量',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '购进价',
  `sell_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '零售价',
  `split_ratio` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(11, 4) NULL DEFAULT NULL COMMENT '数量',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  `cs` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `jx` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\n导入\r\ndrug：药品库存\r\n\r\n表说明：\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dr_stor_in
-- ----------------------------
DROP TABLE IF EXISTS `dr_stor_in`;
CREATE TABLE `dr_stor_in`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_unit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_num` decimal(16, 4) NULL DEFAULT NULL,
  `buy_sprice` decimal(16, 4) NULL DEFAULT NULL,
  `sell_sprice` decimal(16, 4) NULL DEFAULT NULL,
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiry_date` date NULL DEFAULT NULL,
  `prod_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prod_code_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `split_ratio` decimal(16, 4) NULL DEFAULT NULL,
  `split_num` decimal(16, 4) NULL DEFAULT NULL,
  `split_price` decimal(16, 4) NULL DEFAULT NULL,
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reg_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_archive_logging
-- ----------------------------
DROP TABLE IF EXISTS `emr_archive_logging`;
CREATE TABLE `emr_archive_logging`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊id',
  `archive_state` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归档状态',
  `archive_time` datetime(0) NULL DEFAULT NULL COMMENT '归档时间',
  `archive_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归档人id',
  `archive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归档人姓名',
  `archive_option` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归档意见',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_archive_logging_01`(`hosp_code`, `visit_id`) USING BTREE,
  INDEX `idx_logging`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '\r\n\r\n（住院病人表中新增字段）\r\n业务逻辑：\r\n1、归档       出院病人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_borrow_record
-- ----------------------------
DROP TABLE IF EXISTS `emr_borrow_record`;
CREATE TABLE `emr_borrow_record`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借阅科室',
  `borrow_doctor` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借阅医生',
  `borrow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借阅人id',
  `borrow_in_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '住院号',
  `borrow_patient` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '病人姓名',
  `borrow_time` datetime(0) NULL DEFAULT NULL COMMENT '借阅时间',
  `borrow_duration` int(11) NULL DEFAULT NULL COMMENT '借阅期限（单位：天）',
  `is_return` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否归还',
  `revert_time` datetime(0) NULL DEFAULT NULL COMMENT '归还时间',
  `revert_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '归还人姓名',
  `revert_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '归还人id',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '借阅原因',
  `crte_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `crte_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

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
  `page_print_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页面打印格式编码',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文档类型代码(YWLX)',
  `doc_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文档类别代码(WDLB)',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别限制代码',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '五笔码',
  `seq` int(32) NULL DEFAULT NULL COMMENT '顺序号',
  `is_del_nullline` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否自动删除空行',
  `is_common` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否常用文档',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否末级文档',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否有效',
  `is_unique` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否唯一',
  `is_page_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否换页打印',
  `is_audit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否需要审核',
  `is_hosp` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否全院病历',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用科室id',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `record_time_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历记录时间',
  `valid_time` int(32) NULL DEFAULT NULL COMMENT '电子病历时效性（小时）',
  `insure_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医保节点',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_classify_01`(`hosp_code`, `dept_id`) USING BTREE,
  INDEX `idx_emr_classify_02`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify：文档分类\r\n\r\n表说明：' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_classify_element
-- ----------------------------
DROP TABLE IF EXISTS `emr_classify_element`;
CREATE TABLE `emr_classify_element`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `classinfo_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档分类编码（emr_classify.code）',
  `element_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '元素编码（emr_element.code）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用科室id',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_classify_element_01`(`hosp_code`, `classinfo_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify：文档分类\r\nelement：元素\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_classify_template
-- ----------------------------
DROP TABLE IF EXISTS `emr_classify_template`;
CREATE TABLE `emr_classify_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `classify_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历文档分类编码（emr_classify.code）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用科室id',
  `template_html` mediumblob NULL COMMENT '模板文件',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_classify_template_01`(`hosp_code`, `classify_code`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nclassify: 文档分类\r\ntemplate: ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_doc_template
-- ----------------------------
DROP TABLE IF EXISTS `emr_doc_template`;
CREATE TABLE `emr_doc_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `template_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模板名称',
  `gxfw` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '共享范围',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '部门编码',
  `classify_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文档分类编码',
  `emr_json` json NULL COMMENT 'json格式病历',
  `nr_html` mediumblob NULL COMMENT '完整html格式病历',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_doc_template_01`(`hosp_code`, `classify_code`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_element
-- ----------------------------
DROP TABLE IF EXISTS `emr_element`;
CREATE TABLE `emr_element`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父元素编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元素编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元素名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元素类型代码（YSLX）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '五笔码',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否末级元素',
  `is_require` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否必填',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否有效',
  `is_single_template` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否单项模板',
  `default_vlaue` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '默认值',
  `min_value` decimal(11, 4) NULL DEFAULT NULL COMMENT '数值下限',
  `max_value` decimal(11, 4) NULL DEFAULT NULL COMMENT '数值上限',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别限制代码',
  `min_age` int(11) NULL DEFAULT NULL COMMENT '最小年龄',
  `max_age` int(11) NULL DEFAULT NULL COMMENT '最大年龄',
  `patient_code_ref` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病人信息关联项代码（BRGLX）',
  `sys_code_ref` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '系统参数关联项(关联系统码表)',
  `sys_code_default` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '系统参数默认值',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `is_sys_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否获取当前时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_emr_element_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_element_match
-- ----------------------------
DROP TABLE IF EXISTS `emr_element_match`;
CREATE TABLE `emr_element_match`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '医院编码',
  `emr_element_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医院病历元素ID',
  `emr_element_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医院病历元素编码',
  `emr_element_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医院病历元素名称',
  `insure_emr_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医保病历元素ID',
  `insure_emr_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医保病历元素编码',
  `insure_emr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医保病历元素名称',
  `insure_up_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属医保上级目录',
  `crte_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_element_template
-- ----------------------------
DROP TABLE IF EXISTS `emr_element_template`;
CREATE TABLE `emr_element_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '部门编码',
  `mb_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单项模板名称',
  `gxfw` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '共享范围',
  `element_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '元素编码',
  `mbnr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单项模板内容',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_element_template_01`(`hosp_code`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient`;
CREATE TABLE `emr_patient`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '就诊id',
  `classify_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历模板ID（emr_classify_template.id）',
  `patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历记录ID（emr_patient_record.id）',
  `patient_html_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历内容ID（emr_patient_html.id）',
  `sc_patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '上次病历记录ID（emr_patient_record.id）',
  `sc_patient_html_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '上次病历内容ID（emr_patient_html.id）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用科室id',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送审状态代码（SHZT）',
  `review_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送审人id',
  `review_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送审人姓名',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '送审时间',
  `is_specify` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否指定审核人（SF）',
  `specify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '指定审核人id',
  `specify_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '指定审核人姓名',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实际审核人id',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实际审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '实际审核时间',
  `audit_option` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核意见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `record_time` datetime(0) NULL DEFAULT NULL,
  `print_num` int(10) NULL DEFAULT NULL COMMENT '病历打印次数',
  `emr_insure_upload` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '电子病历是否上传医保',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_patient_01`(`hosp_code`, `visit_id`) USING BTREE,
  INDEX `idx_petient`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\npatient：病人\r\n\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_html
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_html`;
CREATE TABLE `emr_patient_html`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '就诊id',
  `patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历病人ID（emr_patient.id）',
  `classify_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历模板ID（emr_classify_template.id）',
  `patient_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历记录ID（emr_patient_record.id）',
  `html` mediumblob NULL COMMENT '病历内容',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送审状态代码（SHZT）',
  `review_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送审人id',
  `review_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送审人姓名',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '送审时间',
  `is_specify` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否指定审核人（SF）',
  `specify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '指定审核人id',
  `specify_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '指定审核人姓名',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实际审核人id',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实际审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '实际审核时间',
  `audit_option` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核意见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_patient_html_01`(`hosp_code`, `visit_id`, `patient_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\npatient：病人\r\nsub：送审\r\nl' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_patient_record
-- ----------------------------
DROP TABLE IF EXISTS `emr_patient_record`;
CREATE TABLE `emr_patient_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '就诊id',
  `patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历病人ID（emr_patient.id）',
  `classify_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历模板ID（emr_classify_template.id）',
  `patient_html_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '病历内容ID（emr_patient_html.id）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `emr_json` json NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_emr_patient_record_01`(`hosp_code`, `visit_id`, `patient_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\npatient：病人\r\n\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_print
-- ----------------------------
DROP TABLE IF EXISTS `emr_print`;
CREATE TABLE `emr_print`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医院编码',
  `emr_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '病历id',
  `visit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '就诊id',
  `emr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '病历名称',
  `print_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '打印人',
  `print_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '打印人id',
  `print_time` datetime(0) NULL DEFAULT NULL COMMENT '打印时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_print_setting
-- ----------------------------
DROP TABLE IF EXISTS `emr_print_setting`;
CREATE TABLE `emr_print_setting`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '格式编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '格式名称',
  `width` decimal(11, 4) NULL DEFAULT NULL COMMENT '页宽',
  `height` decimal(11, 4) NULL DEFAULT NULL COMMENT '页高',
  `margin_left` decimal(11, 4) NULL DEFAULT NULL COMMENT '左边距',
  `margin_right` decimal(11, 4) NULL DEFAULT NULL COMMENT '右边距',
  `margin_up` decimal(11, 4) NULL DEFAULT NULL COMMENT '上边距',
  `margin_under` decimal(11, 4) NULL DEFAULT NULL COMMENT '下边距',
  `is_infeed` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否横向（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_emr_print_setting_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nemr:电子病历缩写\r\nprint: 打印\r\nsetting：设置\r\n                                      -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_quality_aging
-- ----------------------------
DROP TABLE IF EXISTS `emr_quality_aging`;
CREATE TABLE `emr_quality_aging`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `emr_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历模板编码',
  `tips_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '提示类型代码(BL_TSLX:1单次,2循环)',
  `datum_time` decimal(14, 0) NULL DEFAULT NULL COMMENT '基准时间代码(BL_JZSJ:1入院时间,2出院时间,3转科时间,4手术时间,5医嘱时间)',
  `time_out` decimal(14, 0) NULL DEFAULT NULL COMMENT '超时时间(小时)',
  `advice_list` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医嘱ID列表',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病历质控-时效控制' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emr_quality_data_rules
-- ----------------------------
DROP TABLE IF EXISTS `emr_quality_data_rules`;
CREATE TABLE `emr_quality_data_rules`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `emr_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历模板ID',
  `rules_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '规则描述',
  `rules_sql` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '规则SQL',
  `tips` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '规则提示语',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '是否有效：0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病历质控-数据规则控制' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advance_pay
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advance_pay`;
CREATE TABLE `inpt_advance_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `ap_order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预交单号',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '预交金额',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否结算（SF）',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算id',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冲红id',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结缴款ID',
  `source_pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `cheque_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支票号码（支付方式为支票时：显示必填）',
  `service_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '手续费（支付方式为POS时：显示选填，默认0）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advance_pay_01`(`hosp_code`, `visit_id`, `ap_order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice`;
CREATE TABLE `inpt_advice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱单号',
  `iat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `iatd_group_no` int(11) NULL DEFAULT NULL COMMENT '模板内组号',
  `iatd_group_seq_no` int(11) NULL DEFAULT NULL COMMENT '模板组内序号',
  `iatd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板明细ID',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊/住院科室id',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开嘱科室ID',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组内序号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱分类代码（YZLB）',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `start_exec_num` int(11) NULL DEFAULT NULL COMMENT '开嘱当日执行次数',
  `end_exec_num` int(11) NULL DEFAULT NULL COMMENT '停嘱当天执行次数',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱内容',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '总数量单位（DW）',
  `herb_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药付（剂）数',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否阳性（SF）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药药房ID',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `plan_stop_time` datetime(0) NULL DEFAULT NULL COMMENT '医嘱预停时间（长期）',
  `technology_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技申请单号',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否交病人（SF）：临时医嘱',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一执行人姓名',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人id',
  `second_exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人姓名',
  `long_start_time` datetime(0) NULL DEFAULT NULL COMMENT '医嘱开始时间，长期医嘱生效时间',
  `last_exec_time` datetime(0) NULL DEFAULT NULL COMMENT '最近执行时间',
  `teach_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '带教医生id',
  `teach_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '带教医生姓名',
  `is_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否核收（SF）',
  `check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱核收人ID',
  `check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱核收人姓名',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '医嘱核收时间',
  `is_stop_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否停嘱核收（SF）',
  `stop_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱医生ID',
  `stop_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱医生姓名',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '停嘱时间',
  `stop_check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱核收人ID',
  `stop_check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱核收人姓名',
  `stop_check_time` datetime(0) NULL DEFAULT NULL COMMENT '停嘱核收时间',
  `is_long` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否临嘱（SF）（0：长期，1：临时）',
  `is_stop` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否停嘱（SF）',
  `is_reject` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否拒收（SF）',
  `reject_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拒收备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人/开嘱医生ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人/开嘱医生姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建/录入时间',
  `skin_durg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品ID',
  `skin_phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品药房ID',
  `skin_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品单位代码（DW）',
  `source_ia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试来源ID',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `submit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交人ID',
  `submit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交人',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `advice_prefix` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱前缀',
  `advice_suffix` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱后缀',
  `collect_blood_time` datetime(0) NULL DEFAULT NULL COMMENT '预计采血时间',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消人ID',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消人',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '取消时间',
  `skin_result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试结果(PSJG)',
  `yylx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药类型 ',
  `advance_days` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提前领药天数',
  `is_insure_upload` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上传至医保',
  `stage_detail_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '临床路径项目明细ID(clinic_path_stage_detail_item.id)',
  `clinical_path_stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入径状态表id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_inpt_advice_01`(`hosp_code`, `order_no`) USING BTREE,
  INDEX `idx_inpt_advice_02`(`hosp_code`, `visit_id`) USING BTREE,
  INDEX `idx_inpt_advice_03`(`source_ia_id`, `hosp_code`) USING BTREE,
  INDEX `idx_inpt_advice_04`(`exec_dept_id`, `is_check`) USING BTREE,
  INDEX `idx_inpt_advice_technology_no`(`technology_no`) USING BTREE,
  INDEX `idx_inpt_advice_05`(`hosp_code`, `is_check`, `is_stop`, `usage_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_detail
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_detail`;
CREATE TABLE `inpt_advice_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `ia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院医嘱ID（inpt_advice.id）',
  `ba_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱目录ID（base_advice.id）',
  `ia_group_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组号',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目数量单位（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目总金额',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源方式代码（FYLYFS）',
  `lmt_user_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '限制使用标志',
  `lim_user_explain` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '限制使用说明',
  `is_reimburse` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否报销',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advice_detail_01`(`hosp_code`, `visit_id`, `ia_id`) USING BTREE,
  INDEX `idx_inpt_advice_detail_02`(`ia_id`, `hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '来源方式代码（FYLYFS）：6：医嘱 3：动静态计费 9：其他' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_exec
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_exec`;
CREATE TABLE `inpt_advice_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `advice_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开嘱科室ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `plan_exec_time` datetime(0) NULL DEFAULT NULL COMMENT '计划执行时间',
  `exec_time` datetime(0) NULL DEFAULT NULL COMMENT '实际执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一执行人姓名',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人ID',
  `second_exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人姓名',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否阳性（SF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打印（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `skin_result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试结果(PSJG)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advice_exec_01`(`hosp_code`, `advice_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医嘱执行记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_print
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_print`;
CREATE TABLE `inpt_advice_print`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `ia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院医嘱ID（inpt_advice.id）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱单号',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组内序号',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱内容',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否阳性（SF）',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一执行人姓名',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人id',
  `second_exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人姓名',
  `last_exec_time` datetime(0) NULL DEFAULT NULL COMMENT '最近执行时间',
  `check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱核收人ID',
  `check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱核收人姓名',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '医嘱核收时间',
  `stop_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱医生ID',
  `stop_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱医生姓名',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '停嘱时间',
  `stop_check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱核收人ID',
  `stop_check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停嘱核收人姓名',
  `stop_check_time` datetime(0) NULL DEFAULT NULL COMMENT '停嘱核收时间',
  `is_long` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否临嘱（SF）（0：长期，1：临时）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人/开嘱医生ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人/开嘱医生姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建/录入时间',
  `is_in_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '开嘱信息是否打印',
  `is_stop_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '停嘱信息是否打印',
  `is_exec_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '执行信息是否打印',
  `is_skin_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '皮试信息是否打印',
  `is_in_check_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '开嘱核收信息是否打印',
  `is_stop_check_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '停嘱核收信息是否打印',
  `long_start_time` datetime(0) NULL DEFAULT NULL COMMENT '医嘱开始时间，长期医嘱生效时间',
  `skin_result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试结果(PSJG)',
  `seq_no` int(32) NULL DEFAULT NULL COMMENT '医嘱打印序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消人id',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消人',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '取消时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advice_print_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_temp
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_temp`;
CREATE TABLE `inpt_advice_temp`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板类型代码（MBLX），0全院，1科室，2个人',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用科室ID（科室、个人）',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用科室名称（科室、个人）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用医生ID（个人）',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用医生名称（个人）',
  `herb_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药付（剂）数',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advice_temp_01`(`hosp_code`, `type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院医嘱模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_advice_temp_detail
-- ----------------------------
DROP TABLE IF EXISTS `inpt_advice_temp_detail`;
CREATE TABLE `inpt_advice_temp_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `iat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '模板组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '模板组内序号',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(11, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(11, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '总金额',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否交病人（SF），交病人默认为临嘱',
  `is_long` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否临嘱（SF）（0：长期，1：临时）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模版内容',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别代码（CFLB）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '总数量单位（DW）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advice_temp_detail_01`(`hosp_code`, `iat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院处方模板明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_baby
-- ----------------------------
DROP TABLE IF EXISTS `inpt_baby`;
CREATE TABLE `inpt_baby`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿编号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `birth_time` datetime(0) NULL DEFAULT NULL COMMENT '出生时间',
  `weight` decimal(14, 4) NULL DEFAULT NULL COMMENT '出生体重（G）',
  `height` decimal(14, 4) NULL DEFAULT NULL COMMENT '出生身高（CM）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生时情况',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算类型代码（JSLX）',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否作废（SF）',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作废人id',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作废人姓名',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '作废时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_baby_01`(`hosp_code`, `visit_id`) USING BTREE,
  INDEX `index_inpt_baby_02`(`hosp_code`, `id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '结算类型代码：0：正常结算 1：中途结算 2：新生儿结算 3：其它结算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_bed_change
-- ----------------------------
DROP TABLE IF EXISTS `inpt_bed_change`;
CREATE TABLE `inpt_bed_change`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `change_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异动类型代码（YDLX）',
  `before_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前床号ID',
  `before_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前床号名称',
  `after_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后床号ID',
  `after_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后床号名称',
  `before_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前科室ID',
  `before_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前科室名称',
  `after_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后科室ID',
  `after_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后科室名称',
  `before_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前病区ID',
  `before_ward_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前病区名称',
  `after_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后病区ID',
  `after_ward_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后病区名称',
  `before_zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前主治医生ID',
  `before_zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前主治医生姓名',
  `after_zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后主治医生ID',
  `after_zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后主治医生姓名',
  `before_jz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前经治医生ID',
  `before_jz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前经治医生姓名',
  `after_jz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后经治医生ID',
  `after_jz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后经治医生姓名',
  `before_zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前主管医生ID',
  `before_zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前主管医生姓名',
  `after_zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后主管医生ID',
  `after_zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后主管医生姓名',
  `before_resp_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前责任护士ID',
  `before_resp_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换前责任护士姓名',
  `after_resp_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后责任护士ID',
  `after_resp_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '换后责任护士姓名',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '终止时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_bed_change_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '护理记录明细表\r\n\r\n\r\n异动类型代码：0、安床，1、换床，2、包床，3、:转科，4、释放' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_clinical_path_stage
-- ----------------------------
DROP TABLE IF EXISTS `inpt_clinical_path_stage`;
CREATE TABLE `inpt_clinical_path_stage`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `clinical_path_stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入径状态表id',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径目录ID(clinic_path_list.id)',
  `stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临床路径阶段ID(clinic_path_stage.id)',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录名称',
  `illness_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病情状态(BQZT)（0:正常 1正变异 2 负变异）',
  `variation_reason` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变异原因分类(BYYY)',
  `variation_remarke` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变异原因补充',
  `begin_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '阶段开始时间',
  `end_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '阶段结束时间',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '病人阶段病情记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_clinical_path_state
-- ----------------------------
DROP TABLE IF EXISTS `inpt_clinical_path_state`;
CREATE TABLE `inpt_clinical_path_state`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `path_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '临床路径状态(LJZT) 1路径内；2完成路径, 3退出路径',
  `start_stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入径开始阶段',
  `start_stage_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入径开始阶段名称',
  `list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前路径目录ID(clinic_path_list.id)',
  `path_crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入径创建人ID',
  `path_crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入径创建人姓名',
  `path_crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '入径创建时间',
  `stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前阶段ID(clinic_path_stage.id)',
  `stage_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前阶段名称',
  `end_stage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出径阶段ID(clinic_path_stage.id)',
  `end_stage_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出径阶段名称',
  `end_crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出径人ID',
  `end_crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出径人姓名',
  `end_crte_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '出径时间',
  `end_path_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出径方式(CJFS)0出院；1变异',
  `end_path_remarke` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出径备注',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '路径内发生费用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '临床路径病人记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_consultation_apply
-- ----------------------------
DROP TABLE IF EXISTS `inpt_consultation_apply`;
CREATE TABLE `inpt_consultation_apply`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID(inpt_advice.id)',
  `consul_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊申请单号',
  `consul_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊申请状态(HZZT:0保存,1审核,2完成,3作废)',
  `consul_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊类型(HZLX:0常规会诊,1急会诊)',
  `consul_time` datetime(0) NULL DEFAULT NULL COMMENT '会诊时间',
  `consul_addr` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊地址',
  `apply_deptid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `apply_userid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人ID',
  `apply_username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人姓名',
  `illness` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病情描述',
  `diagno_treat` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊疗情况',
  `consul_reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊原因',
  `consul_objective` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊目的',
  `consul_deptid_list` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊科室ID合集',
  `consul_userid_list` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会诊医生ID合集',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `finish_userid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '完成人ID',
  `finish_username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '完成人姓名',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会诊申请记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_cost
-- ----------------------------
DROP TABLE IF EXISTS `inpt_cost`;
CREATE TABLE `inpt_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `iat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `iatd_group_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组号',
  `iatd_seq_no` int(11) NULL DEFAULT NULL COMMENT '医嘱组内序号',
  `advice_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱执行签名ID（inpt_advice_exec.id）',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用来源方式代码（FYLYFS）',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用来源ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原费用ID',
  `source_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源科室ID',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室ID',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `total_num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '总数量单位（DW）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药付（剂）数',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目总金额',
  `preferential_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠总金额',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后总金额',
  `back_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '退药数量',
  `back_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '退药状态代码（TYZT）:0、正常，1、已退费未退药，2、已退费已退药',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开嘱医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开嘱医生姓名',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开嘱科室ID',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `is_dist` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否已发药（SF）',
  `is_give` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否交病人（SF）：临时医嘱',
  `is_ok` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否确费：0、未确认，1、已确认',
  `ok_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确费人ID',
  `ok_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确费人姓名',
  `ok_time` datetime(0) NULL DEFAULT NULL COMMENT '确费时间',
  `settle_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '结算状态代码： 0未结算，1预结算，2已结算',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID（outpt_settle）',
  `is_check` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否核对（SF）',
  `check_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用核对人ID',
  `check_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用核对人姓名',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '费用核对时间',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主治医生名称',
  `jz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经治医生ID',
  `jz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经治医生名称',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主管医生名称',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人姓名',
  `exec_time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `plan_exec_time` datetime(0) NULL DEFAULT NULL COMMENT '计划执行时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `is_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否已记账（SF）',
  `cost_time` datetime(0) NULL DEFAULT NULL COMMENT '计费时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_wait` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否生成待领表',
  `distribute_all_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批次发药汇总id',
  `feedetl_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传到医保的费用序列号',
  `is_hosp_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可报销',
  `lmt_user_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否限制级用药',
  `lim_user_explain` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制用药说明',
  `is_reimburse` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否报销',
  `attribution_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用类型代码（FYLXDM）',
  `is_upload` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '招采商品销售是否上传(SF)',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家编码',
  `nation_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_cost_01`(`visit_id`) USING BTREE,
  INDEX `idx_inpt_cost_02`(`settle_id`) USING BTREE,
  INDEX `idx_inpt_cost_03`(`item_id`) USING BTREE,
  INDEX `idx_inpt_cost_04`(`cost_time`) USING BTREE,
  INDEX `idx_inpt_cost_05`(`iat_id`) USING BTREE,
  INDEX `idx_inpt_cost_06`(`distribute_all_detail_id`) USING BTREE,
  INDEX `idx_inpt_cost_07`(`source_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院费用表\r\n费用来源方式代码：\r\n门诊使用：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试，5：皮试换药药品，\r\n住院使用：6、医嘱,，7、为长期记账；8、补记账\r\n9：其他费用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_cost_settle
-- ----------------------------
DROP TABLE IF EXISTS `inpt_cost_settle`;
CREATE TABLE `inpt_cost_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID（inpt_settle.id）',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用ID（inpt_cost.id）',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_cost_settle_01`(`settle_id`, `cost_id`) USING BTREE,
  INDEX `idx_inpt_cost_settle_02`(`cost_id`, `visit_id`, `reality_price`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、费用来源方式代码：\r\n门诊使用：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试，5' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_critical_values
-- ----------------------------
DROP TABLE IF EXISTS `inpt_critical_values`;
CREATE TABLE `inpt_critical_values`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `critical_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危机项目id',
  `critical_item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危机项目名称',
  `jyckz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验参考值',
  `jyjgz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验结果值',
  `jyjgdw` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验结果单位',
  `jyzt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验状态代码',
  `wjzzt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危急值状态代码',
  `jyff` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验方法代码',
  `wjsm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危急说明',
  `wjsj` datetime(0) NULL DEFAULT NULL COMMENT '危急时间',
  `is_wjcl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否危急处理',
  `wjclrid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危急处理人ID',
  `wjclrxm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危急处理人姓名',
  `wjclrsj` datetime(0) NULL DEFAULT NULL COMMENT '危急处理人时间',
  `wjclbz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '危急处理备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_critical_values_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `inpt_diagnose`;
CREATE TABLE `inpt_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `is_main` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否主诊断（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `custom_disease` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcm_syndromes_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医症候Id',
  `tcm_syndromes_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医症候名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_diagnose_01`(`hosp_code`, `visit_id`) USING BTREE,
  INDEX `idx_inpt_diagnose_02`(`disease_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院诊断表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_guarantee
-- ----------------------------
DROP TABLE IF EXISTS `inpt_guarantee`;
CREATE TABLE `inpt_guarantee`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `guarantee_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '担保单号',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '担保金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `guarantee_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '担保人ID',
  `guarantee_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '担保人姓名',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_advance_pay_01`(`hosp_code`, `visit_id`, `guarantee_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '住院担保管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_insure_pay
-- ----------------------------
DROP TABLE IF EXISTS `inpt_insure_pay`;
CREATE TABLE `inpt_insure_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同单位明细代码（HTDW）',
  `org_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构名称',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保报销总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_insure_pay_01`(`settle_id`) USING BTREE,
  INDEX `idx_inpt_insure_pay_02`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同单位明细代码：\r\n0、个人账户，1、基本医疗，2、补充医疗，3、民政，4、协议支付' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_long_cost
-- ----------------------------
DROP TABLE IF EXISTS `inpt_long_cost`;
CREATE TABLE `inpt_long_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目总金额',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药房ID',
  `last_exec_time` datetime(0) NULL DEFAULT NULL COMMENT '最近执行日期',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '本次计费时间',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `source_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源类型代码（LYLX）',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源ID（床位ID）',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否取消（SF）',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消人ID',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消人姓名',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '取消时间',
  `cancel_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `attribution_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用类型代码（FYLXDM）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_long_cost_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '来源类型代码（LYLX）：0、床位，1、录入，2、包床\r\n\r\n录入：只能录入项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_medicine_advance
-- ----------------------------
DROP TABLE IF EXISTS `inpt_medicine_advance`;
CREATE TABLE `inpt_medicine_advance`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID',
  `advance_days` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提前领药天数',
  `type_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药类型(领药单据类型ID)',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `sfpy` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否配药（0未配药，1已配药）',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `crte_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_medicine_advance_advice
-- ----------------------------
DROP TABLE IF EXISTS `inpt_medicine_advance_advice`;
CREATE TABLE `inpt_medicine_advance_advice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `advance_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提前领药记录ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `hosp_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_medicine_advance_advice_01`(`advice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_nurse_record
-- ----------------------------
DROP TABLE IF EXISTS `inpt_nurse_record`;
CREATE TABLE `inpt_nurse_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿id',
  `bno_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理单据ID（base_nursing_order.id）',
  `pg_index` int(11) NULL DEFAULT NULL COMMENT '页码',
  `first_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一签名护士ID',
  `first_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一签名护士姓名',
  `second_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二签名护士ID',
  `second_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二签名护士姓名',
  `is_day_sum` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否日间小结（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `delete_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人ID',
  `delete_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人姓名',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `day_sum_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日间小结人ID',
  `day_sum_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日间小结姓名',
  `day_sum_time` datetime(0) NULL DEFAULT NULL COMMENT '日间小结时间',
  `teacher_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '带教护士ID',
  `teacher_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '带教护士姓名',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '组号',
  `group_seq_no` int(1) NULL DEFAULT NULL COMMENT '组内序号',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否尾行(SF)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `item_001` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目001',
  `item_002` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目002',
  `item_003` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目003',
  `item_004` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目004',
  `item_005` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目005',
  `item_006` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目006',
  `item_007` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目007',
  `item_008` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目008',
  `item_009` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目009',
  `item_010` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目010',
  `item_011` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目011',
  `item_012` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目012',
  `item_013` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目013',
  `item_014` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目014',
  `item_015` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目015',
  `item_016` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目016',
  `item_017` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目017',
  `item_018` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目018',
  `item_019` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目019',
  `item_020` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目020',
  `item_021` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目021',
  `item_022` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目022',
  `item_023` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目023',
  `item_024` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目024',
  `item_025` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目025',
  `item_026` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目026',
  `item_027` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目027',
  `item_028` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目028',
  `item_029` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目029',
  `item_030` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目030',
  `item_031` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目031',
  `item_032` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目032',
  `item_033` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目033',
  `item_034` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目034',
  `item_035` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目035',
  `item_036` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目036',
  `item_037` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目037',
  `item_038` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目038',
  `item_039` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目039',
  `item_040` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目040',
  INDEX `idx_inpt_nurse_record_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '护理记录明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_nurse_template
-- ----------------------------
DROP TABLE IF EXISTS `inpt_nurse_template`;
CREATE TABLE `inpt_nurse_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板内容',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_nurse_third
-- ----------------------------
DROP TABLE IF EXISTS `inpt_nurse_third`;
CREATE TABLE `inpt_nurse_third`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿id',
  `record_time` datetime(0) NULL DEFAULT NULL COMMENT '护理记录时间',
  `in_num` int(11) NULL DEFAULT NULL COMMENT '住院天数',
  `temperature_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体温测量仪代码（TWCLY）',
  `temperature` decimal(14, 4) NULL DEFAULT NULL COMMENT '体温',
  `temperature_retest` decimal(14, 4) NULL DEFAULT NULL COMMENT '复测体温',
  `pulse` int(11) NULL DEFAULT NULL COMMENT '脉搏',
  `heart_rate` int(11) NULL DEFAULT NULL COMMENT '心率',
  `blood_sugar` decimal(14, 4) NULL DEFAULT NULL COMMENT '血糖',
  `is_ventilator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否使用呼吸机（SF）',
  `breath` int(11) NULL DEFAULT NULL COMMENT '呼吸',
  `forty_up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '40°以上体温代码（FTTW）',
  `forty_up_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '40°以上体温备注',
  `thirty_five_down_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '35°以下体温代码（TFTW）',
  `thirty_five_down_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '35°以下体温备注',
  `intake` decimal(14, 4) NULL DEFAULT NULL COMMENT '入量（ml）',
  `output` decimal(14, 4) NULL DEFAULT NULL COMMENT '出量（ml）',
  `other_output` decimal(14, 4) NULL DEFAULT NULL COMMENT '其他（ml）',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他备注',
  `skin_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试结果（PSJG）',
  `excrement_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大便次数代码（XBCS）',
  `pee_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小便次数代码（DBCS）',
  `height` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身高',
  `weight` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体重',
  `girth` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '腹围',
  `am_bp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上午血压（mmHg）',
  `pm_bp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下午血压（mmHg）',
  `is_operation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否手术（SF）',
  `operation_days` int(11) NULL DEFAULT NULL COMMENT '手术后天数',
  `operation_cnt` int(11) NULL DEFAULT NULL COMMENT '手术次数',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `drug_allergy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_nurse_third_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '体温测量仪代码：0、腋表，1、口表，2、肛表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_adrs
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_adrs`;
CREATE TABLE `inpt_past_adrs`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品ID',
  `drug_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `adrs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '不良反应症状',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发生原因',
  `serious` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重程度',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '发生日期',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_past_adrs_01`(`hosp_code`, `profile_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_allergy
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_allergy`;
CREATE TABLE `inpt_past_allergy`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品ID',
  `drug_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `allergy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏症状',
  `serious` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重程度',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '发生日期',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_past_allergy_01`(`hosp_code`, `profile_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_drug
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_drug`;
CREATE TABLE `inpt_past_drug`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `drug_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品ID',
  `drug_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '用药开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '用药结束时间',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_past_drug_01`(`hosp_code`, `profile_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_operation
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_operation`;
CREATE TABLE `inpt_past_operation`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `chief_complaint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诉',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术ID（ICD-9）',
  `operation_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术医生ID',
  `operation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术医生名称',
  `operation_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_past_operation_01`(`hosp_code`, `profile_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_past_treat
-- ----------------------------
DROP TABLE IF EXISTS `inpt_past_treat`;
CREATE TABLE `inpt_past_treat`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `chief_complaint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诉',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_past_treat_01`(`hosp_code`, `profile_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_pay
-- ----------------------------
DROP TABLE IF EXISTS `inpt_pay`;
CREATE TABLE `inpt_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '支付金额',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票据号（微信条码号、支付宝条码号、支票号码）',
  `service_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '手续费（pos）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_pay_01`(`hosp_code`, `settle_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_settle
-- ----------------------------
DROP TABLE IF EXISTS `inpt_settle`;
CREATE TABLE `inpt_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `settle_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算类型代码（JSLX）',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后总金额',
  `trunc_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '舍入金额（存在正负金额）',
  `actual_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '实收金额',
  `self_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '个人支付金额',
  `mi_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '统筹支付金额',
  `ap_total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '预交金合计',
  `ap_offset_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '预交金冲抵',
  `settle_take_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '结算补收',
  `settle_back_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '结算退款',
  `settle_back_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算退款支付方式代码（ZFFS）',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否结算（SF）',
  `settle_time` datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冲红ID',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打印（SF）',
  `hosp_df_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医院垫付金额',
  `hosp_jm_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医院减免金额',
  `out_settle_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院结算方式代码（CYJSFS）',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结缴款ID',
  `source_pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `acct_pay` decimal(16, 2) NULL DEFAULT NULL COMMENT '个人账户金额支出',
  `credit_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '挂账金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_settle_01`(`visit_id`) USING BTREE,
  INDEX `idx_inpt_settle_02`(`settle_time`) USING BTREE,
  INDEX `idx_inpt_settle_03`(`crte_id`) USING BTREE,
  INDEX `idx_inpt_settle_04`(`settle_no`) USING BTREE,
  INDEX `idx_inpt_settle_05`(`daily_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '结算类型代码：0：正常结算 1：中途结算 2：新生儿结算 3：其它结算\r\n出院结算方式代码：0、正常结算，1、' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_settle_invoice
-- ----------------------------
DROP TABLE IF EXISTS `inpt_settle_invoice`;
CREATE TABLE `inpt_settle_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票ID',
  `invoice_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票明细ID',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票号码',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '发票总金额',
  `print_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票打印人ID',
  `print_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票打印人姓名',
  `print_time` datetime(0) NULL DEFAULT NULL COMMENT '发票打印时间',
  `print_num` int(11) NULL DEFAULT NULL COMMENT '发票打印次数',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冲红ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_settle_invoice_01`(`hosp_code`, `settle_id`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_settle_invoice_content
-- ----------------------------
DROP TABLE IF EXISTS `inpt_settle_invoice_content`;
CREATE TABLE `inpt_settle_invoice_content`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算发票ID（outpt_settle_invoice）',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院发票代码',
  `in_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院发票名称',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_settle_invoice_content_01`(`hosp_code`, `settle_invoice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inpt_visit
-- ----------------------------
DROP TABLE IF EXISTS `inpt_visit`;
CREATE TABLE `inpt_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病案号/住院档案号（入院登记回写）',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍代码（GJZD）',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业代码（ZY）',
  `education_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历代码（XL）',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人地址',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族代码（MZ）',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型代码（ZJLB）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址',
  `preferential_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类别ID',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `receive_hosp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收医院名称',
  `bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前床位ID',
  `bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前床位名称',
  `nursing_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理级别（医嘱回写）',
  `diet_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '膳食类型（医嘱回写）',
  `Illness_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病情标识（医嘱回写）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前状态代码（BRZT）',
  `in_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院病区ID',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院科室名称',
  `in_time` datetime(0) NULL DEFAULT NULL COMMENT '入院时间',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主治医生名称',
  `jz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经治医生ID',
  `jz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经治医生名称',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主管医生名称',
  `in_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院备注',
  `in_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院方式代码（RYFS）',
  `in_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院主诊断ID（base_disease）',
  `in_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院主诊断名称（base_disease）',
  `in_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院主诊断ICD-10码（base_disease）',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院情况代码（RYQK）',
  `outpt_visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊就诊号',
  `outpt_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊医生ID',
  `outpt_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊医生姓名',
  `outpt_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊主诊断ID',
  `outpt_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊主诊断名称',
  `outpt_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊主诊断ICD-10码',
  `out_ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院病区ID',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院科室名称',
  `out_time` datetime(0) NULL DEFAULT NULL COMMENT '出院时间',
  `out_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院主诊断ID',
  `out_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院主诊断名称',
  `out_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院主诊断ICD-10码',
  `out_oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院操作人ID',
  `out_oper_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院操作人姓名',
  `out_oper_time` datetime(0) NULL DEFAULT NULL COMMENT '出院操作时间',
  `out_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院情况代码（CYQK）',
  `out_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院备注',
  `out_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院方式代码（CYFS）',
  `is_archive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否归档（SF）',
  `archive_time` datetime(0) NULL DEFAULT NULL COMMENT '归档时间',
  `archive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归档人ID',
  `archive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归档人姓名',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保类型代码（CBLX）',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保机构编码',
  `insure_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保号',
  `insure_biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保业务类型编码',
  `insure_treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保待遇类型编码',
  `insure_patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保病人ID',
  `insure_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保合同单位备注',
  `total_advance` decimal(14, 4) NULL DEFAULT NULL COMMENT '累计预交金',
  `total_cost` decimal(14, 4) NULL DEFAULT NULL COMMENT '累计费用',
  `total_balance` decimal(14, 4) NULL DEFAULT NULL COMMENT '累计余额',
  `guarantee_balance` decimal(14, 4) NULL DEFAULT NULL COMMENT '担保金额',
  `resp_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '责任护士ID',
  `resp_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '责任护士名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `critical_value_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '危急值状态代码：0 正常，1、有危急值，2、已处理危急值',
  `pret_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '早产标志',
  `fetus_cnt` int(3) NULL DEFAULT NULL COMMENT '胎儿数',
  `fetts` int(3) NULL DEFAULT NULL COMMENT '胎次',
  `latechb_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '晚育标志',
  `birctrl_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划生育手术类别',
  `matn_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生育类别',
  `fpsc_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划生育服务证号',
  `total_in_days` int(3) NULL DEFAULT NULL COMMENT '住院天数',
  `total_in_count` int(3) NULL DEFAULT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_inpt_visit_01`(`in_no`) USING BTREE,
  INDEX `idx_inpt_visit_02`(`cert_no`) USING BTREE,
  INDEX `idx_inpt_visit_03`(`in_time`) USING BTREE,
  INDEX `idx_inpt_visit_04`(`out_time`) USING BTREE,
  INDEX `idx_inpt_visit_05`(`in_dept_id`) USING BTREE,
  INDEX `idx_inpt_visit_06`(`status_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '病人信息表：\r\n0、待入（办理入院登记未安床）\r\n1、在院（护士安床后在院状态）\r\n2、' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_admdvs
-- ----------------------------
DROP TABLE IF EXISTS `insure_admdvs`;
CREATE TABLE `insure_admdvs`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `admdvs_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区划编码',
  `admdvs_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '区划名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_bacterial_report
-- ----------------------------
DROP TABLE IF EXISTS `insure_bacterial_report`;
CREATE TABLE `insure_bacterial_report`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊id',
  `mdtrt_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '就医登记号',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `appy_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请单号',
  `rpotc_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告单号',
  `germ_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '细菌代号',
  `germ_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '细菌名称',
  `coly_cntg` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菌落计数',
  `clte_medm` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养基',
  `clte_time` datetime(0) NULL DEFAULT NULL COMMENT '培养时间',
  `clte_cond` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养条件',
  `exam_rslt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验结果',
  `fnd_way` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发现方式',
  `exam_org_name` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验机构名称',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志',
  `is_trans` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否传输',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_bldinfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_bldinfo`;
CREATE TABLE `insure_bldinfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保登记号',
  `bld_cat` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血品种',
  `bld_amt` int(6) NULL DEFAULT NULL COMMENT '输血量',
  `bld_unt` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血计量单位',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_bldinfo_01`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保输血信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_configuration
-- ----------------------------
DROP TABLE IF EXISTS `insure_configuration`;
CREATE TABLE `insure_configuration`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构名称',
  `reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保注册编码',
  `org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `attr_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保归属机构编码',
  `type_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保类型代码（YBLX）',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保服务请求地址路径',
  `request_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保请求方式代码（YBQQFS）',
  `time_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求超时时间',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `contact` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `is_remote` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否异地（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Servers(kafka地址)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `appId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用渠道编号',
  `mdtrtarea_admvs` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医地医保区划',
  `is_unified_pay` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否走统一支付平台(1是，0/null否)',
  `hosp_account_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院帐户名称',
  `hosp_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帐户',
  `bank_account` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `hosp_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_configuration_01`(`code`) USING BTREE,
  INDEX `idx_insure_configuration_02`(`org_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nhosp：医院\r\nconfiguration：配置\r\n                                          -' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for insure_dict
-- ----------------------------
DROP TABLE IF EXISTS `insure_dict`;
CREATE TABLE `insure_dict`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保注册编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型编码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `ext01` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段1',
  `ext02` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段2',
  `ext03` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段3',
  `medicine_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_dict_01`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\ndict：字典\r\n\r\n表说明：\r\n用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_disease
-- ----------------------------
DROP TABLE IF EXISTS `insure_disease`;
CREATE TABLE `insure_disease`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构注册编码',
  `insure_illness_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insure_illness_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心疾病编码',
  `insure_illness_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心疾病名称',
  `icd10` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `take_date` datetime(0) NULL DEFAULT NULL COMMENT '生效日期',
  `lose_date` datetime(0) NULL DEFAULT NULL COMMENT '失效日期',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `ver` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保统一支付下载数据版本号',
  `ver_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保统一支付下载数据版本名称',
  `down_load_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载病种分类',
  `size` int(6) NULL DEFAULT NULL COMMENT '每次调用下载接口的数据大小',
  `num` int(6) NULL DEFAULT NULL COMMENT '每次调用下载接口的分页',
  `record_counts` int(6) NULL DEFAULT NULL COMMENT '下载当前版本号的总数据量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_disease_01`(`insure_illness_code`) USING BTREE,
  INDEX `idx_insure_disease_02`(`down_load_type`) USING BTREE,
  INDEX `idx_insure_disease_03`(`insure_reg_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\ndiagnose：疾病\r\n\r\n表说明：\r\n                                   -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_disease_match
-- ----------------------------
DROP TABLE IF EXISTS `insure_disease_match`;
CREATE TABLE `insure_disease_match`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医保注册编码',
  `hosp_illness_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院疾病ID',
  `hosp_illness_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院ICD编码',
  `hosp_illness_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院ICD名称',
  `insure_illness_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医保中心疾病ID',
  `insure_illness_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医保中心ICD编码',
  `insure_illness_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医保中心ICD名称',
  `is_match` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否匹配（SF）',
  `is_trans` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否传输（SF）',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审批状态代码（SHZT）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `treatment_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '治疗分型',
  `oper_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术值',
  `unoper_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '非手术分值',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_disease_match_01`(`hosp_illness_code`) USING BTREE,
  INDEX `idx_insure_disease_match_02`(`insure_illness_code`) USING BTREE,
  INDEX `idx_insure_disease_match_03`(`insure_reg_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表含义：\r\ninsure：医保\r\ndiagnose：疾病\r\nmatching：匹配\r\n                                         -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_disease_oper
-- ----------------------------
DROP TABLE IF EXISTS `insure_disease_oper`;
CREATE TABLE `insure_disease_oper`  (
  `disease_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心疾病编码',
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `oper_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术分值',
  `unoper_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '非手术分值',
  `disease_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心疾病名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_diseinfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_diseinfo`;
CREATE TABLE `insure_diseinfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `diag_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断类别',
  `diag_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断代码',
  `diag_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断名称',
  `adm_cond_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院病情类型',
  `maindiag_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诊断标识',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_diseinfo_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_diseinfo_02`(`insure_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保诊断信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_doctor_info
-- ----------------------------
DROP TABLE IF EXISTS `insure_doctor_info`;
CREATE TABLE `insure_doctor_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保编码',
  `dr_codg` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师编码',
  `dr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师姓名',
  `prac_dr_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执业医师编号',
  `dr_prac_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师执业类型',
  `dr_pro_tech_duty` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师专业技术职务',
  `dr_prac_scp_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师执业范围代码',
  `pro_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业编号',
  `dcl_prof_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否申报为本市专家库成员',
  `practice_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师执业情况',
  `dr_profttl_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师职称编号',
  `psn_itro` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人能力简介',
  `mul_prac_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多点执业标志',
  `main_pracins_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主执业机构标志',
  `hosp_dept_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院科室编码',
  `bind_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定岗标志',
  `siprof_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否医保专家库成员',
  `loclprof_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否本地申报专家',
  `hi_dr_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否医保医师',
  `cert_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `certno` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_upload` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否上传',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者名称',
  `crte_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保医师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_doctor_mgtinfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_doctor_mgtinfo`;
CREATE TABLE `insure_doctor_mgtinfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保编码',
  `insure_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保上传医生信息表id',
  `dr_codg` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医师编码',
  `hi_serv_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗服务类型',
  `hi_serv_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗服务类型名称',
  `hi_serv_stas` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务状态0；正常  1：暂停',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '医疗服务开始时间',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '医疗服务结束时间',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保医师执业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_drugsensitive_report
-- ----------------------------
DROP TABLE IF EXISTS `insure_drugsensitive_report`;
CREATE TABLE `insure_drugsensitive_report`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊id',
  `mdtrt_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '就医登记号',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `appy_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请单号',
  `rpotc_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告单号',
  `germ_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '细菌代号',
  `germ_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '细菌名称',
  `sstb_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药敏代码',
  `sstb_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药敏名称',
  `reta_rslt_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抗药结果代码',
  `reta_rslt_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抗药结果',
  `ref_val` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参考值',
  `exam_mtd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验方法',
  `exam_rslt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验结果',
  `exam_org_name` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验机构名称',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志',
  `is_trans` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否传输',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保药敏记录上报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_entry_log
-- ----------------------------
DROP TABLE IF EXISTS `insure_entry_log`;
CREATE TABLE `insure_entry_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '患者就诊id',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `medicine_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人类型(BRLX)',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否住院:0否、1是（SF）',
  `inpt_visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号',
  `outpt_visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊号',
  `aac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人电脑号',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保登记号',
  `is_advice_entry` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否医嘱录入上传:0否、1是（SF）',
  `is_mris_entry` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否病案录入上传:0否、1是（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_entry_log_01`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '记录医嘱,病案上传到医保的日志信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_examinfo_report
-- ----------------------------
DROP TABLE IF EXISTS `insure_examinfo_report`;
CREATE TABLE `insure_examinfo_report`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mdtrt_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '就医登记号',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `appy_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请单号',
  `appy_doc_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请单据名称',
  `rpotc_no` varbinary(50) NULL DEFAULT NULL COMMENT '报告单号',
  `rpotc_type_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告单类别代码',
  `exam_rpotc_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查报告单名称',
  `exam_date` datetime(0) NULL DEFAULT NULL COMMENT '检查日期',
  `rpt_date` datetime(0) NULL DEFAULT NULL COMMENT '报告日期',
  `sapl_date` datetime(0) NULL DEFAULT NULL COMMENT '送检日期',
  `cma_date` datetime(0) NULL DEFAULT NULL COMMENT '采样日期',
  `spcm_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标本号',
  `spcm_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标本名称',
  `exam_type_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查类别代码',
  `exam_item_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查-项目代码',
  `exam_type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '检查-类别名称',
  `exam_item_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查-项目名称',
  `inhosp_exam_item_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '院内检查-项目代码',
  `inhosp_exam_item_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '院内检查-项目名称',
  `exam_part` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查部位',
  `exam_rslt_poit_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '检查结果阳性标志',
  `exam_rslt_abn` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查 / 检验结果异常标志',
  `exam_ccls` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查结论',
  `appy_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请机构名称',
  `appy_dept_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室代码',
  `exam_dept_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查科室代码',
  `ipt_dept_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院科室代码',
  `ipt_dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院科室名称',
  `bilg_dr_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单医生代码',
  `bilg_dr_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单医生姓名',
  `exe_org_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行机构名称',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `is_trans` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上传',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_fix_record
-- ----------------------------
DROP TABLE IF EXISTS `insure_fix_record`;
CREATE TABLE `insure_fix_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员编号',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `biz_appy_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务申请类型',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `agnter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人姓名',
  `agnter_cert_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人证件类型',
  `agnter_certno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人证件号码',
  `agnter_tel` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人联系方式',
  `agnter_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人联系地址',
  `agnter_rlts` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人关系',
  `fix_srt_no` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定点排序号',
  `fixmedins_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定点医药机构编号',
  `fixmedins_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定点医药机构名称',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `trt_dcla_detl_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇申报明细流水号',
  `cret_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '患者姓名',
  `biz_appy_type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务申请类型名称',
  `agnter_rlts_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人关系名称',
  `agnter_cert_type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '代办人证件类型名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_fix_record_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_fix_record_02`(`trt_dcla_detl_sn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保定点备案表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_function
-- ----------------------------
DROP TABLE IF EXISTS `insure_function`;
CREATE TABLE `insure_function`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保注册编码',
  `function_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能编码',
  `instance_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用实例名',
  `function_class` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能包地址（当前医保对应的医保功能号）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能号描述',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nfunction：功能号\r\n\r\n表说明：\r\n                                    -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_function_log
-- ----------------------------
DROP TABLE IF EXISTS `insure_function_log`;
CREATE TABLE `insure_function_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `msg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务流水号',
  `msg_info` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务功能号',
  `msg_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务名称',
  `in_params` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务入参',
  `out_params` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务回参',
  `code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '状态码（1：成功 0:失败 999:系统错误）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `error_msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误信息',
  `is_hospital` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否住院',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '冲正状态（1:已冲正  0：未冲正）',
  `medis_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_function_log_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_function_log_02`(`msg_info`) USING BTREE,
  INDEX `idx_insure_function_log_03`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_icuinfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_icuinfo`;
CREATE TABLE `insure_icuinfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `scs_cutd_ward_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重症监护病房类型',
  `scs_cutd_inpool_time` datetime(0) NULL DEFAULT NULL COMMENT '重症监护进入时间',
  `scs_cutd_exit_time` datetime(0) NULL DEFAULT NULL COMMENT '重症监护退出时间',
  `scs_cutd_sum_dura` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重症监护合计时长',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_icuinfo_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_icuinfo_02`(`insure_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保重症信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_basic
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_basic`;
CREATE TABLE `insure_individual_basic`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `aac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人电脑号',
  `injury_borth_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人业务号(工伤、生育)',
  `aaa027` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分级统筹中心编码',
  `aaa027_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分级统筹中心名称',
  `aac003` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `aac004` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `aac002` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公民身份号码',
  `aaz500` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '社会保障号码',
  `aae005` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `aac006` date NULL DEFAULT NULL COMMENT '出生日期',
  `orgcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区编码',
  `aab999` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位管理码',
  `aab019` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位类型',
  `aab001` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位编码',
  `bka008` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位名称',
  `bka035` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员类别编码',
  `bka035_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员类别名称',
  `aac008` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员状态编码',
  `aac008_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员状态名称',
  `bac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公务员级别编码',
  `bac001_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公务员级别名称',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `aka130_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型名称',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇类型',
  `bka006_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇类型名称',
  `aac148` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补助类型',
  `aac148_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补助类型名称',
  `aac013` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用工形式编码',
  `aac013_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用工形式名称',
  `aae140` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '险种类型（码表AAE140）',
  `bka888` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金冻结状态',
  `akc252` decimal(14, 4) NULL DEFAULT NULL COMMENT '个人帐户余额',
  `aac066` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保身份',
  `aab301` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属行政区代码.常住地',
  `aac031` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员缴费状态{1参保缴费，2暂停缴费，3终止缴费}',
  `aae030_last` datetime(0) NULL DEFAULT NULL COMMENT '上次住院入院日期',
  `aae031_last` datetime(0) NULL DEFAULT NULL COMMENT '上次住院出院日期',
  `aae030_special` datetime(0) NULL DEFAULT NULL COMMENT '特殊业务申请有效开始时间',
  `aae031_special` datetime(0) NULL DEFAULT NULL COMMENT '特殊业务申请有效结束时间',
  `aaz267` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗待遇申请事件ID',
  `baa027` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保地中心编码',
  `baa027_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保地中心名称',
  `akc193` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ICD编码',
  `akc193_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ICD名称',
  `aac158` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '低保对象标识',
  `akc026` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参加公务员医疗补助标识',
  `baa301` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保地行政区划代码(指参保人所在地的行政区划代码)',
  `aab300` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保地社会保险经办机构名称(指参保人所在地的社会保险经办机构名称)',
  `akc009` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保人员类别',
  `bka010` int(11) NULL DEFAULT NULL COMMENT '本年住院次数',
  `bkh015` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '套餐标识',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `card_iden` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡识别码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_basic_01`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nbasic：基本\r\n                                            -&#' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_business
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_business`;
CREATE TABLE `insure_individual_business`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人基本信息id',
  `aaz267` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务申请序列号',
  `bear_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生育序列号',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `aka130_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务名称',
  `voip_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务认定编号',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇类型',
  `bka006_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇名称',
  `aka083` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请内容编码',
  `aka083_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '申请内容名称',
  `aae030` datetime(0) NULL DEFAULT NULL COMMENT '申请生效日期',
  `aae031` datetime(0) NULL DEFAULT NULL COMMENT '申请失效日期',
  `aka120` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请病种编码/疾病编码',
  `aka121` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请病种名称/疾病名称',
  `vulnerability` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受伤部位',
  `pay_mark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '先行支付标志（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_business_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_business_02`(`mib_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nbusiness：业务\r\n                                               ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_cost
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_cost`;
CREATE TABLE `insure_individual_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用id',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算id',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否住院（SF）',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应医保项目类别',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应医保项目编码',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应医保项目名称',
  `guest_ratio` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自付比例',
  `primary_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '原费用',
  `apply_last_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '报销后费用',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '顺序号（以患者为单位，生成费用顺序号）',
  `transmit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传输标志（0：未传输、1：已传输）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `rxSn` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '（医保返回）处方明细 ID',
  `overlmtSelfpayAmt` decimal(14, 0) NULL DEFAULT NULL COMMENT '超限价自付金额',
  `chrgItemLv` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费项目等级',
  `fulamtOwnpayFlag` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全额自费标志',
  `ownpayRea` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自费原因',
  `hiLmtpric` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保限价',
  `fulamt_ownpay_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '全自费金额',
  `overlmt_selfpay` decimal(16, 2) NULL DEFAULT NULL COMMENT '超限价金额',
  `preselfpay_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '先行自付金额',
  `inscp_scp_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '符合政策范围金额',
  `medChrgitmType` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗收费项目类别',
  `overlmt_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '超限价金额',
  `lmt_used_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用标志',
  `pric_uplmt_amt` decimal(16, 6) NULL DEFAULT NULL COMMENT '定价上限金额',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  `insure_is_transmit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已经上传到医保  1：已经上传 0未上传',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新医保上传的费用批次号',
  `sum_fee` decimal(16, 2) NULL DEFAULT NULL COMMENT '本次上传到医保的费用总金额',
  `det_item_fee_sumamt` decimal(16, 2) NULL DEFAULT NULL COMMENT '明细项目费用总额',
  `feedetl_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传到医保的费用序列号',
  `init_feedetl_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传到医保的原费用序列号',
  `fee_start_time` datetime(0) NULL DEFAULT NULL COMMENT '上传费用开始时间',
  `fee_end_time` datetime(0) NULL DEFAULT NULL COMMENT '上传费用结束时间',
  `insure_register_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `settle_count` int(4) NULL DEFAULT 0 COMMENT '中途结算次数',
  `is_half_settle` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否中途结算',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_id`(`id`) USING BTREE,
  INDEX `idx_insure_individual_cost_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_cost_02`(`cost_id`) USING BTREE,
  INDEX `idx_insure_individual_cost_03`(`settle_id`) USING BTREE,
  INDEX `idx_insure_individual_cost_04`(`insure_settle_id`) USING BTREE,
  INDEX `idx_insure_individual_cost_05`(`item_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：就诊\r\ncost：费用\r\n                                           -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_fund
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_fund`;
CREATE TABLE `insure_individual_fund`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人基本信息id',
  `fund_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金编号',
  `fund_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金名称',
  `indi_freeze_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金状态标志（\"0\"——\"正常\" \r\n            \"1\"——\"冻结\"\r\n            \"2\"——\"暂停参保\" \"3\"——\"中止参保\" \"9\"—— \"未参保\"）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `fund_pay_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金支付类型',
  `inscp_scp_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '符合政策范围金额',
  `crt_payb_lmt_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '本次可支付限额金额',
  `fund_payamt` decimal(16, 2) NULL DEFAULT NULL COMMENT '基金支付金额',
  `fund_pay_type_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金支付类型名称',
  `setl_proc_info` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算过程信息',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_fund_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_fund_02`(`mib_id`) USING BTREE,
  INDEX `idx_insure_individual_fund_03`(`insure_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nfund：基金\r\n                                           -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_inpatient
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_inpatient`;
CREATE TABLE `insure_individual_inpatient`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人基本信息id',
  `akb020` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编号',
  `aaa027` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心编码',
  `akc190` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号',
  `aae011` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记人工号',
  `bka015` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记人',
  `bka016` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记标志',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `aae036` datetime(0) NULL DEFAULT NULL COMMENT '业务登记日期',
  `aae030` datetime(0) NULL DEFAULT NULL COMMENT '业务开始时间',
  `bka018` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '业务开始情况',
  `bkz101` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病名称',
  `akc193` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院疾病诊断（akc193码）',
  `akf001` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院科室',
  `bka020` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院科室名称',
  `bka021` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院病区',
  `bka021_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院病区名称',
  `ake0201` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院床位号',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_inpatient_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_inpatient_02`(`mib_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nlastbizinfo：住' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_settle
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_settle`;
CREATE TABLE `insure_individual_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算id',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否住院（SF）',
  `visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊登记号',
  `discharge_dn_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院疾病诊断编码',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保注册编码',
  `medicine_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `discharge_dn_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院疾病诊断名称',
  `discharged_date` datetime(0) NULL DEFAULT NULL COMMENT '出院日期',
  `discharged_case` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院情况',
  `settleway` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算方式,01 普通结算,02 包干结算',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总费用',
  `insure_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保支付',
  `plan_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '统筹基金支付',
  `serious_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '大病互助支付',
  `civil_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '公务员补助支付',
  `retire_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '离休基金支付',
  `personal_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '个人账户支付',
  `person_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '个人支付',
  `hosp_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医院支付',
  `before_settle` decimal(14, 4) NULL DEFAULT NULL COMMENT '结算前账户余额',
  `last_settle` decimal(14, 4) NULL DEFAULT NULL COMMENT '结算后账户余额',
  `rests_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '其他支付',
  `assign_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '指定账户支付金额',
  `starting_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '起付线金额',
  `top_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '超封顶线金额',
  `plan_account_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '统筹段自负金额',
  `portion_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '部分自付金额',
  `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志,0正常，2冲红，1，被冲红',
  `settle_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算状态,0试算，1结算',
  `costbatch` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用批次',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇类型',
  `injury_borth_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务申请号,门诊特病，工伤，生育',
  `is_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前结算是否使用个人账户,0是，1否',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `insureSettleId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算返回的结算id',
  `medicalRegNo` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院登记唯一返回的就诊登记号',
  `ext03` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段4',
  `ext04` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段5',
  `ext05` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段6',
  `ext06` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段3',
  `ext07` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段7',
  `ext08` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段8',
  `ext09` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段9',
  `ext10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义字段10',
  `omsgid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易信息(原交易)中的msgid,发送方报文ID',
  `oinfno` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易信息(原交易)中的infno',
  `remote_msg_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异地清分结果确认交易号',
  `clr_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算类别',
  `clr_way` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算方式',
  `clr_optins` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算经办机构',
  `maf_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '医疗救助基金',
  `hosp_exem_amount` decimal(14, 4) NULL DEFAULT NULL COMMENT '医院减免金额',
  `is_cancel` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '医保结算记录是否作废 1:是 0 否',
  `com_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '企业支付',
  `all_portion_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '全自费金额',
  `over_self_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '超限价',
  `preselfpay_amt` decimal(14, 4) NULL DEFAULT NULL COMMENT '先行自付金额',
  `inscp_scp_amt` decimal(14, 4) NULL DEFAULT NULL COMMENT '符合政策范围金额',
  `pool_prop_selfpay` decimal(14, 4) NULL DEFAULT NULL COMMENT '本医疗保险统筹基金支付比例',
  `acct_mulaid_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '个人账户共计支付金额',
  `acct_inj_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '职工意外伤害基金',
  `ret_acct_inj_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '居民意外伤害基金',
  `government_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '政府兜底基金',
  `thb_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '特惠保补偿金',
  `care_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '优抚对象医疗补助基金',
  `low_in_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '农村低收入人口医疗补充保险',
  `oth_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '其他基金',
  `retired_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '离休人员医疗保障基金',
  `soldier_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '一至六级残疾军人医疗补助基金',
  `retired_outpt_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '离休老工人门慢保障基金',
  `injury_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '工伤保险基金',
  `hall_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '厅级干部补助基金',
  `soldier_to_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '军转干部医疗补助基金',
  `welfare_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '公益补充保险基金',
  `COVID_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '新冠肺炎核酸检测财政补助',
  `family_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '居民家庭账户金',
  `behalf_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '代缴基金（破产改制）',
  `fertility_pay` decimal(14, 4) NULL DEFAULT NULL COMMENT '生育基金',
  `psn_part_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '个人负担总金额',
  `hifdm_pay` decimal(14, 4) UNSIGNED NULL DEFAULT NULL COMMENT '伤残人员医疗保障基金（珠海医保新增回参字段）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_settle_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_settle_02`(`settle_id`) USING BTREE,
  INDEX `idx_insure_individual_settle_03`(`insureSettleId`) USING BTREE,
  INDEX `idx_insure_individual_settle_04`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：就诊\r\nsettle：结算\r\n                                             -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_stats
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_stats`;
CREATE TABLE `insure_individual_stats`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人基本信息id',
  `biz_year` int(11) NULL DEFAULT NULL COMMENT '本年业务总次数',
  `drug_year` int(11) NULL DEFAULT NULL COMMENT '本年购药次数',
  `diag_year` int(11) NULL DEFAULT NULL COMMENT '本年门诊次数',
  `inhosp_year` int(11) NULL DEFAULT NULL COMMENT '本年住院次数',
  `special_year` int(11) NULL DEFAULT NULL COMMENT '本年门诊特殊病次数',
  `fee_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年总费用',
  `fund_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年统筹基金累计支出',
  `acct_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年个人帐户累计支出',
  `additional_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年大病互助金累计支出',
  `retire_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年离休基金累计支出',
  `official_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年公务员补助累计支出',
  `qfx_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年住院起付线支出',
  `declare_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年申报费用累计',
  `grzf_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年个人自付',
  `jmyw_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年居民意外伤害基金',
  `corp_add_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '本年企业补充支付',
  `month_diag_year` decimal(14, 4) NULL DEFAULT NULL COMMENT '当前月份门诊公务员支付累积',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_stats_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_stats_02`(`mib_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nfund：基金\r\n                                            -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_transfer
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_transfer`;
CREATE TABLE `insure_individual_transfer`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人基本信息id',
  `rela_hosp_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院关联医院编号',
  `rela_aaz217` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院关联就医登记号',
  `aaz267` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院申请序列号',
  `bka068` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院标志',
  `akc200` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '本年度住院次数',
  `declare_year` decimal(14, 2) NULL DEFAULT NULL COMMENT '本年住院申报累计金额',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_transfer_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_transfer_02`(`mib_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\nmedical：医保\r\nIndividual：个人\r\ninpatient：住院' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_individual_visit
-- ----------------------------
DROP TABLE IF EXISTS `insure_individual_visit`;
CREATE TABLE `insure_individual_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '患者就诊id',
  `mib_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人基本信息id',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保注册编码',
  `medicine_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `is_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否住院（SF）',
  `visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号/就诊号',
  `aac001` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人电脑号',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保登记号',
  `aka130` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `aka130_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型名称',
  `bka006` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇类型',
  `bka006_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇类型名称',
  `injury_borth_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务申请号',
  `visit_icd_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊疾病编码',
  `visit_icd_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊疾病名称',
  `visit_time` datetime(0) NULL DEFAULT NULL COMMENT '就诊时间',
  `visit_drpt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室ID',
  `visit_drpt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室名称',
  `visit_area_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊病区ID',
  `visit_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊病区名称',
  `visit_berth` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊床位',
  `starting_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '起付线金额',
  `shift_hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转入医院编码',
  `out_hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转出医院编码',
  `cause` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院原因',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `is_ecqr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否电子凭证登记',
  `pay_ord_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付订单号',
  `pay_token` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付 token',
  `accessory_diagnosis_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '副诊断名称',
  `accessory_diagnosis_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '副诊断编码',
  `insuplc_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保地医保区划',
  `mdtrt_cert_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊凭证类型',
  `mdtrt_cert_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊凭证编号',
  `omsgid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易信息(原交易)中的msgid,发送方报文ID',
  `oinfno` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易信息(原交易)中的infno',
  `pret_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '早产标志',
  `fetus_cnt` int(3) NULL DEFAULT NULL COMMENT '胎儿数',
  `birctrl_matn_date` datetime(0) NULL DEFAULT NULL COMMENT '计划生育手术或生育日期',
  `fetts` int(3) NULL DEFAULT NULL COMMENT '胎次',
  `geso_val` int(2) NULL DEFAULT NULL COMMENT '孕周数',
  `latechb_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '晚育标志',
  `birctrl_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划生育手术类别',
  `fpsc_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划生育服务证号',
  `matn_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生育类别',
  `is_out` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否办理医保出院登记',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `settle_count` int(4) NULL DEFAULT 0 COMMENT '中途结算次数',
  `is_half_settle` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否中途结算',
  `is_one_settle` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否一站式结算患者',
  `idet_start_date` datetime(0) NULL DEFAULT NULL COMMENT '身份有效开始时间',
  `idet_end_date` datetime(0) NULL DEFAULT NULL COMMENT '身份有效结束时间',
  `psn_idet_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_token_deng_ji` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子凭证登记时paytoken备份',
  `hcard_basinfo` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '广州读卡就医基本信息',
  `hcard_chkinfo` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '广州读卡就医校验信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_individual_visit_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_individual_visit_02`(`mib_id`) USING BTREE,
  INDEX `idx_insure_individual_visit_03`(`medicine_org_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nIndividual：个人\r\nvisit：就诊\r\n                                            -&#' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for insure_inpt_record
-- ----------------------------
DROP TABLE IF EXISTS `insure_inpt_record`;
CREATE TABLE `insure_inpt_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `insutype` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '险种类型',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `insu_optins` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保机构医保区划',
  `diag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断代码',
  `dise_cond_dscr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病病情描述',
  `reflin_medins_no` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转往定点医药机构编号',
  `reflin_medins_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转往医院名称',
  `mdtrtarea_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医地行政区划',
  `hosp_agre_refl_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院同意转院标志',
  `refl_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院类型',
  `refl_date` datetime(0) NULL DEFAULT NULL COMMENT '转院日期',
  `refl_rea` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院原因',
  `refl_opnn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院意见',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `trt_dcla_detl_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇申报明细流水号',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填写撤销原因',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `diag_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断代码',
  `refl_type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转院类型名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_inpt_record_01`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保转院备案信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_inpt_transfusion_record
-- ----------------------------
DROP TABLE IF EXISTS `insure_inpt_transfusion_record`;
CREATE TABLE `insure_inpt_transfusion_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `mdtrt_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '院内唯一号(与主键相同)',
  `mdtrt_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '(医保)就诊ID',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '(医保)人员编号',
  `abo_code` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ABO血型代码',
  `rh_code` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Rh血型代码',
  `bld_natu_code` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血性质代码',
  `bld_abo_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血 ABO血型代码',
  `bld_rh_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血Rh血型代码',
  `bld_cat_code` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血品种代码',
  `bld_amt` decimal(7, 0) NULL DEFAULT NULL COMMENT '输血量',
  `bld_amt_unt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血量计量单位',
  `bld_defs_type_code` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血反应类型代码',
  `bld_cnt` decimal(2, 0) NULL DEFAULT NULL COMMENT '输血次数',
  `bld_time` datetime(0) NULL DEFAULT NULL COMMENT '输血时间',
  `bld_rea` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血原因',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志',
  `is_transmission` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否传输',
  `transmission_time` datetime(0) NULL DEFAULT NULL COMMENT '传输时间',
  `transmission_times` int(4) UNSIGNED NULL DEFAULT 0 COMMENT '传输次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_inpt_transfusion_record_01`(`mdtrt_id`) USING BTREE,
  INDEX `idx_insure_inpt_transfusion_record_02`(`psn_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_item
-- ----------------------------
DROP TABLE IF EXISTS `insure_item`;
CREATE TABLE `insure_item`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保注册编码',
  `item_mark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类别标志（11.西药、12.中成药、13.中草药、2.项目）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目编码',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目名称',
  `item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目类别',
  `item_dosage` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目剂型',
  `item_spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保中心项目价格',
  `item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目单位',
  `prod` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `deductible` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自费比例',
  `check_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '限价',
  `directory` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保目录标志（0.甲、1.乙、2.全自费）',
  `take_date` datetime(0) NULL DEFAULT NULL COMMENT '生效日期',
  `lose_date` datetime(0) NULL DEFAULT NULL COMMENT '失效日期',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `ver` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新医保下载版本号',
  `ver_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新医保下载版本名称',
  `down_load_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载类型',
  `sp_drug_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊药品标志',
  `sp_lmtpric_drug_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊限价药品标志',
  `lmt_user_flag` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用标志',
  `hosp_item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目名称',
  `hosp_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目编码',
  `size` int(6) NULL DEFAULT NULL COMMENT '每次调用下载接口的数据大小',
  `num` int(6) NULL DEFAULT NULL COMMENT '每次调用下载接口的分页',
  `record_counts` int(6) NULL DEFAULT NULL COMMENT '下载当前版本号的总数据量',
  `drugadm_strdcode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品本位码',
  `lim_user_explain` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用说明',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_item_01`(`item_code`) USING BTREE,
  INDEX `idx_insure_item_02`(`insure_reg_code`) USING BTREE,
  INDEX `idx_insure_item_03`(`down_load_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nitem：项目\r\n\r\n表说明：\r\n用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_item_match
-- ----------------------------
DROP TABLE IF EXISTS `insure_item_match`;
CREATE TABLE `insure_item_match`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保注册编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类别标志（11.西药、12.中成药、13.中草药、2.项目）',
  `molss_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人社部药品id',
  `pqcc_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卫计委药品id',
  `hosp_item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目id',
  `hosp_item_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目名称',
  `hosp_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目编码',
  `hosp_item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目类别',
  `hosp_item_spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `hosp_item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目单位',
  `hosp_item_prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院项目剂型',
  `hosp_item_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医院项目价格',
  `insure_item_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目名称',
  `insure_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目编码',
  `insure_item_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目类别',
  `insure_item_spec` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insure_item_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目单位',
  `insure_item_prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保中心项目剂型',
  `insure_item_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保中心项目价格',
  `deductible` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自费比例',
  `standard_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '本位码',
  `check_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '限价',
  `manufacturer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `is_match` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匹配（SF）',
  `is_trans` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否传输（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `take_date` datetime(0) NULL DEFAULT NULL COMMENT '生效日期',
  `lose_date` datetime(0) NULL DEFAULT NULL COMMENT '失效日期',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `lmt_user_flag` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用标志',
  `lim_user_explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcmdrug_used_way` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中药使用方式 1复方 2单方',
  `nation_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家匹配编码',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注内容',
  `hilist_lv` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保目录等级',
  `chrg_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费类别',
  `hilist_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '医保目录价格',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_item_match_03`(`hosp_item_id`) USING BTREE,
  INDEX `idx_insure_item_match_04`(`insure_item_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表含义：\r\ninsure：医保\r\nitem：项目\r\nmatching：匹配\r\n\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_iteminfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_iteminfo`;
CREATE TABLE `insure_iteminfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varbinary(32) NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  `med_chrgitm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗收费项目',
  `claa_sumfee` decimal(16, 2) NULL DEFAULT NULL COMMENT '金额',
  `amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '甲类费用合计',
  `clab_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '乙类金额',
  `fulamt_ownpay_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '全自费金额',
  `oth_amt` decimal(16, 2) NULL DEFAULT NULL COMMENT '其他金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_iteminfo_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_iteminfo_02`(`insure_settle_id`) USING BTREE,
  INDEX `idx_insure_iteminfo_03`(`medical_reg_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医疗保障结算清单:收费项目信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_nostruct_report
-- ----------------------------
DROP TABLE IF EXISTS `insure_nostruct_report`;
CREATE TABLE `insure_nostruct_report`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `mdtrt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `psn_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `otp_ipt_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊/住院标志',
  `exam_test_flag` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查/检验标志',
  `appy_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请号',
  `file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_formate` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件格式类型',
  `exam_test_rpot` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查/检验报告(base64编码格式)',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志',
  `is_trans` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上传',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_oprninfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_oprninfo`;
CREATE TABLE `insure_oprninfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  `oprn_oprt_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '术操作类别',
  `oprn_oprt_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术操作名称',
  `oprn_oprt_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术操作代码',
  `oprn_oprt_date` datetime(0) NULL DEFAULT NULL COMMENT '手术操作日期',
  `anst_way` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉方式',
  `oper_dr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '术者医师姓名',
  `oper_dr_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '术者医师代码',
  `anst_dr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉医师姓名',
  `anst_dr_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉医师代码',
  `oprn_oprt_start_time` datetime(0) NULL DEFAULT NULL COMMENT '手术开始时间',
  `oprn_oprt_end_time` datetime(0) NULL DEFAULT NULL COMMENT '手术截止时间',
  `anst_dr_start_time` datetime(0) NULL DEFAULT NULL COMMENT '麻醉开始时间',
  `anst_dr_end_time` datetime(0) NULL DEFAULT NULL COMMENT '麻醉截止时间',
  PRIMARY KEY (`id`, `hosp_code`) USING BTREE,
  INDEX `idx_insure_oprninfo_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_oprninfo_02`(`insure_settle_id`) USING BTREE,
  INDEX `idx_insure_oprninfo_03`(`medical_reg_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医疗保障结算清单:手术信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_opspdiseinfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_opspdiseinfo`;
CREATE TABLE `insure_opspdiseinfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  `diag_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断代码',
  `diag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断名称',
  `oprn_oprt_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术操作名称',
  `oprn_oprt_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术操作代码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_opspdiseinfo_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_opspdiseinfo_02`(`insure_settle_id`) USING BTREE,
  INDEX `idx_insure_opspdiseinfo_03`(`medical_reg_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医疗保障结算清单:门诊慢特病信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_outpt_twodise
-- ----------------------------
DROP TABLE IF EXISTS `insure_outpt_twodise`;
CREATE TABLE `insure_outpt_twodise`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保机构编码',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `opsp_dise_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门慢门特病种目录代码',
  `opsp_dise_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门慢门特病种名称',
  `hosp_opnn` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院意见',
  `hosp_appy_time` datetime(0) NULL DEFAULT NULL COMMENT '医院申请时间',
  `begndate` date NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` date NULL DEFAULT NULL COMMENT '结束日期',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人姓名',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `appy_rea` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请原因',
  `symp_dscr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '症状描述',
  `appyer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人',
  `appyer_tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人电话',
  `appyer_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人地址',
  `trtDclaDetlSn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇申报明细流水号',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_outpt_twodise_01`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门诊两病备案信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_pathological_report
-- ----------------------------
DROP TABLE IF EXISTS `insure_pathological_report`;
CREATE TABLE `insure_pathological_report`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `mdtrt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `psn_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `palg_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '病理号',
  `frozen_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冰冻号',
  `cma_date` datetime(0) NULL DEFAULT NULL COMMENT '送检日期',
  `rpot_date` datetime(0) NULL DEFAULT NULL COMMENT '报告日期',
  `cma_matl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送检材料',
  `clnc_dise` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '临床诊断',
  `exam_fnd` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查所见',
  `sabc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '免疫组化',
  `palg_diag` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病理诊断',
  `rpot_doc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告医师',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志',
  `is_trans` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上传',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_patient_sum
-- ----------------------------
DROP TABLE IF EXISTS `insure_patient_sum`;
CREATE TABLE `insure_patient_sum`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `medis_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `psn_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '个人编号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记号',
  `insutype` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '险种',
  `year` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年度',
  `cum_type_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '累计类别代码',
  `cum_ym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '累计年月',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `cum` decimal(16, 2) NULL DEFAULT NULL COMMENT '累计值',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_patient_sum_01`(`medical_reg_no`) USING BTREE,
  INDEX `idx_insure_patient_sum_02`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保个人累计信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_payinfo
-- ----------------------------
DROP TABLE IF EXISTS `insure_payinfo`;
CREATE TABLE `insure_payinfo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `medical_reg_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医登记',
  `insure_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算id',
  `fund_pay_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金支付类型',
  `fund_payamt` decimal(16, 2) NULL DEFAULT NULL COMMENT '基金支付金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_payinfo_01`(`medical_reg_no`) USING BTREE,
  INDEX `idx_insure_payinfo_02`(`insure_settle_id`) USING BTREE,
  INDEX `idx_insure_payinfo_03`(`medical_reg_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医疗保障结算清单:基金支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_record
-- ----------------------------
DROP TABLE IF EXISTS `insure_record`;
CREATE TABLE `insure_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊id',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `insutype` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '险种类型',
  `opsp_dise_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门慢门特病种目录代码',
  `opsp_dise_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门慢门特病种名称',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `insu_optins` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保机构医保区划',
  `ide_fixmedins_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '鉴定定点医药机构编号',
  `ide_fixmedins_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '鉴定定点医药机构名称',
  `hosp_ide_date` datetime(0) NULL DEFAULT NULL COMMENT '医院鉴定日期',
  `diag_dr_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断医师编码',
  `diag_dr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断医师姓名',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `trt_dcla_detl_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇申报明细流水号',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注(填写撤销原因)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `reg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_record_01`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门诊慢特病备案登记信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_setl_info
-- ----------------------------
DROP TABLE IF EXISTS `insure_setl_info`;
CREATE TABLE `insure_setl_info`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `visit_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊id',
  `insure_settle_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结算id',
  `settle_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传结算流水号',
  `fixmedins_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定点医药机构名称',
  `fixmedins_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定点医疗机构编号',
  `hi_setl_lv` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保结算等级',
  `hi_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保编号',
  `medcasno` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '病案号',
  `dcla_time` datetime(0) NULL DEFAULT NULL COMMENT '申报时间',
  `psn_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员姓名',
  `gend` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `brdy` date NOT NULL COMMENT '出生日期',
  `age` decimal(4, 1) NULL DEFAULT NULL COMMENT '年龄',
  `ntly` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国籍',
  `neb_age` int(3) NULL DEFAULT NULL COMMENT '年龄（不足一岁）',
  `naty` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '民族',
  `patn_cert_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '患者证件类别',
  `certno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证件号码',
  `prfs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职业',
  `curr_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址',
  `emp_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位名称',
  `emp_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位地址',
  `emp_tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位电话',
  `poscode` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `coner_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人姓名',
  `patn_rlts` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '与患者关系',
  `coner_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人地址',
  `coner_tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人电话',
  `hi_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保类型',
  `insuplc` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保地区划',
  `sp_psn_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊人员类型',
  `nwb_adm_type` decimal(3, 0) NULL DEFAULT NULL COMMENT '新生儿入院类型',
  `nwb_bir_wt` decimal(6, 2) NULL DEFAULT NULL COMMENT '新生儿出生体重',
  `nwb_adm_wt` decimal(6, 2) NULL DEFAULT NULL COMMENT '新生儿入院体重',
  `opsp_diag_caty` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊慢特病诊断科别',
  `opsp_mdtrt_date` date NULL DEFAULT NULL COMMENT '门诊慢特病就诊日期',
  `ipt_med_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院医疗类型',
  `adm_way` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院途径',
  `trt_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '治疗类别',
  `adm_time` datetime(0) NULL DEFAULT NULL COMMENT '入院时间',
  `adm_caty` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院科别',
  `refldept_dept` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转科科别',
  `dscg_time` datetime(0) NULL DEFAULT NULL COMMENT '出院时间',
  `dscg_caty` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院科别',
  `act_ipt_days` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实际住院天数',
  `opt_wm_dise` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门（急）诊西医诊断',
  `wm_disw_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '西医诊断疾病代码',
  `opt_tcm_dise` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门（急）诊中医诊断',
  `tcm_dise_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医诊断代码',
  `diag_code_cnt` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断代码计数',
  `oprn_oprt_code_cnt` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术操作代码计数',
  `vent_used_dura` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呼吸机使用时长',
  `pwcry_bfadmcoma_dura` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颅脑损伤患者入院前昏迷时长',
  `pwcry_afadmcoma_dura` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颅脑损伤患者入院后昏迷时长',
  `bld_cat` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血品种',
  `bld_amt` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血量',
  `bld_unt` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输血计量单位',
  `spga_nurscare_days` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特级护理天数',
  `lv1_nurscare_days` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级护理天数',
  `scd_nurscare_days` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级护理天数',
  `lv3_nursecare_days` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '三级护理天数',
  `dscg_way` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '离院方式',
  `acp_medins_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拟接收机构名称',
  `acp_optins_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拟接收机构代码',
  `bill_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '票据代码',
  `bill_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '票据号码',
  `biz_sn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务流水号',
  `days_rinp_flag_31` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院31天内再住院计划标志',
  `days_rinp_pup_31` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出院31天内再住院目的',
  `chfpdr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诊医师姓名',
  `chfpdr_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诊医师代码',
  `setl_begn_date` date NOT NULL COMMENT '结算开始日期',
  `setl_en_date` date NOT NULL COMMENT '结算结束日期',
  `psn_selfpay` decimal(16, 2) NULL DEFAULT NULL,
  `psn_ownpay` decimal(16, 2) NOT NULL COMMENT '个人自费',
  `acct_pay` decimal(16, 2) NOT NULL COMMENT '个人账户支出',
  `psn_cashpay` decimal(16, 2) NOT NULL COMMENT '个人现金支付',
  `hi_paymtd` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保支付方式',
  `hsorg` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保机构',
  `hsorg_opter` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构经办人',
  `medins_fill_dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构填报部门',
  `medins_fill_psn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构填报人',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `setl_list_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保反参清单流水号',
  `zr_nurseName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '责任护士姓名',
  `zr_nurseCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '责任护士代码',
  `mdtrt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保就医登记号',
  `opt_tcm_dise_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门（急）诊中医诊断代码',
  `opt_wm_dise_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ' 门（急）诊西医诊断代码',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_setl_info_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_setl_info_02`(`insure_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医疗保障结算清单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_special_record
-- ----------------------------
DROP TABLE IF EXISTS `insure_special_record`;
CREATE TABLE `insure_special_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `evt_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型',
  `dcla_souc` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申报来源',
  `bydise_setl_list_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按病种结算病种目录代码',
  `bydise_setl_dise_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按病种结算病种名称',
  `oprn_oprt_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术操作名称',
  `oprn_oprt_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手术操作代码',
  `fixmedins_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定点医药机构编号',
  `fix_blng_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定点归属医保区划',
  `appy_date` datetime(0) NULL DEFAULT NULL COMMENT '申请日期',
  `appy_rea` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `agnter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人姓名',
  `agnter_cert_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人证件类型',
  `agnter_certno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人证件号码',
  `agnter_tel` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人联系方式',
  `agnter_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人联系地址',
  `agnter_rlts` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人关系',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `memo` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `psn_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `evtsn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件流水号',
  `serv_matt_inst_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务事项实例ID',
  `serv_matt_node_inst_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务事项环节实例ID',
  `evt_inst_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件实例ID',
  `trt_dcla_detl_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待遇申报明细流水号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单病种备案' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_stock_upload
-- ----------------------------
DROP TABLE IF EXISTS `insure_stock_upload`;
CREATE TABLE `insure_stock_upload`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insure_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保类型',
  `fixmedins_bchno` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流水号\r\n',
  `inv_data_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型\r\n1-盘存信息：对应删除【3501】交易数据；\r\n2-库存变更信息：对应删除【3502】交易数据；\r\n3-采购信息：对应删除【3503】、【3504】交易数据4-销售信息：对应删除【3505】、【3506】交易数据',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `cert_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `selectsss`(`inv_data_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保进销存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_directory
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_directory`;
CREATE TABLE `insure_unified_directory`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `hilist_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录编码',
  `hilist_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录名称',
  `insu_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参保机构医保区划',
  `begndate` datetime(0) NOT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `med_chrgitm_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗收费项目类别',
  `chrgitm_lv` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费项目等级',
  `lmt_used_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '限制使用标志',
  `list_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录类别',
  `med_use_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗使用标志',
  `matn_used_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生育使用标志',
  `hilist_use_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录使用类别',
  `lmt_cpnd_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '限复方使用类型',
  `wubi` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔助记码',
  `pinyin` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音助记码',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效标志',
  `rid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一记录号',
  `updt_time` datetime(0) NOT NULL COMMENT '更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `crte_optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建机构',
  `opter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人姓名',
  `opt_time` datetime(0) NULL DEFAULT NULL COMMENT '经办时间',
  `optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办机构',
  `poolarea_no` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统筹区',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_directory_01`(`hilist_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保目录信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_limitprice
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_limitprice`;
CREATE TABLE `insure_unified_limitprice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hilist_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录编码',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `hilist_lmtpric_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录限价类型',
  `overlmt_dspo_way` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录超限处理方式',
  `insu_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参保机构医保区划',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `hilist_pric_uplmt_amt` decimal(16, 4) NOT NULL COMMENT '医保目录定价上限金额',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效标志',
  `rid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一记录号',
  `updt_time` datetime(0) NOT NULL COMMENT '更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `crte_optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建机构',
  `opter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人姓名',
  `opt_time` datetime(0) NULL DEFAULT NULL COMMENT '经办时间',
  `optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办机构',
  `tabname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `poolarea_no` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统筹区',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_limitprice_01`(`hilist_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保目录限价信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_match
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_match`;
CREATE TABLE `insure_unified_match`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `med_list_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗目录编码',
  `hilist_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录编码',
  `list_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录类别',
  `insu_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参保机构医保区划',
  `begndate` datetime(0) NOT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效标志',
  `rid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一记录号',
  `updt_time` datetime(0) NOT NULL COMMENT '更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `crte_optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建机构',
  `opter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人姓名',
  `opt_time` datetime(0) NULL DEFAULT NULL COMMENT '经办时间',
  `optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办机构',
  `poolarea_no` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统筹区',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_match_01`(`med_list_codg`) USING BTREE,
  INDEX `idx_insure_unified_match_02`(`hilist_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医药机构目录匹配信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_medicmatch
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_medicmatch`;
CREATE TABLE `insure_unified_medicmatch`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `fixmedins_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定点医药机构编号',
  `medins_list_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定点医药机构目录编号',
  `medins_list_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定点医药机构目录名称',
  `med_list_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗目录编码',
  `list_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录类别',
  `insu_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参保机构医保区划',
  `begndate` datetime(0) NOT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `aprvno` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批准文号',
  `dosform` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型',
  `exct_cont` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '除外内容',
  `item_cont` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目内涵',
  `prcunt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计价单位',
  `spec` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `pacspec` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '包装规格',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效标志',
  `rid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一记录号',
  `updt_time` datetime(0) NOT NULL COMMENT '更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `crte_optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建机构',
  `opter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人姓名',
  `opt_time` datetime(0) NULL DEFAULT NULL COMMENT '经办时间',
  `optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办机构',
  `poolarea_no` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统筹区',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_medicmatch_01`(`med_list_codg`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医疗目录与医保目录匹配信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_nation_drug
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_nation_drug`;
CREATE TABLE `insure_unified_nation_drug`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `med_list_codg` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗目录编码',
  `drug_prodname` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品商品名',
  `genname_codg` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通用名编号',
  `drug_genname` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品通用名',
  `ethdrug_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族药种类',
  `chemname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '化学名称',
  `alis` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `eng_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `dosform` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型',
  `each_dos` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每次用量',
  `used_frqu` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用频次',
  `nat_drug_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家药品编号',
  `used_mtd` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法',
  `ing` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成分',
  `chrt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性状',
  `defs` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '不良反应',
  `tabo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '禁忌',
  `mnan` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注意事项',
  `stog` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '贮藏',
  `drug_spec` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品规格',
  `prcunt_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计价单位类型',
  `otc_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '非处方药标志',
  `pacmatl` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '包装材质',
  `pacspec` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '包装规格',
  `min_useunt` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小使用单位',
  `min_salunt` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小销售单位',
  `manl` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明书',
  `rute` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '给药途径',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `pham_type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药理分类',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pac_cnt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '包装数量',
  `min_unt` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小计量单位',
  `min_pac_cnt` decimal(14, 2) NULL DEFAULT NULL COMMENT '最小包装数量',
  `min_pacunt` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小包装单位',
  `min_prepunt` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小制剂单位',
  `drug_expy` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品有效期',
  `efcc_atd` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能主治',
  `min_prcunt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最小计价单位',
  `wubi` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔助记码',
  `pinyin` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音助记码',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效标志',
  `rid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一记录号',
  `crte_time` datetime(0) NOT NULL COMMENT '数据创建时间',
  `updt_time` datetime(0) NOT NULL COMMENT '数据更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建经办机构',
  `opter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经办人',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经办人姓名',
  `opt_time` datetime(0) NOT NULL COMMENT '经办时间',
  `optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经办机构',
  `ver` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_nation_drug_01`(`med_list_codg`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保民族药品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_ratio
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_ratio`;
CREATE TABLE `insure_unified_ratio`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `hilist_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录编码',
  `selfpay_prop_psn_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保目录自付比例人员类别',
  `selfpay_prop_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录自付比例类别',
  `insu_admdvs` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参保机构医保区划',
  `begndate` datetime(0) NOT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `selfpay_prop` decimal(5, 2) NOT NULL COMMENT '自付比例',
  `vali_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效标志',
  `rid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一记录号',
  `updt_time` datetime(0) NOT NULL COMMENT '更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `crte_optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建机构',
  `opter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人',
  `opter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办人姓名',
  `opt_time` datetime(0) NULL DEFAULT NULL COMMENT '经办时间',
  `optins_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经办机构',
  `tabname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `poolarea_no` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统筹区',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_ratio_01`(`hilist_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医保目录先行自付比例信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_reckon
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_reckon`;
CREATE TABLE `insure_unified_reckon`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `insure_reg_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医保注册编码',
  `medicine_org_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗机构编码',
  `clr_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算类别',
  `clr_way` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算方式',
  `clr_optins` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算中心',
  `setlym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清算年月',
  `psntime` int(8) NULL DEFAULT 0 COMMENT '清算人数',
  `medfee_sumamt` decimal(16, 2) NULL DEFAULT 0.00 COMMENT '医疗总金额',
  `med_sumfee` decimal(16, 2) NULL DEFAULT 0.00 COMMENT '医保认可费用总额',
  `fund_appy_sum` decimal(16, 2) NULL DEFAULT 0.00 COMMENT '基金申报总额',
  `cash_payamt` decimal(16, 2) NULL DEFAULT 0.00 COMMENT '现金支付金额',
  `acct_pay` decimal(16, 2) NULL DEFAULT 0.00 COMMENT '个人账户支出',
  `begndate` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `enddate` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `is_declare` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否申报',
  `declare_time` datetime(0) NULL DEFAULT NULL COMMENT '申报时间',
  `declare_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申报人姓名',
  `clr_appy_evt_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构清算申请事件ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_reckon_01`(`insure_reg_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '中心清算申报表(湖南特有)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_unified_remote
-- ----------------------------
DROP TABLE IF EXISTS `insure_unified_remote`;
CREATE TABLE `insure_unified_remote`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `certno` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证件号码',
  `mdtrt_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊登记号',
  `mdtrt_setl_time` datetime(0) NOT NULL COMMENT '就诊结算时间',
  `setl_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算流水号',
  `medfee_sumamt` decimal(16, 2) NOT NULL COMMENT '总费用',
  `optins_pay_sumamt` decimal(16, 2) NOT NULL COMMENT '经办机构支付总额',
  `cnfm_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trt_year` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结算年度',
  `trt_month` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结算月份',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `is_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 's是否异地清分结果确认',
  `mdtrtarea` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就医地医保区划',
  `medins_no` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编号',
  `fulamt_advpay_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全额垫付标志',
  `omgsId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易流水号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_unified_remote_01`(`mdtrt_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '异地清分信息表(珠海特有)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for insure_upload_cost
-- ----------------------------
DROP TABLE IF EXISTS `insure_upload_cost`;
CREATE TABLE `insure_upload_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊id',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费id',
  `mdtrt_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就医流水号',
  `ipt_otp_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '住院/门诊号',
  `med_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗类别',
  `chrg_bchno` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费批次号',
  `feedetl_sn` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '费用明细流水号',
  `psn_cert_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员证件类型',
  `certno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证件号码',
  `psn_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员姓名',
  `fee_ocur_time` datetime(0) NOT NULL COMMENT '费用发生时间',
  `cnt` decimal(16, 4) NOT NULL COMMENT '数量',
  `pric` decimal(16, 2) NOT NULL COMMENT '单价',
  `det_item_fee_sumamt` decimal(16, 6) NOT NULL COMMENT '明细费用总额',
  `med_list_codg` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗目录编码',
  `medins_list_codg` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医药机构编码',
  `medins_list_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医药机构目录名称',
  `med_chrgitm_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医疗收费项目类别',
  `prodname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名',
  `bilg_dept_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开单科室编码',
  `bilg_dept_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开单科室名称',
  `bilg_dr_codg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开单医生编码',
  `bilg_dr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开单医师姓名',
  `org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医疗机构编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_insure_upload_cost_01`(`visit_id`) USING BTREE,
  INDEX `idx_insure_upload_cost_02`(`cost_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '19、费用上传明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lis_callback_status
-- ----------------------------
DROP TABLE IF EXISTS `lis_callback_status`;
CREATE TABLE `lis_callback_status`  (
  `barcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `patinfoid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `hisid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `feeItem_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `feeItem_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '采集、 接收、 上机、 发布等',
  `date` datetime(0) NULL DEFAULT NULL,
  `crte_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lis_critical_value
-- ----------------------------
DROP TABLE IF EXISTS `lis_critical_value`;
CREATE TABLE `lis_critical_value`  (
  `area_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '区域编码',
  `pat_infoId` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '报告单唯一编号',
  `test_item_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '检验项目编号',
  `critical_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '危急值结果',
  `critical_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '危急值结果状态  ▲ ▼',
  `critical_time` datetime(0) NULL DEFAULT NULL COMMENT '发现危急值时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lis_inspect_apply
-- ----------------------------
DROP TABLE IF EXISTS `lis_inspect_apply`;
CREATE TABLE `lis_inspect_apply`  (
  `areaCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `patInfoId` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `testDate` datetime(0) NULL DEFAULT NULL,
  `scode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '仪器急诊标本回传结果为1，普通标本为0',
  `ecode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `eqpName` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `testNum` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `emergencyTag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `chargeTag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `criticalStatus` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '危急值状态 2：有危急值，待登记 1:已登记危急值 0：无危急值',
  `reviewTag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '复查登记标志',
  `reviewTimes` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '复查次数',
  `positiveTag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阳性报告标志(预留)',
  `pid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门诊住院号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `pyCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病人姓名首拼',
  `age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄',
  `ageUnit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '年龄单位字典表编码',
  `fullAge` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '完整年龄',
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别  男，女，未知',
  `ssid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '患者身份证号',
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '患者手机号',
  `birthday` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '患者出生年月日',
  `patientCompany` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'patientCompany	varchar	文本	患者所在公司',
  `patType` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊类型  枚举表',
  `patTypeName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '患者就诊类型',
  `dptCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开单科室编码',
  `dptName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开单科室',
  `dctCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开单医生编码',
  `dctName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开单医生',
  `bedNum` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病人床号',
  `lczd` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '临床诊断',
  `drugRemark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '患者用药信息',
  `ordersRemark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '申请备注信息'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lis_inspect_apply_active
-- ----------------------------
DROP TABLE IF EXISTS `lis_inspect_apply_active`;
CREATE TABLE `lis_inspect_apply_active`  (
  `area_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `pat_infoId` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'lis_pat_info表主键（报告单唯一主键）',
  `test_date` datetime(0) NULL DEFAULT NULL,
  `scode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ecode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `eqp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `test_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `emergency_tag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `chargeTag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `critical_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `review_tag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `review_times` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `positive_tag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `py_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `age_unit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `full_age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ssid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `birthday` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `patient_company` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pat_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pat_type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dpt_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dpt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dct_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dct_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `bed_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `lczd` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `drug_remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `orders_remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `order_time` datetime(0) NULL DEFAULT NULL,
  `barcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gather_time` datetime(0) NULL DEFAULT NULL,
  `receive_time` datetime(0) NULL DEFAULT NULL,
  `input_time` datetime(0) NULL DEFAULT NULL,
  `test_time` datetime(0) NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `test_tser_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `test_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `test_user_sign` blob NULL,
  `check_user_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `check__user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sample_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `caseId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `patient_uniqueId` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sample_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sample_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sample_part` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `security_level` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `hospital_times` datetime(0) NULL DEFAULT NULL,
  `infectious_tag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `result_comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `warn_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `age_unit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `testItem_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `testItemName` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `testItemEname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `testTime` datetime(0) NULL DEFAULT NULL,
  `result_type` blob NULL,
  `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `result_unit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `range_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `reference_range` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `range_state_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `feeItem_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `feeItem_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `his_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `resultlist` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `crte_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lis_pdf
-- ----------------------------
DROP TABLE IF EXISTS `lis_pdf`;
CREATE TABLE `lis_pdf`  (
  `pdfurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pdffile` mediumtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `pdfpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `crte_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medic_apply
-- ----------------------------
DROP TABLE IF EXISTS `medic_apply`;
CREATE TABLE `medic_apply`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `apply_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技类别（CFLB）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿id',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用id',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号/就诊号',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方单号',
  `is_inpt` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否住院',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室名称',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请医生名称',
  `apply_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请时间',
  `imp_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `bar_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '条形码',
  `print_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最近打印时间',
  `print_times` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印次数',
  `rporter` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告人',
  `is_allergy` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否过敏检查（SF）',
  `result_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果ID（对应结果主表ID）',
  `is_positive` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏结果是否阳性（SF）',
  `document_sta` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据状态SQDZT',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应医嘱/处方明细ID',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱/处方内容',
  `medic_type` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技类型（PACSXMLX、LISXMLX）',
  `is_merge` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否合管（SF）',
  `merge_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合管主单ID（自身ID）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `report_time` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `coll_blood_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采血人ID',
  `coll_blood_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采血人名称',
  `coll_blood_time` datetime(0) NULL DEFAULT NULL COMMENT '采血时间',
  `is_issue` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上传',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_medic_apply_01`(`apply_no`) USING BTREE,
  INDEX `idx_medic_apply_02`(`visit_id`) USING BTREE,
  INDEX `idx_medic_apply_03`(`in_no`) USING BTREE,
  INDEX `idx_medic_apply_04`(`opd_id`) USING BTREE,
  INDEX `idx_medic_apply_05`(`merge_id`) USING BTREE,
  INDEX `idx_medic_apply_06`(`apply_time`) USING BTREE,
  INDEX `idx_medic_apply_07`(`document_sta`) USING BTREE,
  INDEX `idx_medic_apply_08`(`type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medic_apply_detail
-- ----------------------------
DROP TABLE IF EXISTS `medic_apply_detail`;
CREATE TABLE `medic_apply_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `apply_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '申请ID（medic_apply.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '对应医嘱/处方明细ID',
  `advice_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医嘱编码',
  `inspect_method` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '检查方法',
  `inspect_position` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '检查部位',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医嘱/处方内容',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `blob_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '返回图片ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_medic_apply_detail_01`(`apply_id`) USING BTREE,
  INDEX `idx_medic_apply_detail_02`(`visit_id`) USING BTREE,
  INDEX `idx_medic_apply_detail_03`(`opd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medic_data
-- ----------------------------
DROP TABLE IF EXISTS `medic_data`;
CREATE TABLE `medic_data`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1.lis科室同步,2:lis医生同步,3:lis收费项目同步,4:lis申请单,5:lis主动获取,6:lis推送,7:lis PDF报告查询,8:lis危急值主动获取,9:lis危急值推送,\r\n41.pacs科室同步,42:pacs医生同步,43:pacs收费项目同步,44:pacs申请单,45:pacs',
  `result_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存放结果表名',
  `source_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1:表/视图,2:接口',
  `source_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果来源(表/视图)',
  `table_sql` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表/视图字段转换sql(source_type为1)',
  `interface_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口地址',
  `interface_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口方式(GET/POST)',
  `class_url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类路径',
  `method_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `status_code` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0:作废,1:启用',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'lis/pacs配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medic_data_detail
-- ----------------------------
DROP TABLE IF EXISTS `medic_data_detail`;
CREATE TABLE `medic_data_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1.lis科室同步,2:lis医生同步,3:lis收费项目同步,4:lis申请单,5:lis主动获取,6:lis推送,7:lis PDF报告查询,8:lis危急值主动获取,9:lis危急值推送,\r\n41.pacs科室同步,42:pacs医生同步,43:pacs收费项目同步,44:pacs申请单,45:pacs',
  `result_e_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果表字段英文名',
  `result_c_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果表字段中文名',
  `result_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果表字段类型',
  `result_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `resource_e_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源字段英文名',
  `resource_c_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源字段中文名',
  `resource_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源表字段类型',
  `resource_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'lis/pacs配置明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medic_result
-- ----------------------------
DROP TABLE IF EXISTS `medic_result`;
CREATE TABLE `medic_result`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `hosp_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号/就诊号',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱id/处方id',
  `bar_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '条形码',
  `type_code` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技类别(1:lis,2:pacs)',
  `item_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目id',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `result_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果类型(1:数字,2:文本,3:图片存放地址)',
  `result` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果',
  `result_detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果描述',
  `reporte_text` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告内容',
  `pic_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `is_positive` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1:阳性,2:阴性',
  `reference_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参考值范围',
  `result_unit` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果单位',
  `range_state` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果状态',
  `range_state_color` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果状态颜色',
  `check_part` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查部位',
  `check_ways` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查方式',
  `check_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查类型/设备',
  `diagnosis_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断id',
  `diagnosis_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `apply_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请单号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'lis/pacs结果表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_base_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_base_info`;
CREATE TABLE `mris_base_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案号',
  `in_blh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病理号',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住院号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `gender_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别名称',
  `age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `age_unit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄单位名称',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件类型名称',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件号码',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '国籍代码（GJZD）',
  `nationality_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '国籍名称',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '民族代码（MZ）',
  `nation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '民族名称',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '籍贯',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职业代码（ZY）',
  `occupation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职业名称',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `marry_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '婚姻状况名称',
  `work` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '工作单位',
  `work_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位电话',
  `work_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位邮编',
  `work_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位地址',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_rela_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人关系名称',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人电话',
  `contact_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人邮编',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人地址',
  `now_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（省）编码',
  `now_prov_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（省）名称',
  `now_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（市）编码',
  `now_city_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（市）名称',
  `now_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（区、县）编码',
  `now_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（区、县）名称',
  `now_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地邮编',
  `native_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（省）编码',
  `native_prov_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（省）名称',
  `native_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（市）编码',
  `native_city_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（市）名称',
  `native_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（区、县）编码',
  `native_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（区、县）名称',
  `native_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地邮编',
  `in_cnt` int(11) NULL DEFAULT NULL COMMENT '住院次数',
  `in_time` datetime(0) NULL DEFAULT NULL COMMENT '入院时间',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院科室名称',
  `in_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院床位ID',
  `in_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院床位名称',
  `out_time` datetime(0) NULL DEFAULT NULL COMMENT '出院时间',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院科室名称',
  `out_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院床位ID',
  `out_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院床位名称',
  `in_days` int(11) NULL DEFAULT NULL COMMENT '住院天数',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病版本（疾病分类名称）',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病诊断ICD',
  `disease_icd10_name` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病诊断名称',
  `bl_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病理诊断ICD',
  `bl_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病理诊断名称',
  `director_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室主任ID',
  `director_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室主任姓名',
  `director_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室副主任ID',
  `director_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室副主任姓名',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主治医生姓名',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主管医生姓名',
  `zr_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '责任护士ID',
  `zr_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '责任护士姓名',
  `emr_quality_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历质量代码',
  `emr_quality_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历质量',
  `zk_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控医生ID',
  `zk_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控医生姓名',
  `zk_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控护士ID',
  `zk_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控护士姓名',
  `zk_time` datetime(0) NULL DEFAULT NULL COMMENT '质控时间',
  `is_autopsy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否尸检（SF）编码',
  `is_autopsy_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否尸检（SF）名称',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '血型代码（XX）编码',
  `blood_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '血型名称',
  `rh_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'RH代码',
  `rh_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'RH名称',
  `baby_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿体重',
  `is_allergy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否药物过敏（SF）',
  `allergy_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '过敏药物合集',
  `out_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '离院方式代码（CYFS）',
  `out_mode_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '离院方式名称',
  `turn_org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转院机构名称',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情代码（RYQK）',
  `in_situation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情名称',
  `out_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院病情代码CYBQ）',
  `out_situation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院病情名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `hosp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗机构名称',
  `pay_way_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗支付方式类型(ZFFS)',
  `pay_way_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗支付方式名称',
  `health_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '健康卡号',
  `baby_birth_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿出生体重',
  `baby_in_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿入院体重',
  `native_adress` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地址',
  `now_adress` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地址',
  `baby_age_month` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄（月）不足一周岁',
  `birth_adress` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出生地址',
  `id_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话号码',
  `work_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位名称及地址',
  `in_way` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院途径',
  `in_ward` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病房',
  `damage_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '损伤中毒的外部原因',
  `jx_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '进修医师id',
  `sx_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实习医师id',
  `jx_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '进修医师名称',
  `sx_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实习医师名称',
  `doctor_coder_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码员id',
  `doctor_coder_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码员名称',
  `in_ward2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病房2',
  `in_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院科室id',
  `disease_icd10_other` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病编码（损伤原因）',
  `turn_dept_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科科室',
  `case_classification` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病例分析等级（A/B/C/D）',
  `is_monitor` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否实施重症监控',
  `monitor_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '重症监控天数',
  `monitor_hour` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '重症监控小时',
  `is_dzb` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否单种病管理',
  `lclj_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '临床路径管理状态',
  `drg_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'DGR管理状态',
  `is_kss` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否使用抗生素',
  `is_xjsj` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否细菌送检',
  `is_cry` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '法定传染源类型',
  `zl_level` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '肿瘤分期',
  `apgar_num` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿Apgar评分',
  `aim` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '目的',
  `is_inpt` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否有出院31天再住院计划',
  `inpt_before_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院前昏迷时间（天）',
  `inpt_before_hour` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院前昏迷时间（时）',
  `inpt_before_minute` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院前昏迷时间（分）',
  `inpt_last_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院后昏迷时间（天）',
  `inpt_last_hour` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院后昏迷时间（时）',
  `inpt_last_minute` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院后昏迷时间（分）',
  `yljg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗机构编码',
  `turn_time1` datetime(0) NULL DEFAULT NULL COMMENT '转科时间1',
  `turn_time2` datetime(0) NULL DEFAULT NULL COMMENT '转科时间2',
  `turn_time3` datetime(0) NULL DEFAULT NULL COMMENT '转科时间3',
  `turn_dept1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科1',
  `turn_dept2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科2',
  `turn_dept3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科3',
  `outpt_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门急诊医生',
  `outpt_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门急诊医生id',
  `case_classify` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病例分型',
  `clinical_path_case` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '临床路径病例',
  `rescue_count` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '抢救次数',
  `rescue_success_count` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '成功次数',
  `in_upload_insure` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否上传医保',
  `is_upload_insure` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '病案是否上传医保'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病案基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_control
-- ----------------------------
DROP TABLE IF EXISTS `mris_control`;
CREATE TABLE `mris_control`  (
  `id` varchar(32) CHARACTER SET ujis COLLATE ujis_japanese_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `is_commit` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否提交（SF）',
  `commit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '提交人ID',
  `commit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '提交人姓名',
  `commit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否打印（SF）',
  `print_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '打印人ID',
  `print_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '打印人姓名',
  `print_time` datetime(0) NULL DEFAULT NULL COMMENT '打印时间',
  `is_send` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否送档（SF）',
  `send_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送档人ID',
  `send_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '送档人姓名',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '送档时间',
  `is_receive` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否接收（SF）',
  `receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接收人ID',
  `receive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接收人姓名',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '接收时间',
  `borrow_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '借阅状态代码（JYZT）',
  `borrow_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '借阅人ID',
  `borrow_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '借阅人姓名',
  `borrow_time` datetime(0) NULL DEFAULT NULL COMMENT '借阅时间',
  `back_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归还人ID',
  `back_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归还人姓名',
  `back_time` datetime(0) NULL DEFAULT NULL COMMENT '归还时间',
  `is_copy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否复印（SF）',
  `copy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '复印人ID',
  `copy_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '复印人姓名',
  `copy_time` datetime(0) NULL DEFAULT NULL COMMENT '复印时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '借阅状态代码（JYZT）：0、未借，1、借阅，2、归还' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_cost
-- ----------------------------
DROP TABLE IF EXISTS `mris_cost`;
CREATE TABLE `mris_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `fy01` decimal(14, 4) NULL DEFAULT NULL COMMENT '总费用',
  `fy02` decimal(14, 4) NULL DEFAULT NULL COMMENT '西药费',
  `fy03` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药费',
  `fy04` decimal(14, 4) NULL DEFAULT NULL COMMENT '中成药费',
  `fy05` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药费',
  `fy06` decimal(14, 4) NULL DEFAULT NULL COMMENT '其他费用',
  `fy07` decimal(14, 4) NULL DEFAULT NULL COMMENT '自费金额',
  `zhylfwl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费',
  `zhylfwl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（2）一般治疗操作费',
  `zhylfwl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（3）护理费',
  `zhylfwl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（4）其他费用',
  `zhylfwl05` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费（中医辨证论治费）',
  `zhylfwl06` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费（中医辨证论治会诊费）',
  `zdl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(5) 病理诊断费',
  `zdl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(6) 实验室诊断费',
  `zdl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(7) 影像学诊断费',
  `zdl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(8) 临床诊断项目费',
  `zll01` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：(9) 非手术治疗项目费',
  `zll02` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：非手术治疗项目费 其中临床物理治疗费',
  `zll03` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：(10) 手术治疗费',
  `zll04` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：手术治疗费 其中麻醉费',
  `zll05` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：手术治疗费 其中手术费',
  `kfl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '康复类：(11) 康复费',
  `xyl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '西药类： 西药费 其中抗菌药物费用',
  `zyf01` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药费:医疗机构中药制剂费',
  `xyzpl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 血费',
  `xyzpl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 白蛋白类制品费',
  `xyzpl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 球蛋白制品费',
  `xyzpl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类：凝血因子类制品费',
  `xyzpl05` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 细胞因子类费',
  `hcl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '耗材类：检查用一次性医用材料费',
  `hcl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '耗材类：治疗用一次性医用材料费',
  `hcl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '耗材类：手术用一次性医用材料费',
  `zyl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医治疗类，中医类总费用',
  `zyl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医诊断费',
  `zyl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医外治费',
  `zyl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医骨伤',
  `zyl05` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：针刺与灸法',
  `zyl06` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医推拿治疗',
  `zyl07` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医肛肠治疗',
  `zyl08` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医特殊治疗',
  `zyl09` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医其他',
  `zyl10` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中药特殊调配加工',
  `zyl11` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：辩证施膳',
  `xyzpl06` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 细胞因子类制品费',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `mris_diagnose`;
CREATE TABLE `mris_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `disease_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型名称',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD版本',
  `disease_icd10_1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD编码_1',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD编码',
  `disease_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病名称',
  `in_situation_code2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情_2',
  `in_situation_code1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情_1',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情',
  `columns_num` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '列数',
  `disease_icd10_2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD编码_2',
  `disease_icd10_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病名称_1',
  `disease_icd10_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病名称_2',
  `disease_code2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型代码2（ZDLX）',
  `disease_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型名称2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病案诊断信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_inpt_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `mris_inpt_diagnose`;
CREATE TABLE `mris_inpt_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `disease_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型名称',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD版本',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD编码',
  `disease_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病名称',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病案诊断信息表(珠海)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_obstetrics
-- ----------------------------
DROP TABLE IF EXISTS `mris_obstetrics`;
CREATE TABLE `mris_obstetrics`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '婴儿ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '婴儿姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `weight` decimal(14, 4) NULL DEFAULT NULL COMMENT '婴儿体重（G）',
  `childbirth_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分娩结果代码（BAFMJG）',
  `outcome_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转归代码（BAZG）',
  `rescue_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '抢救次数',
  `rescue_success_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '抢救成功次数',
  `breathing_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '呼吸代码（BAHX）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_oper_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_oper_info`;
CREATE TABLE `mris_oper_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `oper_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术疾病ID（base_disease）',
  `oper_disease_icd9` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术疾病编码ICD-9（base_disease）',
  `oper_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术疾病名称（base_disease）',
  `notched_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '切口代码（QK）',
  `notched_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '切口名称',
  `heal_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '愈合代码（YH）',
  `heal_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '愈合名称',
  `ana_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '麻醉方式代码（MZFS）',
  `ana_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '麻醉方式名称',
  `oper_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术医生ID',
  `oper_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术医生姓名',
  `oper_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术日期',
  `assistant_id4` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术一助ID',
  `assistant_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术一助姓名',
  `assistant_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术二助ID',
  `assistant_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术二助姓名',
  `assistant_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术三助ID',
  `assistant_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术三助姓名',
  `ana_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第一麻醉医生ID',
  `ana_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第一麻醉医生姓名',
  `ana_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第二麻醉医生ID',
  `ana_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第二麻醉医生姓名',
  `ana_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第三麻醉医生ID',
  `ana_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第三麻醉医生姓名',
  `oper_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术级别代码（SSJB）',
  `oper_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术级别名称',
  `oper_position` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术部位代码（SSBW）',
  `oper_position_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术部位名称',
  `columns_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '行数',
  `other_oper_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '择期手术时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_tcm_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_tcm_info`;
CREATE TABLE `mris_tcm_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '治疗类别代码（ZLLB）',
  `treat_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '治疗类别名称',
  `zzzyzj` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '自制中药制剂代码（ZZZYZJ）',
  `zzzyzjmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '自制中药制剂名称',
  `zyryycyfh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医入院与出院符合代码',
  `zyryycyfhmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医入院与出院符合名称',
  `mjzzyzd` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门（急）诊中医诊断编码',
  `mjzzyzdmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门（急）诊中医诊断名称',
  `sslclj` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实施临床路径代码',
  `sslcljmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实施临床路径名称',
  `syyljgzyzj` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用医疗机构中药制剂代码',
  `syyljgzyzjmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用医疗机构中药制剂名称',
  `syzyzlsb` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗设备代码',
  `syzyzlsbmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗设备名称',
  `syzyzljs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗技术代码',
  `syzyzljsmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗技术名称',
  `bzsh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '辨证施护代码',
  `bzshmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '辨证施护名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_tumour_chemo
-- ----------------------------
DROP TABLE IF EXISTS `mris_tumour_chemo`;
CREATE TABLE `mris_tumour_chemo`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `chemo_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗编号',
  `chemo_time` datetime(0) NULL DEFAULT NULL COMMENT '化疗时间',
  `chemo_durg` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗药物及计量',
  `chemo_course` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗疗程',
  `effect_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疗效代码（LX）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_tumour_info
-- ----------------------------
DROP TABLE IF EXISTS `mris_tumour_info`;
CREATE TABLE `mris_tumour_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `flfs_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '放疗方式代码（FLFS）',
  `flfs_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '放疗方式名称',
  `flcx_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '放疗程序代码（FLCX）',
  `flcx_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '放疗程序名称',
  `flzz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '放疗装置代码（FLZZ）',
  `flzz_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '放疗装置名称',
  `yfzjl` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '原发灶剂量',
  `yfzcs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '原发灶次数',
  `yfzts` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '原发灶天数',
  `yfzkssj` datetime(0) NULL DEFAULT NULL COMMENT '原发灶开始日期',
  `yfzjssj` datetime(0) NULL DEFAULT NULL COMMENT '原发灶结束时间',
  `qylbjjl` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '区域淋巴结剂量',
  `qylbjcs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '区域淋巴结次数',
  `qylbjts` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '区域淋巴结天数',
  `qylbjkssj` datetime(0) NULL DEFAULT NULL COMMENT '区域淋巴结开始时间',
  `qylbjjssj` datetime(0) NULL DEFAULT NULL COMMENT '区域淋巴结结束时间',
  `zyzmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转移灶名称',
  `zyzjl` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转移灶剂量',
  `zyzcs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转移灶次数',
  `zyzts` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转移灶天数',
  `zyzkssj` datetime(0) NULL DEFAULT NULL COMMENT '转移灶开始时间',
  `zyzjssj` datetime(0) NULL DEFAULT NULL COMMENT '转移灶结束时间',
  `hlfs` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗方式代码（HLFS）',
  `hlfsmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗方式名称',
  `hlff` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗方法代码（HLFF）',
  `hlffmc` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '化疗方法名称',
  `zlfqlx` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '肿瘤分期类型代码（ZLFQ）',
  `fqt` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分期T',
  `fqn` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分期N',
  `fqm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分期M',
  `fq` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分期',
  `fqmh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分期编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mris_turn_dept
-- ----------------------------
DROP TABLE IF EXISTS `mris_turn_dept`;
CREATE TABLE `mris_turn_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转出科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转出科室名称',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转入科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转入科室名称',
  `in_dept_time` datetime(0) NULL DEFAULT NULL COMMENT '转科时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msg_temp_record
-- ----------------------------
DROP TABLE IF EXISTS `msg_temp_record`;
CREATE TABLE `msg_temp_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱id',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型(1:库存不足)',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类别',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目id',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `msg` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `accept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接受消息人id',
  `accept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接受消息人姓名',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息状态',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_msg_temp_record_01`(`hosp_code`, `visit_id`, `advice_id`) USING BTREE,
  INDEX `idx_msg_temp_record_02`(`hosp_code`, `type`, `status_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：消息模板记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_account_temp
-- ----------------------------
DROP TABLE IF EXISTS `oper_account_temp`;
CREATE TABLE `oper_account_temp`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模板名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模板使用科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模板使用科室名称',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '五笔码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '手术补记账模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_account_temp_detail
-- ----------------------------
DROP TABLE IF EXISTS `oper_account_temp_detail`;
CREATE TABLE `oper_account_temp_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `temp_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '模板id',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `bfc_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类名称',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(11, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率id（表base_rate）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_oper_account_temp_detail_tempId`(`temp_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '手术补记账模板明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_anesthesia_durg
-- ----------------------------
DROP TABLE IF EXISTS `oper_anesthesia_durg`;
CREATE TABLE `oper_anesthesia_durg`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `anesthesia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉记录ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录类型',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `value1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值1',
  `value2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值2',
  `value3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值3',
  `value4` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值4',
  `value5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值5',
  `value6` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值6',
  `value7` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值7',
  `value8` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值8',
  `value9` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值9',
  `value10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值10',
  `updt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人ID',
  `updt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `updt_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_oper_anesthesia_durg_01`(`hosp_code`, `anesthesia_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_anesthesia_monitor
-- ----------------------------
DROP TABLE IF EXISTS `oper_anesthesia_monitor`;
CREATE TABLE `oper_anesthesia_monitor`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `anesthesia_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉记录ID',
  `monitor_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监测时间',
  `sbp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收缩压',
  `dbp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '舒张压',
  `pulse` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '脉搏',
  `breath` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呼吸',
  `map` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MAP',
  `nose_temperature` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '鼻咽温',
  `rectum_temperature` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '直肠温',
  `updt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人ID',
  `updt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `updt_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_oper_anesthesia_monitor_01`(`hosp_code`, `anesthesia_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_anesthesia_record
-- ----------------------------
DROP TABLE IF EXISTS `oper_anesthesia_record`;
CREATE TABLE `oper_anesthesia_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `anesthesia_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉日期',
  `weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体重 单位：公斤',
  `height` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身高 单位：厘米',
  `oper_disease_before` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术前诊断',
  `oper_disease_after` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术后诊断',
  `asa_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ASA分级代码',
  `proposed_oper` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拟施手术',
  `anesthesia_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉分类代码',
  `anesthesia_method` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉方法',
  `position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术体位',
  `lateraldecubitus_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '侧卧体位',
  `in_room_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入室时间',
  `anesthesia_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉时间',
  `intubation_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '插管时间',
  `extubation_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拨管时间',
  `oper_start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术开始时间',
  `oper_end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术结束时间',
  `back_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回病房/PACU/ICU时间',
  `perform_surgery` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实施手术',
  `operation_doctor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术者',
  `anaesthesia_doctor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉者',
  `handwashing_nurse` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '洗手护士',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `interval_minute` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监测间隔 单位：分钟',
  `updt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人ID',
  `updt_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `updt_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_oper_anesthesia_record_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_info_record
-- ----------------------------
DROP TABLE IF EXISTS `oper_info_record`;
CREATE TABLE `oper_info_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID（inpt_advice.id）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '血型代码（XX）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室ID（住院科室）',
  `oper_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术科室ID',
  `bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前床位ID',
  `bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前床位名称',
  `oper_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术疾病ID（base_disease）',
  `oper_disease_icd9` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术疾病编码ICD-9（base_disease）',
  `oper_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术疾病名称（base_disease）',
  `in_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院主诊断ID（base_disease）',
  `in_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院主诊断名称（base_disease）',
  `in_disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入院主诊断ICD-10码（base_disease）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术主刀医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术主刀医生姓名',
  `assistant_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术一助ID',
  `assistant_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术一助姓名',
  `assistant_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术二助ID',
  `assistant_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术二助姓名',
  `assistant_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术三助ID',
  `assistant_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术三助姓名',
  `special_request` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术特殊要求',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术内容',
  `oper_Type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术执行类别（SSZXLB）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核医生ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核医生姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  `oper_plan_time` datetime(0) NULL DEFAULT NULL COMMENT '手术安排时间',
  `oper_room_Id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术室ID',
  `oper_room_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术室名称',
  `xh_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术类别',
  `xh_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '巡回护士姓名',
  `qx_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '器械护士ID',
  `qx_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '器械护士姓名',
  `ana_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '麻醉方式代码（MZFS）',
  `ana_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一麻醉医生ID',
  `ana_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一麻醉医生姓名',
  `ana_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二麻醉医生ID',
  `ana_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二麻醉医生姓名',
  `ana_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三麻醉医生ID',
  `ana_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三麻醉医生姓名',
  `oper_end_time` datetime(0) NULL DEFAULT NULL COMMENT '手术完成时间',
  `notched_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '切口等级代码（QKDJ）',
  `heal_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '愈合情况代码（YHQK）',
  `oper_time_hour` decimal(14, 4) NULL DEFAULT NULL COMMENT '手术耗时（按小时：1.5小时）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术状态代码（SSZT）',
  `is_cost` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否计费（SF）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '计费总金额',
  `cost_time` datetime(0) NULL DEFAULT NULL COMMENT '计费时间',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计费人ID',
  `cost_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计费人姓名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID/手术申请人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名/手术申请人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间/手术申请时间',
  `rank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术级别',
  `ward_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病区ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_oper_info_record_01`(`hosp_code`, `visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '手术内容：拟 [今天上午 10-23 00:00] [手术申请测试01]\r\n手术状态标志代码：0、已申请，1、取消申请，3、已安排，4、取消安排，5、已完成' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oper_room
-- ----------------------------
DROP TABLE IF EXISTS `oper_room`;
CREATE TABLE `oper_room`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术室编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术室名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术室地址',
  `is_use` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否在用（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_oper_room_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily`;
CREATE TABLE `outin_daily`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '总费用合计',
  `insure_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '合同单位总金额',
  `yjss_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '预交金实收总金额',
  `yjtk_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '预交金退款总金额',
  `yjjs_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '预交金结算总金额',
  `yjcd_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '预交金冲抵总金额',
  `yktcz_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '一卡通充值总金额',
  `ykttk_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '一卡通退款总金额',
  `back_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '退款总金额（减）',
  `preferential_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '优惠总金额（减）',
  `round_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '舍入总金额（减）',
  `card_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '一卡通支付总金额',
  `reality_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '实缴总金额',
  `is_ok` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否缴款确认（SF）',
  `ok_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认缴款人ID',
  `ok_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认缴款人姓名',
  `ok_time` datetime(0) NULL DEFAULT NULL COMMENT '确认缴款时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人/缴款人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人/缴款人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建/缴款时间',
  `credit_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '挂账总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_01`(`hosp_code`, `daily_no`, `crte_id`) USING BTREE,
  INDEX `idx_outin_daily`(`hosp_code`, `daily_no`, `is_ok`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '缴款类型代码：0、门诊挂号，1、门诊收费，2、住院\r\n应缴总金额：费用合计-合同单位支付总金额' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_advance_pay
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_advance_pay`;
CREATE TABLE `outin_daily_advance_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '支付总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_advance_pay_01`(`hosp_code`, `daily_no`, `daily_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_card_pay
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_card_pay`;
CREATE TABLE `outin_daily_card_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '支付总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_card_pay_01`(`hosp_code`, `daily_no`, `daily_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '缴款一卡通支付情况表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_finclassify
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_finclassify`;
CREATE TABLE `outin_daily_finclassify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '总费用合计',
  `preferential_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '优惠总金额',
  `reality_total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '优惠后总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_finclassify_01`(`hosp_code`, `daily_no`, `daily_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_insure
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_insure`;
CREATE TABLE `outin_daily_insure`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保类型代码（YBLX）',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '统筹支付总金额',
  `insure_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '医保支付总金额',
  `personal_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '个人账户支付总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_insure_01`(`hosp_code`, `daily_no`, `daily_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_invoice
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_invoice`;
CREATE TABLE `outin_daily_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `num` int(11) NULL DEFAULT NULL COMMENT '发票张数',
  `start_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票起始号码',
  `end_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票结束号码',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '发票费用合计',
  `zf_num` int(11) NULL DEFAULT NULL COMMENT '发票作废数量',
  `tf_num` int(11) NULL DEFAULT NULL COMMENT '发票退费数量',
  `use_num` int(11) NULL DEFAULT NULL COMMENT '发票使用数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_invoice_01`(`hosp_code`, `daily_no`, `daily_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_daily_pay
-- ----------------------------
DROP TABLE IF EXISTS `outin_daily_pay`;
CREATE TABLE `outin_daily_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `daily_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结ID',
  `daily_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结单号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴款类型代码（JKLX）',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `total_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '支付总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_daily_pay_01`(`hosp_code`, `daily_no`, `daily_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_invoice
-- ----------------------------
DROP TABLE IF EXISTS `outin_invoice`;
CREATE TABLE `outin_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领用人ID',
  `receive_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领用人姓名',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '领用时间',
  `use_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用人ID',
  `use_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用人姓名',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票据类型代码',
  `prefix` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票据号前缀',
  `start_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '起始号码',
  `end_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终止号码',
  `curr_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前号码',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用状态代码',
  `num` int(11) NULL DEFAULT NULL COMMENT '发票剩余数量',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_invoice_01`(`hosp_code`, `receive_id`, `use_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票据领用信息表\r\n\r\n票据类型代码：0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outin_invoice_detail
-- ----------------------------
DROP TABLE IF EXISTS `outin_invoice_detail`;
CREATE TABLE `outin_invoice_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票ID（outin_invoice）',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票号码',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用状态代码',
  `use_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID（outpt_settle）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outin_invoice_detail_01`(`hosp_code`, `invoice_id`, `invoice_no`) USING BTREE,
  INDEX `idx_outin_invoice_detail_02`(`hosp_code`, `settle_id`) USING BTREE,
  INDEX `idx_outin_invoice_detail_001`(`settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票据使用情况明细表\r\n\r\n使用状态代码：0、已使用，1、退领，2、作废' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classes
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classes`;
CREATE TABLE `outpt_classes`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次名称',
  `start_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次开始时间：时分秒',
  `end_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次结束时间：时分秒',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_classes_01`(`start_date`) USING BTREE,
  INDEX `idx_outpt_classes_02`(`end_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classes_doctor
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classes_doctor`;
CREATE TABLE `outpt_classes_doctor`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `cc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别排班ID',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生ID',
  `register_num` int(11) NULL DEFAULT NULL COMMENT '限号数',
  `clinic_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_classes_doctor_01`(`cc_id`) USING BTREE,
  INDEX `idx_outpt_classes_doctor_02`(`doctor_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classes_queue
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classes_queue`;
CREATE TABLE `outpt_classes_queue`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `queue_date` date NULL DEFAULT NULL COMMENT '队列日期',
  `cs_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次ID',
  `cc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别排班ID（outpt_classify_classes）',
  `cy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号类别ID（outpt_classify）',
  `triage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊台ID',
  `triage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊方式代码(FZFS)',
  `register_num` int(11) NULL DEFAULT NULL COMMENT '限号数',
  `gen_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成方式代码（SCFS）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号科室ID',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_classes_queue_01`(`dept_id`) USING BTREE,
  INDEX `idx_outpt_classes_queue_02`(`queue_date`) USING BTREE,
  INDEX `idx_outpt_classes_queue_03`(`cy_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生成方式代码：0、自动生成，1、手动生成' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classify
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classify`;
CREATE TABLE `outpt_classify`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号科室ID',
  `visit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊类别代码（JZLB）',
  `register_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号类别代码（GHLB）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_expert` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否专家（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_classify_01`(`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊类别，01：门诊 02 ：急诊 \r\n挂号类别代码：1、主任医师，2、主治医师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classify_classes
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classify_classes`;
CREATE TABLE `outpt_classify_classes`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `cs_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次ID',
  `cy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号类别ID（outpt_classify）',
  `triage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊台ID',
  `triage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊方式代码(FZFS)',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号科室ID',
  `weeks` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '星期',
  `register_num` int(11) NULL DEFAULT NULL COMMENT '限号数',
  `is_call` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否叫号（SF）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_classify_classes_01`(`cy_id`) USING BTREE,
  INDEX `idx_outpt_classify_classes_02`(`dept_id`) USING BTREE,
  INDEX `idx_outpt_classify_classes_03`(`weeks`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '星期，使用1....7 表示星期1-7\r\n分诊台ID：科室表中科室性质为分诊台的科室ID' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_classify_cost
-- ----------------------------
DROP TABLE IF EXISTS `outpt_classify_cost`;
CREATE TABLE `outpt_classify_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `cy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号类别ID（outpt_classify）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（项目/材料）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目数量',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_classify_cost_01`(`cy_id`) USING BTREE,
  INDEX `idx_outpt_classify_cost_02`(`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_cost
-- ----------------------------
DROP TABLE IF EXISTS `outpt_cost`;
CREATE TABLE `outpt_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID（outpt_visit）',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方ID（outpt_prescribe）',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细id（outpt_prescribe_detail）',
  `ope_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方执行签名ID（outpt_prescribe_exec）',
  `source_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用来源方式代码（FYLYFS）',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用来源ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原费用ID',
  `source_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源科室ID（直接划价来源收费室，划价收费来源开方科室）',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药付（剂）数',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '项目总金额',
  `preferential_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠总金额',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后总金额',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `is_dist` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已发药（SF）',
  `back_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '退药数量',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID（outpt_settle）',
  `settle_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算状态代码： 0未结算，1预结算，2已结算',
  `one_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原结算ID(记录第一次结算id)',
  `self_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保自费比例',
  `is_technology_ok` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否医技确费：0、未确认，1、已确认',
  `technology_ok_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技确费人ID',
  `technology_ok_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技确费人姓名',
  `technology_ok_time` datetime(0) NULL DEFAULT NULL COMMENT '医技确费时间',
  `avg_buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '平均购进价（药品、材料中的平均购进价）',
  `avg_sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '平均零售价（药品、材料中的平均零售价）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方医生名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方科室ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `distribute_all_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药批次汇总id',
  `lmt_user_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用标志',
  `lim_user_explain` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用说明',
  `is_reimburse` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否报销',
  `is_upload` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '招采商品销售是否上传(SF)',
  `settle_invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票结算信息Id -> outpt_settle_invoice的Id\r\n',
  `is_technology` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否医技 0：不是  1：是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_cost_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_cost_02`(`settle_id`) USING BTREE,
  INDEX `idx_outpt_cost_03`(`item_id`) USING BTREE,
  INDEX `idx_outpt_cost_04`(`crte_time`) USING BTREE,
  INDEX `idx_outpt_cost_05`(`op_id`, `opd_id`) USING BTREE,
  INDEX `idx_outpt_cost_06`(`settle_invoice_id`) USING BTREE,
  INDEX `idx_outpt_cost_07`(`distribute_all_detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '费用来源方式代码：\r\n门诊使用：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试，5：皮' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `outpt_diagnose`;
CREATE TABLE `outpt_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '102' COMMENT '诊断类型代码（ZDLX）',
  `is_main` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否主诊断（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `custom_disease` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcm_syndromes_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医症候Id',
  `tcm_syndromes_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医症候名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_diagnose_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_diagnose_02`(`disease_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_doctor_queue
-- ----------------------------
DROP TABLE IF EXISTS `outpt_doctor_queue`;
CREATE TABLE `outpt_doctor_queue`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `cq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次队列ID（outpt_classes_queue）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生姓名',
  `is_valid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `register_num` int(11) NULL DEFAULT NULL COMMENT '限号数',
  `clinic_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊室ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_doctor_queue_01`(`cq_id`) USING BTREE,
  INDEX `idx_outpt_doctor_queue_02`(`doctor_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、加号处理 ：0 or null:可随意加号 1:必须挂号排班号后才能加号\r\n\r\n2、实际加号排序' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_doctor_register
-- ----------------------------
DROP TABLE IF EXISTS `outpt_doctor_register`;
CREATE TABLE `outpt_doctor_register`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生队列ID',
  `register_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '号源时间=号源时间/号源数',
  `start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '号源开始时间：1:00',
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '号源结束时间：1:30',
  `register_num` int(11) NULL DEFAULT NULL COMMENT '分时段限号数',
  `seq_no` int(4) NULL DEFAULT 0 COMMENT '排序号（按照医生来区分,每个医生的排序号每天都会清0）',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `is_use` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已用（SF）',
  `is_lock` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否锁号（SF）（第三方对接）',
  `is_add` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否加号（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_doctor_register_01`(`dq_id`) USING BTREE,
  INDEX `idx_outpt_doctor_register_02`(`start_time`) USING BTREE,
  INDEX `idx_outpt_doctor_register_03`(`end_time`) USING BTREE,
  INDEX `idx_outpt_doctor_register_04`(`profile_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、加号处理 ：0 or null:可随意加号 1:必须挂号排班号后才能加号\r\n\r\n2、实际加号排序' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_infusion_register
-- ----------------------------
DROP TABLE IF EXISTS `outpt_infusion_register`;
CREATE TABLE `outpt_infusion_register`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `exec_date` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人姓名',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_infusion_register_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_infusion_register_02`(`opd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_insure_pay
-- ----------------------------
DROP TABLE IF EXISTS `outpt_insure_pay`;
CREATE TABLE `outpt_insure_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同单位明细代码（HTDW）',
  `org_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构编码',
  `org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保机构名称',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保报销总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_insure_pay_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_insure_pay_02`(`settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同单位明细代码：\r\n0、个人账户，1、基本医疗，2、补充医疗，3、民政，4、协议支付' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_medical_care_apply
-- ----------------------------
DROP TABLE IF EXISTS `outpt_medical_care_apply`;
CREATE TABLE `outpt_medical_care_apply`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '养老院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案id',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（XB）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄单位（NLDW）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `change_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转诊类别（ZZLB：1医转养、2养转医）',
  `care_to_medic_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '养转医申请ID',
  `visit_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '患者类别（1门诊、2住院）',
  `apply_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `apply_company_code` varchar(62) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请入住机构编码',
  `apply_company_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请入住机构名称',
  `hope_in_time` datetime(0) NULL DEFAULT NULL COMMENT '期望入住时间',
  `apply_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人ID',
  `apply_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人姓名',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `is_house` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否入住（SF）',
  `house_bed` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入住床号',
  `referral_main_suit` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转诊主诉',
  `nusre_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护理级别（HLJB）',
  `disease_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诊断id',
  `disease_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诊断名称',
  `reality_in_time` datetime(0) NULL DEFAULT NULL COMMENT '实际入住时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医养申请状态（YYSQZT：0待处理、1已接收、2已拒绝）',
  `visit_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室id',
  `visit_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊医生id',
  `visit_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊医生姓名',
  `visit_time` datetime(0) NULL DEFAULT NULL COMMENT '就诊时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'outpt：门诊\r\nmedical_care：医养\r\napply：申请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_medical_care_configuration
-- ----------------------------
DROP TABLE IF EXISTS `outpt_medical_care_configuration`;
CREATE TABLE `outpt_medical_care_configuration`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `hosp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院名称',
  `org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '养老院编码',
  `org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '养老院名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'outpt：门诊\r\nmedical_care：医养\r\nconfiguration：配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_medical_record
-- ----------------------------
DROP TABLE IF EXISTS `outpt_medical_record`;
CREATE TABLE `outpt_medical_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病ID',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '发病日期',
  `chief_complaint` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诉',
  `present_illness` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现病史',
  `past_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史',
  `oneself_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人史',
  `family_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家族史',
  `allergy_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏史',
  `vaccination_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预防接种史',
  `auxiliary_inspect` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '辅助检查',
  `disease_analysis` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病种分析',
  `handle_suggestion` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '处理意见',
  `specialty_check` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '专科检查',
  `temperature` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体温',
  `min_blood_pressure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最低血压',
  `max_blood_pressure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最高血压',
  `breath` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呼吸',
  `height` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身高',
  `blood_sugar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '血糖',
  `pulse` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '脉搏',
  `weight` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体重',
  `bmi` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'BMI(计算公式:体重（kg）÷身高(米)^2)',
  `entrust` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '嘱托',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `tcm_disease_id` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcm_syndromes_id` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_medical_record_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_medical_record_02`(`profile_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门诊病历表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_medical_template
-- ----------------------------
DROP TABLE IF EXISTS `outpt_medical_template`;
CREATE TABLE `outpt_medical_template`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板类型代码（MBLX），0全院，1科室，2个人',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用科室ID（科室、个人）',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用科室名称（科室、个人）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用医生ID（个人）',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用医生名称（个人）',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `chief_complaint` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主诉',
  `present_illness` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现病史',
  `past_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '既往史',
  `oneself_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人史',
  `family_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家族史',
  `allergy_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏史',
  `vaccination_history` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预防接种史',
  `auxiliary_inspect` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '辅助检查',
  `disease_analysis` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病种分析',
  `handle_suggestion` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '处理意见',
  `specialty_check` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '专科检查',
  `temperature` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体温',
  `min_blood_pressure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最低血压',
  `max_blood_pressure` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最高血压',
  `breath` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呼吸',
  `height` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身高',
  `blood_sugar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '血糖',
  `pulse` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '脉搏',
  `weight` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体重',
  `bmi` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'BMI(计算公式:体重（kg）÷身高(米)^2)',
  `entrust` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '嘱托',
  `remarke` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_medical_template_01`(`dept_id`) USING BTREE,
  INDEX `idx_outpt_medical_template_02`(`doctor_id`) USING BTREE,
  INDEX `idx_outpt_medical_template_03`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门诊病历模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_pay
-- ----------------------------
DROP TABLE IF EXISTS `outpt_pay`;
CREATE TABLE `outpt_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '支付金额',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票据号（微信条码号、支付宝条码号、支票号码）',
  `service_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '手续费（pos）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_pay_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_pay_02`(`settle_id`) USING BTREE,
  INDEX `idx_outpt_pay_03`(`pay_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe`;
CREATE TABLE `outpt_prescribe`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `diagnose_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '诊断ID集合（多个用逗号分开）',
  `tcm_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医诊断id',
  `tcm_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医诊断名称',
  `tcm_syndromes_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医证候id',
  `tcm_syndromes_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中医证候名称',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方单号',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方医生名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开方科室名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方类别代码（CFLB）',
  `prescribe_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方类型代码（CFLX）',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否结算（SF）',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否作废（SF）',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打印（SF）',
  `is_herb_hospital` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药是否本院煎药（SF）(执行次数)',
  `herb_num` decimal(11, 4) NULL DEFAULT NULL COMMENT '中草药付（剂）数 (天数)',
  `herb_use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药用法（ZYYF）',
  `weight` decimal(11, 4) NULL DEFAULT NULL COMMENT '体重（儿科）',
  `agent_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人姓名（精麻）',
  `agent_cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代办人身份编号（精麻）',
  `cancel_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作废人ID',
  `cancel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作废人',
  `cancel_date` datetime(0) NULL DEFAULT NULL COMMENT '作废时间',
  `cancel_reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作废原因',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间（开方日期）',
  `is_submit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否提交',
  `submit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交人ID',
  `submit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交人',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_prescribe_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_prescribe_02`(`order_no`) USING BTREE,
  INDEX `idx_outpt_prescribe_03`(`dept_id`) USING BTREE,
  INDEX `idx_outpt_prescribe_04`(`settle_id`) USING BTREE,
  INDEX `idx_outpt_prescribe_05`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '处方类别：1、西药，2、材料，3、中草药，4、检验LIS，5、检查PACS，6、处置\r\n处方类型：1、普通，2' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_detail
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_detail`;
CREATE TABLE `outpt_prescribe_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `opt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方模板ID',
  `optd_group_no` int(11) NULL DEFAULT NULL COMMENT '处方模板内组号',
  `optd_group_seq_no` int(11) NULL DEFAULT NULL COMMENT '处方模板组内序号',
  `optd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方模板明细ID',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '处方组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '处方组内序号',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否皮试（SF）',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否阳性（SF）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方内容',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药药房ID',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `exec_date` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人姓名',
  `exec_num` int(11) NULL DEFAULT NULL COMMENT '本院执行次数',
  `technology_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医技申请单号',
  `skin_durg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品ID',
  `skin_phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品药房ID',
  `skin_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品单位代码（DW）',
  `prescribe_prefix` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方前缀',
  `prescribe_suffix` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方后缀',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_prescribe_detail_01`(`hosp_code`, `visit_id`, `op_id`) USING BTREE,
  INDEX `ix_outpt_prescribe_detail_itemId`(`item_id`) USING BTREE,
  INDEX `idx_outpt_prescribe_detail_02`(`op_id`, `hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '处方明细(lc_cf02)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_detail_ext
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_detail_ext`;
CREATE TABLE `outpt_prescribe_detail_ext`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '处方组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '处方组内序号',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱目录ID（base_advice.id）',
  `self_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '医保自费比例',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方内容',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药药房ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `lmt_user_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用标志',
  `lim_user_explain` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制使用说明',
  `is_reimburse` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否报销',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_prescribe_detail_ext_01`(`hosp_code`, `visit_id`, `op_id`, `opd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_exec
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_exec`;
CREATE TABLE `outpt_prescribe_exec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `plan_exec_date` datetime(0) NULL DEFAULT NULL COMMENT '计划执行时间',
  `sign_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人姓名',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `exec_time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `second_exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人ID',
  `second_exec_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二执行人姓名',
  `is_print` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否打印（SF）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_prescribe_exec_01`(`hosp_code`, `visit_id`, `opd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_temp
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_temp`;
CREATE TABLE `outpt_prescribe_temp`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板类型代码（MBLX），0全院，1科室，2个人',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用科室ID（科室、个人）',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用科室名称（科室、个人）',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用医生ID（个人）',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板使用医生名称（个人）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效（SF）',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_prescribe_temp_01`(`hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_prescribe_temp_detail
-- ----------------------------
DROP TABLE IF EXISTS `outpt_prescribe_temp_detail`;
CREATE TABLE `outpt_prescribe_temp_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `opt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方模板ID',
  `group_no` int(11) NULL DEFAULT NULL COMMENT '处方模板组号',
  `group_seq_no` int(11) NULL DEFAULT NULL COMMENT '处方模板组内序号',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方类别代码（CFLB）',
  `prescribe_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方类型代码（CFLX）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品、项目、材料、医嘱目录）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `rate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '频率ID',
  `speed_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '速度代码（SD）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量单位（DW）',
  `total_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '总数量（数量*频率*用药天数）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `use_days` int(11) NULL DEFAULT NULL COMMENT '用药天数',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `herb_note_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中草药脚注代码（ZYJZ）（中药调剂方法）',
  `is_skin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否皮试：0否、1是（SF）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_prescribe_temp_detail_01`(`hosp_code`, `opt_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '处方模板明细表(lc_mb02)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_refund_apply
-- ----------------------------
DROP TABLE IF EXISTS `outpt_refund_apply`;
CREATE TABLE `outpt_refund_apply`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '费用ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目ID（项目/材料）',
  `item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目名称（项目/材料）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `num_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位（DW）',
  `refund_xplain` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '退费说明',
  `refund_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '退费状态代码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '申请状态 1：已申诉待确认；2：确认申请',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '结算id',
  `one_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '原结算ID(记录第一次结算id)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_refund_apply_01`(`cost_id`) USING BTREE,
  INDEX `idx_outpt_refund_apply_02`(`settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '门诊医生退费申请记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register`;
CREATE TABLE `outpt_register`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `register_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号单号（单据生成规则表）',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（XB）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型代码',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `source_bz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源标志代码（LYBZ）',
  `visit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊类别代码（JZLB）',
  `source_tj_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人来源途径代码（LYTJ）',
  `source_tj_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人来源途径备注',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '挂号时间',
  `cf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号类别ID',
  `cq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '坐诊班次ID',
  `dq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生队列ID',
  `dr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生号源明细ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号科室名称',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号医生姓名',
  `is_cancel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否作废（SF）',
  `is_first_visit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否初诊（SF）',
  `is_add` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否加号（SF）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业代码（ZY）',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_register_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_register_02`(`register_no`) USING BTREE,
  INDEX `idx_outpt_register_03`(`dept_id`) USING BTREE,
  INDEX `idx_outpt_register_04`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊类别:01 门诊 02 急诊 参照码表：ZQ\r\n\r\n状态标志:            0：正常 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register_detail
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register_detail`;
CREATE TABLE `outpt_register_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（项目/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `bfc_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '财务分类ID',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '项目单价',
  `num` decimal(11, 4) NULL DEFAULT NULL COMMENT '项目数量',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `preferential_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠配置ID',
  `total_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '项目总金额',
  `preferential_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '优惠总金额',
  `card_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '一卡通支付金额',
  `credit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '挂账金额',
  `reality_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '实收总金额',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `origin_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原费用明细ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_register_detail_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_register_detail_02`(`register_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '状态标志:0：正常 1：被冲红 2：冲红' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register_pay
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register_pay`;
CREATE TABLE `outpt_register_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `rs_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式代码（ZFFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票据号（微信条码号、支付宝条码号、支票号码）',
  `price` decimal(11, 4) NULL DEFAULT NULL COMMENT '支付金额',
  `service_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '手续费（针对POS机）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_register_pay_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_register_pay_02`(`rs_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_register_settle
-- ----------------------------
DROP TABLE IF EXISTS `outpt_register_settle`;
CREATE TABLE `outpt_register_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号ID',
  `total_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '挂号总金额',
  `preferential_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '优惠总金额',
  `card_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '一卡通支付金额',
  `credit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '挂账金额',
  `reality_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '实收总金额',
  `settle_time` datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结缴款ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `origin_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原结算ID',
  `bill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票段ID',
  `bill_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票据号码',
  `pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `credit_is_pay` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否补缴',
  `back_pay_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补缴时间',
  `back_pay_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补缴人id',
  `back_pay_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补缴人姓名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_register_settle_01`(`register_id`) USING BTREE,
  INDEX `idx_outpt_register_settle_02`(`settle_time`) USING BTREE,
  INDEX `idx_outpt_register_settle_03`(`crte_id`) USING BTREE,
  INDEX `idx_outpt_register_settle_04`(`daily_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊类别:01 门诊 02 急诊 参照码表：ZQ\r\n\r\n状态标志:            0：正常 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_settle
-- ----------------------------
DROP TABLE IF EXISTS `outpt_settle`;
CREATE TABLE `outpt_settle`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `settle_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算单号',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `settle_time` datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后总金额',
  `trunc_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '舍入金额（存在正负金额）',
  `card_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '一卡通支付金额',
  `actual_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '实收金额',
  `self_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '个人支付金额',
  `mi_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '统筹支付金额',
  `is_settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否结算（SF）',
  `daily_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日结缴款ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冲红ID',
  `is_print` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打印（SF）',
  `old_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原结算ID',
  `is_print_list` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打印清单（SF）',
  `print_list_time` datetime(0) NULL DEFAULT NULL COMMENT '清单打印时间',
  `source_pay_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付来源代码（ZFLY，第三方对接）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付订单号（第三方订单号）',
  `one_settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原结算ID(记录第一次结算id)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `acct_pay` decimal(16, 2) NULL DEFAULT NULL COMMENT '个人账户金额',
  `credit_price` decimal(14, 2) NULL DEFAULT NULL COMMENT '挂账金额',
  `credit_is_pay` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否补缴',
  `back_pay_time` datetime(0) NULL DEFAULT NULL COMMENT '补缴时间',
  `back_pay_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补缴人id',
  `back_pay_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补缴人姓名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_settle_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_settle_02`(`settle_time`) USING BTREE,
  INDEX `idx_outpt_settle_03`(`settle_no`) USING BTREE,
  INDEX `idx_outpt_settle_04`(`crte_id`) USING BTREE,
  INDEX `idx_outpt_settle_05`(`daily_settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '舍入方式：\r\n0、不处理\r\n1、按角4舍5入：1.15块=1.2块\r\n2、按角舍 ：1.1' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_settle_invoice
-- ----------------------------
DROP TABLE IF EXISTS `outpt_settle_invoice`;
CREATE TABLE `outpt_settle_invoice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '就诊ID',
  `invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票ID',
  `invoice_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票明细ID',
  `invoice_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票号码',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '发票总金额',
  `print_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票打印人ID',
  `print_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票打印人姓名',
  `print_time` datetime(0) NULL DEFAULT NULL COMMENT '发票打印时间',
  `print_num` int(11) NULL DEFAULT NULL COMMENT '发票打印次数',
  `is_main` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否主单（SF）',
  `main_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主单发票ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志代码（ZTBZ）',
  `red_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冲红ID',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `divide_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分单备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_settle_invoice_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_settle_invoice_02`(`settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '结算票据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_settle_invoice_content
-- ----------------------------
DROP TABLE IF EXISTS `outpt_settle_invoice_content`;
CREATE TABLE `outpt_settle_invoice_content`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `settle_invoice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算发票ID（outpt_settle_invoice）',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊发票代码',
  `out_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊发票名称',
  `reality_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '优惠后总金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_settle_invoice_content_01`(`hosp_code`, `settle_invoice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票据内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_skin_result
-- ----------------------------
DROP TABLE IF EXISTS `outpt_skin_result`;
CREATE TABLE `outpt_skin_result`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `skin_durg_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品ID',
  `skin_durg_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试药品名称',
  `is_positive` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否阳性（SF）',
  `skin_result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '皮试结果代码（PSJG）',
  `exec_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行科室ID',
  `exec_date` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `exec_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `exec_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人姓名',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_skin_result_01`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_skin_result_02`(`opd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_triage_visit
-- ----------------------------
DROP TABLE IF EXISTS `outpt_triage_visit`;
CREATE TABLE `outpt_triage_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '医院编码',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `cq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次队列ID',
  `dq_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生队列ID',
  `triage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊台ID',
  `triage_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊台名称',
  `triage_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊号',
  `clinic_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊室ID',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生ID',
  `doctor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生名称',
  `clinic_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊室名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室ID',
  `dept_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室名称',
  `sort_no` int(32) NULL DEFAULT NULL COMMENT '排序号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人姓名',
  `triage_start_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊状态（FZZT）',
  `is_call` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可叫号（SF）',
  `call_time` datetime(0) NULL DEFAULT NULL COMMENT '叫号时间',
  `call_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '叫号次数',
  `is_loss` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否过号',
  `loss_time` datetime(0) NULL DEFAULT NULL COMMENT '过号时间',
  `remark` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `triage_peo_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊人ID',
  `triage_peo_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分诊人姓名',
  `triage_peo_time` datetime(0) NULL DEFAULT NULL COMMENT '分诊时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_triage_visit_01`(`register_id`) USING BTREE,
  INDEX `idx_outpt_triage_visit_02`(`visit_id`) USING BTREE,
  INDEX `idx_outpt_triage_visit_03`(`dept_id`) USING BTREE,
  INDEX `idx_outpt_triage_visit_04`(`doctor_id`) USING BTREE,
  INDEX `idx_outpt_triage_visit_05`(`triage_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for outpt_visit
-- ----------------------------
DROP TABLE IF EXISTS `outpt_visit`;
CREATE TABLE `outpt_visit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `profile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人档案ID',
  `out_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊档案号（挂号时生成）',
  `register_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号ID',
  `register_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂号单号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族代码（MZ）',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `now_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地地址',
  `visit_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊号（根据单据规则生成）',
  `visit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊类别代码（JZLB）',
  `patient_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人类型代码（BRLX）',
  `preferential_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠类别ID',
  `source_tj_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人来源途径代码（LYTJ）',
  `source_tj_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人来源途径备注',
  `insure_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保类型代码（CBLX）',
  `insure_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保机构编码',
  `insure_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参保号',
  `insure_biz_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保业务类型编码',
  `insure_treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保待遇类型编码',
  `insure_patient_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保病人ID',
  `insure_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医保合同单位备注',
  `doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊医生ID',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊医生名称',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊科室名称',
  `visit_time` datetime(0) NULL DEFAULT NULL COMMENT '就诊时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_visit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否就诊（SF）',
  `is_first_visit` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否复诊（SF）',
  `tran_in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '转入院代码（ZRY）：0、未开住院证，1、已开住院证、2、已入院登记',
  `in_cert_time` datetime(0) NULL DEFAULT NULL COMMENT '开住院证时间',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建议入院科室ID',
  `in_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建议入院诊断ID',
  `in_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建议入院备注',
  `advance_payment` decimal(14, 4) NULL DEFAULT NULL COMMENT '建议预交金',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `is_food_borne` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否食源性：1.是 0.否）',
  `is_phys` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为体检登记：1是',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业代码（ZY）',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outpt_visit_01`(`register_id`) USING BTREE,
  INDEX `idx_outpt_visit_02`(`cert_no`) USING BTREE,
  INDEX `idx_outpt_visit_03`(`visit_time`) USING BTREE,
  INDEX `idx_outpt_visit_04`(`visit_no`) USING BTREE,
  INDEX `idx_outpt_visit_05`(`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pacs_inspect_result
-- ----------------------------
DROP TABLE IF EXISTS `pacs_inspect_result`;
CREATE TABLE `pacs_inspect_result`  (
  `trade_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '交易编码',
  `hospital_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `hospital_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院名称',
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '授权码',
  `checkresult` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '数据列表'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_apply
-- ----------------------------
DROP TABLE IF EXISTS `phar_apply`;
CREATE TABLE `phar_apply`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `in_stro_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库库位ID',
  `out_stro_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库库位ID',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_out` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否出库状态代码(SF)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_apply_idx_01`(`hosp_code`) USING BTREE,
  INDEX `phar_apply_idx_02`(`in_stro_id`) USING BTREE,
  INDEX `phar_apply_idx_03`(`out_stro_id`) USING BTREE,
  INDEX `phar_apply_idx_04`(`crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\napply：申领（申请领取）\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_apply_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_apply_detail`;
CREATE TABLE `phar_apply_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `apply_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申领ID',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产企业ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_apply_detail_idx_01`(`hosp_code`) USING BTREE,
  INDEX `phar_apply_detail_idx_02`(`item_id`) USING BTREE,
  INDEX `phar_apply_detail_idx_03`(`apply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\napply：申领（申请领取）\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_distribute
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_distribute`;
CREATE TABLE `phar_in_distribute`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药窗口ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `order_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据类型代码（DJLX）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) NULL DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) NULL DEFAULT NULL COMMENT '发药时间',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志（ZTZT）：0、正常，1、被冲红，2、冲红',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_in_distribute_idx_01`(`hosp_code`) USING BTREE,
  INDEX `phar_in_distribute_idx_02`(`dist_time`) USING BTREE,
  INDEX `phar_in_distribute_idx_03`(`order_no`) USING BTREE,
  INDEX `phar_in_distribute_idx_04`(`status_code`) USING BTREE,
  INDEX `phar_in_distribute_idx_05`(`phar_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\ndistribut' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_distribute_all_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_distribute_all_detail`;
CREATE TABLE `phar_in_distribute_all_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `distribute_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药ID',
  `ir_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药申请ID',
  `ird_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药申请明细ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `old_dist_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原发药明细id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_01`(`hosp_code`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_02`(`crte_time`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_03`(`item_id`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_04`(`ird_id`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_05`(`ir_id`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_06`(`distribute_id`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_07`(`visit_id`) USING BTREE,
  INDEX `phar_in_distribute_all_detail_idx_08`(`old_dist_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：药房住院分批发药明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_distribute_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_distribute_detail`;
CREATE TABLE `phar_in_distribute_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `distribute_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药ID',
  `ir_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药申请ID',
  `ird_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药申请明细ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `stock_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库存明细ID',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `up_batch_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期批号结余数量',
  `batch_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期批号结余数量',
  `up_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期购进总金额',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期购进总金额',
  `up_sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期零售总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期零售总金额',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  `distribute_all_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药明细总表',
  `old_dist_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原发药明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_01`(`hosp_code`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_02`(`item_id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_03`(`ird_id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_04`(`ir_id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_05`(`distribute_id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_06`(`visit_id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_07`(`old_dist_id`) USING BTREE,
  INDEX `phar_in_distribute_detail_idx_08`(`distribute_all_detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\ndistribut' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_receive
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_receive`;
CREATE TABLE `phar_in_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药窗口ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `order_receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药单据类型ID（base_order_receive.id）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) NULL DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) NULL DEFAULT NULL COMMENT '发药时间',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药状态代码（LYZT）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_in_receive_idx_01`(`crte_time`) USING BTREE,
  INDEX `phar_in_receive_idx_02`(`order_no`) USING BTREE,
  INDEX `phar_in_receive_idx_03`(`phar_id`) USING BTREE,
  INDEX `phar_in_receive_idx_04`(`dept_id`) USING BTREE,
  INDEX `phar_in_receive_idx_05`(`hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\nreceive：领' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_receive_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_receive_detail`;
CREATE TABLE `phar_in_receive_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `wr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待领ID',
  `receive_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药申请ID',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_in_receive_detail_idx_01`(`hosp_code`) USING BTREE,
  INDEX `phar_in_receive_detail_idx_02`(`item_id`) USING BTREE,
  INDEX `phar_in_receive_detail_idx_03`(`wr_id`) USING BTREE,
  INDEX `phar_in_receive_detail_idx_04`(`receive_id`) USING BTREE,
  INDEX `phar_in_receive_detail_idx_05`(`visit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\nreceive：领' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_in_wait_receive
-- ----------------------------
DROP TABLE IF EXISTS `phar_in_wait_receive`;
CREATE TABLE `phar_in_wait_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `advice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱ID',
  `group_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医嘱组号',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `baby_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婴儿ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药状态代码（LYZT）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) NULL DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) NULL DEFAULT NULL COMMENT '发药时间',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `is_emergency` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否紧急（SF）',
  `is_back` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否退药（SF）',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用明细ID',
  `old_wr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原待领ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_01`(`status_code`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_02`(`is_back`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_03`(`dept_id`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_04`(`advice_id`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_05`(`visit_id`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_06`(`cost_id`) USING BTREE,
  INDEX `phar_in_wait_receive_idx_07`(`phar_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nin：住院\r\nwait：等待\r\n                                         ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_distribute
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_distribute`;
CREATE TABLE `phar_out_distribute`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药窗口ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) NULL DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) NULL DEFAULT NULL COMMENT '发药时间',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态标志（ZTZT）：0、正常，1、被冲红，2、冲红',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_out_distribute_idx_01`(`phar_id`) USING BTREE,
  INDEX `phar_out_distribute_idx_02`(`visit_id`) USING BTREE,
  INDEX `phar_out_distribute_idx_03`(`status_code`) USING BTREE,
  INDEX `phar_out_distribute_idx_04`(`crte_time`) USING BTREE,
  INDEX `phar_out_distribute_idx_05`(`settle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\ndistribu' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_distribute_all_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_distribute_all_detail`;
CREATE TABLE `phar_out_distribute_all_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `distribute_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原费用ID',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `back_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '当前退药数量',
  `total_back_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '累计退药数量',
  `old_dist_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原发药明细ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退药状态代码（TYZT）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_out_distribute_all_detail_idx_01`(`item_id`) USING BTREE,
  INDEX `phar_out_distribute_all_detail_idx_02`(`cost_id`) USING BTREE,
  INDEX `phar_out_distribute_all_detail_idx_03`(`op_id`, `opd_id`) USING BTREE,
  INDEX `phar_out_distribute_all_detail_idx_04`(`distribute_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：药房门诊分批发药明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_distribute_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_distribute_detail`;
CREATE TABLE `phar_out_distribute_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `distribute_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `old_cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原费用ID',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `stock_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库存明细ID',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `back_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '当前退药数量',
  `total_back_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '累计退药数量',
  `up_batch_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期批号结余数量',
  `batch_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期批号结余数量',
  `up_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期购进总金额',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期购进总金额',
  `up_sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期零售总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期零售总金额',
  `old_dist_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原发药明细ID',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退药状态代码（TYZT）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  `distribute_all_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药明细总表',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_out_distribute_detail_idx_01`(`item_id`) USING BTREE,
  INDEX `phar_out_distribute_detail_idx_02`(`cost_id`) USING BTREE,
  INDEX `phar_out_distribute_detail_idx_03`(`op_id`, `opd_id`) USING BTREE,
  INDEX `phar_out_distribute_detail_idx_04`(`distribute_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\ndistribu' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_receive
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_receive`;
CREATE TABLE `phar_out_receive`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊ID',
  `settle_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结算ID',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药状态代码（LYZT）',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药窗口ID',
  `assign_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人ID',
  `assign_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配药人姓名',
  `assign_time` datetime(0) NULL DEFAULT NULL COMMENT '配药时间',
  `dist_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人ID',
  `dist_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发药人姓名',
  `dist_time` datetime(0) NULL DEFAULT NULL COMMENT '发药时间',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请科室ID',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_out_receive_idx_01`(`visit_id`) USING BTREE,
  INDEX `phar_out_receive_idx_02`(`phar_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\nreceive：' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_out_receive_detail
-- ----------------------------
DROP TABLE IF EXISTS `phar_out_receive_detail`;
CREATE TABLE `phar_out_receive_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `or_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领药申请ID',
  `op_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方ID',
  `opd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处方明细ID',
  `cost_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费用ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `total_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '总金额',
  `chinese_drug_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药付数',
  `usage_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用法代码（YF）',
  `use_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用药性质代码（YYXZ）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开单单位',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phar_out_receive_detail_idx_01`(`or_id`) USING BTREE,
  INDEX `phar_out_receive_detail_idx_02`(`op_id`, `opd_id`) USING BTREE,
  INDEX `phar_out_receive_detail_idx_03`(`cost_id`) USING BTREE,
  INDEX `phar_out_receive_detail_idx_04`(`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nphar：药房模块缩写，pharmacy\r\nout：门诊\r\nreceive：' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phar_outpt_call
-- ----------------------------
DROP TABLE IF EXISTS `phar_outpt_call`;
CREATE TABLE `phar_outpt_call`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `phar_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发药药房ID',
  `window_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发药窗口ID',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `phar_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '领药号',
  `sort_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '排序号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病人姓名',
  `is_call` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否叫号',
  `call_time` datetime(0) NULL DEFAULT NULL COMMENT '叫号时间',
  `call_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '叫号次数',
  `is_loss` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否过号',
  `loss_time` datetime(0) NULL DEFAULT NULL COMMENT '过号时间',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report_configuration
-- ----------------------------
DROP TABLE IF EXISTS `report_configuration`;
CREATE TABLE `report_configuration`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `temp_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板编码',
  `temp_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表模板文件名称',
  `return_data_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回数据类型 1-文件地址 2-base64编码 3-html',
  `is_upload` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否上传 0-否 1-是',
  `custom_config` json NULL COMMENT '自定义配置',
  `updt_time` datetime(0) NOT NULL COMMENT '更新时间',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report_file_record
-- ----------------------------
DROP TABLE IF EXISTS `report_file_record`;
CREATE TABLE `report_file_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `temp_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板编码',
  `file_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `file_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件地址',
  `crter_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表文件记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sheet1
-- ----------------------------
DROP TABLE IF EXISTS `sheet1`;
CREATE TABLE `sheet1`  (
  `realityPrice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `bfcId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `bfcName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `doctorId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `doctorName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `deptId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `deptName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `visitId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `visitName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_adjust
-- ----------------------------
DROP TABLE IF EXISTS `stro_adjust`;
CREATE TABLE `stro_adjust`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '调前总金额',
  `after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '调后总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_stro_adjust_01`(`hosp_code`, `order_no`) USING BTREE,
  INDEX `idx_stro_adjust_02`(`hosp_code`, `biz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_adjust_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_adjust_detail`;
CREATE TABLE `stro_adjust_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `adjust_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '调价ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '调前零售单价',
  `after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '调后零售单价',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零购进单价',
  `split_before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '调前拆零零售单价',
  `split_after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '调后拆零零售单价',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_adjust_detail_01`(`hosp_code`, `adjust_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                       -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_in
-- ----------------------------
DROP TABLE IF EXISTS `stro_in`;
CREATE TABLE `stro_in`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `in_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库方式代码（CRFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `stock_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库位ID',
  `supplier_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商ID',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `in_order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '整单入库单号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_stro_in_01`(`hosp_code`, `order_no`) USING BTREE,
  INDEX `idx_stro_in_02`(`hosp_code`, `stock_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nin：入库\r\n\r\n                            -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_in_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_in_detail`;
CREATE TABLE `stro_in_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `in_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  `ndan` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prod_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_in_detail_01`(`hosp_code`, `in_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nin：入库\r\ndet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_in_record
-- ----------------------------
DROP TABLE IF EXISTS `stro_in_record`;
CREATE TABLE `stro_in_record`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '医院编码',
  `item_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '入库数量',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '入库购进价',
  `sell_price` decimal(14, 0) NULL DEFAULT NULL COMMENT '零售价',
  `stock_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '库存id',
  `stock_detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '库存明细id',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_record_01`(`stock_detail_id`, `hosp_code`) USING BTREE,
  INDEX `idx_record_02`(`item_id`, `hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_incdec
-- ----------------------------
DROP TABLE IF EXISTS `stro_incdec`;
CREATE TABLE `stro_incdec`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益前总金额',
  `after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益后总金额',
  `price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_stro_incdec_01`(`hosp_code`, `order_no`) USING BTREE,
  INDEX `idx_stro_incdec_02`(`hosp_code`, `biz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_incdec_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_incdec_detail`;
CREATE TABLE `stro_incdec_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `adjust_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '损益ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sell_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `split_price` decimal(11, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `sell_before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益前零售总金额（损益前库存数量 * 零售单价）',
  `sell_after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益后零售总金额（（损益前库存数量 - 损益数量） * 零售单价）',
  `buy_before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益前购进总金额（损益前库存数量 * 购进单价）',
  `buy_after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益后购进总金额（（损益前库存数量 - 损益数量） * 购进单价）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  `before_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益前库存数量',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盘点结论代码（PDJL）',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量（损益前库存数量 * 拆分比）',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `profit_loss_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '损益类型：0.报损  1报溢（SYLX）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_incdec_detail_01`(`hosp_code`, `adjust_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nadjust：调整\r\n                                       -&#&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_inventory
-- ----------------------------
DROP TABLE IF EXISTS `stro_inventory`;
CREATE TABLE `stro_inventory`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `before_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘前总金额',
  `after_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘后总金额',
  `incdec_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_stro_inventory_01`(`hosp_code`, `order_no`) USING BTREE,
  INDEX `idx_stro_inventory_02`(`hosp_code`, `biz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\ninventory：盘点\r\n                                   -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_inventory_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_inventory_detail`;
CREATE TABLE `stro_inventory_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `inventory_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盘点单ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  `before_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘点前总数量',
  `before_split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘点前拆零总数量',
  `final_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '实盘总数量',
  `incdec_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '损益总数量',
  `final_split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '实盘拆零总数量',
  `result_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盘点结论代码（PDJL）',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `split_buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零购进单价',
  `split_sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零零售单价',
  `incdec_buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘点损益购进总金额',
  `incdec_sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘点损益零售总金额',
  `location_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货架号',
  `upload_insure` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进销存是否上传',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_inventory_detail_01`(`hosp_code`, `inventory_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\ninventory：盘点\r\n                                          -' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_invoicing
-- ----------------------------
DROP TABLE IF EXISTS `stro_invoicing`;
CREATE TABLE `stro_invoicing`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `outin_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出入方式代码（CRFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '大单位数量（入库为正，出库为负）',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前单位代码（DW）',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `batch_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '批号结余数量',
  `up_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '上期/期初数量',
  `surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期/期末数量',
  `up_buy_price_all` decimal(14, 4) NULL DEFAULT 0.0000 COMMENT '上期/期初购进总金额',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期/期末购进总金额',
  `up_sell_price_all` decimal(14, 4) NULL DEFAULT 0.0000 COMMENT '上期/期初零售总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本期/期末零售总金额',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID（操作）',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名（操作）',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间（操作）',
  `crte_time_stamp` bigint(13) NULL DEFAULT NULL COMMENT '时间戳',
  `invoicing_target_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进销存目标id',
  `invoicing_target_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进销存目标名称',
  `new_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '新价格',
  `new_split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '新拆零单价',
  `upload_insure` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否上传到医保',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `select_inin`(`item_id`, `invoicing_target_id`) USING BTREE,
  INDEX `select_444`(`upload_insure`) USING BTREE,
  INDEX `idx_stro_invoicing_02`(`hosp_code`, `item_id`, `biz_id`) USING BTREE,
  INDEX `idx_stro_invoicing_01`(`hosp_code`, `biz_id`, `crte_time`, `outin_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nInvoicing：进销存\r\n                                   ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_invoicing_monthly
-- ----------------------------
DROP TABLE IF EXISTS `stro_invoicing_monthly`;
CREATE TABLE `stro_invoicing_monthly`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室id',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期末数量',
  `up_buy_price_all` decimal(14, 4) NULL DEFAULT 0.0000 COMMENT '本月期初购进总金额',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期末购进总金额',
  `up_sell_price_all` decimal(14, 4) NULL DEFAULT 0.0000 COMMENT '本月期初零售总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期末零售总金额',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间（操作）',
  `new_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '新价格',
  `new_split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '新拆零单价',
  `stro_in_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库入库数量',
  `stro_in_num_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库入库零售总金额',
  `return_supl_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库退供应商数量',
  `return_supl_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库退供应商零售总金额',
  `stro_to_dept_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到科室数量',
  `stro_to_dept_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到科室零售总金额',
  `stro_to_phar_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到药房数量',
  `stro_to_phar_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到药房零售总金额',
  `report_losses_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '报损消耗数量',
  `report_losses_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '报损消耗数量零售总金额',
  `phar_return_stro_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药房退库数量',
  `phar_return_stro_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药房退库零售总金额',
  `adjust_profit_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价调盈零售总金额',
  `adjust_loss_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价亏损零售总金额',
  `take_strock_subtract_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘亏数量',
  `take_strock_subtract_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘亏零售总金额',
  `take_strock_add_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘盈总数量',
  `take_strock_add_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘盈零售总金额',
  `stro_in_num_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库入库购进总金额',
  `return_supl_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库退供应商购进总金额',
  `stro_to_dept_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到科室购进总金额',
  `stro_to_phar_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到药房购进总金额',
  `report_losses_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '报损消耗数量购进总金额',
  `phar_return_stro_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药房退库购进总金额',
  `adjust_profit_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价调盈购进总金额',
  `adjust_loss_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价亏损购进总金额',
  `take_strock_subtract_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘亏购进总金额',
  `take_strock_add_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘盈购进总金额',
  `out_sales_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '门诊销售数量',
  `out_sales_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '门诊销售零售总金额',
  `out_sales_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '门诊销售购进总金额',
  `in_sales_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '住院销售购进总金额',
  `in_sales_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '住院销售零售总金额',
  `in_sales_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '住院销售数量',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `select_inin`(`item_id`) USING BTREE,
  INDEX `idx_stro_invoicing_02`(`hosp_code`, `item_id`, `biz_id`) USING BTREE,
  INDEX `idx_stro_invoicing_01`(`hosp_code`, `biz_id`, `crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nInvoicing：进销存\r\n                                   ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_invoicing_monthly_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_invoicing_monthly_detail`;
CREATE TABLE `stro_invoicing_monthly_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室id',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '大单位数量（入库为正，出库为负）',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `curr_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前单位代码（DW）',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `up_surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期初数量',
  `surplus_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期末数量',
  `up_buy_price_all` decimal(14, 4) NULL DEFAULT 0.0000 COMMENT '本月期初购进总金额',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期末购进总金额',
  `up_sell_price_all` decimal(14, 4) NULL DEFAULT 0.0000 COMMENT '本月期初零售总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '本月期末零售总金额',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间（操作）',
  `new_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '新价格',
  `new_split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '新拆零单价',
  `stro_in_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库入库数量',
  `stro_in_num_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库入库零售总金额',
  `return_supl_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库退供应商数量',
  `return_supl_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库退供应商零售总金额',
  `stro_to_dept_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到科室数量',
  `stro_to_dept_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到科室零售总金额',
  `stro_to_phar_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到药房数量',
  `stro_to_phar_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到药房零售总金额',
  `report_losses_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '报损消耗数量',
  `report_losses_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '报损消耗数量零售总金额',
  `phar_return_stro_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '药房退库数量',
  `phar_return_stro_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药房退库零售总金额',
  `adjust_profit_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价调盈零售总金额',
  `adjust_loss_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价亏损零售总金额',
  `take_strock_subtract_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘亏数量',
  `take_strock_subtract_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘亏零售总金额',
  `take_strock_add_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘盈总数量',
  `take_strock_add_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘盈零售总金额',
  `stro_in_num_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库入库购进总金额',
  `return_supl_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库退供应商购进总金额',
  `stro_to_dept_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到科室购进总金额',
  `stro_to_phar_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药库出库到药房购进总金额',
  `report_losses_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '报损消耗数量购进总金额',
  `phar_return_stro_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '药房退库购进总金额',
  `adjust_profit_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价调盈购进总金额',
  `adjust_loss_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '调价亏损购进总金额',
  `take_strock_subtract_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘亏购进总金额',
  `take_strock_add_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '盘盈购进总金额',
  `out_sales_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '门诊销售数量',
  `out_sales_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '门诊销售零售总金额',
  `out_sales_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '门诊销售购进总金额',
  `in_sales_buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '住院销售购进总金额',
  `in_sales_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '住院销售零售总金额',
  `in_sales_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '住院销售数量',
  `monthly_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '月度进销存主表id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `select_inin`(`item_id`) USING BTREE,
  INDEX `idx_stro_invoicing_02`(`hosp_code`, `item_id`, `biz_id`) USING BTREE,
  INDEX `idx_stro_invoicing_01`(`hosp_code`, `biz_id`, `crte_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nInvoicing：进销存\r\n                                   ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_out
-- ----------------------------
DROP TABLE IF EXISTS `stro_out`;
CREATE TABLE `stro_out`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `out_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库方式代码（CRFS）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `in_order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '整单入库单号',
  `out_stock_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库库位ID',
  `in_stock_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库库位ID',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `ok_audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认审核状态代码（SHZT）',
  `ok_audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认审核人ID',
  `ok_audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认审核人姓名',
  `ok_audit_time` datetime(0) NULL DEFAULT NULL COMMENT '确认审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nout：出库\r\n\r\n                             ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_out_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_out_detail`;
CREATE TABLE `stro_out_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `out_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `dosage` decimal(14, 4) NULL DEFAULT NULL COMMENT '剂量',
  `dosage_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量单位代码（JLDW）',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_out_detail_01`(`hosp_code`, `out_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nout：出库\r\nde' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_purchase
-- ----------------------------
DROP TABLE IF EXISTS `stro_purchase`;
CREATE TABLE `stro_purchase`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据号',
  `supplier_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商ID',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `audit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态代码（SHZT）',
  `audit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_stro_purchase_01`(`hosp_code`, `order_no`) USING BTREE,
  INDEX `idx_stro_purchase_02`(`hosp_code`, `biz_id`, `supplier_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\npurchase：采购\r\n                                  -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_purchase_detail`;
CREATE TABLE `stro_purchase_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `purchase_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购ID',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `seq_no` decimal(14, 4) NULL DEFAULT NULL COMMENT '顺序号',
  `stock_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '当时库存数量',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_purchase_detail_01`(`hosp_code`, `purchase_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\npurchase：采购\r\n                                         -&' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_stock
-- ----------------------------
DROP TABLE IF EXISTS `stro_stock`;
CREATE TABLE `stro_stock`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `location_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库位码（货架号）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '库存总数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `stock_max` decimal(14, 4) NULL DEFAULT NULL COMMENT '库存上限',
  `stock_min` decimal(14, 4) NULL DEFAULT NULL COMMENT '库存下限',
  `stock_occupy` decimal(14, 4) NULL DEFAULT NULL COMMENT '占用库存',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_stock_02`(`item_id`, `hosp_code`) USING BTREE,
  INDEX `idx_stro_stock_01`(`hosp_code`, `biz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nstock：库存\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_stock_detail
-- ----------------------------
DROP TABLE IF EXISTS `stro_stock_detail`;
CREATE TABLE `stro_stock_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '数量',
  `buy_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进单价',
  `sell_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售单价',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `batch_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_stock_detail_01`(`hosp_code`, `biz_id`) USING BTREE,
  INDEX `idx_stro_stock_detail_02`(`hosp_code`, `item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nstock：库存\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stro_stock_time
-- ----------------------------
DROP TABLE IF EXISTS `stro_stock_time`;
CREATE TABLE `stro_stock_time`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `biz_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库房ID（药库/药房）',
  `location_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库位码（货架号）',
  `item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型代码（XMLB）',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID（药品/材料）',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `spec` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `prep_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂型代码（JXFL）',
  `num` decimal(14, 4) NULL DEFAULT NULL COMMENT '库存总数量',
  `unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位代码（DW）',
  `buy_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '购进总金额',
  `sell_price_all` decimal(14, 4) NULL DEFAULT NULL COMMENT '零售总金额',
  `split_ratio` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆分比',
  `split_num` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零数量',
  `split_price` decimal(14, 4) NULL DEFAULT NULL COMMENT '拆零单价',
  `split_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拆零单位代码（DW）',
  `stock_max` decimal(14, 4) NULL DEFAULT NULL COMMENT '库存上限',
  `stock_min` decimal(14, 4) NULL DEFAULT NULL COMMENT '库存下限',
  `stock_occupy` decimal(14, 4) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '占用库存',
  `stock_time` varchar(32) CHARACTER SET utf16le COLLATE utf16le_general_ci NULL DEFAULT NULL COMMENT '月底时间',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stro_stock_01`(`hosp_code`, `biz_id`) USING BTREE,
  INDEX `idx_stro_stock_02`(`item_id`, `hosp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nstro：库房模块缩写，store仓库、room房间\r\nstock：库存\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值域代码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值域名称',
  `show_default` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示默认值',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_tree` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否树形',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_code_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：值域代码表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_code_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_code_detail`;
CREATE TABLE `sys_code_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `c_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值域代码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值域值名称',
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值域值',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `up_value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级值域值',
  `up_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级值域名称',
  `is_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否末级：0否，1是',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效：0否，1是',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_code_detail_01`(`hosp_code`, `c_code`) USING BTREE,
  INDEX `ix_sys_code_detail_value`(`value`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：值域代码表明细表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `hosp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院名称',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工号',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作科室id',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作科室名称',
  `dept_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作科室性质',
  `dept_type_identity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作科室标识',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求IP地址',
  `request_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全路径',
  `request_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求状态(200:成功,500:失败)',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_log_info_cz
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_info_cz`;
CREATE TABLE `sys_log_info_cz`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `czlx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `czms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `czff` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `qqcs` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `fhcs` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回参数',
  `qqlj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '请求路径',
  `czrid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
  `czr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `czip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作IP',
  `xzsj` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `trace_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志唯一编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log_info_yc
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_info_yc`;
CREATE TABLE `sys_log_info_yc`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `qqcs` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `ycmc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常名称',
  `ycxx` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常信息',
  `qqlj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '请求路径',
  `czff` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `czrid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
  `czr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `czip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作IP',
  `xzsj` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `czlx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `czms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `trace_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志唯一编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '异常日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `sys_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统编码：sys_system表系统编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型代码：0、目录，1、菜单',
  `up_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级菜单编码',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单描述',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_button`;
CREATE TABLE `sys_menu_button`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `menu_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码：sys_menu表菜单编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮名称',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮类型代码',
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮图标',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮描述',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '\r\n\r\n1、按钮类型代码：primary、success、info、warning、danger\r\n          ' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `source_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源id',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊id',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室id',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息主题',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `level` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息级别',
  `receiver_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收者id串',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '消息开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '消息结束时间',
  `interval_time` int(10) NULL DEFAULT NULL COMMENT '消息间隔时间',
  `send_count` int(14) NULL DEFAULT NULL COMMENT '发送次数',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息状态',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转页面路径',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '最后执行时间',
  `crte_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数代码',
  `value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数描述',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `is_show` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_need_pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否需要密码(是否SF: 0否，1是)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_sys_p_01`(`hosp_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：系统参数表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_parameter_update
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter_update`;
CREATE TABLE `sys_parameter_update`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `sys_paramter_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统参数表主键id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `before_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前参数代码',
  `after_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后参数代码',
  `before_value` varchar(2500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前参数值',
  `after_value` varchar(2500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后参数值',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数描述',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `is_show` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可见',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_need_pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否需要密码(是否SF: 0否，1是)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_sys_p_01`(`hosp_code`, `before_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表名含义：\r\nbase：系统管理\r\nward：系统参数表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_menu_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_button`;
CREATE TABLE `sys_role_menu_button`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `menu_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `button_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_system`;
CREATE TABLE `sys_system`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统编码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统名称',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `seq_no` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属科室编码',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工工号（登录账户）',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码（初始化密码：888888）',
  `is_in_job` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否在职',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族代码',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况代码',
  `is_pm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否党员',
  `in_pm_date` date NULL DEFAULT NULL COMMENT '入党时间',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型代码',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `photo_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像照片路径',
  `sign_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子签名照片路径',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生介绍',
  `speciality` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生擅长',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭地址',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `education_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历分类代码',
  `major_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所学专业代码',
  `school` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业学校',
  `school_date` date NULL DEFAULT NULL COMMENT '毕业时间',
  `school_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业证书图片路径',
  `work_date` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `work_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职工类型代码(RYZW)',
  `duties_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职称代码',
  `prac_certi_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执业证书编号',
  `prac_certi_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执业证书名称',
  `durg_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品级别代码集合（YPJB）（多个用逗号分开）',
  `ph_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毒麻药品级别代码集合（DMYPJB）（多个用逗号分开）',
  `antibacterial_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抗菌药品级别代码集合（KJYPJB）（多个用逗号分开）',
  `opeartion_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手术级别代码集合（SSJB）（多个用逗号分开）',
  `pswd_err_cnt` int(11) NULL DEFAULT 0 COMMENT '密码错误次数',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `status_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户状态代码',
  `pym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音码',
  `wbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '五笔码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `signature_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签字路径',
  `doctor_intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生简介',
  `is_guide` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否阅读指引',
  `is_anaesthesia` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否具有麻醉权',
  `is_password_change` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '近期是否修改过密码',
  `only_open_item` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否只能开检查项目，（0：还可以开药品，1：只能开检查项目）',
  PRIMARY KEY (`id`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '1、用户状态代码：1正常2停用3密码错误冻结' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `us_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户子系统关系编码',
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_system`;
CREATE TABLE `sys_user_system`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `us_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户子系统关系编码',
  `user_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工工号（登录账户）',
  `system_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统编码',
  `teacher_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '带教员工工号（导师）',
  `dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作科室编码',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tcm_mris_base_info
-- ----------------------------
DROP TABLE IF EXISTS `tcm_mris_base_info`;
CREATE TABLE `tcm_mris_base_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `in_profile` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案号',
  `in_blh` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病理号',
  `in_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住院号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `gender_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别代码（XB）',
  `gender_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别名称',
  `age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄',
  `age_unit_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄单位代码（NLDW）',
  `age_unit_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄单位名称',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `cert_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件类型代码（ZJLX）',
  `cert_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件类型名称',
  `cert_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件号码',
  `nationality_cation` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '国籍代码（GJZD）',
  `nationality_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '国籍名称',
  `nation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '民族代码（MZ）',
  `nation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '民族名称',
  `native_place` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '籍贯',
  `occupation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职业代码（ZY）',
  `occupation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职业名称',
  `marry_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '婚姻状况代码（HYZK）',
  `marry_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '婚姻状况名称',
  `work` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '工作单位',
  `work_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位电话',
  `work_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位邮编',
  `work_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位地址',
  `contact_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人姓名',
  `contact_rela_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人关系（RYGX）',
  `contact_rela_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人关系名称',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人电话',
  `contact_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人邮编',
  `contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人地址',
  `now_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（省）编码',
  `now_prov_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（省）名称',
  `now_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（市）编码',
  `now_city_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（市）名称',
  `now_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（区、县）编码',
  `now_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地（区、县）名称',
  `now_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地邮编',
  `native_prov` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（省）编码',
  `native_prov_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（省）名称',
  `native_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（市）编码',
  `native_city_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（市）名称',
  `native_area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（区、县）编码',
  `native_area_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地（区、县）名称',
  `native_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地邮编',
  `in_cnt` int(11) NULL DEFAULT NULL COMMENT '住院次数',
  `in_time` datetime(0) NULL DEFAULT NULL COMMENT '入院时间',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院科室名称',
  `in_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院床位ID',
  `in_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院床位名称',
  `out_time` datetime(0) NULL DEFAULT NULL COMMENT '出院时间',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院科室名称',
  `out_bed_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院床位ID',
  `out_bed_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院床位名称',
  `in_days` int(11) NULL DEFAULT NULL COMMENT '住院天数',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病版本（疾病分类名称）',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病诊断ICD',
  `disease_icd10_name` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病诊断名称',
  `bl_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病理诊断ICD',
  `bl_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病理诊断名称',
  `director_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室主任ID',
  `director_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室主任姓名',
  `director_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室副主任ID',
  `director_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '科室副主任姓名',
  `zz_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主治医生ID',
  `zz_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主治医生姓名',
  `zg_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主管医生ID',
  `zg_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主管医生姓名',
  `zr_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '责任护士ID',
  `zr_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '责任护士姓名',
  `emr_quality_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历质量代码',
  `emr_quality_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病历质量',
  `zk_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控医生ID',
  `zk_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控医生姓名',
  `zk_nurse_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控护士ID',
  `zk_nurse_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '质控护士姓名',
  `zk_time` datetime(0) NULL DEFAULT NULL COMMENT '质控时间',
  `is_autopsy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否尸检（SF）编码',
  `is_autopsy_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否尸检（SF）名称',
  `blood_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '血型代码（XX）编码',
  `blood_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '血型名称',
  `rh_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'RH代码',
  `rh_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'RH名称',
  `baby_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿体重',
  `is_allergy` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否药物过敏（SF）',
  `allergy_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '过敏药物合集',
  `out_mode_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '离院方式代码（CYFS）',
  `out_mode_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '离院方式名称',
  `turn_org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转院机构名称',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情代码（RYQK）',
  `in_situation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情名称',
  `out_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院病情代码CYBQ）',
  `out_situation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出院病情名称',
  `crte_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人ID',
  `crte_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `crte_time` datetime(0) NOT NULL COMMENT '创建时间',
  `hosp_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗机构名称',
  `pay_way_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗支付方式类型(ZFFS)',
  `pay_way_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗支付方式名称',
  `health_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '健康卡号',
  `baby_birth_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿出生体重',
  `baby_in_weight` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿入院体重',
  `native_adress` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '户口地址',
  `now_adress` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '居住地址',
  `baby_age_month` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年龄（月）不足一周岁',
  `birth_adress` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出生地址',
  `id_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话号码',
  `work_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位名称及地址',
  `in_way` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院途径',
  `in_ward` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病房',
  `damage_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '损伤中毒的外部原因',
  `jx_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '进修医师id',
  `sx_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实习医师id',
  `jx_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '进修医师名称',
  `sx_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实习医师名称',
  `doctor_coder_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码员id',
  `doctor_coder_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码员名称',
  `in_ward2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病房2',
  `in_dept_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院科室id',
  `disease_icd10_other` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病编码（损伤原因）',
  `turn_dept_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科科室',
  `case_classification` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病例分析等级（A/B/C/D）',
  `is_monitor` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否实施重症监控',
  `monitor_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '重症监控天数',
  `monitor_hour` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '重症监控小时',
  `is_dzb` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否单种病管理',
  `lclj_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '临床路径管理状态',
  `drg_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'DGR管理状态',
  `is_kss` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否使用抗生素',
  `is_xjsj` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否细菌送检',
  `is_cry` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '法定传染源类型',
  `zl_level` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '肿瘤分期',
  `apgar_num` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '新生儿Apgar评分',
  `aim` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '目的',
  `is_inpt` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否有出院31天再住院计划',
  `inpt_before_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院前昏迷时间（天）',
  `inpt_before_hour` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院前昏迷时间（时）',
  `inpt_before_minute` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院前昏迷时间（分）',
  `inpt_last_day` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院后昏迷时间（天）',
  `inpt_last_hour` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院后昏迷时间（时）',
  `inpt_last_minute` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院后昏迷时间（分）',
  `yljg_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医疗机构编码',
  `turn_time1` datetime(0) NULL DEFAULT NULL COMMENT '转科时间1',
  `turn_time2` datetime(0) NULL DEFAULT NULL COMMENT '转科时间2',
  `turn_time3` datetime(0) NULL DEFAULT NULL COMMENT '转科时间3',
  `turn_dept1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科1',
  `turn_dept2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科2',
  `turn_dept3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转科3',
  `outpt_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门急诊医生',
  `outpt_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门急诊医生id',
  `case_classify` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病例分型',
  `clinical_path_case` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '临床路径病例',
  `rescue_count` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '抢救次数',
  `rescue_success_count` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '成功次数',
  `in_upload_insure` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否上传医保',
  `is_upload_insure` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '病案是否上传医保',
  `treat_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '治疗类别代码（ZLLB）',
  `treat_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '治疗类别名称',
  `tcm_disease_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门（急）诊中医诊断编码',
  `tcm_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门（急）诊中医诊断名称',
  `clinical_way_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实施临床路径代码',
  `clinical_way_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实施临床路径名称',
  `zyzj_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用医疗机构中药制剂代码',
  `zyzj_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用医疗机构中药制剂名称',
  `zyylsb_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗设备代码',
  `zyylsb_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗设备名称',
  `zyyljs_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗技术代码',
  `zyyljs_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医诊疗技术名称',
  `bzsh_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '辨证施护代码',
  `bzsh_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '辨证施护名称',
  `zyhljs_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '使用中医护理技术'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病案基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tcm_mris_cost
-- ----------------------------
DROP TABLE IF EXISTS `tcm_mris_cost`;
CREATE TABLE `tcm_mris_cost`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `fy01` decimal(14, 4) NULL DEFAULT NULL COMMENT '总费用',
  `fy02` decimal(14, 4) NULL DEFAULT NULL COMMENT '西药费',
  `fy03` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药费',
  `fy04` decimal(14, 4) NULL DEFAULT NULL COMMENT '中成药费',
  `fy05` decimal(14, 4) NULL DEFAULT NULL COMMENT '中草药费',
  `fy06` decimal(14, 4) NULL DEFAULT NULL COMMENT '其他费用',
  `fy07` decimal(14, 4) NULL DEFAULT NULL COMMENT '自费金额',
  `zhylfwl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费',
  `zhylfwl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（2）一般治疗操作费',
  `zhylfwl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（3）护理费',
  `zhylfwl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（4）其他费用',
  `zhylfwl05` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费（中医辨证论治费）',
  `zhylfwl06` decimal(14, 4) NULL DEFAULT NULL COMMENT '综合医疗服务类：（1）一般医疗服务费（中医辨证论治会诊费）',
  `zdl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(5) 病理诊断费',
  `zdl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(6) 实验室诊断费',
  `zdl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(7) 影像学诊断费',
  `zdl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '诊断类：(8) 临床诊断项目费',
  `zll01` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：(9) 非手术治疗项目费',
  `zll02` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：非手术治疗项目费 其中临床物理治疗费',
  `zll03` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：(10) 手术治疗费',
  `zll04` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：手术治疗费 其中麻醉费',
  `zll05` decimal(14, 4) NULL DEFAULT NULL COMMENT '治疗类：手术治疗费 其中手术费',
  `kfl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '康复类：(11) 康复费',
  `xyl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '西药类： 西药费 其中抗菌药物费用',
  `zyf01` decimal(14, 4) NULL DEFAULT NULL COMMENT '中药费:医疗机构中药制剂费',
  `xyzpl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 血费',
  `xyzpl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 白蛋白类制品费',
  `xyzpl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 球蛋白制品费',
  `xyzpl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类：凝血因子类制品费',
  `xyzpl05` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 细胞因子类费',
  `hcl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '耗材类：检查用一次性医用材料费',
  `hcl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '耗材类：治疗用一次性医用材料费',
  `hcl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '耗材类：手术用一次性医用材料费',
  `zyl01` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医治疗类，中医类总费用',
  `zyl02` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医诊断费',
  `zyl03` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医外治费',
  `zyl04` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医骨伤',
  `zyl05` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：针刺与灸法',
  `zyl06` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医推拿治疗',
  `zyl07` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医肛肠治疗',
  `zyl08` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医特殊治疗',
  `zyl09` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中医其他',
  `zyl10` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：中药特殊调配加工',
  `zyl11` decimal(14, 4) NULL DEFAULT NULL COMMENT '中医类：辩证施膳',
  `xyzpl06` decimal(14, 4) NULL DEFAULT NULL COMMENT '血液和血液制品类： 细胞因子类制品费',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tcm_mris_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `tcm_mris_diagnose`;
CREATE TABLE `tcm_mris_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_tcm_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `disease_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型代码（ZDLX）',
  `disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型名称',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD版本',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病ICD编码',
  `disease_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '疾病名称',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病案诊断信息表(中医病案首页西医诊断)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tcm_mris_oper_info
-- ----------------------------
DROP TABLE IF EXISTS `tcm_mris_oper_info`;
CREATE TABLE `tcm_mris_oper_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `oper_disease_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术疾病ID（base_disease）',
  `oper_disease_icd9` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术疾病编码ICD-9（base_disease）',
  `oper_disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术疾病名称（base_disease）',
  `notched_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '切口代码（QK）',
  `notched_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '切口名称',
  `heal_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '愈合代码（YH）',
  `heal_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '愈合名称',
  `ana_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '麻醉方式代码（MZFS）',
  `ana_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '麻醉方式名称',
  `oper_doctor_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术医生ID',
  `oper_doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术医生姓名',
  `oper_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术日期',
  `assistant_id4` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术一助ID',
  `assistant_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术一助姓名',
  `assistant_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术二助ID',
  `assistant_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术二助姓名',
  `assistant_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术三助ID',
  `assistant_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术三助姓名',
  `ana_id1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第一麻醉医生ID',
  `ana_name1` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第一麻醉医生姓名',
  `ana_id2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第二麻醉医生ID',
  `ana_name2` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第二麻醉医生姓名',
  `ana_id3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第三麻醉医生ID',
  `ana_name3` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '第三麻醉医生姓名',
  `oper_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术级别代码（SSJB）',
  `oper_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术级别名称',
  `oper_position` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术部位代码（SSBW）',
  `oper_position_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手术部位名称',
  `columns_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '行数',
  `other_oper_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '择期手术时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tcm_mris_syndromes_diagnose
-- ----------------------------
DROP TABLE IF EXISTS `tcm_mris_syndromes_diagnose`;
CREATE TABLE `tcm_mris_syndromes_diagnose`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_tcm_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `disease_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医诊断类型代码（ZDLX）',
  `disease_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '诊断类型名称',
  `icd_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医疾病ICD版本',
  `disease_icd10` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医疾病ICD编码',
  `disease_icd10_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医疾病名称',
  `in_situation_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入院病情',
  `tcm_syndromes_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医症候Id',
  `tcm_syndromes_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中医症候名称'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '病案诊断信息表(中医病案首页中医诊断)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tcm_mris_turn_dept
-- ----------------------------
DROP TABLE IF EXISTS `tcm_mris_turn_dept`;
CREATE TABLE `tcm_mris_turn_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '医院编码',
  `mbi_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '病案ID（mris_base_info.id）',
  `visit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '就诊ID',
  `out_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转出科室ID',
  `out_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转出科室名称',
  `in_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转入科室ID',
  `in_dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转入科室名称',
  `in_dept_time` datetime(0) NULL DEFAULT NULL COMMENT '转科时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for upload_type
-- ----------------------------
DROP TABLE IF EXISTS `upload_type`;
CREATE TABLE `upload_type`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `hosp_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编码',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传数据的类型编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传数据的类型名称',
  `sql_str` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '获取数据sql脚本',
  `valid_flage` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志（0有效,1无效）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for version_info
-- ----------------------------
DROP TABLE IF EXISTS `version_info`;
CREATE TABLE `version_info`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `version_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本发布号',
  `create_time` date NULL DEFAULT NULL COMMENT '版本发布时间',
  `version_update` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本更新信息',
  `version_service` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本服务信息',
  `version_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本文件地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_blob_triggers`;
CREATE TABLE `xxl_job_qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_calendars`;
CREATE TABLE `xxl_job_qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_cron_triggers`;
CREATE TABLE `xxl_job_qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_fired_triggers`;
CREATE TABLE `xxl_job_qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_job_details`;
CREATE TABLE `xxl_job_qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_locks`;
CREATE TABLE `xxl_job_qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_paused_trigger_grps`;
CREATE TABLE `xxl_job_qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_scheduler_state`;
CREATE TABLE `xxl_job_qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_simple_triggers`;
CREATE TABLE `xxl_job_qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_simprop_triggers`;
CREATE TABLE `xxl_job_qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_group`;
CREATE TABLE `xxl_job_qrtz_trigger_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行器名称',
  `order` tinyint(4) NOT NULL DEFAULT 0 COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_info`;
CREATE TABLE `xxl_job_qrtz_trigger_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime(0) NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_log`;
CREATE TABLE `xxl_job_qrtz_trigger_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '调度-日志',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '执行-日志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40176 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_logglue`;
CREATE TABLE `xxl_job_qrtz_trigger_logglue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_registry`;
CREATE TABLE `xxl_job_qrtz_trigger_registry`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_triggers`;
CREATE TABLE `xxl_job_qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `xxl_job_qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for dbf_n02
-- ----------------------------
DROP VIEW IF EXISTS `dbf_n02`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `dbf_n02` AS select `sys`.`name` AS `USERNAME`,`sys`.`name` AS `XM`,(case `sys`.`cert_code` when '01' then '1' when '02' then '1' when '03' then '4' when '04' then '2' when '06' then '3' when '07' then '3' else '1' end) AS `SFZJZL`,`sys`.`cert_no` AS `SFZJHM`,date_format(`sys`.`birthday`,'%Y%m%d') AS `CSRQ`,`sys`.`gender_code` AS `XBDM`,`sys`.`nation_code` AS `MZDM`,date_format(`sys`.`work_date`,'%Y%m%d') AS `CJGZRQ`,`sys`.`phone` AS `SJHM`,(case `sys`.`education_code` when '1' then '7' when '2' then '7' when '3' then '7' when '4' then '6' when '5' then '3' when '7' then '2' when '8' then '1' end) AS `XLDM`,`sys`.`dept_code` AS `SZKSDM`,`dept`.`name` AS `KSSJMC`,`sys`.`major_code` AS `YJXKDM`,'' AS `SFHDGJZS`,'' AS `SFZCQKYX`,'' AS `SFCSTJGZ` from (`sys_user` `sys` left join `base_dept` `dept` on(((`sys`.`hosp_code` = `dept`.`hosp_code`) and (`sys`.`dept_code` = `dept`.`code`))));

-- ----------------------------
-- View structure for dbf_n042
-- ----------------------------
DROP VIEW IF EXISTS `dbf_n042`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `dbf_n042` AS select `base`.`in_profile` AS `USERNAME`,`base`.`pay_way_code` AS `YLFKFS`,`base`.`health_card` AS `JKKH`,`base`.`in_cnt` AS `ZYCS`,`base`.`in_profile` AS `BAH`,`base`.`name` AS `XM`,`base`.`age` AS `NL`,`base`.`occupation_code` AS `ZY`,date_format(`base`.`in_time`,'%Y%m%d') AS `RYSJ`,date_format(`base`.`out_time`,'%Y%m%d') AS `CYSJ`,`base`.`disease_icd10_name` AS `ZYZD`,`base`.`icd_version` AS `JBDM` from (`mris_base_info` `base` left join `mris_cost` `cost` on(((`base`.`hosp_code` = `cost`.`hosp_code`) and (`base`.`visit_id` = `cost`.`visit_id`))));

-- ----------------------------
-- View structure for dbf_n043
-- ----------------------------
DROP VIEW IF EXISTS `dbf_n043`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `dbf_n043` AS select `base`.`in_profile` AS `USERNAME`,`base`.`pay_way_code` AS `YLFKFS`,`base`.`health_card` AS `JKKH`,`base`.`in_cnt` AS `ZYCS`,`base`.`in_profile` AS `BAH`,`base`.`name` AS `XM`,`base`.`age` AS `NL`,`base`.`occupation_code` AS `ZY`,date_format(`base`.`in_time`,'%Y%m%d') AS `RYSJ`,date_format(`base`.`out_time`,'%Y%m%d') AS `CYSJ`,`base`.`disease_icd10_name` AS `ZYZD`,`base`.`icd_version` AS `JBDM`,'' AS `JBBM`,'' AS `GBJBBM`,`base`.`disease_icd10_name` AS `CYSZYZD` from (`mris_base_info` `base` left join `mris_cost` `cost` on(((`base`.`hosp_code` = `cost`.`hosp_code`) and (`base`.`visit_id` = `cost`.`visit_id`))));

-- ----------------------------
-- View structure for his_baby_info
-- ----------------------------
DROP VIEW IF EXISTS `his_baby_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_baby_info` AS select `a`.`visit_id` AS `visit_id`,`a`.`baby_id` AS `baby_id`,`a`.`name` AS `baby_name` from `mris_obstetrics` `a`;

-- ----------------------------
-- View structure for his_base_area_info
-- ----------------------------
DROP VIEW IF EXISTS `his_base_area_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_base_area_info` AS select `base_dept`.`id` AS `AREA_ID`,`base_dept`.`name` AS `AREA_NAME` from `base_dept` where (`base_dept`.`type_code` = '15');

-- ----------------------------
-- View structure for his_base_bed_info
-- ----------------------------
DROP VIEW IF EXISTS `his_base_bed_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_base_bed_info` AS select (select `base_dept`.`id` from `base_dept` where (`base_dept`.`code` = (select `base_dept`.`ward_code` from `base_dept` where (`base_dept`.`code` = `base_bed`.`dept_code`)))) AS `AREA_ID`,`base_bed`.`id` AS `BED_ID`,`base_bed`.`name` AS `BED_NAME`,(select `base_dept`.`id` from `base_dept` where (`base_dept`.`code` = `base_bed`.`dept_code`)) AS `DEPT_ID`,(case `base_bed`.`status_code` when '1' then '0' else '1' end) AS `BED_TYPE`,(case `base_bed`.`is_valid` when '0' then '1' else '0' end) AS `KYBZ` from `base_bed` where (`base_bed`.`status_code` in ('1','2'));

-- ----------------------------
-- View structure for his_base_dept_info
-- ----------------------------
DROP VIEW IF EXISTS `his_base_dept_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_base_dept_info` AS select `a`.`code` AS `dept_code`,`a`.`name` AS `dept_name`,`a`.`is_valid` AS `flag`,'' AS `remark`,`a`.`id` AS `sort_no`,(case when (`a`.`name` like '%急诊%') then 'y' else 'n' end) AS `is_emgc_dept`,(case `a`.`type_code` when '1' then 'y' else 'n' end) AS `is_outpatient_dept`,(case `a`.`type_code` when '2' then 'y' else 'n' end) AS `is_clinic_dept`,(case when (`a`.`name` like '%外%') then '2' else '1' end) AS `dept_flag` from `base_dept` `a` where (`a`.`is_valid` = '1');

-- ----------------------------
-- View structure for his_base_emp_info
-- ----------------------------
DROP VIEW IF EXISTS `his_base_emp_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_base_emp_info` AS select `sys_user`.`id` AS `EMP_ID`,`sys_user`.`code` AS `EMP_NO`,`sys_user`.`password` AS `PASSWD`,`sys_user`.`gender_code` AS `EMP_SEX_ID`,(select `base_dept`.`id` from `base_dept` where (`base_dept`.`code` = `sys_user`.`dept_code`)) AS `EMP_DEPT_ID`,(select `base_dept`.`name` from `base_dept` where (`base_dept`.`code` = `sys_user`.`dept_code`)) AS `EMP_DEPT`,`sys_user`.`work_type_code` AS `CURT_DUTY`,NULL AS `SUPP_INFO` from `sys_user`;

-- ----------------------------
-- View structure for his_base_items_info
-- ----------------------------
DROP VIEW IF EXISTS `his_base_items_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_base_items_info` AS select `base_item`.`id` AS `ITEMS_ID`,`base_item`.`code` AS `ITEMS_CODE`,`base_item`.`name` AS `ITEMS_NAME`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'DW') and (`sys_code_detail`.`value` = `base_item`.`unit_code`))) AS `UTIL`,`base_item`.`spec` AS `STAND`,`base_item`.`name_pym` AS `PINYIN_CODE`,`base_item`.`name_wbm` AS `WUBI_CODE`,`base_item`.`price` AS `ITEMS_UNVLN`,`base_item`.`remark` AS `SUPP_INFO`,'XM' AS `ITEMS_TYPE` from `base_item` union all select `base_material`.`id` AS `ITEMS_ID`,`base_material`.`code` AS `ITEMS_CODE`,`base_material`.`name` AS `ITEMS_NAME`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'DW') and (`sys_code_detail`.`value` = `base_material`.`unit_code`))) AS `UTIL`,`base_material`.`spec` AS `STAND`,`base_material`.`pym` AS `PINYIN_CODE`,`base_material`.`wbm` AS `WUBI_CODE`,`base_material`.`price` AS `ITEMS_UNVLN`,`base_material`.`remark` AS `SUPP_INFO`,'CL' AS `ITEMS_TYPE` from `base_material`;

-- ----------------------------
-- View structure for his_base_mdcl_items_rel
-- ----------------------------
DROP VIEW IF EXISTS `his_base_mdcl_items_rel`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_base_mdcl_items_rel` AS select `a`.`code` AS `MDCL_ADVICE_CODE`,`d`.`item_code` AS `ITEMS_ID` from (`base_advice` `a` join `base_advice_detail` `d`) where ((`d`.`advice_code` = `a`.`code`) and (`a`.`is_valid` = '1'));

-- ----------------------------
-- View structure for his_drug_list
-- ----------------------------
DROP VIEW IF EXISTS `his_drug_list`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_drug_list` AS select `base_drug`.`id` AS `DRUG_LIST_ID`,`base_drug`.`code` AS `LIST_CODE`,`base_drug`.`usual_name` AS `NAME`,`base_drug`.`insure_code` AS `DRUG_CODE`,(select `t`.`name` from `sys_code_detail` `t` where ((`t`.`c_code` = 'JXFL') and (`t`.`value` = `base_drug`.`prep_code`))) AS `DOS_FORMS`,NULL AS `OTC_FLAG`,(case `base_drug`.`is_skin` when '0' then 'N' else 'Y' end) AS `ST_FLAG`,(case `base_drug`.`big_type_code` when '0' then '1' when '1' then '2' when '2' then '3' else '4' end) AS `DRUG_TYPE`,(select `t`.`name` from `sys_code_detail` `t` where ((`t`.`c_code` = 'DMTX') and (`t`.`value` = `base_drug`.`ph_code`))) AS `POISON_HEMP_FEAT`,(select `t`.`name` from `sys_code_detail` `t` where ((`t`.`c_code` = 'JLDW') and (`t`.`value` = `base_drug`.`dosage_unit_code`))) AS `DOS_UTIL`,(case ifnull(`base_drug`.`antibacterial_code`,'N') when 'N' then 'N' else 'Y' end) AS `ATBCTRL_FLAG`,`base_drug`.`antibacterial_code` AS `ATBCTRL_LEVEL`,`base_drug`.`ddd` AS `DDD`,(case `base_drug`.`is_valid` when '0' then 'N' else 'Y' end) AS `FLAG`,`base_drug`.`usual_pym` AS `PINYIN_CODE`,`base_drug`.`usual_wbm` AS `WUBI_CODE`,`base_drug`.`spec` AS `STAND`,(select `t`.`name` from `sys_code_detail` `t` where ((`t`.`c_code` = 'DW') and (`t`.`value` = `base_drug`.`unit_code`))) AS `UTIL`,`base_drug`.`price` AS `RETAIL_PRICE`,`base_drug`.`split_price` AS `SPLIT_RETAIL_PRICE`,(select `t`.`name` from `sys_code_detail` `t` where ((`t`.`c_code` = 'DW') and (`t`.`value` = `base_drug`.`split_unit_code`))) AS `SPLIT_UTIL`,`base_drug`.`prod_code` AS `MNFCTR`,NULL AS `SUPP_INFO` from `base_drug` order by `base_drug`.`id`;

-- ----------------------------
-- View structure for his_hsptzd_charge
-- ----------------------------
DROP VIEW IF EXISTS `his_hsptzd_charge`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_hsptzd_charge` AS select `inpt_cost`.`id` AS `CHARGE_DETAIL_ID`,`inpt_cost`.`visit_id` AS `VISIT_ID`,NULL AS `BILLING_CATEGORY_ID`,NULL AS `BILLING_CATEGORY`,`inpt_cost`.`bfc_id` AS `CATEGORY_ID`,(select `base_finance_classify`.`name` from `base_finance_classify` where (`base_finance_classify`.`id` = `inpt_cost`.`bfc_id`)) AS `CATEGORY_NAME`,(case `inpt_cost`.`item_code` when '1' then '1' when '2' then '5' else '2' end) AS `ITEMS_CATEGORY`,`inpt_cost`.`item_id` AS `ITEMS_ID`,NULL AS `ITEMS_CODE`,`inpt_cost`.`item_name` AS `ITEMS_NAME`,`inpt_cost`.`spec` AS `STAND`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'DW') and (`sys_code_detail`.`value` = `inpt_cost`.`total_num_unit_code`))) AS `UNIT`,`inpt_cost`.`price` AS `UNVLN`,`inpt_cost`.`total_num` AS `NUM`,`inpt_cost`.`total_price` AS `BILLING_MONEY`,`inpt_cost`.`reality_price` AS `DISCOUNT_MONEY`,`inpt_cost`.`exec_time` AS `OCCUR_TIME`,`inpt_cost`.`cost_time` AS `PRODUCE_TIME`,`inpt_cost`.`doctor_id` AS `PRESCR_DOCTOR_ID`,`inpt_cost`.`doctor_name` AS `PRESCR_DOCTOR`,(select `base_dept`.`code` from `base_dept` where (`base_dept`.`id` = `inpt_cost`.`dept_id`)) AS `PRESCR_DEPT_ID`,(select `base_dept`.`name` from `base_dept` where (`base_dept`.`id` = `inpt_cost`.`dept_id`)) AS `PRESCR_DEPT`,(select `base_dept`.`code` from `base_dept` where (`base_dept`.`id` = `inpt_cost`.`exec_dept_id`)) AS `EXEC_DEPT_ID`,(select `base_dept`.`name` from `base_dept` where (`base_dept`.`id` = `inpt_cost`.`exec_dept_id`)) AS `EXEC_DEPT`,(select `base_dept`.`code` from `base_dept` where (`base_dept`.`id` = `inpt_cost`.`in_dept_id`)) AS `VISIT_DEPT_ID`,(select `base_dept`.`name` from `base_dept` where (`base_dept`.`id` = `inpt_cost`.`in_dept_id`)) AS `VISIT_DEPT`,`inpt_cost`.`iat_id` AS `MDCL_ADVICE_ID`,`inpt_cost`.`status_code` AS `FLAG` from `inpt_cost` order by `inpt_cost`.`id`;

-- ----------------------------
-- View structure for his_hsptzd_mdcl_advice
-- ----------------------------
DROP VIEW IF EXISTS `his_hsptzd_mdcl_advice`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_hsptzd_mdcl_advice` AS select `inpt_advice`.`id` AS `MDCL_ADVICE_ID`,`inpt_advice`.`visit_id` AS `VISIT_ID`,(case `inpt_advice`.`is_long` when '1' then '10' else '00' end) AS `MDCL_ADVICE_TYPE`,(case `inpt_advice`.`item_code` when '4' then (select `base_advice`.`code` from `base_advice` where (`base_advice`.`id` = `inpt_advice`.`item_id`)) else `inpt_advice`.`item_id` end) AS `MDCL_ADVICE_CODE`,(case `inpt_advice`.`item_code` when '1' then '1' when '2' then '2' when '3' then '2' when '4' then '3' end) AS `MDCL_ADVICE_SOURCE`,'N' AS `CH_HERB_FLAG`,`inpt_advice`.`group_no` AS `MDCL_ADVICE_NO`,`inpt_advice`.`item_id` AS `ITEMS_ID`,`inpt_advice`.`item_name` AS `ITEMS_NAME`,`inpt_advice`.`content` AS `MDCL_ADVICE_CONTENT`,NULL AS `MDCL_ADVICE_DISP`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'YF') and (`sys_code_detail`.`value` = `inpt_advice`.`usage_code`))) AS `PHARMACY_WAY`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'JXFL') and (`sys_code_detail`.`value` = `inpt_advice`.`prep_code`))) AS `DOS_FORMS`,`inpt_advice`.`spec` AS `STAND`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'SD') and (`sys_code_detail`.`value` = `inpt_advice`.`speed_code`))) AS `SPEED`,(select `base_rate`.`name` from `base_rate` where (`base_rate`.`id` = `inpt_advice`.`rate_id`)) AS `HZ`,(select `base_rate`.`remark` from `base_rate` where (`base_rate`.`id` = `inpt_advice`.`rate_id`)) AS `HZ_DESCR`,`inpt_advice`.`dosage` AS `DOSAGE`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'JLDW') and (`sys_code_detail`.`value` = `inpt_advice`.`dosage_unit_code`))) AS `DOSAGE_UNIT`,`inpt_advice`.`crte_time` AS `ENTIY_TIME`,`inpt_advice`.`long_start_time` AS `MDCL_ADVICE_START_TIME`,(select `base_dept`.`code` from `base_dept` where (`base_dept`.`id` = `inpt_advice`.`dept_id`)) AS `PRECR_ADVICE_DEPT_ID`,(select `base_dept`.`name` from `base_dept` where (`base_dept`.`id` = `inpt_advice`.`dept_id`)) AS `PRECR_ADVICE_DEPT`,`inpt_advice`.`crte_id` AS `PRECR_ADVICE_DOCTOR_ID`,`inpt_advice`.`crte_name` AS `PRECR_ADVICE_DOCTOR`,`inpt_advice`.`is_check` AS `CHECK_FLAG`,`inpt_advice`.`check_id` AS `CHECK_DOCTOR_ID`,`inpt_advice`.`check_name` AS `CHECK_DOCTOR`,`inpt_advice`.`check_time` AS `PRECR_ADVICE_CHECK_TIME`,`inpt_advice`.`sign_code` AS `EXEC_STATUS`,`inpt_advice`.`stop_time` AS `CEASE_ADVICE_TIME`,`inpt_advice`.`stop_doctor_id` AS `CEASE_ADVICE_DOCTOR_ID`,`inpt_advice`.`stop_doctor_name` AS `CEASE_ADVICE_DOCTOR`,`inpt_advice`.`stop_check_id` AS `CEASE_ADVICE_CHECK_DOCTOR_ID`,`inpt_advice`.`stop_check_name` AS `CEASE_ADVICE_CHECK_DOCTOR`,`inpt_advice`.`stop_check_time` AS `CEASE_ADVICE_CHECK_TIME`,'1' AS `MDCL_ADVICE_STATUS`,(case ifnull(`inpt_advice`.`is_skin`,'2') when '2' then '0' when '1' then '2' else '0' end) AS `ST_FLAG`,NULL AS `SUPP_INFO` from `inpt_advice`;

-- ----------------------------
-- View structure for his_hsptzd_patient_base
-- ----------------------------
DROP VIEW IF EXISTS `his_hsptzd_patient_base`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_hsptzd_patient_base` AS select `a`.`id` AS `visit_id`,`a`.`in_no` AS `adm_no`,`a`.`in_profile` AS `patient_no`,`a`.`name` AS `name`,`a`.`gender_code` AS `sex_id`,`a`.`age` AS `age`,`a`.`birthday` AS `birthday`,`a`.`cert_no` AS `id_card`,`a`.`phone` AS `tel`,(case `a`.`status_code` when '7' then '11' else `a`.`status_code` end) AS `status`,`a`.`in_ward_id` AS `area_id`,(select `t`.`name` from `base_dept` `t` where (`t`.`id` = `a`.`in_ward_id`)) AS `area_name`,`a`.`in_dept_id` AS `visit_dept_id`,`a`.`in_dept_name` AS `visit_dept`,`a`.`out_remark` AS `remark`,`a`.`in_time` AS `hsptzd_time`,`a`.`jz_doctor_id` AS `tb_doctor_id`,`a`.`jz_doctor_name` AS `tb_doctor`,`a`.`out_dept_id` AS `hosp_disch_dept_id`,`a`.`out_dept_name` AS `hosp_disch_dept`,`a`.`out_time` AS `hosp_disch_time`,(case `a`.`out_situation_code` when '4' then 'y' else 'n' end) AS `is_die`,`a`.`insure_code` AS `insur_area_id`,`b`.`aae140` AS `insur_type`,`b`.`bka006` AS `ttmnt_type_code`,(select `t`.`total_out` from `base_profile_file` `t` where (`t`.`id` = `a`.`profile_id`)) AS `hsptzd_count`,`b`.`akc193` AS `dis_code`,`b`.`akc193_name` AS `dis_name` from (`inpt_visit` `a` join `insure_individual_basic` `b`) where ((`a`.`insure_patient_id` = `b`.`id`) and (`a`.`status_code` <> '8'));

-- ----------------------------
-- View structure for his_insur_patient_info
-- ----------------------------
DROP VIEW IF EXISTS `his_insur_patient_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_insur_patient_info` AS select `bu`.`visit_id` AS `VISIT_ID`,`v`.`insure_org_code` AS `HOSP_ID`,`b`.`aae140` AS `INSUR_ID`,NULL AS `INSUR_NO`,'N' AS `IS_OPRT`,NULL AS `OPRT_TYPE`,`bu`.`aka121` AS `VISIT_ILL_NAME`,`bu`.`aka120` AS `VISIT_ILL_CODE`,`bu`.`bka006` AS `TTMNT_TYPE_CODE`,`bu`.`aka120` AS `OUT_ILL_CODE`,`bu`.`aka121` AS `OUT_ILL_NAME` from ((`insure_individual_basic` `b` join `insure_individual_business` `bu`) join `inpt_visit` `v`) where ((`b`.`id` = `bu`.`mib_id`) and (`bu`.`visit_id` = `v`.`id`));

-- ----------------------------
-- View structure for his_insur_settl_data
-- ----------------------------
DROP VIEW IF EXISTS `his_insur_settl_data`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_insur_settl_data` AS select `insure_individual_settle`.`id` AS `SETTL_ID`,`insure_individual_settle`.`visit_id` AS `VISIT_ID`,`insure_individual_settle`.`insure_org_code` AS `HOSP_ID`,`insure_individual_settle`.`bka006` AS `INSUR_ID`,`insure_individual_settle`.`aka130` AS `BIZ_TYPE_DESCR`,`insure_individual_settle`.`visit_no` AS `INDI_ID`,NULL AS `PERS_CATEGORY_DESCR`,`insure_individual_settle`.`discharge_dn_code` AS `INSUR_DAGNS_ID`,`insure_individual_settle`.`discharge_dn_name` AS `INSUR_DAGNS_NAME`,`insure_individual_settle`.`total_price` AS `CHARGE_AMOUNT`,`insure_individual_settle`.`insure_price` AS `FUND_PAY_AMOUNT`,`insure_individual_settle`.`person_price` AS `IDVD_PAY_AMOUNT`,`insure_individual_settle`.`personal_price` AS `IDVD_ACCOUNT_AMOUNT`,`insure_individual_settle`.`hosp_price` AS `LUMPSUM_AMOUNT`,`insure_individual_settle`.`settle_state` AS `SETTL_STATUS`,`insure_individual_settle`.`starting_price` AS `DED`,`insure_individual_settle`.`plan_price` AS `CDNTN_FUND_PAY`,`insure_individual_settle`.`serious_price` AS `ILL_FUND_PAY`,`insure_individual_settle`.`civil_price` AS `CIVIL_SVNT_FUND_PAY`,`insure_individual_settle`.`rests_price` AS `OTHER_FUND_PAY`,`insure_individual_settle`.`bka006` AS `TTMNT_TYPE_CODE`,'0' AS `STOP_ACCOUNT_COUNT`,`insure_individual_settle`.`crte_time` AS `SETTL_TIME` from `insure_individual_settle` order by `insure_individual_settle`.`id`;

-- ----------------------------
-- View structure for his_mdcl_advice_list
-- ----------------------------
DROP VIEW IF EXISTS `his_mdcl_advice_list`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_mdcl_advice_list` AS select `base_advice`.`id` AS `MDCL_ADVICE_ID`,`base_advice`.`name` AS `NAME`,`base_advice`.`wbm` AS `WUBI_CODE`,`base_advice`.`pym` AS `PINYIN_CODE`,NULL AS `SUPP_INFO` from `base_advice` where (`base_advice`.`is_valid` = '1');

-- ----------------------------
-- View structure for his_mr_outpatinet_chronic_dagns
-- ----------------------------
DROP VIEW IF EXISTS `his_mr_outpatinet_chronic_dagns`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_mr_outpatinet_chronic_dagns` AS select `inpt_visit`.`id` AS `visit_id`,`inpt_visit`.`in_profile` AS `patient_no`,`inpt_visit`.`in_dept_id` AS `visit_dept_id`,`inpt_visit`.`in_time` AS `visit_date`,`inpt_visit`.`in_disease_id` AS `dagns_id`,`inpt_visit`.`in_disease_name` AS `dagns_name`,1 AS `orderno` from `inpt_visit` order by `inpt_visit`.`in_profile`;

-- ----------------------------
-- View structure for his_mr_patient_critical_info
-- ----------------------------
DROP VIEW IF EXISTS `his_mr_patient_critical_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_mr_patient_critical_info` AS select `mris_base_info`.`visit_id` AS `visit_id`,`mris_base_info`.`in_profile` AS `patient_no`,NULL AS `critical_id`,NULL AS `critical_type`,NULL AS `critical_enter_date`,NULL AS `critical_out_date`,`mris_base_info`.`monitor_hour` AS `total_hour` from `mris_base_info`;

-- ----------------------------
-- View structure for his_mr_patient_settl_dagns_detail
-- ----------------------------
DROP VIEW IF EXISTS `his_mr_patient_settl_dagns_detail`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_mr_patient_settl_dagns_detail` AS select `d`.`id` AS `orderno`,`d`.`disease_name` AS `dagns_type`,`d`.`disease_icd10_name` AS `disease_name`,`d`.`disease_icd10` AS `icd_code`,`d`.`in_situation_code1` AS `dagns_condi_id`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'RYBQ') and (`sys_code_detail`.`value` = `d`.`in_situation_code1`))) AS `dagns_condi`,`d`.`visit_id` AS `visit_id`,`v`.`in_profile` AS `patient_no`,NULL AS `dagns_category` from (`mris_diagnose` `d` join `inpt_visit` `v`) where (`d`.`visit_id` = `v`.`id`);

-- ----------------------------
-- View structure for his_mr_patient_settl_list
-- ----------------------------
DROP VIEW IF EXISTS `his_mr_patient_settl_list`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_mr_patient_settl_list` AS select `i`.`hosp_code` AS `hospital_code`,`i`.`hosp_name` AS `hospital_name`,`i`.`in_no` AS `serial_no`,NULL AS `insur_settl_id`,NULL AS `insur_impl_way`,NULL AS `pay_category `,NULL AS `insur_patient_no `,NULL AS `hosp_id`,NULL AS `branch_hosp_id`,NULL AS `social_curity_card`,`i`.`cert_name` AS `card_type`,`i`.`cert_no` AS `card_number`,`i`.`cert_code` AS `card_type_id`,`i`.`in_profile` AS `patient_no`,`i`.`visit_id` AS `visit_id`,`i`.`in_no` AS `adm_no`,`i`.`age` AS `age`,`i`.`age_unit_name` AS `Nwb_age`,`i`.`name` AS `name`,`i`.`gender_code` AS `sex_id`,`i`.`gender_name` AS `sex`,`i`.`birthday` AS `birthday`,`i`.`nationality_cation` AS `country_id`,`i`.`nationality_name` AS `country`,`i`.`nation_code` AS `ation_id`,`i`.`nation_name` AS `nation`,`i`.`occupation_code` AS `job_id`,`i`.`occupation_name` AS `job`,`i`.`now_adress` AS `curr_address`,`i`.`now_prov_name` AS `addr_province`,`i`.`now_city_name` AS `addr_city`,`i`.`now_area_name` AS `addr_county`,`i`.`now_adress` AS `addr_street`,`i`.`work` AS `company_name`,`i`.`work_address` AS `company_address`,`i`.`work_phone` AS `company_tel`,`i`.`work_post_code` AS `ompany_post`,`i`.`contact_name` AS `cont`,`i`.`contact_rela_code` AS `cont_rel_id`,`i`.`contact_rela_name` AS `cont_rel_patient`,`i`.`contact_address` AS `cont_address`,NULL AS `cont_addr_prov`,NULL AS `cont_addr_city`,NULL AS `cont_addr_county`,`i`.`contact_address` AS `cont_addr_street`,`i`.`contact_phone` AS `cont_tel`,NULL AS `insur_type`,NULL AS `special_pesn_type_id`,NULL AS `special_pesn_type`,NULL AS `newborn_adm_way`,`i`.`baby_birth_weight` AS `newborn_weight`,`i`.`baby_in_weight` AS `newborn_hsptzd_weight`,NULL AS `outpatient_chronic_dept`,NULL AS `outpatient_chronic_dept_id`,NULL AS `outpatient_chronic_visit_date`,'1' AS `hsptzd_medc_type_id`,'住院' AS `hsptzd_medc_type`,`i`.`in_way` AS `hsptzd_ways_id`,`i`.`in_way` AS `hsptzd_ways`,NULL AS `treatype_tradi_id`,NULL AS `treatype_tradi`,`i`.`in_time` AS `hsptzd_date`,`i`.`in_dept_name` AS `hsptzd_dept`,`i`.`in_dept_id` AS `hsptzd_dept_id`,`i`.`out_time` AS `hosp_disch_date`,`i`.`out_dept_name` AS `disch_dept`,`i`.`out_dept_id` AS `disch_dept_id`,`i`.`in_days` AS `actual_adm_days`,NULL AS `first_transf_dept_id`,NULL AS `first_transf_dept`,`i`.`disease_icd10` AS `outemgc_dagns_id`,`i`.`disease_icd10_name` AS `outemgc_dagns`,NULL AS `outemgc_chinese_dagns_id`,NULL AS `outemgc_chinese_dagns`,NULL AS `ventilator_usage_days`,NULL AS `ventilator_usage_hours`,NULL AS `ventilator_usage_mins`,`i`.`inpt_before_day` AS `bef_coma_days`,`i`.`inpt_before_hour` AS `bef_coma_hours`,`i`.`inpt_before_minute` AS `bef_coma_mins`,`i`.`inpt_last_day` AS `aft_coma_days`,`i`.`inpt_last_hour` AS `aft_coma_hours`,`i`.`inpt_last_minute` AS `aft_coma_mins`,NULL AS `blood_type_id`,NULL AS `blood_type`,NULL AS `blood_amt`,NULL AS `blood_unit`,NULL AS `super_nurscare_days`,NULL AS `lv1_nurscare_days`,NULL AS `lv2_nurscare_days`,NULL AS `lv3_nurscare_days`,`i`.`out_mode_name` AS `out_hosp`,`i`.`out_mode_code` AS `out_hosp_id`,`i`.`turn_org_name` AS `mdcl_advice_out_code`,`i`.`turn_org_name` AS `mdcl_advice_out`,`i`.`is_inpt` AS `isagain_hsptzd_id`,`i`.`is_inpt` AS `isagain_hsptzd`,`i`.`aim` AS `again_aim`,`i`.`zz_doctor_name` AS `att_doctor`,`i`.`zz_doctor_id` AS `att_doctor_id`,NULL AS `biz_sn`,NULL AS `settl_begin_date`,NULL AS `settl_end_date`,`i`.`zk_doctor_id` AS `quality_doctor_id`,`i`.`zk_doctor_name` AS `quality_doctor`,`i`.`zk_nurse_id` AS `quality_nurs_id`,`i`.`zk_nurse_name` AS `quality_nurs`,`i`.`zk_time` AS `quality_date`,`c`.`fy01` AS `total_fee`,NULL AS `bed_fee`,NULL AS `diagn_fee`,`c`.`zdl03` AS `inspt_fee`,`c`.`zdl02` AS `exam_fee`,((`c`.`zll01` + `c`.`zll02`) + `c`.`zll03`) AS `treat_fee`,(`c`.`zll04` + `c`.`zll05`) AS `oprt_fee`,`c`.`zhylfwl03` AS `nursing_fee`,((`c`.`hcl01` + `c`.`hcl02`) + `c`.`hcl03`) AS `health_material_fee`,`c`.`fy02` AS `west_drug_fee`,NULL AS `ch_drug_fee`,`c`.`fy04` AS `ch_patent_fee`,NULL AS `med_treat_fee`,NULL AS `regist_fee`,`c`.`zhylfwl04` AS `other_fee`,NULL AS `insur_pay_type`,NULL AS `insur_pay_type_id`,NULL AS `oprtor_name`,NULL AS `oprtor_dept`,NULL AS `cdntn_pay`,NULL AS `ill_pay`,NULL AS `civil_mdc_servant`,NULL AS `large_supp`,NULL AS `enterprise_supp`,NULL AS `self_pay`,NULL AS `own_fee`,NULL AS `persn_cash_pay`,NULL AS `persn_account_pay`,NULL AS `insur_settl_level`,NULL AS `declare_date`,NULL AS `other_pay`,NULL AS `dise_code_cnt`,NULL AS `oprn_oprt_code_cnt`,NULL AS `mdical_assist`,NULL AS `insur_business_type`,NULL AS `insur_nonlocal_flag`,NULL AS `encoder_id `,NULL AS `encoder` from (`mris_base_info` `i` join `mris_cost` `c`) where (`i`.`id` = `c`.`mbi_id`);

-- ----------------------------
-- View structure for his_mr_patient_settl_oprt_detail
-- ----------------------------
DROP VIEW IF EXISTS `his_mr_patient_settl_oprt_detail`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_mr_patient_settl_oprt_detail` AS select `d`.`id` AS `orderno`,`d`.`oper_disease_icd9` AS `oprt_code`,`d`.`oper_disease_name` AS `oprt_name`,`d`.`oper_time` AS `oprt_date`,`d`.`notched_code` AS `oprt_cut_id`,`d`.`notched_name` AS `oprt_cut`,`d`.`heal_code` AS `heal_id`,`d`.`heal_name` AS `heal`,`d`.`oper_doctor_id` AS `oprt_doctor_id`,`d`.`oper_doctor_name` AS `oprt_doctor`,`d`.`ana_code` AS `anthsa_mode_id`,`d`.`ana_name` AS `anthsa_mode`,'N' AS `is_add_port`,`d`.`assistant_id4` AS `first_ass_id`,`d`.`assistant_name1` AS `first_ass`,`d`.`assistant_id2` AS `second_ass_id`,`d`.`assistant_name2` AS `second_ass`,`d`.`ana_id1` AS `anthsa_doctor_id`,`d`.`ana_name1` AS `anthsa_doctor`,NULL AS `plan_oprt_id`,NULL AS `plan_oprt`,`d`.`oper_code` AS `oprt_level_id`,`d`.`oper_name` AS `oprt_level`,`d`.`visit_id` AS `visit_id`,`v`.`in_profile` AS `patient_no` from (`mris_oper_info` `d` join `inpt_visit` `v`) where (`d`.`visit_id` = `v`.`id`);

-- ----------------------------
-- View structure for his_patient_dagns
-- ----------------------------
DROP VIEW IF EXISTS `his_patient_dagns`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_patient_dagns` AS select `a`.`visit_id` AS `visit_id`,if((`a`.`is_main` = '1'),'1','2') AS `dagns_type_id`,0 AS `order`,if(locate('中医',((select `c`.`name` from `sys_code_detail` `c` where ((`c`.`c_code` = 'ZDLX') and (`c`.`value` = `a`.`type_code`))) > 0)),'2','1') AS `dagns_category`,`b`.`name` AS `disease_name`,`b`.`code` AS `icd_code` from (`inpt_diagnose` `a` join `base_disease` `b` on(((`a`.`hosp_code` = `b`.`hosp_code`) and (`a`.`disease_id` = `b`.`id`))));

-- ----------------------------
-- View structure for his_patient_oprt_info
-- ----------------------------
DROP VIEW IF EXISTS `his_patient_oprt_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_patient_oprt_info` AS select `oper_info_record`.`visit_id` AS `visit_id`,`oper_info_record`.`id` AS `orderno`,`oper_info_record`.`oper_disease_id` AS `oprt_code`,`oper_info_record`.`oper_disease_name` AS `oprt_name`,`oper_info_record`.`oper_plan_time` AS `oprt_date`,`oper_info_record`.`notched_code` AS `oprt_cut_id`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'QKDJ') and (`sys_code_detail`.`value` = `oper_info_record`.`notched_code`))) AS `oprt_cut`,`oper_info_record`.`heal_code` AS `heal_id`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'YHQK') and (`sys_code_detail`.`value` = `oper_info_record`.`heal_code`))) AS `heal`,`oper_info_record`.`doctor_id` AS `oprt_doctor_id`,`oper_info_record`.`doctor_name` AS `oprt_doctor`,`oper_info_record`.`ana_code` AS `anthsa_mode_id`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'MZFS') and (`sys_code_detail`.`value` = `oper_info_record`.`ana_code`))) AS `anthsa_mode`,'N' AS `is_add_port`,`oper_info_record`.`assistant_id1` AS `first_ass_id`,`oper_info_record`.`assistant_name1` AS `first_ass`,`oper_info_record`.`assistant_id2` AS `second_ass_id`,`oper_info_record`.`assistant_name2` AS `second_ass`,`oper_info_record`.`ana_id1` AS `anthsa_doctor_id`,`oper_info_record`.`ana_name1` AS `anthsa_doctor`,NULL AS `plan_oprt_id`,NULL AS `plan_oprt`,`oper_info_record`.`rank` AS `oprt_level_id`,(select `sys_code_detail`.`name` from `sys_code_detail` where ((`sys_code_detail`.`c_code` = 'SSJB') and (`sys_code_detail`.`value` = `oper_info_record`.`rank`))) AS `oprt_level` from `oper_info_record`;

-- ----------------------------
-- View structure for his_settl_data
-- ----------------------------
DROP VIEW IF EXISTS `his_settl_data`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `his_settl_data` AS select `inpt_settle`.`id` AS `SETTL_ID`,`inpt_settle`.`visit_id` AS `VISIT_ID`,`inpt_settle`.`settle_no` AS `SETTL_NO`,`inpt_settle`.`total_price` AS `CHARGE_AMOUNT`,`inpt_settle`.`reality_price` AS `DISCOUNT_MONEY`,`inpt_settle`.`actual_price` AS `DOC_FEE`,`inpt_settle`.`trunc_price` AS `ROUND_MONEY`,`inpt_settle`.`self_price` AS `IDVD_PAY`,`inpt_settle`.`settle_time` AS `SETTL_TIME`,`inpt_settle`.`status_code` AS `FLAG`,(case `inpt_settle`.`type_code` when '0' then '2' when '1' then '1' end) AS `SETTL_WAY` from `inpt_settle` where (`inpt_settle`.`type_code` in ('1','0')) order by `inpt_settle`.`id`;

-- ----------------------------
-- View structure for mi_insur_hosp_drugs_rel
-- ----------------------------
DROP VIEW IF EXISTS `mi_insur_hosp_drugs_rel`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `mi_insur_hosp_drugs_rel` AS select `a`.`insure_reg_code` AS `hosp_id`,`a`.`insure_item_code` AS `hosp_list_id`,`a`.`hosp_item_code` AS `drug_list_id`,'' AS `supp_info` from `insure_item_match` `a` where ((`a`.`is_valid` = '1') and (`a`.`is_match` = '1') and (`a`.`hosp_item_type` = '1'));

-- ----------------------------
-- View structure for mi_insur_hosp_items_rel
-- ----------------------------
DROP VIEW IF EXISTS `mi_insur_hosp_items_rel`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `mi_insur_hosp_items_rel` AS select `a`.`insure_reg_code` AS `hosp_id`,`a`.`insure_item_code` AS `hosp_list_id`,`a`.`hosp_item_code` AS `drug_list_id`,'' AS `supp_info` from `insure_item_match` `a` where ((`a`.`is_valid` = '1') and (`a`.`is_match` = '1') and (`a`.`hosp_item_type` = '3'));
-- ----------------------------
-- View structure for user_info
-- ----------------------------
DROP VIEW IF EXISTS `user_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_info` AS select `outpt_medical_record`.`chief_complaint` AS `chief_complaint`,`outpt_medical_record`.`present_illness` AS `present_illness`,`outpt_medical_record`.`past_history` AS `past_history`,`outpt_medical_record`.`oneself_history` AS `oneself_history`,`outpt_medical_record`.`family_history` AS `family_history`,`outpt_medical_record`.`allergy_history` AS `allergy_history`,`outpt_medical_record`.`vaccination_history` AS `vaccination_history`,`outpt_medical_record`.`auxiliary_inspect` AS `auxiliary_inspect`,`outpt_medical_record`.`disease_analysis` AS `disease_analysis`,`outpt_medical_record`.`handle_suggestion` AS `handle_suggestion`,`outpt_medical_record`.`temperature` AS `temperature`,`outpt_medical_record`.`min_blood_pressure` AS `min_blood_pressure`,`outpt_medical_record`.`max_blood_pressure` AS `max_blood_pressure`,`outpt_medical_record`.`breath` AS `breath`,`outpt_medical_record`.`height` AS `height`,`outpt_medical_record`.`blood_sugar` AS `blood_sugar`,`outpt_medical_record`.`pulse` AS `pulse`,`outpt_medical_record`.`weight` AS `weight`,`outpt_medical_record`.`bmi` AS `bmi`,'433130199209047510' AS `cardNo` from `outpt_medical_record` limit 1;



-- ----------------------
-- stro_in  2022-04-20 新增字段
-- ---------------------

ALTER TABLE stro_in
ADD COLUMN fkr_id varchar(32) NULL COMMENT '付款人ID' AFTER in_order_no,
ADD COLUMN fkr_name varchar(50) NULL COMMENT '付款人名称' AFTER fkr_id,
ADD COLUMN fk_status_code varchar(2) NULL  DEFAULT '0' COMMENT '付款状态（0未付款，1已付款）' AFTER fkr_name,
ADD COLUMN fk_remark varchar(1000) NULL COMMENT '付款备注' AFTER fk_status_code,
ADD COLUMN fkdid varchar(32) NULL COMMENT '付款单ID' AFTER fk_remark,
ADD COLUMN fk_time datetime(0) NULL COMMENT '付款确认时间' AFTER fkdid;

--  outpt_cost门诊费用表添加settle_code 结算状态索引
ALTER TABLE `outpt_cost` ADD INDEX `idx_outpt_cost_08` USING BTREE(`settle_code`);

-- inpt_cost住院费用添加settle_code 结算状态索引
ALTER TABLE `inpt_cost` ADD INDEX `idx_inpt_cost_08` USING BTREE(`settle_code`);


-- ----------------------
--   2022-04-28 索引新增
-- ---------------------
ALTER TABLE `phar_in_wait_receive` ADD INDEX `phar_in_wait_receive_idx_08` USING BTREE(`old_wr_id`);
ALTER TABLE `outpt_register` ADD INDEX `idx_outpt_register_05` USING BTREE(`hosp_code`, `is_cancel`, `register_time`);
ALTER TABLE `outpt_register_settle` ADD INDEX `idx_outpt_register_settle_05` USING BTREE(`hosp_code`, `status_code`);
ALTER TABLE `outpt_settle` ADD INDEX `idx_outpt_settle_06` USING BTREE(`is_settle`);
ALTER TABLE `inpt_settle` ADD INDEX `idx_inpt_settle_06` USING BTREE(`crte_time`);

    -- 表结构
-- 自费病人上传新增字段
ALTER TABLE inpt_visit ADD COLUMN `is_uplod_cost` varchar(3) DEFAULT '0' COMMENT '自费病人是否上传费用';
ALTER TABLE inpt_visit ADD COLUMN `is_uplod_dise` varchar(3) DEFAULT '0' COMMENT '自费病人是否诊断和就诊信息';
ALTER TABLE inpt_visit ADD COLUMN `cplt_flag` varchar(3) DEFAULT '0' COMMENT '自费病人上传完成标志';
ALTER TABLE outpt_visit ADD COLUMN `cplt_flag` varchar(3) DEFAULT '0' COMMENT '自费病人上传完成标志';
-- 中医病案首页诊断新增字段
ALTER TABLE tcm_mris_diagnose ADD COLUMN columns_num varchar(4) NULL COMMENT '诊断行号';
-- 就医信息是否上传标志
ALTER TABLE outpt_visit ADD is_uplod_dise varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '就医信息上传标志';
-- 住院人员特殊信息
ALTER TABLE insure_individual_basic ADD ipt_psn_sp_flag_type varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '住院特殊人员标识类型';
ALTER TABLE insure_individual_basic ADD ipt_psn_sp_flag_detl_id varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '住院特殊人员标识';
ALTER TABLE insure_individual_basic ADD ipt_psn_sp_flag varchar(32)CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '住院特殊人员标识id';
-- 医嘱国家编码增加字段
ALTER TABLE base_advice ADD union_nation_code varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '医嘱联合国家编码（医嘱明细中所有项目的国家编码拼接）';

-- 新增门诊用药性质
ALTER TABLE base_material ADD outpt_use_code varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '门诊用药性质（YYXZ）';
-- 修改报表模板表中报表模板文件名称字段长度
ALTER TABLE report_configuration MODIFY COLUMN temp_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表模板文件名称';
-- 修改医嘱联合国家编码（医嘱明细中所有项目的国家编码拼接）长度
ALTER TABLE base_advice MODIFY COLUMN union_nation_code varchar(512);
-- 表结构调整
ALTER TABLE base_advice ADD union_nation_name varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '医嘱联合国家名称（医嘱明细中所有项目的国家名称拼接）';

CREATE TABLE `settle_clock_match` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `hosp_code` varchar(32) DEFAULT NULL COMMENT '医院编码',
  `platform_type` varchar(4) DEFAULT NULL COMMENT '平台类型（1：质控平台，后续会维护到码表）',
  `platform_dictionary_name` varchar(32) DEFAULT NULL COMMENT '平台字典名称',
  `platform_dictionary_code` varchar(32) DEFAULT NULL COMMENT '平台字典编码',
  `platform_code_name` varchar(32) DEFAULT NULL COMMENT '平台代码名称',
  `platform_code_value` varchar(32) DEFAULT NULL COMMENT '平台代码值',
  `his_dictionary_name` varchar(32) DEFAULT NULL COMMENT 'his字典名称',
  `his_dictionary_code` varchar(32) DEFAULT NULL COMMENT 'his字典编码',
  `his_code_name` varchar(32) DEFAULT NULL COMMENT 'his代码名称',
  `his_code_value` varchar(32) DEFAULT NULL COMMENT 'his代码值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `drg_dip_result` (
`id` varchar(32) NOT NULL COMMENT '主键',
`visit_id` varchar(32) NOT NULL COMMENT '就诊id',
`psn_no` varchar(32) DEFAULT NULL COMMENT '人员编号',
`psn_name` varchar(32) DEFAULT NULL COMMENT '患者姓名',
`certno` varchar(32) DEFAULT NULL COMMENT '患者身份证号码',
`gend` varchar(32) DEFAULT NULL COMMENT '性别',
`age` int(11) DEFAULT NULL COMMENT '年龄',
`insutype` varchar(32) DEFAULT NULL COMMENT '险种',
`med_type` varchar(32) DEFAULT NULL COMMENT '业务类型',
`med_type_name` varchar(32) DEFAULT NULL COMMENT '业务类型名称',
`in_time` datetime DEFAULT NULL COMMENT '入院时间',
`out_time` datetime DEFAULT NULL COMMENT '出院时间',
`inpt_diagnose` varchar(500) DEFAULT NULL COMMENT '入院诊断',
`outpt_diagnose` varchar(500) DEFAULT NULL COMMENT '出院诊断',
`dept_id` varchar(32) DEFAULT NULL COMMENT '科室id',
`dept_name` varchar(32) DEFAULT NULL COMMENT '科室名称',
`doctor_id` varchar(32) DEFAULT NULL COMMENT '医生id',
`doctor_name` varchar(32) DEFAULT NULL COMMENT '医生姓名',
`medical_reg_no` varchar(32) DEFAULT NULL COMMENT '就医登记号',
`settle_id` varchar(32) DEFAULT NULL COMMENT 'his结算id',
`insure_settle_id` varchar(32) DEFAULT NULL COMMENT '医保结算id',
`drg_dip_code` varchar(32) DEFAULT NULL COMMENT 'drg\\dip组编码',
`drg_dip_name` varchar(128) DEFAULT NULL COMMENT 'drg\\dip组名称',
`icd10` varchar(32) DEFAULT NULL COMMENT '主要诊断',
`icd9` varchar(32) DEFAULT NULL COMMENT '主要手术',
`total_fee` decimal(10,2) DEFAULT NULL COMMENT '总费用',
`stand_fee` decimal(10,2) DEFAULT NULL COMMENT '标杆费用',
`override` decimal(10,2) DEFAULT NULL COMMENT '倍率',
`cdntn_pay` decimal(10,2) DEFAULT NULL COMMENT '统筹金额',
`diff_fee` decimal(10,2) DEFAULT NULL COMMENT '预计偏差',
`yp_fee` decimal(10,2) DEFAULT NULL COMMENT '药品费',
`cl_fee` decimal(10,2) DEFAULT NULL COMMENT '耗材费',
`states` varchar(2) DEFAULT '2' COMMENT '质控完成状态',
`type` varchar(2) DEFAULT NULL COMMENT '质控类型:(1:DRG,2:DIP)',
`business_type` varchar(2) DEFAULT NULL COMMENT '业务类型:(1:结算清单,2:病案首页)',
`business_id` varchar(32) DEFAULT NULL COMMENT '业务id',
`hosp_code` varchar(32) DEFAULT NULL COMMENT '医院编码',
`insure_reg_code` varchar(32) DEFAULT NULL COMMENT '医保注册编码',
`hosp_name` varchar(64) DEFAULT NULL COMMENT '医院名称',
`org_code` varchar(32) DEFAULT NULL COMMENT '机构编码',
`pro_medic_mater` varchar(32) DEFAULT NULL COMMENT '药占比',
`weight_value` varchar(32) DEFAULT NULL COMMENT '权重',
`pro_consum` varchar(32) DEFAULT NULL COMMENT '耗材占比',
`diag_fee_sco` varchar(32) DEFAULT NULL COMMENT '费用分值',
`with_ccormcc` varchar(128) DEFAULT NULL COMMENT 'cc/mcc信息',
`ccmcc_name` varchar(128) DEFAULT NULL COMMENT 'cc/mcc信息',
`bl` varchar(32) DEFAULT NULL COMMENT '倍率',
`rate` varchar(32) DEFAULT NULL COMMENT '费率',
`fee_Pay` decimal(10,2) DEFAULT NULL COMMENT '支付费用',
`stand_pro_medic_mater` varchar(32) DEFAULT NULL COMMENT '标杆药占比',
`stand_pro_consum` varchar(32) DEFAULT NULL COMMENT '标杆耗材占比',
`profit` decimal(10,2) DEFAULT NULL COMMENT '盈亏额',
`suspicious_num` int(11) DEFAULT NULL COMMENT '可疑条数',
`violation_num` int(11) DEFAULT NULL COMMENT '违规条数',
`medcasno` varchar(32) DEFAULT NULL COMMENT '病案号',
`visit_no` varchar(32) DEFAULT NULL COMMENT '住院号/就诊号',
`group_messages` text COMMENT '分组提示',
`group_result` varchar(32) DEFAULT NULL COMMENT '分组结果',
`valid_flag` varchar(2) DEFAULT '1' COMMENT '有效标志',
`crt_id` varchar(32) DEFAULT NULL COMMENT '创建人',
`crt_name` varchar(32) DEFAULT NULL COMMENT '创建姓名',
`crt_time` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DIP/DRG质控结果表';

CREATE TABLE `drg_dip_result_detail` (
`id` varchar(32) NOT NULL COMMENT 'id',
`result_id` varchar(32) DEFAULT NULL COMMENT '质控结果主表id',
`rule_level` varchar(32) DEFAULT NULL COMMENT '规则等级',
`rule_score` varchar(32) DEFAULT NULL COMMENT '规则分数',
`field_id` varchar(128) DEFAULT NULL COMMENT '错误字段字段名',
`check_filed` varchar(128) DEFAULT NULL COMMENT '错误字段字段中文名',
`result_msg` varchar(500) DEFAULT NULL COMMENT '质控结果信息',
`original_value` varchar(200) DEFAULT NULL COMMENT '字段原始值',
`is_main` varchar(32) DEFAULT NULL COMMENT '主次标识',
`sort` varchar(16) DEFAULT NULL COMMENT '排序号',
`rulet_type` varchar(32) DEFAULT NULL COMMENT '规则类型',
`valid_flag` varchar(2) DEFAULT '1' COMMENT '有效标志',
`crt_id` varchar(32) DEFAULT NULL COMMENT '创建人编号',
`crt_name` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
`crt_time` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DIP/DRG质控信息结果明细表';


ALTER TABLE base_advice  ADD COLUMN `oper_nation_name` varchar(32) NULL COMMENT '手术国家名称' ;
ALTER TABLE inpt_medicine_advance_advice ADD this_exec_time date NULL COMMENT '本次领药的结束时间';
ALTER TABLE inpt_medicine_advance_advice ADD last_exec_time date NULL COMMENT '上次医嘱的最后执行时间';
ALTER TABLE inpt_advice_exec  ADD advance_id varchar(100) NULL COMMENT '提前领药ID（inpt_medicine_advance主键ID）';
ALTER TABLE inpt_cost  ADD advance_id varchar(100) NULL COMMENT '提前领药ID（inpt_medicine_advance主键ID）';
ALTER TABLE phar_in_wait_receive  ADD advance_id varchar(100) NULL COMMENT '提前领药ID（inpt_medicine_advance主键ID）';
alter table insure_setl_info modify column spga_nurscare_days int(3) DEFAULT null COMMENT '特级护理天数';
alter table insure_setl_info modify column lv1_nurscare_days int(3) DEFAULT null COMMENT '一级护理天数';
alter table insure_setl_info modify column scd_nurscare_days int(3) DEFAULT null COMMENT '二级护理天数';
alter table insure_setl_info modify column lv3_nursecare_days int(3) DEFAULT null COMMENT '三级护理天数';
ALTER TABLE drg_dip_result ADD COLUMN `score_price` decimal(10,2) DEFAULT NULL COMMENT '分值单价';
ALTER TABLE outpt_medical_record ADD menstrual_history varchar(500) DEFAULT null COMMENT '月经生育史';
ALTER TABLE .outpt_medical_template ADD menstrual_history varchar(500) NULL COMMENT '月经生育史';
alter table  base_finance_classify add index `idx_base_finance_classify_02` (`up_code`) USING BTREE;








