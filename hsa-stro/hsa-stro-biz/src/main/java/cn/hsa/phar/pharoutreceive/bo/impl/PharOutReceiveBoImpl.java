package cn.hsa.phar.pharoutreceive.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.phar.pharoutreceive.bo.PharOutReceiveBo;
import cn.hsa.module.phar.pharoutreceive.dao.PharOutReceiveDAO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.phar.pharoutreceive.bo.impl
 * @Class_name: PharOutReceiveBoImpl
 * @Describe(描述):门诊领药申请信息BoImpl层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class PharOutReceiveBoImpl extends HsafBO implements PharOutReceiveBo {

    @Resource
    private PharOutReceiveDAO pharOutReceiveDAO;

    /**
     * @Menthod batchInsert
     * @Desrciption  新增门诊领药申请信息
     * @param pharOutReceiveDOList 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return int 受影响的行数
     */
    @Override
    public int batchInsert(List<PharOutReceiveDO> pharOutReceiveDOList) {
        return pharOutReceiveDAO.batchInsert(pharOutReceiveDOList);
    }
}
