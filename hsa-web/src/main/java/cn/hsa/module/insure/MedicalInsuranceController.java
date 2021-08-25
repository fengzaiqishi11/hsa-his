package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureIndividualBasicService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.insure
 * @Class_name: medicalInsuranceController
 * @Description: 医保接口控制层
 * @Author: marong
 * @Date: 　2020/10/13　　09点24分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/insure/medicalInsurance")
@Slf4j
public class MedicalInsuranceController extends BaseController {


    @Resource
    private InsureIndividualBasicService insureIndividualBasicService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    /**
    * @Method queryAll
    * @Param [insureIndividualBasicDTO]
    * @description   数据库查询患者信息
    * @author marong
    * @date 2020/11/4 20:48
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
    * @throws
    */
    @RequestMapping("queryAll")
    public WrapperResponse<List<InsureIndividualBasicDTO>> queryAll(InsureIndividualBasicDTO insureIndividualBasicDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualBasicDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
        WrapperResponse<List<InsureIndividualBasicDTO>> insureIndividualBasicList = insureIndividualBasicService_consumer.queryAll(map);
        return insureIndividualBasicList;
    }


}
