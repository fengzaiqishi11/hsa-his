package cn.hsa.inpt.nurseexcutecard.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurseexcutecard.bo.NurseExcuteCardBO;
import cn.hsa.module.inpt.nurseexcutecard.service.NurseExcuteCardService;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.nurseexcutecard.service.impl
 * @Class_name:: NurseExcuteCardServiceImpl
 * @Description:
 * @Author: fuhui
 * @Date: 2020/9/8 14:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/inpt/nurseExcuteCard")
@Slf4j
@Service("nurseExcuteCard_provider")
public class NurseExcuteCardServiceImpl extends HsafService implements NurseExcuteCardService {

    @Resource
    private NurseExcuteCardBO nurseExcuteCardBO;


    /**
     * @Method queryPatient()
     * @Desrciption 分页查询病人就诊信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/8 10:01
     * @Return 病人分页信息
     **/
    @Override
    @HsafRestPath(value = "/queryPatient", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPatient(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        return WrapperResponse.success(nurseExcuteCardBO.queryPatient(inptVisitDTO));
    }

    /**
     * @Method queryDocterAdvice()
     * @Desrciption 根据诊断id 查询医嘱信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/8 10:01
     * @Return 医嘱分页信息
     **/
    @Override
    @HsafRestPath(value = "/queryDocterAdvice", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryDocterAdvice(Map map) {
      InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
      return WrapperResponse.success(nurseExcuteCardBO.queryDocterAdvice(inptVisitDTO));
    }

    /**
    * @Menthod queryDocterAdviceAll
    * @Desrciption 护理执行卡
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/7/3 14:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
    **/
    @Override
    public WrapperResponse<List<InptAdviceDTO>> queryDocterAdviceAll(Map map) {
      InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
      return WrapperResponse.success(nurseExcuteCardBO.queryDocterAdviceAll(inptVisitDTO));
    }

  /**
     * @Method: AllPrinting()
     * @Descrition: 护理执行卡批量打印病人数据的功能
     * @Pramas: AllPrinting
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/21
     * @Retrun: List<List < InptAdviceDTO>>
     */
    @Override
    public WrapperResponse<List<List<InptAdviceDTO>>> AllPrinting(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        return WrapperResponse.success(nurseExcuteCardBO.AllPrinting(inptVisitDTO));
    }

    /**
     * @Method: updatePrintFlag()
     * @Descrition: 打印完成以后 修改打印的标识符
     * @Pramas: inptAdviceDTO包含：打印的医嘱id集合 对应的病人的就诊id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/22
     * @Retrun: boolean
     */
    @Override
    public WrapperResponse<Boolean> updatePrintFlag(Map map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
        return WrapperResponse.success(nurseExcuteCardBO.updatePrintFlag(inptAdviceDTO));
    }

    /**
    * @Menthod queryPatientNurseCard
    * @Desrciption 根据护理执行卡 查询患者信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/7/2 10:28
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPatientNurseCard(Map map) {
      InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
      return WrapperResponse.success(nurseExcuteCardBO.queryPatientNurseCard(inptVisitDTO));
    }

}
