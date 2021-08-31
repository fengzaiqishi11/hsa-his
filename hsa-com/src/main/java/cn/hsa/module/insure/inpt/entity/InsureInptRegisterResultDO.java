package cn.hsa.module.insure.inpt.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.insure.inpt.entity
* @Class_name: InsureInptRegisterResultDO
* @Describe:  入院登记 - 登记成功返回实体类（Result）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRegisterResultDO extends PageDO implements Serializable {
        private String mdtrtId;	// 就诊ID	字符型	30		Y	医保返回唯一流水
}