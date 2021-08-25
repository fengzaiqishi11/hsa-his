package cn.hsa.inpt.nurse.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.compositequery.bo.CompositeQueryBO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.NursingRecordBO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;
import cn.hsa.module.inpt.nurse.service.NursingRecordService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.nurse.service.impl
 * @Class_name: NursingRecordServiceImpl
 * @Describe: 护理记录service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nursingRecord")
@Service("nursingRecordService_provider")
public class NursingRecordServiceImpl extends HsafService implements NursingRecordService {

    @Resource
    private NursingRecordBO nursingRecordBO;

    @Resource
    private CompositeQueryBO compositeQueryBO;

    /**
     * @Method queryInptVisitAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptVisitAll(Map map) {
       // return WrapperResponse.success(nursingRecordBO.queryAll(MapUtils.get(map, "inptVisitDTO")));
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = nursingRecordBO.queryAll(inptVisitDTO);
        return  WrapperResponse.success(pageDTO);
    }
    /**
     * @Method queryInptVisitAll
     * @Desrciption 查询所有住院病人,预交金缴纳情况
     * @Param inptVisitDTO-住院病人DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryAcceptGold(Map map) {
        // return WrapperResponse.success(nursingRecordBO.queryAll(MapUtils.get(map, "inptVisitDTO")));
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        List<Map<String, Object>> acceptGoldMap = nursingRecordBO.queryAcceptGold(inptVisitDTO);
        return  WrapperResponse.success(acceptGoldMap);
    }


    /**
     * @Method save
     * @Desrciption 保存护理记录(新增 、 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
//        return WrapperResponse.success(nursingRecordBO.save(map));
        return WrapperResponse.success(nursingRecordBO.saveByGroup(map));
    }

    /**
     * @Method delete
     * @Desrciption 删除护理记录
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(nursingRecordBO.delete(map));
    }

    /**
     * @Method queryNursingRecord
     * @Desrciption 分页查询护理单记录
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryNursingRecord(Map map) {
        return WrapperResponse.success(nursingRecordBO.queryNursingRecord(MapUtils.get(map, "inptNurseRecordDTO")));
    }

    /**
     * @Method queryNurseRecordByPrint
     * @Desrciption 护理记录打印接口
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<InptNurseRecordDTO>>
     **/
    @Override
    public WrapperResponse<List<InptNurseRecordDTO>> queryNurseRecordByPrint(Map map) {
        return WrapperResponse.success(nursingRecordBO.queryNurseRecordByPrint(MapUtils.get(map, "inptNurseRecordDTO")));
    }

    /**
     * @Method addDaySummary
     * @Desrciption 添加日间小结
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> addDaySummary(Map map) {
        return WrapperResponse.success(nursingRecordBO.addDaySummary(map));
    }

    /**
     * @Method getValue
     * @Desrciption 获取分割参数
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Map<String,String>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> getValue(Map map) {
        return WrapperResponse.success(nursingRecordBO.getValue(map));
    }

    /**
     * @Method getMaxGroupNo
     * @Desrciption 获取当前人员最大组号
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Integer
     **/
    @Override
    public WrapperResponse<Integer> getMaxGroupNo(Map map) {
        return WrapperResponse.success(nursingRecordBO.getMaxGroupNo(MapUtils.get(map, "inptNurseRecordDTO")));
    }
}
