package cn.hsa.module.inpt.nurseexcutecard.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceExecDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NurseExcuteCardDAO {

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     * @Param 1、 baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    List<InptVisitDTO> queryPatient(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryDocterAdvice()
     * @Desrciption 根据诊断id 查询医嘱信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/8 10:01
     * @Return 医嘱分页信息
     **/
    List<InptAdviceDTO> queryDocterAdvice(InptVisitDTO inptVisitDTO);

    List<InptAdviceDTO> queryDocterAdviceMany(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updatePrintFlag()
     * @Descrition: 打印完成以后 修改打印的标识符
     * @Pramas: inptAdviceDTO包含：打印的医嘱id集合 对应的病人的就诊id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/22
     * @Retrun: boolean
     */
    Boolean updatePrintFlag(InptAdviceDTO inptAdviceDTO);

    Boolean updatePrintFlagByType(@Param("inptAdviceExecDTOS") List<InptAdviceExecDTO> inptAdviceExecDTOS);

    /**
     * @Method: queryExcuteByIds
     * @Description: 根据医嘱执行ids查询对应的医嘱执行记录
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-03-30 20:36
     * @Return
     */
    List<InptAdviceExecDTO> queryExcuteByIds(InptAdviceDTO inptAdviceDTO);

    /**
    * @Menthod queryPatientNurseCard
    * @Desrciption 根据护理执行卡 查询患者信息
    *
    * @Param
    * [inptAdviceDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/2 10:53
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    List<String> queryVistId(InptVisitDTO inptVisitDTO);

    List<InptVisitDTO> queryPatientByIds(InptVisitDTO inptVisitDTO);
}
