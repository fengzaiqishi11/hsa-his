package cn.hsa.module.insure;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.module.service.InsureRecruitPurchaseService;
import cn.hsa.module.insure.stock.entity.InsureInventoryStockUpdate;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: CommonInterfaceController
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureRecruitPurchase")
@Slf4j
public class InsureRecruitPurchaseController extends BaseController {

    @Resource
    private InsureRecruitPurchaseService insureRecruitPurchaseService_consumer;


    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @GetMapping("/queryHospitalInventory")
    public WrapperResponse<PageDTO> queryHospitalInventory(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        insureRecruitPurchaseDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureRecruitPurchaseDTO",insureRecruitPurchaseDTO);
        return insureRecruitPurchaseService_consumer.queryAll(map);
    }


    /**
     * @Description: 获取当前医院库存列表
     * @Param: [map]
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021/8/24 10:50
     */
    @PostMapping("/queryCommoditySalesRecord")
    public WrapperResponse<Map<String, Object>> queryCommoditySalesRecord(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.queryCommoditySalesRecord(map);
    }

    /**
     * @Menthod: queryPersonList
     * @Desrciption: 查询(门诊/住院)存在(销售/退货)(药品/材料)记录的人员列表
     * @Param:
     *      1.itemType-药品1，材料2
     *      2.queryType-全院0，门诊1，住院2
     *      3.keyword-搜索条件(姓名、证件号、就诊号/住院号、住院床号)
     *      4.startDate-搜索开始日期
     *      5.endDate-搜索结束日期
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-31 10:17
     * @Return:
     **/
    @GetMapping("/queryPersonList")
    public WrapperResponse<PageDTO> queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureRecruitPurchaseDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureRecruitPurchaseDTO", insureRecruitPurchaseDTO);
        return insureRecruitPurchaseService_consumer.queryPersonList(map);
    }

    /**
     * @Menthod: queryItemList
     * @Desrciption: 根据就诊id查询人员对应的药品/材料信息
     * @Param:
     *      1.visitId-就诊id
     *      2.itemType-药品1，材料2
     *      3.queryType-门诊1，住院2
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-09-01 15:13
     * @Return:
     **/
    @GetMapping("/queryItemList")
    public WrapperResponse<PageDTO> queryItemList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(insureRecruitPurchaseDTO.getVisitId())) {
            throw new RuntimeException("未选择需要查询的人员信息");
        }
        if (StringUtils.isEmpty(insureRecruitPurchaseDTO.getItemType())) {
            throw new RuntimeException("未选择业务类型，药品1或者材料2");
        }
        if (StringUtils.isEmpty(insureRecruitPurchaseDTO.getQueryType())) {
            throw new RuntimeException("未选择查询范围，门诊1或者住院2");
        }
//        if (StringUtils.isEmpty(insureRecruitPurchaseDTO.getInsureRegCode())) {
//            throw new RuntimeException("未选择医保机构编码，请选择");
//        }
        if (StringUtils.isEmpty(insureRecruitPurchaseDTO.getSellType())) {
            throw new RuntimeException("未选择业务类型，销售1或者退货2");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureRecruitPurchaseDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureRecruitPurchaseDTO", insureRecruitPurchaseDTO);
        return insureRecruitPurchaseService_consumer.queryItemList(map);
    }

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售列表查询【8503】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: list
     **/
    @GetMapping("/queryDrugSells")
    public WrapperResponse<PageDTO> queryDrugSells(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureRecruitPurchaseDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureRecruitPurchaseDTO", insureRecruitPurchaseDTO);
        return insureRecruitPurchaseService_consumer.queryDrugSells(map);
    }

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品/材料销售【8504】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    @PostMapping("/addDrugSells")
    public WrapperResponse<Boolean> addDrugSells(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.addDrugSells(map);
    }
    /**
     * @Meth: uploadToInsure
     * @Description: 招采：药品库存上传变更接口
     * @Param: [map, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/10/20
     */
    @PostMapping("/updateToInsure")
    public WrapperResponse<Boolean> updateToInsure(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("certId", sysUserDTO.getId());
        return insureRecruitPurchaseService_consumer.updateToInsure(map);
    }
    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售退货【8505】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    @PostMapping("/deleteDrugSells")
    public WrapperResponse<Boolean> deleteDrugSells(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.deleteDrugSells(map);
    }
    /**
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 接口连通性测试
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/26 9:43
     * @Return
     **/
    @PostMapping("/selectCommonInterfaceTest")
    public WrapperResponse<Map<String,Object>> selectCommonInterfaceTest(Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paramMap.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.selectCommonInterfaceTest(paramMap);
    }

    /**
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 获取token
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/26 9:43
     * @Return
     **/
    @PostMapping("/getToken")
    public WrapperResponse<Map<String,Object>> getToken(Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paramMap.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.getToken(paramMap);
    }
    /**
     * 海南-查询药品耗材库存变更信息
     * @param insureInventoryStockUpdate
     * @return
     */
    @GetMapping("/queryYpInsureInventoryStockUpdatePage")
    public WrapperResponse<PageDTO> queryYpInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureInventoryStockUpdate.setHospCode(sysUserDTO.getHospCode());
        map.put("insureInventoryStockUpdate",insureInventoryStockUpdate);
        return insureRecruitPurchaseService_consumer.queryYpInsureInventoryStockUpdatePage(map);
    }
}
