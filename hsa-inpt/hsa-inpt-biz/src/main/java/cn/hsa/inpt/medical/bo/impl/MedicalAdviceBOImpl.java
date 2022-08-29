package cn.hsa.inpt.medical.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bac.service.BaseAssistCalcService;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import cn.hsa.module.base.bdc.service.BaseDailyfirstCalcService;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.base.rate.service.BaseRateService;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.center.message.dao.MessageInfoDAO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.inpt.doctor.bo.DoctorAdviceBO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDetailDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inregister.bo.InptVisitBO;
import cn.hsa.module.inpt.medical.bo.MedicalAdviceBO;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.nurse.bo.BackCostByInputBO;
import cn.hsa.module.inpt.nurse.bo.DoctorAdviceExecuteBO;
import cn.hsa.module.inpt.nurse.dao.InptAdviceExecDAO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;
import cn.hsa.module.msg.entity.MsgTempRecordDO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.service.OperInfoRecordService;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.medical.bo.impl
 * @class_name: MedicalAdviceBOImpl
 * @Description: 医嘱核收BO实现类
 * @Author: youxianlin
 * @Email: 254580179@qq.com
 * @Date: 2020/9/9 11:16
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class MedicalAdviceBOImpl extends HsafBO implements MedicalAdviceBO {
    private Logger logger = LoggerFactory.getLogger(MedicalAdviceBOImpl.class);

    //住院医嘱
    @Resource
    private InptAdviceDAO inptAdviceDAO;
    //住院费用
    @Resource
    private InptCostDAO inptCostDAO;
    //住院费用退费
    @Resource
    private BackCostByInputBO backCostByInputBO;
    //医嘱执行
    @Resource
    private InptAdviceExecDAO inptAdviceExecDAO;
    //住院医嘱明细
    @Resource
    private InptAdviceDetailDAO inptAdviceDetailDAO;
    //住院就诊
    @Resource
    private InptVisitBO inptVisitBO;
    //医嘱目录
    @Resource
    private BaseAdviceService baseAdviceService_consumer;
    //用户
    @Resource
    private SysUserService sysUserService_consumer;
    //系统参数
    @Resource
    private SysParameterService sysParameterService_consumer;
    //频率
    @Resource
    private BaseRateService baseRateService_consumer;
    //药品
    @Resource
    private BaseDrugService baseDrugService_consumer;
    //材料
    @Resource
    private BaseMaterialService baseMaterialService_consumer;
    //项目
    @Resource
    private BaseItemService baseItemService_consumer;
    //住院退药
//    @Resource
//    private InBackDrugService inBackDrugService_consumer;
    //辅助计费
    @Resource
    private BaseAssistCalcService baseAssistCalcService_consumer;
    //每日首次计费
    @Resource
    private BaseDailyfirstCalcService baseDailyfirstCalcService_consumer;
    //取消执行记录
    @Resource
    private DoctorAdviceExecuteBO doctorAdviceExecuteBO;
    //手术申请
    @Resource
    private OperInfoRecordService operInfoRecordService_consumer;
    //费用优惠
    @Resource
    private BasePreferentialService basePreferentialService_consumer;
    //医生站
    @Resource
    private DoctorAdviceBO doctorAdviceBO;
    //疾病
    @Resource
    private BaseDiseaseService diseaseService_consumer;
    //科室
    @Resource
    private BaseDeptService baseDeptService_consumer;
    //单据规则
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;
    //码表
    @Resource
    private SysCodeService sysCodeService_consumer;
    //住院患者
    @Resource
    private InptVisitDAO inptVisitDAO;

    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService_consumer;

//    @Resource
//    private MessageInfoDAO messageInfoDAO;


    /**
     * @Method: getMedicalAdvices
     * @Description: 获取未核收医嘱列表
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 11:19
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO) {
        //设置分页参数
        PageHelper.startPage(medicalAdviceDTO.getPageNo(),medicalAdviceDTO.getPageSize());

        return PageDTO.of(inptAdviceDAO.getMedicalAdvices(medicalAdviceDTO));
    }

    /**
     * @Method: modifyMedicalAdvices
     * @Description: 医嘱拒收  更新医嘱表is_reject,reject_remark
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:16
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean modifyRefuseMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO) {
        //校验必填项
        if(medicalAdviceDTO == null){
            throw new AppException("参数为空,拒收失败");
        }
        if(ListUtils.isEmpty(medicalAdviceDTO.getIds())){
            throw new AppException("参数ids为空,拒收失败");
        }
        if(StringUtils.isEmpty(medicalAdviceDTO.getRejectRemark())){
            throw new AppException("拒收备注为空,拒收失败");
        }

        int count =inptAdviceDAO.modifyMedicalAdvices(medicalAdviceDTO);

        // 医嘱拒收写入消息
        insertAdviceMessage(medicalAdviceDTO);
        return count>0;
    }

    /**
     * @Method: acceptMedicalAdvices
     * @Description: 医嘱核收
     * 1.获取所有的医嘱，校验是否核收
     * 2.更新医嘱表核收状态、核收人...
     * 3.根据医嘱目录表(base_advice)中的医嘱类别代码（YZLB）更新住院病人表(inpt_visit)  护理级别(nursing_code)、膳食类型(diet_type)、病情标识(Illness_code)
     * 4.核收新停医嘱，判断是否存在停同类、停非同类以及停自身的医嘱
     * 5.退费，取消执行记录，退药
     * 6.根据医嘱生成执行记录
     * 7.根据医嘱生成费用信息，领药申请记录
     * 8.静态计费
     *      入库医嘱明细表
     *      入库费用表 (执行记录一对一)
     * 9.动态计费
     *      入库费用表(执行记录一对一,费用表执行记录ID就是用来关联动静态计费)
     * 10.皮试：自动生成一条皮试临时医嘱,写入执行表(是否皮试)
     * 11.手术
     * @Param: [medicalAdviceDTO] 包含(医嘱ID,和核收人信息,hospCode)
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:31
     * @Return: java.lang.Boolean
     **/
    @Override
    public  Boolean modifyAcceptMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO) {
        try {
            logger.info("====长期费用1："+DateUtils.format());
            //校验
            if(medicalAdviceDTO == null) {
                throw new AppException("参数为空,核收失败");
            }
            //需要核收的医嘱ID
            List<String> adviceIds = medicalAdviceDTO.getIds();
            if(ListUtils.isEmpty(adviceIds)) {
                throw new AppException("医嘱ID为空,核收失败");
            }

            //根据医嘱ID获取医嘱记录
            List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);
            if(ListUtils.isEmpty(inptAdviceDTOList)) {
                throw new AppException("根据传入的医嘱ID未获取到医嘱信息");
            }

            //查询核收的同组未勾上的医嘱
            List<Integer> groupNos = inptAdviceDTOList.stream().filter(s-> s.getGroupNo() > 0).map(InptAdviceDTO::getGroupNo).collect(Collectors.toList()) ;
            //同组的才会去处理（并将医嘱集合跟id集合进行重新赋值）
            if (!ListUtils.isEmpty(groupNos)) {
                inptAdviceDTOList = inptAdviceDAO.findGroupAdvice(inptAdviceDTOList);
                adviceIds = inptAdviceDTOList.stream().map(InptAdviceDTO::getId).distinct().collect(Collectors.toList()) ;
            }
            //需要停嘱的医嘱集合
            List<InptAdviceDTO> stopAdviceList = new ArrayList<>();
            //校验是否有医嘱已核收
            for (InptAdviceDTO inptAdviceDTO:inptAdviceDTOList) {
                // 提交的医嘱才能核收
                if (!Constants.SF.S.equals(inptAdviceDTO.getIsSubmit())) {
                    throw new AppException("["+inptAdviceDTO.getContent()+"]未提交,不能核收");
                }
                // 拒收医嘱不能核收
                if (Constants.SF.S.equals(inptAdviceDTO.getIsReject())) {
                    throw new AppException("["+inptAdviceDTO.getContent()+"]已拒收,不能核收");
                }
                // 新开医嘱已核收
                if (Constants.SF.S.equals(inptAdviceDTO.getIsCheck()) && Constants.SF.F.equals(inptAdviceDTO.getIsStop())) {
                    throw new AppException("新开医嘱["+inptAdviceDTO.getContent()+"]已核收,不能重复核收");
                }
                // 新停医嘱已核收
                if (Constants.SF.S.equals(inptAdviceDTO.getIsStopCheck()) && Constants.SF.S.equals(inptAdviceDTO.getIsStop())) {
                    throw new AppException("新停医嘱["+inptAdviceDTO.getContent()+"]已核收,不能重复核收");
                }
                //停嘱
                if (Constants.SF.S.equals(inptAdviceDTO.getIsStop())) {
                    inptAdviceDTO.setIsStopCheck("1");
                    inptAdviceDTO.setStopCheckId(medicalAdviceDTO.getCheckId());
                    inptAdviceDTO.setStopCheckName(medicalAdviceDTO.getCheckName());
                    inptAdviceDTO.setStopCheckTime(medicalAdviceDTO.getCheckTime());
                    stopAdviceList.add(inptAdviceDTO);
                }else{
                    inptAdviceDTO.setIsCheck("1");
                    inptAdviceDTO.setCheckId(medicalAdviceDTO.getCheckId());
                    inptAdviceDTO.setCheckName(medicalAdviceDTO.getCheckName());
                    inptAdviceDTO.setCheckTime(medicalAdviceDTO.getCheckTime());
                }
            }

            //根据医嘱ID获取住院就诊信息
            List<InptVisitDTO> inptVisitDTOList = inptVisitBO.getVisitByAdviceId(adviceIds, medicalAdviceDTO.getHospCode());
            if(ListUtils.isEmpty(inptVisitDTOList)) {
                throw new AppException("获取住院病人信息为空");
            }
            for (InptVisitDTO inptVisitDTO:inptVisitDTOList) {
                if (!Constants.BRZT.ZY.equals(inptVisitDTO.getStatusCode())) {
                    throw new AppException("需要核收病人必须是在院状态");
                }
            }
            //医嘱更新核收信息(新开医嘱、新停医嘱)
            updateCheckInfo(inptAdviceDTOList);

            //更新住院病人表(inpt_visit)  护理级别(nursing_code)、膳食类型(diet_type)、病情标识(Illness_code)
            updateInpVIsitInfo(inptVisitDTOList);

            //判断是否存在停同类、停非同类以及停自身的医嘱(新开医嘱)
            stopAdvice(medicalAdviceDTO, adviceIds,stopAdviceList);

            // 医嘱停嘱核收-->膳食/护理/危重修改 add luoyong 2021-12-28
            if (!ListUtils.isEmpty(stopAdviceList)) {
                this.updateStopInptVisitBizType(stopAdviceList);
            }

            //退费，取消执行记录，退药
            changeReturnCost(medicalAdviceDTO, stopAdviceList);

            //静态计费
            buildStaticCost(medicalAdviceDTO.getHospCode(), adviceIds);
            logger.info("====长期费用7："+DateUtils.format());
            //执行记录
            buildAdviceExec(medicalAdviceDTO, adviceIds,"0");
            logger.info("====长期费用8："+DateUtils.format());
            //根据医嘱生成费用记录、待领记录、更新住院就诊表费用信息、医嘱表最近执行时间
            buildAdviceCost(medicalAdviceDTO,adviceIds,inptVisitDTOList,"0");
            logger.info("====长期费用9："+DateUtils.format());
            //动态计费
            buildDynamic(medicalAdviceDTO, adviceIds,"0");
            logger.info("====长期费用10："+DateUtils.format());
            //手术
            buildOperInfo(medicalAdviceDTO, inptAdviceDTOList, inptVisitDTOList);

            //医技
            buildMedic(medicalAdviceDTO, adviceIds);

            //更新就诊表费用
            if (!ListUtils.isEmpty(inptVisitDTOList)) {
                inptVisitDAO.updateInptVisitAmount(inptVisitDTOList);
            }
            // 核收消息消费 liuliyun 2021/12/03
            consumeCheckAdviceMessage(inptAdviceDTOList,medicalAdviceDTO.getHospCode());
            //医嘱更新最近执行之间
            //if (!ListUtils.isEmpty(adviceIds)) {
            //inptAdviceDAO.updateLastExeTime(medicalAdviceDTO, adviceIds);
            //}
            logger.info("====长期费用13："+DateUtils.format());
        } catch (Exception e) {
            AppException tmp = new AppException("核收失败:"+e.getMessage());
            // 保存堆栈信息避免被覆盖
            tmp.setStackTrace(e.getStackTrace());
            // 保存被抑制的异常信息
            for(Throwable t : e.getSuppressed()){
                tmp.addSuppressed(t);
            }
            throw tmp;
        }
        return true;
    }

    /**
     * @Method: buildMedic
     * @Description:
         * CXGL_BBLX_SHOW：需收取静脉采血医技分类代码
     * MZSF_JMCX_MLID:自动带入的静脉采血医嘱目录编码
     * 容器费用:医嘱目录配置容器类容->码表备注配置容器具体费用项目
     * 如何判断是否收取采血费和容器费:根据费用表费用来源ID,时间查询费用表
     *
     * 1.先找到系统参数判断是否收容器费用, 采血费
     * 2.判断每个人每一天只收取一次容器费用,采血费
     * 3.生成费用
     * 4.医技申请单入库
     * 5.医技申请明细入库
     * @Param: [medicalAdviceDTO, inptAdviceDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/11 11:47
     * @Return: void
     **/
    private void buildMedic(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds) {
        if (ListUtils.isEmpty(adviceIds)) {
            return;
        }
        //医技分类代码集合
        String[] codeArray = {};

        //根据主键获取医嘱记录
        List<InptAdviceDTO> inptAdviceDTOs = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);
        if (ListUtils.isEmpty(inptAdviceDTOs)) {
            throw new AppException("获取医嘱信息为空");
        }

        //根据医嘱ID获取住院就诊信息
        List<InptVisitDTO> inptVisitDTOList = inptVisitBO.getVisitByAdviceId(adviceIds, medicalAdviceDTO.getHospCode());
        if(ListUtils.isEmpty(inptVisitDTOList)) {
            throw new AppException("获取住院病人信息为空");
        }

        //获取参数(需收取静脉采血医技分类代码)
        SysParameterDTO cxData = getSysParameterDTO(medicalAdviceDTO.getHospCode(), "CXGL_BBLX_SHOW");
        if (cxData !=null && !StringUtils.isEmpty(cxData.getValue())) {
            codeArray = cxData.getValue().split(",");
        }

        for (InptVisitDTO visitDTO:inptVisitDTOList) {
            //默认费用为0
            visitDTO.setTotalCost(BigDecimal.valueOf(-1));

            //获取用户下所有的医嘱目录集合
            List<BaseAdviceDTO> baseAdviceDTOList = inptAdviceDAO.queryAdvicesByVisitId(visitDTO.getHospCode(), visitDTO.getId(), adviceIds);
            if (ListUtils.isEmpty(baseAdviceDTOList)) {
                continue;
            }

            //循环医嘱
            for (InptAdviceDTO adviceDTO:inptAdviceDTOs) {
                if (!visitDTO.getId().equals(adviceDTO.getVisitId())) {
                    continue;
                }
                //LIS、PACS才有医技申请单和相关费用
                if (!Constants.YZLB.YZLB3.equals(adviceDTO.getTypeCode()) && !Constants.YZLB.YZLB12.equals(adviceDTO.getTypeCode())) {
                    continue;
                }
                //LIS、PACS只能是临时医嘱
                if (!"1".equals(adviceDTO.getIsLong())) {
                    throw new AppException("LIS、PACS类医嘱只能是临时医嘱");
                }
                //是否合管
                boolean flag = false;
                //主管ID
                String mergeId = "";

                //获取该医嘱的医嘱目录
                BaseAdviceDTO baseAdvice = getBaseAdviceDTO(adviceDTO.getHospCode(), adviceDTO.getItemId());

                //获取容器码表明细数据
                List<SysCodeDetailDTO> sysCodeDetailDTOList = getSysCodeDetail(adviceDTO.getHospCode(),"RQ",baseAdvice.getContainerCode());

                //获取参数(自动带入的静脉采血医嘱目录编码)
                SysParameterDTO parameterDTO = getSysParameterDTO(medicalAdviceDTO.getHospCode(), "MZSF_JMCX_MLID");

                //根据编码获取医嘱目录(采血费)
                BaseItemDTO cxfBaseItem = null;
                if (parameterDTO != null && !StringUtils.isEmpty(parameterDTO.getValue())) {
                    cxfBaseItem = getBaseItemDTOByCode(medicalAdviceDTO.getHospCode(), parameterDTO.getValue());
                }
                /**
                 * 判断是否合管 医技分类、容器、标本不能为空,有空表示不合管
                 * 1.根据医技分类、容器、标本判断是否合管
                 * 合管:判断采血费、容器费是否已产生
                 * 不合管：直接生成费用
                 * 2.合管的数据需要处理申请单
                 */
                if (Constants.YZLB.YZLB3.equals(adviceDTO.getTypeCode()) && !StringUtils.isEmpty(baseAdvice.getContainerCode())
                        && !StringUtils.isEmpty(baseAdvice.getSpecimenCode()) && !StringUtils.isEmpty(baseAdvice.getTechnologyCode())) {
                    // 判断医技代码是否包含在 (需收取静脉采血医技分类代码)参数中
                    boolean isContainsInBloodParameters = Arrays.asList(codeArray).contains(baseAdvice.getTechnologyCode());
                    for (BaseAdviceDTO baseAdviceDTO:baseAdviceDTOList) {
                        if (StringUtils.isEmpty(baseAdviceDTO.getContainerCode())
                                || StringUtils.isEmpty(baseAdviceDTO.getSpecimenCode()) || StringUtils.isEmpty(baseAdviceDTO.getTechnologyCode())) {
                            continue;
                        }
                        //医技分类、容器、标本都相同那么就合管
                            if (baseAdvice.getContainerCode().equals(baseAdviceDTO.getContainerCode())
                                    && baseAdvice.getSpecimenCode().equals(baseAdviceDTO.getSpecimenCode())
                                    && baseAdvice.getTechnologyCode().equals(baseAdviceDTO.getTechnologyCode())) {
                                //查询该用户下采血费记录,不存在就新增
                                if (cxfBaseItem != null) {
                                    List<InptCostDTO> cxCostDTOList = inptCostDAO.queryCxFfCost(visitDTO.getHospCode(), visitDTO.getId(),
                                            DateUtils.format(medicalAdviceDTO.getCheckTime(), DateUtils.Y_M_D), cxfBaseItem.getId());
                                    if (ListUtils.isEmpty(cxCostDTOList)) {
                                        //判断该医嘱目录的医技分类是否包含在参数里面
                                        if (codeArray!=null && codeArray.length>0 && isContainsInBloodParameters) {
                                            saveBaseAdviceCost(medicalAdviceDTO, visitDTO, adviceDTO, cxfBaseItem);
                                        }
                                    }
                                }

                                //查询该用户下容器费记录,不存在就新增
                                if (!ListUtils.isEmpty(sysCodeDetailDTOList) && !StringUtils.isEmpty(sysCodeDetailDTOList.get(0).getRemark())) {
                                    List<InptCostDTO> rqCostDTOList = inptCostDAO.queryRqfCost(visitDTO.getHospCode(), visitDTO.getId(),
                                            DateUtils.format(medicalAdviceDTO.getCheckTime(), DateUtils.Y_M_D), sysCodeDetailDTOList.get(0).getRemark());
                                    if (ListUtils.isEmpty(rqCostDTOList)) {
                                        saveRqfCost(medicalAdviceDTO, visitDTO, adviceDTO, sysCodeDetailDTOList);
                                    }
                                }
                                if (StringUtils.isEmpty(mergeId)) {
                                    mergeId = baseAdviceDTO.getId();
                                } else {
                                    System.out.println("合管啦");
                                }
                                flag = true;
                            }
                    }

                    //不合管的医嘱,费用入库
                    if (!flag) {
                        //判断该医嘱目录的医技分类是否包含在参数里面,采血费
                        if (codeArray!=null && codeArray.length>0 && cxfBaseItem!=null && isContainsInBloodParameters) {
                            // 2021-10-09 更改采血费生成方式,
                            // 以前是根据医嘱目录相关参数判断是否需要合管,再去收取采血费,现在是只根据收费项目判断,当天有就不产生费用
                            // 如果要改回来 直接把这一段去掉,用下面这一段即可
                            // saveBaseAdviceCost(medicalAdviceDTO, visitDTO, adviceDTO, cxfBaseItem);
                            List<InptCostDTO> cxCostDTOList = inptCostDAO.queryCxFfCost(visitDTO.getHospCode(), visitDTO.getId(),
                                    DateUtils.format(medicalAdviceDTO.getCheckTime(), DateUtils.Y_M_D), cxfBaseItem.getId());
                            if (ListUtils.isEmpty(cxCostDTOList)) {
                                //判断该医嘱目录的医技分类是否包含在参数里面
                                if (codeArray!=null && codeArray.length>0 && isContainsInBloodParameters) {
                                    saveBaseAdviceCost(medicalAdviceDTO, visitDTO, adviceDTO, cxfBaseItem);
                                }
                            }
                        }
                        //容器费
                        if (!ListUtils.isEmpty(sysCodeDetailDTOList) && !StringUtils.isEmpty(sysCodeDetailDTOList.get(0).getRemark())) {
                            saveRqfCost(medicalAdviceDTO, visitDTO, adviceDTO, sysCodeDetailDTOList);
                        }
                    }
                }

                //医技申请单
                MedicalApplyDTO medicalApplyDTO = getMedicalApplyDTO(medicalAdviceDTO, adviceDTO, visitDTO, flag, mergeId);

                //医技申请单明细
                getMedicalApplyDetailDTO(adviceDTO, medicalApplyDTO);
            }
        }
    }

    /**
     * @Method: saveCxfCost
     * @Description: 容器费
     * @Param: [medicalAdviceDTO, visitDTO, adviceDTO, sysCodeDetailDTOList, baseAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 17:47
     * @Return: void
     **/
    private void saveRqfCost(MedicalAdviceDTO medicalAdviceDTO, InptVisitDTO visitDTO, InptAdviceDTO adviceDTO, List<SysCodeDetailDTO> sysCodeDetailDTOList) {
        if (StringUtils.isEmpty(sysCodeDetailDTOList.get(0).getRemark())) {
            return;
        }

        //获取项目信息
        BaseMaterialDTO materialDTO = getBaseMaterialDTOByCode(adviceDTO.getHospCode(), sysCodeDetailDTOList.get(0).getRemark());
        if (materialDTO == null) {
            return;
        }

        InptCostDTO inptCostDTO = new InptCostDTO();
        inptCostDTO.setId(SnowflakeUtils.getId());
        //组装住院费用参数
        inptCostDTO.setPrice(materialDTO.getPrice());
        inptCostDTO.setNum(BigDecimal.valueOf(1));
        inptCostDTO.setNumUnitCode(materialDTO.getUnitCode());
        //用法
        inptCostDTO.setHospCode(materialDTO.getHospCode());
        inptCostDTO.setVisitId(adviceDTO.getVisitId());
        inptCostDTO.setBabyId(adviceDTO.getBabyId());
        inptCostDTO.setIatId(adviceDTO.getId());
        inptCostDTO.setIatdGroupNo(adviceDTO.getGroupNo());
        inptCostDTO.setIatdSeqNo(adviceDTO.getGroupSeqNo());
        inptCostDTO.setSourceCode(Constants.FYLYFS.QTFY);
        //费用来源ID->医嘱目录ID
        inptCostDTO.setSourceId(materialDTO.getId());
        inptCostDTO.setSourceDeptId(adviceDTO.getDeptId());
        inptCostDTO.setInDeptId(adviceDTO.getInDeptId());
        //财务分类
        inptCostDTO.setBfcId(materialDTO.getBfcId());
        inptCostDTO.setItemCode(Constants.XMLB.CL);
        inptCostDTO.setItemId(materialDTO.getId());
        inptCostDTO.setItemName(materialDTO.getName());
        inptCostDTO.setUseDays(1);
        //总数量=数量(费用表)*用药天数(医嘱表)
        inptCostDTO.setTotalNum(BigDecimalUtils.multiply(inptCostDTO.getNum(), BigDecimal.valueOf(inptCostDTO.getUseDays())));
        //总数量单位
        inptCostDTO.setTotalNumUnitCode(inptCostDTO.getNumUnitCode());
        //总金额 总数量*单价
        inptCostDTO.setTotalPrice(BigDecimalUtils.multiply(inptCostDTO.getNum(),inptCostDTO.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        //优惠金额默认给0,后续统一做优惠处理,再更新
        inptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
        //优惠后总金额 = 总金额-优惠金额
        inptCostDTO.setRealityPrice(inptCostDTO.getTotalPrice());
        //开嘱医生信息
        inptCostDTO.setDoctorId(adviceDTO.getCrteId());
        inptCostDTO.setDoctorName(adviceDTO.getCrteName());
        inptCostDTO.setDeptId(adviceDTO.getDeptId());
        //是否已发药
        inptCostDTO.setIsDist("0");
        //规格
        inptCostDTO.setSpec(materialDTO.getSpec());
        //是否已退药
        inptCostDTO.setBackCode("0");
        inptCostDTO.setIsGive("0");
//        inptCostDTO.setIsOk("1");
        inptCostDTO.setIsOk(Constants.SF.S);
        inptCostDTO.setOkId(medicalAdviceDTO.getCheckId());
        inptCostDTO.setOkName(medicalAdviceDTO.getCheckName());
        inptCostDTO.setOkTime(DateUtils.getNow());
        inptCostDTO.setSettleCode("0");
        inptCostDTO.setIsCheck("0");
        //循环找到就诊信息，填充主治、经治、主管医生信息
        inptCostDTO.setZzDoctorId(visitDTO.getZzDoctorId());
        inptCostDTO.setZzDoctorName(visitDTO.getZzDoctorName());
        inptCostDTO.setJzDoctorId(visitDTO.getJzDoctorId());
        inptCostDTO.setJzDoctorName(visitDTO.getJzDoctorName());
        inptCostDTO.setZgDoctorId(visitDTO.getZgDoctorId());
        inptCostDTO.setZgDoctorName(visitDTO.getZgDoctorName());
        //执行人信息
        inptCostDTO.setExecId(StringUtils.isEmpty(adviceDTO.getExecId())?adviceDTO.getSecondExecId():adviceDTO.getExecId());
        inptCostDTO.setExecName(StringUtils.isEmpty(adviceDTO.getExecName())?adviceDTO.getSecondExecName():adviceDTO.getExecName());
        inptCostDTO.setExecTime(medicalAdviceDTO.getCheckTime());
        inptCostDTO.setExecDeptId(adviceDTO.getExecDeptId());
        //计划执行时间
        inptCostDTO.setPlanExecTime(medicalAdviceDTO.getCheckTime());
        //计费时间
        inptCostDTO.setCostTime(medicalAdviceDTO.getCheckTime());
        inptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
        //创建人信息
        inptCostDTO.setCrteId(medicalAdviceDTO.getCheckId());
        inptCostDTO.setCrteName(medicalAdviceDTO.getCheckName());
        inptCostDTO.setCrteTime(medicalAdviceDTO.getCheckTime());
        //计算优惠金额
        calculatPreferential(inptCostDTO, visitDTO.getPreferentialTypeId());

        inptCostDAO.insertInptCost(inptCostDTO);
    }

    /**
     * @Method: getSysCodeDetail
     * @Description: 获取容器码表明细数据
     * @Param: [hospCode, value]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 17:24
     * @Return: void
     **/
    private List<SysCodeDetailDTO> getSysCodeDetail(String hospCode,String code,String value) {
        Map codeMap = new HashMap();
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setHospCode(hospCode);
        sysCodeDetailDTO.setCode(code);
        sysCodeDetailDTO.setValue(value);
        codeMap.put("hospCode", hospCode);
        codeMap.put("sysCodeDetailDTO", sysCodeDetailDTO);
        List<SysCodeDetailDTO> sysCodeDetailDTOList = sysCodeService_consumer.queryCodeDetailAll(codeMap).getData();
        return sysCodeDetailDTOList;
    }

    /**
     * @Method: saveBaseAdviceCost
     * @Description: 保存采血费、容器费费用
     * LIS才有容器费、采血费
     *
     * @Param: [medicalAdviceDTO, visitDTO, adviceDTO, baseAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 16:17
     * @Return: void
     **/
    private void saveBaseAdviceCost(MedicalAdviceDTO medicalAdviceDTO, InptVisitDTO visitDTO, InptAdviceDTO adviceDTO, BaseItemDTO itemDTO) {
        if (itemDTO == null){
            return;
        }

        InptCostDTO inptCostDTO = new InptCostDTO();
        inptCostDTO.setId(SnowflakeUtils.getId());
        //组装住院费用参数
        inptCostDTO.setPrice(itemDTO.getPrice());
        inptCostDTO.setNum(new BigDecimal("1"));
        inptCostDTO.setNumUnitCode(itemDTO.getUnitCode());
        //用法
        inptCostDTO.setHospCode(itemDTO.getHospCode());
        inptCostDTO.setVisitId(adviceDTO.getVisitId());
        inptCostDTO.setBabyId(adviceDTO.getBabyId());
        inptCostDTO.setIatId(adviceDTO.getId());
        inptCostDTO.setIatdGroupNo(adviceDTO.getGroupNo());
        inptCostDTO.setIatdSeqNo(adviceDTO.getGroupSeqNo());
        //执行记录ID
//                            inptCostDTO.setAdviceExecId(execDTO.getId());
        inptCostDTO.setSourceCode(Constants.FYLYFS.QTFY);
        //费用来源ID->医嘱目录ID
        inptCostDTO.setSourceId(itemDTO.getId());
        inptCostDTO.setSourceDeptId(adviceDTO.getDeptId());
        inptCostDTO.setInDeptId(adviceDTO.getInDeptId());
        //财务分类
        inptCostDTO.setBfcId(itemDTO.getBfcId());
        inptCostDTO.setItemCode(Constants.XMLB.XM);
        inptCostDTO.setItemId(itemDTO.getId());
        inptCostDTO.setItemName(itemDTO.getName());
        inptCostDTO.setUseDays(1);
        //总数量=数量(费用表)*用药天数(医嘱表)
        inptCostDTO.setTotalNum(BigDecimalUtils.multiply(inptCostDTO.getNum(), BigDecimal.valueOf(inptCostDTO.getUseDays())));
        //总数量单位
        inptCostDTO.setTotalNumUnitCode(inptCostDTO.getNumUnitCode());
        //总金额 总数量*单价
        inptCostDTO.setTotalPrice(BigDecimalUtils.multiply(inptCostDTO.getNum(),inptCostDTO.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        //优惠金额默认给0,后续统一做优惠处理,再更新
        inptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
        //优惠后总金额 = 总金额-优惠金额
        inptCostDTO.setRealityPrice(inptCostDTO.getTotalPrice());
        //开嘱医生信息
        inptCostDTO.setDoctorId(adviceDTO.getCrteId());
        inptCostDTO.setDoctorName(adviceDTO.getCrteName());
        inptCostDTO.setDeptId(adviceDTO.getDeptId());
        //是否已发药
        inptCostDTO.setIsDist("0");
        //规格
        inptCostDTO.setSpec(itemDTO.getSpec());
        //是否已退药
        inptCostDTO.setBackCode("0");
        inptCostDTO.setIsGive("0");
        inptCostDTO.setIsOk("1");
        inptCostDTO.setSettleCode("0");
        inptCostDTO.setIsCheck("0");
        //循环找到就诊信息，填充主治、经治、主管医生信息
        inptCostDTO.setZzDoctorId(visitDTO.getZzDoctorId());
        inptCostDTO.setZzDoctorName(visitDTO.getZzDoctorName());
        inptCostDTO.setJzDoctorId(visitDTO.getJzDoctorId());
        inptCostDTO.setJzDoctorName(visitDTO.getJzDoctorName());
        inptCostDTO.setZgDoctorId(visitDTO.getZgDoctorId());
        inptCostDTO.setZgDoctorName(visitDTO.getZgDoctorName());
        //执行人信息
        inptCostDTO.setExecId(StringUtils.isEmpty(adviceDTO.getExecId())?adviceDTO.getSecondExecId():adviceDTO.getExecId());
        inptCostDTO.setExecName(StringUtils.isEmpty(adviceDTO.getExecName())?adviceDTO.getSecondExecName():adviceDTO.getExecName());
        inptCostDTO.setExecTime(medicalAdviceDTO.getCheckTime());
        inptCostDTO.setExecDeptId(adviceDTO.getExecDeptId());
        //计划执行时间
        inptCostDTO.setPlanExecTime(medicalAdviceDTO.getCheckTime());
        //计费时间
        inptCostDTO.setCostTime(medicalAdviceDTO.getCheckTime());
        inptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
        //创建人信息
        inptCostDTO.setCrteId(medicalAdviceDTO.getCheckId());
        inptCostDTO.setCrteName(medicalAdviceDTO.getCheckName());
        inptCostDTO.setCrteTime(medicalAdviceDTO.getCheckTime());
        //计算优惠金额
        calculatPreferential(inptCostDTO, visitDTO.getPreferentialTypeId());

        inptCostDAO.insertInptCost(inptCostDTO);
    }

    /**
     * @Method: getBaseAdviceDetail
     * @Description: 获取医嘱目录明细
     * @Param: [baseAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 16:06
     * @Return: java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    private List<BaseAdviceDetailDTO> getBaseAdviceDetail(String hospCode,String adviceCode) {
        Map baseMap = new HashMap();
        BaseAdviceDetailDTO baseAdviceDetailDTO = new BaseAdviceDetailDTO();
        baseAdviceDetailDTO.setHospCode(hospCode);
        baseAdviceDetailDTO.setAdviceCode(adviceCode);
        baseMap.put("hospCode", hospCode);
        baseMap.put("baseAdviceDetailDTO", baseAdviceDetailDTO);
        return baseAdviceService_consumer.queryAllAdviceDetail(baseMap).getData();
    }

    /**
     * @Method: getBaseAdviceDTO
     * @Description: 根据编码获取医嘱目录
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/14 14:40
     * @Return: cn.hsa.module.base.ba.dto.BaseAdviceDTO
     **/
    private BaseAdviceDTO getBaseAdviceDTOByCode(String hospCode,String code) {
        //根据参数值获取到对应的医嘱目录
        Map map = new HashMap();
        BaseAdviceDTO baseAdviceDTO = new BaseAdviceDTO();
        baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setCode(code);
        map.put("hospCode", hospCode);
        map.put("baseAdviceDTO", baseAdviceDTO);
        //根据编码查询医嘱目录
        BaseAdviceDTO adviceDTO = baseAdviceService_consumer.getBaseAdviceByCode(map).getData();
        /*if (adviceDTO == null) {
            throw new AppException("获取医嘱目录信息["+code+"]为空");
        }*/
        return adviceDTO;
    }

    /**
     * @Method: getMedicalApplyDetailDTO
     * @Description: 医技申请单明细
     * @Param: [medicalApplyDetailDTOList, adviceDTO, medicalApplyDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/11 11:33
     * @Return: void
     **/
    private void getMedicalApplyDetailDTO(InptAdviceDTO adviceDTO, MedicalApplyDTO medicalApplyDTO) {
        MedicalApplyDetailDTO medicalApplyDetailDTO = new MedicalApplyDetailDTO();
        medicalApplyDetailDTO.setId(SnowflakeUtils.getId());
        medicalApplyDetailDTO.setHospCode(adviceDTO.getHospCode());
        medicalApplyDetailDTO.setApplyId(medicalApplyDTO.getId());
        medicalApplyDetailDTO.setVisitId(medicalApplyDTO.getVisitId());
        medicalApplyDetailDTO.setOpdId(medicalApplyDTO.getOpdId());
        medicalApplyDetailDTO.setAdviceCode(adviceDTO.getOrderNo());
        inptAdviceDAO.insertMedicalApplyDetail(medicalApplyDetailDTO);
    }

    /**
     * @Method: getMedicalApplyDTO
     * @Description: 组装申请单
     * @Param: [medicalAdviceDTO, medicalApplyDTOList, adviceDTO, visitDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/11 11:30
     * @Return: cn.hsa.module.medic.apply.dto.MedicalApplyDTO
     **/
    private MedicalApplyDTO getMedicalApplyDTO(MedicalAdviceDTO medicalAdviceDTO, InptAdviceDTO adviceDTO, InptVisitDTO visitDTO,
                                               boolean flag, String mergeId) {
        //生成单据号
        HashMap orderMap = new HashMap();
        orderMap.put("hospCode", adviceDTO.getHospCode());
        orderMap.put("typeCode", Constants.ORDERRULE.YJ);
        String orderNo = baseOrderRuleService_consumer.getOrderNo(orderMap).getData();
        if (StringUtils.isEmpty(orderNo)) {
            throw new AppException("医技生成单据号失败");
        }

        //根据ID获取科室信息
        BaseDeptDTO deptDTO = getBaseDeptDTO(adviceDTO.getHospCode(), adviceDTO.getDeptId());

        //根据医嘱ID获取医嘱明细ID
        List<InptAdviceDetailDTO> adviceDetailDTOList = inptAdviceDetailDAO.getAdviceDetailByAdviceId(adviceDTO.getHospCode(), adviceDTO.getId());
        if (ListUtils.isEmpty(adviceDetailDTOList)) {
            throw new AppException("医嘱明细为空");
        }

        MedicalApplyDTO medicalApplyDTO = new MedicalApplyDTO();
        medicalApplyDTO.setId(SnowflakeUtils.getId());
        medicalApplyDTO.setHospCode(adviceDTO.getHospCode());
        //单据号
        medicalApplyDTO.setApplyNo(orderNo);
        //医技申请单单号回写医嘱
        adviceDTO.setTechnologyNo(orderNo);
        //查询对应的医嘱目录
        BaseAdviceDTO baseAdviceDTO =  getBaseAdviceDTO(adviceDTO.getHospCode(),adviceDTO.getItemId());
        if(baseAdviceDTO == null ){
            throw new AppException("医嘱核收生成医技申请单出错,【"+adviceDTO.getItemName()+"】未获取到!");
        }
        medicalApplyDTO.setMedicType(baseAdviceDTO.getTechnologyCode());
        //医技类别
        if (Constants.YZLB.YZLB3.equals(adviceDTO.getTypeCode())) {
            medicalApplyDTO.setTypeCode(Constants.CFLB.LIS);
        } else if (Constants.YZLB.YZLB12.equals(adviceDTO.getTypeCode())) {
            medicalApplyDTO.setTypeCode(Constants.CFLB.PACS);
        }
        medicalApplyDTO.setVisitId(adviceDTO.getVisitId());
        if (StringUtils.isNotEmpty(adviceDTO.getBabyId())) {
            medicalApplyDTO.setBabyId(adviceDTO.getBabyId());
        }
        medicalApplyDTO.setInNo(visitDTO.getInNo());
        medicalApplyDTO.setOrderNo(adviceDTO.getOrderNo());
        if (Constants.BRZT.ZY.equals(visitDTO.getStatusCode())) {
            medicalApplyDTO.setIsInpt(Constants.SF.S);
        } else {
            medicalApplyDTO.setIsInpt(Constants.SF.F);
        }
        medicalApplyDTO.setDeptId(adviceDTO.getDeptId());
        medicalApplyDTO.setDeptName(deptDTO.getName());
        medicalApplyDTO.setDoctorId(adviceDTO.getCrteId());
        medicalApplyDTO.setDoctorName(adviceDTO.getCrteName());
        medicalApplyDTO.setApplyTime(DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
        medicalApplyDTO.setImpDeptId(adviceDTO.getExecDeptId());
        //条形码
//        medicalApplyDTO.setBarCode(SnowflakeUtils.getId());
        // 条形码
        //生成单据号
        HashMap orderMapTXM = new HashMap();
        orderMap.put("hospCode", adviceDTO.getHospCode());
        orderMap.put("typeCode", Constants.ORDERRULE.TXM);
        String barCode = baseOrderRuleService_consumer.getOrderNo(orderMap).getData();
        if (StringUtils.isEmpty(barCode)) {
            throw new AppException("条形码生成失败");
        }
        medicalApplyDTO.setBarCode(barCode);
        //对应医嘱/处方明细ID
        medicalApplyDTO.setOpdId(adviceDetailDTOList.get(0).getId());
        medicalApplyDTO.setContent(adviceDTO.getContent());
        //是否合管
        medicalApplyDTO.setIsMerge(Constants.SF.F);
        //合管主单ID（自身ID）
        medicalApplyDTO.setMergeId(mergeId);
        if (flag && !StringUtils.isEmpty(mergeId)) {
            //根据医嘱ID获取医嘱明细ID
            List<InptAdviceDetailDTO> mergeAdviceDetailList = inptAdviceDetailDAO.getAdviceDetailByAdviceId(adviceDTO.getHospCode(), mergeId);
            if (ListUtils.isEmpty(mergeAdviceDetailList)) {
                throw new AppException("合管主管ID对应的医嘱明细为空");
            }

            //根据医嘱ID查询申请单
            MedicalApplyDTO applyDTO = inptAdviceDAO.queryApplyByAdviceId(medicalApplyDTO.getHospCode(), mergeAdviceDetailList.get(0).getId(), medicalApplyDTO.getVisitId());
            if (applyDTO != null) {
                medicalApplyDTO.setMergeId(applyDTO.getMergeId());
                medicalApplyDTO.setBarCode(applyDTO.getBarCode());
            }

            medicalApplyDTO.setIsMerge(Constants.SF.S);
        }
        //防止合管id为空，导致合管打印数据有误
        if(StringUtils.isEmpty(mergeId)){
            medicalApplyDTO.setMergeId(medicalApplyDTO.getId());
        }
        medicalApplyDTO.setDocumentSta("01");
        medicalApplyDTO.setCrteId(medicalAdviceDTO.getCheckId());
        medicalApplyDTO.setCrteName(medicalAdviceDTO.getCheckName());
        medicalApplyDTO.setCrteTime(medicalAdviceDTO.getCheckTime());
        //入库
        inptAdviceDAO.insertMedicalApply(medicalApplyDTO);

        //回写医技申请的
        inptAdviceDAO.updateTechnologyNoById(adviceDTO);
        return medicalApplyDTO;
    }

    /**
     * @Method: getBaseDeptDTO
     * @Description: 根据ID获取科室信息
     * @Param: [hospCode, deptId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/11 11:16
     * @Return: cn.hsa.module.base.dept.dto.BaseDeptDTO
     **/
    private BaseDeptDTO getBaseDeptDTO(String hospCode, String deptId) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setId(deptId);
        map.put("baseDeptDTO", baseDeptDTO);
        BaseDeptDTO deptDTO = baseDeptService_consumer.getById(map).getData();
        if (deptDTO == null) {
            throw new AppException("科室信息为空");
        }
        return deptDTO;
    }

    /**
     * @Method: buildOperInfo
     * @Description: 手术申请
     * 如果有手术的医嘱，手术申请表插入数据
     * 2021-07-09 16:14 手术医嘱核收时不应该更新手术记账时间，
     *            手术记账时间应该是补记账时才做更新操作,已和姚凡确认
     * @Param: [medicalAdviceDTO, inptAdviceDTOList, inptVisitDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/3 19:25
     * @Return: void
     **/
    private void buildOperInfo(MedicalAdviceDTO medicalAdviceDTO, List<InptAdviceDTO> inptAdviceDTOList, List<InptVisitDTO> inptVisitDTOList) {
        for (InptAdviceDTO inptAdviceDTO:inptAdviceDTOList) {
            //执行科室不能为空,医嘱类别为手术,手术是临时医嘱
            if (Constants.YZLB.YZLB5.equals(inptAdviceDTO.getTypeCode())) {
                if (StringUtils.isEmpty(inptAdviceDTO.getExecDeptId())) {
                    throw new AppException("手术类医嘱["+inptAdviceDTO.getContent()+"]执行科室不能为空");
                }
                if (!"1".equals(inptAdviceDTO.getIsLong())) {
                    throw new AppException("手术类医嘱["+inptAdviceDTO.getContent()+"]只能是临时医嘱");
                }
                OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
                operInfoRecordDTO.setId(SnowflakeUtils.getId());
                operInfoRecordDTO.setHospCode(inptAdviceDTO.getHospCode());
                operInfoRecordDTO.setVisitId(inptAdviceDTO.getVisitId());
                operInfoRecordDTO.setBabyId(inptAdviceDTO.getBabyId());
                //住院号
                for (InptVisitDTO visitDTO:inptVisitDTOList) {
                    //判断当前病人是否为该医嘱下的病人
                    if(!inptAdviceDTO.getVisitId().equals(visitDTO.getId())) {
                        continue;
                    }
                    operInfoRecordDTO.setInNo(visitDTO.getInNo());
                    operInfoRecordDTO.setName(visitDTO.getName());
                    operInfoRecordDTO.setGenderCode(visitDTO.getGenderCode());
                    operInfoRecordDTO.setAge(visitDTO.getAge());
                    operInfoRecordDTO.setAgeUnitCode(visitDTO.getAgeUnitCode());
                    operInfoRecordDTO.setBedId(visitDTO.getBedId());
                    operInfoRecordDTO.setBedName(visitDTO.getBedName());
                    //血型代码
//                        operInfoRecordDTO.setBloodCode("");
                    //入院诊断
                    operInfoRecordDTO.setInDiseaseId(visitDTO.getInDiseaseId());
                    operInfoRecordDTO.setInDiseaseName(visitDTO.getInDiseaseName());
                    //病区ID
                    operInfoRecordDTO.setWardId(visitDTO.getInWardId());
                    //根据ID获取ICD10编码
                    Map map = new HashMap();
                    BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
                    baseDiseaseDTO.setHospCode(visitDTO.getHospCode());
                    baseDiseaseDTO.setId(visitDTO.getInDiseaseId());
                    map.put("hospCode", visitDTO.getHospCode());
                    map.put("baseDiseaseDTO", baseDiseaseDTO);
                    BaseDiseaseDTO diseaseDTO = diseaseService_consumer.getById(map).getData();
                    if (diseaseDTO != null) {
                        operInfoRecordDTO.setInDiseaseIcd10(diseaseDTO.getNationCode());
                    }
                    // 赋值完成后结束当前循环
                    break;
                }

                operInfoRecordDTO.setContent(inptAdviceDTO.getContent());
                // 手术的医嘱id
                operInfoRecordDTO.setOperDiseaseId(inptAdviceDTO.getItemId());
                operInfoRecordDTO.setOperDiseaseName(inptAdviceDTO.getItemName());

                operInfoRecordDTO.setAdviceId(inptAdviceDTO.getId());
                operInfoRecordDTO.setDeptId(inptAdviceDTO.getInDeptId());

                // operInfoRecordDTO.setOperDeptId(inptAdviceDTO.getExecDeptId());
                operInfoRecordDTO.setStatusCode("0");
//                    operInfoRecordDTO.setIsCost("1");
                //费用
//                    operInfoRecordDTO.setTotalPrice(totalPrice);
                operInfoRecordDTO.setCostTime(medicalAdviceDTO.getCheckTime());
                operInfoRecordDTO.setCostId(medicalAdviceDTO.getCheckId());
                operInfoRecordDTO.setCostName(medicalAdviceDTO.getCheckName());
                operInfoRecordDTO.setCrteTime(medicalAdviceDTO.getCheckTime());
                operInfoRecordDTO.setCrteId(medicalAdviceDTO.getCheckId());
                operInfoRecordDTO.setCrteName(medicalAdviceDTO.getCheckName());
                //主刀医生信息
                operInfoRecordDTO.setDoctorId(inptAdviceDTO.getCrteId());
                operInfoRecordDTO.setDoctorName(inptAdviceDTO.getCrteName());

                Map map = new HashMap();
                map.put("hospCode", medicalAdviceDTO.getHospCode());
                map.put("operInfoRecordDTO", operInfoRecordDTO);
                operInfoRecordService_consumer.updateSurgeryByAdviceId(map);
            }
        }
    }

    /**
     * @Method: modifyLongCost
     * @Description: 长期费用滚动
     * @Param: [hospCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/31 14:35
     * @Return: java.lang.Boolean
     **/
    @Override
    public synchronized Boolean modifyLongCost(MedicalAdviceDTO medicalAdviceDTO) {
        try {
            logger.info("====长期费用1："+DateUtils.format());
            //获取需要滚动长期费用的医嘱集合(长期医嘱、已核收、未停止、在院病人、拒收标志正常、签名状态非已取消、非文字医嘱、开嘱科室过滤)  最近执行时间<=当前时间
            List<String> adviceIds = inptAdviceDAO.queryAdvicesToLongCost(medicalAdviceDTO);
            if (ListUtils.isEmpty(adviceIds)) {
                return false;
            }
            //根据医嘱ID获取住院就诊信息
            List<InptVisitDTO> inptVisitDTOList = inptVisitBO.getVisitByAdviceId(adviceIds, medicalAdviceDTO.getHospCode());
            if(ListUtils.isEmpty(inptVisitDTOList)) {
                throw new AppException("获取住院病人信息为空");
            }

            logger.info("====长期费用2："+DateUtils.format());
            //执行记录
            buildAdviceExec(medicalAdviceDTO, adviceIds,"1");
            logger.info("====长期费用3："+DateUtils.format());
            //根据医嘱生成费用记录、待领记录、更新住院就诊表费用信息、医嘱表最近执行时间
            buildAdviceCost(medicalAdviceDTO,adviceIds,inptVisitDTOList,"1");
            logger.info("====长期费用4："+DateUtils.format());
            //动态计费
            buildDynamic(medicalAdviceDTO, adviceIds,"1");
            logger.info("====长期费用5："+DateUtils.format());
            //更新就诊表费用
            if (!ListUtils.isEmpty(inptVisitDTOList)) {
                inptVisitDAO.updateInptVisitAmount(inptVisitDTOList);
            }
            //医嘱更新最近执行之间
//            if (!ListUtils.isEmpty(adviceIds)) {
//                inptAdviceDAO.updateLastExeTime(medicalAdviceDTO, adviceIds);
//            }

            //获取医嘱集合并筛选预停时间是当天的，进行停嘱操作
            List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);
            if (!ListUtils.isEmpty(inptAdviceDTOList)) {
                inptAdviceDTOList = inptAdviceDTOList.stream().filter(s->s.getPlanStopTime() !=null && (DateUtils.differentDays(new Date(),s.getPlanStopTime()) <= 0)).collect(Collectors.toList());
                InptAdviceDTO adviceDTO = new InptAdviceDTO ();
                adviceDTO.setCrteId("-1");
                adviceDTO.setCrteName("预停自动停嘱");
                updateStopAdvice(medicalAdviceDTO, adviceDTO, inptAdviceDTOList);
            }

            logger.info("====长期费用6："+DateUtils.format());
        } catch (Exception e) {
            AppException tmp = new AppException("长期费用计算失败:"+e.getMessage());
            tmp.setStackTrace(e.getStackTrace());
            // 保存被抑制的异常信息
            for(Throwable t : e.getSuppressed()){
                tmp.addSuppressed(t);
            }
            throw tmp;
        }
        return true;
    }

    /**
     * @Method: modifyBuildDynamic
     * @Description:动态计费
     * 同一组医嘱  第一条大输液医嘱的数量作为动态计费数量
     * 判断首次计费还是连续计费，辅助计费  插入费用表
     * 1.查询出医嘱所属的患者列表
     * 2.获取所有的医嘱列表
     * 3.获取到每一天的每日执行次数，总次数
     * 4.根据辅助计费明细循环组装费用信息
     * @Param: [medicalAdviceDTO, inptAdviceDTOList, inptVisitDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 15:23
     * @Return: void
     **/
    @Override
    public boolean buildDynamic(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds, String type) {
        //新增费用集合
        List<InptCostDTO> inptCostDTOList = new ArrayList<>();
        //退费费用集合
        List<InptCostDTO> tfInptCostDTOList = new ArrayList<>();
        //医嘱ID
        List<String> adviceIdList = new ArrayList<>();

        //根据主键获取医嘱记录
        List<InptAdviceDTO> inptAdviceDTOs = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);
        if (!ListUtils.isEmpty(inptAdviceDTOs)) {
            inptAdviceDTOs.stream().forEach(adviceDTO->{
                if (Constants.XMLB.YP.equals(adviceDTO.getItemCode())) {
                    adviceIdList.add(adviceDTO.getId());
                }
            });
        }
        if (ListUtils.isEmpty(adviceIdList)) {
            return true;
        }

        //根据医嘱ID获取住院就诊信息
        List<InptVisitDTO> inptVisitDTOList = inptVisitBO.getVisitByAdviceId(adviceIdList, medicalAdviceDTO.getHospCode());
        if(ListUtils.isEmpty(inptVisitDTOList)) {
            throw new AppException("获取住院病人信息为空");
        }

        //获取每日首次计费信息
        List<BaseDailyfirstCalcDTO> baseDailyfirstCalcDTOList = getDailyFirstCalc(medicalAdviceDTO.getHospCode());

        for (InptVisitDTO visitDTO:inptVisitDTOList) {
            //默认费用为0
            visitDTO.setTotalCost(BigDecimal.valueOf(0));
            //某个患者下需要生成动态费用的医嘱(大输液,未停嘱)
            List<InptAdviceDTO> adviceDTOList = new ArrayList<>();

            //获取到当前患者每个时间的首次费用最大次数
            Map<String, BigDecimal> dtfyScMap = getScNum(type, baseDailyfirstCalcDTOList, visitDTO, adviceDTOList,medicalAdviceDTO);

            for(String key : dtfyScMap.keySet()){
                //日期
                String time = key.split("_")[1];
                //用法
                String usageCode = key.split("_")[0];
                //每日首次数
                BigDecimal scNum = dtfyScMap.get(key);

                //获取某个用户某一天的所有首次费用
                List<InptCostDTO> scVisitCostDTOList = inptCostDAO.queryScCost(time,visitDTO.getId(),visitDTO.getHospCode(),"1",usageCode);

                //获取某个用户某一天的所有续瓶费用
                List<InptCostDTO> lxVisitCostDTOList = inptCostDAO.queryScCost(time,visitDTO.getId(),visitDTO.getHospCode(),"0",usageCode);

                //还需要生成的首次计费次数
                scNum = BigDecimalUtils.subtract(scNum, BigDecimal.valueOf(scVisitCostDTOList.size()));
                if (scNum.compareTo(BigDecimal.valueOf(0)) < 0) {
                    continue;
                }

                //获取同用法的医嘱
                List<InptAdviceDTO> inptAdviceDTOS = adviceDTOList.stream().filter(s-> usageCode.equals(s.getUsageCode())).collect(Collectors.toList());
                if (ListUtils.isEmpty(adviceDTOList)) {
                    continue;
                }

                //生成动态费用
                getCost(medicalAdviceDTO, inptCostDTOList, tfInptCostDTOList, visitDTO, inptAdviceDTOS, time, scNum,
                        scVisitCostDTOList, lxVisitCostDTOList, usageCode);
            }
        }

        //费用入库
        if (!ListUtils.isEmpty(inptCostDTOList)) {
            inptCostDAO.insertInptCostBatch(inptCostDTOList);
        }

        //冲红费用
        if (!ListUtils.isEmpty(tfInptCostDTOList)) {
            for (InptCostDTO costDTO:tfInptCostDTOList) {
                costDTO.setBackNum(costDTO.getNum());
                costDTO.setBackAmount(costDTO.getRealityPrice());
            }
            //调用退费接口
            Map<String, Object> backCostMap = new HashMap<>();
            backCostMap.put("hospCode", medicalAdviceDTO.getHospCode());
            backCostMap.put("userId", medicalAdviceDTO.getCheckId());
            backCostMap.put("userName", medicalAdviceDTO.getCheckName());
            backCostMap.put("deptId", medicalAdviceDTO.getDeptId());
            backCostMap.put("sourceType", "medicalAdvice");
            backCostMap.put("inptCostDTOs", tfInptCostDTOList);
            backCostByInputBO.saveBackCostWithInpt(backCostMap);
        }
        return true;
    }

    /**
     * @param medicalAdviceDTO
     * @Method: updateAdviceInChecked
     * @Description: 修改医嘱信息，核收人，核对签名人，核收状态
     * isChecked: 0：未核收，1：已核对，2：已核收未核对，3：核对退回，4：
     * @Param: [medicalAdviceDTO]
     * @Author: pengbo
     * @Date: 2022/08/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public Boolean updateAdviceInChecked(MedicalAdviceDTO medicalAdviceDTO) {

        if(medicalAdviceDTO.getKzIds() != null && medicalAdviceDTO.getKzIds().length>0 ){
            inptAdviceDAO.updateNewAdviceInChecked(medicalAdviceDTO);
        }
        if(medicalAdviceDTO.getStopIds() != null && medicalAdviceDTO.getStopIds().length>0 ){
            inptAdviceDAO.updateStopAdviceInChecked(medicalAdviceDTO);
        }

        return true;
    }

    /**
     * @Method: getCost
     * @Description:
     * 1.循环医嘱
     * 2.获取医嘱下面的执行记录
     * 3.计算出每日执行次数
     * 4.循环执行记录
     *   1.获取到当前执行记录下的首次计费
     *   2.获取到当前执行记录下的续瓶计费
     *   3.判断当前执行记录下面是否还需要生成动态费用--->有-->判断是生成首次费用还是续瓶费用
     *   4.判断是否还需要生成首次费用-->要-->冲红当前执行记录下面的续瓶费用,生成首次费用
     * @Param: [medicalAdviceDTO, inptCostDTOList, tfInptCostDTOList, visitDTO, adviceDTOList, time, scNum, scVisitCostDTOList, lxVisitCostDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/19 15:16
     * @Return: void
     **/
    private void getCost(MedicalAdviceDTO medicalAdviceDTO, List<InptCostDTO> inptCostDTOList, List<InptCostDTO> tfInptCostDTOList, InptVisitDTO visitDTO,
                         List<InptAdviceDTO> adviceDTOList, String time, BigDecimal scNum, List<InptCostDTO> scVisitCostDTOList,
                         List<InptCostDTO> lxVisitCostDTOList, String usageCode) {
        for (InptAdviceDTO inptAdviceDTO :adviceDTOList) {
            //根据医嘱ID获取执行记录
            List<InptAdviceExecDTO> execDTOList = inptAdviceExecDAO.queryExecByAdviceId(inptAdviceDTO.getId(),medicalAdviceDTO.getHospCode(),time);
            //获取频率信息
            BaseRateDTO baseRateDTO = getBaseRateDTO(inptAdviceDTO.getHospCode(), inptAdviceDTO.getRateId());
            //频率执行次数
//            int rateTimes = baseRateDTO.getDailyTimes().intValue();
            //计算每日执行次数
            int dailyTimes = getDailyTimes(inptAdviceDTO, DateUtils.parse(time,DateUtils.Y_M_D), DateUtils.parse(time,DateUtils.Y_M_D), baseRateDTO.getDailyTimes().intValue());

//            int totalNum = dailyTimes*inptAdviceDTO.getNum().intValue();

            //需要生成多少条记录,根据费用表计算
            int totalNum = inptCostDAO.queryCostZSL(inptAdviceDTO.getId(),inptAdviceDTO.getHospCode(),DateUtils.parse(time,DateUtils.Y_M_D));

            //获取到医嘱下面已经产生多少条动态费用
            int syNum = inptCostDAO.queryYcsDtCost(inptAdviceDTO.getId(),inptAdviceDTO.getHospCode(),DateUtils.parse(time,DateUtils.Y_M_D));
            if (syNum <= 0) {
                syNum = 0;
            }
            totalNum = totalNum - syNum;

            for (int i=0;i<execDTOList.size();i++) {
                if (totalNum <= 0) {
                    break;
                }
                if (i >= dailyTimes) {
                    continue;
                }

                InptAdviceExecDTO execDTO = execDTOList.get(i);
                //当前执行记录下对应的首次计费
                List<InptCostDTO> scAdviceCostDTOList = new ArrayList<>();

                for (InptCostDTO costDTO:scVisitCostDTOList) {
                    if (execDTO.getId().equals(costDTO.getAdviceExecId())) {
                        scAdviceCostDTOList.add(costDTO);
                    }
                }

                //当前执行记录下对应的续瓶计费
                List<InptCostDTO> lxAdviceCostDTOList = new ArrayList<>();
                for (InptCostDTO costDTO:lxVisitCostDTOList) {
                    if (execDTO.getId().equals(costDTO.getAdviceExecId())) {
                        lxAdviceCostDTOList.add(costDTO);
                    }
                }

                //当前执行记录还没有动态计费的次数(0.5用量的情况下不会生成连续输液费用)
                int exeNum  = inptAdviceDTO.getNum().setScale( 0, BigDecimal.ROUND_UP ).intValue();
                exeNum = exeNum-scAdviceCostDTOList.size()- lxAdviceCostDTOList.size();

                //判断当前执行记录下面是否还需要生成动态费用--->有-->判断是生成首次费用还是续瓶费用
                if (exeNum > 0) {
                    for (int j=0;j<exeNum;j++) {
                        if (totalNum <= 0) {
                            break;
                        }
                        if (scNum.compareTo(BigDecimal.valueOf(0)) > 0) {
                            buidDtCost(inptAdviceDTO, execDTO, medicalAdviceDTO, visitDTO, "1", time,inptCostDTOList, usageCode);
                            scNum = BigDecimalUtils.subtract(scNum,BigDecimal.valueOf(1));
                        } else {
                            //续瓶
                            buidDtCost(inptAdviceDTO, execDTO, medicalAdviceDTO, visitDTO, "0", time,inptCostDTOList, usageCode);
                        }
                        totalNum--;
                    }
                }

                //判断是否还需要生成首次费用-->要-->冲红当前执行记录下面的续瓶费用,生成首次费用
                if (scNum.compareTo(BigDecimal.valueOf(0))>0 && !ListUtils.isEmpty(lxAdviceCostDTOList)) {
                    for (InptCostDTO costDTO:lxAdviceCostDTOList) {
                        if (totalNum <= 0) {
                            break;
                        }
                        buidDtCost(inptAdviceDTO, execDTO, medicalAdviceDTO, visitDTO, "1", time,inptCostDTOList, usageCode);
                        scNum = BigDecimalUtils.subtract(scNum,BigDecimal.valueOf(1));
                        //冲红的费用
                        tfInptCostDTOList.add(costDTO);
                        totalNum--;
                    }
                }
            }
        }
    }

    /**
     * @Method: getScNum
     * @Description: 获取到当前患者每个时间的首次费用最大次数
     * 1.获取用户下面的所有未核收的医嘱
     * 2.根据用法、频率、部门拿到每日首次
     * 3.判断每日执行数量是否大于每日首次
     * 总数量=当日执行次数*数量
     * A 数量:2  频率:tid  当日执行次数:1 首次:3
     * B 数量:1  评论:bid  当日执行次数:2 首次:2
     * 那么首次:2次,费用挂在A医嘱上面
     * A 数量:1  频率:tid  当日执行次数:1 首次:3
     * B 数量:3  评论:bid  当日执行次数:2 首次:2
     * 那么首次:2次,费用挂在B医嘱上面
     * @Param: [type, baseDailyfirstCalcDTOList, visitDTO, inptAdviceDTOList, adviceDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/19 14:52
     * @Return: java.util.Map<java.lang.String,java.math.BigDecimal>
     **/
    private Map<String, BigDecimal> getScNum(String type, List<BaseDailyfirstCalcDTO> baseDailyfirstCalcDTOList,
                                             InptVisitDTO visitDTO, List<InptAdviceDTO> adviceDTOList,MedicalAdviceDTO medicalAdviceDTO) {
        //首次计费数量
        Map<String, BigDecimal> dtfyScMap = new HashMap();
        //分组组号
        Map<String, Object> zhMap = new HashMap();
        //获取医嘱记录
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getAdvicesByVisitId(visitDTO.getHospCode(),visitDTO.getId());

        for (InptAdviceDTO inptAdviceDTO :inptAdviceDTOList) {
            //非药品没有动态计费
            if (!Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode())) {
                continue;
            }
            if (visitDTO.getId().equals(inptAdviceDTO.getVisitId())) {
                //同一组获取药品只获取第一个,或者没有分组
                if (zhMap.get(inptAdviceDTO.getGroupNo().toString()) == null || "0".equals(inptAdviceDTO.getGroupNo().toString())) {
                    zhMap.put(inptAdviceDTO.getGroupNo().toString(), inptAdviceDTO.getGroupNo());
                } else {
                    continue;
                }
                //获取药品信息
                BaseDrugDTO baseDrugDTO = getBaseDrugDTOById(inptAdviceDTO.getHospCode(), inptAdviceDTO.getItemId(),medicalAdviceDTO.getDeptId());
                //判断是否是大输液
                if (StringUtils.isNotEmpty(baseDrugDTO.getIsLvp()) && Constants.SF.S.equals(baseDrugDTO.getIsLvp())) {
                    //计算开始日期、结束日期、判断是否预停
                    Map<String, Object> map = getTime(inptAdviceDTO, type);
                    //开始日期
                    Date startTime = (Date) map.get("startTime");
                    //结束日期
                    Date endTime = (Date) map.get("endTime");
                    if (startTime.compareTo(endTime) > 0) {
                        continue;
                    }

                    //获取频率信息
                    BaseRateDTO baseRateDTO = getBaseRateDTO(inptAdviceDTO.getHospCode(), inptAdviceDTO.getRateId());
                    //频率执行周期
                    int day = baseRateDTO.getExecInterval().intValue();
                    //频率执行次数
                    int rateTimes = baseRateDTO.getDailyTimes().intValue();

                    while (startTime.compareTo(endTime) <= 0) {
                        //计算每日执行次数
                        int dailyTimes = getDailyTimes(inptAdviceDTO, startTime, endTime, rateTimes);
                        //如果执行执行次数小于等于0，那么就不产生执行记录和费用
                        if (dailyTimes <= 0) {
                            startTime = DateUtils.dateAdd(startTime, day);
                            continue;
                        }
                        //组装key值
                        String key = inptAdviceDTO.getUsageCode() + "_" + DateUtils.format(startTime, "yyyy-MM-dd");
                        //默认首次计费1
                        if (dtfyScMap.get(key) == null) {
                            dtfyScMap.put(key, BigDecimal.valueOf(1));
                        }
                        /***
                         * 总数量=当日执行次数*数量
                         * A 数量:2  频率:tid  当日执行次数:1 首次:1
                         * B 数量:1  评论:bid  当日执行次数:2 首次:2
                         * 那么首次:2次,费用挂在A医嘱上面
                         *
                         * A 数量:1  频率:tid  当日执行次数:1 首次:1
                         * B 数量:3  评论:bid  当日执行次数:2 首次:2
                         * 那么首次:2次,费用挂在B医嘱上面
                         */
                        int totalNum = dailyTimes*inptAdviceDTO.getNum().intValue();
                        //获取到医嘱下面还有多少费用数量(没数据:新开医嘱,使用总数量,有数据:退费或停嘱冲红的费用,总数量=费用数量)
                        int syNum = inptCostDAO.querySyCost(inptAdviceDTO.getId(),inptAdviceDTO.getHospCode(),startTime);
                        if (syNum >= 0) {
                            totalNum = syNum;
                        }

                        for (BaseDailyfirstCalcDTO baseDailyfirstCalcDTO : baseDailyfirstCalcDTOList) {
                            //判断用法、频率、部门是否相等
                            if (inptAdviceDTO.getUsageCode().equals(baseDailyfirstCalcDTO.getUsageCode())
                                    && inptAdviceDTO.getRateId().equals(baseDailyfirstCalcDTO.getRateId())
                                    && inptAdviceDTO.getDeptId().equals(baseDailyfirstCalcDTO.getDeptId())) {
                                //如果当日执行次数<每日首次  那么首次就是用当天执行次数
                                int times = 0;
                                if (totalNum < baseDailyfirstCalcDTO.getDailyFirstNum()) {
                                    times = totalNum;
                                } else {
                                    times = baseDailyfirstCalcDTO.getDailyFirstNum();
                                }
                                if (dtfyScMap.get(key) != null && dtfyScMap.get(key).intValue() < times) {
                                    dtfyScMap.put(key, BigDecimal.valueOf(times));
                                }
                            }
                        }
                        //时间根据频率周期变化 开始时间 = 开始时间+频率周期
                        startTime = DateUtils.dateAdd(startTime, day);
                    }
                    adviceDTOList.add(inptAdviceDTO);
                }
            }
        }
        return dtfyScMap;
    }

    /**
     * @Method: buidDtCost
     * @Description: 组装动态费用信息
     * 1.获取到辅助费用明细
     * 2.组装动态费用信息
     * @Param: [medicalAdviceDTO, inptAdviceDTOList, visitDTO, isFirst, usageCode, num, startTime, inptCostDTOList, dto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/12 15:10
     * @Return: void
     **/
    public void buidDtCost(InptAdviceDTO adviceDTO,InptAdviceExecDTO execDTO,MedicalAdviceDTO medicalAdviceDTO,InptVisitDTO visitDTO, String isFirst,
                           String startTime, List<InptCostDTO> inptCostDTOList, String usageCode){
        //辅助计费编码
        String code = null;
        String name = "";

        //根据用法拿到辅助计费对象
        String[] result = getBaseAssistCalcDTO(adviceDTO,adviceDTO.getUsageCode(),"1",isFirst);
        if (result!=null && result.length>0 && Integer.valueOf(result[1])>0 && !StringUtils.isEmpty(result[0])) {
            code = result[0];
            name = result[2];
        }

        if (!StringUtils.isEmpty(code)) {
            //组装获取计费明细参数
            List<BaseAssistCalcDetailDTO> assistCalcDetailDTOList = inptAdviceDAO.getAssistDetail(medicalAdviceDTO.getHospCode(), code);
            for(BaseAssistCalcDetailDTO baseAssistCalcDetailDO : assistCalcDetailDTOList){
                InptCostDTO inptCostDTO = new InptCostDTO();
                //计费时间=计费当天日期+当前时间(时分秒)
                Date date = DateUtils.parse(startTime + " " + DateUtils.format(DateUtils.getNow(), DateUtils.H_M_S), DateUtils.Y_M_DH_M_S);
                // update 计费时间=计费当天日期+核收时间(时分秒)  luoyong 2021-10-28
                if(date.before(adviceDTO.getLongStartTime())) {
                    date = DateUtils.parse(startTime + " " + DateUtils.format(adviceDTO.getLongStartTime(), DateUtils.H_M_S), DateUtils.Y_M_DH_M_S);
                    date = DateUtils.dateAddMinute(date, 10);
                }
                if (baseAssistCalcDetailDO.getItemId() == null ){
                    throw new AppException(baseAssistCalcDetailDO.getName() +"配置项目错误!");
                }
                //辅助计费 有可能为 3 项目，2材料
                if("3".equals(baseAssistCalcDetailDO.getItemCode())){
                    //获取项目信息
                    BaseItemDTO itemDTO = getBaseItemDTOByID(medicalAdviceDTO.getHospCode(),baseAssistCalcDetailDO.getItemId());
                    if (itemDTO == null){
                        throw new AppException("辅助计费【"+name+"】配置的项目有误!");
                    }
                    inptCostDTO.setPrice(itemDTO.getPrice());
                    inptCostDTO.setNumUnitCode(itemDTO.getUnitCode());
                }else if("2".equals(baseAssistCalcDetailDO.getItemCode())){
                    //获取材料信息
                    BaseMaterialDTO materialDTO = getBaseMaterialDTOByID(medicalAdviceDTO.getHospCode(),baseAssistCalcDetailDO.getItemId());
                    if (materialDTO == null){
                        throw new AppException("辅助计费【"+name+"】配置的材料有误!");
                    }

                    //之前是按最小单位算的  但是现在 辅助计费 需要根据 辅助计费明细里面的单位去确定按什么单位收费 -- 2022-05-11
                    if(StringUtils.isNotEmpty(baseAssistCalcDetailDO.getItemUnitCode()) && baseAssistCalcDetailDO.getItemUnitCode().equals(materialDTO.getSplitUnitCode())){
                        inptCostDTO.setPrice(materialDTO.getSplitPrice());
                        inptCostDTO.setNumUnitCode(materialDTO.getSplitUnitCode());
                    }else{
                        inptCostDTO.setPrice(materialDTO.getPrice());
                        inptCostDTO.setNumUnitCode(materialDTO.getUnitCode());
                    }
                }else {
                    throw new AppException("辅助计费【"+name+"】未配置项目或材料!");
                }
                //组装住院费用参数
                //inptCostDTO.setPrice(itemDTO.getPrice());
                inptCostDTO.setNum(baseAssistCalcDetailDO.getNum());
                //inptCostDTO.setNumUnitCode(itemDTO.getUnitCode());
                inptCostDTO.setId(SnowflakeUtils.getId());
                inptCostDTO.setAdvanceId(medicalAdviceDTO.getSfTqly());
                //用法
                inptCostDTO.setUsageCode(usageCode);
                inptCostDTO.setHospCode(medicalAdviceDTO.getHospCode());
                inptCostDTO.setVisitId(adviceDTO.getVisitId());
                inptCostDTO.setBabyId(adviceDTO.getBabyId());
                inptCostDTO.setIatId(adviceDTO.getId());
                inptCostDTO.setIatdGroupNo(adviceDTO.getGroupNo());
                inptCostDTO.setIatdSeqNo(adviceDTO.getGroupSeqNo());
                //执行记录ID
                inptCostDTO.setAdviceExecId(execDTO.getId());
                inptCostDTO.setSourceCode(Constants.FYLYFS.DJTJF);
                //费用来源ID->辅助计费主表ID
                inptCostDTO.setSourceId(baseAssistCalcDetailDO.getAssistId());
                inptCostDTO.setSourceDeptId(adviceDTO.getDeptId());
                inptCostDTO.setInDeptId(adviceDTO.getInDeptId());
                //财务分类
                inptCostDTO.setBfcId(baseAssistCalcDetailDO.getBfcId());
                inptCostDTO.setItemCode(baseAssistCalcDetailDO.getItemCode());
                inptCostDTO.setItemId(baseAssistCalcDetailDO.getItemId());
                inptCostDTO.setItemName(baseAssistCalcDetailDO.getItemName());
                inptCostDTO.setSpec(baseAssistCalcDetailDO.getSpec());
                //剂型代码
//                inptCostDTO.setPrepCode(inptAdviceDTO.getPrepCode());
                //剂量=剂量(医嘱表)*当天执行次数
//                inptCostDTO.setDosage(inptAdviceDTO.getDosage()!=null?BigDecimalUtils.multiply(inptAdviceDTO.getDosage(),BigDecimal.valueOf(inptAdviceDTO)):null);
//                inptCostDTO.setDosageUnitCode(inptAdviceDTO.getDosageUnitCode());
//                inptCostDTO.setRateId(inptAdviceDTO.getRateId());
//                inptCostDTO.setSpeedCode(inptAdviceDTO.getSpeedCode());
                inptCostDTO.setUseDays(adviceDTO.getUseDays()==null?1:adviceDTO.getUseDays());
                //总数量=数量(费用表)*用药天数(医嘱表)
                inptCostDTO.setTotalNum(BigDecimalUtils.multiply(inptCostDTO.getNum(),BigDecimal.valueOf(inptCostDTO.getUseDays())));
                //总数量单位
                inptCostDTO.setTotalNumUnitCode(inptCostDTO.getNumUnitCode());
//                inptCostDTO.setHerbNoteCode(inptAdviceDTO.getHerbNoteCode());
//                inptCostDTO.setUseCode(inptAdviceDTO.getUseCode());
//                inptCostDTO.setHerbNum(inptAdviceDTO.getHerbNum());
                //总金额 总数量*单价
                inptCostDTO.setTotalPrice(BigDecimalUtils.multiply(inptCostDTO.getTotalNum(),inptCostDTO.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //优惠金额默认给0,后续统一做优惠处理,再更新
                inptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
                //优惠后总金额 = 总金额-优惠金额
                inptCostDTO.setRealityPrice(inptCostDTO.getTotalPrice());
                //开嘱医生信息
                inptCostDTO.setDoctorId(adviceDTO.getCrteId());
                inptCostDTO.setDoctorName(adviceDTO.getCrteName());
                inptCostDTO.setDeptId(adviceDTO.getDeptId());
                //发药药房ID
//                inptCostDTO.setPharId(inptAdviceDTO.getPharId());
                //是否已发药
                inptCostDTO.setIsDist("0");
                //是否已退药
                inptCostDTO.setBackCode("0");
                inptCostDTO.setIsGive("0");
                inptCostDTO.setIsOk(Constants.SF.S);
                inptCostDTO.setSettleCode("0");
                inptCostDTO.setIsCheck("0");
                //循环找到就诊信息，填充主治、经治、主管医生信息
                inptCostDTO.setZzDoctorId(visitDTO.getZzDoctorId());
                inptCostDTO.setZzDoctorName(visitDTO.getZzDoctorName());
                inptCostDTO.setJzDoctorId(visitDTO.getJzDoctorId());
                inptCostDTO.setJzDoctorName(visitDTO.getJzDoctorName());
                inptCostDTO.setZgDoctorId(visitDTO.getZgDoctorId());
                inptCostDTO.setZgDoctorName(visitDTO.getZgDoctorName());
                //执行人信息
                inptCostDTO.setExecId(StringUtils.isEmpty(adviceDTO.getExecId())?adviceDTO.getSecondExecId():adviceDTO.getExecId());
                inptCostDTO.setExecName(StringUtils.isEmpty(adviceDTO.getExecName())?adviceDTO.getSecondExecName():adviceDTO.getExecName());
                inptCostDTO.setExecTime(date);
                inptCostDTO.setExecDeptId(adviceDTO.getExecDeptId());
                //计划执行时间
                inptCostDTO.setPlanExecTime(date);
                //计费时间
                inptCostDTO.setCostTime(date);
                inptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
                //创建人信息
                inptCostDTO.setCrteId(medicalAdviceDTO.getCheckId());
                inptCostDTO.setCrteName(medicalAdviceDTO.getCheckName());
                inptCostDTO.setCrteTime(date);
                inptCostDTO.setAttributionCode("0");

                //计算优惠金额
                calculatPreferential(inptCostDTO, visitDTO.getPreferentialTypeId());

                inptCostDTOList.add(inptCostDTO);
            }
        }
    }

    /**
     * @Method: getDailyFirstCalc
     * @Description: 获取每日首次计费信息
     * @Param: [hospCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/12 10:31
     * @Return: java.util.List<cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO>
     **/
    private List<BaseDailyfirstCalcDTO> getDailyFirstCalc(String hospCode) {
        //获取每日首次计费信息
        Map dailyMap = new HashMap();
        BaseDailyfirstCalcDTO dailyfirstCalcDTO = new BaseDailyfirstCalcDTO();
        dailyfirstCalcDTO.setHospCode(hospCode);
        dailyMap.put("hospCode", hospCode);
        dailyMap.put("dailyfirstCalcDTO", dailyfirstCalcDTO);
        return baseDailyfirstCalcService_consumer.queryDaily(dailyMap).getData();
    }

    /**
     * @Method: buildStaticCost
     * @Description: 静态计费
     * 1.判断是否有辅助计费
     * 2.获取辅助计费的主键
     * 3.入库医嘱明细表、
     * 4.住院费用表、更新就诊表、领药表（后续生成费用信息统一生成）
     * @Param: [medicalAdviceDTO, inptAdviceDTOList, inptVisitDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 14:16
     * @Return: void
     **/
    private void buildStaticCost(String hospCode, List<String> adviceIds) {
        //根据医嘱ID获取医嘱记录
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getInptAdviceByIds(hospCode, adviceIds);
        List<String > adviceKey = new ArrayList<String>();
        for (InptAdviceDTO inptAdviceDTO :inptAdviceDTOList) {
            //长期医嘱未停止或者临时医嘱（20211105 修改）
            boolean flag = (("0".equals(inptAdviceDTO.getIsLong()) && !"1".equals(inptAdviceDTO.getIsStop())) || "1".equals(inptAdviceDTO.getIsLong()) );

            //相同病人的医嘱组号(同一组号辅助计费共用,不重复生成)
            if (flag) {
                if (!adviceKey.contains(inptAdviceDTO.getVisitId()+"_"+inptAdviceDTO.getGroupNo()) || inptAdviceDTO.getGroupNo()==0) {
                    //根据用法拿到辅助计费对象
                    String[] result = getBaseAssistCalcDTO(inptAdviceDTO,inptAdviceDTO.getUsageCode(),"0","");
                    if (result!=null && result.length>0 && Integer.valueOf(result[1])>0  && !StringUtils.isEmpty(result[0])) {
                        //中草药医嘱
                        if ("11".equals(inptAdviceDTO.getTypeCode())) {
                            staticBuildCost(inptAdviceDTO, result[0],"XE");
                        } else {
                            staticBuildCost(inptAdviceDTO, result[0],"AN");
                        }
                    }

                    if(inptAdviceDTO.getGroupNo() != 0){
                        adviceKey.add(inptAdviceDTO.getVisitId()+"_"+inptAdviceDTO.getGroupNo());
                    }
                }
            }
        }
    }

    /**
     * @Method: staticBuildCost
     * @Description: 静态计费
     * 1.获取辅助计费明细记录
     * 2.入库医嘱明细表
     * 3.入库住院费用表
     * 4.更新住院就诊表累计金额，余额
     * @Param: [medicalAdviceDTO, inptVisitDTOList, inptAdviceDTO, assistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 17:41
     * @Return: void
     **/
    private void staticBuildCost(InptAdviceDTO inptAdviceDTO, String acCode,String usageCode) {
        if (StringUtils.isEmpty(acCode)) {
            return;
        }

        //医嘱明细记录
        List<InptAdviceDetailDTO> adviceDetailDTOList = new ArrayList<>();

        //判断用法(中草药:医嘱数量,其他:1)
        BigDecimal num = new BigDecimal(0) ;
        if(usageCode.equals("XE")){
            num =inptAdviceDTO.getNum();
        } else if (usageCode.equals("AN")){
            num = new BigDecimal(1) ;
        }

        //组装获取计费明细参数
        List<BaseAssistCalcDetailDTO> assistCalcDetailDTOList = inptAdviceDAO.getAssistDetail(inptAdviceDTO.getHospCode(), acCode);

        //循环明细,组装医嘱明细数据,入库
        for (BaseAssistCalcDetailDTO detailDTO:assistCalcDetailDTOList) {
            if (detailDTO.getItemId() == null ){
                throw new AppException(detailDTO.getName() +"配置项目错误!");
            }
            //医嘱明细对象
            InptAdviceDetailDTO inptAdviceDetailDTO = new InptAdviceDetailDTO();
            if (Constants.XMLB.CL.equals(detailDTO.getItemCode())) {//材料
                //根据编码获取材料信息
                BaseMaterialDTO materiaDTO = getBaseMaterialDTOByID(inptAdviceDTO.getHospCode(),detailDTO.getItemId());
                if (materiaDTO == null ){
                    throw new AppException("生成静态计费未获取到材料信息!");
                }
                //名称
                inptAdviceDetailDTO.setItemName(materiaDTO.getName());

                //这里的医嘱明细单位必须从辅助计费中的明细里面取 单位值  -  2022-05-11 彭波
                if(StringUtils.isNotEmpty(detailDTO.getItemUnitCode()) && detailDTO.getItemUnitCode().equals(materiaDTO.getSplitUnitCode())){
                    inptAdviceDetailDTO.setPrice(materiaDTO.getSplitPrice());
                    inptAdviceDetailDTO.setUnitCode(materiaDTO.getSplitUnitCode());
                }else{
                    inptAdviceDetailDTO.setPrice(materiaDTO.getPrice());
                    inptAdviceDetailDTO.setUnitCode(materiaDTO.getUnitCode());
                }
                //单价
                //inptAdviceDetailDTO.setPrice(materiaDTO.getSplitPrice());
                //单位
                //inptAdviceDetailDTO.setUnitCode(materiaDTO.getSplitUnitCode());
                //项目ID
                inptAdviceDetailDTO.setItemId(materiaDTO.getId());
                //名称
                inptAdviceDetailDTO.setItemName(materiaDTO.getName());
                //用药性质
                inptAdviceDetailDTO.setUseCode(Constants.YYXZ.KSZB);

                if(StringUtils.isNotEmpty(materiaDTO.getUseCode())){
                    inptAdviceDetailDTO.setUseCode(materiaDTO.getUseCode());
                }

                //财务分类
                inptAdviceDetailDTO.setBfcId(detailDTO.getBfcId());
            } else if (Constants.XMLB.XM.equals(detailDTO.getItemCode())) {//项目
                //根据编码获取项目信息
                BaseItemDTO itemDTO = getBaseItemDTOByID(inptAdviceDTO.getHospCode(),detailDTO.getItemId());
                if (itemDTO == null ){
                    throw new AppException("生成静态计费未获取到项目信息!");
                }
                //单价
                inptAdviceDetailDTO.setPrice(itemDTO.getPrice());
                //名称
                inptAdviceDetailDTO.setItemName(itemDTO.getName());
                //单位
                inptAdviceDetailDTO.setUnitCode(itemDTO.getUnitCode());
                //项目ID
                inptAdviceDetailDTO.setItemId(itemDTO.getId());
                //用药性质(科室自备)
                inptAdviceDetailDTO.setUseCode(Constants.YYXZ.KSZB);
                //财务分类
                inptAdviceDetailDTO.setBfcId(detailDTO.getBfcId());
            }else {
                throw new AppException("生成静态计费未配置项目或材料!");
            }
            //医嘱明细
            inptAdviceDetailDTO.setId(SnowflakeUtils.getId());
            inptAdviceDetailDTO.setHospCode(detailDTO.getHospCode());
            inptAdviceDetailDTO.setVisitId(inptAdviceDTO.getVisitId());
            inptAdviceDetailDTO.setIaId(inptAdviceDTO.getId());
            //医嘱目录ID
//            inptAdviceDetailDTO.setBaId(adviceDTO.getId());
            inptAdviceDetailDTO.setIaGroupNo(inptAdviceDTO.getGroupNo());
            inptAdviceDetailDTO.setItemCode(detailDTO.getItemCode());
            //数量  辅助计费数量*数量
            inptAdviceDetailDTO.setNum(BigDecimalUtils.multiply(detailDTO.getNum(),num));
            //总金额 = 数量*单价
            inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(inptAdviceDetailDTO.getNum(),inptAdviceDetailDTO.getPrice()));
            inptAdviceDetailDTO.setSourceCode(Constants.FYLYFS.DJTJF);
            adviceDetailDTOList.add(inptAdviceDetailDTO);
        }
        //医嘱明细入库
        if (!ListUtils.isEmpty(adviceDetailDTOList)) {
            inptAdviceDetailDAO.insertInptAdviceDetails(adviceDetailDTOList);
        }
    }

    /**
     * @Method: getBaseItemDTOByCode
     * @Description: 根据编码获取项目信息
     * @Param: [hospCode, code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 16:36
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    private BaseItemDTO getBaseItemDTOByCode(String hospCode,String code) {
        //组装获取项目信息参数
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setCode(code);
        map.put("baseItemDTO", baseItemDTO);
        //获取项目信息
        WrapperResponse<BaseItemDTO> response = baseItemService_consumer.getByCode(map);
        BaseItemDTO itemDTO = response.getData();
        return itemDTO;
    }

    /**
     * @Method: getBaseDrugDTOByCode
     * @Description: 根据编码获取药品信息
     * @Param: [hospCode, code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 16:04
     * @Return: cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    private BaseDrugDTO getBaseDrugDTOByCode(String hospCode,String code) {
        //组装获取药品信息参数
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setCode(code);
        map.put("baseDrugDTO", baseDrugDTO);
        //获取药品信息
        WrapperResponse<BaseDrugDTO> wrapperResponse = baseDrugService_consumer.getByCode(map);
        BaseDrugDTO drugDTO = wrapperResponse.getData();
        if (drugDTO == null) {
            throw new AppException("药品信息为空");
        }
        return drugDTO;
    }

    /**
     * @Method: getBaseAssistCalcDetailDTOS
     * @Description: 根据辅助计费编码获取明细数据
     * @Param: [hospCode, acCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 15:40
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO>
     **/
    private List<BaseAssistCalcDetailDTO> getBaseAssistCalcDetailDTOS(String hospCode, String acCode) {
        Map detailMap = new HashMap();
        BaseAssistCalcDTO calcDTO = new BaseAssistCalcDTO();
        calcDTO.setHospCode(hospCode);
        calcDTO.setCode(acCode);
        detailMap.put("hospCode", hospCode);
        detailMap.put("baseAssistCalcDTO", calcDTO);
        //获取辅助计费明细
        WrapperResponse<List<BaseAssistCalcDetailDTO>> response = baseAssistCalcService_consumer.queryAssistDetails(detailMap);
        List<BaseAssistCalcDetailDTO> assistCalcDetailDTOList = response.getData();
        if (ListUtils.isEmpty(assistCalcDetailDTOList)) {
            throw new AppException("辅助计费明细为空");
        }
        return assistCalcDetailDTOList;
    }

    /**
     *
     * 根据用法找到辅助计费
     * 优先级
     * 1.指定科室、指定项目
     * 2.不指定科室、指定项目
     * 3.指定科室、不指定项目
     * 4.不指定科室、不指定项目
     * 5.返回最新的一条记录
     * @Method: getBaseAssistCalcDTO
     * @Param: [inptAdviceDTO, usageCode, wayCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/11 20:16
     * @Return: java.lang.String[]
     **/
    private String[] getBaseAssistCalcDTO(InptAdviceDTO inptAdviceDTO,String usageCode,String wayCode,String isFirst) {
        String code = "" ;
        String yxjb = "0" ;
        String name = "";
        if (StringUtils.isEmpty(usageCode)) {
            return null;
        }
        //根据参数获取辅助计费列表
        BaseAssistCalcDTO baseAssistCalcDTO = new BaseAssistCalcDTO();
        baseAssistCalcDTO.setUsageCode(usageCode);
        baseAssistCalcDTO.setHospCode(inptAdviceDTO.getHospCode());
        baseAssistCalcDTO.setWayCode(wayCode);
        baseAssistCalcDTO.setIsFirst(isFirst);
        Map map = new HashMap();
        map.put("hospCode", inptAdviceDTO.getHospCode());
        map.put("baseAssistCalcDTO", baseAssistCalcDTO);
        List<BaseAssistCalcDTO> baseAssistCalcDTOList = baseAssistCalcService_consumer.queryAssists(map).getData();

        //判断优先级级别
        if (!ListUtils.isEmpty(baseAssistCalcDTOList)) {
            for (BaseAssistCalcDTO dto:baseAssistCalcDTOList) {
                //是否指定科室
                if(StringUtils.isEmpty(dto.getDeptCode())) {
                    dto.setZdksbz("1");
                    dto.setDeptId("");
                } else {
                    dto.setZdksbz("0");
                }
                //是否指定项目
                if(StringUtils.isEmpty(dto.getBizCode())) {
                    dto.setZdxmbz("1");
                    dto.setZdxmid("");
                } else {
                    dto.setZdxmbz("0");
                }

                String yxjbInt = "0";
                // 指定科室、指定项目
                if ("0".equals(dto.getZdksbz()) && "0".equals(dto.getZdxmbz())
                        && dto.getZdxmid().equals(inptAdviceDTO.getItemId()) && dto.getDeptId().equals(inptAdviceDTO.getDeptId())) {
                    yxjbInt = "4";
                }
                // 不指定科室、指定项目
                else if ("1".equals(dto.getZdksbz()) && "0".equals(dto.getZdxmbz()) && dto.getZdxmid().equals(inptAdviceDTO.getItemId())) {
                    yxjbInt = "3";
                }
                // 指定科室、不指定项目
                else if ("0".equals(dto.getZdksbz()) && "1".equals(dto.getZdxmbz()) && dto.getDeptId().equals(inptAdviceDTO.getDeptId())) {
                    yxjbInt = "2";
                }
                // 不指定科室、不指定项目
                else if ("1".equals(dto.getZdksbz()) && "1".equals(dto.getZdxmbz())) {
                    yxjbInt = "1";
                }
                //时间降序排列，如果找到了该记录，就终止循环
                if (Integer.parseInt(yxjbInt) > Integer.parseInt(yxjb)) {
                    yxjb = yxjbInt;
                    code = dto.getCode();
                    name = dto.getName();
                    break;
                }
            }
        }
        return new String[]{code,yxjb,name};
    }

    /**
     * @Method: getInptCostDTO
     * @Description: 组装住院费用对象
     * 费用优惠(按比例，按金额，不优惠)
     * @Param: [medicalAdviceDTO, inptVisitDTOList, inptCostDTOs, adviceExecDTO, adviceDTO, baseRateDTO, inptAdviceDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 11:30
     * @Return: cn.hsa.module.inpt.doctor.dto.InptCostDTO
     **/
    private InptCostDTO buildInptCostDTO(MedicalAdviceDTO medicalAdviceDTO, InptAdviceDTO adviceDTO, InptAdviceDetailDTO inptAdviceDetailDTO,
                                         int dailyTimes, Date date, InptVisitDTO visitDTO,Map<String, BaseDrugDTO> drugMap,Map<String, BaseMaterialDTO> materiaMap) {


        //药品、材料如果是个人自备不收费
        if ((Constants.XMLB.YP.equals(adviceDTO.getItemCode()) || Constants.XMLB.CL.equals(adviceDTO.getItemCode()))
                && Constants.YYXZ.GRZB.equals(adviceDTO.getUseCode()) && !Constants.FYLYFS.DJTJF.equals(inptAdviceDetailDTO.getSourceCode()) ) {
            return null;
        }
        InptCostDTO inptCostDTO = new InptCostDTO();
        inptCostDTO.setAdvanceId(medicalAdviceDTO.getSfTqly());
        //用量单位和数量不变
        inptCostDTO.setNum(inptAdviceDetailDTO.getNum());
        inptCostDTO.setNumUnitCode(inptAdviceDetailDTO.getUnitCode());
        //总数量=用量*执行次数 并且向上取整
        inptCostDTO.setTotalNum(inptAdviceDetailDTO.getNum());
        inptCostDTO.setTotalNumUnitCode(inptAdviceDetailDTO.getUnitCode());
        inptCostDTO.setPrice(inptAdviceDetailDTO.getPrice());
        //药品
        BaseDrugDTO drugDTO = null;
        //材料
        BaseMaterialDTO materialDTO = null;
        //获取药品/项目信息,如果拆分比为空,默认给1
        if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode())) {
            drugDTO = drugMap.get(inptAdviceDetailDTO.getItemId());
            if(drugDTO == null){
                throw new RuntimeException("医嘱【" + adviceDTO.getItemName() + "】未获取到有效药品,检查药品信息!");
            }
            // 没有配置默认 1：单次向上取整
            if(StringUtils.isEmpty(drugDTO.getTruncCode()) || "1".equals(drugDTO.getTruncCode())){
                inptAdviceDetailDTO.setNum(BigDecimal.valueOf(Math.ceil(inptAdviceDetailDTO.getNum().doubleValue())));
            }
            //---------2021-08-13
            //中草药药品做费用，不跟频率挂钩
            if("11".equals(adviceDTO.getTypeCode())) {
                dailyTimes = 1 ;
            }
        } else if (Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())) {
            materialDTO = materiaMap.get(inptAdviceDetailDTO.getItemId());
        }

        //数量=医嘱明细表数量*执行次数
        BigDecimal numBig = BigDecimalUtils.multiply(BigDecimal.valueOf(dailyTimes), inptAdviceDetailDTO.getNum());
        //判断药品 跟  材料
        if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode()) || Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())) {
            //如果医嘱明细用量单位不等于医嘱总数量单位,那么费用表总数量单位使用医嘱表总数量单位,并且计算出数量(向上取整)
            if (!inptAdviceDetailDTO.getUnitCode().equals(adviceDTO.getTotalNumUnitCode())) {
                //单位和数量换算
                if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode())) {//药品
                    inptCostDTO.setTotalNumUnitCode(adviceDTO.getTotalNumUnitCode());
                    // 如果费用表的总数量单位等于药品表里面的包装单位  那么数量 = 医嘱剂量/拆分比
                    if (inptCostDTO.getTotalNumUnitCode().equals(drugDTO.getUnitCode())) {
                        numBig = BigDecimal.valueOf(Math.ceil(BigDecimalUtils.divide(numBig, drugDTO.getSplitRatio()).doubleValue()));
                        inptCostDTO.setPrice(drugDTO.getPrice());
                    }
                    // 如果费用表的总数量单位等于药品表里面的拆零单位  那么数量 = 医嘱剂量*拆分比
                    else if (inptCostDTO.getTotalNumUnitCode().equals(drugDTO.getSplitUnitCode())){
                        numBig = BigDecimal.valueOf(Math.ceil(BigDecimalUtils.multiply(numBig, drugDTO.getSplitRatio()).doubleValue()));
                        inptCostDTO.setPrice(drugDTO.getSplitPrice());
                    }
                } else if (Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode()) && !Constants.FYLYFS.DJTJF.equals(inptAdviceDetailDTO.getSourceCode())) {//材料
                    if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getUnitCode())) {
                        numBig = BigDecimal.valueOf(Math.ceil(BigDecimalUtils.divide(numBig, materialDTO.getSplitRatio()).doubleValue()));
                        inptCostDTO.setPrice(materialDTO.getPrice());
                    } else if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getSplitUnitCode())){
                        numBig = BigDecimal.valueOf(Math.ceil(BigDecimalUtils.multiply(numBig, materialDTO.getSplitRatio()).doubleValue()));
                        inptCostDTO.setPrice(materialDTO.getSplitPrice());
                    }
                }
            } else {
                //如果开单的但是是小单位那么向上取整
                if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode())) {
                    if (drugDTO.getSplitUnitCode().equals(inptAdviceDetailDTO.getUnitCode())) {
                        numBig = BigDecimal.valueOf(Math.ceil(numBig.doubleValue()));
                    }
                } else if (Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())) {//材料
                    if (materialDTO.getSplitUnitCode().equals(inptAdviceDetailDTO.getUnitCode())) {
                        numBig = BigDecimal.valueOf(Math.ceil(numBig.doubleValue()));
                    }
                }
            }
        }

        //如果拆分比为空或者拆分比为1默认向上取整
        if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode())) {
            if (drugDTO.getSplitRatio()==null || drugDTO.getSplitRatio().compareTo(BigDecimal.valueOf(1))==0) {
                numBig = BigDecimal.valueOf(Math.ceil(BigDecimalUtils.divide(numBig, drugDTO.getSplitRatio()).doubleValue()));
            }
            inptCostDTO.setUsageCode(adviceDTO.getUsageCode());
            inptCostDTO.setSpec(drugDTO.getSpec());
            //剂量=剂量(医嘱表)*当天执行次数
            //inptCostDTO.setDosage(adviceDTO.getDosage()!=null?BigDecimalUtils.multiply(adviceDTO.getDosage(),BigDecimal.valueOf(dailyTimes)):null);
            inptCostDTO.setDosage(adviceDTO.getDosage());
            inptCostDTO.setDosageUnitCode(adviceDTO.getDosageUnitCode());
        } else if (Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())) {//材料
            if (materialDTO.getSplitRatio()==null || materialDTO.getSplitRatio().compareTo(BigDecimal.valueOf(1))==0) {
                numBig = BigDecimal.valueOf(Math.ceil(BigDecimalUtils.divide(numBig, materialDTO.getSplitRatio()).doubleValue()));
            }
            inptCostDTO.setSpec(materialDTO.getSpec());
            //inptCostDTO.setDosage(BigDecimalUtils.multiply(new BigDecimal(1),BigDecimal.valueOf(dailyTimes)));
            inptCostDTO.setDosage(new BigDecimal(1));
            inptCostDTO.setDosageUnitCode(materialDTO.getUnitCode());
        }

        //发药药房ID
        inptCostDTO.setPharId(adviceDTO.getPharId());

        //如果主表为药品，明细为材料库位ID 需要根据明细去判断 20210531 pengbo
        if(Constants.XMLB.YP.equals(adviceDTO.getItemCode()) && Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())){
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("hospCode",adviceDTO.getHospCode());
            paramMap.put("deptId",adviceDTO.getExecDeptId());
            List<Map<String, Object>>  stroStockList =  inptAdviceDAO.getStroStockById(paramMap);
            if (ListUtils.isEmpty(stroStockList)){
                throw new RuntimeException("本科室未配置卫材类药库!");
            }
            inptCostDTO.setPharId((String) stroStockList.get(0).get("id"));
        }

        //皮试换药单位处理
        if (Constants.FYLYFS.PSHYYP.equals(inptAdviceDetailDTO.getSourceCode())) {
            inptCostDTO.setNumUnitCode(inptAdviceDetailDTO.getUnitCode());
            inptCostDTO.setTotalNumUnitCode(inptAdviceDetailDTO.getUnitCode());
            inptCostDTO.setPrice(inptAdviceDetailDTO.getPrice());
            numBig = inptAdviceDetailDTO.getNum();

            //获取皮试来源医嘱信息
            InptAdviceDTO dto = new InptAdviceDTO();
            dto.setHospCode(inptAdviceDetailDTO.getHospCode());
            dto.setId(adviceDTO.getSourceIaId());
            InptAdviceDTO sourceAdvice = inptAdviceDAO.getInptAdviceById(dto);
            if (sourceAdvice!=null) {
                inptCostDTO.setPharId(sourceAdvice.getPharId());
            }
        }

        //组装住院费用参数
        inptCostDTO.setId(SnowflakeUtils.getId());
        inptCostDTO.setHospCode(inptAdviceDetailDTO.getHospCode());
        inptCostDTO.setVisitId(inptAdviceDetailDTO.getVisitId());
        inptCostDTO.setBabyId(adviceDTO.getBabyId());
        inptCostDTO.setIatId(adviceDTO.getId());
        inptCostDTO.setIatdGroupNo(adviceDTO.getGroupNo());
        inptCostDTO.setIatdSeqNo(adviceDTO.getGroupSeqNo());
        inptCostDTO.setSourceCode(inptAdviceDetailDTO.getSourceCode());
        //费用来源ID->医嘱明细ID
        inptCostDTO.setSourceId(inptAdviceDetailDTO.getId());
        inptCostDTO.setSourceDeptId(adviceDTO.getDeptId());
        inptCostDTO.setInDeptId(adviceDTO.getInDeptId());
        //财务分类
        inptCostDTO.setBfcId(inptAdviceDetailDTO.getBfcId());
        inptCostDTO.setItemCode(inptAdviceDetailDTO.getItemCode());
        inptCostDTO.setItemId(inptAdviceDetailDTO.getItemId());
        inptCostDTO.setItemName(inptAdviceDetailDTO.getItemName());
        inptCostDTO.setPrepCode(adviceDTO.getPrepCode());
        inptCostDTO.setRateId(adviceDTO.getRateId());
        inptCostDTO.setSpeedCode(adviceDTO.getSpeedCode());
        //用药天数
        inptCostDTO.setUseDays(adviceDTO.getUseDays()==null?1:adviceDTO.getUseDays());
        //总数量=数量(医嘱表数量)*当天执行次数*用药天数(医嘱表) 或者 总数量=数量(费用表)*用药天数(医嘱表)
        inptCostDTO.setTotalNum(BigDecimalUtils.multiply(numBig,BigDecimal.valueOf(adviceDTO.getUseDays())));
        inptCostDTO.setHerbNoteCode(adviceDTO.getHerbNoteCode());
        //用法必须取医嘱明细中的用法（考虑到静态计费中维护材料，可能存在科室自备的情况-20210531-pengbo）
        inptCostDTO.setUseCode(inptAdviceDetailDTO.getUseCode());
        inptCostDTO.setHerbNum(adviceDTO.getHerbNum());
        //总费用=数量*单价 保留两位小数 四舍五入
        inptCostDTO.setTotalPrice((BigDecimalUtils.multiply(inptCostDTO.getTotalNum(), inptCostDTO.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
        //优惠金额默认给0,后续统一做优惠处理,再更新
        inptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
        //优惠后总金额 = 总金额-优惠金额
        inptCostDTO.setRealityPrice(inptCostDTO.getTotalPrice());
        //开嘱医生信息
        inptCostDTO.setDoctorId(adviceDTO.getCrteId());
        inptCostDTO.setDoctorName(adviceDTO.getCrteName());
        inptCostDTO.setDeptId(adviceDTO.getDeptId());
        //是否已发药
        inptCostDTO.setIsDist("0");
        //是否已退药
        inptCostDTO.setBackCode("0");
        inptCostDTO.setIsWait(Constants.SF.F);
        inptCostDTO.setIsGive(adviceDTO.getIsGive());

        //决定lis,pass项目是否确费：未配置时或否定时，所有核收费用都是确费状态，配置了lis pass项目为未确费状态
        SysParameterDTO sysParameterDTO =  getSysParameterDTO(inptCostDTO.getHospCode(),"LIS_PACS_SFQF");
        //康复理疗医嘱是否需要确费 2022/4/25 by yuelong.chen
        SysParameterDTO sysParameterDTO2 =  getSysParameterDTO(inptCostDTO.getHospCode(),"RECOVERY_SFQF");
        if((sysParameterDTO != null && "0".equals(sysParameterDTO.getValue())) && ("3".equals(adviceDTO.getTypeCode())||"12".equals(adviceDTO.getTypeCode()))){
            // 2021年5月14日14:37:54 官红强  如果执行科室的科室性质为检验科室或影像科室，则为未确费，否则确费
            if (adviceDTO.getExecDeptKsxz() != null && ("7".equals(adviceDTO.getExecDeptKsxz()) || "8".equals(adviceDTO.getExecDeptKsxz()))) {
                inptCostDTO.setIsOk(Constants.SF.F);
            } else {
                inptCostDTO.setIsOk(Constants.SF.S);
            }
        }else if((sysParameterDTO2 != null && "0".equals(sysParameterDTO2.getValue())) && ("19".equals(adviceDTO.getTypeCode()))){
            inptCostDTO.setIsOk(Constants.SF.F);
        }else{
            inptCostDTO.setIsOk(Constants.SF.S);
            inptCostDTO.setOkId(medicalAdviceDTO.getCheckId());
            inptCostDTO.setOkName(medicalAdviceDTO.getCheckName());
            inptCostDTO.setOkTime(date);
        }


        inptCostDTO.setSettleCode("0");
        inptCostDTO.setIsCheck("0");
        String preferentialTypeId = "";
        //循环找到就诊信息，填充主治、经治、主管医生信息
        inptCostDTO.setZzDoctorId(visitDTO.getZzDoctorId());
        inptCostDTO.setZzDoctorName(visitDTO.getZzDoctorName());
        inptCostDTO.setJzDoctorId(visitDTO.getJzDoctorId());
        inptCostDTO.setJzDoctorName(visitDTO.getJzDoctorName());
        inptCostDTO.setZgDoctorId(visitDTO.getZgDoctorId());
        inptCostDTO.setZgDoctorName(visitDTO.getZgDoctorName());
        preferentialTypeId = visitDTO.getPreferentialTypeId();
        //执行人信息
        inptCostDTO.setExecId(StringUtils.isEmpty(adviceDTO.getExecId())?adviceDTO.getSecondExecId():adviceDTO.getExecId());
        inptCostDTO.setExecName(StringUtils.isEmpty(adviceDTO.getExecName())?adviceDTO.getSecondExecName():adviceDTO.getExecName());
        inptCostDTO.setExecTime(date);
        inptCostDTO.setExecDeptId(adviceDTO.getExecDeptId());
        //计划执行时间
        inptCostDTO.setPlanExecTime(date);
        //计费时间
        inptCostDTO.setCostTime(date);
        inptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
        //创建人信息
        inptCostDTO.setCrteId(medicalAdviceDTO.getCheckId());
        inptCostDTO.setCrteName(medicalAdviceDTO.getCheckName());
        inptCostDTO.setCrteTime(date);
        inptCostDTO.setAttributionCode("0");

        //交病人类型判断 如果交病人且为临时医嘱时   (重新赋值  总数量，总数量单位，单价，总价)
        //if("1".equals(adviceDTO.getIsLong()) && "2".equals(adviceDTO.getYylx())){
        if("1".equals(adviceDTO.getIsLong())){
            inptCostDTO.setTotalNum(adviceDTO.getTotalNum());
            // 动静态计费的总数量=每日数量*医嘱天数 add luoyong 2021.11.29
            if (Constants.FYLYFS.DJTJF.equals(inptAdviceDetailDTO.getSourceCode())) {
                inptCostDTO.setTotalNum(BigDecimalUtils.multiply(numBig,BigDecimal.valueOf(adviceDTO.getUseDays())));
            }
            if(Constants.XMLB.XM.equals(inptAdviceDetailDTO.getItemCode())){
              inptCostDTO.setNumUnitCode(inptAdviceDetailDTO.getUnitCode());
              inptCostDTO.setTotalNumUnitCode(inptAdviceDetailDTO.getUnitCode());
            }
            //药品
            if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode())) {
                if (inptCostDTO.getTotalNumUnitCode().equals(drugDTO.getUnitCode())) {
                    inptCostDTO.setPrice(drugDTO.getPrice());
                } else if (inptCostDTO.getTotalNumUnitCode().equals(drugDTO.getSplitUnitCode())){
                    inptCostDTO.setPrice(drugDTO.getSplitPrice());
                }
            }
            //材料
            else if (Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())) {//材料
                if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getUnitCode())) {
                    inptCostDTO.setPrice(materialDTO.getPrice());
                } else if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getSplitUnitCode())){
                    inptCostDTO.setPrice(materialDTO.getSplitPrice());
                }
            }
            //特殊处理 2021-09-28 考虑到临时医嘱下的医嘱目录用量大于1时的处理
            /**
             * 1。需要算出当前医嘱目录下的材料数量,再用算出的数量乘以 医嘱主表的总数量 就得到了实际的收费数量
             * 注意：（也可以直接根据医嘱目录查询医嘱目录明细的中的base_advice_detail.num）
             */
            else if(Constants.XMLB.YZML.equals(adviceDTO.getItemCode())){
                //算出医嘱目录下绑定的材料数量
                BigDecimal numNew = BigDecimalUtils.divide(inptAdviceDetailDTO.getNum(),adviceDTO.getNum());
                //算出实际收费项目的总数量
                inptCostDTO.setTotalNum(BigDecimalUtils.multiply(numNew, adviceDTO.getTotalNum()));

            }

            inptCostDTO.setTotalPrice((BigDecimalUtils.multiply(inptCostDTO.getTotalNum(), inptCostDTO.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        //计算优惠金额
        calculatPreferential(inptCostDTO, preferentialTypeId);

        if (inptCostDTO == null) {
            throw new AppException("组装住院费用信息失败");
        }
        return inptCostDTO;
    }

    /**
     * @Method: calculatPreferential
     * @Description: 计算优惠金额
     * @Param: [inptCostDTO, preferentialTypeId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/10 10:56
     * @Return: void
     **/
    private void calculatPreferential(InptCostDTO inptCostDTO, String preferentialTypeId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> costList = new ArrayList<>();
        Map<String, Object> costMap = new HashMap<>();
        costMap.put("hospCode", inptCostDTO.getHospCode());
        costMap.put("preferentialTypeId", preferentialTypeId);
        costMap.put("itemId", inptCostDTO.getItemId());
        costMap.put("itemCode", inptCostDTO.getItemCode());
        costMap.put("bfcId", inptCostDTO.getBfcId());
        costMap.put("type", "1");
        costMap.put("totalPrice", inptCostDTO.getTotalPrice());
        costList.add(costMap);
        map.put("costList", costList);
        map.put("hospCode", inptCostDTO.getHospCode());
        List<Map<String, Object>> list = basePreferentialService_consumer.calculatPreferential(map).getData();
        if (!ListUtils.isEmpty(list)) {
            Map<String, Object> returnMap = list.get(0);
            inptCostDTO.setPreferentialPrice(MapUtils.get(returnMap,"preferentialPrice"));
            inptCostDTO.setRealityPrice(MapUtils.get(returnMap,"realityPrice"));
        }
    }

    /**
     * @Method: getBaseItemDTOByID
     * @Description: 根据ID获取项目信息
     * @Param: [hospCode, itemId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 11:14
     * @Return: cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    private BaseItemDTO getBaseItemDTOByID(String hospCode,String itemId) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setId(itemId);
        map.put("baseItemDTO", baseItemDTO);
        //获取项目信息
        WrapperResponse<BaseItemDTO> response = baseItemService_consumer.getById(map);
        BaseItemDTO itemDTO = response.getData();
        if (itemDTO == null) {
            throw new AppException("项目信息为空");
        }
        return itemDTO;
    }

    /**
     * @Method: getBaseDrugDTO
     * @Description: 根据ID获取药品信息
     * @Param: [hospCode, itemId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 15:55
     * @Return: cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    private BaseDrugDTO getBaseDrugDTOById(String hospCode,String itemId,String loginDept) {
        //组装获取药品信息参数
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setId(itemId);
        baseDrugDTO.setLoginDeptId(loginDept);
        map.put("baseDrugDTO", baseDrugDTO);
        //获取药品信息
        WrapperResponse<BaseDrugDTO> response = baseDrugService_consumer.getById(map);
        BaseDrugDTO drugDTO = response.getData();
        if (drugDTO == null) {
            throw new AppException("药品信息为空");
        }
        return drugDTO;
    }

    /**
     * @Method: produceWaitReceive
     * @Description: 住院领药申请
     * @Param: [inptCostDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/17 17:27
     * @Return: boolean
     **/
    private void produceWaitReceive(InptCostDTO inptCostDTO,InptAdviceDTO adviceDTO, MedicalAdviceDTO medicalAdviceDTO,Date date,List<MsgTempRecordDO> msgList,
                                    List<PharInWaitReceiveDTO> inWaitReceiveDTOS,Map<String, BaseDrugDTO> drugMap,Map<String, BaseMaterialDTO> materiaMap) {
        //非药品和材料 不需要生成领药申请
        if (!Constants.XMLB.YP.equals(inptCostDTO.getItemCode()) && !Constants.XMLB.CL.equals(inptCostDTO.getItemCode())) {
            return;
        }
        //自备 不需要生产领药申请
        if (Constants.YYXZ.GRZB.equals(inptCostDTO.getUseCode()) || Constants.YYXZ.KSZB.equals(inptCostDTO.getUseCode())) {
            return;
        }

        //组装领药申请参数
        PharInWaitReceiveDTO waitReceiveDTO = new PharInWaitReceiveDTO();
        //药品
        if (Constants.XMLB.YP.equals(inptCostDTO.getItemCode())) {
            //获取药品信息
            BaseDrugDTO drugDTO = drugMap.get(inptCostDTO.getItemId());

            waitReceiveDTO.setPrice(drugDTO.getPrice());
            waitReceiveDTO.setSplitPrice(drugDTO.getSplitPrice());
            waitReceiveDTO.setSplitRatio(drugDTO.getSplitRatio());
            waitReceiveDTO.setSplitUnitCode(drugDTO.getSplitUnitCode());
            waitReceiveDTO.setUnitCode(drugDTO.getUnitCode());

            //根据单位判断单价是单价还是拆零单价
            if (inptCostDTO.getTotalNumUnitCode().equals(drugDTO.getUnitCode())) {
                waitReceiveDTO.setNum(inptCostDTO.getTotalNum());
                waitReceiveDTO.setSplitNum(BigDecimalUtils.multiply(inptCostDTO.getTotalNum(),drugDTO.getSplitRatio()));
            } else if (inptCostDTO.getTotalNumUnitCode().equals(drugDTO.getSplitUnitCode())) {
                waitReceiveDTO.setSplitNum(inptCostDTO.getTotalNum());
                waitReceiveDTO.setNum((BigDecimalUtils.divide(inptCostDTO.getTotalNum(),drugDTO.getSplitRatio())));
            } else {
                throw new AppException("该药品不存在当前单位");
            }
        } else if (Constants.XMLB.CL.equals(inptCostDTO.getItemCode())) {//材料
            //获取材料信息
            BaseMaterialDTO materialDTO = materiaMap.get(inptCostDTO.getItemId());

            waitReceiveDTO.setPrice(materialDTO.getPrice());
            waitReceiveDTO.setSplitPrice(materialDTO.getSplitPrice());
            waitReceiveDTO.setSplitRatio(materialDTO.getSplitRatio());
            waitReceiveDTO.setSplitUnitCode(materialDTO.getSplitUnitCode());
            waitReceiveDTO.setUnitCode(materialDTO.getUnitCode());
            //根据单位判断单价是单价还是拆零单价
            if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getUnitCode())) {
                waitReceiveDTO.setNum(inptCostDTO.getTotalNum());
                waitReceiveDTO.setSplitNum(BigDecimalUtils.multiply(inptCostDTO.getTotalNum(),materialDTO.getSplitRatio()));
            } else if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getSplitUnitCode())) {
                waitReceiveDTO.setNum((BigDecimalUtils.divide(inptCostDTO.getTotalNum(),materialDTO.getSplitRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)));
                waitReceiveDTO.setSplitNum(inptCostDTO.getTotalNum());
            } else {
                throw new AppException("该材料不存在当前单位");
            }
        }

        //校验库存
        if (Constants.XMLB.YP.equals(inptCostDTO.getItemCode()) || Constants.XMLB.CL.equals(inptCostDTO.getItemCode())) {
            InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
            inptAdviceDTO.setHospCode(inptCostDTO.getHospCode());
            inptAdviceDTO.setItemId(inptCostDTO.getItemId());
            inptAdviceDTO.setPharId(inptCostDTO.getPharId());
            inptAdviceDTO.setTotalNum(waitReceiveDTO.getNum());
            inptAdviceDTO.setUnitCode(waitReceiveDTO.getUnitCode());
            //判断库存
            if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
                //组装消息数据
                buildMsg(medicalAdviceDTO, msgList, inptCostDTO);
            }
        }

        waitReceiveDTO.setId(SnowflakeUtils.getId());
        waitReceiveDTO.setAdvanceId(inptCostDTO.getAdvanceId());
        waitReceiveDTO.setHospCode(inptCostDTO.getHospCode());
        waitReceiveDTO.setAdviceId(adviceDTO.getId());
        waitReceiveDTO.setGroupNo(adviceDTO.getGroupNo()==null?"":adviceDTO.getGroupNo().toString());
        waitReceiveDTO.setVisitId(adviceDTO.getVisitId());
        waitReceiveDTO.setBabyId(adviceDTO.getBabyId());
        waitReceiveDTO.setItemCode(inptCostDTO.getItemCode());
        waitReceiveDTO.setItemId(inptCostDTO.getItemId());
        waitReceiveDTO.setItemName(inptCostDTO.getItemName());
        waitReceiveDTO.setSpec(inptCostDTO.getSpec());
        waitReceiveDTO.setDosage(inptCostDTO.getDosage());
        waitReceiveDTO.setDosageUnitCode(inptCostDTO.getDosageUnitCode());
        waitReceiveDTO.setChineseDrugNum(adviceDTO.getHerbNum());
        waitReceiveDTO.setStatusCode("0");
        waitReceiveDTO.setPharId(inptCostDTO.getPharId());
        waitReceiveDTO.setUsageCode(inptCostDTO.getUsageCode());
        waitReceiveDTO.setUseCode(inptCostDTO.getUseCode());
        //申请科室ID
        waitReceiveDTO.setDeptId(inptCostDTO.getDeptId());
        //是否紧急默认不紧急,需要加急在页面设置
        waitReceiveDTO.setIsEmergency("0");
        waitReceiveDTO.setIsBack("0");
        waitReceiveDTO.setCostId(inptCostDTO.getId());
        waitReceiveDTO.setCrteId(medicalAdviceDTO.getCheckId());
        waitReceiveDTO.setCrteName(medicalAdviceDTO.getCheckName());
        waitReceiveDTO.setCrteTime(date);
        //开单单位
        waitReceiveDTO.setCurrUnitCode(inptCostDTO.getTotalNumUnitCode());
        //保留两位小数 四舍五入
        waitReceiveDTO.setTotalPrice(inptCostDTO.getRealityPrice());
        if (waitReceiveDTO != null) {
            inptCostDTO.setIsWait(Constants.SF.S);
            inWaitReceiveDTOS.add(waitReceiveDTO);
        }
    }

    /**
     * @Method: getBaseMaterialDTOByID
     * @Description: 根据ID获取材料信息
     * @Param: [hospCode, itemId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 11:11
     * @Return: cn.hsa.module.base.bmm.dto.BaseMaterialDTO
     **/
    private BaseMaterialDTO getBaseMaterialDTOByID(String hospCode,String itemId) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setId(itemId);
        map.put("baseMaterialDTO", baseMaterialDTO);
        map.put("type" , "YZLB");
        //获取材料信息
        WrapperResponse<BaseMaterialDTO> response = baseMaterialService_consumer.getById(map);
        BaseMaterialDTO materialDTO = response.getData();
        if (materialDTO == null) {
            throw new AppException("材料信息为空");
        }
        return materialDTO;
    }

    /**
     * @Method: getBaseMaterialDTOByCode
     * @Description: 根据编码获取材料
     * @Param: [hospCode, code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/15 16:11
     * @Return: cn.hsa.module.base.bmm.dto.BaseMaterialDTO
     **/
    private BaseMaterialDTO getBaseMaterialDTOByCode(String hospCode,String code) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setCode(code);
        map.put("baseMaterialDTO", baseMaterialDTO);
        //获取材料信息
        WrapperResponse<BaseMaterialDTO> response = baseMaterialService_consumer.getByCode(map);
        BaseMaterialDTO materialDTO = response.getData();
        return materialDTO;
    }

    /**
     * @Method: buildAdviceExec
     * @Description: 根据医嘱生成执行记录
     * 1.获取所有的医嘱明细记录
     * 2.循环医嘱明细记录
     * 3.计算开始结束时间
     * 4.计算频次
     * 5.组装、入库执行表(数据来源：医嘱明细)
     * 6.组装、入库费用表(数据来源：医嘱明细)
     * 7.组装、入库领药申请
     * 8.更新就诊表费用信息
     * 9.更新医嘱表最近执行时间
     * 皮试：自动生成一条皮试临时医嘱,写入执行表(是否皮试)
     * @Param: [medicalAdviceDTO, inptAdviceDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/16 20:55
     * @Return: void
     **/
    private void buildAdviceCost(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds,List<InptVisitDTO> inptVisitDTOList
            ,String type) {
        //记录需要更新最近执行时间的医嘱及最后生成费用的时间<adviceId,lastCostTime>
        Map<String,Date>  adviceIdCostTime = new HashMap<String,Date>();

        //住院费用信息
        List<InptCostDTO> inptCostDTOs = new ArrayList<InptCostDTO>();

        List<String> inputCostStr = new ArrayList<String>();
        //领药申请集合
        List<PharInWaitReceiveDTO> inWaitReceiveDTOS = new ArrayList<>();
        //消息集合
        List<MsgTempRecordDO> msgList = new ArrayList<>();
        //药品
        Map<String, BaseDrugDTO> drugMap = new HashMap<>();
        //材料
        Map<String, BaseMaterialDTO> materiaMap = new HashMap<>();

        // 获取医保限制用药用药参数 luoyong 2021.07.28/14:21
        SysParameterDTO sysParameterDTO = getSysParameterDTO(medicalAdviceDTO.getHospCode(), "INSURE_DEFAULT_REG_CODE");

        //获取医嘱集合
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);
        if (ListUtils.isEmpty(inptAdviceDTOList)) {
            throw new AppException("获取医嘱记录为空");
        }
        //医嘱信息
        Map<String, InptAdviceDTO> adviceMap = inptAdviceDTOList.stream().collect(Collectors.toMap(InptAdviceDTO::getId, advice->advice));
        if (adviceMap==null || adviceMap.size()<=0) {
            throw new AppException("获取医嘱信息为空");
        }
        Map<String, InptVisitDTO> visitMap = inptVisitDTOList.stream().collect(Collectors.toMap(InptVisitDTO::getId, visit->visit));

        //根据医嘱ID获取医嘱明细记录
        List<InptAdviceDetailDTO> inptAdviceDetailDTOList = inptAdviceDetailDAO.getAdviceDetails(medicalAdviceDTO.getHospCode(),adviceIds);
        if (ListUtils.isEmpty(inptAdviceDetailDTOList)) {
            return;
        }
        //医嘱对象
        InptAdviceDTO inptAdviceDTO = null ;
        //获取参数(住院手术计费方式:ZY_SSJFFS) 住院手术计费方式（0：医生开医嘱计费，1：手术室补记账记费）
        SysParameterDTO jffsSysParameter = getSysParameterDTO(medicalAdviceDTO.getHospCode(), "ZY_SSJFFS");

        for(InptAdviceDetailDTO inptAdviceDetailDTO: inptAdviceDetailDTOList) {
            //获取对应的医嘱信息
            inptAdviceDTO = adviceMap.get(inptAdviceDetailDTO.getIaId());
            if (inptAdviceDTO == null) {
                throw new AppException("获取医嘱信息失败");
            }

            //获取患者信息
            InptVisitDTO visitDTO = visitMap.get(inptAdviceDetailDTO.getVisitId());
            if (visitDTO == null) {
                throw new AppException("获取患者信息失败");
            }

            //校验状态
            if (checkAdvice(inptAdviceDTO)) continue;

            //文字医嘱不生成费用
            if (Constants.YZLB.YZLB10.equals(inptAdviceDTO.getTypeCode())) {
                continue;
            }
            //手术类医嘱动态判断是否医嘱核收生成费用
            if (Constants.YZLB.YZLB5.equals(inptAdviceDTO.getTypeCode()) && (jffsSysParameter == null || "1".equals(jffsSysParameter.getValue()))) {
                continue;
            }

            //计算开始日期、结束日期、判断是否预停
            Map<String,Object> map = getTime(inptAdviceDTO, type);
            //开始日期
            Date startTime = (Date) map.get("startTime");
            //结束日期
            Date endTime = (Date) map.get("endTime");
            if (startTime.compareTo(endTime) > 0) {
                continue;
            }

            //获取频率信息
            BaseRateDTO baseRateDTO = getBaseRateDTO(inptAdviceDTO.getHospCode(), inptAdviceDTO.getRateId());
            //频率执行周期
            int day = baseRateDTO.getExecInterval().intValue();
            //频率执行次数
            int rateTimes = baseRateDTO.getDailyTimes().intValue();


            //获取药品/项目信息,如果拆分比为空,默认给1
            if (Constants.XMLB.YP.equals(inptAdviceDetailDTO.getItemCode())) {
                if (drugMap==null || drugMap.isEmpty()|| drugMap.get(inptAdviceDetailDTO.getItemId())==null) {
                    //科室 取执行参数科室  如果为空 取当前病人的入院科室
                    BaseDrugDTO drugDTO = getBaseDrugDTOById(inptAdviceDetailDTO.getHospCode(),inptAdviceDetailDTO.getItemId(),StringUtils.isEmpty(medicalAdviceDTO.getDeptId())?visitDTO.getInDeptId():medicalAdviceDTO.getDeptId());
                    if (drugDTO.getSplitRatio() == null) {
                        drugDTO.setSplitRatio(BigDecimal.valueOf(1));
                    }
                    drugMap.put(inptAdviceDetailDTO.getItemId(), drugDTO);
                }
            } else if (Constants.XMLB.CL.equals(inptAdviceDetailDTO.getItemCode())) {
                if (materiaMap==null || materiaMap.isEmpty()  || materiaMap.get(inptAdviceDetailDTO.getItemId())==null) {
                    BaseMaterialDTO materialDTO = getBaseMaterialDTOByID(inptAdviceDetailDTO.getHospCode(),inptAdviceDetailDTO.getItemId());
                    if (materialDTO.getSplitRatio() == null) {
                        materialDTO.setSplitRatio(BigDecimal.valueOf(1));
                    }
                    materiaMap.put(inptAdviceDetailDTO.getItemId(), materialDTO);
                }
            }

            while (startTime.compareTo(endTime) <= 0) {
                //计算每日执行次数
                int dailyTimes = getDailyTimes(inptAdviceDTO, startTime, endTime, rateTimes);

                //如果执行执行次数小于等于0，那么就不产生执行记录和费用
                if (dailyTimes <= 0) {
                    //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
                    adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
                    startTime = DateUtils.dateAdd(startTime, day);
                    continue;
                }

                //计费时间=计费当天日期+核收时间(时分秒)
                Date date = DateUtils.parse(DateUtils.format(startTime, DateUtils.Y_M_D) + " "
                        + DateUtils.format(medicalAdviceDTO.getCheckTime(), DateUtils.H_M_S), DateUtils.Y_M_DH_M_S);

                // 隔天核收，计费时间修改
                if(date.before(inptAdviceDTO.getLongStartTime())) {
                    date = DateUtils.parse(DateUtils.format(startTime, DateUtils.Y_M_D) + " " + DateUtils.format(inptAdviceDTO.getLongStartTime(), DateUtils.H_M_S), DateUtils.Y_M_DH_M_S);
                    date = DateUtils.dateAddMinute(date, 10);
                }

                //判断当天费用是否已生成，如果已生产跳过循环
                // （2022-02-17某医院提出当人工手动退费时，这一天的数据不用产生，为了解决此问题，将费用状态，发药状态判断条件放开,只要有这一天费用数据将不会产生）
                List<InptCostDTO> costDTOList = inptCostDAO.queryCostList(inptAdviceDTO.getHospCode(),inptAdviceDTO.getId(),inptAdviceDetailDTO.getId(),startTime);
                if (!ListUtils.isEmpty(costDTOList)) {//已生成费用，跳出循环
                    //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
                    adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
                    startTime = DateUtils.dateAdd(startTime, day);
                    continue;
                }

                //组装住院费用对象
                InptCostDTO inptCostDTO = buildInptCostDTO(medicalAdviceDTO, inptAdviceDTO, inptAdviceDetailDTO, dailyTimes, date, visitDTO, drugMap, materiaMap);

                if(inptCostDTO == null ){
                    //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
                    adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
                    startTime = DateUtils.dateAdd(startTime, day);
                    continue;
                }
                //判断当前集合是否已经存在对应的待领记录
//                Date finalDate = date;
//                if (!ListUtils.isEmpty(inptCostDTOs.stream().filter(cost -> Constants.SF.S.equals(cost.getIsWait()) && cost.getIatId().equals(inptCostDTO.getIatId())
//                        && DateUtils.dateToDate(cost.getPlanExecTime()).compareTo(DateUtils.dateToDate(date))==0
//                        && cost.getSourceId().equals(inptCostDTO.getSourceId())).collect(Collectors.toList()))) {
//                    //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
//                    adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
//                    startTime = DateUtils.dateAdd(startTime, day);
//                    continue;
//                }
                //替换上面的判断
                String key = inptCostDTO.getIatId()+"_"+inptCostDTO.getSourceId()+"_"+DateUtils.format(inptCostDTO.getCostTime(),DateUtils.YMD);
                if (inputCostStr.contains(key)) {
                    //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
                    adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
                    startTime = DateUtils.dateAdd(startTime, day);
                    continue;
                }
                //判断领药记录是否已生成
                List<InptCostDTO> waitReceiveDTOList = inptCostDAO.queryWait(inptCostDTO);
                if (!ListUtils.isEmpty(waitReceiveDTOList)) {
                    //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
                    adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
                    startTime = DateUtils.dateAdd(startTime, day);
                    continue;
                }

                //住院领药申请
                produceWaitReceive(inptCostDTO, inptAdviceDTO, medicalAdviceDTO, date, msgList, inWaitReceiveDTOS, drugMap, materiaMap);

                // 根据医保限制用药系统参数(INSURE_DEFAULT_REG_CODE)，判断是否需要更新费用表中相关医保限制用药字段 luoyong 2021.07.28/14:21
                if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                    Map parse = (Map) JSON.parse(sysParameterDTO.getValue());
                    String isLmtDrugFlag = MapUtils.get(parse, "isLmtDrugFlag");
                    // 开启了限制用药
                    if (StringUtils.isNotEmpty(isLmtDrugFlag) && "1".equals(isLmtDrugFlag)) {
                        inptCostDTO.setLmtUserFlag(inptAdviceDetailDTO.getLmtUserFlag());
                        inptCostDTO.setLimUserExplain(inptAdviceDetailDTO.getLimUserExplain());
                        inptCostDTO.setIsReimburse(inptAdviceDetailDTO.getIsReimburse());
                    }
                }

                inptCostDTOs.add(inptCostDTO);
                //添加Key 防止重复生成当天费用
                inputCostStr.add(key);

                if(inptCostDTOs.size() % 100 == 0){
                    inptCostDAO.insertInptCostBatch(inptCostDTOs);
                    inptCostDTOs.clear();
                }

                //记录每条医嘱的最后费用时间是哪一天,周期内的医嘱不做这样的处理，医嘱核收后的每一天都会生成费用(pengbo)
                adviceIdCostTime.put(inptAdviceDetailDTO.getIaId(),startTime);
                //时间根据频率周期变化 开始时间 = 开始时间+频率周期
                startTime = DateUtils.dateAdd(startTime, day);
            }
        }

        //住院费用信息入库
        if (!ListUtils.isEmpty(inptCostDTOs)) {
            inptCostDAO.insertInptCostBatch(inptCostDTOs);
        }

        //领药申请入库
        if (!ListUtils.isEmpty(inWaitReceiveDTOS)) {
            inptCostDAO.insertPharInWaitReceiveBatch(inWaitReceiveDTOS);
        }

        //医嘱最近执行时间更新（最外面的更新执行时间方法（updateLastExeTime）替换成下面这个）
        if (!adviceIdCostTime.isEmpty()) {
            inptAdviceDAO.newUpdateLastExeTime(medicalAdviceDTO,adviceIdCostTime);
        }
        //消息入库
        if(!ListUtils.isEmpty(msgList)) {
            inptCostDAO.insertMsg(msgList);
        }
    }

    /**
     * @Method: buildMsg
     * @Description: 组装消息
     * @Param: [medicalAdviceDTO, msgList, inptAdviceDTO, date, inptCostDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/21 11:21
     * @Return: void
     **/
    private void buildMsg(MedicalAdviceDTO medicalAdviceDTO, List<MsgTempRecordDO> msgList, InptCostDTO inptCostDTO) {
        //根据条件查询消息模板,如果存在就不再插入
        List<MsgTempRecordDO> msgs = inptCostDAO.queryMsg(inptCostDTO.getHospCode(),inptCostDTO.getVisitId(),
                inptCostDTO.getIatId(),medicalAdviceDTO.getCheckTime());
        if (!ListUtils.isEmpty(msgs)) {
            return;
        }
        MsgTempRecordDO msgTempRecordDO = new MsgTempRecordDO();
        msgTempRecordDO.setId(SnowflakeUtils.getId());
        msgTempRecordDO.setHospCode(inptCostDTO.getHospCode());
        msgTempRecordDO.setVisitId(inptCostDTO.getVisitId());
        msgTempRecordDO.setAdviceId(inptCostDTO.getIatId());
        msgTempRecordDO.setType(Constants.MSGTYPE.KCBZ);
        msgTempRecordDO.setItemCode(inptCostDTO.getItemCode());
        msgTempRecordDO.setItemId(inptCostDTO.getItemId());
        msgTempRecordDO.setItemName(inptCostDTO.getItemName());
        msgTempRecordDO.setMsg("["+inptCostDTO.getItemName()+","+inptCostDTO.getSpec()+"]库存不足");
        msgTempRecordDO.setAcceptId(null);
        msgTempRecordDO.setAcceptName(null);
        msgTempRecordDO.setStatusCode(Constants.MSGSTATUS.WFS);
        msgTempRecordDO.setCrteId(medicalAdviceDTO.getCheckId());
        msgTempRecordDO.setCrteName(medicalAdviceDTO.getCheckName());
        msgTempRecordDO.setCrteTime(medicalAdviceDTO.getCheckTime());
        msgList.add(msgTempRecordDO);
    }

    /**
     * @Method: buildAdviceExec
     * @Description: 生成执行记录
     * @Param: [medicalAdviceDTO, adviceIds, BZDTF_TQLY_YYFS]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/11 15:24
     * @Return: void
     **/
    private void buildAdviceExec(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds, String type) {
        //执行记录
        List<InptAdviceExecDTO> inptAdviceExecDTOList = new ArrayList<>();

        //根据医嘱ID获取医嘱记录
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);

        if (!ListUtils.isEmpty(inptAdviceDTOList)) {
            for(InptAdviceDTO inptAdviceDTO: inptAdviceDTOList) {
                //

                //动态判断是否医嘱核收生成手术执行记录
                if (Constants.YZLB.YZLB5.equals(inptAdviceDTO.getTypeCode())) {
                    //获取参数(住院手术计费方式:ZY_SSJFFS) 住院手术计费方式（0：医生开医嘱计费，1：手术室补记账记费）
                    SysParameterDTO jffsSysParameter = getSysParameterDTO(medicalAdviceDTO.getHospCode(), "ZY_SSJFFS");
                    if (jffsSysParameter == null || "1".equals(jffsSysParameter.getValue())){
                        continue;
                    }
                }

                //校验状态
                if (checkAdvice(inptAdviceDTO)) continue;

                //计算开始日期、结束日期、判断是否预停
                Map<String,Object> map = getTime(inptAdviceDTO, type);
                //开始日期
                Date startTime = (Date) map.get("startTime");
                //结束日期
                Date endTime = (Date) map.get("endTime");
                //是否预停
                /*boolean lsYttz = (boolean) map.get("lsYttz");
                if (lsYttz) {
                    inptAdviceDTO.setStopTime(inptAdviceDTO.getPlanStopTime());
                }*/
                if (startTime.compareTo(endTime) > 0) {
                    continue;
                }

                //获取频率信息
                BaseRateDTO baseRateDTO = getBaseRateDTO(inptAdviceDTO.getHospCode(), inptAdviceDTO.getRateId());
                //频率执行周期
                int day = baseRateDTO.getExecInterval().intValue();
                //频率执行次数
                int rateTimes = baseRateDTO.getDailyTimes().intValue();

                while (startTime.compareTo(endTime) <= 0) {
                    //计算每日执行次数
                    int dailyTimes = getDailyTimes(inptAdviceDTO, startTime, endTime, rateTimes);

                    //如果执行执行次数小于等于0，那么就不产生执行记录和费用
                    if (dailyTimes <= 0) {
                        startTime = DateUtils.dateAdd(startTime, day);
                        continue;
                    }

                    //生成执行记录
                    buildAdviceExecDTO(medicalAdviceDTO, inptAdviceExecDTOList, inptAdviceDTO, startTime, dailyTimes);

                    //时间根据频率周期变化 开始时间 = 开始时间+频率周期
                    startTime = DateUtils.dateAdd(startTime, day);
                }
            }

            //执行记录入库
            if (!ListUtils.isEmpty(inptAdviceExecDTOList)) {
                inptAdviceExecDAO.insertInptAdviceExecBatch(inptAdviceExecDTOList);
            }
        }
    }

    /**
     * @Method: buildAdviceExecDTO
     * @Description: 组装执行对象
     * 动静态计费不需要生成执行记录,需要皮试、皮试换药带出来的药品不需要生成执行记录(需要费用和领药申请)
     * 计划执行时间: 一天两次  (23:59:59  -  当前时间)  分成3段  取后面两个时间
     * @Param: [medicalAdviceDTO, inptAdviceDetailDTO, inptAdviceDTO, startTime]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 11:38
     * @Return: cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO
     **/
    private void buildAdviceExecDTO(MedicalAdviceDTO medicalAdviceDTO, List<InptAdviceExecDTO> inptAdviceExecDTOList
            , InptAdviceDTO inptAdviceDTO, Date startTime, int dailyTimes) {
        //皮试换药带出来的药品不需要生成执行记录
        if (!StringUtils.isEmpty(inptAdviceDTO.getSourceIaId())) {
            return;
        }
        /**
         * 循环频次,组装住院执行记录
         * 需要一天执行3次,如果已有两条记录，那么只生成一条
         */
        //查询已生成的执行记录
        InptAdviceExecDTO execDTO = new InptAdviceExecDTO();
        execDTO.setVisitId(inptAdviceDTO.getVisitId());
        execDTO.setAdviceId(inptAdviceDTO.getId());
        execDTO.setHospCode(inptAdviceDTO.getHospCode());
        execDTO.setPlanExecTime(startTime);
        List<InptAdviceExecDTO> execDTOList = inptAdviceExecDAO.queryInptAdvicePage(execDTO);
        int execDTOListNum = ListUtils.isEmpty(execDTOList) ? 0 : execDTOList.size();

        //如果需要执行次数<已经执行次数,不再执行
        if (dailyTimes < execDTOListNum) {
            return;
        }

        //计算执行时间
        String planTime = DateUtils.format(startTime,DateUtils.Y_M_D);

        //获取计划执行时间时间段
        int hour = (int)(24/(dailyTimes+1));
        if (hour <= 0) {
            hour = 1;
        }
        String advanceId = medicalAdviceDTO.getSfTqly();
        for (int i=0;i<dailyTimes;i++) {
            if (i < execDTOListNum) {
                continue;
            }
            InptAdviceExecDTO InptAdviceExecDTO = new InptAdviceExecDTO();
            InptAdviceExecDTO.setId(SnowflakeUtils.getId());
            InptAdviceExecDTO.setAdvanceId(advanceId);
            InptAdviceExecDTO.setHospCode(inptAdviceDTO.getHospCode());
            InptAdviceExecDTO.setAdviceId(inptAdviceDTO.getId());
            //医嘱明细ID
//                InptAdviceExecDTO.setAdviceDetailId(inptAdviceDetailDTO.getId());
            InptAdviceExecDTO.setVisitId(inptAdviceDTO.getVisitId());
            InptAdviceExecDTO.setBabyId(inptAdviceDTO.getBabyId());
            InptAdviceExecDTO.setDeptId(inptAdviceDTO.getDeptId());
            InptAdviceExecDTO.setExecDeptId(inptAdviceDTO.getExecDeptId());
            InptAdviceExecDTO.setSignCode(Constants.QMZT.WQM);

            Date planDate = null;
            int cHour = hour*(i+1);
            if (cHour >= 24) {
                planDate = DateUtils.parse(planTime+" 23:50:00",DateUtils.Y_M_DH_M_S);
            } else {
                planDate = DateUtils.parse(planTime+" "+cHour+":00:00",DateUtils.Y_M_DH_M_S);
            }
            InptAdviceExecDTO.setPlanExecTime(planDate);

            InptAdviceExecDTO.setIsSkin(inptAdviceDTO.getIsSkin());
            //是否皮试
            InptAdviceExecDTO.setIsPositive(inptAdviceDTO.getIsPositive());
            //是否打印
            InptAdviceExecDTO.setIsPrint(Constants.SF.F);
            InptAdviceExecDTO.setCrteId(medicalAdviceDTO.getCheckId());
            InptAdviceExecDTO.setCrteName(medicalAdviceDTO.getCheckName());
            InptAdviceExecDTO.setCrteTime(medicalAdviceDTO.getCheckTime());
            if (InptAdviceExecDTO != null) {
                inptAdviceExecDTOList.add(InptAdviceExecDTO);
            }
            if(inptAdviceExecDTOList.size() % 500 == 0) {
                inptAdviceExecDAO.insertInptAdviceExecBatch(inptAdviceExecDTOList);
                inptAdviceExecDTOList.clear();
            }
        }
    }

    /**
     * @Method: checkAdvice
     * @Description: 校验
     * @Param: [BZDTF_TQLY_YYFS, inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/6 16:40
     * @Return: boolean
     **/
    private boolean checkAdvice(InptAdviceDTO inptAdviceDTO) {
        //临时医嘱，交给病人
        if("1".equals(inptAdviceDTO.getIsLong()) && "1".equals(inptAdviceDTO.getIsGive())) {
            throw new AppException("临时医嘱[" + inptAdviceDTO.getContent() + "]交病人");
        }
        //临时医嘱执行后，不允许再执行
        if ("1".equals(inptAdviceDTO.getIsLong()) && inptAdviceDTO.getLastExecTime()!=null) {
            throw new AppException("临时医嘱[" + inptAdviceDTO.getContent() + "]医嘱执行后,不允许再执行");
        }
        //未核收的医嘱不允许执行
        if("0".equals(inptAdviceDTO.getIsCheck())){
            throw new AppException("未核收的医嘱不允许执行,医嘱内容:"+ inptAdviceDTO.getContent());
        }
        return false;
    }

    /**
     * @Method: getDailyTimes
     * @Description: 计算每日执行次数
     * 1.开嘱当天执行次数
     * 2.停嘱当天执行次数
     * 3.出院带药，交病人的次数默认给1
     * @Param: [inptAdviceDTO, startTime, endTime, baseRateDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/2 10:41
     * @Return: int
     **/
    private int getDailyTimes(InptAdviceDTO inptAdviceDTO, Date startTime, Date endTime, int rateTimes) {
        int dailyTimes = 0;
        int startExecNum = inptAdviceDTO.getStartExecNum()== null ? 0:inptAdviceDTO.getStartExecNum();
        int endExecNum = inptAdviceDTO.getEndExecNum()== null ? 0:inptAdviceDTO.getEndExecNum();
        Date stopTime = inptAdviceDTO.getStopTime();
        Date longStratTime = inptAdviceDTO.getLongStartTime();

        if (startTime.compareTo(endTime)==0 && stopTime!=null && endTime.compareTo(DateUtils.dateToDate(stopTime))==0 && endExecNum>=0) {
            //开始日期=结束日期=停嘱日期 停嘱当日执行次数>=0 表示是停嘱当天
            dailyTimes = endExecNum>rateTimes?rateTimes:endExecNum;
        } else if (startTime.compareTo(DateUtils.dateToDate(longStratTime))==0 && startExecNum>=0) {
            //开始日期=医嘱开始日期 开嘱当日执行次数>0 表示开嘱当天
            dailyTimes = startExecNum>rateTimes?rateTimes:startExecNum;
        } else if ("1".equals(inptAdviceDTO.getIsGive()) && Constants.YYXZ.CYDY.equals(inptAdviceDTO.getUseCode())) {
            //用药性质:出院带药  交病人 次数为1  增加控制交病人且为出院带药在执行表里面装只插一条记录
            dailyTimes = 1;
        } else {
            dailyTimes = rateTimes;
        }
        return dailyTimes;
    }

    /**
     * @Method: getInptAdviceDTO
     * @Description: 根据医嘱ID获取医嘱信息
     * @Param: [hospCode, adviceId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 10:24
     * @Return: cn.hsa.module.inpt.doctor.dto.InptAdviceDTO
     **/
    private InptAdviceDTO getInptAdviceDTO(String hospCode, String adviceId) {
        //根据医嘱ID获取医嘱对象
        InptAdviceDTO adviceDTO = new InptAdviceDTO();
        adviceDTO.setHospCode(hospCode);
        adviceDTO.setId(adviceId);
        InptAdviceDTO inptAdviceDTO = inptAdviceDAO.getInptAdviceById(adviceDTO);
        if (adviceDTO == null) {
            throw new AppException("获取医嘱信息失败");
        }
        return inptAdviceDTO;
    }

    /**
     * @Method: getBaseRateDTO
     * @Description: 获取频率信息
     * @Param: [inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/17 17:02
     * @Return: cn.hsa.module.base.rate.dto.BaseRateDTO
     **/
    private BaseRateDTO getBaseRateDTO(String hospCode,String rateId) {
        //医嘱如果没有填写频率那么默认给一天一次 周期为1
        if (StringUtils.isEmpty(rateId)) {
            BaseRateDTO baseRateDTO = new BaseRateDTO();
            baseRateDTO.setDailyTimes(BigDecimal.valueOf(1));
            baseRateDTO.setExecInterval(BigDecimal.valueOf(1));
            return baseRateDTO;
        }

        //组装getBaseRateDTO信息请求参数
        Map map = new HashMap();
        BaseRateDTO rateDTO = new BaseRateDTO();
        rateDTO.setHospCode(hospCode);
        rateDTO.setId(rateId);
        map.put("hospCode", hospCode);
        map.put("baseRateDTO", rateDTO);
        //通过主键获取频率信息
        BaseRateDTO baseRateDTO = baseRateService_consumer.getById(map).getData();
        if (baseRateDTO == null) {
            baseRateDTO = new BaseRateDTO();
            baseRateDTO.setDailyTimes(BigDecimal.valueOf(1));
            baseRateDTO.setExecInterval(BigDecimal.valueOf(1));
            return baseRateDTO;
        } else {
            //如果频率没有设置每日执行次数或者周期  那么默认给1
            if (baseRateDTO.getDailyTimes().compareTo(BigDecimal.valueOf(0)) <= 0){
                baseRateDTO.setDailyTimes(BigDecimal.valueOf(1));
            }
            if (baseRateDTO.getExecInterval().compareTo(BigDecimal.valueOf(0)) <= 0){
                baseRateDTO.setExecInterval(BigDecimal.valueOf(1));
            }
        }
        return baseRateDTO;
    }

    /**
     * @Method: getTime
     * @Description: 计算开始日期、结束日期、判断是否预停  长期费用计算日期方法
     * 开始时间:
     *      长期医嘱:最近执行时间不为空，使用最近执行时间否则使用医嘱开始时间
     *      临时医嘱：医嘱开始时间
     * 结束时间：
     *      长期医嘱：默认核收时间
     *      临时医嘱：长期费用滚动时间
     * 停嘱时间不为空，医嘱结束日期小于等于结束时间：
     *          医嘱开始时间小于医嘱停止时间：结束时间=停嘱时间-1；否则：结束时间=医嘱停嘱时间
     * 预停时间不为空，预停时间小于等于结束时间：
     *      结束时间=预停时间-1
     * type->1:长期费用
     * @Param: [medicalAdviceDTO, inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/16 20:32
     * @Return: void
     **/
    private Map<String,Object> getTime(InptAdviceDTO inptAdviceDTO, String type) {
        //开始日期
        Date startTime = null;
        //结束日期
        Date endTime = null;
        //提前领药天数
        int days = 0;
        if (StringUtils.isNotEmpty(inptAdviceDTO.getAdvanceDays())){
            days = Integer.parseInt(inptAdviceDTO.getAdvanceDays());
            if (days<0){
                days = 0;
            }
        }
        if ("0".equals(inptAdviceDTO.getIsLong())) {//长期医嘱
            //默认补足(补收前面的费用)
            if (inptAdviceDTO.getLastExecTime() != null) {
                startTime = DateUtils.dateToDate(inptAdviceDTO.getLastExecTime());
            } else {
                startTime = DateUtils.dateToDate(inptAdviceDTO.getLongStartTime());
            }
            endTime = DateUtils.dateToDate(DateUtils.dateAdd(DateUtils.getNow(),days));
        } else {//临时医嘱
            startTime = DateUtils.dateToDate(inptAdviceDTO.getLongStartTime());
            endTime = startTime;
        }

        boolean lsYttz = false;
        //停嘱时间
        Date stopTime = DateUtils.dateToDate(inptAdviceDTO.getStopTime());
        //医嘱开始时间
        Date longStartTime = DateUtils.dateToDate(inptAdviceDTO.getLongStartTime());
        //预停时间
        Date plantTime = DateUtils.dateToDate(inptAdviceDTO.getPlanStopTime());
        //结束日期不等于空
        if (stopTime != null) {
            //医嘱结束日期小于等于长期费用结束日期
            if (stopTime.compareTo(endTime) <= 0) {
                if ("1".equals(type)) {
                    //医嘱开始时间小于停止时间
                    if (longStartTime.compareTo(stopTime) < 0) {
                        //停嘱当天不需要计费
                        endTime = DateUtils.dateAdd(stopTime, -1);
                    } else {
                        endTime = DateUtils.dateToDate(stopTime);
                    }
                } else {
                    endTime = DateUtils.dateToDate(stopTime);
                }

            }
        }
        //预停时间不为空并且预停时间小于等于结束时间  预停不停嘱
        if (plantTime!=null && plantTime.compareTo(endTime)<=0) {
            //预停日期不需要计费（预停日期-1）
            lsYttz = true;
            //预停医嘱当天需要生成费用 -- 2021-08-03 pengbo
            //endTime = DateUtils.dateAdd(plantTime,-1) ;
            endTime = plantTime ;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("lsYttz", lsYttz);
        return map;
    }

    /**
     * @Method: getSysParameterDTO
     * @Description: 获取系统参数
     * @Param: [hospCode, code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:27
     * @Return: cn.hsa.module.sys.parameter.dto.SysParameterDTO
     **/
    private SysParameterDTO  getSysParameterDTO(String hospCode,String code) {
        //获取参数(护士站执行医嘱生成费用方式)
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("code", code);
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        return sysParameterDTO;
    }

    /**
     * @Method: changeReturnCost
     * @Description:
     * 需要退费...的情况
     * 1.新开医嘱早上核收,下午停嘱
     * 2.长期费用凌晨生成了费用和执行记录 下午需要停嘱
     * 停嘱医嘱时：增加参数判断不自动退费的用药方式（因口服类药品可能以开封，不支持自动退费），并且控制核收时要先核收该医嘱的正常医嘱才能核收停嘱的医嘱（防止费用问题），
     * 1.获取停嘱当日及后面的费用全部冲红、退药
     * 2.获取停嘱当日及后面的执行记录全部取消签名
     * @Param: [medicalAdviceDTO, inptAdviceDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/16 8:51
     * @Return: void
     **/
    private void changeReturnCost(MedicalAdviceDTO medicalAdviceDTO, List<InptAdviceDTO> stopAdviceList) {
        if (ListUtils.isEmpty(stopAdviceList)) {
            return;
        }

        //组装参数获取需要停止的医嘱集合
        List<String> adviceIds = new ArrayList<>();
        for (InptAdviceDTO advice:stopAdviceList) {
            adviceIds.add(advice.getId());
        }
        //根据医嘱ID获取医嘱记录
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getInptAdviceByIds(medicalAdviceDTO.getHospCode(), adviceIds);
        if(ListUtils.isEmpty(inptAdviceDTOList)) {
            return;
        }

        //获取参数(提前领药不自动退费的用药方式,停嘱时不需要退费、取消执行记录、退药)
        SysParameterDTO BZDTF_TQLY_YYFS = getSysParameterDTO(medicalAdviceDTO.getHospCode(), "BZDTF_TQLY_YF");
        //如果为空，默认给静脉推注
        if (BZDTF_TQLY_YYFS == null) {
            BZDTF_TQLY_YYFS = new SysParameterDTO();
            BZDTF_TQLY_YYFS.setValue("3");
        }
        if (StringUtils.isEmpty(BZDTF_TQLY_YYFS.getValue())) {
            BZDTF_TQLY_YYFS.setValue("3");
        }

        for(InptAdviceDTO inptAdviceDTO:inptAdviceDTOList) {
            //停嘱退费只处理已取消或者已停止的医嘱
            if (Constants.QMZT.QXQM.equals(inptAdviceDTO.getSignCode()) || Constants.SF.S.equals(inptAdviceDTO.getIsStopCheck())) {
                //用法不包含在参数内的才能进行退费
                if (!StringUtils.isEmpty(inptAdviceDTO.getUsageCode()) && !StringUtils.isEmpty(BZDTF_TQLY_YYFS.getValue())) {
                    String[] BZDTF_TQLY_YYFS_array = BZDTF_TQLY_YYFS.getValue().split(",");
                    if (Arrays.stream(BZDTF_TQLY_YYFS_array).filter(tqly -> tqly.equals(inptAdviceDTO.getUsageCode())).collect(Collectors.toList()).size()>0) {
                        continue;
                    }
                }

                //退费校验
                checkCancel(inptAdviceDTO);

                //冲红费用、退药
                cancelCostAndDrug(medicalAdviceDTO, inptAdviceDTO);

                //取消未执行的执行记录
                updateCancelExec(inptAdviceDTO,medicalAdviceDTO);
            }
        }
    }

    /**
     * @Method: checkCancel
     * @Description: 退费校验
     * @Param: [inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/7 10:30
     * @Return: void
     **/
    private void checkCancel(InptAdviceDTO inptAdviceDTO) {
        //长期医嘱(停嘱、未核收)和临时医嘱,临时医嘱(未核收),不需要退费
        boolean flag = ("0".equals(inptAdviceDTO.getIsLong()) && "1".equals(inptAdviceDTO.getIsStop())
                && ("0".equals(inptAdviceDTO.getIsStopCheck()) || StringUtils.isEmpty(inptAdviceDTO.getIsStopCheck())))
                || ("1".equals(inptAdviceDTO.getIsLong()) && "0".equals(inptAdviceDTO.getIsCheck()));
        if (flag) {
            throw new AppException("医嘱还未核收,无法停嘱核收,医嘱内容[" + inptAdviceDTO.getContent() + "]");
        }
    }

    /**
     * @Method: cancelCost
     * @Description: 冲红费用
     * 查出在停嘱时间之后的费用，冲红掉，更新住院就诊表费用信息
     * @Param: [medicalAdviceDTO, inptCostDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 20:39
     * @Return: void
     **/
    private void cancelCostAndDrug(MedicalAdviceDTO medicalAdviceDTO, InptAdviceDTO inptAdviceDTO) {
        //组装参数
        Map<String, Object> param = new HashMap<>();
        //长嘱
        if ("0".equals(inptAdviceDTO.getIsLong())) {
            param.put("tzDate", inptAdviceDTO.getStopTime());
        } else {
            param.put("tzDate", inptAdviceDTO.getLongStartTime());
        }

        param.put("adviceId", inptAdviceDTO.getId());
        param.put("usageCode", inptAdviceDTO.getUsageCode());
        param.put("visitId", inptAdviceDTO.getVisitId());
        param.put("hospCode", inptAdviceDTO.getHospCode());
        //查询停嘱时间之后的医嘱(状态正常、结算状态正常、医嘱ID,就诊ID,计费时间>=停嘱时间)
        List<InptCostDTO> inptCostDTOList = inptCostDAO.getTzzhCost(param);
        List<InptCostDTO> backCostList = new ArrayList<>();

        if (!ListUtils.isEmpty(inptCostDTOList)) {
            List<String> costIds = new ArrayList<>();
            PharInWaitReceiveDTO pharInWaitReceiveDTO = null ;
            for (InptCostDTO costDTO:inptCostDTOList) {
                // 如果是药品 或者材料  并且是   常规或者 出院带药的情况 需要校验是否预配药,发药
//                if((Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode()) || Constants.XMLB.CL.equals(inptAdviceDTO.getItemCode())) && (Constants.YYXZ.CG.equals(inptAdviceDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(inptAdviceDTO.getUseCode())) ){
//                    pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
//                    pharInWaitReceiveDTO.setCostId(costDTO.getId());
//                    pharInWaitReceiveDTO.setHospCode(costDTO.getHospCode());
//                    pharInWaitReceiveDTO = inptCostDAO.getPharInWaitReceiveByIatid(pharInWaitReceiveDTO);
//                    //如果 该请领不是带领状态  那么跳过次医嘱
//                    if(pharInWaitReceiveDTO != null && !Constants.LYZT.DL.equals(pharInWaitReceiveDTO.getStatusCode())){
//                      continue;
//                    }
//                }

                // 经确认动静态辅助计费不退费 （手动去退） 2022-06-09 pengbo
                Integer endExecNum= inptAdviceDTO.getEndExecNum() == null ? 0 : inptAdviceDTO.getEndExecNum();
                if(Constants.FYLYFS.DJTJF.equals(costDTO.getSourceCode()) && endExecNum>0){
                    continue;
                }

                BigDecimal tzNum = BigDecimal.valueOf(0);
                BigDecimal tzCost = BigDecimal.valueOf(0);
                //药品才会有部分退费
                Date now =  DateUtils.trunc(DateUtils.getNow(),DateUtils.Y_M_D);
                Date costTime = DateUtils.trunc(costDTO.getCostTime(),DateUtils.Y_M_D);
                //如果费用时间比当前时间大那么全部退掉
                if (Constants.XMLB.YP.equals(costDTO.getItemCode()) && !DateUtils.dateCompare(now,costTime)){
                    //根据费用来源ID获取医嘱明细信息
                    InptAdviceDetailDTO inptAdviceDetailDTO = new InptAdviceDetailDTO();
                    inptAdviceDetailDTO.setHospCode(costDTO.getHospCode());
                    inptAdviceDetailDTO.setId(costDTO.getSourceId());
                    InptAdviceDetailDTO adviceDetailDTO = inptAdviceDetailDAO.getInptAdviceDetailById(inptAdviceDetailDTO);
                    if (adviceDetailDTO == null) {
                        throw new AppException("根据费用来源ID获取医嘱明细失败");
                    }

                    BaseDrugDTO baseDrugDTO = getBaseDrugDTOById(inptAdviceDTO.getHospCode(),inptAdviceDTO.getItemId(),medicalAdviceDTO.getDeptId());
                    // 没有配置默认 1：单次向上取整
                    if(StringUtils.isEmpty(baseDrugDTO.getTruncCode()) || "1".equals(baseDrugDTO.getTruncCode())){
                        adviceDetailDTO.setNum(BigDecimal.valueOf(Math.ceil(adviceDetailDTO.getNum().doubleValue())));
                    }
                    //-----------------------------------------------
                    //计算停嘱当天的费用数量
                    tzNum = BigDecimalUtils.multiply(BigDecimal.valueOf(inptAdviceDTO.getEndExecNum()==null?0:inptAdviceDTO.getEndExecNum()),
                            adviceDetailDTO.getNum());

                    //如果用量单位不等于总数量单位 转换成大单位数量
                    if (!inptAdviceDTO.getUnitCode().equals(inptAdviceDTO.getTotalNumUnitCode())){
                        tzNum = BigDecimalUtils.divide(tzNum,baseDrugDTO.getSplitRatio());
                    }

                    //计算停嘱当天的费用
                    tzCost = BigDecimalUtils.multiply(tzNum,costDTO.getPrice());

                    //向上取整
                    tzNum = BigDecimal.valueOf(Math.ceil(tzNum.doubleValue()));
                } else if (!DateUtils.dateCompare(now,costTime)){
                    //材料、项目、医嘱目录计算停嘱当天的费用数量
                    tzNum = BigDecimal.valueOf(inptAdviceDTO.getEndExecNum()==null?0:inptAdviceDTO.getEndExecNum());
                }

                if (Constants.XMLB.YP.equals(costDTO.getItemCode()) || Constants.XMLB.CL.equals(costDTO.getItemCode())){
                    costDTO.setBackNum(BigDecimal.valueOf(Math.ceil(BigDecimalUtils.subtract(costDTO.getTotalNum(),tzNum).doubleValue())));
                    costDTO.setBackAmount(BigDecimalUtils.subtract(costDTO.getTotalPrice(),tzCost));
                }else{
                    Map map = new HashMap();
                    map.put("hospCode",inptAdviceDTO.getHospCode());
                    BaseRateDTO baseRateDTO = new BaseRateDTO();
                    baseRateDTO.setHospCode(inptAdviceDTO.getHospCode());
                    baseRateDTO.setId(inptAdviceDTO.getRateId());
                    map.put("baseRateDTO",baseRateDTO);
                    baseRateDTO = baseRateService_consumer.getById(map).getData();
                    BigDecimal dailyTimes = BigDecimal.valueOf(1) ;
                    if(baseRateDTO != null){
                        dailyTimes = baseRateDTO.getDailyTimes();
                    }
                    //总数量 - （总数量*停止次数/每日次数）
                    BigDecimal  backNum =BigDecimalUtils.subtract(costDTO.getTotalNum(),BigDecimalUtils.divide(BigDecimalUtils.multiply(costDTO.getTotalNum(),tzNum),dailyTimes));
                    backNum = BigDecimal.valueOf(Math.ceil(backNum.doubleValue()));
                    costDTO.setBackNum(backNum);
                    costDTO.setBackAmount(BigDecimalUtils.subtract(costDTO.getTotalPrice(),tzCost));

                    // costDTO.setBackNum(BigDecimalUtils.subtract(costDTO.getTotalNum(),tzNum));
                    // costDTO.setBackAmount(BigDecimalUtils.subtract(costDTO.getTotalPrice(),tzCost));
                }

                //过滤掉退费数量为0的费用
                if (costDTO.getBackNum().compareTo(BigDecimal.valueOf(0)) > 0) {
                    backCostList.add(costDTO);
                    costIds.add(costDTO.getId());
                }
            }

            //调用退费接口
            if (!ListUtils.isEmpty(backCostList)) {
                Map<String, Object> backCostMap = new HashMap<>();
                backCostMap.put("hospCode", medicalAdviceDTO.getHospCode());
                backCostMap.put("userId", medicalAdviceDTO.getCheckId());
                backCostMap.put("userName", medicalAdviceDTO.getCheckName());
                backCostMap.put("deptId", medicalAdviceDTO.getDeptId());
                backCostMap.put("sourceType", "medicalAdvice");
                backCostMap.put("inptCostDTOs", backCostList);
                backCostByInputBO.saveBackCostWithInpt(backCostMap);
            }

//            if (!ListUtils.isEmpty(costIds)) {
//                Map<String, Object> waitReceiveMap = new HashMap<>();
//                PharInWaitReceiveDTO waitReceiveDTO = new PharInWaitReceiveDTO();
//                waitReceiveDTO.setCostIds(costIds);
//                waitReceiveDTO.setHospCode(medicalAdviceDTO.getHospCode());
//                waitReceiveMap.put("hospCode", medicalAdviceDTO.getHospCode());
//                waitReceiveMap.put("pharInWaitReceiveDTO",waitReceiveDTO);
//                //获取待领记录
//                List<PharInWaitReceiveDTO> waitReceiveDTOList = pharInWaitReceiveService_consumer.queryPharInWaitReceiveToIsBack(waitReceiveMap).getData();
//
//                //调用退药接口
//                if (!ListUtils.isEmpty(waitReceiveDTOList)) {
//                    List<String> ids = new ArrayList<>();
//                    for (PharInWaitReceiveDTO receiveDTO:waitReceiveDTOList) {
//                        ids.add(receiveDTO.getId());
//                    }
//                    Map<String, Object> backWaitMap = new HashMap<>();
//                    PharInWaitReceiveDTO pharInWaitReceiveDTO = waitReceiveDTOList.get(0);
//                    pharInWaitReceiveDTO.setIds(ids);
//                    pharInWaitReceiveDTO.setHospCode(medicalAdviceDTO.getHospCode());
//                    backWaitMap.put("hospCode", medicalAdviceDTO.getHospCode());
//                    backWaitMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
//                    inBackDrugService_consumer.updateInHospitalBackDrug(backWaitMap);
//                }
//            }
        }
    }

    /**
     * @Method: updateCancelExec
     * @Description: 取消未执行的执行记录
     * 查出在停嘱时间之后的执行记录，取消
     * @Param: [inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 20:37
     * @Return: void
     **/
    private void updateCancelExec(InptAdviceDTO inptAdviceDTO,MedicalAdviceDTO medicalAdviceDTO) {
        //组装参数
        InptAdviceExecDTO inptAdviceExecDTO = new InptAdviceExecDTO();
        inptAdviceExecDTO.setHospCode(inptAdviceDTO.getHospCode());
        inptAdviceExecDTO.setVisitId(inptAdviceDTO.getVisitId());
        inptAdviceExecDTO.setAdviceId(inptAdviceDTO.getId());
        //长嘱
        if ("0".equals(inptAdviceDTO.getIsLong())) {
            inptAdviceExecDTO.setPlanExecTime(inptAdviceDTO.getStopTime());
        } else {
            inptAdviceExecDTO.setPlanExecTime(inptAdviceDTO.getLongStartTime());
        }
        //获取所有的执行记录
        List<InptAdviceExecDTO> inptAdviceExecDTOList = inptAdviceExecDAO.getDRNotExec(inptAdviceExecDTO);
        if (ListUtils.isEmpty(inptAdviceExecDTOList)) {
            return;
        }
        //开特殊医嘱目录时没有填写停嘱当天执行次数会报错
        if(inptAdviceDTO.getEndExecNum() == null){
            throw new RuntimeException("当前医嘱["+inptAdviceDTO.getItemName() +"]未填写停嘱执行次数!");
        }
        //需要取消执行的次数
        int num = inptAdviceExecDTOList.size()-inptAdviceDTO.getEndExecNum();
        //未执行的记录
        List<InptAdviceExecDTO> adviceExecDTOList = new ArrayList<>();
        for(int i=0;i<inptAdviceExecDTOList.size();i++) {
            if (i<num) {
                InptAdviceExecDTO execDTO = new InptAdviceExecDTO();
                execDTO.setHospCode(inptAdviceDTO.getHospCode());
                execDTO.setId(inptAdviceExecDTOList.get(i).getId());
                execDTO.setSignCode(Constants.QMZT.QXQM);
                execDTO.setExecId(medicalAdviceDTO.getCheckId());
                execDTO.setExecName(medicalAdviceDTO.getCheckName());
                adviceExecDTOList.add(execDTO);
            }
        }
        //调用取消执行接口
        if(!ListUtils.isEmpty(adviceExecDTOList)) {
            doctorAdviceExecuteBO.updateDoctorAdviceExecute(adviceExecDTOList);
        }
    }

    /**
     * @Method: stopAdvice
     * @Description: 判断是否存在停同类、停非同类以及停自身的医嘱
     * 1.获取存在停同类、停非同类、停自身的医嘱
     * 2.循环医嘱记录 分别获取停同类、停非同类、停自身对应的医嘱
     * 3.更新医嘱表核收信息
     *
     * 停同类:正常医嘱并且开嘱时间在当前医嘱之前的医嘱类别为同类医嘱进行停止（排除自身）
     * 非同类:常医嘱并且开嘱时间在当前医嘱之前的医嘱类别为非同类医嘱进行停止（排除自身）
     * 停自身:只能是长期医嘱
     *
     * @Param: [medicalAdviceDTO, adviceIds]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 16:26
     * @Return: void
     **/
    private void stopAdvice(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds,List<InptAdviceDTO> stopAdviceList) {
        //根据医嘱ID查询停同类、停非同类、停自身的医嘱列表
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.getTzInptAdvices(adviceIds, medicalAdviceDTO.getHospCode());
        if(ListUtils.isEmpty(inptAdviceDTOList)) {
            return;
        }
        List<InptAdviceDTO> tzList = null ;
        //循环住院医嘱记录
        for(InptAdviceDTO adviceDTO:inptAdviceDTOList) {
            //根据ID获取医嘱目录信息
            BaseAdviceDTO boById = getBaseAdviceDTO(adviceDTO.getHospCode(), adviceDTO.getItemId());
            //需要停用的医嘱
            List<InptAdviceDTO> list = new ArrayList<>();
            //停同类(更新停嘱当日执行次数,执行次数->医嘱目录，非文字医嘱->停嘱当日执行次数)
            if("1".equals(boById.getIsStopSame())) {
                tzList = inptAdviceDAO.getTlAdvices(adviceDTO);
                tzList.forEach(e->e.setStopTime(adviceDTO.getLongStartTime()));
                list.addAll(tzList);
            }
            //停非同类(更新停嘱当日执行次数,执行次数->医嘱目录，非文字医嘱->停嘱当日执行次数)
            if("1".equals(boById.getIsStopSameNot())) {
                tzList = inptAdviceDAO.getFtlAdvices(adviceDTO);
                tzList.forEach(e->e.setStopTime(adviceDTO.getLongStartTime()));
                list.addAll(tzList);
            }
            //停自身(长期医嘱) 不更新停嘱当日执行次数
            if("1".equals(boById.getIsStopMyself())) {
                if ("0".equals(adviceDTO.getIsLong())) {
                    list.add(adviceDTO);
                }
            }

            //更新医嘱表停嘱核收信息
            if (!ListUtils.isEmpty(list)) {
                stopAdviceList.addAll(list);
                adviceIds.addAll(list.stream().map(e -> e.getId()).collect(Collectors.toList()));
                updateStopAdvice(medicalAdviceDTO, adviceDTO, list);
            }
        }
    }

    /**
     * @Method: getBaseAdviceDTO
     * @Description: 根据ID获取医嘱目录信息
     * @Param: [adviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/27 10:48
     * @Return: cn.hsa.module.base.ba.dto.BaseAdviceDTO
     **/
    private BaseAdviceDTO getBaseAdviceDTO(String hospCode,String itemId) {
        //组装获取医嘱目录的请求参数
        Map map = new HashMap();
        BaseAdviceDTO baseAdviceDTO = new BaseAdviceDTO();
        baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setId(itemId);
        map.put("hospCode", hospCode);
        map.put("baseAdviceDTO", baseAdviceDTO);
        //根据项目ID查询医嘱目录
        WrapperResponse<BaseAdviceDTO> response = baseAdviceService_consumer.getById(map);
        BaseAdviceDTO boById = response.getData();
        if (boById == null) {
            throw new AppException("医嘱目录为空");
        }
        return boById;
    }

    /**
     * @Method: getSysUserDTO
     * @Description: 查询带教医生
     * @Param: [adviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:38
     * @Return: cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    private SysUserDTO getSysUserDTO(InptAdviceDTO adviceDTO) {
        //组装参数
        Map teachMap = new HashMap();
        teachMap.put("hospCode", adviceDTO.getHospCode());
        teachMap.put("id", adviceDTO.getId());
        //根据用户ID获取代教医生信息
        WrapperResponse<SysUserDTO> response = sysUserService_consumer.getTeachDoctor(teachMap);
        return response.getData();
    }

    /**
     * @Method: updateStopAdvice
     * @Description: 停嘱(医嘱状态、提前收费天数、停嘱医生信息、停嘱和对人信息、停嘱带教医生信息、停嘱带教护士信息)
     * @Param: [medicalAdviceDTO, adviceDTO, ftlAdviceList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:29
     * @Return: void
     **/
    private void updateStopAdvice(MedicalAdviceDTO medicalAdviceDTO, InptAdviceDTO adviceDTO, List<InptAdviceDTO> adviceList) {
        List<InptAdviceDTO> adviceStopDTOList = new ArrayList<>();
        for (InptAdviceDTO dto : adviceList) {//停嘱
            InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
            inptAdviceDTO.setId(dto.getId());
            inptAdviceDTO.setHospCode(dto.getHospCode());
            //停嘱时间
            Date stopTime = medicalAdviceDTO.getCheckTime() ;
            if(dto.getStopTime() != null){
                stopTime = dto.getStopTime();
            }
            inptAdviceDTO.setStopTime(stopTime);
            //获取带教医生信息
            //SysUserDTO sysUserDTO = getSysUserDTO(adviceDTO);
            //代教医生 实习医生老师
            //if (sysUserDTO != null) {
                //inptAdviceDTO.setTeachDoctorId(sysUserDTO.getId());
                //inptAdviceDTO.setTeachDoctorName(sysUserDTO.getName());
            //}
            inptAdviceDTO.setStopDoctorId(adviceDTO.getCrteId());
            inptAdviceDTO.setStopDoctorName(adviceDTO.getCrteName());
            //停嘱审核人
            inptAdviceDTO.setStopCheckId(medicalAdviceDTO.getCheckId());
            inptAdviceDTO.setStopCheckName(medicalAdviceDTO.getCheckName());
            inptAdviceDTO.setStopCheckTime(medicalAdviceDTO.getCheckTime());
            //是否停嘱核收
            inptAdviceDTO.setIsStopCheck("1");
            //是否停嘱
            inptAdviceDTO.setIsStop("1");

            adviceStopDTOList.add(inptAdviceDTO);
        }
        //更新停嘱核收标识
        if (!ListUtils.isEmpty(adviceStopDTOList)) {
            inptAdviceDAO.updateStopInptAdviceBatch(adviceStopDTOList);
        }
    }

    /**
     * @Method: updateInpVIsitInfo
     * @Description: 更新住院病人表(inpt_visit)  护理级别(nursing_code)、膳食类型(diet_type)、病情标识(Illness_code)
     * 1.循环就诊病人记录、医嘱记录
     * 2.根据就诊ID、医嘱ID获取到医嘱目录
     * 3.判断医嘱目录是否存在护理级别、膳食类型、病情标识
     * 4.更新到就诊表
     * @Param: [adviceDTO, baseAdviceDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 15:45
     * @Return: void
     **/
    private void updateInpVIsitInfo(List<InptVisitDTO> inptVisitDTOList) {
        List<BaseAdviceDTO> baseAdviceDTOList = inptAdviceDAO.getIllnessAdviceByVisitId(inptVisitDTOList);
        if(ListUtils.isEmpty(baseAdviceDTOList)){
            return;
        }
        Map<String, List<BaseAdviceDTO> > map = baseAdviceDTOList.stream().collect(Collectors.groupingBy(BaseAdviceDTO::getVisitId, Collectors.toList()));

        InptVisitDTO inptVisitDTO = null;
        List<InptVisitDTO> listVisit = new ArrayList<InptVisitDTO>();
        for(String visitId:map.keySet()) {
            inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setId(visitId);
            baseAdviceDTOList = map.get(visitId);
            //循环当前所有的医嘱目录,判断是否存在膳食类型、护理级别、病情标识
            for(BaseAdviceDTO baseAdviceDTO:baseAdviceDTOList) {
                inptVisitDTO.setHospCode(baseAdviceDTO.getHospCode());
                if("1".equals(baseAdviceDTO.getBizType())) {
                    //膳食类型
                    inptVisitDTO.setDietType(baseAdviceDTO.getBizCode());
                } else if("2".equals(baseAdviceDTO.getBizType())) {
                    //护理级别
                    inptVisitDTO.setNursingCode(baseAdviceDTO.getBizCode());
                } else if("3".equals(baseAdviceDTO.getBizType())) {
                    //病情标识
                    inptVisitDTO.setIllnessCode(baseAdviceDTO.getBizCode());
                }
            }
            listVisit.add(inptVisitDTO);
        }
        //组装住院病人表参数（不管是否为null都需要修改，如果为null 说明没有病重医嘱，需要将病重字段置空）
        if(!ListUtils.isEmpty(listVisit)){
            inptVisitDAO.updateIllnessBacth(listVisit);
        }
    }

    /**
     *  医嘱停嘱或取消停嘱-->膳食/护理/危重修改
     * @param inptAdviceDTOList
     */
    private void updateStopInptVisitBizType(List<InptAdviceDTO> inptAdviceDTOList) {
        // 根据停嘱的医嘱列表查询的膳食/护理/危重类医嘱
        List<BaseAdviceDTO> baseAdviceDTOList = inptAdviceDAO.getLongIllnessAdviceByAdviceId(inptAdviceDTOList);
        if (ListUtils.isEmpty(baseAdviceDTOList)) {
            return;
        }

        Map<String, List<BaseAdviceDTO>> map = baseAdviceDTOList.stream().collect(Collectors.groupingBy(BaseAdviceDTO::getVisitId, Collectors.toList()));

        List<InptVisitDTO> inptVisitDTOList = new ArrayList<>();
        InptVisitDTO inptVisitDTO = null;
        for(String visitId:map.keySet()) {
            inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setId(visitId);
            baseAdviceDTOList = map.get(visitId);
            //循环当前所有的医嘱目录,判断是否存在膳食类型、护理级别、病情标识
            for(BaseAdviceDTO baseAdviceDTO:baseAdviceDTOList) {
                inptVisitDTO.setHospCode(baseAdviceDTO.getHospCode());
                if("1".equals(baseAdviceDTO.getBizType())) {
                    //膳食类型
                    inptVisitDTO.setDietType(null);
                } else if("2".equals(baseAdviceDTO.getBizType())) {
                    //护理级别
                    inptVisitDTO.setNursingCode(null);
                } else if("3".equals(baseAdviceDTO.getBizType())) {
                    //病情标识
                    inptVisitDTO.setIllnessCode(null);
                }
            }
            inptVisitDTOList.add(inptVisitDTO);
        }

        //组装住院病人表参数（不管是否为null都需要修改，如果为null 说明没有病重医嘱，需要将病重字段置空）
        if(!ListUtils.isEmpty(inptVisitDTOList)){
            inptVisitDAO.updateIllnessBacth(inptVisitDTOList);
        }
    }

    /**
     * @Method: updateCheckInfo
     * @Description: 更新医嘱表(inpt_advice)
     * 1.开嘱  更新是否核收(is_check)、医嘱核收人ID(check_id)、医嘱核收人姓名(check_name)、医嘱核收时间(check_time)
     * 2.停嘱  更新是否停嘱核收(is_stop_check)、停嘱核收人ID(stop_check_id)、停嘱核收人姓名(stop_check_name)、停嘱核收时间(stop_check_time)
     * @Param: [medicalAdviceDTO, adviceId]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 11:18
     * @Return: void
     **/
    private void updateCheckInfo(List<InptAdviceDTO> adviceDTOList) {

        //根据是否停嘱判断
        if (ListUtils.isEmpty(adviceDTOList)) {
            throw new AppException("根据传入的医嘱ID未获取到医嘱信息");
        }

        //批量修改医嘱核收跟停嘱核收信息
        inptAdviceDAO.updateStopAndCheckInfo(adviceDTOList);
    }

    // 医嘱拒收写入消息 lly 2021-12-02
    public int insertAdviceMessage(MedicalAdviceDTO medicalAdviceDTO){
        String hospCode =medicalAdviceDTO.getHospCode();
        String name = medicalAdviceDTO.getCrteName();
        String crteId = medicalAdviceDTO.getCrteId();
        Map openParam = new HashMap();
        openParam.put("hospCode", hospCode);
        openParam.put("code", "MSG_OPEN");
        SysParameterDTO openSysParameterDTO = sysParameterService_consumer.getParameterByCode(openParam).getData();
        if (openSysParameterDTO!=null&& "1".equals(openSysParameterDTO.getValue())) {
            Map queryParam =new HashMap();
            queryParam.put("hospCode",hospCode);
            queryParam.put("iatIdList",medicalAdviceDTO.getIds());
            queryParam.put("type",Constants.YZ_TYPE.YZ_TYPE_JS);
            List<InptVisitDTO> inptVisitDTOS = inptAdviceDAO.queryRejectAdviceList(queryParam);
            Map paramMap = new HashMap();
            paramMap.put("hospCode", hospCode);
            paramMap.put("code", "XXTS_SETTING");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(paramMap).getData();
            ConfigInfoDO configInfoDO = null;
            if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                configInfoDO = StringUtils.getConfigInfoDOFromSys(sysParameterDTO.getValue(),"adviceMsg");
            }
            List<MessageInfoDTO> messageInfoDTOList = new ArrayList<>();
            if (inptVisitDTOS != null && inptVisitDTOS.size() > 0) {
                for (InptVisitDTO inptVisitDTO : inptVisitDTOS) {
                    MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                    messageInfoDTO.setId(SnowflakeUtils.getId());
                    messageInfoDTO.setHospCode(hospCode);
                    messageInfoDTO.setSourceId("");
                    messageInfoDTO.setVisitId(inptVisitDTO.getId());
                    // 推送到科室
                    if ("1".equals(configInfoDO.getIsPersonal())) {
                        messageInfoDTO.setReceiverId("");
                        messageInfoDTO.setDeptId(inptVisitDTO.getInDeptId());
                    }else if ("0".equals(configInfoDO.getIsPersonal())){
                        // 推送到个人
                        messageInfoDTO.setDeptId("");
                        messageInfoDTO.setReceiverId(configInfoDO.getReceiverId());
                    }else {
                        // 默认推送到科室
                        messageInfoDTO.setReceiverId("");
                        messageInfoDTO.setDeptId(inptVisitDTO.getInDeptId());
                    }
                    messageInfoDTO.setLevel(configInfoDO.getLevel());
                    messageInfoDTO.setSendCount(configInfoDO.getSendCount());
                    messageInfoDTO.setType(Constants.MSG_TYPE.MSG_YZ);
                    messageInfoDTO.setContent(inptVisitDTO.getName() + "的医嘱已被拒收");
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
                    messageInfoDTOList.add(messageInfoDTO);
                }
                // 获取医院kafka 的IP与端口
                sendMessage(messageInfoDTOList,Constants.MSG_TOPIC.producerTopicKey,hospCode);
            }
        }
        return 0;
    }

    // 核收消息消费 lly 2021-12-03
    public void consumeCheckAdviceMessage(List<InptAdviceDTO> inptAdviceDTOList,String hospCode){
        Map openParam = new HashMap();
        openParam.put("hospCode", hospCode);
        openParam.put("code", "MSG_OPEN");
        // 有开启消息提醒时才需要消费
        SysParameterDTO openSysParameterDTO = sysParameterService_consumer.getParameterByCode(openParam).getData();
        if (openSysParameterDTO!=null&& "1".equals(openSysParameterDTO.getValue())) {
            Set visitIds = new HashSet();
            if (inptAdviceDTOList != null && inptAdviceDTOList.size() > 0) {
                for (InptAdviceDTO adviceDTO : inptAdviceDTOList) {
                    visitIds.add(adviceDTO.getVisitId());
                }
            }
            // 消息置为已读
            if (visitIds != null && visitIds.size() > 0) {
                MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                messageInfoDTO.setStatusCode(Constants.MSGZT.MSG_YD);
                messageInfoDTO.setType(Constants.MSG_TYPE.MSG_YZ);
                messageInfoDTO.setHospCode(hospCode);
                messageInfoDTO.setIds(new ArrayList<>(visitIds));
                List<MessageInfoDTO> messageInfoDTOList =new ArrayList<>();
                messageInfoDTOList.add(messageInfoDTO);
                //messageInfoDAO.updateMssageInfoBatch(messageInfoDTO);
                sendMessage(messageInfoDTOList,Constants.MSG_TOPIC.consumerTopicKey,hospCode);
            }
        }
    }

    public void sendMessage(List<MessageInfoDTO> messageInfoDTOS,String type,String hospCode){
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
        String producerTopic = type;//生产者消息推送Topic
        KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
        String message = JSONObject.toJSONString(messageInfoDTOS);
        KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
    }
}
