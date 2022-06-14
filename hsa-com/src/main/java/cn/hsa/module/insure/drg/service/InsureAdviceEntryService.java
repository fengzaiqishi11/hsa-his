package cn.hsa.module.insure.drg.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.advice.service
 * @class_name: InsureAdviceEntryService
 * @project_name: hsa-his
 * @Description: 医嘱录入信息上传服务层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 15:05
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureAdviceEntryService {

    /**
     * @Method: adviceEntry()
     * @Descrition: 录入在院人员医嘱信息
     * @Pramas: inptVisitDTO住院
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    WrapperResponse<Boolean> saveAdviceEntry(Map map);

//    /**
//     * @Method: drawoadAdvicePatient()
//     * @Decription: 提取待录入医嘱人员信息
//     * @Pramas: type:1 住院号  2:参保人电脑号 3:参保人的姓名 4: 参保人的公民身份号码 5:参保人的 IC 卡号
//     *          insureIndividualVisitDTO:医保就诊病人传输对象
//     * @Author: fuhui
//     * @Email: 3277857701@qq.com
//     * @Date: 2020/12/10
//     * @Retrun: map
//     */
//    Map<String,Object> drawLoadAdvicePatient(Map map);

    /**
     * @Method deleteAdvice
     * @Desrciption 删除患者已录入医嘱
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/12 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<Boolean>deleteAdvice(Map map);


    /**
     * @Method: queryPage()
     * @Descrition: 分页查询住院病人信息
     * @Pramas:  insureIndividualVisitDTO:医保住院病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: pageDTO
     */
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method: queryAdvice()
     * @Descrition: 根据就诊id 查询医嘱信息
     * @Pramas: insureIndividualVisitDTO医保就诊病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/16
     * @Retrun: 医嘱分页信息
     */
    WrapperResponse<PageDTO> queryAdvicePage(Map map);

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @method BIZC300001
     * @author wangqiao
     * @date 2022/5/31 11:21
     * @description 工伤医保录入
     **/
    WrapperResponse<Boolean> BIZC300001(Map map);

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @method deleteInjuryAdvice
     * @author wang'qiao
     * @date 2022/6/9 14:16
     * @description
     **/
    WrapperResponse<Boolean> deleteInjuryAdvice(Map map);
}
