package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.outpt.medictocare.bo.MedicToCareBO;
import cn.hsa.module.outpt.medictocare.dao.MedicToCareDAO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuelong.chen
 * @create 2022-02-28 9:26
 * @desc医转养controller层
 **/
@Component
@Slf4j
public class MedicToCareBOImpl extends HsafBO implements MedicToCareBO {

    @Resource
    private MedicToCareDAO medicToCareDAO;

    @Override
    public PageDTO queryPage(MedicToCareDTO medicToCareDTO) {
        PageHelper.startPage(medicToCareDTO.getPageNo(),medicToCareDTO.getPageSize());
        List<MedicToCareDTO> medicToCareDTOS;
        //区分门诊住院
        if("1".equals(medicToCareDTO.getVisitType())){
            medicToCareDTOS  = medicToCareDAO.queryMZPage(medicToCareDTO);
        }else {
             medicToCareDTOS = medicToCareDAO.queryZYPage(medicToCareDTO);
        }
        return PageDTO.of(medicToCareDTOS);
    }

    @Override
    public PageDTO queryHospitalPatientInfoPage(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public Map<String, Object> getMedicToCareInfoById(Map<String, Object> map) {
        String id = MapUtils.get(map,"id");
        return medicToCareDAO.getMedicToCareInfoById(id);
    }

    /**
     * @Menthod: queryHospitalPatientInfoPage()
     * @Desrciption: 分页查询出医院病人信息表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryMedicToCareInfoPage(MedicToCareDTO medicToCareDTO) {
        PageHelper.startPage(medicToCareDTO.getPageNo(),medicToCareDTO.getPageSize());
        List<MedicToCareDTO> medicToCareDTOS = medicToCareDAO.queryMedicToCareInfoPage(medicToCareDTO);
        return PageDTO.of(medicToCareDTOS);
    }
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    @Override
    public Boolean insertMedicToCare(MedicToCareDTO medicToCareDTO) {
        //校验数据
        if(StringUtils.isEmpty(medicToCareDTO.getApplyCompanyCode())){
            throw new RuntimeException("未传入申请机构编码");
        }
        //补充数据,患者信息数据
        medicToCareDTO = this.replenishInfo(medicToCareDTO);
        Map<String, Object> visitInfo = new HashMap<>();
        this.handeleVisit(visitInfo, medicToCareDTO);
        //插入本地表
        medicToCareDAO.insertMedicDate(medicToCareDTO);
        //调用API
        //todo 调用api传输

        return true;
    }

    @Override
    public Boolean updateMedicToCare(Map<String, Object> map) {
        return medicToCareDAO.updateMedicToCare(map);
    }

    //补充数据
    private MedicToCareDTO replenishInfo(MedicToCareDTO medicToCareDTO){
        MedicToCareDTO  medicToCareDTO1 = medicToCareDAO.queryVisitById(medicToCareDTO);
        if (medicToCareDTO1 == null) {
            throw new RuntimeException("未查询到相关就诊信息");
        }
        //todo填充实体类
        medicToCareDTO.setStatusCode("0");
        medicToCareDTO.setName(medicToCareDTO1.getName());
        medicToCareDTO.setGenderCode(medicToCareDTO1.getGenderCode());
        medicToCareDTO.setAge(medicToCareDTO1.getAge());
        medicToCareDTO.setAgeUnitCode(medicToCareDTO1.getAgeUnitCode());
        medicToCareDTO.setCertNo(medicToCareDTO1.getCertNo());
        medicToCareDTO.setPhone(medicToCareDTO1.getPhone());
        medicToCareDTO.setName(medicToCareDTO1.getName());
        medicToCareDTO.setName(medicToCareDTO1.getName());
        medicToCareDTO.setName(medicToCareDTO1.getName());
        medicToCareDTO.setName(medicToCareDTO1.getName());
        return medicToCareDTO;
    }

    //补充数据,患者信息数据
    private void handeleVisit(Map<String, Object> visitInfo, MedicToCareDTO medicToCareDTO) {
        visitInfo.put("apply_id",medicToCareDTO.getId());
        visitInfo.put("name",medicToCareDTO.getName());
        visitInfo.put("id_no",medicToCareDTO.getCertNo());
        visitInfo.put("sex",medicToCareDTO.getGenderCode());
        visitInfo.put("age",medicToCareDTO.getAge());
        visitInfo.put("phone",medicToCareDTO.getPhone());
        visitInfo.put("referral_category",medicToCareDTO.getChangeType());
        visitInfo.put("dept",medicToCareDTO.getDeptId());
        visitInfo.put("source_org",medicToCareDTO.getHospCode());
        visitInfo.put("expect_checkin_data",medicToCareDTO.getHopeInTime());
        visitInfo.put("medical_info_id",medicToCareDTO.getVisitId());
        visitInfo.put("remark",medicToCareDTO.getRemark());
        visitInfo.put("whether_checkin",medicToCareDTO.getIsHouse());
        visitInfo.put("checkin_data",medicToCareDTO.getRealityInTime());
        visitInfo.put("checkin_bed",medicToCareDTO.getHouseBed());
        visitInfo.put("nursing_level",medicToCareDTO.getNusreTypeCode());
        visitInfo.put("apply_date",medicToCareDTO.getCrteTime());
        visitInfo.put("applicant",medicToCareDTO.getCrteName());
    }


}