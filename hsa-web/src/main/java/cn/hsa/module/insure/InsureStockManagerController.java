package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
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
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureGoodBuyBack",insureGoodBuyBack);
        return insureStockManagerService_consumer.queryInsureGoodBuyBackPage(map);
    }

    ;

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
        map.put("insureGoodSell", insureGoodSell);
        return insureStockManagerService_consumer.queryInsureGoodSellPage(map);
    }

    ;

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

    ;


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
        map.put("insureGoodSellBack", insureGoodSellBack);
        return insureStockManagerService_consumer.queryInsureGoodSellBackPage(map);
    }

    ;

    /**
     * 上传商品销售退货信息
     *
     * @param map
     * @return
     */
    @PostMapping("/uploadInsureGoodSellBack")
    public WrapperResponse<Boolean> uploadInsureGoodSellBack(@RequestBody   Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureStockManagerService_consumer.uploadInsureGoodSellBack(map);
    }

    ;


    /**
     * 查询商品盘点信息
     *
     * @param insureInventoryCheck
     * @return
     */
    @GetMapping("/queryInsureInventoryCheckPage")
    public WrapperResponse<PageDTO> queryInsureInventoryCheckPage(InsureInventoryCheck insureInventoryCheck, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        String hospCode = sysUserDTO.getHospCode();
        insureInventoryCheck.setHospCode(hospCode);
        map.put("hospCode", hospCode);
        map.put("insureInventoryCheck",insureInventoryCheck);
        return insureStockManagerService_consumer.queryInsureInventoryCheckPage(map);
    }

    ;

    /**
     * 上传商品盘点信息
     *
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

    ;


    /**
     * 查询商品库存变更信息
     *
     * @param insureInventoryStockUpdate
     * @return
     */
    @GetMapping("/queryInsureInventoryStockUpdatePage")
    public WrapperResponse<PageDTO> queryInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureInventoryStockUpdate",insureInventoryStockUpdate);
        return insureStockManagerService_consumer.queryInsureInventoryStockUpdatePage(map);
    }

    ;

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
