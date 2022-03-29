package cn.hsa.stro.stroinvoicing.bo.impl;

import cn.hsa.module.stro.stroinvoicing.bo.StroInvoicingMonthlyBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Class_name: StroInvoicingMonthlyBOImpl
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:42
 **/
@Component
@Slf4j
public class StroInvoicingMonthlyBOImpl implements StroInvoicingMonthlyBO {
    /**
     * @Meth: copyStroInvoicing
     * @Description: 同步进销存数据
     * @Param: [map]
     * @return: boolean
     * @Author: zhangguorui
     * @Date: 2022/3/18
     */
    @Override
    public boolean copyStroInvoicing(Map map) {

        return false;
    }
}
