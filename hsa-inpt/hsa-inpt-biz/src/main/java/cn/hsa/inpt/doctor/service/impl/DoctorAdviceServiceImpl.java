package cn.hsa.inpt.doctor.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.inpt.doctor.bo.DoctorAdviceBO;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.doctor.advice.service.impl
 *@Class_name: DoctorAdviceServiceImpl
 *@Describe: 住院医嘱
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 11:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/doctor")
@Service("doctorAdviceService_provider")
public class DoctorAdviceServiceImpl extends HsafService implements DoctorAdviceService {

    @Resource
    DoctorAdviceBO doctorAdviceBO;

    /**
    * @Method updateInptAdviceBatch
    * @Desrciption 批量更新医嘱
    * @param map
    * @Author liuqi1
    * @Date   2020/9/2 15:29
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateInptAdviceBatch(Map<String, Object> map) {
        List<InptAdviceDTO> inptAdviceDTOS = MapUtils.get(map, "inptAdviceDTOS");
        Boolean isSuccess = doctorAdviceBO.updateInptAdviceBatch(inptAdviceDTOS);
        return WrapperResponse.success(isSuccess);
    }

    /**
    * @Method getOperationName
    * @Param [map]
    * @description 下拉框查询手术名称
    * @author marong
    * @date 2020/9/22 20:00
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>>
    * @throws
    */
    @Override
    public WrapperResponse<List<InptAdviceDetailDTO>> queryOperationNameList(Map map) {
        InptAdviceDetailDTO inptAdviceDetailDTO = MapUtils.get(map,"inptAdviceDetailDTO");
        return WrapperResponse.success(doctorAdviceBO.queryOperationNameList(inptAdviceDetailDTO));
    }

    /**
     * @Method queryInptVisitPage
     * @Desrciption 查询住院就诊信息
     * @param map：inptVisitDTO
     * @Author zengfeng
     * @Date   2020/9/27 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptVisitPage(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = doctorAdviceBO.queryInptVisitPage(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method queryInptAdviceVisitID
     * @Desrciption 查询住院医嘱信息
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    @Override
    public WrapperResponse<List<InptAdviceDTO>> queryInptAdviceVisitID(Map map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
        return WrapperResponse.success(doctorAdviceBO.queryInptAdviceVisitID(inptAdviceDTO));
    }

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param map：inptAdviceDTOList 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveInptAdvice(Map<String, Object> map) {
        List<InptAdviceDTO> inptAdviceDTOList = MapUtils.get(map, "inptAdviceDTOList");
        Boolean isSuccess = doctorAdviceBO.saveInptAdvice(inptAdviceDTOList);
        return WrapperResponse.success(isSuccess);
    }

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医院医嘱信息
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> deleteInptAdvice(Map<String, Object> map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map, "inptAdviceDTO");
        Boolean isSuccess = doctorAdviceBO.deleteInptAdvice(inptAdviceDTO);
        return WrapperResponse.success(isSuccess);
    }

    /**
     * @Method updateBatchInptAdviceStop
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateBatchInptAdviceStop(Map<String, Object> map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map, "inptAdviceDTO");
        Boolean isSuccess = doctorAdviceBO.updateBatchInptAdviceStop(inptAdviceDTO);
        return WrapperResponse.success(isSuccess);
    }

    /**
     * @Method updateBatchInptAdviceplan
     * @Desrciption 批量更新医嘱预停时间
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateBatchInptAdviceplan(Map<String, Object> map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map, "inptAdviceDTO");
        Boolean isSuccess = doctorAdviceBO.updateBatchInptAdviceplan(inptAdviceDTO);
        return WrapperResponse.success(isSuccess);
    }

    /**
     * @Menthod getInptDiagnose
     * @Desrciption  获取诊断信息
     * @param map: inptVisitDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    @Override
    public WrapperResponse<List<InptDiagnoseDTO>> getInptDiagnose(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return WrapperResponse.success(doctorAdviceBO.getInptDiagnose(inptVisitDTO));
    }

    /**
     * @param map
     * @Method queryInptDiagnose
     * @Desrciption 查询医保病人的诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/11 19:42
     * @Return
     */
    @Override
    public WrapperResponse<List<InptDiagnoseDTO>> queryInptDiagnose(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return WrapperResponse.success(doctorAdviceBO.queryInptDiagnose(inptVisitDTO));
    }

    /**
     * @Menthod saveInptDiagnose
     * @Desrciption  保存诊断信息
     * @param map: inptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> saveInptDiagnose(Map map) {
        InptDiagnoseDTO inptDiagnoseDTO = MapUtils.get(map, "inptDiagnoseDTO");
        return WrapperResponse.success(doctorAdviceBO.saveInptDiagnose(inptDiagnoseDTO));
    }

    /**
     * @Method updateInptAdviceIsSubmit
     * @Desrciption 提交医嘱
     * @param map：inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateInptAdviceIsSubmit(Map<String, Object> map) {
        InptAdviceTDTO inptAdviceTDTO = MapUtils.get(map, "inptAdviceTDTO");
        Boolean isSuccess = doctorAdviceBO.updateInptAdviceIsSubmit(inptAdviceTDTO);
        return WrapperResponse.success(isSuccess);
    }

    @Override
    public WrapperResponse<List<Map<String, Object>>> checkStock(Map map) {
        return WrapperResponse.success(doctorAdviceBO.checkStock(MapUtils.get(map,"inptAdviceDTO")));
    }

    /**
     * @Method updateBatchInptAdviceCancel
     * @Desrciption 批量取消
     * @param map ：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateBatchInptAdviceCancel(Map map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map, "inptAdviceDTO");
        Boolean isSuccess = doctorAdviceBO.updateBatchInptAdviceCancel(inptAdviceDTO);
        return WrapperResponse.success(isSuccess);
    }

    /**
     * @param paramMap 医嘱明细信息
     * @Method saveInptAdvice
     * @Desrciption 保存中草药医嘱（中草药医嘱录入界面为明细数据,医嘱主界面为主表数据）
     * 1.判断费用是否满足开医嘱需求
     * 2.根据visitId判断开医嘱科室跟入院科室一样
     * 3.判断是否有修改的数据,  根据iaId,找到对应的医嘱主表，如果提交标志判断,有修改的数据就删除数据，否则提示不能修改
     * 2.
     * @Author zengfeng
     * @Date 2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveInptAdviceZCY(Map<String, Object> paramMap) {
        return WrapperResponse.success(doctorAdviceBO.saveInptAdviceZCY(paramMap));
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 08:48
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(Map paramMap) {
        return WrapperResponse.success(doctorAdviceBO.queryLimitDrugList(MapUtils.get(paramMap, "inptAdviceDTO")));
    }

    /**
     * @Menthod: updateInptAdviceDetailLmt
     * @Desrciption: 更新医嘱明细表限制用药相关字段
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 10:39
     * @Return:
     **/
    @Override
    public WrapperResponse<Boolean> updateInptAdviceDetailLmt(Map map) {
        return WrapperResponse.success(doctorAdviceBO.updateInptAdviceDetailLmt(MapUtils.get(map, "insureItemMatchDTOS")));
    }

    /**
     * @Menthod: queryLisAdvice
     * @Desrciption: 根据合管条件查询同类型的lis医嘱，用于合并打印lis申请单
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-11 10:24
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InptAdviceDTO>> queryLisAdvice(Map map) {
        return WrapperResponse.success(doctorAdviceBO.queryLisAdvice(MapUtils.get(map, "inptAdviceDTO")));
    }

    /**
     * @Menthod: getZyAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药医嘱列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-26 11:31
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InptAdviceDTO>> getZyAdviceByVisitId(Map map) {
        return WrapperResponse.success(doctorAdviceBO.getZyAdviceByVisitId(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询未提交医嘱信息
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-01 10:33
     * @Return:
     **/
    @Override
    public WrapperResponse<Boolean> queryUnsubmitAdviceList(Map map) {
        doctorAdviceBO.insertUnsubmitAdviceList(map, Constants.YZ_TYPE.YZ_TYPE_WTJ);
        return WrapperResponse.success(true);
    }

    /**
     * @param map
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询某人的LIS或者PACS医嘱信息
     * @Author: pengbo
     * @Date: 2022-06-13 10:29
     * @Return:
     */
    @Override
    public WrapperResponse<List<InptAdviceDTO>> queryLisOrPacsAdvice(Map map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
        return WrapperResponse.success(doctorAdviceBO.queryLisOrPacsAdvice(inptAdviceDTO));
    }
}
