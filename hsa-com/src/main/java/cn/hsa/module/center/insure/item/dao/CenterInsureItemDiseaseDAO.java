package cn.hsa.module.center.insure.item.dao;

import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  中心端医保项目疾病相关数据库访问层
 * @author luonianxin
 */
public interface CenterInsureItemDiseaseDAO {
    /**
     * @Method selectLatestVer
     * @Desrciption  查询下载数据的最后一次信息，包括版本号  分页数据
     *
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/4 15:21
     * @Return
     **/
    InsureDiseaseDTO selectCenterInsureDiseaseLatestVer(Map<String, Object> map);

    /**
     *  医保统一支付平台：下载接口 获取最新的版本号
     * @Param map
     * @Author fuhui
     * @Date   2021/4/10 1:02
     * @return cn.hsa.module.insure.module.dto.InsureItemDTO
     **/

    InsureItemDTO selectCenterInsureItemLatestVer(Map<String, Object> map);

    /**
     * @Method addCsDownLaod
     * @Desrciption   长沙医保下载：根据项目编码，开始时间下载数据
     * @Param  startDate:开始时间 也是医保端那边已经处理好匹配数据，如果时间选择当天或者最近，有可能返回空数据
     *         downLoadType:下载类型：01药品目录 02：诊疗项目信息 03：医疗服务设施信息
     *         04：费用类别信息 05：病种信息 06：项目对照信息  07：病种分型信息
     *
     * @Author fuhui
     * @Date   2021/1/27 10:30
     * @Return
     **/
    Boolean insertCenterInsureItem(@Param("list") List<InsureItemDTO> list);
    /**
     *  插入中心端医保疾病表
     *
     * @Author fuhui
     * @Date   2021/1/28 11:14
     * @return java.lang.Integer
     **/
    Integer insertCenterDisease(@Param("list")List <InsureDiseaseDTO> insureDiseaseDTOList);

    /**
     *  分页查询医保项目信息
     * @Author fuhui
     * @Date   2021/1/27 11:12
     * @return java.util.List
     **/
    List<InsureItemDTO> queryCenterInsureItemPage(InsureItemDTO insureItemDTO);


    /**
     * queryCenterInsureDiseasePage
     *   查询从医保下载回来的疾病信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 21:32
     * @return java.util.List
     **/
    List<InsureDiseaseDTO> queryCenterInsureDiseasePage(InsureDiseaseDTO insureDiseaseDTO);
}
