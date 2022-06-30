package cn.hsa.inpt.bedlist.bo.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.inpt.bedlist.bo.BedListBO;
import cn.hsa.module.inpt.bedlist.dao.BedListDAO;
import cn.hsa.module.inpt.bedlist.dto.InptLongCostDTO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.longcost.bo.BedLongCostBO;
import cn.hsa.module.mris.mrisHome.entity.InptBedChangeDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.inpt.bedlist.bo.impl
 * @Class_name: BedListBoImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/8 15:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BedListBOImpl implements BedListBO {

    @Resource
    private BedListDAO bedListDAO;

    @Resource
    private BedLongCostBO bedLongCostBO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    private InptCostDAO inptCostDAO;

    @Resource
    private SysParameterService sysParameterService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SysParameterService sysParameterService_consumer;

//    @Resource
//    private MessageInfoDAO messageInfoDAO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询床位信息
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/8 15:25
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public List<InptVisitDTO> queryPage(InptVisitDTO inptVisitDTO) {
        return bedListDAO.queryPage(inptVisitDTO);
    }

    /**
     * @Method queryWaitVisitAll
     * @Desrciption 查询待安床病人
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/10 11:34
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    @Override
    public List<InptVisitDTO> queryWaitVisitAll(InptVisitDTO inptVisitDTO) {
        return bedListDAO.queryWaitVisitAll(inptVisitDTO);
    }

    /***************************************zhongming added by 20201222 begin*****************************************/
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
    public List<Map<String, Object>> queryDeptByWardId(Map map) {
        return bedListDAO.queryDeptByWardId(map);
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
    public Boolean saveBedChange(Map map) {
        // 异动类型
        String changeCode = MapUtils.getEmptyErr(map, "changeCode", "床位异动失败：异动类型不能为空");
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        String key =new StringBuilder(hospCode + visitId + changeCode).toString();
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            throw new AppException("当前患者正在操作，稍后刷新界面再试");
        }
        try {
            // 使用redis锁病人，并设置自动过期时间10秒，防止异常情况redis不会自动清除的问题
            redisUtils.set(key, visitId, 10);
            switch (changeCode) {
                // 安床
                case Constants.YDLX.AC:
                    handleAc(map);
                    break;
                // 包床
                case Constants.YDLX.BC:
                    handleBc(map);
                    break;
                // 包床取消
                case Constants.YDLX.BCQX:
                    handleBcqx(map);
                    break;
                // 换床
                case Constants.YDLX.HC:
                    handleHc(map);
                    break;
                // 转科
                case Constants.YDLX.ZK:
                    handleZk(map);
                    break;
                // 预出院
                case Constants.YDLX.YCY:
                    handleYcy(map);
                    break;
                // 出院召回
                case Constants.YDLX.CYZH:
                    // 召回类型
                    String handleCode = MapUtils.getEmptyErr(map, "handleCode", "出院召回失败：召回类型不能为空");
                    // 合法性校验
                    if (!StringUtils.inString(handleCode, "0", "1")) {
                        throw new AppException("出院召回失败：召回类型不合法");
                    }
                    // 继续住院
                    if ("0".equals(handleCode)) {
                        handleCyzh_Jxzy(map);
                    }
                    // 召回费用
                    else {
                        handleCyzh_Zhfy(map);
                    }
                    break;
                // 转换科室
                case Constants.YDLX.ZHC :
                    handleHk(map);
                    break;
                default:
                    throw new AppException("床位异动失败：异动类型参数不匹配");
            }
        } finally {
            redisUtils.del(key);//删除结算key
        }
        return true;
    }

    /**
     * @Method 生成单据号
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/24 21:49
     * @Return
     **/
    private String getOrderNo(String hospCode, String typeCode) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("typeCode", typeCode);
        WrapperResponse<String> wr = baseOrderRuleService_consumer.getOrderNo(map);
        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        String orderNo = wr.getData();
        if (StringUtils.isEmpty(orderNo)) {
            throw new AppException("生成单据号为空");
        }
        return orderNo;
    }

    /**
     * @Method 根据就诊ID查询住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/19 20:43
     * @Return
     **/
    private InptVisitDTO getInptVisitById(String changeCode, Map map) {
        String visitId = MapUtils.get(map, "visitId");
        String deptId = MapUtils.get(map, "deptId");
        String deptName = MapUtils.get(map, "deptName");
        InptVisitDTO inptVisitDTO = bedListDAO.getInptVisitById(visitId);
        // 住院就诊病人为空
        if (inptVisitDTO == null) {
            throw new AppException("未查询到住院病人信息");
        }
        // 安床
        if (Constants.YDLX.AC.equals(changeCode)) {
            if (!Constants.BRZT.DR.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("安床失败：病人不是待安床状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("安床失败：病人非【" + deptName + "】科室病人");
            }
        }
        // 包床
        else if(Constants.YDLX.BC.equals(changeCode)) {
            if (!Constants.BRZT.ZY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("包床失败：病人不是在院状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("包床失败：病人非【" + deptName + "】科室病人");
            }
        }
        // 包床取消
        else if(Constants.YDLX.BCQX.equals(changeCode)) {
            if (!Constants.BRZT.ZY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("取消包床失败：病人不是在院状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("取消包床失败：非【" + deptName + "】科室病人");
            }
        }
        // 换床
        else if(Constants.YDLX.HC.equals(changeCode)) {
            if (!Constants.BRZT.ZY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("换床失败：病人不是在院状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("换床失败：病人非【" + deptName + "】科室病人");
            }
        }
        // 转科
        else if(Constants.YDLX.ZK.equals(changeCode)) {
            if (!Constants.BRZT.ZY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("转科失败：病人不是在院状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("转科失败：病人非【" + deptName + "】科室病人");
            }
        }
        // 预出院
        else if(Constants.YDLX.YCY.equals(changeCode)) {
            if (!Constants.BRZT.ZY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("预出院失败：病人不是在院状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("预出院失败：病人非【" + deptName + "】科室病人");
            }
        }
        // 出院召回
        else if(Constants.YDLX.CYZH.equals(changeCode)) {
            if (!Constants.BRZT.YCY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("出院召回失败：病人不是预出院状态");
            }
            if (!deptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("出院召回失败：病人非【" + deptName + "】科室病人");
            }
        }
        return inptVisitDTO;
    }

    /**
     * @Method 根据床位ID查询床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/19 20:43
     * @Return
     **/
    private BaseBedDTO getBaseBedById(String changeCode, Map map) {
        String bedId = MapUtils.get(map, "bedId");
        String deptId = MapUtils.get(map, "deptId");
        String deptName = MapUtils.get(map, "deptName");
        // 获取床位信息
        BaseBedDTO baseBedDTO = bedListDAO.getBaseBedById(bedId);
        // 床位是否存在
        if (baseBedDTO == null) {
            throw new AppException("床位基础信息已丢失，请联系管理员");
        }
        // 床位是否失效
        if (!Constants.SF.S.equals(baseBedDTO.getIsValid())) {
            throw new AppException("床位【" + baseBedDTO.getName() + "】不是有效床位");
        }
        // 床位所属科室和当前操作科室不一致
        if (!deptId.equals(baseBedDTO.getDeptId())) {
            throw new AppException("床位【" + baseBedDTO.getName() + "】不属于【" + deptName + "】科室");
        }

        // 安床
        if (Constants.YDLX.AC.equals(changeCode)) {
            // 床位是否已占用
            if (StringUtils.isNotEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("安床失败：床位【" + baseBedDTO.getName() + "】已占用");
            }
            // 床位状态
            if (!Constants.CWZT.ZC.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("安床失败：床位【" + baseBedDTO.getName() + "】非正常床位");
            }
        }
        // 包床
        else if(Constants.YDLX.BC.equals(changeCode)) {
            // 床位是否已占用
            if (StringUtils.isNotEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("包床失败：床位【" + baseBedDTO.getName() + "】已占用");
            }
            // 床位状态
            if (!Constants.CWZT.ZC.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("包床失败：床位【" + baseBedDTO.getName() + "】非正常床位");
            }
        }
        // 包床取消
        else if(Constants.YDLX.BCQX.equals(changeCode)) {
            // 床位是否未占用
            if (StringUtils.isEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("包床取消失败：床位【" + baseBedDTO.getName() + "】未占用");
            }
            // 床位状态
            if (!Constants.CWZT.PC.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("包床取消失败：床位【" + baseBedDTO.getName() + "】非包床床位");
            }
        }
        // 换床
        else if(Constants.YDLX.HC.equals(changeCode)) {
            // 床位是否已占用
            if (StringUtils.isNotEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("换床失败：床位【" + baseBedDTO.getName() + "】已占用");
            }
            // 床位状态
            if (!Constants.CWZT.ZC.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("换床失败：床位【" + baseBedDTO.getName() + "】非正常床位");
            }
        }
        // 转科
        else if(Constants.YDLX.ZK.equals(changeCode)) {
            // 床位是否未占用
            if (StringUtils.isEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("转科失败：床位【" + baseBedDTO.getName() + "】未占用");
            }
            // 床位状态
            if (!Constants.CWZT.ZC.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("转科失败：床位【" + baseBedDTO.getName() + "】非正常床位");
            }
        }
        // 预出院
        else if(Constants.YDLX.YCY.equals(changeCode)) {
            // 床位是否未占用
            if (StringUtils.isEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("预出院失败：床位【" + baseBedDTO.getName() + "】未占用");
            }
            // 床位状态
            if (!Constants.CWZT.ZC.equals(baseBedDTO.getStatusCode()) && !Constants.CWZT.XN.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("预出院失败：床位【" + baseBedDTO.getName() + "】非正常床位");
            }
        }
        // 出院召回 & 继续住院
        else if(Constants.YDLX.CYZH.equals(changeCode) && "0".equals(MapUtils.get(map, "handleCode"))) {
            // 床位是否已占用
            if (StringUtils.isNotEmpty(baseBedDTO.getVisitId())) {
                throw new AppException("出院召回失败：床位【" + baseBedDTO.getName() + "】已占用");
            }
            // 床位状态
            if (!Constants.CWZT.ZC.equals(baseBedDTO.getStatusCode())) {
                throw new AppException("出院召回失败：床位【" + baseBedDTO.getName() + "】非正常床位");
            }
        }
        return baseBedDTO;
    }

    /**
     * @Method 获取前端传入的医生、护士ID，从数据库查询医生、护士信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 15:50
     * @Return
     **/
    private Map<String, SysUserDTO> getDoctorInfo(String... userIds) {
        Map<String, SysUserDTO> userMap = bedListDAO.querySysUserByIds(Arrays.asList(userIds));
        if (MapUtils.isEmpty(userMap)) {
            throw new AppException("工作人员信息已丢失，请联系管理员");
        }
        return userMap;
    }

    /**
     * @Method 校验医生、护士信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 15:50
     * @Return
     **/
    private void vaildDoctorInfo(SysUserDTO userDTO, int typeCode) {
        String title = "责任护士";
        if (typeCode == 1) {
            title = "经治医生";
        }
        else if (typeCode == 2) {
            title = "主治医生";
        }
        else if (typeCode == 3) {
            title = "主管医生";
        }
        if (userDTO == null) {
            throw new AppException("未查询到" + title);
        }
        if(Constants.SF.F.equals(userDTO.getIsInJob())) {
            throw new AppException(title + "【" + userDTO.getName() + "】已离职");
        }
        if (typeCode == 4) {
            if(!userDTO.getWorkTypeCode().startsWith("20")) {
                throw new AppException(title + "【" + userDTO.getName() + "】工作类型非护士");
            }
        } else {
            if(!userDTO.getWorkTypeCode().startsWith("10")) {
                throw new AppException(title + "【" + userDTO.getName() + "】工作类型非医生");
            }
        }
    }

    /**
     * @Method 生成床位长期费用记账表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 15:53
     * @Return
     **/
    private boolean buildLongCost(Map map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String bedId = MapUtils.get(map, "bedId");
        // 获取床位收费项目列表
        List<BaseItemDTO> itemList =  bedListDAO.queryBaseBedItemByBedId(hospCode, bedId);

        // 未配置床位收费项目
        if (ListUtils.isEmpty(itemList)) {
            return true;
        }
        Map parameter = new HashMap();
        parameter.put("hospCode",hospCode);
        parameter.put("code","attribution_settlement");
        String parameterValue = "0";
        WrapperResponse<SysParameterDTO> parameterByCode = sysParameterService.getParameterByCode(parameter);
        if(parameterByCode.getData() != null) {
           parameterValue = parameterByCode.getData().getValue();
        }
        List<InptLongCostDTO> longCostDtoList = new ArrayList<>();
        for (BaseItemDTO dto : itemList) {
            InptLongCostDTO longCostDto = new InptLongCostDTO();
            longCostDto.setId(SnowflakeUtils.getId());
            longCostDto.setHospCode(hospCode);
            longCostDto.setVisitId(MapUtils.get(map, "visitId"));
            longCostDto.setDeptId(MapUtils.get(map, "deptId"));
            longCostDto.setItemId(dto.getId());
            longCostDto.setItemCode(Constants.XMLB.XM);
            longCostDto.setItemName(dto.getName());
            longCostDto.setPrice(dto.getPrice());
            longCostDto.setNum(new BigDecimal(1));
            longCostDto.setUnitCode(dto.getUnitCode());
            longCostDto.setTotalPrice(dto.getPrice());
            longCostDto.setStartTime(new Date());
            longCostDto.setSourceTypeCode("0");
            longCostDto.setSourceId(bedId);
            longCostDto.setIsCancel("0");
            longCostDto.setCrteId(MapUtils.get(map, "userId"));
            longCostDto.setCrteName(MapUtils.get(map, "userName"));
            longCostDto.setCrteTime(new Date());
            longCostDto.setAttributionCode("0");
            if("1".equals(parameterValue)){
              longCostDto.setAttributionCode("1");
            }
            longCostDtoList.add(longCostDto);
        }
        // 新增床位长期费用记录
        return bedListDAO.insertLongCost(longCostDtoList);
    }

    /**
     * @Method 取消床位长期费用
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/23 9:11
     * @Return
     **/
    private void stopInptLongCostByBedId(Map map, List<String> bedIdList, String cancelRemark) {
        InptLongCostDTO longCostDTO = new InptLongCostDTO();
        longCostDTO.setVisitId(MapUtils.get(map, "visitId"));
        longCostDTO.setSourceTypeCode("0");
        longCostDTO.setDeptId(MapUtils.get(map, "deptId"));
        longCostDTO.setBedIdList(bedIdList);
        longCostDTO.setEndTime(new Date());
        longCostDTO.setCancelTime(new Date());
        longCostDTO.setCancelId(MapUtils.get(map, "userId"));
        longCostDTO.setCancelName(MapUtils.get(map, "userName"));
        longCostDTO.setCancelRemark(cancelRemark);
        String changeCode = MapUtils.getEmptyErr(map, "changeCode", "床位异动失败：异动类型不能为空");
        //预出院,转科，换科停所有
        if(Constants.YDLX.YCY.equals(changeCode) || Constants.YDLX.ZK.equals(changeCode) || Constants.YDLX.ZHC.equals(changeCode)){
            longCostDTO.setChangeCode(changeCode);
        }
        bedListDAO.stopInptLongCost(longCostDTO);
    }

    /**
     * @Method 检查是否可以转科或预出院
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/24 14:23
     * @Return
     **/
    private void checkIsAllowZkOrYcy(int type, String hospCode, String visitId) {
        List<String> errMsgList = bedListDAO.checkIsAllowZkOrYcy(type, hospCode, visitId);
        for(String errMsg : errMsgList) {
            if (StringUtils.isNotEmpty(errMsg)) {
                throw new AppException(errMsg);
            }
        }
    }

    /**
     * @Method 床位异动数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/23 9:54
     * @Return
     **/
    private InptBedChangeDO buildBedChange(String changeCode, Map map, BaseBedDTO oldBed, InptVisitDTO inptVisitDTO) {
        InptBedChangeDO bedChangeDO = new InptBedChangeDO();
        bedChangeDO.setId(SnowflakeUtils.getId());
        bedChangeDO.setHospCode(inptVisitDTO.getHospCode());
        bedChangeDO.setVisitId(inptVisitDTO.getId());
        bedChangeDO.setChangeCode(changeCode);
        // 安床
        if (Constants.YDLX.AC.equals(changeCode)) {
            // 换后床位
            bedChangeDO.setAfterBedId(inptVisitDTO.getBedId());
            bedChangeDO.setAfterBedName(inptVisitDTO.getBedName());
            // 换后科室
            bedChangeDO.setAfterDeptId(inptVisitDTO.getInDeptId());
            bedChangeDO.setAfterDeptName(inptVisitDTO.getInDeptName());
            // 换后病区
            bedChangeDO.setAfterWardId(inptVisitDTO.getWardId());
            bedChangeDO.setAfterWardName(inptVisitDTO.getWardName());
            // 换后主治医生
            bedChangeDO.setAfterZzDoctorId(inptVisitDTO.getZzDoctorId());
            bedChangeDO.setAfterZzDoctorName(inptVisitDTO.getZzDoctorName());
            // 换后经治医生
            bedChangeDO.setAfterJzDoctorId(inptVisitDTO.getJzDoctorId());
            bedChangeDO.setAfterJzDoctorName(inptVisitDTO.getJzDoctorName());
            // 换后主管医生
            bedChangeDO.setAfterZgDoctorId(inptVisitDTO.getZgDoctorId());
            bedChangeDO.setAfterZgDoctorName(inptVisitDTO.getZgDoctorName());
            // 换后责任护士
            bedChangeDO.setAfterRespNurseId(inptVisitDTO.getRespNurseId());
            bedChangeDO.setAfterRespNurseName(inptVisitDTO.getRespNurseName());
        }
        // 包床
        else if (Constants.YDLX.BC.equals(changeCode)) {
            // 换后床位
            bedChangeDO.setAfterBedId(inptVisitDTO.getBedId());
            bedChangeDO.setAfterBedName(inptVisitDTO.getBedName());
        }
        // 包床取消
        else if (Constants.YDLX.BCQX.equals(changeCode)) {
            // 换前床位
            bedChangeDO.setBeforeBedId(inptVisitDTO.getBedId());
            bedChangeDO.setBeforeBedName(inptVisitDTO.getBedName());
            bedChangeDO.setEndTime(new Date());
        }
        // 换床
        else if (Constants.YDLX.HC.equals(changeCode)) {
            // 换前床位
            bedChangeDO.setBeforeBedId(oldBed.getId());
            bedChangeDO.setBeforeBedName(oldBed.getName());

            // 正常
            if (Constants.CWZT.ZC.equals(oldBed.getStatusCode())) {
                // 换前主治医生
                bedChangeDO.setBeforeZzDoctorId(MapUtils.get(map, "oldZzDoctorId"));
                bedChangeDO.setBeforeZzDoctorName(MapUtils.get(map, "oldZzDoctorName"));
                // 换前经治医生
                bedChangeDO.setBeforeJzDoctorId(MapUtils.get(map, "oldJzDoctorId"));
                bedChangeDO.setBeforeJzDoctorName(MapUtils.get(map, "oldJzDoctorName"));
                // 换前主管医生
                bedChangeDO.setBeforeZgDoctorId(MapUtils.get(map, "oldZgDoctorId"));
                bedChangeDO.setBeforeZgDoctorName(MapUtils.get(map, "oldZgDoctorName"));
                // 换前责任护士
                bedChangeDO.setBeforeRespNurseId(MapUtils.get(map, "oldRespNurseId"));
                bedChangeDO.setBeforeRespNurseName(MapUtils.get(map, "oldRespNurseName"));

                // 换后床位
                bedChangeDO.setAfterBedId(inptVisitDTO.getBedId());
                bedChangeDO.setAfterBedName(inptVisitDTO.getBedName());
                // 换后主治医生
                bedChangeDO.setAfterZzDoctorId(inptVisitDTO.getZzDoctorId());
                bedChangeDO.setAfterZzDoctorName(inptVisitDTO.getZzDoctorName());
                // 换后经治医生
                bedChangeDO.setAfterJzDoctorId(inptVisitDTO.getJzDoctorId());
                bedChangeDO.setAfterJzDoctorName(inptVisitDTO.getJzDoctorName());
                // 换后主管医生
                bedChangeDO.setAfterZgDoctorId(inptVisitDTO.getZgDoctorId());
                bedChangeDO.setAfterZgDoctorName(inptVisitDTO.getZgDoctorName());
                // 换后责任护士
                bedChangeDO.setAfterRespNurseId(inptVisitDTO.getRespNurseId());
                bedChangeDO.setAfterRespNurseName(inptVisitDTO.getRespNurseName());
            }
        }
        // 转科
        else if (Constants.YDLX.ZK.equals(changeCode)) {
            // 换前床位
            bedChangeDO.setBeforeBedId(oldBed.getId());
            bedChangeDO.setBeforeBedName(oldBed.getName());
            // 换前科室
            bedChangeDO.setBeforeDeptId(MapUtils.get(map, "oldInDeptId"));
            bedChangeDO.setBeforeDeptName(MapUtils.get(map, "oldInDeptName"));
            // 换前病区
            bedChangeDO.setBeforeWardId(MapUtils.get(map, "oldWardId"));
            bedChangeDO.setBeforeWardName(MapUtils.get(map, "oldWardName"));

            // 正常
            if (Constants.CWZT.ZC.equals(oldBed.getStatusCode())) {
                // 换前主治医生
                bedChangeDO.setBeforeZzDoctorId(MapUtils.get(map, "oldZzDoctorId"));
                bedChangeDO.setBeforeZzDoctorName(MapUtils.get(map, "oldZzDoctorName"));
                // 换前经治医生
                bedChangeDO.setBeforeJzDoctorId(MapUtils.get(map, "oldJzDoctorId"));
                bedChangeDO.setBeforeJzDoctorName(MapUtils.get(map, "oldJzDoctorName"));
                // 换前主管医生
                bedChangeDO.setBeforeZgDoctorId(MapUtils.get(map, "oldZgDoctorId"));
                bedChangeDO.setBeforeZgDoctorName(MapUtils.get(map, "oldZgDoctorName"));
                // 换前责任护士
                bedChangeDO.setBeforeRespNurseId(MapUtils.get(map, "oldRespNurseId"));
                bedChangeDO.setBeforeRespNurseName(MapUtils.get(map, "oldRespNurseName"));
            }
            // 换后科室
            bedChangeDO.setAfterDeptId(inptVisitDTO.getInDeptId());
            bedChangeDO.setAfterDeptName(inptVisitDTO.getInDeptName());
            // 换后病区
            bedChangeDO.setAfterWardId(inptVisitDTO.getWardId());
            bedChangeDO.setAfterWardName(inptVisitDTO.getWardName());
        }
        // 预出院
        else if (Constants.YDLX.YCY.equals(changeCode)) {
            // 换前床位
            bedChangeDO.setBeforeBedId(oldBed.getId());
            bedChangeDO.setBeforeBedName(oldBed.getName());
            // 换前科室
            bedChangeDO.setBeforeDeptId(MapUtils.get(map, "oldInDeptId"));
            bedChangeDO.setBeforeDeptName(MapUtils.get(map, "oldInDeptName"));
            // 换前病区
            bedChangeDO.setBeforeWardId(MapUtils.get(map, "oldWardId"));
            bedChangeDO.setBeforeWardName(MapUtils.get(map, "oldWardName"));

            // 正常
            if (Constants.CWZT.ZC.equals(oldBed.getStatusCode())) {
                // 换前主治医生
                bedChangeDO.setBeforeZzDoctorId(MapUtils.get(map, "oldZzDoctorId"));
                bedChangeDO.setBeforeZzDoctorName(MapUtils.get(map, "oldZzDoctorName"));
                // 换前经治医生
                bedChangeDO.setBeforeJzDoctorId(MapUtils.get(map, "oldJzDoctorId"));
                bedChangeDO.setBeforeJzDoctorName(MapUtils.get(map, "oldJzDoctorName"));
                // 换前主管医生
                bedChangeDO.setBeforeZgDoctorId(MapUtils.get(map, "oldZgDoctorId"));
                bedChangeDO.setBeforeZgDoctorName(MapUtils.get(map, "oldZgDoctorName"));
                // 换前责任护士
                bedChangeDO.setBeforeRespNurseId(MapUtils.get(map, "oldRespNurseId"));
                bedChangeDO.setBeforeRespNurseName(MapUtils.get(map, "oldRespNurseName"));
            }
            bedChangeDO.setEndTime(new Date());
        }

        // 创建人
        bedChangeDO.setCrteId(MapUtils.get(map, "userId"));
        bedChangeDO.setCrteName(MapUtils.get(map, "userName"));
        bedChangeDO.setCrteTime(new Date());

        return bedChangeDO;
    }

    /**
     * @Method 转科、预出院：补全床位长期费用
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/27 16:32
     * @Return
     **/
    private void compBedLongCost(Map map) {
        Map reqMap = new HashMap();
        reqMap.put("isAuto", Constants.SF.F);
        reqMap.put("visitId", MapUtils.get(map, "visitId"));
        reqMap.put("userId", MapUtils.get(map, "userId"));
        reqMap.put("userName", MapUtils.get(map, "userName"));
        reqMap.put("hospCode", MapUtils.get(map, "hospCode"));
        bedLongCostBO.saveBedLongCost(reqMap);
    }

    /**
     * @Method 转科、预出院：补全医嘱长期费用
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/27 16:32
     * @Return
     **/
    private void compAdviceLongCost(Map map) {

    }

    /**
     * @Method 参数校验
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/24 14:13
     * @Return
     **/
    private void validParam(String changeCode, Map map) {
        MapUtils.isEmptyErr(map, "hospCode", "visitId", "bedId", "deptId", "deptName", "userId", "userName");

        // 获取就诊病人信息
        InptVisitDTO inptVisitDTO = getInptVisitById(changeCode, map);

        // 获取床位信息
        BaseBedDTO baseBedDTO = null;

        // 出院召回 - 虚拟床位
        String handleCode = MapUtils.get(map, "handleCode");
        if (!(Constants.YDLX.CYZH.equals(changeCode) && "1".equals(handleCode))) {
            baseBedDTO = getBaseBedById(changeCode, map);
        }

        // 设置异动前数据
        map.put("oldBedId", inptVisitDTO.getId());
        map.put("oldBedName", inptVisitDTO.getName());
        map.put("oldInDeptId", inptVisitDTO.getInDeptId());
        map.put("oldInDeptName", inptVisitDTO.getInDeptName());
        map.put("oldWardId", inptVisitDTO.getWardId());
        map.put("oldWardName", inptVisitDTO.getWardName());
        map.put("oldZzDoctorId", inptVisitDTO.getZzDoctorId());
        map.put("oldZzDoctorName", inptVisitDTO.getZzDoctorName());
        map.put("oldJzDoctorId", inptVisitDTO.getJzDoctorId());
        map.put("oldJzDoctorName", inptVisitDTO.getJzDoctorName());
        map.put("oldZgDoctorId", inptVisitDTO.getZgDoctorId());
        map.put("oldZgDoctorName", inptVisitDTO.getZgDoctorName());
        map.put("oldRespNurseId", inptVisitDTO.getRespNurseId());
        map.put("oldRespNurseName", inptVisitDTO.getRespNurseName());

        // 安床 & 换床
        if (Constants.YDLX.AC.equals(changeCode) || Constants.YDLX.HC.equals(changeCode)) {
            // 换床：不能换到当前床位
            if (Constants.YDLX.HC.equals(changeCode) && MapUtils.get(map, "bedId").equals(inptVisitDTO.getBedId())) {
                throw new AppException("换床失败：不能换到病人当前床位");
            }

            // 医生、护士ID
            String jzDoctorId = MapUtils.getEmptyErr(map, "jzDoctorId", "经治医生ID为空");
            String zzDoctorId = MapUtils.getEmptyErr(map, "zzDoctorId", "主治医生ID为空");
            String zgDoctorId = MapUtils.getEmptyErr(map, "zgDoctorId", "主管医生ID为空");
            String respNurseId = MapUtils.getEmptyErr(map, "respNurseId", "责任护士ID为空");
            if (Constants.YDLX.AC.equals(changeCode)){// 只会影响安床，不会影响换床
                String inTime = MapUtils.get(map,"inTime");
                Optional.ofNullable(inTime).orElseThrow(()->new AppException("入院时间不能为空"));
                inptVisitDTO.setInTime(DateUtils.parse(inTime,DateUtils.Y_M_DH_M_S));
            }

            // 获取医生、护士信息
            Map<String, SysUserDTO> userMap = getDoctorInfo(jzDoctorId , zzDoctorId, zgDoctorId, respNurseId);
            // 经治医生
            SysUserDTO jzDoctor = userMap.get(jzDoctorId);
            vaildDoctorInfo(jzDoctor, 1);
            // 主治医生
            SysUserDTO zzDoctor = userMap.get(zzDoctorId);
            vaildDoctorInfo(zzDoctor, 2);
            // 主管医生
            SysUserDTO zgDoctor = userMap.get(zgDoctorId);
            vaildDoctorInfo(zgDoctor, 3);
            // 责任护士
            SysUserDTO respNurse = userMap.get(respNurseId);
            vaildDoctorInfo(respNurse, 4);

            inptVisitDTO.setBedId(baseBedDTO.getId());
            inptVisitDTO.setBedName(baseBedDTO.getName());
            inptVisitDTO.setJzDoctorId(jzDoctor.getId());
            inptVisitDTO.setJzDoctorName(jzDoctor.getName());
            inptVisitDTO.setZzDoctorId(zzDoctor.getId());
            inptVisitDTO.setZzDoctorName(zzDoctor.getName());
            inptVisitDTO.setZgDoctorId(zgDoctor.getId());
            inptVisitDTO.setZgDoctorName(zgDoctor.getName());
            inptVisitDTO.setRespNurseId(respNurse.getId());
            inptVisitDTO.setRespNurseName(respNurse.getName());
        }
        // 包床
        else if (Constants.YDLX.BC.equals(changeCode)) {
            inptVisitDTO.setBedId(baseBedDTO.getId());
            inptVisitDTO.setBedName(baseBedDTO.getName());
        }
        // 包床取消
        else if (Constants.YDLX.BCQX.equals(changeCode)) {
            inptVisitDTO.setBedId(baseBedDTO.getId());
            inptVisitDTO.setBedName(baseBedDTO.getName());
        }
        // 转科
        else if (Constants.YDLX.ZK.equals(changeCode)) {
            // 转入病区ID
            String inWardId = MapUtils.getEmptyErr(map, "inWardId", "转入病区ID为空");
            // 转入科室ID
            String inDeptId = MapUtils.getEmptyErr(map, "inDeptId", "转入科室ID为空");

            // 转入科室不能是当前科室
            if (inDeptId.equals(inptVisitDTO.getInDeptId())) {
                throw new AppException("转科失败：不能转入相同科室");
            }
            BaseDeptDTO deptDTO = bedListDAO.getBaseDeptById(inDeptId);
            if (deptDTO == null) {
                throw new AppException("转科失败：转入科室数据丢失，请联系管理员");
            }
            if (!Constants.SF.S.equals(deptDTO.getIsValid())) {
                throw new AppException("转科失败：转入科室数据已设置为无效");
            }
            if (!inWardId.equals(deptDTO.getWardId())) {
                throw new AppException("转科失败：目的科室【" + deptDTO.getName() + "】不属于目的病区【" + deptDTO.getWardName() + "】");
            }

            inptVisitDTO.setInDeptId(deptDTO.getId());
            inptVisitDTO.setInDeptName(deptDTO.getName());
            inptVisitDTO.setWardId(deptDTO.getWardId());
            inptVisitDTO.setWardName(deptDTO.getWardName());
        }
        // 预出院
        else if (Constants.YDLX.YCY.equals(changeCode)) {
            // 出院诊断
            String outDiseaseId = MapUtils.getEmptyErr(map, "outDiseaseId", "请选择出院诊断");
            // 出院时间
            String outTime = MapUtils.getEmptyErr(map, "outTime", "请选择出院时间");
            // 出院情况
            String outSituationCode = MapUtils.getEmptyErr(map, "outSituationCode", "请选择出院情况");
            // 出院方式
            String outModeCode = MapUtils.getEmptyErr(map, "outModeCode", "请选择出院方式");

            // 出院时间不能小于入院时间
            Date outTimeDt = DateUtils.parse(outTime, DateUtils.Y_M_DH_M_S);
            if (DateUtils.dateCompare(outTimeDt, inptVisitDTO.getInTime())) {
                throw new AppException("预出院失败：出院时间不能小于入院时间");
            }

            BaseDeptDTO deptDTO = bedListDAO.getBaseDeptById(MapUtils.get(map, "deptId"));
            if (deptDTO == null) {
                throw new AppException("预出院失败：出院科室数据丢失，请联系管理员");
            }
            BaseDiseaseDTO diseaseDTO = bedListDAO.getBaseDiseaseById(outDiseaseId);
            if (diseaseDTO == null) {
                throw new AppException("预出院失败：出院诊断数据丢失，请联系管理员");
            }
            if (!Constants.SF.S.equals(diseaseDTO.getIsValid())) {
                throw new AppException("预出院失败：出院诊断数据已设置为无效");
            }

            inptVisitDTO.setOutDeptId(deptDTO.getId());
            inptVisitDTO.setOutDeptName(deptDTO.getName());
            inptVisitDTO.setOutWardId(deptDTO.getWardId());
            inptVisitDTO.setOutTime(outTimeDt);
            inptVisitDTO.setOutSituationCode(outSituationCode);
            inptVisitDTO.setOutModeCode(outModeCode);
            inptVisitDTO.setOutDiseaseId(diseaseDTO.getId());
            inptVisitDTO.setOutDiseaseName(diseaseDTO.getName());
            inptVisitDTO.setOutDiseaseIcd10(diseaseDTO.getNationCode());
            inptVisitDTO.setOutRemark(MapUtils.get(map, "outRemark"));
            inptVisitDTO.setOutOperId(MapUtils.get(map, "userId"));
            inptVisitDTO.setOutOperName(MapUtils.get(map, "userName"));
            inptVisitDTO.setOutOperTime(new Date());
        }
        // 出院召回：继续住院
        else if (Constants.YDLX.CYZH.equals(changeCode) && "0".equals(handleCode)) {
            inptVisitDTO.setBedId(baseBedDTO.getId());
            inptVisitDTO.setBedName(baseBedDTO.getName());
        }

        map.put("baseBedDTO", baseBedDTO);
        map.put("inptVisitDTO", inptVisitDTO);
    }

    /**
     * @Method 安床
     * @Description
     * 1、校验参数
     * 2、校验医生、护士状态
     * 3、校验病人状态
     * 4、校验床位状态
     * 5、生成床位长期费用
     * 6、修改病人状态
     * 7、修改床位状态
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:43
     * @Return
     **/
    private void handleAc(Map map) {
        // 参数校验
        validParam(Constants.YDLX.AC, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 生成床位异动
        InptBedChangeDO bedChangeDO = buildBedChange(Constants.YDLX.AC, map, null, inptVisitDTO);
        if (!bedListDAO.insertInptBedChange(Arrays.asList(new InptBedChangeDO[]{bedChangeDO}))) {
            throw new AppException("安床失败：病人【" + inptVisitDTO.getName() + "】生成床位异动数据失败");
        }

        // 生成长期费用记录表
        if (!buildLongCost(map)) {
            throw new AppException("安床失败：病人【" + inptVisitDTO.getName() + "】生成床位长期记账数据失败");
        }

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Ac(inptVisitDTO)) {
            throw new AppException("安床失败：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }

        // 更新床位信息
        if (!bedListDAO.updateBaseBedVisit_Ac(inptVisitDTO.getBedId(), inptVisitDTO.getId())) {
            throw new AppException("安床失败：床位【" + inptVisitDTO.getBedName() + "】状态发生变化");
        }
    }

    /**
     * @Method 包床
     * @Description
     * 1、校验参数
     * 2、校验病人状态、床位状态
     * 3、生成包床长期费用
     * 4、修改床位信息
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleBc(Map map) {
        // 参数校验
        validParam(Constants.YDLX.BC, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 生成床位异动
        InptBedChangeDO bedChangeDO = buildBedChange(Constants.YDLX.BC, map, null, inptVisitDTO);
        if (!bedListDAO.insertInptBedChange(Arrays.asList(new InptBedChangeDO[]{bedChangeDO}))) {
            throw new AppException("包床失败：病人【" + inptVisitDTO.getName() + "】生成床位异动数据失败");
        }

        // 生成长期费用记录表
        if (!buildLongCost(map)) {
            throw new AppException("包床失败：病人【" + inptVisitDTO.getName() + "】生成床位长期记账数据失败");
        }

        // 更新床位信息
        if (!bedListDAO.updateBaseBedVisit_Bc(inptVisitDTO.getBedId(), inptVisitDTO.getId())) {
            throw new AppException("包床失败：床位【" + inptVisitDTO.getBedName() + "】状态发生变化");
        }
    }

    /**
     * @Method 释放包床
     * @Description
     * 1、校验参数
     * 2、校验病人状态、床位状态
     * 3、取消包床长期费用
     * 4、修改床位信息
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleBcqx(Map map) {
        // 参数校验
        validParam(Constants.YDLX.BCQX, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 生成床位异动
        InptBedChangeDO bedChangeDO = buildBedChange(Constants.YDLX.BCQX, map, null, inptVisitDTO);
        if (!bedListDAO.insertInptBedChange(Arrays.asList(new InptBedChangeDO[]{bedChangeDO}))) {
            throw new AppException("取消包床失败：病人【" + inptVisitDTO.getName() + "】生成床位异动数据失败");
        }

        // 取消包床长期费用
        String cancelRemark = "科室【" + inptVisitDTO.getInDeptName() + "】，护士【" + MapUtils.get(map, "userName") + "】取消包床";
        stopInptLongCostByBedId(map, Arrays.asList(new String[]{inptVisitDTO.getBedId()}), cancelRemark);

        // 更新床位信息
        if (!bedListDAO.updateBaseBedVisit_Bcqx(inptVisitDTO.getBedId(), inptVisitDTO.getId())) {
            throw new AppException("取消包床失败：床位【" + inptVisitDTO.getBedName() + "】状态发生变化");
        }
    }

    /**
     * @Method 换床
     * @Description
     * 1、校验参数
     * 2、校验医生、护士状态
     * 3、校验病人状态、床位状态
     * 4、停止未换前长期费用
     * 5、清空未换前床位、包床信息
     * 6、生成床位异动信息
     * 7、生成新床长期费用
     * 8、修改住院病人信息
     * 9、修改新床就诊病人信息
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleHc(Map map) {
        validParam(Constants.YDLX.HC, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 查询已占用床位信息
        List<BaseBedDTO> bedList = bedListDAO.queryBaseBedByVisit(inptVisitDTO.getHospCode(), inptVisitDTO.getId());
        if (ListUtils.isEmpty(bedList)) {
            throw new AppException("换床失败：病人无在床信息");
        }

        // 待停止的长期费用列表
        List<String> bedIdList = new ArrayList<>();
        // 待生成的床位异动列表
        List<InptBedChangeDO> bedChangeList = new ArrayList<>();
        for (BaseBedDTO dto : bedList) {
            bedIdList.add(dto.getId());

            // 床位异动数据
            bedChangeList.add(buildBedChange(Constants.YDLX.HC, map, dto, inptVisitDTO));
        }

        // 停止床位长期费用
        String cancelRemark = "科室【" + inptVisitDTO.getInDeptName() + "】，护士【" + MapUtils.get(map, "userName") + "】换床";
        stopInptLongCostByBedId(map, bedIdList, cancelRemark);

        // 清空已占床就诊ID、状态
        bedListDAO.clearBaseBedVisitByBedId(bedIdList);

        // 生成床位异动
        bedListDAO.insertInptBedChange(bedChangeList);

        // 生成长期费用记录表
        buildLongCost(map);

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Hc(inptVisitDTO)) {
            throw new AppException("换床失败：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }

        // 更新床位信息
        if (!bedListDAO.updateBaseBedVisit_Hc(inptVisitDTO.getBedId(), inptVisitDTO.getId())) {
            throw new AppException("换床失败：床位【" + inptVisitDTO.getBedName() + "】状态发生变化");
        }
    }

    /**
     * @Method 转科
     * @Description
     * 1、校验参数
     * 2、校验医生、护士状态
     * 3、校验病人状态、床位状态
     * 4、校验是否有未停止的长期医嘱、未核收的医嘱、未填写皮试结果的医嘱、未确认的费用、未发并且未退的药品
     * 5、停止床位长期费用
     * 6、清空床位、包床信息
     * 7、生成床位异动信息
     * 8、修改住院病人信息
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleZk(Map map) {
        validParam(Constants.YDLX.ZK, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 查询已占用床位信息
        List<BaseBedDTO> bedList = bedListDAO.queryBaseBedByVisit(inptVisitDTO.getHospCode(), inptVisitDTO.getId());
        if (ListUtils.isEmpty(bedList)) {
            throw new AppException("转科失败：病人无在床信息");
        }

        // 校验是否有未停止的长期医嘱、未核收的医嘱、未填写皮试结果的医嘱、未确认的费用、未发并且未退的药品
        checkIsAllowZkOrYcy(0, inptVisitDTO.getHospCode(), inptVisitDTO.getId());

        // 待停止的长期费用列表
        List<String> bedIdList = new ArrayList<>();
        // 待生成的床位异动列表
        List<InptBedChangeDO> bedChangeList = new ArrayList<>();
        for (BaseBedDTO dto : bedList) {
            bedIdList.add(dto.getId());

            // 床位异动数据
            bedChangeList.add(buildBedChange(Constants.YDLX.ZK, map, dto, inptVisitDTO));
        }

        // 补全床位长期费用
        compBedLongCost(map);

        // 补全医嘱长期费用
        compAdviceLongCost(map);

        // 停止床位长期费用
        String cancelRemark = "科室【" + inptVisitDTO.getInDeptName() + "】，护士【" + MapUtils.get(map, "userName") + "】转科";
        stopInptLongCostByBedId(map, bedIdList, cancelRemark);

        // 清空已占床就诊ID、状态
        bedListDAO.clearBaseBedVisitByBedId(bedIdList);

        // 生成床位异动
        bedListDAO.insertInptBedChange(bedChangeList);

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Zk(inptVisitDTO)) {
            throw new AppException("转科失败：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }
    }

    /**
     * @Description: 换科，仅修改患者的医嘱，费用科室
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2022/1/11 16:22
     * @Return
     */
    private void handleHk(Map map) {
        validParam(Constants.YDLX.ZHC, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 查询已占用床位信息
        List<BaseBedDTO> bedList = bedListDAO.queryBaseBedByVisit(inptVisitDTO.getHospCode(), inptVisitDTO.getId());
        if (ListUtils.isEmpty(bedList)) {
            throw new AppException("换科失败：病人无在床信息");
        }

        // 转入病区ID
        String inWardId = MapUtils.getEmptyErr(map, "inWardId", "转入病区ID为空");
        // 转入科室ID
        String inDeptId = MapUtils.getEmptyErr(map, "inDeptId", "转入科室ID为空");

        if(StringUtils.isEmpty(inDeptId)){
            throw new AppException("换科失败：转入科室ID为空,请刷新页面重新操作！");
        }
        if(StringUtils.isEmpty(inWardId)){
            throw new AppException("换科失败：转入病区ID为空,请刷新页面重新操作！");
        }

		inptVisitDTO.setInDeptId(inDeptId);
        inptVisitDTO.setInWardId(inWardId);

        // 查询科室名字
        String inDeptName = bedListDAO.getDeptName(map);
        if (inDeptName != null && !"".equals(inDeptName)) {
            inptVisitDTO.setInDeptName(inDeptName);
        }

        // 校验是否有未停止的长期医嘱、未核收的医嘱、未填写皮试结果的医嘱、未确认的费用、未发并且未退的药品
        checkIsAllowHk(0, inptVisitDTO.getHospCode(), inptVisitDTO.getId());

        // 待停止的长期费用列表
        List<String> bedIdList = new ArrayList<>();
        // 待生成的床位异动列表
        List<InptBedChangeDO> bedChangeList = new ArrayList<>();
        for (BaseBedDTO dto : bedList) {
            bedIdList.add(dto.getId());

            // 床位异动数据
            bedChangeList.add(buildBedChange(Constants.YDLX.ZK, map, dto, inptVisitDTO));
        }

        // 补全床位长期费用
        //compBedLongCost(map);

        // 补全医嘱长期费用
        //compAdviceLongCost(map);

        // 停止床位长期费用
        String cancelRemark = "科室【" + inptVisitDTO.getInDeptName() + "】，护士【" + MapUtils.get(map, "userName") + "】换科";
        stopInptLongCostByBedId(map, bedIdList, cancelRemark);

        // 清空已占床就诊ID、状态
        bedListDAO.clearBaseBedVisitByBedId(bedIdList);

        // 生成床位异动
        bedListDAO.insertInptBedChange(bedChangeList);

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Zk(inptVisitDTO)) {
            throw new AppException("病人换科失败：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }

        // 修改医嘱 的就诊科室、开医嘱科室id，
        bedListDAO.updateAdviceHK(inptVisitDTO);

        // 医嘱执行表的开医嘱科室id、执行科室id，
        bedListDAO.updateAdviceExecHK(inptVisitDTO);

        // 费用表来源科室id、就诊科室id、开医嘱科室id、执行科室id
//        bedListDAO.updateInptCostHK(inptVisitDTO);

    }

    private void checkIsAllowHk(int type, String hospCode, String visitId) {
        List<String> errMsgList = bedListDAO.checkIsAllowHk(type, hospCode, visitId);
        for(String errMsg : errMsgList) {
            if (StringUtils.isNotEmpty(errMsg)) {
                throw new AppException(errMsg);
            }
        }
    }

    /**
     * @Method 预出院
     * @Description
     * 1、校验参数
     * 2、校验医生、护士状态
     * 3、校验病人状态、床位状态
     * 4、校验是否有未停止的长期医嘱、未核收的医嘱、未填写皮试结果的医嘱、未确认的费用、未发并且未退的药品、判断手术是否未完成、判断手术是否未补记账
     * 4、停止床位长期费用
     * 5、清空床位、包床信息
     * 6、生成床位异动信息
     * 8、修改住院病人信息
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleYcy(Map map) {
        validParam(Constants.YDLX.YCY, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        if(inptVisitDTO == null){
            throw new AppException("预出院失败：未获取到病人信息!");
        }

        //查询当前病人是否有在预出院时间之后的费用
        List<InptCostDTO> list  = inptCostDAO.findOutTimeAfterCost(inptVisitDTO);
        if(!ListUtils.isEmpty(list)){
            throw new AppException("预出院失败：当前病人存在出院时间以后的费用,请及时处理!");
        }

        // 查询已占用床位信息
        List<BaseBedDTO> bedList = bedListDAO.queryBaseBedByVisit(inptVisitDTO.getHospCode(), inptVisitDTO.getId());
        if (ListUtils.isEmpty(bedList)) {
            throw new AppException("预出院失败：病人无在床信息");
        }

        // 床位信息
        BaseBedDTO baseBedDTO = MapUtils.get(map, "baseBedDTO");
        // 出院召回 - 召回费用预出院
        if (Constants.CWZT.XN.equals(baseBedDTO.getStatusCode())) {
            // 删除虚拟床位
            if (!bedListDAO.deleteBaseBed_XN(baseBedDTO.getId())) {
                throw new AppException("预出院失败：虚拟床位【" + baseBedDTO.getName() + "】状态发生变化");
            }
        }
        else {
            // 校验是否有未停止的长期医嘱、未核收的医嘱、未填写皮试结果的医嘱、未确认的费用、未发并且未退的药品、判断手术是否未完成、判断手术是否未补记账
            checkIsAllowZkOrYcy(1, inptVisitDTO.getHospCode(), inptVisitDTO.getId());

            // 待停止的长期费用列表
            List<String> bedIdList = new ArrayList<>();
            // 待生成的床位异动列表
            List<InptBedChangeDO> bedChangeList = new ArrayList<>();
            for (BaseBedDTO dto : bedList) {
                bedIdList.add(dto.getId());

                // 床位异动数据
                bedChangeList.add(buildBedChange(Constants.YDLX.YCY, map, dto, inptVisitDTO));
            }

            // 补全床位长期费用
            compBedLongCost(map);

            // 补全医嘱长期费用
            compAdviceLongCost(map);

            // 停止床位长期费用
            String cancelRemark = "科室【" + inptVisitDTO.getInDeptName() + "】，护士【" + MapUtils.get(map, "userName") + "】预出院";
            stopInptLongCostByBedId(map, bedIdList, cancelRemark);

            // 清空已占床就诊ID、状态
            bedListDAO.clearBaseBedVisitByBedId(bedIdList);

            // 生成床位异动
            bedListDAO.insertInptBedChange(bedChangeList);
        }

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Ycy(inptVisitDTO)) {
            throw new AppException("预出院失败：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }
        // 20210723 无出院诊断办理出院时，选择的出院诊断更新至诊断管理表 liuliyun
        insertDiagnose(inptVisitDTO);
        // 20210831 计算住院天数更新到inpt_visit表
        if (inptVisitDTO.getInTime()!=null&&inptVisitDTO.getOutTime()!=null){
            int totalInDays = DateUtils.differentDays(inptVisitDTO.getInTime(),inptVisitDTO.getOutTime());
            if (totalInDays == 0) {
                totalInDays = 1;
            }
            inptVisitDTO.setTotalInDays(totalInDays);
            bedListDAO.updateInptVisitTotalDays(inptVisitDTO);
        }
        // lly 2021-12-06 预出院收费提醒
        chargeFeeMsg(inptVisitDTO);
    }
    // 20210723 无出院诊断办理出院时，选择的出院诊断更新至诊断管理表 liuliyun
    public void insertDiagnose(InptVisitDTO inptVisitDTO){
            if (inptVisitDTO!=null) {
                // 查询是否存在出院主诊断
                Integer diagnoseCount = bedListDAO.getInptDiagnoseByVisitId(inptVisitDTO.getHospCode(),
                        inptVisitDTO.getId(),inptVisitDTO.getOutDiseaseId());
                if (diagnoseCount != null && diagnoseCount > 0) {
                } else {
                    InptDiagnoseDTO inptDiagnose = new InptDiagnoseDTO();
                    inptDiagnose.setHospCode(inptVisitDTO.getHospCode());
                    inptDiagnose.setVisitId(inptVisitDTO.getId());
                    inptDiagnose.setId(SnowflakeUtils.getId());
                    inptDiagnose.setCrteName(inptVisitDTO.getCrteName());
                    inptDiagnose.setCrteId(inptVisitDTO.getCrteId());
                    inptDiagnose.setDiseaseId(inptVisitDTO.getOutDiseaseId());
                    // 查询出入院诊断之外的主诊断
                    Integer count = bedListDAO.getInptDiagnose(inptVisitDTO.getHospCode(), inptVisitDTO.getId());
                    if (count != null && count > 0) {
                        inptDiagnose.setIsMain("0");
                    } else {
                        inptDiagnose.setIsMain("1");
                    }
                    inptDiagnose.setTypeCode("204");
                    bedListDAO.insertDiagnose(inptDiagnose);
                }
            }
    }
    /**
     * @Method 出院召回 - 继续住院
     * @Description
     * 1、校验参数
     * 2、校验病人状态
     * 3、修改床位病人占床信息
     * 4、修改住院病人信息
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleCyzh_Jxzy(Map map) {
        validParam(Constants.YDLX.CYZH, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 生成长期费用记录表
        if (!buildLongCost(map)) {
            throw new AppException("出院召回：病人【" + inptVisitDTO.getName() + "】生成床位长期记账数据失败");
        }

        // 更新床位信息
        if (!bedListDAO.updateBaseBedVisit_Ac(inptVisitDTO.getBedId(), inptVisitDTO.getId())) {
            throw new AppException("出院召回：床位【" + inptVisitDTO.getBedName() + "】状态发生变化");
        }

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Cyzh(inptVisitDTO)) {
            throw new AppException("出院召回：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }
    }

    /**
     * @Method 出院召回 - 召回费用
     * @Description
     * 1、校验参数
     * 2、校验病人状态
     * 3、生成虚拟床位
     * 4、修改住院病人信息
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 19:42
     * @Return
     **/
    private void handleCyzh_Zhfy(Map map) {
        validParam(Constants.YDLX.CYZH, map);

        // 查询就诊病人
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        // 生成虚拟床位
        BaseBedDTO baseBedDTO = new BaseBedDTO();
        baseBedDTO.setId(SnowflakeUtils.getId());
        baseBedDTO.setHospCode(inptVisitDTO.getHospCode());
        baseBedDTO.setDeptCode(inptVisitDTO.getDeptCode());
        baseBedDTO.setCode(getOrderNo(inptVisitDTO.getHospCode(), Constants.ORDERRULE.CW) + "-XN");
        baseBedDTO.setName(inptVisitDTO.getBedName());
        baseBedDTO.setStatusCode(Constants.CWZT.XN);
        baseBedDTO.setSeqNo(bedListDAO.getMaxSeqNoByDeptCode(inptVisitDTO.getHospCode(), inptVisitDTO.getDeptCode()));
        baseBedDTO.setVisitId(inptVisitDTO.getId());
        baseBedDTO.setIsValid(Constants.SF.S);
        baseBedDTO.setCrteId(MapUtils.get(map, "userId"));
        baseBedDTO.setCrteName(MapUtils.get(map, "userName"));
        baseBedDTO.setCrteTime(new Date());

        // 新增虚拟床位
        bedListDAO.insertBaseBedVisit_Cyzh(baseBedDTO);

        // 设置住院病人虚拟床位ID
        inptVisitDTO.setBedId(baseBedDTO.getId());

        // 修改住院病人信息
        if (!bedListDAO.updateInptVisit_Cyzh(inptVisitDTO)) {
            throw new AppException("出院召回：病人【" + inptVisitDTO.getName() + "】状态发生变化");
        }
    }
    /***************************************zhongming added by 20201222 end*****************************************/

    // 预出院将收费消息写入消息表 lly 2021-12-06
    public void chargeFeeMsg(InptVisitDTO inptVisitDTO){
        String hospCode =inptVisitDTO.getHospCode();
        String name = inptVisitDTO.getCrteName();
        String crteId = inptVisitDTO.getCrteId();
        Map openParam = new HashMap();
        openParam.put("hospCode", hospCode);
        openParam.put("code", "MSG_OPEN");
        SysParameterDTO openSysParameterDTO = sysParameterService_consumer.getParameterByCode(openParam).getData();
        if (openSysParameterDTO!=null&& "1".equals(openSysParameterDTO.getValue())) {
            Map paramMap = new HashMap();
            paramMap.put("hospCode", hospCode);
            paramMap.put("code", "XXTS_SETTING");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(paramMap).getData();
            ConfigInfoDO configInfoDO = null;
            if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                configInfoDO = StringUtils.getConfigInfoDOFromSys(sysParameterDTO.getValue(),"chargeMsg");
            }
            if (configInfoDO ==null){
                return;
            }
            if (inptVisitDTO != null) {
                    MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                    messageInfoDTO.setId(SnowflakeUtils.getId());
                    messageInfoDTO.setHospCode(hospCode);
                    messageInfoDTO.setSourceId("");
                    messageInfoDTO.setVisitId(inptVisitDTO.getId());
                    // 推送到科室
                if ("1".equals(configInfoDO.getIsPersonal())) {
                    messageInfoDTO.setReceiverId("");
                    messageInfoDTO.setDeptId(configInfoDO.getDeptId());
                }else if ("0".equals(configInfoDO.getIsPersonal())){
                    // 推送到个人
                    messageInfoDTO.setDeptId("");
                    messageInfoDTO.setReceiverId(configInfoDO.getReceiverId());
                }else {
                    // 默认推送到科室
                    messageInfoDTO.setReceiverId("");
                    messageInfoDTO.setDeptId(configInfoDO.getDeptId());
                }
                    messageInfoDTO.setLevel(configInfoDO.getLevel());
                    messageInfoDTO.setSendCount(configInfoDO.getSendCount());
                    messageInfoDTO.setType(Constants.MSG_TYPE.MSG_SF);
                    messageInfoDTO.setContent(inptVisitDTO.getName() + "已预出院，请及时结算");
                    Date startTime = DateUtils.dateAddMinute(new Date(), configInfoDO.getStartTime());
                    Date endTime = DateUtils.dateAddMinute(startTime, configInfoDO.getEndTime());
                    messageInfoDTO.setStartTime(startTime);
                    messageInfoDTO.setEndTime(endTime);
                    messageInfoDTO.setStatusCode(Constants.MSGZT.MSG_WD);
                    messageInfoDTO.setIntervalTime(configInfoDO.getIntervalTime());
                    messageInfoDTO.setUrl(configInfoDO.getUrl());
                    messageInfoDTO.setCrteName(name);
                    messageInfoDTO.setCrteTime(DateUtils.getNow());
                    messageInfoDTO.setCrteId(crteId);
                    List<MessageInfoDTO> messageInfoDTOList =new ArrayList<>();
                    messageInfoDTOList.add(messageInfoDTO);
                //messageInfoDAO.insertMessageInfo(messageInfoDTO);
                // 获取医院kafka 的IP与端口
                Map<String, Object> sysMap = new HashMap<>();
                sysMap.put("hospCode", hospCode);
                sysMap.put("code", "KAFKA_MSG_IP");
                SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
                if (sys == null || sys.getValue() == null) {
                    return;
                }
                String server = sys.getValue();
                // 1. 创建一个kafka生产者
                String producerTopic = Constants.MSG_TOPIC.producerTopicKey;//生产者消息推送Topic
                KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
                String message = JSONObject.toJSONString(messageInfoDTOList);
                KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
            }
        }
    }
}
