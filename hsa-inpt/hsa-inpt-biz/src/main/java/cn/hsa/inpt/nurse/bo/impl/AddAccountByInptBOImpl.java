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
    * @Desrciption ????????????????????????
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:37
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    @Override
    public PageDTO queryPatientInfoPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        // ?????????????????? ????????????????????????????????????
        SysParameterDTO mergeParameterDTO =null;
        Map<String, Object> isMergeParam = new HashMap<>();
        isMergeParam.put("hospCode", inptVisitDTO.getHospCode());
        isMergeParam.put("code", "BABY_INSURE_FEE");
        mergeParameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
        List<InptVisitDTO> inptVisitDTOS = null;
        if (mergeParameterDTO != null && Constants.SF.S.equals(mergeParameterDTO.getValue())) {
            // ??????????????????
            inptVisitDTOS = inptVisitDAO.queryInptVisitPage(inptVisitDTO);
        } else {
            inptVisitDTOS =  inptVisitDAO.queryInptVisitPageNoMerge(inptVisitDTO);
        }
        return PageDTO.of(inptVisitDTOS);
    }

    /**
     * @Method queryPatientInfoPage
     * @Desrciption ????????????????????????
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
    * @Desrciption ?????????????????????
    * @param map
    * @Author liuqi1  ????????????????????? by marong 9???29???
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
          throw new AppException("??????????????????????????????,???????????????"+visitDTOBalance.getQfCose()+"???,??????????????????"+visitDTOBalance.getTotalBalance()+"???");
        }
        List<InptCostDTO> itemDtoList = new ArrayList<>();//??????????????????
        List<InptCostDTO> materialDtoList = new ArrayList<>();//??????????????????
        List<InptCostDTO> baseDrugDTOList = new ArrayList<>();
        List<PharInWaitReceiveDTO> getPharInWaitDtoList = new ArrayList<>();//?????????????????????????????????
        InptVisitDTO inptVisitDTO = new InptVisitDTO();//???????????????????????????????????????id???????????????????????????

        //??????????????????   //????????????????????? by marong 9???29???
        handleCostData(inptCostDTOs, hospCode, userId, userName, loginDeptId, itemDtoList, materialDtoList, baseDrugDTOList,inptVisitDTO,getPharInWaitDtoList);
        //????????????
        itemDtoList.addAll(materialDtoList);
        //????????????
        itemDtoList.addAll(baseDrugDTOList);
        if(!ListUtils.isEmpty(itemDtoList)){
            //??????????????????
            inptPreferentialRecalculate(itemDtoList);
            //????????????
            inptCostDAO.insertInptCostBatch(itemDtoList);
        }
        //?????????????????????
        if(StringUtils.isNotEmpty(inptCostDTOs.get(0).getIsSs()) && Constants.SF.S.equals(inptCostDTOs.get(0).getIsSs())){
            InptCostDTO inptCostDTO = inptCostDTOs.get(0);
            inptCostDTO.setSourceDeptId(loginDeptId);
            inptCostDAO.updateOperInfoRecord(inptCostDTO);
        }
        if(!ListUtils.isEmpty(getPharInWaitDtoList)){
            Map<String,Object> m = new HashMap<>();
            m.put("hospCode",hospCode);
            m.put("pharInWaitReceiveDTOs",getPharInWaitDtoList);
            //????????????????????????????????????????????? 2021-08-05-pengbo???
            // pharInWaitReceiveService_consumer.insertPharInWaitBatch(m);
            inptCostDAO.insertPharInWaitReceiveBatch(getPharInWaitDtoList);
        }
        //???????????????????????????
        if (StringUtils.isEmpty(inptVisitDTO.getBabyId())) {
            inptVisitDAO.updateTotalCost(inptVisitDTO);
        }
        return true;
    }

    /** ????????????????????? **/
    @Override
    public Boolean saveAddAccountByOutpt(Map<String, Object> map) {
        List<InptCostDTO> inptCostDTOList = MapUtils.get(map, "inptCostDTOs");
        checkAddAccountPamar(inptCostDTOList,map);
        String hospCode = MapUtils.get(map, "hospCode");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String loginDeptId = MapUtils.get(map, "loginDeptId");
        String loginDeptName = MapUtils.get(map, "loginDeptName");
        //??????????????????
        List<OutptCostDTO> itemDtoList = new ArrayList<>();
        List<OutptCostDTO> outptCostDTOS = new ArrayList<>();
        //??????????????????
        List<OutptCostDTO> materialDtoList = new ArrayList<>();
        List<OutptCostDTO> baseDrugDTOList = new ArrayList<>();
        //???????????????????????????????????????id???????????????????????????
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        copyProperties(inptCostDTOList,outptCostDTOS);
        //??????????????????
        handleOutptCostData(outptCostDTOS, hospCode, userId, userName, loginDeptId, itemDtoList, materialDtoList, baseDrugDTOList,outptVisitDTO);
        //????????????
        itemDtoList.addAll(materialDtoList);
        //????????????
        itemDtoList.addAll(baseDrugDTOList);
        if(!ListUtils.isEmpty(itemDtoList)){
            //??????????????????
//            outptPreferentialRecalculate(itemDtoList);
            //????????????
            Map<String,Object> map1 = new HashMap<>();
            map1.put("hospCode",hospCode);
            map1.put("outptCostDTOs",outptCostDTOS);
            ouptCostService_consumer.insertOutptCostBatch(map1);
        }
        //?????????????????????
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

    /** ?????????????????? **/
    public List<InptCostDTO> outptPreferentialRecalculate(List<InptCostDTO> inptCostDTOs){
        if (ListUtils.isEmpty(inptCostDTOs)) {
            return null;
        }
        //????????????id??????????????????
        InptCostDTO inptCostDTO = inptCostDTOs.get(0);

        Map<String,String> param = new HashMap<>(4);
        param.put("hospCode",inptCostDTO.getHospCode());
        param.put("id",inptCostDTO.getVisitId());

        OutptVisitDTO outptVisitDTO = outptVisitService_consumer.queryByVisitID(param);

        if(null == outptVisitDTO) {
            throw new AppException("???????????????????????????????????????????????????????????????????????????"+param);
        }
        //????????????????????????
        Map<String,Object> map = new HashMap<>(4);
        List<Map<String,Object>> costList = new ArrayList<>();
        for(InptCostDTO i:inptCostDTOs){
            Map<String,Object> m = new HashMap<>();
            m.put("id",i.getId());
            m.put("hospCode",i.getHospCode());
            //????????????ID
            m.put("preferentialTypeId",outptVisitDTO.getPreferentialTypeId());
            m.put("itemId",i.getItemId());
            m.put("itemCode",i.getItemCode());
            m.put("bfcId",i.getBfcId());
            //0??????:??????,1:??????
            m.put("type","1");
            m.put("totalPrice",i.getTotalPrice());
            costList.add(m);
        }

        map.put("hospCode",inptCostDTO.getHospCode());
        map.put("costList",costList);
        //???????????????????????????
        WrapperResponse<List<Map<String, Object>>> listWrapperResponse = basePreferentialService_consumer.calculatPreferential(map);
        List<Map<String, Object>> data = listWrapperResponse.getData();

        //???????????????????????????????????????
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
     * ???itemDtoList ?????????????????? outptCostDTOS
     *  ????????????????????????????????????????????????
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
    * @Desrciption ?????????????????????
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
      throw new AppException("?????????????????????,????????????");
    }
    if(StringUtils.isEmpty(hospCode)){
      throw new AppException("????????????????????????");
    }
    if(StringUtils.isEmpty(userId)){
      throw new AppException("?????????????????????");
    }
    if(StringUtils.isEmpty(userName)){
      throw new AppException("?????????????????????");
    }
    if(StringUtils.isEmpty(loginDeptId)){
      throw new AppException("????????????????????????");
    }
    for(InptCostDTO inptCostDTO : inptCostDTOs){
      if(StringUtils.isEmpty(inptCostDTO.getItemId())){
        throw new AppException(inptCostDTO.getItemName()+",??????????????????");
      }
      if(StringUtils.isEmpty(inptCostDTO.getItemCode())){
        throw new AppException(inptCostDTO.getItemName()+",????????????????????????");
      }
      // ????????????
      if (StringUtils.isEmpty(inptCostDTO.getExecDeptId())) {
        throw new AppException(inptCostDTO.getItemName()+":????????????????????????");
      }
      // ????????????
      if (inptCostDTO.getNum() == null) {
        throw new AppException(inptCostDTO.getItemName()+":??????????????????");
      }
      // ????????????
      if (BigDecimalUtils.compareTo(inptCostDTO.getNum(), BigDecimal.valueOf(0)) <= 0) {
        throw new AppException(inptCostDTO.getItemName()+":??????????????????0");
      }
      // ????????????
      if (inptCostDTO.getTotalNum() == null) {
          throw new AppException(inptCostDTO.getItemName()+":?????????????????????");
      }
      // ????????????
      if (BigDecimalUtils.compareTo(inptCostDTO.getTotalNum(), BigDecimal.valueOf(0)) <= 0) {
          throw new AppException(inptCostDTO.getItemName()+":?????????????????????0");
      }
      //?????????????????????  ??????????????????
      if ( (Constants.YYXZ.CG.equals(inptCostDTO.getUseCode())  || Constants.YYXZ.CYDY.equals(inptCostDTO.getUseCode()))
        && (Constants.XMLB.YP.equals(inptCostDTO.getItemCode())  || Constants.XMLB.CL.equals(inptCostDTO.getItemCode()))
        && StringUtils.isEmpty(inptCostDTO.getPharId()) ) {
        throw new AppException(inptCostDTO.getItemName()+":????????????????????????");
      }
      //??????
      if (Constants.XMLB.YP.equals(inptCostDTO.getItemCode()) && StringUtils.isEmpty(inptCostDTO.getRateId())) {
        throw new AppException(inptCostDTO.getItemName()+":??????????????????");
      }
      //??????
      if (Constants.XMLB.YP.equals(inptCostDTO.getItemCode()) && StringUtils.isEmpty(inptCostDTO.getUsageCode())) {
        throw new AppException(inptCostDTO.getItemName()+":??????????????????");
      }
      // ??????????????????
      if(Constants.XMLB.YP.equals(inptCostDTO.getItemCode())&&
              (Constants.YYXZ.CG.equals(inptCostDTO.getUseCode())  || Constants.YYXZ.CYDY.equals(inptCostDTO.getUseCode()))){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("hospCode",hospCode);
            params.put("pharId",inptCostDTO.getPharId());
            params.put("itemId",inptCostDTO.getItemId());
            params.put("hospCode",hospCode);

            List stock = inptCostDAO.checkStock(params);
            if(ListUtils.isEmpty(stock)){
                throw new AppException(inptCostDTO.getItemName()+" ????????????!,???????????????????????????????????????????????????????????????");
            }

      }
    }
    return true;
  }

    /**
     * @Method inptPreferentialRecalculate
     * @Desrciption ??????????????????
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
        //????????????id??????????????????
        InptCostDTO inptCostDTO = inptCostDTOs.get(0);
        InptVisitDTO paramDto = new InptVisitDTO();
        paramDto.setHospCode(inptCostDTO.getHospCode());
        paramDto.setId(inptCostDTO.getVisitId());
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

        //????????????????????????
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> costList = new ArrayList<>();
        for(InptCostDTO i:inptCostDTOs){
            Map<String,Object> m = new HashMap<>();
            m.put("id",i.getId());
            m.put("hospCode",i.getHospCode());
            m.put("preferentialTypeId",inptVisitById.getPreferentialTypeId());//????????????ID
            m.put("itemId",i.getItemId());
            m.put("itemCode",i.getItemCode());
            m.put("bfcId",i.getBfcId());
            m.put("type","1");//0??????:??????,1:??????
            m.put("totalPrice",i.getTotalPrice());
            costList.add(m);
        }

        map.put("hospCode",inptCostDTO.getHospCode());
        map.put("costList",costList);
        //???????????????????????????
        WrapperResponse<List<Map<String, Object>>> listWrapperResponse = basePreferentialService_consumer.calculatPreferential(map);
        List<Map<String, Object>> data = listWrapperResponse.getData();

        //???????????????????????????????????????
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
    * @Desrciption ????????????????????????
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/11/10 17:04
    * @Return void
    **/
    public void inptPreferentialRecalculateOld(List<InptCostDTO> inptCostDTOs){
        //????????????id??????????????????
        InptCostDTO inptCostDTO = inptCostDTOs.get(0);
        InptVisitDTO paramDto = new InptVisitDTO();
        paramDto.setHospCode(inptCostDTO.getHospCode());
        paramDto.setId(inptCostDTO.getVisitId());
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

        //??????preferentialTypeId?????????base_preferential????????????
        Map queryMap = new HashMap();
        BasePreferentialTypeDTO basePreferentialTypeDTO = new BasePreferentialTypeDTO();
        basePreferentialTypeDTO.setHospCode(inptVisitById.getHospCode());
        basePreferentialTypeDTO.setId(inptVisitById.getPreferentialTypeId());
        queryMap.put("hospCode", inptVisitById.getHospCode());
        queryMap.put("basePreferentialTypeDTO", basePreferentialTypeDTO);
        WrapperResponse<List<BasePreferentialDTO>> response =  basePreferentialService_consumer.queryPreferentials(queryMap);
        List<BasePreferentialDTO> preferentialList = response.getData();
        if(ListUtils.isEmpty(preferentialList)){
            //????????????????????????????????????????????? = ???????????????
            for (InptCostDTO icDto : inptCostDTOs) {
                icDto.setRealityPrice(icDto.getTotalPrice());
            }
            return;
        }

        boolean isConfig = false;//??????????????????????????????????????? false:????????????
        //???preferentialList??????????????????type_code?????? 1 ???????????? 2?????? 3??????
        for (InptCostDTO icDto : inptCostDTOs) {
            isConfig = false;
            for (BasePreferentialDTO dto :preferentialList) {
                //????????????????????????
                if(("1".equals(dto.getTypeCode()) && dto.getBizCode().equals(inptCostDTO.getBfcCode()))
                        || (inptCostDTO.getItemId().equals(dto.getItemId()) && inptCostDTO.getItemCode().equals(dto.getItemCode()))){
                    isConfig = true;

                    //????????????????????????in_code 1: ????????? 2: ?????????
                    if("1".equals(dto.getInCode())){
                        //????????????????????????????????? * ????????????
                        BigDecimal preferentialPrice =  BigDecimalUtils.multiply((new BigDecimal(1).subtract(dto.getInScale())), icDto.getTotalPrice());
                        //???????????????????????????????????? - ???????????????
                        BigDecimal realityPrice = BigDecimalUtils.subtract(icDto.getTotalPrice(), preferentialPrice);
                        icDto.setPreferentialPrice(preferentialPrice);
                        icDto.setRealityPrice(realityPrice);
                        break;
                    }else if("2".equals(dto.getInCode())){
                        //???????????????
                        icDto.setPreferentialPrice(dto.getInScale());

                        //??????????????????
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
                //????????????????????????????????????????????? = ???????????????
                icDto.setRealityPrice(icDto.getTotalPrice());
            }
        }
    }


    /**
    * @Method queryBackCostWithAddAccountPage
    * @Desrciption ???????????????????????????
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
     * @Desrciption ??????visitId and soureTypeCode ????????????????????????????????????????????????
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
     * @Desrciption ?????????????????????????????????
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<Boolean> saveAccountRepairLong(Map<String, Object> map) {
        List<InptLongCostDTO> longCostDtoList = MapUtils.get(map, "longCostDtoList");
        if(ListUtils.isEmpty(longCostDtoList)){
            throw new RuntimeException("????????????,?????????????????????????????????!");
        }

        //????????????
        String hospCode = MapUtils.get(map, "hospCode");
        //?????????ID
        String userId = MapUtils.get(map, "userId");
        //???????????????
        String userName = MapUtils.get(map, "userName");
        //????????????ID
        String loginDeptId = MapUtils.get(map, "loginDeptId");
        //??????????????????
        String loginDeptName = MapUtils.get(map, "loginDeptName");

        //???????????????
        List<InptCostDTO> costDtoList = new ArrayList<>();
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(longCostDtoList.get(0).getVisitId());
        inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
        int index = 0;
        for (InptLongCostDTO inptLongCostDTO :longCostDtoList){
            index++;
            if (inptLongCostDTO.getStartTime() == null){
                throw new AppException(inptLongCostDTO.getItemName() + "??????????????????????????????!");
            }
            if (inptLongCostDTO.getTotalNum() == null || BigDecimal.ZERO.equals(inptLongCostDTO.getTotalNum())){
                throw new AppException(inptLongCostDTO.getItemName() + "?????????0?????????,????????????!");
            }
            if (inptLongCostDTO.getStartTime().before(inptVisitDTO.getInTime())){
                throw new AppException(inptLongCostDTO.getItemName() + "???????????????????????????????????????"+ DateUtils.format(inptVisitDTO.getInTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            inptLongCostDTO.setId(SnowflakeUtils.getId());
            inptLongCostDTO.setCrteId(userId);
            inptLongCostDTO.setCrteName(userName);
            inptLongCostDTO.setHospCode(hospCode);
            //?????????????????????LYLX??? : 0????????????1????????????2????????? ....
            inptLongCostDTO.setSourceTypeCode("1");
            Date startTime =  DateUtils.trunc(inptLongCostDTO.getStartTime(),"yyyy-MM-dd");

            //?????????????????????????????????
            Date endTime = DateUtils.trunc(DateUtils.getNow(),"yyyy-MM-dd");;
            if(inptLongCostDTO.getEndTime() != null){
                endTime = DateUtils.trunc(inptLongCostDTO.getEndTime(),"yyyy-MM-dd");
            }

            Date now =  DateUtils.trunc(new Date(),"yyyy-MM-dd");
            //????????????(??????????????????)
            InptVisitDTO paramDto = new InptVisitDTO();
            paramDto.setHospCode(hospCode);
            paramDto.setId(inptLongCostDTO.getVisitId());
            InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

            if (startTime == null || endTime == null){
                throw  new  RuntimeException(inptLongCostDTO.getItemName()+"????????????,????????????????????????");
            }

            //????????????????????????????????????,??????????????????????????????????????????
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
                //????????????
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

        //???????????????????????????
        if (!ListUtils.isEmpty(costDtoList)){
            map.put("inptCostDTOs",costDtoList);
            this.saveAddAccountByInpt(map);
        }

        return WrapperResponse.success(bedLongCostDAO.insertLongCost(longCostDtoList));
    }

    /**
    * @Method handleCostData
    * @Desrciption ??????????????????
     * ????????????????????????????????????????????????????????????????????????
    * @param inptCostDTOs ?????????
    * @param hospCode ??????
    * @param userId ????????????
    * @param userName ??????????????????
    * @param loginDeptId ????????????id
    * @param itemDtoList ??????????????????
    * @param materialDtoList ??????????????????
    * @param getPharInWaitDtoList ?????????????????????????????????
    * @Author liuqi1  ????????????????????? by marong 9???29???
    * @Date   2020/9/3 16:43
    * @Return void
    **/
    private void handleCostData(List<InptCostDTO> inptCostDTOs, String hospCode, String userId, String userName, String loginDeptId,
                                List<InptCostDTO> itemDtoList, List<InptCostDTO> materialDtoList,List<InptCostDTO> baseDrugDTOList,InptVisitDTO inptVisitDTO,
                                List<PharInWaitReceiveDTO> getPharInWaitDtoList) {
        List<String> itemList = new ArrayList<>();//??????id??????
        List<String> materialList = new ArrayList<>();//??????id??????
        List<String> drugList = new ArrayList<>();//??????id??????

        //????????????(??????????????????????????????)
        InptCostDTO idto = inptCostDTOs.get(0);
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(idto.getVisitId());

        //????????????(??????????????????)
        InptVisitDTO paramDto = new InptVisitDTO();
        paramDto.setHospCode(hospCode);
        paramDto.setId(inptVisitDTO.getId());
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(paramDto);

        //???????????????,????????????,????????????
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

        //----???????????????????????????????????????id?????????????????????????????????????????????????????????
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setHospCode(hospCode);

        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setHospCode(hospCode);

        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setHospCode(hospCode);

        BigDecimal totalCost = new BigDecimal(0);//????????????
        for(InptCostDTO dto:inptCostDTOs){
            dto.setIsOk(Constants.SF.S);
            dto.setOkId(userId);
            dto.setOkName(userName);
            dto.setOkTime(DateUtils.getNow());
            dto.setHospCode(hospCode);
            totalCost = BigDecimalUtils.add(totalCost, dto.getTotalPrice());

            if(Constants.XMLB.CL.equals(dto.getItemCode())){
                //??????
                materialList.add(dto.getItemId());
                materialDtoList.add(dto);
            }else if(Constants.XMLB.XM.equals(dto.getItemCode())){
                //??????
                itemList.add(dto.getItemId());
                itemDtoList.add(dto);
            }else if (Constants.XMLB.YP.equals(dto.getItemCode())){
                //??????
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

        //????????????????????????
        if(itemList.size() > 0){
            //???????????????????????????????????????
            WrapperResponse<List<BaseItemDTO>> listItemResponse = baseItemService_consumer.queryAll(itemMap);
            //?????????????????????????????????
            List<BaseItemDTO> items = listItemResponse.getData();

            //?????????????????????????????????
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
                dto.setSpec(id.getSpec());//??????
                dto.setNumUnitCode(id.getUnitCode());//??????
                dto.setTotalNumUnitCode(id.getUnitCode());//?????????
                dto.setRealityPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()));
                dto.setTotalPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//?????????
                dto.setTotalNum(dto.getNum());
                dto.setBfcCode(id.getBfcCode());
                dto.setHospCode(hospCode);
                dto.setStatusCode(Constants.ZTBZ.ZC);//???????????? 1:?????? 2:?????? 3:?????????
                dto.setIsCost(Constants.SF.S);

                dto.setCrteId(userId);
                dto.setCrteName(userName);
                dto.setCrteTime(dto.getCrteTime() == null ? DateUtils.getNow() :dto.getCrteTime());
                dto.setCostTime(dto.getCostTime() == null ? dto.getCrteTime() :dto.getCostTime());
                dto.setSourceCode(Constants.FYLYFS.BJZ);//?????????????????? 8????????????
                dto.setSourceDeptId(loginDeptId);//????????????
            });
        }

        //????????????????????????
        if(materialList.size() > 0){
            //???????????????????????????????????????
            WrapperResponse<List<BaseMaterialDTO>> listMaterialResponse = baseMaterialService_consumer.queryBaseMaterialDTOs(materialMap);

            //?????????????????????????????????
            List<BaseMaterialDTO> materials = listMaterialResponse.getData();

            //?????????????????????????????????
            materialDtoList.forEach(dto -> {
                BaseMaterialDTO md = new BaseMaterialDTO();
                for(BaseMaterialDTO fbm:materials){
                    if(fbm.getHospCode().equals(dto.getHospCode()) &&
                            fbm.getId().equals(dto.getItemId())){
                        md=fbm;
                        break;
                    }
                }
                //??????id
                String costId = SnowflakeUtils.getId();
                dto.setId(costId);

                dto.setItemName(md.getName());
                dto.setSpec(md.getSpec());//??????
                //????????????????????????????????????
                if(dto.getTotalNumUnitCode().equals(md.getSplitUnitCode())){
                    dto.setPrice(md.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getSplitPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//?????????
                }else{
                    dto.setPrice(md.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//?????????
                }

                dto.setBfcCode(md.getBfcCode());
                dto.setHospCode(hospCode);
                dto.setStatusCode(Constants.ZTBZ.ZC);//???????????? 1:?????? 2:?????? 3:?????????
                dto.setIsCost(Constants.SF.F);//???????????? ????????????????????????????????????????????? 0:??? 1:???

                dto.setCrteId(userId);
                dto.setCrteName(userName);
                dto.setCrteTime(DateUtils.getNow());
//                if (dto.getCrteTime() == null) {
//                    dto.setCrteTime(DateUtils.getNow());
//                }
                dto.setSourceCode(Constants.FYLYFS.BJZ);//?????????????????? 8????????????
                dto.setSourceDeptId(loginDeptId);//????????????
                dto.setIsWait(Constants.SF.F);

                //2:???????????? 3???????????????
                if(!(dto.getUseCode().equals(Constants.YYXZ.GRZB) || dto.getUseCode().equals(Constants.YYXZ.KSZB))){
                    dto.setIsWait(Constants.SF.S);
                    //???????????????????????????????????????????????????????????????

                    //????????????(????????????????????????????????????) 20210512-------------------- pengbo
                    InptAdviceDTO inptAdviceDTO = new InptAdviceDTO ();
                    inptAdviceDTO.setHospCode(dto.getHospCode());
                    //???????????????/????????????????????????????????????/??????????????????????????????
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
                        throw new AppException(inptAdviceDTO.getItemName()+":????????????");
                    }
                    //????????????(????????????????????????????????????) 20210512-------------------- pengbo

                    PharInWaitReceiveDTO wdto = new PharInWaitReceiveDTO();
                    wdto.setId(SnowflakeUtils.getId());
                    wdto.setHospCode(hospCode);
                    wdto.setAdviceId(dto.getIatId());//??????id
                    wdto.setGroupNo(dto.getIatdGroupNo()+"");//????????????
                    wdto.setVisitName(dto.getVisitId());//??????id
                    wdto.setBabyId(dto.getBabyId());
                    wdto.setItemCode(dto.getItemCode());
                    wdto.setItemId(dto.getItemId());
                    wdto.setItemName(dto.getItemName());
                    wdto.setSpec(md.getSpec());//??????
                    wdto.setSplitRatio(md.getSplitRatio());//?????????
                    wdto.setSplitUnitCode(md.getSplitUnitCode());//??????????????????
                    wdto.setUnitCode(md.getUnitCode());
                    //????????????????????????????????????
                    if(dto.getTotalNumUnitCode().equals(md.getSplitUnitCode())){
                        wdto.setSplitNum(dto.getTotalNum());//????????????
                        wdto.setNum(BigDecimalUtils.divide(dto.getTotalNum(), md.getSplitRatio()).setScale(2,   BigDecimal.ROUND_HALF_UP));//?????????
                    }else{
                        wdto.setSplitNum(BigDecimalUtils.multiply(dto.getTotalNum(),md.getSplitRatio() ));//????????????
                        wdto.setNum(dto.getTotalNum());//?????????
                    }
                    wdto.setSplitPrice(md.getSplitPrice());//????????????
                    wdto.setPrice(md.getPrice());//???????????????
                    wdto.setTotalPrice(dto.getTotalPrice());//?????????

                    //????????????
                    wdto.setCurrUnitCode(dto.getTotalNumUnitCode());
                    wdto.setIsEmergency(Constants.SF.F);
                    wdto.setIsBack(Constants.SF.F);
                    wdto.setStatusCode(Constants.LYZT.DL);
                    wdto.setUsageCode(dto.getUsageCode());
                    wdto.setVisitId(dto.getVisitId());
                    wdto.setGroupNo("0");

                    wdto.setStatusCode(Constants.FYZT.DL);//0????????????1????????????2????????????3?????????
                    wdto.setPharId(dto.getPharId());//????????????id
                    wdto.setUseCode(dto.getUseCode());//????????????
                    wdto.setDeptId(loginDeptId);//????????????
                    wdto.setCostId(costId);//????????????id
                    wdto.setCrteId(userId);
                    wdto.setCrteName(userName);
                    wdto.setCrteTime(DateUtils.getNow());

                    getPharInWaitDtoList.add(wdto);
                }
            });
        }

        //????????????????????????
        if(drugList.size() > 0){
            //???????????????????????????????????????
            WrapperResponse<List<BaseDrugDTO>> listDrugResponse = baseDrugService_consumer.queryAll(drugMap);

            //?????????????????????????????????
            List<BaseDrugDTO> drugs = listDrugResponse.getData();

            //?????????????????????????????????
            baseDrugDTOList.forEach(dto -> {
                BaseDrugDTO drug = new BaseDrugDTO();
                for(BaseDrugDTO bdd:drugs){
                    if(bdd.getHospCode().equals(dto.getHospCode()) &&
                            bdd.getId().equals(dto.getItemId())){
                        drug=bdd;
                        break;
                    }
                }
                //??????id
                String costId = SnowflakeUtils.getId();
                dto.setId(costId);

                dto.setItemName(drug.getGoodName());
                dto.setSpec(drug.getSpec());//??????
                //????????????????????????????????????
                if(dto.getTotalNumUnitCode().equals(drug.getSplitUnitCode())){
                    dto.setPrice(drug.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getSplitPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//?????????
                }else{
                    dto.setPrice(drug.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getPrice(), dto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));//?????????
                }

                dto.setHospCode(hospCode);
                dto.setStatusCode(Constants.ZTBZ.ZC);//???????????? 1:?????? 2:?????? 3:?????????
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
                dto.setSourceCode(Constants.FYLYFS.BJZ);//?????????????????? 8????????????
                dto.setSourceDeptId(loginDeptId);//????????????
                dto.setIsWait(Constants.SF.F);

                //2:???????????? 3???????????????
                if(!(dto.getUseCode().equals(Constants.YYXZ.GRZB) || dto.getUseCode().equals(Constants.YYXZ.KSZB))){
                    dto.setIsWait(Constants.SF.S);
                    //???????????????????????????????????????????????????????????????
                    //????????????(????????????????????????????????????) 20210512-------------------- pengbo
                    InptAdviceDTO inptAdviceDTO = new InptAdviceDTO ();
                    inptAdviceDTO.setHospCode(dto.getHospCode());
                    //???????????????/????????????????????????????????????/??????????????????????????????
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
                        throw new AppException(inptAdviceDTO.getItemName()+":????????????");
                    }
                    //????????????(????????????????????????????????????) 20210512-------------------- pengbo
                    PharInWaitReceiveDTO wdto = new PharInWaitReceiveDTO();
                    wdto.setId(SnowflakeUtils.getId());
                    wdto.setHospCode(hospCode);
                    wdto.setAdviceId(dto.getIatId());//??????id
                    wdto.setGroupNo(dto.getIatdGroupNo()+"");//????????????
                    wdto.setVisitName(dto.getVisitId());//??????id
                    wdto.setBabyId(dto.getBabyId());
                    wdto.setItemCode(dto.getItemCode());
                    wdto.setItemId(dto.getItemId());
                    wdto.setItemName(dto.getItemName());
                    wdto.setSpec(drug.getSpec());//??????
                    wdto.setUnitCode(drug.getUnitCode());//??????
                    wdto.setSplitRatio(drug.getSplitRatio());//?????????
                    wdto.setSplitUnitCode(drug.getSplitUnitCode());//??????????????????
                    //????????????
                    wdto.setIsBack(Constants.SF.F);
                    wdto.setStatusCode(Constants.LYZT.DL);
                    wdto.setUsageCode(dto.getUsageCode());
                    wdto.setVisitId(dto.getVisitId());
                    wdto.setUnitCode(drug.getUnitCode());
                    //????????????????????????????????????
                    if(dto.getTotalNumUnitCode().equals(drug.getSplitUnitCode())){
                        wdto.setSplitNum(dto.getTotalNum());//????????????
                        wdto.setNum(BigDecimalUtils.divide(dto.getTotalNum(), drug.getSplitRatio()).setScale(2,   BigDecimal.ROUND_HALF_UP));//?????????
                    }else{
                        wdto.setSplitNum(BigDecimalUtils.multiply(dto.getTotalNum(),drug.getSplitRatio() ));//????????????
                        wdto.setNum(dto.getTotalNum());//?????????
                    }
                    wdto.setSplitPrice(drug.getSplitPrice());//????????????
                    wdto.setPrice(drug.getPrice());//???????????????
                    wdto.setTotalPrice(dto.getTotalPrice());//?????????
                    wdto.setStatusCode(Constants.FYZT.DL);//0????????????1????????????2????????????3?????????
                    wdto.setPharId(dto.getPharId());//????????????id
                    wdto.setUseCode(dto.getUseCode());//????????????
                    wdto.setDeptId(loginDeptId);//????????????
                    wdto.setCostId(costId);//????????????id
                    wdto.setCrteId(userId);
                    wdto.setCrteName(userName);
                    wdto.setCrteTime(DateUtils.getNow());
                    wdto.setCurrUnitCode(dto.getTotalNumUnitCode());//????????????
                    getPharInWaitDtoList.add(wdto);
                }
            });
        }

    }


    /**
     * ???????????????????????????????????????
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/28 9:40
     **/
    private void handleOutptCostData(List<OutptCostDTO> outptCostDTOList, String hospCode, String userId, String userName, String loginDeptId, List<OutptCostDTO> itemDtoList, List<OutptCostDTO> materialDtoList, List<OutptCostDTO> baseDrugDTOList, OutptVisitDTO outptVisitDTO) {
        List<String> itemList = new ArrayList<>();//??????id??????
        List<String> materialList = new ArrayList<>();//??????id??????
        List<String> drugList = new ArrayList<>();//??????id??????

        //????????????(??????????????????????????????)
        OutptCostDTO idto = outptCostDTOList.get(0);
        outptVisitDTO.setHospCode(hospCode);
        outptVisitDTO.setId(idto.getVisitId());

        Map<String,String> param = new HashMap<>();
        param.put("hospCode",hospCode);
        param.put("id",outptVisitDTO.getId());


        OutptVisitDTO outptVisitDTO1 = outptVisitService_consumer.queryByVisitID(param);

        // ??????????????????
        for (OutptCostDTO costDTO: outptCostDTOList ) {
            costDTO.setDoctorId(outptVisitDTO1.getDoctorId());
            costDTO.setDoctorName(outptVisitDTO1.getDoctorName());
            costDTO.setDeptId(outptVisitDTO1.getDeptId());
        }

        //----???????????????????????????????????????id?????????????????????????????????????????????????????????
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setHospCode(hospCode);

        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setHospCode(hospCode);

        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setHospCode(hospCode);
        //????????????
        BigDecimal totalCost = new BigDecimal(0);
        for(OutptCostDTO dto:outptCostDTOList){
            // ????????????
            dto.setId(SnowflakeUtils.getId());
            dto.setIsTechnologyOk(Constants.SF.S);
            dto.setTechnologyOkId(userId);
            dto.setTechnologyOkName(userName);
            dto.setTechnologyOkTime(DateUtils.getNow());
            dto.setHospCode(hospCode);
            //???????????? 1:?????? 2:?????? 3:?????????
            dto.setStatusCode(Constants.ZTBZ.ZC);
            dto.setCrteId(userId);
            dto.setCrteName(userName);
            //?????????????????? 8????????????
            dto.setSourceCode(Constants.FYLYFS.BJZ);
            //????????????
            dto.setSourceDeptId(loginDeptId);
            dto.setOpId(idto.getAdviceId());
            //2:???????????? 3??????????????? ????????????
            if(!(dto.getUseCode().equals(Constants.YYXZ.GRZB) || dto.getUseCode().equals(Constants.YYXZ.KSZB))) {
                inventoryCheck(hospCode,dto);
            }
            totalCost = BigDecimalUtils.add(totalCost, dto.getTotalPrice());

            if(Constants.XMLB.CL.equals(dto.getItemCode())){
                //??????
                materialList.add(dto.getItemId());
                materialDtoList.add(dto);
            }else if(Constants.XMLB.XM.equals(dto.getItemCode())){
                //??????
                itemList.add(dto.getItemId());
                itemDtoList.add(dto);
            }else if (Constants.XMLB.YP.equals(dto.getItemCode())){
                //??????
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

        //????????????????????????
        if(itemList.size() > 0){
            //???????????????????????????????????????
            WrapperResponse<List<BaseItemDTO>> listItemResponse = baseItemService_consumer.queryAll(itemMap);
            //?????????????????????????????????
            List<BaseItemDTO> items = listItemResponse.getData();
            //?????????????????????????????????
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
                dto.setSpec(id.getSpec());//??????
                dto.setNumUnitCode(id.getUnitCode());//??????
                dto.setRealityPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()));
                dto.setTotalPrice(BigDecimalUtils.multiply(id.getPrice(), dto.getNum()));//?????????
                dto.setTotalNum(dto.getNum());
                dto.setBfcCode(id.getBfcCode());
                dto.setCrteTime(dto.getCrteTime() == null ? DateUtils.getNow() :dto.getCrteTime());
                dto.setTechnologyOkTime(dto.getTechnologyOkTime() == null ? dto.getCrteTime() :dto.getTechnologyOkTime());
            });
        }

        //????????????????????????
        if(materialList.size() > 0){
            //???????????????????????????????????????
            WrapperResponse<List<BaseMaterialDTO>> listMaterialResponse = baseMaterialService_consumer.queryBaseMaterialDTOs(materialMap);

            //?????????????????????????????????
            List<BaseMaterialDTO> materials = listMaterialResponse.getData();

            //?????????????????????????????????
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
                dto.setSpec(md.getSpec());//??????
                //????????????????????????????????????
                if(dto.getSplitUnitCode().equals(md.getSplitUnitCode())){
                    dto.setPrice(md.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getSplitPrice(), dto.getTotalNum()));//?????????
                }else{
                    dto.setPrice(md.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(md.getPrice(), dto.getTotalNum()));//?????????
                }

                dto.setBfcCode(md.getBfcCode());
            });
        }

        //????????????????????????
        if(drugList.size() > 0){
            //???????????????????????????????????????
            WrapperResponse<List<BaseDrugDTO>> listDrugResponse = baseDrugService_consumer.queryAll(drugMap);

            //?????????????????????????????????
            List<BaseDrugDTO> drugs = listDrugResponse.getData();

            //?????????????????????????????????
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
                dto.setSpec(drug.getSpec());//??????
                //?????????????????????????????????
                if(dto.getNumUnitCode().equals(drug.getSplitUnitCode())){
                    dto.setPrice(drug.getSplitPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getSplitPrice(), dto.getTotalNum()));//?????????
                }else{
                    dto.setPrice(drug.getPrice());
                    dto.setTotalPrice(BigDecimalUtils.multiply(drug.getPrice(), dto.getTotalNum()));//?????????
                }
            });
        }
    }


    /**
       * ????????????(???????????????????????????)
       * @param dto ??????????????????
       * @param hospCode ????????????
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/28 14:17
    **/
    private void inventoryCheck(String hospCode, OutptCostDTO dto) {

        if(Constants.XMLB.XM.equals(dto.getItemCode())) {
            return;
        }
        //??????????????????
        Map<String ,Object> params = new HashMap<>();
        params.put("codeList",new String[]{"MZYS_CF_WJSFYKC"});
        params.put("hospCode",hospCode);
        Map<String, SysParameterDTO> mapParameter = sysParameterService_consumer.getParameterByCodeList(params).getData();
        SysParameterDTO sysPrameter = MapUtils.get(mapParameter, "MZYS_CF_WJSFYKC");
        String wjsykc = sysPrameter == null ? "24" : sysPrameter.getValue();
        // ????????????
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setWjsykc(wjsykc);
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setItemName(dto.getItemName());
        inptAdviceDTO.setUnitCode(dto.getNumUnitCode());
        inptAdviceDTO.setTotalNum(dto.getTotalNum());
        if (ListUtils.isEmpty(inptAdviceDAO.checkStock(inptAdviceDTO))) {
            throw new AppException(inptAdviceDTO.getItemName()+":????????????");
        }

    }

    @Override
    public PageDTO queryBabyPatientInfoPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        //    <--------?????????????????????????????? liuliyun 2021/09/04------>
        SysParameterDTO sysParameterDTO =null;
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", inptVisitDTO.getHospCode());
        isInsureUnifiedMap.put("code", "BABY_INSURE_FEE");
        sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        // ????????????????????????
        Map<String, Object> hMap = new HashMap<>();
        hMap.put("hospCode", inptVisitDTO.getHospCode());
        hMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO1 = sysParameterService_consumer.getParameterByCode(hMap).getData();
        // ??????????????????????????????
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())){
            List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryMergeInptVisitPage(inptVisitDTO);
            if (sysParameterDTO1 != null ) {
                if(!ListUtils.isEmpty(inptVisitDTOS)){
                    for(InptVisitDTO inptVisitDTO1 :inptVisitDTOS){
                        inptVisitDTO1.setOrgCode(sysParameterDTO1.getValue());
                    }
                }
            }
            return PageDTO.of(inptVisitDTOS);
        //    <--------?????????????????????????????? liuliyun 2021/09/04------>
        }else {
            List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryBabyInptVisitPage(inptVisitDTO);
            if (sysParameterDTO1 != null ) {
                if(!ListUtils.isEmpty(inptVisitDTOS)){
                    for(InptVisitDTO inptVisitDTO1 :inptVisitDTOS){
                        inptVisitDTO1.setOrgCode(sysParameterDTO1.getValue());
                    }
                }
            }
            return PageDTO.of(inptVisitDTOS);
        }
    }

    @Override
    public WrapperResponse<Boolean> updateAccountRepairLong(Map<String, Object> map) {
        InptLongCostDTO longCostDto =   (InptLongCostDTO) map.get("longCostDto");
        String userId =   (String) map.get("userId");
        String userName =   (String) map.get("userName");
        if(longCostDto == null ){
            throw new RuntimeException("???????????????????????????????????????!");
        }
        InptLongCostDTO updateLongCost = new InptLongCostDTO();
        updateLongCost.setId(longCostDto.getId());
        updateLongCost.setHospCode(longCostDto.getHospCode());
        updateLongCost.setIsCancel("1");
        updateLongCost.setCancelId(userId);
        updateLongCost.setCancelName(userName);
        updateLongCost.setCancelRemark(DateUtils.format()+",["+userName+"]????????????????????????????????????");
        updateLongCost.setCancelTime(DateUtils.getNow());
        updateLongCost.setEndTime(DateUtils.getNow());

        return WrapperResponse.success(bedLongCostDAO.updateAccountRepairLong(updateLongCost) >0);
    }
}
