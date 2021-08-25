package cn.hsa.module.outpt.outinInvoice.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fee.service
 * @Class_name: InvoiceService
 * @Describe: 发票管理Service接口层（提供给dubbo调用）
 * @Author: liaojiguang
 * @Eamil: liaojiguang@powersi.com.cn
 * @Date: 2020/8/17 18:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutinInvoiceService {

    /**
     * @Menthod getInvoicePage
     * @Desrciption 根据参数获取发票领用信息
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/8/17 17:40
     * @Return cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO
     **/
    @GetMapping("/service/outpt/outinInvoice/getInvociePage")
    WrapperResponse<PageDTO> getInvoicePage(Map<String,Object> map);

    /**
     * @Menthod updateInvoiceStatus
     * @Desrciption 发票使用后，发票主表，明细表数据处理方法
     * @param map  入参(id,hospCode,prefix,currNo,settleId)
     * @Author liaojiguang
     * @Date   2020/9/27 11:27
     * @Return WrapperResponse<Boolean>
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceStatus")
    WrapperResponse<OutinInvoiceDetailDO> updateInvoiceStatus(Map map);

    @GetMapping("/service/outpt/outinInvoice/getInvoiceList")
    WrapperResponse<List<OutinInvoiceDTO>> getInvoiceList(Map<String,Object> map);

    @PutMapping("/service/outpt/outinInvoice/updateOutinInvoiceStatus")
    WrapperResponse<Boolean> updateOutinInvoiceStatus(Map<String,Object> map);

    @PutMapping("/service/outpt/outinInvoice/insertOutinInvoiceDetail")
    WrapperResponse<Boolean> insertOutinInvoiceDetail(Map<String,Object> map);

    /**
     * @Menthod updateInvoiceReceive
     * @Desrciption 发票领用
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/8/24 17:40
     * @Return cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceReceive")
    WrapperResponse<Boolean> updateInvoiceReceive(Map<String,Object> map);

    /**
     * @Menthod updateInvoiceOutReceive
     * @Desrciption 发票退领
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/8/24 17:40
     * @Return cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceOutReceive")
    WrapperResponse<Boolean> updateInvoiceOutReceive(Map<String,Object> map);

    /**
     * @Menthod updateInvoiceInvalid
     * @Desrciption 发票作废
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/8/24 17:40
     * @Return cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceInvalid")
    WrapperResponse<Boolean> updateInvoiceInvalid(Map<String,Object> map);

    /**
     * @Menthod getOutinInvoiceById()
     * @Desrciption   根据主键id查询发票领用详细信息
     * @Param [map] 发票管理传输DTO对象
     * @Author liaojiguang
     * @Date   2020/08/25 17:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    @GetMapping("/service/outpt/outinInvoice/getOutinInvoiceById")
    WrapperResponse<OutinInvoiceDO> getOutinInvoiceById(Map map);

    /**
     * @Menthod queryPatientInvoicePage()
     * @Desrciption   根据条件查询发票重打补打信息展示数据
     * @Param [map] 发票管理传输DTO对象
     * @Author liaojiguang
     * @Date   2020/08/26 09:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutptRegisterDTO>
     **/
    @GetMapping("/service/outpt/outinInvoice/getPatientInvoicePage")
    WrapperResponse<PageDTO> queryPatientInvoicePage(Map map);

    /**
     * @Menthod updateInvoicePrint
     * @Desrciption 发票补打重打
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/8/26 17:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceOutReceive")
    WrapperResponse<Boolean> updateInvoicePrint(Map<String,Object> map);

    /**
     * @Menthod getInvoiceDetailPage()
     * @Desrciption   根据条件分页查询发票领用详细信息
     * @Param 发票ID
     * @Param
     * @Author liaojiguang
     * @Date   2020/09/02 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/outpt/outinInvoice/getInvoiceDetailPage")
    WrapperResponse<PageDTO> queryInvoiceDetailPage(Map map);

    /**
     * @Menthod queryItemInfoDetails()
     * @Desrciption   获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @GetMapping("/service/outpt/outinInvoice/queryItemInfoDetails")
    WrapperResponse<List<Map<String, Object>>> queryItemInfoDetails(Map map);

    /**
     * @Menthod queryChangeDetails()
     * @Desrciption 获取门诊费用明细信息
     * @Param outinInvoiceDTO
     * @Author caoliang
     * @Date 2021/05/20 10:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
     *
     * @return*/
    @GetMapping("/service/outpt/outinInvoice/queryChangeDetails")
    WrapperResponse<PageDTO> queryChangeDetails(Map map);

    /**
     * @Menthod queryItemInfoByParams()
     * @Desrciption   获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @GetMapping("/service/outpt/outinInvoice/queryItemInfoByParams")
    WrapperResponse<List<Map<String, Object>>> queryItemInfoByParams(Map map);

    /**
     * @Menthod updateForOutinInvoiceQuery
     * @Desrciption 查询发票段信息
     * @param map 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/10 11:34
     * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO>
     */
    @GetMapping("/service/outpt/outinInvoice/updateForOutinInvoiceQuery")
    WrapperResponse<List<OutinInvoiceDTO>> updateForOutinInvoiceQuery(Map map);

    /**
     * @Menthod updatePrintChecklist()
     * @Desrciption  打印清单
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    WrapperResponse<Boolean> updatePrintChecklist(Map map);

    /**
     * @Menthod queryInactiveOutinInvoices
     * @Desrciption 获取待用发票
     * @param map 查询条件
     * @Author 廖继广
     * @Date 2020/10/10 11:34
     * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO>
     */
    @GetMapping("/service/outpt/outinInvoice/queryInactiveOutinInvoices")
    WrapperResponse<List<OutinInvoiceDTO>> queryInactiveOutinInvoices(Map map);

    /**
     * @Menthod getInvoiceDetailByInvoiceInfo
     * @Desrciption 根据发票id + 发票号码获取详情信息
     * @param map 查询条件
     * @Author 廖继广
     * @Date 2020/12/28 11:34
     * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDetailDTO>
     */
    @GetMapping("/service/outpt/outinInvoice/getInvoiceDetailByInvoiceInfo")
    WrapperResponse<OutinInvoiceDetailDO> getInvoiceDetailByInvoiceInfo(Map map);

    /**
     * @Menthod updateInvoiceDetailById
     * @Desrciption 更新发票详情
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/12/28 10:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceDetailById")
    WrapperResponse<Boolean> updateInvoiceDetailById(Map<String,Object> map);

    /**
     * @Menthod updateInvoiceUseInfo
     * @Desrciption 清空发票号段使用人，状态改为待用状态
     * @param map  [crteId] 当前日结缴款人 [hospCode] 医院编码
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    @PutMapping("/service/outpt/outinInvoice/updateInvoiceUseInfo")
    Boolean updateInvoiceUseInfo(Map<String,Object> map);

    /**
     * @Description: 查询费用清单，例如：材料费：200.00 西药费： 890.00
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/15 10:31
     * @Return
     */
    @GetMapping("/service/outpt/outinInvoice/getCostInventory")
	WrapperResponse<OutptCostDTO> getCostInventory(Map<String, Object> map);

    /**
     * @Description: 查询处方费用明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/23 10:20
     * @Return
     */
    @GetMapping("/service/outpt/outinInvoice/queryOutptCFCostDetails")
    WrapperResponse<List<Map<String, Object>>> queryOutptCFCostDetails(Map<String, Object> map);

    /**
     * @Description: 编辑发票信息（使用人，发票状态）
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/31 15:55
     * @Return
     */
    @PostMapping("/service/outpt/outinInvoice/updateOutinInvoice")
	WrapperResponse<Boolean> updateOutinInvoice(Map map);
}
