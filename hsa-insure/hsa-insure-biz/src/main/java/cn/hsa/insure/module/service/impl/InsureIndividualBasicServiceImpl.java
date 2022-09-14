package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureIndividualBasicBO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureIndividualBasicService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.insureIndividualBasic.service.impl
 * @Class_name:: InsureIndividualBasicServiceImpl
 * @Description:
 * @Author: 马荣
 * @Date: 2020/10/13　10点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureIndividualBasic")
@Service("insureIndividualBasicService_provider")
public class InsureIndividualBasicServiceImpl extends HsafService implements InsureIndividualBasicService {

    @Resource
    private InsureIndividualBasicBO insureIndividualBasicBO;


    /**
     * @Method queryAll
     * @Param [map]
     * @description   数据库查询患者信息
     * @author marong
     * @date 2020/11/4 20:47
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
     * @throws
     */
    @Override
    public WrapperResponse<List<InsureIndividualBasicDTO>> queryAll(Map map) {
        return WrapperResponse.success(insureIndividualBasicBO.queryAll(MapUtils.get(map,"insureIndividualBasicDTO")));
    }



    /**
     * @Method getPatientsInfoByInpt
     * @Param [map]
     * @description   住院查询医保个人信息
     * @author marong
     * @date 2020/11/4 20:50
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
     * @throws
     */
    @Override
    public WrapperResponse<Map<String,Object>> getPatientsInfoByInpt(Map map) {
        Map<String,Object> resultMap= insureIndividualBasicBO.getPatientsInfoByInpt(MapUtils.get(map, "insureIndividualBasicDTO"));
        return WrapperResponse.success(resultMap);
    }

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 分页的医保个人信息数据传输对象
     **/
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map,"basicDTO");
        return WrapperResponse.success(insureIndividualBasicBO.queryPage(insureIndividualBasicDTO));
    }

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 医保个人信息数据传输对象
     **/
    @Override
    public WrapperResponse<InsureIndividualBasicDTO> getById(Map map) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map,"basicDTO");
        return WrapperResponse.success(insureIndividualBasicBO.getById(insureIndividualBasicDTO));
    }

    /**
     * @Method insertInsureIndividualBasic()
     * @Desrciption  新增数据
     * @Param map
     *
     * @Author liaojiguang
     * @Date   2020/11/19
     * @Return 医保个人信息数据传输对象
     **/
    @Override
    public WrapperResponse<Boolean> insertInsureIndividualBasic(Map map) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map,"insureIndividualBasicDTO");
        return WrapperResponse.success(insureIndividualBasicBO.insertInsureIndividualBasic(insureIndividualBasicDTO));
    }

    @Override
    public WrapperResponse<InsureIndividualBasicDTO> getByVisitId(Map map) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map,"insureIndividualBasicDTO");
        return WrapperResponse.success(insureIndividualBasicBO.getByVisitId(insureIndividualBasicDTO));
    }

    /**
     * @param map
     * @Method deleteInsureBasic
     * @Desrciption 根据id删除医保基本信息表
     * @Param
     * @Author fuhui
     * @Date 2021/5/8 9:21
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureBasic(Map<String, Object> map) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map,"insureIndividualBasicDTO");
        return WrapperResponse.success(insureIndividualBasicBO.deleteInsureBasic(insureIndividualBasicDTO));
    }

    /**
     * @param map
     * @Method queryInsurePatientPage
     * @Desrciption 分页查询医保病人的登记注册信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/17 14:14
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsurePatientPage(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");

        return WrapperResponse.success(insureIndividualBasicBO.queryInsurePatientPage(insureIndividualVisitDTO));
    }

    /**
     * @param map
     * @Method queryInptAndOutptPatientPage
     * @Desrciption 查询门诊或者住院病人的登记信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 14:41
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryInptAndOutptPatientPage(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualBasicBO.queryInptAndOutptPatientPage(map));
    }

    /**
     * @param map
     * @Method queryInsureInfo
     * @Desrciption 通过入院或者门诊登记的个人信息 用身份证或者调用医保接口获取个人编号 通过个人编好调用医保查询接口
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 15:05
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryInsureInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualBasicBO.queryInsureInfo(map));
    }
    /**
     * @Method getPersonInfo
     * @Desrciption
     * @Param
     *
     * @Author YUELONG.CHEN
     * @Date   2021/12/14 15:05
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryPersonInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualBasicBO.queryPersonInfo(map));
    }

    /**
     * @param map
     * @Description: 获取已结算人员信息 - 用于门诊已结算自费病人（大学生医保）医保直报
     * @Param:
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2021/12/15 13:59
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryOutptSettleInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualBasicBO.queryOutptSettleInfo(map));
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @method signIn
     * @author powersi
     * @date 2022/9/13 19:29
     * @description 签到
     **/
    @Override
    public WrapperResponse signIn(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualBasicBO.signIn(map));
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @method signIn
     * @author powersi
     * @date 2022/9/13 19:29
     * @description 签退
     **/
    @Override
    public WrapperResponse signOut(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualBasicBO.signOut(map));
    }
}
