package cn.hsa.inpt.drawMedicine.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.drawMedicine.bo.DrawMedicineBO;
import cn.hsa.module.inpt.drawMedicine.service.DrawMedicineService;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.drawMedicine.server.impl
 * @Class_name: DrawMedicineServerImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/23 9:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/drawMedicine")
@Service("drawMedicineService_provider")
public class DrawMedicineServiceImpl implements DrawMedicineService {

    @Resource
    DrawMedicineBO drawMedicineBO;

    /**
     * @Method beforehandDrawMedicine
     * @Desrciption 预配药
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveBeforehandDrawMedicine(Map map) {
        drawMedicineBO.saveBeforehandDrawMedicine(map);
        return WrapperResponse.success(true);
    }

    /**
    * @Menthod checkDrawMedicineStock
    * @Desrciption 预配药的时候校验库存
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/7 10:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<PharInWaitReceiveDTO> checkDrawMedicineStock(Map map) {
      return WrapperResponse.success(drawMedicineBO.checkDrawMedicineStock(map));
    }


  /**
     * @Method getApplyDetailsList
     * @Desrciption 查询领药明细数据
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getApplyDetailsList(Map map) {
        PageDTO pageDTO = drawMedicineBO.getApplyDetailsList(map);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method getApplySummaryList
     * @Desrciption 查询领药汇总信息
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getApplySummaryList(Map map) {
        PageDTO pageDTO = drawMedicineBO.getApplySummaryList(map);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单集合
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getPharInReceiveList(Map map) {
        PageDTO pageDTO = drawMedicineBO.getPharInReceiveList(map);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method getPharInReceiveListDetail
     * @Desrciption 查询领药单明细集合
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getPharInReceiveListDetail(Map map) {
        PageDTO pageDTO = drawMedicineBO.getPharInReceiveListDetail(map);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method cancelDrawMedicine
     * @Desrciption 取消配药
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> cancelDrawMedicine(Map map) {
        drawMedicineBO.deleteCancelDrawMedicine(MapUtils.get(map, "pharInReceiveDTO"));
        return WrapperResponse.success(true);
    }

    /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 修改状态为紧急领药
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 10:58
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
  @Override
  public WrapperResponse<Boolean> updateUrgentMedicine(Map map) {
    drawMedicineBO.updateUrgentMedicine(map);
    return WrapperResponse.success(true);
  }

    /**
     * @param map
     * @Method saveAdvanceTakeMedicine
     * @Desrciption 提前领药
     * @params map
     * @Author pengbo
     * @Date 2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> saveAdvanceTakeMedicine(Map<String, Object> map) {
        drawMedicineBO.saveAdvanceTakeMedicine(map);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<List<Map<String, Object>>> getTableTqlyData(Map<String, Object> map) {
        return WrapperResponse.success(drawMedicineBO.getTableTqlyData(map));
    }

    /**
     * @param map
     * @Method updateAdvance
     * @Desrciption 取消提前领药
     * @params map
     * @Author pengbo
     * @Date 2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> updateAdvance(Map<String, Object> map) {
        return WrapperResponse.success(drawMedicineBO.updateAdvance(map));
    }

    @Override
    public WrapperResponse<List<Map<String, Object>>> queryAllVisit(Map<String, Object> map) {
        return WrapperResponse.success(drawMedicineBO.queryAllVisit(map));
    }

}
