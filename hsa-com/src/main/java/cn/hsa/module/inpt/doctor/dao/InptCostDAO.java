package cn.hsa.module.inpt.doctor.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.msg.entity.MsgTempRecordDO;
import cn.hsa.module.phar.pharinbackdrug.dto.*;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.inpt.dao
 * @Class_name: InptCostDao
 * @Describe: 住院费用数据访问层
 * @Author: liuqi1
 * @Eamil: liuqi1@powersi.com.cn
 * @Date: 2020-09-01 19:46:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptCostDAO {

    /**
     * @param inptCostDTO
     * @Method queryInptCostById
     * @Desrciption 单个查询
     * @Author liuqi1
     * @Date 2020/9/1 20:14
     * @Return cn.hsa.module.inpt.dto.InptCostDTO
     **/
    InptCostDTO getInptCostById(InptCostDTO inptCostDTO);

    /**
     * @param inptCostDTO
     * @Method queryInptCostPage
     * @Desrciption 分页查询
     * @Author liuqi1
     * @Date 2020/9/1 20:16
     * @Return java.util.List<cn.hsa.module.inpt.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryInptCostPage(InptCostDTO inptCostDTO);

    /**
     * @param list
     * @Method queryInptCostByAdviceId
     * @Desrciption 查询出同医嘱、计划执行时间同天的正常费用
     * @Author liuqi1
     * @Date 2020/11/12 20:18
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryInptCostByAdviceId(@Param("list") List<InptCostDTO> list);

    /**
     * @param inptCostDTO
     * @Method queryBackCostSurePage
     * @Desrciption 退费确认查询
     * @Author liuqi1
     * @Date 2020/9/11 10:48
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryBackCostSurePage(InptCostDTO inptCostDTO);

    List<InptCostDTO> queryOutpatientSurgeryCostPage(InptCostDTO inptCostDTO);


    /**
     * @param inptCostDTO
     * @Method insertInptCost
     * @Desrciption 单个新增
     * @Author liuqi1
     * @Date 2020/9/1 20:18
     * @Return int
     **/
    int insertInptCost(InptCostDTO inptCostDTO);

    /**
     * @param inptCostDTOs
     * @Method insertInptCostBatch
     * @Desrciption 批量新增
     * @Author liuqi1
     * @Date 2020/9/3 11:35
     * @Return int
     **/
    int insertInptCostBatch(List<InptCostDTO> inptCostDTOs);

    /**
     * @param inptCostDTO
     * @Method updateInptCost
     * @Desrciption 单个更新
     * @Author liuqi1
     * @Date 2020/9/1 20:18
     * @Return int
     **/
    int updateInptCost(InptCostDTO inptCostDTO);

    List<Map<String,Object>> checkStock(Map<String,Object> params);

    /**
     * @param inptCostDTOs
     * @Method updateInptCostBatch
     * @Desrciption 批量更新住院费用的回退数量
     * @Author liuqi1
     * @Date 2020/9/4 16:53
     * @Return int
     **/
    int updateInptCostBatch(List<InptCostDTO> inptCostDTOs);

    /**
     * @param inptAdviceExecDTOs
     * @Method updateInptCostExecuteBatch
     * @Desrciption 医嘱执行更新执行人
     * @Author liuqi1
     * @Date 2020/11/4 19:35
     * @Return int
     **/
    int updateInptCostExecuteBatch(List<InptAdviceExecDTO> inptAdviceExecDTOs);

    /**
     * @Method: getTzzhCost
     * @Description: 获取停嘱时间之后的医嘱
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 19:24
     * @Return: List<InptAdviceDTO>
     **/
    List<InptCostDTO> getTzzhCost(Map<String, Object> param);

    /**
     * @Method: getDtzhCost
     * @Description: 获取医嘱下面的动态费用
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/1 9:48
     * @Return:
     **/
    List<InptCostDTO> getDtzhCost(Map<String, Object> param);

    /**
     * @Method: updateBackNum
     * @Description: 更新回退数量
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 20:11
     * @Return: int
     **/
    int updateBackNum(InptCostDTO inptCostDTO);

    /**
     * @Method: getTzdrCost
     * @Description: List<InptCostDTO>
     * @Param: 获取停嘱时间当天的费用
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 21:02
     * @Return:
     **/
    List<InptCostDTO> getTzdrCost(Map<String, Object> param);

    /**
     * @param inptCostDTO
     * @Method updateInptCostBatchWithBackCost
     * @Desrciption 住院退费确认更新
     * @Author liuqi1
     * @Date 2020/9/11 16:06
     * @Return int
     **/
    int updateInptCostBatchWithBackCost(InptCostDTO inptCostDTO);

    /**
     * @Menthod updateNewBackCost
     * @Desrciption 住院退费确认更新(更新状态 已退费不退药)
     * @Param [inptCostDTO]
     * @Author jiahong.yang
     * @Date 2021/7/20 10:07
     * @Return int
     **/
    int updateNewBackCost(InptCostDTO inptCostDTO);

    /**
     * @param inptCostDTO
     * @Method updateInptCostBatchWithBackDrug
     * @Desrciption 住院退药批量更新
     * @Author liuqi1
     * @Date 2020/9/15 9:51
     * @Return int
     **/
    int updateInptCostBatchWithBackDrug(InptCostDTO inptCostDTO);

    /**
     * @Method: getSourceIds
     * @Description: 获取医嘱下面所有的费用表中的费用来源ID
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/22 20:29
     * @Return:
     **/
    List<String> getSourceIds(String hospCode, List<InptAdviceDTO> inptAdviceDTOList);

    /**
     * @Method: getSourceFirstIds
     * @Description: 获取费用表中所有动态计费首次费用的费用来源id, 用于后面计算病人当天已经生成了多少次首次计费
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/22 20:40
     * @Return:
     **/
    List<String> getSourceFirstIds(String hospCode, List<InptAdviceDTO> inptAdviceDTOList);


    /**
     * @return int
     * @throws
     * @Method updateInptCostList
     * @Param [inptCostDTOList]
     * @description 批量修改费用
     * @author marong
     * @date 2020/9/21 14:30
     */
    int updateInptCostList(List<InptCostDTO> inptCostDTOList);


    /**
     * @param param 查询条件
     * @Menthod queryInptCostByBfc
     * @Desrciption 根据财务分类统计费用金额
     * @Author Ou·Mr
     * @Date 2020/9/27 10:08
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryInptCostByBfc(Map param);

    /**
    * @Menthod queryNewInptCostByBfc
    * @Desrciption 不考虑归属结算
    *
    * @Param
    * [param]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 16:38
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> queryNewInptCostByBfc(Map param);

    /**
     * @param param 查询条件
     * @Menthod queryInptCostList
     * @Desrciption 查询费用信息
     * @Author Ou·Mr
     * @Date 2020/9/30 11:32
     * @Return java.util.List<cn.hsa.module.inpt.doctor.entity.InptCostDO>
     */
    List<InptCostDO> queryInptCostList(Map<String, Object> param);


    /**
    * @Menthod queryInptCostList
    * @Desrciption 不需要判断归属结算
    *
    * @Param
    * [param]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 16:15
    * @Return java.util.List<cn.hsa.module.inpt.doctor.entity.InptCostDO>
    **/
    List<InptCostDO> queryNewInptCostList(Map<String, Object> param);

    /**
    * @Menthod queryIsExitAttributionCostList
    * @Desrciption 正常结算看是否存在归属结算费用
    *
    * @Param
    * [param]
    *
    * @Author jiahong.yang
    * @Date   2021/8/20 11:03
    * @Return java.util.List<cn.hsa.module.inpt.doctor.entity.InptCostDO>
    **/
    List<InptCostDO> queryIsExitAttributionCostList(Map<String, Object> param);

    /**
     * @param param 请求参数
     * @Menthod editInptCostByIds
     * @Desrciption 修改费用信息
     * @Author Ou·Mr
     * @Date 2020/9/30 17:29
     * @Return int 受影响行数
     */
    int editInptCostByIds(Map param);

    /**
     * @Method: queryCostList
     * @Description: 根据参数查询费用记录
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/9 20:28
     * @Return:
     **/
    List<InptCostDTO> queryCostList(String hospCode, String adviceId, String detailId, Date startTime);

    /**
     * @Method: queryScCost
     * @Description: 查询某个人某一天产生了多少次首次计费费用
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/18 17:00
     * @Return:int
     **/
    List<InptCostDTO> queryScCost(String time, String visitId, String hospCode, String isFirst, String usageCode);

    InptCostDTO queryInptCostById(String id, String hospCode, String usageCode);

    List<String> queryAdviceIdsByCostIds(@Param("costIds") List<String> costIds, @Param("hospCode") String hospCode);

    int querySyCost(String adviceId, String hospCode, Date startTime);

    List<InptCostDTO> queryInptCosts(List<String> ids, String hospCode);

    /**
     * @Method: queryCxFOrRqfCost
     * @Description: 查询采血费记录
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 15:45
     * @Return:
     **/
    List<InptCostDTO> queryCxFfCost(String hospCode, String visitId, String checkTime, String baseItemId);

    /**
     * @Method: queryRqfCost
     * @Description: 查询采血材料费用
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 21:38
     * @Return:
     **/
    List<InptCostDTO> queryRqfCost(String hospCode, String visitId, String checkTime, String materialId);

    /**
     * @Method: queryDTCosts
     * @Description: 根据费用ID获取动态费用
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/15 20:17
     * @Return:
     **/
    List<InptCostDTO> queryDTCosts(List<String> ids, String hospCode);

    int updateOperInfoRecord(InptCostDTO inptCostDTO);

    int queryYcsDtCost(String adviceId, String hospCode, Date startTime);

    List<InptCostDTO> queryWait(InptCostDTO inptCostDTO);

    int queryCostZSL(String adviceId, String hospCode, Date date);

    List<Map<String, Object>> queryShortcutWindows(Map param);

    String queryBaseDeptById(Map param);

    /**
     * @Method: insertMsg
     * @Description: 消息入库
     * @Param: msgList msgList
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/21 11:12
     * @Return: int
     **/
    int insertMsg(List<MsgTempRecordDO> msgList);

    List<MsgTempRecordDO> queryMsg(String hospCode, String visitId, String adviceId, Date time);

    /**
     * @Method: insertPharInWaitReceiveBatch
     * @Description: 批量插入领药表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 8:56
     * @Return:
     **/
    int insertPharInWaitReceiveBatch(List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs);

    /**
     * @Description: 根据退费id查询出医技的费用
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/16 17:53
     * @Return
     */
    List<InptCostDTO> queryYJInptCostPage(InptCostDTO inptCostDTO);

    /**
     * @Description: 查询退费项目的医技申请
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/12/11 14:27
     * @Return
     */
    List<String> queryMedic(InptCostDTO inptCostDTO);

    /**
     * @Description: 住院退费时更新医技状态
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/12/11 14:00
     * @Return
     */
    void updateMedicApply(MedicalApplyDTO medicalApplyDTO);

    /**
     * @param pharInReceiveDTOs
     * @Method insertPharInReceives
     * @Desrciption 批量新增
     * @Author liuqi1
     * @Date 2020/8/25 16:14
     * @Return int
     **/
    int insertPharInReceives(List<PharInReceiveDTO> pharInReceiveDTOs);

    /**
     * @param pharInReceiveDetailDTOs
     * @Method insertPharInReceiveDetails
     * @Desrciption 批量新增
     * @Author liuqi1
     * @Date 2020/8/25 16:20
     * @Return int
     **/
    int insertPharInReceiveDetails(@Param("list") List<PharInReceiveDetailDTO> pharInReceiveDetailDTOs);

    /**
     * @Menthod updateCostBackCodeStatus
     * @Desrciption 修改费用退药状态代码
     * @Param [inptCostDTO]
     * @Author jiahong.yang
     * @Date 2021/5/6 16:27
     * @Return int
     **/
    int updateCostBackCodeStatus(InptCostDTO inptCostDTO);

    /**
     * 查询门诊病人手术退费项目
     *
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/2 17:04
     **/
    List<Map<String, Object>> querySurgeryBackCostInfoPage(Map<String, Object> map);

    /**
     * 查询当前病人是否有在预出院时间之后的费用
     *
     * @Author: pengbo
     * @Date: 2021/7/6 17:04
     **/
    List<InptCostDTO> findOutTimeAfterCost(InptVisitDTO inptVisitDTO);

    PharInWaitReceiveDTO getPharInWaitReceiveByIatid(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Description: 医保中途结算查询医保费用上传区间的费用
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/7/28 16:23
     * @Return
     */
    List<InptCostDO> queryMidWaySettleInptCostList(Map<String, Object> param);

    /**
     * @Method checkInsureAndHisFee
     * @Desrciption   办理医保预出院之前 需要核对his和医保费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/29 14:11
     * @Return
    **/
    List<InptCostDTO> checkInsureAndHisFee(Map<String, Object> map);

    PharInWaitReceiveDTO queryPharInWaitReceiveOrg(Map<String, Object> map);

    int updatePharInWaitReceiveOrg(PharInWaitReceiveDTO receiveDTO);

    int deletePharInWaitReceive(PharInWaitReceiveDTO receiveDTO);

    // 发药批次
    PharInDistributeAllDetailDTO queryAllPharDistributeOrg(Map map);

    int updateAllPharDistributeOrg(PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO);

    // 发药明细
    PharInReceiveDetailDTO getPharInReceiveDetailByWrId(Map map);
    // 发药明细
    int updatePharInReceiveDetailOrg(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    //发药明细表记录
    PharInDistributeDetailDTO getPharInDistributeDetailByWrId(Map map);

    int updatePharInDistributeDetailOrg(PharInDistributeDetailDTO pharInDistributeDetailDTO);

    /**
     * @Method: insertPharInReceiveDetail
     * @Description:
     **/
    int insertPharInReceiveDetail(List<PharInReceiveDetailDTO> list);

    /**
     * @Method: insertInDistributeDetail
     * @Description: 插入住院发药明细表
     **/
    int insertInDistributeDetail(List<PharInDistributeDetailDTO> list);

    /**
     * @Menthod insertInDistributeAllDetail
     * @Desrciption 住院发药批次汇总表入库
     **/
    int insertInDistributeAllDetail(List<PharInDistributeAllDetailDTO> list);

    int deletePharInReceiveDetailOrg(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    int deleteInDistributeAllDetailOrg(PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO);

    int deleteInDistributeDetailOrg(PharInDistributeDetailDTO pharInDistributeDetailDTO);
    /**
     * @Meth: updateCostIdBatch
     * @Description: 防止不同事务导致的问题
     * @Param: [inptCostDTOs]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/12/6
     */
    int updateCostIdBatch(List<InptCostDTO> inptCostDTOs);

    void newUpdateLastExeTime(MedicalAdviceDTO medicalAdviceDTO, Map<String, Date> adviceIdCostTime);
    /**
     * @Meth: querySurgeryOutptBackCostInfoPage
     * @Description: 查询门诊手术补记账退费
     * @Param: [map]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangguorui
     * @Date: 2022/2/21
     */
    List<Map<String, Object>> querySurgeryOutptBackCostInfoPage(Map<String, Object> map);
    /**
     * @Meth: querySurgeryInptBackCostInfoPage
     * @Description: 查询住院补记账退费
     * @Param: [map]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangguorui
     * @Date: 2022/2/21
     */
    List<Map<String, Object>> querySurgeryInptBackCostInfoPage(Map<String, Object> map);
    /**
     * @Meth: updateInWaitStatus
     * @Description:
     * @Param: [updateInwait]
     * @return: void
     * @Author: zhangguorui
     * @Date: 2022/4/20
     */
    void updateInWaitStatus(PharInWaitReceiveDTO updateInwait);
    /**
     * @Author gory
     * @Description 更新住院费用为已申请退费
     * @Date 2022/5/25 17:16
     * @Param [inptCostDTO]
     **/
    void updateInptCostIsOkByIds(InptCostDTO inptCostDTO);
    /**
     * @Author gory
     * @Description 通过单据id，获得单据状态
     * @Date 2022/8/1 17:06
     * @Param [pharInReceiveDTO]
     **/
    PharInReceiveDO getInReceiveById(PharInReceiveDTO pharInReceiveDTO);
}
