package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.insure.module.dto.PayInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.bo
 * @Class_name:: InsureGetInfoBO
 * @Description: 信息采集上传
 * @Author: zhangxuan
 * @Date: 2021-04-09 20:04
 * @Company: 创智和宇信息技术股份有限公司
 */
public interface InsureGetInfoBO {

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 11:35
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>
     **/
    Map insertSettleInfo(Map<String,Object> map);

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author yuelong.chen
     * @Date   2021-08-19 11:35
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>
     **/
    Map<String,Object> insertSetlInfo(Map<String,Object> map);

    /**
     * @Method queryInsureCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:53
     * @Return java.util.Map
     **/
    Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:22
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:22
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryVisit(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsureSettle
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsure
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryInsure(InsureSettleInfoDTO insureSettleInfoDTO);


    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    PageDTO queryPage(InsureSettleInfoDTO insureSettleInfoDTO);


    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
     **/
    PageDTO queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @param map
     * @Method saveInsureSettleInfo
     * @Desrciption 医疗保障基金结算清单信息： 保存
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 14:54
     * @Return
     */
    Map saveInsureSettleInfo(Map<String, Object> map);

    /**
     * @param map
     * @Method deleteSettleInfo
     * @Desrciption 重置医疗保障结算清单信息
     * 1.如果已经上传则不允许 清空重置
     * @Param
     * @Author fuhui
     * @Date 2021/11/4 13:58
     * @Return
     */
    Boolean deleteSettleInfo(Map<String, Object> map);

    /**
     * @Method updateSettleInfo
     * @Desrciption  修改医疗保障结算清单信息
     *
     * @Param
     *
     * @Author liuhuiming
     * @Date   2022/04/22 13:58
     * @Return
     **/
    Boolean updateSettleInfo(Map<String, Object> map);

    /**
     * @param map
     * @Method selectLoadingSettleInfo
     * @Desrciption 加载保存到数据库的数据信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/6 10:21
     * @Return
     */
    Map<String,Object> selectLoadingSettleInfo(Map<String, Object> map);

    /**
     * @Method queryPage
     * @Desrciption  查询结算清单左侧人员类别信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/9 15:29
     * @Return
     **/

    PageDTO querySetlePage(Map<String, Object> map);
    /**
     * @Author gory
     * @Description 结算清单质控DRG
     * @Date 2022/6/6 16:00
     * @Param [map]
     **/
    Map<String,Object> insertInsureSettleInfoForDRG(Map<String, Object> map);
    /**
     * @Author gory
     * @Description 结算清单质控DIP
     * @Date 2022/6/6 16:00
     * @Param [map]
     **/
    Map<String,Object> insertInsureSettleInfoForDIP(Map<String, Object> map);

    Map<String,Object>  insertInsureSettleInfoForDRGorDIP(Map<String, Object> map);

    /**
     * @Author gory
     * @Description 查询医保区划
     * @Date 2022/6/6 16:00
     * @Param [map]
     **/
    List<Map<String,Object>> queryAdmdvs(Map<String, Object> map);
}
