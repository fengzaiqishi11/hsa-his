package cn.hsa.module.inpt.nurseexcutecard.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;

public interface NurseExcuteCardBO {

    /**
     * @Method queryPatient()
     * @Desrciption 分页查询病人就诊信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/8 10:01
     * @Return 病人分页信息
     **/
    PageDTO queryPatient(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryDocterAdvice()
     * @Desrciption 根据诊断id 查询医嘱信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/8 10:01
     * @Return 医嘱分页信息
     **/
    PageDTO queryDocterAdvice(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod queryDocterAdviceAll
    * @Desrciption
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/3 17:30
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
    **/
    List<InptAdviceDTO> queryDocterAdviceAll(InptVisitDTO inptVisitDTO);

    /**
     * @Method: AllPrinting()
     * @Descrition: 护理执行卡批量打印病人数据的功能
     * @Pramas: AllPrinting
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/21
     * @Retrun: List<List < InptAdviceDTO>>
     */
    List<List<InptAdviceDTO>> AllPrinting(InptVisitDTO inptVisitDTO);

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

    /**
    * @Menthod queryPatientNurseCard
    * @Desrciption
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/2 10:29
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPatientNurseCard(InptVisitDTO inptVisitDTO);
}
