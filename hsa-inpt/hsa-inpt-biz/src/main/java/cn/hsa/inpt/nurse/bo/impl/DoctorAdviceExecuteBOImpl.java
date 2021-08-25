package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.DoctorAdviceExecuteBO;
import cn.hsa.module.inpt.nurse.dao.InptAdviceExecDAO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *@Package_name: hsa.inpt.nurse.bo.impl
 *@Class_name: DoctorAdviceExecuteBoImpl
 *@Describe: 医嘱执行
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 16:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class DoctorAdviceExecuteBOImpl extends HsafBO implements DoctorAdviceExecuteBO {

    @Resource
    InptAdviceExecDAO inptAdviceExecDao;
    @Resource
    InptAdviceDAO inptAdviceDao;
    @Resource
    InptCostDAO inptCostDAO;
    @Resource
    SysCodeService sysCodeService_consumer;
    //住院医嘱
    @Resource
    private InptAdviceDAO inptAdviceDAO;

    @Resource
    private InptVisitDAO inptVisitDAO;

    /**
    * @Method queryDoctorAdviceExecuteInfo
    * @Desrciption 医嘱执行分页查询
    * @param inptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/2 17:06
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryDoctorAdviceExecuteInfo(InptAdviceExecDTO inptAdviceExecDTO) {
        PageHelper.startPage(inptAdviceExecDTO.getPageNo(),inptAdviceExecDTO.getPageSize());
        List<InptAdviceExecDTO> inptAdviceExecDTOS = inptAdviceExecDao.queryInptAdvicePage(inptAdviceExecDTO);
        return PageDTO.of(inptAdviceExecDTOS);
    }

    /**
    * @Method updateDoctorAdviceExecute
    * @Desrciption 医嘱执行修改
    * @param inptAdviceExecDTOs
    * @Author liuqi1
    * @Date   2020/9/2 17:07
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateDoctorAdviceExecute(List<InptAdviceExecDTO> inptAdviceExecDTOs) {
        if (ListUtils.isEmpty(inptAdviceExecDTOs)) {
            throw new AppException("参数为空");
        }

        for (InptAdviceExecDTO inptAdviceExecDTO: inptAdviceExecDTOs) {
          if("2".equals(inptAdviceExecDTO.getSignCode()) && StringUtils.isEmpty(inptAdviceExecDTO.getExecName())){
            throw new AppException("执行人为空");
          } else if("2".equals(inptAdviceExecDTO.getSignCode()) && inptAdviceExecDTO.getExecTime() == null) {
            throw new AppException("执行人时间为空");
          } else if ("2".equals(inptAdviceExecDTO.getSignCode()) && !StringUtils.isEmpty(inptAdviceExecDTO.getSourceIaId())
            && StringUtils.isEmpty(inptAdviceExecDTO.getIsPositive())){
            throw new AppException("皮试结果为空");
          }else if ("2".equals(inptAdviceExecDTO.getSignCode()) && inptAdviceExecDTO.getExecTime() != null){

              //判断当前执行时间是否小于医嘱时间
              InptAdviceDTO inptAdvice = new InptAdviceDTO() ;
              inptAdvice.setHospCode(inptAdviceExecDTO.getHospCode());
              inptAdvice.setId(inptAdviceExecDTO.getAdviceId());
              inptAdvice = inptAdviceDAO.getInptAdviceById(inptAdvice);
              if(DateUtils.dateCompare(inptAdviceExecDTO.getExecTime(),inptAdvice.getLongStartTime())){
                  throw new AppException("执行时间不能小于医嘱开始时间");
              }
          }
        }

        Map map = new HashMap();
        map.put("hospCode", inptAdviceExecDTOs.get(0).getHospCode());
        map.put("code", "PSJG");
        List<SysCodeSelectDTO> sysCodeSelectDTOList = sysCodeService_consumer.getByCode(map).getData().get("PSJG");
        //医嘱执行批量修改
        int eCount = inptAdviceExecDao.updateInptAdviceExecBatch(inptAdviceExecDTOs);
        //医嘱执行批量更新费用表的执行人
        int costCount = inptCostDAO.updateInptCostExecuteBatch(inptAdviceExecDTOs);
        //皮试根据来源ID,获取主医嘱记录
        List<InptAdviceDTO> adviceList = inptAdviceExecDao.queryPsList(inptAdviceExecDTOs,map.get("hospCode").toString());

        List<InptAdviceDTO> list = new ArrayList<>();
        Map<String,String> map1 = new HashMap();
        inptAdviceExecDTOs.forEach(dto ->{
            InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
            inptAdviceDTO.setId(dto.getAdviceId());
            inptAdviceDTO.setHospCode(dto.getHospCode());
            inptAdviceDTO.setIsPositive(dto.getIsPositive());
            inptAdviceDTO.setExecId(dto.getExecId());
            inptAdviceDTO.setExecName(dto.getExecName());
            inptAdviceDTO.setSecondExecId(dto.getSecondExecId());
            inptAdviceDTO.setSecondExecName(dto.getSecondExecName());
            inptAdviceDTO.setSignCode(dto.getSignCode());
            inptAdviceDTO.setContent(dto.getContent());
            inptAdviceDTO.setSourceIaId(dto.getSourceIaId());
            inptAdviceDTO.setSkinResultCode(dto.getSkinResultCode()); //添加皮试结果

            if (!ListUtils.isEmpty(sysCodeSelectDTOList)) {
                for (SysCodeSelectDTO codeSelectDTO:sysCodeSelectDTOList) {
                    if (codeSelectDTO.getValue().equals(dto.getSkinResultCode())) {
                        String newContent = "(" + codeSelectDTO.getLabel() + ")";
                        map1.put(inptAdviceDTO.getSourceIaId(),newContent+"_"+inptAdviceDTO.getIsPositive());
                        inptAdviceDTO.setContent(inptAdviceDTO.getContent()+ newContent);
                    }
                }
            }
            list.add(inptAdviceDTO);
        });
        //批量更新医嘱
        int aCount = inptAdviceDao.updateInptAdviceBatch(list);
        //更新原医嘱表是否阳性,医嘱内容
          if(map1 != null && map1.size() > 0 && !ListUtils.isEmpty(adviceList)){
          for(String key : map1.keySet()){
            for (int j = 0; j < adviceList.size(); j++) {
              if(!StringUtils.isEmpty(key) && key.equals(adviceList.get(j).getId())){
                adviceList.get(j).setContent(adviceList.get(j).getContent() + map1.get(key).split("_")[0]);
                adviceList.get(j).setIsPositive(map1.get(key).split("_")[1]);
              }
            }
          }
          inptAdviceExecDao.updateIsPositive(adviceList);
        }

        //医嘱更新最近执行之间
        if (!ListUtils.isEmpty(inptAdviceExecDTOs)) {
            inptAdviceDAO.updateLastExeTimeByIds(inptAdviceExecDTOs);
        }

        return (eCount>0 && aCount>0 && costCount>0);
    }

    /**
    * @Menthod updateAdviceExecuteCance
    * @Desrciption 医嘱执行取消
    *
    * @Param
    * [inptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 9:19
    * @Return java.lang.Boolean
    **/
  @Override
  public Boolean updateAdviceExecuteCance(List<InptAdviceExecDTO> inptAdviceExecDTOs) {
    if (ListUtils.isEmpty(inptAdviceExecDTOs)) {
      throw new AppException("参数为空");
    }
    Map map = new HashMap();
    map.put("hospCode", inptAdviceExecDTOs.get(0).getHospCode());
    map.put("code", "PSJG");
    List<SysCodeSelectDTO> sysCodeSelectDTOList = sysCodeService_consumer.getByCode(map).getData().get("PSJG");
    //医嘱执行批量修改
    int eCount = inptAdviceExecDao.updateInptAdviceExecBatchCancel(inptAdviceExecDTOs);
    // 查询最近执行记录
    List<InptAdviceExecDTO> inptAdviceExecDTOS = new ArrayList<>();
    for (int i = 0; i < inptAdviceExecDTOs.size(); i++) {
      InptAdviceExecDTO inptAdviceExecDTO = inptAdviceExecDao.queryAdviceExecLately(inptAdviceExecDTOs.get(i));
      if(inptAdviceExecDTO == null) {
        inptAdviceExecDTO = new InptAdviceExecDTO();
      }
      inptAdviceExecDTO.setHospCode(inptAdviceExecDTOs.get(i).getHospCode());
      inptAdviceExecDTO.setAdviceId(inptAdviceExecDTOs.get(i).getAdviceId());
      inptAdviceExecDTO.setPlanExecTime(inptAdviceExecDTOs.get(i).getPlanExecTime());
      inptAdviceExecDTO.setIsPositive(inptAdviceExecDTOs.get(i).getIsPositive());
      inptAdviceExecDTO.setSourceIaId(inptAdviceExecDTOs.get(i).getSourceIaId());
      inptAdviceExecDTO.setContent(inptAdviceExecDTOs.get(i).getContent());
      inptAdviceExecDTOS.add(inptAdviceExecDTO);
    }
    //医嘱执行批量更新费用表的执行人
    int costCount = inptCostDAO.updateInptCostExecuteBatch(inptAdviceExecDTOS);
    //皮试根据来源ID,获取主医嘱记录
    List<InptAdviceDTO> adviceList = inptAdviceExecDao.queryPsList(inptAdviceExecDTOs,map.get("hospCode").toString());
    List<InptAdviceDTO> list = new ArrayList<>();
    Map<String,String> map1 = new HashMap();
    inptAdviceExecDTOS.forEach(dto ->{
      InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
      inptAdviceDTO.setId(dto.getAdviceId());
      inptAdviceDTO.setHospCode(dto.getHospCode());
      inptAdviceDTO.setExecId(dto.getExecId());
      inptAdviceDTO.setExecName(dto.getExecName());
      inptAdviceDTO.setSecondExecId(dto.getSecondExecId());
      inptAdviceDTO.setSecondExecName(dto.getSecondExecName());
      if(StringUtils.isEmpty(inptAdviceDTO.getSignCode())){
        inptAdviceDTO.setSignCode("1");
      } else {
        inptAdviceDTO.setSignCode(dto.getSignCode());
      }
      inptAdviceDTO.setContent(dto.getContent());
      inptAdviceDTO.setSourceIaId(dto.getSourceIaId());
      if(!StringUtils.isEmpty(dto.getIsPositive())){
        String content = inptAdviceDTO.getContent().substring(0,inptAdviceDTO.getContent().lastIndexOf("("));
        inptAdviceDTO.setContent(content);
      }
      list.add(inptAdviceDTO);
    });
    //批量更新医嘱
    int aCount = inptAdviceDao.updateInptAdviceBatch(list);
    if(!ListUtils.isEmpty(adviceList)){
      for (int i = 0; i < adviceList.size(); i++) {
          String content = adviceList.get(i).getContent().substring(0,adviceList.get(i).getContent().lastIndexOf("("));
          adviceList.get(i).setContent(content);
          adviceList.get(i).setIsPositive(null);
          inptAdviceExecDao.updateIsPositive(adviceList);
      }
    }
    return true;
  }


  @Override
    public PageDTO queryNoMedical(InptAdviceDTO inptAdviceDTO) {

        PageHelper.startPage(inptAdviceDTO.getPageNo(),inptAdviceDTO.getPageSize());
        List<InptAdviceDTO> inptAdviceExecDTOS = inptAdviceDao.queryNoMedical(inptAdviceDTO);
        return PageDTO.of(inptAdviceExecDTOS);
    }
}
