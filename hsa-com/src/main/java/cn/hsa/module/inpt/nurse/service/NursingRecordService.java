package cn.hsa.module.inpt.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-inpt")
public interface NursingRecordService {
    /**
     * @Method queryInptVisitAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/nursingRecord/queryInptVisitAll")
    WrapperResponse<PageDTO> queryInptVisitAll(Map map);

    /**
     * @Method queryInptVisitAll
     * @Desrciption 查询所有住院病人,预交金缴纳情况
     * @Param inptVisitDTO-住院病人DTO
     * @Author: lizihuan
     * @Eamil: lizihuan@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return
     **/
    @GetMapping("/service/inpt/nursingRecord/queryAcceptGold")
    WrapperResponse<List<Map<String, Object>>>queryAcceptGold(Map map);

    /**
     * @Method save
     * @Desrciption 保存护理记录(新增 、 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/inpt/nursingRecord/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method delete
     * @Desrciption 删除护理记录
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/inpt/nursingRecord/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Method queryNursingRecord
     * @Desrciption 分页查询护理单记录
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/nursingRecord/queryNursingRecord")
    WrapperResponse<PageDTO> queryNursingRecord(Map map);

    /**
     * @Method queryNurseRecordByPrint
     * @Desrciption 护理记录打印接口
     * @Param inptNurseRecordDTO-护理记录DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<InptNurseRecordDTO>>
     **/
    @GetMapping("/service/inpt/nursingRecord/queryNurseRecordByPrint")
    WrapperResponse<List<InptNurseRecordDTO>> queryNurseRecordByPrint(Map map);

    /**
     * @Method addDaySummary
     * @Desrciption 添加日间小结
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/nursingRecord/addDaySummary")
    WrapperResponse<Boolean> addDaySummary(Map map);

    /**
     * @Method getValue
     * @Desrciption 获取分割参数
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Map<String,String>
     **/
    @GetMapping("/service/inpt/nursingRecord/getValue")
    WrapperResponse<Map<String, Object>> getValue(Map map);

    /**
     * @Method getMaxGroupNo
     * @Desrciption 获取当前人员最大组号
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Integer
     **/
    @GetMapping("/service/inpt/nursingRecord/getMaxGroupNo")
    WrapperResponse<Integer> getMaxGroupNo(Map map);
}
