package cn.hsa.inpt.inregister.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.bo.InptBabyBO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inregister.bo.InptVisitBO;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inregister.service.impl
 * @Class_name: InRegisetrtServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/8 15:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/inpt/visit")
@Service("inptVisitService_provider")
public class InptVisitServiceImpl extends HsafService implements InptVisitService {

    @Resource
    private InptVisitBO inptVisitBO;

    @Resource
    private InptBabyBO inptBabyBO;

    /**
     * @Method queryRegisteredPage
     * @Desrciption 查询已就诊的记录
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryRegisteredPage(Map map) {
        return WrapperResponse.success(inptVisitBO.queryRegisteredPage(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Method queryUnregisteredPage
     * @Desrciption 查询未就诊但门诊需要转入院的记录
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryUnregisteredPage(Map map) {
        return WrapperResponse.success(inptVisitBO.queryUnregisteredPage(MapUtils.get(map,"outptVisitDTO")));
    }

    /**
     * @Method save
     * @Desrciption 新增/编辑入院登记
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<String> save(Map map) {
        return WrapperResponse.success(inptVisitBO.save(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Method queryByCertNo
     * @Desrciption 新增/编辑入院登记
     * @Param
     * [String]
     * @Author caoliang
     * @Date   2021/6/2 9:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<InptVisitDTO> queryByCertNo(Map map) {
        return WrapperResponse.success(inptVisitBO.queryByCertNo(MapUtils.get(map,"inptVisitDTO")));
    }
    /**
     * @Method queryPrintInpt
     * @Desrciption 查询打印住院证
     * @Param [OutptVisitDTO]
     * @Author yuelong.chen
     * @Date 2021/11/22 16:08
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<OutptVisitDTO> queryPrintInpt(Map map) {
        return WrapperResponse.success(inptVisitBO.queryPrintInpt(map));
    }

    /**
     * @Method deleteRegister
     * @Desrciption 取消入院登记
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteRegister(Map map) {
        return WrapperResponse.success(inptVisitBO.deleteRegister(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Method getInptVisitById()
     * @Desrciption  根据就诊id 查询病人的参保机构 进而用来判断病人是否是异地就医
     * @Param vsisitId:就诊id
     *
     * @Author fuhui
     * @Date   2020/11/12 15:12
     * @Return
     **/
    @Override
    @HsafRestPath(value = "/getInptVisitById", method = RequestMethod.GET)
    public WrapperResponse<InptVisitDTO> getInptVisitById(Map map) {
        return WrapperResponse.success(inptVisitBO.getInptVisitById(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Method deleteInsureRegister
     * @Desrciption  取消医保入院登记
     * @Param [map]
     * @Author 廖继广
     * @Date   2020/12/21 18:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteInsureRegister(Map map) {
        return WrapperResponse.success(inptVisitBO.deleteInsureRegister(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Method saveInsureInRegister
     * @Desrciption 医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveInsureInRegister(Map map) {
        return WrapperResponse.success(inptVisitBO.saveInsureInRegister(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Description: 获取选择的农合患者信息保存到医院本地数据库
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/19 19:25
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> saveNHInsureData(Map map) {
        return WrapperResponse.success(inptVisitBO.saveNHInsureData(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Description: 获取当前医院全部药品、材料、项目
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/21 19:49
     * @Return
     */
	@Override
	public WrapperResponse<Boolean> saveNHInsureDrugItemData(Map map) {
		return WrapperResponse.success(inptVisitBO.saveNHInsureDrugItemData(map));
	}

    /**
     * @Method queryInsureRegisterPage
     * @Desrciption 获取医保登记人员信息 - 已登记人员
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/22 14:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureRegisterPage(Map map) {
        return WrapperResponse.success(inptVisitBO.queryInsureRegisterPage(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @Menthod saveInsureInRegister_Dz
     * @Desrciption  电子凭证医保登记
     * @param map
     * @Author xingyu.xie
     * @Date   2021/3/1 14:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveInsureInRegisterEMD(Map map) {
        return WrapperResponse.success(inptVisitBO.saveInsureInRegisterEMD(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @param paramMap
     * @return PageDTO
     * @Menthod queryPatients
     * @Desrciption 在院病人信息查询
     * @Author pengbo
     * @Date 2021/3/5 14:45
     **/
    @Override
    public WrapperResponse<PageDTO> queryPatients(Map<String, Object> paramMap) {
        return WrapperResponse.success(inptVisitBO.queryPatients(paramMap));
    }

    /**
     * @param map
     * @Description: 查询当前科室病人以及以前转科的病人用于退费管理查询，
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryInptVisitAndZkPage(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = inptVisitBO.queryInptVisitAndZkPage(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    /* *//**
     * @Method: inRegisterByArtificial
     * @Description: 手工登记入院
     * @Param: map
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2021/3/18 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **//*
    @Override
    public WrapperResponse<Boolean> inRegisterByArtificial(Map<String, Object> map) {
        return WrapperResponse.success(inptVisitBO.inRegisterByArtificial(MapUtils.get(map,"inptVisitDTO")));
    }*/

    /**
     * @param paramMap
     * @return List<InptBabyDTO>
     * @Menthod getBabyInfoByVisitId
     * @Desrciption 根据visitId查询婴儿信息
     * @Author liuliyun
     * @Date 2021/5/14 10:47
     **/
    @Override
    public WrapperResponse<List<InptBabyDTO>> getBabyInfoByVisitId(Map<String, Object> paramMap) {
        InptBabyDTO inptBabyDTO=new InptBabyDTO();
        inptBabyDTO.setHospCode((String) paramMap.get("hospCode"));
        inptBabyDTO.setVisitId((String) paramMap.get("visitId"));
        return WrapperResponse.success(inptBabyBO.findByCondition(inptBabyDTO));
    }

    @Override
    public WrapperResponse<Boolean> updateUplod(Map<String, Object> map) {
        return WrapperResponse.success(inptVisitBO.updateUplod(MapUtils.get(map, "inptVisitDTO")));
    }
}
