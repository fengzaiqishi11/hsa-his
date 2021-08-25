package cn.hsa.module.phar;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharoutbackdrug.service.OutBackDrugService;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro
 * @Class_name: OutBackDrugController
 * @Describe: 门诊退药控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/outBackDrug")
@Slf4j
public class OutBackDrugController extends BaseController {

    @Resource
    OutBackDrugService outBackDrugService_consumer;

    /**
     * @Menthod queryOutBackDrugPeoplePage
     * @Desrciption   根据姓名，身份证和手机号分页查询退药人
     * @param pharOutDistributeDTO 门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 11:05
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/queryOutBackDrugPeoplePage")
    public WrapperResponse<PageDTO> queryOutBackDrugPeoplePage(PharOutDistributeDTO pharOutDistributeDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutDistributeDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharOutDistributeDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("pharOutDistributeDTO",pharOutDistributeDTO);
        return outBackDrugService_consumer.queryOutBackDrugPeoplePage(map);
    }


    /**
     * @Menthod queryOutBackDrugDetailPage
     * @Desrciption  根据发药id分页查询其所有的发药单和退药单
     * @param pharOutDistributeDTO 门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 11:05
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/queryOutBackDrugDetailPage")
    public WrapperResponse<PageDTO> queryOutBackDrugDetailPage(PharOutDistributeDTO pharOutDistributeDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutDistributeDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("pharOutDistributeDTO",pharOutDistributeDTO);
        return outBackDrugService_consumer.queryOutBackDrugDetailPage(map);
    }

    /**
     * @Menthod updateOutBackDrug
     * @Desrciption  门诊退药，更新费用表退药数量，更新原发药记录退药数量，新增一条负记录到发药表
     * @param pharOutDistributeDTO 门诊发药详细表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 11:03
     * @Return boolean
     **/
    @PostMapping("/updateBackNumAndInsertDistribute")
    public WrapperResponse<Boolean> updateBackNumAndInsertDistribute(@RequestBody PharOutDistributeDTO pharOutDistributeDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        pharOutDistributeDTO.setDistUserName(sysUserDTO.getName());
        pharOutDistributeDTO.setDistUserId(sysUserDTO.getId());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharOutDistributeDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("pharOutDistributeDTO",pharOutDistributeDTO);
        return outBackDrugService_consumer.updateBackNumAndInsertDistribute(map);
    }



}
