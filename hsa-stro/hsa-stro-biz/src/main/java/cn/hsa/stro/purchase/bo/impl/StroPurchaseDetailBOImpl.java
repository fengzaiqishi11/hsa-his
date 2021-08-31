package cn.hsa.stro.purchase.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.stro.purchase.bo.StroPurchaseDetailBO;
import cn.hsa.module.stro.purchase.dao.StroPurchaseDetailDAO;
import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Package_name: cn.hsa.stro.purchase.bo.impl
 * @Class_name: StroPurchaseDetailBOImpl
 * @Describe: 采购药品明细BoImpl 业务实现层
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/23 15:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class StroPurchaseDetailBOImpl extends HsafBO implements StroPurchaseDetailBO {

    @Resource
    private StroPurchaseDetailDAO stroPurchaseDetailDAO;

    @Override
    public int deleteByPrimaryKey(String id) {
        return stroPurchaseDetailDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(StroPurchaseDO record) {
        return stroPurchaseDetailDAO.insert(record);
    }

    @Override
    public int insertSelective(StroPurchaseDO record) {
        return stroPurchaseDetailDAO.insertSelective(record);
    }

    @Override
    public StroPurchaseDO selectByPrimaryKey(String id) {
        return stroPurchaseDetailDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(StroPurchaseDO record) {
        return stroPurchaseDetailDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(StroPurchaseDO record) {
        return stroPurchaseDetailDAO.updateByPrimaryKey(record);
    }
}
