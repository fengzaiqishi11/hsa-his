package cn.hsa.outpt.outptvisit.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.visit.bo.OutptVisitBO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

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
     *参数获取服务
     */
    @Resource
    private SysParameterService sysParameterService_consumer;
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

    @Override
    public OutptVisitDTO queryInptVisitInfo(OutptVisitDTO outptVisitDTO) {
        OutptVisitDTO outptResultDto = outptVisitDAO.queryInptVisitInfo(outptVisitDTO);
        return outptResultDto;
    }
    @Override
    public InsureIndividualVisitDTO queryInsureVisitInfo(OutptVisitDTO outptVisitDTO) {
        InsureIndividualVisitDTO outptResultDto = outptVisitDAO.queryInsureVisitInfo(outptVisitDTO);
        return outptResultDto;
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

    /**
     * @param outptVisitDTO
     * @Menthod: queryPrescriptionAllowed
     * @Desrciption: 获取病人是否在允许的开方时间内
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-09-1 08:51
     * @Return: Boolean
     */
    @Override
    public Boolean queryPrescriptionAllowed(OutptVisitDTO outptVisitDTO) {
        boolean flag = true;
        /*参数控制*/
        flag = queryCheckParam(sysParameterDTO -> (null == sysParameterDTO || Constants.SF.F.equals(sysParameterDTO.getValue())),
                        (map) -> sysParameterService_consumer.getParameterByCode(map).getData(),
                        outptVisitDTO.getHospCode());
        /*校验确认sysParameterDTO的值，为空或者值为0直接返回*/
        if (flag){
            return true;
        }
        /*获取数据*/
        OutptVisitDTO finalOutptVisitDTO = outptVisitDAO.queryByID(outptVisitDTO);
        /*校验数据*/
        Assert.notNull(finalOutptVisitDTO,()-> "获取患者" + outptVisitDTO.getName() + "就诊数据为空！");
        /*校验是否就诊与是否允许在就诊时间当天开方*/
        Predicate<OutptVisitDTO> visitDTOPredicate = ((Predicate<OutptVisitDTO>) outptVisitDTO1 -> Constants.SF.F.equals(outptVisitDTO1.getIsVisit()))
                .or(outptVisitDTO2 -> {
                    if(DateUtil.today().equals(DateUtils.format(outptVisitDTO2.getCrteTime(), DateUtils.Y_M_D))){
                        return true;
                    }
                    return false;});
         flag = visitDTOPredicate.test(finalOutptVisitDTO);
        return flag;
    }

    /**
     *参数控制
     */
    public boolean queryCheckParam(Predicate<SysParameterDTO> predicate,Function<Map,SysParameterDTO> function,String hospCode){
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("code", "IS_PRESC_ALLOWED");
        SysParameterDTO apply = function.apply(map);
        return predicate.test(apply);
    }

}
