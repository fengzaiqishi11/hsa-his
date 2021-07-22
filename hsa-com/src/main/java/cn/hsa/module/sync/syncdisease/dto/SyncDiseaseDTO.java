package cn.hsa.module.sync.syncdisease.dto;

import cn.hsa.module.sync.syncdisease.entity.SyncDiseaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.disease.dto
 * @Class_name:: CenterDiseaseDTO
 * @Description: 疾病管理数据传输DTO对象
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncDiseaseDTO extends SyncDiseaseDO implements Serializable {
    //输入框内容
    String keyword;
    // 要删除的id列表
    private List<String> ids;
}
