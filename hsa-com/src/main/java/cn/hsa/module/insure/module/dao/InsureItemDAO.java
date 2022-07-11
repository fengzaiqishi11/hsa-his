package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureItem
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/1/27 10:46
 * @Company: 创智和宇
 **/
public interface InsureItemDAO {

    /**
     * @Method queryAll
     * @Desrciption:
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 11:12
     * @Return
    **/
    List<InsureItemDTO> queryInsureItemAll(InsureItemDTO insureItemDTO);

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
    Boolean insertInsureItem(@Param("list") List<InsureItemDTO>list);

    /**
     * @Method selectLatestVer
     * @Desrciption  医保统一支付平台：下载接口 获取最新的版本号
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/10 1:02
     * @Return
    **/

    InsureItemDTO selectLatestVer(Map<String, Object> map);

    /**
     * @param insureItemDTO
     * @Method queryPageUnifiedItem
     * @Desrciption 医保统一支付平台：分页查询医保下载回来的数据
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:28
     * @Return
     */
    List<InsureItemDTO> queryPageUnifiedItem(InsureItemDTO insureItemDTO);

    /**
     * @Method getById
     * @Desrciption  医保统一支付平台：根据id和医院编码查询医保下载信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/10 2:02
     * @Return
    **/
    InsureItemDTO getById(InsureItemMatchDTO itemMatchDTO);

    /**
     * @param insureItemDTO
     * @Method insertUnifiedItem
     * @Desrciption 医保统一只支付平台 新增项目新增功能
     * @Param insureItemDTO:医保项目表
     * @Author fuhui
     * @Date 2021/6/15 11:29
     * @Return
     */
    Boolean insertUnifiedItem(InsureItemDTO insureItemDTO);

    /**
     * @param insureItemDTO
     * @Method deleteInsureItemByRegCode
     * @Desrciption 清除当前医保的下载数据
     * @Param insureItemDTO:医保项目表
     * @Author LiaoJiGuang
     * @Date 2021/7/9 10:56
     * @Return
     */
    Boolean deleteInsureItemByRegCode(InsureItemDTO insureItemDTO);

    /**
     * @param insureItemMatchDTO
     * @Method getInsureItemById
     * @Desrciption 根据ID获取项目匹配信息
     * @Param
     * @Author LiaoJiGuang
     * @Date 2021/7/9 10:56
     * @Return
     */
    InsureItemDTO getInsureItemById(InsureItemMatchDTO insureItemMatchDTO);

    List<InsureItemDTO> selectLastPageList(Map<String, Object> map);

    void deleteLastPage(Map<String, Object> map);
}
