package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptCostDO;
import cn.hsa.module.outpt.fees.entity.OutptPrescribeDO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.dao
 * @Class_name: OutptCostDAO
 * @Describe(描述): 门诊费用Dao
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 9:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptCostDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询门诊费用信息
     * @param key 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:12
     * @Return cn.hsa.module.outpt.fees.dto.OutptCostDTO 门诊费用信息
     */
    OutptCostDTO selectByPrimaryKey(@Param("id") String key);

    /**
     * @Menthod insert
     * @Desrciption 保存门诊费用信息
     * @param outptCostDO 门诊费用参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:16
     * @Return int 受影响的行数
     */
    int insert(OutptCostDO outptCostDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存门诊费用信息（字段为 null OR '' 不会保存字段）
     * @param outptCostDO 门诊费用参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:22
     * @Return int 受影响的行数
     */
    int insertSelective(OutptCostDO outptCostDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改门诊费用信息
     * @param outptCostDO 门诊费用参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(OutptCostDO outptCostDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改门诊费用信息
     * @param outptCostDO 门诊费用参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:28
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(OutptCostDO outptCostDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除门诊费用信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:30
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除门诊费用
     * @param ids 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:33
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

	/**
	 * @Description: 删除非处方费用
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/7/21 19:20
	 * @Return
	 */
	int deleteFCFCostByIds(@Param("ids") String[] ids, String hospCode);
    /**
     * @Menthod findByCondition
     * @Desrciption 查询门诊费用信息
     * @param outptCostDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 9:38
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO> 结果集
     */
    List<OutptCostDTO> findByCondition(OutptCostDTO outptCostDTO);

    /**
     * @Menthod queryOutptVisitList
     * @Desrciption  查询门诊就诊人员信息
     * @param outptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 16:09
     * @Return java.util.List<cn.hsa.module.outpt.visit.dto.OutptVisitDTO> 结果集
     */
    List<OutptVisitDTO> queryOutptVisitList(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod queryOutptPrescribeList
     * @Desrciption  查询处方信息（是否结算 = 0、是否作废 = 0）
     * @param outptCostDTO 查询条件 （就诊id、医院编码）
     * @Author Ou·Mr
     * @Date 2020/8/25 17:03
     * @Return java.util.List<cn.hsa.module.outpt.fees.entity.OutptPrescribeDO>
     */
    List<OutptPrescribeDO> queryOutptPrescribeList(OutptCostDTO outptCostDTO);

    /**
     * @Menthod queryDrugMaterialByVisitId
     * @Desrciption  根据处方id、就诊id、医院编码、处方执行签名id不为空；查询处方费用信息
     * @param param 查询条件（处方id、就诊id、医院编码）
     * @Author Ou·Mr
     * @Date 2020/8/25 17:22
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     */
    List<OutptCostDTO> queryDrugMaterialByVisitId(Map param);

    /**
     * @Menthod queryDrugMaterialList
     * @Desrciption   分页查询医院项目、药品、材料信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/26 9:38
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     */
    List<OutptCostDTO> queryDrugMaterialList(Map param);

    /**
     * @Menthod editOutptVisitByKey
     * @Desrciption  根据主键编辑门诊就诊信息
     * @param outptVisitDTO 编辑参数
     * @Author Ou·Mr
     * @Date 2020/8/26 10:23
     * @Return int 受影响的行数
     */
    int editOutptVisitByKey(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod addOutptVisit
     * @Desrciption  保存门诊就诊患者信息
     * @param outptVisitDTO 保存参数
     * @Author Ou·Mr
     * @Date 2020/8/26 10:25
     * @Return int 受影响的行数
     */
    int addOutptVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod delOutptCostByVisitId
     * @Desrciption 删除患者费用信息
     * @param param 删除参数
     * @Author Ou·Mr
     * @Date 2020/8/26 21:10
     * @Return int 受影响的行数
     */
    int delOutptCostByVisitId(Map param);

    /**
     * @Menthod getBasePreferentialTypeList
     * @Desrciption 查询医院优惠配置
     * @param param 查询条件（医院编码、是否有效）
     * @Author Ou·Mr
     * @Date 2020/8/27 10:03
     * @Return java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO>
     */
    List<BasePreferentialTypeDTO> getBasePreferentialTypeList(Map param);

    /**
     * @Menthod getBasePreferentialList
     * @Desrciption 查询优惠类型明细
     * @param param 查询条件（医院编码、是否有效、优惠类型编码）
     * @Author Ou·Mr
     * @Date 2020/8/27 15:05
     * @Return java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
     */
    List<BasePreferentialDTO> getBasePreferentialList(Map param);

    /**
     * @Menthod batchInsert
     * @Desrciption   批量新增费用信息
     * @param outptCostDTOList 费用参数
     * @Author Ou·Mr
     * @Date 2020/8/28 11:25
     * @Return int 受影响的行数
     */
    int batchInsert(List<OutptCostDTO> outptCostDTOList);

    /**
     * @Menthod batchInsert
     * @Desrciption   批量新增费用信息2（手术补记账）
     * @param outptCostDTOList 费用参数
     * @Author Ou·Mr
     * @Date 2020/8/28 11:25
     * @Return int 受影响的行数
     */
    int batchOutptCostInsert(List<cn.hsa.module.inpt.doctor.dto.OutptCostDTO> outptCostDTOList);

    /**
    * @Menthod updateList
    * @Desrciption  批量修改费用信息
     * @param outptCostDTOList 费用信息表参数
    * @Author xingyu.xie
    * @Date   2020/8/29 10:41
    * @Return int
    **/
    int updateList(List<OutptCostDTO> outptCostDTOList);

    /**
     * @Menthod selectBySettleId
     * @Desrciption 根据结算id查询本次结算的费用信息
     * @param param  hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
     * @Author Ou·Mr
     * @Date 2020/8/31 10:50
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     */
    List<OutptCostDTO> queryBySettleId(Map param);

    /**
     * @Menthod getPahrOutReceiceInfoBySelectMap
     * @Desrciption 根据参数获取门诊发药申请表信息
     * @param selectMap  hospCode（医院编码）、visitId(就诊id)、settleId（结算id）
     * @Author lijiguang
     * @Date 2020/9/06 10:50
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
     */
    List<PharOutReceiveDTO> getPahrOutReceiceInfoBySelectMap(Map selectMap);

    /**
     * @Menthod deletePharOutReceiveById
     * @Desrciption 根据参数删除门诊发药申请主表信息
     * @param pharOutReceiveDTO
     * @Author lijiguang
     * @Date 2020/9/06 10:50
     * @Return Boolean
     */
    Boolean deletePharOutReceiveById(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Menthod deletePharOutReceiveById
     * @Desrciption 根据参数删除门诊发药申请明细表信息
     * @param pharOutReceiveDTO
     * @Author lijiguang
     * @Date 2020/9/06 10:50
     * @Return Boolean
     */
    Boolean deletePharOutReceiveDetailById(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Menthod queryPharOutReceiveDetailInfo
     * @Desrciption 根据门诊领药申请主表ID获取领药申请明细信息
     * @param pharOutReceiveDTO
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return List<PharOutReceiveDetailDTO>
     */
    List<PharOutReceiveDetailDTO> queryPharOutReceiveDetailInfo(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Menthod batchInsertPharOutReceiveDetai
     * @Desrciption 批量插入领药申请明细信息
     * @param pharOutReceiveDetailDTOList
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return Boolean
     */
    Boolean batchInsertPharOutReceiveDetail(List<PharOutReceiveDetailDTO> pharOutReceiveDetailDTOList);

    /**
     * @Menthod insetPharOutReceive
     * @Desrciption 插入领药申请主表信息
     * @param pharOutReceiveDTO
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return Boolean
     */
    Boolean insetPharOutReceive(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Menthod queryPharOutDistributeDetail
     * @Desrciption 获取发药明细表信息
     * @param
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return List<PharOutDistributeDetailDTO>
     */
    List<PharOutDistributeDetailDTO> queryPharOutDistributeDetail(PharOutDistributeDTO pharOutDistributeDTO);

    /**
     * @Menthod queryPharOutDistributeDetail
     * @Desrciption 批量插入发药明细表信息
     * @param insertLastList
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return List<PharOutDistributeDetailDTO>
     */
    Boolean batchInsertPharOutDistributeDetail(List<PharOutDistributeDetailDTO> insertLastList);

    /**
     * @Menthod insertPharOutDistribute
     * @Desrciption 插入发药主表信息
     * @param pharOutDistributeDTO
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return Boolean
     */
    Boolean insertPharOutDistribute(PharOutDistributeDTO pharOutDistributeDTO);

    /**
     * @Menthod updatePharOutDistributeDetails
     * @Desrciption 批量更新发药明细表信息
     * @param pharOutDistributeDetailList
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return Boolean
     */
    Boolean updatePharOutDistributeBatchDetails(List<PharOutDistributeAllDetailDTO> pharOutDistributeDetailList);

    /**
     * @Menthod updatePharOutDistributeDetails
     * @Desrciption 批量参数获取发药主表信息
     * @param outptSettleDTO
     * @Author lijiguang
     * @Date 2020/9/08 10:50
     * @Return PharOutDistributeDTO
     */
    PharOutDistributeDTO getPharOutDistribute(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod batchEditCostPrice
     * @Desrciption 批量修改费用表的费用信息
     * @param outptCostDTOList 费用数据
     * @Author Ou·Mr
     * @Date 2020/9/11 13:47
     * @Return int 受影响的行数
     */
    int batchEditCostPrice(List<OutptCostDTO> outptCostDTOList);

    /**
     * @Menthod editCostSettleCodeByIDS
     * @Desrciption 根据费用id批量修改费用状态信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/14 11:06
     * @Return int 受影响的行数
     */
    int editCostSettleCodeByIDS(Map param);

    /**
     * @Menthod queryPharOutDistribute
     * @Desrciption 根据费用id批量修改费用状态信息
     * @param selectMap 请求参数
     * @Author liaojiguang
     * @Date 2020/9/14 11:06
     * @Return List
     */
    List<PharOutDistributeDTO> queryPharOutDistribute(Map selectMap);


    /**
     * @Menthod queryBaseDept
     * @Desrciption   获取选择科室指定的药房科室信息
     * @param baseDeptDrugStoreDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/18 15:34
     * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     */
    List<BaseDeptDTO> queryBaseDept(BaseDeptDrugStoreDTO baseDeptDrugStoreDTO);

    /**
     * @Menthod queryDrugMaterialListByIds
     * @Desrciption 根据费用项目id查询费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/21 19:59
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     */
    List<OutptCostDTO> queryDrugMaterialListByIds(Map<String,Object> param);

    /**
     * @Menthod updateOutptPrescribeByIds
     * @Desrciption 修改处方结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/15 9:52
     * @Return int 受影响行数
     */
    int updateOutptPrescribeByIds(Map<String,Object> param);

    /**
     * @Menthod queryOutptCostSourceidNotNUll
     * @Desrciption 查询患者处方费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/11 14:35
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     */
    List<OutptCostDTO> queryOutptCostSourceidNotNUll(Map param);

    /**
     * @Menthod queryOutptCostRest
     * @Desrciption 查询非处方、非划价收费费用
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/16 10:06
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     */
    List<OutptCostDTO> queryOutptCostRest(Map param);

    /**
     * @Menthod queryShortcutWindows
     * @Desrciption 根据药房id查询最快捷发药窗口
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/17 9:28
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryShortcutWindows(Map param);

    /**
     * @Menthod queryBaseDeptById
     * @Desrciption 根据科室id查询科室名称
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/17 9:45
     * @Return java.lang.String
     */
    String queryBaseDeptById(Map param);

    /**
     * @Menthod queryOutptSettleVisitList
     * @Desrciption 查询已结算患者信息
     * @param outptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/7 22:38
     * @Return java.util.List<OutptVisitDTO>
     */
    List<OutptVisitDTO> queryOutptSettleVisitList(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod queryPharOutDistributeDetailIByList
     * @Desrciption 查询发药明细
     * @param distributeList 查询条件
     * @Author LiaoJiGuang
     * @Date 2020/12/29 9:01
     * @Return java.util.List<cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDetailDTO>
     */
    List<PharOutDistributeAllDetailDTO> queryPharOutDistributeDetailIByList(List<PharOutDistributeDTO> distributeList);

    /**
     * @Description: 根据结算id查询发药明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/27 13:40
     * @Return
     */
	List<PharOutDistributeAllDetailDTO> queryPharOutDistributeDetailIBySettleId(OutptSettleDTO outptSettleDTO);

    /**
     * @Description: 批量更新项目费用的优惠金额，用于保存自定义优惠
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 16:00
     * @Return
     */
    int updateOutptCosts(List<OutptCostDTO> list);

    /**
     * @Description: 根据就诊id查询费用信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/2 10:14
     * @Return
     */
	List<OutptCostDTO> selectByVisitId(Map<String, Object> map);

	/**
	 * @Description: 查询项目默认执行科室
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/4 18:57
	 * @Return
	 */
	String selectXMZXKS(OutptCostDTO outptCostDTO);

	/**
	 * @Description: 更新患者病人类型与优惠类别
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/9 9:02
	 * @Return
	 */
	void updateOutptVisitPreferentialType(OutptVisitDTO outptVisitDTO);

	/**
	 * @Description: 根据患者主键查询患者信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/9 9:15
	 * @Return
	 */
	OutptVisitDTO getOutptVisitById(Map param);

    /**
     * @param param 就诊id 结算id
     * @Description 根据就诊id 结算id 医院编码查询费用信息
     * @author: zibo.yuan
     * @date: 2021/3/5
     * @return: java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     **/
    List<OutptCostDTO> getOutotCost(Map param);

    /**
     * @param paramMap
     * @Description 根据处方ID 查询对应所有的处方信息
     * @author: zibo.yuan
     * @date: 2021/3/5
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     **/
    List<OutptPrescribeDTO> queryPrescribeList(Map<String, Object> paramMap);

    List<OutptCostDTO> queryOutptCost(Map<String, Object> map);

    /**
     * @Description: 更新发药明细表费用id
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/27 11:52
     * @Return
     */
	void updatePharOutDistributeDetailsOldCostId(List<OutptCostDTO> outptCostDTOList);

	/**
	   * 更新手术表的门诊病人手术费用信息
	   * @Author: luonianxin
	   * @Email: nianxin.luo@powersi.com
	   * @Date: 2021/5/31 13:44
	**/
    int updateOperInfoRecord(Map<String, Object> updateParams);

    /**
     * @Method queryOutptInsureCostByVisit
     * @Desrciption  查询门诊医保病人的匹配费用信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/2 9:19
     * @Return
    **/
    List<Map<String, Object>> queryOutptInsureCostByVisit(Map<String, String> insureCostParam);

    /**
     * @Method queryOutptPrescribeCost
     * @Desrciption  根据处方id查询处方费用
     * @Param java.util.Map     *
     * @Author liuliyun
     * @Date   2021/09/03 10:09
     * @Return
     **/
    List<OutptCostDTO> queryOutptPrescribeCostList(Map<String, Object> map);

    /**
     * @Method queryPatientPrescribeNoSettle
     * @Desrciption  查询病人已提交未结算的处方
     * @Param java.util.Map
     * @Author liuliyun
     * @Date   2021/09/03 10:30
     * @Return
     **/
    List<OutptPrescribeDO> queryPatientPrescribeNoSettle(OutptPrescribeDO outptPrescribeDO);

	/**
	 * @Description: 门诊退费后需要更新医技申请状态
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/9/10 14:51
	 * @Return
	 */
	void updateMedicApply(String visitId, String hospCode, String documentSta, @Param("list") List<OutptCostDTO> deleteMedicApplyList);

	/**
	 * @Menthod batchInsert
	 * @Desrciption  批量插入门诊领药申请数据
	 * @param pharOutReceiveDOList 数据集合
	 * @Author Ou·Mr
	 * @Date 2020/9/18 17:49
	 * @Return int 受影响的行数
	 */
	int batchPharOutReceiveInsert(List<PharOutReceiveDO> pharOutReceiveDOList);

	/**
	 * @Menthod batchInsert
	 * @Desrciption  批量新增门诊领药申请信息
	 * @param pharOutReceiveDetailDOList 参数
	 * @Author Ou·Mr
	 * @Date 2020/9/16 15:13
	 * @Return int 受影响的行数
	 */
	int batchPharOutReceiveDetailInsert(List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList);

	/**
	 * @Description: 查询体检患者费用数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/11/22 20:27
	 * @Return
	 */
	List<OutptCostDTO> queryCostVisitIdTJ(Map param);

    /**
     * 修改费用发票ID
     * @param outotCost
     */
    void batchUpdateSettleInvoiceId(List<OutptCostDTO> outotCost);
    /**
     * @Meth: queryCostByIds
     * @Description: 通过费用id集合查询出门诊费用
     * @Param: [costIds]
     * @return: java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     * @Author: zhangguorui
     * @Date: 2022/2/23
     */
    List<OutptCostDTO> queryCostByIds(@Param("hospCode") String hospCode,@Param("costIds") List<String> costIds);
    /**
     * @Author gory
     * @Description 批量修改确费状态
     * @Date 2022/5/24 16:04
     * @Param [hospCode, lisAndPasCostIds]
     **/
    void updateIsTechnologyOkByContans(@Param("status")String status,@Param("hospCode")String hospCode,
                                      @Param("lisAndPasCostIds")  List<String> lisAndPasCostIds);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-17 10:33
     * @Description 根据门诊就诊ids查询门诊费用明细信息
     * @param ids
     * @return java.util.List<cn.hsa.module.inpt.doctor.dto.OutptCostDTO>
     */
    List<cn.hsa.module.inpt.doctor.dto.OutptCostDTO> queryOutptCostByvisitIds(List<String> ids);

    /**
     * 更新费用表结算ID
     * @param settleId
     * @param costList
     * @Author 医保开发二部-湛康
     * @Date 2022-05-24 10:05
     * @return void
     */
    void updateCostSettleId(String settleId,@Param("list") List<OutptCostDTO> costList);
}
