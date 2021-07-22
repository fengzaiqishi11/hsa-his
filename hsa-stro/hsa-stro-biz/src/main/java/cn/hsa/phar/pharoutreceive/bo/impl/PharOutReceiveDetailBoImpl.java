package cn.hsa.phar.pharoutreceive.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.phar.pharoutreceive.bo.PharOutReceiveDetailBo;
import cn.hsa.module.phar.pharoutreceive.dao.PharOutReceiveDetailDAO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.phar.pharoutreceive.bo.impl
 * @Class_name: PharOutReceiveDetailBoImpl
 * @Describe(描述):门诊领药申请明细信息BoImpl层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class PharOutReceiveDetailBoImpl extends HsafBO implements PharOutReceiveDetailBo {

    @Resource
    private PharOutReceiveDetailDAO pharOutReceiveDetailDAO;

    @Override
    public int batchInsert(List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList) {
        return pharOutReceiveDetailDAO.batchInsert(pharOutReceiveDetailDOList);
    }
}
