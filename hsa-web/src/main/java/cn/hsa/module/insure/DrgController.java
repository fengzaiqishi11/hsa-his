package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drg.service.InsureDrgService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: DrgController
 * @project_name: hsa-his
 * @Description: Drg业务控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 18:03
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/drg")
@Slf4j
public class DrgController extends BaseController {

    /**
     * Drg业务服务层
     */
    @Resource
    private InsureDrgService insureDrgService_consumer;

    /**
     * @Method: queryPage()
     * @Descrition: 分页查询Drg分组信息
     * @Pramas: insureIndividualVisitDTO:visitNo医保登记号
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: Drg分组信息
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureDrgService_consumer.queryPage(map);
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
    @GetMapping("/queryInsureIndividualVisitPage")
    public WrapperResponse<PageDTO> queryInsureIndividualVisitPage(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureDrgService_consumer.queryInsureIndividualVisit(map);
    }

}
