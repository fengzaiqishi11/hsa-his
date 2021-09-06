package cn.hsa.inpt.doctor.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.doctor.bo.DoctorAdviceBO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.inpt.inptprint.dao.InptPrintDAO;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.oper.operInforecord.dao.OperInfoRecordDAO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.service.OperInfoRecordService;
import cn.hsa.module.stro.stock.dto.CheckStockDTO;
import cn.hsa.module.stro.stock.dto.CheckStockRespDTO;
import cn.hsa.module.stro.stock.service.CheckStockService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *@Package_name: cn.hsa.doctor.advice.bo.impl
 *@Class_name: DoctorAdviceBoImpl
 *@Describe: 住院医嘱
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 11:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class DoctorAdviceBOImpl extends HsafBO implements DoctorAdviceBO {

    @Resource
    InptAdviceDAO inptAdviceDAO;

    @Resource
    InptPrintDAO inptPrintDAO;

    /**
     * 单据接口调用
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;
    /**
     * 系统参数调用
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    /** 手术模块数据库访问层 **/
    @Resource
    private OperInfoRecordDAO operInfoRecordDAO;

    /**
     * 住院就诊dao
     */
    @Resource
    private InptVisitDAO inptVisitDAO;

    /**
     * 医保项目匹配服务
     */
    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    /**
     * 医保登记服务
     */
    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    /**
     * 库存校验service
     * */
    @Resource
    private CheckStockService checkStockService_consumer;

    /**
    * @Method updateInptAdviceBatch
    * @Desrciption 批量更新医嘱
    * @param inptAdviceDTOS
    * @Author liuqi1
    * @Date   2020/9/2 15:25
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateInptAdviceBatch(List<InptAdviceDTO> inptAdviceDTOS) {
        int count = inptAdviceDAO.updateInptAdviceBatch(inptAdviceDTOS);
        return count>0;
    }

    /**
    * @Method queryOperationNameList
    * @Param [inptAdviceDetailDTO]
    * @description   查询手术名称
    * @author marong
    * @date 2020/9/21 14:57
    * @return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>
    * @throws
    */
    @Override
    public List<InptAdviceDetailDTO> queryOperationNameList(InptAdviceDetailDTO inptAdviceDetailDTO) {
        List<InptAdviceDetailDTO> inptAdviceDetailDTOS = inptAdviceDAO.queryOperationNameList(inptAdviceDetailDTO);
        return inptAdviceDetailDTOS;
    }

    /**
     * @Method queryInptVisitPage
     * @Desrciption 查询住院就诊信息
     * @param inptVisitDTO
     * @Author zengfeng
     * @Date   2020/9/27 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInptVisitPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTODTOList = inptAdviceDAO.queryInptVisitPage(inptVisitDTO);
        return PageDTO.of(inptVisitDTODTOList);
    }

    /**
     * @Method queryInptAdviceVisitID
     * @Desrciption 查询住院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    @Override
    public List<InptAdviceDTO> queryInptAdviceVisitID(InptAdviceDTO inptAdviceDTO) {
        return inptAdviceDAO.queryInptAdviceVisitID(inptAdviceDTO);
    }

    /**
     * 判断是否有联录数据，生成组号
     * @param inptAdviceDTOList
     * @return
     */
    public List<InptAdviceDTO> buildInptAdviceDTOGroupNo(List<InptAdviceDTO> inptAdviceDTOList){
        List<InptAdviceDTO> outptGroupNoList = inptAdviceDTOList;
        //组号
        int groupNo = 0;
        //组内序号
        int groupSeqNo = 1;
        if(!ListUtils.isEmpty(inptAdviceDTOList)){
            //获取最大组号
            groupNo = inptAdviceDAO.getMaxGroupNo(outptGroupNoList.get(0));
        }

        boolean isTrue = true;
        for(int i = 0; i < inptAdviceDTOList.size() ; i++){
            if(i == 0){
                outptGroupNoList.get(i).setGroupNo(0);
                outptGroupNoList.get(i).setGroupSeqNo(0);
            }else{
                //用法是否同上
                if("0".equals(outptGroupNoList.get(i).getUsageCode())){
                    //判断是否是连续联录，多条序号不需要添加
                    if(isTrue){
                        groupNo = groupNo + 1;
                        isTrue = false;
                        //序号(修改第一条序号)
                        outptGroupNoList.get(i-1).setGroupSeqNo(groupSeqNo);
                        //组号修改第一条组号)
                        outptGroupNoList.get(i-1).setGroupNo(groupNo);
                    }
                    groupSeqNo = groupSeqNo + 1;
                    outptGroupNoList.get(i).setGroupNo(groupNo);
                    //序号
                    outptGroupNoList.get(i).setGroupSeqNo(groupSeqNo);
                    //频率
                    outptGroupNoList.get(i).setRateId(outptGroupNoList.get(i-1).getRateId());
                    //执行科室
                    outptGroupNoList.get(i).setExecDeptId(outptGroupNoList.get(i-1).getExecDeptId());
                    //用药性质
                    if(StringUtils.isEmpty(outptGroupNoList.get(i).getUseCode())){
                        outptGroupNoList.get(i).setUseCode(outptGroupNoList.get(i-1).getUseCode());
                    }
                    //使用天数
                    outptGroupNoList.get(i).setUseDays(outptGroupNoList.get(i-1).getUseDays());
                    // 用法
                    outptGroupNoList.get(i).setUsageCode(outptGroupNoList.get(i-1).getUsageCode());
                    //执行次数
                    outptGroupNoList.get(i).setExecNum(outptGroupNoList.get(i-1).getExecNum());
                }else{
                    isTrue = true;
                    outptGroupNoList.get(i).setGroupNo(0);
                    outptGroupNoList.get(i).setGroupSeqNo(0);
                    groupSeqNo = 1;
                }
            }
        }
        return outptGroupNoList;
    }

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param inptAdviceDTOList 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public Boolean saveInptAdvice(List<InptAdviceDTO> inptAdviceDTOList) {
        List<InptAdviceDetailDTO> inptAdviceDetailDTOList = new ArrayList<>();
        List<InptAdviceDTO> addInptAdviceDTOList = new ArrayList<>();
        //皮试费
        List<InptAdviceDTO> addPsInptAdviceDTOList = new ArrayList<>();
        if(!ListUtils.isEmpty(inptAdviceDTOList)){
            InptVisitDTO inptVisitDTO = inptAdviceDAO.checkBalance(inptAdviceDTOList.get(0));
            if(inptVisitDTO != null){
                throw new AppException("您已欠费，不能在开医嘱,最多欠费："+inptVisitDTO.getQfCose()+"元,您当前余额："
                        + inptVisitDTO.getTotalBalance().setScale(2,BigDecimal.ROUND_HALF_UP) +"元");
            }
        }

        InptVisitDTO inptVisitDTO = inptAdviceDAO.getInptVisit(inptAdviceDTOList.get(0));
        if(!inptVisitDTO.getInDeptId().equals(inptAdviceDTOList.get(0).getDeptId())){
            throw new AppException("入院科室与开嘱科室不一致，请检查");
        }
        //获取组号
        inptAdviceDTOList = buildInptAdviceDTOGroupNo(inptAdviceDTOList);
        for(InptAdviceDTO inptAdviceDTO : inptAdviceDTOList){
            //皮试费用不重新插入
            if(StringUtils.isNotEmpty(inptAdviceDTO.getSourceIaId())){
                continue;
            }
            if(inptAdviceDTO.getLongStartTime() == null || "".equals(inptAdviceDTO.getLongStartTime())){
                inptAdviceDTO.setLongStartTime(DateUtils.getNow());
            }
            //医嘱开始时间不能小于入院时间
            if(!DateUtils.dateCompare(inptVisitDTO.getInTime(), inptAdviceDTO.getLongStartTime())){
                throw new AppException("医嘱开始时间不能小于入院时间");
            }
            if(StringUtils.isNotEmpty(inptAdviceDTO.getId())){
                InptAdviceDTO inptAdviceDTOSoure = inptAdviceDAO.checkIsCheck(inptAdviceDTO);
                if(inptAdviceDTOSoure != null && Constants.SF.S.equals(inptAdviceDTOSoure.getIsSubmit())){
                    continue;
                }else{
                    //删除
                    this.deleteInptAdvice(inptAdviceDTO);
                }
            }
            //数据校验
            this.validCfParam(inptAdviceDTO);
            //草药
            if(StringUtils.isNotEmpty(inptAdviceDTO.getBigTypeCode()) && "2".equals(inptAdviceDTO.getBigTypeCode())){
                //中药付数
                inptAdviceDTO.setHerbNum(BigDecimal.valueOf(inptAdviceDTO.getUseDays()));
            }
            //给药天数为空时默认为1
            if(inptAdviceDTO.getUseDays() == null){
                inptAdviceDTO.setUseDays(1);
            }
            //长嘱给药天数默认为1
            if(Constants.SF.F.equals(inptAdviceDTO.getIsLong())){
                inptAdviceDTO.setUseDays(1);
            }
            //主键
            inptAdviceDTO.setId(SnowflakeUtils.getId());
            //医嘱单号
            inptAdviceDTO.setOrderNo(this.getOrderNo(inptAdviceDTO.getHospCode(), Constants.ORDERRULE.YSYZ));
            //是否签名
            inptAdviceDTO.setSignCode(Constants.QMZT.WQM);
            //是否提交
            inptAdviceDTO.setIsSubmit(Constants.SF.F);
            //未停嘱
            inptAdviceDTO.setIsStop(Constants.SF.F);
            //未核收
            inptAdviceDTO.setIsCheck(Constants.SF.F);
            //是否停嘱核收
            inptAdviceDTO.setIsStopCheck(Constants.SF.F);
            //是否拒收
            inptAdviceDTO.setIsReject(Constants.SF.F);
            //是否拒收
            inptAdviceDTO.setIsPrint(Constants.SF.F);


            //是否皮试
            if(StringUtils.isEmpty(inptAdviceDTO.getIsSkin())){
                //是否皮试
                inptAdviceDTO.setIsSkin(Constants.SF.F);
            }
            //住院医嘱明细
            inptAdviceDetailDTOList.addAll(this.buildInptAdviceExecDTO(inptAdviceDTO));
            addInptAdviceDTOList.add(inptAdviceDTO);
        }
        if(!ListUtils.isEmpty(addInptAdviceDTOList)){
            for(InptAdviceDTO inptAdviceDTO : addInptAdviceDTOList){
                //判断是否需要皮试
                if(StringUtils.isNotEmpty(inptAdviceDTO.getIsSkin()) && Constants.SF.S.equals(inptAdviceDTO.getIsSkin())){
                    InptAdviceDTO inptAdviceDTOPs  = this.buildPsfy(inptAdviceDTO.getHospCode(), inptAdviceDTO);
                    if(inptAdviceDTOPs != null){
                        addPsInptAdviceDTOList.add(inptAdviceDTOPs);
                        //获取换药药品明细
                        if(StringUtils.isNotEmpty(inptAdviceDTO.getSkinDurgId())){
                            //皮试换药（换药的医嘱ID跟皮试费用的）
                            inptAdviceDetailDTOList.add(this.getPsHyFy(inptAdviceDTO, inptAdviceDTOPs.getId()));
                        }
                        //住院医嘱明细
                        inptAdviceDetailDTOList.addAll(this.buildInptAdviceExecDTO(inptAdviceDTOPs));
                    }
                }
            }
            //添加皮试费用
            addInptAdviceDTOList.addAll(addPsInptAdviceDTOList);
            //保存住院医嘱
            inptAdviceDAO.insertBathInptAdvice(addInptAdviceDTOList);
            //保存住院医嘱明细
            if (!ListUtils.isEmpty(inptAdviceDetailDTOList)) {
                inptAdviceDAO.insertBathInptAdviceDetail(inptAdviceDetailDTOList);
            }
            for(InptAdviceDTO inptAdviceDTO : addInptAdviceDTOList) {
                if(inptAdviceDTO.getOperInfoRecordDTO()!= null) {
                    OperInfoRecordDTO operInfoRecordDTO = inptAdviceDTO.getOperInfoRecordDTO();
                    operInfoRecordDTO.setId(SnowflakeUtils.getId());
                    operInfoRecordDTO.setHospCode(inptAdviceDTO.getHospCode());
                    operInfoRecordDTO.setVisitId(inptAdviceDTO.getVisitId());
                    operInfoRecordDTO.setBabyId(inptAdviceDTO.getBabyId());
                    operInfoRecordDTO.setInNo(inptAdviceDTO.getInNo());
                    operInfoRecordDTO.setAdviceId(inptAdviceDTO.getId());
                    operInfoRecordDTO.setGenderCode(inptAdviceDTO.getGenderCode());
                    // 医嘱未核收时状态手术状态都默认为-2未审核状态
                    operInfoRecordDTO.setStatusCode(Constants.OPER_STATUS_CHANGE.MEDICAL_ORDERS_WRITENED_NOT_BEEN_CHECKED);

                    operInfoRecordDAO.insertSurgery(operInfoRecordDTO);
                }
            }
        }
        return false;
    }

    /**
     * @Method updateInptAdviceIsSubmit
     * @Desrciption 提交医嘱
     * @param inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public boolean updateInptAdviceIsSubmit(InptAdviceTDTO inptAdviceTDTO) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setVisitId(inptAdviceTDTO.getVisitId());
        inptAdviceDTO.setHospCode(inptAdviceTDTO.getHospCode());
        InptVisitDTO inptVisitDTO = inptAdviceDAO.getInptVisit(inptAdviceDTO);
        if(!inptVisitDTO.getInDeptId().equals(inptAdviceTDTO.getDeptId())){
            throw new AppException("入院科室与开嘱科室不一致，请检查");
        }
        if(!ListUtils.isEmpty(inptAdviceTDTO.getInptAdviceDTOList())){
            //批量更新停嘱
            inptAdviceDAO.updateBatchAdvice(inptAdviceTDTO.getInptAdviceDTOList());
        }
        //提交医嘱
        inptAdviceDAO.updateInptAdviceBatchSumbit(inptAdviceTDTO);

        InptAdvicePrintDTO inptAdvicePrintDTO = new InptAdvicePrintDTO();
        inptAdvicePrintDTO.setHospCode(inptAdviceTDTO.getHospCode());
        inptAdvicePrintDTO.setVisitId(inptAdviceTDTO.getVisitId());
        inptAdvicePrintDTO.setIds1(inptAdviceTDTO.getIds());
        // 根据提交的id查询医嘱主表,转化为医嘱打印信息,新增信息
        List<InptAdvicePrintDTO> insetAdvicePrint= inptPrintDAO.queryAdviceByVistiId(inptAdvicePrintDTO);
        if(ListUtils.isEmpty(insetAdvicePrint)) {
              throw new AppException("未获取到医嘱信息");
          }
        // 查询改患者在打印表中有没有数据
        List<InptAdvicePrintDTO> oldAdvicePrintDTOS = inptPrintDAO.queryAdvicePrintByVisitId(inptAdvicePrintDTO);
        // 根据就诊id查询该患者在打印表中的医嘱最大序号
        Integer seqNo = inptPrintDAO.queryMaxSeqNoByVisit(inptAdvicePrintDTO);
        // 如果有则序号递增
        if(!ListUtils.isEmpty(oldAdvicePrintDTOS)) {
          for (InptAdvicePrintDTO item: insetAdvicePrint){
            item.setId(SnowflakeUtils.getId());
            item.setIsValid("1");
            item.setSeqNo(++seqNo);
          }
        } else {
          // 没有则默认序号从0递增
          for (int i = 0; i < insetAdvicePrint.size(); i++) {
            insetAdvicePrint.get(i).setId(SnowflakeUtils.getId());
            insetAdvicePrint.get(i).setIsValid("1");
            insetAdvicePrint.get(i).setSeqNo(i);
          }
        }
        return inptPrintDAO.insertAdvicePrint(insetAdvicePrint) > 0;
    }

    @Override
    public List<Map<String, Object>> checkStock(InptAdviceDTO inptAdviceDTO) {
        if(inptAdviceDTO == null) {
            throw new AppException("参数为空");
        }
        //校验参数
        if(cn.hsa.util.StringUtils.isEmpty(inptAdviceDTO.getItemId())) {
            throw new AppException("项目ID不能为空");
        }
        if(cn.hsa.util.StringUtils.isEmpty(inptAdviceDTO.getPharId())) {
            throw new AppException("库房ID不能为空");
        }
        if(cn.hsa.util.StringUtils.isEmpty(inptAdviceDTO.getHospCode())) {
            throw new AppException("医院编码不能为空");
        }
        return inptAdviceDAO.checkStock(inptAdviceDTO);
    }

    /**
     * @Menthod validCfParam
     * @Desrciption  校验数据
     * @param inptAdviceDTO ：主表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptVisitDTO
     **/
    private void validCfParam(InptAdviceDTO inptAdviceDTO) {
        // 参数校验
        if (StringUtils.isEmpty(inptAdviceDTO.getItemCode())) {
            throw new AppException(inptAdviceDTO.getItemName()+":项目类型不能为空");
        }
        // 参数校验
        if (StringUtils.isEmpty(inptAdviceDTO.getExecDeptId())) {
            throw new AppException(inptAdviceDTO.getItemName()+":执行科室不能为空");
        }
        // 参数校验
        if (inptAdviceDTO.getNum() == null) {
            throw new AppException(inptAdviceDTO.getItemName()+":数量不能为空");
        }
        // 参数校验
        if (inptAdviceDTO.getTotalNum() == null) {
            throw new AppException(inptAdviceDTO.getItemName()+":总数量不能为空");
        }
        //药品
        if (Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode()) && StringUtils.isEmpty(inptAdviceDTO.getRateId())) {
            throw new AppException(inptAdviceDTO.getItemName()+":频率不能为空");
        }
        //药品
        if (Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode()) && StringUtils.isEmpty(inptAdviceDTO.getUsageCode())) {
            throw new AppException(inptAdviceDTO.getItemName()+":用法不能为空");
        }
        if (Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode()) || Constants.XMLB.CL.equals(inptAdviceDTO.getItemCode())){
            if(Constants.YYXZ.CG.equals(inptAdviceDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(inptAdviceDTO.getUseCode())){
                //医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔
                String wjsykc = this.getSysParameter(inptAdviceDTO.getHospCode() , "MZYS_CF_WJSFYKC");
                inptAdviceDTO.setWjsykc(wjsykc);
                // add by zhangguorui
                Map map = new HashMap();
                map.put("hospCode", inptAdviceDTO.getHospCode());
                CheckStockDTO checkStockDTO = new CheckStockDTO();
                BeanUtils.copyProperties(inptAdviceDTO,checkStockDTO);
                map.put("checkStockDTO", checkStockDTO);
                // 调用住院库存校验接口 获得 库存-占存-在途数量 等于可操作的数量
                WrapperResponse<CheckStockRespDTO> checkResult = checkStockService_consumer.checkOutDrugOrMeterialStock(map);
                CheckStockRespDTO checkStockRespDTO = checkResult.getData();
                //判断库存
                if (ListUtils.isEmpty(inptAdviceDAO.checkStock(inptAdviceDTO))
                        || checkStockRespDTO.getResult().compareTo(new BigDecimal(0)) < 0) {
                    throw new AppException(inptAdviceDTO.getItemName() + ":库存不足," +
                            "其中【库存数量 = " + checkStockRespDTO.getStrockSplitNum() + "】，" +
                            "【占用库存 = " + checkStockRespDTO.getStockOccupy() + "】，" +
                            "【未结算/未核收数量 = " + BigDecimalUtils.add(checkStockRespDTO.getTotalNumberNoCaculate(),
                            checkStockRespDTO.getTotalNumberNoCheck()) + "】，" +
                            "【未配药数量 =" + BigDecimalUtils.add(checkStockRespDTO.getPrescribeOuptNumber(),
                            checkStockRespDTO.getPrescribeInptNumber()) + "】"
                    );
                }
            }
        }
    }

    /**
     * @Menthod buildPsfy
     *
     * @Desrciption  生成皮试费用
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public InptAdviceDTO buildPsfy(String hospCode , InptAdviceDTO inptAdviceDTO){
        InptAdviceDTO inptAdviceDTOPs = new InptAdviceDTO();
        //获取系统参数皮试医嘱
        String psyzmlCode = this.getSysParameter(hospCode , "PS_YZML");
        //获取皮试用法
        String psyyfs = this.getSysParameter(hospCode , "PS_YYFS");
        //未找到皮试医嘱目录,则这里不处理;
        if(StringUtils.isEmpty(psyzmlCode)){
            return null;
        }
        inptAdviceDTOPs = this.getPsfy(psyzmlCode, inptAdviceDTO);
        inptAdviceDTOPs.setUsageCode(psyyfs);
        return inptAdviceDTOPs;
    }

    /**
     * @Menthod getPsfy
     * @Desrciption  获取皮试费用
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public InptAdviceDTO getPsfy(String psyzmlCode, InptAdviceDTO inptAdviceDTO){
        InptAdviceDTO inptAdviceDTOPs = new InptAdviceDTO();
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        //医嘱编码
        baseDrugDTO.setCode(psyzmlCode);
        //医院编码
        baseDrugDTO.setHospCode(inptAdviceDTO.getHospCode());
        //获取医嘱信息
        BaseAdviceDTO baseAdviceDTO = inptAdviceDAO.getAdviceData(baseDrugDTO);
        if(baseAdviceDTO == null){
            return null;
        }
        //主键
        inptAdviceDTOPs.setId(SnowflakeUtils.getId());
        //医院编码
        inptAdviceDTOPs.setHospCode(inptAdviceDTO.getHospCode());
        //就诊ID
        inptAdviceDTOPs.setVisitId(inptAdviceDTO.getVisitId());
        //医嘱单号
        inptAdviceDTOPs.setOrderNo(this.getOrderNo(inptAdviceDTO.getHospCode(), Constants.ORDERRULE.YZ));
        //就诊/住院科室
        inptAdviceDTOPs.setInDeptId(inptAdviceDTO.getInDeptId());
        //执行科室
        inptAdviceDTOPs.setExecDeptId(baseAdviceDTO.getOutDeptId());
        //开嘱科室
        inptAdviceDTOPs.setDeptId(inptAdviceDTO.getDeptId());
        //医嘱组号
        inptAdviceDTOPs.setGroupNo(0);
        //医嘱组内序号
        inptAdviceDTOPs.setGroupSeqNo(0);
        //医嘱分类代码
        inptAdviceDTOPs.setTypeCode(baseAdviceDTO.getTypeCode());
        //签名状态
        inptAdviceDTOPs.setSignCode(Constants.QMZT.WQM);
        //开嘱当日执行次数
        inptAdviceDTOPs.setStartExecNum(1);
        //项目类型代码
        inptAdviceDTOPs.setItemCode(baseAdviceDTO.getItemCode());
        //项目ID
        inptAdviceDTOPs.setItemId(baseAdviceDTO.getId());
        //项目名称
        inptAdviceDTOPs.setItemName(baseAdviceDTO.getName()+"("+inptAdviceDTO.getItemName()+")");
        //项目名称
        inptAdviceDTOPs.setContent(baseAdviceDTO.getName()+"("+inptAdviceDTO.getItemName()+")");
        //数量
        inptAdviceDTOPs.setNum(BigDecimal.valueOf(1));
        //总数量
        inptAdviceDTOPs.setTotalNum(BigDecimal.valueOf(1));
        //单位
        inptAdviceDTOPs.setUnitCode(baseAdviceDTO.getUnitCode());
        //单位
        inptAdviceDTOPs.setTotalNumUnitCode(baseAdviceDTO.getUnitCode());
        //单价
        inptAdviceDTOPs.setPrice(baseAdviceDTO.getPrice());
        //总价格
        inptAdviceDTOPs.setTotalPrice(baseAdviceDTO.getPrice());
        //是否长嘱
        inptAdviceDTOPs.setIsLong(Constants.SF.S);
        //是否停嘱
        inptAdviceDTOPs.setIsStop(Constants.SF.F);
        //创建人
        inptAdviceDTOPs.setCrteId(inptAdviceDTO.getCrteId());
        //创建人
        inptAdviceDTOPs.setCrteName(inptAdviceDTO.getCrteName());
        //医嘱开始时间
        inptAdviceDTOPs.setLongStartTime(inptAdviceDTO.getLongStartTime());
        //用药天数
        inptAdviceDTOPs.setUseDays(1);
        //皮试来源ID
        inptAdviceDTOPs.setSourceIaId(inptAdviceDTO.getId());
        inptAdviceDTOPs.setIsSubmit(Constants.SF.F);
        return inptAdviceDTOPs;
    }

    /*
     * @Menthod getPsfy
     * @Desrciption  获取皮试换药费用
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     */
    public InptAdviceDetailDTO getPsHyFy(InptAdviceDTO inptAdviceDTO, String psfID){
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setItemId(inptAdviceDTO.getSkinDurgId());

        baseDrugDTO.setHospCode(inptAdviceDTO.getHospCode());

        BaseDrugDTO baseDrug = inptAdviceDAO.getBaseDrug(baseDrugDTO);

        InptAdviceDetailDTO inptAdviceDetailDTO = new InptAdviceDetailDTO();
        //主键
        inptAdviceDetailDTO.setId(SnowflakeUtils.getId());
        //医院编码
        inptAdviceDetailDTO.setHospCode(inptAdviceDTO.getHospCode());
        //就诊ID
        inptAdviceDetailDTO.setVisitId(inptAdviceDTO.getVisitId());
        //住院医嘱ID
        inptAdviceDetailDTO.setIaId(psfID);
        //医嘱组号
        inptAdviceDetailDTO.setIaGroupNo(inptAdviceDTO.getGroupNo());
        //财务分类
        inptAdviceDetailDTO.setBfcId(baseDrug.getBfcId());
        //项目类型代码
        inptAdviceDetailDTO.setItemCode(Constants.XMLB.YP);
        //项目ID
        inptAdviceDetailDTO.setItemId(baseDrug.getId());
        //项目名称
        inptAdviceDetailDTO.setItemName(baseDrug.getGoodName());
        //单价
        inptAdviceDetailDTO.setPrice(baseDrug.getPrice());
        //总金额
        inptAdviceDetailDTO.setTotalPrice(baseDrug.getPrice());
        //数量
        inptAdviceDetailDTO.setNum(BigDecimal.valueOf(1));
        //数量单位
        inptAdviceDetailDTO.setUnitCode(baseDrug.getUnitCode());
        //用药性质代码（YYXZ）
        inptAdviceDetailDTO.setUseCode(baseDrug.getUseCode());
        //费用来源
        inptAdviceDetailDTO.setSourceCode(Constants.FYLYFS.PSHYYP);
        return inptAdviceDetailDTO;
    }

    /**
     * @Method 获取系统参数值
     * @Description
     *
     * @Param
     *
     * @Author zengfeng
     * @Date 2020/9/28 21:05
     * @Return
     **/
    private String getSysParameter(String hospCode, String code) {
        Map paramMap = new HashMap();
        paramMap.put("hospCode", hospCode);
        paramMap.put("code", code);
        return sysParameterService_consumer.getParameterByCode(paramMap).getData().getValue();
    }


    /**
     * @Menthod buildInptAdviceExecDTO
     * @Desrciption  医嘱明细
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<InptAdviceExecDTO>
     **/
    public List<InptAdviceDetailDTO> buildInptAdviceExecDTO(InptAdviceDTO inptAdviceDTO) {
        List<InptAdviceDetailDTO> inptAdviceDetailDTOList = new ArrayList<>();
        // 1:药品 2：材料 3：项目 4：医嘱目录
        if (Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode()) ||  Constants.XMLB.CL.equals(inptAdviceDTO.getItemCode()) || Constants.XMLB.XM.equals(inptAdviceDTO.getItemCode())) {
            InptAdviceDetailDTO inptAdviceDetailDTO = new InptAdviceDetailDTO();
            //主键
            inptAdviceDetailDTO.setId(SnowflakeUtils.getId());
            //医院编码
            inptAdviceDetailDTO.setHospCode(inptAdviceDTO.getHospCode());
            //就诊ID
            inptAdviceDetailDTO.setVisitId(inptAdviceDTO.getVisitId());
            //住院医嘱ID
            inptAdviceDetailDTO.setIaId(inptAdviceDTO.getId());
            //医嘱目录ID
            inptAdviceDetailDTO.setBaId(null);
            //财务分类
            inptAdviceDetailDTO.setBfcId(inptAdviceDTO.getBfcId());
            //医嘱组号
            inptAdviceDetailDTO.setIaGroupNo(inptAdviceDTO.getGroupNo());
            //项目类型代码
            inptAdviceDetailDTO.setItemCode(inptAdviceDTO.getItemCode());
            //项目ID
            inptAdviceDetailDTO.setItemId(inptAdviceDTO.getItemId());
            //项目名称
            inptAdviceDetailDTO.setItemName(inptAdviceDTO.getItemName());
            //项目数量
            inptAdviceDetailDTO.setNum(inptAdviceDTO.getNum());
            //项目数量单位
            inptAdviceDetailDTO.setUnitCode(inptAdviceDTO.getUnitCode());

            BaseDrugDTO baseDrugMap = new BaseDrugDTO();
            baseDrugMap.setHospCode(inptAdviceDTO.getHospCode());
            baseDrugMap.setItemId(inptAdviceDTO.getItemId());
            if(Constants.XMLB.YP.equals(inptAdviceDTO.getItemCode())){
                BaseDrugDTO baseDrugDTO = inptAdviceDAO.getBaseDrug(baseDrugMap);
                if(inptAdviceDTO.getUnitCode().equals(baseDrugDTO.getSplitUnitCode())){
                    //项目单价
                    inptAdviceDetailDTO.setPrice(baseDrugDTO.getSplitPrice());
                    //项目总金额
                    inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(baseDrugDTO.getSplitPrice(), inptAdviceDTO.getNum()));
                }else{
                    //项目单价
                    inptAdviceDetailDTO.setPrice(baseDrugDTO.getPrice());
                    //项目总金额
                    inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(baseDrugDTO.getPrice(), inptAdviceDTO.getNum()));
                }
            }else if(Constants.XMLB.CL.equals(inptAdviceDTO.getItemCode())){
                BaseMaterialDTO baseMaterialDTO = inptAdviceDAO.getBaseMaterial(baseDrugMap);
                if(inptAdviceDTO.getUnitCode().equals(baseMaterialDTO.getSplitUnitCode())){
                    //项目单价
                    inptAdviceDetailDTO.setPrice(baseMaterialDTO.getSplitPrice());
                    //项目总金额
                    inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(baseMaterialDTO.getSplitPrice(), inptAdviceDTO.getNum()));
                }else{
                    //项目单价
                    inptAdviceDetailDTO.setPrice(baseMaterialDTO.getPrice());
                    //项目总金额
                    inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(baseMaterialDTO.getPrice(), inptAdviceDTO.getNum()));
                }
            }else{
                //项目单价
                inptAdviceDetailDTO.setPrice(inptAdviceDTO.getPrice());
                //项目总金额
                inptAdviceDetailDTO.setTotalPrice(inptAdviceDTO.getTotalPrice());
            }
            //用药性质代码
            inptAdviceDetailDTO.setUseCode(inptAdviceDTO.getUseCode());
            //来源方式代码
            inptAdviceDetailDTO.setSourceCode(Constants.FYLYFS.YZ);
            inptAdviceDetailDTOList.add(inptAdviceDetailDTO);
        }else if(Constants.XMLB.YZML.equals(inptAdviceDTO.getItemCode())){
            List<BaseItemDTO> baseItemList = inptAdviceDAO.getAdviceDetail(inptAdviceDTO);
            for(BaseItemDTO baseItemDTO : baseItemList){
                InptAdviceDetailDTO inptAdviceDetailDTO = new InptAdviceDetailDTO();
                //主键
                inptAdviceDetailDTO.setId(SnowflakeUtils.getId());
                //医院编码
                inptAdviceDetailDTO.setHospCode(inptAdviceDTO.getHospCode());
                //就诊ID
                inptAdviceDetailDTO.setVisitId(inptAdviceDTO.getVisitId());
                //财务分类
                inptAdviceDetailDTO.setBfcId(baseItemDTO.getBfcId());
                //住院医嘱ID
                inptAdviceDetailDTO.setIaId(inptAdviceDTO.getId());
                //医嘱目录ID
                inptAdviceDetailDTO.setBaId(inptAdviceDTO.getItemId());
                //医嘱组号
                inptAdviceDetailDTO.setIaGroupNo(inptAdviceDTO.getGroupNo());
                //项目类型代码
                inptAdviceDetailDTO.setItemCode(baseItemDTO.getTypeCode());
                //项目ID
                inptAdviceDetailDTO.setItemId(baseItemDTO.getId());
                //项目名称
                inptAdviceDetailDTO.setItemName(baseItemDTO.getName());
                //项目数量
                inptAdviceDetailDTO.setNum(BigDecimalUtils.multiply(baseItemDTO.getNum(),inptAdviceDTO.getNum()));
                //项目数量单位
                inptAdviceDetailDTO.setUnitCode(baseItemDTO.getUnitCode());
                //项目单价
                inptAdviceDetailDTO.setPrice(baseItemDTO.getPrice());
                //项目总金额
                inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(baseItemDTO.getPrice(),inptAdviceDetailDTO.getNum()));
                //用药性质代码(默认科室自备，医嘱不为空的情况以医嘱为主，其次就是医嘱目录详情)
                inptAdviceDetailDTO.setUseCode(Constants.YYXZ.KSZB);
                //材料中的用药性质
                if(StringUtils.isNotEmpty(baseItemDTO.getUseCode())){
                    inptAdviceDetailDTO.setUseCode(baseItemDTO.getUseCode());
                }
                //医嘱中的用药性质
                if(StringUtils.isNotEmpty(inptAdviceDTO.getUseCode())){
                    inptAdviceDetailDTO.setUseCode(inptAdviceDTO.getUseCode());
                }
                //来源方式代码
                inptAdviceDetailDTO.setSourceCode(Constants.FYLYFS.YZ);
                inptAdviceDetailDTOList.add(inptAdviceDetailDTO);
            }
        }
        return inptAdviceDetailDTOList;
    }

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public Boolean deleteInptAdvice(InptAdviceDTO inptAdviceDTO) {
        //批量删除
        if(StringUtils.isNotEmpty(inptAdviceDTO.getIatIds())){
            this.deleteInptAdviceBath(inptAdviceDTO);
        }else{
            InptAdviceDTO inptAdviceDTOSoure = inptAdviceDAO.checkIsCheck(inptAdviceDTO);
            if(inptAdviceDTOSoure != null && Constants.SF.S.equals(inptAdviceDTOSoure.getIsCheck())){
                throw new AppException("医嘱已核收，不能删除");
            }
            //删除医嘱
            inptAdviceDAO.deleteInptAdvice(inptAdviceDTO);
            //删除医嘱明细
            inptAdviceDAO.deleteInptAdviceDetail(inptAdviceDTO);
            //是否存在皮试费用
            if(StringUtils.isNotEmpty(inptAdviceDTOSoure.getIsSkin()) && Constants.SF.S.equals(inptAdviceDTOSoure.getIsSkin())){
                //删除医嘱(皮试费用)
                inptAdviceDAO.deleteInptAdvicePs(inptAdviceDTOSoure);
                //删除医嘱明细(皮试费用)
                inptAdviceDAO.deleteInptAdviceDetailPs(inptAdviceDTOSoure);
            }
            // 医嘱打印信息删除
            InptAdvicePrintDTO inptAdvicePrintDTO = new InptAdvicePrintDTO();
            inptAdvicePrintDTO.setHospCode(inptAdviceDTO.getHospCode());
            inptAdvicePrintDTO.setIaId(inptAdviceDTO.getId());
            // 删除医嘱打印表信息
            inptPrintDAO.deleteAdvicePrintByIaid(inptAdvicePrintDTO);
        }
        return true;
    }

    /**
     * 批量删除
     * @param inptAdviceDTO
     * @return
     */
    public Boolean deleteInptAdviceBath(InptAdviceDTO inptAdviceDTO) {
        String[] ids = inptAdviceDTO.getIatIds().split(",");
        for(String id : ids){
            InptAdviceDTO inptAdvice = new InptAdviceDTO();
            inptAdvice.setId(id);
            inptAdvice.setHospCode(inptAdviceDTO.getHospCode());
            InptAdviceDTO inptAdviceDTOSoure = inptAdviceDAO.checkIsCheck(inptAdvice);
            if(inptAdviceDTOSoure == null){
                return true;
            }
            if(inptAdviceDTOSoure != null && Constants.SF.S.equals(inptAdviceDTOSoure.getIsCheck())){
                throw new AppException("医嘱已核收，不能删除");
            }
            //删除医嘱
            inptAdviceDAO.deleteInptAdvice(inptAdvice);
            //删除医嘱明细
            inptAdviceDAO.deleteInptAdviceDetail(inptAdvice);
            //是否存在皮试费用
            if(StringUtils.isNotEmpty(inptAdviceDTOSoure.getIsSkin()) && Constants.SF.S.equals(inptAdviceDTOSoure.getIsSkin())){
                //删除医嘱(皮试费用)
                inptAdviceDAO.deleteInptAdvicePs(inptAdviceDTOSoure);
                //删除医嘱明细(皮试费用)
                inptAdviceDAO.deleteInptAdviceDetailPs(inptAdviceDTOSoure);
            }
            // 医嘱打印信息删除
            InptAdvicePrintDTO inptAdvicePrintDTO = new InptAdvicePrintDTO();
            inptAdvicePrintDTO.setHospCode(inptAdviceDTO.getHospCode());
            inptAdvicePrintDTO.setIaId(id);
            // 删除医嘱打印表信息
            inptPrintDAO.deleteAdvicePrintByIaid(inptAdvicePrintDTO);
        }
        return true;
    }

    /**
     * @Method updateBatchInptAdviceStop
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public Boolean updateBatchInptAdviceStop(InptAdviceDTO inptAdviceDTO) {
        this.checkAdvice("1", inptAdviceDTO);
        return inptAdviceDAO.updateBatchInptAdviceStop(inptAdviceDTO) > 0;
    }

    /**
     * @Method updateBatchInptAdviceplan
     * @Desrciption 批量更新医嘱预停时间
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public Boolean updateBatchInptAdviceplan(InptAdviceDTO inptAdviceDTO) {
        this.checkAdvice("2", inptAdviceDTO);
        return inptAdviceDAO.updateBatchInptAdviceplan(inptAdviceDTO) > 0;
    }

    /**
     * @Menthod getOrderNo
     * @Desrciption  生成规则单据号
     * @param hospCode 医院编码
     * @param typeCode 规则标志Code
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return java.lang.String 单据号
     */
    private String getOrderNo(String hospCode,String typeCode){
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",hospCode);
        param.put("typeCode",typeCode);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        return orderNo.getData();
    }

    /**
     * 判断是否执行
     * @param flag 1:停嘱 2：预停
     * @param inptAdviceDTO
     * @return
     */
    public boolean checkAdvice(String flag, InptAdviceDTO inptAdviceDTO){
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.queryAll(inptAdviceDTO);
        List<InptAdviceDTO> checkInptAdviceList = new ArrayList();
        checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.F.equals(s.getIsCheck())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptAdviceList)){
            throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱未核收，不能操作");
        }
        checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.S.equals(s.getIsLong())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptAdviceList)){
            throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱为临时医嘱，不能操作");
        }
        checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> StringUtils.isNotEmpty(s.getIsReject()) && Constants.SF.S.equals(s.getIsReject())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptAdviceList)){
            throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱已拒收，不能操作");
        }
        checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.S.equals(s.getIsStopCheck())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptAdviceList)){
            throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱已停嘱核收，不能操作");
        }
        //停嘱
        if("1".equals(flag)){
            if("1".equals(inptAdviceDTO.getType())){
                checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.S.equals(s.getIsStop())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(checkInptAdviceList)){
                    throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱已停嘱，不能停嘱");
                }
            }else if("2".equals(inptAdviceDTO.getType())){
                checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.F.equals(s.getIsStop())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(checkInptAdviceList)){
                    throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱未停嘱，不能取消停嘱");
                }
            }
        }
        //预停
        else if("2".equals(flag)){
            checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.S.equals(s.getIsStop())).collect(Collectors.toList());
            if(!ListUtils.isEmpty(checkInptAdviceList)){
                throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱已停嘱，不能预停");
            }
            if("1".equals(inptAdviceDTO.getType())){


                checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> DateUtils.dateCompare(inptAdviceDTO.getPlanStopTime(), s.getLongStartTime())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(checkInptAdviceList)){
                    throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱预停时间不能小于医嘱开始时间");
                }
                checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> s.getPlanStopTime() != null ).collect(Collectors.toList());
                if(!ListUtils.isEmpty(checkInptAdviceList)){
                    throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱已预停，不能预停");
                }
            }else if("2".equals(inptAdviceDTO.getType())){
                checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> s.getPlanStopTime() == null ).collect(Collectors.toList());
                if(!ListUtils.isEmpty(checkInptAdviceList)){
                    throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱未预停，不能取消预停");
                }
            }
        }
        return true;
    }

    /**
     * @Menthod getInptDiagnose
     * @Desrciption  获取诊断信息
     * @param inptVisitDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<InptDiagnoseDTO>
     **/
    @Override
    public List<InptDiagnoseDTO> getInptDiagnose(InptVisitDTO inptVisitDTO) {
        return inptAdviceDAO.getInptDiagnose(inptVisitDTO);
    }

    /**
     * @param inptVisitDTO
     * @Method queryInptDiagnose
     * @Desrciption 查询医保病人的诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/11 19:42
     * @Return
     */
    @Override
    public List<InptDiagnoseDTO> queryInptDiagnose(InptVisitDTO inptVisitDTO) {
        return inptAdviceDAO.queryInptDiagnose(inptVisitDTO);
    }

    /**
     * @Menthod saveInptDiagnose
     * @Desrciption  保存诊断信息
     * @param inptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public boolean saveInptDiagnose(InptDiagnoseDTO inptDiagnoseDTO) {
        if(inptDiagnoseDTO == null || inptDiagnoseDTO.getInptDiagnoseDTOList() == null){
            throw new AppException("诊断信息不能为空");
        }
        for(InptDiagnoseDTO inptDiagnose : inptDiagnoseDTO.getInptDiagnoseDTOList()){
            inptDiagnose.setHospCode(inptDiagnoseDTO.getHospCode());
            inptDiagnose.setVisitId(inptDiagnoseDTO.getVisitId());
            inptDiagnose.setId(SnowflakeUtils.getId());
            inptDiagnose.setCrteName(inptDiagnoseDTO.getCrteName());
            inptDiagnose.setCrteId(inptDiagnoseDTO.getCrteId());
        }
        //删除全部诊断
        inptAdviceDAO.deleteDiagnose(inptDiagnoseDTO);
        //新增全部诊断
        inptAdviceDAO.insertDiagnose(inptDiagnoseDTO.getInptDiagnoseDTOList());
        return true;
    }

    /**
     * @Method updateBatchInptAdviceCancel
     * @Desrciption 批量取消
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public Boolean updateBatchInptAdviceCancel(InptAdviceDTO inptAdviceDTO) {
        List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.queryAll(inptAdviceDTO);
        List<InptAdviceDTO> checkInptAdviceList = new ArrayList();
        //长期医嘱
        checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.F.equals(s.getIsLong())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptAdviceList)){
            throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱为长期医嘱，不能取消");
        }
        //是否核收医嘱
        checkInptAdviceList = inptAdviceDTOList.stream().filter(s-> Constants.SF.F.equals(s.getIsCheck())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptAdviceList)){
            throw new AppException(checkInptAdviceList.get(0).getContent()+"医嘱未核收，不能取消");
        }
        //费用信息
        List<InptCostDTO> inptCostDTOList = inptAdviceDAO.queryInptCostList(inptAdviceDTO);
        List<InptCostDTO> checkInptCostDTOList = new ArrayList();
        //正常状态，必须冲账
        checkInptCostDTOList = inptCostDTOList.stream().filter(s-> Constants.ZTBZ.ZC.equals(s.getStatusCode())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptCostDTOList)){
            throw new AppException(checkInptCostDTOList.get(0).getItemName()+"未退费，不能取消");
        }
        //已退费，并且需要确认退费
        checkInptCostDTOList = inptCostDTOList.stream().filter(s-> Constants.ZTBZ.CH.equals(s.getStatusCode()) && "0".equals(s.getIsOk())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(checkInptCostDTOList)){
            throw new AppException(checkInptCostDTOList.get(0).getItemName()+"未确认退费，不能取消");
        }
        // item_code = 4 处置 type_code=5手术
        List<InptAdviceDTO> operAdviceList = inptAdviceDTOList.stream().filter(s -> "4".equals(s.getItemCode())&& "5".equals(s.getTypeCode())).collect(Collectors.toList());
        // 取消医嘱时也要取消手术
        if(!ListUtils.isEmpty(operAdviceList)){
            Map map = new HashMap();
            map.put("hospCode",operAdviceList.get(0).getHospCode());
            OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
            operInfoRecordDTO.setPageSize(1000);
            operInfoRecordDTO.setPageNo(0);
            operInfoRecordDTO.setAdviceId(operAdviceList.get(0).getId());
            List<OperInfoRecordDTO> operInfoRecordDTOs  = operInfoRecordDAO.queryOperInfoRecordList(operInfoRecordDTO);
            // 已安排未完成的手术列表
            List<OperInfoRecordDTO> unCompleteOperList = operInfoRecordDTOs.stream().filter(o -> "2".equals(o.getStatusCode())).collect(Collectors.toList());
            // 已申请的手术列表
            List<OperInfoRecordDTO> applyUnScheduledList = operInfoRecordDTOs.stream().filter(o -> "0".equals(o.getStatusCode())).collect(Collectors.toList());
            if(!ListUtils.isEmpty(unCompleteOperList)){
                throw new AppException("存在已安排未完成的手术 "+unCompleteOperList.get(0).getContent()+" 请检查！");
            }
            // 获取当前医嘱对应的已申请未安排手术并将其取消
            if(!ListUtils.isEmpty(applyUnScheduledList)){
                OperInfoRecordDTO dto = null;
                for (OperInfoRecordDTO operInfoRecordDto:applyUnScheduledList) {
                    dto = new OperInfoRecordDTO();
                    dto.setId(operInfoRecordDto.getId());
                    dto.setHospCode(operInfoRecordDto.getHospCode());
                    dto.setStatusCode("-1");
                    operInfoRecordDAO.updateSurgeryStatus(dto);
                }
            }
        }
        return inptAdviceDAO.updateBatchInptAdviceCancel(inptAdviceDTO) > 0;
    }


    /**
     * @Method saveInptAdvice
     * @Desrciption
     * 保存中草药医嘱（中草药医嘱录入界面为明细数据,医嘱主界面为主表数据）
     * 1.判断费用是否满足开医嘱需求
     * 2.根据visitId判断开医嘱科室跟入院科室一样
     * 3.判断是否有修改的数据,  根据iaId,找到对应的医嘱主表，如果提交标志判断,有修改的数据就删除数据，否则提示不能修改
     * 2.
     * @param parmMap 医嘱明细信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public Boolean saveInptAdviceZCY(Map<String,Object> parmMap) {

        //中草药医嘱
        List<InptAdviceDTO> inptAdviceList = (List<InptAdviceDTO>) parmMap.get("inptAdviceList");
        //中草药医嘱信息
        InptAdviceDTO inptAdviceDTO = (InptAdviceDTO) parmMap.get("inptAdviceDTO");

        //就诊病人信息
        InptVisitDTO inptVisitDTO = null;

        //判断是否符合开处方要求
        if (!ListUtils.isEmpty(inptAdviceList)) {
            InptAdviceDTO parmInptAdvice = new InptAdviceDTO();
            parmInptAdvice.setVisitId(inptAdviceDTO.getId());
            parmInptAdvice.setHospCode(inptAdviceDTO.getHospCode());
            inptVisitDTO = inptAdviceDAO.checkBalance(parmInptAdvice);
            if (inptVisitDTO != null) {
                throw new AppException("您已欠费，不能在开医嘱,最多欠费：" + inptVisitDTO.getQfCose() + "元,您当前余额：" + inptVisitDTO.getTotalBalance().setScale(2,BigDecimal.ROUND_HALF_UP)+ "元");
            }
        } else {
            throw new AppException("保存失败,未获取到医嘱信息!");
        }
        inptVisitDTO = inptAdviceDAO.getInptVisit(inptAdviceDTO);
        if(!inptVisitDTO.getInDeptId().equals(inptAdviceDTO.getDeptId())){
            throw new AppException("入院科室与开嘱科室不一致，请检查");
        }

        List<InptAdviceDTO> inptAdviceDTOList = new ArrayList<InptAdviceDTO>();

        int splitNum = 2 ;
        int index = 0;

        InptAdviceDTO inptAdviceDtoParm = null ;
        InptAdviceDetailDTO inptAdviceDetailDTO = null ;
        List<InptAdviceDetailDTO> inptAdviceDetailDTOList = new ArrayList<>() ;
        for(InptAdviceDTO parmInptAdvice : inptAdviceList) {

            if(index%splitNum == 0){
                inptAdviceDtoParm = DeepCopy.deepCopy(parmInptAdvice);
                inptAdviceDtoParm.setId(SnowflakeUtils.getId());
                inptAdviceDtoParm.setItemName("");
                inptAdviceDtoParm.setNum(new BigDecimal(0));
                inptAdviceDtoParm.setTotalNum(new BigDecimal(0));
                inptAdviceDtoParm.setTotalPrice(new BigDecimal(0));
                inptAdviceDtoParm.setPrice(new BigDecimal(0));
            }
            /**
             * 明细数据
             */
            inptAdviceDetailDTO = new InptAdviceDetailDTO();
            inptAdviceDetailDTO.setId(SnowflakeUtils.getId());
            inptAdviceDetailDTO.setHospCode(inptVisitDTO.getHospCode());
            inptAdviceDetailDTO.setVisitId(inptVisitDTO.getId());
            inptAdviceDetailDTO.setIaId(inptAdviceDtoParm.getId());
            inptAdviceDetailDTO.setBaId(null);
            inptAdviceDetailDTO.setBfcId(parmInptAdvice.getBfcId());
            inptAdviceDetailDTO.setIaGroupNo(0);
            inptAdviceDetailDTO.setItemCode(parmInptAdvice.getItemCode());
            inptAdviceDetailDTO.setItemName(parmInptAdvice.getItemName());
            inptAdviceDetailDTO.setItemId(parmInptAdvice.getItemId());
            inptAdviceDetailDTO.setNum(BigDecimalUtils.multiply(new BigDecimal(parmInptAdvice.getUseDays()),parmInptAdvice.getNum()));
            inptAdviceDetailDTO.setUnitCode(parmInptAdvice.getUnitCode());
            inptAdviceDetailDTO.setPrice(parmInptAdvice.getPrice());
            inptAdviceDetailDTO.setTotalPrice(BigDecimalUtils.multiply(inptAdviceDetailDTO.getPrice(),inptAdviceDetailDTO.getNum()));
            inptAdviceDetailDTO.setUseCode(parmInptAdvice.getUseCode());
            inptAdviceDetailDTO.setSourceCode(Constants.FYLYFS.YZ);
            inptAdviceDetailDTOList.add(inptAdviceDetailDTO);

            /**
             * 主表数据
             */
            inptAdviceDtoParm.setItemName(StringUtils.isEmpty(inptAdviceDtoParm.getItemName()) ? parmInptAdvice.getItemName():inptAdviceDtoParm.getItemName() + "," + parmInptAdvice.getItemName());
            inptAdviceDtoParm.setContent(inptAdviceDtoParm.getItemName());
            //医嘱单号
            inptAdviceDtoParm.setOrderNo(this.getOrderNo(inptAdviceDTO.getHospCode(), Constants.ORDERRULE.YSYZ));
            //是否签名
            inptAdviceDtoParm.setSignCode(Constants.QMZT.WQM);
            //是否提交
            inptAdviceDtoParm.setIsSubmit(Constants.SF.F);
            //未停嘱
            inptAdviceDtoParm.setIsStop(Constants.SF.F);
            //未核收
            inptAdviceDtoParm.setIsCheck(Constants.SF.F);
            //是否停嘱核收
            inptAdviceDtoParm.setIsStopCheck(Constants.SF.F);
            //是否拒收
            inptAdviceDtoParm.setIsReject(Constants.SF.F);
            //是否拒收
            inptAdviceDtoParm.setIsPrint(Constants.SF.F);
            //是否皮试
            inptAdviceDtoParm.setIsSkin(Constants.SF.F);
            inptAdviceDtoParm.setIatdGroupNo(0);
            inptAdviceDtoParm.setIatdGroupSeqNo(0);
            //visitId
            inptAdviceDtoParm.setVisitId(inptAdviceDTO.getVisitId());
            inptAdviceDtoParm.setNum(BigDecimalUtils.add(inptAdviceDtoParm.getNum(),parmInptAdvice.getNum()));
            inptAdviceDtoParm.setTotalNum(BigDecimalUtils.add(inptAdviceDtoParm.getTotalNum(),BigDecimalUtils.multiply(parmInptAdvice.getNum(),new BigDecimal(inptAdviceDTO.getUseDays()))));
            inptAdviceDtoParm.setTotalPrice(BigDecimalUtils.add(BigDecimalUtils.multiply(BigDecimalUtils.multiply(parmInptAdvice.getNum(),parmInptAdvice.getPrice()),new BigDecimal(inptAdviceDTO.getUseDays())),inptAdviceDtoParm.getTotalPrice()));
            inptAdviceDtoParm.setPrice(BigDecimalUtils.add(inptAdviceDtoParm.getPrice(),parmInptAdvice.getPrice()));
            inptAdviceDtoParm.setHospCode(inptVisitDTO.getHospCode());
            inptAdviceDtoParm.setTypeCode("1");
            inptAdviceDtoParm.setInDeptId(inptVisitDTO.getInDeptId());
            inptAdviceDtoParm.setExecDeptId(inptVisitDTO.getInDeptId());
            inptAdviceDtoParm.setDeptId(inptVisitDTO.getInDeptId());
            inptAdviceDtoParm.setRemark(inptAdviceDTO.getRemark());
            inptAdviceDtoParm.setStartExecNum(1);
            inptAdviceDtoParm.setHerbNoteCode(inptAdviceDTO.getHerbNoteCode());
            inptAdviceDtoParm.setEndExecNum(0);
            inptAdviceDtoParm.setUseDays(inptAdviceDTO.getUseDays());
            inptAdviceDtoParm.setHerbNum(new BigDecimal(inptAdviceDTO.getUseDays()));
            inptAdviceDtoParm.setUseCode("1");
            inptAdviceDtoParm.setUsageCode(inptAdviceDTO.getUsageCode());
            inptAdviceDtoParm.setLongStartTime(DateUtils.getNow());
            inptAdviceDtoParm.setCollectBloodTime(DateUtils.getNow());
            inptAdviceDtoParm.setIsLong(Constants.SF.F);
            inptAdviceDtoParm.setCrteId(inptAdviceDTO.getCrteId());
            inptAdviceDtoParm.setCrteName(inptAdviceDTO.getCrteName());
            inptAdviceDtoParm.setTeachDoctorId(inptAdviceDTO.getTeachDoctorId());
            inptAdviceDtoParm.setTeachDoctorName(inptAdviceDTO.getTeachDoctorName());
            if(index%splitNum == 0){
                inptAdviceDTOList.add(inptAdviceDtoParm);
            }
            index++;
        }

        //保存住院医嘱
        if (!ListUtils.isEmpty(inptAdviceDTOList)) {
            inptAdviceDAO.insertBathInptAdvice(inptAdviceDTOList);
        }

        //保存住院医嘱明细
        if (!ListUtils.isEmpty(inptAdviceDetailDTOList)) {
            inptAdviceDAO.insertBathInptAdviceDetail(inptAdviceDetailDTOList);
        }
        return true;
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 08:48
     * @Return:
     **/
    @Override
    public List<InsureItemMatchDTO> queryLimitDrugList(InptAdviceDTO inptAdviceDTO) {
        if (StringUtils.isEmpty(inptAdviceDTO.getVisitId())) throw new RuntimeException("就诊id为空，请核对！");
        if (StringUtils.isEmpty(inptAdviceDTO.getIdsStr())) throw new RuntimeException("未选择需要提交的医嘱！");
        // 根据就诊id查询就诊记录
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(inptAdviceDTO.getVisitId());
        inptVisitDTO.setHospCode(inptAdviceDTO.getHospCode());
        InptVisitDTO visitById = inptVisitDAO.getInptVisitById(inptVisitDTO);
        if (visitById == null) throw new RuntimeException("就诊记录不存在，请核对！");

        // 根据系统参数(INSURE_DEFAULT_REG_CODE)获取限制用药的默认医保机构编码
        SysParameterDTO sysParameterDTO = this.getSysParam(inptVisitDTO.getHospCode());
        if (sysParameterDTO == null || StringUtils.isEmpty(sysParameterDTO.getValue())) {
            return null;
        }
        Map parse = new HashMap();
        if (StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
            parse = (Map) JSON.parse(sysParameterDTO.getValue());
        }
        if (StringUtils.isNotEmpty(MapUtils.get(parse, "isLmtDrugFlag")) && "0".equals(MapUtils.get(parse, "isLmtDrugFlag"))) {
            return null;
        }

        // 根据医嘱ids字符串和visitId从处方明细表副表查询出处方列表
        List<InptAdviceDetailDTO> list = inptAdviceDAO.queryAdviceByIdsAndVisitId(inptAdviceDTO);
        List<String> itemIdList = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            itemIdList = list.stream().map(InptAdviceDetailDTO::getItemId).distinct().collect(Collectors.toList());
        }

        // 病人类型
        String patientCode = visitById.getPatientCode();
        // 医保机构编码
        String insureRegCode = null;
        if (StringUtils.isNotEmpty(patientCode)) {
            if (Integer.parseInt(patientCode) > 0) { // 医保病人
                // 通过就诊id查询医保登记信息
                Map insureParamMap = new HashMap();
                insureParamMap.put("hospCode", inptVisitDTO.getHospCode());
                insureParamMap.put("id", inptVisitDTO.getId());
                insureParamMap.put("limitFlag", "1");
                InsureIndividualVisitDTO insureIndividualVisitById = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureParamMap);
//                if (insureIndividualVisitById == null) throw new RuntimeException("医保病人请先进行医保登记");
                if (insureIndividualVisitById == null) return null;
                insureRegCode = insureIndividualVisitById.getInsureRegCode();

            } else if (Integer.parseInt(patientCode) == 0 ) { // 自费病人
                if (StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                    if (StringUtils.isNotEmpty(MapUtils.get(parse, "isLmtDrugFlag"))
                            && "1".equals(MapUtils.get(parse, "isLmtDrugFlag"))
                            && StringUtils.isNotEmpty(MapUtils.get(parse, "defaultInsureRegCode"))) {
                        // 启用限制用药，且配置了默认医保机构编码
                        insureRegCode = MapUtils.get(parse, "defaultInsureRegCode");
                    } else {
                        // 不启用限制用药
                        throw new RuntimeException("请先在系统参数【INSURE_DEFAULT_REG_CODE】配置默认医保机构编码");
                    }
                }
            }
        }

        // 根据医保机构编码查询限制级用药列表
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        // 医院编码
        insureItemMatchDTO.setHospCode(inptVisitDTO.getHospCode());
        // 医保机构编码
        insureItemMatchDTO.setInsureRegCode(insureRegCode);
        // 已审核
        insureItemMatchDTO.setAuditCode(Constants.SHZT.SHWC);
        // 有效
        insureItemMatchDTO.setIsValid(Constants.SF.S);
        // 已匹配
        insureItemMatchDTO.setIsMatch(Constants.SF.S);
        // 已传输
        insureItemMatchDTO.setIsTrans(Constants.SF.S);
        // 属限制级用药
        insureItemMatchDTO.setLmtUserFlag(Constants.SF.S);
        Map map = new HashMap();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("insureItemMatchDTO", insureItemMatchDTO);
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchService_consumer.queryLimitDrugList(map).getData();

        List<InsureItemMatchDTO> result = new ArrayList<>();
        // 返回结果，根据医嘱明细下所有的项目id匹配医保限制类用药的项目
        if (!ListUtils.isEmpty(itemIdList) && !ListUtils.isEmpty(insureItemMatchDTOS)) {
            for (String itemId : itemIdList) {
                for (InsureItemMatchDTO itemMatchDTO : insureItemMatchDTOS) {
                    if (itemId.equals(itemMatchDTO.getHospItemId())) {
                        result.add(itemMatchDTO);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @Menthod: updateInptAdviceDetailLmt
     * @Desrciption: 更新医嘱明细表限制用药相关字段
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 10:39
     * @Return:
     **/
    @Override
    public Boolean updateInptAdviceDetailLmt(List<InsureItemMatchDTO> insureItemMatchDTOS) {
        if (ListUtils.isEmpty(insureItemMatchDTOS)) {
            throw new RuntimeException("入参错误，请选择需要保存的处方！");
        }
        // 去重后的就诊visitIds
        List<String> visitIds = insureItemMatchDTOS.stream().map(InsureItemMatchDTO::getVisitId).distinct().collect(Collectors.toList());
        // 去重后的项目itemIds
        List<String> itemIds = insureItemMatchDTOS.stream().map(InsureItemMatchDTO::getHospItemId).distinct().collect(Collectors.toList());
        Map<String, List<InsureItemMatchDTO>> listMap = insureItemMatchDTOS.stream().collect(Collectors.groupingBy(InsureItemMatchDTO::getHospItemId));

        //根据visitIds，itemIds查询出对应的费用表以及处方明细表副表数据
        Map map = new HashMap();
        map.put("visitIds", visitIds);
        map.put("itemIds", itemIds);
        map.put("hospCode", insureItemMatchDTOS.get(0).getHospCode());
        List<InptAdviceDetailDTO> detailsExtDTOS = inptAdviceDAO.queryInptAdviceDetail(map);

        int count = 0;
        // 更新处方明细表副表数据，限制用药字段
        if (!ListUtils.isEmpty(detailsExtDTOS)) {
            for (InptAdviceDetailDTO detailsExtDTO : detailsExtDTOS) {
                List<InsureItemMatchDTO> itemMatchDTOSByItemId = MapUtils.get(listMap, detailsExtDTO.getItemId());
                if (!ListUtils.isEmpty(itemMatchDTOSByItemId)) {
                    for (InsureItemMatchDTO insureItemMatchDTO : itemMatchDTOSByItemId) {
                        detailsExtDTO.setLmtUserFlag(insureItemMatchDTO.getLmtUserFlag());
                        detailsExtDTO.setLimUserExplain(insureItemMatchDTO.getLimUserExplain());
                        detailsExtDTO.setIsReimburse(insureItemMatchDTO.getIsReimburse());
                    }
                }
            }
            count = inptAdviceDAO.updateInptAdviceDetail(detailsExtDTOS);
        }
        return count > 0;
    }

    /**
     * 根据系统参数获取限制用药的默认医保机构编码
     * @param hospCode
     * @return
     */
    private SysParameterDTO getSysParam(String hospCode) {
        Map sysParamMap = new HashMap();
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", "INSURE_DEFAULT_REG_CODE"); // 医保限制用药默认医保机构编码
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParamMap).getData();
        return sysParameterDTO;
    }
}
