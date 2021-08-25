package cn.hsa.inpt.bedlist.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.bedlist.bo.BedListBO;
import cn.hsa.module.inpt.bedlist.service.BedListService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.bedlist.server.impl
 * @Class_name: BedListServerImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/8 15:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/bedlist")
@Service("bedListService_provider")
public class BedListServiceImpl implements BedListService {

    @Resource
    BedListBO bedListBO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询床位信息
     * @params [map]
     * @Author chenjun
     * @Date 2020/9/8 15:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryPage(Map map) {
        // 在床病人
        List<InptVisitDTO> inBedList = bedListBO.queryPage(MapUtils.get(map, "inptVisitDTO"));
        // 待安床病人
        List<InptVisitDTO> waitBedList = bedListBO.queryWaitVisitAll(MapUtils.get(map, "inptVisitDTO"));

        // 结果MAP
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("inBedList", inBedList);
        retMap.put("waitBedList", waitBedList);
        return WrapperResponse.success(retMap);
    }

    /**
     * @Method 床位变动接口
     * @Description
     * 1、安床 = '0'
     * 2、换床 = '1'
     * 3、包床 = '2'
     * 4、转科 = '3'
     * 5、包床取消 = '4'
     * 6、预出院 = '5'
     * 7、出院召回/召回费用 = '6'
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 11:03
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> saveBedChange(Map map) {
        return WrapperResponse.success(bedListBO.saveBedChange(map));
    }

    /**
     * @Method 根据病区查询科室信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 9:43
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryDeptByWardId(Map map) {
        Map<String, Object> respMap = new HashMap();
        respMap.put("deptId", MapUtils.get(map, "deptId"));
        respMap.put("deptList", bedListBO.queryDeptByWardId(map));
        return WrapperResponse.success(respMap);
    }
}