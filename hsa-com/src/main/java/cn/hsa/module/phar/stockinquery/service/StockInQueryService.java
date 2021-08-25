package cn.hsa.module.phar.stockinquery.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.phar.stockinquery
 * @class_name: StockInQueryService
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 20:55
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-stro")
public interface StockInQueryService {
    /**
     * @Method: queryPage()
     * @Description: 分页查询所有的药品出库与确认信息
     * @Param: stroOutinDTO 药房药库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:11
     * @Return:
     */
    @GetMapping("/web/stro/stockInQuery/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

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
    @PostMapping ("/web/stro/stockInQuery/batchCheck")
    WrapperResponse<Boolean> updateBatchCheck(Map map);

    /**
     * @Method: stockInDetail()
     * @Description: 药房入库明细信息
     * @Param: stroOutinDetailDTO出入库明细数据传输DTO对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/26 20:08
     * @Return:
     */
    @GetMapping("/web/stro/stockInQuery/stockInDetail")
    WrapperResponse<List<StroOutDetail>> queryStockInDetail(Map map);

}
