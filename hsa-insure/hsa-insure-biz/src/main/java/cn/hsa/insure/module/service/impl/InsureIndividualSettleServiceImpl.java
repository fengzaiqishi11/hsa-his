package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureIndividualSettleBO;
import cn.hsa.module.insure.module.dto.InsureIndividualFundDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.service.InsureIndividualSettleService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service
 * @Class_name:: InsureIndividualSettleService
 * @Description: 医保个人结算查询业务服务实现层
 * @Author: fuhui
 * @Date: 2020/11/5 9:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureIndividualSettle")
@Service("insureIndividualSettleService_provider")
public class InsureIndividualSettleServiceImpl extends HsafService implements InsureIndividualSettleService {
    /**
     * 医保个人结算查询业务逻辑接口层
     */
    @Resource
    private InsureIndividualSettleBO insureIndividualSettleBO;

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象集合
     **/
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        InsureIndividualSettleDTO insureIndividualSettleDTO = MapUtils.get(map,"insureIndividualSettleDTO");
        PageDTO pageDTO = insureIndividualSettleBO.queryPage(insureIndividualSettleDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象
     **/
    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<InsureIndividualSettleDTO> getById(Map map) {
        InsureIndividualSettleDTO insureIndividualSettleDTO = MapUtils.get(map,"insureIndividualSettleDTO");
        return WrapperResponse.success(insureIndividualSettleBO.getById(insureIndividualSettleDTO));
    }

    /**
     * @Menthod insert
     * @Desrciption 保存医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:27
     * @Return int 受影响的行数
     */
    @Override
    public int insert(Map<String, Object> param) {
        return insureIndividualSettleBO.insert(MapUtils.get(param,"insureIndividualSettleDO"));
    }

    /**
     * @Menthod insertSelective
     * @Desrciption 保存医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:29
     * @Return int 受影响行数
     */
    @Override
    public int insertSelective(Map<String, Object> param) {
        return insureIndividualSettleBO.insertSelective(MapUtils.get(param,"insureIndividualSettleDO"));
    }

    /**
     * @Menthod delInsureIndividualSettleByVisitId
     * @Desrciption 根据就诊id删除医保结算信息
     * @param param 请求参数(hospCode:医院编码、visitId:就诊id、settleState:结算状态)
     * @Author Ou·Mr
     * @Date 2020/11/29 20:58
     * @Return int 受影响行数
     */
    @Override
    public int delInsureIndividualSettleByVisitId(Map<String, String> param) {
        return insureIndividualSettleBO.delInsureIndividualSettleByVisitId(param);
    }

    /**
     * @Menthod findByCondition
     * @Desrciption 根据医院编码、就诊id、结算id 查询医保结算信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/30 11:32
     * @Return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    @Override
    public InsureIndividualSettleDTO findByCondition(Map<String, Object> param) {
        return insureIndividualSettleBO.findByCondition(MapUtils.get(param,"insureIndividualSettleDTO"));
    }

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:21
     * @Return int 受影响行数
     */
    @Override
    public int updateByPrimaryKey(Map<String, Object> param) {
        return insureIndividualSettleBO.updateByPrimaryKey(MapUtils.get(param,"insureIndividualSettleDO"));
    }

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:22
     * @Return int 受影响函数
     */
    @Override
    public int updateByPrimaryKeySelective(Map<String, Object> param) {
        return insureIndividualSettleBO.updateByPrimaryKeySelective(MapUtils.get(param,"insureIndividualSettleDO"));
    }

    /**
     * @Menthod getByParams
     * @Desrciption 修改医保结算信息
     * @param settleMap 请求参数
     * @Author liaojiguang
     * @Date 2020/12/02 11:10
     * @Return InsureIndividualSettleDTO
     */
    @Override
    public InsureIndividualSettleDTO getByParams(Map<String, Object> settleMap) {
        return insureIndividualSettleBO.getByParams(settleMap);
    }

    /**
     * @param isInsureUnifiedMap
     * @Method insertBatchFund
     * @Desrciption 结算以后 把基金信息保存到基金表当中
     * @Param
     * @Author fuhui
     * @Date 2021/4/29 19:27
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertBatchFund(Map<String, Object> isInsureUnifiedMap) {
        List<InsureIndividualFundDTO> fundDTOList = MapUtils.get(isInsureUnifiedMap,"fundDTOList");
        return WrapperResponse.success( insureIndividualSettleBO.insertBatchFund(fundDTOList));
    }

    /**
     * @param map
     * @Method querySettle
     * @Desrciption 查询医保结算数据
     * @Param map
     * @Author fuhui
     * @Date 2021/6/30 19:07
     * @Return
     */
    @Override
    public WrapperResponse<InsureIndividualSettleDTO> querySettle(Map<String, Object> map) {
        InsureIndividualSettleDTO insureIndividualSettleDTO = MapUtils.get(map,"insureIndividualSettleDTO");
        return WrapperResponse.success(insureIndividualSettleBO.querySettle(insureIndividualSettleDTO));
    }

    /**
     * @param map
     * @Method queryIsSettleInfo
     * @Desrciption 查询已经结算的医保记录信息
     * @Param
     * @Author fuhui
     * @Date 2021/7/26 10:14
     * @Return
     */
    @Override
    public WrapperResponse<InsureIndividualSettleDTO> queryIsSettleInfo(Map<String, Object> map) {
        return null;
    }

    /**
     * @param map
     * @Method selectInsureIndividualSettleById
     * @Desrciption 通过his结算id、就诊id查询医保结算表记录
     * @Param
     * @Author fuhui
     * @Date 2021/12/20 10:30
     * @Return
     */
    @Override
    public WrapperResponse<InsureIndividualSettleDTO> selectInsureIndividualSettleById(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualSettleBO.selectInsureIndividualSettleById(map));
    }

    /**
     * 根据就诊ID获取最新一条结算信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-17 8:44
     * @return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    @Override
    public InsureIndividualSettleDTO getInsureSettleByVisitId(Map<String, Object> map) {
      return insureIndividualSettleBO.getInsureSettleByVisitId(map);
    }

}
