package cn.hsa.interf.nation.drug.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.interf.nation.service.NationDrugService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.nation.drug.controller
 * @Class_name: NationDrugController
 * @Describe: 国家三大贯标-药品
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-12-01 10:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/interf/nationDrug")
public class NationDrugController {

    @Resource
    private NationDrugService nationDrugService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     * 根据省份编码查询中心端的基础项目数据
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/11 14:07
     **/
    @GetMapping("/queryCenterNationStandardDrugPage")
    public WrapperResponse<PageDTO> queryCenterNationStandardDrugPage(NationStandardDrugDTO nationStandardMaterialDTO){
        if (StringUtils.isEmpty(nationStandardMaterialDTO.getHospCode())) {
            throw new AppException("未传入医院编码，请退出后重进");
        }
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", nationStandardMaterialDTO.getHospCode());
        // 查询 医院所在省份代码
        mapPatamater.put("code", "GJSDML_SF");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        if(null == sysParameterDTO){
            throw new AppException("未查询到医院代码为：【 "+nationStandardMaterialDTO.getHospCode()+" 】的`国家三大目录贯标省份`系统编码参数,请维护参数后再查询！");
        }
        // 省份代码
        String provinceCode = sysParameterDTO.getValue();
        nationStandardMaterialDTO.setProvinceCode(provinceCode);
        return nationDrugService_consumer.queryNationStandardDrugPage(nationStandardMaterialDTO);
    }
}
