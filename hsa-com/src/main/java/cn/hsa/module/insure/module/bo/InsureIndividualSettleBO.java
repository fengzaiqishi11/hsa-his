package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualFundDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualSettle.bo
 * @Class_name:: InsureIndividualSettleBO
 * @Description: 医保个人结算查询业务逻辑接口层
 * @Author: fuhui
 * @Date: 2020/11/5 9:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureIndividualSettleBO {

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象集合
     **/
    PageDTO queryPage(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象
     **/
    InsureIndividualSettleDTO getById(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @Menthod insert
     * @Desrciption 保存医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:27
     * @Return int 受影响的行数
     */
    int insert(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod insertSelective
     * @Desrciption 保存医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:29
     * @Return int 受影响行数
     */
    int insertSelective(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod delInsureIndividualSettleByVisitId
     * @Desrciption 根据就诊id删除医保结算信息
     * @param param 请求参数(hospCode:医院编码、visitId:就诊id、settleState:结算状态)
     * @Author Ou·Mr
     * @Date 2020/11/29 20:58
     * @Return int 受影响行数
     */
    int delInsureIndividualSettleByVisitId(Map<String,String> param);

    /**
     * @Menthod findByCondition
     * @Desrciption 根据医院编码、就诊id、结算id 查询医保结算信息
     * @param insureIndividualSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/30 11:32
     * @Return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO findByCondition(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:21
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:22
     * @Return int 受影响函数
     */
    int updateByPrimaryKeySelective(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod getByParams
     * @Desrciption 修改医保结算信息
     * @param settleMap 请求参数
     * @Author liaojiguang
     * @Date 2020/12/02 11:10
     * @Return InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO getByParams(Map<String, Object> settleMap);

    /**
     * @param fundDTOList
     * @Method insertBatchFund
     * @Desrciption 结算以后 把基金信息保存到基金表当中
     * @Param
     * @Author fuhui
     * @Date 2021/4/29 19:27
     * @Return
     */
    Boolean insertBatchFund(List<InsureIndividualFundDTO> fundDTOList);

    /**
     * @param insureIndividualSettleDTO
     * @Method querySettle
     * @Desrciption 查询医保结算数据
     * @Param map
     * @Author fuhui
     * @Date 2021/6/30 19:07
     * @Return
     */
    InsureIndividualSettleDTO querySettle(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @param map
     * @Method selectInsureIndividualSettleById
     * @Desrciption 通过his结算id、就诊id查询医保结算表记录
     * @Param
     * @Author fuhui
     * @Date 2021/12/20 10:30
     * @Return
     */
    InsureIndividualSettleDTO selectInsureIndividualSettleById(Map<String, Object> map);

    /**
     * 根据就诊ID获取最新一条结算信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-17 8:47
     * @return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO getInsureSettleByVisitId(Map<String, Object> map);
}

