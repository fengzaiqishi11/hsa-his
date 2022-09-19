package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.outpt.fees.dto.OutptPayDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.dao
 * @Class_name: OutptPayDAO
 * @Describe(描述):门诊支付方式DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 9:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptPayDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询门诊支付方式
     * @param key 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:12
     * @Return cn.hsa.module.outpt.fees.dto.OutptPayDTO 门诊支付方式信息
     */
    OutptPayDTO selectByPrimaryKey(@Param("id") String key);

    /**
     * @Menthod insert
     * @Desrciption 保存合同单位结算情况信息
     * @param outptPayDO 门诊合同单位结算情况
     * @Author Ou·Mr
     * @Date 2020/8/25 9:16
     * @Return int 受影响的行数
     */
    int insert(OutptPayDO outptPayDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存合门诊支付方式信息（字段为 null OR '' 不会保存字段）
     * @param outptPayDO 门诊支付方式参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:22
     * @Return int 受影响的行数
     */
    int insertSelective(OutptPayDO outptPayDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改门诊支付方式信息
     * @param outptPayDO 门诊支付方式参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(OutptPayDO outptPayDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改门诊支付方式信息
     * @param outptPayDO 门诊支付方式参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:28
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(OutptPayDO outptPayDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除门诊支付方式信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:30
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除门诊支付方式
     * @param ids 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:33
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询门诊支付方式信息
     * @param outptPayDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 9:38
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptPayDTO> 结果集
     */
    List<OutptPayDTO> findByCondition(OutptPayDTO outptPayDTO);

    /**
     * @Menthod findByCondition
     * @Desrciption 对冲门诊支付方式信息
     * @param outptPayDTOList
     * @Author liaojiguang
     * @Date 2020/9/07 11:50
     * @Return 影响行数
     */
    int insertOutptPays(List<OutptPayDTO> outptPayDTOList);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询门诊支付方式信息
     * @param outptSettleDTO
     * @Author liaojiguang
     * @Date 2020/9/07 11:50
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptPayDTO> 结果集
     */
    List<OutptPayDTO> findOutptPayByParams(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod batchInsertOutptPay
     * @Desrciption  批量保存发票信息
     * @param outptPayDOList 参数
     * @Author Ou·Mr
     * @Date 2020/9/14 15:26 
     * @Return int 受影响的行数
     */
    int batchInsertOutptPay(List<OutptPayDO> outptPayDOList);

    /**
     * @Description: 门诊病人lis数据下发
     * @Param: [outptVisitDTO]
     * @return: java.util.List<cn.hsa.module.medic.apply.dto.MedicalApplyDTO>
     * @Author: zhangxuan
     * @Date: 2021-06-29
     */
    List<Map> queryMedicalApplyList(OutptVisitDTO outptVisitDTO);

    /**
     * @Description: 住院病人lsi数据下发
     * @Param: [outptVisitDTO]
     * @return: java.util.List<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2021-07-02
     */
    List<Map> queryInptMedicalApplyList(OutptVisitDTO outptVisitDTO);

    /**
     * @Description: 根据就诊id，结算id查询支付信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/9/1 15:32
     * @Return
     */
    List<OutptPayDTO> selectOutptPatByVisitIdAndSettleId(Map selectMap);

    /**
     * @param outptPayDTO
     * @Menthod: getPayInfoByParams
     * @Desrciption: 获取支付信息
     * @Param: outptPayDTO
     * @Author: 廖继广
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2021-10-13 13:44
     * @Return:
     */
    OutptPayDTO getPayInfoByParams(OutptPayDTO outptPayDTO);

    /**
     * @Description: 根据就诊id，结算id查询支付信息
     * @Param: selectMap
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2022/09/06 10:59
     * @Return OutptPayDTO
     */
   OutptPayDTO selectPaymentSettlePay(Map selectMap);
}
