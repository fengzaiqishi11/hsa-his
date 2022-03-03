package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.outpt.medictocare.bo.MedicToCareBO;
import cn.hsa.module.outpt.medictocare.dao.MedicToCareDAO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    /**
     * 调用的url
     */
    @Value("${caretomedic.url}")
    private String url;

    
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
        //调用API
        if("1".equals(medicToCareDTO.getChangeType())){
            Map<String, Object> visitInfo = new HashMap<>();
            try{
                //补充数据,患者信息数据
                medicToCareDTO = this.replenishInfo(medicToCareDTO);
                this.handeleVisit(visitInfo, medicToCareDTO);
                this.commonSendInfo(visitInfo);
            }catch (Exception e){
                //异常处理
            }
        }
        //插入本地表
        medicToCareDAO.insertMedicDate(medicToCareDTO);
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
        /*
            *转诊类别：change_type
            *是否入住等待就诊回写is_house
            * 入住床号house_bed
            * 转诊主诉需要前端传入
            * 实际 入住时间reality_in_time等待回写
         */
        //填充实体类
        //医转样申请状态，0---待处理
        medicToCareDTO.setStatusCode("0");
        medicToCareDTO.setName(medicToCareDTO1.getName());
        medicToCareDTO.setGenderCode(medicToCareDTO1.getGenderCode());
        medicToCareDTO.setAge(medicToCareDTO1.getAge());
        medicToCareDTO.setAgeUnitCode(medicToCareDTO1.getAgeUnitCode());
        medicToCareDTO.setCertNo(medicToCareDTO1.getCertNo());
        medicToCareDTO.setPhone(medicToCareDTO1.getPhone());
        medicToCareDTO.setChangeType("1");
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
        visitInfo.put("remark",StringUtils.isEmpty(medicToCareDTO.getRemark())?"":medicToCareDTO.getRemark());
        visitInfo.put("whether_checkin",StringUtils.isEmpty(medicToCareDTO.getIsHouse())?"1":medicToCareDTO.getIsHouse());
        visitInfo.put("checkin_data",medicToCareDTO.getRealityInTime());
        visitInfo.put("checkin_bed",StringUtils.isEmpty(medicToCareDTO.getHouseBed())?"":medicToCareDTO.getHouseBed());
        visitInfo.put("nursing_level",medicToCareDTO.getNusreTypeCode());
        visitInfo.put("apply_date",medicToCareDTO.getCrteTime());
        visitInfo.put("applicant",medicToCareDTO.getApplyId());
    }

    //使用HTTP调用接口
    private Map<String,Object> commonSendInfo(Map<String, Object> visitInfo){
        Map httpParam = new HashMap();
        //发送的数据
        httpParam.put("visitInfo",visitInfo);
        String json = JSONObject.toJSONString(httpParam);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(this.url, json);
        if (StringUtils.isEmpty(resultStr)){
            throw new RuntimeException("失败！");
        }
        //获取回参
        Map<String, Object> m = (Map) JSON.parse(resultStr);
        String resultCode = MapUtils.get(m,"code","");
        if (StringUtils.isEmpty(resultCode)){
            throw new RuntimeException("调用医养接口无响应!");
        }
        if (!"1".equals(resultCode)){
            throw new RuntimeException("调用医养接口错误,原因："+MapUtils.get(m,"message",""));
        }
        return m;
    }

}