package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.inpt.bedlist.dto.InptLongCostDTO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.inpt.longcost.dao.BedLongCostDAO;
import cn.hsa.module.inpt.nurse.bo.AddAccountByInptBO;
import cn.hsa.module.outpt.fees.service.OutptCostKeepAccountsService;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 *@Package_name: cn.hsa.inpt.nurse.bo.impl
 *@Class_name: AddAccountByInptBOImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 11:28
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class AddAccountByInptBOImpl extends HsafBO implements AddAccountByInptBO {

    @Resource
    InptCostDAO inptCostDAO;
    @Resource
    BedLongCostDAO bedLongCostDAO ;
    @Resource
    BaseItemService baseItemService_consumer;
    @Resource
    BaseMaterialService baseMaterialService_consumer;
    @Resource
    BaseDrugService baseDrugService_consumer;
    @Resource
    PharInWaitReceiveService pharInWaitReceiveService_consumer;
    @Resource
    InptVisitDAO inptVisitDAO;
    @Resource
    BasePreferentialService basePreferentialService_consumer;
    @Resource
    SysParameterService sysParameterService_consumer;
    @Resource
    InptAdviceDAO inptAdviceDAO;
    @Resource
    OutptVisitService outptVisitService_consumer;
    @Resource
    OutptCostKeepAccountsService ouptCostService_consumer;
    /**
    * @Method queryPatientInfoPage
    * @Desrciption 分页查询患者信息
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:37
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    @Override
    public PageDTO queryPatientInfoPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        // 获取系统参数 是否开启大人婴儿合并结算
        SysParameterDTO mergeParameterDTO =null;
        Map<String, Object> isMergeParam = new HashMap<>();
        isMergeParam.put("hospCode", inptVisitDTO.getHospCode());
        isMergeParam.put("code", "BABY_INSURE_FEE");
        mergeParameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
        List<InptVisitDTO> inptVisitDTOS = null;
        if (mergeParameterDTO != null && Constants.SF.S.equals(mergeParameterDTO.getValue())) {
            // 开启合并结算
            inptVisitDTOS = inptVisitDAO.queryInptVisitPage(inptVisitDTO);
        } else {
            inptVisitDTOS =  inptVisitDAO.queryInptVisitPageNoMerge(inptVisitDTO);
        }
        return PageDTO.of(inptVisitDTOS);
    }

    /**
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param inptVisitDTO
     * @Author xingyu.xie
     * @Date   2020/9/4 10:37
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    @Override
    public PageDTO queryPatientInfoPageByMoney(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getWarningMoney())){
            HashMap map =  new HashMap();
            map.put("hospCode",inptVisitDTO.getHospCode());
            map.put("code","ZYCK_BJX");
            WrapperResponse<SysParameterDTO> parameterByCode = sysParameterService_consumer.getParameterByCode(map);
            inptVisitDTO.setWarningMoney(parameterByCode.getData().getValue());
        }
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryInptVisitPageByMoney(inptVisitDTO);
        return PageDTO.of(inptVisitDTOS);
    }

    /**
    * @Method saveAddAccountByInpt
    * @Desrciption 住院补记账保存
    * @param map
    * @Author liuqi1  增加药品补记账 by marong 9月29日
    * @Date   2020/9/3 11:34
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean saveAddAccountByInpt(Map<String,Object> map) {
        List<InptCostDTO> inptCostDTOs = MapUtils.get(map, "inptCostDTOs");
        checkAddAccountPamar(inptCostDTOs,map);


        String hospCode = MapUtils.get(map, "hospCode");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String loginDeptId = MapUtils.get(map, "loginDeptId");
        String loginDeptName = MapUtils.get(map, "loginDeptName");

        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setVisitId(inptCostDTOs.get(0).getVisitId());
        inptAdviceDTO.setHospCode(hospCode);
        InptVisitDTO visitDTOBalance = inptAdviceDAO.checkBalance(inptAdviceDTO);
        if(visitDTOBalance != null){
          throw new AppException("您已欠费，不能补记帐,最多欠费："+visitDTOBalance.getQfCose()+"元,您当前余额："+visitDTOBalance.getTotalBalance()+"元");
        }
        List<InptCostDTO> itemDtoList = new ArrayList<>();//项目费用集合
        List<InptCostDTO> materialDtoList = new ArrayList<>();//材料费用集合
        List<InptCostDTO> baseDrugDTOList = new ArrayList<>();
        List<PharInWaitReceiveDTO> getPharInWaitDtoList = new ArrayList<>();//需要去库房取的材料集合
        InptVisitDTO inptVisitDTO = new InptVisitDTO();//患者信息，含医院编码、就诊id、补记账的合计费用

        //费用数据处理   //增加药品补记账 by marong 9月29日
        handleCostData(inptCostDTOs, hospCode, userId, userName, loginDeptId, itemDtoList, materialDtoList, baseDrugDTOList,inptVisitDTO,getPharInWaitDtoList);
        //材料费用
        itemDtoList.addAll(materialDtoList);
        //药品费用
        itemDtoList.addAll(baseDrugDTOList);
        if(!ListUtils.isEmpty(itemDtoList)){
            //住院优惠重算
            inptPreferentialRecalculate(itemDtoList);
            //项目费用
            inptCostDAO.insertInptCostBatch(itemDtoList);
        }
        //是否手麻补记账
        if(StringUtils.isNotEmpty(inptCostDTOs.get(0).getIsSs()) && Constants.SF.S.equals(inptCostDTOs.get(0).getIsSs())){
            InptCostDTO inptCostDTO = inptCostDTOs.get(0);
            inptCostDTO.setSourceDeptId(loginDeptId);
            inptCostDAO.updateOperInfoRecord(inptCostDTO);
        }
        if(!ListUtils.isEmpty(getPharInWaitDtoList)){
            Map<String,Object> m = new HashMap<>();
            m.put("hospCode",hospCode);
            m.put("pharInWaitReceiveDTOs",getPharInWaitDtoList);
            //待领表新增记录（考虑到事务统一 2021-08-05-pengbo）
            // pharInWaitReceiveService_consumer.insertPharInWaitBatch(m);
            inptCostDAO.insertPharInWaitReceiveBatch(getPharInWaitDtoList);
        }
        //更新患者的合计费用
        if (StringUtils.isEmpty(inptVisitDTO.getBabyId())) {
            inptVisitDAO.updateTotalCost(inptVisitDTO);
        }
        return true;
    }

    /** 门诊手术补记账 **/
    @Override
    public Boolean saveAddAccountByOutpt(Map<String, Object> map) {
        List<InptCostDTO> inptCostDTOList = MapUtils.get(map, "inptCostDTOs");
        checkAddAccountPamar(inptCostDTOList,map);
        String hospCode = MapUtils.get(map, "hospCode");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String loginDeptId = MapUtils.get(map, "loginDeptId");
        String loginDeptName = MapUtils.get(map, "loginDeptName");
        //项目费用集合
        List<OutptCostDTO> itemDtoList = new ArrayList<>();
        List<OutptCostDTO> outptCostDTOS = new ArrayList<>();
        //材料费用集合
        List<OutptCostDTO> materialDtoList = new ArrayList<>();
        List<OutptCostDTO> baseDrugDTOList = new ArrayList<>();
        //患者信息，含医院编码、就诊id、补记账的合计费用
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        copyProperties(inptCostDTOList,outptCostDTOS);
        //费用数据处理
        handleOutptCostData(outptCostDTOS, hospCode, userId, userName, loginDeptId, itemDtoList, materialDtoList, baseDrugDTOList,outptVisitDTO);
        //材料费用
        itemDtoList.addAll(materialDtoList);
        //药品费用
        itemDtoList.addAll(baseDrugDTOList);
        if(!ListUtils.isEmpty(itemDtoList)){
            //住院优惠重算
//            outptPreferentialRecalculate(itemDtoList);
            //项目费用
            Map<String,Object> map1 = new HashMap<>();
            map1.put("hospCode",hospCode);
            map1.put("outptCostDTOs",outptCostDTOS);
            ouptCostService_consumer.insertOutptCostBatch(map1);
        }
        //是否手麻补记账
        if(StringUtils.isNotEmpty(outptCostDTOS.get(0).getIsSs()) && Constants.SF.S.equals(outptCostDTOS.get(0).getIsSs())){
            OutptCostDTO outptCostDTO = outptCostDTOS.get(0);
            Map<String,Object> updateParam = new HashMap<>();
            updateParam.put("sourceDeptId",outptCostDTO.getSourceDeptId());
            updateParam.put("sourceId",outptCostDTO.getSourceId());
            updateParam.put("hospCode",outptCostDTO.getHospCode());

            ouptCostService_consumer.updateOperInfoRecord(updateParam);
        }
        return true;
    }

    /** 门诊优惠重算 **/
    public List<InptCostDTO> outptPreferentialRecalculate(List<InptCostDTO> inptCostDTOs){
        if (ListUtils.isEmpty(inptCostDTOs)) {
            return null;
        }
        //根据就诊id获得患者信息
        InptCostDTO inptCostDTO = inptCostDTOs.get(0);

        Map<String,String> param = new HashMap<>(4);
        param.put("hospCode",inptCostDTO.getHospCode());
        param.put("id",inptCostDTO.getVisitId());

        OutptVisitDTO outptVisitDTO = outptVisitService_consumer.queryByVisitID(param);

        if(null == outptVisitDTO) {
            throw new AppException("未查询到对应医院门诊病人的信息！出错病人信息如下："+param);
        }
        //封装优惠重算参数
        Map<String,Object> map = new HashMap<>(4);
        List<Map<String,Object>> costList = new ArrayList<>();
        for(InptCostDTO i:inptCostDTOs){
            Map<String,Object> m = new HashMap<>();
            m.put("id",i.getId());
            m.put("hospCode",i.getHospCode());
            //优惠类别ID
            m.put("preferentialTypeId",outptVisitDTO.getPreferentialTypeId());
            m.put("itemId",i.getItemId());
            m.put("itemCode",i.getItemCode());
            m.put("bfcId",i.getBfcId());
            //0或空:门诊,1:住院
            m.put("type","1");
            m.put("totalPrice",i.getTotalPrice());
            costList.add(m);
        }

        map.put("hospCode",inptCostDTO.getHospCode());
        map.put("costList",costList);
        //调用公共的优惠重算
        WrapperResponse<List<Map<String, Object>>> listWrapperResponse = basePreferentialService_consumer.calculatPreferential(map);
        List<Map<String, Object>> data = listWrapperResponse.getData();

        //回写优惠金额和优惠后总金额
        for(InptCostDTO i:inptCostDTOs){
            for(Map<String, Object> m:data){
                if(i.getId().equals(MapUtils.get(m, "id"))){
                    i.setPreferentialPrice(MapUtils.get(m, "preferentialPrice"));
                    i.setRealityPrice(MapUtils.get(m, "realityPrice"));
                    break;
                }
            }
        }

        return inptCostDTOs;
    }

    /**
     * 从itemDtoList 赋值属性值到 outptCostDTOS
     *  门诊数据的创建时间就是补记账时间
     * **/
    private void copyProperties(List<InptCostDTO> srcList, List<OutptCostDTO> destList) {
        OutptCostDTO outptCostDTO = null;
        Date now = DateUtils.getNow();
        for(InptCostDTO inptCostDTO : srcList){
            outptCostDTO = new OutptCostDTO();
            BeanUtils.copyProperties(inptCostDTO,outptCostDTO,OutptCostDTO.class);
            outptCostDTO.setSourceCode("8");
            if(StringUtils.isEmpty(inptCostDTO.getUsageCode())){
                outptCostDTO.setUsageCode(Constants.YYXZ.CG);
            }
            outptCostDTO.setCrteTime(inptCostDTO.getCostTime());
            destList.add(outptCostDTO);
        }


    }

    /**
    * @Menthod checkAddAccountPamar
    * @Desrciption 补记帐参数校验
    *
    * @Param
    * [inptCostDTOs, map]
    *
    * @Author jiahong.yang
    * @Date   2021/2/5 9:20
    * @Return boolean
    **/
    public boolean checkAddAccountPamar(List<InptCostDTO> inptCostDTOs, Map<String,Object> map){
    String hospCode = MapUtils.get(map, "hospCode");
    String userId = MapUtils.get(map, "userId");
    String userName = MapUtils.get(map, "userName");
    String loginDeptId = MapUtils.get(map, "loginDeptId");

    if(ListUtils.isEmpty(inptCostDTOs)){
      throw new AppException("补记账保存失败,参数不全");
    }
    if(StringUtils.isEmpty(hospCode)){
      throw new AppException("医院编码不能为空");
    }
    if(StringUtils.isEmpty(userId)){
      throw new AppException("用户名不能为空");
    }
    if(StringUtils.isEmpty(userName)){
      throw new AppException("用户名不能为空");
    }
    if(StringUtils.isEmpty(loginDeptId)){
      throw new AppException("登录科室不能为空");
    }
    for(InptCostDTO inptCostDTO : inptCostDTOs){
      if(StringUtils.isEmpty(inptCostDTO.getItemId())){
        throw new AppException(inptCostDTO.getItemName()+",项目不能为空");
      }
      if(StringUtils.isEmpty(inptCostDTO.getItemCode())){
        throw new AppException(inptCostDTO.getItemName()+",项目类型不能为空");
      }
      // 参数校验
      if (StringUtils.isEmpty(inptCostDTO.getExecDeptId())) {
        throw new AppException(inptCostDTO.getItemName()+":执行科室不能为空");
      }
      // 参数校验
      if (inptCostDTO.getNum() == null) {
        throw new AppException(inptCostDTO.getItemName()+":数量不能为空");
      }
      // 参数校验
      if (BigDecimalUtils.compareTo(inptCostDTO.getNum(), BigDecimal.valueOf(0)) <= 0) {
        throw new AppException(inptCostDTO.getItemName()+":数量必须大于0");
      }
      //常规、出院带药  药品或者材料
      if ( (Constants.YYXZ.CG.equals(inptCostDTO.getUseCode())  || Constants.YYXZ.CYDY.equals(inptCostDTO.getUseCode()))
        && (Constants.XMLB.YP.equals(inptCostDTO.getItemCode())  || Constants.XMLB.CL.equals(inptCostDTO.getItemCode()))
        && StringUtils.isEmpty(inptCostDTO.getPharId()) ) {
        throw new AppException(inptCostDTO.getItemName()+":发药药房不能为空");
      }
      //药品
      if (Constants.XMLB.YP.equals(inptCostDTO.getItemCode()) && StringUtils.isEmpty(inptCostDTO.getRateId())) {
        throw new AppException(inptCostDTO.getItemName()+":频率不能为空");
      }
      //药品
      if (Constants.XMLB.YP.equals(inptCostDTO.getItemCode()) && StringUtils.isEmpty(inptCostDTO.getUsageCode())) {
        throw new AppException(inptCostDTO.getItemName()+":用法不能为空");
      }
      // 校验药品库存
      if(Constants.XMLB.YP.equals(inptCostDTO.getItemCode())&&
              (Constants.YYXZ.CG.equals(inptCostDTO.getUseCode())  || Constants.YYXZ.CYDY.equals(inptCostDTO.getUseCode()))){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("hospCode",hospCode);
            params.put("pharId",inptCostDTO.getPharId());
            params.put("itemId",inptCostDTO.getItemId());
            params.put("hospCode",hospCode);

            List stock = inptCostDAO.checkStock(params);
            if(ListUtils.isEmpty(stock)){
                throw new AppException(inptCostDTO.getItemName()+" 库存不足!,请检查发药药房是否选择正确以及库存是否充足");
            }

      }
    }
    return true;
  }

    /**
     * @Method inptPreferentialRecalculate
     * @Desrciption 住院优惠重算
     * @param inptCostDTOs
     * @Author liuqi1
     * @Date   2020/9/30 16:26
     * @Return void
     **/
    @Override
    public List<InptCostDTO> inptPreferentialRecalculate(List<InptCostDTO> inptCostDTOs){
        if (ListUtils.isEmpty(inptCostDTOs)) {
            return null;
        }
        //根据就诊id获得患者信息
        InptCostDTO inptCostDTO = inptCostDTOs.get(0);
        InptVisitDTO paramDto = new InptVisitDTO();
        paramDto.setHospCode(inptCostDTO.getHospCode());
        paramDto.setId(inptCostDTO.getVisitId());
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

        //封装优惠重算参数
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> costList = new ArrayList<>();
        for(InptCostDTO i:inptCostDTOs){
            Map<String,Object> m = new HashMap<>();
            m.put("id",i.getId());
            m.put("hospCode",i.getHospCode());
            m.put("preferentialTypeId",inptVisitById.getPreferentialTypeId());//优惠类别ID
            m.put("itemId",i.getItemId());
            m.put("itemCode",i.getItemCode());
            m.put("bfcId",i.getBfcId());
            m.put("type","1");//0或空:门诊,1:住院
            m.put("totalPrice",i.getTotalPrice());
            costList.add(m);
        }

        map.put("hospCode",inptCostDTO.getHospCode());
        map.put("costList",costList);
        //调用公共的优惠重算
        WrapperResponse<List<Map<String, Object>>> listWrapperResponse = basePreferentialService_consumer.calculatPreferential(map);
        List<Map<String, Object>> data = listWrapperResponse.getData();

        //回写优惠金额和优惠后总金额
        for(InptCostDTO i:inptCostDTOs){
            for(Map<String, Object> m:data){
                if(i.getId().equals(MapUtils.get(m, "id"))){
                    i.setPreferentialPrice(MapUtils.get(m, "preferentialPrice"));
                    i.setRealityPrice(MapUtils.get(m, "realityPrice"));
                    break;
                }
            }
        }

        return inptCostDTOs;
        //inptPreferentialRecalculateOld(inptCostDTOs);
    }

    /**
    * @Method inptPreferentialRecalculateOld
    * @Desrciption 老的住院优惠重算
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/11/10 17:04
    * @Return void
    **/
    public void inptPreferentialRecalculateOld(List<InptCostDTO> inptCostDTOs){
        //根据就诊id获得患者信息
        InptCostDTO inptCostDTO = inptCostDTOs.get(0);
        InptVisitDTO paramDto = new InptVisitDTO();
        paramDto.setHospCode(inptCostDTO.getHospCode());
        paramDto.setId(inptCostDTO.getVisitId());
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

        //根据preferentialTypeId查询出base_preferential明细数据
        Map queryMap = new HashMap();
        BasePreferentialTypeDTO basePreferentialTypeDTO = new BasePreferentialTypeDTO();
        basePreferentialTypeDTO.setHospCode(inptVisitById.getHospCode());
        basePreferentialTypeDTO.setId(inptVisitById.getPreferentialTypeId());
        queryMap.put("hospCode", inptVisitById.getHospCode());
        queryMap.put("basePreferentialTypeDTO", basePreferentialTypeDTO);
        WrapperResponse<List<BasePreferentialDTO>> response =  basePreferentialService_consumer.queryPreferentials(queryMap);
        List<BasePreferentialDTO> preferentialList = response.getData();
        if(ListUtils.isEmpty(preferentialList)){
            //如果没有配置优惠，优惠后总金额 = 项目总金额
            for (InptCostDTO icDto : inptCostDTOs) {
                icDto.setRealityPrice(icDto.getTotalPrice());
            }
            return;
        }

        boolean isConfig = false;//单个项目是否配置优惠，默认 false:没有配置
        //把preferentialList按照优惠类型type_code分组 1 财务分类 2药品 3项目
        for (InptCostDTO icDto : inptCostDTOs) {
            isConfig = false;
            for (BasePreferentialDTO dto :preferentialList) {
                //判断是否财务分类
                if(("1".equals(dto.getTypeCode()) && dto.getBizCode().equals(inptCostDTO.getBfcCode()))
                        || (inptCostDTO.getItemId().equals(dto.getItemId()) && inptCostDTO.getItemCode().equals(dto.getItemCode()))){
                    isConfig = true;

                    //判断住院优惠方式in_code 1: 按比例 2: 按金额
                    if("1".equals(dto.getInCode())){
                        //优惠总金额：项目总金额 * 优惠比列
                        BigDecimal preferentialPrice =  BigDecimalUtils.multiply((new BigDecimal(1).subtract(dto.getInScale())), icDto.getTotalPrice());
                        //优惠后总金额：项目总金额 - 优惠总金额
                        BigDecimal realityPrice = BigDecimalUtils.subtract(icDto.getTotalPrice(), preferentialPrice);
                        icDto.setPreferentialPrice(preferentialPrice);
                        icDto.setRealityPrice(realityPrice);
                        break;
                    }else if("2".equals(dto.getInCode())){
                        //优惠总金额
                        icDto.setPreferentialPrice(dto.getInScale());

                        //优惠后总金额
                        BigDecimal realityPrice = BigDecimalUtils.subtract(icDto.getTotalPrice(), dto.getInScale());
                        icDto.setRealityPrice(realityPrice);
                        break;
                    }else{
                        icDto.setRealityPrice(icDto.getTotalPrice());
                        break;
                    }
                }
            }

            if(!isConfig){
                //如果没有配置优惠，优惠后总金额 = 项目总金额
                icDto.setRealityPrice(icDto.getTotalPrice());
            }
        }
    }


    /**
    * @Method queryBackCostWithAddAccountPage
    * @Desrciption 补记账退费分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/4 14:47
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryBackCostWithAddAccountPage(InptCostDTO inptCostDTO) {
        PageHelper.startPage(inptCostDTO.getPageNo(), inptCostDTO.getPageSize());
        List<InptCostDTO> inptCostDTOS = inptCostDAO.queryInptCostPage(inptCostDTO);
        return PageDTO.of(inptCostDTOS);
    }

    /**
     * @param inptCostDTO
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 根据visitId and soureTypeCode 查询这个病人录入的长期费用补记账
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public PageDTO queryLongCostByVistIdAndSoureTypeCode(InptCostDTO inptCostDTO) {
        PageHelper.startPage(inptCostDTO.getPageNo(), inptCostDTO.getPageSize());
        List<InptLongCostDTO> inptLongCostDTOs = bedLongCostDAO.queryLongCostByVistIdAndSoureTypeCode(inptCostDTO);
        return PageDTO.of(inptLongCostDTOs);
    }

    /**
     * @param map
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 补记账长期费用管理保存
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<Boolean> saveAccountRepairLong(Map<String, Object> map) {
        List<InptLongCostDTO> longCostDtoList = MapUtils.get(map, "longCostDtoList");
        if(ListUtils.isEmpty(longCostDtoList)){
            throw new RuntimeException("保存失败,未获取到补记账费用信息!");
        }

        //医院编码
        String hospCode = MapUtils.get(map, "hospCode");
        //登陆人ID
        String userId = MapUtils.get(map, "userId");
        //登陆人名称
        String userName = MapUtils.get(map, "userName");
        //登录科室ID
        String loginDeptId = MapUtils.get(map, "loginDeptId");
        //登录科室名称
        String loginDeptName = MapUtils.get(map, "loginDeptName");

        //补全的费用
        List<InptCostDTO> costDtoList = new ArrayList<>();
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(longCostDtoList.get(0).getVisitId());
        inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
        int index = 0;
        for (InptLongCostDTO inptLongCostDTO :longCostDtoList){
            index++;
            if (inptLongCostDTO.getStartTime() == null){
                throw new AppException(inptLongCostDTO.getItemName() + "计费开始时间不能为空!");
            }
            if (inptLongCostDTO.getTotalNum() == null || BigDecimal.ZERO.equals(inptLongCostDTO.getTotalNum())){
                throw new AppException(inptLongCostDTO.getItemName() + "数量为0或为空,保存失败!");
            }
            if (inptLongCostDTO.getStartTime().before(inptVisitDTO.getInTime())){
                throw new AppException(inptLongCostDTO.getItemName() + "计费时间不能小于入院时间："+ DateUtils.format(inptVisitDTO.getInTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            inptLongCostDTO.setId(SnowflakeUtils.getId());
            inptLongCostDTO.setCrteId(userId);
            inptLongCostDTO.setCrteName(userName);
            inptLongCostDTO.setHospCode(hospCode);
            //来源类型代码（LYLX） : 0、床位，1、录入，2、包床 ....
            inptLongCostDTO.setSourceTypeCode("1");
            Date startTime =  DateUtils.trunc(inptLongCostDTO.getStartTime(),"yyyy-MM-dd");

            //默认结束时间为当前时间
            Date endTime = DateUtils.trunc(DateUtils.getNow(),"yyyy-MM-dd");;
            if(inptLongCostDTO.getEndTime() != null){
                endTime = DateUtils.trunc(inptLongCostDTO.getEndTime(),"yyyy-MM-dd");
            }

            Date now =  DateUtils.trunc(new Date(),"yyyy-MM-dd");
            //患者信息(用于取值赋值)
            InptVisitDTO paramDto = new InptVisitDTO();
            paramDto.setHospCode(hospCode);
            paramDto.setId(inptLongCostDTO.getVisitId());
            InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

            if (startTime == null || endTime == null){
                throw  new  RuntimeException(inptLongCostDTO.getItemName()+"开始时间,结束时间不能为空");
            }

            //如果结束时间大于当前时间,费用补全的结束时间为当前时间
            if (!DateUtils.dateCompare(endTime,now)){
                endTime = now ;
            }

            while(!DateUtils.dateCompare(endTime,startTime)){
                InptCostDTO inptCostDTO = new InptCostDTO();
                inptCostDTO.setId(SnowflakeUtils.getId());
                inptCostDTO.setHospCode(inptLongCostDTO.getHospCode()) ;
                inptCostDTO.setVisitId(inptLongCostDTO.getVisitId());
                inptCostDTO.setIatId(inptLongCostDTO.getId());
                inptCostDTO.setSourceCode("8");
                inptCostDTO.setSourceId(inptLongCostDTO.getId()) ;
                inptCostDTO.setSourceDeptId(loginDeptId);
                inptCostDTO.setInDeptId(inptVisitById.getInDeptId());
                inptCostDTO.setBfcId(inptLongCostDTO.getBfcId());
                inptCostDTO.setItemCode(inptLongCostDTO.getItemCode()) ;
                inptCostDTO.setItemId(inptLongCostDTO.getItemId());
                inptCostDTO.setItemName(inptLongCostDTO.getItemName());
                inptCostDTO.setPrice(inptLongCostDTO.getPrice());
                inptCostDTO.setNum(inptLongCostDTO.getNum()) ;
                inptCostDTO.setSpec(null);
                inptCostDTO.setPrepCode(null);
                inptCostDTO.setDosage(null);
                inptCostDTO.setDosageUnitCode(null) ;
                inptCostDTO.setUsageCode(null) ;
                inptCostDTO.setRateId(null) ;
                inptCostDTO.setSpeedCode(null) ;
                inptCostDTO.setUseDays(1);
                inptCostDTO.setNumUnitCode(inptLongCostDTO.getUnitCode());
                inptCostDTO.setTotalNum(inptLongCostDTO.getTotalNum()) ;
                inptCostDTO.setTotalNumUnitCode(inptLongCostDTO.getUnitCode()) ;
                inptCostDTO.setHerbNoteCode(null);
                inptCostDTO.setUseCode(inptLongCostDTO.getUseCode()) ;
                inptCostDTO.setPharId(inptLongCostDTO.getPharId());
                inptCostDTO.setHerbNum(null);
                inptCostDTO.setTotalPrice(BigDecimalUtils.multiply(inptLongCostDTO.getTotalNum(),inptLongCostDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                inptCostDTO.setPreferentialPrice(new BigDecimal(0));
                inptCostDTO.setRealityPrice(new BigDecimal(0));
                inptCostDTO.setBackNum(new BigDecimal(0));
                inptCostDTO.setDoctorId(userId);
                inptCostDTO.setDoctorName(userName);
                inptCostDTO.setDeptId(loginDeptId) ;
                inptCostDTO.setIsDist(Constants.SF.F);
                inptCostDTO.setIsGive(inptLongCostDTO.getUseCode()) ;
                inptCostDTO.setBackCode(Constants.TYZT.YFY) ;
                inptCostDTO.setIsOk("0") ;
                inptCostDTO.setOkId(null);
                inptCostDTO.setOkName(null) ;
                inptCostDTO.setSettleCode("0") ;
                inptCostDTO.setDoctorId(inptVisitById.getZzDoctorId());
                inptCostDTO.setDoctorName(inptVisitById.getZzDoctorName());
                inptCostDTO.setZgDoctorId(inptVisitById.getZgDoctorId());
                inptCostDTO.setZgDoctorName(inptVisitById.getZgDoctorName());
                inptCostDTO.setZzDoctorId(inptVisitById.getZzDoctorId());
                inptCostDTO.setZzDoctorName(inptVisitById.getZzDoctorName());
                inptCostDTO.setJzDoctorId(inptVisitById.getJzDoctorId());
                inptCostDTO.setJzDoctorName(inptVisitById.getJzDoctorName());
                inptCostDTO.setExecId(userId) ;
                inptCostDTO.setExecName(userName) ;
                inptCostDTO.setExecTime(inptCostDTO.getCrteTime()) ;
                //执行科室
                inptCostDTO.setExecDeptId(loginDeptId);
                inptCostDTO.setStatusCode(Constants.ZTBZ.ZC) ;
                inptCostDTO.setIsCost(Constants.SF.S) ;
                String strDate = DateUtils.format(startTime,"yyyy-MM-dd") + " " + DateUtils.format(inptLongCostDTO.getStartTime(),"HH:mm:ss");
                inptCostDTO.setCostTime(DateUtils.parse(strDate,"yyyy-MM-dd HH:mm:ss")) ;
                inptCostDTO.setCrteId(userId) ;
                inptCostDTO.setCrteName(userName);
                inptCostDTO.setCrteTime(inptLongCostDTO.getCrteTime()) ;
                inptCostDTO.setIsWait("1") ;
                inptCostDTO.setAttributionCode(inptLongCostDTO.getAttributionCode());
                costDtoList.add(inptCostDTO);
                startTime = DateUtils.dateAdd(startTime, 1);
            }
            inptLongCostDTO.setSourceId(inptVisitById.getBedId());
            inptLongCostDTO.setIsCancel("0");
            inptLongCostDTO.setLastExecTime(endTime);
        }

        //调用补记账保存接口
        if (!ListUtils.isEmpty(costDtoList)){
            map.put("inptCostDTOs",costDtoList);
            this.saveAddAccountByInpt(map);
        }

        return WrapperResponse.success(bedLongCostDAO.insertLongCost(longCostDtoList));
    }

    /**
    * @Method handleCostData
    * @Desrciption 费用数据处理
     * 将源数据分成项目集合和材料集合，并为对应字段赋值
    * @param inptCostDTOs 源数据
    * @param hospCode 医院
    * @param userId 登录用户
    * @param userName 登录用户名称
    * @param loginDeptId 登录机构id
    * @param itemDtoList 项目费用集合
    * @param materialDtoList 材料费用集合
    * @param getPharInWaitDtoList 需要去库房取的材料集合
    * @Author liuqi1  增加药品补记账 by marong 9月29日
    * @Date   2020/9/3 16:43
    * @Return void
    **/
    private void handleCostData(List<InptCostDTO> inptCostDTOs, String hospCode, String userId, String userName, String loginDeptId,
                                List<InptCostDTO> itemDtoList, List<InptCostDTO> materialDtoList,List<InptCostDTO> baseDrugDTOList,InptVisitDTO inptVisitDTO,
                                List<PharInWaitReceiveDTO> getPharInWaitDtoList) {
        List<String> itemList = new ArrayList<>();//项目id集合
        List<String> materialList = new ArrayList<>();//材料id集合
        List<String> drugList = new ArrayList<>();//药品id集合

        //患者信息(用于更新病人表总费用)
        InptCostDTO idto = inptCostDTOs.get(0);
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(idto.getVisitId());

        //患者信息(用于取值赋值)
        InptVisitDTO paramDto = new InptVisitDTO();
        paramDto.setHospCode(hospCode);
        paramDto.setId(inptVisitDTO.getId());
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

        //取主管医生,主治医生,就诊医生
        for (InptCostDTO inptCos: inptCostDTOs ) {
            inptCos.setDoctorId(inptVisitById.getZzDoctorId());
            inptCos.setDoctorName(inptVisitById.getZzDoctorName());
            inptCos.setZgDoctorId(inptVisitById.getZgDoctorId());
            inptCos.setZgDoctorName(inptVisitById.getZgDoctorName());
            inptCos.setZzDoctorId(inptVisitById.getZzDoctorId());
            inptCos.setZzDoctorName(inptVisitById.getZzDoctorName());
            inptCos.setJzDoctorId(inptVisitById.getJzDoctorId());
            inptCos.setJzDoctorName(inptVisitById.getJzDoctorName());
            inptCos.setIndeptId(inptVisitById.getInDeptId());
            inptCos.setInDeptId(inptVisitById.getInDeptId());
        }

        //----从源数据中获得项目或材料的id集合，并从数据库中取得项目或材料的信息
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setHospCode(hospCode);

        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setHospCode(hospCode);

        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setHospCode(hospCode);

        BigDecimal totalCost = new BigDecimal(0);//合计金额
        for(InptCostDTO dto:inptCostDTOs){
            dto.setIsOk(Constants.SF.S);
            dto.setOkId(userId);
            dto.setOkName(userName);
            dto.setOkTime(DateUtils.getNow());
            dto.setHospCode(hospCode);
            totalCost = BigDecimalUtils.add(totalCost, dto.getTotalPrice());

            if(Constants.XMLB.CL.equals(dto.getItemCode())){
                //材料
                materialList.add(dto.getItemId());
                materialDtoList.add(dto);
            }else if(Constants.XMLB.XM.equals(dto.getItemCode())){
                //项目
                itemList.add(dto.getItemId());
                itemDtoList.add(dto);
            }else if (Constants.XMLB.YP.equals(dto.getItemCode())){
                //药品
                drugList.add(dto.getItemId());
                baseDrugDTOList.add(dto);
            }
        }
        inptVisitDTO.setTotalCost(totalCost);

        baseItemDTO.setIds(itemList);
        Map<String,Object> itemMap = new HashMap<>();
        itemMap.put("hospCode", hospCode);
        itemMap.put("baseItemDTO",baseItemDTO);

        baseMaterialDTO.setIds(materialList);
        Map<String,Object> materialMap = new HashMap<>();
        materialMap.put("hospCode", hospCode);
        materialMap.put("baseMaterialDTO",baseMaterialDTO);

        baseDrugDTO.setIds(drugList);
        Map<String,Object> drugMap = new HashMap<>();
        drugMap.put("hospCode", hospCode);
        drugMap.put("baseDrugDTO",baseDrugDTO);

        //如果有项目补记账
        if(itemList.size() > 0){
            //从数据库中查询出项目的信息
            WrapperResponse<List<BaseItemDTO>> listItemResponse = baseItemService_consumer.queryAll(itemMap);
            //数据库中项目的信息集合
            List<BaseItemDTO> items = listItemResponse.getData();

            //为项目费用集合补充信息
            itemDtoList.forEach(dto -> {
                BaseItemDTO id = new BaseItemDTO();
                for(BaseItemDTO fbi:items){
                    if(fbi.getHospCode().equals(dto.getHospCode()) &&
                            fbi.getId().equals(dto.getItemId())){
                        id=fbi;
                        break;
                    }
                }
                dto.setId(SnowflakeUtils.getId());
                dto.setItemName(id.getName());
                dto.setPrice(id.getPrice());
                dto.setSpec(id.getSpec());//规格
                dto.setNumUnitCode(id.getUnitCode());//单位
                dto.setTotalNumUnitCode(id.getUnitCode());//总单位
                dto.setRealityPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()));
                dto.setTotalPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//总金额
                dto.setTotalNum(dto.getNum());
                dto.setBfcCode(id.getBfcCode());
                dto.setHospCode(hospCode);
                dto.setStatusCode(Constants.ZTBZ.ZC);//状态标志 1:正常 2:冲红 3:被冲红
                dto.setIsCost(Constants.SF.S);

                dto.setCrteId(userId);
                dto.setCrteName(userName);
                dto.setCrteTime(dto.getCrteTime() == null ? DateUtils.getNow() :dto.getCrteTime());
                dto.setCostTime(dto.getCostTime() == null ? dto.getCrteTime() :dto.getCostTime());
                dto.setSourceCode(Constants.FYLYFS.BJZ);//费用来源方式 8：补记账
                dto.setSourceDeptId(loginDeptId);//来源科室
            });
        }

        //如果有材料补记账
        if(materialList.size() > 0){
            //从数据库中查询出材料的信息
            WrapperResponse<List<BaseMaterialDTO>> listMaterialResponse = baseMaterialService_consumer.queryBaseMaterialDTOs(materialMap);

            //数据库中材料的信息集合
            List<BaseMaterialDTO> materials = listMaterialResponse.getData();

            //为材料费用集合补充信息
            materialDtoList.forEach(dto -> {
                BaseMaterialDTO md = new BaseMaterialDTO();
                for(BaseMaterialDTO fbm:materials){
                    if(fbm.getHospCode().equals(dto.getHospCode()) &&
                            fbm.getId().equals(dto.getItemId())){
                        md=fbm;
                        break;
                    }
                }
                //费用id
                String costId = SnowflakeUtils.getId();
                dto.setId(costId);

                dto.setItemName(md.getName());
                dto.setSpec(md.getSpec());//规格
                //总数量的单位等于拆零单位
                if(dto.getTotalNumUnitCode().equals(md.getSplitUnitCode())){
                    dto.setPrice(md.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getSplitPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//总金额
                }else{
                    dto.setPrice(md.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//总金额
                }

                dto.setBfcCode(md.getBfcCode());
                dto.setHospCode(hospCode);
                dto.setStatusCode(Constants.ZTBZ.ZC);//状态标志 1:正常 2:冲红 3:被冲红
                dto.setIsCost(Constants.SF.F);//是否记账 默认是未记账，发药后改为已记账 0:否 1:是

                dto.setCrteId(userId);
                dto.setCrteName(userName);
                dto.setCrteTime(DateUtils.getNow());
//                if (dto.getCrteTime() == null) {
//                    dto.setCrteTime(DateUtils.getNow());
//                }
                dto.setSourceCode(Constants.FYLYFS.BJZ);//费用来源方式 8：补记账
                dto.setSourceDeptId(loginDeptId);//来源科室
                dto.setIsWait(Constants.SF.F);

                //2:个人自备 3：科室自备
                if(!(dto.getUseCode().equals(Constants.YYXZ.GRZB) || dto.getUseCode().equals(Constants.YYXZ.KSZB))){
                    dto.setIsWait(Constants.SF.S);
                    //只要不是个人自备或科室自备，则需要去库房取

                    //判断库存(延用开医嘱判断库存的方法) 20210512-------------------- pengbo
                    InptAdviceDTO inptAdviceDTO = new InptAdviceDTO ();
                    inptAdviceDTO.setHospCode(dto.getHospCode());
                    //医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔
                    String wjsykc = "0";
                    Map paramMap = new HashMap ();
                    paramMap.put("hospCode",dto.getHospCode());
                    paramMap.put("code","MZYS_CF_WJSFYKC");
                    SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(paramMap).getData();
                    if(sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())){
                        wjsykc = sysParameterDTO.getValue();
                    }
                    inptAdviceDTO.setWjsykc(wjsykc);
                    inptAdviceDTO.setPharId(dto.getPharId());
                    inptAdviceDTO.setItemId(dto.getItemId());
                    inptAdviceDTO.setItemName(dto.getItemName());
                    inptAdviceDTO.setUnitCode(dto.getTotalNumUnitCode());
                    inptAdviceDTO.setTotalNum(dto.getTotalNum());
                    if (ListUtils.isEmpty(inptAdviceDAO.checkStock(inptAdviceDTO))) {
                        throw new AppException(inptAdviceDTO.getItemName()+":库存不足");
                    }
                    //判断库存(延用开医嘱判断库存的方法) 20210512-------------------- pengbo

                    PharInWaitReceiveDTO wdto = new PharInWaitReceiveDTO();
                    wdto.setId(SnowflakeUtils.getId());
                    wdto.setHospCode(hospCode);
                    wdto.setAdviceId(dto.getIatId());//医嘱id
                    wdto.setGroupNo(dto.getIatdGroupNo()+"");//医嘱组号
                    wdto.setVisitName(dto.getVisitId());//就诊id
                    wdto.setBabyId(dto.getBabyId());
                    wdto.setItemCode(dto.getItemCode());
                    wdto.setItemId(dto.getItemId());
                    wdto.setItemName(dto.getItemName());
                    wdto.setSpec(md.getSpec());//规格
                    wdto.setSplitRatio(md.getSplitRatio());//拆分比
                    wdto.setSplitUnitCode(md.getSplitUnitCode());//拆零单位代码
                    wdto.setUnitCode(md.getUnitCode());
                    //总数量的单位等于拆零单位
                    if(dto.getTotalNumUnitCode().equals(md.getSplitUnitCode())){
                        wdto.setSplitNum(dto.getTotalNum());//拆零数量
                        wdto.setNum(BigDecimalUtils.divide(dto.getTotalNum(), md.getSplitRatio()).setScale(2,   BigDecimal.ROUND_HALF_UP));//总数量
                    }else{
                        wdto.setSplitNum(BigDecimalUtils.multiply(dto.getTotalNum(),md.getSplitRatio() ));//拆零数量
                        wdto.setNum(dto.getTotalNum());//总数量
                    }
                    wdto.setSplitPrice(md.getSplitPrice());//拆零单价
                    wdto.setPrice(md.getPrice());//大单位单价
                    wdto.setTotalPrice(dto.getTotalPrice());//总金额

                    //拆零数量
                    wdto.setCurrUnitCode(dto.getTotalNumUnitCode());
                    wdto.setIsEmergency(Constants.SF.F);
                    wdto.setIsBack(Constants.SF.F);
                    wdto.setStatusCode(Constants.LYZT.DL);
                    wdto.setUsageCode(dto.getUsageCode());
                    wdto.setVisitId(dto.getVisitId());
                    wdto.setGroupNo("0");

                    wdto.setStatusCode(Constants.FYZT.DL);//0、待领，1、请领，2、配药，3、发药
                    wdto.setPharId(dto.getPharId());//发药药房id
                    wdto.setUseCode(dto.getUseCode());//用药性质
                    wdto.setDeptId(loginDeptId);//申请科室
                    wdto.setCostId(costId);//费用明细id
                    wdto.setCrteId(userId);
                    wdto.setCrteName(userName);
                    wdto.setCrteTime(DateUtils.getNow());

                    getPharInWaitDtoList.add(wdto);
                }
            });
        }

        //如果有药品补记账
        if(drugList.size() > 0){
            //从数据库中查询出药品的信息
            WrapperResponse<List<BaseDrugDTO>> listDrugResponse = baseDrugService_consumer.queryAll(drugMap);

            //数据库中药品的信息集合
            List<BaseDrugDTO> drugs = listDrugResponse.getData();

            //为药品费用集合补充信息
            baseDrugDTOList.forEach(dto -> {
                BaseDrugDTO drug = new BaseDrugDTO();
                for(BaseDrugDTO bdd:drugs){
                    if(bdd.getHospCode().equals(dto.getHospCode()) &&
                            bdd.getId().equals(dto.getItemId())){
                        drug=bdd;
                        break;
                    }
                }
                //费用id
                String costId = SnowflakeUtils.getId();
                dto.setId(costId);

                dto.setItemName(drug.getGoodName());
                dto.setSpec(drug.getSpec());//规格
                //总数量的单位等于拆零单位
                if(dto.getTotalNumUnitCode().equals(drug.getSplitUnitCode())){
                    dto.setPrice(drug.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getSplitPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//总金额
                }else{
                    dto.setPrice(drug.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//总金额
                }

                dto.setHospCode(hospCode);
                dto.setStatusCode(Constants.ZTBZ.ZC);//状态标志 1:正常 2:冲红 3:被冲红
                dto.setIsCost(Constants.SF.F);
                dto.setCrteId(userId);
                dto.setCrteName(userName);
                dto.setCrteTime(DateUtils.getNow());
                if(dto.getCostTime() == null) {
                    dto.setCostTime(DateUtils.getNow());
                }
                /*if (dto.getCrteTime() == null) {
                    dto.setCrteTime(DateUtils.getNow());
                }*/
                dto.setSourceCode(Constants.FYLYFS.BJZ);//费用来源方式 8：补记账
                dto.setSourceDeptId(loginDeptId);//来源科室
                dto.setIsWait(Constants.SF.F);

                //2:个人自备 3：科室自备
                if(!(dto.getUseCode().equals(Constants.YYXZ.GRZB) || dto.getUseCode().equals(Constants.YYXZ.KSZB))){
                    dto.setIsWait(Constants.SF.S);
                    //只要不是个人自备或科室自备，则需要去库房取
                    //判断库存(延用开医嘱判断库存的方法) 20210512-------------------- pengbo
                    InptAdviceDTO inptAdviceDTO = new InptAdviceDTO ();
                    inptAdviceDTO.setHospCode(dto.getHospCode());
                    //医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔
                    String wjsykc = "0";
                    Map paramMap = new HashMap ();
                    paramMap.put("hospCode",dto.getHospCode());
                    paramMap.put("code","MZYS_CF_WJSFYKC");
                    SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(paramMap).getData();
                    if(sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())){
                        wjsykc = sysParameterDTO.getValue();
                    }
                    inptAdviceDTO.setWjsykc(wjsykc);
                    inptAdviceDTO.setPharId(dto.getPharId());
                    inptAdviceDTO.setItemId(dto.getItemId());
                    inptAdviceDTO.setItemName(dto.getItemName());
                    inptAdviceDTO.setUnitCode(dto.getTotalNumUnitCode());
                    inptAdviceDTO.setTotalNum(dto.getTotalNum());
                    if (ListUtils.isEmpty(inptAdviceDAO.checkStock(inptAdviceDTO))) {
                        throw new AppException(inptAdviceDTO.getItemName()+":库存不足");
                    }
                    //判断库存(延用开医嘱判断库存的方法) 20210512-------------------- pengbo
                    PharInWaitReceiveDTO wdto = new PharInWaitReceiveDTO();
                    wdto.setId(SnowflakeUtils.getId());
                    wdto.setHospCode(hospCode);
                    wdto.setAdviceId(dto.getIatId());//医嘱id
                    wdto.setGroupNo(dto.getIatdGroupNo()+"");//医嘱组号
                    wdto.setVisitName(dto.getVisitId());//就诊id
                    wdto.setBabyId(dto.getBabyId());
                    wdto.setItemCode(dto.getItemCode());
                    wdto.setItemId(dto.getItemId());
                    wdto.setItemName(dto.getItemName());
                    wdto.setSpec(drug.getSpec());//规格
                    wdto.setUnitCode(drug.getUnitCode());//单位
                    wdto.setSplitRatio(drug.getSplitRatio());//拆分比
                    wdto.setSplitUnitCode(drug.getSplitUnitCode());//拆零单位代码
                    //拆零数量
                    wdto.setIsBack(Constants.SF.F);
                    wdto.setStatusCode(Constants.LYZT.DL);
                    wdto.setUsageCode(dto.getUsageCode());
                    wdto.setVisitId(dto.getVisitId());
                    wdto.setUnitCode(drug.getUnitCode());
                    //总数量的单位等于拆零单位
                    if(dto.getTotalNumUnitCode().equals(drug.getSplitUnitCode())){
                        wdto.setSplitNum(dto.getTotalNum());//拆零数量
                        wdto.setNum(BigDecimalUtils.divide(dto.getTotalNum(), drug.getSplitRatio()).setScale(2,   BigDecimal.ROUND_HALF_UP));//总数量
                    }else{
                        wdto.setSplitNum(BigDecimalUtils.multiply(dto.getTotalNum(),drug.getSplitRatio() ));//拆零数量
                        wdto.setNum(dto.getTotalNum());//总数量
                    }
                    wdto.setSplitPrice(drug.getSplitPrice());//拆零单价
                    wdto.setPrice(drug.getPrice());//大单位单价
                    wdto.setTotalPrice(dto.getTotalPrice());//总金额
                    wdto.setStatusCode(Constants.FYZT.DL);//0、待领，1、请领，2、配药，3、发药
                    wdto.setPharId(dto.getPharId());//发药药房id
                    wdto.setUseCode(dto.getUseCode());//用药性质
                    wdto.setDeptId(loginDeptId);//申请科室
                    wdto.setCostId(costId);//费用明细id
                    wdto.setCrteId(userId);
                    wdto.setCrteName(userName);
                    wdto.setCrteTime(DateUtils.getNow());
                    wdto.setCurrUnitCode(dto.getTotalNumUnitCode());//当前单位
                    getPharInWaitDtoList.add(wdto);
                }
            });
        }

    }


    /**
     * 处理门诊手术补记账费用记录
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/28 9:40
     **/
    private void handleOutptCostData(List<OutptCostDTO> outptCostDTOList, String hospCode, String userId, String userName, String loginDeptId, List<OutptCostDTO> itemDtoList, List<OutptCostDTO> materialDtoList, List<OutptCostDTO> baseDrugDTOList, OutptVisitDTO outptVisitDTO) {
        List<String> itemList = new ArrayList<>();//项目id集合
        List<String> materialList = new ArrayList<>();//材料id集合
        List<String> drugList = new ArrayList<>();//药品id集合

        //患者信息(用于更新病人表总费用)
        OutptCostDTO idto = outptCostDTOList.get(0);
        outptVisitDTO.setHospCode(hospCode);
        outptVisitDTO.setId(idto.getVisitId());

        Map<String,String> param = new HashMap<>();
        param.put("hospCode",hospCode);
        param.put("id",outptVisitDTO.getId());


        OutptVisitDTO outptVisitDTO1 = outptVisitService_consumer.queryByVisitID(param);

        // 设置医生信息
        for (OutptCostDTO costDTO: outptCostDTOList ) {
            costDTO.setDoctorId(outptVisitDTO1.getDoctorId());
            costDTO.setDoctorName(outptVisitDTO1.getDoctorName());
            costDTO.setDeptId(outptVisitDTO1.getDeptId());
        }

        //----从源数据中获得项目或材料的id集合，并从数据库中取得项目或材料的信息
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setHospCode(hospCode);

        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setHospCode(hospCode);

        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setHospCode(hospCode);
        //合计金额
        BigDecimal totalCost = new BigDecimal(0);
        for(OutptCostDTO dto:outptCostDTOList){
            // 数据主键
            dto.setId(SnowflakeUtils.getId());
            dto.setIsTechnologyOk(Constants.SF.S);
            dto.setTechnologyOkId(userId);
            dto.setTechnologyOkName(userName);
            dto.setTechnologyOkTime(DateUtils.getNow());
            dto.setHospCode(hospCode);
            //状态标志 1:正常 2:冲红 3:被冲红
            dto.setStatusCode(Constants.ZTBZ.ZC);
            dto.setCrteId(userId);
            dto.setCrteName(userName);
            //费用来源方式 8：补记账
            dto.setSourceCode(Constants.FYLYFS.BJZ);
            //来源科室
            dto.setSourceDeptId(loginDeptId);
            dto.setOpId(idto.getAdviceId());
            //2:个人自备 3：科室自备 库存校验
            if(!(dto.getUseCode().equals(Constants.YYXZ.GRZB) || dto.getUseCode().equals(Constants.YYXZ.KSZB))) {
                inventoryCheck(hospCode,dto);
            }
            totalCost = BigDecimalUtils.add(totalCost, dto.getTotalPrice());

            if(Constants.XMLB.CL.equals(dto.getItemCode())){
                //材料
                materialList.add(dto.getItemId());
                materialDtoList.add(dto);
            }else if(Constants.XMLB.XM.equals(dto.getItemCode())){
                //项目
                itemList.add(dto.getItemId());
                itemDtoList.add(dto);
            }else if (Constants.XMLB.YP.equals(dto.getItemCode())){
                //药品
                drugList.add(dto.getItemId());
                baseDrugDTOList.add(dto);
            }
        }
        baseItemDTO.setIds(itemList);
        Map<String,Object> itemMap = new HashMap<>();
        itemMap.put("hospCode", hospCode);
        itemMap.put("baseItemDTO",baseItemDTO);

        baseMaterialDTO.setIds(materialList);
        Map<String,Object> materialMap = new HashMap<>();
        materialMap.put("hospCode", hospCode);
        materialMap.put("baseMaterialDTO",baseMaterialDTO);

        baseDrugDTO.setIds(drugList);
        Map<String,Object> drugMap = new HashMap<>();
        drugMap.put("hospCode", hospCode);
        drugMap.put("baseDrugDTO",baseDrugDTO);

        //如果有项目补记账
        if(itemList.size() > 0){
            //从数据库中查询出项目的信息
            WrapperResponse<List<BaseItemDTO>> listItemResponse = baseItemService_consumer.queryAll(itemMap);
            //数据库中项目的信息集合
            List<BaseItemDTO> items = listItemResponse.getData();
            //为项目费用集合补充信息
            itemDtoList.forEach(dto -> {
                BaseItemDTO id = new BaseItemDTO();
                for(BaseItemDTO fbi:items){
                    if(fbi.getHospCode().equals(dto.getHospCode()) &&
                            fbi.getId().equals(dto.getItemId())){
                        id=fbi;
                        break;
                    }
                }
                dto.setItemName(id.getName());
                dto.setPrice(id.getPrice());
                dto.setSpec(id.getSpec());//规格
                dto.setNumUnitCode(id.getUnitCode());//单位
                dto.setRealityPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()));
                dto.setTotalPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()));//总金额
                dto.setTotalNum(dto.getNum());
                dto.setBfcCode(id.getBfcCode());
                dto.setCrteTime(dto.getCrteTime() == null ? DateUtils.getNow() :dto.getCrteTime());
                dto.setTechnologyOkTime(dto.getTechnologyOkTime() == null ? dto.getCrteTime() :dto.getTechnologyOkTime());
            });
        }

        //如果有材料补记账
        if(materialList.size() > 0){
            //从数据库中查询出材料的信息
            WrapperResponse<List<BaseMaterialDTO>> listMaterialResponse = baseMaterialService_consumer.queryBaseMaterialDTOs(materialMap);

            //数据库中材料的信息集合
            List<BaseMaterialDTO> materials = listMaterialResponse.getData();

            //为材料费用集合补充信息
            materialDtoList.forEach(dto -> {
                BaseMaterialDTO md = new BaseMaterialDTO();
                for(BaseMaterialDTO fbm:materials){
                    if(fbm.getHospCode().equals(dto.getHospCode()) &&
                            fbm.getId().equals(dto.getItemId())){
                        md=fbm;
                        break;
                    }
                }

                dto.setItemName(md.getName());
                dto.setSpec(md.getSpec());//规格
                //总数量的单位等于拆零单位
                if(dto.getSplitUnitCode().equals(md.getSplitUnitCode())){
                    dto.setPrice(md.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getSplitPrice(), dto.getTotalNum()));//总金额
                }else{
                    dto.setPrice(md.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getPrice(), dto.getTotalNum()));//总金额
                }

                dto.setBfcCode(md.getBfcCode());
            });
        }

        //如果有药品补记账
        if(drugList.size() > 0){
            //从数据库中查询出药品的信息
            WrapperResponse<List<BaseDrugDTO>> listDrugResponse = baseDrugService_consumer.queryAll(drugMap);

            //数据库中药品的信息集合
            List<BaseDrugDTO> drugs = listDrugResponse.getData();

            //为药品费用集合补充信息
            baseDrugDTOList.forEach(dto -> {
                BaseDrugDTO drug = new BaseDrugDTO();
                for(BaseDrugDTO bdd:drugs){
                    if(bdd.getHospCode().equals(dto.getHospCode()) &&
                            bdd.getId().equals(dto.getItemId())){
                        drug=bdd;
                        break;
                    }
                }
                dto.setItemName(drug.getGoodName());
                dto.setSpec(drug.getSpec());//规格
                //数量的单位等于拆零单位
                if(dto.getNumUnitCode().equals(drug.getSplitUnitCode())){
                    dto.setPrice(drug.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getSplitPrice(), dto.getTotalNum()));//总金额
                }else{
                    dto.setPrice(drug.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getPrice(), dto.getTotalNum()));//总金额
                }
            });
        }
    }


    /**
       * 库存校验(项目不需要校验库存)
       * @param dto 门诊计费项目
       * @param hospCode 医院编码
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/28 14:17
    **/
    private void inventoryCheck(String hospCode, OutptCostDTO dto) {

        if(Constants.XMLB.XM.equals(dto.getItemCode())) {
            return;
        }
        //获取系统参数
        Map<String ,Object> params = new HashMap<>();
        params.put("codeList",new String[]{"MZYS_CF_WJSFYKC"});
        params.put("hospCode",hospCode);
        Map<String, SysParameterDTO> mapParameter = sysParameterService_consumer.getParameterByCodeList(params).getData();
        SysParameterDTO sysPrameter = MapUtils.get(mapParameter, "MZYS_CF_WJSFYKC");
        String wjsykc = sysPrameter == null ? "24" : sysPrameter.getValue();
        // 检查库存
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setWjsykc(wjsykc);
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setItemName(dto.getItemName());
        inptAdviceDTO.setUnitCode(dto.getNumUnitCode());
        inptAdviceDTO.setTotalNum(dto.getTotalNum());
        if (ListUtils.isEmpty(inptAdviceDAO.checkStock(inptAdviceDTO))) {
            throw new AppException(inptAdviceDTO.getItemName()+":库存不足");
        }

    }

    @Override
    public PageDTO queryBabyPatientInfoPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        //    <--------是否开启大人婴儿合并 liuliyun 2021/09/04------>
        SysParameterDTO sysParameterDTO =null;
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", inptVisitDTO.getHospCode());
        isInsureUnifiedMap.put("code", "BABY_INSURE_FEE");
        sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        // 开启大人婴儿合并结算
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())){
            List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryMergeInptVisitPage(inptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        //    <--------是否开启大人婴儿合并 liuliyun 2021/09/04------>
        }else {
            List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryBabyInptVisitPage(inptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        }
    }

    @Override
    public WrapperResponse<Boolean> updateAccountRepairLong(Map<String, Object> map) {
        InptLongCostDTO longCostDto =   (InptLongCostDTO) map.get("longCostDto");
        String userId =   (String) map.get("userId");
        String userName =   (String) map.get("userName");
        if(longCostDto == null ){
            throw new RuntimeException("未获取到需要取消的费用信息!");
        }
        InptLongCostDTO updateLongCost = new InptLongCostDTO();
        updateLongCost.setId(longCostDto.getId());
        updateLongCost.setHospCode(longCostDto.getHospCode());
        updateLongCost.setIsCancel("1");
        updateLongCost.setCancelId(userId);
        updateLongCost.setCancelName(userName);
        updateLongCost.setCancelRemark(DateUtils.format()+",["+userName+"]护士站长期费用补记账停止");
        updateLongCost.setCancelTime(DateUtils.getNow());
        updateLongCost.setEndTime(DateUtils.getNow());

        return WrapperResponse.success(bedLongCostDAO.updateAccountRepairLong(updateLongCost) >0);
    }
}
