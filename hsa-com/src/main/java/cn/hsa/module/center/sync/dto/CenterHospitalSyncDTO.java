package cn.hsa.module.center.sync.dto;

import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.sync.dto
 * @Class_name: CenterHospitalSyncDTO
 * @Describe: 1.4 医院数据同步关系表
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterHospitalSyncDTO extends CenterHospitalSyncDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 5395024959956634843L;

    //同步表集合
    private List<CenterSyncDTO> centerSyncDOList;
}
