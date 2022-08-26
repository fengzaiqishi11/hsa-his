package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.stock.bo.InsureStockManagerBO;
import cn.hsa.module.insure.stock.entity.*;
import cn.hsa.module.insure.stock.service.InsureStockManagerService;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InsureNHPatientServiceImpl
 * @Describe: 农合医保患者信息数据下发
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/4/19 19:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureStockManager")
@Service("insureStockManagerService_provider")
public class InsureStockManagerServiceImpl  extends HsafService implements InsureStockManagerService {

    @Resource
    private InsureStockManagerBO insureStockManagerBO;


    /**
     * 查询商品采购信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureGoodBuyPage(Map<String, Object> map) {
        InsureGoodBuy insureGoodBuy = MapUtils.getEmptyErr(map,"insureGoodBuy","查询采购信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodBuyPage(insureGoodBuy)));
    }

    /**
     * 批量修改商品采购信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureGoodBuy(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureGoodBuy(map));
    }

    /**
     * 查询商品采购退货信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureGoodBuyBackPage(Map<String, Object> map) {
        InsureGoodBuyBack insureGoodBuyBack = MapUtils.getEmptyErr(map,"insureGoodBuyBack","查询采购退货信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodBuyBackPage(insureGoodBuyBack)));
    }

    /**
     * 批量修改商品采购退货信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureGoodBuyBack(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureGoodBuyBack(map));
    }

    /**
     * 查询商品删除信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureGoodInfoDeletePage(Map<String, Object> map) {
        InsureGoodInfoDelete insureGoodInfoDelete = MapUtils.getEmptyErr(map,"insureGoodInfoDelete","查询采购信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodInfoDeletePage(insureGoodInfoDelete)));
    }

    /**
     * 批量修改商品删除信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureGoodInfoDelete(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureGoodInfoDelete(map));
    }

    /**
     * 查询商品销售信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureGoodSellPage(Map<String, Object> map) {
        InsureGoodSell insureGoodSell = MapUtils.getEmptyErr(map,"insureGoodSell","查询销售信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodSellPage(insureGoodSell)));
    }


    /**
     * 查询商品销售信息-- 海南医保接口
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureGoodSellPageForHainan(Map<String, Object> map) {
        InsureGoodSell insureGoodSell = MapUtils.getEmptyErr(map,"insureGoodSell","查询销售信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodSellPageForHainan(insureGoodSell)));
    }

    /**
     * 批量修改商品销售信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureGoodSell(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureGoodSell(map));
    }

    /**
     * 查询商品销售退货信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureGoodSellBackPage(Map<String, Object> map) {
        InsureGoodSellBack insureGoodSellBack = MapUtils.getEmptyErr(map,"insureGoodSellBack","查询销售退货信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodSellBackPage(insureGoodSellBack)));
    }

    /**
     * 批量修改商品销售退货信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureGoodSellBack(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureGoodSellBack(map));
    }

    /**
     * @Meth: queryInsureInventoryCheckPage
     * @Description: 查询需要上传的盘存信息
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureInventoryCheckPage(Map<String, Object> map) {
        InsureInventoryCheck insureInventoryCheck = MapUtils.getEmptyErr(map,"insureInventoryCheck","查询盘点信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureInventoryCheckPage(insureInventoryCheck)));
    }


    @Override
    public WrapperResponse<PageDTO> queryInsureGoodSellBackPageForHainan(Map<String, Object> map) {
        InsureGoodSellBack insureGoodSellBack = MapUtils.getEmptyErr(map,"insureGoodSellBack","查询销售退货信息出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureGoodSellBackPageForHainan(insureGoodSellBack)));
    }
    /**
     * 批量修改商品盘点信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureInventoryCheck(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureInventoryCheck(map));
    }

    /**
     * 查询商品库存变更信息
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureInventoryStockUpdatePage(Map<String, Object> map) {
        InsureInventoryStockUpdate insureInventoryStockUpdate = MapUtils.getEmptyErr(map,"insureInventoryStockUpdate","查询库存变更出错!");
        return WrapperResponse.success(PageDTO.of(insureStockManagerBO.queryInsureInventoryStockUpdatePage(insureInventoryStockUpdate)));
    }

    /**
     * 批量修改商品库存变更信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> uploadInsureInventoryStock(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.uploadInsureInventoryStock(map));
    }
    /**
     * @Meth: queryPersonList
     * @Description: 查询销售或者退货人员
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    @Override
    public WrapperResponse<PageDTO> queryPersonList(Map<String, Object> map) {
        return WrapperResponse.success(insureStockManagerBO.queryPersonList(MapUtils.get(map, "insureRecruitPurchaseDTO")));
    }
}
