package cn.hsa.outpt.outptvisit.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.visit.bo.OutptVisitBO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptvisit.bo.impl
 * @Class_name: OutptVisitBOImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/3 16:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class OutptVisitBOImpl extends HsafBO implements OutptVisitBO {
    /**
     * 门诊挂号数据访问层
     */
    @Resource
    private OutptVisitDAO outptVisitDAO;

    /**
     * @Method queryVisitRecords
     * @Desrciption
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:04
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryVisitRecords(OutptVisitDTO outptVisitDTO) {
        // 设置分页信息
        PageHelper.startPage(outptVisitDTO.getPageNo(),outptVisitDTO.getPageSize());
        return PageDTO.of(outptVisitDAO.queryVisitRecords(outptVisitDTO));
    }

    /**
     * @Method updateTranInCode
     * @Desrciption
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:04
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateTranInCode(OutptVisitDTO outptVisitDTO) {
        return outptVisitDAO.updateTranInCode(outptVisitDTO)>0;
    }


    /**
     * @Menthod queryByVisitID
     * @Desrciption 根据ID查询门诊患者信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/17 21:54
     * @Return cn.hsa.module.outpt.visit.dto.OutptVisitDTO
     */
    @Override
    public OutptVisitDTO queryByVisitID(Map<String, String> param) {
        return outptVisitDAO.queryByVisitID(param);
    }


    /**
     * @Method updateOutptVisit
     * @Desrciption  走统一支付平台时，登记挂号成功以后，修改病人类型
     * @Param outptVisitMap
     *
     * @Author fuhui
     * @Date   2021/3/8 15:25
     * @Return
     **/
    @Override
    public Boolean updateOutptVisit(OutptVisitDTO outptVisitDTO) {
        return outptVisitDAO.updateOutptVisit(outptVisitDTO);
    }

    /**
     * @param map
     * @Method selectOutptVisitById
     * @Desrciption 根据就诊id查询门诊患者
     * @Param
     * @Author fuhui
     * @Date 2021/12/13 16:27
     * @Return
     */
    @Override
    public OutptVisitDTO selectOutptVisitById(Map<String, Object> map) {
        return outptVisitDAO.selectOutptVisitById(map);
    }

    @Override
    public List<OutptVisitDTO> queryOutptVisitSelfFeePatient(Map<String, String> param) {
        return outptVisitDAO.queryOutptVisitSelfFeePatient(param);
    }

    @Override
    public List<OutptCostDTO> queryOutptCostByvisitIds(Map<String, Object> reqMap) {
        return outptVisitDAO.queryOutptCostByvisitIds(reqMap);
    }

    @Override
    public Boolean updateUplod(OutptVisitDTO outptVisitDTO) {
        outptVisitDAO.updateUplod(outptVisitDTO);
        return true;
    }

    @Override
    public Boolean updateOutptVisitUploadFlag(OutptVisitDTO outptVisitDTO) {
         outptVisitDAO.updateOutptVisitUploadFlag(outptVisitDTO);
        return true;
    }

    /**
     * @param outptVisitDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
     * @method selectOutptVisitByCertNo
     * @author wang'qiao
     * @date 2022/6/21 21:29
     * @description 根据证件类型和证件号码 查询信息
     **/
    @Override
    public OutptVisitDTO selectOutptVisitByCertNo(OutptVisitDTO outptVisitDTO) {
        return outptVisitDAO.selectOutptVisitByCertNo(outptVisitDTO);
    }

    /**
     * @param outptSettleDO
     * @Method updateOutptAcctPay
     * @Desrciption 修改
     * @Param
     * @Author fuhui
     * @Date 2021/12/15 11:16
     * @Return
     */
    @Override
    public boolean updateOutptAcctPay(OutptSettleDO outptSettleDO) {
        return outptVisitDAO.updateOutptAcctPay(outptSettleDO);
    }

    /**
     * @param map
     * @Method selectOutptSettleById
     * @Desrciption 根据就诊id查询门诊结算信息
     * @Param
     * @Author fuhui
     * @Date 2021/12/13 16:34
     * @Return
     */
    @Override
    public OutptSettleDTO selectOutptSettleById(Map<String, Object> map) {
        return outptVisitDAO.selectOutptSettleById(map);
    }


}
