package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.insure.insureItemMatch.bo
 * @Class_name: InsureItemMatchBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 10:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureItemMatchBO {

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:03
     * @Return java.util.List<cn.hsa.module.insure.insureItemMatch.dto.InsureItemMatchDTO>
     **/
    PageDTO queryPage(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 11:48
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     **/
    List<InsureItemMatchDTO> queryAll(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method addItemMatch
     * @Desrciption 项目生成
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:03
     * @Return java.lang.Boolean
     **/
    Boolean addItemMatch(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method deleteItemMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 11:48
     * @Return java.lang.Boolean
     **/
    Boolean deleteItemMatch(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method addDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:03
     * @Return java.lang.Boolean
     **/
    Boolean addDownload(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method queryHospItem
     * @Desrciption  展示医院端自己的项目匹配内容
     * @Param itemName:项目名称 itemCode:项目类型
     *
     * @Author fuhui
     * @Date   2021/1/27 10:13
     * @Return
     **/
    PageDTO queryHospItem(Map map);

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
    Boolean insertInsureItem(InsureItemDTO insureItemDTO);

    /**
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     *
     * @Author fuhui
     * @Date   2021/1/27 11:17
     * @Return
     **/
    PageDTO queryInsureItemAll(InsureItemDTO insureItemDTO);

    /**
     * @Method handMatch
     * @Desrciption  处理手动匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 14:25
     * @Return
     **/
    Boolean insertHandMatch(Map<String,Object> map);

    /**
     * @Method autoMatch()
     * @Desrciption  长沙医保匹配：根据名称进行匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 10:02
     * @Return
     **/
    Boolean insertAutoMatch(Map<String,Object> map);

    /**
     *
     * @Method cancelMatch
     * @Desrciption 根据项目id, 取消医保匹配
     * @Param
     * @Author fuhui
     * @Date 2021/1/30 14:10
     * @Return
     */
    Boolean deleteInsureMatch(Map<String,Object> map);

    Integer updateIsTrans(InsureItemDTO insureItemDTO);

    /**
     * @param insureItemMatchDTO
     * @Method deleteInsureItemMatch
     * @Desrciption 医保通一支付平台：删除匹配数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/28 12:50
     * @Return
     */
    Boolean deleteInsureItemMatch(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @param itemMatchDTO
     * @Method updateInsureItemMatch
     * @Desrciption 修改医保匹配项目
     * @Param
     * @Author fuhui
     * @Date 2021/4/8 16:50
     * @Return
     */
    Boolean updateInsureItemMatch(InsureItemMatchDTO itemMatchDTO);

    /**
     * @param insureItemMatchDTO
     * @Method updateUplaodInsureItem
     * @Desrciption 项目对照撤销
     * @Param
     * @Author fuhui
     * @Date 2021/4/28 10:23
     * @Return
     */
    Integer updateUplaodInsureItem(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method updateInsureItemMatchInfo
     * @Desrciption   导入医保匹配数据
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/20 05:20
     * @Return
     **/
    Boolean updateInsureItemMatchInfo(Map map);

    /**
     * @Method insertInsureItemInfos
     * @Desrciption   批量查询医保项目数据（下载）
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/25 05:20
     * @Return
     **/
    Map<String,Object> insertInsureItemInfos(Map<String,String> map);

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     */
    List<InsureItemMatchDTO> queryLimitDrugList(InsureItemMatchDTO insureItemMatchDTO);

    PageDTO queryUnMacthAllPage(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @param itemMatchDTO
     * @return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     * @method queryByHospItemId
     * @author wang'qiao
     * @date 2022/6/21 21:28
     * @description 根据hospItemId查询项目信息
     **/
    List<InsureItemMatchDTO> queryByHospItemId(InsureItemMatchDTO itemMatchDTO);
}
