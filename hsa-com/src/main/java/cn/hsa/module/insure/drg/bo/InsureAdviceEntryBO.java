package cn.hsa.module.insure.drg.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;

/**
 * @Package_name: cn.hsa.module.insure.advice.bo
 * @class_name: InsureAdviceEntryBo
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 15:05
 * @Company: 创智和宇
 **/
public interface InsureAdviceEntryBO {

    /**
     * @Method: adviceEntry()
     * @Descrition: 录入在院人员医嘱信息
     * @Pramas: inptVisitDTO住院
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    boolean saveAdviceEntry(InsureIndividualVisitDTO insureIndividualVisitDTO);


    /**
     * @Method: queryPage()
     * @Descrition: 分页查询住院病人信息
     * @Pramas:  insureIndividualVisitDTO:医保住院病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: pageDTO
     */
    PageDTO queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO);

    Boolean deleteAdvice(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryAdvice()
     * @Descrition: 根据就诊id 查询医嘱信息
     * @Pramas: insureIndividualVisitDTO医保就诊病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/16
     * @Retrun: 医嘱分页信息
     */
    PageDTO queryAdvicePage(InsureIndividualVisitDTO insureIndividualVisitDTO);


    /**
      * @method BIZC300001
      * @author wang'qiao
      * @date 2022/6/9 13:deleteInjuryAdvice
      *	@description  工伤医嘱上传
      * @param  insureIndividualVisitDTO
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
      *
     **/
    WrapperResponse<Boolean> BIZC300001(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
      * @method deleteInjuryAdvice
      * @author wang'qiao
      * @date 2022/6/9 14:17
      *	@description 	工伤医嘱批量删除
      * @param  insureIndividualVisitDTO
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
      *
     **/
    WrapperResponse<Boolean> deleteInjuryAdvice(InsureIndividualVisitDTO insureIndividualVisitDTO);
}
