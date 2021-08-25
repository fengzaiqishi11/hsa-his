package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptquery.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.outptquery.service.OutptRegisterQueryService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @class_name: OutptRegisterQueryController
 * @Description: 门诊挂号查询控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/10 15:11
 * @Company: 创智和宇
 **/

@RestController
@RequestMapping("/web/outpt/outptRegisterQuery")
public class OutptRegisterQueryController extends BaseController {

    /**
     * 门诊挂号查service层 消费者
     */
    @Resource
    private OutptRegisterQueryService outptRegisterQueryService_consumer;

    /**
     * @Method: queryPage()
     * @Description: 门诊挂号分页查询
     * @Param: map
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/10 14:38
     * @Return: outptRegisterDTO门诊挂号数据传输对象集合
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptRegisterDTO outptRegisterDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        Map map =new HashMap();
        outptRegisterDTO.setHospCode(userDTO.getHospCode());
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptRegisterDTO", outptRegisterDTO);
        return this.outptRegisterQueryService_consumer.queryPage(map);
    }

}
