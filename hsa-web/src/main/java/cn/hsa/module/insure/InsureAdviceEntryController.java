package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.drg.service.InsureAdviceEntryService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InusreAdviceEntryController
 * @project_name: hsa-his
 * @Description: 医嘱录入信息上传业务控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 14:57
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureAdviceEntry")
@Slf4j
public class InsureAdviceEntryController extends BaseController {

    /**
     * 医嘱录入信息服务层接口
     */
    @Resource
    private InsureAdviceEntryService insureAdviceEntryService_consumer;


    /**
     * @Method: adviceEntry()
     * @Descrition: 录入在院人员医嘱信息
     * @Pramas: inptVisitDTO住院
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    @PostMapping("/saveAdviceEntry")
    public WrapperResponse<Boolean> saveAdviceEntry(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setCrteId(sysUserDTO.getId());
        insureIndividualVisitDTO.setCrteName(sysUserDTO.getName());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());

        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureAdviceEntryService_consumer.saveAdviceEntry(map);
    }

    /**
     * @param insureIndividualVisitDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @method BIZC300001
     * @author wangqiao
     * @date 2022/5/31 11:05
     * @description 工伤医嘱录入
     **/
    @PostMapping("/uploadInjuryAdvice")
    public WrapperResponse<Boolean> BIZC300001(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setCrteId(sysUserDTO.getId());
        insureIndividualVisitDTO.setCrteName(sysUserDTO.getName());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        return insureAdviceEntryService_consumer.BIZC300001(map);
    }

    /**
     * @param insureIndividualVisitDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @method BIZC300001
     * @author wangqiao
     * @date 2022/5/31 11:05
     * @description 工伤医嘱删除
     **/
    @PostMapping("/deleteInjuryAdvice")
    public WrapperResponse<Boolean> deleteInjuryAdvice(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setCrteId(sysUserDTO.getId());
        insureIndividualVisitDTO.setCrteName(sysUserDTO.getName());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        return insureAdviceEntryService_consumer.deleteInjuryAdvice(map);
    }


    /**
     * @Method: queryPage()
     * @Descrition: 分页查询住院病人信息
     *   这里的医嘱录入上传 只有病人结算以后 才能进行医嘱录入操作
     * @Pramas:  insureIndividualVisitDTO:医保住院病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/11
     * @Retrun: pageDTO
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());

        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureAdviceEntryService_consumer.queryPage(map);
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
    @GetMapping("/queryAdvicePage")
    public WrapperResponse <PageDTO> queryAdvicePage(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
       if(StringUtils.isEmpty(insureIndividualVisitDTO.getVisitId())){
           throw new AppException("查询医嘱信息的就诊Id参数为空");
       }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());

        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureAdviceEntryService_consumer.queryAdvicePage(map);
    }

    @PostMapping("/deleteAdvice")
    public WrapperResponse<Boolean> deleteAdvice(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureAdviceEntryService_consumer.deleteAdvice(map);
    }

}
