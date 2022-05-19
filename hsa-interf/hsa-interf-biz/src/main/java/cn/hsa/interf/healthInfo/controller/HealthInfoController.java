package cn.hsa.interf.healthInfo.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.HealthPatientInfoService;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @description: lis结果上传
* @author: zhangxuan
* @date: 2021-07-05
**/
@RestController
@RequestMapping("interf/health/info")
@Slf4j
public class HealthInfoController extends BaseController {

    @Resource
    private HealthPatientInfoService healthPatientInfoService;

    /**
     * @Description: 查询患者基本信息
     * @Param: [PaitentBaseInfoDO]
     * @return: WrapperResponse<List<PaitentBaseInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-5-12 13:53
     */
    @PostMapping("queryBaseProfile")
    public WrapperResponse<List<TbHzjbxx>> queryBaseProfile(@RequestBody Map map){
        return healthPatientInfoService.queryBaseProfile(map);
    }

    /**
     * @Description: 查询患者基本信息
     * @Param: [OutptRegisterInfoDO]
     * @return: WrapperResponse<List<OutptRegisterInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-5-12 13:53
     */
    @PostMapping("queryRegisterInfo")
    public WrapperResponse<List<TbMzgh>> queryRegisterInfo(@RequestBody Map map){
        return healthPatientInfoService.queryRegisterInfo(map);
    }

    /**
     * @Description: 查询就诊信息
     * @Param: [map]
     * @return: WrapperResponse<List<OutptRegisterInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-05-10 17:01
     */
    @PostMapping("queryOutptVisitInfo")
    public WrapperResponse<List<TbMzjzjl>> queryOutptVisitInfo(@RequestBody Map map){
        return healthPatientInfoService.queryOutptVisitInfo(map);
    }

    /**
     * @Description: 查询处方信息
     * @Param: [map]
     * @return: WrapperResponse<List<OutptPrescribeInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-05-10 19:59
     */
    @PostMapping("queryPrescribeInfo")
    public WrapperResponse<List<TbMzcfzxx>> queryPrescribeInfo(@RequestBody Map map){
        return healthPatientInfoService.queryPrescribeInfo(map);
    }

    /**
     * @Description: 查询处方明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<OutptPrescribeInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-05-10 19:59
     */
    @PostMapping("queryOutptPrescribeDetailInfo")
    public WrapperResponse<List<TbMzcfmx>> queryOutptPrescribeDetailInfo(@RequestBody Map map){
        return healthPatientInfoService.queryOutptPrescribeDetailInfo(map);
    }


    /**
     * @Description: 查询处方执行信息
     * @Param: [map]
     * @return: WrapperResponse<List<OutptPrecribeDetailExecuteInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-05-11 16：47
     */
    @PostMapping("queryOutptPrescribeExecuteInfo")
    public WrapperResponse<List<TbMzcfzxjl>> queryOutptPrescribeExecuteInfo(@RequestBody Map map){
        return healthPatientInfoService.queryOutptPrescribeExecuteInfo(map);
    }

    /**
     * @Description: 查询门诊结算信息
     * @Param: [map]
     * @return: WrapperResponse<List<OutptSettleCostInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-05-11 16：55
     */
    @PostMapping("queryOutptSettleInfo")
    public WrapperResponse<List<TbMzsfhz>> queryOutptSettleInfo(@RequestBody Map map){
        return healthPatientInfoService.queryOutptSettleInfo(map);
    }

    /**
     * @Description: 查询门诊费用明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<OutptSettleCostInfoDO>>
     * @Author: liuliyun
     * @Date: 2022-05-11 19:36
     */
    @PostMapping("queryOutptSettleCostDetailInfo")
    public WrapperResponse<List<TbMzsfmx>> queryOutptSettleCostDetailInfo(@RequestBody Map map){
        return healthPatientInfoService.queryOutptSettleCostDetailInfo(map);
    }

    /**
     * @Description: 查询住院就诊信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptVisitInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-12 15:02
     */
    @PostMapping("queryInptVisitInfo")
    public WrapperResponse<List<TbZyjzjl>> queryInptVisitInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptVisitInfo(map);
    }

    /**
     * @Description: 查询住院转科信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptTurnDeptInfo>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-12 15:02
     */
    @PostMapping("queryInptTurnDeptInfo")
    public WrapperResponse<List<TbZkjl>> queryInptTurnDeptInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptTurnDeptInfo(map);
    }

    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptAdviceInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-12 20:02
     */
    @PostMapping("queryInptAdviceInfo")
    public WrapperResponse<List<TbZyyzmx>> queryInptAdviceInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptAdviceInfo(map);
    }

    /**
     * @Description: 查询住院医嘱执行信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptAdviceExecuteInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-12 20:02
     */
    @PostMapping("queryInptAdviceExecuteInfo")
    public WrapperResponse<List<TbZyyzzxjl>> queryInptAdviceExecuteInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptAdviceExecuteInfo(map);
    }


    /**
     * @Description: 查询住院收费明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptCostInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:02
     */
    @PostMapping("queryInptCostInfo")
    public WrapperResponse<List<TbZysfmx>> queryInptCostInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptCostInfo(map);
    }


    /**
     * @Description: 查询住院收费结算信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptSettleInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:27
     */
    @PostMapping("queryInptSettleInfo")
    public WrapperResponse<List<TbZysfjs>> queryInptSettleInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptSettleInfo(map);
    }

    /**
     * @Description: 查询住院三测单记录信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptThirdMeasurementDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 11:18
     */
    @PostMapping("queryInptThirdMeasurementInfo")
    public WrapperResponse<List<TbHlscdjl>> queryInptThirdMeasurementInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptThirdMeasurementInfo(map);
    }


    /**
     * @Description: 查询住院病历索引信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptEmrIndexInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:22
     */
    @PostMapping("queryEmrIndexInfo")
    public WrapperResponse<List<TbDzblsyxx>> queryEmrIndexInfo(@RequestBody Map map){
        return healthPatientInfoService.queryEmrIndexInfo(map);
    }

    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptNurseRecordInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:05
     */
    @PostMapping("queryInptNurseRecordInfo")
    public WrapperResponse<List<TbEmrYbhljl>> queryInptNurseRecordInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptNurseRecordInfo(map);
    }


    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptNurseRecordInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 09:43
     */
    @PostMapping("queryInptEmrOutInfo")
    public WrapperResponse<List<TbEmrCyxj>> queryInptEmrOutInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptEmrOutInfo(map);
    }

    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptBedInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:36
     */
    @PostMapping("queryInptBedInfo")
    public WrapperResponse<List<TbZycwjl>> queryInptBedInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptBedInfo(map);
    }


    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptMrisInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:01
     */
    @PostMapping("queryInptMrisInfo")
    public WrapperResponse<List<TbBasyztb>> queryInptMrisInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptMrisInfo(map);
    }


    /**
     * @Description: 查询住院病案首页-诊断信息
     * @Param: [map]
     * @return: WrapperResponse<List<InptMrisInfoDO>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:37
     */
    @PostMapping("queryInptMrisDiagnoseInfo")
    public WrapperResponse<List<TbBasyzdmx>> queryInptMrisDiagnoseInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptMrisDiagnoseInfo(map);
    }

    /**
     * @Description: 查询住院病案首页-诊断信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyssmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 10:37
     */
    @PostMapping("queryInptMrisOperInfo")
    public WrapperResponse<List<TbBasyssmx>> queryInptMrisOperInfo(@RequestBody Map map){
        return healthPatientInfoService.queryInptMrisOperInfo(map);
    }

}
