DROP TABLE IF EXISTS `report_configuration`;

CREATE TABLE `report_configuration`
(
    `id`               varchar(32) NOT NULL COMMENT '主键',
    `temp_code`        varchar(30) NOT NULL COMMENT '模板编码',
    `temp_name`        varchar(30) NOT NULL COMMENT '报表模板文件名称',
    `temp_content`     mediumblob  NOT NULL COMMENT '报表模板文件内容',
    `return_data_type` varchar(6)  DEFAULT NULL COMMENT '返回数据类型 1-html 2-文件地址 3-文件流',
    `file_format`      varchar(6)  DEFAULT NULL COMMENT '文件格式 1-html 2-pdf 3-txt 4-jpg',
    `updt_time`        datetime    NOT NULL COMMENT '更新时间',
    `crter_id`         varchar(20) DEFAULT NULL COMMENT '创建人',
    `crter_name`       varchar(50) DEFAULT NULL COMMENT '创建人姓名',
    `crte_time`        datetime    DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报表配置表';

DROP TABLE IF EXISTS `report_file_record`;

CREATE TABLE `report_file_record`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `temp_code`    varchar(30)  NOT NULL COMMENT '模板编码',
    `file_address` varchar(200) NOT NULL COMMENT '文件地址',
    `crter_id`     varchar(20) DEFAULT NULL COMMENT '创建人',
    `crter_name`   varchar(50) DEFAULT NULL COMMENT '创建人姓名',
    `crte_time`    datetime    DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报表文件记录表';