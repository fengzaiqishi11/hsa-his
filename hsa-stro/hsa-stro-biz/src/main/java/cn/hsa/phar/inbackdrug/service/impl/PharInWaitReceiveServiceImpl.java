package cn.hsa.phar.inbackdrug.service.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.phar.pharinbackdrug.bo.PharInWaitReceiveBO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.phar.inbackdrug.service.impl
 *@Class_name: PharInWaitReceiveServiceImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 19:25
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/phar/pharInWait")
@Service("pharInWaitReceiveService_provider")
public class PharInWaitReceiveServiceImpl extends HsafService implements PharInWaitReceiveService {

    @Resource
    PharInWaitReceiveBO pharInWaitReceiveBO;

    /**
    * @Method insertPharInWaitBatch
    * @Desrciption 住院待领批量新增
    * @param map
    * @Author liuqi1
    * @Date   2020/9/3 19:28
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertPharInWaitBatch(Map<String, Object> map) {
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs= MapUtils.get(map, "pharInWaitReceiveDTOs");
        Boolean isSuccess = pharInWaitReceiveBO.insertPharInWaitBatch(pharInWaitReceiveDTOs);
        return WrapperResponse.success(isSuccess);
    }

    /**
    * @Method queryPharInWaitReceive
    * @Desrciption 根据条件查询出待领信息集合
    * @param map
    * @Author liuqi1
    * @Date   2020/9/7 13:47
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @Override
    public WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceive(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map, "pharInWaitReceiveDTO");
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveBO.queryPharInWaitReceive(pharInWaitReceiveDTO);
        return WrapperResponse.success(pharInWaitReceiveDTOS);
    }

    @Override
    public WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceives(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map, "pharInWaitReceiveDTO");
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveBO.queryPharInWaitReceives(pharInWaitReceiveDTO);
        return WrapperResponse.success(pharInWaitReceiveDTOS);
    }

    /**
     * @Method: queryPharInWaitReceiveToIsBack
     * @Description: 获取已退费的记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/6 11:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @Override
    public WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceiveToIsBack(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map, "pharInWaitReceiveDTO");
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveBO.queryPharInWaitReceiveToIsBack(pharInWaitReceiveDTO);
        return WrapperResponse.success(pharInWaitReceiveDTOS);
    }

    /**
    * @Menthod queryPharInWaitReceivePage
    * @Desrciption 根据条件分页查询出待领信息集合
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/9/8 16:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPharInWaitReceivePage(Map<String, Object> map) {
      PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map, "pharInWaitReceiveDTO");
      PageDTO pageDTOS = pharInWaitReceiveBO.queryPharInWaitReceivePage(pharInWaitReceiveDTO);
      return WrapperResponse.success(pageDTOS);
    }

    /**
    * @Menthod updateInWaitStatus
    * @Desrciption 修改待领表状态
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/6 15:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateInWaitStatus(Map map) {
        return WrapperResponse.success(pharInWaitReceiveBO.updateInWaitStatus(MapUtils.get(map, "pharInWaitReceiveDTO")));
    }

    /**
     * @Menthod updateInWaitStatus
     * @Desrciption 修改待领表状态
     *
     * @Param
     * [map]
     *
     * @Author jiahong.yang
     * @Date   2021/5/6 15:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateInWaitStatusByWrIds(Map map) {
      return WrapperResponse.success(pharInWaitReceiveBO.updateInWaitStatusByWrIds(MapUtils.get(map, "pharInWaitReceiveDTO")));
    }

  /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 批量修改为紧急领药
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 11:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
  @Override
  public WrapperResponse<Boolean> updateUrgentMedicine(Map map) {
    return WrapperResponse.success(pharInWaitReceiveBO.updateUrgentMedicine(MapUtils.get(map, "pharInWaitReceiveDTO")));
  }

  @Override
    public WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceiveApply(Map map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map, "pharInWaitReceiveDTO");
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveBO.queryPharInWaitReceiveApply(pharInWaitReceiveDTO);
        return WrapperResponse.success(pharInWaitReceiveDTOS);
    }

    @Override
    public WrapperResponse<Boolean> deletePharInReceive(Map map) {
        pharInWaitReceiveBO.deletePharInReceive(MapUtils.get(map, "pharInReceiveDTO"));
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<Boolean> deletePharInReceiveDetail(Map map) {
        pharInWaitReceiveBO.deletePharInReceiveDetail(MapUtils.get(map, "pharInReceiveDetailDTO"));
        return WrapperResponse.success(true);
    }

    /**
    * @Method updateCostIdBatch
    * @Desrciption 批量更新待领表的费用明细id
    * @param map
    * @Author liuqi1
    * @Date   2020/11/9 20:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateCostIdBatch(Map map) {
        List<InptCostDTO> inptCostDTOs= MapUtils.get(map, "inptCostDTOs");
        Boolean isSussess = pharInWaitReceiveBO.updateCostIdBatch(inptCostDTOs);
        return WrapperResponse.success(isSussess);
    }

    @Override
    public WrapperResponse<List<Map<String, Object>>> queryAllVisit(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = (PharInWaitReceiveDTO) map.get("pharInWaitReceiveDTO");
        return  WrapperResponse.success(pharInWaitReceiveBO.queryAllVisit(pharInWaitReceiveDTO));
    }

}
