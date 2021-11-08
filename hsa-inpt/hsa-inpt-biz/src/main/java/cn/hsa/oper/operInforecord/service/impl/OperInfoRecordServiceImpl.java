package cn.hsa.oper.operInforecord.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.oper.operInforecord.bo.OperInfoRecordBO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.service.OperInfoRecordService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.oper.operInfoRecord.service.impl
 * @Class_name:: SurgicalAnesthesiaServiceImpl
 * @Description:
 * @Author: 马荣
 * @Date: ２０２０／０９／１７　18点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/oper/operInfoRecord")
@Service("operInfoRecordService_provider")
public class OperInfoRecordServiceImpl extends HsafService implements OperInfoRecordService {

    @Resource
    private OperInfoRecordBO operInfoRecordBO;





    /**
     * @Method applySurgery
     * @Param [map]
     * @description   申请登记手术 保存
     * @author marong
     * @date 2020/9/18 8:55
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    @Override
    public WrapperResponse<Boolean> saveSurgery(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return WrapperResponse.success(operInfoRecordBO.saveSurgery(operInfoRecordDTO));
    }

    /**
     * 批量保存手术信息
     *
     * @param map
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/7/6 20:08
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<Boolean> updateSurgeryByAdviceId(Map map) {

        return WrapperResponse.success(operInfoRecordBO.updateSurgeryByAdviceId(map));
    }

    /**
    * @Method updateSurgeryStatus
    * @Param [map]
    * @description 更改手术登记的状态
    * @author marong
    * @date 2020/9/18 17:19
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @Override
    public WrapperResponse<Boolean> updateSurgeryStatus(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return WrapperResponse.success(operInfoRecordBO.updateSurgeryStatus(operInfoRecordDTO));
    }

    /**
    * @Method updateSurgeryInfo
    * @Param [map]
    * @description 手术登记信息更新
    * @author marong
    * @date 2020/9/18 17:19
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @Override
    public WrapperResponse<Boolean> updateSurgeryInfo(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return WrapperResponse.success(operInfoRecordBO.updateSurgeryInfo(operInfoRecordDTO));
    }

    /**
     * 取消手术完成登记
     *
     * @param map 参数
     **/
    @Override
    public WrapperResponse<Boolean> updateSurgeryCompleteToCancel(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return WrapperResponse.success(operInfoRecordBO.updateSurgeryCompleteToCancel(operInfoRecordDTO));
    }

    /**
    * @Method queryOperInfoRecordList
    * @Param [map]
    * @description   获取手术信息
    * @author marong
    * @date 2020/9/22 20:13
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO>>
    * @throws
    */
    @Override
    public WrapperResponse<PageDTO> queryOperInfoRecordList(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return WrapperResponse.success(operInfoRecordBO.queryOperInfoRecordList(operInfoRecordDTO));
    }

    /**
    * @Method checkOperRepeat
    * @Param [operInfoRecordDO]
    * @description   校验手术重复
    * @author marong
    * @date 2020/9/25 17:20
    * @return boolean
    * @throws
    */
    @Override
    public boolean checkOperRepeat(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return operInfoRecordBO.queryOperInfoRecordIsRepeated(operInfoRecordDTO);
    }


    /**
    * @Menthod getOperInfoById
    * @Desrciption  通过id拿到手术记录
     * @param map
    * @Author xingyu.xie
    * @Date   2021/1/14 20:32
    * @Return cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO
    *
     * @return*/
    public WrapperResponse<OperInfoRecordDTO> getOperInfoById(Map map){
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map, "operInfoRecordDTO");
        return WrapperResponse.success(operInfoRecordBO.getOperInfoById(operInfoRecordDTO));
    }

    /**
     * @Menthod: queryOperCostByVisitId
     * @Desrciption: 查询个人手术补记账费用
     * @Param: visit_id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-09 17:37
     * @Return: List<InptCostDTO>
     **/
    @Override
    public WrapperResponse<List<InptCostDTO>> queryOperCostByVisitId(Map<String, Object> paramMap) {
        return WrapperResponse.success(operInfoRecordBO.queryOperCostByVisitId(paramMap));
    }

    /**
     * @Menthod: cancelOper
     * @Desrciption: 取消手术，已核收未申请的状态下取消，statusCode更改未-1
     * @Param: operInfoRecordDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-11 20:19
     * @Return:
     **/
    @Override
    public WrapperResponse<Boolean> cancelOper(Map map) {
        return WrapperResponse.success(operInfoRecordBO.updateOperStatusCode(MapUtils.get(map, "operInfoRecordDTO")));
    }


}
