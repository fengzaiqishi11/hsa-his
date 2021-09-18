package cn.hsa.module.outpt.outinInvoice.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinPartInvoiceDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fee.bo;
 * @Class_name: InvoiceBO
 * @Describe: 发票管理实现层接口
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/17 13:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutinInvoiceBO {

    /**
     * @Menthod getInvoicePage
     * @Desrciption 查询发票领用信息
     * @param outinInvoiceDTO  入参
     * @Author liaojiguang
     * @Date   2020/8/10 17:40
     * @Return cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO
     **/
    PageDTO getInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateInvoiceStatus
     * @Desrciption 发票使用后，发票主表，明细表数据处理方法
     * @param outinInvoiceDTO  入参(id,hospCode,prefix,curr_no,settleId)
     * @Author liaojiguang
     * @Date   2020/9/27 11:27
     * @Return Boolean
     **/
    OutinInvoiceDetailDO updateInvoiceStatus(OutinInvoiceDTO outinInvoiceDTO);

    List<OutinInvoiceDTO> getInvoiceList(OutinInvoiceDTO outinInvoiceDTO);

    Boolean updateOutinInvoiceStatus(OutinInvoiceDTO outinInvoiceDTO);

    Boolean insertOutinInvoiceDetail(OutinInvoiceDetailDO outinInvoiceDetailDO);

    /**
     * @Menthod updateInvoiceReceive
     * @Desrciption 发票领用
     * @param outinInvoiceDTO  入参
     * @Author liaojiguang
     * @Date   2020/8/24 17:40
     * @Return Boolean
     **/
    Boolean updateInvoiceReceive(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateInvoiceOutReceive
     * @Desrciption 发票退领
     * @param outinInvoiceDTO  入参
     * @Author liaojiguang
     * @Date   2020/8/24 17:40
     * @Return Boolean
     **/
    Boolean updateInvoiceOutReceive(OutinInvoiceDTO outinInvoiceDTO,String createId,String createName);

    /**
     * @Menthod updateInvoiceInvalid
     * @Desrciption 发票作废
     * @param outinInvoiceDTO  入参
     * @Author liaojiguang
     * @Date   2020/8/24 17:40
     * @Return Boolean
     **/
    Boolean updateInvoiceInvalid(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod getOutinInvoiceById()
     * @Desrciption   根据主键id查询发票领用详细信息
     * @Param [map] 发票管理传输DTO对象
     * @Author liaojiguang
     * @Date   2020/08/25 17:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    OutinInvoiceDO getOutinInvoiceById(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod getOutinInvoiceById()
     * @Desrciption   根据条件查询发票结算信息
     * @Param [map] 查询条件对象
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cPageDTO
     **/
    PageDTO queryPatientInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateInvoicePrint
     * @Desrciption 发票重打补打
     * <p>
     * 补打：发票号不变再次打印
     * 重打：现作废当前发票，获取新的发票，再次打印
     * </p>
     * @Param [map] 参数
     * @Author liaojiguang
     * @Date 2020/08/26 10:00
     * @Return Boolean
     **/
    Boolean updateInvoicePrint(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod getInvoiceDetailPage()
     * @Desrciption   根据条件分页查询发票领用详细信息
     * @Param 发票ID,医院编码
     * @Param
     * @Author liaojiguang
     * @Date   2020/09/02 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryInvoiceDetailPage(OutinInvoiceDetailDO outinInvoiceDetailDO);

    /**
     * @Menthod queryItemInfoByParams()
     * @Desrciption   获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryItemInfoByParams(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryItemInfoDetails()
     * @Desrciption   获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryItemInfoDetails(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryChangeDetails()
     * @Desrciption 获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author caoliang
     * @Date 2021/05/20 10:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
     **/
    PageDTO queryChangeDetails(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateForOutinInvoiceQuery
     * @Desrciption 查询发票段信息
     * @param outinInvoiceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/10 11:34 
     * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO>
     */
    List<OutinInvoiceDTO> updateForOutinInvoiceQuery(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updatePrintChecklist()
     * @Desrciption  打印清单
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    Boolean updatePrintChecklist(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryInactiveOutinInvoices
     * @Desrciption  获取待用发票信息
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/11/05 19:37
     * @Return java.util.List
     **/
    List<OutinInvoiceDTO> queryInactiveOutinInvoices(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod getInvoiceDetailByInvoiceInfo
     * @Desrciption 根据发票id + 发票号码获取详情信息
     * @param map 查询条件
     * @Author 廖继广
     * @Date 2020/12/28 10:34
     * @Return java.util.map
     */
    OutinInvoiceDetailDO getInvoiceDetailByInvoiceInfo(Map<String,Object> map);

    /**
     * @Menthod updateInvoiceDetailById
     * @Desrciption 更新发票详情
     * @param outinInvoiceDetailDO  入参
     * @Author liaojiguang
     * @Date   2020/12/28 10:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    Boolean updateInvoiceDetailById(OutinInvoiceDetailDO outinInvoiceDetailDO);

    /**
     * @Menthod updateInvoiceUseInfo
     * @Desrciption 清空发票号段使用人，状态改为待用状态
     * @param map  [crteId] 当前日结缴款人 [hospCode] 医院编码
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    Boolean updateInvoiceUseInfo(Map<String, Object> map);

    /**
     * @Description: 查询费用清单，例如：材料费：200.00 西药费： 890.00
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/15 10:33
     * @Return
     */
    OutptCostDTO getCostInventory(Map<String, Object> map);

    /**
     * @Description: 查询处方费用明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/23 10:28
     * @Return
     */
    List<Map<String, Object>> queryOutptCFCostDetails(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Description: 编辑发票信息（使用人，发票状态）
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/31 15:57
     * @Return
     */
	Boolean updateOutinInvoice(Map map);


    /**
     * @Description: 查询分单发票list
     * @Param:  outinInvoiceDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date 2021/9/16 15:31
     * @Return
     */
    Map<String, List<OutinPartInvoiceDO>> queryPartInvoice(OutinInvoiceDTO outinInvoiceDTO);

}
