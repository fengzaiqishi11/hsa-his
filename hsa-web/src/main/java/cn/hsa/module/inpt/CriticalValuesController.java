package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValueItemDTO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValuesDTO;
import cn.hsa.module.inpt.criticalvalues.service.CriticalValuesService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @class_name: CriticalValuesController
 * @Description: 危急值控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/1/715:10
 * @Company: 创智和宇
 **/
@RestController
@Slf4j
@RequestMapping("/web/inpt/criticalValues")
public class CriticalValuesController extends BaseController {

    /**
     * 危急值服务层接口
     */
    @Resource
    private CriticalValuesService criticalValuesService_consumer;


    /**
     * @Method: queryPage()
     * @Description: 根据就诊id,医院编码查询危急值数据
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2021/1/7 14:14
     * @Return: PageDTO
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO>queryPage(CriticalValuesDTO criticalValuesDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        criticalValuesDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("criticalValuesDTO",criticalValuesDTO);
        return criticalValuesService_consumer.queryPage(map);
    }

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     *               危机值处理以后，修改危机值状态
     *               如果该病人没有危急值了，则修改住院病人表的危急值状态
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @PostMapping("/updateCriticalValue")
    public WrapperResponse<Boolean>updateCriticalValue(@RequestBody List<CriticalValuesDTO> criticalValuesDTOList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(ListUtils.isEmpty(criticalValuesDTOList)){
            throw new AppException("要修改危急值的数据集合为空");
        }
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        criticalValuesDTOList.forEach(criticalValuesDTO -> {
            criticalValuesDTO.setHospCode(sysUserDTO.getHospCode());
            // 如果危急值处理备注不为空，说明未处理
            if(!StringUtils.isEmpty(criticalValuesDTO.getWjclbz())){
                criticalValuesDTO.setWjclrid(sysUserDTO.getId());          // 危急处理人ID
                criticalValuesDTO.setWjclrxm(sysUserDTO.getName());          // 危急处理人姓名
                criticalValuesDTO.setWjclrsj(DateUtils.getNow()); // 危急处理人时间
                // 如果该项目是危机项目，则是危机处理
                if(Constants.WJZZT.YWJZ.equals(criticalValuesDTO.getWjzzt())){
                    criticalValuesDTO.setIsWjcl(Constants.SF.S); // 是否危机值处理
                }
                else {
                    criticalValuesDTO.setIsWjcl(Constants.SF.F); // 是否危机值处理
                }
                criticalValuesDTO.setWjzzt(Constants.WJZZT.YCLWJZ); // 危急值状态变为正常
            }
        });
        map.put("criticalValuesDTOList",criticalValuesDTOList);
        return criticalValuesService_consumer.updateCriticalValue(map);
    }

    /**
     * @Method: queryItemByVisitId()
     * @Description: 根据就诊查看检查项目类型，查询医嘱类型是lis医技的
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @GetMapping("/queryItemByVisitId")
    public  WrapperResponse<PageDTO> queryItemByVisitId(CriticalValueItemDTO criticalValueItemDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        criticalValueItemDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("criticalValueItemDTO",criticalValueItemDTO);
        return criticalValuesService_consumer.queryItemByVisitId(map);
    }
}
