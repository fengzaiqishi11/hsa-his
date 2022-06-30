package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drg.bo.InsureAdviceEntryBO;
import cn.hsa.module.insure.drg.service.InsureAdviceEntryService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @class_name: InsureAdviceEntryServiceImpl
 * @project_name: hsa-his
 * @Description: 医嘱录入信息上传服务实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 15:09
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureAdviceEntry")
@Service("insureAdviceEntryService_provider")
public class InsureAdviceEntryServiceImpl extends HsafService implements InsureAdviceEntryService {
    /**
     * 医嘱录入业务逻辑Bo层
     */
    @Resource
    private InsureAdviceEntryBO insureAdviceEntryBO;


    /**
     * @Method: adviceEntry()
     * @Descrition: 录入在院人员医嘱信息
     * @Pramas: inptVisitDTO住院
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    @Override
    public WrapperResponse<Boolean> saveAdviceEntry(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        return WrapperResponse.success(insureAdviceEntryBO.saveAdviceEntry(insureIndividualVisitDTO));
    }

    /**
     * @Method: drawoadAdvicePatient()
     * @Decription: 提取待录入医嘱人员信息
     * @Pramas: InsureIndividualVisitDTO 医保住院病人数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: map
     */
//    @Override
//    public WrapperResponse<Map<String, Object> >drawLoadAdvicePatient(Map map) {
//        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
//        return WrapperResponse.success(insureAdviceEntryBO.drawLoadAdvicePatient(insureIndividualVisitDTO));
//    }

    /**
     * @Method: queryPage()
     * @Descrition: 分页查询住院病人信息
     * @Pramas:  insureIndividualVisitDTO:医保住院病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: pageDTO
     */

    @Override
    public WrapperResponse<PageDTO > queryPage(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        PageDTO pageDTO = insureAdviceEntryBO.queryPage(insureIndividualVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: queryAdvice()
     * @Descrition: 根据就诊id 查询医嘱信息
     * @Pramas: insureIndividualVisitDTO医保就诊病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/16
     * @Retrun: 医嘱分页信息
     */
    @Override
    public WrapperResponse<PageDTO> queryAdvicePage(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        PageDTO pageDTO = insureAdviceEntryBO.queryAdvicePage(insureIndividualVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<Boolean> BIZC300001(Map map) {
        return insureAdviceEntryBO.BIZC300001(MapUtils.get(map, "insureIndividualVisitDTO"));
    }

    @Override
    public WrapperResponse<Boolean> deleteInjuryAdvice(Map map) {
        return insureAdviceEntryBO.deleteInjuryAdvice(MapUtils.get(map, "insureIndividualVisitDTO"));
    }

    @Override
    public WrapperResponse<Boolean> deleteAdvice(Map map) {
        return WrapperResponse.success(insureAdviceEntryBO.deleteAdvice(MapUtils.get(map,"insureIndividualVisitDTO")));
    }
}
