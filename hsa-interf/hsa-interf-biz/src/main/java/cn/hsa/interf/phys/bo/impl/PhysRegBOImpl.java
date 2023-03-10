package cn.hsa.interf.phys.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.phys.bo.PhysRegBO;
import cn.hsa.module.interf.phys.dao.PhysRegDAO;
import cn.hsa.module.interf.phys.dto.PhysRegisterDTO;
import cn.hsa.module.interf.phys.dto.PhysSettleDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class PhysRegBOImpl extends HsafBO implements PhysRegBO {
    @Resource
    private PhysRegDAO physRegDAO;

    /**
     * 系统参数
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     * @Description: 获取体检者登记信息
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @Override
    public WrapperResponse<Map> saveRegPhysInfo(Map map) {

        return null;
    }
    /**
     * @Method addVisitByPhys
     * @Desrciption 同步就诊信息
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/7/14 15:00
     * @Return java.lang.Boolean
     */
    @Override
    public Boolean addVisitByPhys(Map map) {
        PhysRegisterDTO physRegisterDTO = MapUtils.get(map, "physRegisterDTO");
        // 体检和就诊对字段
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        // 先把一样的属性复制过去
        BeanUtils.copyProperties(physRegisterDTO, outptVisitDTO);
        // 判断一些必填项不能为空
        // 主键不能为空
        Optional.ofNullable(outptVisitDTO.getId()).orElseThrow(() -> new AppException("传入的就诊id不能为空"));
        // 身份证不能为空
        Optional.ofNullable(outptVisitDTO.getCertNo()).orElseThrow(() -> new AppException("传入的身份证号不能为空"));
        // 是否就诊 默认传过来的是就诊
        outptVisitDTO.setIsVisit(Constants.SF.S);
        // 是否为体检登记标志
        outptVisitDTO.setIsPhys("1");
        // 设置就诊时间
        outptVisitDTO.setVisitTime(DateUtils.getNow());
        int result=0;
        // 存在体检者登记记录则修改否则新增
        if (physRegDAO.queryVisitByPhys(outptVisitDTO) > 0){
            result = physRegDAO.updateVisitByPhys(outptVisitDTO);
        }else{
            result = physRegDAO.addVisitByPhys(outptVisitDTO);
        }
        return result > 0;
    }
    /**
     * @Method addOrUpdateOutptCost
     * @Desrciption 批量插入费用表
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/7/14 16:09
     * @Return java.lang.Boolean
     */
    @Override
    public Boolean addOrUpdateOutptCost(Map map) {
        List<PhysSettleDTO> settleDTOS = MapUtils.get(map, "settleDTOS");
        List<OutptCostDTO> outptCostDTOS = new ArrayList<>();
        if (!ListUtils.isEmpty(settleDTOS)) {
            Map mapS = new HashMap();
            mapS.put("hospCode", settleDTOS.get(0).getHospCode());
            mapS.put("code", "TJ_YHID");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapS).getData();
            settleDTOS.stream().forEach(x->{
                OutptCostDTO outptCostDTO = new OutptCostDTO();
                // id
                outptCostDTO.setId(SnowflakeUtils.getId());
                // hospCode
                outptCostDTO.setHospCode(x.getHospCode());
                // 就诊id
                outptCostDTO.setVisitId(x.getRegisterId());
                // 项目id
                outptCostDTO.setItemId(x.getGroupId());
                // 项目名称
                outptCostDTO.setItemName(x.getGroupName());
                // 原价
                outptCostDTO.setTotalPrice(x.getRealityPrice());
                // 单价
                outptCostDTO.setPrice(x.getRealityPrice());
                // 总数量
                outptCostDTO.setTotalNum(BigDecimalUtils.convert("1"));
                // 数量
                outptCostDTO.setNum(BigDecimalUtils.convert("1"));
                // 计费id ************************ 通过参数取获取
                //优惠id
                outptCostDTO.setBfcId(sysParameterDTO.getValue());
                // 优惠价格
                outptCostDTO.setPreferentialPrice(BigDecimalUtils.convert("0"));
                // 优惠后价格
                outptCostDTO.setRealityPrice(x.getRealityPrice());
                // 状态标志
                outptCostDTO.setStatusCode(x.getStatusCode());
                // 是否结算
                outptCostDTO.setSettleCode(x.getIsSettle());
                // 项目类别设置为项目
                outptCostDTO.setItemCode(Constants.XMLB.XM);
                // 设置创建时间
                outptCostDTO.setCrteTime(DateUtils.getNow());
                // 费用来源方式代码
                outptCostDTO.setSourceCode(Constants.FYLYFS.QTFY);
                outptCostDTOS.add(outptCostDTO);
            });
            // 先查询出已经结算并且未退费的项目
            List<OutptCostDTO> outptCostDTOList = physRegDAO.queryCostPhys(outptCostDTOS.get(0));
            // 删除未结算的项目
            physRegDAO.deleteBatchPhys(outptCostDTOS.get(0));
            List<OutptCostDTO> commonList = new ArrayList<>();
            // 筛选出未插入的费用信息
            if (!ListUtils.isEmpty(outptCostDTOList)){
                for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                    // 去掉重复的（已经插入的费用信息）
                    for(int i = 0; i < outptCostDTOS.size(); i++) {
                        if (outptCostDTOS.get(i).getItemId().equals(outptCostDTO.getItemId())) {
                            outptCostDTOS.remove(i);
                        }
                    }
                }
            }
            int result = 0;
            // 新增费用信息
            if (! ListUtils.isEmpty(outptCostDTOS)) {
                result = physRegDAO.addBatchPhys(outptCostDTOS);
                return result > 0;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * @Description: 同步体检收费组合到项目表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    @Override
    public Boolean insertPhysGroup(Map map) {
        List<Map> list = MapUtils.get(map, "result");
        for (Map resultMap : list){
            resultMap.put("remark", "phys");
            resultMap.put("crteId", "phys");
            resultMap.put("crteName", "体检");
            resultMap.put("crteTime", DateUtils.getNow());
        }
        physRegDAO.insertPhysGroup(list);
        return true;
    }

    /**
     * @Description: 插入退费申请
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    @Override
    public Boolean insertReturn(Map map) {
        List<Map> list = MapUtils.get(map, "result");
        for (Map resultMap : list){
            resultMap.put("id", SnowflakeUtils.getId());
            resultMap.put("status", "2");
            resultMap.put("crteId", "phys");
            resultMap.put("crteName", "体检");
            resultMap.put("crteTime", DateUtils.getNow());
        }
        physRegDAO.updateRCodt(list);
        physRegDAO.insertReturn(list);
        return null;
    }


}
