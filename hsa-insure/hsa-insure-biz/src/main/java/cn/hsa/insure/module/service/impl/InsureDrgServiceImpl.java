package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drg.bo.InsureDrgBO;
import cn.hsa.module.insure.drg.service.InsureDrgService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @class_name: InsureDrgServiceImpl
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 18:47
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureDrg")
@Service("insureDrg_provider")
public class InsureDrgServiceImpl extends HsafService implements InsureDrgService {

    /**
     * Drg业务实现层接口
     */
    @Resource
    private InsureDrgBO insureDrgBO;

    /**
     * @Method: queryPage()
     * @Descrition: 分页查询Drg分组信息
     * @Pramas: insureIndividualVisitDTO:visitNo医保登记号
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: Drg分组信息
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        return insureDrgBO.queryPage(insureIndividualVisitDTO);
    }


    /**
     * @Method: queryInsureIndividualVisit()
     * @Descrition: 分页查询医保就诊表信息
     * @Pramas: insureIndividualVisitDTO数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: insureIndividualVisitDTO数据对象集合
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureIndividualVisit(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        PageDTO pageDTO = insureDrgBO.queryInsureIndividualVisit(insureIndividualVisitDTO);
        return WrapperResponse.success(pageDTO);
    }
}
