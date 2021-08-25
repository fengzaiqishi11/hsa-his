package cn.hsa.phar.stockinquery.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharapply.dto.StroOutDTO;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import cn.hsa.module.phar.stockinquery.bo.StockInQueryBO;
import cn.hsa.module.phar.stockinquery.service.StockInQueryService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.stockinquery.service.impl
 * @class_name: StockInQueryServiceImpl
 * @Description:
 * @Author: fuhui
 * @Company: 创智和宇
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 20:59
 **/
@HsafRestPath("/service/stro/stockinquery")
@Slf4j
@Service("stockInQueryService_provider")
public class StockInQueryServiceImpl extends HsafService implements StockInQueryService {
    /**
     * 入库查询与确认BO层
     */
    @Resource
    private StockInQueryBO stockInQueryBO;

    /**
     * @Method: queryPage()
     * @Description: 分页查询所有的药品出库与确认信息
     * @Param: stroOutinDTO 药房药库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:11
     * @Return:
     */
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        StroOutDTO stroOutDTO = MapUtils.get(map, "stroOutDTO");
        PageDTO pageDTO=stockInQueryBO.queryPage(stroOutDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: batchCheck()
     * @Description: 批量入库审核药房数据信息
     * @Param:  stroOutinDTO 药房药库数据传输对象
     * 1.ids: 批量审核id list集合
     * 2. hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:13
     * @Return:
     */
    @Override
    @HsafRestPath(value = "/batchCheck", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateBatchCheck(Map map) {
        StroOutDTO stroOutDTO  = MapUtils.get(map, "stroOutDTO");
        return WrapperResponse.success(stockInQueryBO.updateBatchCheck(stroOutDTO));
    }

    /**
     * @Method: stockInDetail()
     * @Description: 药房入库明细信息
     * @Param: stroOutinDetailDTO出入库明细数据传输DTO对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/26 20:08
     * @Return:
     */
    @Override
    @HsafRestPath(value = "/stockInDetail", method = RequestMethod.GET)
    public WrapperResponse<List<StroOutDetail>> queryStockInDetail(Map map) {
        StroOutDTO stroOutDTO  = MapUtils.get(map, "stroOutDTO");
        return WrapperResponse.success(stockInQueryBO.queryStockInDetail(stroOutDTO));
    }
}
