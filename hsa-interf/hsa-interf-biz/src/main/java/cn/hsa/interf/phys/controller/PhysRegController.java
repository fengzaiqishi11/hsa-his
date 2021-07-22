package cn.hsa.interf.phys.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.phys.dto.PhysDTO;
import cn.hsa.module.interf.phys.dto.PhysRegisterDTO;
import cn.hsa.module.interf.phys.dto.PhysSettleDTO;
import cn.hsa.module.interf.phys.service.PhysRegService;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* @description: 体检登记体检者信息
* @author: zhangxuan
* @date: 2021-06-18
**/
@RestController
@RequestMapping("interf/phys")
@Slf4j
public class PhysRegController extends BaseController {
    @Resource
    private PhysRegService physRegService;

    /**
     * @Description: 获取体检者登记信息
     * @Param: [physDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @PostMapping("/regPhysInfo")
    public WrapperResponse<Map> regPhysInfo(@RequestBody PhysDTO physDTO){
        // 判断医院编码是否为空，医院编码在需要解密，所以那边传过来的医院编码需要加密，现在测试阶段直接判断是否为空就行
        Optional.ofNullable(physDTO.getHospCode()).orElseThrow(()-> new AppException("医院编码解析失败"));
        Map map = new HashMap();
//        map.put("hospCode",hospCode);
        map.put("physDTO",physDTO);

        return physRegService.regPhysInfo(map);
    }
    /**
     * @Method addVisitByPhys
     * @Desrciption 新增登记一次就要 同步一次就诊的数据
     * @Param [physRegisterDTO]
     * @Author zhangguorui
     * @Date   2021/7/14 13:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @PostMapping("/addVisitByPhys")
    public WrapperResponse<Boolean> addVisitByPhys(@RequestBody PhysRegisterDTO physRegisterDTO) {
        Optional.ofNullable(physRegisterDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        // 获得医院编码 一般需要做解密操作，但phys传过来的没有做加密处理 所以暂时不解密
        String hospCode = physRegisterDTO.getHospCode();
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("physRegisterDTO", physRegisterDTO);
        return physRegService.addVisitByPhys(map);
    }
    /**
     * @Method addOrUpdateOutptCost
     * @Desrciption 批量插入费用表
     * @Param [settleDTOS]
     * @Author zhangguorui
     * @Date 2021/7/14 15:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @PostMapping("/addOrUpdateOutptCost")
    public WrapperResponse<Boolean> addOrUpdateOutptCost(@RequestBody List<PhysSettleDTO> settleDTOS) {
        if (ListUtils.isEmpty(settleDTOS)) {
            throw new AppException("上传的费用数据不能为空");
        }
        PhysSettleDTO physSettleDTO = settleDTOS.get(0);
        String hospCode = physSettleDTO.getHospCode();
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("settleDTOS", settleDTOS);
        return physRegService.addOrUpdateOutptCost(map);
    }
}
