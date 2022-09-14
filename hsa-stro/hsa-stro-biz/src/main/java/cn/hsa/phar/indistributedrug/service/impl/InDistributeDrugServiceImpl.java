package cn.hsa.phar.indistributedrug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharindistributedrug.bo.InDistributeDrugBO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharindistributedrug.service.InDistributeDrugService;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.phar.indistributedrug.service
* @class_name: InDistributeDrugServiceImpl
* @Description: 住院发药service实现类
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:52
* @Company: 创智和宇
**/
@Slf4j
@HsafRestPath("/service/stro/inDistributeDrug")
@Service("inDistributeDrugService_provider")
public class InDistributeDrugServiceImpl extends HsafService implements InDistributeDrugService {

    @Resource
    private InDistributeDrugBO inDistributeDrugBO;

    /**
     * @Method: getInRecivePage
     * @Description: 住院发药--申请记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:24
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getInRecivePage(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.getInRecivePage(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
     * @Method: getSendInRecivePage
     * @Description: 住院发药记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getSendInRecivePage(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.getSendInRecivePage(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
     * @Method: getInReviceDetailPage
     * @Description: 住院发药--取药明细单
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getInReviceDetailPage(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.getInReviceDetailPage(MapUtils.get(map,"pharInReceiveDetailDTO")));
    }


    /**
    * @Menthod getInReviceDetail
    * @Desrciption  住院配药的明细单打印 按医嘱类型和病人分组
     * @param map
    * @Author xingyu.xie
    * @Date   2020/12/25 9:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>>>
    **/
    @Override
    public WrapperResponse<Map<String, List<PharInReceiveDetailDTO>>> getInReviceDetail(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.getInReviceDetail(MapUtils.get(map,"pharInReceiveDetailDTO")));
    }

    /**
     * @Method: getInSumReviceDetailPage
     * @Description: 住院发药--取药合计单
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getInSumReviceDetailPage(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.getInSumReviceDetailPage(MapUtils.get(map,"pharInReceiveDetailDTO")));
    }

    /**
     * @Method: inDispense
     * @Description: 住院配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:51
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<PharInReceiveDTO> inDispense(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.updateInDispense(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
     * @Method: cancelInDispense
     * @Description: 取消配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:03
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> cancelInDispense(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.updateCancelInDispense(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
     * @Method: inDistribute
     * @Description: 住院发药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:52
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> inDistribute(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.updateInDistribute(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
    * @Menthod queryPatientByOrder
    * @Desrciption 根据配药单号查询患者
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    **/
    public WrapperResponse<List<InptVisitDTO>> queryPatientByOrder(Map map) {
      return WrapperResponse.success(inDistributeDrugBO.queryPatientByOrder(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
    * @Menthod queryDrugByOrderAndVisitId
    * @Desrciption 根据配药单号,就诊id查询病人配药
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO>>
    **/
    public WrapperResponse<List<InptAdviceDTO>> queryDrugByOrderAndVisitId(Map map) {
      return WrapperResponse.success(inDistributeDrugBO.queryDrugByOrderAndVisitId(MapUtils.get(map,"pharInReceiveDTO")));
    }

    /**
    * @Menthod updatePremedication
    * @Desrciption 选择性取消预配药
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 10:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updatePremedication(Map map) {
      return WrapperResponse.success(inDistributeDrugBO.updatePremedication(MapUtils.get(map,"pharInReceiveDTO")));
    }
    /**
     * @Menthod: queryDMDrugByOrderAndVisitId
     * @Desrciption: 根据就诊id查询精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InptAdviceDTO>> queryDMDrugByOrderAndVisitId(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.queryDMDrugByOrderAndVisitId(MapUtils.get(map,"pharInReceiveDTO")));
    }
    /**
     * @Menthod: queryDMPatientByOrder
     * @Desrciption: 查询配药发药精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InptVisitDTO>> queryDMPatientByOrder(Map map) {
        return WrapperResponse.success(inDistributeDrugBO.queryDMPatientByOrder(MapUtils.get(map,"pharInReceiveDTO")));
    }
}
