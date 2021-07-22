package cn.hsa.module.center.sync.dto;

import cn.hsa.module.center.sync.entity.CenterSyncDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.sync.dto
 * @Class_name: 1.3 医院数据同步表
 * @Describe: 1.3 医院数据同步表
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterSyncDTO extends CenterSyncDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 605431683282168028L;

    private String newHospCode;
    //表名字段，用于校验表名是否唯一
    private String tableNameField;

    private List<CenterSyncDTO> list;

    private String queryStr;
}
