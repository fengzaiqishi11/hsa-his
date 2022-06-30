package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureItemMatch.service
 * @Class_name: InsureItemMatchService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 10:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureItemMatchService {

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 10:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureItemMatch.dto.InsureItemMatchDTO>>
     **/
    @GetMapping("queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>>
     **/
    @PostMapping("queryAll")
    WrapperResponse<List<InsureItemMatchDTO>> queryAll(Map map);

    /**
     * @Method addItemMatch
     * @Desrciption 项目生成
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 10:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addItemMatch")
    WrapperResponse<Boolean>addItemMatch(Map map);

    /**
     * @Method deleteItemMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("deleteItemMatch")
    WrapperResponse<Boolean>deleteItemMatch(Map map);

    /**
     * @Method queryDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 10:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addDownload")
    WrapperResponse<Boolean>addDownload(Map map);

    /**
     * @Method queryHospItem()
     * @Desrciption  展示医院端自己的项目匹配内容
     * @Param itemName:项目名称 itemCode:项目类型
     *
     * @Author fuhui
     * @Date   2021/1/27 10:13
     * @Return
     **/
    WrapperResponse<PageDTO> queryHospItem(Map map);

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
    WrapperResponse<Boolean> insertInsureItem(Map map);

    /**
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     *
     * @Author fuhui
     * @Date   2021/1/27 11:17
     * @Return
     **/
    WrapperResponse<PageDTO> queryInsureItemAll(Map map);

    /**
     * @Method handMatch
     * @Desrciption  处理手动匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 14:25
     * @Return
     **/
    WrapperResponse<Boolean> insertHandMatch(Map<String,Object> map);

    /**
     * @Method autoMatch()
     * @Desrciption  长沙医保匹配：根据名称进行匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 10:02
     * @Return
     **/
    WrapperResponse<Boolean> insertAutoMatch(Map<String,Object> map);

    /**
     * @Method updateInsureMatch
     * @Desrciption  根据项目id,取消医保匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/30 14:10
     * @Return
     **/
    WrapperResponse<Boolean> deleteInsureMatch(Map<String,Object> map);


    /**
     * @Method uploadItem
     * @Desrciption  医保统一支付平台;项目对照上传
     * @Param insureItemDTO
     *
     * @Author fuhui
     * @Date   2021/3/15 17:21
     * @Return Boolean
     **/
    WrapperResponse<Integer> uploadItem(Map<String, Object> map);

    /**
     * @Method deleteInsureItemMatch
     * @Desrciption  医保通一支付平台：删除匹配数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/28 12:50
     * @Return
    **/
    WrapperResponse<Boolean> deleteInsureItemMatch(Map<String, Object> map);

    /**
     * @Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照撤销
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     **/
    WrapperResponse<Boolean> deleteInsureItem(Map<String, Object> map);

    /**
     * @Method updateInsureItemMatch
     * @Desrciption   修改医保匹配项目
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/8 16:50
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureItemMatch(Map<String, Object> map);

    /**
     * @Method updateUplaodInsureItem
     * @Desrciption  项目对照撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/28 10:23
     * @Return
     **/
    WrapperResponse<Integer> updateUplaodInsureItem(Map<String, Object> map);

    /**
     * @Method updateInsureItemMatchInfo
     * @Desrciption   导入医保匹配数据
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/20 05:20
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureItemMatchInfo(Map map);

    /**
     * @Method insertInsureItemInfos
     * @Desrciption   获取医保项目数据
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/25 05:20
     * @Return
     **/
    WrapperResponse<Map<String,Object>> insertInsureItemInfos(Map<String,String> map);

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: paramMap
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     */
    @GetMapping("/service/insure/insureItemMatch/queryLimitDrugList")
    WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(Map paramMap);

    WrapperResponse<PageDTO> queryUnMacthAllPage(Map<String, Object> selectItemMap);

    /**
      * @method queryByHospItemId
      * @author wang'qiao
      * @date 2022/6/21 21:28
      *	@description 	根据hospItemId查询项目信息
      * @param  selectItemMap
      * @return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
      *
     **/
    List<InsureItemMatchDTO> queryByHospItemId(Map<String, Object> selectItemMap);
}
