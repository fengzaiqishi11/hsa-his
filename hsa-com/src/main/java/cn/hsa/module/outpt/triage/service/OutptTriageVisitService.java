package cn.hsa.module.outpt.triage.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.sys.redis.bo.RedisBO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
   * 分诊队列服务层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/22 17:07
**/
@FeignClient(value = "hsa-outpt")
public interface OutptTriageVisitService {

    /**
     * 新增就诊病人到队列中
     *
     * @Method: insertOutptTriageVisit
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/

    WrapperResponse<Boolean> insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 更新就诊病人到队列中
     *
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    WrapperResponse<Boolean> updateOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 查询分诊病人列表
     *
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    WrapperResponse<List<OutptTriageVisitDTO>> queryOutptTriageVisitPage(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 删除就诊队列中的病人信息
     *
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    WrapperResponse<Boolean> deleteOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 分页查询分诊病人信息
     *
     * @param map
     * @return
     */
    @GetMapping("/service/output/outptTriageVisit/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
       * 排队叫号-叫号接口，根据挂号信息进行叫号
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/30 19:30
    **/
    @PostMapping("/service/output/outptTriageVisit/callNumberInTheQueue")
    WrapperResponse<Map<String,Object>> updateCallNumberInTheQueue(Map<String,Object> map);

    /**
       * 排队叫号-过号接口，根据挂号信息进行过号
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/30 19:30
    **/
    @PostMapping("/service/output/outptTriageVisit/numberMiss")
    WrapperResponse<Boolean> updateNumberMiss(Map<String,Object> map);
    /**
     * @Method
     * @Desrciption 修改分诊病人信息
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     **/
    WrapperResponse<Boolean> updateOutptTriageVisitById(Map map);

    /**
     * @Method
     * @Desrciption 根据诊室信息和队列日期查询有哪些医生坐诊
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     **/
    WrapperResponse<List<SysUserDTO>> getDoctorByClinicIdAndQueueDate(Map map);

    /**
       * 排队叫号分诊病人查询，根据医院编码与分诊台编码进行查询
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/5 10:04
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    WrapperResponse<List<OutptTriageVisitDTO>> queryOutptTriageVisit(Map map);
}
