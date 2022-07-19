package cn.hsa.module.inpt.drawMedicine.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.drawMedicine.bo
 * @Class_name: DrawMedicineBO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/11 16:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface DrawMedicineBO {

    /**
     * @Method beforehandDrawMedicine
     * @Desrciption item
       @params [billList]
     * @Author chenjun
     * @Date   2020/9/11 16:47
     * @Return java.lang.Boolean
    **/
    Boolean saveBeforehandDrawMedicine(Map map);


  PharInWaitReceiveDTO checkDrawMedicineStock(Map map);

    /**
     * @Method getApplyDetailsList
     * @Desrciption 查询领药明细数据
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO getApplyDetailsList(Map map);

    /**
     * @Method getApplySummaryList
     * @Desrciption 查询领药汇总信息
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO getApplySummaryList(Map map);

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单集合
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO getPharInReceiveList(Map map);

    /**
     * @Method getPharInReceiveListDetail
     * @Desrciption 查询领药单明细集合
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO getPharInReceiveListDetail(Map map);

    /**
     * @Method cancelDrawMedicine
     * @Desrciption 取消配药
     * @params [map]
     * @Author chenjun
     * @Date 2020-12-17 15:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteCancelDrawMedicine(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 修改状态为紧急领药
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 10:59
    * @Return java.lang.Boolean
    **/
    Boolean updateUrgentMedicine(Map map);

    /**
     * @param map
     * @Method saveAdvanceTakeMedicine
     * @Desrciption 提前领药
     * @params map
     * @Author pengbo
     * @Date 2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    void saveAdvanceTakeMedicine(Map<String, Object> map);

    List<Map<String, Object>> getTableTqlyData(Map<String, Object> map);

    /**
     * @param map
     * @Method updateAdvance
     * @Desrciption 取消提前领药
     * @params map
     * @Author pengbo
     * @Date 2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    Boolean updateAdvance(Map<String, Object> map);

  List<Map<String, Object>> queryAllVisit(Map<String, Object> map);
}
