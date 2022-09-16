package cn.hsa.module.insure.module.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;

import java.util.List;
import java.util.Map;

public interface InsureIndividualBasicBO {

    /**
     * @Method queryAll
     * @Param [map]
     * @description   数据库查询患者信息
     * @author marong
     * @date 2020/11/4 20:47
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
     * @throws
     */
    List<InsureIndividualBasicDTO> queryAll(InsureIndividualBasicDTO insureIndividualBasicDTO);

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 分页的医保个人信息数据传输对象
     **/
    PageDTO queryPage(InsureIndividualBasicDTO insureIndividualBasicDTO);
    /**
     * @Method getPatientsInfoByInpt
     * @Param [map]
     * @description   住院查询医保个人信息
     * @author marong
     * @date 2020/11/4 20:50
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
     * @throws
     */
    Map<String,Object> getPatientsInfoByInpt(InsureIndividualBasicDTO insureIndividualBasicDTO);

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 医保个人信息数据传输对象
     **/
    InsureIndividualBasicDTO getById(InsureIndividualBasicDTO insureIndividualBasicDTO);

    /**
     * @Method insertInsureIndividualBasic()
     * @Desrciption  新增数据
     * @Param map
     *
     * @Author liaojiguang
     * @Date   2020/11/19
     * @Return 医保个人信息数据传输对象
     **/
    Boolean insertInsureIndividualBasic(InsureIndividualBasicDTO insureIndividualBasicDTO);

    InsureIndividualBasicDTO getByVisitId(InsureIndividualBasicDTO insureIndividualBasicDTO);

    /**
     * @param insureIndividualBasicDTO
     * @Method deleteInsureBasic
     * @Desrciption 根据id删除医保基本信息表
     * @Param
     * @Author fuhui
     * @Date 2021/5/8 9:21
     * @Return
     */
    Boolean deleteInsureBasic(InsureIndividualBasicDTO insureIndividualBasicDTO);

    /**
     * @param
     * @Method queryInsurePatientPage
     * @Desrciption 分页查询医保病人的登记注册信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/17 14:14
     * @Return
     */
    PageDTO queryInsurePatientPage(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param map
     * @Method queryInptAndOutptPatientPage
     * @Desrciption 查询门诊或者住院病人的登记信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 14:41
     * @Return
     */
    PageDTO queryInptAndOutptPatientPage(Map<String, Object> map);

    /**
     * @param map
     * @Method queryInsureInfo
     * @Desrciption 通过入院或者门诊登记的个人信息 用身份证或者调用医保接口获取个人编号 通过个人编好调用医保查询接口
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 15:05
     * @Return
     */
    Map<String,Object> queryInsureInfo(Map<String, Object> map);
    /**
     * @Method getPersonInfo
     * @Desrciption
     * @Param
     *
     * @Author YUELONG.CHEN
     * @Date   2021/12/14 15:05
     * @Return
     **/
    Map<String,Object> queryPersonInfo(Map<String, Object> map);

    /**
     * @param map
     * @Description: 获取已结算人员信息 - 用于门诊已结算自费病人（大学生医保）医保直报
     * @Param:
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2021/12/15 13:59
     * @Return
     */
    PageDTO queryOutptSettleInfo(Map<String, Object> map);

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @method signIn
     * @author powersi
     * @date 2022/9/13 19:29
     * @description 签退
     **/
    Map signIn(Map<String, Object> map);

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @method signIn
     * @author powersi
     * @date 2022/9/13 19:29
     * @description 签退
     **/
    Map signOut(Map<String, Object> map);
}
