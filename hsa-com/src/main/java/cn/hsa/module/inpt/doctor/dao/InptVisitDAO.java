package cn.hsa.module.inpt.doctor.dao;

import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 *@Package_name: cn.hsa.module.inpt.dao
 *@Class_name: InptVisitDAO
 *@Describe: 住院就诊信息
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-04 09:49:54
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptVisitDAO {

    /**
    * @Method getInptVisitById
    * @Desrciption 单个查询
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:16
    * @Return cn.hsa.module.inpt.doctor.dto.InptVisitDTO
    **/
        InptVisitDTO getInptVisitById(InptVisitDTO inptVisitDTO);

    /**
    * @Method queryInptVisitPage
    * @Desrciption 分页查询
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:16
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    List<InptVisitDTO> queryInptVisitPage(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod queryInptVisitPageByMoney
    * @Desrciption  根据金额预警线分页查询患者信息 住院催款用
     * @param inptVisitDTO
    * @Author xingyu.xie
    * @Date   2020/11/7 11:08
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    List<InptVisitDTO> queryInptVisitPageByMoney(InptVisitDTO inptVisitDTO);

    List<InptVisitDTO> queryInptVisitPageS(InptVisitDTO inptVisitDTO);

    /**
    * @Method insertInptVisit
    * @Desrciption 单个新增
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:16
    * @Return int
    **/
    int insertInptVisit(InptVisitDTO inptVisitDTO);

    /**
    * @Method updateInptVisit
    * @Desrciption 单个修改
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:16
    * @Return int
    **/
    int updateInptVisit(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryOutptVisitAll
     * @Desrciption 查询门诊就诊中开了住院证的数据
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/10/29 11:48
     * @Return java.util.List<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
     **/
    List<OutptVisitDTO> queryOutptVisitAll(OutptVisitDTO outptVisitDTO);

    /**
     * @Method deleteById
     * @Desrciption 取消入院登记
     * @Param
     * [inptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/24 16:43
     * @Return java.lang.Boolean
     **/
    Integer deleteById(InptVisitDTO inptVisitDTO);

    /**
     * @Method: getVisitByAdviceId
     * @Description: 通过医嘱ID获取住院病人信息
     * @Param: [adviceIds, hospCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **/
    List<InptVisitDTO> getVisitByAdviceId(List<String> adviceIds, String hospCode);

    /**
     * @Method: updateInptVisitAmount
     * @Description: 回写就诊表金额，余额
     * @Param: [inptVisitDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:47
     * @Return: Boolean
     **/
    int updateInptVisitAmount(@Param("visitDTOList") List<InptVisitDTO> visitDTOList);

    /**
     * @Menthod queryInptVisitList
     * @Desrciption  查询可结算住院患者信息
     * @param inptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/25 16:43
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     */
    List<InptVisitDTO> queryInptVisitList(InptVisitDTO inptVisitDTO);

    /**
     * @Method updateInptVisitZk
     * @Desrciption 转科修改方法
       @params [inptVisitDTO]
     * @Author chenjun
     * @Date   2020/10/13 8:42
     * @Return void
    **/
    void updateInptVisitZk(InptVisitDTO inptVisitDTO);

    /**
     * @Method insertDiagnose
     * @Desrciption 新增住院诊断
     * @Param
     * [inptDiagnoseDTO]
     * @Author liaojunjie
     * @Date   2020/11/2 9:49
     * @Return java.lang.Boolean
     **/
    Boolean insertDiagnose(InptDiagnoseDTO inptDiagnoseDTO);

    /**
     * @Method getDeptCodeByDeptId
     * @Desrciption 获取科室编码
     * @Param
     * [selectMap]
     * @Author 廖继广
     * @Date   2020/11/3 9:23
     * @Return BaseDeptDTO
     **/
    BaseDeptDTO getDeptCodeByDeptId(Map<String, Object> selectMap);

    /**
     * @Method getUserInfoById
     * @Desrciption 获取用户信息
     * @Param
     * [selectMap]
     * @Author 廖继广
     * @Date   2020/11/3 9:23
     * @Return SysUserDTO
     **/
    SysUserDTO getUserInfoById(Map<String, Object> selectMap);

    /**
     * @Method queryByCertNo
     * @Desrciption 获取用户信息
     * @Param
     * [String]
     * @Author caoliang
     * @Date   2021/6/1 15:28
     * @Return InptVisitDTO
     **/
    InptVisitDTO queryByCertNo(InptVisitDTO inptVisitDTO);

    /**
     * @Method insertInsureIndividualVisit
     * @Desrciption 新增医保个人就诊信息
     * @Param insureIndividualVisitDTO
     * @Author 廖继广
     * @Date   2020/11/3 14:07
     * @Return 影响行数
     **/
    int insertInsureIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method getInsureInptRegisterInfo
     * @Desrciption 获取医保入院登记信息
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    InsureIndividualVisitDTO getInsureIndividualVisitInfo(InptVisitDTO inptVisitDTO);

    /**
     * @Method updateInsureIndividualVisit
     * @Desrciption 更新医保个人就诊信息表
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    int updateInsureIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method getInsureIndividualSettleInfo
     * @Desrciption 获取医保结算信息
     * @Param [insureIndividualSettleDTO]
     * @Author 廖继广
     * @Date 2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    InsureIndividualSettleDTO getInsureIndividualSettleInfo(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @Method updateInsureIndividualSettle
     * @Desrciption 更新医保结算信息表
     * @Param [selectEntity]
     * @Author 廖继广
     * @Date 2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    int updateInsureIndividualSettle(InsureIndividualSettleDTO selectEntity);

    /**
     * @Method insertInsureIndividualSettle
     * @Desrciption 新增医保结算信息表
     * @Param [selectEntity]
     * @Author 廖继广
     * @Date 2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    int insertInsureIndividualSettle(InsureIndividualSettleDTO selectEntity);

    /**
    * @Method updateTotalCost
    * @Desrciption 更新患者的合计费用
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/11/5 14:27
    * @Return int
    **/
    int updateTotalCost(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptVisitById()
     * @Desrciption  根据就诊id 查询医保病人的医保登记号 医疗机构编码
     * @Param id:就诊id hospCode:医院编码
     *
     * @Author fuhui
     * @Date   2020/11/16 16:17
     * @Return  InptVisitDTO 住院病人数据传输对象
     **/
    InptVisitDTO queryInptVisitById(InptVisitDTO inptVisitDTO);

    /**
     * @Method deleteInsureInptRegisterByParams
     * @Desrciption  删除医保就诊信息
     * @Param id inptVisitDTO
     * @Author liaojiguang
     * @Date   2020/11/17 16:34
     * @Return  inptVisitDTO 就诊信息
     **/
    Boolean deleteInsureInptRegisterByParams(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method queryInsureRegisterPage
     * @Desrciption 获取医保登记人员信息
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/22 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    List<InptVisitDTO> queryInsureRegisterPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInsureUnRegisterPage
     * @Desrciption 医保登记人员信息获取 - 未登记人员
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/22 14:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    List<InptVisitDTO> queryInsureUnRegisterPage(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod updateInsureIndividualCostBySettleId
     * @Desrciption 根据结算id修改医保结算信息
     * @param settleId 结算id
     * @Author Ou·Mr
     * @Date 2020/12/28 18:55
     * @Return int 受影响行数
     */
    int updateInsureIndividualCostBySettleId(@Param("settleId") String settleId);

    /**
     * @Menthod queryPatients
     * @Desrciption  在院病人信息查询
     * @param paramMap
     * @Author pengbo
     * @Date   2021/3/5 14:45
     * @return PageDTO
     **/
    List<Map<String, Object>> queryPatients(Map<String, Object> paramMap);

    /**
     * @Description: 查询住院患者是农合医保的患者信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/20 9:50
     * @Return
     */
	List<Map<String, Object>> selectNHPatientData(InptVisitDTO inptVisitDTO);

	/**
	 * @Description: 查询农合医保住院病人费用数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/4/20 13:41
	 * @Return
	 */
    List<Map<String, Object>> selectNHPatientCostData(InptVisitDTO inptVisitDTO);

    /**
     * @Description: 查询当前医院药品、材料、项目数据
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/21 19:53
     * @Return
     */
	List<Map<String, Object>> saveNHInsureDrugItemData(Map map);

	List<Map<String, Object>> saveMaterialData(Map map);

	List<Map<String, Object>> saveDrugData(Map map);

	List<Map<String, Object>> saveItemData(Map map);
    /**
     * @Description: 查询当前病人的最小记费时间
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    InptCostDTO getMinTimeCostByVisitId(InptVisitDTO inptVisitDTO);
    /**
     * @Description: 查询当前病人的最小医嘱开始时间
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    InptAdviceDTO getMinTimeInptAdviceByVisitId(InptVisitDTO inptVisitDTO);

    /**
     * @Description: 查询当前科室病人以及以前转科的病人用于退费管理查询，
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    List<InptVisitDTO> queryInptVisitAndZkPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryBabyInptVisitPage
     * @Desrciption 分页查询
     * @param inptVisitDTO
     * @Author liuliyun
     * @Date   2021/05/25 13:35
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryBabyInptVisitPage(InptVisitDTO inptVisitDTO);

    void updateIllness(InptVisitDTO inptVisitDTO);
}
