package cn.hsa.module.insure.module.dao;


import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualBasicDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InsureIndividualBasicDAO {

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
    List<InsureIndividualBasicDTO> queryPage(InsureIndividualBasicDTO insureIndividualBasicDTO);

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
     * @Menthod insert
     * @Desrciption 医保个人基本信息保存
     * @param insureIndividualBasicDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 17:38 
     * @Return int 受影响行数
     */
    int insert(InsureIndividualBasicDO insureIndividualBasicDO);

    /**
     * @Menthod insertSelective
     * @Desrciption 医保个人基本信息保存
     * @param insureIndividualBasicDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 17:39 
     * @Return int 受影响行数
     */
    int insertSelective(InsureIndividualBasicDO insureIndividualBasicDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 根据主键修改医保个人信息
     * @param insureIndividualBasicDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 17:39 
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InsureIndividualBasicDO insureIndividualBasicDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 根据主键修改医保个人基本信息
     * @param insureIndividualBasicDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 17:39 
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InsureIndividualBasicDO insureIndividualBasicDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除医保个人基本信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/11/19 17:40 
     * @Return int 受影响行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByAac001
     * @Desrciption 根据医院编码、电脑号删除医保基本信息
     * @param param 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 20:05 
     * @Return int 受影响行数
     */
    int deleteByAac001(Map param);

    /**
     * @Method insertInsureIndividualBasic()
     * @Desrciption  新增数据
     * @Param map
     *
     * @Author liaojiguang
     * @Date   2020/11/19
     * @Return 医保个人信息数据传输对象
     **/
    int insertInsureIndividualBasic(InsureIndividualBasicDTO insureIndividualBasicDTO);

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
     * @param insureIndividualVisitDTO@Method queryInsurePatientPage
     * @Desrciption 分页查询医保病人的登记注册信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/17 14:14
     * @Return
     */
    List<InsureIndividualVisitDTO> queryInsurePatientPage(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param map
     * @Method queryInptAndOutptPatientPage
     * @Desrciption 查询门诊或者住院病人的登记信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 14:41
     * @Return
     */
    List<InsureIndividualVisitDTO> queryInptAndOutptPatientPage(Map<String, Object> map);
    /**
     * @Method getPersonInfo
     * @Desrciption
     * @Param
     *
     * @Author YUELONG.CHEN
     * @Date   2021/12/14 15:05
     * @Return
     **/
    Map<String, Object> queryPersonInfo(Map<String, Object> map);

    /**
     * @param map
     * @Description: 获取已结算人员信息 - 用于门诊已结算自费病人（大学生医保）医保直报
     * @Param:
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2021/12/15 13:59
     * @Return
     */
    List<Map<String, Object>> queryOutptSettleInfo(Map<String, Object> map);

    List<InsureIndividualVisitDTO> queryInptAndOutptMtPatientPage(Map<String, Object> map);
}
