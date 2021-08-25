package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.service.InsureIndividualSettleService;
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
 * @Class_name:: InsureIndividualSettleController
 * @Description:  医保个人结算查询控制层
 * @Author: fuhui
 * @Date: 2020/11/5 9:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureIndividualSettle")
@Slf4j
public class InsureIndividualSettleController extends BaseController {

    /**
     * 医保个人结算查询业务服务层接口
     */
    @Resource
    private InsureIndividualSettleService insureIndividualSettleService_consumer;


    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象集合
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureIndividualSettleDTO insureIndividualSettleDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureIndividualSettleDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureIndividualSettleDTO",insureIndividualSettleDTO);
        return insureIndividualSettleService_consumer.queryPage(map);
    }

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象
     **/
    @GetMapping("/getById")
    public WrapperResponse<InsureIndividualSettleDTO> getById(InsureIndividualSettleDTO insureIndividualSettleDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureIndividualSettleDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureIndividualSettleDTO",insureIndividualSettleDTO);
        return insureIndividualSettleService_consumer.getById(map);
    }
}
