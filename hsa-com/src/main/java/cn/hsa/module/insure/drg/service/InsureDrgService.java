package cn.hsa.module.insure.drg.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import com.github.pagehelper.Page;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.drg.service
 * @class_name: InsureDrgService
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 18:45
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureDrgService {

    /**
     * @Method: queryPage()
     * @Descrition: 分页查询Drg分组信息
     * @Pramas: insureIndividualVisitDTO:visitNo医保登记号
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: Drg分组信息
     */
    WrapperResponse<PageDTO>queryPage(Map map);

    /**
     * @Method: queryInsureIndividualVisit()
     * @Descrition: 分页查询医保就诊表信息
     * @Pramas: insureIndividualVisitDTO数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: insureIndividualVisitDTO数据对象集合
     */
    WrapperResponse<PageDTO> queryInsureIndividualVisit(Map map);
}
