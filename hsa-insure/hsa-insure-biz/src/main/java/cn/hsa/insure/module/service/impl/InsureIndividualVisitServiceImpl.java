package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.bo.InsureIndividualVisitBO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InsureIndividualVisitServiceImpl
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/18 20:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/insureIndividualVisit")
@Service("insureIndividualVisitService_provider")
public class InsureIndividualVisitServiceImpl extends HsafService implements InsureIndividualVisitService {


    @Resource
    private InsureIndividualVisitBO insureIndividualVisitBO;

    /**
     * @Menthod addInsureIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 20:16
     * @Return int 受影响行数
     */
    @Override
    public InsureIndividualVisitDO addInsureIndividualVisit(Map<String,Object> param) {
        return insureIndividualVisitBO.addInsureIndividualVisit(param);
    }

    /**
     * @Menthod findByCondition
     * @Desrciption 查询医保就诊信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/29 15:57
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     */
    @Override
    public List<InsureIndividualVisitDTO> findByCondition(Map<String, Object> param) {
        return insureIndividualVisitBO.findByCondition(MapUtils.get(param,"insureIndividualVisitDTO"));
    }

    /**
     * @Menthod editInsureIndividualVisit
     * @Desrciption 编辑医保就诊信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 14:59 
     * @Return int 受影响行数
     */
    @Override
    public int editInsureIndividualVisit(Map<String, Object> param) {
        return insureIndividualVisitBO.editInsureIndividualVisit(MapUtils.get(param,"insureIndividualVisitDO"));
    }

    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param insertMap 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    @Override
    public Boolean insertIndividualVisit(Map<String, Object> insertMap) {
        return insureIndividualVisitBO.insertIndividualVisit(MapUtils.get(insertMap,"insureIndividualVisitDTO"));
    }


    /**
     * @Menthod deleteByVisitId
     * @Desrciption 根据就诊id删除
     * @param insertMap 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    @Override
    public Boolean deleteByVisitId(Map<String, Object> insertMap) {
        return insureIndividualVisitBO.deleteByVisitId(MapUtils.get(insertMap,"visitId"));
    }
    /**
     * @param insureVisitParam
     * @Method getInsureIndividualVisitById
     * @Desrciption 根据就诊id和医院编码查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 8:58
     * @Return
     */
    @Override
    public InsureIndividualVisitDTO getInsureIndividualVisitById(Map<String, Object> insureVisitParam) {

        return insureIndividualVisitBO.getInsureIndividualVisitById(insureVisitParam);
    }

    /**
     * @param map
     * @Method deleteInsureVisitById
     * @Desrciption 退费以后，取消门诊挂号登记
     * @Param
     * @Author fuhui
     * @Date 2021/3/16 19:50
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureVisitById(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualVisitBO.deleteInsureVisitById(map));
    }

    /**
     * @param map
     * @Method updateInsureInidivdual
     * @Desrciption 修改医保病人信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/20 22:41
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureInidivdual(Map<String,Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        return WrapperResponse.success(insureIndividualVisitBO.updateInsureInidivdual(inptVisitDTO));
    }

    /**
     * @param map
     * @Method selectInsureInfo
     * @Desrciption 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/7/30 15:32
     * @Return
     */
    @Override
    public WrapperResponse<InsureIndividualVisitDTO> selectInsureInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        return WrapperResponse.success(insureIndividualVisitBO.selectInsureInfo(insureIndividualVisitDTO));
    }
}
