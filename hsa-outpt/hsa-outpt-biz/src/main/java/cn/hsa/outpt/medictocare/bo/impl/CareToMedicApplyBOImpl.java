package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.outpt.medictocare.bo.CareToMedicApplyBO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:54
 * @desc
 **/
@Component
@Slf4j
public class CareToMedicApplyBOImpl extends HsafBO implements CareToMedicApplyBO {

    @Resource
    private MedicToCareDAO medicToCareDAO;
    /**
     * 调用的url
     */
    @Value("${medictocare.url}")
    private String url;

    @Override
    public PageDTO queryCareToMedicPage(MedicToCareDTO medicToCareDTO) {
        PageHelper.startPage(medicToCareDTO.getPageNo(),medicToCareDTO.getPageSize());
        List<MedicToCareDTO> medicToCareDTOS = medicToCareDAO.queryMedicToCareInfoPage(medicToCareDTO);
        return PageDTO.of(medicToCareDTOS);
    }

    @Override
    public Map<String, Object> getCareToMedicInfoById(MedicToCareDTO medicToCareDTO) {
        String id = medicToCareDTO.getId();
        return medicToCareDAO.getMedicToCareInfoById(id);
    }
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 养转医申请完后更新
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     * 1.接受申请
     *      1.填充挂号信息
     *      2.插入就诊信息
     * 2.拒绝接受申请
     * 3.插入本地医养表
     * .调用API推送申请信息
     **/
    @Override
    public Boolean updateCareToMedic(Map<String, Object> map) {
        if("1".equals(MapUtils.get(map,"statusCode"))){
            //1.接受申请
            //填充就诊信息
            OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO();
            //插入就诊表
            medicToCareDAO.insertOutPtInfo(outptVisitDTO);
            //填充就诊确认信息接口
            this.sendInfo(map);
        }else if("2".equals(MapUtils.get(map,"statusCode"))){
            //2.拒绝接受申请
            this.sendInfo(map);
        }else {
            //异常
            throw new RuntimeException("请填写申请状态");
        }
        //插入本地
        medicToCareDAO.updateMedicToCare(map);
        //调用api推送
        return true;
    }

    private OutptVisitDTO setOutptVisitDTO() {
        return null;
    }

    //获取就诊确认信息接口
    private Map sendInfo(Map<String, Object> map){
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("apply_id",MapUtils.get(map,"apply_id"));
        paramap.put("apply_status",MapUtils.get(map,"statusCode"));
        paramap.put("affirm_date",new Date());
        return paramap;
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