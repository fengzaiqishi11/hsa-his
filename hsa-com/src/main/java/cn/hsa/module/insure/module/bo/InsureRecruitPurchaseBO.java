package cn.hsa.module.insure.module.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.stock.entity.InsureInventoryStockUpdate;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseBO
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureRecruitPurchaseBO {
    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    PageDTO queryAll(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

    /**
     * @Method insertinvChgMedinsInfo
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    void insertinvChgMedinsInfo();
    /**
     * @Method queryAll
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    void updateinvChgMedinsInfo();
    /**
     * @Method queryCommoditySalesRecord
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    Map<String,Object> queryCommoditySalesRecord(Map<String,Object> map);
    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    void insertCommoditySalesReturnRecord();


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
    PageDTO queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

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
    PageDTO queryItemList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售列表查询【8503】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: list
     **/
    PageDTO queryDrugSells(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售【8504】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    Boolean addDrugSells(Map<String, Object> map);

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售退货【8505】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    Boolean deleteDrugSells(Map<String, Object> map);
    /**
     * @param paramMap
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 接口连通性测试
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 9:43
     * @Return
     */
    Map<String,Object> selectCommonInterfaceTest(Map<String, Object> paramMap);

    /**
     * @param hospCode
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 接口连通性测试
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 9:43
     * @Return
     */
    Map<String, Object> getToken(Map<String, Object> map);
    String getToken(String hospCode,String insureRegCode);
    /**
     * @Meth: uploadToInsure
     * @Description: 药品库存上传变更
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/10/20
     */
    Boolean updateToInsure(Map<String, Object> map);

    /**
     * 海南-查询药品库存变更信息
     *
     * @param insureInventoryStockUpdate
     * @return
     */
    List<InsureInventoryStockUpdate> queryYpInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate);

}
