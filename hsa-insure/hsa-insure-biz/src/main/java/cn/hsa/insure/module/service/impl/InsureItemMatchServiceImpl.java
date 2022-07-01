package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureItemMatchBO;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.insureItemMatch.service.impl
 * @Class_name: InsureItemMatchServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 11:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureItemMatch")
@Service("insureItemMatchService_provider")
public class InsureItemMatchServiceImpl extends HsafService implements InsureItemMatchService {

    @Resource
    InsureItemMatchBO insureItemMatchBO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:09
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureItemMatch.dto.InsureItemMatchDTO>>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(insureItemMatchBO.queryPage(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>>
     **/
    @Override
    public WrapperResponse<List<InsureItemMatchDTO>> queryAll(Map map) {
        return WrapperResponse.success(insureItemMatchBO.queryAll(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @Method addItemMatch
     * @Desrciption 项目生成
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:09
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> addItemMatch(Map map) {
        return WrapperResponse.success(insureItemMatchBO.addItemMatch(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @Method deleteItemMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteItemMatch(Map map) {
        return WrapperResponse.success(insureItemMatchBO.deleteItemMatch(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @Method addDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:09
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> addDownload(Map map) {
        return WrapperResponse.success(insureItemMatchBO.addDownload(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @Method queryHospItem()
     * @Desrciption  展示医院端自己的项目匹配内容
     * @Param itemName:项目名称 itemCode:项目类型
     *
     * @Author fuhui
     * @Date   2021/1/27 10:13
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryHospItem(Map map) {
        return WrapperResponse.success(insureItemMatchBO.queryHospItem(map));
    }

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
    @Override
    public WrapperResponse<Boolean> insertInsureItem(Map map) {
        InsureItemDTO insureItemDTO = MapUtils.get(map, "insureItemDTO");
        return WrapperResponse.success(insureItemMatchBO.insertInsureItem(insureItemDTO));
    }

    /**
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     *
     * @Author fuhui
     * @Date   2021/1/27 11:17
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureItemAll(Map map) {
        InsureItemDTO insureItemDTO = MapUtils.get(map, "insureItemDTO");
        return WrapperResponse.success(insureItemMatchBO.queryInsureItemAll(insureItemDTO));
    }

    /**
     * @Method handMatch
     * @Desrciption  处理手动匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 14:25
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> insertHandMatch(Map<String,Object> map) {
        return WrapperResponse.success(insureItemMatchBO.insertHandMatch(map));
    }

    /**
     * @param map
     * @Method autoMatch()
     * @Desrciption 长沙医保匹配：根据名称进行匹配
     * @Param
     * @Author fuhui
     * @Date 2021/1/27 10:02
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertAutoMatch(Map<String,Object> map) {
        return WrapperResponse.success(insureItemMatchBO.insertAutoMatch(map));
    }

    /**
     * @Method updateInsureMatch
     * @Desrciption 根据项目id, 取消医保匹配
     * @Param
     * @Author fuhui
     * @Date 2021/1/30 14:10
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureMatch(Map<String,Object> map) {
        return WrapperResponse.success(insureItemMatchBO.deleteInsureMatch(map));
    }

    /**
     * @param map
     * @Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照上传
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Integer> uploadItem(Map<String, Object> map) {
        InsureItemDTO insureItemDTO =MapUtils.get(map,"insureItemDTO");
        return WrapperResponse.success(insureItemMatchBO.updateIsTrans(insureItemDTO));
    }

    /**
     * @param map
     * @Method deleteInsureItemMatch
     * @Desrciption 医保通一支付平台：删除匹配数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/28 12:50
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureItemMatch(Map<String, Object> map) {
        return WrapperResponse.success(insureItemMatchBO.deleteInsureItemMatch(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @param map
     * @Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照撤销
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureItem(Map<String, Object> map) {
        return null;
    }

    /**
     * @param map
     * @Method updateInsureItemMatch
     * @Desrciption 修改医保匹配项目
     * @Param
     * @Author fuhui
     * @Date 2021/4/8 16:50
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureItemMatch(Map<String, Object> map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map,"insureItemMatchDTO");
        return WrapperResponse.success(insureItemMatchBO.updateInsureItemMatch(itemMatchDTO));
    }

    /**
     * @param map
     * @Method updateUplaodInsureItem
     * @Desrciption 项目对照撤销
     * @Param
     * @Author fuhui
     * @Date 2021/4/28 10:23
     * @Return
     */
    @Override
    public WrapperResponse<Integer> updateUplaodInsureItem(Map<String, Object> map) {
        return WrapperResponse.success(insureItemMatchBO.updateUplaodInsureItem(MapUtils.get(map,"insureItemMatchDTO")));
    }

    /**
     * @Method updateInsureItemMatchInfo
     * @Desrciption   导入医保匹配数据
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/20 05:20
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> updateInsureItemMatchInfo(Map map) {
        return WrapperResponse.success(insureItemMatchBO.updateInsureItemMatchInfo(map));
    }

    /**
     * @Method insertInsureItemInfos
     * @Desrciption   批量查询医保项目数据（下载）
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/25 05:20
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String,Object>> insertInsureItemInfos(Map map) {
        return WrapperResponse.success(insureItemMatchBO.insertInsureItemInfos(map));
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     */
    @Override
    public WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(Map paramMap) {
        return WrapperResponse.success(insureItemMatchBO.queryLimitDrugList(MapUtils.get(paramMap, "insureItemMatchDTO")));
    }

    @Override
    public WrapperResponse<PageDTO> queryUnMacthAllPage(Map<String, Object> selectItemMap) {
        return WrapperResponse.success(insureItemMatchBO.queryUnMacthAllPage(MapUtils.get(selectItemMap, "insureItemMatchDTO")));
    }

    /**
     * @param selectItemMap
     * @return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     * @method queryByHospItemId
     * @author wang'qiao
     * @date 2022/6/21 21:28
     * @description 根据hospItemId查询项目信息
     **/
    @Override
	public List<InsureItemMatchDTO> queryByHospItemId(Map<String, Object> selectItemMap) {
		return insureItemMatchBO.queryByHospItemId(MapUtils.get(selectItemMap, "insureItemMatchDTO"));
	}
}
