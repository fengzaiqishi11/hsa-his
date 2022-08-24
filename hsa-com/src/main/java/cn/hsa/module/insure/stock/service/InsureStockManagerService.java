package cn.hsa.module.insure.stock.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.stock.entity.*;
import com.github.pagehelper.Page;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.stock.service
 * @Class_name: InsureStockManagerService
 * @Describe(描述): 新医保【库存管理】统一开放调用 Service 接口
 * @Author: Pengbo
 * @Date: 2021/08/21 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureStockManagerService {
    /**
     * 查询商品采购信息
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureGoodBuyPage(Map<String, Object> map);

    /**
     * 批量修改商品采购信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureGoodBuy(Map<String, Object> map);


    /**
     * 查询商品采购退货信息
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureGoodBuyBackPage(Map<String, Object> map);

    /**
     * 批量修改商品采购退货信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureGoodBuyBack(Map<String, Object> map);


    /**
     * 查询商品删除信息
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureGoodInfoDeletePage(Map<String, Object> map);

    /**
     * 批量修改商品删除信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureGoodInfoDelete(Map<String, Object> map);


    /**
     * 查询商品销售信息
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureGoodSellPage(Map<String, Object> map);


    /**
     * 查询商品销售信息-海南医保接口
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureGoodSellPageForHainan(Map<String, Object> map);

    /**
     * 批量修改商品销售信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureGoodSell(Map<String, Object> map);


    /**
     * 查询商品销售退货信息
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureGoodSellBackPage(Map<String, Object> map);

    /**
     * 批量修改商品销售退货信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureGoodSellBack(Map<String, Object> map);


    /**
     * @Meth: queryInsureInventoryCheckPage
     * @Description: 查询需要上传的盘存信息
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    WrapperResponse<PageDTO> queryInsureInventoryCheckPage(Map<String, Object> map);

    /**
     * 批量修改商品盘点信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureInventoryCheck(Map<String, Object> map);


    /**
     * 查询商品库存变更信息
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureInventoryStockUpdatePage(Map<String, Object> map);

    /**
     * 批量修改商品库存变更信息
     *
     * @param map
     * @return
     */
    WrapperResponse<Boolean> uploadInsureInventoryStock(Map<String, Object> map);
    /**
     * @Meth: queryPersonList
     * @Description: 查询销售 / 退货人员
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    WrapperResponse<PageDTO> queryPersonList(Map<String, Object> map);

    WrapperResponse<PageDTO> queryInsureGoodSellBackPageForHainan(Map<String, Object> map);
}
