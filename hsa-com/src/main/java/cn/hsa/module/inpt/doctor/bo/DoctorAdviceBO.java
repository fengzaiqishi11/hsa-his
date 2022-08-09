package cn.hsa.module.inpt.doctor.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.doctor.bo
 *@Class_name: DoctorAdviceBo
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 14:46
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface DoctorAdviceBO {

    /**
    * @Method updateInptAdvice
    * @Desrciption 批量更新医嘱
    * @param inptAdviceDTOS
    * @Author liuqi1
    * @Date   2020/9/2 14:50
    * @Return java.lang.Boolean
    **/
    Boolean updateInptAdviceBatch(List<InptAdviceDTO> inptAdviceDTOS);

    /**
     * @Method queryOperationNameList
     * @Param [inptAdviceDetailDTO]
     * @description    获取手术名称
     * @author marong
     * @date 2020/9/18 11:29
     * @return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>
     * @throws
     */
    List<InptAdviceDetailDTO> queryOperationNameList(InptAdviceDetailDTO inptAdviceDetailDTO);

    /**
     * @Method queryInptVisitPage
     * @Desrciption 查询住院就诊信息
     * @param inptVisitDTO
     * @Author zengfeng
     * @Date   2020/9/27 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryInptVisitPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptAdviceVisitID
     * @Desrciption 查询住院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    List<InptAdviceDTO> queryInptAdviceVisitID(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param inptAdviceDTOList 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    Boolean saveInptAdvice(List<InptAdviceDTO> inptAdviceDTOList);

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
     Boolean deleteInptAdvice(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateBatchInptAdviceStop
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    Boolean updateBatchInptAdviceStop(InptAdviceDTO inptAdviceDTO);


    /**
     * @Method updateBatchInptAdviceplan
     * @Desrciption 批量更新医嘱预停时间
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    Boolean updateBatchInptAdviceplan(InptAdviceDTO inptAdviceDTO);

    /**
     * @Menthod getInptDiagnose
     * @Desrciption  获取诊断信息
     * @param inptVisitDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    List<InptDiagnoseDTO> getInptDiagnose(InptVisitDTO inptVisitDTO);

    /**
     * @param inptVisitDTO
     * @Method queryInptDiagnose
     * @Desrciption 查询医保病人的诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/11 19:42
     * @Return
     */
    List<InptDiagnoseDTO>queryInptDiagnose(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod saveInptDiagnose
     * @Desrciption  保存诊断信息
     * @param inptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    boolean saveInptDiagnose(InptDiagnoseDTO inptDiagnoseDTO);

    /**
     * @Method updateInptAdviceIsSubmit
     * @Desrciption 提交医嘱
     * @param inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    boolean updateInptAdviceIsSubmit(InptAdviceTDTO inptAdviceTDTO);

    /**
     * @Desrciption 检查库存
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> checkStock(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateBatchInptAdviceCancel
     * @Desrciption 批量取消
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    Boolean updateBatchInptAdviceCancel(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method saveInptAdvice
     * @Desrciption
     * 保存中草药医嘱（中草药医嘱录入界面为明细数据,医嘱主界面为主表数据）
     * 1.判断费用是否满足开医嘱需求
     * 2.根据visitId判断开医嘱科室跟入院科室一样
     * 3.判断是否有修改的数据,  根据iaId,找到对应的医嘱主表，如果提交标志判断,有修改的数据就删除数据，否则提示不能修改
     * 2.
     * @param parmMap 医嘱明细信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    Boolean saveInptAdviceZCY (Map<String,Object> parmMap);

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 08:48
     * @Return:
     **/
    List<InsureItemMatchDTO> queryLimitDrugList(InptAdviceDTO inptAdviceDTO);

    /**
     * @Menthod: updateInptAdviceDetailLmt
     * @Desrciption: 更新医嘱明细表限制用药相关字段
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 10:39
     * @Return:
     **/
    Boolean updateInptAdviceDetailLmt(List<InsureItemMatchDTO> insureItemMatchDTOS);

    /**
     * @Menthod: queryLisAdvice
     * @Desrciption: 根据合管条件查询同类型的lis医嘱，用于合并打印lis申请单
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-11 10:24
     * @Return:
     **/
    List<InptAdviceDTO> queryLisAdvice(InptAdviceDTO inptAdviceDTO);

    /**
     * @Menthod: getZyAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药医嘱列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-26 11:31
     * @Return:
     **/
    List<InptAdviceDTO> getZyAdviceByVisitId(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询未提交医嘱信息
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-01 10:01
     * @Return:
     **/
    List<MessageInfoDTO> insertUnsubmitAdviceList(Map map,String type);
    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询某人的LIS或者PACS医嘱信息
     * @Author: pengbo
     * @Date: 2022-06-13 10:29
     * @Return:
     **/
    List<InptAdviceDTO> queryLisOrPacsAdvice(InptAdviceDTO inptAdviceDTO);
    /**
     * @Menthod: getCFAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药、精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    List<InptAdviceDTO> getCFAdviceByVisitId(InptVisitDTO inptVisitDTO);
}
