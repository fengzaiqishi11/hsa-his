package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualFundDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualSettle.service
 * @Class_name:: InsureIndividualSettleService
 * @Description: 医保个人结算查询业务服务层接口
 * @Author: fuhui
 * @Date: 2020/11/5 9:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureIndividualSettleService {
    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象集合
     **/
    @GetMapping("/service/insure/insureIndividualSettle/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象
     **/
    @GetMapping("/service/insure/insureIndividualSettle/getById")
    WrapperResponse<InsureIndividualSettleDTO> getById(Map map);

    /**
     * @Menthod insert
     * @Desrciption 保存医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:27
     * @Return int 受影响的行数
     */
    @PostMapping("/service/insure/insureIndividualSettle/insert")
    int insert(Map<String,Object> param);

    /**
     * @Menthod insertSelective
     * @Desrciption 保存医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:29
     * @Return int 受影响行数
     */
    @PostMapping("/service/insure/insureIndividualSettle/insertSelective")
    int insertSelective(Map<String,Object> param);

    /**
     * @Menthod delInsureIndividualSettleByVisitId
     * @Desrciption 根据就诊id删除医保结算信息
     * @param param 请求参数(hospCode:医院编码、visitId:就诊id、settleState:结算状态)
     * @Author Ou·Mr
     * @Date 2020/11/29 20:58
     * @Return int 受影响行数
     */
    @DeleteMapping("/service/insure/insureIndividualSettle/delInsureIndividualSettleByVisitId")
    int delInsureIndividualSettleByVisitId(Map<String,String> param);

    /**
     * @Menthod findByCondition
     * @Desrciption 根据医院编码、就诊id、结算id 查询医保结算信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/30 11:32
     * @Return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    @GetMapping("/service/insure/insureIndividualSettle/findByCondition")
    InsureIndividualSettleDTO findByCondition(Map<String,Object> param);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:21
     * @Return int 受影响行数
     */
    @PutMapping("/service/insure/insureIndividualSettle/updateByPrimaryKey")
    int updateByPrimaryKey(Map<String,Object> param);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:22
     * @Return int 受影响函数
     */
    @PutMapping("/service/insure/insureIndividualSettle/updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Map<String,Object> param);

    /**
     * @Menthod getByParams
     * @Desrciption 修改医保结算信息
     * @param settleMap 请求参数
     * @Author liaojiguang
     * @Date 2020/12/02 12:22
     * @Return InsureIndividualSettleDTO
     */
    @PutMapping("/service/insure/insureIndividualSettle/getByParams")
    InsureIndividualSettleDTO getByParams(Map<String, Object> settleMap);

    /**
     * @Method insertBatchFund
     * @Desrciption  结算以后 把基金信息保存到基金表当中
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/29 19:27
     * @Return
    **/
    WrapperResponse<Boolean> insertBatchFund(Map<String, Object> isInsureUnifiedMap);

    /**
     * @Method querySettle
     * @Desrciption  查询医保结算数据
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/6/30 19:07
     * @Return
    **/
    WrapperResponse<InsureIndividualSettleDTO> querySettle(Map<String, Object> map);

    /**
     * @Method queryIsSettleInfo
     * @Desrciption  查询已经结算的医保记录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/26 10:14
     * @Return
    **/
    WrapperResponse<InsureIndividualSettleDTO> queryIsSettleInfo(Map<String, Object> map);

    /**
     * @Method selectInsureIndividualSettleById
     * @Desrciption  通过his结算id、就诊id查询医保结算表记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/20 10:30
     * @Return
    **/
    WrapperResponse<InsureIndividualSettleDTO> selectInsureIndividualSettleById(Map<String, Object> map);

    /**
     * 根据就诊ID获取最新一条结算信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-17 8:40
     * @return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO getInsureSettleByVisitId(Map<String, Object> map);
}
