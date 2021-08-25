package cn.hsa.module.insure.drg.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;

/**
 * @Package_name: cn.hsa.module.insure.drg.bo
 * @class_name: InsureDrgBO
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 18:45
 * @Company: 创智和宇
 **/
public interface InsureDrgBO {

    /**
     * @Method: queryPage()
     * @Descrition: 分页查询Drg分组信息
     * @Pramas: insureIndividualVisitDTO:visitNo医保登记号
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: Drg分组信息
     */
    WrapperResponse<PageDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryInsureIndividualVisit()
     * @Descrition: 分页查询医保就诊表信息
     * @Pramas: insureIndividualVisitDTO数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: insureIndividualVisitDTO数据对象集合
     */
    PageDTO queryInsureIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);
}
