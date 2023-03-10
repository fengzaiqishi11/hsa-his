package cn.hsa.inpt.nurseexcutecard.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceExecDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurseexcutecard.bo.NurseExcuteCardBO;
import cn.hsa.module.inpt.nurseexcutecard.dao.NurseExcuteCardDAO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.bo.SysParameterBO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.bcel.internal.generic.ATHROW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.inpt.nurseexcutecard.bo.impl
 * @Class_name:: NurseExcuteCardBOImpl
 * @Description: 护理执行卡业务逻辑实现层
 * @Author: fuhui
 * @Date: 2020/9/8 13:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Component
@Slf4j
public class NurseExcuteCardBOImpl extends HsafBO implements NurseExcuteCardBO {

    /**
     * 护理执行卡数据访问层
     */
    @Resource
    private NurseExcuteCardDAO nurseExcuteCardDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource

    private BaseAdviceService baseAdviceService_consumer;

    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Method queryPatient()
     * @Desrciption 分页查询病人就诊信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/8 10:01
     * @Return 病人分页信息
     **/
    @Override
    public PageDTO queryPatient(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        List<InptVisitDTO> inptVisitDTOList = nurseExcuteCardDAO.queryPatient(inptVisitDTO);
        return PageDTO.of(inptVisitDTOList);
    }

    /**
     * @Method queryDocterAdvice()
     * @Desrciption 1.根据诊断id 查询医嘱信息
     * 2. 根据系统参数 查询出大输液的用法参数
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/8 10:01
     * @Return 医嘱分页信息
     **/
    @Override
    public PageDTO queryDocterAdvice(InptVisitDTO inptVisitDTO) {
        // 懒加载设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        // 查询记录
        List<InptAdviceDTO> adviceDTOList = getNurseExcuteCardRecord(inptVisitDTO);
        return PageDTO.of(adviceDTOList);
    }

    /**
     * 查询护理记录
     * @param inptVisitDTO
     * @return
     */
    private List<InptAdviceDTO> getNurseExcuteCardRecord(InptVisitDTO inptVisitDTO) {
        String printType = inptVisitDTO.getPrintType();
        if(StringUtils.isEmpty(printType)){
            throw new AppException("打印类型为空");
        }
        // 由于输液瓶贴、静脉输液卡、输液一览卡、留观输液瓶贴为同一组数据，需根据不同的打印单据类型过滤打印状态，增加一个是否共享（isShared）字段来区分
        if (StringUtils.isNotEmpty(printType) && ("1".equals(printType) || "2".equals(printType) || "3".equals(printType) || "5".equals(printType) || "14".equals(printType) || "16".equals(printType))){
            inptVisitDTO.setIsShared(Constants.SF.S);
        } else {
            inptVisitDTO.setIsShared(Constants.SF.F);
        }
        if(!StringUtils.isEmpty(inptVisitDTO.getVisitIds())){
            String[] split = inptVisitDTO.getVisitIds().split(",");
            List<String> strings = Arrays.asList(split);
            inptVisitDTO.setIds(strings);
        }
        Map map1 = new HashMap();
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setCode("HLZXK");
        sysCodeDetailDTO.setValue(printType);
        sysCodeDetailDTO.setHospCode(inptVisitDTO.getHospCode());
        map1.put("hospCode",inptVisitDTO.getHospCode());
        map1.put("sysCodeDetailDTO",sysCodeDetailDTO);
        WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(map1);
        List<SysCodeDetailDTO> data = listWrapperResponse.getData();
        String value = data.get(0).getRemark();
        value = value.replace("'", "");
        if(!StringUtils.isEmpty(value)){
            String[] split = value.split(",");
            List<String> dsyList = Arrays.asList(split);
            inptVisitDTO.setUsageCodeList(dsyList);
        }
        inptVisitDTO.setIsStop(Constants.SF.F);
        List<String> strings = new ArrayList<>();
        //治疗护理执行卡
        if ("8".equals(printType) || "7".equals(printType)) {
            strings.add("2");
            strings.add("6");
            inptVisitDTO.setTypeCodeList(strings);
        }
        //Lis列表
        if ("12".equals(printType)) {
            strings.add("3");
            inptVisitDTO.setTypeCodeList(strings);
        }
        //饮食执行卡
        if ("9".equals(printType)) {
            strings.add("4");
            inptVisitDTO.setTypeCodeList(strings);
        }
        if("15".equals(printType)) {
            inptVisitDTO.setIsStop(Constants.SF.S);
        }

        List<InptAdviceDTO> adviceDTOList = new ArrayList<>();
        // 获取系统参数，判断执行卡输液瓶贴是返回一条还是多条，默认是多条
        String code = "INFUSION_BOTTLE_STICKER";
        SysParameterDTO sysParameterDTO = this.getSysParameter(code, inptVisitDTO.getHospCode());
        // 增加是否不关联领药表的系统参数，默认关联
        this.relevancePharCode(inptVisitDTO);
        if ("1".equals(printType)) {
            // 输液瓶贴
            if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue()) && "1".equals(sysParameterDTO.getValue())) {
                adviceDTOList = nurseExcuteCardDAO.queryDocterAdvice(inptVisitDTO);
            } else {
                // 瓶贴多条 不分组
                adviceDTOList = nurseExcuteCardDAO.queryDocterAdviceMany(inptVisitDTO);
            }
        } else {
            adviceDTOList = nurseExcuteCardDAO.queryDocterAdvice(inptVisitDTO);
        }
        return adviceDTOList;
    }


    // 获取系统参数
    private SysParameterDTO getSysParameter(String code, String hospCode) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("code", code);
        WrapperResponse<SysParameterDTO> response = sysParameterService_consumer.getParameterByCode(map);
        return response.getData();
    }

    /**
    * @Menthod queryDocterAdviceAll
    * @Desrciption 查询所有护理执行卡
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/3 14:50
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
    **/
    @Override
    public List<InptAdviceDTO> queryDocterAdviceAll(InptVisitDTO inptVisitDTO) {
      List<InptAdviceDTO> adviceDTOList = getNurseExcuteCardRecord(inptVisitDTO);
      return adviceDTOList;
    }

  /**
     * @Method: AllPrinting()
     * @Descrition: 护理执行卡批量打印病人数据的功能
     * @Pramas: AllPrinting
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/21
     * @Retrun: List<List < InptAdviceDTO>>
     */
    @Override
    public List<List<InptAdviceDTO>> AllPrinting(InptVisitDTO inptVisitDTO) {
        List<InptAdviceDTO> adviceDTOList = nurseExcuteCardDAO.queryDocterAdvice(inptVisitDTO);
        Map<String, List<InptAdviceDTO>> collectMap = adviceDTOList.stream().collect(Collectors.groupingBy(InptAdviceDTO::getVisitId));
        List<List<InptAdviceDTO>> adviceCollectList = new ArrayList<>();
        for (Map.Entry<String, List<InptAdviceDTO>> map : collectMap.entrySet()) {
            adviceCollectList.add(map.getValue());
        }
        return adviceCollectList;
    }

    /**
     * @Method: updatePrintFlag()
     * @Descrition: 打印完成以后 修改打印的标识符
     * @Pramas: inptAdviceDTO包含：打印的医嘱id集合 对应的病人的就诊id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/22
     * @Retrun: boolean
     */
    @Override
    public Boolean updatePrintFlag(InptAdviceDTO inptAdviceDTO) {
        if (ListUtils.isEmpty(inptAdviceDTO.getIds())){
            throw new RuntimeException("未选择打印的记录");
        }
        /*if (ListUtils.isEmpty(inptAdviceDTO.getInptAdviceExecIds())){
            throw new RuntimeException("未选择打印的记录");
        }*/
        if (StringUtils.isNotEmpty(inptAdviceDTO.getPrintType()) && ("1".equals(inptAdviceDTO.getPrintType()) || "2".equals(inptAdviceDTO.getPrintType()) || "3".equals(inptAdviceDTO.getPrintType()) || "5".equals(inptAdviceDTO.getPrintType()) || "14".equals(inptAdviceDTO.getPrintType()) || "16".equals(inptAdviceDTO.getPrintType()))){
            /**
             * 拼接打印标志：由于输液瓶贴(1)、输液治疗卡(2)、输液一栏卡(3)、皮下肌肉执行卡(5)、留观输液瓶贴(14)、输液治疗卡(16)共用同一条数据，打印标志需根据不同的类型设置不同的打印标志
             *  isPrint(mapToString): {'1':'0'; '2':'0'; '3':'0'; '14':'0'; '16':'0'} 未打印状态
             *  isPrint(mapToString): {'1':'1'; '2':'1'; '3':'1'; '14':'1'; '16':'1'} 已打印状态
             */
            SysParameterDTO sysParameter = this.getSysParameter("INFUSION_BOTTLE_STICKER", inptAdviceDTO.getHospCode());
            if ("1".equals(inptAdviceDTO.getPrintType())) {
                if (sysParameter != null && StringUtils.isNotEmpty(sysParameter.getValue()) && "1".equals(sysParameter.getValue())) {
                    // 开启参数，页面输液瓶贴展示一条，根据页面医嘱id修改执行表所有打印状态
                } else {
                    // 默认不开启，根据执行id修改打印状态
                    inptAdviceDTO.setInptAdviceExecIds(inptAdviceDTO.getIds());
                    inptAdviceDTO.setIds(null);
                }
            }
            //根据医嘱执行id查询出对应的医嘱执行记录
            List<InptAdviceExecDTO> list =  nurseExcuteCardDAO.queryExcuteByIds(inptAdviceDTO);
            if (!ListUtils.isEmpty(list)){
                for (InptAdviceExecDTO inptAdviceExecDTO : list) {
                    String isPrintJson = inptAdviceExecDTO.getIsPrint();
                    if ("0".equals(isPrintJson)){
                        Map<String, String> m = new HashMap<>();
                        m.put(inptAdviceDTO.getPrintType(), isPrintJson);
                        isPrintJson = JSON.toJSONString(m);
                    }
                    Map<String, String> m = (Map) JSON.parse(isPrintJson);
                    m.put(inptAdviceDTO.getPrintType(), Constants.SF.S);
                    inptAdviceExecDTO.setIsPrint(JSON.toJSONString(m));
                }
                return nurseExcuteCardDAO.updatePrintFlagByType(list);
            }else {
                return false;
            }
        } else {
            inptAdviceDTO.setIsPrint(Constants.SF.S);
//            if(!ListUtils.isEmpty(inptAdviceDTO.getInptAdviceExecIds())){
            if(!ListUtils.isEmpty(inptAdviceDTO.getIds())){
              return nurseExcuteCardDAO.updatePrintFlag(inptAdviceDTO);
            } else {
              return false;
            }
        }
    }

    /**
    * @Menthod queryPatientNurseCard
    * @Desrciption 根据护理执行卡 查询患者信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/2 13:55
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPatientNurseCard(InptVisitDTO inptVisitDTO) {
      String printType = inptVisitDTO.getPrintType();
      if(!StringUtils.isEmpty(printType)) {
        Map map1 = new HashMap();
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setCode("HLZXK");
        sysCodeDetailDTO.setValue(printType);
        sysCodeDetailDTO.setHospCode(inptVisitDTO.getHospCode());
        map1.put("hospCode",inptVisitDTO.getHospCode());
        map1.put("sysCodeDetailDTO",sysCodeDetailDTO);
        // 查询改执行卡类的用法
        WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(map1);
        List<SysCodeDetailDTO> data = listWrapperResponse.getData();
        String value = data.get(0).getRemark();
        value = value.replace("'", "");
        if(!StringUtils.isEmpty(value)){
          String[] split = value.split(",");
          List<String> dsyList = Arrays.asList(split);
          inptVisitDTO.setUsageCodeList(dsyList);
        }
        inptVisitDTO.setIsStop(Constants.SF.F);
        List<String> strings = new ArrayList<>();
        //治疗护理执行卡
        if ("8".equals(printType) || "7".equals(printType)) {
          strings.add("2");
          strings.add("6");
          inptVisitDTO.setTypeCodeList(strings);
        }
        //Lis列表
        if ("12".equals(printType)) {
          strings.add("3");
          inptVisitDTO.setTypeCodeList(strings);
        }
        //饮食执行卡
        if ("9".equals(printType)) {
          strings.add("4");
          inptVisitDTO.setTypeCodeList(strings);
        }
        if("15".equals(printType)) {
          inptVisitDTO.setIsStop(Constants.SF.S);
        }
      }
      // 增加是否不关联领药表的系统参数，默认关联
      this.relevancePharCode(inptVisitDTO);
      PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
      List<String> visitIds = nurseExcuteCardDAO.queryVistId(inptVisitDTO);
      if(ListUtils.isEmpty(visitIds)) {
        return null;
      }
      InptVisitDTO newVisit = new InptVisitDTO();
      newVisit.setIds(visitIds);
      newVisit.setHospCode(inptVisitDTO.getHospCode());
      List<InptVisitDTO> inptVisitDTOS = nurseExcuteCardDAO.queryPatientByIds(newVisit);
      return PageDTO.of(inptVisitDTOS);
    }

    // 是否关联领药申请参数
    private void relevancePharCode(InptVisitDTO inptVisitDTO) {
        String pharCode = "HLZXK_RELEVANCE_PHAR";
        SysParameterDTO pharSysParamDTO = this.getSysParameter(pharCode, inptVisitDTO.getHospCode());
        if (pharSysParamDTO != null && StringUtils.isNotEmpty(pharSysParamDTO.getValue()) && "1".equals(pharSysParamDTO.getValue())) {
            // 不关联，参数值为1
            inptVisitDTO.setIsRelevance(Constants.SF.S);
        } else {
            // 关联，默认关联
            inptVisitDTO.setIsRelevance(Constants.SF.F);
        }
    }
}
