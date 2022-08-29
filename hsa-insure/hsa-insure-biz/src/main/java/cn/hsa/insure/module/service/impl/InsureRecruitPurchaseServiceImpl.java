package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureRecruitPurchaseBO;
import cn.hsa.module.insure.module.service.InsureRecruitPurchaseService;
import cn.hsa.module.insure.stock.entity.InsureGoodBuy;
import cn.hsa.module.insure.stock.entity.InsureInventoryStockUpdate;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseServiceImpl
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureRecruitPurchase")
@Service("insureRecruitPurchaseService_provider")
public class InsureRecruitPurchaseServiceImpl extends HsafService implements InsureRecruitPurchaseService {

    @Resource
    private InsureRecruitPurchaseBO insureRecruitPurchaseBO;

    /**
     * 获取当前医院库存列表
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryAll(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryAll(MapUtils.get(map,"insureRecruitPurchaseDTO")));
    }

    @Override
    public WrapperResponse<Map<String, Object>> queryCommoditySalesRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryCommoditySalesRecord(map));
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
    @Override
    public WrapperResponse<PageDTO> queryPersonList(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryPersonList(MapUtils.get(map, "insureRecruitPurchaseDTO")));
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
    @Override
    public WrapperResponse<PageDTO> queryItemList(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryItemList(MapUtils.get(map, "insureRecruitPurchaseDTO")));
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
    @Override
    public WrapperResponse<PageDTO> queryDrugSells(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryDrugSells(MapUtils.get(map, "insureRecruitPurchaseDTO")));
    }

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售【8504】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> addDrugSells(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.addDrugSells(map));
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
    @Override
    public WrapperResponse<Boolean> deleteDrugSells(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.deleteDrugSells(map));
    }

    /**
     * @param paramMap
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 接口连通性测试
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 9:43
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> selectCommonInterfaceTest(Map<String, Object> paramMap) {
        return WrapperResponse.success(insureRecruitPurchaseBO.selectCommonInterfaceTest(paramMap));
    }

    /**
     * @param paramMap
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 接口连通性测试
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 9:43
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> getToken(Map<String, Object> paramMap) {
        return WrapperResponse.success(insureRecruitPurchaseBO.getToken(paramMap));
    }
    /**
     * @Meth: uploadToInsure
     * @Description: 药品库存上传变更
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/10/20
     */
    @Override
    public WrapperResponse<Boolean> updateToInsure(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.updateToInsure(map));

    }
    /**
     * 海南-查询药品库存变更信息
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryYpInsureInventoryStockUpdatePage(Map<String, Object> map) {
        InsureInventoryStockUpdate insureInventoryStockUpdate = MapUtils.getEmptyErr(map,"insureInventoryStockUpdate","查询药品库存变更出错!");
        return WrapperResponse.success(PageDTO.of(insureRecruitPurchaseBO.queryYpInsureInventoryStockUpdatePage(insureInventoryStockUpdate)));
    }

}
