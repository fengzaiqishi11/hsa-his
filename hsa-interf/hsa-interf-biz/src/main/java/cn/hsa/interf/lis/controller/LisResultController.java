package cn.hsa.interf.lis.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.interf.phys.service.LisResultService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description: lis结果上传
* @author: zhangxuan
* @date: 2021-07-05
**/
@RestController
@RequestMapping("interf/lis/lisResult")
@Slf4j
public class LisResultController extends BaseController {

    @Resource
    private LisResultService lisResultService;

    /**
     * @Description: 保存lis结果
     * @Param: [medicalApplyDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @PostMapping("saveLisResult")
    public Boolean saveLisResult(@RequestBody List<MedicalApplyDTO> medicalApplyDTO){

        Map map = new HashMap<>();
//        map.put("hospCode",hospCode);
        map.put("medicalApplyDTO", medicalApplyDTO);

        return lisResultService.saveLisResult(map);
    }

    /**
     * @Description: 查找没有lis结果的就诊人的就诊号（）
     * @Param: [outptVisitDTOList]
     * @return: java.util.List<cn.hsa.module.medic.apply.dto.MedicalApplyDTO>
     * @Author: zhangxuan
     * @Date: 2021-07-06
     */
//    @PostMapping("queryNoResult")
//    public List<MedicalApplyDTO> queryNoResult(){
//
//        Map map = new HashMap();
//        map.put("hospCode", hospCode);
//
//        return lisResultService.queryNoResult(map);
//    }

    /**
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    @PostMapping("insertLisResult")
    public Map insertLisResult(@RequestBody Map map){
        List<Map> medicalResultDTOList = MapUtils.get(map, "lisResult");
        map.put("hospCode",MapUtils.get(medicalResultDTOList.get(0),"hospCode"));
        return lisResultService.insertLisResult(map);

    }

    /**
     * @Description: 医嘱目录信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("queryAdvice")
    public Map queryAdvice(@RequestBody Map map){

        map.put("typeCode", "3");

        return lisResultService.queryAdvice(map);

    }

    /**
     * @Description: 科室信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("queryDept")
    public Map queryDept(@RequestBody Map map){

//        map.put("hospCode",hospCode);
//        map.put("crteName", userName);
//        map.put("crteId",userId);

        return lisResultService.queryAdvice(map);

    }

    /**
     * @Description: 用户信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("queryUser")
    public Map queryUser(@RequestBody Map map){

//        map.put("hospCode",hospCode);
//        map.put("crteName", userName);
//        map.put("crteId",userId);

        return lisResultService.queryAdvice(map);

    }

    /**
     * @Description: 测试
     * @Param: []
     * @return: java.lang.String
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("test")
    public String aaa(){
        return "111111111111";
    }



}
