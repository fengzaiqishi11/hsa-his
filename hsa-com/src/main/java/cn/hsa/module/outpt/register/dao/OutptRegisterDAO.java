package cn.hsa.module.outpt.register.dao;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.*;
import cn.hsa.module.outpt.register.entity.*;
import cn.hsa.module.outpt.visit.entity.OutptVisitDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.outpt.register.dao
 * @Class_name: PharApplyDAO
 * @Description: 门诊挂号数据访问层
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/11 08:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptRegisterDAO {

    /**
     * @Method:  queryRegisterInfoByParams
     * @Description:   分页显示所有门诊挂号正常信息
     * @Param:  OutptRegisterDTO 查询挂号信息的数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/11 08:38
     * @Return: OutptRegisterDto 挂号信息的数据传输对象集合
     */
    List<OutptRegisterDTO> queryRegisterInfoByParamsPage(OutptRegisterDTO OutptRegisterDTO);

    /**
     * @Method:  updateByPrimaryKeySelective
     * @Description:   根据主键修改挂号表信息
     * @Param:  OutptRegisterDTO 查询挂号信息的数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/11 15:29
     * @Return:  影响行数
     */
    int updateByPrimaryKeySelective (OutptRegisterDTO OutptRegisterDTO);

    /**
     * @Method:  selectByPrimaryKey
     * @Description:   根据主键与医院编码获取唯一记录
     * @Param:  map 查询挂号信息的数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/11 15:29
     * @Return:  实体对象
     */
    OutptRegisterDO selectByPrimaryKey (Map<String,Object> map);

    /**
     * @Menthod selectDetailByRegisterId
     * @Desrciption 门诊退号 - 根据参数获取挂号费用信息
     * @Param selectMap
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  集合对象
     **/
    List<OutptRegisterDetailDO> selectDetailByRegisterId(Map<String, Object> selectMap);

    /**
     * @Menthod updateDetailByRegisterId
     * @Desrciption 门诊退号 - 挂号支付信息原始数据被冲红（更新一条记录）
     * @Param outptRegisterDetailDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  影响行数
     **/
    int updateDetailByRegisterId(List<OutptRegisterDetailDO> list);

    /**
     * @Menthod insertDetail
     * @Desrciption 门诊退号 - 挂号支付费用新增一条冲红数据（新增）
     * @Param outptRegisterDetailDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  影响行数
     **/
    int insertDetail(List<OutptRegisterDetailDO> list);

    /**
     * @Menthod selectSettleByRegisterId
     * @Desrciption 门诊退号 - 根据参数获取挂号结算信息
     * @Param selectMap
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  实体对象
     **/
    OutptRegisterSettleDO selectSettleByRegisterId(Map<String, Object> selectMap);
    /**
     * @Menthod updateSettleByRegisterId
     * @Desrciption 门诊退号 - 挂号支付信息原始数据被冲红（更新一条记录）
     * @Param outptRegisterSettleDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  实体对象
     **/
    int updateSettleByRegisterId(OutptRegisterSettleDO outptRegisterSettleDO);

    /**
     * @Menthod insertDetail
     * @Desrciption 门诊退号 - 挂号结算信息新增一条冲红数据（新增）
     * @Param outptRegisterDetailDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  影响行数
     **/
    int insertSettle(OutptRegisterSettleDO outptRegisterSettleDO);

    /**
     * @Menthod registerPayChangeRed
     * @Desrciption 门诊退号 - 根据参数获取挂号支付方式信息
     * @Param selectMap
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    List<OutptRegisterPayDO> selectPayByRegisterId(Map<String, Object> selectMap);

    /**
     * @Menthod insertDetail
     * @Desrciption 门诊退号 - 挂号结算信息新增冲红数据（新增）
     * @Param outptRegisterDetailDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     * @Return:  影响行数
     **/
    int insertPayList(List<OutptRegisterPayDO> insertList);

    /**
     * @Menthod unBlockNumberInfo
     * @Desrciption 门诊退号 - 清除号源信息（更新医生号源信息）
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     * @Return:  影响行数
     **/
    int updateDoctorRegister(Map<String, Object> updateMap);

    /**
     * @Menthod updatePatientState
     * @Desrciption 门诊退号 - 就诊记录直接删除
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     * @Return:  影响行数
     **/
    int deleteVisitByVisitId(OutptRegisterDO outptRegisterDO);

    /**
     * @Menthod updatePatientState
     * @Desrciption 门诊退号 - 判断是否就诊
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     * @Return:  影响行数
     **/
    OutptVisitDO getVisitInfoByVisitId(OutptRegisterDO outptRegisterDO);


    void insert(OutptRegisterDTO outptRegisterDTO);

    void insertRegDetail(List<OutptRegisterDetailDto> list);

    void batchInsertRegisterSettle(OutptRegisterSettleDto outptRegisterSettleDto);

    void batchInsertOutptRegisterPay(List<OutptRegisterPayDto> list);

    List<OutptDoctorRegisterDO> queryDoctorRegisterByTime(Map map);

    void updateDoctorRegisterStatus(OutptDoctorRegisterDO outptDoctorRegisterDO);

    void insertDoctouRegisterIsAdd(OutptDoctorRegisterDto outptDoctorRegisterDto);

    List<OutptDoctorQueueDto> queryOutptDoctorPage(OutptClassifyDTO outptClassifyDTO);

    List<OutptClassifyCostDTO> queryRegisterCostList(OutptClassifyDTO outptClassifyDTO);

    List<BaseDeptDTO> queryDeptAll(BaseDeptDTO baseDeptDTO);

    List<OutptRegisterDTO> queryAll(Map map);

    /**
     * @Menthod insertRegisterDetail
     * @Desrciption 插入费用明细(直接就诊)
     * @Param OutptRegisterDetailDto
     * @Author zengfeng
     * @Date   2020/9/8 15:40:23
     * @Return:  影响行数
     **/
    int insertRegisterDetail(List<OutptRegisterDetailDto> list);

    // 根据就诊id查询挂号记录(outpt_register)
    OutptRegisterDTO  getOutptRegisterByVisitId(Map<String, Object> map);

    /**
     * @Description: 查询当前发票号段已经使用的最大发票号码
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/25 12:06
     * @Return
     */
    OutinInvoiceDTO getMaxInvoiceNo(OutptRegisterSettleDto outptRegisterSettleDto);

    int updateDetailById(@Param("list") List<OutptRegisterDetailDO> list);

    List<OutptRegisterDTO> queryRegisterInfoByCertno(Map<String, Object> map);
}