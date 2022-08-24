package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.stock.entity.*;
import cn.hsa.module.insure.stock.service.InsureStockManagerService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/web/insure/insureStockManager")
@Slf4j
public class InsureStockManagerController extends BaseController {
    @Resource
    private InsureStockManagerService insureStockManagerService_consumer;

    /**
     * 查询商品采购信息
     *
     * @param insureGoodBuy
     * @return
     */
    @GetMapping("/queryInsureGoodBuyPage")
    public WrapperResponse<PageDTO> queryInsureGoodBuyPage(InsureGoodBuy insureGoodBuy, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureGoodBuy.setHospCode(sysUserDTO.getHospCode());
        map.put("insureGoodBuy",insureGoodBuy);
        return insureStockManagerService_consumer.queryInsureGoodBuyPage(map);
    }

    ;

    /**
     * 上传商品采购信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureGoodBuy")
    public WrapperResponse<Boolean> uploadInsureGoodBuy(@RequestBody  Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureGoodBuy(map);
    }

    ;


    /**
     * 查询商品采购退货信息
     *
     * @param insureGoodBuyBack
     * @return
     */
    @GetMapping("/queryInsureGoodBuyBackPage")
    public WrapperResponse<PageDTO> queryInsureGoodBuyBackPage(InsureGoodBuyBack insureGoodBuyBack, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        insureGoodBuyBack.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureGoodBuyBack",insureGoodBuyBack);
        return insureStockManagerService_consumer.queryInsureGoodBuyBackPage(map);
    }

    /**
     * 上传商品采购退货信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureGoodBuyBack")
    public WrapperResponse<Boolean> uploadInsureGoodBuyBack(@RequestBody  Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureGoodBuyBack(map);
    }

    ;


    /**
     * 查询商品删除信息
     *
     * @param insureGoodInfoDelete
     * @return
     */
    @GetMapping("/queryInsureGoodInfoDeletePage")
    public WrapperResponse<PageDTO> queryInsureGoodInfoDeletePage(InsureGoodInfoDelete insureGoodInfoDelete, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureGoodInfoDelete.setHospCode(sysUserDTO.getHospCode());
        map.put("insureGoodInfoDelete",insureGoodInfoDelete);
        return insureStockManagerService_consumer.queryInsureGoodInfoDeletePage(map);
    }

    ;

    /**
     * 上传商品删除信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureGoodInfoDelete")
    public WrapperResponse<Boolean> uploadInsureGoodInfoDelete(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("certId", sysUserDTO.getId());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureStockManagerService_consumer.uploadInsureGoodInfoDelete(map);
    }

    ;


    /**
     * 查询商品销售信息
     *
     * @param insureGoodSell
     * @return
     */
    @GetMapping("/queryInsureGoodSellPage")
    public WrapperResponse<PageDTO> queryInsureGoodSellPage(InsureGoodSell insureGoodSell, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureGoodSell.setHospCode(sysUserDTO.getHospCode());
        map.put("insureGoodSell", insureGoodSell);
        return insureStockManagerService_consumer.queryInsureGoodSellPage(map);
    }


    /**
     * 查询商品销售信息-- 海南医保接口
     *
     * @param insureGoodSell
     * @return
     */
    @GetMapping("/queryInsureGoodSellPageForHainan")
    public WrapperResponse<PageDTO> queryInsureGoodSellPageForHainan(InsureGoodSell insureGoodSell, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureGoodSell.setHospCode(sysUserDTO.getHospCode());
        map.put("insureGoodSell", insureGoodSell);
        return insureStockManagerService_consumer.queryInsureGoodSellPageForHainan(map);
    }
    /**
     * @Meth: queryPersonList
     * @Description: 查询销售/退货 人员
     * @Param: [insureRecruitPurchaseDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    @GetMapping("/queryPersonList")
    public WrapperResponse<PageDTO> queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO,
                                                    HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureRecruitPurchaseDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureRecruitPurchaseDTO", insureRecruitPurchaseDTO);
        return insureStockManagerService_consumer.queryPersonList(map);
    }


    /**
     * 上传商品销售信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureGoodSell")
    public WrapperResponse<Boolean> uploadInsureGoodSell(@RequestBody  Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureGoodSell(map);
    }



    /**
     * 查询商品销售退货信息
     *
     * @param insureGoodSellBack
     * @return
     */
    @GetMapping("/queryInsureGoodSellBackPage")
    public WrapperResponse<PageDTO> queryInsureGoodSellBackPage(InsureGoodSellBack insureGoodSellBack, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureGoodSellBack.setHospCode(sysUserDTO.getHospCode());
        map.put("insureGoodSellBack", insureGoodSellBack);
        return insureStockManagerService_consumer.queryInsureGoodSellBackPage(map);
    }

    /**
     * 查询商品销售退货信息  -- 海南医保接口
     *
     * @param insureGoodSellBack
     * @return
     */
    @GetMapping("/queryInsureGoodSellBackPageForHainan")
    public WrapperResponse<PageDTO> queryInsureGoodSellBackPageForHainan(InsureGoodSellBack insureGoodSellBack, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureGoodSellBack.setHospCode(sysUserDTO.getHospCode());
        map.put("insureGoodSellBack", insureGoodSellBack);
        return insureStockManagerService_consumer.queryInsureGoodSellBackPageForHainan(map);
    }



    /**
     * 上传商品销售退货信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureGoodSellBack")
    public WrapperResponse<Boolean> uploadInsureGoodSellBack(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureGoodSellBack(map);
    }

    /**
     * @Meth: queryInsureInventoryCheckPage
     * @Description: 查询需要上传的盘存信息
     * @Param: [insureInventoryCheck, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    @GetMapping("/queryInsureInventoryCheckPage")
    public WrapperResponse<PageDTO> queryInsureInventoryCheckPage(InsureInventoryCheck insureInventoryCheck,
                                                                  HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        String hospCode = sysUserDTO.getHospCode();
        insureInventoryCheck.setHospCode(hospCode);
        map.put("hospCode", hospCode);
        map.put("insureInventoryCheck",insureInventoryCheck);
        return insureStockManagerService_consumer.queryInsureInventoryCheckPage(map);
    }


    /**
     * 上传商品盘点信息
     *【3501】商品盘存上传
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureInventoryCheck")
    public WrapperResponse<Boolean> uploadInsureInventoryCheck(@RequestBody  Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureInventoryCheck(map);
    }



    /**
     * 查询商品库存变更信息
     * @param insureInventoryStockUpdate
     * @return
     */
    @GetMapping("/queryInsureInventoryStockUpdatePage")
    public WrapperResponse<PageDTO> queryInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureInventoryStockUpdate.setHospCode(sysUserDTO.getHospCode());
        map.put("insureInventoryStockUpdate",insureInventoryStockUpdate);
        return insureStockManagerService_consumer.queryInsureInventoryStockUpdatePage(map);
    }


    /**
     * 上传商品库存变更信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureInventoryStock")
    public WrapperResponse<Boolean> uploadInsureInventoryStock(@RequestBody  Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureInventoryStock(map);
    }

    ;
}
