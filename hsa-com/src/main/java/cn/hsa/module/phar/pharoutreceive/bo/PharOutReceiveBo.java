package cn.hsa.module.phar.pharoutreceive.bo;

import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharoutreceive.bo
 * @Class_name: PharOutReceiveBo
 * @Describe(描述):门诊领药申请信息Bo层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharOutReceiveBo {

    /**
     * @Menthod batchInsert
     * @Desrciption  新增门诊领药申请信息
     * @param pharOutReceiveDOList 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return int 受影响的行数
     */
    int batchInsert(List<PharOutReceiveDO> pharOutReceiveDOList);

}
