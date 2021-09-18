package cn.hsa.outpt.outinInvoice.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.outinInvoice.bo.OutinInvoiceBO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinPartInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.service.OutinInvoiceService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.fee.service.impl
 * @Class_name:: invoiceServiceImpl
 * @Description: 发票管理service实现类
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/24 10:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/outinInvoice")
@Service("outinInvoiceService_provider")
public class OutinInvoiceServiceImpl extends HsafService implements OutinInvoiceService {

    @Resource
    private OutinInvoiceBO outinInvoiceBO;

    /**
     * @Menthod: getInvoicePage
     * @Desrciption: 根据条件分页查询发票领用信息
     * @Param: map
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/24 10:08
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getInvoicePage(Map<String, Object> map) {
        PageDTO pageDTO = outinInvoiceBO.getInvoicePage(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod updateInvoiceStatus
     * @Desrciption 发票使用后，发票主表，明细表数据处理方法
     * @param map  入参(id,hospCode,prefix,curr_no,settleId)
     * @Author liaojiguang
     * @Date   2020/9/27 11:27
     * @Return WrapperResponse<OutinInvoiceDetailDO>
     **/
    @Override
    public WrapperResponse<OutinInvoiceDetailDO> updateInvoiceStatus(Map map) {
        return WrapperResponse.success(outinInvoiceBO.updateInvoiceStatus(MapUtils.get(map,"outinInvoiceDTO")));
    }

    @Override
    public WrapperResponse<List<OutinInvoiceDTO>> getInvoiceList(Map<String, Object> map) {
        List<OutinInvoiceDTO> list = outinInvoiceBO.getInvoiceList(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(list);
    }

    @Override
    public WrapperResponse<Boolean> updateOutinInvoiceStatus(Map<String, Object> map) {
        Boolean falg = outinInvoiceBO.updateOutinInvoiceStatus(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(falg);
    }

    @Override
    public WrapperResponse<Boolean> insertOutinInvoiceDetail(Map<String, Object> map) {
        Boolean falg = outinInvoiceBO.insertOutinInvoiceDetail(MapUtils.get(map,"outinInvoiceDetailDO"));
        return WrapperResponse.success(falg);
    }

    /**
     * @Menthod: updateInvoiceReceive
     * @Desrciption: 发票领用
     * @Param: map
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/24 17:41
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateInvoiceReceive(Map<String, Object> map) {
        Boolean flag = outinInvoiceBO.updateInvoiceReceive(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(flag);
    }

    /**
     * @Menthod: updateInvoiceReceive
     * @Desrciption: 发票退领
     * @Param: map
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/24 17:41
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateInvoiceOutReceive(Map<String, Object> map) {
        Boolean flag = outinInvoiceBO.updateInvoiceOutReceive(MapUtils.get(map,"outinInvoiceDTO"),MapUtils.get(map,"createId"),MapUtils.get(map,"createName"));
        return WrapperResponse.success(flag);
    }

    /**
     * @Menthod: updateInvoiceReceive
     * @Desrciption: 发票作废
     * @Param: map
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/24 17:41
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateInvoiceInvalid(Map<String, Object> map) {
        Boolean flag = outinInvoiceBO.updateInvoiceInvalid(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(flag);
    }

    /**
     * @Menthod getOutinInvoiceById()
     * @Desrciption   根据主键id查询发票领用详细信息
     * @Param [map] 发票管理传输DTO对象
     * @Author liaojiguang
     * @Date   2020/08/25 17:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    @Override
    public WrapperResponse<OutinInvoiceDO> getOutinInvoiceById(Map map) {
        OutinInvoiceDO outinInvoiceDO = outinInvoiceBO.getOutinInvoiceById(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(outinInvoiceDO);
    }

    /**
     * @Menthod queryPatientInvoicePage()
     * @Desrciption   根据条件查询发票结算信息
     * @Param [map] 查询条件对象
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPatientInvoicePage(Map map) {
        PageDTO pageDTO = outinInvoiceBO.queryPatientInvoicePage(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod getInvoiceDetailPage()
     * @Desrciption   根据条件分页查询发票领用详细信息
     * @Param 发票ID,医院编码
     * @Param
     * @Author liaojiguang
     * @Date   2020/09/02 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInvoiceDetailPage(Map map) {
        PageDTO pageDTO = outinInvoiceBO.queryInvoiceDetailPage(MapUtils.get(map,"outinInvoiceDetailDO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryItemInfoByParams()
     * @Desrciption   补打获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryItemInfoByParams(Map map) {
        List<Map<String, Object>> list = outinInvoiceBO.queryItemInfoByParams(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(list);
    }

    /**
     * @Menthod queryItemInfoByParams()
     * @Desrciption   获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryItemInfoDetails(Map map) {
        List<Map<String, Object>> list = outinInvoiceBO.queryItemInfoDetails(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(list);
    }

    /**
     * @Menthod queryChangeDetails()
     * @Desrciption 获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author caoliang
     * @Date 2021/05/20 10:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
     *
     * @return*/
    @Override
    public WrapperResponse<PageDTO> queryChangeDetails(Map map) {
        PageDTO  list = outinInvoiceBO.queryChangeDetails(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(list);
    }

    /**
     * @Menthod updateForOutinInvoiceQuery
     * @Desrciption 查询发票段信息
     * @param map 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/10 11:34
     * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO>
     */
    @Override
    public WrapperResponse<List<OutinInvoiceDTO>> updateForOutinInvoiceQuery(Map map) {
        return WrapperResponse.success(outinInvoiceBO.updateForOutinInvoiceQuery(MapUtils.get(map,"outinInvoiceDTO")));
    }

    /**
     * @Menthod updatePrintChecklist()
     * @Desrciption  打印清单
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updatePrintChecklist(Map map) {
        return WrapperResponse.success(outinInvoiceBO.updatePrintChecklist(MapUtils.get(map,"outinInvoiceDTO")));
    }

    /**
     * @Menthod updateInvoicePrint()
     * @Desrciption   发票补发重打
     * @Param [map] 查询条件对象
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateInvoicePrint(Map map) {
        return WrapperResponse.success(outinInvoiceBO.updateInvoicePrint(MapUtils.get(map,"outinInvoiceDTO")));
    }

    /**
     * @Menthod queryInactiveOutinInvoices()
     * @Desrciption   获取待用发票
     * @Param [map] 查询条件对象
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<List<OutinInvoiceDTO>> queryInactiveOutinInvoices(Map map) {
        return WrapperResponse.success(outinInvoiceBO.queryInactiveOutinInvoices(MapUtils.get(map,"outinInvoiceDTO")));
    }

    /**
     * @Menthod getInvoiceDetailByInvoiceInfo
     * @Desrciption 根据发票id + 发票号码获取详情信息
     * @param map 查询条件
     * @Author 廖继广
     * @Date 2020/12/28 11:34
     * @Return java.util.map
     */
    @Override
    public WrapperResponse<OutinInvoiceDetailDO> getInvoiceDetailByInvoiceInfo(Map map) {
        return WrapperResponse.success(outinInvoiceBO.getInvoiceDetailByInvoiceInfo(map));
    }

    /**
     * @Menthod updateInvoiceDetailById
     * @Desrciption 更新发票详情
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/12/28 10:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateInvoiceDetailById(Map<String, Object> map) {
        return WrapperResponse.success(outinInvoiceBO.updateInvoiceDetailById((MapUtils.get(map,"outinInvoiceDetailDO"))));
    }

    /**
     * @Menthod updateInvoiceUseInfo
     * @Desrciption 清空发票号段使用人，状态改为待用状态
     * @param map  [crteId] 当前日结缴款人 [hospCode] 医院编码
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateInvoiceUseInfo(Map<String, Object> map) {
        return outinInvoiceBO.updateInvoiceUseInfo(map);
    }

    /**
     * @Description: 查询费用清单，例如：材料费：200.00 西药费： 890.00
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/15 10:33
     * @Return
     */
    @Override
    public WrapperResponse<OutptCostDTO> getCostInventory(Map<String, Object> map) {
        return WrapperResponse.success(outinInvoiceBO.getCostInventory(map));
    }

    /**
     * @Description: 查询处方费用明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/23 10:24
     * @Return
     */
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryOutptCFCostDetails(Map<String, Object> map) {
        List<Map<String, Object>> list = outinInvoiceBO.queryOutptCFCostDetails(MapUtils.get(map,"outinInvoiceDTO"));
        return WrapperResponse.success(list);
    }

    /**
     * @Description: 编辑发票信息（使用人，发票状态）
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/31 15:56
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateOutinInvoice(Map map) {
        Boolean flag = outinInvoiceBO.updateOutinInvoice(map);
        return WrapperResponse.success(flag);
    }

    @Override
    public WrapperResponse<Map<String, List<OutinPartInvoiceDO>>> queryPartInvoice(Map map) {
        return WrapperResponse.success(outinInvoiceBO.queryPartInvoice(MapUtils.get(map,"outinInvoiceDTO")));
    }


}
